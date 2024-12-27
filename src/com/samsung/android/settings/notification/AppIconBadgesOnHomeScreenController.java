package com.samsung.android.settings.notification;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceUtils;
import androidx.preference.SecSwitchPreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AppIconBadgesOnHomeScreenController extends TogglePreferenceController
        implements OnResume, OnPause {
    private static final String KEY_NOTIFICATION_BADGING = "notification_badging";
    private static final int OFF = 0;
    private static final int ON = 1;
    private static final String TAG = "AppIconBadgesPref";
    private SecSwitchPreferenceScreen mPreference;
    private SettingObserver mSettingObserver;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettingObserver extends ContentObserver {
        public final Uri NOTIFICATION_BADGING_URI;
        public final Preference mPreference;

        public SettingObserver(Preference preference) {
            super(new Handler());
            this.NOTIFICATION_BADGING_URI =
                    Settings.Secure.getUriFor(
                            AppIconBadgesOnHomeScreenController.KEY_NOTIFICATION_BADGING);
            this.mPreference = preference;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            if (this.NOTIFICATION_BADGING_URI.equals(uri)) {
                AppIconBadgesOnHomeScreenController.this.updateState(this.mPreference);
            }
        }
    }

    public AppIconBadgesOnHomeScreenController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(KEY_NOTIFICATION_BADGING);
        if (findPreference != null) {
            this.mSettingObserver = new SettingObserver(findPreference);
            SecSwitchPreferenceScreen secSwitchPreferenceScreen =
                    (SecSwitchPreferenceScreen) findPreference;
            this.mPreference = secSwitchPreferenceScreen;
            SecPreferenceUtils.applySummaryColor(secSwitchPreferenceScreen, true);
        }
    }

    public CharSequence getAppIconBadgeSummary(boolean z) {
        if (!z) {
            return ApnSettings.MVNO_NONE;
        }
        return this.mContext.getString(
                NotiUtils.getBadgeAppIconType(this.mContext) == 0
                        ? R.string.notification_badge_app_number
                        : R.string.notification_badge_app_dot);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (SemPersonaManager.isKnoxId(UserHandle.myUserId())
                && !SemPersonaManager.isKioskModeEnabled(this.mContext)
                && !SemPersonaManager.isSecureFolderId(UserHandle.myUserId())) {
            return 4;
        }
        if (UserHandle.myUserId() == 0 && SemPersonaManager.isKioskModeEnabled(this.mContext)) {
            return 4;
        }
        return Settings.System.getInt(this.mContext.getContentResolver(), "minimal_battery_use", 0)
                        == 1
                ? 5
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY_NOTIFICATION_BADGING;
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
        return Settings.Secure.getIntForUser(
                        this.mContext.getContentResolver(), KEY_NOTIFICATION_BADGING, 1, -2)
                == 1;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        SettingObserver settingObserver = this.mSettingObserver;
        if (settingObserver != null) {
            ContentResolver contentResolver = this.mContext.getContentResolver();
            settingObserver.getClass();
            contentResolver.unregisterContentObserver(settingObserver);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        SettingObserver settingObserver = this.mSettingObserver;
        if (settingObserver != null) {
            this.mContext
                    .getContentResolver()
                    .registerContentObserver(
                            settingObserver.NOTIFICATION_BADGING_URI, false, settingObserver);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        LoggingHelper.insertEventLogging(4050, 4052, z ? 1L : 0L);
        SecSwitchPreferenceScreen secSwitchPreferenceScreen = this.mPreference;
        if (secSwitchPreferenceScreen != null) {
            secSwitchPreferenceScreen.setSummary(getAppIconBadgeSummary(z));
        }
        return Settings.Secure.putIntForUser(
                this.mContext.getContentResolver(), KEY_NOTIFICATION_BADGING, z ? 1 : 0, -2);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        this.mPreference.setSummary(getAppIconBadgeSummary(getThreadEnabled()));
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
