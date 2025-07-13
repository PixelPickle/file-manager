package com.squirrelly_app.file_manager.model;

import com.squirrelly_app.file_manager.exception.DirectoryReadException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Directory {

    private String path;
    private List<Directory> subDirectories;
    private List<String> files;

    public Directory(String repositoryPath, String directoryPath) throws DirectoryReadException {

        this.path = directoryPath;

        subDirectories = new ArrayList<>();
        files = new ArrayList<>();

        File[] directoryContents = getDirectoryContents(repositoryPath);

        Stream.of(directoryContents).forEach(file -> {

            if (file.isDirectory()) {

                String subDirectoryPath = path + "/" + file.getName();

                Directory subDirectory = new Directory(repositoryPath, subDirectoryPath);

                subDirectories.add( subDirectory );

            } else {

                files.add(file.getName());

            }

        });

    }

    private File[] getDirectoryContents(String repositoryPath) throws DirectoryReadException {

        String fullPath = repositoryPath + path;

        File directoryFile = new File(fullPath);

        if (!directoryFile.exists()) {
            throw new DirectoryReadException("Path does not exist: " + path);
        }

        if (!directoryFile.isDirectory()) {
            throw new DirectoryReadException("Path is not a directory: " + path);
        }

        File[] directoryContents = directoryFile.listFiles();

        if (directoryContents == null) {
            throw new DirectoryReadException("Failed to Iterate Files in directory: " + path);
        }

        return directoryContents;

    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<Directory> getSubDirectories() {
        return subDirectories;
    }

    public void setSubDirectories(List<Directory> subDirectories) {
        this.subDirectories = subDirectories;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "Directory{" +
                "path='" + path + '\'' +
                ", subDirectories=" + subDirectories +
                ", files=" + files +
                '}';
    }
}
