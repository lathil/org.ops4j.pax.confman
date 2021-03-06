/*
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
package org.ops4j.pax.configmanager.internal;

import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ops4j.pax.configmanager.IConfigurationFileHandler;
import org.ops4j.pax.configmanager.IConfigurationUpdater;
import org.ops4j.pax.configmanager.internal.handlers.PropertiesFileConfigurationHandler;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;

public final class Activator implements BundleActivator
{

    private static final Log LOGGER = LogFactory.getLog( Activator.class );
    private static final String SERVICE_NAME = IConfigurationFileHandler.class.getName();

    private ServiceTracker m_configTracker;
    private ConfigurationFileHandlerServiceTracker m_configFileTracker;
    private ServiceRegistration m_propertyFileHandlerRegistration;
    private ConfigurationAdminFacade m_configAdminFacade;
    private ServiceRegistration m_configUpdaterRegistration;

    public void start( final BundleContext context )
        throws Exception
    {
        if( LOGGER.isDebugEnabled() )
        {
            Bundle contextBundle = context.getBundle();
            String symbolicName = contextBundle.getSymbolicName();
            LOGGER.debug( "Starting [" + symbolicName + "]..." );
        }

        PropertiesFileConfigurationHandler handler = new PropertiesFileConfigurationHandler();
        m_propertyFileHandlerRegistration = context.registerService( Activator.SERVICE_NAME, handler, new Hashtable() );
        m_configAdminFacade = new ConfigurationAdminFacade( new ConfigurationAdminFacade.PropertyResolver()
        {

            /**
             * Resolves properties from bundle context.
             * @see ConfigurationAdminFacade.PropertyResolver#getProperty(String)
             */
            public String getProperty( final String key )
            {
                return context.getProperty( key );
            }

            }
        );

        m_configTracker = new ConfigAdminServiceTracker( context, m_configAdminFacade );
        m_configTracker.open();

        m_configFileTracker = new ConfigurationFileHandlerServiceTracker( context, m_configAdminFacade );
        m_configFileTracker.open();

        ConfigurationUpdater configurationUpdater = new ConfigurationUpdater( this.m_configAdminFacade );
        this.m_configUpdaterRegistration = context.registerService( IConfigurationUpdater.class.getName(),
            configurationUpdater, new Hashtable() );
    }

    public void stop( BundleContext context )
        throws Exception
    {
        if( LOGGER.isDebugEnabled() )
        {
            Bundle contextBundle = context.getBundle();
            String symbolicName = contextBundle.getSymbolicName();
            LOGGER.debug( "Stopping [" + symbolicName + "]" );
        }

        m_propertyFileHandlerRegistration.unregister();
        m_propertyFileHandlerRegistration = null;

        m_configUpdaterRegistration.unregister();
        m_configUpdaterRegistration = null;

        m_configFileTracker.close();
        m_configFileTracker = null;

        m_configTracker.close();
        m_configTracker = null;

        m_configAdminFacade.dispose();
        m_configAdminFacade = null;
    }
}
