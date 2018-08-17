package xyz.michaelsmith.cs4550.project.common.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import xyz.michaelsmith.cs4550.project.common.api.exception.ResourceNotFoundException;
import xyz.michaelsmith.cs4550.project.common.dto.ApiErrorDto;

@ControllerAdvice
public class ApiExceptionHandlerAdvice {
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ApiErrorDto handleResourceNotFound(ResourceNotFoundException ex) {
        return new ApiErrorDto(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }
}
