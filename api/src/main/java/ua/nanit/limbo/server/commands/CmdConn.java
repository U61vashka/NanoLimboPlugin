package ua.nanit.limbo.server.commands;

import ua.nanit.limbo.server.Command;
import ua.nanit.limbo.server.LimboServer;
import ua.nanit.limbo.server.Log;

public class CmdConn implements Command {

    private final LimboServer server;

    public CmdConn(LimboServer server) {
        this.server = server;
    }

    @Override
    public void execute() {
        Log.info("Connections: %d", server.getConnections().getCount());
    }

    @Override
    public String name() {
        return "conn";
    }

    @Override
    public String description() {
        return "Display connections count";
    }
}
