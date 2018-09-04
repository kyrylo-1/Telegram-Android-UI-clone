package com.shorka.telegramclone_ui.contact_chat_screen;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.shorka.telegramclone_ui.R;
import com.shorka.telegramclone_ui.db.LocalDatabase;
import com.shorka.telegramclone_ui.db.Message;
import com.shorka.telegramclone_ui.db.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Kyrylo Avramenko on 8/20/2018.
 */
public class ContactChatViewModel extends AndroidViewModel {

    private static final String TAG = "ContactChatViewModel";

    private final CompositeDisposable disposables = new CompositeDisposable();
    private final LocalDatabase localDb;

    public ContactChatViewModel(@NonNull Application application, LocalDatabase localDb) {
        super(application);
        Log.d(TAG, "ContactChatViewModel: init");
        this.localDb = localDb;
    }

    public User getUser(long id) {
        return localDb.getUserRepo().getCachedUserById(id);
    }

    public LiveData<List<Message>> getListMessages(long recipientId) {
        return localDb.getMessageRepo().getMessagesyRecipientId(recipientId);
    }

    @SuppressLint("CheckResult")
    public void sendMessage(long recipientId, String text) {
        Log.d(TAG, "sendMessage with text: " + text);
        Message m = new Message(0);
        m.recipientId = recipientId;
        m.text = text;
        m.messageType = Message.SENT;
        m.setDate(Calendar.getInstance().getTime());

        Consumer<Message> consumer = localDb.getMessageRepo()::insertMessage;
        Flowable.just(m)
                .subscribeOn(Schedulers.io())
                .subscribe(consumer, Throwable::printStackTrace);
    }

    public void handleCopyMessage(final Set<Message> messages) {

        final StringBuilder textBuilder = new StringBuilder();

        final String nameOfCurrUser = localDb.getUserRepo().getCurrUser().getFullName();
        String nameOfRecipient = null;
        Message prevMsg = null;
        String nameOfSender = null;

        //Sort messages by date
        List<Message> listMsgs = new ArrayList<>(messages);
        Collections.sort(listMsgs, (o1, o2) -> Long.compare(o1.date, o2.date));

        for (Message m : listMsgs) {

            if (prevMsg == null || prevMsg.messageType != m.messageType || nameOfSender == null) {
                nameOfSender = getNameOfSender(m.messageType, nameOfCurrUser, nameOfRecipient, m.recipientId);
                if (TextUtils.isEmpty(nameOfRecipient))
                    nameOfRecipient = nameOfSender;

                textBuilder.append(nameOfSender).append(":").append('\n');
            }

            String text = m.text;
            if (!TextUtils.isEmpty(text))
                textBuilder.append(text).append('\n').append('\n');

            prevMsg = m;
        }

        //because we dont need last 'new line' character
        //Do it in cycle, because we have 2 'new line'
        for (int i = 0; i < 2; i++) {
            if (textBuilder.length() > 0 && textBuilder.charAt(textBuilder.length() - 1) == '\n')
                textBuilder.deleteCharAt(textBuilder.length() - 1);
        }


        String result = textBuilder.toString();

        ClipboardManager clipboard = (ClipboardManager) getApplication().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("simple_text", result);

        if (!TextUtils.isEmpty(result) && clipboard != null) {
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getApplication(), R.string.copy_to_clipboard, Toast.LENGTH_SHORT).show();
        }
    }

    private String getNameOfSender(int messageType, String nameOfCurrUser, String nameOfRecipient,
                                   long recipientId) {

        if (messageType == Message.SENT)
            return nameOfCurrUser;

        if (TextUtils.isEmpty(nameOfRecipient))
            nameOfRecipient = localDb.getUserRepo().getCachedUserById(recipientId).getFullName();

        return nameOfRecipient;
    }


}
