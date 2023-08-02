package demo.config.apollo;

import com.ctrip.framework.apollo.core.ConfigConsts;
import com.ctrip.framework.apollo.core.enums.Env;
import com.ctrip.framework.apollo.core.enums.EnvUtils;
import com.ctrip.framework.apollo.core.spi.Ordered;
import com.ctrip.framework.apollo.core.utils.StringUtils;
import com.ctrip.framework.apollo.spi.ApolloInjectorCustomizer;
import com.ctrip.framework.apollo.util.ConfigUtil;
import com.ctrip.framework.foundation.Foundation;
import com.ctrip.framework.foundation.internals.Utils;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

@Slf4j
public class ConfigUtilApolloInjectorCustomizer implements ApolloInjectorCustomizer {

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public <T> T getInstance(Class<T> clazz) {
        boolean present = ClassUtils.isPresent("com.ctrip.framework.apollo.util.ConfigUtil", ApolloInjectorCustomizer.class.getClassLoader());
        if (!present) {
            return null;
        }
        if (StringUtils.equals("com.ctrip.framework.apollo.util.ConfigUtil", ClassUtils.getQualifiedName(clazz))) {
            return (T) createConfigUtil();
        }
        return null;
    }

    private ConfigUtil createConfigUtil() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ConfigUtil.class);
        enhancer.setCallback((MethodInterceptor)(obj, method, args, proxy) -> {
            String name = method.getName();
            if ("getApolloEnv".equals(name) && method.getReturnType() == Env.class) {
                String env = System.getProperty("env");
                if (!Utils.isBlank(env)) {
                    env = env.trim();
                    return EnvUtils.transformEnv(env);
                }
                env = System.getenv("ENV");
                if (!Utils.isBlank(env)) {
                    env = env.trim();
                    return EnvUtils.transformEnv(env);
                }
                env = Foundation.server().getProperty("env", "");
                if (!Utils.isBlank(env)) {
                    env = env.trim();
                    return EnvUtils.transformEnv(env);
                }
                env = Foundation.app().getProperty("env", "");
                if (!Utils.isBlank(env)) {
                    env = env.trim();
                    return EnvUtils.transformEnv(env);
                }
                return EnvUtils.transformEnv(null);
            } else {
                return proxy.invokeSuper(obj, args);
            }
        });

        ConfigUtil configUtil = (ConfigUtil) enhancer.create();
        setCluster(configUtil);

        return configUtil;
    }

    private void setCluster(ConfigUtil configUtil) {
        Field clusterField = ReflectionUtils.findField(ConfigUtil.class, "cluster");
        //Load data center from system property
        String cluster = System.getProperty(ConfigConsts.APOLLO_CLUSTER_KEY);
        //Use data center as cluster
        if (Strings.isNullOrEmpty(cluster)) {
            cluster = Foundation.server().getDataCenter();
        }
        if (Strings.isNullOrEmpty(cluster)) {
            cluster = Foundation.app().getProperty(ConfigConsts.APOLLO_CLUSTER_KEY, "");
        }
        //Use default cluster
        if (Strings.isNullOrEmpty(cluster)) {
            cluster = ConfigConsts.CLUSTER_NAME_DEFAULT;
        }
        ReflectionUtils.makeAccessible(clusterField);
        if (!Utils.isBlank(cluster)) {
            cluster = cluster.trim();
        }
        ReflectionUtils.setField(clusterField, configUtil, cluster);
    }

    @Override
    public <T> T getInstance(Class<T> clazz, String name) {
        return null;
    }
}
