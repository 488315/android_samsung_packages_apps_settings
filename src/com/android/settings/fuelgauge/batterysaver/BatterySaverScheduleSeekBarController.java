package com.android.settings.fuelgauge.batterysaver;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.widget.SeekBar;

import androidx.preference.Preference;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.widget.SeekBarPreference;
import com.android.settingslib.Utils;
import com.android.settingslib.fuelgauge.BatterySaverUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BatterySaverScheduleSeekBarController
        implements Preference.OnPreferenceChangeListener, SeekBar.OnSeekBarChangeListener {
    public Context mContext;

    @VisibleForTesting int mPercentage;

    @VisibleForTesting public SeekBarPreference mSeekBarPreference;

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        int intValue = ((Integer) obj).intValue() * 5;
        this.mPercentage = intValue;
        String string =
                this.mContext.getString(
                        R.string.battery_saver_seekbar_title, Utils.formatPercentage(intValue));
        preference.setTitle(string);
        this.mSeekBarPreference.mOverrideSeekBarStateDescription = string;
        return true;
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public final void onStopTrackingTouch(SeekBar seekBar) {
        if (this.mPercentage > 0) {
            Settings.Global.putInt(
                    this.mContext.getContentResolver(),
                    "low_power_trigger_level",
                    this.mPercentage);
        }
    }

    public final void updateSeekBar$2() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        if (!"key_battery_saver_percentage"
                .equals(BatterySaverUtils.getBatterySaverScheduleKey(this.mContext))) {
            this.mSeekBarPreference.setVisible(false);
            return;
        }
        int max =
                Math.max(
                        Settings.Global.getInt(contentResolver, "low_power_trigger_level", 0) / 5,
                        2);
        this.mSeekBarPreference.setVisible(true);
        this.mSeekBarPreference.setProgress(max);
        String string =
                this.mContext.getString(
                        R.string.battery_saver_seekbar_title, Utils.formatPercentage(max * 5));
        this.mSeekBarPreference.setTitle(string);
        this.mSeekBarPreference.mOverrideSeekBarStateDescription = string;
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public final void onStartTrackingTouch(SeekBar seekBar) {}

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {}
}
