package com.example.poc_gemini.dto;

public class GeminiTranslationRequest {
    private String originalHtml;

    public GeminiTranslationRequest(String originalHtml) {
        this.originalHtml = originalHtml;
    }
    
    public String getOriginalHtml() {
        return originalHtml;
    }

    public void setOriginalHtml(String originalHtml) {
        this.originalHtml = originalHtml;
    }
}
