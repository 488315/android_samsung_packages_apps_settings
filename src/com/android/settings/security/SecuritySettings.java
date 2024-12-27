package com.android.settings.security;

import android.content.Context;
import android.content.Intent;

import com.android.settings.R;
import com.android.settings.biometrics.combination.CombinedBiometricStatusPreferenceController;
import com.android.settings.biometrics.face.FaceStatusPreferenceController;
import com.android.settings.biometrics.fingerprint.FingerprintStatusPreferenceController;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.safetycenter.SafetyCenterManagerWrapper;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.security.trustagent.TrustAgentListPreferenceController;
import com.android.settings.widget.PreferenceCategoryController;
import com.android.settingslib.core.lifecycle.Lifecycle;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SecuritySettings extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.security_dashboard_settings);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.security.SecuritySettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            return SecuritySettings.buildPreferenceControllers(context, null, null);
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            ((SecuritySettingsFeatureProvider)
                            featureFactoryImpl.securitySettingsFeatureProvider$delegate.getValue())
                    .getClass();
            SafetyCenterManagerWrapper.get().getClass();
            return !SafetyCenterManagerWrapper.isEnabled(context);
        }
    }

    public static List buildPreferenceControllers(
            Context context, Lifecycle lifecycle, SecuritySettings securitySettings) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new FaceStatusPreferenceController(context, lifecycle));
        arrayList2.add(new FingerprintStatusPreferenceController(context, lifecycle));
        arrayList2.add(new CombinedBiometricStatusPreferenceController(context, lifecycle));
        arrayList2.add(new ChangeScreenLockPreferenceController(context, securitySettings));
        arrayList.add(
                new PreferenceCategoryController(context, "security_category")
                        .setChildren(arrayList2));
        arrayList.addAll(arrayList2);
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        return buildPreferenceControllers(context, super.getSettingsLifecycle(), this);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "SecuritySettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 87;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.security_dashboard_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        if (((TrustAgentListPreferenceController) use(TrustAgentListPreferenceController.class))
                        .handleActivityResult(i, i2)
                || ((LockUnificationPreferenceController)
                                use(LockUnificationPreferenceController.class))
                        .handleActivityResult(i, i2, intent)) {
            return;
        }
        super.onActivityResult(i, i2, intent);
    }
}
