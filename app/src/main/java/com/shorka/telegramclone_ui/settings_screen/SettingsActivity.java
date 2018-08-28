package com.shorka.telegramclone_ui.settings_screen;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.IntDef;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.shorka.telegramclone_ui.DividerCustomItemDecoration;
import com.shorka.telegramclone_ui.HeaderView;
import com.shorka.telegramclone_ui.ProfilePicDialogFragment;
import com.shorka.telegramclone_ui.R;
import com.shorka.telegramclone_ui.RecyclerItemClickListener;
import com.shorka.telegramclone_ui.ViewModelFactory;
import com.shorka.telegramclone_ui.adapter.ComplexRecyclerViewAdapter;
import com.shorka.telegramclone_ui.db.User;
import com.shorka.telegramclone_ui.entities.SettingsTextEntity;
import com.shorka.telegramclone_ui.utils.Config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by Kyrylo Avramenko on 6/11/2018.
 */
public class SettingsActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    //<editor-fold desc="fields">
    private static final String TAG = "SettingsActivity";
    private final Context context = SettingsActivity.this;
    private HeaderView toolbarHeaderView, floatHeaderView;
    private DividerCustomItemDecoration itemCustomDecor;
    private DividerItemDecoration itemDefaultDecor;
    private boolean isHideToolbarView = false;
    private SettingsViewModel settViewModel;
    //    private ArrayList<Object> listUserInfo;
    private final HashMap<Integer, String> mapRecycleItems = new HashMap<>();


    //</editor-fold>

    public static void open(Context context) {
        context.startActivity(new Intent(context, SettingsActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Log.d(TAG, "onCreate: ");
        setupUI();
        initRecycleView();
        setViewModel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUserHeader(settViewModel.getCachedUser());
//        updateAllSettings(settViewModel.getCachedUser());
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
        Log.d(TAG, "setupUI: toolbarHEaderView id: " + toolbarHeaderView.getId());
        floatHeaderView = (HeaderView) findViewById(R.id.float_header_view);

        toolbarHeaderView.setTxtViewName((TextView) toolbarHeaderView.findViewById(R.id.name));
        toolbarHeaderView.setTxtViewLastSeen((TextView) toolbarHeaderView.findViewById(R.id.last_seen));

        floatHeaderView.setTxtViewName((TextView) floatHeaderView.findViewById(R.id.name_float));
        floatHeaderView.setTxtViewLastSeen((TextView) floatHeaderView.findViewById(R.id.last_seen_float));

        final AppBarLayout mAppBarLayout = (AppBarLayout) findViewById(R.id.appbar_test);
        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
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

    private void setHeaderText(@IdRes int idSnippet, String text) {

        final View view = findViewById(idSnippet);
        TextView txtView = view.findViewById(R.id.txt_settings_header);
        if (txtView != null) {
            txtView.setText(text);
//            Log.d(TAG, "setHeaderText: txtView NOT null");
        } else Log.e(TAG, "setHeaderText: txtView is NULL for" + idSnippet);
    }

    private void showDialog() {
        final ProfilePicDialogFragment dialog = new ProfilePicDialogFragment();
        dialog.show(getFragmentManager(), "dialog");
        dialog.setCamClickOptions(new ProfilePicDialogFragment.OnCameraClickOptions() {
            @Override
            public void onFromCamClicked() {
                Log.d(TAG, "onFromCamClicked: ");
                Intent intent = new Intent(Config.Intents.IMAGE_CAPTURE);
                startActivityForResult(intent, Config.Requests.CAMERA_PIC_REQUEST);
            }

            @Override
            public void onFromGallery() {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, Config.Requests.GALLERY_REQUEST);
            }

            @Override
            public void onDeletePhoto() {

            }
        });
    }

    private void setViewModel() {
        ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
        settViewModel = ViewModelProviders.of(this, factory).get(SettingsViewModel.class);
        settViewModel.getLiveCurrUser().observe(this, user -> {
            Log.d(TAG, "onChanged: update user details: ");
            if (user == null)
                Log.e(TAG, "observeViewModel: user in NULL");

            else {
                Log.d(TAG, "setViewModel: set data from lived user");
                settViewModel.cacheUser(user);
                updateUserHeader(user);
                updateAllSettings(user);
            }
        });
    }


    private List<AdapterAndList> adapterAndLists;

    private void initRecycleView() {

        //Initialize only once
        if (adapterAndLists != null) return;

        adapterAndLists = new ArrayList<>();
        ArrayList<Object> list = SettingsTextEntitiesGenerator.getUserInfoList();
        adapterAndLists.add(new AdapterAndList(Sections.BASIC_INFO,
                setRecycleView(R.id.recycler_view_info, list, itemCustomDecor), list));

        list = SettingsTextEntitiesGenerator.getSettingsList();
        adapterAndLists.add(new AdapterAndList(Sections.SETTINGS,
                setRecycleView(R.id.recycler_view_settings, list, itemCustomDecor), list));

        list = SettingsTextEntitiesGenerator.getMessagesList();
        adapterAndLists.add(new AdapterAndList(Sections.MESSAGES,
                setRecycleView(R.id.main_recycler_view_messages, list, itemCustomDecor), list));

        list = SettingsTextEntitiesGenerator.getSupporList();
        adapterAndLists.add(new AdapterAndList(Sections.SUPPORT,
                setRecycleView(R.id.recycler_view_support, list, itemDefaultDecor), list));

    }

    private ComplexRecyclerViewAdapter setRecycleView(@IdRes int idRecycleVoew, ArrayList<Object> list, RecyclerView.ItemDecoration itemDecor) {

        for (Object obj : list) {
            SettingsTextEntity ste = (SettingsTextEntity) obj;
            mapRecycleItems.put(ste.getIdRes(), ste.getDescription());
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(idRecycleVoew);
        ComplexRecyclerViewAdapter adapter = new ComplexRecyclerViewAdapter(list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(itemDecor);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(context, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Log.d(TAG, "onItemClick: pos: " + position + "\n view: " + view.getId());

//                if(view.getId() == R.id.recycler_view_info){
//                    Log.d(TAG, "onItemClick: recycler_view_info");
//                }

                if (mapRecycleItems.containsKey(view.getId())) {
                    clickOnRecycleItem(mapRecycleItems.get(view.getId()));
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));

        return adapter;
    }

    private void clickOnRecycleItem(String itemName) {
        Log.d(TAG, "clickOnRecycleItem: itemName: " + itemName);

        final String lowerItemName = itemName.toLowerCase();

        if (lowerItemName.equals("bio")) {
            startActivity(new Intent(this, ChangeBioActivity.class));
        } else {
            Toast t = Toast.makeText(context, "Isn't implemented yet", Toast.LENGTH_SHORT);
//            t.setGravity(Gravity.BOTTOM,0,0);
            t.show();
        }
    }

    private void updateUserHeader(@NonNull User user) {
        if (user == null)
            return;

        final String name = user.getFullName();
        final String date = "Dec 14th";
        floatHeaderView.bindTo(name, date);
        toolbarHeaderView.bindTo(name, date);
    }

    private void updateAllSettings(@NonNull final User user) {

        if (user == null) {
            Log.e(TAG, "updateAllSettings: user is null");
            return;
        }

        Log.d(TAG, "updateAllSettings: ");
        for (AdapterAndList adpList : adapterAndLists) {
            switch (adpList.settingSection) {
                case Sections.BASIC_INFO:
                    updateUserInfo(user, adpList.list);
                    break;

                case Sections.SETTINGS:
                    break;

                case Sections.MESSAGES:
                    break;

                case Sections.SUPPORT:
                    break;
            }
        }
    }

    private void updateUserInfo(final User user, final ArrayList<Object> list) {

        Log.d(TAG, "updateUserInfo: ");

        // set necessary data to list elements
        for (Object obj : list) {
            SettingsTextEntity ste = (SettingsTextEntity) obj;
            final String mainText = ste.getMainText();
            final String description = ste.getDescription();
            Log.d(TAG, "updateUserInfo: ste" + ste.getMainText() + " sec: " + description);

            boolean isMainTextEmpty = TextUtils.isEmpty(mainText);

            if (description.equals("Phone") && (isMainTextEmpty || !mainText.equals(user.phoneNumber))) {
                ste.setMainText(user.phoneNumber);
            }

            else if (description.equals("Username") && (isMainTextEmpty || !mainText.equals(user.username))) {
                ste.setMainText(user.username);
            }

            else if (description.equals("Bio") && (isMainTextEmpty || !mainText.equals(user.bio))) {
                ste.setMainText(user.bio);
            }
        }

        ComplexRecyclerViewAdapter adapter = getAdapter(Sections.BASIC_INFO);
        if (adapter != null) {
            adapter.setItems(list);
        }
    }

    @Nullable
    private ComplexRecyclerViewAdapter getAdapter(@Sections.SectionDef final int settingSection) {
        for (AdapterAndList item : adapterAndLists) {
            if (item.settingSection == settingSection) {
                return item.adapter;
            }
        }
        return null;
    }

    private class AdapterAndList {

        @Sections.SectionDef
        final int settingSection;

        final ComplexRecyclerViewAdapter adapter;
        public final ArrayList<Object> list;

        AdapterAndList(@Sections.SectionDef int settingSection, ComplexRecyclerViewAdapter adapter, ArrayList<Object> list) {
            this.settingSection = settingSection;
            this.adapter = adapter;
            this.list = list;
        }
    }

    private static class Sections {
        static final int BASIC_INFO = 0;
        static final int SETTINGS = 1;
        static final int MESSAGES = 2;
        static final int SUPPORT = 3;

        @IntDef({BASIC_INFO, SETTINGS, MESSAGES, SUPPORT})
        @Retention(RetentionPolicy.SOURCE)
        @interface SectionDef {
        }
    }

}
