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

package it.unitn.disi.smatch.webapi.server.utils;

/**
 * A utility class to store the request time for a thread
 *
 * @author Viktor Pravdin <pravdin@disi.unitn.it>
 * @date Dec 13, 2011 1:10:52 PM
 */
public final class RequestTimer {

    private static final ThreadLocal<Long> TIMER = new ThreadLocal<Long>();

    private RequestTimer() {
    }

    /**
     * Sets the request start time
     *
     * @param time The request start time
     */
    public static void setTime(Long time) {
        TIMER.set(time);
    }

    /**
     * Sets the request start time to the current time
     */
    public static void setToNow() {
        TIMER.set(System.currentTimeMillis());
    }

    /**
     * Gets the request start time
     *
     * @return The request start time or null if the current thread hasn't set
     * the request start time.
     */
    public static Long getTime() {
        return TIMER.get();
    }
}
