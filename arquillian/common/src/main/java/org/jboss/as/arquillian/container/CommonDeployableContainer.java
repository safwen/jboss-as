/*
 * JBoss, Home of Professional Open Source
 * Copyright 2009, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.arquillian.container;

import org.jboss.arquillian.container.spi.client.container.DeployableContainer;
import org.jboss.arquillian.container.spi.client.container.DeploymentException;
import org.jboss.arquillian.container.spi.client.container.LifecycleException;
import org.jboss.arquillian.container.spi.client.protocol.ProtocolDescription;
import org.jboss.arquillian.container.spi.client.protocol.metadata.ProtocolMetaData;
import org.jboss.arquillian.container.spi.context.annotation.ContainerScoped;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.ApplicationScoped;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.as.controller.client.helpers.standalone.ServerDeploymentManager;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.descriptor.api.Descriptor;
import org.jboss.util.NotImplementedException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.jboss.as.arquillian.container.Authentication.getCallbackHandler;

/**
 * A JBossAS deployable container
 *
 * @author Thomas.Diesler@jboss.com
 * @since 17-Nov-2010
 */
public abstract class CommonDeployableContainer<T extends CommonContainerConfiguration> implements DeployableContainer<T> {

    private static final String JBOSS_URL_PKG_PREFIX = "org.jboss.ejb.client.naming";

    private final Logger log = Logger.getLogger(CommonDeployableContainer.class.getName());
    private T containerConfig;
    private ManagementClient managementClient;

    @Inject
    @ContainerScoped
    private InstanceProducer<ArchiveDeployer> archiveDeployerInst;

    @Inject
    @ApplicationScoped
    private InstanceProducer<Context> jndiContext;

    @Inject
    @ContainerScoped
    private InstanceProducer<ManagementClient> managementClientInst;

    @Override
    public ProtocolDescription getDefaultProtocol() {
        return new ProtocolDescription("jmx-as7");
    }

    @Override
    public void setup(T config) {
        containerConfig = config;
    }

    @Override
    public final void start() throws LifecycleException {
        if(containerConfig.getUsername() != null) {
            Authentication.username = containerConfig.getUsername();
            Authentication.password = containerConfig.getPassword();
        }

        ModelControllerClient modelControllerClient = null;
        try {
            modelControllerClient = ModelControllerClient.Factory.create(
                    containerConfig.getManagementAddress(),
                    containerConfig.getManagementPort(),
                    getCallbackHandler());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        managementClient = new ManagementClient(modelControllerClient, containerConfig.getManagementAddress(), containerConfig.getManagementPort());

        managementClientInst.set(managementClient);

        archiveDeployerInst.set(new ArchiveDeployer(
                ServerDeploymentManager.Factory.create(modelControllerClient)));

        try {
            final Properties jndiProps = new Properties();
            jndiProps.setProperty(Context.URL_PKG_PREFIXES, JBOSS_URL_PKG_PREFIX);
            jndiContext.set(new InitialContext(jndiProps));
        } catch (final NamingException ne) {
            throw new LifecycleException("Could not set JNDI Naming Context", ne);
        }

        try {
            startInternal();
        } catch (LifecycleException e) {
            safeCloseClient();
            throw e;
        }
    }

    protected abstract void startInternal() throws LifecycleException;

    @Override
    public final void stop() throws LifecycleException {
        try {
            stopInternal();
        } finally {
            safeCloseClient();
        }
    }

    protected abstract void stopInternal() throws LifecycleException;

    protected T getContainerConfiguration() {
        return containerConfig;
    }

    protected ManagementClient getManagementClient() {
        return managementClient;
    }

    protected ModelControllerClient getModelControllerClient() {
        return managementClient.getControllerClient();
    }

    @Override
    public ProtocolMetaData deploy(Archive<?> archive) throws DeploymentException {
        ArchiveDeployer archiveDeployer = archiveDeployerInst.get();
        String runtimeName = archiveDeployer.deploy(archive);

        return managementClient.getDeploymentMetaData(runtimeName);
    }

    @Override
    public void undeploy(Archive<?> archive) throws DeploymentException {
        ArchiveDeployer archiveDeployer = archiveDeployerInst.get();
        archiveDeployer.undeploy(archive.getName());
    }

    @Override
    public void deploy(Descriptor descriptor) throws DeploymentException {
        throw new NotImplementedException();
    }

    @Override
    public void undeploy(Descriptor descriptor) throws DeploymentException {
        throw new NotImplementedException();
    }

    private void safeCloseClient() {
        try {
            managementClient.close();
        } catch (Exception e) {
            log.log(Level.WARNING, "Caught exception closing ModelControllerClient", e);
        }
    }

}
