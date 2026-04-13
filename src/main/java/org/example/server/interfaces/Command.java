package org.example.server.interfaces;

import org.example.packet.request.ResponsePacket;
import org.example.packet.request.CommandPacket;

public interface Command {
    ResponsePacket executeCommand(CommandPacket packet);
}