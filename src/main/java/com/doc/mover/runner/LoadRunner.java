package com.doc.mover.runner;


import com.doc.mover.config.DocLoaderProperties;
import com.doc.mover.service.DocSender;
import com.doc.mover.service.IdFileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class LoadRunner implements CommandLineRunner {

    private final DocLoaderProperties props;
    private final IdFileReader idFileReader;
    private final DocSender docSender;

    public LoadRunner(DocLoaderProperties props, IdFileReader idFileReader, DocSender docSender) {
        this.props = props;
        this.idFileReader = idFileReader;
        this.docSender = docSender;
    }

    @Override
    public void run(String... args) throws Exception {
        List<String> allIds = loadIds();
        if (allIds.isEmpty()) {
            System.out.println("No IDs to send. Exiting.");
            return;
        }
        int batchSize = Math.max(1, props.getBatchSize());
        for (int i = 0; i < allIds.size(); i += batchSize) {
            List<String> batch = new ArrayList<>(allIds.subList(i, Math.min(i + batchSize, allIds.size())));
            docSender.sendBatch(batch);
        }
        System.out.println("Done. Total IDs: " + allIds.size());
    }

    private List<String> loadIds() throws IOException {
        String path = props.getInputFile();
        if (path == null || path.isBlank()) {
            throw new IllegalArgumentException("loader.inputFile must be set");
        }
        return idFileReader.readIds(path);
    }
}