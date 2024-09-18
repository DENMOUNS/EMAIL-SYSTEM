package cm.landry.email_system.entity.support;


import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.data.auditing.AuditingHandler;

public class AuditingEntityListener {

    private AuditingHandler handler;

    @PrePersist
    public void touchForCreate(Object target) {
        if (handler != null) {
            handler.markCreated(target);
        }
    }

    @PreUpdate
    public void touchForUpdate(Object target) {
        if (handler != null) {
            handler.markModified(target);
        }
    }

    // Autres méthodes et logique interne...
}