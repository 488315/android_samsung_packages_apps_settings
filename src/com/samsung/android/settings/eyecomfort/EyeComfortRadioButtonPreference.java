package com.samsung.android.settings.eyecomfort;

import android.R;
import android.content.Context;
import android.provider.Settings;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.preference.CheckBoxPreference;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecPreferenceUtils;

import com.samsung.android.settings.logging.LoggingHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class EyeComfortRadioButtonPreference extends CheckBoxPreference {
    public OnClickListener mListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnClickListener {}

    public EyeComfortRadioButtonPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.checkBoxPreferenceStyle);
    }

    @Override // androidx.preference.CheckBoxPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        SecPreferenceUtils.applyTitleLargerTextIfNeeded(
                (TextView) preferenceViewHolder.findViewById(R.id.title));
    }

    @Override // androidx.preference.TwoStatePreference, androidx.preference.Preference
    public final void onClick() {
        OnClickListener onClickListener = this.mListener;
        if (onClickListener != null) {
            EyeComfortSettings eyeComfortSettings = (EyeComfortSettings) onClickListener;
            if (equals(eyeComfortSettings.mAdaptivePreference)) {
                Settings.System.putInt(
                        eyeComfortSettings.getContentResolver(),
                        "blue_light_filter_adaptive_mode",
                        1);
                LoggingHelper.insertEventLogging(4222, 40200, 1L);
                LoggingHelper.insertEventLogging(4222, 7411, 0L);
            } else if (equals(eyeComfortSettings.mCustomPreference)) {
                boolean z =
                        Settings.Secure.getInt(
                                        eyeComfortSettings.getContentResolver(), "location_mode", 0)
                                != 0;
                int i =
                        Settings.System.getInt(
                                eyeComfortSettings.getContentResolver(),
                                "blue_light_filter_type",
                                0);
                if (!z && i == 2) {
                    eyeComfortSettings.showLocationOnDialog(1);
                    return;
                }
                Settings.System.putInt(
                        eyeComfortSettings.getContentResolver(), "blue_light_filter_scheduled", 1);
                LoggingHelper.insertEventLogging(4222, 7411, 1L);
                LoggingHelper.insertEventLogging(4222, 40200, 0L);
            }
        }
    }

    public EyeComfortRadioButtonPreference(Context context) {
        this(context, null);
    }

    public EyeComfortRadioButtonPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i, 0);
        this.mListener = null;
        setLayoutResource(com.android.settings.R.layout.sec_preference_radio_button_eye_comfort);
        setWidgetLayoutResource(com.android.settings.R.layout.preference_widget_radiobutton);
    }
}
