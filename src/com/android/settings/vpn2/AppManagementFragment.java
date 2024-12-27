package com.android.settings.vpn2;

import android.app.AppOpsManager;
import android.app.Dialog;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.VpnManager;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import androidx.preference.Preference;

import com.android.internal.net.VpnConfig;
import com.android.internal.util.ArrayUtils;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedPreference;
import com.android.settingslib.RestrictedSwitchPreference;

import com.samsung.android.gtscell.data.FieldName;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AppManagementFragment extends SettingsPreferenceFragment
        implements Preference.OnPreferenceChangeListener,
                Preference.OnPreferenceClickListener,
                ConfirmLockdownFragment.ConfirmLockdownListener {
    public DevicePolicyManager mDevicePolicyManager;
    public AdvancedVpnFeatureProvider mFeatureProvider;
    public PackageInfo mPackageInfo;
    public PackageManager mPackageManager;
    public String mPackageName;
    public RestrictedSwitchPreference mPreferenceAlwaysOn;
    public RestrictedPreference mPreferenceForget;
    public RestrictedSwitchPreference mPreferenceLockdown;
    public Preference mPreferenceVersion;
    public String mVpnLabel;
    public VpnManager mVpnManager;
    public final int mUserId = UserHandle.myUserId();
    public final AnonymousClass1 mForgetVpnDialogFragmentListener = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.vpn2.AppManagementFragment$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {}
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class CannotConnectFragment extends InstrumentedDialogFragment {
        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 547;
        }

        @Override // androidx.fragment.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            String string = getArguments().getString("label");
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            String string2 =
                    getActivity().getString(R.string.vpn_cant_connect_title, new Object[] {string});
            AlertController.AlertParams alertParams = builder.P;
            alertParams.mTitle = string2;
            alertParams.mMessage = getActivity().getString(R.string.vpn_cant_connect_message);
            builder.setPositiveButton(R.string.okay, (DialogInterface.OnClickListener) null);
            return builder.create();
        }
    }

    public static boolean appHasVpnPermission(Context context, ApplicationInfo applicationInfo) {
        return !ArrayUtils.isEmpty(
                ((AppOpsManager) context.getSystemService("appops"))
                        .getOpsForPackage(
                                applicationInfo.uid,
                                applicationInfo.packageName,
                                new int[] {47, 94}));
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2033;
    }

    public void init(
            String str,
            AdvancedVpnFeatureProvider advancedVpnFeatureProvider,
            RestrictedPreference restrictedPreference) {
        this.mPackageName = str;
        this.mFeatureProvider = advancedVpnFeatureProvider;
        this.mPreferenceForget = restrictedPreference;
    }

    public final boolean onAlwaysOnVpnClick(boolean z, boolean z2) {
        VpnConfig vpnConfig = this.mVpnManager.getVpnConfig(this.mUserId);
        boolean z3 =
                (vpnConfig == null || TextUtils.equals(vpnConfig.user, this.mPackageName))
                        ? false
                        : true;
        boolean isAnyLockdownActive = VpnUtils.isAnyLockdownActive(getActivity());
        if (!z3 && (!z2 || isAnyLockdownActive)) {
            return setAlwaysOnVpnByUI(z, z2);
        }
        ConfirmLockdownFragment.show(this, z3, z, isAnyLockdownActive, z2, null);
        return false;
    }

    @Override // com.android.settings.vpn2.ConfirmLockdownFragment.ConfirmLockdownListener
    public final void onConfirmLockdown(Bundle bundle, boolean z, boolean z2) {
        setAlwaysOnVpnByUI(z, z2);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.vpn_app_management);
        this.mPackageManager = getContext().getPackageManager();
        this.mDevicePolicyManager =
                (DevicePolicyManager) getContext().getSystemService(DevicePolicyManager.class);
        this.mVpnManager = (VpnManager) getContext().getSystemService(VpnManager.class);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mFeatureProvider =
                (AdvancedVpnFeatureProviderImpl)
                        featureFactoryImpl.advancedVpnFeatureProvider$delegate.getValue();
        this.mPreferenceVersion = findPreference(FieldName.VERSION);
        this.mPreferenceAlwaysOn = (RestrictedSwitchPreference) findPreference("always_on_vpn");
        this.mPreferenceLockdown = (RestrictedSwitchPreference) findPreference("lockdown_vpn");
        this.mPreferenceForget = (RestrictedPreference) findPreference("forget_vpn");
        this.mPreferenceAlwaysOn.setOnPreferenceChangeListener(this);
        this.mPreferenceLockdown.setOnPreferenceChangeListener(this);
        this.mPreferenceForget.setOnPreferenceClickListener(this);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        String key = preference.getKey();
        key.getClass();
        if (key.equals("always_on_vpn")) {
            return onAlwaysOnVpnClick(
                    ((Boolean) obj).booleanValue(), this.mPreferenceLockdown.mChecked);
        }
        if (key.equals("lockdown_vpn")) {
            return onAlwaysOnVpnClick(
                    this.mPreferenceAlwaysOn.mChecked, ((Boolean) obj).booleanValue());
        }
        Log.w("AppManagementFragment", "unknown key is clicked: " + preference.getKey());
        return false;
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();
        key.getClass();
        if (!key.equals("forget_vpn")) {
            Log.w("AppManagementFragment", "unknown key is clicked: ".concat(key));
            return false;
        }
        updateRestrictedViews();
        if (!this.mPreferenceForget.isEnabled()) {
            return false;
        }
        AppDialogFragment.show(
                this,
                this.mForgetVpnDialogFragmentListener,
                this.mPackageInfo,
                this.mVpnLabel,
                true,
                true);
        return true;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        Bundle arguments = getArguments();
        if (arguments == null) {
            Log.e("AppManagementFragment", "empty bundle");
        } else {
            String string = arguments.getString("package");
            this.mPackageName = string;
            if (string == null) {
                Log.e("AppManagementFragment", "empty package name");
            } else {
                try {
                    this.mPackageInfo = this.mPackageManager.getPackageInfo(string, 0);
                    this.mVpnLabel =
                            VpnConfig.getVpnLabel(getPrefContext(), this.mPackageName).toString();
                    if (this.mPackageInfo.applicationInfo == null) {
                        Log.e("AppManagementFragment", "package does not include an application");
                    } else {
                        if (appHasVpnPermission(getContext(), this.mPackageInfo.applicationInfo)) {
                            this.mPreferenceVersion.setSummary(this.mPackageInfo.versionName);
                            updateUI$5();
                            return;
                        }
                        Log.e("AppManagementFragment", "package didn't register VPN profile");
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    Log.e("AppManagementFragment", "package not found", e);
                }
            }
        }
        finish();
    }

    public final boolean setAlwaysOnVpnByUI(boolean z, boolean z2) {
        updateRestrictedViews();
        if (!this.mPreferenceAlwaysOn.isEnabled()) {
            return false;
        }
        if (this.mUserId == 0) {
            VpnUtils.clearLockdownVpn(getContext());
        }
        boolean alwaysOnVpnPackageForUser =
                this.mVpnManager.setAlwaysOnVpnPackageForUser(
                        this.mUserId, z ? this.mPackageName : null, z2, null);
        if (!z
                || (alwaysOnVpnPackageForUser
                        && this.mPackageName.equals(
                                this.mVpnManager.getAlwaysOnVpnPackageForUser(this.mUserId)))) {
            updateUI$5();
        } else {
            String str = this.mVpnLabel;
            if (getFragmentManager().findFragmentByTag("CannotConnect") == null) {
                Bundle m = AbsAdapter$1$$ExternalSyntheticOutline0.m("label", str);
                CannotConnectFragment cannotConnectFragment = new CannotConnectFragment();
                cannotConnectFragment.setArguments(m);
                cannotConnectFragment.show(getFragmentManager(), "CannotConnect");
            }
        }
        return alwaysOnVpnPackageForUser;
    }

    public void updateRestrictedViews() {
        AdvancedVpnFeatureProvider advancedVpnFeatureProvider = this.mFeatureProvider;
        getContext();
        advancedVpnFeatureProvider.getClass();
        this.mPreferenceForget.setVisible(true);
        if (isAdded()) {
            RestrictedSwitchPreference restrictedSwitchPreference = this.mPreferenceAlwaysOn;
            restrictedSwitchPreference.mHelper.checkRestrictionAndSetDisabled(
                    this.mUserId, "no_config_vpn");
            RestrictedSwitchPreference restrictedSwitchPreference2 = this.mPreferenceLockdown;
            restrictedSwitchPreference2.mHelper.checkRestrictionAndSetDisabled(
                    this.mUserId, "no_config_vpn");
            RestrictedPreference restrictedPreference = this.mPreferenceForget;
            restrictedPreference.mHelper.checkRestrictionAndSetDisabled(
                    this.mUserId, "no_config_vpn");
            if (this.mPackageName.equals(this.mDevicePolicyManager.getAlwaysOnVpnPackage())) {
                RestrictedLockUtils.EnforcedAdmin profileOrDeviceOwner =
                        RestrictedLockUtils.getProfileOrDeviceOwner(
                                getContext(), null, UserHandle.of(this.mUserId));
                this.mPreferenceAlwaysOn.setDisabledByAdmin(profileOrDeviceOwner);
                this.mPreferenceForget.setDisabledByAdmin(profileOrDeviceOwner);
                if (this.mDevicePolicyManager.isAlwaysOnVpnLockdownEnabled()) {
                    this.mPreferenceLockdown.setDisabledByAdmin(profileOrDeviceOwner);
                }
            }
            if (this.mVpnManager.isAlwaysOnVpnPackageSupportedForUser(
                    this.mUserId, this.mPackageName)) {
                this.mPreferenceAlwaysOn.setSummary(R.string.vpn_always_on_summary);
                return;
            }
            this.mPreferenceAlwaysOn.setEnabled(false);
            this.mPreferenceLockdown.setEnabled(false);
            this.mPreferenceAlwaysOn.setSummary(R.string.vpn_always_on_summary_not_supported);
        }
    }

    public final void updateUI$5() {
        if (isAdded()) {
            boolean equals =
                    this.mPackageName.equals(
                            this.mVpnManager.getAlwaysOnVpnPackageForUser(this.mUserId));
            boolean z = equals && VpnUtils.isAnyLockdownActive(getActivity());
            this.mPreferenceAlwaysOn.setChecked(equals);
            this.mPreferenceLockdown.setChecked(z);
            updateRestrictedViews();
        }
    }
}
