package com.samsung.android.settings.asbase.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.android.settings.R;

import com.samsung.android.settings.asbase.vibration.VibrationSystemIntensitySettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecVibrationLinkablePreference extends SecAudioLinkablePreference {
    public SecVibrationLinkablePreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // com.samsung.android.settings.asbase.widget.SecAudioLinkablePreference
    public final String getDestinationName() {
        return VibrationSystemIntensitySettings.class.getName();
    }

    @Override // com.samsung.android.settings.asbase.widget.SecAudioLinkablePreference
    public final int getGuideTextResource() {
        return R.string.sec_sound_vibration_feedback_link;
    }

    @Override // com.samsung.android.settings.asbase.widget.SecAudioLinkablePreference
    public final int getTitleResource$1() {
        return R.string.sec_vibration_feedback;
    }

    public SecVibrationLinkablePreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SecVibrationLinkablePreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SecVibrationLinkablePreference(Context context) {
        this(context, null);
    }
}
