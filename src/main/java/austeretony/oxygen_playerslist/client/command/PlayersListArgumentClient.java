package austeretony.oxygen_playerslist.client.command;

import java.util.concurrent.TimeUnit;

import austeretony.oxygen_core.client.api.ClientReference;
import austeretony.oxygen_core.client.api.OxygenHelperClient;
import austeretony.oxygen_core.common.command.ArgumentExecutor;
import austeretony.oxygen_playerslist.client.gui.playerslist.PlayersListScreen;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class PlayersListArgumentClient implements ArgumentExecutor {

    @Override
    public String getName() {
        return "players-list";
    }

    @Override
    public void process(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 1)
            OxygenHelperClient.scheduleTask(()->this.openMenu(), 100L, TimeUnit.MILLISECONDS);
    }

    private void openMenu() {
        ClientReference.delegateToClientThread(()->ClientReference.displayGuiScreen(new PlayersListScreen()));
    }
}
