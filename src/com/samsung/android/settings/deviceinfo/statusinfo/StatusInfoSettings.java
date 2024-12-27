package com.samsung.android.settings.deviceinfo.statusinfo;

import android.content.Context;
import android.net.ConnectivityManager;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.deviceinfo.BluetoothAddressPreferenceController;
import com.android.settings.deviceinfo.IpAddressPreferenceController;
import com.android.settings.deviceinfo.UptimePreferenceController;
import com.android.settings.deviceinfo.WifiMacAddressPreferenceController;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.search.Indexable$SearchIndexProvider;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class StatusInfoSettings extends DashboardFragment {
    public static final Indexable$SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.sec_device_info_status);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.deviceinfo.statusinfo.StatusInfoSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            return StatusInfoSettings.buildPreferenceControllers$5(context, null);
        }
    }

    public static List buildPreferenceControllers$5(Context context, Lifecycle lifecycle) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new IpAddressPreferenceController(context, lifecycle));
        arrayList.add(new WifiMacAddressPreferenceController(context, lifecycle));
        arrayList.add(new BluetoothAddressPreferenceController(context, lifecycle));
        arrayList.add(new UptimePreferenceController(context, lifecycle));
        WiMacAddressPreferenceController wiMacAddressPreferenceController =
                new WiMacAddressPreferenceController(context, lifecycle);
        wiMacAddressPreferenceController.mCM =
                (ConnectivityManager) context.getSystemService("connectivity");
        arrayList.add(wiMacAddressPreferenceController);
        arrayList.add(new PaymentServicesPreferenceController(context));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        getActivity();
        return buildPreferenceControllers$5(context, getSettingsLifecycle());
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "DeviceStatus";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 44;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_device_info_status;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        ((EidPreferenceController) use(EidPreferenceController.class)).setHost(this);
    }
}
