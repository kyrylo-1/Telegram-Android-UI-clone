package com.shorka.telegramclone_ui.settings_screen;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.shorka.telegramclone_ui.GlideApp;
import com.shorka.telegramclone_ui.R;
import com.shorka.telegramclone_ui.ViewModelFactory;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Kyrylo Avramenko on 9/19/2018.
 */
public class ZoomPhotoActivity extends AppCompatActivity {

    private static final String TAG = "ZoomPhotoActivity";
    public static final String PHOTO_URL = "PhotoURL";
    private final CompositeDisposable compDisposable = new CompositeDisposable();
    private ZoomPhotoViewModel viewModel;
    private View frameLay;
    ImageView imgPhoto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_zoom_photo);
        setUpToolbar();

        observeViewModel();

        if (getIntent().hasExtra(PHOTO_URL)) {
            viewModel.setCurrPicPath(getIntent().getStringExtra(PHOTO_URL));
        }
        else {
            Log.e(TAG, "onCreate: no extra string date for__ " + PHOTO_URL);
        }
        setUpUI();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (compDisposable != null && compDisposable.isDisposed())
            compDisposable.clear();
    }


    private void setUpToolbar() {
        final Toolbar toolbar = findViewById(R.id.photo_zoom_toolbar);
        toolbar.setTitle("1 of 1");

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
    }

    private void setUpUI() {

        frameLay = findViewById(R.id.frameLay);
        makeProgressVisible(true);

        imgPhoto = findViewById(R.id.iv_square_photo);

        GlideApp.with(this)
                .asBitmap()
                .load(viewModel.getCurrPicPath())
                .fitCenter()
                .into(target);
    }

    private void observeViewModel() {
        ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
        viewModel = ViewModelProviders.of(this, factory).get(ZoomPhotoViewModel.class);
    }

    private void makeProgressVisible(boolean isVisible) {
        frameLay.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private SimpleTarget target = new SimpleTarget<Bitmap>() {
        @Override
        public void onResourceReady(Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
            imgPhoto.setImageBitmap(bitmap);
            makeProgressVisible(false);
        }
    };
}
