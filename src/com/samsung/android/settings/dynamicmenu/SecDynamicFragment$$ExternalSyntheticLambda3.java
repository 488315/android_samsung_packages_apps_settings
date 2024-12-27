package com.samsung.android.settings.dynamicmenu;

import androidx.preference.Preference;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class SecDynamicFragment$$ExternalSyntheticLambda3
        implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ SecDynamicFragment$$ExternalSyntheticLambda3(
            Preference preference, String str) {
        this.f$0 = preference;
        this.f$1 = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((SecDynamicFragment) this.f$0).updatePreferenceUI((ArrayList) this.f$1);
                break;
            default:
                ((Preference) this.f$0).setSummary((String) this.f$1);
                break;
        }
    }

    public /* synthetic */ SecDynamicFragment$$ExternalSyntheticLambda3(
            SecDynamicFragment secDynamicFragment, ArrayList arrayList) {
        this.f$0 = secDynamicFragment;
        this.f$1 = arrayList;
    }
}
