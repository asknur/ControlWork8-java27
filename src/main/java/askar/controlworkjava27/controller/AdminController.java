package askar.controlworkjava27.controller;

import askar.controlworkjava27.repository.FileRepository;
import askar.controlworkjava27.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserRepository userRepository;
    private final FileRepository fileRepository;

    @GetMapping
    public String adminPanelView(Model model) {
        log.info("Admin panel accessed");
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("files", fileRepository.findAll());
        return "admin";
    }

    @PostMapping("/files/delete/{id}")
    public String deleteFile(@PathVariable Long id) {
        log.info("Admin deleting file id: {}", id);
        fileRepository.deleteById(id);
        return "redirect:/admin";
    }
}
