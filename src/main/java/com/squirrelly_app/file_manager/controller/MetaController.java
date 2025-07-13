package com.squirrelly_app.file_manager.controller;

import com.squirrelly_app.file_manager.service.MetaService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/meta")
public class MetaController {

    private final MetaService metaService;

    public MetaController(MetaService metaService) {
        this.metaService = metaService;
    }

    @GetMapping( value = { "/version", "/version/" }, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getVersion() {
        return metaService.getVersion();
    }

}
