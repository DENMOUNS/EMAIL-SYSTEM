package cm.landry.email_system.service;

import cm.landry.email_system.entity.Conversation;
import cm.landry.email_system.repository.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversationService {

    @Autowired
    private ConversationRepository conversationRepository;

    public Conversation createConversation(Conversation conversation) {
        return conversationRepository.save(conversation);
    }

    public Conversation findById(Long conversationId) {
        return conversationRepository.findById(conversationId).orElse(null);
    }
}
