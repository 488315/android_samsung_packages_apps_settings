package com.samsung.android.settings.wifi.warning;

import android.content.DialogInterface;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class MobileHotspotWithNanDialog$$ExternalSyntheticLambda1
        implements DialogInterface.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MobileHotspotWithNanDialog f$0;

    public /* synthetic */ MobileHotspotWithNanDialog$$ExternalSyntheticLambda1(
            MobileHotspotWithNanDialog mobileHotspotWithNanDialog, int i) {
        this.$r8$classId = i;
        this.f$0 = mobileHotspotWithNanDialog;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        int i2 = this.$r8$classId;
        MobileHotspotWithNanDialog mobileHotspotWithNanDialog = this.f$0;
        switch (i2) {
            case 0:
                mobileHotspotWithNanDialog.dismissApDisableDialog();
                mobileHotspotWithNanDialog.sendEnableNanBroadcast(false);
                mobileHotspotWithNanDialog.sendNotifySelectedBroadcast(false);
                mobileHotspotWithNanDialog.mActivity.setResult(0);
                mobileHotspotWithNanDialog.mActivity.finish();
                break;
            default:
                mobileHotspotWithNanDialog.disableMobileHotspot();
                mobileHotspotWithNanDialog.sendNotifySelectedBroadcast(true);
                break;
        }
    }
}
