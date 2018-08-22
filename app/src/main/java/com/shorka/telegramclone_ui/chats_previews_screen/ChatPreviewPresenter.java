//package com.shorka.telegramclone_ui.chats_previews_screen;
//
//import android.support.annotation.NonNull;
//import android.util.Log;
//
//import com.shorka.telegramclone_ui.db.UserMessages;
//import com.shorka.telegramclone_ui.db.UserRepository;
//
//import java.util.List;
//
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.functions.Consumer;
//import io.reactivex.schedulers.Schedulers;
//
//
///**
// * Created by Kyrylo Avramenko on 8/1/2018.
// */
//public class ChatPreviewPresenter implements ChatPreviewContract.UserActionsListener {
//
//    private static final String TAG = "ChatPreviewPresenter";
//    @NonNull
//    private ChatPreviewContract.View view;
//    @NonNull
//    private UserRepository userRepo;
//
//
//    public ChatPreviewPresenter(@NonNull UserRepository userRepo, @NonNull ChatPreviewContract.View view) {
//        this.userRepo = userRepo;
//        this.view = view;
//    }
//
//    @Override
//    public void loadChats() {
//        Log.d(TAG, "loadChats: ");
//        userRepo.getSingleCurrUser()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(user -> {
//                            Log.d(TAG, "accept: " + user.phoneNumber + " \n bio: " + user.bio);
////                            userRepo.setCurrUser(user);
////                            view.updateUserDetail(user);
////
//                        }
//                );
//
////        loadChats2();
//    }
//
//    @Override
//    public void writeNewMessage() {
//
//    }
//
//    @Override
//    public void openChatMessages(long idRecipient) {
//
//    }
//
//    private void loadChats2() {
////        userRepo.get()
////                .subscribeOn(Schedulers.io())
////                .observeOn(AndroidSchedulers.mainThread())
////                .subscribe(userMsgs -> {
////                            Log.d(TAG, "loadChats2 accept with size of userMesseges: " + userMsgs.size());
////                        }
////                );
//
////        userRepo.getUserMsgsById(2)
////                .subscribeOn(Schedulers.io())
////                .observeOn(AndroidSchedulers.mainThread())
////                .subscribe(new Consumer<UserMessages>() {
////                    @Override
////                    public void accept(UserMessages userMsgs) throws Exception {
////
////                    }
////                });
////        userRepo.getUserMsgsById(2);
//    }
//}
