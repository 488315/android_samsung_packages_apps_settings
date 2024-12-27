package com.samsung.android.settings.usefulfeature.atttvmode;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class AttTvModePreferenceController extends TogglePreferenceController
        implements LifecycleObserver, OnResume, OnPause {
    private static final String KEY_ATT_TV_MODE = "att_tvmode_settings";
    public static final String POWER_DOUBLE_TAP_DB = "double_tab_launch";
    public static final int POWER_DOUBLE_TAP_OFF = 2;
    public static final int POWER_DOUBLE_TAP_ON = 3;
    private static final String TAG = "AttTvModePreferenceController";
    public static final String TVMODE_OWNER_PREV_STATUS = "tvmode_owner_status";
    public static final String TVMODE_STATUS_DB = "tvmode_state";
    public static final int TVMODE_STATUS_OFF = 0;
    public static final int TVMODE_STATUS_ON = 1;
    private SecSwitchPreferenceScreen mPreference;
    private ContentObserver mTvModeObserver;

    public AttTvModePreferenceController(Context context) {
        this(context, KEY_ATT_TV_MODE);
    }

    private boolean getTvmodeStatus() {
        if (UserHandle.semGetMyUserId() == 0) {
            int i = Settings.System.getInt(this.mContext.getContentResolver(), TVMODE_STATUS_DB, 0);
            int i2 =
                    Settings.System.getInt(
                            this.mContext.getContentResolver(), TVMODE_OWNER_PREV_STATUS, 0);
            if (i != i2) {
                setTvmodeStatus(i2);
            }
        }
        return Settings.System.getInt(this.mContext.getContentResolver(), TVMODE_STATUS_DB, 0) != 0;
    }

    private void setTvmodeStatus(int i) {
        int semGetMyUserId = UserHandle.semGetMyUserId();
        if (semGetMyUserId != 0) {
            Settings.System.semPutIntForUser(
                    this.mContext.getContentResolver(), TVMODE_STATUS_DB, i, 0);
        } else {
            Settings.System.putInt(this.mContext.getContentResolver(), TVMODE_OWNER_PREV_STATUS, i);
        }
        Settings.System.putInt(this.mContext.getContentResolver(), TVMODE_STATUS_DB, i);
        if (semGetMyUserId != 0) {
            Settings.System.semPutIntForUser(
                    this.mContext.getContentResolver(), POWER_DOUBLE_TAP_DB, i == 1 ? 3 : 2, 0);
        }
        Settings.System.putInt(
                this.mContext.getContentResolver(), POWER_DOUBLE_TAP_DB, i == 1 ? 3 : 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePreference(boolean z) {
        SecSwitchPreferenceScreen secSwitchPreferenceScreen = this.mPreference;
        if (secSwitchPreferenceScreen != null) {
            secSwitchPreferenceScreen.setChecked(z);
            if (z) {
                this.mPreference.setSummary((CharSequence) null);
            } else {
                this.mPreference.setSummary(R.string.err_tv_mode_off);
            }
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (SecSwitchPreferenceScreen) preferenceScreen.findPreference(KEY_ATT_TV_MODE);
        updatePreference(getThreadEnabled());
        this.mTvModeObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.samsung.android.settings.usefulfeature.atttvmode.AttTvModePreferenceController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        AttTvModePreferenceController attTvModePreferenceController =
                                AttTvModePreferenceController.this;
                        attTvModePreferenceController.updatePreference(
                                attTvModePreferenceController.getThreadEnabled());
                    }
                };
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (SemCscFeature.getInstance()
                                .getString(
                                        "CscFeature_SystemUI_ConfigDefQuickSettingItem",
                                        ApnSettings.MVNO_NONE)
                                .contains("TvMode")
                        && Utils.hasPackage(this.mContext, "com.samsung.tvmode"))
                ? 0
                : 3;
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        SecSwitchPreferenceScreen secSwitchPreferenceScreen = this.mPreference;
        if (secSwitchPreferenceScreen != null && preference.equals(secSwitchPreferenceScreen)) {
            try {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.MAIN");
                intent.setClassName(
                        "com.samsung.tvmode", "com.samsung.tvmode.activity.RecentAppsActivity");
                this.mContext.startActivity(intent);
                return true;
            } catch (ActivityNotFoundException e) {
                Log.secD(TAG, "ActivityNotFoundException in tvmode");
                e.printStackTrace();
            }
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
        return getTvmodeStatus();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean isSliceable() {
        return isAvailable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        if (this.mTvModeObserver != null) {
            this.mContext.getContentResolver().unregisterContentObserver(this.mTvModeObserver);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        updatePreference(getThreadEnabled());
        if (this.mTvModeObserver != null) {
            this.mContext
                    .getContentResolver()
                    .registerContentObserver(
                            Settings.System.getUriFor(TVMODE_STATUS_DB),
                            true,
                            this.mTvModeObserver);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        if (this.mPreference != null) {
            setTvmodeStatus(!getThreadEnabled() ? 1 : 0);
            updatePreference(!getThreadEnabled());
        }
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public AttTvModePreferenceController(Context context, String str) {
        super(context, str);
    }
}
