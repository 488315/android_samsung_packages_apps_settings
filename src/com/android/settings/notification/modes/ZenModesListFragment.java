package com.android.settings.notification.modes;

import android.content.Context;

import com.android.settings.R;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.utils.ManagedServiceSettings;
import com.android.settings.utils.ZenServiceListing;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ZenModesListFragment extends ZenModesFragmentBase {
    public static final ManagedServiceSettings.Config CONFIG =
            new ManagedServiceSettings.Config(
                    "ZenModesSettings",
                    null,
                    "android.service.notification.ConditionProviderService",
                    "android.app.action.AUTOMATIC_ZEN_RULE",
                    "android.permission.BIND_CONDITION_PROVIDER_SERVICE",
                    "condition provider",
                    0,
                    0,
                    0);
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.modes_list_settings);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.notification.modes.ZenModesListFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            ManagedServiceSettings.Config config = ZenModesListFragment.CONFIG;
            if (ZenModesBackend.sInstance == null) {
                ZenModesBackend.sInstance = new ZenModesBackend(context.getApplicationContext());
            }
            ZenModesBackend zenModesBackend = ZenModesBackend.sInstance;
            return ImmutableList.construct(
                    new ZenModesListPreferenceController(context, null, zenModesBackend),
                    new ZenModesListAddModePreferenceController(context, zenModesBackend));
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            ((ArrayList) nonIndexableKeys).add("zen_modes_list");
            return nonIndexableKeys;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        new ZenServiceListing(getContext(), CONFIG).reloadApprovedServices();
        if (ZenModesBackend.sInstance == null) {
            ZenModesBackend.sInstance = new ZenModesBackend(context.getApplicationContext());
        }
        ZenModesBackend zenModesBackend = ZenModesBackend.sInstance;
        return ImmutableList.construct(
                new ZenModesListPreferenceController(context, this, zenModesBackend),
                new ZenModesListAddModePreferenceController(context, zenModesBackend));
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 142;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.modes_list_settings;
    }

    @Override // com.android.settings.notification.modes.ZenModesFragmentBase
    public final void updateZenModeState() {}
}
