package com.samsung.android.settings.lockscreen.controller;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.fingerprint.FingerprintManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.SecSwitchPreference;

import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.security.SecurityUtils;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ScreenTransitionEffectPreferenceController extends TogglePreferenceController {
    private static final String KEY_SCREEN_TRANSITION_EFFECT = "screen_transition_effect";

    public ScreenTransitionEffectPreferenceController(Context context) {
        this(context, KEY_SCREEN_TRANSITION_EFFECT);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (!SecurityUtils.isBiometricsLockEnabled(this.mContext)) {
            return 5;
        }
        Context context = this.mContext;
        if (Utils.isGuestUser(context) || Rune.isShopDemo(context)) {
            return 3;
        }
        return (SecurityUtils.isFaceDisabled(this.mContext, UserHandle.myUserId())
                        && SecurityUtils.isFingerprintDisabled(
                                this.mContext, UserHandle.myUserId()))
                ? 3
                : 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        if (preference.getKey().equals(getPreferenceKey())) {
            LoggingHelper.insertEventLogging(9031, 8503, getThreadEnabled());
        }
        return super.handlePreferenceTreeClick(preference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        int semGetTransitionEffectValue = FingerprintManager.semGetTransitionEffectValue();
        if (semGetTransitionEffectValue == -1) {
            semGetTransitionEffectValue = 1;
        }
        return Settings.System.getInt(
                        this.mContext.getContentResolver(),
                        KEY_SCREEN_TRANSITION_EFFECT,
                        semGetTransitionEffectValue)
                == 1;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        Settings.System.putInt(
                this.mContext.getContentResolver(), KEY_SCREEN_TRANSITION_EFFECT, z ? 1 : 0);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        preference.setEnabled(SecurityUtils.isBiometricsLockEnabled(this.mContext));
        ((SecSwitchPreference) preference).setChecked(getThreadEnabled());
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public ScreenTransitionEffectPreferenceController(Context context, String str) {
        super(context, str);
    }
}
