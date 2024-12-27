package com.samsung.android.settings.security;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreferenceScreen;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecScreenPinningPreferenceController extends TogglePreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    private SecSwitchPreferenceScreen mPreference;
    private ContentObserver mScreenPinningObserver;

    public SecScreenPinningPreferenceController(Context context, String str) {
        super(context, str);
    }

    private boolean isScreenLockUsed() {
        String string;
        String string2 = this.mContext.getString(R.string.screen_pinning_unlock_none);
        Context context = this.mContext;
        LockPatternUtils lockPatternUtils = new LockPatternUtils(context);
        int keyguardStoredPasswordQuality =
                lockPatternUtils.getKeyguardStoredPasswordQuality(UserHandle.myUserId());
        if (keyguardStoredPasswordQuality == 65536) {
            if (lockPatternUtils.isLockPatternEnabled(UserHandle.myUserId())) {
                string =
                        context.getString(
                                R.string.screen_pinning_unlock,
                                context.getString(R.string.sec_unlock_set_unlock_pattern_title));
            }
            string = context.getString(R.string.screen_pinning_unlock_none);
        } else if (keyguardStoredPasswordQuality == 131072
                || keyguardStoredPasswordQuality == 196608) {
            string =
                    context.getString(
                            R.string.screen_pinning_unlock,
                            context.getString(R.string.sec_unlock_set_unlock_pin_title));
        } else {
            if (keyguardStoredPasswordQuality == 262144
                    || keyguardStoredPasswordQuality == 327680
                    || keyguardStoredPasswordQuality == 393216) {
                string =
                        context.getString(
                                R.string.screen_pinning_unlock,
                                context.getString(R.string.sec_unlock_set_unlock_password_title));
            }
            string = context.getString(R.string.screen_pinning_unlock_none);
        }
        return Settings.Secure.getInt(
                        this.mContext.getContentResolver(),
                        "lock_to_app_exit_locked",
                        !string2.equalsIgnoreCase(string) ? 1 : 0)
                != 0;
    }

    private void setLockToAppEnabled(boolean z) {
        Settings.System.putInt(
                this.mContext.getContentResolver(), "lock_to_app_enabled", z ? 1 : 0);
        LoggingHelper.insertEventLogging(4503, 7885, z);
        if (!z || Rune.isJapanDCMModel()) {
            return;
        }
        Settings.Secure.putInt(
                this.mContext.getContentResolver(),
                "lock_to_app_exit_locked",
                isScreenLockUsed() ? 1 : 0);
        if (isScreenLockUsed()) {
            Settings.Secure.putInt(
                    this.mContext.getContentResolver(), "interaction_control_exit_locked", 1);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        SecSwitchPreferenceScreen secSwitchPreferenceScreen =
                (SecSwitchPreferenceScreen) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = secSwitchPreferenceScreen;
        updateState(secSwitchPreferenceScreen);
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (Rune.isSamsungDexMode(this.mContext.getApplicationContext())) {
            return 5;
        }
        return this.mContext.getResources().getBoolean(R.bool.config_show_screen_pinning_settings)
                ? 0
                : 2;
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
        return Settings.System.getInt(this.mContext.getContentResolver(), "lock_to_app_enabled", 0)
                != 0;
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
        if (this.mScreenPinningObserver == null) {
            this.mScreenPinningObserver =
                    new ContentObserver(
                            new Handler(
                                    Looper
                                            .getMainLooper())) { // from class:
                                                                 // com.samsung.android.settings.security.SecScreenPinningPreferenceController.1
                        @Override // android.database.ContentObserver
                        public final void onChange(boolean z) {
                            SecScreenPinningPreferenceController
                                    secScreenPinningPreferenceController =
                                            SecScreenPinningPreferenceController.this;
                            secScreenPinningPreferenceController.updateState(
                                    secScreenPinningPreferenceController.mPreference);
                        }
                    };
        }
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("lock_to_app_enabled"),
                        false,
                        this.mScreenPinningObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        if (this.mScreenPinningObserver != null) {
            this.mContext
                    .getContentResolver()
                    .unregisterContentObserver(this.mScreenPinningObserver);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        setLockToAppEnabled(z);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        boolean z;
        super.updateState(preference);
        boolean isSamsungDexMode = Rune.isSamsungDexMode(this.mContext);
        int enterprisePolicyEnabled =
                Utils.getEnterprisePolicyEnabled(
                        this.mContext,
                        "content://com.sec.knox.provider/RestrictionPolicy3",
                        "isScreenPinningAllowedAsUser",
                        new String[] {"false"});
        boolean z2 = false;
        if (enterprisePolicyEnabled == -1 || enterprisePolicyEnabled != 0) {
            z = true;
        } else {
            this.mPreference.setChecked(false);
            z = false;
        }
        SecSwitchPreferenceScreen secSwitchPreferenceScreen = this.mPreference;
        if (z && !isSamsungDexMode) {
            z2 = true;
        }
        secSwitchPreferenceScreen.setEnabled(z2);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
