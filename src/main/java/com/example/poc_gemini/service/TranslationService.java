package com.example.poc_gemini.service;

import com.example.poc_gemini.dto.GeminiTranslationRequest;
import com.example.poc_gemini.dto.GeminiTranslationResponse;
import com.example.poc_gemini.entity.TranslationRecord;
import com.example.poc_gemini.repository.TranslationRecordRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class TranslationService {

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    private final RestTemplate restTemplate;
    private final TranslationRecordRepository repository;

    public TranslationService(RestTemplate restTemplate, TranslationRecordRepository repository) {
        this.restTemplate = restTemplate;
        this.repository = repository;
    }

    public GeminiTranslationResponse translate(GeminiTranslationRequest request) {
        // 🔒 Validate input
        if (request == null || request.getOriginalHtml() == null || request.getOriginalHtml().isBlank()) {
            throw new IllegalArgumentException("Input text cannot be null or blank.");
        }

        // 🧠 Prepare request body for Gemini API
        Map<String, Object> body = Map.of(
            "contents", List.of(
                Map.of("parts", List.of(
                    Map.of("text", "Translate the following Thai text to English, MUST contain only translated and format: " + request.getOriginalHtml())
                ))
            )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + geminiApiKey;

        try {
            @SuppressWarnings("rawtypes")
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new RuntimeException("Failed to get valid response from Gemini API.");
            }

            @SuppressWarnings("unchecked")
            Map<String, Object> responseBody = response.getBody();
            @SuppressWarnings({ "unchecked", "null" })
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) responseBody.get("candidates");

            if (candidates == null || candidates.isEmpty()) {
                throw new RuntimeException("No translation candidates returned.");
            }

            @SuppressWarnings("unchecked")
            Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");

            if (parts == null || parts.isEmpty()) {
                throw new RuntimeException("No translated text found in response.");
            }

            String translatedHtml = (String) parts.get(0).get("text");

            // 💾 Save to database
            TranslationRecord record = new TranslationRecord();
            record.setOriginalHtml(request.getOriginalHtml());
            record.setTranslatedHtml(translatedHtml);
            repository.save(record);
            System.out.println("✅ Saved to DB: " + translatedHtml);

            // ✅ Return result to frontend
            return new GeminiTranslationResponse(translatedHtml);

        } catch (Exception e) {
            // 🐞 Optionally: log or wrap exception
            throw new RuntimeException("Translation error: " + e.getMessage(), e);
        }
    }
}
