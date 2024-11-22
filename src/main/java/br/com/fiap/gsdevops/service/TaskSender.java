package br.com.fiap.gsdevops.service;

import br.com.fiap.gsdevops.config.RabbitConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TaskSender {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public TaskSender(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendEmailTask(String para, String assunto, String texto) {
        try {

            Map<String, String> emailData = new HashMap<>();
            emailData.put("para", para);
            emailData.put("assunto", assunto);
            emailData.put("texto", texto);

            String mensagemJson = objectMapper.writeValueAsString(emailData);
            rabbitTemplate.convertAndSend(RabbitConfig.TASK_QUEUE, mensagemJson);

        } catch (JsonProcessingException e) {
            System.err.println("Erro ao serializar mensagem: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
