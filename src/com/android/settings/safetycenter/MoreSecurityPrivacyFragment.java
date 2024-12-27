package com.android.settings.safetycenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.security.LockUnificationPreferenceController;
import com.android.settings.security.trustagent.TrustAgentListPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;

import com.sec.ims.volte2.data.VolteConstants;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class MoreSecurityPrivacyFragment extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.more_security_privacy_settings);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.safetycenter.MoreSecurityPrivacyFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            BaseSearchIndexProvider baseSearchIndexProvider =
                    MoreSecurityPrivacyFragment.SEARCH_INDEX_DATA_PROVIDER;
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(SafetyCenterUtils.getControllersForAdvancedPrivacy(context, null));
            arrayList.addAll(
                    SafetyCenterUtils.getControllersForAdvancedSecurity(context, null, null));
            return arrayList;
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
            return SafetyCenterManagerWrapper.isEnabled(context);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        Lifecycle settingsLifecycle = getSettingsLifecycle();
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(
                SafetyCenterUtils.getControllersForAdvancedPrivacy(context, settingsLifecycle));
        arrayList.addAll(
                SafetyCenterUtils.getControllersForAdvancedSecurity(
                        context, settingsLifecycle, this));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final String getCategoryKey() {
        return "com.android.settings.category.ia.more_security_privacy_settings";
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "MoreSecurityPrivacyFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return VolteConstants.ErrorCode.MAKECALL_REG_FAILURE_GENERAL;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.more_security_privacy_settings;
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

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        SafetyCenterUtils.replaceEnterpriseStringsForPrivacyEntries(this);
        SafetyCenterUtils.replaceEnterpriseStringsForSecurityEntries(this);
    }
}
