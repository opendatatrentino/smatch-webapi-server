/*******************************************************************************
 * Copyright 2012-2013 University of Trento - Department of Information
 * Engineering and Computer Science (DISI)
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 *
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 ******************************************************************************/ 

/**
 * ResourceServlet.java
 */
package it.unitn.disi.smatch.webapi.server.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 *
 * @author Sergey Kanshin <kanshin@disi.unitn.it>
 * @version May 30, 2011
 */
public class ResourceServlet extends HttpServlet {

    private static final int KB = 1024;
    private Logger logger = Logger.getLogger(getClass());
    private static final long serialVersionUID = 1L;
    private String allowedResourceExtension = "";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String requestUrl = request.getRequestURL().toString();
        String path = null;
        String ext = "";
        if (requestUrl.indexOf(request.getServletPath()) > 0) {
            int k = requestUrl.indexOf(request.getServletPath()) + request.getServletPath().length();
            path = requestUrl.substring(k + 1, requestUrl.length());
            if (path.lastIndexOf('.') > 0) {
                ext = path.substring(path.lastIndexOf('.') + 1, path.length());
            }
        } else {
            logger.warn("Cannot extract resource path from reqeust URL '" + requestUrl + "'");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (ext.length() == 0 || !allowedResourceExtension.contains(ext)) {
            logger.warn("The resource extension '" + ext + "' is not allowed. Possible extensions are " + allowedResourceExtension);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
        in = null != in ? in : Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        if (null == in) {
            logger.warn("Resource '" + path + "' not found");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        response.setContentType(getContentType(ext));

        writeOutput(response, in);

        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void writeOutput(HttpServletResponse response, InputStream in) {
        try {
            byte[] bytes = new byte[KB];
            OutputStream output = response.getOutputStream();
            int len = 0;
            while ((len = in.read(bytes)) > 0) {
                output.write(bytes, 0, len);
            }
            output.flush();
            output.close();
            in.close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e1) {
                    logger.error(e1.getMessage(), e);
                }
            }
        }
    }

    protected String getContentType(String ext) {
        if ("jpg".equals(ext)) {
            return "image/jpeg";
        } else if ("png".equals(ext)) {
            return "image/png";
        } else if ("gif".equals(ext)) {
            return "image/gif";
        } else if ("css".equals(ext)) {
            return "text/css";
        } else if ("js".equals(ext)) {
            return "text/javascript";
        }
        return "";
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    public void init() throws ServletException {
        super.init();
        this.allowedResourceExtension = getInitParameter("allowedResourceExtensions");
        logger.info("resource servlet initialized");
    }
}
