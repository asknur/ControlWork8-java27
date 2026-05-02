package askar.controlworkjava27.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerService {

    @ExceptionHandler(Exception.class)
    public String handleException(HttpServletRequest request, Model model, Exception e) {
        model.addAttribute("status", 500);
        model.addAttribute("reason", e.getMessage());
        model.addAttribute("details", request);
        return "errors/error";
    }
}
