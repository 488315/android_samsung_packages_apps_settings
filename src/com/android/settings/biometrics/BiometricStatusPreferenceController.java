package com.android.settings.biometrics;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;

import androidx.activity.result.ActivityResultLauncher;
import androidx.preference.Preference;

import com.android.internal.app.UnlaunchableAppActivity;
import com.android.internal.widget.LockPatternUtils;
import com.android.settings.Utils;
import com.android.settings.biometrics.activeunlock.ActiveUnlockStatusUtils;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BiometricStatusPreferenceController extends BasePreferenceController {
    private final ActiveUnlockStatusUtils mActiveUnlockStatusUtils;
    private final BiometricNavigationUtils mBiometricNavigationUtils;
    private WeakReference<ActivityResultLauncher> mLauncherWeakReference;
    protected final LockPatternUtils mLockPatternUtils;
    protected final int mProfileChallengeUserId;
    protected final UserManager mUm;
    private final int mUserId;

    public BiometricStatusPreferenceController(Context context, String str) {
        super(context, str);
        int myUserId = UserHandle.myUserId();
        this.mUserId = myUserId;
        this.mLauncherWeakReference = new WeakReference<>(null);
        UserManager userManager = (UserManager) context.getSystemService("user");
        this.mUm = userManager;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mLockPatternUtils =
                featureFactoryImpl.getSecurityFeatureProvider().getLockPatternUtils(context);
        this.mProfileChallengeUserId = Utils.getManagedProfileId(userManager, myUserId);
        this.mBiometricNavigationUtils = new BiometricNavigationUtils(getUserId());
        this.mActiveUnlockStatusUtils = new ActiveUnlockStatusUtils(context);
    }

    private int getAvailabilityFromUserSupported() {
        return isUserSupported() ? 0 : 4;
    }

    private int getAvailabilityStatusWithWorkProfileCheck() {
        if (!isHardwareSupported()) {
            return 3;
        }
        if (isDeviceSupported() || !isWorkProfileController()) {
            return getAvailabilityFromUserSupported();
        }
        return 3;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (this.mActiveUnlockStatusUtils.isAvailable()) {
            return getAvailabilityStatusWithWorkProfileCheck();
        }
        if (isDeviceSupported()) {
            return getAvailabilityFromUserSupported();
        }
        return 3;
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

    public abstract String getSettingsClassName();

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    public abstract String getSummaryText();

    public int getUserId() {
        return this.mUserId;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return super.handlePreferenceTreeClick(preference);
        }
        BiometricNavigationUtils biometricNavigationUtils = this.mBiometricNavigationUtils;
        Context context = preference.getContext();
        String settingsClassName = getSettingsClassName();
        Bundle extras = preference.getExtras();
        ActivityResultLauncher activityResultLauncher = this.mLauncherWeakReference.get();
        biometricNavigationUtils.getClass();
        UserManager userManager = UserManager.get(context);
        int i = biometricNavigationUtils.mUserId;
        Intent createInQuietModeDialogIntent =
                userManager.isQuietModeEnabled(UserHandle.of(i))
                        ? UnlaunchableAppActivity.createInQuietModeDialogIntent(i)
                        : null;
        if (createInQuietModeDialogIntent != null) {
            context.startActivity(createInQuietModeDialogIntent);
            return false;
        }
        Intent settingsPageIntent =
                biometricNavigationUtils.getSettingsPageIntent(extras, settingsClassName);
        if (activityResultLauncher != null) {
            activityResultLauncher.launch(settingsPageIntent);
        } else {
            context.startActivity(settingsPageIntent);
        }
        return true;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    public abstract boolean isDeviceSupported();

    public abstract boolean isHardwareSupported();

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    public boolean isUserSupported() {
        return true;
    }

    public boolean isWorkProfileController() {
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

    public boolean setPreferenceTreeClickLauncher(
            Preference preference, ActivityResultLauncher activityResultLauncher) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        this.mLauncherWeakReference = new WeakReference<>(activityResultLauncher);
        return true;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        if (preference == null) {
            return;
        }
        if (!isAvailable()) {
            preference.setVisible(false);
            return;
        }
        preference.setVisible(true);
        preference.setSummary(getSummaryText());
        setSummaryColor(preference);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}

    public void setSummaryColor(Preference preference) {}
}
