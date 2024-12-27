package com.android.settings.connecteddevice.audiosharing;

import android.util.Pair;
import android.view.View;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AudioSharingDialogFragment$$ExternalSyntheticLambda1
        implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AudioSharingDialogFragment f$0;

    public /* synthetic */ AudioSharingDialogFragment$$ExternalSyntheticLambda1(
            AudioSharingDialogFragment audioSharingDialogFragment, int i) {
        this.$r8$classId = i;
        this.f$0 = audioSharingDialogFragment;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i = this.$r8$classId;
        AudioSharingDialogFragment audioSharingDialogFragment = this.f$0;
        switch (i) {
            case 0:
                Pair[] pairArr = AudioSharingDialogFragment.sEventData;
                audioSharingDialogFragment.dismissInternal(false, false);
                break;
            case 1:
                Pair[] pairArr2 = AudioSharingDialogFragment.sEventData;
                audioSharingDialogFragment.dismissInternal(false, false);
                break;
            default:
                Pair[] pairArr3 = AudioSharingDialogFragment.sEventData;
                audioSharingDialogFragment.dismissInternal(false, false);
                break;
        }
    }

    public /* synthetic */ AudioSharingDialogFragment$$ExternalSyntheticLambda1(
            AudioSharingDialogFragment audioSharingDialogFragment,
            AudioSharingDeviceItem audioSharingDeviceItem) {
        this.$r8$classId = 0;
        this.f$0 = audioSharingDialogFragment;
    }
}
