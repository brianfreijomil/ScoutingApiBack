package com.microservice.user.config.kafka_config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    //replicas -> copias en otro cluster/topi, sirve de respaldo

    @Bean
    public NewTopic generateTopic() {

        Map<String, String> configurations = new HashMap<>();
        configurations.put(TopicConfig.CLEANUP_POLICY_CONFIG, TopicConfig.CLEANUP_POLICY_DELETE); //delete -> borra mensaje, compact mantiene el mensaje mas actual
        configurations.put(TopicConfig.RETENTION_MS_CONFIG, "86400000");//retengo el msj en el server de kafka durante un dia, luego se borrara o compactara (default -1 (no se borra nunca))
        configurations.put(TopicConfig.SEGMENT_BYTES_CONFIG, "1073741824"); //tamaño maximo del segmento ( 1073741824 -> 1GB (default))
        configurations.put(TopicConfig.MAX_MESSAGE_BYTES_CONFIG, "1000012"); //cant en bytes que voy a soportar dentro de mis mensajes (by default 1 MB)
        return TopicBuilder
                .name("scouter-management-topic")
                .partitions(2)
                .replicas(1)
                .configs(configurations)
                .build();
    }
}
