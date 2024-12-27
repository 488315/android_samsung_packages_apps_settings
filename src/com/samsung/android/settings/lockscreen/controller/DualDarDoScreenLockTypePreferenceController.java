package com.samsung.android.settings.lockscreen.controller;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceScreen;
import androidx.preference.SecPreferenceUtils;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.core.instrumentation.Instrumentable;

import com.samsung.android.knox.dar.ddar.DualDarManager;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.lockscreen.LockUtils;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class DualDarDoScreenLockTypePreferenceController extends BasePreferenceController {
    public static final String CHOOSE_LOCK_GENERIC =
            "com.android.settings.password.ChooseLockGeneric$ChooseLockGenericFragment";
    public static final int SET_OR_CHANGE_LOCK_METHOD_REQUEST = 123;
    private static final String TAG = "DualDarDoScreenLockTypePreferenceController";
    private LockPatternUtils mLockPatternUtils;
    private Fragment mParentFragment;
    private SecPreferenceScreen mPreference;
    private int mRequestCode;
    private PreferenceScreen mScreen;
    private int mUserId;

    public DualDarDoScreenLockTypePreferenceController(Context context, String str) {
        super(context, str);
        Log.d(TAG, TAG);
        this.mUserId = KnoxUtils.getDualDarDoInnerAuthUserId(context);
        this.mLockPatternUtils = new LockPatternUtils(context);
    }

    private String getBiometricsSummary() {
        Log.d(TAG, "getBiometricsSummary");
        StringBuilder sb = new StringBuilder();
        int keyguardStoredPasswordQuality =
                this.mLockPatternUtils.getKeyguardStoredPasswordQuality(this.mUserId);
        if (keyguardStoredPasswordQuality == 65536
                || keyguardStoredPasswordQuality == 131072
                || keyguardStoredPasswordQuality == 196608) {
            sb.append(this.mContext.getString(R.string.sec_unlock_set_unlock_pin_title));
        } else if (keyguardStoredPasswordQuality == 262144
                || keyguardStoredPasswordQuality == 327680
                || keyguardStoredPasswordQuality == 393216) {
            sb.append(this.mContext.getString(R.string.sec_unlock_set_unlock_password_title));
        }
        return sb.toString();
    }

    private boolean isSupportMultiWindow() {
        Log.d(TAG, "isSupportMultiWindow");
        if (LockUtils.isSupportSubLockscreen()) {
            return true;
        }
        Toast.makeText(
                        this.mParentFragment.getActivity(),
                        R.string.lock_screen_doesnt_support_multi_window_text,
                        0)
                .show();
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private boolean startFragment(Fragment fragment, String str, int i, int i2, Bundle bundle) {
        String str2 = TAG;
        Log.d(str2, "startFragment called..");
        Fragment fragment2 = this.mParentFragment;
        if (!((fragment2 != null ? fragment2.getActivity() : null) instanceof SettingsActivity)) {
            Log.w(
                    str2,
                    "Parent isn't SettingsActivity nor PreferenceActivity, thus there's no way to"
                        + " launch the given Fragment (name: "
                            + str
                            + ", requestCode: "
                            + i2
                            + ")");
            return false;
        }
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = str;
        launchRequest.mArguments = bundle;
        subSettingLauncher.setResultListener(fragment, i2);
        subSettingLauncher.setTitleRes(i, null);
        launchRequest.mTitle = null;
        launchRequest.mSourceMetricsCategory =
                fragment instanceof Instrumentable
                        ? ((Instrumentable) fragment).getMetricsCategory()
                        : 0;
        subSettingLauncher.launch();
        return true;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Log.d(TAG, "displayPreference");
        this.mScreen = preferenceScreen;
        SecPreferenceScreen secPreferenceScreen =
                (SecPreferenceScreen) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = secPreferenceScreen;
        if (secPreferenceScreen != null) {
            secPreferenceScreen.getClass();
            SecPreferenceUtils.applySummaryColor(secPreferenceScreen, true);
            if (LockUtils.isLockMenuDisabledByEdm(this.mContext)) {
                this.mPreference.setEnabled(false);
            }
            if (this.mLockPatternUtils.isSecure(0)) {
                this.mPreference.setEnabled(true);
            } else {
                this.mPreference.setEnabled(false);
            }
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        String str = KnoxUtils.mDeviceType;
        if (DualDarManager.isOnDeviceOwnerEnabled()) {
            return !this.mLockPatternUtils.isSecure(0) ? 5 : 0;
        }
        Log.d(TAG, "disable DualDarDo pref controller");
        return 4;
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
        Log.d(TAG, "getSummary");
        return !this.mLockPatternUtils.isSecure(0)
                ? this.mContext.getString(R.string.lock_screen_smart_lock_disabled_summary)
                : !this.mLockPatternUtils.isSecure(this.mUserId)
                        ? this.mContext.getString(R.string.sec_unlock_set_unlock_off_title)
                        : getBiometricsSummary();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        String str = TAG;
        Log.d(str, "handlePreferenceTreeClick");
        Log.d(str, "getPreferenceKey() = " + getPreferenceKey());
        Log.d(str, "preference.getKey() = " + preference.getKey());
        Log.d(str, "mParentFragment = " + this.mParentFragment);
        if (getPreferenceKey().equals(preference.getKey())) {
            Fragment fragment = this.mParentFragment;
            if (fragment != null
                    && LockUtils.isInMultiWindow(fragment.getActivity())
                    && !isSupportMultiWindow()) {
                return true;
            }
            LoggingHelper.insertEventLogging(4400, "LSE4401");
            Bundle bundle = new Bundle();
            bundle.putBoolean("fromScreenLock", true);
            bundle.putBoolean("fromDualDarDoScreenLock", true);
            startFragment(
                    this.mParentFragment,
                    "com.android.settings.password.ChooseLockGeneric$ChooseLockGenericFragment",
                    R.string.sec_unlock_set_unlock_launch_picker_title,
                    123,
                    bundle);
        }
        return super.handlePreferenceTreeClick(preference);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public void init(Fragment fragment, int i) {
        Log.d(TAG, "init");
        this.mParentFragment = fragment;
        this.mRequestCode = i;
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
        displayPreference(this.mScreen);
        updateState(this.mPreference);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
