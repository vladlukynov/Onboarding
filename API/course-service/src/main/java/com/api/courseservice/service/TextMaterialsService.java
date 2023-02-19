package com.api.courseservice.service;

import java.util.Map;

public interface TextMaterialsService {
    void setResultsForTextMaterial(Long textMaterialsId, Long userId);
    Map<String, String> getTextMaterials(Long textMaterialsId);
}
