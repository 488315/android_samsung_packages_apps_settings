package com.samsung.android.settings.wifi.develop.connectioninfo;

import android.content.Context;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settingslib.core.lifecycle.Lifecycle;

import com.samsung.android.settings.wifi.develop.connectioninfo.ConnectionStatusPreferenceController.ConnectionInfoRepoCallback;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class ConnectionStatusFragment extends DashboardFragment {
    public ConnectionInfoRepo mConnectionInfoRepo;

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        if (this.mConnectionInfoRepo == null) {
            Log.d("ConnectionStatusFragment", "Abnormally finished. ConnectionInfoRepo is null");
            finish();
        } else {
            ConnectionInfoRepo connectionInfoRepo = this.mConnectionInfoRepo;
            Lifecycle settingsLifecycle = getSettingsLifecycle();
            ConnectionStatusPreferenceController connectionStatusPreferenceController =
                    new ConnectionStatusPreferenceController(context);
            connectionStatusPreferenceController.mGraphLabels =
                    new String[] {"2.4GHz", "5GHz", "6GHz"};
            connectionStatusPreferenceController.mConnectionInfoRepo = connectionInfoRepo;
            connectionStatusPreferenceController.mConnectionInfoRepoCallback =
                    connectionStatusPreferenceController.new ConnectionInfoRepoCallback();
            settingsLifecycle.addObserver(connectionStatusPreferenceController);
            arrayList.add(connectionStatusPreferenceController);
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
        return R.xml.sec_wifi_develop_connection_status_fragment;
    }
}
