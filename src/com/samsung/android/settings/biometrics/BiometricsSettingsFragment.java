package com.samsung.android.settings.biometrics;

import android.content.Context;
import android.content.res.Resources;
import android.provider.SearchIndexableData;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.biometrics.face.FaceEntry;
import com.samsung.android.settings.biometrics.face.FaceSettingsPreferenceController;
import com.samsung.android.settings.biometrics.fingerprint.FingerprintEntry;
import com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsPreferenceController;
import com.samsung.android.settings.dynamicmenu.SecDynamicFragment;
import com.samsung.android.settings.knox.KnoxUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BiometricsSettingsFragment extends SecDynamicFragment {
    public static final String TAG = BiometricsSettingsFragment.class.getName();
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.biometrics.BiometricsSettingsFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            ArrayList arrayList = (ArrayList) nonIndexableKeys;
            arrayList.add(FaceSettingsPreferenceController.KEY_FACE_SETTINGS);
            arrayList.add(FingerprintSettingsPreferenceController.KEY_FINGER_SCANNER);
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            Resources resources = context.getResources();
            ArrayList arrayList = new ArrayList();
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            if (new FaceSettingsPreferenceController(context).isAvailable()) {
                searchIndexableRaw.screenTitle =
                        Rune.SUPPORT_AOD
                                ? resources.getString(R.string.lockscreen_and_aod)
                                : resources.getString(R.string.lockscreen);
                searchIndexableRaw.title = resources.getString(R.string.bio_face_recognition_title);
                ((SearchIndexableData) searchIndexableRaw).key =
                        FaceSettingsPreferenceController.KEY_FACE_SETTINGS;
                ((SearchIndexableData) searchIndexableRaw).intentTargetPackage =
                        context.getPackageName();
                ((SearchIndexableData) searchIndexableRaw).intentTargetClass =
                        FaceEntry.class.getName();
                arrayList.add(searchIndexableRaw);
            }
            if (new FingerprintSettingsPreferenceController(context).isAvailable()) {
                SearchIndexableRaw searchIndexableRaw2 = new SearchIndexableRaw(context);
                searchIndexableRaw2.screenTitle =
                        Rune.SUPPORT_AOD
                                ? resources.getString(R.string.lockscreen_and_aod)
                                : resources.getString(R.string.lockscreen);
                searchIndexableRaw2.title = String.valueOf(R.string.bio_fingerprint_sanner_title);
                ((SearchIndexableData) searchIndexableRaw2).key =
                        FingerprintSettingsPreferenceController.KEY_FINGER_SCANNER;
                ((SearchIndexableData) searchIndexableRaw2).intentTargetPackage =
                        context.getPackageName();
                ((SearchIndexableData) searchIndexableRaw2).intentTargetClass =
                        FingerprintEntry.class.getName();
                arrayList.add(searchIndexableRaw2);
            }
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return (KnoxUtils.checkKnoxCustomSettingsHiddenItem(16)
                            || Utils.isGuestUser(context)
                            || Rune.isShopDemo(context))
                    ? false
                    : true;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return TAG;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_biometrics_settings;
    }
}
