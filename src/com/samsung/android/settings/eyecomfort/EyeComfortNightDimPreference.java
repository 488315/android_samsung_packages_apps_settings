package com.samsung.android.settings.eyecomfort;

import android.content.Context;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.preference.SecSwitchPreference;

import com.samsung.android.settings.logging.LoggingHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class EyeComfortNightDimPreference extends SecSwitchPreference {
    public EyeComfortNightDimPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // androidx.preference.TwoStatePreference
    public final boolean isChecked() {
        try {
            try {
                r2 =
                        Settings.System.getInt(
                                        getContext().getContentResolver(),
                                        "blue_light_filter_night_dim",
                                        0)
                                == 1;
                Log.d("EyeComfortNightDimPreference", "Current setting: " + r2);
                return r2;
            } catch (Exception e) {
                Log.d("EyeComfortNightDimPreference", e.getMessage());
                return r2;
            }
        } catch (Throwable unused) {
            return r2;
        }
    }

    @Override // androidx.preference.TwoStatePreference
    public final void setChecked(boolean z) {
        LoggingHelper.insertEventLogging(46, 7416, z ? 1L : 0L);
        if (Settings.System.putInt(
                getContext().getContentResolver(), "blue_light_filter_night_dim", z ? 1 : 0)) {
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    z ? 1 : 0, "Successfully set DB value to ", "EyeComfortNightDimPreference");
        } else {
            Log.d("EyeComfortNightDimPreference", "Error in setting DB value");
        }
        super.setChecked(z);
    }

    public EyeComfortNightDimPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public EyeComfortNightDimPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public EyeComfortNightDimPreference(Context context) {
        super(context);
    }
}
