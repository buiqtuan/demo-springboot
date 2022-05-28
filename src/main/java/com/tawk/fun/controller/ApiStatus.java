package com.tawk.fun.controller;

public enum ApiStatus {
    SUCCESS("SUCCESS"),FAIL("FAIL");

    private String label;

    ApiStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
