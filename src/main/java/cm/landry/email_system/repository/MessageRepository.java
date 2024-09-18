package cm.landry.email_system.repository;

import cm.landry.email_system.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
