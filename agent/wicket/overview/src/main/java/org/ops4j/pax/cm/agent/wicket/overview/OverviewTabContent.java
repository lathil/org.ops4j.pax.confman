/*
 * Copyright 2006 Edward Yakop.
 *
 * Licensed  under the  Apache License,  Version 2.0  (the "License");
 * you may not use  this file  except in  compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed  under the  License is distributed on an "AS IS" BASIS,
 * WITHOUT  WARRANTIES OR CONDITIONS  OF ANY KIND, either  express  or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package org.ops4j.pax.cm.agent.wicket.overview;

import java.util.Locale;
import org.ops4j.pax.cm.agent.wicket.WicketApplicationConstant;
import org.ops4j.pax.wicket.service.Content;

/**
 * {@code OverviewTabContent} interface represents {@code OverviewPage} content for menu tab.
 *
 * @author Edward Yakop
 * @see OverviewTab
 * @since 0.1.0
 */
public interface OverviewTabContent extends Content
{
    /**
     * {@code OverviewTabContent} must have this as destionation id.
     *
     * @since 0.1.0
     */
    String OVERVIEW_TAB_DESTINATION_ID = WicketApplicationConstant.Overview.DESTINATION_ID_MENU_TAB;

    /**
     * Create tab with {@code locale} as specified.
     *
     * @param locale The locale of the tab.
     *
     * @return Returns tab with the specified locale.
     *
     * @since 0.1.0
     */
    OverviewTab createTab( Locale locale );
}