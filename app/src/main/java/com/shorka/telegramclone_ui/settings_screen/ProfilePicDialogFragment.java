package com.shorka.telegramclone_ui.settings_screen;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Window;

import com.shorka.telegramclone_ui.R;

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
        builder.setItems(R.array.cam_options, (dialog, which) -> {
            // The 'which' argument contains the index position
            // of the selected item
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
        });
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();

        Window window = getDialog().getWindow();
        window.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.dialog_bg));
    }

    public interface OnCameraClickOptions {
        void onFromCamClicked();

        void onFromGallery();

        void onDeletePhoto();
    }
}
