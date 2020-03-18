package austeretony.oxygen_playerslist.client.gui.settings;

import austeretony.alternateui.screen.framework.GUIElementsFramework;
import austeretony.oxygen_core.client.OxygenManagerClient;
import austeretony.oxygen_core.client.api.ClientReference;
import austeretony.oxygen_core.client.api.EnumBaseGUISetting;
import austeretony.oxygen_core.client.gui.elements.OxygenCheckBoxButton;
import austeretony.oxygen_core.client.gui.elements.OxygenDropDownList;
import austeretony.oxygen_core.client.gui.elements.OxygenDropDownList.OxygenDropDownListWrapperEntry;
import austeretony.oxygen_core.client.gui.elements.OxygenTextLabel;
import austeretony.oxygen_core.client.gui.settings.ElementsContainer;
import austeretony.oxygen_core.client.gui.settings.gui.callback.SetColorCallback;
import austeretony.oxygen_core.client.gui.settings.gui.callback.SetKeyCallback;
import austeretony.oxygen_core.client.gui.settings.gui.callback.SetOffsetCallback;
import austeretony.oxygen_core.client.gui.settings.gui.callback.SetScaleCallback;
import austeretony.oxygen_playerslist.client.settings.EnumPlayersListClientSetting;
import austeretony.oxygen_playerslist.client.settings.gui.EnumPlayersListGUISetting;

public class PlayersListSettingsContainer implements ElementsContainer {

    //common

    private OxygenCheckBoxButton addPlayersListButton;

    //interface

    private OxygenDropDownList alignmentPlayersList;


    @Override
    public String getLocalizedName() {
        return ClientReference.localize("oxygen_playerslist.gui.settings.module.playerslist");
    }

    @Override
    public boolean hasCommonSettings() {
        return true;
    }

    @Override
    public boolean hasGUISettings() {
        return true;
    }

    @Override
    public void addCommon(GUIElementsFramework framework) {
        framework.addElement(new OxygenTextLabel(68, 25, ClientReference.localize("oxygen_core.gui.settings.option.oxygenMenu"), EnumBaseGUISetting.TEXT_SCALE.get().asFloat() - 0.05F, EnumBaseGUISetting.TEXT_ENABLED_COLOR.get().asInt()));

        //add players list to menu
        framework.addElement(new OxygenTextLabel(78, 34, ClientReference.localize("oxygen_playerslist.gui.settings.option.addPlayersList"), EnumBaseGUISetting.TEXT_SUB_SCALE.get().asFloat() - 0.1F, EnumBaseGUISetting.TEXT_DARK_ENABLED_COLOR.get().asInt()));

        framework.addElement(this.addPlayersListButton = new OxygenCheckBoxButton(68, 29));
        this.addPlayersListButton.setToggled(EnumPlayersListClientSetting.ADD_PLAYERS_LIST.get().asBoolean());
        this.addPlayersListButton.setClickListener((mouseX, mouseY, mouseButton)->{
            EnumPlayersListClientSetting.ADD_PLAYERS_LIST.get().setValue(String.valueOf(this.addPlayersListButton.isToggled()));
            OxygenManagerClient.instance().getClientSettingManager().changed();
        });
    }

    @Override
    public void addGUI(GUIElementsFramework framework) {
        framework.addElement(new OxygenTextLabel(68, 25, ClientReference.localize("oxygen_core.gui.settings.option.alignment"), EnumBaseGUISetting.TEXT_SCALE.get().asFloat() - 0.05F, EnumBaseGUISetting.TEXT_ENABLED_COLOR.get().asInt()));

        //players list alignment

        String currAlignmentStr;
        switch (EnumPlayersListGUISetting.PLAYERS_LIST_ALIGNMENT.get().asInt()) {
        case - 1: 
            currAlignmentStr = ClientReference.localize("oxygen_core.alignment.left");
            break;
        case 0:
            currAlignmentStr = ClientReference.localize("oxygen_core.alignment.center");
            break;
        case 1:
            currAlignmentStr = ClientReference.localize("oxygen_core.alignment.right");
            break;    
        default:
            currAlignmentStr = ClientReference.localize("oxygen_core.alignment.center");
            break;
        }
        framework.addElement(this.alignmentPlayersList = new OxygenDropDownList(68, 35, 55, currAlignmentStr));
        this.alignmentPlayersList.addElement(new OxygenDropDownListWrapperEntry<Integer>(- 1, ClientReference.localize("oxygen_core.alignment.left")));
        this.alignmentPlayersList.addElement(new OxygenDropDownListWrapperEntry<Integer>(0, ClientReference.localize("oxygen_core.alignment.center")));
        this.alignmentPlayersList.addElement(new OxygenDropDownListWrapperEntry<Integer>(1, ClientReference.localize("oxygen_core.alignment.right")));

        this.alignmentPlayersList.<OxygenDropDownListWrapperEntry<Integer>>setElementClickListener((element)->{
            EnumPlayersListGUISetting.PLAYERS_LIST_ALIGNMENT.get().setValue(String.valueOf(element.getWrapped()));
            OxygenManagerClient.instance().getClientSettingManager().changed();
        });

        framework.addElement(new OxygenTextLabel(68, 33, ClientReference.localize("oxygen_playerslist.gui.settings.option.alignmentPlayersList"), EnumBaseGUISetting.TEXT_SUB_SCALE.get().asFloat() - 0.1F, EnumBaseGUISetting.TEXT_DARK_ENABLED_COLOR.get().asInt()));
    }

    @Override
    public void resetCommon() {
        //add players list to menu
        this.addPlayersListButton.setToggled(true);
        EnumPlayersListClientSetting.ADD_PLAYERS_LIST.get().reset();    

        OxygenManagerClient.instance().getClientSettingManager().changed();
    }

    @Override
    public void resetGUI() {
        //players list alignment
        this.alignmentPlayersList.setDisplayText(ClientReference.localize("oxygen_core.alignment.center"));
        EnumPlayersListGUISetting.PLAYERS_LIST_ALIGNMENT.get().reset();

        OxygenManagerClient.instance().getClientSettingManager().changed();
    }

    @Override
    public void initSetColorCallback(SetColorCallback callback) {}

    @Override
    public void initSetScaleCallback(SetScaleCallback callback) {}

    @Override
    public void initSetOffsetCallback(SetOffsetCallback callback) {}

    @Override
    public void initSetKeyCallback(SetKeyCallback callback) {}
}
