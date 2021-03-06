package com.orcchg.musicsquare.ui.music;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.orcchg.musicsquare.R;
import com.orcchg.musicsquare.data.DataManager;
import com.orcchg.musicsquare.data.model.Musician;
import com.orcchg.musicsquare.ui.base.BasePresenter;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import timber.log.Timber;

class MusicListPresenter extends BasePresenter<MusicListMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    MusicListPresenter(@NonNull DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void detachView() {
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
        super.detachView();
    }

    void loadMusicians() {
        checkViewAttached();
        getMvpView().showLoading();
        mSubscription = mDataManager.getMusicians()
                .subscribe(new Observer<List<Musician>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        Timber.e("Data error: %s", e.toString());
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(List<Musician> musicians) {
                        getMvpView().showMusicList(musicians);
                    }
                });
    }

    void onRetry() {
        mDataManager.invalidateCache(true);
        loadMusicians();  // retry network request
    }

    void onMenuItemAboutClicked() {
        new AlertDialog.Builder((Activity) getMvpView())
                .setTitle(R.string.str_about)
                .setMessage(R.string.str_about_message)
                .setPositiveButton(R.string.str_ok, (dialog, which) -> dialog.dismiss()).show();
    }
}
