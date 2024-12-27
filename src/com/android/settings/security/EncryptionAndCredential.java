package com.android.settings.security;

import android.content.Context;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.PreferenceCategoryController;
import com.android.settingslib.core.lifecycle.Lifecycle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class EncryptionAndCredential extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.encryption_and_credential);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.security.EncryptionAndCredential$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            return EncryptionAndCredential.buildPreferenceControllers$1(context, null);
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return true;
        }
    }

    public static List buildPreferenceControllers$1(Context context, Lifecycle lifecycle) {
        ArrayList arrayList = new ArrayList();
        EncryptionStatusPreferenceController encryptionStatusPreferenceController =
                new EncryptionStatusPreferenceController(
                        context,
                        EncryptionStatusPreferenceController.PREF_KEY_ENCRYPTION_DETAIL_PAGE);
        arrayList.add(encryptionStatusPreferenceController);
        arrayList.add(
                new PreferenceCategoryController(
                                context, "encryption_and_credentials_status_category")
                        .setChildren(Arrays.asList(encryptionStatusPreferenceController)));
        arrayList.add(new UserCredentialsPreferenceController(context));
        arrayList.add(new ResetCredentialsPreferenceController(context, lifecycle));
        arrayList.add(new InstallCertificatePreferenceController(context));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        return buildPreferenceControllers$1(context, getSettingsLifecycle());
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "EncryptionAndCredential";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 846;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.encryption_and_credential;
    }
}
