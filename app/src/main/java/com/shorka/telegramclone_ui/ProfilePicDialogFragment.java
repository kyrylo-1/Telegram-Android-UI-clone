package com.shorka.telegramclone_ui;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;

/**
 * Created by Kyrylo Avramenko on 6/15/2018.
 */
public class ProfilePicDialogFragment extends DialogFragment {

    private static final String TAG = "ProfilePicDialogFragmen";
    private OnCameraClickOptions camClickOptions;

    public void setCamClickOptions(OnCameraClickOptions camClickOptions) {
        this.camClickOptions = camClickOptions;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(R.array.cam_options, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // The 'which' argument contains the index position
                // of the selected item
                Log.d(TAG, "onClick: "+which);
                switch (which){
                    case 0:
                        camClickOptions.onFromCamClicked();
                        break;

                    case 1:
                        camClickOptions.onFromGallery();
                        break;

                    case 2:
                        camClickOptions.onDeletePhoto();
                        break;
                }
            }
        });
        return builder.create();
    }


    public interface OnCameraClickOptions {
        void onFromCamClicked();

        void onFromGallery();

        void onDeletePhoto();
    }
}
