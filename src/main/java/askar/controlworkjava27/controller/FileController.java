package askar.controlworkjava27.controller;

import org.springframework.http.ContentDisposition;
import java.nio.charset.StandardCharsets;
import askar.controlworkjava27.dto.FileDto;
import askar.controlworkjava27.model.File;
import askar.controlworkjava27.repository.FileRepository;
import askar.controlworkjava27.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;
    private final FileRepository fileRepository;


    @GetMapping
    public String listPublicFiles(Model model,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "5") int size) {
        Page<FileDto> filePage = fileService.getPublicFiles(page, size);
        model.addAttribute("files", filePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", filePage.getTotalPages());
        return "files/list";
    }

    @GetMapping("/upload")
    public String uploadPage() {
        return "files/upload";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file,
                         @RequestParam(defaultValue = "true") boolean isPublic,
                         Principal principal) {
        log.info("Upload request from: {}", principal.getName());
        fileService.upload(file, isPublic, principal.getName());
        return "redirect:/profile";
    }

    @GetMapping("/download/public/{id}")
    public ResponseEntity<Resource> downloadPublic(@PathVariable Long id) {
        File entry = fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));

        Resource resource = fileService.downloadPublic(id);

        ContentDisposition contentDisposition = ContentDisposition.attachment()
                .filename(entry.getName(), StandardCharsets.UTF_8)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .body(resource);
    }

    @GetMapping("/download/private/{key}")
    public ResponseEntity<Resource> downloadPrivate(@PathVariable String key) {
        String filename = fileService.getOriginalNameByKey(key);
        Resource resource = fileService.downloadPrivate(key);

        ContentDisposition contentDisposition = ContentDisposition.attachment()
                .filename(filename, StandardCharsets.UTF_8)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .body(resource);
    }
}
