package br.com.fiap.gsdevops.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitConfig {

    // Nome da fila
    public static final String TASK_QUEUE = "user_registration_queue";
    // Exchange padrão (não configurada)
    public static final String EXCHANGE = null;

    @Bean
    public Queue taskQueue() {
        // Criação da fila persistente
        return new Queue(TASK_QUEUE, true);
    }
}
