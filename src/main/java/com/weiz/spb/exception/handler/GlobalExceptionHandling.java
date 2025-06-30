package com.weiz.spb.exception.handler;

import com.weiz.spb.common.constants.AppConst;
import com.weiz.spb.exception.*;
import com.weiz.spb.services.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandling {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<Void> handleInternalServerErrorException(Exception ex) {

        return Response.failure(AppConst.INTERNAL_SERVER_ERROR.getMessage(), ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<Void> handleBadRequestException(BadRequestException ex) {

        return Response.failure(AppConst.BAD_REQUEST.getMessage(), ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response<Void> handleNotFoundException(NotFoundException ex) {

        return Response.failure(AppConst.NOT_FOUND.getMessage(), ex.getMessage());
    }

    @ExceptionHandler(ResourceConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Response<Void> handleResourceConflictException(ResourceConflictException ex) {

        return Response.failure(AppConst.NOT_FOUND.getMessage(), ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Response<Void> handleUnauthorizedException(UnauthorizedException ex) {

        return Response.failure(AppConst.UNAUTHORIZED.getMessage(), ex.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Response<Void> handleForbiddenException(ForbiddenException ex) {

        return Response.failure(AppConst.FORBIDDEN.getMessage(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        final List<String> objResult = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            objResult.add(fieldError.getDefaultMessage());
        }

        return Response.failure(AppConst.BAD_REQUEST.getMessage(), objResult);
    }
}
