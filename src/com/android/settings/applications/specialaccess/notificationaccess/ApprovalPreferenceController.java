package com.android.settings.applications.specialaccess.notificationaccess;

import android.R;
import android.app.AppOpsManager;
import android.app.Flags;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.android.settings.core.BasePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.RestrictedPreferenceHelper;
import com.android.settingslib.RestrictedSwitchPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ApprovalPreferenceController extends BasePreferenceController {
    private static final String TAG = "ApprovalPrefController";
    private ComponentName mCn;
    private NotificationManager mNm;
    private PreferenceFragmentCompat mParent;
    private PackageInfo mPkgInfo;
    private PackageManager mPm;
    private String mSettingIdentifier;

    public ApprovalPreferenceController(Context context, String str) {
        super(context, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$updateState$0(
            CharSequence charSequence, Preference preference, Object obj) {
        if (((Boolean) obj).booleanValue()) {
            if (isServiceEnabled(this.mCn)) {
                return true;
            }
            ScaryWarningDialogFragment scaryWarningDialogFragment =
                    new ScaryWarningDialogFragment();
            scaryWarningDialogFragment.setServiceInfo$1(this.mCn, charSequence, this.mParent);
            scaryWarningDialogFragment.show(this.mParent.getFragmentManager(), "dialog");
            return false;
        }
        if (!isServiceEnabled(this.mCn)) {
            return true;
        }
        FriendlyWarningDialogFragment friendlyWarningDialogFragment =
                new FriendlyWarningDialogFragment();
        friendlyWarningDialogFragment.setServiceInfo(this.mCn, charSequence, this.mParent);
        friendlyWarningDialogFragment.show(this.mParent.getFragmentManager(), "friendlydialog");
        return false;
    }

    public void disable(ComponentName componentName) {
        logSpecialPermissionChange(true, componentName.getPackageName());
        this.mNm.setNotificationListenerAccessGranted(componentName, false);
        if (this.mNm.isNotificationPolicyAccessGrantedForPackage(componentName.getPackageName())) {
            return;
        }
        if (Flags.modesApi()) {
            this.mNm.removeAutomaticZenRules(componentName.getPackageName(), true);
        } else {
            this.mNm.removeAutomaticZenRules(componentName.getPackageName());
        }
    }

    public void enable(ComponentName componentName) {
        logSpecialPermissionChange(true, componentName.getPackageName());
        this.mNm.setNotificationListenerAccessGranted(componentName, true);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    public boolean isServiceEnabled(ComponentName componentName) {
        return this.mNm.isNotificationListenerAccessGranted(componentName);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    public void logSpecialPermissionChange(boolean z, String str) {
        int i = z ? 776 : 777;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getMetricsFeatureProvider().action(this.mContext, i, str);
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

    public ApprovalPreferenceController setCn(ComponentName componentName) {
        this.mCn = componentName;
        return this;
    }

    public ApprovalPreferenceController setNm(NotificationManager notificationManager) {
        this.mNm = notificationManager;
        return this;
    }

    public ApprovalPreferenceController setParent(
            PreferenceFragmentCompat preferenceFragmentCompat) {
        this.mParent = preferenceFragmentCompat;
        return this;
    }

    public ApprovalPreferenceController setPkgInfo(PackageInfo packageInfo) {
        this.mPkgInfo = packageInfo;
        return this;
    }

    public ApprovalPreferenceController setPm(PackageManager packageManager) {
        this.mPm = packageManager;
        return this;
    }

    public ApprovalPreferenceController setSettingIdentifier(String str) {
        this.mSettingIdentifier = str;
        return this;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        RestrictedSwitchPreference restrictedSwitchPreference =
                (RestrictedSwitchPreference) preference;
        final CharSequence loadLabel = this.mPkgInfo.applicationInfo.loadLabel(this.mPm);
        boolean z =
                this.mCn.flattenToShortString().length()
                        <= NotificationManager.MAX_SERVICE_COMPONENT_NAME_LENGTH;
        boolean isServiceEnabled = isServiceEnabled(this.mCn);
        restrictedSwitchPreference.setChecked(isServiceEnabled);
        restrictedSwitchPreference.setOnPreferenceChangeListener(
                new Preference
                        .OnPreferenceChangeListener() { // from class:
                                                        // com.android.settings.applications.specialaccess.notificationaccess.ApprovalPreferenceController$$ExternalSyntheticLambda0
                    @Override // androidx.preference.Preference.OnPreferenceChangeListener
                    public final boolean onPreferenceChange(Preference preference2, Object obj) {
                        boolean lambda$updateState$0;
                        lambda$updateState$0 =
                                ApprovalPreferenceController.this.lambda$updateState$0(
                                        loadLabel, preference2, obj);
                        return lambda$updateState$0;
                    }
                });
        if (com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags
                        .enhancedConfirmationModeApisEnabled()
                && android.security.Flags.extendEcmToAllSettings()) {
            if (!z && !isServiceEnabled) {
                restrictedSwitchPreference.setEnabled(false);
                return;
            } else if (isServiceEnabled) {
                restrictedSwitchPreference.setEnabled(true);
                return;
            } else {
                restrictedSwitchPreference.mHelper.checkEcmRestrictionAndSetDisabled(
                        this.mSettingIdentifier, this.mCn.getPackageName());
                return;
            }
        }
        String packageName = this.mCn.getPackageName();
        int i = this.mPkgInfo.applicationInfo.uid;
        RestrictedPreferenceHelper restrictedPreferenceHelper = restrictedSwitchPreference.mHelper;
        restrictedPreferenceHelper.packageName = packageName;
        restrictedPreferenceHelper.uid = i;
        if (restrictedSwitchPreference.mAppOpsManager == null) {
            restrictedSwitchPreference.mAppOpsManager =
                    (AppOpsManager)
                            restrictedSwitchPreference
                                    .getContext()
                                    .getSystemService(AppOpsManager.class);
        }
        int noteOpNoThrow =
                restrictedSwitchPreference.mAppOpsManager.noteOpNoThrow(119, i, packageName);
        boolean z2 =
                !restrictedSwitchPreference
                                .getContext()
                                .getResources()
                                .getBoolean(R.bool.config_focusScrollContainersInTouchMode)
                        || noteOpNoThrow == 0
                        || noteOpNoThrow == 3;
        if (!z && !isServiceEnabled) {
            restrictedSwitchPreference.setEnabled(false);
            return;
        }
        if (isServiceEnabled) {
            restrictedSwitchPreference.setEnabled(true);
            return;
        }
        if (z2 && restrictedSwitchPreference.mHelper.mDisabledByEcm) {
            restrictedSwitchPreference.setEnabled(true);
            return;
        }
        if (z2) {
            return;
        }
        RestrictedPreferenceHelper restrictedPreferenceHelper2 = restrictedSwitchPreference.mHelper;
        if (!restrictedPreferenceHelper2.mDisabledByEcm) {
            restrictedPreferenceHelper2.mDisabledByEcm = true;
            restrictedPreferenceHelper2.updateDisabledState();
            restrictedSwitchPreference.notifyChanged();
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
