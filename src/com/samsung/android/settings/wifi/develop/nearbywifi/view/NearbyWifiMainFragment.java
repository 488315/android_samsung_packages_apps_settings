package com.samsung.android.settings.wifi.develop.nearbywifi.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiScanner;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceManager;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import com.samsung.android.settings.wifi.develop.OnTabSelectedListener;
import com.samsung.android.settings.wifi.develop.nearbywifi.adapter.BssidListAdapter;
import com.samsung.android.settings.wifi.develop.nearbywifi.model.Repository;
import com.samsung.android.settings.wifi.develop.nearbywifi.model.SsidInfo;
import com.samsung.android.util.SemLog;
import com.sec.ims.settings.ImsProfile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class NearbyWifiMainFragment extends SettingsPreferenceFragment {
    public final ChannelUtilController mChannelUtilController;
    public NearbyWifiPreference mNearbyWifiPreference;
    public MenuItem mProgressMenu;
    public MenuItem mRefreshMenu;
    public final AnonymousClass1 mScanReceiver;
    public int mSelectedTabIndex;
    public boolean mWaitingForScanResult;
    public WifiManager mWifiManager;

    /* JADX WARN: Type inference failed for: r0v2, types: [com.samsung.android.settings.wifi.develop.nearbywifi.view.NearbyWifiMainFragment$1] */
    public NearbyWifiMainFragment() {
        ChannelUtilController channelUtilController = new ChannelUtilController();
        channelUtilController.mCategory = new PreferenceCategory[3];
        this.mChannelUtilController = channelUtilController;
        this.mWaitingForScanResult = false;
        this.mScanReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.samsung.android.settings.wifi.develop.nearbywifi.view.NearbyWifiMainFragment.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        NearbyWifiMainFragment nearbyWifiMainFragment;
                        WifiManager wifiManager;
                        if (intent.getBooleanExtra("resultsUpdated", true)
                                && (wifiManager =
                                                (nearbyWifiMainFragment =
                                                                NearbyWifiMainFragment.this)
                                                        .mWifiManager)
                                        != null) {
                            nearbyWifiMainFragment.scanResultUpdated$1(
                                    wifiManager.getScanResults());
                        }
                        NearbyWifiMainFragment nearbyWifiMainFragment2 =
                                NearbyWifiMainFragment.this;
                        if (nearbyWifiMainFragment2.mWaitingForScanResult) {
                            nearbyWifiMainFragment2.mWaitingForScanResult = false;
                            nearbyWifiMainFragment2.mRefreshMenu.setVisible(true);
                            NearbyWifiMainFragment.this.mProgressMenu.setVisible(false);
                        }
                    }
                };
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_wifi_development_nearbywifi;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        Context context = getContext();
        if (context != null) {
            WifiScanner wifiScanner = (WifiScanner) context.getSystemService("wifiscanner");
            Repository repository = Repository.LazyHolder.INSTANCE;
            List availableChannels = wifiScanner.getAvailableChannels(11);
            repository.supportedFreqs.clear();
            repository.supportedFreqs.addAll(availableChannels);
            WifiManager wifiManager = (WifiManager) context.getSystemService(ImsProfile.PDN_WIFI);
            this.mWifiManager = wifiManager;
            scanResultUpdated$1(wifiManager.getScanResults());
        }
        NearbyWifiTabPreference nearbyWifiTabPreference =
                (NearbyWifiTabPreference) findPreference("nearbywifi_tab_preference");
        if (nearbyWifiTabPreference != null) {
            nearbyWifiTabPreference.mOnTabSelectedListener =
                    new OnTabSelectedListener() { // from class:
                                                  // com.samsung.android.settings.wifi.develop.nearbywifi.view.NearbyWifiMainFragment$$ExternalSyntheticLambda0
                        @Override // com.samsung.android.settings.wifi.develop.OnTabSelectedListener
                        public final void onTabSelected(int i) {
                            NearbyWifiMainFragment nearbyWifiMainFragment =
                                    NearbyWifiMainFragment.this;
                            nearbyWifiMainFragment.getClass();
                            SemLog.d(
                                    "NearbyWifi.MainFragment",
                                    "onTabSelectedListener: selectedTab=" + i);
                            nearbyWifiMainFragment.mSelectedTabIndex = i;
                            nearbyWifiMainFragment.showTab(i);
                        }
                    };
        }
        this.mNearbyWifiPreference = (NearbyWifiPreference) findPreference("nearbywifi_preference");
        ChannelUtilController channelUtilController = this.mChannelUtilController;
        PreferenceManager preferenceManager = getPreferenceManager();
        channelUtilController.getClass();
        Repository repository2 = Repository.LazyHolder.INSTANCE;
        channelUtilController.mRepo = repository2;
        channelUtilController.mCuMap = repository2.getCuInfo();
        channelUtilController.mCuBarChart24G =
                (CuBarChart) preferenceManager.findPreference("cu_bar_chart_24g");
        channelUtilController.mCuBarChart5G =
                (CuBarChart) preferenceManager.findPreference("cu_bar_chart_5g");
        channelUtilController.mCuBarChart6G =
                (CuBarChart) preferenceManager.findPreference("cu_bar_chart_6g");
        channelUtilController.mBestChannel = preferenceManager.findPreference("best_channel");
        PreferenceCategory preferenceCategory =
                (PreferenceCategory) preferenceManager.findPreference("nearbywifi_category_cu_24g");
        PreferenceCategory[] preferenceCategoryArr = channelUtilController.mCategory;
        preferenceCategoryArr[0] = preferenceCategory;
        preferenceCategoryArr[1] =
                (PreferenceCategory) preferenceManager.findPreference("nearbywifi_category_cu_5g");
        preferenceCategoryArr[2] =
                (PreferenceCategory) preferenceManager.findPreference("nearbywifi_category_cu_6g");
        CuBarChart cuBarChart = channelUtilController.mCuBarChart24G;
        if (cuBarChart != null) {
            List list = (List) ((HashMap) channelUtilController.mCuMap).get(1);
            cuBarChart.mBand = 1;
            cuBarChart.mCuInfoList = list;
            cuBarChart.mBarShapeResId = R.drawable.sec_wifi_development_bar_green;
        }
        CuBarChart cuBarChart2 = channelUtilController.mCuBarChart5G;
        if (cuBarChart2 != null) {
            List list2 = (List) ((HashMap) channelUtilController.mCuMap).get(2);
            cuBarChart2.mBand = 2;
            cuBarChart2.mCuInfoList = list2;
            cuBarChart2.mBarShapeResId = R.drawable.sec_wifi_development_bar_blue;
        }
        CuBarChart cuBarChart3 = channelUtilController.mCuBarChart6G;
        if (cuBarChart3 != null) {
            List list3 = (List) ((HashMap) channelUtilController.mCuMap).get(8);
            cuBarChart3.mBand = 8;
            cuBarChart3.mCuInfoList = list3;
            cuBarChart3.mBarShapeResId = R.drawable.sec_wifi_development_bar_purple;
        }
        channelUtilController.setChannelRecommendation();
        SemLog.d(
                "NearbyWifi.MainFragment",
                "onActivityCreated: mSelectedTabIndex=" + this.mSelectedTabIndex);
        showTab(this.mSelectedTabIndex);
        requireActivity()
                .getOnBackPressedDispatcher()
                .addCallback(
                        getViewLifecycleOwner(),
                        new OnBackPressedCallback() { // from class:
                                                      // com.samsung.android.settings.wifi.develop.nearbywifi.view.NearbyWifiMainFragment.2
                            @Override // androidx.activity.OnBackPressedCallback
                            public final void handleOnBackPressed() {
                                NearbyWifiMainFragment nearbyWifiMainFragment =
                                        NearbyWifiMainFragment.this;
                                NearbyWifiPreference nearbyWifiPreference =
                                        nearbyWifiMainFragment.mNearbyWifiPreference;
                                if (nearbyWifiPreference != null
                                        && nearbyWifiPreference.isVisible()
                                        && nearbyWifiMainFragment.mNearbyWifiPreference.mSsidTitle
                                                        .getVisibility()
                                                != 0) {
                                    nearbyWifiMainFragment.mNearbyWifiPreference.changeMode();
                                } else {
                                    setEnabled(false);
                                    nearbyWifiMainFragment
                                            .requireActivity()
                                            .getOnBackPressedDispatcher()
                                            .onBackPressed();
                                }
                            }
                        });
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        FragmentActivity activity = getActivity();
        ActionBar supportActionBar =
                activity instanceof AppCompatActivity
                        ? ((AppCompatActivity) activity).getSupportActionBar()
                        : null;
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setHomeButtonEnabled();
            menuInflater.inflate(R.menu.sec_wifi_development_nearbywifi, menu);
            this.mRefreshMenu = menu.findItem(R.id.refresh);
            this.mProgressMenu = menu.findItem(R.id.progress);
            SemLog.d(
                    "NearbyWifi.MainFragment",
                    "setupActionBarMenus: mWaitingForScanResult=" + this.mWaitingForScanResult);
            if (this.mWaitingForScanResult) {
                this.mRefreshMenu.setVisible(false);
                this.mProgressMenu.setVisible(true);
            }
        }
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        WifiManager wifiManager;
        if (menuItem.getItemId() != R.id.refresh) {
            return super.onOptionsItemSelected(menuItem);
        }
        if (!this.mWaitingForScanResult
                && (wifiManager = this.mWifiManager) != null
                && wifiManager.startScan()) {
            SemLog.d("NearbyWifi.MainFragment", "Scan requested");
            this.mWaitingForScanResult = true;
            this.mRefreshMenu.setVisible(false);
            this.mProgressMenu.setVisible(true);
        }
        return true;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        Context context = getContext();
        if (context != null) {
            context.unregisterReceiver(this.mScanReceiver);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        Context context = getContext();
        if (context != null) {
            context.registerReceiver(
                    this.mScanReceiver, new IntentFilter("android.net.wifi.SCAN_RESULTS"));
        }
        this.mWaitingForScanResult = false;
    }

    public final void scanResultUpdated$1(List list) {
        Repository.LazyHolder.INSTANCE.update(list);
        int i = this.mSelectedTabIndex;
        if (i != 0) {
            if (i != 1) {
                return;
            }
            SemLog.d("NearbyWifi.MainFragment", "Update ChannelUtilPreference");
            ChannelUtilController channelUtilController = this.mChannelUtilController;
            if (channelUtilController.mRepo != null) {
                SemLog.d("NearbyWifi.ChannelUtilController", "Refresh Cu bar charts");
                Map cuInfo = channelUtilController.mRepo.getCuInfo();
                channelUtilController.mCuMap = cuInfo;
                CuBarChart cuBarChart = channelUtilController.mCuBarChart24G;
                if (cuBarChart != null) {
                    cuBarChart.updateChartItem((List) ((HashMap) cuInfo).get(1));
                }
                CuBarChart cuBarChart2 = channelUtilController.mCuBarChart5G;
                if (cuBarChart2 != null) {
                    cuBarChart2.updateChartItem(
                            (List) ((HashMap) channelUtilController.mCuMap).get(2));
                }
                CuBarChart cuBarChart3 = channelUtilController.mCuBarChart6G;
                if (cuBarChart3 != null) {
                    cuBarChart3.updateChartItem(
                            (List) ((HashMap) channelUtilController.mCuMap).get(8));
                }
                channelUtilController.setChannelRecommendation();
                return;
            }
            return;
        }
        SemLog.d("NearbyWifi.MainFragment", "Update NearbyWifiPreference");
        NearbyWifiPreference nearbyWifiPreference = this.mNearbyWifiPreference;
        if (nearbyWifiPreference == null || nearbyWifiPreference.mRepo == null) {
            return;
        }
        if (nearbyWifiPreference.mSsidTitle.getVisibility() == 0) {
            nearbyWifiPreference.mSsidListAdapter.notifyDataSetChanged();
            RadarView radarView = nearbyWifiPreference.mRadarView;
            radarView.mRadarUnits = nearbyWifiPreference.mRepo.createRadarUnitBySsid();
            radarView.invalidate();
            return;
        }
        Repository repository = nearbyWifiPreference.mRepo;
        SsidInfo ssidInfo = (SsidInfo) repository.ssidMap.get(nearbyWifiPreference.mSelectedSsid);
        if (ssidInfo != null) {
            BssidListAdapter bssidListAdapter = nearbyWifiPreference.mBssidListAdapter;
            bssidListAdapter.mBssidList = ssidInfo.bssids;
            bssidListAdapter.notifyDataSetChanged();
            RadarView radarView2 = nearbyWifiPreference.mRadarView;
            Repository repository2 = nearbyWifiPreference.mRepo;
            ArrayList arrayList = ssidInfo.bssids;
            repository2.getClass();
            radarView2.mRadarUnits = Repository.createRadarUnitByBssid(arrayList);
            radarView2.invalidate();
        }
    }

    public final void showTab(int i) {
        if (i == 0) {
            NearbyWifiPreference nearbyWifiPreference = this.mNearbyWifiPreference;
            if (nearbyWifiPreference != null) {
                nearbyWifiPreference.setVisible(true);
                this.mNearbyWifiPreference.showSsidList();
            }
            this.mChannelUtilController.setVisible(false);
            return;
        }
        if (i != 1) {
            return;
        }
        NearbyWifiPreference nearbyWifiPreference2 = this.mNearbyWifiPreference;
        if (nearbyWifiPreference2 != null) {
            nearbyWifiPreference2.setVisible(false);
        }
        this.mChannelUtilController.setVisible(true);
    }
}
