package com.android.settings.display;

import androidx.preference.Preference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */
class CustomizableLockScreenQuickAffordancesPreferenceController$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Preference f$1;

    public /* synthetic */
    CustomizableLockScreenQuickAffordancesPreferenceController$$ExternalSyntheticLambda0(
            Preference preference, CharSequence charSequence) {
        this.f$1 = preference;
        this.f$0 = charSequence;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((CustomizableLockScreenQuickAffordancesPreferenceController) this.f$0)
                        .lambda$refreshSummary$2(this.f$1);
                break;
            default:
                this.f$1.setSummary((CharSequence) this.f$0);
                break;
        }
    }

    public /* synthetic */
    CustomizableLockScreenQuickAffordancesPreferenceController$$ExternalSyntheticLambda0(
            CustomizableLockScreenQuickAffordancesPreferenceController
                    customizableLockScreenQuickAffordancesPreferenceController,
            Preference preference) {
        this.f$0 = customizableLockScreenQuickAffordancesPreferenceController;
        this.f$1 = preference;
    }
}
