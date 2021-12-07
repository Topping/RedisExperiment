package com.example.demo.configuration;

import com.example.demo.model.Product;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import io.lettuce.core.resource.ClientResources;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.convert.KeyspaceConfiguration;

import java.time.Duration;
import java.util.List;

@Configuration
public class RedisConfig {
    @Value("${spring.application.name}")
    String clientName;
    @Value("${demo.redis.clusternodes}")
    List<String> clusterNodes;
    @Value("${demo.redis.password}")
    String password;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        var serverConfig = new RedisClusterConfiguration(clusterNodes);
        serverConfig.setPassword(RedisPassword.of(password));
        var clientOptions = ClusterClientOptions.builder()
                .autoReconnect(true)
                .topologyRefreshOptions(refreshOptions())
                .build();
        var clientConfig = LettuceClientConfiguration.builder()
                .clientName(clientName)
                .commandTimeout(Duration.ofSeconds(15))
                .clientOptions(clientOptions)
                .build();
        return new LettuceConnectionFactory(serverConfig, clientConfig);
    }

    private ClusterTopologyRefreshOptions refreshOptions() {
        return ClusterTopologyRefreshOptions.builder()
                .enableAllAdaptiveRefreshTriggers()
                .build();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}
