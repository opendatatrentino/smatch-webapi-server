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

package it.unitn.disi.smatch.webapi.server.servlets;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * This class is used to add/modify the parameters from the existing
 * {@link HttpServletRequest}
 *
 * @author vinay
 *
 */
public class CustomHttpServletRequest extends HttpServletRequestWrapper {

    public CustomHttpServletRequest(HttpServletRequest request) {
        super(request);
    }
    private Map<String, String[]> params = new HashMap();

    @Override
    public String getParameter(String name) {
        // if we added one, return that one
        if (params.get(name) != null) {
            return (String) params.get(name)[0];
        }
        // otherwise return what's in the original request
        HttpServletRequest req = (HttpServletRequest) super.getRequest();
        return modify(req.getParameter(name));
    }

    @Override
    public String[] getParameterValues(String paramName) {
        // if we added one, return that one
        if (params.get(paramName) != null) {
            return (String[]) params.get(paramName);
        }
        // otherwise return what's in the original request
        String values[] = super.getParameterValues(paramName);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                values[i] = modify(values[i]);
            }
        }
        return values;
    }

    public void addParameter(String name, String value) {
        params.put(name, new String[]{value});
    }

    /**
     * This method is used to trim/remove trailing characters from parameter
     *
     * @param value
     * @return
     */
    public String modify(String value) {
        String result = value;
        
        if (result != null) {
            result = result.trim();
            if (result.startsWith("\"")
                    && result.endsWith("\"")) {
                result = result.substring(1,
                        result.length() - 1);
            }
            if (result.startsWith("\'")
                    && result.endsWith("\'")) {
                result = result.substring(1,
                        result.length() - 1);
            }
            if (result.isEmpty()) {
                result = null;
            }
        }
        return result;
    }
}
