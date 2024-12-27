package com.android.settings.network.telephony;

import androidx.preference.Preference;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class DefaultSubscriptionController$$ExternalSyntheticLambda0
        implements Preference.SummaryProvider {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DefaultSubscriptionController$$ExternalSyntheticLambda0(
            int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // androidx.preference.Preference.SummaryProvider
    public final CharSequence provideSummary(Preference preference) {
        CharSequence lambda$updateEntries$1;
        CharSequence lambda$refreshSummary$0;
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                lambda$updateEntries$1 =
                        DefaultSubscriptionController.lambda$updateEntries$1(
                                (List) obj, preference);
                return lambda$updateEntries$1;
            default:
                lambda$refreshSummary$0 =
                        ((DefaultSubscriptionController) obj).lambda$refreshSummary$0(preference);
                return lambda$refreshSummary$0;
        }
    }
}
