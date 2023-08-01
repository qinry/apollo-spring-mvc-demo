package demo.config.apollo;

import com.ctrip.framework.foundation.internals.DefaultProviderManager;
import com.ctrip.framework.foundation.internals.provider.DefaultApplicationProvider;
import com.ctrip.framework.foundation.internals.provider.DefaultNetworkProvider;
import com.ctrip.framework.foundation.internals.provider.DefaultServerProvider;
import com.ctrip.framework.foundation.spi.provider.Provider;

public class AppPropProviderManager extends DefaultProviderManager {

    public AppPropProviderManager() {
        // Load per-application configuration, like app id, from classpath://META-INF/app.properties
        Provider applicationProvider = new DefaultApplicationProvider();
        applicationProvider.initialize();
        register(applicationProvider);
        // env variable from app configuration, allow put in system property
        String env = applicationProvider.getProperty("env", "DEV");
        System.setProperty("env", env);
        // Load network parameters
        Provider networkProvider = new DefaultNetworkProvider();
        networkProvider.initialize();
        register(networkProvider);

        // Load environment (fat, fws, uat, prod ...) and dc, from /opt/settings/server.properties, JVM property and/or OS
        // environment variables.
        Provider serverProvider = new DefaultServerProvider();
        serverProvider.initialize();
        register(serverProvider);
    }
}
