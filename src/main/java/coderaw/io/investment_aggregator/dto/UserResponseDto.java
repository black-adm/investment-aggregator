package coderaw.io.investment_aggregator.dto;

import java.time.Instant;

public record UserResponseDto(String userId, String username, String email, Instant createdAt) {
}
