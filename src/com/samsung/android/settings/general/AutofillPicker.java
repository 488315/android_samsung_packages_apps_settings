package com.samsung.android.settings.general;

import android.content.Context;
import android.credentials.CredentialManager;
import android.os.Bundle;
import android.provider.SearchIndexableResource;

import androidx.lifecycle.LifecycleObserver;

import com.android.settings.R;
import com.android.settings.applications.autofill.PasswordsPreferenceController;
import com.android.settings.applications.credentials.CredentialManagerPreferenceController;
import com.android.settings.applications.credentials.DefaultCombinedPreferenceController;
import com.android.settings.applications.credentials.DefaultPrivateCombinedPreferenceController;
import com.android.settings.applications.credentials.DefaultWorkCombinedPreferenceController;
import com.android.settings.applications.defaultapps.DefaultAutofillPreferenceController;
import com.android.settings.applications.defaultapps.DefaultPrivateAutofillPreferenceController;
import com.android.settings.applications.defaultapps.DefaultWorkAutofillPreferenceController;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;

import com.samsung.android.settings.logging.SALogging;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AutofillPicker extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass2();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.general.AutofillPicker$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            ArrayList arrayList = new ArrayList();
            AutofillPicker.buildAutofillPreferenceControllers(context, arrayList);
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            BaseSearchIndexProvider baseSearchIndexProvider =
                    AutofillPicker.SEARCH_INDEX_DATA_PROVIDER;
            searchIndexableResource.xmlResId =
                    (context == null || !CredentialManager.isServiceEnabled(context))
                            ? R.xml.sec_default_autofill_picker_settings
                            : R.xml.sec_default_autofill_picker_settings_credman;
            return List.of(searchIndexableResource);
        }
    }

    public static void buildAutofillPreferenceControllers(Context context, List list) {
        if (CredentialManager.isServiceEnabled(context)) {
            ArrayList arrayList = (ArrayList) list;
            arrayList.add(new DefaultCombinedPreferenceController(context));
            arrayList.add(new DefaultWorkCombinedPreferenceController(context));
            arrayList.add(new DefaultPrivateCombinedPreferenceController(context));
            return;
        }
        ArrayList arrayList2 = (ArrayList) list;
        arrayList2.add(new DefaultAutofillPreferenceController(context));
        arrayList2.add(new DefaultWorkAutofillPreferenceController(context));
        arrayList2.add(new DefaultPrivateAutofillPreferenceController(context));
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        buildAutofillPreferenceControllers(context, arrayList);
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "AutofillPicker";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 792;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        Context context = getContext();
        return (context == null || !CredentialManager.isServiceEnabled(context))
                ? R.xml.sec_default_autofill_picker_settings
                : R.xml.sec_default_autofill_picker_settings_credman;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        if (!CredentialManager.isServiceEnabled(context)) {
            getSettingsLifecycle()
                    .addObserver((LifecycleObserver) use(PasswordsPreferenceController.class));
            return;
        }
        ((CredentialManagerPreferenceController) use(CredentialManagerPreferenceController.class))
                .init(
                        this,
                        getFragmentManager(),
                        getIntent(),
                        new CredentialManagerPreferenceController
                                .Delegate() { // from class:
                                              // com.samsung.android.settings.general.AutofillPicker.1
                            @Override // com.android.settings.applications.credentials.CredentialManagerPreferenceController.Delegate
                            public final void forceDelegateRefresh() {
                                AutofillPicker.this.forceUpdatePreferences();
                            }

                            @Override // com.android.settings.applications.credentials.CredentialManagerPreferenceController.Delegate
                            public final void setActivityResult(int i) {
                                AutofillPicker.this.getActivity().setResult(i);
                            }
                        },
                        false);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        SALogging.insertSALog("ST8500");
    }
}
