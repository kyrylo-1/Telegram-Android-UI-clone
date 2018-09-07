package com.shorka.telegramclone_ui;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by Kyrylo Avramenko on 6/12/2018.
 */
public class SettingsTextEntity {

    @IdRes
    private final int idRes;
    private TextType textType;
    private String mainText;
    private String description;

    public SettingsTextEntity(TextType textType, String mainText, String description) {
        this.idRes = View.generateViewId();
        this.textType = textType;
        this.mainText = mainText;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdRes() {
        return idRes;
    }

    public enum TextType {
        PlainText, SubTitle, TextWithSelect, TextWithToggle
    }

}
