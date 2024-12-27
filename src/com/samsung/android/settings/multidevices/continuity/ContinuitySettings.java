package com.samsung.android.settings.multidevices.continuity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

import com.samsung.android.content.clipboard.SemClipboardManager;
import com.samsung.android.util.SemLog;
import com.sec.ims.IMSParameter;
import com.sec.ims.configuration.DATA;
import com.sec.ims.settings.ImsProfile;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ContinuitySettings extends SettingsPreferenceFragment
        implements CompoundButton.OnCheckedChangeListener, Preference.OnPreferenceChangeListener {
    public FragmentActivity mContext;
    public LocalBluetoothAdapter mLocalAdapter;
    public SecContinuityHelpAnimationLayoutPreference mPreviewPreference;
    public Preference mSamsungAccountPref;
    public int mSamsungInternet = 2;
    public int mSamsungNotes = 2;
    public final AnonymousClass1 mSettingObserver =
            new ContentObserver(
                    new Handler(
                            Looper
                                    .getMainLooper())) { // from class:
                                                         // com.samsung.android.settings.multidevices.continuity.ContinuitySettings.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z, Uri uri) {
                    Uri uri2;
                    if (uri == null
                            || (uri2 = ContinuitySettings.this.mUriContinuity) == null
                            || !uri2.equals(uri)) {
                        return;
                    }
                    boolean continuitySettingValue =
                            ContinuitySettings.getContinuitySettingValue(
                                    ContinuitySettings.this.mContext);
                    ContinuitySettings.this.updateSettingMenuEnabled(continuitySettingValue);
                    if (z) {
                        return;
                    }
                    ContinuitySettings.this.mSwitchBar.setChecked(continuitySettingValue);
                }
            };
    public SettingsMainSwitchBar mSwitchBar;
    public Uri mUriContinuity;
    public WifiManager mWifiManager;

    public static synchronized boolean getContinuitySettingValue(Context context) {
        boolean z;
        synchronized (ContinuitySettings.class) {
            z = Settings.System.getInt(context.getContentResolver(), "mcf_continuity", 0) == 1;
        }
        return z;
    }

    public static synchronized void setContinuitySettingValue(Context context, int i) {
        synchronized (ContinuitySettings.class) {
            if (i == 0 || i == 1) {
                try {
                    int i2 =
                            Settings.System.getInt(
                                    context.getContentResolver(), "mcf_continuity", -1);
                    if (i2 == -1 || i2 != i) {
                        if (Settings.System.putInt(
                                context.getContentResolver(), "mcf_continuity", i)) {
                            Intent intent = new Intent();
                            intent.setAction("com.samsung.android.mcfds.LOG_SAMSUNG_ANALYTICS");
                            intent.putExtra("type", IMSParameter.CALL.EVENT);
                            intent.putExtra("screenId", DATA.DM_FIELD_INDEX.UT_APN_NAME);
                            if (i == 1) {
                                intent.putExtra("eventId", "10100");
                                intent.putExtra("detail", "by user");
                            } else {
                                intent.putExtra("eventId", "10101");
                            }
                            intent.setPackage("com.samsung.android.mcfds");
                            context.sendBroadcast(
                                    intent,
                                    "com.samsung.android.mcfds.permission.USE_SAMSUNG_ANALYTICS_LOGGER");
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 4376;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_continuity_settings;
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

    public final void initPreference$11() {
        ArrayList<String> stringArrayList;
        getPreferenceScreen().removeAll();
        addPreferencesFromResource(R.xml.sec_continuity_settings);
        setAutoRemoveInsetCategory(false);
        if (Utils.isTablet()) {
            getPreferenceScreen().removePreference(findPreference("continuity_preview"));
            this.mPreviewPreference =
                    (SecContinuityHelpAnimationLayoutPreference)
                            findPreference("continuity_preview_tablet");
        } else {
            getPreferenceScreen().removePreference(findPreference("continuity_preview_tablet"));
            this.mPreviewPreference =
                    (SecContinuityHelpAnimationLayoutPreference)
                            findPreference("continuity_preview");
        }
        FrameLayout frameLayout =
                (FrameLayout)
                        this.mPreviewPreference.mRootView.findViewById(
                                R.id.help_animation_container);
        if (frameLayout != null) {
            frameLayout.setFocusable(false);
        }
        this.mSamsungAccountPref = findPreference("samsung_account_preference");
        updateAccountInformation$1();
        Uri uriFor = Settings.System.getUriFor("mcf_continuity");
        this.mUriContinuity = uriFor;
        if (uriFor != null) {
            this.mContext
                    .getContentResolver()
                    .registerContentObserver(this.mUriContinuity, false, this.mSettingObserver);
        }
        if (Utils.isNightMode(this.mContext)) {
            SecContinuityHelpAnimationLayoutPreference secContinuityHelpAnimationLayoutPreference =
                    this.mPreviewPreference;
            if (!"ContinueAppsOnOtherDevicesDark.json"
                    .equals(secContinuityHelpAnimationLayoutPreference.mImageValue)) {
                secContinuityHelpAnimationLayoutPreference.mImageValue =
                        "ContinueAppsOnOtherDevicesDark.json";
                secContinuityHelpAnimationLayoutPreference.notifyChanged();
            }
        } else {
            SecContinuityHelpAnimationLayoutPreference secContinuityHelpAnimationLayoutPreference2 =
                    this.mPreviewPreference;
            if (!"ContinueAppsOnOtherDevices.json"
                    .equals(secContinuityHelpAnimationLayoutPreference2.mImageValue)) {
                secContinuityHelpAnimationLayoutPreference2.mImageValue =
                        "ContinueAppsOnOtherDevices.json";
                secContinuityHelpAnimationLayoutPreference2.notifyChanged();
            }
        }
        SecContinuityHelpAnimationLayoutPreference secContinuityHelpAnimationLayoutPreference3 =
                this.mPreviewPreference;
        String str =
                this.mContext.getString(R.string.continuity_description_1)
                        + " "
                        + this.mContext.getString(R.string.continuity_description_2);
        if (str != null) {
            secContinuityHelpAnimationLayoutPreference3.mDescString1 = str;
            secContinuityHelpAnimationLayoutPreference3.notifyChanged();
        } else {
            secContinuityHelpAnimationLayoutPreference3.getClass();
        }
        SecContinuityHelpAnimationLayoutPreference secContinuityHelpAnimationLayoutPreference4 =
                this.mPreviewPreference;
        String string = this.mContext.getString(R.string.continuity_supported_apps);
        if (string != null) {
            secContinuityHelpAnimationLayoutPreference4.mDescString2 = string;
            secContinuityHelpAnimationLayoutPreference4.notifyChanged();
        } else {
            secContinuityHelpAnimationLayoutPreference4.getClass();
        }
        this.mSamsungInternet = 2;
        this.mSamsungNotes = 2;
        Bundle call =
                getContentResolver()
                        .call(
                                Uri.parse("content://com.samsung.android.mcfds.HandoffProvider"),
                                "getAppMinVersionCodes",
                                (String) null,
                                (Bundle) null);
        if (call != null && (stringArrayList = call.getStringArrayList("appList")) != null) {
            PackageManager packageManager = this.mContext.getPackageManager();
            Iterator<String> it = stringArrayList.iterator();
            while (it.hasNext()) {
                String next = it.next();
                long j = call.getLong(next);
                SemLog.d("ContinuitySettings", "packageName:" + next + ", minVersionCode:" + j);
                try {
                    PackageInfo packageInfo = packageManager.getPackageInfo(next, 0);
                    if (packageManager.checkSignatures("com.samsung.android.mcfds", next) != 0) {
                        if ("com.sec.android.app.sbrowser".equals(next)) {
                            this.mSamsungInternet = 0;
                        } else if ("com.samsung.android.app.notes".equals(next)) {
                            this.mSamsungNotes = 0;
                        }
                        SemLog.d(
                                "ContinuitySettings",
                                "getPackageInfo, " + next + " does not match signature");
                    } else if (j > packageInfo.getLongVersionCode()) {
                        if ("com.sec.android.app.sbrowser".equals(next)) {
                            this.mSamsungInternet = 1;
                        } else if ("com.samsung.android.app.notes".equals(next)) {
                            this.mSamsungNotes = 1;
                        }
                        SemLog.d("ContinuitySettings", "getPackageInfo, " + next + " needs update");
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                    if ("com.sec.android.app.sbrowser".equals(next)) {
                        this.mSamsungInternet = 0;
                    } else if ("com.samsung.android.app.notes".equals(next)) {
                        this.mSamsungNotes = 0;
                    }
                    SemLog.d("ContinuitySettings", "getPackageInfo, " + next + " is not installed");
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.mContext.getString(R.string.continuity_samsung_internet));
        int i = this.mSamsungInternet;
        if (i == 0) {
            sb.append(this.mContext.getString(R.string.continuity_not_installed));
        } else if (i == 1) {
            sb.append(this.mContext.getString(R.string.continuity_update_needed));
        }
        SecContinuityHelpAnimationLayoutPreference secContinuityHelpAnimationLayoutPreference5 =
                this.mPreviewPreference;
        String sb2 = sb.toString();
        if (sb2 != null) {
            secContinuityHelpAnimationLayoutPreference5.mDescAppString1 = sb2;
            secContinuityHelpAnimationLayoutPreference5.notifyChanged();
        } else {
            secContinuityHelpAnimationLayoutPreference5.getClass();
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(this.mContext.getString(R.string.continuity_samsung_notes));
        int i2 = this.mSamsungNotes;
        if (i2 == 0) {
            sb3.append(this.mContext.getString(R.string.continuity_not_installed));
        } else if (i2 == 1) {
            sb3.append(this.mContext.getString(R.string.continuity_update_needed));
        }
        SecContinuityHelpAnimationLayoutPreference secContinuityHelpAnimationLayoutPreference6 =
                this.mPreviewPreference;
        String sb4 = sb3.toString();
        if (sb4 != null) {
            secContinuityHelpAnimationLayoutPreference6.mDescAppString2 = sb4;
            secContinuityHelpAnimationLayoutPreference6.notifyChanged();
        } else {
            secContinuityHelpAnimationLayoutPreference6.getClass();
        }
        StringBuilder sb5 = new StringBuilder();
        SemClipboardManager semClipboardManager =
                (SemClipboardManager) this.mContext.getSystemService("semclipboard");
        if (semClipboardManager != null && semClipboardManager.isEnabled()) {
            sb5.append(
                    this.mContext.getString(
                            Utils.isTablet()
                                    ? R.string.continuity_description_4_tablet
                                    : R.string.continuity_description_4_phone));
            sb5.append("\n\n");
        }
        sb5.append(this.mContext.getString(R.string.continuity_description_5));
        sb5.append(" ");
        sb5.append(this.mContext.getString(R.string.continuity_description_6));
        SecContinuityHelpAnimationLayoutPreference secContinuityHelpAnimationLayoutPreference7 =
                this.mPreviewPreference;
        String sb6 = sb5.toString();
        if (sb6 != null) {
            secContinuityHelpAnimationLayoutPreference7.mDescString3 = sb6;
            secContinuityHelpAnimationLayoutPreference7.notifyChanged();
        } else {
            secContinuityHelpAnimationLayoutPreference7.getClass();
        }
        this.mSamsungAccountPref.setOnPreferenceClickListener(
                new Preference.OnPreferenceClickListener() { // from class:
                    // com.samsung.android.settings.multidevices.continuity.ContinuitySettings$$ExternalSyntheticLambda0
                    @Override // androidx.preference.Preference.OnPreferenceClickListener
                    public final boolean onPreferenceClick(Preference preference) {
                        ContinuitySettings continuitySettings = ContinuitySettings.this;
                        if (continuitySettings.getSamsungAccount() == null) {
                            Intent intent =
                                    new Intent(
                                            continuitySettings.mContext,
                                            (Class<?>) ContinuityAddAccountActivity.class);
                            intent.putExtra("requestCode", 1);
                            try {
                                continuitySettings.mContext.startActivity(intent);
                                return true;
                            } catch (ActivityNotFoundException unused2) {
                                return true;
                            }
                        }
                        try {
                            Intent intent2 =
                                    new Intent(
                                            "com.samsung.android.mobileservice.action.ACTION_OPEN_SASETTINGS");
                            intent2.setFlags(603979776);
                            continuitySettings.startActivity(intent2);
                            return true;
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                            return false;
                        }
                    }
                });
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        SettingsMainSwitchBar settingsMainSwitchBar =
                ((SettingsActivity) getActivity()).mMainSwitch;
        this.mSwitchBar = settingsMainSwitchBar;
        settingsMainSwitchBar.show();
        this.mSwitchBar.addOnSwitchChangeListener(this);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        boolean z2 = false;
        if (!z) {
            setContinuitySettingValue(this.mContext, 0);
            return;
        }
        if (getSamsungAccount() == null) {
            Intent intent =
                    new Intent(this.mContext, (Class<?>) ContinuityAddAccountActivity.class);
            intent.putExtra("requestCode", 1);
            try {
                this.mContext.startActivity(intent);
                return;
            } catch (ActivityNotFoundException unused) {
                return;
            }
        }
        LocalBluetoothAdapter localBluetoothAdapter = this.mLocalAdapter;
        if (localBluetoothAdapter != null && !localBluetoothAdapter.mAdapter.isEnabled()) {
            this.mLocalAdapter.setBluetoothEnabled(true);
            z2 = true;
        }
        WifiManager wifiManager = this.mWifiManager;
        if (wifiManager != null && !wifiManager.isWifiEnabled()) {
            this.mWifiManager.setWifiEnabled(true);
            z2 = true;
        }
        if (z2) {
            FragmentActivity fragmentActivity = this.mContext;
            Toast.makeText(
                            fragmentActivity,
                            fragmentActivity.getString(
                                    R.string.continuity_set_enable_description,
                                    fragmentActivity.getString(R.string.continuity_title)),
                            1)
                    .show();
        }
        setContinuitySettingValue(this.mContext, 1);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        initPreference$11();
        updateSettingMenuEnabled(getContinuitySettingValue(this.mContext));
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        this.mContext = activity;
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(
                        activity, com.android.settings.bluetooth.Utils.mOnInitCallback);
        if (localBluetoothManager != null) {
            this.mLocalAdapter = localBluetoothManager.mLocalAdapter;
        }
        this.mWifiManager = (WifiManager) this.mContext.getSystemService(ImsProfile.PDN_WIFI);
        initPreference$11();
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        if (this.mUriContinuity != null) {
            this.mContext.getContentResolver().unregisterContentObserver(this.mSettingObserver);
        }
        this.mUriContinuity = null;
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.removeOnSwitchChangeListener(this);
            this.mSwitchBar.hide();
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        synchronized (this) {
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        return true;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        updateAccountInformation$1();
        if (getSamsungAccount() != null) {
            this.mSwitchBar.setChecked(getContinuitySettingValue(this.mContext));
        } else {
            setContinuitySettingValue(this.mContext, 0);
            this.mSwitchBar.setChecked(false);
        }
        updateSettingMenuEnabled(getContinuitySettingValue(this.mContext));
    }

    public final void updateAccountInformation$1() {
        if (this.mSamsungAccountPref == null) {
            return;
        }
        Account samsungAccount = getSamsungAccount();
        this.mSamsungAccountPref.setTitle(R.string.continuity_samsung_account);
        if (samsungAccount == null) {
            this.mSamsungAccountPref.setSummary(
                    R.string.continuity_not_signed_in_to_samsung_account);
            return;
        }
        this.mSamsungAccountPref.setSummary(samsungAccount.name);
        SemLog.d(
                "ContinuitySettings",
                "updateAccountInformation, account.name" + samsungAccount.name);
        this.mSamsungAccountPref.setWidgetLayoutResource(0);
    }

    public final void updateSettingMenuEnabled(boolean z) {
        SemLog.d("ContinuitySettings", "updateSettingMenuEnabled, isEnabled: " + z);
        Preference preference = this.mSamsungAccountPref;
        if (preference == null) {
            return;
        }
        preference.getIcon();
    }
}
