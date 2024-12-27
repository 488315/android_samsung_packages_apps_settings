package com.samsung.android.settings.display.controller;

import android.animation.ValueAnimator;
import android.content.ContentResolver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.MathUtils;

import androidx.preference.PreferenceScreen;

import com.android.settings.Utils;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.RestrictedLockUtilsInternal;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.display.BrightnessManager;
import com.samsung.android.settings.display.BrightnessSeekBarPreference;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecBrightnessPreferenceController extends SecDisplayPreferenceBaseController
        implements PreferenceControllerMixin {
    public Context mContext;
    public BrightnessSeekBarPreference mPreference;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (BrightnessSeekBarPreference) preferenceScreen.findPreference("secbrightness");
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "secbrightness";
    }

    @Override // com.samsung.android.settings.display.controller.SecDisplayPreferenceBaseController
    public final ArrayList getUriListRequiringObservation() {
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(Settings.System.getUriFor("display_outdoor_mode"));
        return arrayList;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @Override // com.samsung.android.settings.display.controller.SecDisplayPreferenceBaseController
    public final void onPause() {
        super.onPause();
        BrightnessSeekBarPreference brightnessSeekBarPreference = this.mPreference;
        ContentResolver contentResolver = brightnessSeekBarPreference.mContentResolver;
        contentResolver.unregisterContentObserver(
                brightnessSeekBarPreference.mBrightnessModeObserver);
        contentResolver.unregisterContentObserver(
                brightnessSeekBarPreference.mAutoBrightnessDetailObserver);
        contentResolver.unregisterContentObserver(
                brightnessSeekBarPreference.mMaxBrightnessDialogObserver);
        BrightnessManager brightnessManager = brightnessSeekBarPreference.mBrightnessManager;
        ContentResolver contentResolver2 = brightnessManager.mContext.getContentResolver();
        contentResolver2.unregisterContentObserver(brightnessManager.mBrightnessObserver);
        contentResolver2.unregisterContentObserver(brightnessManager.mHBM_PMS_EnterObserver);
        brightnessManager.mContext.unregisterReceiver(
                brightnessManager.mMaxBrightnessChangedReceiver);
        BrightnessSeekBarPreference brightnessSeekBarPreference2 = this.mPreference;
        ValueAnimator valueAnimator = brightnessSeekBarPreference2.mSliderAnimator;
        if (valueAnimator == null || !valueAnimator.isRunning()) {
            return;
        }
        brightnessSeekBarPreference2.mSliderAnimator.end();
    }

    @Override // com.samsung.android.settings.display.controller.SecDisplayPreferenceBaseController
    public final void onResume() {
        super.onResume();
        if (RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                        this.mContext, UserHandle.myUserId(), "no_config_brightness")
                != null) {
            BrightnessSeekBarPreference brightnessSeekBarPreference = this.mPreference;
            brightnessSeekBarPreference.mIsRestricted = true;
            brightnessSeekBarPreference.notifyChanged();
        }
        BrightnessSeekBarPreference brightnessSeekBarPreference2 = this.mPreference;
        ContentResolver contentResolver = brightnessSeekBarPreference2.mContentResolver;
        contentResolver.registerContentObserver(
                Settings.System.getUriFor("screen_brightness_mode"),
                true,
                brightnessSeekBarPreference2.mBrightnessModeObserver);
        contentResolver.registerContentObserver(
                Settings.System.getUriFor("auto_brightness_detail"),
                true,
                brightnessSeekBarPreference2.mAutoBrightnessDetailObserver);
        contentResolver.registerContentObserver(
                Settings.System.getUriFor("shown_max_brightness_dialog"),
                true,
                brightnessSeekBarPreference2.mMaxBrightnessDialogObserver);
        BrightnessManager brightnessManager = brightnessSeekBarPreference2.mBrightnessManager;
        ContentResolver contentResolver2 = brightnessManager.mContext.getContentResolver();
        contentResolver2.registerContentObserver(
                Settings.System.getUriFor("screen_brightness"),
                true,
                brightnessManager.mBrightnessObserver);
        contentResolver2.registerContentObserver(
                Settings.System.getUriFor("high_brightness_mode_pms_enter"),
                true,
                brightnessManager.mHBM_PMS_EnterObserver);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.intent.action.MAX_BRIGHTNESS_CHANGED");
        brightnessManager.mContext.registerReceiver(
                brightnessManager.mMaxBrightnessChangedReceiver, intentFilter, 2);
        BrightnessSeekBarPreference brightnessSeekBarPreference3 = this.mPreference;
        if (brightnessSeekBarPreference3.mBrightnessSeekBar != null) {
            if (brightnessSeekBarPreference3.mInternalChange) {
                int currentProgress = brightnessSeekBarPreference3.getCurrentProgress();
                brightnessSeekBarPreference3.updateProgress(
                        MathUtils.lerpInv(0.0f, 2.6738688E8f, currentProgress), currentProgress);
                brightnessSeekBarPreference3.mInternalChange = false;
            } else {
                brightnessSeekBarPreference3.animateSliderTo(
                        brightnessSeekBarPreference3.getCurrentProgress());
            }
        }
        this.mPreference.onBrightnessModeChanged();
        this.mPreference.notifyHierarchyChanged();
        this.mPreference.mInitFinish = true;
    }

    @Override // com.samsung.android.settings.display.controller.SecDisplayPreferenceBaseController
    public final void updatePreference$11() {
        if (this.mPreference == null) {
            return;
        }
        boolean z = false;
        boolean z2 = Settings.System.getInt(this.mContentResolver, "display_outdoor_mode", 0) != 0;
        boolean z3 = Rune.isSamsungDexMode(this.mContext) && Utils.isDesktopDualMode(this.mContext);
        BrightnessSeekBarPreference brightnessSeekBarPreference = this.mPreference;
        if (!z3 && !z2) {
            z = true;
        }
        brightnessSeekBarPreference.setEnabled(z);
    }
}
