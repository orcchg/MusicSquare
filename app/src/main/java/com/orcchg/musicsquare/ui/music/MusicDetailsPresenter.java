package com.orcchg.musicsquare.ui.music;

import android.support.annotation.NonNull;

import com.orcchg.musicsquare.data.DataManager;
import com.orcchg.musicsquare.data.model.Musician;
import com.orcchg.musicsquare.ui.base.BasePresenter;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import timber.log.Timber;

public class MusicDetailsPresenter extends BasePresenter<MusicDetailsMvpView> {
    static final String EXTRA_MUSICIAN_ID = "extra_musician_id";

    private final DataManager mDataManager;
    private final long mMusicianId;
    private Subscription mSubscription;

    MusicDetailsPresenter(@NonNull DataManager dataManager, long musicianId) {
        mDataManager = dataManager;
        mMusicianId = musicianId;
    }

    @Override
    public void detachView() {
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
        super.detachView();
    }

    void onStart() {
        checkViewAttached();
        mSubscription = mDataManager.getMusician(mMusicianId)
                .subscribe(new Observer<List<Musician>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        Timber.e("Data error: %s", e.toString());
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(List<Musician> musician) {
                        if (musician.isEmpty()) {
                            Timber.e("Item with id %s not found", Long.toString(mMusicianId));
                            getMvpView().showError();
                        } else {
                            getMvpView().showMusicDetails(musician.get(0));
                        }
                    }
                });
    }
}
