package com.pills.pillstracker.metrics;

import com.newrelic.telemetry.Attributes;
import com.newrelic.telemetry.micrometer.NewRelicRegistry;
import com.newrelic.telemetry.micrometer.NewRelicRegistryConfig;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.util.NamedThreadFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.CompositeMeterRegistryAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.export.simple.SimpleMetricsExportAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;


@Configuration
@AutoConfigureBefore({
    CompositeMeterRegistryAutoConfiguration.class,
    SimpleMetricsExportAutoConfiguration.class
})
@AutoConfigureAfter(MetricsAutoConfiguration.class)
@ConditionalOnClass(NewRelicRegistry.class)
public class NewRelicMetricsExportAutoConfiguration {

    @Value("${newrelic.app.name}")
    private String appName;

    @Value("${newrelic.api.key}")
    private String key;

    @Bean
    public NewRelicRegistryConfig newRelicConfig() {

        return new NewRelicRegistryConfig() {
            @Override
            public String get(String key) {

                return null;
            }

            @Override
            public String apiKey() {

                return key;
            }

            @Override
            public Duration step() {

                return Duration.ofSeconds(5);
            }

            @Override
            public String serviceName() {

                return appName;
            }

        };
    }

    @Bean
    public NewRelicRegistry newRelicMeterRegistry(NewRelicRegistryConfig config)
        throws UnknownHostException {

        NewRelicRegistry newRelicRegistry =
            NewRelicRegistry.builder(config)
                .commonAttributes(
                    new Attributes()
                        .put("host", InetAddress.getLocalHost().getHostName()))
                .build();
        newRelicRegistry.config().meterFilter(MeterFilter.ignoreTags("plz_ignore_me"));
        newRelicRegistry.config().meterFilter(MeterFilter.denyNameStartsWith("jvm.threads"));
        newRelicRegistry.start(new NamedThreadFactory("newrelic.micrometer.registry"));
        return newRelicRegistry;
    }

}
