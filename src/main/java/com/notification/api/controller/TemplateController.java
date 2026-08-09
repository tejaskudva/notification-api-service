package com.notification.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.notification.api.models.request.CreateTemplateRequest;
import com.notification.api.models.request.TemplateFilterRequest;
import com.notification.api.models.request.UpdateTemplateRequest;
import com.notification.api.models.response.TemplateResponse;
import com.notification.api.services.interfaces.TemplateService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/template")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;

    @PostMapping
    public ResponseEntity<TemplateResponse> createTemplate(
            @Valid @RequestBody CreateTemplateRequest templateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(templateService.createTemplate(templateRequest));
    }

    @GetMapping
    public ResponseEntity<Object> filterTemplate(@ModelAttribute TemplateFilterRequest request) {
        return ResponseEntity.ok(templateService.filterTemplate(request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TemplateResponse> updateTemplate(@PathVariable String id,
            @Valid @RequestBody UpdateTemplateRequest templateRequest) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(templateService.updateTemplate(id, templateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTemplate(@PathVariable String id) {
        templateService.deleteTemplate(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Template Deleted Successfully");
    }
}