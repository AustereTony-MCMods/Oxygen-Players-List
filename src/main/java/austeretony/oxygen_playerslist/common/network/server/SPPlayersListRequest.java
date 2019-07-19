package austeretony.oxygen_playerslist.common.network.server;

import java.util.UUID;

import austeretony.oxygen.common.OxygenManagerServer;
import austeretony.oxygen.common.api.OxygenHelperServer;
import austeretony.oxygen.common.core.api.CommonReference;
import austeretony.oxygen.common.main.OxygenMain;
import austeretony.oxygen.common.network.ProxyPacket;
import austeretony.oxygen_playerslist.common.main.PlayersListMain;
import austeretony.oxygen_playerslist.common.network.client.CPPlayersListCommand;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;

public class SPPlayersListRequest extends ProxyPacket {

    private EnumRequest request;

    public SPPlayersListRequest() {}

    public SPPlayersListRequest(EnumRequest request) {
        this.request = request;
    }

    @Override
    public void write(PacketBuffer buffer, INetHandler netHandler) {
        buffer.writeByte(this.request.ordinal());
    }

    @Override
    public void read(PacketBuffer buffer, INetHandler netHandler) {
        EntityPlayerMP playerMP = getEntityPlayerMP(netHandler);
        UUID playerUUID = CommonReference.getPersistentUUID(playerMP);
        this.request = EnumRequest.values()[buffer.readByte()];
        switch (this.request) {
        case OPEN_PLAYERS_LIST:
            if (!OxygenHelperServer.isSyncing(playerUUID)) {
                OxygenManagerServer.instance().syncSharedPlayersData(playerMP, OxygenHelperServer.getSharedDataIdentifiersForScreen(PlayersListMain.PLAYER_LIST_SCREEN_ID));
                OxygenMain.network().sendTo(new CPPlayersListCommand(CPPlayersListCommand.EnumCommand.OPEN_PLAYERS_LIST), playerMP);
            }
            break;
        }
    }

    public enum EnumRequest {

        OPEN_PLAYERS_LIST
    }
}
