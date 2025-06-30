package com.weiz.spb.services.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class Response<T> {

    private final boolean success;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final List<String> errors;

    public static <T> Response<T> success(T data, String message) {
        return new Response<>(true, message, data, null);
    }

    public static <T> Response<T> failure(String message, String error) {
        return new Response<>(false, message, null, List.of(error));
    }

    public static <T> Response<T> failure(String message, List<String> errors) {
        return new Response<>(false, message, null, errors);
    }
}
