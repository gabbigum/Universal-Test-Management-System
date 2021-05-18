package utms.cli.runner;

import org.junit.BeforeClass;
import org.junit.Test;
import utms.cli.enums.TestStatus;

import static org.junit.Assert.assertEquals;


public class CommandRunnerTest {

    private static CommandRunner commandRunner;

    @BeforeClass
    public static void init() {
        commandRunner = new CommandRunner();
    }

    @Test
    public void testIfCommandRunnerFailsCommandOnIllegalTest() {
        utms.cli.model.Test test = new utms.cli.model.Test();
        test.setCommand("echoo Test1");
        test.setEnabled(true);

        commandRunner.runTest(test);

        String actualStatus = test.getStatus();
        String expectedStatus = TestStatus.FAILED.getStatus();

        assertEquals("Expected status 'failed'.", expectedStatus, actualStatus);
    }

    @Test
    public void testIfCommandRunnerExecutesCommandOnCorrectTest() {
        utms.cli.model.Test test = new utms.cli.model.Test();
        test.setCommand("echo Test1");
        test.setEnabled(true);

        commandRunner.runTest(test);

        String actualStatus = test.getStatus();
        String expectedStatus = TestStatus.PASSED.getStatus();

        assertEquals("Expected status 'passed'.", expectedStatus, actualStatus);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testIfCommandRunnerThrowsExceptionOnNullCommand() {
        utms.cli.model.Test test = new utms.cli.model.Test();
        test.setCommand(null);
        test.setEnabled(true);

        commandRunner.runTest(test);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testIfCommandRunnerThrowsExceptionOnNullTest() {
        commandRunner.runTest(null);
    }




}
