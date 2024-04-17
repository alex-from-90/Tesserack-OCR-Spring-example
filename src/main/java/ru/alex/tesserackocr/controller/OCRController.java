package ru.alex.tesserackocr.controller;

import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.alex.tesserackocr.service.OCRService;

import java.io.IOException;

@Controller
public class OCRController {

    private final OCRService ocrService;

    // Конструктор контроллера, принимает сервис OCRService
    public OCRController(OCRService ocrService) {
        this.ocrService = ocrService;
    }

    // Обработчик GET-запроса для отображения формы загрузки файла
    @GetMapping("/")
    public String index() {
        return "upload";
    }

    // Обработчик POST-запроса для распознавания изображения
    @PostMapping("/recognize")
    public String recognizeImage(@RequestParam("file") MultipartFile file, Model model) {
        // Проверка, был ли загружен файл
        if (file.isEmpty()) {
            model.addAttribute("error", "Файл не выбран");
            return "upload";
        }

        try {
            // Вызов сервисного метода для распознавания изображения
            String ocrResult = ocrService.recognizeImage(file);

            // Добавление распознанного текста в модель для отображения на странице
            model.addAttribute("ocrResponse", ocrResult);

            // Добавление распознанного текста в модель для отображения в формате hOCR
            model.addAttribute("hocrResponse", ocrResult);
        } catch (IOException | TesseractException e) {
            // Обработка исключений при возникновении ошибок
            model.addAttribute("error", "Ошибка при распознавании файла: " + e.getMessage());
            return "upload";
        }

        // Переход на страницу с результатом
        return "result";
    }
}
