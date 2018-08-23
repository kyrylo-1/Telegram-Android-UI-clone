package com.shorka.telegramclone_ui.settings_screen;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.shorka.telegramclone_ui.R;
import com.shorka.telegramclone_ui.ViewModelFactory;

/**
 * Created by Kyrylo Avramenko on 8/13/2018.
 */
public class ChangeNameActivity extends AppCompatActivity {

    private static final String TAG = "ChangeNameActivity";
    private EditText etFirstName, etLastName;
    private ChangeNameViewModel viewModel;
    public static void open(Context context) {
        context.startActivity(new Intent(context, ChangeNameActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_change_name);

        Log.d(TAG, "onCreate: ");
        setUpToolbar();
        setupUI();
        observeViewModel();
    }
    
    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.change_name_toolbar);
        toolbar.setTitle("Edit firstName");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void setupUI() {

        etFirstName = findViewById(R.id.edit_first_name);
        etLastName = findViewById(R.id.edit_last_name);
    }

    private void observeViewModel(){
        ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
        viewModel = ViewModelProviders.of(this, factory).get(ChangeNameViewModel.class);
        showName(viewModel.getUserFirstName(),viewModel.getUserLastName());
    }

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_change_name, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.check_change_name) {
            Log.d(TAG, "onOptionsItemSelected: check_change_name");
            updateUsername();
        }
        return super.onOptionsItemSelected(item);      
    }

    private void updateUsername(){

        if(TextUtils.isEmpty(etFirstName.getText()))
            return;

        viewModel.updateUsername(etFirstName.getText().toString(), etLastName.getText().toString());
        onBackPressed();
    }


    private void showName(String firsName, @Nullable String lastName) {

        if(!TextUtils.isEmpty(firsName)){
            etFirstName.setText(firsName);
            etFirstName.setSelection(firsName.length());
        }

        if(!TextUtils.isEmpty(lastName)){
            etLastName.setText(lastName);
        }
    }
}
