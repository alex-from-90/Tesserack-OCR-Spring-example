package ru.alex.tesserackocr.service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.alex.tesserackocr.config.TesseractConfig;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class OCRServiceImpl implements OCRService {

    // Список допустимых расширений файлов для распознавания
    private final List<String> allowedExtensions = Arrays.asList(".jpg", ".png", ".pdf");

    // Конфигурация Tesseract
    private final TesseractConfig tesseractConfig = new TesseractConfig();

    // Метод для распознавания изображения из MultipartFile
    @Override
    public String recognizeImage(MultipartFile file) throws IOException, TesseractException {
        // Получение оригинального имени файла и его расширения
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;

        // Находим индекс последней точки в имени файла
        int dotIndex = originalFilename.lastIndexOf(".");

        // Проверка, что точка найдена и расширение не пустое
        if (dotIndex == -1 || dotIndex == originalFilename.length() - 1) {
            throw new IllegalArgumentException("Невозможно определить расширение файла");
        }

        String extension = originalFilename.substring(dotIndex).toLowerCase(); // Приводим к нижнему регистру

        // Проверка допустимости расширения файла
        if (!allowedExtensions.contains(extension)) {
            throw new IllegalArgumentException("Недопустимый тип файла. Разрешены только jpg, png, pdf");
        }

        // Создание временного файла и сохранение в него данных из MultipartFile
        File tempFile = File.createTempFile("temp", extension);
        file.transferTo(tempFile);

        // Получение экземпляра Tesseract с настройками из TesseractConfig
        Tesseract tesseract = tesseractConfig.getTesseract();

        // Распознавание текста в временном файле с помощью Tesseract
        return tesseract.doOCR(tempFile);
    }

}
