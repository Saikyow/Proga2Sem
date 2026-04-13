package org.example.client.commands;

import org.example.client.interfaces.Command;
import org.example.packet.request.CommandPacket;
import org.example.packet.request.ResponsePacket;
import org.example.packet.element.Location;
import org.example.packet.element.Person;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.List;

import org.example.client.managers.ManagerInputOutput;

import static org.example.client.Client.readModule;
import static org.example.client.Client.writeModule;

public class PrintAscending implements Command {
    public static ManagerInputOutput managerInputOutput = ManagerInputOutput.getInstance();

    public boolean checkArg(String[] args) {
        if (args == null || args.length == 0) {
            return true;
        } else {
            managerInputOutput.writeLineIO("Команда print_ascending не принимает аргументы\n");
            return false;
        }
    }


    public void executeCommand(String[] args, SocketChannel server) throws IOException, ClassNotFoundException {
        if (!checkArg(args)) {
            managerInputOutput.writeLineIO("Неверное количество аргументов\n");
            return;
        }


        CommandPacket commandPacket = new CommandPacket("print_ascending", args, null);
        writeModule.writePacketForServer(server, commandPacket);

        ResponsePacket response = readModule.readResponseForClient(server);

        if (response == null) {
            managerInputOutput.writeLineIO("Сервер не вернул ответ\n");
            return;
        }

        managerInputOutput.writeLineIO(response.getMessage() + "\n");

        List<Person> persons = (List<Person>) response.getData();

        if (persons == null || persons.isEmpty()) {
            managerInputOutput.writeLineIO("Коллекция пустая\n");
            return;
        }

        String header = String.format(
                "%-3s | %-15s | %-5s | %-4s | %-10s | %-6s | %-6s | %-10s | %-12s | %-8s | %-8s | %-8s | %-12s",
                "ID", "Name", "X", "Y", "DateCr", "Height", "Weight",
                "Color", "Nationality", "Loc_X", "Loc_Y", "Loc_Z", "Loc_name"
        );
        managerInputOutput.writeLineIO(header + "\n");
        managerInputOutput.writeLineIO("-".repeat(header.length()) + "\n");

        for (Person person : persons) {
            Location location = person.getLocation();

            String locationX = location == null ? "null" : String.valueOf(location.getX());
            String locationY = location == null ? "null" : String.valueOf(location.getY());
            String locationZ = location == null ? "null" : String.valueOf(location.getZ());
            String locationName = location == null ? "null" : String.valueOf(location.getName());

            String line = String.format(
                    "%-3s | %-15s | %-5s | %-4s | %-10s | %-6s | %-6s | %-10s | %-12s | %-8s | %-8s | %-8s | %-12s",
                    person.getId(),
                    person.getName(),
                    person.getCoordinates().getX(),
                    person.getCoordinates().getY(),
                    person.getCrationDate(),
                    person.getHeight(),
                    person.getWeight(),
                    person.getHairColor(),
                    person.getNationality(),
                    locationX,
                    locationY,
                    locationZ,
                    locationName
            );

            managerInputOutput.writeLineIO(line + "\n");
        }
    }



    @Override
    public String toString() {
        return "print_ascending - выводит элементы коллекции в порядке возрастания";
    }
}