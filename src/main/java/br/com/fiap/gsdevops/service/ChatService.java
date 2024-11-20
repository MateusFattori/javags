package br.com.fiap.gsdevops.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    final ChatClient chatClient;

    public ChatService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .defaultSystem("""
                        Você é um assistente especializado em sugerir formas de reduzir as emissões de CO₂.
                        Ao receber o valor da emissão, forneça dicas práticas para reduzir o impacto ambiental, como alternativas de combustível, opções de veículos mais eficientes e mudanças no comportamento de direção.
                        """)
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(new InMemoryChatMemory())
                )
                .build();
    }

    public String getEmissionReductionTips(double emissionValue) {
        String userMessage = String.format("A emissão de CO₂ registrada foi de %.2f kg. O que posso fazer para reduzir isso?", emissionValue);

        return chatClient
                .prompt()
                .user(userMessage)
                .call()
                .content();
    }
}

