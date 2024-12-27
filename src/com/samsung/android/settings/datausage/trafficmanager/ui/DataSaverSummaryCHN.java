package com.samsung.android.settings.datausage.trafficmanager.ui;

import android.content.Context;
import android.content.res.Resources;
import android.provider.SearchIndexableData;
import android.widget.TextView;

import androidx.preference.SecSwitchPreference;

import com.android.settings.R;
import com.android.settings.datausage.DataSaverSummary;
import com.android.settings.datausage.DataUsageUtils;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.settings.Rune;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class DataSaverSummaryCHN extends DataSaverSummary {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.sec_data_saver_chn);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.datausage.trafficmanager.ui.DataSaverSummaryCHN$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            Resources resources = context.getResources();
            new SearchIndexableRaw(context);
            if (Rune.SUPPORT_SMARTMANAGER_CN) {
                SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
                ((SearchIndexableData) searchIndexableRaw).key = "data_saver_chn_title";
                searchIndexableRaw.title = resources.getString(R.string.data_saver_title_chn);
                searchIndexableRaw.screenTitle =
                        resources.getString(R.string.data_usage_summary_title);
                ((SearchIndexableData) searchIndexableRaw).intentTargetPackage =
                        context.getPackageName();
                ((SearchIndexableData) searchIndexableRaw).intentTargetClass =
                        DataSaverSummaryCHN.class.getName();
                arrayList.add(searchIndexableRaw);
            }
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return DataUsageUtils.hasMobileData(context)
                    && DataUsageUtils.getDefaultSubscriptionId(context) != -1
                    && Rune.SUPPORT_SMARTMANAGER_CN;
        }
    }

    @Override // com.android.settings.datausage.DataSaverSummary
    public final void initUI$6() {
        addPreferencesFromResource(R.xml.sec_data_saver_chn);
        this.switchBar = (SecSwitchPreference) findPreference("data_saver_switch_chn");
        LayoutPreference layoutPreference =
                (LayoutPreference) findPreference("data_saver_desc_chn");
        if (layoutPreference != null) {
            ((TextView) layoutPreference.mRootView.findViewById(R.id.data_saver_description_title))
                    .setText(R.string.sec_data_saver_description_chn);
        }
    }
}
