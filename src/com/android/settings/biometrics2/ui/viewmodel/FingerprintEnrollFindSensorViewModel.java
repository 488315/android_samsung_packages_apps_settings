package com.android.settings.biometrics2.ui.viewmodel;

import android.app.Application;
import android.view.accessibility.AccessibilityManager;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FingerprintEnrollFindSensorViewModel extends AndroidViewModel {
    public final AccessibilityManager mAccessibilityManager;
    public final MutableLiveData mActionLiveData;
    public final boolean mIsSuw;

    public FingerprintEnrollFindSensorViewModel(Application application, boolean z) {
        super(application);
        this.mActionLiveData = new MutableLiveData();
        this.mAccessibilityManager =
                (AccessibilityManager) application.getSystemService(AccessibilityManager.class);
        this.mIsSuw = z;
    }

    public final void onSkipButtonClick$1() {
        this.mActionLiveData.postValue(Integer.valueOf(this.mIsSuw ? 1 : 0));
    }

    public final void onStartButtonClick$1() {
        this.mActionLiveData.postValue(2);
    }
}
