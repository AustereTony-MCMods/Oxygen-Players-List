package austeretony.oxygen_playerslist.common.network.client;

import austeretony.oxygen.common.network.ProxyPacket;
import austeretony.oxygen_playerslist.client.PlayersListManagerClient;
import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;

public class CPPlayersListCommand extends ProxyPacket {

    private EnumCommand command;

    public CPPlayersListCommand() {}

    public CPPlayersListCommand(EnumCommand command) {
        this.command = command;
    }

    @Override
    public void write(PacketBuffer buffer, INetHandler netHandler) {
        buffer.writeByte(this.command.ordinal());
    }

    @Override
    public void read(PacketBuffer buffer, INetHandler netHandler) {
        this.command = EnumCommand.values()[buffer.readByte()];
        switch (this.command) {
        case OPEN_PLAYERS_LIST:
            PlayersListManagerClient.openPlayersListDelegated();
            break;
        }
    }

    public enum EnumCommand {

        OPEN_PLAYERS_LIST
    }
}
