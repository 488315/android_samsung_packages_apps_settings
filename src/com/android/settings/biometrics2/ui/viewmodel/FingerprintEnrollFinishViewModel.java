package com.android.settings.biometrics2.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.settings.biometrics2.data.repository.FingerprintRepository;
import com.android.settings.biometrics2.ui.model.EnrollmentRequest;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FingerprintEnrollFinishViewModel extends AndroidViewModel {
    public final MutableLiveData mActionLiveData;
    public final FingerprintRepository mFingerprintRepository;
    public final EnrollmentRequest mRequest;
    public final int mUserId;

    public FingerprintEnrollFinishViewModel(
            int i,
            Application application,
            FingerprintRepository fingerprintRepository,
            EnrollmentRequest enrollmentRequest) {
        super(application);
        this.mActionLiveData = new MutableLiveData();
        this.mUserId = i;
        this.mRequest = enrollmentRequest;
        this.mFingerprintRepository = fingerprintRepository;
    }
}
