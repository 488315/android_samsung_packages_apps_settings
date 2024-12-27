package com.samsung.android.settings.datausage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.SearchIndexableData;
import android.provider.SearchIndexableResource;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.datausage.DataUsageBaseFragment;
import com.android.settings.datausage.DataUsageUtils;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.connection.ConnectionsUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class DataUsageSummaryCHN extends DataUsageBaseFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.datausage.DataUsageSummaryCHN$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getDynamicRawDataToIndex(Context context) {
            if (!Rune.SUPPORT_SMARTMANAGER_CN) {
                return null;
            }
            List dynamicRawDataToIndex = super.getDynamicRawDataToIndex(context);
            if (DataUsageUtils.hasMobileData(context)) {
                int i = ConnectionsUtils.$r8$clinit;
                if (DataUsageUtils.hasActiveSim(context)
                        && ((UserManager) context.getSystemService("user")).isAdminUser()) {
                    SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
                    ((SearchIndexableData) searchIndexableRaw).key =
                            "data_usage_enable" + DataUsageUtils.getDefaultSubscriptionId(context);
                    searchIndexableRaw.title =
                            context.getResources().getString(R.string.data_usage_enable_mobile);
                    searchIndexableRaw.keywords =
                            Utils.getKeywordForSearch(context, R.string.data_usage_enable_mobile);
                    searchIndexableRaw.screenTitle =
                            context.getResources().getString(R.string.data_usage_summary_title);
                    ((ArrayList) dynamicRawDataToIndex).add(searchIndexableRaw);
                }
            }
            return dynamicRawDataToIndex;
        }

        /* JADX WARN: Code restructure failed: missing block: B:11:0x0034, code lost:

           if (com.android.settings.datausage.DataUsageUtils.hasActiveSim(r6) == false) goto L13;
        */
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.util.List getNonIndexableKeys(android.content.Context r6) {
            /*
                r5 = this;
                boolean r0 = com.samsung.android.settings.Rune.SUPPORT_SMARTMANAGER_CN
                if (r0 != 0) goto L6
                r5 = 0
                return r5
            L6:
                java.util.List r5 = super.getNonIndexableKeys(r6)
                r0 = r5
                java.util.ArrayList r0 = (java.util.ArrayList) r0
                java.lang.String r1 = "mobile_category"
                java.lang.String r2 = "wifi_category"
                java.lang.String r3 = "ethernet_category"
                java.lang.String r4 = "data_usage_enable"
                com.android.settings.accessibility.AccessibilitySettings$$ExternalSyntheticOutline0.m(r0, r1, r2, r3, r4)
                boolean r1 = com.android.settings.datausage.DataUsageUtils.hasMobileData(r6)
                java.lang.String r2 = "restrict_app_data"
                if (r1 != 0) goto L25
                r0.add(r2)
            L25:
                boolean r1 = com.android.settings.datausage.DataUsageUtils.hasMobileData(r6)
                java.lang.String r3 = "top_up_data_balance"
                if (r1 == 0) goto L36
                int r1 = com.samsung.android.settings.connection.ConnectionsUtils.$r8$clinit
                boolean r1 = com.android.settings.datausage.DataUsageUtils.hasActiveSim(r6)
                if (r1 != 0) goto L43
            L36:
                java.lang.String r1 = "mobile_data_usage_detail"
                r0.add(r1)
                java.lang.String r1 = "billing_preference_chn"
                r0.add(r1)
                r0.add(r3)
            L43:
                boolean r1 = com.android.settings.datausage.DataUsageUtils.hasWifiRadio(r6)
                if (r1 != 0) goto L4f
                java.lang.String r1 = "wifi_data_usage"
                r0.add(r1)
            L4f:
                java.lang.String r1 = "user"
                java.lang.Object r6 = r6.getSystemService(r1)
                android.os.UserManager r6 = (android.os.UserManager) r6
                boolean r6 = r6.isAdminUser()
                if (r6 != 0) goto L64
                r0.add(r2)
                r0.add(r3)
            L64:
                return r5
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.samsung.android.settings.datausage.DataUsageSummaryCHN.AnonymousClass1.getNonIndexableKeys(android.content.Context):java.util.List");
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            if (!Rune.SUPPORT_SMARTMANAGER_CN) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.xmlResId = R.xml.sec_data_usage_cellular_chn;
            arrayList.add(searchIndexableResource);
            SearchIndexableResource searchIndexableResource2 = new SearchIndexableResource(context);
            searchIndexableResource2.xmlResId = R.xml.data_usage_wifi;
            arrayList.add(searchIndexableResource2);
            return arrayList;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "DataUsageSummaryCHN ";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 37;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_data_usage_chn;
    }

    @Override // com.android.settings.datausage.DataUsageBaseFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (Rune.SUPPORT_SMARTMANAGER_CN
                && !SemPersonaManager.isSecureFolderId(UserHandle.myUserId())) {
            Intent sMDataUsageSummaryIntent = DataUsageUtilsCHN.getSMDataUsageSummaryIntent(false);
            sMDataUsageSummaryIntent.putExtra(
                    ":settings:fragment_args_key",
                    getArguments().getString(":settings:fragment_args_key"));
            startActivity(sMDataUsageSummaryIntent);
        }
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.finish();
        }
    }
}
