package com.squirrelly_app.file_manager.model;

@SuppressWarnings("unused")
public class WriteResponse {

    private String filename;
    private String directory;

    public WriteResponse(String filename, String directory) {
        this.filename = filename;
        this.directory = directory;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    @Override
    public String toString() {
        return "WriteResponse{" +
                "filename='" + filename + '\'' +
                ", directory='" + directory + '\'' +
                '}';
    }
}
