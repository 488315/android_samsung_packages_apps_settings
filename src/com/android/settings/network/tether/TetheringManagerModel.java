package com.android.settings.network.tether;

import android.app.Application;
import android.net.TetheringManager;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class TetheringManagerModel extends AndroidViewModel {
    public final EventCallback mEventCallback;
    public final StartTetheringCallback mStartTetheringCallback;
    public final MutableLiveData mTetheredInterfaces;
    public final TetheringManager mTetheringManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class EventCallback implements TetheringManager.TetheringEventCallback {
        public EventCallback() {}

        public final void onTetheredInterfacesChanged(List list) {
            TetheringManagerModel.this.mTetheredInterfaces.setValue(list);
        }
    }

    public TetheringManagerModel(Application application) {
        super(application);
        EventCallback eventCallback = new EventCallback();
        this.mEventCallback = eventCallback;
        this.mTetheredInterfaces = new MutableLiveData();
        this.mStartTetheringCallback = new StartTetheringCallback();
        TetheringManager tetheringManager =
                (TetheringManager) application.getSystemService(TetheringManager.class);
        this.mTetheringManager = tetheringManager;
        tetheringManager.registerTetheringEventCallback(
                application.getMainExecutor(), eventCallback);
    }

    @Override // androidx.lifecycle.ViewModel
    public final void onCleared() {
        this.mTetheringManager.unregisterTetheringEventCallback(this.mEventCallback);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class StartTetheringCallback implements TetheringManager.StartTetheringCallback {
        public final void onTetheringStarted() {}

        public final void onTetheringFailed(int i) {}
    }
}
