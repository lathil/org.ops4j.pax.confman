/*
 * Copyright 2008 Alin Dreghiciu.
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
package org.ops4j.pax.cm.service.internal;

import org.ops4j.pax.cm.domain.ConfigurationTarget;
import org.ops4j.pax.cm.domain.ServiceIdentity;
import org.ops4j.pax.cm.domain.ConfigurationSource;
import org.ops4j.pax.cm.service.internal.AdminCommand;

/**
 * Configuration strategy applied during configuration processing. Basically there are two configuration startegies:br/>
 * - One for configuring a ManagedService<br/>
 * - One for configuring a ManagedServiceFactory
 *
 * @author Alin Dreghiciu
 * @since 0.3.0, February 15, 2008
 */
public interface ConfigurationStrategy
{

    /**
     * Create service identity.
     *
     * @param pid        persistent identifier
     * @param factoryPid persisten factory id
     * @param location   bundle location
     *
     * @return created service identity
     */
    ServiceIdentity createServiceIdentity( String pid,
                                           String factoryPid,
                                           String location );
    /**
     * Callback before finding an adaptor & adaptation process.
     * The strategy can do customisation of for example metadata.
     *
     * @param source configuration source
     */
    void prepareSource( ConfigurationSource source );

    /**
     * Creates a configuration command for configuration target.
     *
     * @param configurationTarget configuration target model
     *
     * @return created configuration command
     */
    AdminCommand createConfigurationCommand( ConfigurationTarget configurationTarget );

}
