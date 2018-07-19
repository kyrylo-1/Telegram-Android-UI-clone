package com.shorka.telegramclone_ui.activities;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.shorka.telegramclone_ui.DividerCustomPaddingItemDecoration;
import com.shorka.telegramclone_ui.FabScroll;
import com.shorka.telegramclone_ui.R;
import com.shorka.telegramclone_ui.RecyclerItemClickListener;
import com.shorka.telegramclone_ui.Utils;
import com.shorka.telegramclone_ui.adapter.MessagesGridRecycleViewAdapter;
import com.shorka.telegramclone_ui.entities.MessagePreviewEntity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private final Context mContext = MainActivity.this;
    private RecyclerView mRecycleView;
    private RecyclerView.Adapter mAdapter;
    private FloatingActionButton mFab;
    FabScroll mFabScroll;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        mFab = (FloatingActionButton) findViewById(R.id.main_write_fab);
//        mFab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final Menu menuNav = navigationView.getMenu();
        menuNav.setGroupVisible(R.id.nav_group_accounts, false);
        MenuItem iconCurrAccount = menuNav.findItem(R.id.nav_curr_account);

        //TODO
        CircleImageView imageView = new CircleImageView(mContext);
        imageView.setMinimumHeight(62);
        imageView.setMinimumWidth(62);
//        imageView.setForegroundGravity(Gravity.RIGHT);
        imageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.profile_default_male));
        iconCurrAccount.setActionView(imageView);
//        iconCurrAccount.setIcon(ContextCompat.getDrawable(mContext, R.drawable.profile_default_male));

        ToggleButton toggleBtn = navigationView.getHeaderView(0).findViewById(R.id.account_view_icon_button);
        toggleBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                menuNav.setGroupVisible(R.id.nav_group_accounts, isChecked);

            }
        });
        initContentMain();

