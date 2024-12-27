package com.android.settings.support;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.provider.SearchIndexableData;

import com.android.settings.R;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SupportDashboardActivity extends Activity {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.support.SupportDashboardActivity$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            if (!context.getResources().getBoolean(R.bool.config_support_enabled)) {
                ((ArrayList) nonIndexableKeys).add("support_dashboard_activity");
            }
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            searchIndexableRaw.title = context.getString(R.string.page_tab_title_support);
            searchIndexableRaw.screenTitle = context.getString(R.string.page_tab_title_support);
            searchIndexableRaw.summaryOn = context.getString(R.string.support_summary);
            ((SearchIndexableData) searchIndexableRaw).intentTargetPackage =
                    context.getPackageName();
            ((SearchIndexableData) searchIndexableRaw).intentTargetClass =
                    SupportDashboardActivity.class.getName();
            ((SearchIndexableData) searchIndexableRaw).intentAction =
                    "com.android.settings.action.SUPPORT_SETTINGS";
            ((SearchIndexableData) searchIndexableRaw).key = "support_dashboard_activity";
            arrayList.add(searchIndexableRaw);
            return arrayList;
        }
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (FeatureFactoryImpl._factory == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
    }
}
