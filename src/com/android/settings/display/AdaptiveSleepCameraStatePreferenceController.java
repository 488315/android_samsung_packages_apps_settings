package com.android.settings.display;

import android.content.Context;
import android.hardware.SensorPrivacyManager;
import android.view.View;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settingslib.widget.BannerMessagePreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AdaptiveSleepCameraStatePreferenceController implements LifecycleObserver {
    public final Context mContext;

    @VisibleForTesting BannerMessagePreference mPreference;
    public final AnonymousClass1 mPrivacyChangedListener =
            new SensorPrivacyManager
                    .OnSensorPrivacyChangedListener() { // from class:
                                                        // com.android.settings.display.AdaptiveSleepCameraStatePreferenceController.1
                public final void onSensorPrivacyChanged(int i, boolean z) {
                    AdaptiveSleepCameraStatePreferenceController
                            adaptiveSleepCameraStatePreferenceController =
                                    AdaptiveSleepCameraStatePreferenceController.this;
                    adaptiveSleepCameraStatePreferenceController.initializePreference();
                    adaptiveSleepCameraStatePreferenceController.mPreference.setVisible(
                            adaptiveSleepCameraStatePreferenceController.isCameraLocked());
                }
            };
    public final SensorPrivacyManager mPrivacyManager;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settings.display.AdaptiveSleepCameraStatePreferenceController$1] */
    public AdaptiveSleepCameraStatePreferenceController(Context context, Lifecycle lifecycle) {
        this.mPrivacyManager = SensorPrivacyManager.getInstance(context);
        this.mContext = context;
        lifecycle.addObserver(this);
    }

    public final void initializePreference() {
        if (this.mPreference == null) {
            BannerMessagePreference bannerMessagePreference =
                    new BannerMessagePreference(this.mContext);
            this.mPreference = bannerMessagePreference;
            bannerMessagePreference.setTitle(R.string.auto_rotate_camera_lock_title);
            this.mPreference.setSummary(R.string.adaptive_sleep_camera_lock_summary);
            this.mPreference.setPositiveButtonText$1(R.string.allow);
            this.mPreference.setPositiveButtonOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.android.settings.display.AdaptiveSleepCameraStatePreferenceController$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            AdaptiveSleepCameraStatePreferenceController.this.mPrivacyManager
                                    .setSensorPrivacy(3, 2, false);
                        }
                    });
        }
    }

    @VisibleForTesting
    public boolean isCameraLocked() {
        return this.mPrivacyManager.isSensorPrivacyEnabled(2);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        this.mPrivacyManager.addSensorPrivacyListener(2, this.mPrivacyChangedListener);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        this.mPrivacyManager.removeSensorPrivacyListener(2, this.mPrivacyChangedListener);
    }
}
