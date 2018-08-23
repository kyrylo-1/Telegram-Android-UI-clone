package com.shorka.telegramclone_ui.contacts_screen;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.shorka.telegramclone_ui.R;
import com.shorka.telegramclone_ui.RecyclerItemClickListener;
import com.shorka.telegramclone_ui.ViewModelFactory;
import com.shorka.telegramclone_ui.adapter.ContactsRecyclerViewAdapter;
import com.shorka.telegramclone_ui.contact_chat_screen.ContactChatViewModel;
import com.shorka.telegramclone_ui.db.PhoneContact;
import com.shorka.telegramclone_ui.utils.SmsHelper;


/**
 * Created by Kyrylo Avramenko on 7/17/2018.
 */
public class ContactsActivity extends AppCompatActivity {

    private static final String TAG = "ContactsActivity";
    private final Context context = ContactsActivity.this;
    private ContactsViewModel viewModel;
    private RecyclerView rv;
    private ContactsRecyclerViewAdapter adapterRv;
    
    public static void open(Context context) {
        context.startActivity(new Intent(context, ContactsActivity.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        setUpToolBar();
        initRecyclerView();
        observeViewModel();
    }

    private void setUpToolBar(){
        final Toolbar toolbar = (Toolbar) findViewById(R.id.contacts_toolbar);
        toolbar.setTitle("Contacts");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void initRecyclerView() {
        rv = (RecyclerView) findViewById(R.id.recycler_view_phone_contacts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        rv.setLayoutManager(layoutManager);

//        List<PhoneContact> listContacts = getAllContacts();

        adapterRv = new ContactsRecyclerViewAdapter();
        rv.setAdapter(adapterRv);

        rv.addOnItemTouchListener(new RecyclerItemClickListener(context, rv, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d(TAG, "onItemClick: pos: " + position);
                clickOnRvItem(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));

        Log.d(TAG, "initRecyclerView: rvPos: ");
    }

    private void observeViewModel(){
        Log.d(TAG, "observeViewModel: ");
        ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
        viewModel = ViewModelProviders.of(this, factory).get(ContactsViewModel.class);
        adapterRv.setItems(viewModel.getPhoneContacts());
        rv.scrollToPosition(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contacts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.contacts_add:
                Log.d(TAG, "onOptionsItemSelected: contacts_add");
                return true;

            case R.id.action_search:
                Log.d(TAG, "onOptionsItemSelected: contacts_add");
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    private void clickOnRvItem(int position){
        PhoneContact phoneContact = viewModel.getPhoneContacts().get(position);
//        Log.d(TAG, "onItemClick: phoneNumber: " + listContacts.get(position).getPhoneNumber());
        showInvitationDialog(phoneContact.getPhoneNumber());
    }

    private void showInvitationDialog(final String phoneNumber) {

        final AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context, R.style.CustomDialog);
        builder.setTitle("Telegram")
                .setMessage("This user does not have Telegram yet, send an invitation?")
                .setPositiveButton(android.R.string.ok, (dialog, which) -> SmsHelper.sendSms(context,phoneNumber))
                .setNegativeButton(android.R.string.no, (dialog, which) -> {

                })
                .show();
    }
}
