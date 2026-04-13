package org.example.packet.request;

import org.example.packet.element.Person;

import java.io.Serializable;

public class CommandPacket implements Serializable {
    private final String commandName;
    private final String[] args;
    private final Person values;

        public CommandPacket(String commandName, String[] args, Person values) {
        this.commandName = commandName;
        this.args = args;
        this.values = values;
    }

    public String getCommandName() {
        return commandName;
    }

    public String[] getArgs(){
        return args;
    }

    public Person getValues() {
        return values;
    }

}
