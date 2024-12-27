package com.android.settings.connecteddevice.audiosharing;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */
class AudioSharingCompatibilityPreferenceController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AudioSharingCompatibilityPreferenceController f$0;

    public /* synthetic */ AudioSharingCompatibilityPreferenceController$$ExternalSyntheticLambda0(
            AudioSharingCompatibilityPreferenceController
                    audioSharingCompatibilityPreferenceController,
            int i) {
        this.$r8$classId = i;
        this.f$0 = audioSharingCompatibilityPreferenceController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        AudioSharingCompatibilityPreferenceController
                audioSharingCompatibilityPreferenceController = this.f$0;
        switch (i) {
            case 0:
                audioSharingCompatibilityPreferenceController.lambda$updateEnabled$2(
                        R.string.audio_sharing_stream_compatibility_disabled_description,
                        R.string.audio_sharing_stream_compatibility_description);
                break;
            default:
                audioSharingCompatibilityPreferenceController.lambda$onServiceConnected$0();
                break;
        }
    }
}
