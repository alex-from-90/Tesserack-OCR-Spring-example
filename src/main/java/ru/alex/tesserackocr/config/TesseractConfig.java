package ru.alex.tesserackocr.config;

import net.sourceforge.tess4j.Tesseract;

public class TesseractConfig {

    public TesseractConfig() {
    }

    // Метод для получения экземпляра Tesseract с заданными настройками
    public Tesseract getTesseract() {
        Tesseract tesseract = new Tesseract();

        // Установка пути к каталогу с данными для Tesseract
        String tessDataPath = "src/main/resources/tessdata";
        tesseract.setDatapath(tessDataPath);

        // Установка разрешения изображения (DPI)
        // Важно: Установка DPI может влиять на качество распознавания
        tesseract.setVariable("user_defined_dpi", "600");

        // Установка порога бинаризации (чем выше значение, тем меньше черных точек)
        // Важно: Подбор оптимального значения может улучшить распознавание текста
        tesseract.setVariable("tessedit_threshold", "150");

        // Включение вывода отладочной информации о процессе отклонения
        tesseract.setVariable("tessedit_rejection_debug", "true");

        // Установка языка распознавания (русский и английский)
        // Если нужно распознавать текст только на русском или английском,

        tesseract.setLanguage("rus+eng");

        // Установка режима сегментации страницы
        // 2 - режим авто-сегментации (подходит для большинства общих задач распознавания)
        tesseract.setPageSegMode(2);

        return tesseract;
    }
}
