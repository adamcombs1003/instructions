package com.example.instructions.model;

public record PlatformTrade(
    String account,
    String security,
    String type,
    Integer amount,
    String timestamp
) {}
