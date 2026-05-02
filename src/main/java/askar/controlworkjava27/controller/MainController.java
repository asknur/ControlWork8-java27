package askar.controlworkjava27.controller;

import askar.controlworkjava27.dto.FileDto;
import askar.controlworkjava27.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {
    private final FileService fileService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "5") int size) {
        Page<FileDto> filePage = fileService.getPublicFiles(page, size);
        model.addAttribute("files", filePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", filePage.getTotalPages());
        return "index";
    }
}
