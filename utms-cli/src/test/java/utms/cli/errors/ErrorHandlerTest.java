package utms.cli.errors;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import utms.cli.enums.ErrorCodes;

import static org.junit.Assert.assertEquals;

public class ErrorHandlerTest {

    private ErrorHandler errorHandler;

    @Before
    public void init() {
        errorHandler = new ErrorHandler(new ObjectMapper());
    }

    @Test
    public void testIfErrorHandlerReturnsErrorMessageAsJson() {
        String actual = errorHandler.handleError("Run Id is not valid.");
        String expected = "{\"error\":\"Run Id is not valid.\"}";
        assertEquals(expected, actual);
    }
}
