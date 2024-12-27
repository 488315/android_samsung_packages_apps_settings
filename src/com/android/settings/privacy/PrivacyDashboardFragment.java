package com.android.settings.privacy;

import android.content.Context;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.safetycenter.SafetyCenterManagerWrapper;
import com.android.settings.safetycenter.SafetyCenterUtils;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.utils.SensorPrivacyManagerHelper;

import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PrivacyDashboardFragment extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.sec_privacy_dashboard_settings);
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass2();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.privacy.PrivacyDashboardFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            BaseSearchIndexProvider baseSearchIndexProvider =
                    PrivacyDashboardFragment.SEARCH_INDEX_DATA_PROVIDER;
            return SafetyCenterUtils.getControllersForAdvancedPrivacy(context, null);
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            if (Utils.getManagedProfileId(UserManager.get(context), UserHandle.myUserId())
                    != -10000) {
                return nonIndexableKeys;
            }
            ((ArrayList) nonIndexableKeys).add("privacy_lock_screen_work_profile_notifications");
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            if (isPageSearchEnabled(context)) {
                return super.getXmlResourcesToIndex(context);
            }
            return null;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            SafetyCenterManagerWrapper.get().getClass();
            return !SafetyCenterManagerWrapper.isEnabled(context);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.privacy.PrivacyDashboardFragment$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            SensorPrivacyManagerHelper sensorPrivacyManagerHelper =
                    SensorPrivacyManagerHelper.getInstance(context);
            ArrayList arrayList = new ArrayList();
            String valueOf = String.valueOf(59200);
            String str =
                    sensorPrivacyManagerHelper.isSensorBlocked(-1, 2)
                            ? DATA.DM_FIELD_INDEX.PCSCF_DOMAIN
                            : "1";
            StatusData statusData = new StatusData();
            statusData.mStatusValue = str;
            statusData.mStatusKey = valueOf;
            arrayList.add(statusData);
            String valueOf2 = String.valueOf(59201);
            String str2 =
                    sensorPrivacyManagerHelper.isSensorBlocked(-1, 1)
                            ? DATA.DM_FIELD_INDEX.PCSCF_DOMAIN
                            : "1";
            StatusData statusData2 = new StatusData();
            statusData2.mStatusValue = str2;
            statusData2.mStatusKey = valueOf2;
            arrayList.add(statusData2);
            String valueOf3 = String.valueOf(59202);
            String str3 =
                    Settings.Secure.getInt(
                                            context.getContentResolver(),
                                            "clipboard_show_access_notifications",
                                            0)
                                    == 0
                            ? DATA.DM_FIELD_INDEX.PCSCF_DOMAIN
                            : "1";
            StatusData statusData3 = new StatusData();
            statusData3.mStatusValue = str3;
            statusData3.mStatusKey = valueOf3;
            arrayList.add(statusData3);
            return arrayList;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        return SafetyCenterUtils.getControllersForAdvancedPrivacy(context, getSettingsLifecycle());
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "PrivacyDashboardFrag";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 57021;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_privacy_dashboard_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        SafetyCenterUtils.replaceEnterpriseStringsForPrivacyEntries(this);
        setAutoRemoveInsetCategory(false);
    }
}
