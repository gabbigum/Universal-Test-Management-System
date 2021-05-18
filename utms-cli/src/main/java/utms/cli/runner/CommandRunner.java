package utms.cli.runner;

import utms.cli.enums.TestStatus;
import utms.cli.model.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Base64;

public class CommandRunner {

    public CommandRunner() {

    }

    public Test runTest(Test test) {
        if (test == null || test.getCommand() == null) {
            throw new IllegalArgumentException("You must provide test and command which are not null.");
        }
        LocalDateTime start = LocalDateTime.now();

        String output = null, encodedOutput = null;
        String errorValue = null, encodedError = null;
        String exitStatus = null;

        Process p = null;
        try {
            p = Runtime.getRuntime().exec(test.getCommand());
            p.waitFor();
            int exitVal = p.exitValue();
            exitStatus = getExitStatus(exitVal);

            output = readProccessOutputValue(p);
            encodedOutput = encodeStringToBase64(output);

            errorValue = readErrorValue(p);
            encodedError = encodeStringToBase64(errorValue);
        } catch (IOException e) {
            exitStatus = "failed";
            encodedError = encodeStringToBase64(encodeStringToBase64(e.getCause().getMessage()));
            // e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LocalDateTime end = LocalDateTime.now();

        test.setOutput(encodedOutput);
        test.setError(encodedError);
        test.setStartDate(start);
        test.setEndDate(end);
        test.setStatus(exitStatus);
        return test;
    }

    private String getExitStatus(int exitVal) {
        return exitVal == 0 ? TestStatus.PASSED.getStatus() : TestStatus.FAILED.getStatus();
    }
    // remove code duplication
    private String readErrorValue(Process p) {
        String result = "";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String readProccessOutputValue(Process p) {
        String result = "";

        try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static Process executeCommand(String command) throws IOException {
        return Runtime.getRuntime().exec(command);
    }

    private static String encodeStringToBase64(String output) {
        return Base64.getEncoder().encodeToString(output.getBytes());
    }
}
