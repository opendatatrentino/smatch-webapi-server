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
package it.unitn.disi.smatch.webapi.server;

import it.unitn.disi.smatch.webapi.client.WebApiClient;
import it.unitn.disi.smatch.webapi.model.smatch.Correspondence;
import it.unitn.disi.smatch.webapi.model.smatch.CorrespondenceItem;
import static it.unitn.disi.smatch.webapi.server.WebApiServer.DEFAULT_PORT;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import static junit.framework.Assert.assertNotNull;
import junit.framework.TestCase;
import org.apache.log4j.Logger;

/**
 *
 * @author Moaz Reyad <reyad@disi.unitn.it>
 * @author nicoletti
 */
public class CallSmatchServerITCase extends TestCase {

    public CallSmatchServerITCase(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testMatching() {
        WebApiClient api = WebApiClient.getInstance(Locale.ENGLISH,
                "localhost", DEFAULT_PORT);

        String sourceContextRoot = "Snow Registry";
        String targetContextRoot = "Location";

        List<String> sourceContextNodes = Arrays.asList("Code",
                "Name",
                "Short Name",
                "height",
                "Latitude",
                "Longitude");

        List<String> targetContextNodes = Arrays.asList("Name",
                "Description",
                "Latitude",
                "Longitude");

        Correspondence correspondence = api.match(sourceContextRoot, sourceContextNodes, targetContextRoot, targetContextNodes);

        assertNotNull("Match returns null correspondence", correspondence);

        List<CorrespondenceItem> correspondenceItems = correspondence.getCorrespondenceItems();

        assertNotNull("Correspondence Items are null", correspondenceItems);

        // For default configurations of S-Match, there are 11 correspondace items
        assertTrue("Wrong number of Correspondence Items", correspondenceItems.size() == 11);

        for (CorrespondenceItem item : correspondenceItems) {
            String correspondenceLine = item.getSource() + "\t"
                    + String.valueOf(item.getRelation())
                    + "\t" + item.getTarget();

            Logger.getLogger("Test Smatch").info(correspondenceLine);
        }
    }
}
