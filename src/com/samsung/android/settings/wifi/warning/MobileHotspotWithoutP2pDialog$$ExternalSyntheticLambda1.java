package com.samsung.android.settings.wifi.warning;

import android.content.DialogInterface;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class MobileHotspotWithoutP2pDialog$$ExternalSyntheticLambda1
        implements DialogInterface.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MobileHotspotWithoutP2pDialog f$0;

    public /* synthetic */ MobileHotspotWithoutP2pDialog$$ExternalSyntheticLambda1(
            MobileHotspotWithoutP2pDialog mobileHotspotWithoutP2pDialog, int i) {
        this.$r8$classId = i;
        this.f$0 = mobileHotspotWithoutP2pDialog;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        int i2 = this.$r8$classId;
        MobileHotspotWithoutP2pDialog mobileHotspotWithoutP2pDialog = this.f$0;
        switch (i2) {
            case 0:
                mobileHotspotWithoutP2pDialog.sendBroadcastCancelEnablingWifi();
                mobileHotspotWithoutP2pDialog.mActivity.setResult(0);
                mobileHotspotWithoutP2pDialog.mActivity.finish();
                break;
            default:
                mobileHotspotWithoutP2pDialog.disableMobileHotspot();
                break;
        }
    }
}