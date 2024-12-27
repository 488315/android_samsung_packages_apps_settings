package com.android.settings.applications;

import android.app.Dialog;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.usb.IUsbManager;
import android.os.Bundle;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.applications.appinfo.AppButtonsPreferenceController;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.applications.ApplicationsState;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AppInfoBase extends SettingsPreferenceFragment
        implements ApplicationsState.Callbacks {
    public ApplicationsState.AppEntry mAppEntry;
    public RestrictedLockUtils.EnforcedAdmin mAppsControlDisallowedAdmin;
    public boolean mAppsControlDisallowedBySystem;
    public DevicePolicyManager mDpm;
    public boolean mFinishing;
    public boolean mListeningToPackageRemove;
    public PackageInfo mPackageInfo;
    public String mPackageName;
    public final AnonymousClass1 mPackageRemovedReceiver =
            new BroadcastReceiver() { // from class: com.android.settings.applications.AppInfoBase.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    ApplicationInfo applicationInfo;
                    String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
                    AppInfoBase appInfoBase = AppInfoBase.this;
                    if (appInfoBase.mFinishing) {
                        return;
                    }
                    ApplicationsState.AppEntry appEntry = appInfoBase.mAppEntry;
                    if (appEntry == null
                            || (applicationInfo = appEntry.info) == null
                            || TextUtils.equals(applicationInfo.packageName, schemeSpecificPart)) {
                        AppInfoBase.this.getActivity().finishAndRemoveTask();
                    }
                }
            };
    public PackageManager mPm;
    public ApplicationsState mState;
    public IUsbManager mUsbManager;
    public int mUserId;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class MyAlertDialogFragment extends InstrumentedDialogFragment {
        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 1985;
        }

        @Override // androidx.fragment.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            int i = getArguments().getInt("id");
            getArguments().getInt("moveError");
            AlertDialog createDialog = ((AppInfoBase) getTargetFragment()).createDialog(i);
            if (createDialog != null) {
                return createDialog;
            }
            throw new IllegalArgumentException(
                    SeslRoundedCorner$$ExternalSyntheticOutline0.m(i, "unknown id "));
        }
    }

    public static void startAppInfoFragment(
            Class cls, String str, String str2, int i, Fragment fragment, int i2, int i3) {
        Bundle bundle = new Bundle();
        bundle.putString("package", str2);
        bundle.putInt(NetworkAnalyticsConstants.DataPoints.UID, i);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(fragment.getContext());
        String name = cls.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = i3;
        launchRequest.mTitle = str;
        launchRequest.mArguments = bundle;
        launchRequest.mUserHandle = new UserHandle(UserHandle.getUserId(i));
        subSettingLauncher.setResultListener(fragment, i2);
        subSettingLauncher.launch();
    }

    public abstract AlertDialog createDialog(int i);

    public boolean hasInteractAcrossUsersPermission() {
        FragmentActivity activity = getActivity();
        if (!(activity instanceof SettingsActivity)) {
            return false;
        }
        String initialCallingPackage = ((SettingsActivity) activity).getInitialCallingPackage();
        if (TextUtils.isEmpty(initialCallingPackage)) {
            Log.w("AppInfoBase", "Not able to get calling package name for permission check");
            return false;
        }
        if (this.mPm.checkPermission(
                        "android.permission.INTERACT_ACROSS_USERS_FULL", initialCallingPackage)
                == 0) {
            return true;
        }
        Log.w(
                "AppInfoBase",
                "Package "
                        + initialCallingPackage
                        + " does not have required permission"
                        + " android.permission.INTERACT_ACROSS_USERS_FULL");
        return false;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mFinishing = false;
        FragmentActivity activity = getActivity();
        if (!Utils.isDeviceProvisioned(activity) && Utils.isFrpChallengeRequired(activity)) {
            Log.i("AppInfoBase", "Refusing to start because device is not provisioned");
            activity.finish();
            return;
        }
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getApplicationFeatureProvider();
        ApplicationsState applicationsState =
                ApplicationsState.getInstance(activity.getApplication());
        this.mState = applicationsState;
        applicationsState.newSession(this, getSettingsLifecycle());
        this.mDpm = (DevicePolicyManager) activity.getSystemService("device_policy");
        this.mPm = activity.getPackageManager();
        this.mUsbManager = IUsbManager.Stub.asInterface(ServiceManager.getService("usb"));
        retrieveAppEntry$1();
        if (this.mPackageInfo == null || this.mPackageName == null) {
            setIntentAndFinish(true);
        } else {
            if (this.mListeningToPackageRemove) {
                return;
            }
            this.mListeningToPackageRemove = true;
            IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_REMOVED");
            intentFilter.addDataScheme("package");
            getContext().registerReceiver(this.mPackageRemovedReceiver, intentFilter);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public void onDestroy() {
        if (this.mListeningToPackageRemove) {
            this.mListeningToPackageRemove = false;
            getContext().unregisterReceiver(this.mPackageRemovedReceiver);
        }
        super.onDestroy();
    }

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onPackageListChanged() {
        if (refreshUi()) {
            return;
        }
        setIntentAndFinish(true);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        this.mAppsControlDisallowedAdmin =
                RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                        getActivity(), this.mUserId, "no_control_apps");
        this.mAppsControlDisallowedBySystem =
                RestrictedLockUtilsInternal.hasBaseUserRestriction(
                        getActivity(), this.mUserId, "no_control_apps");
        if (refreshUi()) {
            return;
        }
        setIntentAndFinish(true);
    }

    public void onZenAccessPolicyChanged() {
        refreshUi();
    }

    public abstract boolean refreshUi();

    public final void retrieveAppEntry$1() {
        Bundle arguments = getArguments();
        this.mPackageName = arguments != null ? arguments.getString("package") : null;
        Intent intent =
                arguments == null ? getIntent() : (Intent) arguments.getParcelable("intent");
        if (this.mPackageName == null && intent != null && intent.getData() != null) {
            this.mPackageName = intent.getData().getSchemeSpecificPart();
        }
        if (intent == null || !intent.hasExtra("android.intent.extra.user_handle")) {
            this.mUserId = UserHandle.myUserId();
        } else {
            int identifier =
                    ((UserHandle) intent.getParcelableExtra("android.intent.extra.user_handle"))
                            .getIdentifier();
            this.mUserId = identifier;
            if (identifier != UserHandle.myUserId() && !hasInteractAcrossUsersPermission()) {
                Log.w("AppInfoBase", "Intent not valid.");
                finish();
                return;
            }
        }
        ApplicationsState.AppEntry entry = this.mState.getEntry(this.mUserId, this.mPackageName);
        this.mAppEntry = entry;
        if (entry == null) {
            Log.w("AppInfoBase", "Missing AppEntry; maybe reinstalling?");
            this.mPackageInfo = null;
            return;
        }
        try {
            this.mPackageInfo =
                    this.mPm.getPackageInfoAsUser(
                            entry.info.packageName,
                            PackageManager.PackageInfoFlags.of(4429189632L),
                            this.mUserId);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(
                    "AppInfoBase",
                    "Exception when retrieving package:" + this.mAppEntry.info.packageName,
                    e);
        }
    }

    public final void setIntentAndFinish(boolean z) {
        Log.i("AppInfoBase", "appChanged=" + z);
        Intent intent = new Intent();
        intent.putExtra(AppButtonsPreferenceController.APP_CHG, z);
        ((SettingsActivity) getActivity()).finishPreferencePanel(intent);
        this.mFinishing = true;
    }

    public final void showDialogInner$1(int i) {
        MyAlertDialogFragment myAlertDialogFragment = new MyAlertDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", i);
        bundle.putInt("moveError", 0);
        myAlertDialogFragment.setArguments(bundle);
        myAlertDialogFragment.setTargetFragment(this, 0);
        myAlertDialogFragment.show(getFragmentManager(), "dialog " + i);
    }

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onAllSizesComputed() {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onLauncherInfoChanged() {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onLoadEntriesCompleted() {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onPackageIconChanged() {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public void onPackageSizeChanged(String str) {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onRebuildComplete(ArrayList arrayList) {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onRunningStateChanged(boolean z) {}
}
