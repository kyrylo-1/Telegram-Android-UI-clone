package com.shorka.telegramclone_ui.contact_chat_screen;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.WindowCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.shorka.telegramclone_ui.R;
import com.shorka.telegramclone_ui.ViewModelFactory;
import com.shorka.telegramclone_ui.db.User;
import com.shorka.telegramclone_ui.utils.Config;

import java.util.Objects;

/**
 * Created by Kyrylo Avramenko on 6/22/2018.
 */
//TODO [Polishing] 1) make adjecent sent/received messages closer to each other than to opposite type of messages. Padding can be changed in style file -> 'LayoutOfMessage';
//TODO [Polishing] 2) Make bigger paddingTop value in top message. Its needed to have space from toolbar


public class ContactChatActivity extends AppCompatActivity {

    //region properties
    private final Context context = ContactChatActivity.this;
    private static final String TAG = "ContactChatActivity";

    private TextView txtChatPersonName, txtLastSeen;
    private ImageButton btnSend;
    private EditText editText;

    private ContactChatViewModel chatViewModel;
    private long recipientUserId;
    private ContactChatFragment chatFragment;
    //endregion

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(WindowCompat.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_contact_chat);

        setUpToolBar();
        setUpUI();

        chatFragment = new ContactChatFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_content, chatFragment);
        transaction.commit();

        initEditText();

        recipientUserId = getIntent().getLongExtra(Config.USER_ID_EXTRA, 1);
        observeViewModel(recipientUserId);
        Log.d(TAG, "onCreate: String recipientUserId: " + recipientUserId);
        updateRecipientUI(chatViewModel.getUser(recipientUserId));
    }

    @Override
    protected void onStart() {
        super.onStart();
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_convo, menu);
        return true;
    }

    private void setUpToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.convo_toolbar);
        setSupportActionBar(toolbar);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar == null) throw new AssertionError();

        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setDisplayShowTitleEnabled(false);
        supportActionBar.setDisplayShowCustomEnabled(true);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void setUpUI() {

        final View viewConvoTop = findViewById(R.id.convo_header_view_top);
        txtChatPersonName = viewConvoTop.findViewById(R.id.name);
        txtLastSeen = viewConvoTop.findViewById(R.id.last_seen);

        txtChatPersonName.setTextColor(getResources().getColor(R.color.colorWhite));
        txtLastSeen.setTextColor(getResources().getColor(R.color.colorWhite));

        btnSend = (ImageButton) findViewById(R.id.convo_send_btn);
        enableBtnSend(false);

        btnSend.setOnClickListener(v -> sendMessages());
    }

    private void initEditText() {
        editText = (EditText) findViewById(R.id.convo_message_edittext);
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.d(TAG, "onTextChanged: s: " + s + "\n start: " + start + " _before: " + before + " _count: " + count);
            }

            @Override
            public void afterTextChanged(Editable s) {
//                Log.d(TAG, "afterTextChanged: " + editText.getText().toString());
                enableBtnSend(!s.toString().isEmpty());
            }
        });
    }

    private void observeViewModel(long userId) {

        ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
        chatViewModel = ViewModelProviders.of(this, factory).get(ContactChatViewModel.class);
        chatViewModel.getListMessages(userId).observe(this, messages -> {
            Log.d(TAG, "observeViewModel: obsert list of messages with size: " + Objects.requireNonNull(messages).size());

            if (chatFragment.getAdapterRv() != null) {
                chatFragment.getAdapterRv().setItemsMessages(messages);
                chatFragment.getRecyclerView().scrollToPosition(chatFragment.getAdapterRv().getItemCount() - 1);
            }
        });
    }

    private void enableBtnSend(boolean doEnable) {
        btnSend.setVisibility(doEnable ? View.VISIBLE : View.GONE);
    }

    private void sendMessages() {
        chatViewModel.sendMessage(recipientUserId, editText.getText().toString());
        editText.getText().clear();
    }

    private void updateRecipientUI(User user) {
        txtChatPersonName.setText(user.firstName);
    }
}
