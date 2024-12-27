package com.android.settings.connecteddevice.audiosharing;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AudioSharingBasePreferenceController$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AudioSharingBasePreferenceController f$0;

    public /* synthetic */ AudioSharingBasePreferenceController$$ExternalSyntheticLambda0(
            AudioSharingBasePreferenceController audioSharingBasePreferenceController, int i) {
        this.$r8$classId = i;
        this.f$0 = audioSharingBasePreferenceController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        AudioSharingBasePreferenceController audioSharingBasePreferenceController = this.f$0;
        switch (i) {
            case 0:
                audioSharingBasePreferenceController.lambda$updateVisibility$0();
                break;
            default:
                audioSharingBasePreferenceController.lambda$updateVisibility$2();
                break;
        }
    }
}
