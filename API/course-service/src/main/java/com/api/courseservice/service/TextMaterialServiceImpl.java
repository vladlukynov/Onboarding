package com.api.courseservice.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.courseservice.model.TextMaterials;
import com.api.courseservice.model.TextMaterialsResults;
import com.api.courseservice.repository.TextMaterialRepository;
import com.api.courseservice.repository.TextMaterialsResultsRepository;

@Service
public class TextMaterialServiceImpl implements TextMaterialsService {
    @Autowired
    private TextMaterialRepository textMaterialRepository;

    @Autowired
    private TextMaterialsResultsRepository textMaterialsResultsRepository;

    @Override
    public void setResultsForTextMaterial(Long textMaterialsId, Long userId) {
        TextMaterialsResults textMaterialsResults = new TextMaterialsResults();
        textMaterialsResults.setUserId(userId);
        textMaterialsResults.setTextMaterials(textMaterialRepository.findById(textMaterialsId).get());
        textMaterialsResultsRepository.save(textMaterialsResults);
    }

    @Override
    public Map<String, String> getTextMaterials(Long textMaterialsId) {
        Optional<TextMaterials> textMaterialsOptional = textMaterialRepository.findById(textMaterialsId);
        if (textMaterialsOptional.isPresent()) {
            Map<String, String> map = new HashMap<>();
            map.put("title", textMaterialsOptional.get().getTitle());
            map.put("content", textMaterialsOptional.get().getText());
            map.put("id", String.valueOf(textMaterialsOptional.get().getId()));
            return map;
        }
        throw new EntityNotFoundException();
    }
}
