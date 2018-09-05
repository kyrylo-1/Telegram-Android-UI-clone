package com.shorka.telegramclone_ui.contact_chat_screen;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.shorka.telegramclone_ui.R;
import com.shorka.telegramclone_ui.utils.StringUtils;

/**
 * Created by Kyrylo Avramenko on 9/5/2018.
 */
public class DeletionDialogFragment extends DialogFragment {

    private static final String TAG = "DeletionDialogFragment";
    private static String QTY_OF_MESSAGES = "qtyMessages";
    private OnDeletionOptions delOptions;

    public void setDelOptions(OnDeletionOptions delOptions) {
        this.delOptions = delOptions;
    }

    public static DeletionDialogFragment newInstance(int qtyOfDelMessages) {

        DeletionDialogFragment frag = new DeletionDialogFragment();
        Bundle args = new Bundle();
        args.putInt(QTY_OF_MESSAGES, qtyOfDelMessages);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int qty = getArguments().getInt(QTY_OF_MESSAGES);




        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppAlertTheme);
        Resources res = getResources();
        builder.setTitle(StringUtils.capitalize(res.getString(R.string.message)));

        StringBuilder strB = new StringBuilder();

        strB.append(res.getString(R.string.Are_you_sure));
        strB.append(" ").append(qty).append(" ");
        strB.append(res.getString(qty <= 1 ? R.string.message : R.string.messages))
                .append("?");

        builder.setMessage(strB.toString());

        builder.setPositiveButton(res.getString(R.string.ok), (dialog, which) -> delOptions.onOk());

        builder.setNegativeButton(res.getString(R.string.cancel), (dialog, which) -> {
            if (dialog == null) return;

            dialog.dismiss();
            delOptions.onCancel();
        });




        return builder.create();
    }


    public interface OnDeletionOptions {
        void onOk();

        void onCancel();
    }
}
