package com.android.settings.network;

import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.LifecycleOwner;

import com.android.settings.R;
import com.android.settings.SettingsDumpService;
import com.android.settings.core.OnActivityResultListener;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.lifecycle.Lifecycle;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NetworkDashboardFragment extends DashboardFragment
        implements OnActivityResultListener {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.network_provider_internet);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.network.NetworkDashboardFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            return NetworkDashboardFragment.buildPreferenceControllers(context, null, null);
        }
    }

    public static List buildPreferenceControllers(
            Context context, Lifecycle lifecycle, LifecycleOwner lifecycleOwner) {
        VpnPreferenceController vpnPreferenceController = new VpnPreferenceController(context);
        PrivateDnsPreferenceController privateDnsPreferenceController =
                new PrivateDnsPreferenceController(context);
        if (lifecycle != null) {
            lifecycle.addObserver(vpnPreferenceController);
            lifecycle.addObserver(privateDnsPreferenceController);
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(new MobileNetworkSummaryController(context, lifecycle, lifecycleOwner));
        arrayList.add(vpnPreferenceController);
        arrayList.add(new InternetPreferenceController(context, lifecycle, lifecycleOwner));
        arrayList.add(privateDnsPreferenceController);
        Intent intent = new Intent(context, (Class<?>) SettingsDumpService.class);
        intent.putExtra("show_network_dump", true);
        context.startService(intent);
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        return buildPreferenceControllers(context, getSettingsLifecycle(), this);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "NetworkDashboardFrag";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 746;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.network_provider_internet;
    }

    @Override // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i != 1) {
            return;
        }
        ((AirplaneModePreferenceController) use(AirplaneModePreferenceController.class))
                .onActivityResult(i, i2, intent);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        ((AirplaneModePreferenceController) use(AirplaneModePreferenceController.class))
                .setFragment(this);
        ((NetworkProviderCallsSmsController) use(NetworkProviderCallsSmsController.class))
                .init(this);
    }
}
