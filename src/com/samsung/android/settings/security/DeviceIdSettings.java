package com.samsung.android.settings.security;

import android.content.Context;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;

import com.samsung.android.settings.Rune;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class DeviceIdSettings extends DashboardFragment {
    public static List mPrefControllers = new ArrayList();
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.sec_device_id);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.security.DeviceIdSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return Utils.hasPackage(context, "com.samsung.android.deviceidservice")
                    && Rune.isChinaModel();
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        getSettingsLifecycle();
        ArrayList arrayList = new ArrayList();
        mPrefControllers = arrayList;
        arrayList.add(new ResetDeviceIdPreferenceController(context));
        ((ArrayList) mPrefControllers).add(new AllowDeviceIdPreferenceController(context));
        return mPrefControllers;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "DeviceIdSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 87;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_device_id;
    }
}
