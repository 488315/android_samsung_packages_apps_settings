package com.android.settings.notification;

import androidx.preference.Preference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class RingtonePreferenceControllerBase$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Preference f$1;

    public /* synthetic */ RingtonePreferenceControllerBase$$ExternalSyntheticLambda0(
            Preference preference, CharSequence charSequence) {
        this.f$1 = preference;
        this.f$0 = charSequence;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                RingtonePreferenceControllerBase.$r8$lambda$wz34Fh1d7xtSUshSAMQdGfTqG5g(
                        (RingtonePreferenceControllerBase) this.f$0, this.f$1);
                break;
            default:
                this.f$1.setSummary((CharSequence) this.f$0);
                break;
        }
    }

    public /* synthetic */ RingtonePreferenceControllerBase$$ExternalSyntheticLambda0(
            RingtonePreferenceControllerBase ringtonePreferenceControllerBase,
            Preference preference) {
        this.f$0 = ringtonePreferenceControllerBase;
        this.f$1 = preference;
    }
}
