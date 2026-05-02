package askar.controlworkjava27.service;

import askar.controlworkjava27.dto.FileDto;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    void upload(MultipartFile file, boolean isPublic, String email);

    Page<FileDto> getPublicFiles(int page, int size);

    Page<FileDto> getUserFiles(String email, int page, int size);

    Resource downloadPublic(Long id);

    Resource downloadPrivate(String key);

    String getOriginalName(Long id);

    String getOriginalNameByKey(String key);
}
