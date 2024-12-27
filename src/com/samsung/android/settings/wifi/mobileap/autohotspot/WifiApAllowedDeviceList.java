package com.samsung.android.settings.wifi.mobileap.autohotspot;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.core.content.res.TypedArrayUtils;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.R$styleable;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.dashboard.DashboardFragment;

import com.samsung.android.settings.lockscreen.SecInsetCategory;
import com.samsung.android.settings.wifi.mobileap.WifiApCustomPreference;
import com.samsung.android.wifi.SemWifiApBleScanResult;
import com.samsung.android.wifi.SemWifiApContentProviderHelper;
import com.samsung.android.wifi.SemWifiApSmartWhiteList;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApAllowedDeviceList extends DashboardFragment {
    public FragmentActivity mActivity;
    public SecInsetCategory mAllowedDeviceListPreferenceCategory;
    public Context mContext;
    public IntentFilter mFilter;
    public SemWifiManager mSemWifiManager;
    public MenuItem progressBarItem;
    public List mScanResultDevices = null;
    public final AnonymousClass1 mHandler =
            new Handler() { // from class:
                            // com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApAllowedDeviceList.1
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    int i = message.what;
                    if (i == 2) {
                        if (WifiApAllowedDeviceList.this.getActivity() != null) {
                            Toast.makeText(
                                            WifiApAllowedDeviceList.this.getActivity(),
                                            R.string.d2d_ble_bonding_failed_toast,
                                            1)
                                    .show();
                            return;
                        }
                        return;
                    }
                    if (i == 3) {
                        WifiApAllowedDeviceList.this.showAllowedListPreference();
                        return;
                    }
                    if (i != 4) {
                        return;
                    }
                    String str = (String) message.obj;
                    SecInsetCategory secInsetCategory =
                            WifiApAllowedDeviceList.this.mAllowedDeviceListPreferenceCategory;
                    if (secInsetCategory != null) {
                        synchronized (secInsetCategory) {
                            try {
                                Preference m1362$$Nest$mfindPreferenceFromAllowedList =
                                        WifiApAllowedDeviceList
                                                .m1362$$Nest$mfindPreferenceFromAllowedList(
                                                        WifiApAllowedDeviceList.this, str);
                                if (m1362$$Nest$mfindPreferenceFromAllowedList != null) {
                                    WifiApAllowedDeviceList.this
                                            .mAllowedDeviceListPreferenceCategory.removePreference(
                                            m1362$$Nest$mfindPreferenceFromAllowedList);
                                    Log.d(
                                            "WifiApAllowedDeviceList",
                                            "removed preference:" + str.substring(9));
                                }
                            } finally {
                            }
                        }
                    }
                }
            };
    public final AnonymousClass2 mSemWifiApSmartCallback =
            new SemWifiManager.SemWifiApSmartCallback() { // from class:
                // com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApAllowedDeviceList.2
                public final void onStateChanged(int i, String str) {
                    if (SemWifiManager.MHSDBG) {
                        Log.d(
                                "WifiApAllowedDeviceList",
                                "SemWifiApSmartCallback`s onStateChanged() - state: "
                                        + i
                                        + ", mhsMac: "
                                        + str);
                    }
                    if (str != null) {
                        Preference m1362$$Nest$mfindPreferenceFromAllowedList =
                                WifiApAllowedDeviceList.m1362$$Nest$mfindPreferenceFromAllowedList(
                                        WifiApAllowedDeviceList.this, str);
                        if (m1362$$Nest$mfindPreferenceFromAllowedList == null) {
                            Log.i(
                                    "WifiApAllowedDeviceList",
                                    "SemWifiApSmartCallback`s onStateChanged() - preference not"
                                        + " found ");
                            return;
                        }
                        if (i == 4) {
                            Message message = new Message();
                            message.what = 4;
                            message.obj = str;
                            AnonymousClass1 anonymousClass1 = WifiApAllowedDeviceList.this.mHandler;
                            if (anonymousClass1 != null) {
                                anonymousClass1.sendMessageDelayed(message, 3000L);
                                sendEmptyMessageDelayed(3, 10000L);
                                return;
                            }
                            return;
                        }
                        if (i >= 0) {
                            if (i >= 1) {
                                Log.i(
                                        "WifiApAllowedDeviceList",
                                        "SemWifiApSmartCallback`s onStateChanged() - preference in"
                                            + " connecting state ");
                                m1362$$Nest$mfindPreferenceFromAllowedList.setSummary(
                                        R.string.smart_tethering_ap_connecting_summary);
                                return;
                            } else {
                                if (i == 0) {
                                    Log.i(
                                            "WifiApAllowedDeviceList",
                                            "SemWifiApSmartCallback`s onStateChanged() - preference"
                                                + " in disconnected state ");
                                    m1362$$Nest$mfindPreferenceFromAllowedList.setSummary(
                                            (CharSequence) null);
                                    return;
                                }
                                return;
                            }
                        }
                        Log.i(
                                "WifiApAllowedDeviceList",
                                "SemWifiApSmartCallback`s onStateChanged() - found preference: "
                                        + m1362$$Nest$mfindPreferenceFromAllowedList);
                        Message message2 = new Message();
                        message2.what = 4;
                        message2.obj = str;
                        m1362$$Nest$mfindPreferenceFromAllowedList.setEnabled(false);
                        m1362$$Nest$mfindPreferenceFromAllowedList.setSummary((CharSequence) null);
                        AnonymousClass1 anonymousClass12 = WifiApAllowedDeviceList.this.mHandler;
                        if (anonymousClass12 != null) {
                            anonymousClass12.sendEmptyMessage(2);
                            sendMessageDelayed(message2, 3000L);
                        }
                    }
                }
            };
    public final AnonymousClass3 mListener =
            new WifiApCustomPreference.AutoHotspotCustomPreferenceListener() { // from class:
                // com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApAllowedDeviceList.3
                @Override // com.samsung.android.settings.wifi.mobileap.WifiApCustomPreference.AutoHotspotCustomPreferenceListener
                public final void onPreferenceClicked(Object obj) {
                    Log.i(
                            "WifiApAllowedDeviceList",
                            "mAutoHotspotCustomPreferenceListener`s onPreferenceClicked() -"
                                + " Triggered");
                    if (obj instanceof SemWifiApBleScanResult) {
                        SemWifiApBleScanResult semWifiApBleScanResult =
                                (SemWifiApBleScanResult) obj;
                        String str = semWifiApBleScanResult.mWifiMac;
                        String str2 = semWifiApBleScanResult.mDevice;
                        String str3 = semWifiApBleScanResult.mSSID;
                        boolean z = SemWifiManager.MHSDBG;
                        if (z) {
                            MainClearConfirm$$ExternalSyntheticOutline0.m(
                                    SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                            "onPreferenceClick() - bleAddr: ",
                                            str2,
                                            ", mac: ",
                                            str,
                                            " ssid: "),
                                    str3,
                                    "WifiApAllowedDeviceList");
                        }
                        SemWifiApSmartWhiteList.getInstance().getIterator();
                        int i = 0;
                        if (z) {
                            StringBuilder sb =
                                    new StringBuilder(
                                            "onPreferenceClick() - SemWifiApSmartWhiteList size(");
                            sb.append(SemWifiApSmartWhiteList.getInstance().getSize());
                            sb.append(") <= MAX_ALLOWED_DEVICES(10):");
                            ActionBarContextView$$ExternalSyntheticOutline0.m(
                                    sb,
                                    SemWifiApSmartWhiteList.getInstance().getSize() < 10,
                                    "WifiApAllowedDeviceList");
                        }
                        if (SemWifiApSmartWhiteList.getInstance().getSize() >= 10) {
                            Log.e(
                                    "WifiApAllowedDeviceList",
                                    "onPreferenceClick() - Already MAX allowed devices limit is"
                                        + " reached");
                            Toast.makeText(
                                            WifiApAllowedDeviceList.this.mContext,
                                            WifiApAllowedDeviceList.this
                                                    .getActivity()
                                                    .getResources()
                                                    .getString(
                                                            R.string.wifi_ap_warn_max_whitelist,
                                                            10),
                                            1)
                                    .show();
                            return;
                        }
                        WifiApAllowedDeviceList wifiApAllowedDeviceList =
                                WifiApAllowedDeviceList.this;
                        synchronized (
                                wifiApAllowedDeviceList.mAllowedDeviceListPreferenceCategory) {
                            int preferenceCount =
                                    wifiApAllowedDeviceList.mAllowedDeviceListPreferenceCategory
                                            .getPreferenceCount();
                            while (true) {
                                if (i >= preferenceCount) {
                                    Log.i(
                                            "WifiApAllowedDeviceList",
                                            "checkIfAnyPreferenceIsConnecting() - no other"
                                                + " preference is in connecting state");
                                    WifiApAllowedDeviceList wifiApAllowedDeviceList2 =
                                            WifiApAllowedDeviceList.this;
                                    wifiApAllowedDeviceList2.mSemWifiManager
                                            .connectToSmartD2DClient(
                                                    str2,
                                                    str,
                                                    wifiApAllowedDeviceList2
                                                            .mSemWifiApSmartCallback);
                                    break;
                                }
                                Preference preference =
                                        wifiApAllowedDeviceList.mAllowedDeviceListPreferenceCategory
                                                .getPreference(i);
                                int smartD2DClientConnectedStatus =
                                        wifiApAllowedDeviceList.mSemWifiManager
                                                .getSmartD2DClientConnectedStatus(
                                                        preference.getKey());
                                Log.i(
                                        "WifiApAllowedDeviceList",
                                        "checkIfAnyPreferenceIsConnecting() - Processing ssid: "
                                                + ((Object) preference.getTitle())
                                                + ", connectionState: "
                                                + smartD2DClientConnectedStatus);
                                if (smartD2DClientConnectedStatus >= 1) {
                                    Log.i(
                                            "WifiApAllowedDeviceList",
                                            "checkIfAnyPreferenceIsConnecting() - ssid: "
                                                    + ((Object) preference.getTitle())
                                                    + " is already in connecting state. State: "
                                                    + smartD2DClientConnectedStatus);
                                    Log.i(
                                            "WifiApAllowedDeviceList",
                                            "onPreferenceClick() - connectToSmartD2DClient() is not"
                                                + " called as some other preference is in"
                                                + " connecting state");
                                    break;
                                }
                                i++;
                            }
                        }
                    }
                }

                @Override // com.samsung.android.settings.wifi.mobileap.WifiApCustomPreference.AutoHotspotCustomPreferenceListener
                public final void onSecondaryIconClicked(Object obj) {
                    Log.i(
                            "WifiApAllowedDeviceList",
                            "mAutoHotspotCustomPreferenceListener`s onSecondaryIconClicked() -"
                                + " Triggered");
                }
            };
    public final AnonymousClass4 mReceiver = new BroadcastReceiver() { // from class:
                // com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApAllowedDeviceList.4
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if ("com.samsung.android.server.wifi.softap.smarttethering.d2dClientUpdate"
                            .equals(action)) {
                        Log.d(
                                "WifiApAllowedDeviceList",
                                "mReceiver`s onReceive() - Received SCAN_LIST_UPDATED_INTENT");
                        WifiApAllowedDeviceList.this.showAllowedListPreference();
                        return;
                    }
                    if (!action.equals("android.intent.action.SCREEN_ON")
                            && !action.equals("android.intent.action.SCREEN_OFF")) {
                        if (action.equals(
                                "com.samsung.android.server.wifi.softap.smarttethering.familyid")) {
                            String str =
                                    SemWifiApContentProviderHelper.get(
                                            context, "hash_value_based_on_familyid");
                            if ((!str.isEmpty() ? Long.parseLong(str) : -1L) == -1) {
                                Log.e(
                                        "WifiApAllowedDeviceList",
                                        "familyID is -1, so closing activity");
                                WifiApAllowedDeviceList.this.finish();
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    PowerManager powerManager =
                            (PowerManager)
                                    WifiApAllowedDeviceList.this.mContext.getSystemService("power");
                    if (powerManager == null) {
                        Log.e(
                                "WifiApAllowedDeviceList",
                                "mReceiver`s onReceive - fail to get PowerManager reference");
                        return;
                    }
                    if (powerManager.isInteractive()) {
                        Log.e(
                                "WifiApAllowedDeviceList",
                                "mReceiver`s onReceive - powerManager.isInteractive() is true,"
                                    + " Start scanning devices");
                        WifiApAllowedDeviceList wifiApAllowedDeviceList =
                                WifiApAllowedDeviceList.this;
                        wifiApAllowedDeviceList.getClass();
                        Log.i("WifiApAllowedDeviceList", "startScanningDevices() - Triggered");
                        wifiApAllowedDeviceList.mSemWifiManager.wifiApBleD2DMhsRole(true);
                        return;
                    }
                    Log.e(
                            "WifiApAllowedDeviceList",
                            "mReceiver`s onReceive - powerManager.isInteractive() is false, Stop"
                                + " scanning devices");
                    WifiApAllowedDeviceList wifiApAllowedDeviceList2 = WifiApAllowedDeviceList.this;
                    wifiApAllowedDeviceList2.getClass();
                    Log.i("WifiApAllowedDeviceList", "stopScanningDevices() - Triggered");
                    wifiApAllowedDeviceList2.mSemWifiManager.wifiApBleD2DMhsRole(false);
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SortWifiApBleScanList implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            SemWifiApBleScanResult semWifiApBleScanResult = (SemWifiApBleScanResult) obj;
            SemWifiApBleScanResult semWifiApBleScanResult2 = (SemWifiApBleScanResult) obj2;
            if (semWifiApBleScanResult.mSSID == null) {
                Log.i(
                        "WifiApAllowedDeviceList",
                        "SortWifiApBleScanList`s compare() : m1.mSSID is null");
                return 1;
            }
            if (semWifiApBleScanResult2.mSSID == null) {
                Log.i(
                        "WifiApAllowedDeviceList",
                        "SortWifiApBleScanList`s compare() : m2.mSSID is null");
                return -1;
            }
            Log.i(
                    "WifiApAllowedDeviceList",
                    "SortWifiApBleScanList`s compare() - m1.mSSID.compareTo(m2.mSSID): "
                            + semWifiApBleScanResult.mSSID.compareTo(
                                    semWifiApBleScanResult2.mSSID));
            return semWifiApBleScanResult.mSSID.compareTo(semWifiApBleScanResult2.mSSID);
        }
    }

    /* renamed from: -$$Nest$mfindPreferenceFromAllowedList, reason: not valid java name */
    public static Preference m1362$$Nest$mfindPreferenceFromAllowedList(
            WifiApAllowedDeviceList wifiApAllowedDeviceList, String str) {
        wifiApAllowedDeviceList.getClass();
        if (SemWifiManager.MHSDBG) {
            DialogFragment$$ExternalSyntheticOutline0.m(
                    "findPreferenceFromAllowedList() - mac: ", str, "WifiApAllowedDeviceList");
        }
        Preference findPreference =
                wifiApAllowedDeviceList.mAllowedDeviceListPreferenceCategory.findPreference(str);
        if (findPreference != null) {
            Log.i(
                    "WifiApAllowedDeviceList",
                    "findPreferenceFromAllowedList() - found preference: " + findPreference);
        } else {
            Log.i(
                    "WifiApAllowedDeviceList",
                    "findPreferenceFromAllowedList() - preference not found ");
        }
        return findPreference;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "WifiApAllowedDeviceList";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 3400;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_wifi_ap_auto_hotspot_allowed_device_list;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        Log.i("WifiApAllowedDeviceList", "onActivityCreated() - Triggered");
        super.onActivityCreated(bundle);
        this.mAllowedDeviceListPreferenceCategory =
                (SecInsetCategory) findPreference("allowed_device_list_category");
        IntentFilter intentFilter = new IntentFilter();
        this.mFilter = intentFilter;
        intentFilter.addAction(
                "com.samsung.android.server.wifi.softap.smarttethering.d2dClientUpdate");
        this.mFilter.addAction("android.intent.action.SCREEN_ON");
        this.mFilter.addAction("android.intent.action.SCREEN_OFF");
        this.mFilter.addAction("com.samsung.android.server.wifi.softap.smarttethering.familyid");
        this.mSemWifiManager =
                (SemWifiManager) getActivity().getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        Log.i("WifiApAllowedDeviceList", "onCreate() - Triggered");
        super.onCreate(bundle);
        this.mContext = getActivity().getApplicationContext();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final Dialog onCreateDialog(int i) {
        return null;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        Log.i("WifiApAllowedDeviceList", "onCreateOptionsMenu() - Triggered");
        MenuItem add = menu.add(0, 1, 0, (CharSequence) null);
        this.progressBarItem = add;
        add.setShowAsAction(2);
        this.progressBarItem.setActionView(R.layout.sec_wifi_ap_add_allowed_device_progressbar);
        this.progressBarItem.setEnabled(true);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Log.i("WifiApAllowedDeviceList", "onCreateView() - Triggered");
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        Log.i("WifiApAllowedDeviceList", "onDestroyView() - Triggered");
        super.onDestroyView();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        Log.i("WifiApAllowedDeviceList", "onPause() - Triggered");
        getActivity().unregisterReceiver(this.mReceiver);
        super.onPause();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        Log.i("WifiApAllowedDeviceList", "onResume() - Triggered");
        super.onResume();
        getActivity().registerReceiver(this.mReceiver, this.mFilter, 2);
        showAllowedListPreference();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        Log.i("WifiApAllowedDeviceList", "onStart() - Triggered");
        super.onStart();
        Log.i("WifiApAllowedDeviceList", "startScanningDevices() - Triggered");
        this.mSemWifiManager.wifiApBleD2DMhsRole(true);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        Log.i("WifiApAllowedDeviceList", "onStop() - Triggered");
        Log.i("WifiApAllowedDeviceList", "stopScanningDevices() - Triggered");
        this.mSemWifiManager.wifiApBleD2DMhsRole(false);
        super.onStop();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        boolean z = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
        TypedArray typedArray = null;
        try {
            try {
                typedArray =
                        getContext()
                                .obtainStyledAttributes(
                                        null,
                                        R$styleable.PreferenceFragment,
                                        TypedArrayUtils.getAttr(
                                                getContext(),
                                                R.attr.preferenceFragmentStyle,
                                                android.R.attr.preferenceFragmentStyle),
                                        0);
                Drawable drawable = typedArray.getDrawable(1);
                int dimensionPixelSize = typedArray.getDimensionPixelSize(2, -1);
                super.setDivider(
                        !z
                                ? new InsetDrawable(
                                        drawable,
                                        getResources()
                                                .getDimensionPixelSize(
                                                        R.dimen
                                                                .wifi_ap_start_margin_for_divider_inset),
                                        0,
                                        0,
                                        0)
                                : new InsetDrawable(
                                        drawable,
                                        0,
                                        0,
                                        getResources()
                                                .getDimensionPixelSize(
                                                        R.dimen
                                                                .wifi_ap_start_margin_for_divider_inset),
                                        0));
                if (dimensionPixelSize != -1) {
                    super.setDividerHeight(dimensionPixelSize);
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (typedArray == null) {
                    return;
                }
            }
            typedArray.recycle();
        } catch (Throwable th) {
            if (typedArray != null) {
                typedArray.recycle();
            }
            throw th;
        }
    }

    public final void showAllowedListPreference() {
        Log.i("WifiApAllowedDeviceList", "showAllowedListPreference() : Triggered");
        List wifiApBleD2DScanDetail = this.mSemWifiManager.getWifiApBleD2DScanDetail();
        this.mScanResultDevices = wifiApBleD2DScanDetail;
        if (wifiApBleD2DScanDetail == null || wifiApBleD2DScanDetail.size() <= 0) {
            Log.i("WifiApAllowedDeviceList", "showEmptyScreenPreference() - Triggered");
            this.mAllowedDeviceListPreferenceCategory.removeAll();
            return;
        }
        Iterator it = this.mScanResultDevices.iterator();
        while (it.hasNext()) {
            Utils$$ExternalSyntheticOutline0.m(
                    new StringBuilder("showAllowedListPreference() - Before sort: "),
                    ((SemWifiApBleScanResult) it.next()).mSSID,
                    "WifiApAllowedDeviceList");
        }
        Collections.sort(this.mScanResultDevices, new SortWifiApBleScanList());
        Iterator it2 = this.mScanResultDevices.iterator();
        while (it2.hasNext()) {
            Utils$$ExternalSyntheticOutline0.m(
                    new StringBuilder("showAllowedListPreference() - After sort: "),
                    ((SemWifiApBleScanResult) it2.next()).mSSID,
                    "WifiApAllowedDeviceList");
        }
        List<SemWifiApBleScanResult> list = this.mScanResultDevices;
        synchronized (this.mAllowedDeviceListPreferenceCategory) {
            try {
                Log.i(
                        "WifiApAllowedDeviceList",
                        "updateAllowedDevicePreferenceList() - Triggered, Number of devices"
                            + " received: "
                                + list.size());
                this.mAllowedDeviceListPreferenceCategory.removeAll();
                int i = 0;
                for (SemWifiApBleScanResult semWifiApBleScanResult : list) {
                    WifiApCustomPreference wifiApCustomPreference =
                            new WifiApCustomPreference(
                                    getPrefContext(), semWifiApBleScanResult, this.mListener);
                    int smartD2DClientConnectedStatus =
                            this.mSemWifiManager.getSmartD2DClientConnectedStatus(
                                    semWifiApBleScanResult.mWifiMac);
                    Log.i(
                            "WifiApAllowedDeviceList",
                            "updateAllowedDevicePreferenceList() - tempDevice status: "
                                    + smartD2DClientConnectedStatus);
                    if (smartD2DClientConnectedStatus < 0) {
                        wifiApCustomPreference.setEnabled(false);
                        wifiApCustomPreference.setSummary((CharSequence) null);
                    } else {
                        if (smartD2DClientConnectedStatus >= 1) {
                            wifiApCustomPreference.setEnabled(true);
                            wifiApCustomPreference.setSummary(
                                    R.string.smart_tethering_ap_connecting_summary);
                        } else if (smartD2DClientConnectedStatus == 0) {
                            wifiApCustomPreference.setEnabled(true);
                            wifiApCustomPreference.setSummary((CharSequence) null);
                        }
                        if (SemWifiApSmartWhiteList.getInstance()
                                .isContains(semWifiApBleScanResult.mWifiMac)) {
                            String deviceName =
                                    SemWifiApSmartWhiteList.getInstance()
                                            .getDeviceName(semWifiApBleScanResult.mWifiMac);
                            Log.i(
                                    "WifiApAllowedDeviceList",
                                    "updateAllowedDevicePreferenceList() - Device name is modified"
                                        + " already to: "
                                            + deviceName);
                            wifiApCustomPreference.setTitle(deviceName);
                        } else {
                            Log.i(
                                    "WifiApAllowedDeviceList",
                                    "updateAllowedDevicePreferenceList() - Device name is not"
                                        + " modified from add allowed list section");
                        }
                        this.mAllowedDeviceListPreferenceCategory.addPreference(
                                wifiApCustomPreference);
                        wifiApCustomPreference.setOrder(i);
                        i++;
                    }
                }
            } finally {
            }
        }
    }
}
