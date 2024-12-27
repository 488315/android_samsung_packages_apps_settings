package com.android.settings.security;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.settings.R;
import com.android.settings.safetycenter.SafetyCenterUtils;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.security.trustagent.TrustAgentListPreferenceController;
import com.android.settingslib.drawer.Tile;

import com.samsung.android.settings.dynamicmenu.SecDynamicFragment;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SecurityAdvancedSettings extends SecDynamicFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.sec_security_advanced_settings);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.security.SecurityAdvancedSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            BaseSearchIndexProvider baseSearchIndexProvider =
                    SecurityAdvancedSettings.SEARCH_INDEX_DATA_PROVIDER;
            return SafetyCenterUtils.getControllersForAdvancedSecurity(context, null, null);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        return SafetyCenterUtils.getControllersForAdvancedSecurity(
                context, getSettingsLifecycle(), this);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final boolean displayTile(Tile tile) {
        if ("com.android.settings.category.ia.advanced_security".equals(tile.mCategory)
                && "com.google.android.gms.personalsafety.settings.autolock.AutoLockSettingsActivityAdvancedSecuritySafetyCenter"
                        .equals(tile.mComponentName)) {
            return false;
        }
        return super.displayTile(tile);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "SecurityAdvancedSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1885;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_security_advanced_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        if (((TrustAgentListPreferenceController) use(TrustAgentListPreferenceController.class))
                .handleActivityResult(i, i2)) {
            return;
        }
        super.onActivityResult(i, i2, intent);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        SafetyCenterUtils.replaceEnterpriseStringsForSecurityEntries(this);
    }
}
