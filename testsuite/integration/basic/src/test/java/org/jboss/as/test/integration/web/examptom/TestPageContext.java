/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.as.test.integration.web.examptom;
import static org.junit.Assert.assertTrue;

import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpEntity; 
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.logging.Logger;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
//import java.io.File;
//import ByteChunk.class;
import static org.junit.Assert.assertTrue;
//import org.jboss.as.jboss.web;
import org.junit.Test;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.logging.Logger;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
//import org.apache.catalina.startup.Tomcat;
//import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.tomcat.util.buf.ByteChunk;
@RunWith(Arquillian.class)
@RunAsClient
public class TestPageContext {

   
  private static Logger log = Logger.getLogger(TestPageContext.class);
 
    @Deployment (name = "bug49196.war", testable = false)
    public static WebArchive Bug49196War() {
        ClassLoader tccl = Thread.currentThread().getContextClassLoader();
        String resourcesLocation = "org/jboss/as/test/integration/web/examptom/";

        WebArchive war = ShrinkWrap.create(WebArchive.class, "bug49196.war");
        // war.setWebXML(tccl.getResource(resourcesLocation + "custom-errors-web.xml"));
        war.addAsWebResource(tccl.getResource(resourcesLocation + "bug49196.jsp"), "bug49196.jsp");
        // war.addClass(ErrorGeneratorServlet.class);

        System.out.println(war.toString(true));
        return war;
    }


    @Test
    public void testBug49196() throws Exception   {

//@ArquillianResource URL url = new URL(base, "bug49196/bug49196.jsp");

/*        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0");
        // app dir is relative to server home
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        tomcat.start();*/



       // ByteChunk res = getUrl("http://localhost:" + getPort() +
               // "/bug49192/bug49196.jsp");
	
        // ByteChunk res = (ByteChunk)"http://localhost:8080/bug49196/bug49196.jsp";
        HttpGet httpget = new HttpGet("http://localhost:8080/bug49196/bug49196.jsp");
        DefaultHttpClient httpclient = new DefaultHttpClient();

        log.info("executing request" + httpget.getRequestLine());
       

        HttpResponse response = httpclient.execute(httpget);
        HttpEntity result = response.getEntity();
        String url = result.toString();
        //String result = response.getBody();
	//String url = result;
        assertTrue(url.contains("OK"));

/* public  String asUTF8String(InputStream in) {
        // Precondition check
        Validate.notNull(in, "Stream must be specified");

        StringBuilder buffer = new StringBuilder();
        String line;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, CHARSET_UTF8));
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append(Character.LINE_SEPARATOR);
            }
        } catch (IOException ioe) {
            throw new RuntimeException("Error in obtaining string from " + in, ioe);
        } finally {
            try {
                in.close();
            } catch (IOException ignore) {
                if (log.isLoggable(Level.FINER)) {
                    log.finer("Could not close stream due to: " + ignore.getMessage() + "; ignoring");
                }
            }
        }

        return buffer.toString();
    }




String result = IOUtils.asUTF8String(url.openStream());

assertTrue(result.contains("OK"));*/








    }
}

