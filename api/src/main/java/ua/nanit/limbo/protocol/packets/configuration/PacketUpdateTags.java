package ua.nanit.limbo.protocol.packets.configuration;

import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.nbt.IntBinaryTag;
import net.kyori.adventure.nbt.ListBinaryTag;
import ua.nanit.limbo.protocol.ByteMessage;
import ua.nanit.limbo.protocol.PacketOut;
import ua.nanit.limbo.protocol.registry.Version;

import java.util.Set;

public class PacketUpdateTags implements PacketOut {

    private CompoundBinaryTag tags;

    public CompoundBinaryTag getTags() {
        return tags;
    }

    public void setTags(CompoundBinaryTag tags) {
        this.tags = tags;
    }

    @Override
    public void encode(ByteMessage msg, Version version) {
        Set<String> names = this.tags.keySet();
        msg.writeVarInt(names.size());
        for (String name : names) {
            msg.writeString(name);

            CompoundBinaryTag subTag = (CompoundBinaryTag) this.tags.get(name);

            Set<String> subNames = subTag.keySet();
            msg.writeVarInt(subNames.size());
            for (String subName : subNames) {
                msg.writeString(subName);

                ListBinaryTag ids = (ListBinaryTag) subTag.get(subName);
                msg.writeVarInt(ids.size());
                for (Object objectId : ids) {
                    msg.writeVarInt(((IntBinaryTag) objectId).value());
                }
            }
        }
    }
}
