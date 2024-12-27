package com.samsung.android.settings.privacy;

import android.content.Context;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.instrumentation.Instrumentable;

import com.samsung.android.settings.dynamicmenu.SecDynamicFragment;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.samsung.android.settings.theftprotection.MandatoryBiometricController;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecurityDashboardTheftProtectionFragment extends SecDynamicFragment {
    public static final String TAG = SecurityDashboardTheftProtectionFragment.class.getName();
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.sec_security_and_privacy_theft_protection_settings);
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass2();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.privacy.SecurityDashboardTheftProtectionFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return SecurityDashboardUtils.isTheftProtectionSupported(context);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.privacy.SecurityDashboardTheftProtectionFragment$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            ArrayList arrayList = new ArrayList();
            Uri uri = SecurityDashboardUtils.FMM_SUPPORT_URI;
            boolean z =
                    context.getSharedPreferences("key_theft_protection_preference", 0)
                            .getBoolean("key_theft_detection_lock_on_boarding_status", false);
            String str = DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            String str2 =
                    !z
                            ? "2"
                            : Settings.Secure.getInt(
                                                    context.getContentResolver(),
                                                    "theft_detection_lock_setting",
                                                    0)
                                            != 0
                                    ? "1"
                                    : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            String valueOf = String.valueOf(54201);
            StatusData statusData = new StatusData();
            statusData.mStatusValue = str2;
            statusData.mStatusKey = valueOf;
            arrayList.add(statusData);
            if (!context.getSharedPreferences("key_theft_protection_preference", 0)
                    .getBoolean("key_offline_lock_on_boarding_status", false)) {
                str = "2";
            } else if (Settings.Secure.getInt(
                            context.getContentResolver(), "offline_device_lock_setting", 0)
                    != 0) {
                str = "1";
            }
            String valueOf2 = String.valueOf(54202);
            StatusData statusData2 = new StatusData();
            statusData2.mStatusValue = str;
            statusData2.mStatusKey = valueOf2;
            arrayList.add(statusData2);
            int i =
                    Settings.Secure.getInt(
                            context.getContentResolver(), "mandatory_biometrics", -1);
            String valueOf3 = i != -1 ? String.valueOf(i) : "2";
            String valueOf4 = String.valueOf(54204);
            StatusData statusData3 = new StatusData();
            statusData3.mStatusValue = valueOf3;
            statusData3.mStatusKey = valueOf4;
            arrayList.add(statusData3);
            return arrayList;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return TAG;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 54101;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_security_and_privacy_theft_protection_settings;
    }

    @Override // com.samsung.android.settings.dynamicmenu.SecDynamicFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        ((SecurityDashboardTheftDetectionLockPreferenceController)
                        use(SecurityDashboardTheftDetectionLockPreferenceController.class))
                .init(this);
        ((SecurityDashboardOfflineLockPreferenceController)
                        use(SecurityDashboardOfflineLockPreferenceController.class))
                .init(this);
        ((MandatoryBiometricController) use(MandatoryBiometricController.class)).init(this);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.info_menu, menu);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.info_button) {
            LoggingHelper.insertEventLogging(54101, 54206);
            LifecycleOwner parentFragment = getParentFragment();
            FragmentActivity activity = getActivity();
            if (activity instanceof SettingsActivity) {
                SubSettingLauncher subSettingLauncher = new SubSettingLauncher(activity);
                SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
                launchRequest.mDestinationName =
                        "com.samsung.android.settings.privacy.TheftProtectionInfoFragment";
                launchRequest.mSourceMetricsCategory =
                        parentFragment instanceof Instrumentable
                                ? ((Instrumentable) parentFragment).getMetricsCategory()
                                : 0;
                subSettingLauncher.launch();
                activity.overridePendingTransition(0, 0);
            } else {
                Log.e(
                        TAG,
                        "Parent isn't SettingsActivity nor PreferenceActivity, thus there's no way"
                            + " to launch the given Fragment (name:"
                            + " com.samsung.android.settings.privacy.TheftProtectionInfoFragment)");
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
