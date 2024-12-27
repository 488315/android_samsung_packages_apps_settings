package com.samsung.android.settings.bluetooth;

import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.MenuItem;

import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.applications.appinfo.AppInfoDashboardFragment;
import com.android.settings.core.InstrumentedPreferenceFragment;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.logging.SALogging;

import java.text.DateFormat;
import java.util.Date;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BluetoothControlHistory extends InstrumentedPreferenceFragment
        implements BluetoothCallback {
    public PreferenceGroup mApplicationCategory;
    public FragmentActivity mContext;
    public DateFormat mDateFormat;
    public Preference mDescription;
    public LocalBluetoothManager mLocalManager;
    public PackageManager mPackageManager;
    public ContentResolver mResolver;
    public PreferenceScreen mScreen;
    public String mScreenId;
    public DateFormat mTimeFormat;
    public static final boolean DBG = Debug.semIsProductDev();
    public static final String CLAUSE_ONLY_3RD_PARTY_APP =
            "caller_name!= 'com.android.bluetooth' AND caller_name!= 'com.android.nfc' AND"
                + " caller_name!= 'com.android.settings' AND caller_name!= 'com.android.systemui'"
                + " AND caller_name!= 'com.samsung.android.easysetup' AND caller_name!="
                + " 'com.samsung.android.oneconnect' AND caller_name!="
                + " 'com.sec.android.easysettings' AND caller_name!="
                + " 'com.sec.android.emergencymode.service' AND caller_name!="
                + " 'com.samsung.android.bixby.agent'";

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ControlAppInfo {
        public int mLastEvent;
        public long mLastEventTime;
        public String mPackageName;
    }

    public final Preference createApplicationPreference(ControlAppInfo controlAppInfo) {
        boolean z = DBG;
        String str = ApnSettings.MVNO_NONE;
        if (z) {
            String str2 = controlAppInfo.mPackageName;
            if (str2 == null) {
                str2 = ApnSettings.MVNO_NONE;
            }
            Log.d(
                    "BluetoothControlHistory",
                    "createApplicationPreference :: package name is ".concat(str2));
        }
        try {
            PackageManager packageManager = this.mPackageManager;
            String str3 = controlAppInfo.mPackageName;
            if (str3 == null) {
                str3 = ApnSettings.MVNO_NONE;
            }
            String charSequence =
                    this.mPackageManager
                            .getApplicationLabel(packageManager.getApplicationInfo(str3, 0))
                            .toString();
            String btControlHistory =
                    getBtControlHistory(controlAppInfo.mLastEvent, controlAppInfo.mLastEventTime);
            PackageManager packageManager2 = this.mPackageManager;
            String str4 = controlAppInfo.mPackageName;
            if (str4 == null) {
                str4 = ApnSettings.MVNO_NONE;
            }
            Drawable applicationIcon = packageManager2.getApplicationIcon(str4);
            if (applicationIcon == null) {
                applicationIcon = this.mPackageManager.getDefaultActivityIcon();
            }
            Preference preference = new Preference(getActivity());
            preference.setLayoutResource(R.layout.sec_preference_bluetooth_control_app);
            String str5 = controlAppInfo.mPackageName;
            if (str5 != null) {
                str = str5;
            }
            preference.setKey(str);
            preference.setTitle(charSequence);
            preference.setSummary(btControlHistory);
            preference.setIcon(applicationIcon);
            return preference;
        } catch (PackageManager.NameNotFoundException unused) {
            String str6 = controlAppInfo.mPackageName;
            if (str6 != null) {
                str = str6;
            }
            Log.e(
                    "BluetoothControlHistory",
                    "createApplicationPreference :: Occurs NameNotFoundException about "
                            .concat(str));
            return null;
        }
    }

    public final String getBtControlHistory(int i, long j) {
        if (DBG) {
            Log.d(
                    "BluetoothControlHistory",
                    "getBtControlSummary :: event = " + i + ", Timestamp = " + j);
        }
        this.mDateFormat = android.text.format.DateFormat.getDateFormat(getActivity());
        this.mTimeFormat = android.text.format.DateFormat.getTimeFormat(getActivity());
        Date date = new Date();
        date.setTime(j);
        String str = this.mDateFormat.format(date) + " \u200e " + this.mTimeFormat.format(date);
        if (i == 0) {
            return getActivity()
                    .getString(
                            R.string.sec_bluetooth_control_app_disable_summary, new Object[] {str});
        }
        if (i == 1) {
            return getActivity()
                    .getString(
                            R.string.sec_bluetooth_control_app_enable_summary, new Object[] {str});
        }
        Log.e("BluetoothControlHistory", "getBtControlSummary :: Does not have on/off history");
        return ApnSettings.MVNO_NONE;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 24;
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onBluetoothStateChanged(int i) {
        if (i == 10 || i == 12) {
            Log.d(
                    "BluetoothControlHistory",
                    "onBluetoothStateChanged :: Bluetooth State change to "
                            + i
                            + ", it will update list");
            refreshApplicationPreferences();
        }
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
        addPreferencesFromResource(R.xml.sec_bluetooth_control_history);
        this.mScreen = getPreferenceScreen();
        this.mContext = getActivity();
        PreferenceGroup preferenceGroup = (PreferenceGroup) findPreference("applications_list");
        this.mApplicationCategory = preferenceGroup;
        preferenceGroup.seslSetSubheaderRoundedBackground(3);
        this.mApplicationCategory.mOrderingAsAdded = true;
        Preference findPreference = findPreference("description");
        this.mDescription = findPreference;
        findPreference.seslSetSubheaderRoundedBackground(0);
        this.mLocalManager =
                LocalBluetoothManager.getInstance(
                        getActivity().getApplicationContext(),
                        com.android.settings.bluetooth.Utils.mOnInitCallback);
        setHasOptionsMenu(true);
        this.mScreenId = getResources().getString(R.string.screen_control_history);
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        Log.d("BluetoothControlHistory", "onDestroyView::");
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            SALogging.insertSALog(
                    this.mScreenId,
                    getResources().getString(R.string.event_control_history_navigate_button));
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        LocalBluetoothManager localBluetoothManager = this.mLocalManager;
        if (localBluetoothManager != null) {
            localBluetoothManager.mEventManager.unregisterCallback(this);
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        String key = preference.getKey();
        DialogFragment$$ExternalSyntheticOutline0.m(
                "onPreferenceTreeClick :: Launch Application info about",
                key,
                "BluetoothControlHistory");
        SALogging.insertSALog(
                this.mScreenId,
                getResources().getString(R.string.event_control_history_app_list),
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
        super.onResume();
        LocalBluetoothManager localBluetoothManager = this.mLocalManager;
        if (localBluetoothManager == null) {
            Log.e("BluetoothControlHistory", "onCreate() :: mLocalManager is null");
            return;
        }
        localBluetoothManager.mEventManager.registerCallback(this);
        this.mPackageManager = getActivity().getApplicationContext().getPackageManager();
        this.mResolver = getActivity().getApplicationContext().getContentResolver();
        refreshApplicationPreferences();
        SALogging.insertSALog(this.mScreenId);
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x00d0, code lost:

       if (0 == 0) goto L23;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void refreshApplicationPreferences() {
        /*
            Method dump skipped, instructions count: 331
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.bluetooth.BluetoothControlHistory.refreshApplicationPreferences():void");
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onAudioModeChanged() {}

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
