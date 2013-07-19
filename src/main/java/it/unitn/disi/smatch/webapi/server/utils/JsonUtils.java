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
package it.unitn.disi.smatch.webapi.server.utils;

import it.unitn.disi.smatch.webapi.model.Status;
import it.unitn.disi.smatch.webapi.model.JSONHelper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Utility methods to work with the JSON-based communication protocol
 *
 * @author Viktor Pravdin <pravdin@disi.unitn.it>
 * @date Nov 28, 2011 3:04:35 PM
 */
@Component
public class JsonUtils {

    @Value("${sweb.webapi.version}")
    private String version;

    /**
     * Creates a JSON response with the given body and optional request
     * processing time
     *
     * @param body The body
     * @param requestTime The request processing time, can be null
     * @return The JSON response
     * @throws JSONException
     */
    public JSONObject createResponse(JSONObject body, Long requestTime) throws
            JSONException {
        body.put(JSONHelper.TAG_VERSION, version);
        body.put(JSONHelper.TAG_STATUS, Status.OK.name());
        if (requestTime != null) {
            body.put(JSONHelper.TAG_TIME, System.currentTimeMillis()
                    - requestTime.longValue());
        }
        JSONObject resp = new JSONObject();
        resp.put(JSONHelper.TAG_RESPONSE, body);
        return resp;
    }

    /**
     * Creates a JSON response with the error message and code
     *
     * @param message the error message
     * @param code the error code
     * @return the JSON response with the error message and code
     * @throws JSONException
     */
    public JSONObject createErrorResponse(String message, Integer code) throws
            JSONException {
        JSONObject jError = new JSONObject();
        if (code != null) {
            jError.put(JSONHelper.TAG_CODE, code);
        }
        jError.put(JSONHelper.TAG_MESSAGE, message);
        JSONObject jResponse = new JSONObject();
        jResponse.put(JSONHelper.TAG_VERSION, version);
        jResponse.put(JSONHelper.TAG_STATUS, Status.FAIL.name());
        jResponse.put(JSONHelper.TAG_ERROR, jError);
        JSONObject resp = new JSONObject();
        resp.put(JSONHelper.TAG_RESPONSE, jResponse);
        return resp;
    }
}
