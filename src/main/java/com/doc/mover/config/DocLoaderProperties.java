package com.doc.mover.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "loader")
public class DocLoaderProperties {
    private String url;
    private String authorizationHeaderValue;
    private String inputFile;
    private int batchSize = 50;
    private int connectTimeoutMs = 5000;
    private int readTimeoutMs = 30000;

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getAuthorizationHeaderValue() { return authorizationHeaderValue; }
    public void setAuthorizationHeaderValue(String authorizationHeaderValue) { this.authorizationHeaderValue = authorizationHeaderValue; }

    public String getInputFile() { return inputFile; }
    public void setInputFile(String inputFile) { this.inputFile = inputFile; }

    public int getBatchSize() { return batchSize; }
    public void setBatchSize(int batchSize) { this.batchSize = batchSize; }

    public int getConnectTimeoutMs() { return connectTimeoutMs; }
    public void setConnectTimeoutMs(int connectTimeoutMs) { this.connectTimeoutMs = connectTimeoutMs; }

    public int getReadTimeoutMs() { return readTimeoutMs; }
    public void setReadTimeoutMs(int readTimeoutMs) { this.readTimeoutMs = readTimeoutMs; }
}
