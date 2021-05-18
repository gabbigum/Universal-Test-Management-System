package utms.cli;

import picocli.CommandLine;
import utms.cli.commands.ConfigCommand;

public class Main {

    public static void main(String[] args) {
        new CommandLine(new ConfigCommand()).execute(args);
    }

}
