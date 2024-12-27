package com.samsung.android.settings.wifi.mobileap.clients;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.UserHandle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamMediaService$4$$ExternalSyntheticOutline0;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.settings.WifiApUtils;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.mobileap.clients.report.WifiApHotspotUsageReport;
import com.samsung.android.settings.wifi.mobileap.datamodels.WifiApClientConfig;
import com.samsung.android.settings.wifi.mobileap.datamodels.WifiApDataUsageConfig;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApConnectedDeviceUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFeatureUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;
import com.samsung.android.settings.wifi.mobileap.views.WifiApPreference;
import com.samsung.android.wifi.SemWifiApClientDetails;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApClientsManageMobileHotspot extends DashboardFragment {
    public static final IntentFilter INTENT_FILTER;
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER;
    public FragmentActivity mActivity;
    public PreferenceCategory mConnectedClientsPreferenceCategory;
    public Context mContext;
    public WifiApPreference mNoDevicePreference;
    public WifiApPreference mNoPreviouslyConnectedDevicePreference;
    public LinkedHashMap mOldHotspotClientMacHashMap;
    public PreferenceScreen mPreferenceScreen;
    public PreferenceCategory mPreviouslyConnectedClientsPreferenceCategory;
    public SemWifiManager mSemWifiManager;
    public MenuItem mThisMenuItem;
    public WifiApMobileDataSharedTodayPreferenceController
            mWifiApMobileDataSharedTodayPreferenceController;
    public boolean isScreenRefreshing = false;
    public final AnonymousClass1 mSemWifiApDataUsageUpdateCallback =
            new SemWifiManager
                    .SemWifiApClientListUpdateCallback() { // from class:
                                                           // com.samsung.android.settings.wifi.mobileap.clients.WifiApClientsManageMobileHotspot.1
                public final void onClientListUpdated(List list, long j) {
                    WifiApClientConfig wifiApClientConfig;
                    WifiApClientConfig wifiApClientConfig2;
                    IntentFilter intentFilter = WifiApClientsManageMobileHotspot.INTENT_FILTER;
                    Log.i(
                            "WifiApClientsManageMobileHotspot",
                            "onClientListUpdated() : "
                                    + list.size()
                                    + "totalDataUsageInBytes : "
                                    + j);
                    WifiApDataUsageConfig wifiApDataUsageConfig =
                            new WifiApDataUsageConfig(
                                    System.currentTimeMillis(), System.currentTimeMillis(), j);
                    List<WifiApClientConfig> topHotspotClientConfigListToday =
                            WifiApConnectedDeviceUtils.getTopHotspotClientConfigListToday(
                                    WifiApClientsManageMobileHotspot.this.mContext, list);
                    WifiApClientsManageMobileHotspot wifiApClientsManageMobileHotspot =
                            WifiApClientsManageMobileHotspot.this;
                    if (wifiApClientsManageMobileHotspot.isScreenRefreshing) {
                        Log.d(
                                "WifiApClientsManageMobileHotspot",
                                "refreshConnectedPreferenceCategory : isScreenRefreshing is true");
                        return;
                    }
                    wifiApClientsManageMobileHotspot.isScreenRefreshing = true;
                    wifiApClientsManageMobileHotspot
                            .mWifiApMobileDataSharedTodayPreferenceController.updateState(
                            topHotspotClientConfigListToday, wifiApDataUsageConfig);
                    LinkedHashMap linkedHashMap = new LinkedHashMap();
                    ArrayList arrayList = (ArrayList) topHotspotClientConfigListToday;
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        WifiApClientConfig wifiApClientConfig3 = (WifiApClientConfig) it.next();
                        linkedHashMap.put(
                                wifiApClientConfig3.mMac,
                                Boolean.valueOf(wifiApClientConfig3.isClientConnected()));
                    }
                    Log.d(
                            "WifiApClientsManageMobileHotspot",
                            "Hotspot List : " + topHotspotClientConfigListToday.toString());
                    int size = arrayList.size();
                    MainClearConfirm$$ExternalSyntheticOutline0.m(
                            size,
                            "topHotspotWifiApClientConfigList.size(): ",
                            "WifiApClientsManageMobileHotspot");
                    if (size == 0) {
                        if (wifiApClientsManageMobileHotspot.mOldHotspotClientMacHashMap.size()
                                > 0) {
                            for (Map.Entry entry :
                                    wifiApClientsManageMobileHotspot.mOldHotspotClientMacHashMap
                                            .entrySet()) {
                                wifiApClientsManageMobileHotspot.mConnectedClientsPreferenceCategory
                                        .removePreferenceRecursively((CharSequence) entry.getKey());
                                wifiApClientsManageMobileHotspot
                                        .mPreviouslyConnectedClientsPreferenceCategory
                                        .removePreferenceRecursively((CharSequence) entry.getKey());
                            }
                            wifiApClientsManageMobileHotspot.mOldHotspotClientMacHashMap.clear();
                        }
                        if (wifiApClientsManageMobileHotspot.mConnectedClientsPreferenceCategory
                                        .findPreference("no_device_error")
                                == null) {
                            wifiApClientsManageMobileHotspot.mConnectedClientsPreferenceCategory
                                    .addPreference(
                                            wifiApClientsManageMobileHotspot.mNoDevicePreference);
                        }
                        if (wifiApClientsManageMobileHotspot
                                        .mPreviouslyConnectedClientsPreferenceCategory
                                        .findPreference("no_device_error")
                                == null) {
                            wifiApClientsManageMobileHotspot
                                    .mPreviouslyConnectedClientsPreferenceCategory.addPreference(
                                    wifiApClientsManageMobileHotspot
                                            .mNoPreviouslyConnectedDevicePreference);
                        }
                    } else {
                        LinkedHashMap linkedHashMap2 =
                                wifiApClientsManageMobileHotspot.mOldHotspotClientMacHashMap;
                        boolean z = WifiApSettingsUtils.DBG;
                        if (new ArrayList(linkedHashMap2.entrySet())
                                .equals(new ArrayList(linkedHashMap.entrySet()))) {
                            for (int i = 0;
                                    i
                                            < wifiApClientsManageMobileHotspot
                                                    .mConnectedClientsPreferenceCategory
                                                    .getPreferenceCount();
                                    i++) {
                                if (wifiApClientsManageMobileHotspot
                                                .mConnectedClientsPreferenceCategory.getPreference(
                                                i)
                                        instanceof WifiApClientPreference) {
                                    WifiApClientPreference wifiApClientPreference =
                                            (WifiApClientPreference)
                                                    wifiApClientsManageMobileHotspot
                                                            .mConnectedClientsPreferenceCategory
                                                            .getPreference(i);
                                    String key = wifiApClientPreference.getKey();
                                    Parcelable.Creator<WifiApClientConfig> creator =
                                            WifiApClientConfig.CREATOR;
                                    Iterator it2 = arrayList.iterator();
                                    while (true) {
                                        if (it2.hasNext()) {
                                            wifiApClientConfig2 = (WifiApClientConfig) it2.next();
                                            if (wifiApClientConfig2.mMac.equals(key)) {
                                                break;
                                            }
                                        } else {
                                            wifiApClientConfig2 = null;
                                            break;
                                        }
                                    }
                                    SemWifiApClientDetails semWifiApClientDetails =
                                            wifiApClientConfig2.mSemWifiApClientDetails;
                                    if (semWifiApClientDetails != null) {
                                        wifiApClientPreference.updatePreference(
                                                semWifiApClientDetails);
                                    }
                                }
                            }
                            for (int i2 = 0;
                                    i2
                                            < wifiApClientsManageMobileHotspot
                                                    .mPreviouslyConnectedClientsPreferenceCategory
                                                    .getPreferenceCount();
                                    i2++) {
                                if (wifiApClientsManageMobileHotspot
                                                .mPreviouslyConnectedClientsPreferenceCategory
                                                .getPreference(i2)
                                        instanceof WifiApClientPreference) {
                                    WifiApClientPreference wifiApClientPreference2 =
                                            (WifiApClientPreference)
                                                    wifiApClientsManageMobileHotspot
                                                            .mPreviouslyConnectedClientsPreferenceCategory
                                                            .getPreference(i2);
                                    String key2 = wifiApClientPreference2.getKey();
                                    Parcelable.Creator<WifiApClientConfig> creator2 =
                                            WifiApClientConfig.CREATOR;
                                    Iterator it3 = arrayList.iterator();
                                    while (true) {
                                        if (it3.hasNext()) {
                                            wifiApClientConfig = (WifiApClientConfig) it3.next();
                                            if (wifiApClientConfig.mMac.equals(key2)) {
                                                break;
                                            }
                                        } else {
                                            wifiApClientConfig = null;
                                            break;
                                        }
                                    }
                                    SemWifiApClientDetails semWifiApClientDetails2 =
                                            wifiApClientConfig.mSemWifiApClientDetails;
                                    if (semWifiApClientDetails2 != null) {
                                        wifiApClientPreference2.updatePreference(
                                                semWifiApClientDetails2);
                                    }
                                }
                            }
                        } else {
                            wifiApClientsManageMobileHotspot.mConnectedClientsPreferenceCategory
                                    .removeAll();
                            wifiApClientsManageMobileHotspot
                                    .mPreviouslyConnectedClientsPreferenceCategory.removeAll();
                            for (int i3 = 0; i3 < size; i3++) {
                                final WifiApClientConfig wifiApClientConfig4 =
                                        (WifiApClientConfig) arrayList.get(i3);
                                Context context = wifiApClientsManageMobileHotspot.mContext;
                                SemWifiApClientDetails semWifiApClientDetails3 =
                                        wifiApClientConfig4.mSemWifiApClientDetails;
                                int i4 = wifiApClientConfig4.mProgressbarColorId;
                                final WifiApClientPreference wifiApClientPreference3 =
                                        new WifiApClientPreference(context);
                                wifiApClientPreference3.mContext = context;
                                wifiApClientPreference3.mWarningColor =
                                        context.getColor(R.color.wifi_ap_warning_color);
                                wifiApClientPreference3.mSummaryColor =
                                        context.getColor(R.color.wifi_ap_secondary_text_color);
                                wifiApClientPreference3.mDeviceName =
                                        semWifiApClientDetails3.getClientDeviceName();
                                wifiApClientPreference3.mMac =
                                        semWifiApClientDetails3.getClientMacAddress();
                                wifiApClientPreference3.mIsDeviceCurrentlyConnected =
                                        semWifiApClientDetails3.isClientConnected();
                                if (WifiApFeatureUtils.isWifiApClientDeviceTypeSupported()) {
                                    wifiApClientPreference3.setIcon((Drawable) null);
                                    wifiApClientPreference3.setSecondaryIcon(
                                            context.getDrawable(
                                                    R.drawable.sec_wifi_ap_progress_index_dot));
                                    wifiApClientPreference3.mSecondaryIconWidthInPx =
                                            WifiApSettingsUtils.convertDpToPixel(
                                                    ((WifiApPreference) wifiApClientPreference3)
                                                            .mContext,
                                                    -2);
                                    wifiApClientPreference3.mSecondaryIconHeightInPx =
                                            WifiApSettingsUtils.convertDpToPixel(
                                                    ((WifiApPreference) wifiApClientPreference3)
                                                            .mContext,
                                                    40);
                                    wifiApClientPreference3.notifyChanged();
                                    ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER;
                                    scaleType.getClass();
                                    if (wifiApClientPreference3.mSecondaryIconScaleType
                                            != scaleType) {
                                        wifiApClientPreference3.mSecondaryIconScaleType = scaleType;
                                        wifiApClientPreference3.notifyChanged();
                                    }
                                    wifiApClientPreference3.mSecondaryIconColor = i4;
                                    wifiApClientPreference3.notifyChanged();
                                    if (wifiApClientPreference3.mIsDeviceCurrentlyConnected) {
                                        wifiApClientPreference3.setOnPreferenceClickListener(
                                                new Preference
                                                        .OnPreferenceClickListener() { // from
                                                                                       // class:
                                                                                       // com.samsung.android.settings.wifi.mobileap.clients.WifiApClientPreference.1
                                                    public AnonymousClass1() {}

                                                    @Override // androidx.preference.Preference.OnPreferenceClickListener
                                                    public final boolean onPreferenceClick(
                                                            Preference preference) {
                                                        WifiApClientPreference
                                                                .m1375$$Nest$mopenClientSettingActivity(
                                                                        WifiApClientPreference
                                                                                .this);
                                                        return true;
                                                    }
                                                });
                                    } else {
                                        wifiApClientPreference3.setOnPreferenceClickListener(null);
                                    }
                                } else {
                                    wifiApClientPreference3.setIcon(
                                            context.getDrawable(
                                                    R.drawable.sec_wifi_ap_progress_index_dot));
                                    wifiApClientPreference3.mIconWidthInPx =
                                            WifiApSettingsUtils.convertDpToPixel(
                                                    ((WifiApPreference) wifiApClientPreference3)
                                                            .mContext,
                                                    -2);
                                    wifiApClientPreference3.mIconHeightInPx =
                                            WifiApSettingsUtils.convertDpToPixel(
                                                    ((WifiApPreference) wifiApClientPreference3)
                                                            .mContext,
                                                    40);
                                    wifiApClientPreference3.notifyChanged();
                                    ImageView.ScaleType scaleType2 = ImageView.ScaleType.CENTER;
                                    scaleType2.getClass();
                                    if (wifiApClientPreference3.mIconScaleType != scaleType2) {
                                        wifiApClientPreference3.mIconScaleType = scaleType2;
                                        wifiApClientPreference3.notifyChanged();
                                    }
                                    wifiApClientPreference3.mIconColor = i4;
                                    wifiApClientPreference3.setOnPreferenceClickListener(null);
                                    wifiApClientPreference3.mSecondaryIconClickListener =
                                            new WifiApClientPreference.AnonymousClass2(
                                                    wifiApClientPreference3, 0);
                                    wifiApClientPreference3.notifyChanged();
                                }
                                wifiApClientPreference3.updatePreference(semWifiApClientDetails3);
                                if (wifiApClientPreference3.mIsDeviceCurrentlyConnected) {
                                    wifiApClientsManageMobileHotspot
                                            .mConnectedClientsPreferenceCategory.addPreference(
                                            wifiApClientPreference3);
                                } else {
                                    wifiApClientsManageMobileHotspot
                                            .mPreviouslyConnectedClientsPreferenceCategory
                                            .addPreference(wifiApClientPreference3);
                                }
                                new Handler()
                                        .postDelayed(
                                                new Runnable() { // from class:
                                                                 // com.samsung.android.settings.wifi.mobileap.clients.WifiApClientsManageMobileHotspot.3
                                                    @Override // java.lang.Runnable
                                                    public final void run() {
                                                        WifiApClientPreference
                                                                wifiApClientPreference4 =
                                                                        WifiApClientPreference.this;
                                                        wifiApClientPreference4.mProgressColor =
                                                                wifiApClientConfig4
                                                                        .mProgressbarColorId;
                                                        wifiApClientPreference4.notifyChanged();
                                                    }
                                                },
                                                1L);
                            }
                        }
                        if (wifiApClientsManageMobileHotspot.mConnectedClientsPreferenceCategory
                                        .findPreference("no_device_error")
                                == null) {
                            if (wifiApClientsManageMobileHotspot.mConnectedClientsPreferenceCategory
                                            .getPreferenceCount()
                                    == 0) {
                                wifiApClientsManageMobileHotspot.mConnectedClientsPreferenceCategory
                                        .addPreference(
                                                wifiApClientsManageMobileHotspot
                                                        .mNoDevicePreference);
                            }
                        } else if (wifiApClientsManageMobileHotspot
                                        .mConnectedClientsPreferenceCategory.getPreferenceCount()
                                > 1) {
                            wifiApClientsManageMobileHotspot.mConnectedClientsPreferenceCategory
                                    .removePreferenceRecursively(
                                            wifiApClientsManageMobileHotspot.mNoDevicePreference
                                                    .getKey());
                        }
                        if (wifiApClientsManageMobileHotspot
                                        .mPreviouslyConnectedClientsPreferenceCategory
                                        .findPreference("no_device_error")
                                == null) {
                            if (wifiApClientsManageMobileHotspot
                                            .mPreviouslyConnectedClientsPreferenceCategory
                                            .getPreferenceCount()
                                    == 0) {
                                wifiApClientsManageMobileHotspot
                                        .mPreviouslyConnectedClientsPreferenceCategory
                                        .addPreference(
                                                wifiApClientsManageMobileHotspot
                                                        .mNoPreviouslyConnectedDevicePreference);
                            }
                        } else if (wifiApClientsManageMobileHotspot
                                        .mPreviouslyConnectedClientsPreferenceCategory
                                        .getPreferenceCount()
                                > 1) {
                            wifiApClientsManageMobileHotspot
                                    .mPreviouslyConnectedClientsPreferenceCategory
                                    .removePreferenceRecursively(
                                            wifiApClientsManageMobileHotspot
                                                    .mNoPreviouslyConnectedDevicePreference
                                                    .getKey());
                        }
                    }
                    wifiApClientsManageMobileHotspot.mOldHotspotClientMacHashMap = linkedHashMap;
                    wifiApClientsManageMobileHotspot.isScreenRefreshing = false;
                }

                public final void onOverallDataLimitChanged(long j) {
                    IntentFilter intentFilter = WifiApClientsManageMobileHotspot.INTENT_FILTER;
                    Log.i("WifiApClientsManageMobileHotspot", "onOverallDataLimitChanged() : " + j);
                }
            };
    public final AnonymousClass2 mBroadcastReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.settings.wifi.mobileap.clients.WifiApClientsManageMobileHotspot.2
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    IntentFilter intentFilter = WifiApClientsManageMobileHotspot.INTENT_FILTER;
                    if (AudioStreamMediaService$4$$ExternalSyntheticOutline0.m(
                            "Broadcast Received: ",
                            action,
                            "WifiApClientsManageMobileHotspot",
                            "com.samsung.android.net.wifi.WIFI_AP_STA_STATE_CHANGED")) {
                        WifiApClientsManageMobileHotspot.this.mOldHotspotClientMacHashMap.clear();
                    } else if (action.equals(
                            WifiApMobileDataSharedTodayPreferenceController
                                    .ACTION_WIFI_AP_STATE_CHANGED)) {
                        WifiApClientsManageMobileHotspot.this.mOldHotspotClientMacHashMap.clear();
                    }
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.clients.WifiApClientsManageMobileHotspot$4, reason: invalid class name */
    public final class AnonymousClass4 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            Resources resources = context.getResources();
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            if (UserHandle.myUserId() == 0
                    && WifiApFeatureUtils.isMobileDataUsageSupported(context)) {
                searchIndexableRaw.title =
                        String.valueOf(
                                WifiApUtils.getStringID(R.string.wifi_ap_manage_mobile_hotspot));
                searchIndexableRaw.screenTitle =
                        resources.getString(
                                WifiApUtils.getStringID(R.string.wifi_ap_manage_mobile_hotspot));
                arrayList.add(searchIndexableRaw);
            }
            return arrayList;
        }
    }

    static {
        IntentFilter intentFilter =
                new IntentFilter("com.samsung.android.net.wifi.WIFI_AP_STA_STATE_CHANGED");
        INTENT_FILTER = intentFilter;
        intentFilter.addAction(
                WifiApMobileDataSharedTodayPreferenceController.ACTION_WIFI_AP_STATE_CHANGED);
        SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass4();
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "WifiApClientsManageMobileHotspot";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_wifi_ap_connected_devices_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        ((WifiApMobileDataSharedTodayPreferenceController)
                        use(WifiApMobileDataSharedTodayPreferenceController.class))
                .setHost(this);
        this.mWifiApMobileDataSharedTodayPreferenceController =
                (WifiApMobileDataSharedTodayPreferenceController)
                        use(WifiApMobileDataSharedTodayPreferenceController.class);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.i("WifiApClientsManageMobileHotspot", "onCreate: ");
        this.mContext = getContext();
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.setTitle(WifiApUtils.getStringID(R.string.wifi_ap_manage_mobile_hotspot));
        }
        this.mPreferenceScreen = getPreferenceScreen();
        this.mSemWifiManager =
                (SemWifiManager) this.mContext.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        this.mConnectedClientsPreferenceCategory =
                (PreferenceCategory)
                        this.mPreferenceScreen.findPreference(
                                "connected_devices_preference_category");
        this.mPreviouslyConnectedClientsPreferenceCategory =
                (PreferenceCategory)
                        this.mPreferenceScreen.findPreference(
                                "Previously_connected_devices_preference_category");
        WifiApPreference wifiApPreference = new WifiApPreference(this.mContext);
        this.mNoDevicePreference = wifiApPreference;
        wifiApPreference.setSelectable(false);
        this.mNoDevicePreference.setKey("no_device_error");
        this.mNoDevicePreference.setTitle(R.string.wifi_ap_no_devices);
        WifiApPreference wifiApPreference2 = this.mNoDevicePreference;
        wifiApPreference2.mTextGravity = 17;
        wifiApPreference2.notifyChanged();
        this.mNoDevicePreference.setTitleTextColor(
                this.mContext.getColor(R.color.wifi_ap_no_devices_text_color));
        WifiApPreference wifiApPreference3 = new WifiApPreference(this.mContext);
        this.mNoPreviouslyConnectedDevicePreference = wifiApPreference3;
        wifiApPreference3.setSelectable(false);
        this.mNoPreviouslyConnectedDevicePreference.setKey("no_device_error");
        this.mNoPreviouslyConnectedDevicePreference.setTitle(R.string.wifi_ap_no_devices);
        WifiApPreference wifiApPreference4 = this.mNoPreviouslyConnectedDevicePreference;
        wifiApPreference4.mTextGravity = 17;
        wifiApPreference4.notifyChanged();
        this.mNoPreviouslyConnectedDevicePreference.setTitleTextColor(
                this.mContext.getColor(R.color.wifi_ap_no_devices_text_color));
        this.mOldHotspotClientMacHashMap = new LinkedHashMap();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        FragmentActivity activity = getActivity();
        if ((activity instanceof AppCompatActivity
                        ? ((AppCompatActivity) activity).getSupportActionBar()
                        : null)
                != null) {
            MenuItem add =
                    menu.add(
                            0,
                            1,
                            0,
                            WifiApUtils.getStringID(R.string.wifi_ap_hotspot_usage_report));
            this.mThisMenuItem = add;
            add.setShowAsAction(2);
            this.mThisMenuItem.setIcon(R.drawable.sec_ic_wifi_ap_report);
            this.mThisMenuItem.setTooltipText(
                    this.mContext.getString(
                            WifiApUtils.getStringID(R.string.wifi_ap_hotspot_usage_report)));
        }
        this.mThisMenuItem.setEnabled(true);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId != 1) {
            if (itemId == 16908332) {
                Log.i("WifiApClientsManageMobileHotspot", "Navigate Up clicked");
                SALogging.insertSALog("TETH_013", "8101");
            }
            return super.onOptionsItemSelected(menuItem);
        }
        Log.i("WifiApClientsManageMobileHotspot", "Hotspot usage report Menu Button clicked");
        SALogging.insertSALog("TETH_013", "8073");
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mSourceMetricsCategory = 3400;
        launchRequest.mDestinationName = WifiApHotspotUsageReport.class.getCanonicalName();
        subSettingLauncher.launch();
        return true;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        WifiApFrameworkUtils.setIsHotspotScreenOpened(this.mContext, false);
        this.mSemWifiManager.unregisterClientListDataUsageCallback(
                this.mSemWifiApDataUsageUpdateCallback);
        super.onPause();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.isScreenRefreshing = false;
        WifiApFrameworkUtils.setIsHotspotScreenOpened(this.mContext, true);
        this.mSemWifiManager.registerClientListDataUsageCallback(
                this.mSemWifiApDataUsageUpdateCallback, this.mContext.getMainExecutor(), 10, 20);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        this.mContext.registerReceiver(this.mBroadcastReceiver, INTENT_FILTER, 2);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        this.mContext.unregisterReceiver(this.mBroadcastReceiver);
        super.onStop();
    }
}
