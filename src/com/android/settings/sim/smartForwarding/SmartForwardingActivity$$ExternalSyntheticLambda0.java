package com.android.settings.sim.smartForwarding;

import android.content.DialogInterface;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class SmartForwardingActivity$$ExternalSyntheticLambda0
        implements DialogInterface.OnClickListener {
    public final /* synthetic */ int $r8$classId;

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        switch (this.$r8$classId) {
            case 0:
                String str = SmartForwardingActivity.LOG_TAG;
                dialogInterface.dismiss();
                break;
            default:
                dialogInterface.dismiss();
                break;
        }
    }
}
