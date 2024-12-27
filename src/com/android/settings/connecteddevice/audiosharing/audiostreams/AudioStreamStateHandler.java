package com.android.settings.connecteddevice.audiosharing.audiostreams;

import android.os.Handler;
import android.os.Looper;

import androidx.preference.Preference;

import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AudioStreamStateHandler {
    static final int EMPTY_STRING_RES = 0;
    public final AudioStreamsRepository mAudioStreamsRepository =
            AudioStreamsRepository.getInstance();
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public final SettingsMetricsFeatureProvider mMetricsFeatureProvider;

    public AudioStreamStateHandler() {
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
    }

    public Preference.OnPreferenceClickListener getOnClickListener(
            AudioStreamsProgressCategoryController audioStreamsProgressCategoryController) {
        return null;
    }

    public abstract AudioStreamsProgressCategoryController.AudioStreamState getStateEnum();

    public int getSummary() {
        return 0;
    }

    public void performAction(
            AudioStreamPreference audioStreamPreference,
            AudioStreamsProgressCategoryController audioStreamsProgressCategoryController,
            AudioStreamsHelper audioStreamsHelper) {}
}
