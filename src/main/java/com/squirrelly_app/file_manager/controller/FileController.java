package com.squirrelly_app.file_manager.controller;

import com.squirrelly_app.file_manager.model.WriteRequest;
import com.squirrelly_app.file_manager.model.WriteResponse;
import com.squirrelly_app.file_manager.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping( value = {"", "/"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WriteResponse> writeFile(@RequestBody WriteRequest writeRequest) {
        return fileService.writeFile(writeRequest);
    }

    @GetMapping( value = "/**", produces = { MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.APPLICATION_PDF_VALUE})
    public ResponseEntity<InputStreamResource> readFile(HttpServletRequest request) {
        return fileService.readFile(request.getServletPath());
    }
}
