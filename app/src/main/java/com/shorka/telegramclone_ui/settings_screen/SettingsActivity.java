package com.shorka.telegramclone_ui.settings_screen;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.shorka.telegramclone_ui.Injection;
import com.shorka.telegramclone_ui.adapter.ComplexRecyclerViewAdapter;
import com.shorka.telegramclone_ui.DividerCustomItemDecoration;
import com.shorka.telegramclone_ui.HeaderView;
import com.shorka.telegramclone_ui.ProfilePicDialogFragment;
import com.shorka.telegramclone_ui.R;
import com.shorka.telegramclone_ui.db.User;
import com.shorka.telegramclone_ui.entities.SettingsTextEntity;

import java.util.ArrayList;


/**
 * Created by Kyrylo Avramenko on 6/11/2018.
 */
public class SettingsActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener
        , SettingsContract.View {

    private static final String TAG = "SettingsActivity";
    private final Context mContext = SettingsActivity.this;
    private HeaderView mToolbarHeaderView, mFloatHeaderView;
    private SettingsContract.UserActionsListener settActionsListener;
    private DividerCustomItemDecoration itemCustomDecor;
    private boolean isHideToolbarView = false;

    public static void open(Context context) {
        context.startActivity(new Intent(context, SettingsActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Log.d(TAG, "onCreate: ");
        setupUI();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;

        if (percentage == 1f && isHideToolbarView) {
            mToolbarHeaderView.setVisibility(View.VISIBLE);
            CoordinatorLayout.LayoutParams lpFloat = (CoordinatorLayout.LayoutParams) mFloatHeaderView.getLayoutParams();

            isHideToolbarView = !isHideToolbarView;

        } else if (percentage < 1f && !isHideToolbarView) {
            mToolbarHeaderView.setVisibility(View.GONE);
            isHideToolbarView = !isHideToolbarView;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        final int id = item.getItemId();
        if (id == R.id.action_settings_editname) {

            Log.d(TAG, "onOptionsItemSelected: action_settings_editname");
            ChangeNameActivity.open(this);

        } else if (id == R.id.action_settings_log_out) {

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);
        return true;
    }

    private void setupUI() {

        mToolbarHeaderView = (HeaderView) findViewById(R.id.header_view_top);
        mFloatHeaderView = (HeaderView) findViewById(R.id.float_header_view);

        mToolbarHeaderView.setTxtViewName((TextView) mToolbarHeaderView.findViewById(R.id.name));
        mToolbarHeaderView.setTxtViewLastSeen((TextView) mToolbarHeaderView.findViewById(R.id.last_seen));

        mFloatHeaderView.setTxtViewName((TextView) mFloatHeaderView.findViewById(R.id.name_float));
        mFloatHeaderView.setTxtViewLastSeen((TextView) mFloatHeaderView.findViewById(R.id.last_seen_float));

        final AppBarLayout mAppBarLayout = (AppBarLayout) findViewById(R.id.appbar_test);
        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mAppBarLayout.addOnOffsetChangedListener(this);

        mToolbar.setNavigationOnClickListener(v -> onBackPressed());

        FloatingActionButton fabCam = (FloatingActionButton) findViewById(R.id.settings_fab);
        fabCam.setOnClickListener(v -> {
            Log.d(TAG, "onClick: fabCam");
            showDialog();
        });

        setHeaderText(R.id.snippet_txt_info, "Info");
        setHeaderText(R.id.snippet_txt_settings, "Settings");
        setHeaderText(R.id.snippet_messages, "Messages");
        setHeaderText(R.id.snippet_txt_support, "Support");

        itemCustomDecor = new DividerCustomItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        DividerItemDecoration itemDefaultDecor = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
    }

    private TextView setHeaderText(@IdRes int idSnippet, String text) {

        final View view = findViewById(idSnippet);
        TextView txtView = view.findViewById(R.id.txt_settings_header);
        if (txtView != null) {
            txtView.setText(text);
//            Log.d(TAG, "setHeaderText: txtView NOT null");
        } else {
            Log.e(TAG, "setHeaderText: txtView is NULL for" + idSnippet);
        }
        return txtView;
    }

    private void setRecycleView(@IdRes int idRecycleVoew, ArrayList<Object> list, RecyclerView.ItemDecoration itemDecor) {
        RecyclerView recyclerView = (RecyclerView) findViewById(idRecycleVoew);
        recyclerView.setAdapter(new ComplexRecyclerViewAdapter(list));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(itemDecor);
    }


    final int CAMERA_PIC_REQUEST = 1337;
    final int GALLERY_REQUEST = 1338;

    private void showDialog() {
        final ProfilePicDialogFragment dialog = new ProfilePicDialogFragment();
        dialog.show(getFragmentManager(), "dialog");
        dialog.setCamClickOptions(new ProfilePicDialogFragment.OnCameraClickOptions() {
            @Override
            public void onFromCamClicked() {
                Log.d(TAG, "onFromCamClicked: ");
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent, CAMERA_PIC_REQUEST);
            }

            @Override
            public void onFromGallery() {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            }

            @Override
            public void onDeletePhoto() {

            }
        });
    }

    private ArrayList<Object> getSettingsArrayList() {
        ArrayList<Object> items = new ArrayList<>();
        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.PlainText, "Notifications and Sounds", ""));
        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.PlainText, "Privacy and Security", ""));
        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.PlainText, "Data and Storage", ""));
        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.PlainText, "Chat BackGround", ""));

        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.TextWithSelect, "Theme", "Default"));
        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.TextWithSelect, "Language", "English"));
        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.TextWithToggle, "Enable Animations", ""));

        return items;
    }

    private ArrayList<Object> getMessagesArrayList() {
        ArrayList<Object> items = new ArrayList<>();
        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.TextWithToggle, "In-App Browser", ""));
        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.TextWithSelect, "Stickers", "18"));
        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.TextWithSelect, "Message Text Size", "16"));

        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.TextWithToggle, "Raise to Speak", ""));
        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.TextWithToggle, "Send by Enter", ""));
        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.TextWithToggle, "Autoplay Gifs", ""));
        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.TextWithToggle, "Save to gallery", ""));

        return items;
    }

    private ArrayList<Object> getSupporArrayList() {
        ArrayList<Object> items = new ArrayList<>();
        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.PlainText, "Ask a question", ""));
        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.PlainText, "Telegram FAQ", ""));
        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.PlainText, "Private Policy", ""));

        return items;
    }
    //endregion

    @Override
    public void showUserInfo(User user) {
        final String name = user.name;
        final String date = "Dec 14th";
        mFloatHeaderView.bindTo(name, date);
        mToolbarHeaderView.bindTo(name, date);

        ArrayList<Object> items = new ArrayList<>();
        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.SubTitle, user.phoneNumber, "Phone"));
        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.SubTitle, user.username, "Username"));
        items.add(new SettingsTextEntity(SettingsTextEntity.TextType.SubTitle, user.bio, "Bio"));


        setRecycleView(R.id.recycler_view_info, items, itemCustomDecor);
        setRecycleView(R.id.recycler_view_settings, getSettingsArrayList(), itemCustomDecor);
        setRecycleView(R.id.main_recycler_view_messages, getMessagesArrayList(), itemCustomDecor);
//        setRecycleView(R.id.recycler_view_support, getSupporArrayList(), itemDefaultDecor);
    }

}
