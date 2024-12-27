package com.samsung.android.settings.eyecomfort;

import android.content.Context;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.SeslSeekBar;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import com.samsung.android.hardware.display.SemMdnieManager;
import com.samsung.android.settings.SliderUtils;
import com.samsung.android.settings.display.DisplayCustomPreference;
import com.samsung.android.settings.logging.LoggingHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class EyeComfortSeekBarPreference extends DisplayCustomPreference
        implements View.OnFocusChangeListener {
    public final Context mContext;
    public boolean mEnableControlInSetting;
    public boolean mIsFocusable;
    public SeslSeekBar mSeekBar;

    public EyeComfortSeekBarPreference(Context context, AttributeSet attributeSet) {
        super(
                context,
                attributeSet,
                0,
                R.layout.sec_preference_seekbar_eye_comfort,
                R.id.eye_comfort_seekbar);
        this.mIsFocusable = false;
        this.mEnableControlInSetting = false;
        this.mContext = context;
    }

    @Override // com.samsung.android.settings.display.DisplayCustomPreference,
              // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        SeslSeekBar seslSeekBar =
                (SeslSeekBar) preferenceViewHolder.findViewById(R.id.eye_comfort_seekbar);
        if (seslSeekBar == this.mSeekBar) {
            return;
        }
        this.mSeekBar = seslSeekBar;
        seslSeekBar.setMode(5);
        int i =
                Settings.System.getInt(
                        this.mContext.getContentResolver(), "blue_light_filter_opacity", 5);
        SeslSeekBar seslSeekBar2 = this.mSeekBar;
        synchronized (seslSeekBar2) {
            seslSeekBar2.setProgress(seslSeekBar2.mProgress + 1);
        }
        this.mSeekBar.setMax(10);
        this.mSeekBar.setOnSeekBarChangeListener(this);
        this.mSeekBar.setOnFocusChangeListener(this);
        preferenceViewHolder.itemView.setOnFocusChangeListener(this);
        this.mSeekBar.setProgress(i);
        this.mSeekBar.setSeamless(true);
        seslSeekBar.setStateDescription(
                ((Object) SliderUtils.formatStateDescription(this.mContext, seslSeekBar))
                        + ", "
                        + this.mContext
                                .getResources()
                                .getString(R.string.sec_eye_comfort_color_temperature));
        Settings.System.putInt(this.mContext.getContentResolver(), "blue_light_filter_opacity", i);
    }

    @Override // android.view.View.OnFocusChangeListener
    public final void onFocusChange(View view, boolean z) {
        this.mIsFocusable = z;
        if (z || this.mSeekBar == null) {
            return;
        }
        Settings.System.putInt(
                this.mContext.getContentResolver(),
                "blue_light_filter_opacity",
                this.mSeekBar.getProgress());
    }

    @Override // com.samsung.android.settings.display.DisplayCustomPreference,
              // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
    public final void onProgressChanged(SeslSeekBar seslSeekBar, int i, boolean z) {
        boolean z2 = this.mIsFocusable;
        if (z2 || !(z2 || !z || seslSeekBar == null)) {
            int progress = seslSeekBar.getProgress();
            if (this.mEnableControlInSetting) {
                ((SemMdnieManager) this.mContext.getSystemService("mDNIe"))
                        .setNightMode(true, progress);
            } else {
                Settings.System.putInt(
                        this.mContext.getContentResolver(), "blue_light_filter_opacity", progress);
            }
            seslSeekBar.setStateDescription(
                    ((Object) SliderUtils.formatStateDescription(this.mContext, seslSeekBar))
                            + ", "
                            + this.mContext
                                    .getResources()
                                    .getString(R.string.sec_eye_comfort_color_temperature));
            LoggingHelper.insertEventLogging(4222, 4224, (long) seslSeekBar.getProgress());
        }
    }

    @Override // com.samsung.android.settings.display.DisplayCustomPreference,
              // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
    public final void onStartTrackingTouch(SeslSeekBar seslSeekBar) {
        this.mIsFocusable = true;
        boolean z = false;
        boolean z2 =
                Settings.System.getInt(this.mContext.getContentResolver(), "blue_light_filter", 0)
                        != 0;
        boolean z3 =
                Settings.System.getInt(
                                this.mContext.getContentResolver(),
                                "blue_light_filter_adaptive_mode",
                                0)
                        != 0;
        boolean z4 =
                Settings.System.getInt(
                                this.mContext.getContentResolver(),
                                "blue_light_filter_scheduled",
                                0)
                        != 0;
        int i =
                Settings.System.getInt(
                        this.mContext.getContentResolver(), "blue_light_filter_type", 0);
        if (z4 && i == 0) {
            z = true;
        }
        if (!z2 || z3 || !z4 || z || EyeComfortTimeUtils.duringScheduleTime(this.mContext, i)) {
            return;
        }
        this.mEnableControlInSetting = true;
    }

    @Override // com.samsung.android.settings.display.DisplayCustomPreference,
              // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
    public final void onStopTrackingTouch(SeslSeekBar seslSeekBar) {
        this.mIsFocusable = false;
        Settings.System.putInt(
                this.mContext.getContentResolver(),
                "blue_light_filter_opacity",
                seslSeekBar.getProgress());
        if (this.mEnableControlInSetting) {
            ((SemMdnieManager) this.mContext.getSystemService("mDNIe")).setNightMode(false, 0);
            this.mEnableControlInSetting = false;
            Log.i("EyeComfortSeekBarPreference", "onStopTrackingTouch disable!");
        }
    }

    @Override // com.samsung.android.settings.display.DisplayCustomPreference
    public final void setProgress(int i) {
        setProgress$2(i, true);
        SeslSeekBar seslSeekBar = this.mSeekBar;
        if (seslSeekBar != null) {
            seslSeekBar.setProgress(i);
        }
    }
}
