package com.samsung.android.settings.wifi.develop.savednetwork;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SimpleClock;
import android.os.SystemClock;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.wifitrackerlib.WifiEntry;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.wifi.advanced.WifiHistoryNoItemsPreference;
import com.samsung.android.settings.wifi.develop.OnTabSelectedListener;
import com.samsung.android.wifitrackerlib.SavedNetworkFilter;
import com.samsung.android.wifitrackerlib.SemSavedNetworkTracker;
import com.samsung.android.wifitrackerlib.labs.SemNetworkPredicate;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiLabsNetworkInformationFragment extends SettingsPreferenceFragment
        implements SemSavedNetworkTracker.SavedNetworkTrackerCallback {
    public Context mContext;
    public int mCurrentTabIndex = 0;
    public WifiLabsPreferenceCategory mList;
    public SemSavedNetworkTracker mSavedNetworkTracker;
    public WifiLabsNetworkInformationTabPreference mTabPreference;
    public HandlerThread mWorkerThread;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.develop.savednetwork.WifiLabsNetworkInformationFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends SimpleClock {
        public final long millis() {
            return SystemClock.elapsedRealtime();
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_wifi_labs_network_information_fragment;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getContext();
        HandlerThread handlerThread =
                new HandlerThread(
                        "WifiLabsNetworkInformationFragment{"
                                + Integer.toHexString(System.identityHashCode(this))
                                + "}",
                        10);
        this.mWorkerThread = handlerThread;
        handlerThread.start();
        SimpleClock anonymousClass1 = new AnonymousClass1(ZoneOffset.UTC);
        Lifecycle settingsLifecycle = getSettingsLifecycle();
        Context context = this.mContext;
        this.mSavedNetworkTracker =
                new SemSavedNetworkTracker(
                        settingsLifecycle,
                        context,
                        (WifiManager) context.getSystemService(WifiManager.class),
                        (ConnectivityManager)
                                this.mContext.getSystemService(ConnectivityManager.class),
                        new Handler(Looper.getMainLooper()),
                        this.mWorkerThread.getThreadHandler(),
                        anonymousClass1,
                        this,
                        SavedNetworkFilter.LABS_COMPARATOR);
        WifiLabsNetworkInformationTabPreference wifiLabsNetworkInformationTabPreference =
                (WifiLabsNetworkInformationTabPreference)
                        findPreference("network_information_tab_preference");
        this.mTabPreference = wifiLabsNetworkInformationTabPreference;
        wifiLabsNetworkInformationTabPreference.mOnTabSelectedListener =
                new OnTabSelectedListener() { // from class:
                                              // com.samsung.android.settings.wifi.develop.savednetwork.WifiLabsNetworkInformationFragment$$ExternalSyntheticLambda0
                    @Override // com.samsung.android.settings.wifi.develop.OnTabSelectedListener
                    public final void onTabSelected(int i) {
                        WifiLabsNetworkInformationFragment wifiLabsNetworkInformationFragment =
                                WifiLabsNetworkInformationFragment.this;
                        wifiLabsNetworkInformationFragment.mCurrentTabIndex = i;
                        wifiLabsNetworkInformationFragment.mTabPreference.mPosition = i;
                        wifiLabsNetworkInformationFragment.updateWifiEntries$4();
                    }
                };
        if (getArguments() != null) {
            String string = getArguments().getString("filter", "all");
            int i = string.equals("weak") ? 2 : string.equals("unused") ? 1 : 0;
            this.mCurrentTabIndex = i;
            this.mTabPreference.mPosition = i;
        }
        this.mList = (WifiLabsPreferenceCategory) findPreference("network_information_list");
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        this.mWorkerThread.quit();
        super.onDestroy();
    }

    @Override // com.samsung.android.wifitrackerlib.SemSavedNetworkTracker.SavedNetworkTrackerCallback
    public final void onSavedWifiEntriesChanged() {
        if (isFinishingOrDestroyed()) {
            return;
        }
        updateWifiEntries$4();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        onSavedWifiEntriesChanged();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker.BaseWifiTrackerCallback
    public final void onWifiStateChanged() {
        if (isFinishingOrDestroyed()) {
            return;
        }
        updateWifiEntries$4();
    }

    public final void updateWifiEntries$4() {
        this.mList.removeAll();
        List savedWifiEntries = this.mSavedNetworkTracker.getSavedWifiEntries();
        int i = this.mCurrentTabIndex;
        SavedWifiEntryCategoryOption unusedWifiEntryCategory =
                i == 1
                        ? new UnusedWifiEntryCategory()
                        : i == 2
                                ? new WeakWifiEntryCategory(this.mContext)
                                : new AllWifiEntryCategory();
        ArrayList arrayList = new ArrayList();
        if (i == 1) {
            arrayList.add(SemNetworkPredicate.LONG_UNUSED_NETWORK_PREDICATE);
        } else if (i != 2) {
            arrayList.add(SemNetworkPredicate.ALL_PREDICATE);
        } else {
            arrayList.add(SemNetworkPredicate.OPEN_NETWORK_PREDICATE);
            arrayList.add(SemNetworkPredicate.WEP_NETWORK_PREDICATE);
        }
        ArrayList arrayList2 = new ArrayList();
        int i2 = 0;
        while (true) {
            unusedWifiEntryCategory.getCategoryTitles();
            if (i2 >= ((ArrayList) unusedWifiEntryCategory.getCategoryTitles()).size()) {
                break;
            }
            WifiLabsPreferenceCategory wifiLabsPreferenceCategory =
                    new WifiLabsPreferenceCategory(this.mContext);
            String str = (String) ((ArrayList) unusedWifiEntryCategory.getCategoryTitles()).get(i2);
            wifiLabsPreferenceCategory.setTitle(str);
            wifiLabsPreferenceCategory.mTitle = str;
            arrayList2.add(wifiLabsPreferenceCategory);
            i2++;
        }
        Iterator it = ((ArrayList) savedWifiEntries).iterator();
        int i3 = 0;
        while (it.hasNext()) {
            WifiEntry wifiEntry = (WifiEntry) it.next();
            int i4 = 0;
            while (true) {
                unusedWifiEntryCategory.getCategoryTitles();
                if (i4 < ((ArrayList) unusedWifiEntryCategory.getCategoryTitles()).size()) {
                    WifiLabsPreferenceCategory wifiLabsPreferenceCategory2 =
                            (WifiLabsPreferenceCategory) arrayList2.get(i4);
                    this.mList.addPreference(wifiLabsPreferenceCategory2);
                    if (((SemNetworkPredicate.AllNetworkPredicate) arrayList.get(i4))
                            .matches(this.mContext, wifiEntry.getWifiConfiguration())) {
                        wifiLabsPreferenceCategory2.addPreference(
                                new SavedWifiEntryPreference(this.mContext, wifiEntry));
                        i3++;
                    }
                    i4++;
                }
            }
        }
        for (int i5 = 0; i5 < this.mList.getPreferenceCount(); i5++) {
            if ((this.mList.getPreference(i5) instanceof WifiLabsPreferenceCategory)
                    && ((WifiLabsPreferenceCategory) this.mList.getPreference(i5))
                                    .getPreferenceCount()
                            == 0) {
                this.mList.getPreference(i5).setTitle(ApnSettings.MVNO_NONE);
            }
        }
        if (i3 == 0) {
            WifiLabsPreferenceCategory wifiLabsPreferenceCategory3 = this.mList;
            WifiHistoryNoItemsPreference wifiHistoryNoItemsPreference =
                    new WifiHistoryNoItemsPreference(getContext());
            wifiHistoryNoItemsPreference.setTitle(R.string.sec_wifi_network_information_no_item);
            wifiHistoryNoItemsPreference.seslSetSubheaderRoundedBackground(0);
            wifiHistoryNoItemsPreference.setSelectable(false);
            wifiLabsPreferenceCategory3.addPreference(wifiHistoryNoItemsPreference);
        }
    }
}
