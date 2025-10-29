package com.example.instructions.model;

public record CanonicalTrade(
    String platform_id,
    PlatformTrade trade
) {}
