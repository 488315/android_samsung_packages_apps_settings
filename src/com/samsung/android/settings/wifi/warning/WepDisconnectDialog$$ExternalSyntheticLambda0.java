package com.samsung.android.settings.wifi.warning;

import android.content.DialogInterface;

import com.samsung.android.wifitrackerlib.SemWifiEntryFlags;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class WepDisconnectDialog$$ExternalSyntheticLambda0
        implements DialogInterface.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WepDisconnectDialog f$0;

    public /* synthetic */ WepDisconnectDialog$$ExternalSyntheticLambda0(
            WepDisconnectDialog wepDisconnectDialog, int i) {
        this.$r8$classId = i;
        this.f$0 = wepDisconnectDialog;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        int i2 = this.$r8$classId;
        WepDisconnectDialog wepDisconnectDialog = this.f$0;
        switch (i2) {
            case 0:
                wepDisconnectDialog.mWifiManager.setWepAllowed(false);
                SemWifiEntryFlags.isWepAllowed = -1;
                wepDisconnectDialog.mActivity.setResult(0);
                wepDisconnectDialog.mActivity.finish();
                break;
            default:
                wepDisconnectDialog.mActivity.setResult(0);
                wepDisconnectDialog.mActivity.finish();
                break;
        }
    }
}
