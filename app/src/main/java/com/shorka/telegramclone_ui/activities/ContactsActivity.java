package com.shorka.telegramclone_ui.activities;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.shorka.telegramclone_ui.R;
import com.shorka.telegramclone_ui.RecyclerItemClickListener;
import com.shorka.telegramclone_ui.SmsHelper;
import com.shorka.telegramclone_ui.adapter.ContactsRecyclerViewAdapter;
import com.shorka.telegramclone_ui.models.ContactPhoneBook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by Kyrylo Avramenko on 7/17/2018.
 */
public class ContactsActivity extends SwipeBackActivity {

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

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView rv = (RecyclerView) findViewById(R.id.recycler_view_phone_contacts);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);

        //TODO: load this on start of app for optimization sake
        ContentResolver cr = mContext.getContentResolver(); //Activity/Application android.content.Context
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        final List<ContactPhoneBook> listContacts = cursor.moveToFirst() ? getAllContacts(cursor, cr) : null;
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

        rv.setAdapter(adapter);

        rv.addOnItemTouchListener(new RecyclerItemClickListener(mContext, rv, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d(TAG, "onItemClick: pos: " + position);

                ContactPhoneBook contactPhoneBook = listContacts.get(position);
                Log.d(TAG, "onItemClick: phoneNumber: " + listContacts.get(position).getPhoneNumber());
                showInvitationDialog(listContacts.get(position).getPhoneNumber());
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));

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

    private List<ContactPhoneBook> getAllContacts(Cursor cursor, ContentResolver cr) {

        List<ContactPhoneBook> allContacts = new ArrayList<>();
        do {
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

            if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                while (pCur.moveToNext()) {

                    String contactNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
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

    private void showInvitationDialog(final String phoneNumber) {

        final AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(mContext, R.style.CustomDialog);
        builder.setTitle("Telegram")
                .setMessage("This user does not have Telegram yet, send an invitation?")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SmsHelper.sendSms(mContext,phoneNumber);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }
}
