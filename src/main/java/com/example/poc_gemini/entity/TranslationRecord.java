package com.example.poc_gemini.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "translation_record")
public class TranslationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String originalHtml;

    @Column(columnDefinition = "TEXT")
    private String translatedHtml;

    // Optional: บันทึกเวลา
    @Column(name = "created_at", updatable = false)
    private java.time.LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = java.time.LocalDateTime.now();
    }

    // --- Getters & Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalHtml() {
        return originalHtml;
    }

    public void setOriginalHtml(String originalHtml) {
        this.originalHtml = originalHtml;
    }

    public String getTranslatedHtml() {
        return translatedHtml;
    }

    public void setTranslatedHtml(String translatedHtml) {
        this.translatedHtml = translatedHtml;
    }

    public java.time.LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.time.LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
