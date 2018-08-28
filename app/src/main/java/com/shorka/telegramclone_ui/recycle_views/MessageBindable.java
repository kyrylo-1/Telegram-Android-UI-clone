package com.shorka.telegramclone_ui.recycle_views;

import com.shorka.telegramclone_ui.db.Converters;

import java.util.Date;

/**
 * Created by Kyrylo Avramenko on 8/28/2018.
 */
public interface MessageBindable {

    void bind(String messageText, Date date);

}
