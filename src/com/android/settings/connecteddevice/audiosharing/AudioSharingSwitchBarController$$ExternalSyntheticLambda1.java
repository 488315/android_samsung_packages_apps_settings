package com.android.settings.connecteddevice.audiosharing;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AudioSharingSwitchBarController$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AudioSharingSwitchBarController f$0;

    public /* synthetic */ AudioSharingSwitchBarController$$ExternalSyntheticLambda1(
            AudioSharingSwitchBarController audioSharingSwitchBarController, int i) {
        this.$r8$classId = i;
        this.f$0 = audioSharingSwitchBarController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        AudioSharingSwitchBarController audioSharingSwitchBarController = this.f$0;
        switch (i) {
            case 0:
                audioSharingSwitchBarController.lambda$onCheckedChanged$0();
                break;
            default:
                audioSharingSwitchBarController.lambda$updateSwitch$2();
                break;
        }
    }
}
