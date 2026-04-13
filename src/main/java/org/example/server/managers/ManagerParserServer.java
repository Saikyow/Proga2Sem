package org.example.server.managers;

import org.example.packet.request.ResponsePacket;
import org.example.packet.request.CommandPacket;
import org.example.server.commands.*;
import org.example.server.interfaces.Command;

import java.util.HashMap;

public class ManagerParserServer {

    private final HashMap<String, Command> commands;

    public ManagerParserServer(CollectionManager collectionManager, String filePath) {
        this.commands = new HashMap<>();

        csvParserManager csvManager = csvParserManager.getInstance();

        commands.put("clear", new Clear(collectionManager));
        commands.put("show", new Show(collectionManager));
        commands.put("insert", new InsertCommand(collectionManager));
        commands.put("update_id", new UpdateID(collectionManager));
        commands.put("remove_key", new RemoveKey(collectionManager));
        commands.put("replace_if_greater", new ReplaceIfGreater(collectionManager));
        commands.put("replace_if_lower", new ReplaceIfLowe(collectionManager));
        commands.put("sum_of_weight", new SumOfWeight(collectionManager));
        commands.put("group_counting_by_name", new GroupCountingByName(collectionManager));
        commands.put("print_ascending", new PrintAscending(collectionManager));
        commands.put("info", new Info(collectionManager));
        commands.put("save", new Save(collectionManager, filePath));
    }

    public ResponsePacket execute(CommandPacket packet) {
        if (packet == null) {
            return new ResponsePacket("Пакет пустой", null);
        }

        Command command = commands.get(packet.getCommandName());

        if (command == null) {
            return new ResponsePacket("Неизвестная команда: " + packet.getCommandName(), null);
        }

        return command.executeCommand(packet);
    }
}