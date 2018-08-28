package com.shorka.telegramclone_ui.settings_screen;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shorka.telegramclone_ui.R;
import com.shorka.telegramclone_ui.ViewModelFactory;

import java.util.Objects;

/**
 * Created by Kyrylo Avramenko on 8/24/2018.
 */
public class ChangeBioActivity extends AppCompatActivity {

    private static final String TAG = "ChangeBioActivity";
    private TextView txtCharLimit;
    private final int LIMIT_OF_TEXT_LEN = 70;
    private ChangeBioViewModel viewModel;
    private EditText edBio;

    private String getBio() {
        return String.valueOf(edBio.getText());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bio);
        initViewModel();
        setUpToolBar();
        setUpUI();
    }


    private void setUpToolBar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.bio_toolbar);
        toolbar.setTitle("Bio");

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(v -> saveBio(getBio()));
    }

    private void setUpUI() {

        txtCharLimit = (TextView) findViewById(R.id.bio_text_limit);
        edBio = findViewById(R.id.bio_editText);
        edBio.setText(viewModel.getBio());
        final int edTextLen = edBio.length();
        edBio.setSelection(edTextLen);
        txtCharLimit.setText(String.valueOf(edTextLen));

        edBio.setFilters(new InputFilter[]{new InputFilter.LengthFilter(LIMIT_OF_TEXT_LEN)});
        edBio.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                // Just ignore the [Enter] key
                saveBio(getBio());
                return true;
            }
            return false;
        });

        edBio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                txtCharLimit.setText(String.valueOf(s.length()));
            }
        });
    }

    private void initViewModel() {
        final ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
        viewModel = ViewModelProviders.of(this, factory).get(ChangeBioViewModel.class);
    }

    private void saveBio(final String bio) {
        Log.d(TAG, "saveBio: " + bio);
        if (TextUtils.isEmpty(bio))
            return;

        viewModel.updateBio(bio);
        onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_single_check, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_check:
                Log.d(TAG, "onOptionsItemSelected: menu_check");
                saveBio(getBio());
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}
