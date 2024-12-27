package com.samsung.android.settings.display;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SeslSeekBar;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;

import com.airbnb.lottie.LottieAnimationView;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SubBrightnessSeekBarPreference extends DisplayCustomPreference
        implements View.OnFocusChangeListener {
    public LottieAnimationView mBrightnessIcon;
    public final int[] mBrightnessLevels;
    public final ContentResolver mContentResolver;
    public final Context mContext;
    public boolean mIsRestricted;
    public SeslSeekBar mSeekBar;
    public final AnonymousClass1 mSubBrightnessObserver;

    /* JADX WARN: Type inference failed for: r8v1, types: [com.samsung.android.settings.display.SubBrightnessSeekBarPreference$1] */
    public SubBrightnessSeekBarPreference(Context context, AttributeSet attributeSet) {
        super(
                context,
                attributeSet,
                0,
                R.layout.sec_preference_seekbar_sub_brightness,
                R.id.sub_brightness_seekbar);
        this.mSubBrightnessObserver =
                new ContentObserver(
                        new Handler()) { // from class:
                                         // com.samsung.android.settings.display.SubBrightnessSeekBarPreference.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        SubBrightnessSeekBarPreference.this.onSubBrightnessChanged();
                    }
                };
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        this.mBrightnessLevels = context.getResources().getIntArray(17236323);
    }

    @Override // com.samsung.android.settings.display.DisplayCustomPreference,
              // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        boolean z = false;
        preferenceViewHolder.mDividerAllowedAbove = false;
        SeslSeekBar seslSeekBar =
                (SeslSeekBar) preferenceViewHolder.findViewById(R.id.sub_brightness_seekbar);
        if (isEnabled() && !this.mIsRestricted) {
            z = true;
        }
        seslSeekBar.setEnabled(z);
        if (seslSeekBar == this.mSeekBar) {
            return;
        }
        this.mSeekBar = seslSeekBar;
        seslSeekBar.setMode(5);
        this.mSeekBar.setMax(120);
        this.mSeekBar.setOnFocusChangeListener(this);
        this.mSeekBar.setSoundEffectsEnabled(true);
        this.mSeekBar.setOnKeyListener(this);
        LottieAnimationView lottieAnimationView =
                (LottieAnimationView) preferenceViewHolder.findViewById(R.id.brightness_icon);
        this.mBrightnessIcon = lottieAnimationView;
        lottieAnimationView.setAnimation("Brightness_icon.json");
        int i = Settings.System.getInt(this.mContentResolver, "sub_screen_brightness", 73);
        Settings.System.putInt(this.mContentResolver, "sub_screen_brightness", i);
        this.mSeekBar.setProgress(i);
        setContentDescriptionForAutomationTest$3(i);
        setBrightnessAnimation(i);
    }

    @Override // androidx.preference.Preference
    public final void onClick() {
        RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced;
        if (!this.mIsRestricted
                || (checkIfRestrictionEnforced =
                                RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                                        this.mContext,
                                        UserHandle.myUserId(),
                                        "no_config_brightness"))
                        == null) {
            return;
        }
        RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                this.mContext, checkIfRestrictionEnforced);
    }

    @Override // com.samsung.android.settings.display.DisplayCustomPreference,
              // android.view.View.OnKeyListener
    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        SeslSeekBar seslSeekBar;
        if (keyEvent.getAction() != 1 && keyEvent.getAction() != 0) {
            return false;
        }
        if ((i == 21 || i == 22) && (seslSeekBar = this.mSeekBar) != null) {
            return seslSeekBar.onKeyDown(i, keyEvent);
        }
        return false;
    }

    @Override // com.samsung.android.settings.display.DisplayCustomPreference,
              // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
    public final void onProgressChanged(SeslSeekBar seslSeekBar, int i, boolean z) {
        super.onProgressChanged(seslSeekBar, i, z);
        if (z) {
            if (UsefulfeatureUtils.isUniversalSwitchEnabled(this.mContext)) {
                Settings.System.putInt(this.mContentResolver, "sub_screen_brightness", i);
            }
            setBrightnessAnimation(i);
        }
    }

    @Override // com.samsung.android.settings.display.DisplayCustomPreference,
              // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
    public final void onStopTrackingTouch(SeslSeekBar seslSeekBar) {
        int progress = seslSeekBar.getProgress();
        int[] iArr = this.mBrightnessLevels;
        int i = Preference.DEFAULT_ORDER;
        int i2 = 0;
        for (int i3 : iArr) {
            int abs = Math.abs(i3 - progress);
            if (abs < i) {
                i2 = i3;
                i = abs;
            }
        }
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                "seek bar value = ",
                " , snap value = ",
                progress,
                i2,
                "SubBrightnessSeekBarPreference");
        Settings.System.putInt(this.mContentResolver, "sub_screen_brightness", i2);
        seslSeekBar.setProgress(i2);
        setContentDescriptionForAutomationTest$3(i2);
        setBrightnessAnimation(i2);
    }

    public final void onSubBrightnessChanged() {
        if (this.mSeekBar != null) {
            int i = Settings.System.getInt(this.mContentResolver, "sub_screen_brightness", 73);
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    i,
                    "onSubBrightnessChanged() brightnessValue: ",
                    "SubBrightnessSeekBarPreference");
            this.mSeekBar.setProgress(i);
            setContentDescriptionForAutomationTest$3(i);
            setBrightnessAnimation(i);
        }
    }

    public final void setBrightnessAnimation(int i) {
        float f = i / 120.0f;
        Log.d("SubBrightnessSeekBarPreference", "animationValue : " + f);
        this.mBrightnessIcon.setProgressInternal(f, true);
    }

    public final void setContentDescriptionForAutomationTest$3(int i) {
        if (this.mSeekBar == null || Utils.isTalkBackEnabled(this.mContext)) {
            return;
        }
        this.mSeekBar.setContentDescription(Integer.toString(i));
    }

    @Override // com.samsung.android.settings.display.DisplayCustomPreference,
              // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
    public final void onStartTrackingTouch(SeslSeekBar seslSeekBar) {}

    @Override // android.view.View.OnFocusChangeListener
    public final void onFocusChange(View view, boolean z) {}
}
