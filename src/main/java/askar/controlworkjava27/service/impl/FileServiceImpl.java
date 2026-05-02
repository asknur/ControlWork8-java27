package askar.controlworkjava27.service.impl;

import askar.controlworkjava27.dto.FileDto;
import askar.controlworkjava27.model.File;
import askar.controlworkjava27.model.User;
import askar.controlworkjava27.repository.FileRepository;
import askar.controlworkjava27.repository.UserRepository;
import askar.controlworkjava27.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    @Value("${file.upload.path}")
    private String uploadPath;

    @Override
    public void upload(MultipartFile file, boolean isPublic, String email) {
        String originalName = file.getOriginalFilename();

        log.info("Uploading file: {} by user: {}", originalName, email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String storedName = UUID.randomUUID() + "_" + originalName;

        Path path = Paths.get(uploadPath + storedName);

        try {
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());
        } catch (IOException e) {
            log.error("Failed to save file: {}", e.getMessage());
            throw new RuntimeException("Failed to save file", e);
        }

        File entry = new File();
        entry.setName(originalName);
        entry.setStoredName(storedName);
        entry.setSize(file.getSize());
        entry.setIsPublic(isPublic);
        entry.setDownloadCount(0);
        entry.setUploadedAt(LocalDateTime.now());
        entry.setUser(user);

        if (!isPublic) {
            entry.setPrivateKey(UUID.randomUUID().toString());
        }

        fileRepository.save(entry);
        log.info("File saved with original name: {}", originalName);
    }

    @Override
    public Page<FileDto> getPublicFiles(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("uploadedAt")));
        return fileRepository.findByIsPublicTrue(pageable).map(this::toDto);
    }

    @Override
    public Page<FileDto> getUserFiles(String email, int page, int size) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("uploadedAt")));
        return fileRepository.findByUser(user, pageable).map(this::toDto);
    }

    @Override
    public Resource downloadPublic(Long id) {
        File entry = fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));

        if (!entry.getIsPublic()) {
            throw new RuntimeException("File is not public");
        }

        entry.setDownloadCount(entry.getDownloadCount() + 1);
        fileRepository.save(entry);

        log.info("Downloading public file: {}", entry.getName());
        return loadFile(entry.getStoredName());
    }

    @Override
    public Resource downloadPrivate(String key) {
        File entry = fileRepository.findByPrivateKey(key)
                .orElseThrow(() -> new RuntimeException("Invalid key"));

        entry.setDownloadCount(entry.getDownloadCount() + 1);
        entry.setPrivateKey(null);
        fileRepository.save(entry);

        log.info("Downloading private file: {} with key: {}", entry.getName(), key);
        return loadFile(entry.getStoredName());
    }

    @Override
    public String getOriginalName(Long id) {
        return fileRepository.findById(id)
                .map(File::getName)
                .orElseThrow(() -> new RuntimeException("File not found"));
    }

    @Override
    public String getOriginalNameByKey(String key) {
        return fileRepository.findByPrivateKey(key)
                .map(File::getName)
                .orElseThrow(() -> new RuntimeException("File not found"));
    }

    private Resource loadFile(String storedName) {
        try {
            Path path = Paths.get(uploadPath + storedName);
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists()) return resource;
            throw new RuntimeException("File not found on disk ");
        } catch (Exception e) {
            throw new RuntimeException("Could  not load file", e);
        }
    }

    private FileDto toDto(File f) {
        return FileDto.builder()
                .id(f.getId())
                .name(f.getName())
                .size(f.getSize())
                .isPublic(f.getIsPublic())
                .privateKey(f.getPrivateKey())
                .downloadCount(f.getDownloadCount())
                .uploadedAt(f.getUploadedAt())
                .userId(f.getUser().getId())
                .build();
    }

}
