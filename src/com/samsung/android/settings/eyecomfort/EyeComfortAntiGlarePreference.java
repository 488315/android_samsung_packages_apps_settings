package com.samsung.android.settings.eyecomfort;

import android.content.Context;
import android.provider.Settings;
import android.util.AttributeSet;

import androidx.preference.SecSwitchPreference;

import com.samsung.android.settings.logging.LoggingHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class EyeComfortAntiGlarePreference extends SecSwitchPreference {
    public EyeComfortAntiGlarePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // androidx.preference.TwoStatePreference
    public final void setChecked(boolean z) {
        if (z
                != (Settings.System.getInt(
                                getContext().getContentResolver(),
                                "blue_light_filter_anti_glare",
                                0)
                        == 1)) {
            LoggingHelper.insertEventLogging(46, 40206, z ? 1L : 0L);
            Settings.System.putInt(
                    getContext().getContentResolver(), "blue_light_filter_anti_glare", z ? 1 : 0);
        }
        super.setChecked(z);
    }

    public EyeComfortAntiGlarePreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public EyeComfortAntiGlarePreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public EyeComfortAntiGlarePreference(Context context) {
        super(context);
    }
}
