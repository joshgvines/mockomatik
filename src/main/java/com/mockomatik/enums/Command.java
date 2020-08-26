package com.mockomatik.enums;

public enum Command {

    CREATE("/create"),
    EXIT("/exit"),
    KILL("/kill"),
    HELP("/help"),
    HISTORY("/history"),
    
    OPTIONS("Options:\n"
            + "/create   -  Create Test Templates, INPUT_PATH: <your path> [ENTER]  then-->  OUTPUT_PATH: <your path> [ENTER]\n"
                            + "\t\t - INPUT_PATH  - Path to the classes you want to generate templates for.\n"
                            + "\t\t - OUTPUT_PATH - Path to where you want the templates to be created.\n"
            + "/exit     -  Quit the program \n"
            + "/kill     -  Quit the current run (Only works if a session is already started)\n"
            + "/history  -  Display recently entered commands");
    
    private String command;
    
    private Command(String command) {
        this.command = command;
    }
    
    public String getCommand() {
        return command;
    }
}
