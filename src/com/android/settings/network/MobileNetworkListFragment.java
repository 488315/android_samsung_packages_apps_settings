package com.android.settings.network;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.network.telephony.MobileNetworkUtils;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.spa.SpaActivity;
import com.android.settingslib.spa.framework.util.FlowsKt;
import com.android.settingslib.spaprivileged.framework.common.ContextsKt;
import com.android.settingslib.spaprivileged.settingsprovider.SettingsGlobalBooleanDelegate;
import com.android.settingslib.spaprivileged.settingsprovider.SettingsGlobalBooleanKt;
import com.android.settingslib.spaprivileged.settingsprovider.SettingsGlobalBooleanKt$settingsGlobalBooleanFlow$$inlined$map$1;
import com.android.settingslib.spaprivileged.settingsprovider.SettingsGlobalChangeFlowKt;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

import java.util.regex.Pattern;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001:\u0001\u0004B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0005"
        },
        d2 = {
            "Lcom/android/settings/network/MobileNetworkListFragment;",
            "Lcom/android/settings/dashboard/DashboardFragment;",
            "<init>",
            "()V",
            "Companion",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class MobileNetworkListFragment extends DashboardFragment {
    public static final Companion.SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new Companion.SearchIndexProvider();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class Companion {

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        @Metadata(
                d1 = {
                    "\u0000\n\n"
                        + "\u0000\n"
                        + "\u0002\u0018\u0002\n"
                        + "\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"
                },
                d2 = {
                    "com/android/settings/network/MobileNetworkListFragment$Companion$SearchIndexProvider",
                    "Lcom/android/settings/search/BaseSearchIndexProvider;",
                    "<init>",
                    "()V",
                    "applications__sources__apps__SecSettings__android_common__SecSettings-core"
                },
                k = 1,
                mv = {1, 9, 0})
        public final class SearchIndexProvider extends BaseSearchIndexProvider {
            public SearchIndexProvider() {
                super(R.xml.network_provider_sims_list);
            }

            @Override // com.android.settings.search.BaseSearchIndexProvider
            public final boolean isPageSearchEnabled(Context context) {
                Intrinsics.checkNotNullParameter(context, "context");
                Pattern pattern = SubscriptionUtil.REGEX_DISPLAY_NAME_SUFFIX_PATTERN;
                return context.getResources().getBoolean(R.bool.config_show_sim_info)
                        && ContextsKt.getUserManager(context).isAdminUser();
            }
        }

        public static void collectAirplaneModeAndFinishIfOn(
                SettingsPreferenceFragment settingsPreferenceFragment) {
            Intrinsics.checkNotNullParameter(settingsPreferenceFragment, "<this>");
            Context requireContext = settingsPreferenceFragment.requireContext();
            Intrinsics.checkNotNullExpressionValue(requireContext, "requireContext(...)");
            KProperty[] kPropertyArr = SettingsGlobalBooleanKt.$$delegatedProperties;
            Flow distinctUntilChanged =
                    FlowKt.distinctUntilChanged(
                            new SettingsGlobalBooleanKt$settingsGlobalBooleanFlow$$inlined$map$1(
                                    SettingsGlobalChangeFlowKt.settingsGlobalChangeFlow(
                                            requireContext, "airplane_mode_on", true),
                                    new SettingsGlobalBooleanDelegate(
                                            requireContext, "airplane_mode_on", false)));
            LifecycleOwner viewLifecycleOwner = settingsPreferenceFragment.getViewLifecycleOwner();
            Intrinsics.checkNotNullExpressionValue(
                    viewLifecycleOwner, "getViewLifecycleOwner(...)");
            FlowsKt.collectLatestWithLifecycle(
                    distinctUntilChanged,
                    viewLifecycleOwner,
                    Lifecycle.State.STARTED,
                    new MobileNetworkListFragment$Companion$collectAirplaneModeAndFinishIfOn$1(
                            settingsPreferenceFragment, null));
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "NetworkListFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1627;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.network_provider_sims_list;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Context context = getContext();
        if (context != null) {
            SpaActivity.Companion companion = SpaActivity.Companion;
            SpaActivity.Companion.startSpaActivity(context, "NetworkCellularGroupProvider");
        }
        finish();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        getListView().setItemAnimator(null);
        Preference findPreference = findPreference("add_sim");
        Intrinsics.checkNotNull(findPreference);
        findPreference.setVisible(MobileNetworkUtils.showEuiccSettings(getContext()));
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, bundle);
        Companion.collectAirplaneModeAndFinishIfOn(this);
    }
}
