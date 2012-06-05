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
import org.apache.catalina.*;
import java.net.HttpURLConnection;
import java.net.URL;
// import org.apache;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
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
import java.io.File;
import java.io.IOException;
import org.apache.tomcat.util.buf.ByteChunk;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//import org.apache.catalina.startup.Tomcat;
//import org.jboss.as.test.integration.web.testcat.TomcatBaseTest;
import org.apache.tomcat.util.buf.ByteChunk;

@RunWith(Arquillian.class)
@RunAsClient
public class TestValidator {

   
  private static Logger log = Logger.getLogger(TestValidator.class);
 
    @Deployment (name = "bug47331.war", testable = false)
    public static WebArchive Bug47331War() {
        ClassLoader tccl = Thread.currentThread().getContextClassLoader();
        String resourcesLocation = "org/jboss/as/test/integration/web/examptom/";

        WebArchive war = ShrinkWrap.create(WebArchive.class, "bug47331.war");
        // war.setWebXML(tccl.getResource(resourcesLocation + "custom-errors-web.xml"));
        war.addAsWebResource(tccl.getResource(resourcesLocation + "bug47331.jsp"), "bug47331.jsp");
        // war.addClass(ErrorGeneratorServlet.class);

        System.out.println(war.toString(true));
        return war;
    }
@Test
public void testbug47331(){}
 

/*

int rc = getUrl("http://localhost:" + "8080" +
                "/bug47331/bug47331.jsp", new ByteChunk(), null);
 

        assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, rc);
}

/*ByteChunk res = getUrl("http://localhost:" + "8080" +
                "/bug47331/bug47331.jsp");

String result = res.toString();

        assertTrue(result.indexOf("<p>${'00-hello world'}</p>") > 0);
        assertTrue(result.indexOf("<p>#{'01-hello world'}</p>") > 0);
        assertTrue(result.indexOf("<p>${'02-hello world'}</p>") > 0);
        assertTrue(result.indexOf("<p>#{'03-hello world'}</p>") > 0);
        assertTrue(result.indexOf("<p>${'04-hello world'}</p>") > 0);
        assertTrue(result.indexOf("<p>#{'05-hello world'}</p>") > 0);
        assertTrue(result.indexOf("<p>${'06-hello world'}</p>") > 0);}
@OperateOnDeployment("bug47331.war")
 public void testbug47331(@ArquillianResource URL baseURL) throws Exception {
     //public void testbug47331(@ArquillianResource(TestValidator.class) URL baseURL) throws Exception {
       int errorCode = 200; // OK :D
        //URL url = new URL(baseURL + "/bug47331.jsp");
        // URL url = new URL("/bug47331/bug47331.jsp");
        //testURL(url, errorCode, null, null);
    System.out.println ("OK ABU REESHA ");

}




    public void testBug47331() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir =
            new File("test/webapp-3.0");
        // app dir is relative to server home
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        tomcat.start();

        int rc = getUrl("http://localhost:" + getPort() +
                "/test/bug47331.jsp", new ByteChunk(), null);

        assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, rc);
    }

    @Test
    public void testTldVersions23() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir =
            new File("test/webapp-2.3");
        // app dir is relative to server home
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        tomcat.start();

        ByteChunk res = getUrl("http://localhost:" + getPort() +
                "/test/tld-versions.jsp");

        String result = res.toString();

        assertTrue(result.indexOf("<p>${'00-hello world'}</p>") > 0);
        assertTrue(result.indexOf("<p>#{'01-hello world'}</p>") > 0);
        assertTrue(result.indexOf("<p>${'02-hello world'}</p>") > 0);
        assertTrue(result.indexOf("<p>#{'03-hello world'}</p>") > 0);
        assertTrue(result.indexOf("<p>${'04-hello world'}</p>") > 0);
        assertTrue(result.indexOf("<p>#{'05-hello world'}</p>") > 0);
        assertTrue(result.indexOf("<p>${'06-hello world'}</p>") > 0);
    }

    @Test
    public void testTldVersions24() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir =
            new File("test/webapp-2.4");
        // app dir is relative to server home
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        tomcat.start();

        ByteChunk res = getUrl("http://localhost:" + getPort() +
                "/test/tld-versions.jsp");

        String result = res.toString();

        assertTrue(result.indexOf("<p>00-hello world</p>") > 0);
        assertTrue(result.indexOf("<p>#{'01-hello world'}</p>") > 0);
        assertTrue(result.indexOf("<p>02-hello world</p>") > 0);
        assertTrue(result.indexOf("<p>#{'03-hello world'}</p>") > 0);
        assertTrue(result.indexOf("<p>04-hello world</p>") > 0);
        assertTrue(result.indexOf("<p>#{'05-hello world'}</p>") > 0);
        assertTrue(result.indexOf("<p>06-hello world</p>") > 0);
    }

    @Test
    public void testTldVersions25() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir =
            new File("test/webapp-2.5");
        // app dir is relative to server home
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        tomcat.start();

        ByteChunk res = getUrl("http://localhost:" + getPort() +
                "/test/tld-versions.jsp");

        String result = res.toString();

        assertTrue(result.indexOf("<p>00-hello world</p>") > 0);
        assertTrue(result.indexOf("<p>#{'01-hello world'}</p>") > 0);
        assertTrue(result.indexOf("<p>02-hello world</p>") > 0);
        assertTrue(result.indexOf("<p>#{'03-hello world'}</p>") > 0);
        assertTrue(result.indexOf("<p>04-hello world</p>") > 0);
        assertTrue(result.indexOf("<p>#{'05-hello world'}</p>") > 0);
        assertTrue(result.indexOf("<p>06-hello world</p>") > 0);
    }

    @Test
    public void testTldVersions30() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir =
            new File("test/webapp-3.0");
        // app dir is relative to server home
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        tomcat.start();

        ByteChunk res = getUrl("http://localhost:" + getPort() +
                "/test/tld-versions.jsp");

        String result = res.toString();

        assertTrue(result.indexOf("<p>00-hello world</p>") > 0);
        assertTrue(result.indexOf("<p>#{'01-hello world'}</p>") > 0);
        assertTrue(result.indexOf("<p>02-hello world</p>") > 0);
        assertTrue(result.indexOf("<p>#{'03-hello world'}</p>") > 0);
        assertTrue(result.indexOf("<p>04-hello world</p>") > 0);
        assertTrue(result.indexOf("<p>#{'05-hello world'}</p>") > 0);
        assertTrue(result.indexOf("<p>06-hello world</p>") > 0);
    }

    public static class Echo extends TagSupport {

        private static final long serialVersionUID = 1L;

        private String echo = null;

        public void setEcho(String echo) {
            this.echo = echo;
        }

        public String getEcho() {
            return echo;
        }

        @Override
        public int doStartTag() throws JspException {
            try {
                pageContext.getOut().print("<p>" + echo + "</p>");
            } catch (IOException e) {
                pageContext.getServletContext().log("Tag (Echo21) failure", e);
            }
            return super.doStartTag();
        }
*/    }


