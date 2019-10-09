package austeretony.oxygen_playerslist.client.gui.playerslist;

import java.util.UUID;

import austeretony.alternateui.util.EnumGUIAlignment;
import austeretony.oxygen_core.client.api.ClientReference;
import austeretony.oxygen_core.client.api.OxygenHelperClient;
import austeretony.oxygen_core.client.gui.IndexedGUIButton;
import austeretony.oxygen_core.client.gui.OxygenGUITextures;
import austeretony.oxygen_core.client.gui.elements.CustomRectUtils;
import austeretony.oxygen_core.client.gui.settings.GUISettings;
import austeretony.oxygen_core.common.PlayerSharedData;
import austeretony.oxygen_core.common.api.EnumDimension;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;

public class PlayersListEntryGUIButton extends IndexedGUIButton<UUID> {

    private String dimension;

    private int statusIconU, pingIconV;

    private boolean initialized;

    public PlayersListEntryGUIButton(PlayerSharedData sharedData) {
        super(sharedData.getPlayerUUID());
        this.dimension = EnumDimension.getLocalizedNameFromId(OxygenHelperClient.getPlayerDimension(sharedData));
        this.setDisplayText(sharedData.getUsername());//need for search mechanic
        this.statusIconU = OxygenHelperClient.getPlayerActivityStatus(sharedData).ordinal() * 3;
        NetworkPlayerInfo info = ClientReference.getPlayerInfo(sharedData.getPlayerUUID());
        int responseTime = 1000;
        if (info != null)//mc may sync data faster than oxygen, so NetworkPlayerInfo may be null if player left the game
            responseTime = info.getResponseTime();
        if (responseTime < 0)
            this.pingIconV = 5;
        else if (responseTime < 150)
            this.pingIconV = 0;
        else if (responseTime < 300)
            this.pingIconV = 1;
        else if (responseTime < 600)
            this.pingIconV = 2;
        else if (responseTime < 1000)
            this.pingIconV = 3;
        else
            this.pingIconV = 4;
        this.setDynamicBackgroundColor(GUISettings.get().getEnabledElementColor(), GUISettings.get().getDisabledElementColor(), GUISettings.get().getHoveredElementColor());
        this.setTextDynamicColor(GUISettings.get().getEnabledTextColor(), GUISettings.get().getDisabledTextColor(), GUISettings.get().getHoveredTextColor());
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        if (this.isVisible()) {  
            GlStateManager.pushMatrix();           
            GlStateManager.translate(this.getX(), this.getY(), 0.0F);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);  

            int color, textColor, textY;                      
            if (!this.isEnabled()) {                 
                color = this.getDisabledBackgroundColor();
                textColor = this.getDisabledTextColor();           
            } else if (this.isHovered() || this.isToggled()) {                 
                color = this.getHoveredBackgroundColor();
                textColor = this.getHoveredTextColor();
            } else {                   
                color = this.getEnabledBackgroundColor(); 
                textColor = this.getEnabledTextColor();      
            }

            int third = this.getWidth() / 3;
            CustomRectUtils.drawGradientRect(0.0D, 0.0D, third, this.getHeight(), 0x00000000, color, EnumGUIAlignment.RIGHT);
            drawRect(third, 0, this.getWidth() - third, this.getHeight(), color);
            CustomRectUtils.drawGradientRect(this.getWidth() - third, 0.0D, this.getWidth(), this.getHeight(), 0x00000000, color, EnumGUIAlignment.LEFT);

            textY = (this.getHeight() - this.textHeight(this.getTextScale())) / 2 + 1;

            GlStateManager.pushMatrix();           
            GlStateManager.translate(18.0F, textY, 0.0F); 
            GlStateManager.scale(this.getTextScale(), this.getTextScale(), 0.0F); 
            this.mc.fontRenderer.drawString(this.getDisplayText(), 0, 0, textColor, this.isTextShadowEnabled());
            GlStateManager.popMatrix();

            GlStateManager.pushMatrix();    
            GlStateManager.translate(90.0F, textY, 0.0F); 
            GlStateManager.scale(this.getTextScale(), this.getTextScale(), 0.0F); 
            this.mc.fontRenderer.drawString(this.dimension, 0, 0, textColor, this.isTextShadowEnabled());
            GlStateManager.popMatrix();

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);  

            this.mc.getTextureManager().bindTexture(OxygenGUITextures.STATUS_ICONS); 
            drawCustomSizedTexturedRect(7, 4, this.statusIconU, 0, 3, 3, 12, 3);   
            this.mc.getTextureManager().bindTexture(OxygenGUITextures.PING_ICONS); 
            drawCustomSizedTexturedRect(this.getWidth() - 20, 2, 0, this.pingIconV * 6, 10, 6, 10, 36);  

            GlStateManager.popMatrix();
        }
    }
}
