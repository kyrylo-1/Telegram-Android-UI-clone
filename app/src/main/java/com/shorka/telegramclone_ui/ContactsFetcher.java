package com.shorka.telegramclone_ui;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.text.TextUtils;

import com.shorka.telegramclone_ui.db.PhoneContact;

import java.util.ArrayList;

/**
 * Created by Kyrylo Avramenko on 8/24/2018.
 */
public class ContactsFetcher {

    public static ArrayList<PhoneContact> fetch(Context context) {

        final String DISPLAY_NAME = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY : ContactsContract.Contacts.DISPLAY_NAME;

        final String FILTER = DISPLAY_NAME + " NOT LIKE '%@%'";
        final String ORDER = String.format("%1$s COLLATE NOCASE", DISPLAY_NAME);

        final String[] PROJECTION = {
                ContactsContract.Contacts._ID,
                DISPLAY_NAME,
                ContactsContract.Contacts.HAS_PHONE_NUMBER
        };


        ArrayList<PhoneContact> contacts = new ArrayList<>();

        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, PROJECTION, FILTER, null, ORDER);
        if (cursor != null && cursor.moveToFirst()) {

            do {
                // get the contact's information
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
                Integer hasPhone = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                // get the user's phone number
                String phone = null;
                if (hasPhone > 0) {
                    Cursor cp = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    if (cp != null && cp.moveToFirst()) {
                        phone = cp.getString(cp.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        cp.close();
                    }
                }

                if (!TextUtils.isEmpty(phone)) {
                    contacts.add(new PhoneContact(0, phone, name));
                }

            } while (cursor.moveToNext());

            // clean up cursor
            cursor.close();
        }

        return contacts;
    }
}
