package com.android.settings.applications.credentials;


/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class CredentialManagerPreferenceController$6$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CredentialManagerPreferenceController.AnonymousClass6 f$0;

    public /* synthetic */ CredentialManagerPreferenceController$6$$ExternalSyntheticLambda0(
            CredentialManagerPreferenceController.AnonymousClass6 anonymousClass6, int i) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass6;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        CredentialManagerPreferenceController.AnonymousClass6 anonymousClass6 = this.f$0;
        switch (i) {
            case 0:
                CredentialManagerPreferenceController.this.updateFromExternal();
                break;
            case 1:
                CredentialManagerPreferenceController.this.updateFromExternal();
                break;
            default:
                CredentialManagerPreferenceController.this.updateFromExternal();
                break;
        }
    }
}
