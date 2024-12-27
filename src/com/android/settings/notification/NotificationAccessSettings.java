package com.android.settings.notification;

import android.app.NotificationManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.IconDrawableFactory;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.recyclerview.widget.RecyclerView;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.Utils;
import com.android.settings.applications.specialaccess.notificationaccess.FriendlyWarningDialogFragment;
import com.android.settings.applications.specialaccess.notificationaccess.NotificationAccessController;
import com.android.settings.applications.specialaccess.notificationaccess.ScaryWarningDialogFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.EmptyTextSettings;
import com.android.settings.widget.HighlightablePreferenceGroupAdapter;
import com.android.settingslib.applications.ServiceListing;
import com.android.settingslib.widget.AppSwitchPreference;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NotificationAccessSettings extends EmptyTextSettings {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.notification_access_settings);
    public FragmentActivity mContext;
    public DevicePolicyManager mDpm;
    public IconDrawableFactory mIconDrawableFactory;

    @VisibleForTesting NotificationManager mNm;

    @VisibleForTesting PackageManager mPm;
    public ServiceListing mServiceListing;

    public NotificationAccessSettings() {
        new NotificationBackend();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 179;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.notification_access_settings;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mNm = (NotificationManager) context.getSystemService(NotificationManager.class);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        this.mContext = activity;
        this.mPm = activity.getPackageManager();
        this.mDpm = (DevicePolicyManager) this.mContext.getSystemService("device_policy");
        this.mIconDrawableFactory = IconDrawableFactory.newInstance(this.mContext);
        ServiceListing serviceListing =
                new ServiceListing(
                        this.mContext,
                        "NotifAccessSettings",
                        "enabled_notification_listeners",
                        "android.service.notification.NotificationListenerService",
                        "android.permission.BIND_NOTIFICATION_LISTENER_SERVICE",
                        "notification listener");
        this.mServiceListing = serviceListing;
        ((ArrayList) serviceListing.mCallbacks)
                .add(
                        new ServiceListing
                                .Callback() { // from class:
                                              // com.android.settings.notification.NotificationAccessSettings$$ExternalSyntheticLambda2
                            @Override // com.android.settingslib.applications.ServiceListing.Callback
                            public final void onServicesReloaded(List list) {
                                NotificationAccessSettings.this.updateList(list);
                            }
                        });
        if (UserManager.get(this.mContext).isManagedProfile()) {
            Toast.makeText(
                            this.mContext,
                            this.mDpm
                                    .getResources()
                                    .getString(
                                            "Settings.WORK_APPS_CANNOT_ACCESS_NOTIFICATION_SETTINGS",
                                            new NotificationAccessSettings$$ExternalSyntheticLambda0(
                                                    this, 1)),
                            0)
                    .show();
            finish();
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final RecyclerView.Adapter onCreateAdapter(PreferenceScreen preferenceScreen) {
        HighlightablePreferenceGroupAdapter highlightablePreferenceGroupAdapter =
                new HighlightablePreferenceGroupAdapter(
                        preferenceScreen,
                        ((SettingsActivity) getActivity()).getInitialCallingPackage(),
                        true);
        this.mAdapter = highlightablePreferenceGroupAdapter;
        return highlightablePreferenceGroupAdapter;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        this.mServiceListing.setListening(false);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mServiceListing.reload();
        this.mServiceListing.setListening(true);
    }

    @Override // com.android.settings.widget.EmptyTextSettings,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        setEmptyText(R.string.no_notification_listeners);
    }

    @VisibleForTesting
    public void updateList(List<ServiceInfo> list) {
        final CharSequence charSequence;
        int managedProfileId =
                Utils.getManagedProfileId(
                        (UserManager) this.mContext.getSystemService("user"),
                        UserHandle.myUserId());
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        preferenceScreen.removeAll();
        list.sort(new PackageItemInfo.DisplayNameComparator(this.mPm));
        for (ServiceInfo serviceInfo : list) {
            final ComponentName componentName =
                    new ComponentName(serviceInfo.packageName, serviceInfo.name);
            boolean isNotificationListenerAccessGranted =
                    this.mNm.isNotificationListenerAccessGranted(componentName);
            if (isNotificationListenerAccessGranted
                    || componentName.flattenToString().length()
                            <= NotificationManager.MAX_SERVICE_COMPONENT_NAME_LENGTH) {
                try {
                    charSequence =
                            this.mPm
                                    .getApplicationInfoAsUser(
                                            serviceInfo.packageName, 0, UserHandle.myUserId())
                                    .loadLabel(this.mPm);
                } catch (PackageManager.NameNotFoundException e) {
                    Log.e("NotifAccessSettings", "can't find package name", e);
                    charSequence = null;
                }
                AppSwitchPreference appSwitchPreference = new AppSwitchPreference(getPrefContext());
                appSwitchPreference.setTitle(charSequence);
                IconDrawableFactory iconDrawableFactory = this.mIconDrawableFactory;
                ApplicationInfo applicationInfo = serviceInfo.applicationInfo;
                appSwitchPreference.setIcon(
                        iconDrawableFactory.getBadgedIcon(
                                serviceInfo,
                                applicationInfo,
                                UserHandle.getUserId(applicationInfo.uid)));
                appSwitchPreference.setKey(serviceInfo.packageName);
                appSwitchPreference.setSummary(
                        isNotificationListenerAccessGranted
                                ? R.string.app_permission_summary_allowed
                                : R.string.app_permission_summary_not_allowed);
                if (managedProfileId != -10000
                        && !this.mDpm.isNotificationListenerServicePermitted(
                                serviceInfo.packageName, managedProfileId)) {
                    appSwitchPreference.setSummary(
                            this.mDpm
                                    .getResources()
                                    .getString(
                                            "Settings.WORK_PROFILE_NOTIFICATION_LISTENER_BLOCKED",
                                            new NotificationAccessSettings$$ExternalSyntheticLambda0(
                                                    this, 0)));
                }
                appSwitchPreference.setChecked(
                        NotificationAccessController.hasAccess(this.mContext, componentName));
                appSwitchPreference.setOnPreferenceChangeListener(
                        new Preference
                                .OnPreferenceChangeListener() { // from class:
                                                                // com.android.settings.notification.NotificationAccessSettings$$ExternalSyntheticLambda1
                            @Override // androidx.preference.Preference.OnPreferenceChangeListener
                            public final boolean onPreferenceChange(
                                    Preference preference, Object obj) {
                                ComponentName componentName2 = componentName;
                                CharSequence charSequence2 = charSequence;
                                BaseSearchIndexProvider baseSearchIndexProvider =
                                        NotificationAccessSettings.SEARCH_INDEX_DATA_PROVIDER;
                                NotificationAccessSettings notificationAccessSettings =
                                        NotificationAccessSettings.this;
                                notificationAccessSettings.getClass();
                                if (((Boolean) obj).booleanValue()) {
                                    if (!NotificationAccessController.hasAccess(
                                            notificationAccessSettings.mContext, componentName2)) {
                                        ScaryWarningDialogFragment scaryWarningDialogFragment =
                                                new ScaryWarningDialogFragment();
                                        scaryWarningDialogFragment.setServiceInfo$1(
                                                componentName2, charSequence2, null);
                                        scaryWarningDialogFragment.show(
                                                notificationAccessSettings.getFragmentManager(),
                                                "dialog");
                                        return false;
                                    }
                                } else if (NotificationAccessController.hasAccess(
                                        notificationAccessSettings.mContext, componentName2)) {
                                    FriendlyWarningDialogFragment friendlyWarningDialogFragment =
                                            new FriendlyWarningDialogFragment();
                                    friendlyWarningDialogFragment.setServiceInfo(
                                            componentName2, charSequence2, null);
                                    friendlyWarningDialogFragment.show(
                                            notificationAccessSettings.getFragmentManager(),
                                            "friendlydialog");
                                    return false;
                                }
                                return true;
                            }
                        });
                preferenceScreen.addPreference(appSwitchPreference);
            }
        }
        highlightPreferenceIfNeeded(true);
    }
}
