package com.api.courseservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.courseservice.model.TextMaterials;

@Repository
public interface TextMaterialRepository extends JpaRepository<TextMaterials, Long> {
}
