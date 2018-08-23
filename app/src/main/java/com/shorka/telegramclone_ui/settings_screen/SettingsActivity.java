package com.shorka.telegramclone_ui.settings_screen;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
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

import com.shorka.telegramclone_ui.ViewModelFactory;
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
public class SettingsActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private static final String TAG = "SettingsActivity";
    private final Context context = SettingsActivity.this;
    private HeaderView toolbarHeaderView, floatHeaderView;
    private DividerCustomItemDecoration itemCustomDecor;
    private DividerItemDecoration itemDefaultDecor;
    private boolean isHideToolbarView = false;
    private SettingsViewModel settViewModel;

    public static void open(Context context) {
        context.startActivity(new Intent(context, SettingsActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Log.d(TAG, "onCreate: ");
        setupUI();
        setViewModel();
        showSettingsRecycleView(settViewModel.getCachedUser());
        initUserInfo(settViewModel.getCachedUser());
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUserHeader(settViewModel.getCachedUser());
        updateListOfUserInfo(settViewModel.getCachedUser());
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;

        if (percentage == 1f && isHideToolbarView) {
            toolbarHeaderView.setVisibility(View.VISIBLE);
            CoordinatorLayout.LayoutParams lpFloat = (CoordinatorLayout.LayoutParams) floatHeaderView.getLayoutParams();

            isHideToolbarView = !isHideToolbarView;

        } else if (percentage < 1f && !isHideToolbarView) {
            toolbarHeaderView.setVisibility(View.GONE);
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

        toolbarHeaderView = (HeaderView) findViewById(R.id.header_view_top);
        floatHeaderView = (HeaderView) findViewById(R.id.float_header_view);

        toolbarHeaderView.setTxtViewName((TextView) toolbarHeaderView.findViewById(R.id.name));
        toolbarHeaderView.setTxtViewLastSeen((TextView) toolbarHeaderView.findViewById(R.id.last_seen));

        floatHeaderView.setTxtViewName((TextView) floatHeaderView.findViewById(R.id.name_float));
        floatHeaderView.setTxtViewLastSeen((TextView) floatHeaderView.findViewById(R.id.last_seen_float));

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

        itemCustomDecor = new DividerCustomItemDecoration(context, DividerItemDecoration.VERTICAL);
        itemDefaultDecor = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
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


    private final int CAMERA_PIC_REQUEST = 1337;
    private final int GALLERY_REQUEST = 1338;

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

    private void setViewModel() {
        ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
        settViewModel = ViewModelProviders.of(this, factory).get(SettingsViewModel.class);

    }

    private void showSettingsRecycleView(User user) {

        setRecycleView(R.id.recycler_view_settings,
                SettingsTextEntitiesGenerator.getSettingsList(), itemCustomDecor);

        setRecycleView(R.id.main_recycler_view_messages,
                SettingsTextEntitiesGenerator.getMessagesList(), itemCustomDecor);

        setRecycleView(R.id.recycler_view_support,
                SettingsTextEntitiesGenerator.getSupporList(), itemDefaultDecor);
    }

    private ArrayList<Object> listUserInfo;

    private void initUserInfo(User user) {

        updateUserHeader(user);
        if (listUserInfo == null) {
            listUserInfo = new ArrayList<>();
            listUserInfo.add(new SettingsTextEntity(SettingsTextEntity.TextType.SubTitle, user.phoneNumber, "Phone"));
            listUserInfo.add(new SettingsTextEntity(SettingsTextEntity.TextType.SubTitle, user.username, "Username"));
            listUserInfo.add(new SettingsTextEntity(SettingsTextEntity.TextType.SubTitle, user.bio, "Bio"));
            setRecycleView(R.id.recycler_view_info, listUserInfo, itemCustomDecor);
        }
    }

    private void updateUserHeader(@NonNull User user) {
        if(user == null)
            return;

        final String name = user.getFullName();
        final String date = "Dec 14th";
        floatHeaderView.bindTo(name, date);
        toolbarHeaderView.bindTo(name, date);
    }

    private void updateListOfUserInfo(@NonNull User user) {

        if(user == null)
            return;

        for (Object obj : listUserInfo) {

            SettingsTextEntity ste = (SettingsTextEntity) obj;
            if(ste.getSecondText().equals("Phone") && !ste.getMainText().equals(user.phoneNumber) ) {
                ste.setMainText(user.phoneNumber);
            }

            if(ste.getSecondText().equals("Username") && !ste.getMainText().equals(user.username) ) {
                ste.setMainText(user.username);
            }

            if(ste.getSecondText().equals("Bio") && !ste.getMainText().equals(user.bio) ) {
                ste.setMainText(user.bio);
            }
        }
    }


}
