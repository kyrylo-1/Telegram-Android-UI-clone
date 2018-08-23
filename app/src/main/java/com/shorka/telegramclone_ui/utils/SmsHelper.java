package com.shorka.telegramclone_ui.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.shorka.telegramclone_ui.R;

/**
 * Created by Kyrylo Avramenko on 7/19/2018.
 */
public class SmsHelper {

    private static final String TAG = "SmsHelper";

    public static void sendSms(Context context, String phoneNumber) {
        Log.d(TAG, "sendSms: ");

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
        intent.putExtra("sms_body",  context.getResources().getString(R.string.invitation_text));
        context.startActivity(intent);
    }
}
