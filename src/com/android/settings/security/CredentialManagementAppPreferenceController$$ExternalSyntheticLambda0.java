package com.android.settings.security;

import androidx.preference.Preference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */
class CredentialManagementAppPreferenceController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CredentialManagementAppPreferenceController f$0;
    public final /* synthetic */ Preference f$1;

    public /* synthetic */ CredentialManagementAppPreferenceController$$ExternalSyntheticLambda0(
            CredentialManagementAppPreferenceController credentialManagementAppPreferenceController,
            Preference preference,
            int i) {
        this.$r8$classId = i;
        this.f$0 = credentialManagementAppPreferenceController;
        this.f$1 = preference;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$updateState$0(this.f$1);
                break;
            default:
                this.f$0.lambda$updateState$1(this.f$1);
                break;
        }
    }
}
