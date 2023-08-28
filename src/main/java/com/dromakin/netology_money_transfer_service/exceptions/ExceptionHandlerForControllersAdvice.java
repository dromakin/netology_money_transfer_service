/*
 * File:     ExceptionHandlerForControllersAdvice
 * Package:  com.dromakin.netology_money_transfer_service.exceptions
 * Project:  netology_money_transfer_service
 *
 * Created by dromakin as 28.08.2023
 *
 * author - dromakin
 * maintainer - dromakin
 * version - 2023.08.28
 * copyright - ORGANIZATION_NAME Inc. 2023
 */
package com.dromakin.netology_money_transfer_service.exceptions;

import com.dromakin.netology_money_transfer_service.dto.ErrorResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerForControllersAdvice {

    public static final String RESPONSE_ERROR_MESSAGE_TEMPLATE = "Incorrect request: [{}]";
    public static final String OPERATION_ERROR_MESSAGE_TEMPLATE = "Operation processing error [{}]";
    private static final Logger fileLogger = LoggerFactory.getLogger("OperationsProcessingLog");

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(HttpMessageNotReadableException exception) {
        fileLogger.error(RESPONSE_ERROR_MESSAGE_TEMPLATE, exception.getMessage());
        var error = ErrorResponseDTO.builder()
                .operationId(-1L)
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(MethodArgumentNotValidException exception) {
        var errorMessage = new StringBuilder();
        var errors = exception.getBindingResult().getFieldErrors();
        for (int i = 0; i < errors.size(); i++) {
            errorMessage.append("[")
                    .append(errors.get(i).getField())
                    .append(" = ")
                    .append(errors.get(i).getRejectedValue())
                    .append("] reason: ")
                    .append(errors.get(i).getDefaultMessage());
            if (i + 1 < errors.size()) {
                errorMessage.append(";   ");
            }
        }
        fileLogger.error(RESPONSE_ERROR_MESSAGE_TEMPLATE, errorMessage);
        ErrorResponseDTO error = ErrorResponseDTO.builder().operationId(-1L).message(errorMessage.toString()).build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OperationException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(OperationException exception) {
        fileLogger.error(OPERATION_ERROR_MESSAGE_TEMPLATE, exception.getOperation());
        var error = ErrorResponseDTO.builder()
                .operationId(exception.getOperation().getId())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(OperationNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(OperationNotFoundException exception) {
        fileLogger.error(OPERATION_ERROR_MESSAGE_TEMPLATE, exception.getMessage());
        var error = ErrorResponseDTO.builder()
                .operationId(exception.getId())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
