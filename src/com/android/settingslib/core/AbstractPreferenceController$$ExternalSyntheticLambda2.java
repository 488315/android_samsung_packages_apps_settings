package com.android.settingslib.core;

import androidx.preference.Preference;

import com.android.settingslib.utils.ThreadUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AbstractPreferenceController$$ExternalSyntheticLambda2
        implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Preference f$1;

    public /* synthetic */ AbstractPreferenceController$$ExternalSyntheticLambda2(
            Preference preference, CharSequence charSequence) {
        this.f$1 = preference;
        this.f$0 = charSequence;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                AbstractPreferenceController abstractPreferenceController =
                        (AbstractPreferenceController) this.f$0;
                Preference preference = this.f$1;
                if (preference == null) {
                    abstractPreferenceController.getClass();
                    break;
                } else {
                    CharSequence summary = abstractPreferenceController.getSummary();
                    if (summary != null) {
                        ThreadUtils.postOnMainThread(
                                new AbstractPreferenceController$$ExternalSyntheticLambda2(
                                        preference, summary));
                        break;
                    }
                }
                break;
            default:
                this.f$1.setSummary((CharSequence) this.f$0);
                break;
        }
    }

    public /* synthetic */ AbstractPreferenceController$$ExternalSyntheticLambda2(
            AbstractPreferenceController abstractPreferenceController, Preference preference) {
        this.f$0 = abstractPreferenceController;
        this.f$1 = preference;
    }
}
