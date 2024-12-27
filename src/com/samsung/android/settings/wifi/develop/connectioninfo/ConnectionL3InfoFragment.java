package com.samsung.android.settings.wifi.develop.connectioninfo;

import android.content.Context;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settingslib.core.lifecycle.Lifecycle;

import com.samsung.android.settings.wifi.develop.connectioninfo.ConnectionL3PreferenceController.ConnectionInfoRepoCallback;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class ConnectionL3InfoFragment extends DashboardFragment {
    public ConnectionInfoRepo mConnectionInfoRepo;

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        ConnectionInfoRepo connectionInfoRepo = this.mConnectionInfoRepo;
        if (connectionInfoRepo == null) {
            Log.d("ConnectionL3InfoFragment", "Abnormally finished. ConnectionInfoRepo is null");
            finish();
        } else {
            Lifecycle settingsLifecycle = getSettingsLifecycle();
            ConnectionL3PreferenceController connectionL3PreferenceController =
                    new ConnectionL3PreferenceController(context);
            connectionL3PreferenceController.mConnectionInfoRepo = connectionInfoRepo;
            connectionL3PreferenceController.mConnectionInfoRepoCallback =
                    connectionL3PreferenceController.new ConnectionInfoRepoCallback();
            settingsLifecycle.addObserver(connectionL3PreferenceController);
            arrayList.add(connectionL3PreferenceController);
        }
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return null;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_wifi_develop_connection_l3_status_fragment;
    }
}
