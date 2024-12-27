package com.android.settings.connecteddevice.audiosharing;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */
class AudioSharingCallAudioPreferenceController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AudioSharingCallAudioPreferenceController f$0;

    public /* synthetic */ AudioSharingCallAudioPreferenceController$$ExternalSyntheticLambda1(
            AudioSharingCallAudioPreferenceController audioSharingCallAudioPreferenceController,
            int i) {
        this.$r8$classId = i;
        this.f$0 = audioSharingCallAudioPreferenceController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        AudioSharingCallAudioPreferenceController audioSharingCallAudioPreferenceController =
                this.f$0;
        switch (i) {
            case 0:
                audioSharingCallAudioPreferenceController.lambda$updateSummary$3();
                break;
            default:
                audioSharingCallAudioPreferenceController.updateSummary();
                break;
        }
    }
}
