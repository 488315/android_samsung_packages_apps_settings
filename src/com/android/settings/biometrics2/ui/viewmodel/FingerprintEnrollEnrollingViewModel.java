package com.android.settings.biometrics2.ui.viewmodel;

import android.app.Application;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.settings.biometrics2.data.repository.FingerprintRepository;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FingerprintEnrollEnrollingViewModel extends AndroidViewModel {
    public final AccessibilityManager mAccessibilityManager;
    public final MutableLiveData mActionLiveData;
    public final FingerprintRepository mFingerprintRepository;
    public boolean mOnBackPressed;
    public boolean mOnSkipPressed;
    public final int mUserId;
    public final Vibrator mVibrator;
    public static final VibrationEffect VIBRATE_EFFECT_ERROR =
            VibrationEffect.createWaveform(new long[] {0, 5, 55, 60}, -1);
    public static final VibrationAttributes FINGERPRINT_ENROLLING_SONFICATION_ATTRIBUTES =
            VibrationAttributes.createForUsage(66);

    public FingerprintEnrollEnrollingViewModel(
            Application application, int i, FingerprintRepository fingerprintRepository) {
        super(application);
        this.mActionLiveData = new MutableLiveData();
        this.mUserId = i;
        this.mFingerprintRepository = fingerprintRepository;
        this.mAccessibilityManager =
                (AccessibilityManager) application.getSystemService(AccessibilityManager.class);
        this.mVibrator = (Vibrator) application.getSystemService(Vibrator.class);
    }

    public final void sendAccessibilityEvent(CharSequence charSequence) {
        AccessibilityEvent obtain = AccessibilityEvent.obtain();
        obtain.setEventType(NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT);
        obtain.setClassName(FingerprintEnrollEnrollingViewModel.class.getName());
        obtain.setPackageName(getApplication().getPackageName());
        obtain.getText().add(charSequence);
        this.mAccessibilityManager.sendAccessibilityEvent(obtain);
    }
}
