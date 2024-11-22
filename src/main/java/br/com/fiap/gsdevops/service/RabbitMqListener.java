package br.com.fiap.gsdevops.service;

import br.com.fiap.gsdevops.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RabbitMqListener {

    private final EmailService emailService;
    private final ObjectMapper objectMapper;

    public RabbitMqListener(EmailService emailService, ObjectMapper objectMapper) {
        this.emailService = emailService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "task_queue")
    public void listen(String message) {
        try {
            Map<String, String> emailData = objectMapper.readValue(message, Map.class);

            String para = emailData.get("para");
            String assunto = emailData.get("assunto");
            String texto = emailData.get("texto");

            emailService.enviarEmail(para, assunto, texto);

        } catch (Exception e) {
            System.err.println("Erro ao processar mensagem: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
