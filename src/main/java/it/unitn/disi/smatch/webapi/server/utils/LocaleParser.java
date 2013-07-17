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
 * LocaleParser.java May 31, 2010
 */
package it.unitn.disi.smatch.webapi.server.utils;

import java.util.Locale;

/**
 *
 *
 * @author Sergey Kanshin, kanshin@disi.unitn.it
 * @date May 31, 2010
 */
public final class LocaleParser {

    private LocaleParser() {
    }

    public static Locale parseFromString(String source, Locale defaultLocale) {
        if (null == source || "".equals(source)) {
            return defaultLocale;
        }
        if (Locale.ENGLISH.toString().equals(source)) {
            return Locale.ENGLISH;
        } else if (Locale.ITALY.toString().equals(source)) {
            return Locale.ITALY;
        } else if (Locale.ITALIAN.toString().equals(source)) {
            return Locale.ITALIAN;
        }
        return defaultLocale;
    }
}
