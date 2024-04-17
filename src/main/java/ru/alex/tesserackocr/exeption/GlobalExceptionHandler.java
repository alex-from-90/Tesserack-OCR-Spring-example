package ru.alex.tesserackocr.exeption;

import net.sourceforge.tess4j.TesseractException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "upload";
    }

    @ExceptionHandler(TesseractException.class)
    public String handleTesseractException(TesseractException e, Model model) {
        model.addAttribute("error", "Ошибка при распознавании файла: " + e.getMessage());
        return "upload";
    }

    @ExceptionHandler(IOException.class)
    public String handleIOException(IOException e, Model model) {
        model.addAttribute("error", "Ошибка при чтении файла: " + e.getMessage());
        return "upload";
    }
}
