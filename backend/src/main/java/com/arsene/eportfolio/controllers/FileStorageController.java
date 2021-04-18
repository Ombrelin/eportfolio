package com.arsene.eportfolio.controllers;

import com.arsene.eportfolio.model.entities.DBFile;
import com.arsene.eportfolio.services.FileStorageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/files")
public class FileStorageController {

    private final FileStorageService fileStorageService;

    public FileStorageController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping
    public String upload(@RequestParam("file") MultipartFile file) {
        DBFile dbFile = fileStorageService.store(file);

        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files")
                .path("/" + dbFile.getId())
                .toUriString();
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<Resource> retrieve(@PathVariable("fileId") String fileId) {
        DBFile dbFile = fileStorageService.retrieve(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }

}
