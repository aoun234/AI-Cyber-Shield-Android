package com.example.aicybershield.models;

import java.util.List;

public class ScanResponse {

    private String url;
    private int score;
    private List<Vulnerability> vulnerabilities;

    public String getUrl() {
        return url;
    }

    public int getScore() {
        return score;
    }

    public List<Vulnerability> getVulnerabilities() {
        return vulnerabilities;
    }
}