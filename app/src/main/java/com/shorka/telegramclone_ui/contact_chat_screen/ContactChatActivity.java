package com.shorka.telegramclone_ui.contact_chat_screen;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.shorka.telegramclone_ui.R;
import com.shorka.telegramclone_ui.RecyclerItemClickListener;
import com.shorka.telegramclone_ui.ViewModelFactory;
import com.shorka.telegramclone_ui.adapter.MessageListAdapter;
import com.shorka.telegramclone_ui.db.Message;
import com.shorka.telegramclone_ui.db.User;
import com.shorka.telegramclone_ui.utils.Config;
import com.shorka.telegramclone_ui.utils.FabHelper;

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
    private RecyclerView recyclerView;
    private MessageListAdapter adapterRv;
    private FabHelper fabHelper;
    private ContactChatViewModel chatViewModel;
    private ActionMode actionMode;
    private long recipientUserId;
    private Toolbar toolbar;
    //endregion

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contact_chat);

        setUpToolBar();
        setUpUI();
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

    private LinearLayoutManager linearLayoutManager;

    private void setUpToolBar() {
        toolbar = (Toolbar) findViewById(R.id.convo_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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

        recyclerView = (RecyclerView) findViewById(R.id.test_convo_recycler_view_messages);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

//        adapterRv = createRecycleAdapter();
        adapterRv = new MessageListAdapter();
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

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(context, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                clickOnMessage(adapterRv.getItem(position));
            }

            @Override
            public void onItemLongClick(View view, int position) {
                longClickOnMessage(adapterRv.getItem(position));
            }
        }));
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
            adapterRv.setItemsMessages(messages);
            recyclerView.scrollToPosition(adapterRv.getItemCount() - 1);
        });
    }

    private void enableBtnSend(boolean doEnable) {
        btnSend.setVisibility(doEnable ? View.VISIBLE : View.GONE);
    }

    private void sendMessages() {
        chatViewModel.sendMessage(recipientUserId, editText.getText().toString());
        editText.getText().clear();
    }

    private void scrollButton(FloatingActionButton fab, int dy) {
//        Log.d(TAG, "onScrolled: dy: " + dy + "; RollingState: " + fabHelper.getRollingState());

        int lastVisible = linearLayoutManager.findLastCompletelyVisibleItemPosition();
//        Log.d(TAG, "onScrolled: lastVisible: " + lastVisible);
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
        txtChatPersonName.setText(user.firstName);
    }

    private void clickOnMessage(@NonNull Message message) {
        if (actionMode == null)
            return;

        Log.d(TAG, "clickOnMessage: ");

        toggleMessage(message);
    }

    private void longClickOnMessage(@NonNull Message message) {
        Log.d(TAG, "longClickOnMessage: ");

        //create Action mode only on long click
        if (actionMode == null) {
            actionMode = startActionMode(callback);
        }
        toggleMessage(message);
    }

    private void toggleMessage(@NonNull Message message) {
        adapterRv.toggleSelection(message);
        if (adapterRv.getSizeOfSelectedItems() == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(adapterRv.getSizeOfSelectedItems()));
        }
    }

    private ActionMode.Callback callback = new ActionMode.Callback() {

        private int statusBarColor;
        private MenuInflater inflater;

        public boolean onCreateActionMode(ActionMode mode, Menu menu) {

            inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_message_context, menu);

            mode.setTitle(String.valueOf(adapterRv.getSizeOfSelectedItems()));
            Window window = getWindow();
            statusBarColor = window.getStatusBarColor();
            window.setStatusBarColor(getResources().getColor(R.color.action_mode_status_bar));

            return true;
        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            Log.d(TAG, "onActionItemClicked " + item.getTitle());

            switch (item.getItemId()) {

                case R.id.menu_msg_copy:
                    chatViewModel.handleCopyMessage(adapterRv.getSelectedItems());
                    actionMode.finish();
                    return true;

                case R.id.menu_msg_delete:
                    actionMode.finish();
                    return true;

                default:
                    return false;
            }
        }

        public void onDestroyActionMode(ActionMode mode) {
            Log.d(TAG, "destroy");

            adapterRv.clearSelectedItems();
            actionMode = null;
        }

    };
}
