package com.shorka.telegramclone_ui.settings_screen;

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

import com.shorka.telegramclone_ui.Injection;
import com.shorka.telegramclone_ui.R;

/**
 * Created by Kyrylo Avramenko on 8/13/2018.
 */
public class ChangeNameActivity extends AppCompatActivity implements ChangeNameContract.View {

    private static final String TAG = "ChangeNameActivity";
    private EditText etFirstName, etLastName;
    private ChangeNameContract.UserActionsListener actionsListener;
    
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
        initPresenter();
    }
    
    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.change_name_toolbar);
        toolbar.setTitle("Edit name");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void setupUI() {

        etFirstName = findViewById(R.id.edit_first_name);
        etLastName = findViewById(R.id.edit_last_name);
    }

    private void initPresenter() {
        Log.d(TAG, "initPresenter: ");
        actionsListener = new ChangeNamePresenter(Injection.provideUserRepo(getApplication()), this);
        actionsListener.loadName();
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
            actionsListener.updateName(etFirstName.getText().toString(), etLastName.getText().toString());
        }
        return super.onOptionsItemSelected(item);      
    }


    @Override
    public void returnToPreviousScreen() {
        Log.d(TAG, "returnToPreviousScreen: ");
        onBackPressed();
    }

    @Override
    public void showName(String firsName, @Nullable String lastName) {

        if(!TextUtils.isEmpty(firsName)){
            etFirstName.setText(firsName);
            etFirstName.setSelection(firsName.length());
        }


    }

    //TODO: fix bug with backstack in SettingsActivity

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: ");
        finish();
        SettingsActivity.open(this);
    }
}
