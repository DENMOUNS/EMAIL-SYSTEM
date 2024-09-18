package cm.landry.email_system.service;

import cm.landry.email_system.entity.File;
import cm.landry.email_system.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public File uploadFile(File file) {
        return fileRepository.save(file);
    }
}
