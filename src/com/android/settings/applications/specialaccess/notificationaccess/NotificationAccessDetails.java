package com.android.settings.applications.specialaccess.notificationaccess;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.IconDrawableFactory;
import android.util.Log;
import android.util.Slog;

import androidx.preference.Preference;
import androidx.preference.SwitchPreference;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.notification.NotificationBackend;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.widget.EntityHeaderController;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.applications.AppUtils;

import com.samsung.android.settings.widget.SecInsetCategoryPreference;

import java.util.Iterator;
import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NotificationAccessDetails extends DashboardFragment {
    public ComponentName mComponentName;
    public boolean mCreated;
    public boolean mIsNls;
    public NotificationManager mNm;
    public PackageInfo mPackageInfo;
    public String mPackageName;
    public PackageManager mPm;
    public ServiceInfo mServiceInfo;
    public CharSequence mServiceName;
    public int mUserId;

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "NotifAccessDetails";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1804;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.notification_access_permission_details;
    }

    public final void loadNotificationListenerService() {
        this.mIsNls = false;
        if (this.mComponentName == null) {
            return;
        }
        Iterator it =
                this.mPm
                        .queryIntentServicesAsUser(
                                new Intent(
                                                "android.service.notification.NotificationListenerService")
                                        .setComponent(this.mComponentName),
                                132,
                                this.mUserId)
                        .iterator();
        while (it.hasNext()) {
            ServiceInfo serviceInfo = ((ResolveInfo) it.next()).serviceInfo;
            if ("android.permission.BIND_NOTIFICATION_LISTENER_SERVICE"
                            .equals(serviceInfo.permission)
                    && Objects.equals(this.mComponentName, serviceInfo.getComponentName())) {
                this.mIsNls = true;
                this.mServiceName = serviceInfo.loadLabel(this.mPm);
                this.mServiceInfo = serviceInfo;
                return;
            }
        }
    }

    public void logSpecialPermissionChange(boolean z, String str) {
        int i = z ? 776 : 777;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getMetricsFeatureProvider().action(getContext(), i, str);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        int i;
        String stringExtra;
        super.onAttach(context);
        Intent intent = getIntent();
        if (this.mComponentName == null
                && intent != null
                && (stringExtra =
                                intent.getStringExtra(
                                        "android.provider.extra.NOTIFICATION_LISTENER_COMPONENT_NAME"))
                        != null) {
            ComponentName unflattenFromString = ComponentName.unflattenFromString(stringExtra);
            this.mComponentName = unflattenFromString;
            if (unflattenFromString != null) {
                getArguments().putString("package", this.mComponentName.getPackageName());
            }
        }
        this.mNm = (NotificationManager) getContext().getSystemService(NotificationManager.class);
        this.mPm = getPackageManager();
        Bundle arguments = getArguments();
        this.mPackageName = arguments != null ? arguments.getString("package") : null;
        Intent intent2 =
                arguments == null ? getIntent() : (Intent) arguments.getParcelable("intent");
        if (this.mPackageName == null && intent2 != null && intent2.getData() != null) {
            this.mPackageName = intent2.getData().getSchemeSpecificPart();
        }
        if (intent2 == null || !intent2.hasExtra("android.intent.extra.user_handle")) {
            this.mUserId = UserHandle.myUserId();
        } else {
            String initialCallingPackage =
                    ((SettingsActivity) getActivity()).getInitialCallingPackage();
            if (TextUtils.isEmpty(initialCallingPackage)) {
                Log.w(
                        "NotifAccessDetails",
                        "Not able to get calling package name for permission check");
            } else if (getContext()
                            .getPackageManager()
                            .checkPermission(
                                    "android.permission.INTERACT_ACROSS_USERS_FULL",
                                    initialCallingPackage)
                    != 0) {
                Log.w(
                        "NotifAccessDetails",
                        "Package "
                                + initialCallingPackage
                                + " does not have required permission"
                                + " android.permission.INTERACT_ACROSS_USERS_FULL");
            } else {
                this.mUserId =
                        ((UserHandle)
                                        intent2.getParcelableExtra(
                                                "android.intent.extra.user_handle"))
                                .getIdentifier();
            }
            finish();
        }
        try {
            this.mPackageInfo =
                    this.mPm.getPackageInfoAsUser(this.mPackageName, 134222336, this.mUserId);
        } catch (PackageManager.NameNotFoundException unused) {
        }
        loadNotificationListenerService();
        NotificationBackend notificationBackend = new NotificationBackend();
        try {
            i = this.mPm.getTargetSdkVersion(this.mComponentName.getPackageName());
        } catch (PackageManager.NameNotFoundException unused2) {
            i = 31;
        }
        getPreferenceControllers()
                .forEach(
                        new NotificationAccessDetails$$ExternalSyntheticLambda1(
                                this, notificationBackend, i, 0));
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                getActivity(), this.mUserId, "no_control_apps");
        RestrictedLockUtilsInternal.hasBaseUserRestriction(
                getActivity(), this.mUserId, "no_control_apps");
        if (!refreshUi()) {
            finish();
        }
        if (this.mPackageInfo == null || this.mCreated) {
            return;
        }
        this.mCreated = true;
        loadNotificationListenerService();
        EntityHeaderController entityHeaderController =
                new EntityHeaderController(getActivity(), this, null);
        entityHeaderController.setIcon(
                IconDrawableFactory.newInstance(getContext())
                        .getBadgedIcon(this.mPackageInfo.applicationInfo));
        entityHeaderController.mLabel = this.mPackageInfo.applicationInfo.loadLabel(this.mPm);
        entityHeaderController.mSummary = this.mServiceName;
        entityHeaderController.mIsInstantApp =
                AppUtils.isInstant(this.mPackageInfo.applicationInfo);
        entityHeaderController.mPackageName = this.mPackageName;
        entityHeaderController.mUid = this.mPackageInfo.applicationInfo.uid;
        entityHeaderController.mHasAppInfoLink = true;
        entityHeaderController.mAction1 = 0;
        entityHeaderController.mAction2 = 0;
        getPreferenceScreen().addPreference(entityHeaderController.done(getPrefContext()));
        SecInsetCategoryPreference secInsetCategoryPreference =
                new SecInsetCategoryPreference(getPrefContext());
        secInsetCategoryPreference.setOrder(-1);
        getPreferenceScreen().addPreference(secInsetCategoryPreference);
    }

    public final boolean refreshUi() {
        if (this.mComponentName == null) {
            Slog.d("NotifAccessDetails", "No component name provided");
            return false;
        }
        if (!this.mIsNls) {
            Slog.d("NotifAccessDetails", "Provided component name is not an NLS");
            return false;
        }
        if (UserManager.get(getContext()).isManagedProfile()) {
            Slog.d("NotifAccessDetails", "NLSes aren't allowed in work profiles");
            return false;
        }
        SwitchPreference switchPreference =
                (SwitchPreference) findPreference("notification_access_switch");
        final CharSequence loadLabel = this.mPackageInfo.applicationInfo.loadLabel(this.mPm);
        switchPreference.setChecked(
                this.mNm.isNotificationListenerAccessGranted(this.mComponentName));
        switchPreference.setOnPreferenceChangeListener(
                new Preference
                        .OnPreferenceChangeListener() { // from class:
                                                        // com.android.settings.applications.specialaccess.notificationaccess.NotificationAccessDetails$$ExternalSyntheticLambda0
                    @Override // androidx.preference.Preference.OnPreferenceChangeListener
                    public final boolean onPreferenceChange(Preference preference, Object obj) {
                        CharSequence charSequence = loadLabel;
                        NotificationAccessDetails notificationAccessDetails =
                                NotificationAccessDetails.this;
                        notificationAccessDetails.getClass();
                        if (((Boolean) obj).booleanValue()) {
                            if (!notificationAccessDetails.mNm.isNotificationListenerAccessGranted(
                                    notificationAccessDetails.mComponentName)) {
                                ScaryWarningDialogFragment scaryWarningDialogFragment =
                                        new ScaryWarningDialogFragment();
                                scaryWarningDialogFragment.setServiceInfo$1(
                                        notificationAccessDetails.mComponentName,
                                        charSequence,
                                        notificationAccessDetails);
                                scaryWarningDialogFragment.show(
                                        notificationAccessDetails.getFragmentManager(), "dialog");
                                return false;
                            }
                        } else {
                            if (notificationAccessDetails.mNm.isNotificationListenerAccessGranted(
                                    notificationAccessDetails.mComponentName)) {
                                FriendlyWarningDialogFragment friendlyWarningDialogFragment =
                                        new FriendlyWarningDialogFragment();
                                friendlyWarningDialogFragment.setServiceInfo(
                                        notificationAccessDetails.mComponentName,
                                        charSequence,
                                        notificationAccessDetails);
                                friendlyWarningDialogFragment.show(
                                        notificationAccessDetails.getFragmentManager(),
                                        "friendlydialog");
                                return false;
                            }
                        }
                        return true;
                    }
                });
        return true;
    }
}
