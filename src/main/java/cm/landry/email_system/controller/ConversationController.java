package cm.landry.email_system.controller;

import cm.landry.email_system.entity.Conversation;
import cm.landry.email_system.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/conversations")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    @PostMapping
    public ResponseEntity<Conversation> createConversation(@RequestBody Conversation conversation) {
        Conversation createdConversation = conversationService.createConversation(conversation);
        return ResponseEntity.ok(createdConversation);
    }

    @GetMapping("/{conversationId}")
    public ResponseEntity<Conversation> getConversation(@PathVariable Long conversationId) {
        Conversation conversation = conversationService.findById(conversationId);
        return conversation != null ? ResponseEntity.ok(conversation) : ResponseEntity.notFound().build();
    }
}
