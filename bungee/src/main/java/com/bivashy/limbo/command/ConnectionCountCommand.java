package com.bivashy.limbo.command;

import com.bivashy.limbo.NanoLimboBungee;

import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Default;
import revxrsal.commands.annotation.Dependency;
import revxrsal.commands.bungee.annotation.CommandPermission;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.exception.SendMessageException;
import ua.nanit.limbo.server.LimboServer;

@Command("limboconn")
public class ConnectionCountCommand {
    @Dependency
    private NanoLimboBungee plugin;

    @Default
    @CommandPermission("limbo.connection")
    public void execute(CommandActor actor, LimboServer limboServer) {
        throw new SendMessageException(plugin.getLimboConfig().getMessages().message("connection-count", limboServer.getConnections().getCount()));
    }
}
