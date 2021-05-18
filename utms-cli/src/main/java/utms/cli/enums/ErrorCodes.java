package utms.cli.enums;

public enum ErrorCodes {
    FILE_NOT_FOUND("Configuration file not found."),
    INVALID_FILE("Configuration file is not valid."),
    INVALID_RUN_ID("Run Id is not valid.");

    private String message;

    private ErrorCodes(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
