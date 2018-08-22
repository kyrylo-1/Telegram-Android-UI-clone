package com.shorka.telegramclone_ui.contact_chat_screen;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.shorka.telegramclone_ui.Config;
import com.shorka.telegramclone_ui.FabHelper;
import com.shorka.telegramclone_ui.R;
import com.shorka.telegramclone_ui.ViewModelFactory;
import com.shorka.telegramclone_ui.adapter.MessageListAdapter;
import com.shorka.telegramclone_ui.db.User;
import com.shorka.telegramclone_ui.db.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyrylo Avramenko on 6/22/2018.
 */
public class ContactChatActivity extends AppCompatActivity {

    //region properties
    private final Context context = ContactChatActivity.this;
    private static final String TAG = "ContactChatActivity";

    private TextView txtChatPersonName, txtLastSeen;
    private ImageButton btnSend;
    private EditText editText;
    private RecyclerView recyclerView;
    private MessageListAdapter adapterRv;
    private FabHelper fabHelper;
    private ContactChatViewModel chatViewModel;
    //    //endregion


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contact_chat);

//        setDefaultMessages();
        setUpUI();
        initEditText();
        long userId = getIntent().getLongExtra(Config.USER_ID_EXTRA, 1);
        observeViewModel(userId);
        Log.d(TAG, "onCreate: String userId: " + userId);
        updateRecipientUI(chatViewModel.getUser(userId));
    }

    @Override
    protected void onStart() {
        super.onStart();
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_convo, menu);
        return true;
    }

    private LinearLayoutManager linearLayoutManager;

    private void setUpUI() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.convo_toolbar);
        final View viewConvoTop = findViewById(R.id.convo_header_view_top);
        txtChatPersonName = viewConvoTop.findViewById(R.id.name);
        txtLastSeen = viewConvoTop.findViewById(R.id.last_seen);

        txtChatPersonName.setTextColor(getResources().getColor(R.color.colorWhite));
        txtLastSeen.setTextColor(getResources().getColor(R.color.colorWhite));

        btnSend = (ImageButton) findViewById(R.id.convo_send_btn);
        enableBtnSend(false);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnSend.setOnClickListener(v -> sendMessages());

        recyclerView = (RecyclerView) findViewById(R.id.test_convo_recycler_view_messages);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapterRv = createRecycleAdapter();
        recyclerView.setAdapter(adapterRv);

        final FloatingActionButton fabScrollDown = (FloatingActionButton) findViewById(R.id.fab_sroll_down);
        fabScrollDown.setVisibility(View.INVISIBLE);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                //Initialize FabHelper object
                if (fabHelper == null && Math.abs(dy) > 1) {
                    fabHelper = new FabHelper(fabScrollDown, 200L);
                    fabScrollDown.setTranslationY(fabHelper.getOffscreenTranslation());
                    Log.d(TAG, "onScrolled: create FabHelper: height: " + fabScrollDown.getHeight());
                }

                if (fabHelper != null) {
                    scrollButton(fabScrollDown, dy);
                }
            }
        });

        fabScrollDown.setOnClickListener(v -> {
            // make scroll down
            Log.d(TAG, "onClick:  fabScrollDown with fab getTranslationY: " + fabScrollDown.getTranslationY());
            fabHelper.postRollFabOutCompletely(fabScrollDown);
            recyclerView.smoothScrollToPosition(adapterRv.getItemCount() - 1);
        });

        //TODO  When user exits from certain chat,remember position of item in recyclerView and save it.
        // When user reopens chat, scroll to saved position
        recyclerView.scrollToPosition(adapterRv.getItemCount() - 1);
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
            updateAdapter(messages);
        });
    }

    private MessageListAdapter createRecycleAdapter() {
        final MessageListAdapter adapter = new MessageListAdapter(context);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);

                Log.d(TAG, "onItemRangeInserted: positionStart: " + positionStart + " itemCount: " + itemCount);

                if (positionStart > 0) {
                    adapter.notifyItemChanged(positionStart - 1);
                }

                int lastVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (positionStart >= adapter.getItemCount() - 1 && lastVisiblePosition == positionStart - 1) {
                    recyclerView.scrollToPosition(positionStart);
                }
            }
        });

        return adapter;
    }

    private void enableBtnSend(boolean doEnable) {

        btnSend.setVisibility(doEnable ? View.VISIBLE : View.GONE);
//        mBtnAttachments.setVisibility(doEnable ? View.GONE : View.VISIBLE);
//        recyclerView.
    }

    private void sendMessages() {

//        listMsgs.add(new Message(editText.getText().toString(), 12));
//        msgListAdapter.notifyItemInserted(listMsgs.size() - 1);
        editText.getText().clear();
    }

    private void scrollButton(FloatingActionButton fab, int dy) {
        Log.d(TAG, "onScrolled: dy: " + dy + "; RollingState: " + fabHelper.getRollingState());

        int lastVisible = linearLayoutManager.findLastCompletelyVisibleItemPosition();
        Log.d(TAG, "onScrolled: lastVisible: " + lastVisible);
        //hide fab when user scrolls to the f
//        if (lastVisible >= listMsgs.size() - 2) {
//            Log.d(TAG, "scrollButton: scroll to FirstCompletelyVisibleItem");
//            if (fabHelper.getRollingState() == RollingFabState.IDLE)
//                fabHelper.postRollFabOutCompletely(fab);
//        }

        //Scroll down to fresh messages. if user scrolls a lot of vertical
//        if (dy > 4 && lastVisible < listMsgs.size() - 3 &&
//                (fabHelper.getRollingState() == RollingFabState.IDLE || fabHelper.getRollingState() == RollingFabState.ROLLED_OUT)) {
//
//            fab.setVisibility(View.VISIBLE);
//            fabHelper.postRollFabInCompletely(fab);
//        }

        //Scroll up to older messages
//        else if (dy < 0 && fabHelper.getRollingState() == RollingFabState.IDLE) {
//            fabHelper.postRollFabOutCompletely(fab);
//        }
    }

    private void updateRecipientUI(User user) {
        txtChatPersonName.setText(user.name);
    }

    private void updateAdapter(List<Message> messages) {
//        List<Message> lists = messages;
//        lists.add(new Message(chatViewModel.getLastMessage(), 12));
        adapterRv.setItems(messages);
    }
}
