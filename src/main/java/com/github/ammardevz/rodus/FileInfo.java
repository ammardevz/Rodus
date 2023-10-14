package com.github.ammardevz.rodus;

import lombok.Data;

import java.nio.file.Path;

@Data
public class FileInfo {
    private int index;
    private String name;
    private Path path;

    // Constructors, getters, and setters

    // Constructor
    public FileInfo(int index, String name, Path path) {
        this.index = index;
        this.name = name;
        this.path = path;
    }
}