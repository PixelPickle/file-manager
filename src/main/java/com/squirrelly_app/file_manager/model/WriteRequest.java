package com.squirrelly_app.file_manager.model;

import jakarta.xml.bind.DatatypeConverter;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class WriteRequest {

    private String directory;
    private String data;
    private String extension;

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public List<String> validate() {

        ArrayList<String> errors = new ArrayList<>();

        if (directory == null || directory.isEmpty()) {
            errors.add("Directory is required");
        }

        if (data == null || data.isEmpty()) {
            errors.add("Data is required");
        }

        if (extension == null || extension.isEmpty()) {
            errors.add("Extension is required");
        }

        if (directory != null && !directory.isEmpty() && (directory.startsWith("/") || directory.startsWith("\\"))) {
            errors.add("Directory starts with path separator");
        }

        if (directory != null && !directory.isEmpty() && (directory.endsWith("/") || directory.endsWith("\\"))) {
            errors.add("Directory ends with path separator");
        }

        if (data != null && data.indexOf(',') == -1) {
            errors.add("Data is not valid");
        }

        if (extension != null && extension.charAt(0) == '.') {
            errors.add("Extension begins with a period");
        }

        return errors;

    }

    public byte[] getBytes() {

        int commaIndex = data.indexOf(',');

        int byteDataStartIndex = commaIndex + 1;

        String rawData = data.substring(byteDataStartIndex);

        return DatatypeConverter.parseBase64Binary(rawData);

    }

    @Override
    public String toString() {
        return "WriteRequest{" +
                "directory='" + directory + '\'' +
                ", data='" + (data == null ? null : data.length()) + "' chars" +
                ", extension='" + extension + '\'' +
                '}';
    }
}
