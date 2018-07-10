package com.shorka.telegramclone_ui.entities;

/**
 * Created by Kyrylo Avramenko on 6/12/2018.
 */
public class SettingsTextEntity {

    private TextType textType;
    private String mainText;
    private String secondText;

    public SettingsTextEntity(TextType textType, String mainText, String secondText) {
        this.textType = textType;
        this.mainText = mainText;
        this.secondText = secondText;
    }

    public TextType getTextType() {
        return textType;
    }

    public void setTextType(TextType textType) {
        this.textType = textType;
    }

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public String getSecondText() {
        return secondText;
    }

    public void setSecondText(String secondText) {
        this.secondText = secondText;
    }

    public enum TextType{
        PlainText, SubTitle, TextWithSelect, TextWithToggle
    }

}
