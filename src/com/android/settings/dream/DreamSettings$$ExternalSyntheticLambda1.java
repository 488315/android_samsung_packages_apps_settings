package com.android.settings.dream;

import androidx.preference.Preference;

import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.AbstractPreferenceController;

import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class DreamSettings$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DreamSettings f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ DreamSettings$$ExternalSyntheticLambda1(
            DreamSettings dreamSettings, boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = dreamSettings;
        this.f$1 = z;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Preference findPreference;
        switch (this.$r8$classId) {
            case 0:
                DreamSettings dreamSettings = this.f$0;
                boolean z = this.f$1;
                BaseSearchIndexProvider baseSearchIndexProvider =
                        DreamSettings.SEARCH_INDEX_DATA_PROVIDER;
                dreamSettings.getClass();
                ((List) obj)
                        .forEach(new DreamSettings$$ExternalSyntheticLambda1(dreamSettings, z, 1));
                break;
            default:
                DreamSettings dreamSettings2 = this.f$0;
                boolean z2 = this.f$1;
                AbstractPreferenceController abstractPreferenceController =
                        (AbstractPreferenceController) obj;
                BaseSearchIndexProvider baseSearchIndexProvider2 =
                        DreamSettings.SEARCH_INDEX_DATA_PROVIDER;
                dreamSettings2.getClass();
                String preferenceKey = abstractPreferenceController.getPreferenceKey();
                if (!preferenceKey.equals("dream_main_settings_switch")
                        && !preferenceKey.equals(DreamHomeControlsPreferenceController.PREF_KEY)
                        && (findPreference = dreamSettings2.findPreference(preferenceKey))
                                != null) {
                    findPreference.setEnabled(z2);
                    abstractPreferenceController.updateState(findPreference);
                    break;
                }
                break;
        }
    }
}
