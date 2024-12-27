package com.samsung.android.settings.usefulfeature.gamelauncher;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class GameLauncherPreferenceController extends TogglePreferenceController {
    private static final String ACTION_GAMEHOME_ENABLE =
            "com.samsung.android.game.action.GAME_TOOLS.GAMELAUNCHER_ENABLE";
    private static final String EXTRA_ENABLE_FROM_SETTING = "enable_via_setting";
    private static final String KEY_EVENT_TYPE = "key_event_type";
    private static final String KEY_GAME_LAUNCHER_ENABLE = "game_launcher";
    private static final int PACKAGE_INSTALLED_DISABLED_PRIV_APP = 2;
    private static final int PACKAGE_INSTALLED_DOWNLOADABLE = 1;
    private static final int PACKAGE_INSTALLED_ENABLED_PRIV_APP = 3;
    private static final String PACKAGE_NAME = "com.samsung.android.game.gamehome";
    private static final int PACKAGE_UNINSTALLED = 0;
    private static final String SKEY_GAME_HOME_ENABLE = "game_home_enable";
    private ContentResolver contentResolver;
    private PackageManager packageManager;
    private SecSwitchPreference switchPreference;

    public GameLauncherPreferenceController(Context context) {
        super(context, KEY_GAME_LAUNCHER_ENABLE);
        this.contentResolver = context.getContentResolver();
        this.packageManager = context.getPackageManager();
    }

    private int getPackageStatus() {
        try {
            if ((this.packageManager.getPackageInfo(PACKAGE_NAME, 128).applicationInfo.flags & 1)
                    == 0) {
                return 1;
            }
            int applicationEnabledSetting =
                    this.packageManager.getApplicationEnabledSetting(PACKAGE_NAME);
            return (applicationEnabledSetting == 0 || applicationEnabledSetting == 1) ? 3 : 2;
        } catch (Exception unused) {
            return 0;
        }
    }

    private boolean isGameLauncherEnabled() {
        boolean isPackageEnabled = isPackageEnabled();
        if (isSecureEnabled() != isPackageEnabled) {
            setSecureEnabled(isPackageEnabled);
        }
        return isPackageEnabled;
    }

    private boolean isGameLauncherPreloaded() {
        int packageStatus = getPackageStatus();
        return packageStatus == 2 || packageStatus == 3;
    }

    private boolean isPackageEnabled() {
        try {
            int applicationEnabledSetting =
                    this.packageManager.getApplicationEnabledSetting(PACKAGE_NAME);
            return applicationEnabledSetting == 0 || applicationEnabledSetting == 1;
        } catch (Exception unused) {
            return false;
        }
    }

    private boolean isSecureEnabled() {
        return Settings.Secure.getInt(this.contentResolver, SKEY_GAME_HOME_ENABLE, 0) == 1;
    }

    private boolean isUserOwner() {
        return UserHandle.semGetMyUserId() == 0;
    }

    private void logGameLauncherEnabled(boolean z) {
        LoggingHelper.insertEventLogging(4350, 7630, z);
    }

    private void sendGameLauncherEnableIntent() {
        this.mContext.startForegroundService(
                new Intent(ACTION_GAMEHOME_ENABLE)
                        .setPackage(PACKAGE_NAME)
                        .putExtra(KEY_EVENT_TYPE, EXTRA_ENABLE_FROM_SETTING));
    }

    private void setGameLauncherEnabled(boolean z) {
        this.packageManager.setApplicationEnabledSetting(PACKAGE_NAME, z ? 0 : 3, 0);
        setSecureEnabled(z);
    }

    private void setSecureEnabled(boolean z) {
        Settings.Secure.putInt(this.contentResolver, SKEY_GAME_HOME_ENABLE, z ? 1 : 0);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.switchPreference =
                (SecSwitchPreference) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (isGameLauncherPreloaded() && isUserOwner()) ? 0 : 3;
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
        return isGameLauncherEnabled();
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
        setGameLauncherEnabled(z);
        logGameLauncherEnabled(z);
        if (!z) {
            return true;
        }
        sendGameLauncherEnableIntent();
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