//        AppDatabase db = App.getInstance().getDatabase();
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
        // Inflate the menu; this adds items to the action bar if it is present.
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
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
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
                SettingsActivity.open(mContext);
                break;

            case R.id.nav_contacts:
                Log.d(TAG, "onNavigationItemSelected: press nav_contacts");
                drawer.closeDrawer(GravityCompat.START);
                ContactsActivity.open(mContext);
                break;
        }


        return true;
    }


    private void initContentMain() {

        mRecycleView = findViewById(R.id.main_recycler_view_messages);
        mRecycleView.setAdapter(new MessagesGridRecycleViewAdapter(mContext, getInfoArrayList()));
        mRecycleView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycleView.setNestedScrollingEnabled(false);


        DividerCustomPaddingItemDecoration itemCustomDecor = new DividerCustomPaddingItemDecoration(mContext,
                DividerItemDecoration.VERTICAL,
                Utils.dpToPx(getResources().getDimension(R.dimen.message_image_preview_scale), mContext) -
                        Utils.dpToPx(9, mContext)
        );
        mRecycleView.addItemDecoration(itemCustomDecor);

        mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                if(mFabScroll == null)
//                    mFabScroll = new FabScroll(mFab);
//
//                mFabScroll.scroll(dy);
            }
        });

        mRecycleView.addOnItemTouchListener(new RecyclerItemClickListener(mContext, mRecycleView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d(TAG, "onItemClick: click on pos: " + position + "_  " + view.getId());
                ContactChatActivity.open(mContext);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));


    }

    private ArrayList<MessagePreviewEntity> getInfoArrayList() {
        ArrayList<MessagePreviewEntity> items = new ArrayList<>();

        MessagePreviewEntity entity1 = new MessagePreviewEntity.MessagePreviewBuilder()
                .withContactName("Bob")
                .withLastMessage("Are you even lifting, bro?")
                .withIsPinned(false)
                .withIsReaded(true)
                .withDate("12:43")
                .withImageResId(R.drawable.kochek_withback)
                .buildMesagePreview();

        items.add(entity1);

        MessagePreviewEntity entity2 = new MessagePreviewEntity.MessagePreviewBuilder()
                .withContactName("Alex")
                .withLastMessage("Okay")
                .withIsPinned(true)
                .withIsReaded(true)
                .withDate("8:00")
                .withImageResId(R.drawable.avatar4)
                .buildMesagePreview();

        items.add(entity2);

        MessagePreviewEntity entity3 = new MessagePreviewEntity.MessagePreviewBuilder()
                .withContactName("Ivan")
                .withLastMessage("see you there")
                .withIsPinned(false)
                .withIsReaded(true)
                .withDate("14:25")
                .withImageResId(R.drawable.avatar2)
                .buildMesagePreview();


        items.add(entity3);

        MessagePreviewEntity entity4 = new MessagePreviewEntity.MessagePreviewBuilder()
                .withContactName("Pavel Durov")
                .withLastMessage("Do you know where is my keys?")
                .withIsPinned(false)
                .withIsReaded(false)
                .withDate("18:15")
                .withImageResId(R.drawable.avatar_durov)
                .buildMesagePreview();
        items.add(entity4);

        MessagePreviewEntity entity5 = new MessagePreviewEntity.MessagePreviewBuilder()
                .withContactName("Lisa S")
                .withLastMessage("sup")
                .withIsPinned(false)
                .withIsReaded(false)
                .withDate("23:05")
                .withImageResId(R.drawable.avatar_lisa)
                .buildMesagePreview();
        items.add(entity5);

        MessagePreviewEntity entity6 = new MessagePreviewEntity.MessagePreviewBuilder()
                .withContactName("Mr. Heisenber")
                .withLastMessage("Dont skip my classes anymore")
                .withIsPinned(false)
                .withIsReaded(false)
                .withDate("13:05")
                .withImageResId(R.drawable.avatar_heisenberg)
                .buildMesagePreview();
        items.add(entity6);

        MessagePreviewEntity entity7 = new MessagePreviewEntity.MessagePreviewBuilder()
                .withContactName("Jack Uni")
                .withLastMessage("I need to think about this more carefully. See you")
                .withIsPinned(false)
                .withIsReaded(false)
                .withDate("13:05")
                .withImageResId(R.drawable.profile_default_male)
                .buildMesagePreview();
        items.add(entity7);


        MessagePreviewEntity entity8 = new MessagePreviewEntity.MessagePreviewBuilder()
                .withContactName("Anna Smith")
                .withLastMessage("Really?")
                .withIsPinned(false)
                .withIsReaded(false)
                .withDate("Jun 2")
                .withImageResId(R.drawable.avatar3_female)
                .buildMesagePreview();
        items.add(entity8);

        MessagePreviewEntity entity9 = new MessagePreviewEntity.MessagePreviewBuilder()
                .withContactName("Tranue Clark")
                .withLastMessage("bye")
                .withIsPinned(false)
                .withIsReaded(false)
                .withDate("Jun 2")
                .withImageResId(R.drawable.profile_default_male)
                .buildMesagePreview();
        items.add(entity9);

        MessagePreviewEntity entity10 = new MessagePreviewEntity.MessagePreviewBuilder()
                .withContactName("User3")
                .withLastMessage("hahaha \n it happens")
                .withIsPinned(false)
                .withIsReaded(false)
                .withDate("Jun 2")
                .withImageResId(R.drawable.profile_default_male)
                .buildMesagePreview();
        items.add(entity10);

        MessagePreviewEntity entity11 = new MessagePreviewEntity.MessagePreviewBuilder()
                .withContactName("Jack Robinson")
                .withLastMessage("See ya")
                .withIsPinned(false)
                .withIsReaded(false)
                .withDate("Jun 2")
                .withImageResId(R.drawable.profile_default_male)
                .buildMesagePreview();
        items.add(entity11);

        return items;
    }
}
