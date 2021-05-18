package utms.cli.enums;

public enum TestStatus {
    PASSED("passed"),
    FAILED("failed"),
    SKIPPED("skipped");

    private final String status;

    private TestStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
