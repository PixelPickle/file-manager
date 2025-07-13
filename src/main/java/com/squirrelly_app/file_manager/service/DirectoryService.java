package com.squirrelly_app.file_manager.service;

import com.squirrelly_app.file_manager.Configuration;
import com.squirrelly_app.file_manager.exception.DirectoryReadException;
import com.squirrelly_app.file_manager.model.Directory;
import com.squirrelly_app.file_manager.util.LoggerUtil;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class DirectoryService {

    private static final Logger logger = LoggerFactory.getLogger(DirectoryService.class);

    private final Configuration configuration;

    public DirectoryService(Configuration configuration) {
        this.configuration = configuration;
    }

    public ResponseEntity<Directory> getDirectory(String prefixedPath) {

        String sourceId = "getDirectory";
        String requestId = Long.toString(new Date().getTime());

        LoggerUtil.invoked(logger, requestId, sourceId, prefixedPath);

        String repositoryPath = configuration.getMountPath();

        if (StringUtils.isEmpty(repositoryPath)) {
            LoggerUtil.failure(logger, requestId, sourceId, "No repository path specified");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String[] directoriesArray = prefixedPath.split("/");

        List<String> directoriesList = Arrays.asList(directoriesArray);

        String directoryPath = "/" + String.join("/", directoriesList.subList(2, directoriesList.size()));

        try {

            if ("/".equals(directoryPath)) {
                throw new DirectoryReadException("No directory specified");
            }

            Directory directory = new Directory(repositoryPath, directoryPath);

            return new ResponseEntity<>(directory, HttpStatus.OK);

        } catch (Exception exception) {

            LoggerUtil.failure(logger, requestId, sourceId, exception.getMessage());

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

}
