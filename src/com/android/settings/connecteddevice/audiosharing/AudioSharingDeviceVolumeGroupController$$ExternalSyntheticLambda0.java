package com.android.settings.connecteddevice.audiosharing;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AudioSharingDeviceVolumeGroupController$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AudioSharingDeviceVolumePreference f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ AudioSharingDeviceVolumeGroupController$$ExternalSyntheticLambda0(
            AudioSharingDeviceVolumePreference audioSharingDeviceVolumePreference, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = audioSharingDeviceVolumePreference;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.setProgress(this.f$1, true);
                break;
            default:
                this.f$0.setProgress(this.f$1, true);
                break;
        }
    }
}
