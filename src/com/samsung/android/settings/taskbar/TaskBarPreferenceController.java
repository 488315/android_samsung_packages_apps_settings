package com.samsung.android.settings.taskbar;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.ContentObserver;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class TaskBarPreferenceController extends TogglePreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    public static final String KEY_TASK_BAR_SETTING = "task_bar";
    private static final String RESTORE_FOR_NEW_DEX = "restore_for_newdex";
    private static final String SAMSUNG_LAUNCHER_PACKAGE = "com.sec.android.app.launcher";
    private static final String TAG = "TaskBarPreferenceController";
    private final int EASY_MODE;
    private final int STANDARD_MODE;
    private final String TASK_BAR_REFRESH;
    private final Uri TASK_BAR_REFRESH_URI;
    private Context mContext;
    private DisplayManager mDisplayManager;
    private SecSwitchPreferenceScreen mPreference;
    private ContentObserver mRefreshObserver;
    private SharedPreferences pref;

    public TaskBarPreferenceController(Context context, String str) {
        super(context, str);
        this.TASK_BAR_REFRESH = "task_bar_refresh";
        this.TASK_BAR_REFRESH_URI = Settings.Global.getUriFor("task_bar_refresh");
        this.EASY_MODE = 0;
        this.STANDARD_MODE = 1;
        this.mRefreshObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.samsung.android.settings.taskbar.TaskBarPreferenceController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        Log.i(TaskBarPreferenceController.TAG, "onChange: refresh task bar pref");
                        TaskBarPreferenceController taskBarPreferenceController =
                                TaskBarPreferenceController.this;
                        taskBarPreferenceController.updateState(
                                taskBarPreferenceController.mPreference);
                    }
                };
        this.mContext = context;
        this.mDisplayManager = (DisplayManager) context.getSystemService(DisplayManager.class);
    }

    private boolean isDexMode() {
        return Rune.isSamsungDexMode(this.mContext);
    }

    private boolean isFrontDisplay() {
        return this.mContext.getResources().getConfiguration().semDisplayDeviceType == 5;
    }

    private boolean isSamsungLauncher() {
        PackageManager packageManager = this.mContext.getPackageManager();
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        ResolveInfo resolveActivity = packageManager.resolveActivity(intent, 65536);
        if (resolveActivity == null) {
            return false;
        }
        String str = resolveActivity.activityInfo.packageName;
        Log.i(TAG, "Home app package: " + str);
        return SAMSUNG_LAUNCHER_PACKAGE.equals(str);
    }

    private boolean isSmartViewEnabled() {
        return this.mDisplayManager.getWifiDisplayStatus().getActiveDisplayState() == 2;
    }

    private boolean isTaskBarPrefDisabled() {
        boolean z =
                Settings.System.getInt(this.mContext.getContentResolver(), "easy_mode_switch", 1)
                        == 0;
        boolean isFrontDisplay = isFrontDisplay();
        boolean isSamsungLauncher = isSamsungLauncher();
        boolean isSmartViewEnabled = isSmartViewEnabled();
        String str = TAG;
        StringBuilder m =
                Utils$$ExternalSyntheticOutline0.m(
                        "isEasyModeEnabled: ",
                        z,
                        " isFrontDisplay: ",
                        isFrontDisplay,
                        " isSamsungLauncher: ");
        m.append(isSamsungLauncher);
        m.append(" isSmartView: ");
        m.append(isSmartViewEnabled);
        Log.i(str, m.toString());
        return z || isFrontDisplay || !isSamsungLauncher || isSmartViewEnabled;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        this.mPreference =
                (SecSwitchPreferenceScreen) preferenceScreen.findPreference(KEY_TASK_BAR_SETTING);
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (Rune.supportTaskBar(this.mContext)) {
            return isTaskBarPrefDisabled() ? 5 : 0;
        }
        return 3;
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
        return Settings.Global.getInt(this.mContext.getContentResolver(), KEY_TASK_BAR_SETTING, 1)
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

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        this.mContext
                .getContentResolver()
                .registerContentObserver(this.TASK_BAR_REFRESH_URI, false, this.mRefreshObserver);
        this.pref = this.mContext.getSharedPreferences("display_pref", 0);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mRefreshObserver);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        AbsAdapter$$ExternalSyntheticOutline0.m("TaskBar Switch is clicked to -> ", TAG, z);
        Settings.Global.putInt(this.mContext.getContentResolver(), KEY_TASK_BAR_SETTING, z ? 1 : 0);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        if (Settings.System.getInt(this.mContext.getContentResolver(), "easy_mode_switch", 1)
                == 0) {
            this.mPreference.setEnabled(false);
            preference.setSummary(
                    this.mContext
                            .getResources()
                            .getString(R.string.edge_panels_not_supported_in_easy_mode));
        } else if (TaskBarUtils.isFrontDisplay(this.mContext)) {
            this.mPreference.setEnabled(false);
            preference.setSummary(
                    this.mContext
                            .getResources()
                            .getString(R.string.edge_panels_not_supported_in_winner_cover_screen));
        } else if (Settings.System.getInt(
                        this.mContext.getContentResolver(), "minimal_battery_use", 0)
                == 1) {
            this.mPreference.setEnabled(false);
        } else if (!TaskBarUtils.isSamsungLauncher(this.mContext)) {
            this.mPreference.setEnabled(false);
        } else if (TaskBarUtils.isSmartViewEnabled(this.mContext)) {
            this.mPreference.setEnabled(false);
        } else if (Utils.getEnterprisePolicyEnabled(
                        this.mContext,
                        "content://com.sec.knox.provider2/KioskMode",
                        "isNavigationBarHidden")
                == 1) {
            this.mPreference.setEnabled(false);
        } else if (isDexMode()) {
            this.mPreference.setEnabled(false);
        } else {
            preference.setEnabled(true);
            preference.setSummary((CharSequence) null);
        }
        super.updateState(preference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
