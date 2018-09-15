package com.shorka.telegramclone_ui.chats_previews_screen;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shorka.telegramclone_ui.R;

import java.util.Objects;

/**
 * Created by Kyrylo Avramenko on 9/10/2018.
 */
public class ConvoBottomDialog extends BottomSheetDialogFragment {

    private static final String TAG = "ConvoBottomDialog";
    private ChatPreviewViewModel viewModel;

    public static String RECIPIENT_ID = "recipientID";

    public static ConvoBottomDialog newInstance(long recipientId) {
        return new ConvoBottomDialog();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(ChatPreviewViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_bottom_sheet_convo, container,
                false);

        Bundle bundle = this.getArguments();
        long id = -1;
        if (bundle != null) {
            id = bundle.getLong(RECIPIENT_ID,- 1);
        }

        Log.d(TAG, "onCreateView: id: " + id);
        setUpUI(view, id);

        return view;
    }

    public void setUpUI(View view, long id) {
        TextView tvClear = view.findViewById(R.id.tv_clear_convo);
        TextView tvDelete = view.findViewById(R.id.tv_delete_convo);

        tvClear.setOnClickListener(v -> {
            viewModel.cleanChat(id);
            dismiss();

        });
        tvDelete.setOnClickListener(v -> {
            viewModel.deleteChat(id);
            dismiss();
        });
    }
}
