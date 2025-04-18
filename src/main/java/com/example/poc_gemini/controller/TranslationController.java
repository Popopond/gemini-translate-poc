package com.example.poc_gemini.controller;

import com.example.poc_gemini.dto.GeminiTranslationRequest;
import com.example.poc_gemini.dto.GeminiTranslationResponse;
import com.example.poc_gemini.entity.TranslationRecord;
import com.example.poc_gemini.repository.TranslationRecordRepository;
import com.example.poc_gemini.service.TranslationService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class TranslationController {

    private final TranslationService translationService;
    private final TranslationRecordRepository repository;

    public TranslationController(TranslationService translationService,
                                  TranslationRecordRepository repository) {
        this.translationService = translationService;
        this.repository = repository;
    }

    /**
     * ✅ แปลข้อความและบันทึกลงฐานข้อมูลทันที
     */
    @PostMapping("/translate")
    public GeminiTranslationResponse translate(@RequestBody GeminiTranslationRequest request) {
        return translationService.translate(request);
    }

    /**
     * ✅ ดึงประวัติการแปลทั้งหมด
     */
    @GetMapping("/history")
    public List<TranslationRecord> getAll() {
        return repository.findAll();
    }
}
