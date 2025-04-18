package com.example.poc_gemini.repository;

import com.example.poc_gemini.entity.TranslationRecord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranslationRecordRepository extends JpaRepository<TranslationRecord, Long> {
    // สามารถเพิ่ม method อื่นๆ ที่ต้องการได้ที่นี่
}

