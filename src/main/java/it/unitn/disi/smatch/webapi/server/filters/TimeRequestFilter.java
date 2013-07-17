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
 * TimeRequestFilter.java
 */
package it.unitn.disi.smatch.webapi.server.filters;

import it.unitn.disi.smatch.webapi.server.utils.RequestTimer;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 *
 * @author Sergey Kanshin <kanshin@disi.unitn.it>
 * @version May 6, 2011
 */
public class TimeRequestFilter extends OncePerRequestFilter {

    public static final String TIME_REQEUST_ATTR = "time_reqeust_attr";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        RequestTimer.setToNow();
        request.getSession().setAttribute(TIME_REQEUST_ATTR, new Long(System.currentTimeMillis()));
        filterChain.doFilter(request, response);
    }
}
