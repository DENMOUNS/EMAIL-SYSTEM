package cm.landry.email_system.repository;

import cm.landry.email_system.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
