package ua.nanit.limbo.protocol.packets.configuration;

import ua.nanit.limbo.protocol.ByteMessage;
import ua.nanit.limbo.protocol.PacketOut;
import ua.nanit.limbo.protocol.registry.Version;

import java.util.List;

public class PacketKnownPacks implements PacketOut {

    @Override
    public void encode(ByteMessage msg, Version version) {
        List<String> versions = version.getDisplayNames();
        msg.writeVarInt(versions.size());

        for (String versionName : versions) {
            msg.writeString("minecraft");
            msg.writeString("core");
            msg.writeString(versionName);
        }
    }

}
