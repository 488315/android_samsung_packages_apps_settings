package com.android.settings.utils;

import android.app.Dialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.DialogInterface;
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

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.accessibility.ScreenFlashNotificationColorDialogFragment$$ExternalSyntheticLambda0;
import com.android.settings.applications.specialaccess.vrlistener.VrListenerSettings;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.widget.EmptyTextSettings;
import com.android.settingslib.RestrictedSwitchPreference;
import com.android.settingslib.applications.ServiceListing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ManagedServiceSettings extends EmptyTextSettings {
    public final Config mConfig = VrListenerSettings.CONFIG;
    public FragmentActivity mContext;
    public DevicePolicyManager mDpm;
    public IconDrawableFactory mIconDrawableFactory;
    public PackageManager mPm;
    public ServiceListing mServiceListing;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Config {
        public final String configIntentAction;
        public final int emptyText;
        public final String intentAction;
        public final String noun;
        public final String permission;
        public final String setting;
        public final String tag;
        public final int warningDialogSummary;
        public final int warningDialogTitle;

        public Config(
                String str,
                String str2,
                String str3,
                String str4,
                String str5,
                String str6,
                int i,
                int i2,
                int i3) {
            this.tag = str;
            this.setting = str2;
            this.intentAction = str3;
            this.permission = str5;
            this.noun = str6;
            this.warningDialogTitle = i;
            this.warningDialogSummary = i2;
            this.emptyText = i3;
            this.configIntentAction = str4;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class ScaryWarningDialogFragment extends InstrumentedDialogFragment {
        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 557;
        }

        @Override // androidx.fragment.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            Bundle arguments = getArguments();
            String string = arguments.getString("l");
            final ComponentName unflattenFromString =
                    ComponentName.unflattenFromString(arguments.getString("c"));
            final ManagedServiceSettings managedServiceSettings =
                    (ManagedServiceSettings) getTargetFragment();
            String string2 =
                    getResources()
                            .getString(managedServiceSettings.mConfig.warningDialogTitle, string);
            String string3 =
                    getResources()
                            .getString(managedServiceSettings.mConfig.warningDialogSummary, string);
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            AlertController.AlertParams alertParams = builder.P;
            alertParams.mMessage = string3;
            alertParams.mTitle = string2;
            alertParams.mCancelable = true;
            builder.setPositiveButton(
                    R.string.allow,
                    new DialogInterface
                            .OnClickListener() { // from class:
                                                 // com.android.settings.utils.ManagedServiceSettings$ScaryWarningDialogFragment$$ExternalSyntheticLambda0
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i) {
                            ManagedServiceSettings managedServiceSettings2 =
                                    ManagedServiceSettings.this;
                            managedServiceSettings2.mServiceListing.setEnabled(
                                    unflattenFromString, true);
                        }
                    });
            builder.setNegativeButton(
                    R.string.deny,
                    new ScreenFlashNotificationColorDialogFragment$$ExternalSyntheticLambda0());
            return builder.create();
        }
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
        FragmentActivity fragmentActivity = this.mContext;
        Config config = this.mConfig;
        ServiceListing serviceListing =
                new ServiceListing(
                        fragmentActivity,
                        config.tag,
                        config.setting,
                        config.intentAction,
                        config.permission,
                        config.noun);
        this.mServiceListing = serviceListing;
        ((ArrayList) serviceListing.mCallbacks)
                .add(
                        new ServiceListing.Callback() { // from class:
                            // com.android.settings.utils.ManagedServiceSettings$$ExternalSyntheticLambda0
                            @Override // com.android.settingslib.applications.ServiceListing.Callback
                            public final void onServicesReloaded(List list) {
                                final CharSequence charSequence;
                                final ManagedServiceSettings managedServiceSettings =
                                        ManagedServiceSettings.this;
                                int managedProfileId =
                                        Utils.getManagedProfileId(
                                                (UserManager)
                                                        managedServiceSettings.mContext
                                                                .getSystemService("user"),
                                                UserHandle.myUserId());
                                PreferenceScreen preferenceScreen =
                                        managedServiceSettings.getPreferenceScreen();
                                preferenceScreen.removeAll();
                                list.sort(
                                        new PackageItemInfo.DisplayNameComparator(
                                                managedServiceSettings.mPm));
                                Iterator it = list.iterator();
                                while (it.hasNext()) {
                                    ServiceInfo serviceInfo = (ServiceInfo) it.next();
                                    final ComponentName componentName =
                                            new ComponentName(
                                                    serviceInfo.packageName, serviceInfo.name);
                                    try {
                                        charSequence =
                                                managedServiceSettings
                                                        .mPm
                                                        .getApplicationInfoAsUser(
                                                                serviceInfo.packageName,
                                                                0,
                                                                UserHandle.myUserId())
                                                        .loadLabel(managedServiceSettings.mPm);
                                    } catch (PackageManager.NameNotFoundException e) {
                                        Log.e(
                                                "ManagedServiceSettings",
                                                "can't find package name",
                                                e);
                                        charSequence = null;
                                    }
                                    String charSequence2 =
                                            serviceInfo
                                                    .loadLabel(managedServiceSettings.mPm)
                                                    .toString();
                                    RestrictedSwitchPreference restrictedSwitchPreference =
                                            new RestrictedSwitchPreference(
                                                    managedServiceSettings.getPrefContext());
                                    restrictedSwitchPreference.setPersistent();
                                    IconDrawableFactory iconDrawableFactory =
                                            managedServiceSettings.mIconDrawableFactory;
                                    ApplicationInfo applicationInfo = serviceInfo.applicationInfo;
                                    restrictedSwitchPreference.setIcon(
                                            iconDrawableFactory.getBadgedIcon(
                                                    serviceInfo,
                                                    applicationInfo,
                                                    UserHandle.getUserId(applicationInfo.uid)));
                                    restrictedSwitchPreference.mIconSize = 1;
                                    if (charSequence == null
                                            || charSequence.equals(charSequence2)) {
                                        restrictedSwitchPreference.setTitle(charSequence2);
                                    } else {
                                        restrictedSwitchPreference.setTitle(charSequence);
                                        restrictedSwitchPreference.setSummary(charSequence2);
                                    }
                                    restrictedSwitchPreference.setKey(
                                            componentName.flattenToString());
                                    restrictedSwitchPreference.setChecked(
                                            managedServiceSettings.mServiceListing.mEnabledServices
                                                    .contains(componentName));
                                    if (managedProfileId != -10000
                                            && !managedServiceSettings.mDpm
                                                    .isNotificationListenerServicePermitted(
                                                            serviceInfo.packageName,
                                                            managedProfileId)) {
                                        restrictedSwitchPreference.setSummary(
                                                managedServiceSettings
                                                        .mDpm
                                                        .getResources()
                                                        .getString(
                                                                "Settings.WORK_PROFILE_NOTIFICATION_LISTENER_BLOCKED",
                                                                new Supplier() { // from class:
                                                                    // com.android.settings.utils.ManagedServiceSettings$$ExternalSyntheticLambda1
                                                                    @Override // java.util.function.Supplier
                                                                    public final Object get() {
                                                                        return ManagedServiceSettings
                                                                                .this
                                                                                .getString(
                                                                                        R.string
                                                                                                .work_profile_notification_access_blocked_summary);
                                                                    }
                                                                }));
                                    }
                                    restrictedSwitchPreference.setOnPreferenceChangeListener(
                                            new Preference
                                                    .OnPreferenceChangeListener() { // from class:
                                                // com.android.settings.utils.ManagedServiceSettings$$ExternalSyntheticLambda2
                                                @Override // androidx.preference.Preference.OnPreferenceChangeListener
                                                public final boolean onPreferenceChange(
                                                        Preference preference, Object obj) {
                                                    CharSequence charSequence3 = charSequence;
                                                    ComponentName componentName2 = componentName;
                                                    ManagedServiceSettings managedServiceSettings2 =
                                                            ManagedServiceSettings.this;
                                                    managedServiceSettings2.getClass();
                                                    boolean booleanValue =
                                                            ((Boolean) obj).booleanValue();
                                                    return charSequence3 != null
                                                            ? managedServiceSettings2.setEnabled(
                                                                    componentName2,
                                                                    charSequence3.toString(),
                                                                    booleanValue)
                                                            : managedServiceSettings2.setEnabled(
                                                                    componentName2,
                                                                    null,
                                                                    booleanValue);
                                                }
                                            });
                                    restrictedSwitchPreference.setKey(
                                            componentName.flattenToString());
                                    if (!restrictedSwitchPreference.mChecked) {
                                        restrictedSwitchPreference.mHelper
                                                .checkEcmRestrictionAndSetDisabled(
                                                        managedServiceSettings.mConfig.permission,
                                                        serviceInfo.packageName);
                                    }
                                    preferenceScreen.addPreference(restrictedSwitchPreference);
                                }
                                managedServiceSettings.highlightPreferenceIfNeeded();
                            }
                        });
        setPreferenceScreen(getPreferenceManager().createPreferenceScreen(this.mContext));
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
        setEmptyText(this.mConfig.emptyText);
    }

    public boolean setEnabled(ComponentName componentName, String str, boolean z) {
        if (!z) {
            this.mServiceListing.setEnabled(componentName, false);
            return true;
        }
        if (this.mServiceListing.mEnabledServices.contains(componentName)) {
            return true;
        }
        ScaryWarningDialogFragment scaryWarningDialogFragment = new ScaryWarningDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("c", componentName.flattenToString());
        bundle.putString("l", str);
        scaryWarningDialogFragment.setArguments(bundle);
        scaryWarningDialogFragment.setTargetFragment(this, 0);
        scaryWarningDialogFragment.show(getFragmentManager(), "dialog");
        return false;
    }
}
