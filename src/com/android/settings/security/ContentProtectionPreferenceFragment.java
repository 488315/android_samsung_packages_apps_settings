package com.android.settings.security;

import android.content.ComponentName;
import android.content.Context;
import android.provider.DeviceConfig;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ContentProtectionPreferenceFragment extends DashboardFragment {
    public static final ContentProtectionSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new ContentProtectionSearchIndexProvider(
                    R.layout.content_protection_preference_fragment);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ContentProtectionSearchIndexProvider extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public boolean isPageSearchEnabled(Context context) {
            if (!DeviceConfig.getBoolean(
                    "content_capture", "enable_content_protection_receiver", false)) {
                return false;
            }
            String string = context.getString(android.R.string.date_picker_increment_month_button);
            return (string == null ? null : ComponentName.unflattenFromString(string)) != null;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "ContentProtectionPreferenceFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2045;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.layout.content_protection_preference_fragment;
    }
}
