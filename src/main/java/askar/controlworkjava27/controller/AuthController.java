package askar.controlworkjava27.controller;

import askar.controlworkjava27.dto.UserDto;
import askar.controlworkjava27.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("userDto") UserDto userDto,
                           BindingResult errors,
                           Model model,
                           HttpServletRequest request) {
        if (errors.hasErrors()) {
            model.addAttribute("errors", errors);
            return "auth/register";
        }

        try {
            userService.register(userDto);
            request.login(userDto.getEmail(), userDto.getPassword());
        } catch (Exception e) {
            log.error("Registration failed: {}", e.getMessage());
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }

        return "redirect:/profile";
    }
}
