package com.shorka.telegramclone_ui.settings_screen;

import com.shorka.telegramclone_ui.SettingsTextEntity;

import java.util.ArrayList;

/**
 * Created by Kyrylo Avramenko on 8/22/2018.
 */
public class SettingsTextEntitiesGenerator {


    public static ArrayList<Object> getUserInfoList() {

        ArrayList<Object> items = new ArrayList<>();

        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.SubTitle, "", "Phone"));
        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.SubTitle, "", "Username"));
        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.SubTitle, "", "Bio"));

        return items;
    }

    public static ArrayList<Object> getSettingsList() {
        ArrayList<Object> items = new ArrayList<>();
        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.PlainText, "Notifications and Sounds", ""));
        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.PlainText, "Privacy and Security", ""));
        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.PlainText, "Data and Storage", ""));
        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.PlainText, "Chat BackGround", ""));

        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.TextWithSelect, "Theme", "Default"));
        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.TextWithSelect, "Language", "English"));
        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.TextWithToggle, "Enable Animations", ""));

        return items;
    }

    public static ArrayList<Object> getMessagesList() {
        ArrayList<Object> items = new ArrayList<>();
        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.TextWithToggle, "In-App Browser", ""));
        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.TextWithSelect, "Stickers", "18"));
        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.TextWithSelect, "Message Text Size", "16"));

        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.TextWithToggle, "Raise to Speak", ""));
        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.TextWithToggle, "Send by Enter", ""));
        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.TextWithToggle, "Autoplay Gifs", ""));
        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.TextWithToggle, "Save to gallery", ""));

        return items;
    }

    public static ArrayList<Object> getSupporList() {
        ArrayList<Object> items = new ArrayList<>();
        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.PlainText, "Ask a question", ""));
        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.PlainText, "Telegram FAQ", ""));
        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.PlainText, "Private Policy", ""));

        return items;
    }
}
