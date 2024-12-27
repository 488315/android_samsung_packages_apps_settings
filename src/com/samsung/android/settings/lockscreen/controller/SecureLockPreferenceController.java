package com.samsung.android.settings.lockscreen.controller;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.core.instrumentation.Instrumentable;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.lockscreen.LockUtils;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecureLockPreferenceController extends BasePreferenceController {
    public static final String SECURE_LOCK_SETTINGS =
            "com.samsung.android.settings.lockscreen.SecuredLockSettingsMenu";
    private static final String TAG = "SecureLockPreferenceController";
    private LockPatternUtils mLockPatternUtils;
    private Fragment mParentFragment;
    private Preference mPreference;

    public SecureLockPreferenceController(Context context, String str) {
        super(context, str);
        this.mLockPatternUtils = new LockPatternUtils(context);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private boolean startFragment(Fragment fragment, String str, int i, Bundle bundle) {
        FragmentActivity activity = this.mParentFragment.getActivity();
        if (!(activity instanceof SettingsActivity)) {
            Log.w(
                    TAG,
                    "Parent isn't SettingsActivity nor PreferenceActivity, thus there's no way to"
                        + " launch the given Fragment (name: "
                            + str
                            + ")");
            return false;
        }
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(activity);
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = str;
        launchRequest.mArguments = bundle;
        subSettingLauncher.setTitleRes(i, null);
        launchRequest.mSourceMetricsCategory =
                fragment instanceof Instrumentable
                        ? ((Instrumentable) fragment).getMetricsCategory()
                        : 0;
        subSettingLauncher.launch();
        activity.overridePendingTransition(0, 0);
        return true;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        boolean isLockSettingsBlockonDexMode =
                LockUtils.isLockSettingsBlockonDexMode(this.mContext);
        boolean isKioskModeEnabled = SemPersonaManager.isKioskModeEnabled(this.mContext);
        if (isLockSettingsBlockonDexMode || isKioskModeEnabled) {
            return 5;
        }
        return !this.mLockPatternUtils.isSecure(UserHandle.myUserId()) ? 4 : 0;
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
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!getPreferenceKey().equals(preference.getKey())) {
            return super.handlePreferenceTreeClick(preference);
        }
        if (LockUtils.isInMultiWindow(this.mParentFragment.getActivity())) {
            Toast.makeText(
                            this.mParentFragment.getActivity(),
                            R.string.lock_screen_doesnt_support_multi_window_text,
                            0)
                    .show();
            return true;
        }
        LoggingHelper.insertEventLogging(4400, 4432);
        startFragment(
                this.mParentFragment,
                SECURE_LOCK_SETTINGS,
                R.string.sec_unlock_set_unlock_launch_picker_title,
                preference.getExtras());
        return true;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public void init(Fragment fragment) {
        this.mParentFragment = fragment;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public void updatePreference() {
        this.mPreference.setVisible(isAvailable());
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
