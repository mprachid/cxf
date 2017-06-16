/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.cxf.osgi.itests.soap;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.testutil.common.TestUtil;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class HttpTestActivator implements BundleActivator {
    public static final String PORT = TestUtil.getPortNumber(HttpTestActivator.class);
    private Server server;
    private Server serverJetty;

    @Override
    public void start(BundleContext arg0) throws Exception {
        server = createTestServer("/greeter");
        serverJetty = createTestServer("http://localhost:" + PORT + "/cxf/greeter");
    }

    private Server createTestServer(String url) {
        JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
        factory.setServiceClass(Greeter.class);
        factory.setAddress(url);
        factory.setServiceBean(new GreeterImpl());
        return factory.create();
    }

    @Override
    public void stop(BundleContext arg0) throws Exception {
        server.destroy();
        serverJetty.destroy();
    }

}