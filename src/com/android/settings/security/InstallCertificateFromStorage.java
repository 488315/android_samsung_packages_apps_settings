package com.android.settings.security;

import android.content.Context;
import android.os.Bundle;
import android.os.UserManager;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;

import com.sec.ims.volte2.data.VolteConstants;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class InstallCertificateFromStorage extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.install_certificate_from_storage);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.security.InstallCertificateFromStorage$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            return InstallCertificateFromStorage.buildPreferenceControllers$1$1(context);
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return ((UserManager) context.getSystemService("user")).isAdminUser();
        }
    }

    public static List buildPreferenceControllers$1$1(Context context) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new InstallCaCertificatePreferenceController(context));
        arrayList.add(new InstallUserCertificatePreferenceController(context));
        if (!Utils.isGuestUser(context)) {
            arrayList.add(new InstallWifiCertificatePreferenceController(context));
        }
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        getSettingsLifecycle();
        return buildPreferenceControllers$1$1(context);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "InstallCertificateFromStorage";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return VolteConstants.ErrorCode.MISSED_CALL_NOTIFICATION;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.install_certificate_from_storage;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (Utils.isGuestUser(getActivity())) {
            removePreference("install_wifi_certificate");
        }
    }
}
