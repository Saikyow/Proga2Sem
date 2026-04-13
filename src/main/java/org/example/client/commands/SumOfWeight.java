package org.example.client.commands;

import org.example.client.interfaces.Command;
import org.example.packet.request.ResponsePacket;
import org.example.packet.request.CommandPacket;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import static org.example.client.Client.*;


/**
 * Команда sum_of_weight - выводит сумму значений поля weight для всех элементов коллекции.
 */
public class SumOfWeight implements Command {


    /**
     * Создает команду sum_of_weight.
     *
     * @param collectionManager менеджер коллекции
     */

    /**
     * Выполняет команду sum_of_weight.
     * Суммирует значения поля weight всех элементов коллекции.
     *
     * @param args аргументы команды (не ожидаются)
     */
    public void executeCommand(String[] args, SocketChannel serverChannel)   throws IOException, ClassNotFoundException {

        if (checkArg(args)) {
            CommandPacket commandPacket = new CommandPacket("sum_of_weight", args, null);

                writeModule.writePacketForServer(serverChannel, commandPacket);

                ResponsePacket responsePacket = readModule.readResponseForClient(serverChannel);

                if (responsePacket != null) {
                    managerInputOutput.writeLineIO("Сервер: " + responsePacket.getMessage());
                }


        }

    }

    /**
     * Проверяет аргументы команды.
     *
     * @param args массив аргументов
     * @return true, если аргументов нет
     */
    private boolean checkArg(String[] args){
        if (args == null || args.length == 0){
            return true;
        }
        managerInputOutput.writeLineIO("Ошибка! Команда sum_of_weight не принимает аргументы. \n");
        return false;
    }

    @Override
    public String toString(){
        return "sum_of_weight - выводит сумму значений поля weight для всех элементов коллекции";
    }
}