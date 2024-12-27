package com.samsung.android.settings.multidevices.autoswitch;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.bluetooth.BluetoothDevice;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.UserHandle;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.SecPreferenceCategory;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.knox.container.EnterpriseContainerConstants;
import com.samsung.android.settings.account.AccountUtils;
import com.samsung.android.settings.bluetooth.BluetoothCastSettings$$ExternalSyntheticLambda1;
import com.samsung.android.settings.widget.SecHelpAnimationLayoutPreference;
import com.samsung.android.settings.widget.SecInsetCategoryPreference;
import com.samsung.android.util.SemLog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AutoSwitchSettings extends SettingsPreferenceFragment
        implements Preference.OnPreferenceChangeListener {
    public List mAvailableDevices;
    public FragmentActivity mContext;
    public LayoutPreference mDescListPreference;
    public LocalBluetoothManager mLocalManager;
    public SecPreferenceCategory mPrefCategory;
    public SecHelpAnimationLayoutPreference mPreviewPreference;
    public Preference mSamsungAccountPref;
    public final AnonymousClass1 handler =
            new Handler() { // from class:
                            // com.samsung.android.settings.multidevices.autoswitch.AutoSwitchSettings.1
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    AutoSwitchSettings autoSwitchSettings = AutoSwitchSettings.this;
                    if (!autoSwitchSettings.isAdded() || autoSwitchSettings.getActivity() == null) {
                        SemLog.d("AutoSwitchSettings", "handleMessage - Ignore msg");
                    } else {
                        autoSwitchSettings.updatePreferences$1();
                    }
                }
            };
    public final AnonymousClass2 mBluetoothReceiver = new BroadcastReceiver() { // from class:
                // com.samsung.android.settings.multidevices.autoswitch.AutoSwitchSettings.2
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if (intent.getAction()
                            .equals(
                                    "android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED")) {
                        int intExtra =
                                intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
                        if (intExtra == 2 || intExtra == 0) {
                            sendEmptyMessageDelayed(10, 0L);
                        }
                    }
                }
            };

    public static int getStringId(int i) {
        return i == R.string.autoswitch_description_1
                ? Utils.isTablet() ? R.string.autoswitch_description_1_tablet : i
                : i == R.string.autoswitch_description_2
                        ? Utils.isTablet() ? R.string.autoswitch_description_2_tablet : i
                        : i == R.string.autoswitch_guide_header
                                ? Utils.isTablet() ? R.string.autoswitch_guide_header_tablet : i
                                : (i == R.string.autoswitch_device_list_header && Utils.isTablet())
                                        ? R.string.autoswitch_device_list_header_tablet
                                        : i;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 8202;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_autoswitch_settings;
    }

    public final Account getSamsungAccount() {
        AccountManager accountManager = AccountManager.get(this.mContext);
        if (accountManager == null) {
            return null;
        }
        Account[] accountsByType = accountManager.getAccountsByType("com.osp.app.signin");
        if (accountsByType.length > 0) {
            return accountsByType[0];
        }
        return null;
    }

    public final void initPreference$10() {
        getPreferenceScreen().removeAll();
        addPreferencesFromResource(R.xml.sec_autoswitch_settings);
        this.mSamsungAccountPref = findPreference("samsung_account_preference");
        this.mPreviewPreference =
                (SecHelpAnimationLayoutPreference) findPreference("auto_switch_preview");
        SecPreferenceCategory secPreferenceCategory =
                (SecPreferenceCategory) findPreference("auto_switch_device_list");
        this.mPrefCategory = secPreferenceCategory;
        secPreferenceCategory.setTitle(getStringId(R.string.autoswitch_device_list_header));
        this.mPreviewPreference.setDescText(
                getString(getStringId(R.string.autoswitch_guide_header)));
        LayoutPreference layoutPreference =
                (LayoutPreference) findPreference("auto_switch_dot_list");
        this.mDescListPreference = layoutPreference;
        if (layoutPreference != null) {
            ((TextView) layoutPreference.mRootView.findViewById(R.id.help_description_app_text1))
                    .setText(getStringId(R.string.autoswitch_description_1));
            ((TextView)
                            this.mDescListPreference.mRootView.findViewById(
                                    R.id.help_description_app_text2))
                    .setText(getStringId(R.string.autoswitch_description_2));
        }
        if (Utils.isNightMode(this.mContext)) {
            this.mPreviewPreference.setAnimationResource("Auto_Switch_Buds_Dark.json");
        } else {
            this.mPreviewPreference.setAnimationResource("Auto_Switch_Buds_Light.json");
        }
        if (this.mSamsungAccountPref != null) {
            Account samsungAccount = getSamsungAccount();
            this.mSamsungAccountPref.setTitle(
                    this.mContext.getString(R.string.continuity_samsung_account));
            if (samsungAccount != null) {
                this.mSamsungAccountPref.setSummary(samsungAccount.name);
                SemLog.d(
                        "AutoSwitchSettings",
                        "updateAccountInformation, account.name" + samsungAccount.name);
                this.mSamsungAccountPref.setWidgetLayoutResource(0);
                updateSettingEnabled(true);
            } else {
                this.mSamsungAccountPref.setSummary(
                        this.mContext.getString(
                                R.string.continuity_not_signed_in_to_samsung_account));
                updateSettingEnabled(false);
            }
        }
        this.mSamsungAccountPref.setOnPreferenceClickListener(
                new Preference.OnPreferenceClickListener() { // from class:
                    // com.samsung.android.settings.multidevices.autoswitch.AutoSwitchSettings$$ExternalSyntheticLambda2
                    @Override // androidx.preference.Preference.OnPreferenceClickListener
                    public final boolean onPreferenceClick(Preference preference) {
                        AutoSwitchSettings autoSwitchSettings = AutoSwitchSettings.this;
                        if (autoSwitchSettings.getSamsungAccount() != null) {
                            try {
                                Intent intent =
                                        new Intent(
                                                "com.samsung.android.mobileservice.action.ACTION_OPEN_SASETTINGS");
                                intent.setFlags(603979776);
                                autoSwitchSettings.startActivity(intent);
                                return true;
                            } catch (ActivityNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                        return false;
                    }
                });
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        initPreference$10();
        updatePreferences$1();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getActivity();
        this.mAvailableDevices = new ArrayList();
        this.mLocalManager =
                LocalBluetoothManager.getInstance(
                        this.mContext, com.android.settings.bluetooth.Utils.mOnInitCallback);
        getActivity()
                .registerReceiver(
                        this.mBluetoothReceiver,
                        new IntentFilter(
                                "android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED"));
        initPreference$10();
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(this.mBluetoothReceiver);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (preference instanceof AutoSwitchPreference) {
            boolean booleanValue = ((Boolean) obj).booleanValue();
            CachedBluetoothDevice cachedBluetoothDevice =
                    ((AutoSwitchPreference) preference).mCachedBluetoothDevice;
            FragmentActivity fragmentActivity = this.mContext;
            boolean z = AccountUtils.SupportTwoPhone;
            AccountUtils.addSamsungAccount(fragmentActivity, this, 1001, 0, UserHandle.myUserId());
            if (booleanValue) {
                cachedBluetoothDevice.mDevice.semSetAutoSwitchMode(1);
            } else {
                cachedBluetoothDevice.mDevice.semSetAutoSwitchMode(0);
            }
        }
        return true;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        updatePreferences$1();
    }

    public final void updatePreferences$1() {
        final int i = 0;
        final int i2 = 1;
        Iterator it = ((ArrayList) this.mAvailableDevices).iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (this.mPrefCategory.findPreference(str) != null) {
                SecPreferenceCategory secPreferenceCategory = this.mPrefCategory;
                secPreferenceCategory.removePreference(secPreferenceCategory.findPreference(str));
            }
        }
        if (this.mPrefCategory.findPreference("auto_switch_empty_device") != null) {
            SecPreferenceCategory secPreferenceCategory2 = this.mPrefCategory;
            secPreferenceCategory2.removePreference(
                    secPreferenceCategory2.findPreference("auto_switch_empty_device"));
        }
        if (getPreferenceScreen().findPreference("auto_switch_inset") != null) {
            getPreferenceScreen()
                    .removePreference(getPreferenceScreen().findPreference("auto_switch_inset"));
        }
        ((ArrayList) this.mAvailableDevices).clear();
        ArrayList arrayList = new ArrayList();
        this.mLocalManager.mEventManager.readPairedDevices();
        Iterator<BluetoothDevice> it2 =
                this.mLocalManager.mLocalAdapter.mAdapter.getBondedDevices().iterator();
        while (it2.hasNext()) {
            CachedBluetoothDevice findDevice =
                    this.mLocalManager.mCachedDeviceManager.findDevice(it2.next());
            if (findDevice != null) {
                arrayList.add(findDevice);
            }
        }
        List<CachedBluetoothDevice> list =
                (List)
                        arrayList.stream()
                                .filter(
                                        new Predicate() { // from class:
                                            // com.samsung.android.settings.multidevices.autoswitch.AutoSwitchSettings$$ExternalSyntheticLambda0
                                            /* JADX WARN: Removed duplicated region for block: B:20:0x0083  */
                                            /* JADX WARN: Removed duplicated region for block: B:56:0x00ae  */
                                            @Override // java.util.function.Predicate
                                            /*
                                                Code decompiled incorrectly, please refer to instructions dump.
                                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                                            */
                                            public final boolean test(java.lang.Object r15) {
                                                /*
                                                    Method dump skipped, instructions count: 220
                                                    To view this dump change 'Code comments level' option to 'DEBUG'
                                                */
                                                throw new UnsupportedOperationException(
                                                        "Method not decompiled:"
                                                            + " com.samsung.android.settings.multidevices.autoswitch.AutoSwitchSettings$$ExternalSyntheticLambda0.test(java.lang.Object):boolean");
                                            }
                                        })
                                .filter(
                                        new Predicate() { // from class:
                                            // com.samsung.android.settings.multidevices.autoswitch.AutoSwitchSettings$$ExternalSyntheticLambda0
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                /*
                                                    Method dump skipped, instructions count: 220
                                                    To view this dump change 'Code comments level' option to 'DEBUG'
                                                */
                                                throw new UnsupportedOperationException(
                                                        "Method not decompiled:"
                                                            + " com.samsung.android.settings.multidevices.autoswitch.AutoSwitchSettings$$ExternalSyntheticLambda0.test(java.lang.Object):boolean");
                                            }
                                        })
                                .sorted(new BluetoothCastSettings$$ExternalSyntheticLambda1())
                                .collect(Collectors.toList());
        if (list.isEmpty()) {
            this.mPrefCategory.removeAll();
            Preference preference = new Preference(this.mContext);
            preference.setLayoutResource(R.layout.sec_autoswitch_no_item);
            preference.setKey("auto_switch_empty_device");
            preference.setSelectable(false);
            this.mPrefCategory.addPreference(preference);
        } else {
            int i3 = 0;
            for (CachedBluetoothDevice cachedBluetoothDevice : list) {
                AutoSwitchPreference autoSwitchPreference = new AutoSwitchPreference(this.mContext);
                autoSwitchPreference.mCachedBluetoothDevice = cachedBluetoothDevice;
                autoSwitchPreference.setKey(cachedBluetoothDevice.mDevice.getAddress());
                autoSwitchPreference.setTitle(cachedBluetoothDevice.getName());
                int i4 = i3 + 1;
                autoSwitchPreference.setOrder(i3 + EnterpriseContainerConstants.SYSTEM_SIGNED_APP);
                autoSwitchPreference.setOnPreferenceChangeListener(this);
                if (this.mLocalManager.mProfileManager.mA2dpProfile.getConnectionStatus(
                                cachedBluetoothDevice.mDevice)
                        == 2) {
                    autoSwitchPreference.setSummary(getString(R.string.autoswitch_connected));
                } else {
                    autoSwitchPreference.setSummary(
                            getString(R.string.autoswitch_paired_on_previous_device));
                }
                FragmentActivity fragmentActivity = this.mContext;
                boolean z = com.android.settings.bluetooth.Utils.DEBUG;
                autoSwitchPreference.setIcon(
                        BluetoothUtils.getHostOverlayIconDrawable(
                                fragmentActivity, cachedBluetoothDevice));
                autoSwitchPreference.setChecked(
                        cachedBluetoothDevice.mDevice.semGetAutoSwitchMode() == 1
                                && getSamsungAccount() != null);
                this.mPrefCategory.addPreference(autoSwitchPreference);
                if (!((ArrayList) this.mAvailableDevices).contains(autoSwitchPreference.getKey())) {
                    ((ArrayList) this.mAvailableDevices).add(autoSwitchPreference.getKey());
                }
                i3 = i4;
            }
        }
        SecInsetCategoryPreference secInsetCategoryPreference =
                new SecInsetCategoryPreference(this.mContext);
        secInsetCategoryPreference.setKey("auto_switch_inset");
        getPreferenceScreen().addPreference(secInsetCategoryPreference);
    }

    public final void updateSettingEnabled(boolean z) {
        Preference preference = this.mSamsungAccountPref;
        if (preference == null) {
            return;
        }
        preference.setEnabled(z);
        if (this.mSamsungAccountPref.getIcon() == null) {
            return;
        }
        if (z) {
            this.mSamsungAccountPref.getIcon().setAlpha(255);
        } else {
            this.mSamsungAccountPref.getIcon().setAlpha(102);
        }
    }
}
