package com.shorka.telegramclone_ui.chats_previews_screen;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.shorka.telegramclone_ui.utils.Config;
import com.shorka.telegramclone_ui.DividerCustomPaddingItemDecoration;
import com.shorka.telegramclone_ui.R;
import com.shorka.telegramclone_ui.RecyclerItemClickListener;
import com.shorka.telegramclone_ui.Utils;
import com.shorka.telegramclone_ui.ViewModelFactory;
import com.shorka.telegramclone_ui.contact_chat_screen.ContactChatActivity;
import com.shorka.telegramclone_ui.contacts_screen.ContactsActivity;
import com.shorka.telegramclone_ui.adapter.MessagesGridRecycleViewAdapter;
import com.shorka.telegramclone_ui.db.User;
import com.shorka.telegramclone_ui.entities.MessagePreview;
import com.shorka.telegramclone_ui.settings_screen.SettingsActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatsPreviewActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //region Define global variables
    private static final String TAG = "ChatsPreviewActivity";
    private final Context context = ChatsPreviewActivity.this;

    private RecyclerView recycleView;
    private MessagesGridRecycleViewAdapter adapterRv;
    private TextView txtUserFullname, txtPhoneNumber;
    private MenuItem menuCurrAccount;
    private ChatPreviewViewModel viewModel;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpUI();
        observeViewModel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        updateUserDetail(viewModel.getCacheUser());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }

    private void setUpUI() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //region navigation view set-up
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final Menu menuNav = navigationView.getMenu();
        menuNav.setGroupVisible(R.id.nav_group_accounts, false);
        menuCurrAccount = menuNav.findItem(R.id.nav_curr_account);

        CircleImageView imageView = new CircleImageView(context);
        imageView.setMinimumHeight(62);
        imageView.setMinimumWidth(62);

        imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.profile_default_male));
        menuCurrAccount.setActionView(imageView);

        final View navHeaderView = navigationView.getHeaderView(0);
        ToggleButton toggleBtn = navHeaderView.findViewById(R.id.account_view_icon_button);
        toggleBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                menuNav.setGroupVisible(R.id.nav_group_accounts, isChecked);
            }
        });

        txtUserFullname = navHeaderView.findViewById(R.id.text_user_fullname);
        txtPhoneNumber = navHeaderView.findViewById(R.id.text_phonenumber);
        //endregion

        recycleView = findViewById(R.id.main_recycler_view_messages);
        adapterRv = new MessagesGridRecycleViewAdapter(context);
        recycleView.setAdapter(adapterRv);
        recycleView.setLayoutManager(new LinearLayoutManager(context));

        final DividerCustomPaddingItemDecoration itemCustomDecor = new DividerCustomPaddingItemDecoration(context,
                DividerItemDecoration.VERTICAL,
                Utils.dpToPx(getResources().getDimension(R.dimen.message_image_preview_scale), context) -
                        Utils.dpToPx(9, context)
        );
        recycleView.addItemDecoration(itemCustomDecor);
        recycleView.setNestedScrollingEnabled(false);
        recycleView.addOnItemTouchListener(new RecyclerItemClickListener(context, recycleView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                clickOnRecycleItem(view, position);
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        }));
    }

    private void observeViewModel() {

        ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
        viewModel = ViewModelProviders.of(this, factory).get(ChatPreviewViewModel.class);

        viewModel.getLiveCurrUser().observe(this, user -> {
            Log.d(TAG, "onChanged: update user details: ");
            if (user == null)
                Log.e(TAG, "observeViewModel: user in NULL");

            else {
                viewModel.cacheUser(user);
                updateUserDetail(user);
            }
        });

        viewModel.getAllLiveUsers().observe(this, users -> {
            Log.d(TAG, "observeViewModel: getAllLiveUsers().observe");
            if (users == null) {
                Log.e(TAG, "observeViewModel:getAllLiveUsers is null");
            } else {
                Log.d(TAG, "observeViewModel: getAllLiveUsers() setItems");
                viewModel.setAllUsers(users);
            }
        });


        viewModel.getRecentMessageByChat().observe(this, messages -> {

            if (messages == null) {
                Log.e(TAG, "messages == null");
            } else {
                Log.d(TAG, "observeViewModel: getRecentMessageByChat() setItems. QTY IS: " + messages.size());

                adapterRv.setItems(viewModel.transformToMsgPreviews(messages));
            }

        });

        viewModel.loadPhoneContacts();
//        viewModel.getLivePhoneContacts().observe(this, phoneContacts -> {
//            if(phoneContacts!=null){
//                viewModel.setCachedPhoneContacts(phoneContacts);
//            }
//        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.cancelLoadContacts();;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_search:
                Log.d(TAG, "onOptionsItemSelected: Press action_search");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        switch (item.getItemId()) {

            case R.id.nav_groupchat:
                break;

            case R.id.nav_gallery:
                break;

            case R.id.nav_settings:
                Log.d(TAG, "onNavigationItemSelected: press nav_settings");

                drawer.closeDrawer(GravityCompat.START);
                SettingsActivity.open(context);
                break;

            case R.id.nav_contacts:
                Log.d(TAG, "onNavigationItemSelected: press nav_contacts");
                drawer.closeDrawer(GravityCompat.START);
                ContactsActivity.open(context);
                break;
        }
        return true;
    }

    private void updateUserDetail(@NonNull User user) {

        if (user == null)
            return;
        Log.d(TAG, "updateUserDetail: " + user.firstName);
        txtUserFullname.setText(user.getFullName());
        txtPhoneNumber.setText(user.phoneNumber);
        menuCurrAccount.setTitle(user.firstName);
    }

    private void clickOnRecycleItem(View view, int position) {
        MessagePreview mp = adapterRv.getMessagePreview(position);
        Log.d(TAG, "onItemClick: click on pos: " + position +
                "_  and lastMessage: " + adapterRv.getMessagePreview(position).getLastMessage() +
                " id: " + mp.getId());

        Intent intent = new Intent(context, ContactChatActivity.class);
        intent.putExtra(Config.USER_ID_EXTRA, mp.getId());
        startActivity(intent);
    }
}
