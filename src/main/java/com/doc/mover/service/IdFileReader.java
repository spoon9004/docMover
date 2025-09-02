package com.doc.mover.service;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class IdFileReader {

    public List<String> readIds(String filePath) throws IOException {
        return Files.readAllLines(Path.of(filePath))
                .stream()
                .map(String::trim)
                .filter(line -> !line.isEmpty() && !line.startsWith("#"))
                .toList();
    }
}