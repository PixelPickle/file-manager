package com.squirrelly_app.file_manager.controller;

import com.squirrelly_app.file_manager.model.Directory;
import com.squirrelly_app.file_manager.service.DirectoryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/directory")
public class DirectoryController {

    private final DirectoryService directoryService;

    public DirectoryController(DirectoryService directoryService) {
        this.directoryService = directoryService;
    }

    @GetMapping( value = "/**", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Directory> getDirectory(HttpServletRequest request) {
        return directoryService.getDirectory(request.getServletPath());
    }

}
