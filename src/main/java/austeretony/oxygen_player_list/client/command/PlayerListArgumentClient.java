package austeretony.oxygen_player_list.client.command;

import austeretony.oxygen_core.client.api.OxygenClient;
import austeretony.oxygen_core.common.command.CommandArgument;
import austeretony.oxygen_player_list.common.main.PlayerListMain;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class PlayerListArgumentClient implements CommandArgument {

    @Override
    public String getName() {
        return "player-list";
    }

    @Override
    public void process(MinecraftServer server, ICommandSender sender, String[] args) {
        if (args.length == 1) {
            OxygenClient.openScreenWithDelay(PlayerListMain.SCREEN_ID_PLAYER_LIST);
        }
    }
}
