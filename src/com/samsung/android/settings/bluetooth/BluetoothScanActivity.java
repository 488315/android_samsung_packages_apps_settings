package com.samsung.android.settings.bluetooth;

import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;

import com.android.settings.MainClear$$ExternalSyntheticOutline0;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.applications.appinfo.AppInfoDashboardFragment;
import com.android.settings.core.InstrumentedPreferenceFragment;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.logging.SALogging;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BluetoothScanActivity extends InstrumentedPreferenceFragment
        implements BluetoothCallback {
    public PreferenceGroup mApplicationCategory;
    public FragmentActivity mContext;
    public Preference mDescription;
    public LocalBluetoothAdapter mLocalBluetoothAdapter;
    public LocalBluetoothManager mLocalBluetoothManager;
    public PackageManager mPackageManager;
    public final List mScanServerityList = new ArrayList();
    public PreferenceScreen mScreen;
    public String mScreenId;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ScanAppInfo {
        public String mPackageName;
        public String mSeverity;
    }

    static {
        Debug.semIsProductDev();
    }

    public final void addToList(JSONArray jSONArray) {
        ScanAppInfo scanAppInfo;
        for (int i = 0; i < jSONArray.length(); i++) {
            String[] split = jSONArray.getString(i).split(":");
            String str = split[0];
            String str2 = split[1];
            scanAppInfo = new ScanAppInfo();
            scanAppInfo.mPackageName = str;
            scanAppInfo.mSeverity = str2;
            Utils$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Adding to severity list:"),
                    split[1],
                    "BluetoothScanActivity");
            String str3 = split[1];
            str3.getClass();
            switch (str3) {
                case "Low":
                    ((ArrayList) ((ArrayList) this.mScanServerityList).get(2)).add(scanAppInfo);
                    break;
                case "Mid":
                    ((ArrayList) ((ArrayList) this.mScanServerityList).get(1)).add(scanAppInfo);
                    break;
                case "High":
                    ((ArrayList) ((ArrayList) this.mScanServerityList).get(0)).add(scanAppInfo);
                    break;
                default:
                    Log.e("BluetoothScanActivity", "addToList: Ignore none severity");
                    break;
            }
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 24;
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int listHorizontalPadding = Utils.getListHorizontalPadding(this.mContext);
        setPadding(listHorizontalPadding, 0, listHorizontalPadding, 0);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.sec_bluetooth_scan_activity);
        this.mLocalBluetoothManager =
                LocalBluetoothManager.getInstance(
                        getActivity().getApplicationContext(),
                        com.android.settings.bluetooth.Utils.mOnInitCallback);
        this.mPackageManager = getActivity().getApplicationContext().getPackageManager();
        getActivity().getApplicationContext().getContentResolver();
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        if (localBluetoothManager != null) {
            this.mLocalBluetoothAdapter = localBluetoothManager.mLocalAdapter;
        }
        String string = getResources().getString(R.string.sec_bluetooth_scan_history_description);
        this.mScreen = getPreferenceScreen();
        this.mContext = getActivity();
        PreferenceGroup preferenceGroup = (PreferenceGroup) findPreference("applications_list");
        this.mApplicationCategory = preferenceGroup;
        preferenceGroup.seslSetSubheaderRoundedBackground(3);
        this.mApplicationCategory.mOrderingAsAdded = true;
        Preference findPreference = findPreference("description");
        this.mDescription = findPreference;
        findPreference.seslSetSubheaderRoundedBackground(0);
        this.mDescription.setTitle(string);
        ((ArrayList) this.mScanServerityList).add(new ArrayList());
        ((ArrayList) this.mScanServerityList).add(new ArrayList());
        ((ArrayList) this.mScanServerityList).add(new ArrayList());
        this.mScreenId = getResources().getString(R.string.screen_scan_activity);
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        Log.d("BluetoothScanActivity", "onDestroyView::");
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        String key = preference.getKey();
        DialogFragment$$ExternalSyntheticOutline0.m(
                "onPreferenceTreeClick :: Launch Application info about",
                key,
                "BluetoothScanActivity");
        SALogging.insertSALog(
                this.mScreenId,
                getResources().getString(R.string.event_scan_activity_app_list),
                key);
        Bundle bundle = new Bundle();
        bundle.putString("package", key);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(getContext());
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mSourceMetricsCategory = 24;
        launchRequest.mDestinationName = AppInfoDashboardFragment.class.getName();
        launchRequest.mArguments = bundle;
        subSettingLauncher.setTitleRes(R.string.application_info_label, null);
        subSettingLauncher.launch();
        return false;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        Preference preference;
        super.onResume();
        ((ArrayList) ((ArrayList) this.mScanServerityList).get(0)).clear();
        ((ArrayList) ((ArrayList) this.mScanServerityList).get(1)).clear();
        ((ArrayList) ((ArrayList) this.mScanServerityList).get(2)).clear();
        Log.d("BluetoothScanActivity", "refreshApplicationPreferences :: ");
        this.mApplicationCategory.removeAll();
        this.mScreen.removePreference(this.mDescription);
        getListView().mDrawLastRoundedCorner = true;
        this.mScreen.addPreference(this.mDescription);
        LocalBluetoothAdapter localBluetoothAdapter = this.mLocalBluetoothAdapter;
        String currentScanStats =
                localBluetoothAdapter != null
                        ? localBluetoothAdapter.mAdapter.getCurrentScanStats()
                        : null;
        if (currentScanStats != null) {
            try {
                addToList(new JSONArray(currentScanStats));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("BluetoothScanActivity", "Added to list!! now creating pref");
            Iterator it = ((ArrayList) this.mScanServerityList).iterator();
            while (it.hasNext()) {
                Iterator it2 = ((ArrayList) it.next()).iterator();
                while (it2.hasNext()) {
                    ScanAppInfo scanAppInfo = (ScanAppInfo) it2.next();
                    StringBuilder sb =
                            new StringBuilder("createApplicationPreference :: package name is ");
                    String str = scanAppInfo.mPackageName;
                    String str2 = ApnSettings.MVNO_NONE;
                    if (str == null) {
                        str = ApnSettings.MVNO_NONE;
                    }
                    String str3 = scanAppInfo.mSeverity;
                    MainClearConfirm$$ExternalSyntheticOutline0.m(sb, str, "BluetoothScanActivity");
                    try {
                        PackageManager packageManager = this.mPackageManager;
                        String str4 = scanAppInfo.mPackageName;
                        if (str4 == null) {
                            str4 = ApnSettings.MVNO_NONE;
                        }
                        String charSequence =
                                this.mPackageManager
                                        .getApplicationLabel(
                                                packageManager.getApplicationInfo(str4, 0))
                                        .toString();
                        String string =
                                str3.equals("Low")
                                        ? getResources()
                                                .getString(R.string.sec_bluetooth_scan_low_usage)
                                        : str3.equals("Mid")
                                                ? getResources()
                                                        .getString(
                                                                R.string
                                                                        .sec_bluetooth_scan_mid_usage)
                                                : str3.equals("High")
                                                        ? getResources()
                                                                .getString(
                                                                        R.string
                                                                                .sec_bluetooth_scan_high_usage)
                                                        : ApnSettings.MVNO_NONE;
                        PackageManager packageManager2 = this.mPackageManager;
                        String str5 = scanAppInfo.mPackageName;
                        if (str5 == null) {
                            str5 = ApnSettings.MVNO_NONE;
                        }
                        Drawable applicationIcon = packageManager2.getApplicationIcon(str5);
                        if (applicationIcon == null) {
                            applicationIcon = this.mPackageManager.getDefaultActivityIcon();
                        }
                        preference = new Preference(requireActivity());
                        preference.setLayoutResource(R.layout.sec_preference_bluetooth_control_app);
                        String str6 = scanAppInfo.mPackageName;
                        if (str6 != null) {
                            str2 = str6;
                        }
                        preference.setKey(str2);
                        preference.setTitle(charSequence);
                        preference.setSummary(string);
                        preference.setIcon(applicationIcon);
                    } catch (PackageManager.NameNotFoundException unused) {
                        StringBuilder sb2 =
                                new StringBuilder(
                                        "createApplicationPreference :: Occurs"
                                            + " NameNotFoundException about ");
                        String str7 = scanAppInfo.mPackageName;
                        if (str7 != null) {
                            str2 = str7;
                        }
                        MainClear$$ExternalSyntheticOutline0.m(sb2, str2, "BluetoothScanActivity");
                        preference = null;
                    }
                    if (preference != null) {
                        this.mApplicationCategory.addPreference(preference);
                    } else {
                        Log.d(
                                "BluetoothScanActivity",
                                "addApplicationPreferences :: Can't get Application Information"
                                    + " about : ");
                    }
                }
            }
        }
        int preferenceCount = this.mApplicationCategory.getPreferenceCount();
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                preferenceCount,
                "addApplicationPreferences :: listAppCount: ",
                "BluetoothScanActivity");
        if (preferenceCount >= 0) {
            SALogging.insertSALog(
                    getResources().getString(R.string.screen_bluetooth_global),
                    getResources().getString(R.string.event_bluetooth_bsac),
                    preferenceCount <= 10
                            ? Integer.toString(preferenceCount)
                            : preferenceCount <= 20
                                    ? getResources()
                                            .getString(
                                                    R.string.detail_bluetooth_bcac_count_10_to_20)
                                    : getResources()
                                            .getString(
                                                    R.string.detail_bluetooth_bcac_count_more_20));
        }
        if (preferenceCount == 0) {
            Log.d(
                    "BluetoothScanActivity",
                    "addNoApplicationsPreference :: Does not found Bluetooth scan application");
            this.mApplicationCategory.removeAll();
            Preference preference2 = new Preference(requireActivity());
            preference2.setLayoutResource(R.layout.sec_preference_bluetooth_list_no_item);
            preference2.setKey("idle");
            preference2.setSelectable(false);
            this.mApplicationCategory.addPreference(preference2);
        }
        SALogging.insertSALog(this.mScreenId);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onAudioModeChanged() {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onBluetoothStateChanged(int i) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceAdded(CachedBluetoothDevice cachedBluetoothDevice) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceDeleted(CachedBluetoothDevice cachedBluetoothDevice) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onScanningStateChanged(boolean z) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onActiveDeviceChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onConnectionStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i) {}

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(Bundle bundle, String str) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceBondStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i) {}
}
