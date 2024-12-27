package com.samsung.android.settings.usefulfeature.dailyboard;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.secutil.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class DailyBoardPreferenceController extends TogglePreferenceController
        implements LifecycleObserver, OnResume, OnPause {
    private static final String KEY_DAILY_BOARD = "daily_board_setting";
    private static final String TAG = "DailyBoardPreferenceController";
    private ContentObserver mDailyBoardObserver;
    private SecSwitchPreferenceScreen mPreference;

    public DailyBoardPreferenceController(Context context) {
        this(context, KEY_DAILY_BOARD);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePreferenceSummary(boolean z) {
        if (this.mPreference != null) {
            if (getThreadEnabled()) {
                this.mPreference.setSummary((CharSequence) null);
            } else {
                this.mPreference.setSummary(R.string.sec_daily_board_setting_summary);
            }
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        this.mPreference =
                (SecSwitchPreferenceScreen) preferenceScreen.findPreference(KEY_DAILY_BOARD);
        super.displayPreference(preferenceScreen);
        updatePreferenceSummary(getThreadEnabled());
        this.mDailyBoardObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.samsung.android.settings.usefulfeature.dailyboard.DailyBoardPreferenceController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        if (DailyBoardPreferenceController.this.mPreference != null) {
                            if (DailyBoardPreferenceController.this.getThreadEnabled()
                                    != DailyBoardPreferenceController.this.mPreference.mChecked) {
                                DailyBoardPreferenceController.this.mPreference.setChecked(
                                        DailyBoardPreferenceController.this.getThreadEnabled());
                            }
                            DailyBoardPreferenceController dailyBoardPreferenceController =
                                    DailyBoardPreferenceController.this;
                            dailyBoardPreferenceController.updatePreferenceSummary(
                                    dailyBoardPreferenceController.getThreadEnabled());
                        }
                    }
                };
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        Context context = this.mContext;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        return (TextUtils.isEmpty(
                                SemFloatingFeature.getInstance()
                                        .getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_DAILYBOARD"))
                        || !Utils.hasPackage(context, "com.samsung.android.homemode"))
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        SecSwitchPreferenceScreen secSwitchPreferenceScreen = this.mPreference;
        if (secSwitchPreferenceScreen != null && preference.equals(secSwitchPreferenceScreen)) {
            try {
                this.mContext.startActivity(
                        new Intent()
                                .setClassName(
                                        "com.samsung.android.homemode",
                                        "com.samsung.android.homemode.ui.activity.setting.HomeModeSettingsActivity"));
                return true;
            } catch (ActivityNotFoundException e) {
                Log.secD(TAG, "ActivityNotFoundException in homemode");
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
        return Settings.System.getInt(this.mContext.getContentResolver(), "home_mode_master", 1)
                != 0;
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
        if (this.mPreference == null || this.mDailyBoardObserver == null) {
            return;
        }
        this.mContext.getContentResolver().unregisterContentObserver(this.mDailyBoardObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        if (this.mPreference != null) {
            updatePreferenceSummary(getThreadEnabled());
            if (this.mDailyBoardObserver != null) {
                this.mContext
                        .getContentResolver()
                        .registerContentObserver(
                                Settings.System.getUriFor("home_mode_master"),
                                true,
                                this.mDailyBoardObserver);
            }
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        Settings.System.putInt(this.mContext.getContentResolver(), "home_mode_master", z ? 1 : 0);
        updatePreferenceSummary(getThreadEnabled());
        Intent intent = new Intent("com.samsung.android.homemode.MASTER_SWITCH_ON");
        intent.putExtra("switch_status", z ? 1 : 0);
        intent.setPackage("com.samsung.android.homemode");
        this.mContext.sendBroadcast(intent);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public DailyBoardPreferenceController(Context context, String str) {
        super(context, str);
    }
}
