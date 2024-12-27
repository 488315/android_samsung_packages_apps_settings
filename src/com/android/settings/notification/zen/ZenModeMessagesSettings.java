package com.android.settings.notification.zen;

import android.content.Context;
import android.provider.SearchIndexableResource;

import com.android.settings.R;
import com.android.settings.notification.NotificationBackend;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.search.Indexable$SearchIndexProvider;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ZenModeMessagesSettings extends ZenModeSettingsBase {
    public static final Indexable$SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.notification.zen.ZenModeMessagesSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            return ZenModeMessagesSettings.buildPreferenceControllers$1(context, null);
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.xmlResId = R.xml.zen_mode_messages_settings;
            arrayList.add(searchIndexableResource);
            return arrayList;
        }
    }

    public static List buildPreferenceControllers$1(Context context, Lifecycle lifecycle) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(
                new ZenModePrioritySendersPreferenceController(
                        context,
                        "zen_mode_settings_category_messages",
                        lifecycle,
                        true,
                        new NotificationBackend()));
        arrayList.add(
                new ZenModeBehaviorFooterPreferenceController(
                        context, lifecycle, R.string.zen_mode_messages_footer));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        return buildPreferenceControllers$1(context, getSettingsLifecycle());
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1839;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.zen_mode_messages_settings;
    }
}
