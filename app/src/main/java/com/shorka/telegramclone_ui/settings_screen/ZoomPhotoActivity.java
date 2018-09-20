package com.shorka.telegramclone_ui.settings_screen;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Display;
import android.view.Window;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;
import com.shorka.telegramclone_ui.GlideApp;
import com.shorka.telegramclone_ui.R;
import com.shorka.telegramclone_ui.utils.ImageHelper;

import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Kyrylo Avramenko on 9/19/2018.
 */
public class ZoomPhotoActivity extends AppCompatActivity {

    public static final String PHOTO_URL = "PhotoURL";
    private final CompositeDisposable compDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_zoom_photo);
        setUpToolbar();

        String url = "";
        if (getIntent().hasExtra(PHOTO_URL))
            url = getIntent().getStringExtra(PHOTO_URL);

        setUpUI(url);
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

    private void setUpUI(@NonNull String photoURL) {

        ImageView imgPhoto = findViewById(R.id.iv_square_photo);

        if (TextUtils.isEmpty(photoURL)) {
            imgPhoto.setImageDrawable(getDrawable(R.drawable.profile_default_male));
            return;
        }

//        BitmapDrawable drawable = (BitmapDrawable) imgPhoto.getDrawable();
//        imgPhoto.setImageBitmap(ImageHelper.cropToSquare(drawable.getBitmap()));


        Disposable disposable = Flowable.fromCallable(() -> {
            Bitmap myBitmap = BitmapFactory.decodeFile(photoURL);
            if (myBitmap != null) {
                myBitmap = ImageHelper.cropToSquare(myBitmap);
            }
            return myBitmap;
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnNext(bitmap -> {
                    if (bitmap != null)
                        GlideApp.with(ZoomPhotoActivity.this)
                                .load(bitmap)
                                .fitCenter()
                                .into(imgPhoto);
                })
                .subscribe();

        compDisposable.add(disposable);
    }


}
