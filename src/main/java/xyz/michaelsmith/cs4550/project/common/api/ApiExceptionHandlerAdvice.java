package xyz.michaelsmith.cs4550.project.common.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import xyz.michaelsmith.cs4550.project.common.api.exception.BadRequestException;
import xyz.michaelsmith.cs4550.project.common.api.exception.ResourceNotFoundException;
import xyz.michaelsmith.cs4550.project.common.dto.ApiErrorDto;

@ControllerAdvice
public class ApiExceptionHandlerAdvice {
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ApiErrorDto handleResourceNotFound(ResourceNotFoundException ex) {
        return new ApiErrorDto(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ApiErrorDto handleBadRequest(BadRequestException ex) {
        return new ApiErrorDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }
}
