package com.samsung.android.settings.accessories.controller;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AutomaticUnlockController extends TogglePreferenceController
        implements PreferenceControllerMixin, LifecycleObserver, OnResume, OnPause {
    private static final String AUTOMATIC_UNLOCK_DB = "automatic_unlock";
    private Handler mHandler;
    private SecSwitchPreference mPreference;
    private SettingsObserver mSettingsObserver;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettingsObserver extends ContentObserver {
        public final Uri AUTO_SCREEN_ON;

        public SettingsObserver(Handler handler) {
            super(handler);
            this.AUTO_SCREEN_ON = Settings.System.getUriFor("auto_screen_on");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (AutomaticUnlockController.this.mPreference != null) {
                AutomaticUnlockController automaticUnlockController =
                        AutomaticUnlockController.this;
                automaticUnlockController.updateState(automaticUnlockController.mPreference);
            }
        }

        public final void setListening(boolean z) {
            if (z) {
                ((AbstractPreferenceController) AutomaticUnlockController.this)
                        .mContext
                        .getContentResolver()
                        .registerContentObserver(this.AUTO_SCREEN_ON, false, this);
            } else {
                ((AbstractPreferenceController) AutomaticUnlockController.this)
                        .mContext
                        .getContentResolver()
                        .unregisterContentObserver(this);
            }
        }
    }

    public AutomaticUnlockController(Context context, String str) {
        super(context, str);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mSettingsObserver = new SettingsObserver(this.mHandler);
    }

    private boolean isAutoScreenOnCheck() {
        return (!UsefulfeatureUtils.hasFeatureAutoScreenTurnOn(this.mContext)
                        && this.mContext
                                .getPackageManager()
                                .hasSystemFeature("com.sec.feature.nfc_authentication_cover"))
                || UsefulfeatureUtils.getTypeOfCover(this.mContext) != 0
                || (Settings.System.getInt(this.mContext.getContentResolver(), "auto_screen_on", 1)
                        != 0);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SecSwitchPreference secSwitchPreference =
                (SecSwitchPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = secSwitchPreference;
        if (secSwitchPreference != null) {
            updateState(secSwitchPreference);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (!UsefulfeatureUtils.isCoverVerified(this.mContext)
                        || UsefulfeatureUtils.getTypeOfCover(this.mContext) == 14
                        || SemPersonaManager.isKnoxId(UserHandle.myUserId()))
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
        return R.string.menu_key_advanced_features;
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
        return isAutoScreenOnCheck()
                && Settings.System.getInt(
                                this.mContext.getContentResolver(), AUTOMATIC_UNLOCK_DB, 1)
                        != 0;
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

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        SettingsObserver settingsObserver = this.mSettingsObserver;
        if (settingsObserver != null) {
            settingsObserver.setListening(false);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        SettingsObserver settingsObserver = this.mSettingsObserver;
        if (settingsObserver != null) {
            settingsObserver.setListening(true);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        Settings.System.putInt(this.mContext.getContentResolver(), AUTOMATIC_UNLOCK_DB, z ? 1 : 0);
        LoggingHelper.insertEventLogging(7610, 1052, z);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (isAutoScreenOnCheck()) {
            this.mPreference.setEnabled(true);
            this.mPreference.setChecked(
                    Settings.System.getInt(
                                    this.mContext.getContentResolver(), AUTOMATIC_UNLOCK_DB, 1)
                            != 0);
        } else {
            this.mPreference.setEnabled(false);
            this.mPreference.setChecked(false);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
