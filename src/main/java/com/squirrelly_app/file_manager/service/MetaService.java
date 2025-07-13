package com.squirrelly_app.file_manager.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MetaService {

    public ResponseEntity<String> getVersion() {
        return new ResponseEntity<>("20250711", HttpStatus.OK);
    }

}
