/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.as.ee.subsystem;

import org.jboss.as.controller.AbstractWriteAttributeHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.jboss.as.ee.component.deployers.DefaultEarSubDeploymentsIsolationProcessor;
import org.jboss.as.ee.structure.SpecDescriptorPropertyReplacementProcessor;
import org.jboss.as.ee.structure.GlobalModuleDependencyProcessor;
import org.jboss.dmr.ModelNode;

/**
 * Handles the "write-attribute" operation for the EE subsystem.
 *
 * @author Brian Stansberry (c) 2011 Red Hat Inc.
 */
public class EeWriteAttributeHandler extends AbstractWriteAttributeHandler<Void> {

    private final DefaultEarSubDeploymentsIsolationProcessor isolationProcessor;
    private final GlobalModuleDependencyProcessor moduleDependencyProcessor;
    private final SpecDescriptorPropertyReplacementProcessor specDescriptorPropertyReplacementProcessor;

    public EeWriteAttributeHandler(final DefaultEarSubDeploymentsIsolationProcessor isolationProcessor,
                                   final GlobalModuleDependencyProcessor moduleDependencyProcessor, final SpecDescriptorPropertyReplacementProcessor specDescriptorPropertyReplacementProcessor) {
        super(GlobalModulesDefinition.INSTANCE, EeSubsystemRootResource.EAR_SUBDEPLOYMENTS_ISOLATED);
        this.isolationProcessor = isolationProcessor;
        this.moduleDependencyProcessor = moduleDependencyProcessor;
        this.specDescriptorPropertyReplacementProcessor = specDescriptorPropertyReplacementProcessor;
    }

    public void registerAttributes(final ManagementResourceRegistration registry) {
        registry.registerReadWriteAttribute(GlobalModulesDefinition.INSTANCE, null, this);
        registry.registerReadWriteAttribute(EeSubsystemRootResource.EAR_SUBDEPLOYMENTS_ISOLATED, null, this);
        registry.registerReadWriteAttribute(EeSubsystemRootResource.SPEC_DESCRIPTOR_PROPERTY_REPLACEMENT, null, this);
    }

    @Override
    protected boolean applyUpdateToRuntime(OperationContext context, ModelNode operation, String attributeName,
                                           ModelNode newValue, ModelNode currentValue, HandbackHolder<Void> handbackHolder) throws OperationFailedException {

        applyUpdateToDeploymentUnitProcessor(context, newValue, attributeName);

        return false;
    }

    @Override
    protected void revertUpdateToRuntime(OperationContext context, ModelNode operation, String attributeName,
                                         ModelNode valueToRestore, ModelNode valueToRevert, Void handback) throws OperationFailedException {
        applyUpdateToDeploymentUnitProcessor(context, valueToRestore, attributeName);
    }

    private void applyUpdateToDeploymentUnitProcessor(final OperationContext context, ModelNode newValue, String attributeName) throws OperationFailedException {
        if (GlobalModulesDefinition.INSTANCE.getName().equals(attributeName)) {
            moduleDependencyProcessor.setGlobalModules(newValue);
        } else if (EeSubsystemRootResource.EAR_SUBDEPLOYMENTS_ISOLATED.getName().equals(attributeName)) {
            boolean isolate = newValue.asBoolean();
            isolationProcessor.setEarSubDeploymentsIsolated(isolate);
        }else if (EeSubsystemRootResource.SPEC_DESCRIPTOR_PROPERTY_REPLACEMENT.getName().equals(attributeName)) {
            boolean enabled = newValue.asBoolean();
            specDescriptorPropertyReplacementProcessor.setSpecDescriptorPropertyReplacement(enabled);
        }
    }
}
