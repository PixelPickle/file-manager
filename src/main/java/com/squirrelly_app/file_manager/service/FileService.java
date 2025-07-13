package com.squirrelly_app.file_manager.service;

import com.squirrelly_app.file_manager.Configuration;
import com.squirrelly_app.file_manager.exception.FileReadException;
import com.squirrelly_app.file_manager.exception.FileWriteException;
import com.squirrelly_app.file_manager.model.WriteRequest;
import com.squirrelly_app.file_manager.model.WriteResponse;
import com.squirrelly_app.file_manager.util.LoggerUtil;
import io.micrometer.common.util.StringUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    private final Configuration configuration;

    private static final String REPOSITORY_PATH_FORMAT = "%s/%s/";

    public FileService(Configuration configuration) {
        this.configuration = configuration;
    }

    public ResponseEntity<WriteResponse> writeFile(WriteRequest writeRequest) {

        String sourceId = "writeFile";
        String requestId = Long.toString(new Date().getTime());

        LoggerUtil.invoked(logger, requestId, sourceId, writeRequest.toString());

        String repositoryPath = configuration.getMountPath();

        if (StringUtils.isEmpty(repositoryPath)) {
            LoggerUtil.failure(logger, requestId, sourceId, "No repository path specified");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        List<String> validationErrors = writeRequest.validate();

        if (!validationErrors.isEmpty()) {
            LoggerUtil.failure(logger, requestId, sourceId, String.join(",", validationErrors));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String directoryPath = writeRequest.getDirectory();

        String filename = getFileName(writeRequest);

        byte[] binaryData = writeRequest.getBytes();

        String destinationPath = String.format(REPOSITORY_PATH_FORMAT, repositoryPath, directoryPath);

        try {

            writeFile(destinationPath, filename, binaryData);

            return new ResponseEntity<>(new WriteResponse(filename, directoryPath), HttpStatus.OK);

        } catch (Exception exception) {

            LoggerUtil.failure(logger, requestId, sourceId, exception.getMessage());

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    private static String getFileName(WriteRequest writeRequest) {

        String timestamp = new Timestamp(System.currentTimeMillis()).toString().replaceAll( "\\D+", "");

        String extension = writeRequest.getExtension();

        return String.format("%s.%s", timestamp, extension);

    }

    private static void writeFile(String destinationPath, String filename, byte[] data) throws FileWriteException {

        String destinationFile = destinationPath + filename;

        //noinspection ResultOfMethodCallIgnored
        new File(destinationPath).mkdirs();

        try (FileOutputStream fileOutputStream = new FileOutputStream(destinationFile)) {
            fileOutputStream.write(data);
        } catch (Exception exception) {
            throw new FileWriteException( String.format("Failed to write file %s with cause %s", destinationFile, exception) );
        }

    }

    public ResponseEntity<InputStreamResource> readFile(String fileName) {

        String sourceId = "readFile";
        String requestId = Long.toString(new Date().getTime());

        LoggerUtil.invoked(logger, requestId, sourceId, fileName);

        String repositoryPath = configuration.getMountPath();

        if (StringUtils.isEmpty(repositoryPath)) {
            LoggerUtil.failure(logger, requestId, sourceId, "No repository path specified");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try (FileInputStream fileInputStream = new FileInputStream(repositoryPath + fileName)) {

            byte[] bytes = IOUtils.toByteArray(fileInputStream);

            if (bytes.length == 0) {
                throw new FileReadException("Failed to read file " + fileName);
            }

            LoggerUtil.success(logger, requestId, sourceId, bytes.length + " bytes read");

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

            InputStreamResource inputStreamResource = new InputStreamResource(byteArrayInputStream);

            return new ResponseEntity<>(inputStreamResource, HttpStatus.OK);

        } catch (Exception exception) {

            LoggerUtil.failure(logger, requestId, sourceId, exception.getMessage());

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

}
