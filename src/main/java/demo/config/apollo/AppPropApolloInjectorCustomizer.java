package demo.config.apollo;

import com.ctrip.framework.apollo.core.spi.Ordered;
import com.ctrip.framework.apollo.spi.ApolloInjectorCustomizer;
import com.ctrip.framework.foundation.Foundation;
import lombok.extern.slf4j.Slf4j;

import static com.ctrip.framework.apollo.core.ApolloClientSystemConsts.APOLLO_CLUSTER;
import static com.ctrip.framework.apollo.core.ConfigConsts.CLUSTER_NAME_DEFAULT;

@Slf4j
public class AppPropApolloInjectorCustomizer implements ApolloInjectorCustomizer {

    static {
        try {

            String cluster = Foundation.app().getProperty(APOLLO_CLUSTER, CLUSTER_NAME_DEFAULT);
            System.setProperty(APOLLO_CLUSTER, cluster);
        } catch (Exception e) {
            log.warn("Exception!", e);
        }

    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public <T> T getInstance(Class<T> clazz) {
        return null;
    }

    @Override
    public <T> T getInstance(Class<T> clazz, String name) {
        return null;
    }
}
