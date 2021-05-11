package austeretony.oxygen_player_list.client.gui.player_list;

import austeretony.oxygen_core.client.api.OxygenClient;
import austeretony.oxygen_core.client.gui.base.GUIUtils;
import austeretony.oxygen_core.client.gui.base.Textures;
import austeretony.oxygen_core.client.gui.base.block.Texture;
import austeretony.oxygen_core.client.gui.base.common.ListEntry;
import austeretony.oxygen_core.client.gui.util.OxygenGUIUtils;
import austeretony.oxygen_core.client.util.MinecraftClient;
import austeretony.oxygen_core.common.player.ActivityStatus;
import austeretony.oxygen_core.common.player.shared.PlayerSharedData;
import net.minecraft.client.network.NetworkPlayerInfo;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

public class PlayerListEntry extends ListEntry<UUID> {

    private static final Texture STATUS_ICONS_TEXTURE = Texture.builder()
            .texture(Textures.STATUS_ICONS)
            .size(3, 3)
            .imageSize(12, 3)
            .build();
    private static final Texture PING_ICONS_TEXTURE = Texture.builder()
            .texture(Textures.PING_ICONS)
            .size(10, 6)
            .imageSize(10, 36)
            .build();

    private final PlayerSharedData sharedData;
    private final String lastActivityStr, dimensionStr;
    @Nonnull
    private final List<String> rolesList;

    private ActivityStatus activityStatus;
    private int pingIconV;

    public PlayerListEntry(@Nonnull PlayerSharedData sharedData) {
        super(sharedData.getUsername(), sharedData.getPlayerUUID());
        this.sharedData = sharedData;
        lastActivityStr = OxygenGUIUtils.getLastActivityFormattedString(sharedData);
        dimensionStr = OxygenClient.getPlayerDimensionName(sharedData);

        rolesList = OxygenGUIUtils.getPlayerRoles(sharedData);
        activityStatus = OxygenClient.getPlayerActivityStatus(sharedData);
        updatePing();
    }

    private void updatePing() {
        NetworkPlayerInfo info = MinecraftClient.getPlayerNetworkInfo(sharedData.getPlayerUUID());
        int responseTime = 1000;
        if (info != null)
            responseTime = info.getResponseTime();
        if (responseTime < 0)
            pingIconV = 5;
        else if (responseTime < 150)
            pingIconV = 0;
        else if (responseTime < 300)
            pingIconV = 1;
        else if (responseTime < 600)
            pingIconV = 2;
        else if (responseTime < 1000)
            pingIconV = 3;
        else
            pingIconV = 4;
    }

    @Override
    public void update() {
        activityStatus = OxygenClient.getPlayerActivityStatus(sharedData);
        updatePing();
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        if (!isVisible()) return;
        GUIUtils.pushMatrix();
        GUIUtils.translate(getX(), getY());

        GUIUtils.drawRect(0, 0, getWidth(), getHeight(), getColorFromState(fill));

        GUIUtils.colorDef();
        GUIUtils.drawTexturedRect(7, 4, STATUS_ICONS_TEXTURE.getU() + activityStatus.ordinal() * STATUS_ICONS_TEXTURE.getWidth(),
                STATUS_ICONS_TEXTURE.getV(), STATUS_ICONS_TEXTURE);

        float textY = (getHeight() - GUIUtils.getTextHeight(text.getScale())) / 2F + .5F;
        GUIUtils.drawString(sharedData.getUsername(), 18F, textY, text.getScale(), getColorFromState(text), false);
        GUIUtils.drawString(dimensionStr, 95F, textY, text.getScale(), getColorFromState(text), false);

        GUIUtils.colorDef();
        GUIUtils.drawTexturedRect(getWidth() - 20F, 2, PING_ICONS_TEXTURE.getU(),
                PING_ICONS_TEXTURE.getV() + pingIconV * PING_ICONS_TEXTURE.getHeight(), PING_ICONS_TEXTURE);

        GUIUtils.popMatrix();
    }

    @Override
    public void drawForeground(int mouseX, int mouseY, float partialTicks) {
        if (!isVisible()) return;
        if (activityStatus == ActivityStatus.OFFLINE && mouseX >= getX() + 6 && mouseY >= getY() + 3
                && mouseX < getX() + 11 && mouseY < getY() + 8) {
            drawToolTip(getX() + 10, getY() + 4 - TOOLTIP_HEIGHT, lastActivityStr);
        } else if (!rolesList.isEmpty() && mouseX >= getX() + 18 && mouseY >= getY() + 1
                && mouseX < getX() + 85 && mouseY < getY() + 9) {
            drawToolTip(getX() + 18, getY() + 1 - TOOLTIP_HEIGHT * rolesList.size(), rolesList);
        }
    }
}
