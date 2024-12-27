package com.samsung.android.settings.display.controller;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.Signature;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.display.SecDisplayUtils;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecRestrictedSwitchPreference;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecExtraBrightnessPreferenceController extends TogglePreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    private static final int DISABLE_EXTRA_BRIGHTNESS = 0;
    private static final int ENABLE_EXTRA_BRIGHTNESS = 1;
    private static final String TAG = "SecExtraBrightnessPreferenceController";
    private final ContentObserver mAutoBrightnessObserver;
    private final ContentObserver mContentObserver;
    private SecRestrictedSwitchPreference mPreference;

    public SecExtraBrightnessPreferenceController(Context context, String str) {
        super(context, str);
        final int i = 0;
        this.mAutoBrightnessObserver =
                new ContentObserver(
                        this,
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.samsung.android.settings.display.controller.SecExtraBrightnessPreferenceController.1
                    public final /* synthetic */ SecExtraBrightnessPreferenceController this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        switch (i) {
                            case 0:
                                super.onChange(z);
                                SecExtraBrightnessPreferenceController
                                        secExtraBrightnessPreferenceController = this.this$0;
                                secExtraBrightnessPreferenceController.updateState(
                                        secExtraBrightnessPreferenceController.mPreference);
                                break;
                            default:
                                super.onChange(z);
                                SecExtraBrightnessPreferenceController
                                        secExtraBrightnessPreferenceController2 = this.this$0;
                                secExtraBrightnessPreferenceController2.updateState(
                                        secExtraBrightnessPreferenceController2.mPreference);
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mContentObserver =
                new ContentObserver(
                        this,
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.samsung.android.settings.display.controller.SecExtraBrightnessPreferenceController.1
                    public final /* synthetic */ SecExtraBrightnessPreferenceController this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        switch (i2) {
                            case 0:
                                super.onChange(z);
                                SecExtraBrightnessPreferenceController
                                        secExtraBrightnessPreferenceController = this.this$0;
                                secExtraBrightnessPreferenceController.updateState(
                                        secExtraBrightnessPreferenceController.mPreference);
                                break;
                            default:
                                super.onChange(z);
                                SecExtraBrightnessPreferenceController
                                        secExtraBrightnessPreferenceController2 = this.this$0;
                                secExtraBrightnessPreferenceController2.updateState(
                                        secExtraBrightnessPreferenceController2.mPreference);
                                break;
                        }
                    }
                };
    }

    private int getBrightnessMode() {
        return getBrightnessMode(0);
    }

    private ContentResolver getContentResolver() {
        return this.mContext.getContentResolver();
    }

    private boolean isDexMode() {
        return Rune.isSamsungDexMode(this.mContext) && Utils.isDesktopDualMode(this.mContext);
    }

    private void updateExtraBrightnessState(Preference preference) {
        if (isAvailable()) {
            boolean z = false;
            boolean z2 = getBrightnessMode() == 0;
            preference.setVisible(z2);
            if (z2 && !isDexMode()) {
                z = true;
            }
            preference.setEnabled(z);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        this.mPreference =
                (SecRestrictedSwitchPreference) preferenceScreen.findPreference(getPreferenceKey());
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        StringBuilder sb = new StringBuilder("SUPPORT_EXTRA_BRIGHT:");
        boolean z = Rune.SUPPORT_EXTRA_BRIGHT;
        sb.append(z);
        Log.secD(TAG, sb.toString());
        if (!z) {
            return 3;
        }
        RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced =
                RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                        this.mContext, UserHandle.myUserId(), "no_config_brightness");
        if (checkIfRestrictionEnforced == null) {
            return isDexMode() ? 5 : 0;
        }
        SecRestrictedSwitchPreference secRestrictedSwitchPreference = this.mPreference;
        if (secRestrictedSwitchPreference != null) {
            secRestrictedSwitchPreference.setDisabledByAdmin(checkIfRestrictionEnforced);
        }
        return 5;
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
        return R.string.menu_key_display;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return this.mContext.getString(R.string.extra_brightness_description);
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
        Context context = this.mContext;
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        int i = Settings.Secure.getInt(context.getContentResolver(), "screen_extra_brightness", 0);
        Log.secD("SecDisplayUtils", "getExtraBrightness = " + i);
        return i == 1;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("screen_brightness_mode"),
                        false,
                        this.mAutoBrightnessObserver);
        getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("screen_extra_brightness"),
                        false,
                        this.mContentObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mAutoBrightnessObserver);
        getContentResolver().unregisterContentObserver(this.mContentObserver);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        LoggingHelper.insertEventLogging(46, 40251, z ? 1L : 0L);
        Context context = this.mContext;
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        Log.secD("SecDisplayUtils", "setExtraBrightness : " + (z ? 1 : 0));
        Settings.Secure.putInt(context.getContentResolver(), "screen_extra_brightness", z ? 1 : 0);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (preference == null) {
            return;
        }
        updateExtraBrightnessState(preference);
        super.refreshSummary(preference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    private int getBrightnessMode(int i) {
        return Settings.System.getInt(
                this.mContext.getContentResolver(), "screen_brightness_mode", i);
    }
}
