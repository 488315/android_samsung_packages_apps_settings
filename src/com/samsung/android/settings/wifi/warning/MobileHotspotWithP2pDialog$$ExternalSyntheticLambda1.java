package com.samsung.android.settings.wifi.warning;

import android.content.DialogInterface;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class MobileHotspotWithP2pDialog$$ExternalSyntheticLambda1
        implements DialogInterface.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MobileHotspotWithP2pDialog f$0;

    public /* synthetic */ MobileHotspotWithP2pDialog$$ExternalSyntheticLambda1(
            MobileHotspotWithP2pDialog mobileHotspotWithP2pDialog, int i) {
        this.$r8$classId = i;
        this.f$0 = mobileHotspotWithP2pDialog;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        int i2 = this.$r8$classId;
        MobileHotspotWithP2pDialog mobileHotspotWithP2pDialog = this.f$0;
        switch (i2) {
            case 0:
                mobileHotspotWithP2pDialog.dismissApDisableDialog();
                mobileHotspotWithP2pDialog.sendNotifySelectedBroadcast(false);
                mobileHotspotWithP2pDialog.mActivity.setResult(0);
                mobileHotspotWithP2pDialog.mActivity.finish();
                break;
            default:
                mobileHotspotWithP2pDialog.disableMobileHotspot();
                mobileHotspotWithP2pDialog.sendNotifySelectedBroadcast(true);
                break;
        }
    }
}
