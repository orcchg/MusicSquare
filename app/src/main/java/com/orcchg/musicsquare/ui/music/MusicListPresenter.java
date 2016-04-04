package com.orcchg.musicsquare.ui.music;

import android.app.Activity;

import com.orcchg.musicsquare.MusicSquareApplication;
import com.orcchg.musicsquare.data.DataManager;
import com.orcchg.musicsquare.data.model.Musician;
import com.orcchg.musicsquare.ui.base.BasePresenter;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import timber.log.Timber;

public class MusicListPresenter extends BasePresenter<MusicListMvpView> {

    private DataManager mDataManager;
    private Subscription mSubscription;

    @Override
    public void attachView(MusicListMvpView view) {
        super.attachView(view);
        mDataManager = ((MusicSquareApplication) ((Activity) view).getApplication()).getDataManager();
    }

    @Override
    public void detachView() {
        if (mSubscription != null) mSubscription.unsubscribe();
        super.detachView();
    }

    void loadMusicians() {
        checkViewAttached();
        mSubscription = mDataManager.getMusicians()
                .subscribe(new Observer<List<Musician>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        Timber.e("Network error: " + e);
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(List<Musician> musician) {
                        onMusiciansLoaded(musician);
                    }
                });
    }

    private void onMusiciansLoaded(List<Musician> musician) {
        getMvpView().showMusicList(musician);
    }
}
