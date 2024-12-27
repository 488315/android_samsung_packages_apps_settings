package com.android.settings.enterprise;

import android.content.Context;
import android.os.Bundle;

import com.android.settings.R;
import com.android.settings.applications.ApplicationFeatureProviderImpl;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.overlay.FeatureFactoryImpl;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ApplicationListFragment extends DashboardFragment
        implements ApplicationListPreferenceController.ApplicationListBuilder {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class AdminGrantedPermission extends ApplicationListFragment {
        public final String[] mPermissions;

        public AdminGrantedPermission(String[] strArr) {
            this.mPermissions = strArr;
        }

        @Override // com.android.settings.enterprise.ApplicationListPreferenceController.ApplicationListBuilder
        public final void buildApplicationList(
                ApplicationListPreferenceController applicationListPreferenceController) {
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            ApplicationFeatureProviderImpl applicationFeatureProvider =
                    featureFactoryImpl.getApplicationFeatureProvider();
            String[] strArr = this.mPermissions;
            applicationFeatureProvider.getClass();
            new ApplicationFeatureProviderImpl.CurrentUserAppWithAdminGrantedPermissionsLister(
                            strArr,
                            applicationFeatureProvider.mPm,
                            applicationFeatureProvider.mPms,
                            applicationFeatureProvider.mDpm,
                            applicationFeatureProvider.mUm,
                            applicationListPreferenceController)
                    .execute(new Void[0]);
        }

        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 939;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class AdminGrantedPermissionCamera extends AdminGrantedPermission {
        public AdminGrantedPermissionCamera() {
            super(new String[] {"android.permission.CAMERA"});
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class AdminGrantedPermissionLocation extends AdminGrantedPermission {
        public AdminGrantedPermissionLocation() {
            super(
                    new String[] {
                        "android.permission.ACCESS_COARSE_LOCATION",
                        "android.permission.ACCESS_FINE_LOCATION"
                    });
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class AdminGrantedPermissionMicrophone extends AdminGrantedPermission {
        public AdminGrantedPermissionMicrophone() {
            super(new String[] {"android.permission.RECORD_AUDIO"});
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class EnterpriseInstalledPackages extends ApplicationListFragment {
        @Override // com.android.settings.enterprise.ApplicationListPreferenceController.ApplicationListBuilder
        public final void buildApplicationList(
                ApplicationListPreferenceController applicationListPreferenceController) {
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            ApplicationFeatureProviderImpl applicationFeatureProvider =
                    featureFactoryImpl.getApplicationFeatureProvider();
            applicationFeatureProvider.getClass();
            ApplicationFeatureProviderImpl.CurrentUserPolicyInstalledAppLister
                    currentUserPolicyInstalledAppLister =
                            new ApplicationFeatureProviderImpl.CurrentUserPolicyInstalledAppLister(
                                    applicationFeatureProvider.mPm, applicationFeatureProvider.mUm);
            currentUserPolicyInstalledAppLister.mCallback = applicationListPreferenceController;
            currentUserPolicyInstalledAppLister.execute(new Void[0]);
        }

        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 938;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(
                new ApplicationListPreferenceController(
                        context, this, context.getPackageManager(), this));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "EnterprisePrivacySettings";
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.app_list_disclosure_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        replaceEnterpriseStringTitle(
                "enterprise_privacy_apps_footer",
                "Settings.ADMIN_ACTION_APPS_COUNT_ESTIMATED",
                R.string.enterprise_privacy_apps_count_estimation_info);
    }
}
