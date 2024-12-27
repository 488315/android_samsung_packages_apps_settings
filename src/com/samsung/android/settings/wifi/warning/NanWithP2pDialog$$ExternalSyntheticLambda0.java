package com.samsung.android.settings.wifi.warning;

import android.content.DialogInterface;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class NanWithP2pDialog$$ExternalSyntheticLambda0
        implements DialogInterface.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NanWithP2pDialog f$0;

    public /* synthetic */ NanWithP2pDialog$$ExternalSyntheticLambda0(
            NanWithP2pDialog nanWithP2pDialog, int i) {
        this.$r8$classId = i;
        this.f$0 = nanWithP2pDialog;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        int i2 = this.$r8$classId;
        NanWithP2pDialog nanWithP2pDialog = this.f$0;
        switch (i2) {
            case 0:
                nanWithP2pDialog.showProgressDialog$1();
                nanWithP2pDialog.sendNotifySelectedBroadcast(true);
                break;
            default:
                nanWithP2pDialog.dismissApDisableDialog();
                nanWithP2pDialog.sendEnableNanBroadcast(false);
                nanWithP2pDialog.sendNotifySelectedBroadcast(false);
                nanWithP2pDialog.mActivity.setResult(0);
                nanWithP2pDialog.mActivity.finish();
                break;
        }
    }
}
