package com.samsung.android.settings.privacy;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.accessibility.AccessibilitySettings$$ExternalSyntheticLambda3;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.dashboard.DashboardFeatureProviderImpl;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.drawer.Tile;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.account.AccountUtils;
import com.samsung.android.settings.dynamicmenu.SecDynamicFragment;
import com.samsung.android.settings.lockscreen.controller.ScreenLockTypePreferenceController;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.security.SecurityUtils;
import com.samsung.android.settings.widget.SecurityDashboardStatusPreference;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecurityAndPrivacySettings extends SecDynamicFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.sec_security_and_privacy_settings);
    public DashboardFeatureProviderImpl mDashboardFeatureProvider;
    public SecurityDashboardScanAnimator mScanAnimator;
    public SecurityDashboardStatusPreference mStatusPreference;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.privacy.SecurityAndPrivacySettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            ((ArrayList) nonIndexableKeys).add("privacy_dashboard_page");
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            final ArrayList arrayList = new ArrayList();
            getPreferenceControllers(context).stream()
                    .filter(new SecurityAndPrivacySettings$1$$ExternalSyntheticLambda0())
                    .map(new SecurityAndPrivacySettings$1$$ExternalSyntheticLambda1())
                    .forEach(
                            new Consumer() { // from class:
                                             // com.samsung.android.settings.privacy.SecurityAndPrivacySettings$1$$ExternalSyntheticLambda2
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    ((BasePreferenceController) obj)
                                            .updateRawDataToIndex(arrayList);
                                }
                            });
            return arrayList;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final boolean displayTile(Tile tile) {
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        String dashboardKeyForTile =
                featureFactoryImpl.getDashboardFeatureProvider().getDashboardKeyForTile(tile);
        if (dashboardKeyForTile == null
                || "key_find_my_mobile".equals(dashboardKeyForTile)
                || "security_status_security_patch_level".equals(dashboardKeyForTile)
                || "security_status_package_verifier".equals(dashboardKeyForTile)
                || "security_status_partial_system_updates".equals(dashboardKeyForTile)) {
            return false;
        }
        if ("security_status_find_device".equals(dashboardKeyForTile)) {
            return !SecurityUtils.isSupportFindMyMobileFeature(getActivity());
        }
        if ("rampart_main_setting_alias".equals(dashboardKeyForTile)) {
            tile.mOrderOverride = 800;
            tile.mSaLoggingIdOverride = String.valueOf(57013);
        }
        if ("security_status_of_your_devices".equals(dashboardKeyForTile)) {
            if (Rune.isChinaModel()) {
                return false;
            }
            tile.getClass();
            tile.mSaLoggingIdOverride = String.valueOf(58100);
            FragmentActivity activity = getActivity();
            Uri uri = SecurityDashboardUtils.FMM_SUPPORT_URI;
            if (AccountUtils.isSamsungAccountExists(activity)) {
                tile.mSummaryOverride =
                        getActivity()
                                .getResources()
                                .getString(R.string.security_status_sa_logged_in);
            } else {
                tile.mSummaryOverride =
                        getActivity()
                                .getResources()
                                .getString(
                                        R.string
                                                .security_dashboard_samsung_account_signin_description);
            }
        }
        return super.displayTile(tile);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "SecurityAndPrivacySettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 9032;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_security_and_privacy_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getPrefContext();
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getDashboardFeatureProvider();
        this.mStatusPreference =
                (SecurityDashboardStatusPreference) findPreference("security_dashboard_status");
        Collection<List<AbstractPreferenceController>> preferenceControllers =
                getPreferenceControllers();
        SecurityDashboardStatusPreference securityDashboardStatusPreference =
                this.mStatusPreference;
        SecurityDashboardScanAnimator securityDashboardScanAnimator =
                new SecurityDashboardScanAnimator();
        securityDashboardScanAnimator.mPerformScanVi = true;
        securityDashboardScanAnimator.mStatusPreference = securityDashboardStatusPreference;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        securityDashboardScanAnimator.mScanViMenuControllers = linkedHashMap;
        securityDashboardScanAnimator.mHandler = new Handler(Looper.getMainLooper());
        ArrayList arrayList = new ArrayList();
        preferenceControllers.forEach(
                new AccessibilitySettings$$ExternalSyntheticLambda3(0, arrayList));
        SecurityDashboardPreferenceController securityDashboardPreferenceController =
                (SecurityDashboardPreferenceController)
                        SecurityDashboardScanAnimator.findController("key_screen_lock", arrayList);
        SecurityDashboardPreferenceController securityDashboardPreferenceController2 =
                (SecurityDashboardPreferenceController)
                        SecurityDashboardScanAnimator.findController(
                                "key_accounts_security", arrayList);
        SecurityDashboardPreferenceController securityDashboardPreferenceController3 =
                (SecurityDashboardPreferenceController)
                        SecurityDashboardScanAnimator.findController(
                                "key_device_finders", arrayList);
        SecurityDashboardPreferenceController securityDashboardPreferenceController4 =
                (SecurityDashboardPreferenceController)
                        SecurityDashboardScanAnimator.findController(
                                "key_device_finders_new", arrayList);
        SecurityDashboardPreferenceController securityDashboardPreferenceController5 =
                (SecurityDashboardPreferenceController)
                        SecurityDashboardScanAnimator.findController("key_app_security", arrayList);
        SecurityDashboardPreferenceController securityDashboardPreferenceController6 =
                (SecurityDashboardPreferenceController)
                        SecurityDashboardScanAnimator.findController("key_updates", arrayList);
        if (securityDashboardPreferenceController.getAvailabilityStatus() == 0) {
            linkedHashMap.put("key_screen_lock", securityDashboardPreferenceController);
        }
        if (securityDashboardPreferenceController2.getAvailabilityStatus() == 0) {
            linkedHashMap.put("key_accounts_security", securityDashboardPreferenceController2);
        }
        if (securityDashboardPreferenceController3.getAvailabilityStatus() == 0) {
            linkedHashMap.put("key_device_finders", securityDashboardPreferenceController3);
        }
        if (securityDashboardPreferenceController4.getAvailabilityStatus() == 0) {
            linkedHashMap.put("key_device_finders_new", securityDashboardPreferenceController4);
        }
        if (securityDashboardPreferenceController5.getAvailabilityStatus() == 0) {
            linkedHashMap.put("key_app_security", securityDashboardPreferenceController5);
        }
        if (securityDashboardPreferenceController6.getAvailabilityStatus() == 0) {
            linkedHashMap.put("key_updates", securityDashboardPreferenceController6);
        }
        this.mScanAnimator = securityDashboardScanAnimator;
        ((ScreenLockTypePreferenceController) use(ScreenLockTypePreferenceController.class))
                .init(this);
    }

    @Override // com.samsung.android.settings.dynamicmenu.SecDynamicFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        List list = getListView().mOnChildAttachStateListeners;
        if (list != null) {
            ((ArrayList) list).clear();
        }
        SecurityDashboardScanAnimator securityDashboardScanAnimator = this.mScanAnimator;
        securityDashboardScanAnimator.mHandler.removeCallbacksAndMessages(null);
        securityDashboardScanAnimator.mStatusPreference.setScanning(false, false);
        Iterator it =
                ((LinkedHashMap) securityDashboardScanAnimator.mScanViMenuControllers)
                        .values()
                        .iterator();
        while (it.hasNext()) {
            ((SecurityDashboardPreferenceController) it.next()).onStopScanVi();
        }
        super.onPause();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        String key = preference.getKey();
        key.getClass();
        if (key.equals("privacy_settings")) {
            LoggingHelper.insertEventLogging(9032, 57020);
        }
        return super.onPreferenceTreeClick(preference);
    }

    @Override // com.samsung.android.settings.dynamicmenu.SecDynamicFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        getListView().setItemViewCacheSize(getPreferenceScreen().getPreferenceCount());
        final SecurityDashboardStatusPreference securityDashboardStatusPreference =
                this.mStatusPreference;
        securityDashboardStatusPreference.setupStatus(false);
        securityDashboardStatusPreference.mHandler.postDelayed(
                new Runnable() { // from class:
                                 // com.samsung.android.settings.widget.SecurityDashboardStatusPreference$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SecurityDashboardStatusPreference securityDashboardStatusPreference2 =
                                SecurityDashboardStatusPreference.this;
                        if (((ConcurrentHashMap)
                                        securityDashboardStatusPreference2.mOverallStatusMap)
                                .equals(
                                        securityDashboardStatusPreference2
                                                .mCurrentSaLogStatusMap)) {
                            return;
                        }
                        int i = securityDashboardStatusPreference2.mCriticalCount;
                        int i2 = securityDashboardStatusPreference2.mWarningCount;
                        Map map = securityDashboardStatusPreference2.mOverallStatusMap;
                        Uri uri = SecurityDashboardUtils.FMM_SUPPORT_URI;
                        final HashMap hashMap = new HashMap();
                        hashMap.put(
                                "DB",
                                i > 0 ? "2" : i2 > 0 ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
                        final String[] strArr = new String[1];
                        ((ConcurrentHashMap) map)
                                .forEach(
                                        new BiConsumer() { // from class:
                                                           // com.samsung.android.settings.privacy.SecurityDashboardUtils$$ExternalSyntheticLambda1
                                            @Override // java.util.function.BiConsumer
                                            public final void accept(Object obj, Object obj2) {
                                                String[] strArr2 = strArr;
                                                HashMap hashMap2 = hashMap;
                                                String str = (String) obj;
                                                SecurityDashboardConstants$Status
                                                        securityDashboardConstants$Status =
                                                                (SecurityDashboardConstants$Status)
                                                                        obj2;
                                                if (securityDashboardConstants$Status
                                                        == SecurityDashboardConstants$Status.OK) {
                                                    strArr2[0] = DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
                                                } else if (securityDashboardConstants$Status
                                                        == SecurityDashboardConstants$Status
                                                                .WARNING) {
                                                    strArr2[0] = "1";
                                                } else if (securityDashboardConstants$Status
                                                        == SecurityDashboardConstants$Status
                                                                .CRITICAL) {
                                                    strArr2[0] = "2";
                                                } else if (securityDashboardConstants$Status
                                                        == SecurityDashboardConstants$Status.NONE) {
                                                    strArr2[0] = DATA.DM_FIELD_INDEX.PUBLIC_USER_ID;
                                                }
                                                hashMap2.put(str, strArr2[0]);
                                            }
                                        });
                        hashMap.putIfAbsent("SL", DATA.DM_FIELD_INDEX.PUBLIC_USER_ID);
                        hashMap.putIfAbsent("SA", DATA.DM_FIELD_INDEX.PUBLIC_USER_ID);
                        hashMap.putIfAbsent("GA", DATA.DM_FIELD_INDEX.PUBLIC_USER_ID);
                        hashMap.putIfAbsent("FMM", DATA.DM_FIELD_INDEX.PUBLIC_USER_ID);
                        hashMap.putIfAbsent("DP", DATA.DM_FIELD_INDEX.PUBLIC_USER_ID);
                        hashMap.putIfAbsent("GPP", DATA.DM_FIELD_INDEX.PUBLIC_USER_ID);
                        hashMap.putIfAbsent("SU", DATA.DM_FIELD_INDEX.PUBLIC_USER_ID);
                        hashMap.putIfAbsent("GPSU", DATA.DM_FIELD_INDEX.PUBLIC_USER_ID);
                        SALogging.insertSALog(
                                String.valueOf(9032), String.valueOf(9033), hashMap, 0);
                        ((ConcurrentHashMap)
                                        securityDashboardStatusPreference2.mCurrentSaLogStatusMap)
                                .clear();
                        ((ConcurrentHashMap)
                                        securityDashboardStatusPreference2.mCurrentSaLogStatusMap)
                                .putAll(securityDashboardStatusPreference2.mOverallStatusMap);
                    }
                },
                1000L);
        final SecurityDashboardScanAnimator securityDashboardScanAnimator = this.mScanAnimator;
        if (securityDashboardScanAnimator.mPerformScanVi) {
            SecurityDashboardStatusPreference securityDashboardStatusPreference2 =
                    securityDashboardScanAnimator.mStatusPreference;
            securityDashboardStatusPreference2.setScanning(true, false);
            for (SecurityDashboardPreferenceController securityDashboardPreferenceController :
                    ((LinkedHashMap) securityDashboardScanAnimator.mScanViMenuControllers)
                            .values()) {
                securityDashboardPreferenceController.setDefaultSummary();
                securityDashboardPreferenceController.setStatus(
                        SecurityDashboardConstants$Status.NONE);
            }
            final ArrayList arrayList = new ArrayList();
            ((LinkedHashMap) securityDashboardScanAnimator.mScanViMenuControllers)
                    .keySet()
                    .iterator()
                    .forEachRemaining(
                            new Consumer() { // from class:
                                             // com.samsung.android.settings.privacy.SecurityDashboardScanAnimator$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    arrayList.add((String) obj);
                                }
                            });
            SecurityDashboardPreferenceController securityDashboardPreferenceController2 =
                    (SecurityDashboardPreferenceController)
                            ((LinkedHashMap) securityDashboardScanAnimator.mScanViMenuControllers)
                                    .get(arrayList.get(0));
            securityDashboardPreferenceController2.setStatus(
                    SecurityDashboardConstants$Status.SCANNING);
            securityDashboardStatusPreference2.updateScanCategoryIcon(
                    securityDashboardPreferenceController2.getScanCategoryIcon());
            securityDashboardScanAnimator.mHandler.postDelayed(
                    new Runnable() { // from class:
                                     // com.samsung.android.settings.privacy.SecurityDashboardScanAnimator$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            SecurityDashboardScanAnimator.this.refreshMenuItemsStatusWithVi(
                                    0, arrayList);
                        }
                    },
                    500L);
            securityDashboardScanAnimator.mPerformScanVi = false;
        }
    }
}
