package com.android.settings.connecteddevice.audiosharing;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AudioSharingNamePreferenceController$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AudioSharingNamePreferenceController f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ AudioSharingNamePreferenceController$$ExternalSyntheticLambda0(
            AudioSharingNamePreferenceController audioSharingNamePreferenceController,
            Object obj,
            int i) {
        this.$r8$classId = i;
        this.f$0 = audioSharingNamePreferenceController;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$updateBroadcastName$1((String) this.f$1);
                break;
            default:
                this.f$0.lambda$onPreferenceChange$0(this.f$1);
                break;
        }
    }
}
