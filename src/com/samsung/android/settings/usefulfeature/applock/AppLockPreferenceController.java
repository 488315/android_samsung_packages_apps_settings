package com.samsung.android.settings.usefulfeature.applock;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceScreen;
import androidx.preference.SecPreferenceUtils;
import androidx.window.embedding.ActivityEmbeddingController;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.usefulfeature.Usefulfeature;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class AppLockPreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnResume, OnPause {
    private static final String KEY_APPLOCK = "key_applock";
    private static final String SPROTECT_PACKAGE_NAME = "com.samsung.android.app.sprotect";
    private ContentObserver mAppLockObserver;
    private Usefulfeature mHost;
    private SecPreferenceScreen mPreference;

    public AppLockPreferenceController(Context context) {
        this(context, KEY_APPLOCK);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SecPreferenceScreen secPreferenceScreen =
                (SecPreferenceScreen) preferenceScreen.findPreference(KEY_APPLOCK);
        this.mPreference = secPreferenceScreen;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        secPreferenceScreen.setTitle(R.string.applock_app_name);
        SecPreferenceScreen secPreferenceScreen2 = this.mPreference;
        secPreferenceScreen2.getClass();
        SecPreferenceUtils.applySummaryColor(secPreferenceScreen2, true);
        this.mAppLockObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.samsung.android.settings.usefulfeature.applock.AppLockPreferenceController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        if (AppLockPreferenceController.this.mPreference != null) {
                            AppLockPreferenceController appLockPreferenceController =
                                    AppLockPreferenceController.this;
                            appLockPreferenceController.updateState(
                                    appLockPreferenceController.mPreference);
                        }
                    }
                };
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        StringBuilder sb = Utils.sBuilder;
        return (Rune.SUPPORT_SMARTMANAGER_CN && UserHandle.myUserId() == 0) ? 0 : 3;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return this.mContext.getString(
                ((ActivityManager) this.mContext.getSystemService("activity")).isApplockEnabled()
                        ? R.string.switch_on_text
                        : R.string.switch_off_text);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (preference.equals(this.mPreference)) {
            if (isInMultiWindow(this.mHost.getActivity())) {
                Toast.makeText(
                                this.mContext,
                                R.string.lock_screen_doesnt_support_multi_window_text,
                                0)
                        .show();
            } else {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.MAIN");
                intent.setClassName(
                        "com.samsung.android.applock",
                        "com.samsung.android.applock.settings.AppLockSettingsActivity");
                this.mContext.startActivity(intent);
            }
        }
        return super.handlePreferenceTreeClick(preference);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    public boolean isInMultiWindow(Activity activity) {
        return activity.isInMultiWindowMode()
                && !(ActivityEmbeddingController.getInstance(activity).isActivityEmbedded(activity)
                        && activity.getResources().getConfiguration().windowConfiguration.getStage()
                                == 0);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public boolean isSliceable() {
        return getAvailabilityStatus() != 3;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        if (this.mPreference == null || this.mAppLockObserver == null) {
            return;
        }
        this.mContext.getContentResolver().unregisterContentObserver(this.mAppLockObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        SecPreferenceScreen secPreferenceScreen = this.mPreference;
        if (secPreferenceScreen != null) {
            updateState(secPreferenceScreen);
            if (this.mAppLockObserver != null) {
                this.mContext
                        .getContentResolver()
                        .registerContentObserver(
                                Settings.Secure.getUriFor("app_lock_enabled"),
                                true,
                                this.mAppLockObserver);
            }
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public AppLockPreferenceController(Context context, Usefulfeature usefulfeature) {
        this(context, KEY_APPLOCK);
        this.mHost = usefulfeature;
        if (usefulfeature != null) {
            usefulfeature.getLifecycle().addObserver(this);
        }
    }

    public AppLockPreferenceController(Context context, String str) {
        super(context, str);
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
