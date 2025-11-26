package se.frisk.cadettsplittershistory_edufy.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void handleMissingParam_returnsBadRequest() {
        MissingServletRequestParameterException ex =
                new MissingServletRequestParameterException("testParam", "String");

        ResponseEntity<Map<String, Object>> response = handler.handleMissingParam(ex);

        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().get("message").toString().contains("Missing required"));
        assertTrue(((Map<?, ?>) response.getBody().get("errors")).containsKey("testParam"));
    }

    @Test
    void handleTypeMismatch_returnsBadRequest() {
        MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
        when(ex.getName()).thenReturn("param");

        ResponseEntity<Map<String, Object>> response = handler.handleTypeMismatch(ex);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Invalid query parameter value", response.getBody().get("message"));
        assertTrue(((Map<?, ?>) response.getBody().get("errors")).containsKey("param"));
    }

    @Test
    void handleValidation_returnsBadRequest() {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("objectName", "field", "must not be null");
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);

        ResponseEntity<Map<String, Object>> response = handler.handleValidation(ex);

        assertEquals(400, response.getStatusCodeValue());
        Map<String, String> errors = (Map<String, String>) response.getBody().get("errors");
        assertTrue(errors.containsKey("field"));
        assertEquals("must not be null", errors.get("field"));
    }

    @Test
    void handleBadJson_returnsBadRequest() {
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("Bad JSON");
        ResponseEntity<Map<String, Object>> response = handler.handleBadJson(ex);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Wrong JSON format or invalid value", response.getBody().get("message"));
    }

    @Test
    void handleIllegalArgument_returnsBadRequest() {
        IllegalArgumentException ex = new IllegalArgumentException("Bad input");
        ResponseEntity<Map<String, Object>> response = handler.handleIllegalArgument(ex);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Bad input", response.getBody().get("message"));
    }

    @Test
    void handleNotFound_returnsNotFound() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Not found");
        ResponseEntity<Map<String, Object>> response = handler.handleNotFound(ex);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Not found", response.getBody().get("message"));
    }

    @Test
    void handleAccessDenied_returnsForbidden() {
        AccessDeniedException ex = new AccessDeniedException("Access is denied");
        ResponseEntity<Map<String, Object>> response = handler.handleAccessDenied(ex);

        assertEquals(403, response.getStatusCodeValue());
        assertEquals("Access denied", response.getBody().get("message"));
        assertEquals("FORBIDDEN", response.getBody().get("error"));
    }

    @Test
    void handleGenericException_returnsInternalServerError() {
        Exception ex = new Exception("Oops");
        ResponseEntity<Map<String, Object>> response = handler.handleGenericException(ex);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Unexpected error", response.getBody().get("message"));
    }
}