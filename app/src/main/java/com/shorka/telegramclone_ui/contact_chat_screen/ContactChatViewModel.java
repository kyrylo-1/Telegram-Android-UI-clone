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

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

        for (Message m : messages) {
            String text = m.text;
            if(!TextUtils.isEmpty(text)) textBuilder.append(text).append('\n');
        }

        //because we dont need last 'new line' character
        if (textBuilder.length() > 0 && textBuilder.charAt(textBuilder.length() - 1) == '\n')
            textBuilder.deleteCharAt(textBuilder.length() - 1);

        String result = textBuilder.toString();

        ClipboardManager clipboard = (ClipboardManager) getApplication().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("simple_text", result);

        if (!TextUtils.isEmpty(result)) {
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getApplication(), R.string.copy_to_clipboard, Toast.LENGTH_SHORT).show();
        }
    }
}
