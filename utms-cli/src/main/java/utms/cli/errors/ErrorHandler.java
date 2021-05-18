package utms.cli.errors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ErrorHandler {

    private ObjectMapper objectMapper;

    public ErrorHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String handleError(String errorMessage) {
        ErrorMessage error = new ErrorMessage(errorMessage);
        String value = null;
        try {
            value = objectMapper.writeValueAsString(error);
        } catch (JsonProcessingException jsonProcessingException) {
            jsonProcessingException.printStackTrace();
        }
        return value;
    }
}
