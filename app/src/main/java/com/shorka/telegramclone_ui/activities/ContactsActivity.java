package com.shorka.telegramclone_ui.activities;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;

import com.shorka.telegramclone_ui.R;
import com.shorka.telegramclone_ui.adapter.ContactsRecyclerViewAdapter;
import com.shorka.telegramclone_ui.models.ContactPhoneBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by Kyrylo Avramenko on 7/17/2018.
 */
public class ContactsActivity extends SwipeBackActivity implements LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemClickListener {

    private static final String TAG = "ContactsActivity";
    private final Context mContext = ContactsActivity.this;


    public static void open(Context context) {
        context.startActivity(new Intent(context, ContactsActivity.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.contacts_toolbar);
        toolbar.setTitle("Contacts");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView rv = (RecyclerView) findViewById(R.id.recycler_view_phone_contacts);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);

        //TODO: load this on start of app for optimization sake
        ContentResolver cr = mContext.getContentResolver(); //Activity/Application android.content.Context
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        List<ContactPhoneBook> listContacts = cursor.moveToFirst() ? getAllContacts(cursor, cr) : null;
//        List<ContactPhoneBook> listContacts = getTestList();

        Collections.sort(listContacts, new Comparator<ContactPhoneBook>() {
            @Override
            public int compare(ContactPhoneBook o1, ContactPhoneBook o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        ContactsRecyclerViewAdapter adapter = new ContactsRecyclerViewAdapter(listContacts);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                Log.d(TAG, "onChanged: ");
            }
        });

//        for (ContactPhoneBook phoneBook : listContacts) {
//            Log.d(TAG, "initRecyclerView: Contact: " + phoneBook.getName());
//        }

        rv.setAdapter(adapter);
//        rv.scrollToPosition(0);
        Log.d(TAG, "initRecyclerView: rvPos: ");
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

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    private List<ContactPhoneBook> getAllContacts(Cursor cursor, ContentResolver cr) {

        List<ContactPhoneBook> allContacts = new ArrayList<>();
        do {
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

            if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                while (pCur.moveToNext()) {

                    String contactNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String contactName = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    allContacts.add(new ContactPhoneBook(contactNumber, contactName));

                    break;
                }
                pCur.close();
            }

        } while (cursor.moveToNext());

        return allContacts;
    }

    private List<ContactPhoneBook> getTestList() {
        List<ContactPhoneBook> list = new ArrayList<>();
        for (int i = 0; i < 32; i++) {
            list.add(new ContactPhoneBook("", "Name_" + (i + 1)));
        }
        return list;
    }
}
