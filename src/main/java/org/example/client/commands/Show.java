    package org.example.client.commands;

    import org.example.client.interfaces.Command;
    import org.example.packet.request.ResponsePacket;
    import org.example.packet.element.Location;
    import org.example.packet.element.Person;
    import org.example.packet.request.CommandPacket;

    import java.io.IOException;
    import java.nio.channels.SocketChannel;
    import java.util.List;
    import java.util.Map;


    import org.example.client.managers.ManagerInputOutput;

    import static org.example.client.Client.readModule;
    import static org.example.client.Client.writeModule;

    /**
     * Команда show - выводит все элементы коллекции в строковом представлении.
     */
    public class Show implements Command {
        public static ManagerInputOutput managerInputOutput = ManagerInputOutput.getInstance();
        /**
         * Проверяет аргументы команды.
         *
         * @param args аргументы
         * @return true, если аргументов нет
         */
        public boolean checkArg(String[] args) {
            if (args == null || args.length == 0) {
                return true;
            } else {
                managerInputOutput.writeLineIO("Команда show не принимает аргументы \n");
                return false;
            }
        }

        /**
         * Выполняет команду.
         *
         * @param args аргументы (не ожидаются)
         */
        public void executeCommand(String[] args, SocketChannel server) throws IOException, ClassNotFoundException {


            if (!checkArg(args)) {
                managerInputOutput.writeLineIO("Неверное количество аргументов\n");
                return;
            }


                CommandPacket commandPacket = new CommandPacket("show", args, null);

                writeModule.writePacketForServer(server, commandPacket);

                ResponsePacket ressponse = readModule.readResponseForClient(server);

                if (ressponse != null){
                    Map<Long, Person> persons = (Map<Long, Person>) ressponse.getData();

                    if(persons.isEmpty()){
                        managerInputOutput.writeLineIO("коллекция пустая\n");
                        return;
                    }
                    String header = String.format(
                            "%-6s | %-3s | %-15s | %-5s | %-4s | %-45s | %-4s | %-4s | %-10s | %-10s | %-8s | %-8s | %-8s | %-12s",
                            "KEY", "ID", "Name", "X", "Y", "DateCr", "Height", "Weight",
                            "Color", "Nationality", "Loc_X", "Loc_Y", "Loc_Z", "Loc_name"
                    );
                    managerInputOutput.writeLineIO(header + "\n");
                    managerInputOutput.writeLineIO("-".repeat(header.length()) + "\n");

                    for (Map.Entry<Long, Person> entry : persons.entrySet())     {
                        Long key = entry.getKey();
                        Person person = entry.getValue();
                        Location location = person.getLocation();

                        String locationX = location == null ? "null" : String.valueOf(location.getX());
                        String locationY = location == null ? "null" : String.valueOf(location.getY());
                        String locationZ = location == null ? "null" : String.valueOf(location.getZ());
                        String locationName = location == null ? "null" : String.valueOf(location.getName());


                        String line = String.format(
                                "%-6s | %-3s | %15s | %-5s | %-4s | %-10s | %-4s | %-4s | %-10s | %-10s | %-8s | %-8s | %-8s | %-12s",
                                key,
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
                }if (ressponse == null){
                    managerInputOutput.writeLineIO("Сервер не вернул ответ\n");
                    return;

                }



        }

        @Override
        public String toString() {
            return "show - выводит в стандартный поток вывода все элементы коллекции в строковом представлении";
        }
    }