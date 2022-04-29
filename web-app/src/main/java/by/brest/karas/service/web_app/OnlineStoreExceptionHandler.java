package by.brest.karas.service.web_app;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class OnlineStoreExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public String TheSameNameHandler(Model model, IllegalArgumentException exception){
        model.addAttribute("error", exception.getMessage());
        return "error";
    }
}
