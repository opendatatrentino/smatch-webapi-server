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
 * AbstractController.java
 */
package it.unitn.disi.smatch.webapi.server.controllers;

import it.unitn.disi.smatch.webapi.server.utils.JsonUtils;
import it.unitn.disi.smatch.webapi.server.utils.RequestTimer;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * An abstract controller which will be extended by all controllers.  * 
 *
 * @author Sergey Kanshin <kanshin@disi.unitn.it>
 * @version Feb 24, 2011
 */
public abstract class AbstractController {

    private Logger logger = Logger.getLogger(getClass());
    private static final String RESPONSE = "RESPONSE:\n";

    /**Map of method name and type*/
    private static Map<String, String> methods = new HashMap<String, String>();
    @Value("${smatch.webapi.version}")
    private String version;
    //@Autowired
    private JsonUtils helper;
    private static final int KB = 1024;

    protected AbstractController() {
        helper = new JsonUtils();
    }

    protected JsonUtils getJSONHelper() {
        return helper;
    }

    protected String getVersion(){
        return version;
    }
    
    protected static void addMethodName(String name, String methodType) {
        methods.put(name, methodType);
    }

    protected static Map<String, String> getMethodNames() {
        return methods;
    }

    protected JSONObject getJSONRaw(HttpServletRequest request)
            throws IOException, JSONException {
        InputStream in = request.getInputStream();
        int len = 0;
        byte[] b = new byte[KB];
        StringBuffer buff = new StringBuffer();
        while ((len = in.read(b)) > 0) {
            buff.append(new String(b, 0, len));
        }
        logger.debug("REQUEST: " + request.getMethod() + " " + request.
                getRequestURI() + "\n" + buff.toString());
        return new JSONObject(buff.toString());
    }

    protected JSONObject getJSONRequest(HttpServletRequest request)
            throws IOException, JSONException  {
        JSONObject jContent = getJSONRaw(request);
        return jContent.getJSONObject("request");
    }

    protected JSONObject getJSONFromRequest(JSONObject request) throws
            JSONException {
        if (logger.isDebugEnabled()) {
            logger.debug("REQUEST: " + request.toString(2));
        }
        return request.getJSONObject("request");
    }

    protected JSONObject getJSONParameters(JSONObject jRequest) throws
            JSONException {
        return jRequest.getJSONObject("parameters");
    }

    protected JSONObject prepareResponse(JSONObject jResponse) throws
            JSONException {
        Long requestTime = RequestTimer.getTime();
        JSONObject jContent = helper.createResponse(jResponse, requestTime);
        if (logger.isDebugEnabled()) {
            logger.debug(RESPONSE + jContent.toString(2));
        }
        return jContent;
    }

    protected JSONObject prepareNoResponse() throws JSONException {
        JSONObject jResponse = new JSONObject();
        Long requestTime = RequestTimer.getTime();
        JSONObject jContent = helper.createResponse(jResponse, requestTime);
        if (logger.isDebugEnabled()) {
            logger.debug(RESPONSE + jContent.toString(2));
        }
        return jContent;
    }

    protected JSONObject prepareErrorResponse(String message, int httpStatus)
            throws JSONException {
        JSONObject jContent = helper.createErrorResponse(message, httpStatus);
        if (logger.isDebugEnabled()) {
            logger.debug(RESPONSE + jContent.toString(2));
        }
        return jContent;
    }

    protected JSONObject prepareCreateResourceResponse(Long id) throws
            JSONException {
        JSONObject jResponse = new JSONObject();
        jResponse.put("id", id);
        Long requestTime = RequestTimer.getTime();
        JSONObject jContent = helper.createResponse(jResponse, requestTime);
        if (logger.isDebugEnabled()) {
            logger.debug(RESPONSE + jContent.toString(2));
        }
        return jContent;
    }
    // currently used only when attribute is created

    protected JSONObject prepareCreateResourceResponse(String id) throws
            JSONException {
        JSONObject jResponse = new JSONObject();
        jResponse.put("id", id);
        Long requestTime = RequestTimer.getTime();
        JSONObject jContent = helper.createResponse(jResponse, requestTime);
        if (logger.isDebugEnabled()) {
            logger.debug(RESPONSE + jContent.toString(2));
        }
        return jContent;
    }

    protected JSONObject prepareDeleteResouceResponse(Boolean flag) throws
            JSONException {
        JSONObject jResponse = new JSONObject();
        jResponse.put("deleted", flag);
        Long requestTime = RequestTimer.getTime();
        JSONObject jContent = helper.createResponse(jResponse, requestTime);
        if (logger.isDebugEnabled()) {
            logger.debug(RESPONSE + jContent.toString(2));
        }
        return jContent;
    }
    
    protected JSONObject prepareDeleteResouceResponse(int count) throws
            JSONException {
        JSONObject jResponse = new JSONObject();
        jResponse.put("deleted", count>0);
        jResponse.put("count", count);
        Long requestTime = RequestTimer.getTime();
        JSONObject jContent = helper.createResponse(jResponse, requestTime);
        if (logger.isDebugEnabled()) {
            logger.debug(RESPONSE + jContent.toString(2));
        }
        return jContent;
    }
    
    @ResponseBody
    @ExceptionHandler(JSONException.class)
    protected 
    JSONObject handleJSONException(JSONException e) throws JSONException {
        logger.warn(e.getMessage(), e);
        return prepareErrorResponse(e.getMessage(),
                HttpServletResponse.SC_BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    protected 
    JSONObject handleIllegalArgumentException(IllegalArgumentException e) throws
            JSONException {
        logger.warn(e.getMessage(), e);
        return prepareErrorResponse(e.getMessage(),
                HttpServletResponse.SC_BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(IOException.class)
    protected 
    JSONObject handleIOException(IOException e) throws JSONException {
        logger.error(e.getMessage(), e);
        return prepareErrorResponse(e.getMessage(),
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected 
    JSONObject handleMethodException(Exception e) throws JSONException {
        logger.error(e.getMessage(), e);
        return prepareErrorResponse(e.getMessage(),
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }   

    @ResponseBody
    @ExceptionHandler(Exception.class)
    protected 
    JSONObject handleException(Exception e) throws JSONException {
        logger.error(e.getMessage(), e);
        return prepareErrorResponse(e.getMessage()!=null?e.getMessage():e.getClass().getName(),
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
