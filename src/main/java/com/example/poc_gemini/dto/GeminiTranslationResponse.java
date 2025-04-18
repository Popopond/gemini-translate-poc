package com.example.poc_gemini.dto;

public class GeminiTranslationResponse {
    private String translatedHtml;

    public GeminiTranslationResponse(String translatedHtml) {
        this.translatedHtml = translatedHtml;
    }

    public String getTranslatedHtml() {
        return translatedHtml;
    }
}
