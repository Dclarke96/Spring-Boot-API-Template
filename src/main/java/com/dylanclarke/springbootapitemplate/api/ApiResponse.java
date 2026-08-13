package com.dylanclarke.springbootapitemplate.api;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Standardized API response wrapper for successful and unsuccessful responses."
)
public class ApiResponse<T> {

    @Schema(
            description = "Indicates whether the request completed successfully.",
            example = "true"
    )
    private boolean success;

    @Schema(
            description = "Response payload. May be null when the response does not contain data.",
            nullable = true
    )
    private T data;

    @Schema(
            description = "Human-readable message describing the result of the request.",
            example = "Vehicle retrieved successfully"
    )
    private String message;

    @Schema(
            description = "Timestamp when the API response was generated."
    )
    private LocalDateTime timestamp;

    private ApiResponse(
            boolean success,
            T data,
            String message
    ) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> success(
            T data,
            String message
    ) {
        return new ApiResponse<>(
                true,
                data,
                message
        );
    }

    public static <T> ApiResponse<T> failure(
            String message
    ) {
        return new ApiResponse<>(
                false,
                null,
                message
        );
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
