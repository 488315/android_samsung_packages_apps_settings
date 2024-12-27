package com.android.settings.development.qstile;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.os.SystemProperties;
import android.provider.SearchIndexableData;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.development.DeveloperOptionAwareMixin;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.development.DevelopmentSettingsEnabler;
import com.android.settingslib.search.SearchIndexableRaw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DevelopmentTileConfigFragment extends DashboardFragment
        implements DeveloperOptionAwareMixin {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.development_tile_settings);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.development.qstile.DevelopmentTileConfigFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            for (Map.Entry<String, ?> entry :
                    context.getSharedPreferences("develop_qs_tile", 0).getAll().entrySet()) {
                if (entry.getValue() != null) {
                    String key = entry.getKey();
                    if (!SystemProperties.getBoolean(entry.getValue().toString(), false)) {
                        ((ArrayList) nonIndexableKeys).add(key);
                    }
                }
            }
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            String string;
            ArrayList arrayList = new ArrayList();
            SharedPreferences sharedPreferences =
                    context.getSharedPreferences("develop_qs_tile", 0);
            List<ServiceInfo> tileServiceList =
                    DevelopmentTilePreferenceController.getTileServiceList(context);
            PackageManager packageManager = context.getPackageManager();
            SharedPreferences.Editor edit = sharedPreferences.edit();
            for (ServiceInfo serviceInfo : tileServiceList) {
                SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
                searchIndexableRaw.title = serviceInfo.loadLabel(packageManager).toString();
                ((SearchIndexableData) searchIndexableRaw).key = serviceInfo.name;
                arrayList.add(searchIndexableRaw);
                Bundle bundle = serviceInfo.metaData;
                if (bundle != null
                        && (string =
                                        bundle.getString(
                                                "com.android.settings.development.qstile.REQUIRES_SYSTEM_PROPERTY"))
                                != null) {
                    edit.putString(serviceInfo.name, string);
                }
            }
            edit.apply();
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return DevelopmentSettingsEnabler.isDevelopmentSettingsEnabled(context);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "DevelopmentTileConfig";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1224;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.development_tile_settings;
    }
}
