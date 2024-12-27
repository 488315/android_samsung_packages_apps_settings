package com.samsung.android.settings.wifi.warning;

import android.content.DialogInterface;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class P2pWithNanDialog$$ExternalSyntheticLambda0
        implements DialogInterface.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ P2pWithNanDialog f$0;

    public /* synthetic */ P2pWithNanDialog$$ExternalSyntheticLambda0(
            P2pWithNanDialog p2pWithNanDialog, int i) {
        this.$r8$classId = i;
        this.f$0 = p2pWithNanDialog;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        int i2 = this.$r8$classId;
        P2pWithNanDialog p2pWithNanDialog = this.f$0;
        switch (i2) {
            case 0:
                p2pWithNanDialog.showProgressDialog$1();
                p2pWithNanDialog.sendNotifySelectedBroadcast(true);
                break;
            default:
                p2pWithNanDialog.dismissApDisableDialog();
                p2pWithNanDialog.sendEnableNanBroadcast(false);
                p2pWithNanDialog.sendNotifySelectedBroadcast(false);
                p2pWithNanDialog.mActivity.setResult(0);
                p2pWithNanDialog.mActivity.finish();
                break;
        }
    }
}
