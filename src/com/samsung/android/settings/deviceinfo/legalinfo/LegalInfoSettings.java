package com.samsung.android.settings.deviceinfo.legalinfo;

import android.content.Context;
import android.content.Intent;
import android.provider.SearchIndexableData;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.settings.deviceinfo.SecDeviceInfoUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class LegalInfoSettings extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.sec_legal_info_settings);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.deviceinfo.legalinfo.LegalInfoSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            String string = context.getResources().getString(R.string.legal_information);
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            String preferenceToSpecificActivityTitleInfo =
                    SecDeviceInfoUtils.getPreferenceToSpecificActivityTitleInfo(
                            context, new Intent("android.settings.LICENSE"));
            ((SearchIndexableData) searchIndexableRaw).key = "license";
            searchIndexableRaw.title = preferenceToSpecificActivityTitleInfo;
            searchIndexableRaw.screenTitle = string;
            arrayList.add(searchIndexableRaw);
            SearchIndexableRaw searchIndexableRaw2 = new SearchIndexableRaw(context);
            String preferenceToSpecificActivityTitleInfo2 =
                    SecDeviceInfoUtils.getPreferenceToSpecificActivityTitleInfo(
                            context, new Intent("android.settings.TERMS"));
            ((SearchIndexableData) searchIndexableRaw2).key = "terms";
            searchIndexableRaw2.title = preferenceToSpecificActivityTitleInfo2;
            searchIndexableRaw2.screenTitle = string;
            arrayList.add(searchIndexableRaw2);
            SearchIndexableRaw searchIndexableRaw3 = new SearchIndexableRaw(context);
            String preferenceToSpecificActivityTitleInfo3 =
                    SecDeviceInfoUtils.getPreferenceToSpecificActivityTitleInfo(
                            context, new Intent("android.settings.WEBVIEW_LICENSE"));
            ((SearchIndexableData) searchIndexableRaw3).key = "webview_license";
            searchIndexableRaw3.title = preferenceToSpecificActivityTitleInfo3;
            searchIndexableRaw3.screenTitle = string;
            arrayList.add(searchIndexableRaw3);
            SearchIndexableRaw searchIndexableRaw4 = new SearchIndexableRaw(context);
            String preferenceToSpecificActivityTitleInfo4 =
                    SecDeviceInfoUtils.getPreferenceToSpecificActivityTitleInfo(
                            context,
                            new Intent("com.samsung.settings.SAMSUNG_KNOX_PRIVACY_NOTICE"));
            ((SearchIndexableData) searchIndexableRaw4).key = "knox_privacy_notice";
            searchIndexableRaw4.title = preferenceToSpecificActivityTitleInfo4;
            arrayList.add(searchIndexableRaw4);
            return arrayList;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "LegalInfoSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 225;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_legal_info_settings;
    }
}
