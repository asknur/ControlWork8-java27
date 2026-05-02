package askar.controlworkjava27.controller;

import askar.controlworkjava27.dto.FileDto;
import askar.controlworkjava27.dto.UserDto;
import askar.controlworkjava27.service.FileService;
import askar.controlworkjava27.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;
    private final FileService fileService;

    @GetMapping
    public String profile(Model model, Principal principal,
                          @RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "5") int size) {
        log.info("Profile page for user: {}", principal.getName());

        UserDto user = userService.getByEmail(principal.getName());
        Page<FileDto> filePage = fileService.getUserFiles(principal.getName(), page, size);

        model.addAttribute("user", user);
        model.addAttribute("files", filePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", filePage.getTotalPages());
        return "profile/profile";
    }
}
