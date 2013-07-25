/**
 * *****************************************************************************
 * Copyright 2012-2013 University of Trento - Department of Information
 * Engineering and Computer Science (DISI)
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Lesser General Public License (LGPL)
 * version 2.1 which accompanies this distribution, and is available at
 *
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 *****************************************************************************
 */
/**
 * Service
 *
 * Version: 1.0
 *
 * Date: Jan 22, 2010
 *
 */
package it.unitn.disi.smatch.webapi.server;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.mortbay.component.AbstractLifeCycle;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Request;
import org.mortbay.jetty.RequestLog;
import org.mortbay.jetty.Response;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.ContextHandlerCollection;
import org.mortbay.jetty.handler.DefaultHandler;
import org.mortbay.jetty.handler.HandlerCollection;
import org.mortbay.jetty.handler.RequestLogHandler;
import org.mortbay.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Executor class for Sweb service module
 *
 * @author Sergey Kanshin <kanshin@disi.unitn.it>
 *
 */
public class WebApiServer {

    /**
     * The default port used while running the server
     */
    public static final int DEFAULT_PORT = 9090;
    private Server server;
    private WebAppContext context;
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Creates an instance of the Web API Server
     *
     * @param contextPath The path of the context in the file system. Default is
     * value is current path.
     * @param port The HTTP port for the server. Default value is 9090.
     */
    public WebApiServer(String contextPath, Integer port) {

        Integer serverPort = port;

        if (null == serverPort) {
            serverPort = DEFAULT_PORT;
        }

        server = new Server(serverPort);
        HandlerCollection handlers = new HandlerCollection();
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        context = new WebAppContext(contexts, "webapi", contextPath);
        RequestLogHandler requestLogHandler = new RequestLogHandler();
        HttpLogger httpLog = new HttpLogger();
        requestLogHandler.setRequestLog(httpLog);
        handlers.setHandlers(new Handler[]{contexts, new DefaultHandler(),
            requestLogHandler});
        server.setHandler(handlers);
    }

    /**
     * Initialization of server
     *
     * @param webappDir The path of the directory for the Web Application in the
     * file system.
     */
    public void init(String webappDir) {
        logger.trace("Starting webapp from '" + webappDir + "'");
        context.setWar(webappDir + "/WEB-INF");
        context.setResourceBase(webappDir);
    }

    /**
     * Starts the server
     *
     * @throws InterruptedException if an error occurred
     */
    public void start() throws InterruptedException {
        try {
            server.start();
            logger.info("S-Match webapi server started");
            server.join();
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(WebApiServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Stops the server
     */
    public void stop()
    {
        try {
            server.stop();
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(WebApiServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * The main function to start the server.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException,
            InterruptedException {
        String webappDir = getWebappDir(args);
        Integer port = DEFAULT_PORT;
        String contextPath = "/";
        if (args.length > 1) {
            contextPath = args[1];
        }
        if (args.length > 2) {
            port = Integer.parseInt(args[2]);
        }
        WebApiServer startServer = new WebApiServer(contextPath, port);
        startServer.init(webappDir);
        startServer.start();
    }

    /**
     * Finds the directory of the Web Application
     *
     * @param args command line arguments
     * @return the path to the Web Application directory
     * @throws IOException
     */
    public static String getWebappDir(String[] args) throws IOException {
        if (args.length > 0) {
            File dir = new File(args[0]);
            if (dir.exists()) {
                return dir.getCanonicalPath();
            } else {
                throw new IOException("Cannot found '" + args[0] + "' directory");
            }
        }
        File dir = new File("./src/main/webapp");
        if (dir.exists()) {
            return dir.getCanonicalPath();
        }
        dir = new File("./webapp");
        if (dir.exists()) {
            return dir.getCanonicalPath();
        }
        dir = new File("./../webapp");
        if (dir.exists()) {
            return dir.getCanonicalPath();
        }
        throw new IOException("Cannot find webapp dir");
    }

    /**
     * A helper class for HTTP Logging
     */
    private class HttpLogger extends AbstractLifeCycle implements RequestLog {

        private Logger log = LoggerFactory.getLogger(WebApiServer.class);

        @Override
        public void log(Request req, Response resp) {
            if (!isStarted()) {
                return;
            }
            if (log.isDebugEnabled()) {
                StringBuilder buf = new StringBuilder();
                buf.append(req.getServerName());
                buf.append(" from ");
                buf.append(req.getRemoteHost());
                buf.append(" - ");
                buf.append(req.getMethod());
                buf.append(" ");
                buf.append(req.getUri().toString());
                buf.append(" ");
                buf.append(resp.getStatus());
                if (resp.getReason() != null) {
                    buf.append(" ");
                    buf.append(resp.getReason());
                }
                log.debug(buf.toString());
            }
        }
    }
}
