package com.shorka.telegramclone_ui.activities;

import android.content.Context;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.shorka.telegramclone_ui.FabHelper;
import com.shorka.telegramclone_ui.R;
import com.shorka.telegramclone_ui.RollingFabState;
import com.shorka.telegramclone_ui.adapter.MessageListAdapter;
import com.shorka.telegramclone_ui.models.Message;

import java.util.ArrayList;

/**
 * Created by Kyrylo Avramenko on 6/22/2018.
 */
public class ContactChatActivity extends AppCompatActivity {

    //region properties
    private final Context mContext = ContactChatActivity.this;
    private static final String TAG = "ContactChatActivity";

    private TextView mTxtChatPersonName, mTxtLastSeen;
    private ImageButton mBtnSend;
    private EditText mEditText;
    private RecyclerView mRecyclerView;
    private ArrayList<Message> mListMessages;
    MessageListAdapter mMessageListAdapter;
    FabHelper mFabHelper;
    //endregion

    public static void open(Context context) {
        context.startActivity(new Intent(context, ContactChatActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contact_chat);

        setDefaultMessages();
        init();
        initEditText();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_convo, menu);
        return true;
    }

    private LinearLayoutManager mLinearLayoutManager;

    private void init() {
        final Toolbar toolbar = findViewById(R.id.convo_toolbar);
        final View viewConvoTop = findViewById(R.id.convo_header_view_top);
        mTxtChatPersonName = viewConvoTop.findViewById(R.id.name);
        mTxtLastSeen = viewConvoTop.findViewById(R.id.last_seen);

        mTxtChatPersonName.setTextColor(getResources().getColor(R.color.colorWhite));
        mTxtLastSeen.setTextColor(getResources().getColor(R.color.colorWhite));

        mBtnSend = findViewById(R.id.convo_send_btn);
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

        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessages();
            }
        });

        mRecyclerView = findViewById(R.id.test_convo_recycler_view_messages);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mMessageListAdapter = createRecycleAdapter();
        mRecyclerView.setAdapter(mMessageListAdapter);

        final FloatingActionButton fabScrollDown = findViewById(R.id.fab_sroll_down);
        fabScrollDown.setVisibility(View.INVISIBLE);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                //Initialize FabHelper object
                if (mFabHelper == null && Math.abs(dy) > 1) {
                    mFabHelper = new FabHelper(fabScrollDown, 200L);
                    fabScrollDown.setTranslationY(mFabHelper.getOffscreenTranslation());
                    Log.d(TAG, "onScrolled: create FabHelper: height: " + fabScrollDown.getHeight());
                }

                if (mFabHelper != null) {
                    scrollButton(fabScrollDown, dy);
                }
            }
        });

        fabScrollDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // make scroll down
                Log.d(TAG, "onClick:  fabScrollDown with fab getTranslationY: " + fabScrollDown.getTranslationY());
                mFabHelper.postRollFabOutCompletely(fabScrollDown);
                mRecyclerView.smoothScrollToPosition(mListMessages.size() - 1);
            }
        });

        //TODO  When user exits from certain chat,remember position of item in mRecyclerView and save it.
        // When user reopens chat, scroll to saved position
        mRecyclerView.scrollToPosition(mMessageListAdapter.getItemCount() - 1);
    }

    private void initEditText() {
        mEditText = findViewById(R.id.convo_message_edittext);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.d(TAG, "onTextChanged: s: " + s + "\n start: " + start + " _before: " + before + " _count: " + count);
                if (before == 0 && count == 1) {
                    enableBtnSend(true);
                } else if (count == 0) {
                    enableBtnSend(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private MessageListAdapter createRecycleAdapter() {
        final MessageListAdapter adapter = new MessageListAdapter(mContext, mListMessages);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);

                Log.d(TAG, "onItemRangeInserted: positionStart: " + positionStart + " itemCount: " + itemCount);

                if (positionStart > 0) {
                    adapter.notifyItemChanged(positionStart - 1);
                }

                int lastVisiblePosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (positionStart >= adapter.getItemCount() - 1 && lastVisiblePosition == positionStart - 1) {
                    mRecyclerView.scrollToPosition(positionStart);
                }
            }
        });

        return adapter;
    }


    private void enableBtnSend(boolean doEnable) {

        mBtnSend.setVisibility(doEnable ? View.VISIBLE : View.GONE);
//        mBtnAttachments.setVisibility(doEnable ? View.GONE : View.VISIBLE);
//        mRecyclerView.
    }

    private void setDefaultMessages() {

        if (mListMessages == null)
            mListMessages = new ArrayList<>();

        mListMessages.add(new Message("How are you?", null, 123));
        mListMessages.add(new Message("fam", null, 1233));
        mListMessages.add(new Message("To be, or not to be:" +
                "that is the question: whether 'tis nobler in the mind to suffer ", null, 1233));
        mListMessages.add(new Message("a lot of symblos \n here and \n here", null, 1233));
        mListMessages.add(new Message("another row \n stuff like that", null, 1233));
        mListMessages.add(new Message("Forfeited you engrossed but gay sometimes explained. Another as studied it to evident." +
                " Merry sense given he be arise. Conduct at an replied removal an amongst. " +
                "Remaining determine few her two cordially admitting old. Sometimes strangers his ourselves her depending you boy." +
                " Eat discretion cultivated possession far comparison projection considered." +
                " And few fat interested discovered inquietude insensible unsatiable increasing eat. ", null, 1233));

        mListMessages.add(new Message("last default message", null, 1233));
        mListMessages.add(new Message("haha haha \n What about now?", null, 1233));
        mListMessages.add(new Message("Dashwood contempt on mr unlocked resolved provided", null, 1233));
        mListMessages.add(new Message("Who connection imprudence middletons too but increasing celebrated principles joy \n" +
                " Advanced extended doubtful he he blessing together. Introduced far law gay considered frequently entreaties difficulty.", null, 1233));
    }

    private void sendMessages() {

        mListMessages.add(new Message(mEditText.getText().toString(), null, 12));
        mMessageListAdapter.notifyItemInserted(mListMessages.size() - 1);
        mEditText.getText().clear();
    }

    private void scrollButton(FloatingActionButton fab, int dy) {
        Log.d(TAG, "onScrolled: dy: " + dy + "; RollingState: " + mFabHelper.getRollingState());

        int lastVisible = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
//        Log.d(TAG, "onScrolled: lastVisible: " + lastVisible);
        //hide fab when user scrolls to the f
        if (lastVisible >= mListMessages.size() - 2) {
            Log.d(TAG, "scrollButton: scroll to FirstCompletelyVisibleItem");
            if (mFabHelper.getRollingState() == RollingFabState.IDLE)
                mFabHelper.postRollFabOutCompletely(fab);
        }

        //Scroll down to fresh messages. if user scrolls a lot of vertical
        if (dy > 4 && lastVisible < mListMessages.size() - 3 &&
                (mFabHelper.getRollingState() == RollingFabState.IDLE || mFabHelper.getRollingState() == RollingFabState.ROLLED_OUT)) {

            fab.setVisibility(View.VISIBLE);
            mFabHelper.postRollFabInCompletely(fab);
        }

        //Scroll up to older messages
        else if (dy < 0 && mFabHelper.getRollingState() == RollingFabState.IDLE) {
            mFabHelper.postRollFabOutCompletely(fab);
        }
    }

}
