package com.samsung.android.settings.bluetooth.lebroadcast;

import android.content.DialogInterface;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */
class SecBluetoothLeBroadcastSourceInfoController$$ExternalSyntheticLambda1
        implements DialogInterface.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SecBluetoothLeBroadcastSourceInfoController f$0;
    public final /* synthetic */ String f$1;

    public /* synthetic */ SecBluetoothLeBroadcastSourceInfoController$$ExternalSyntheticLambda1(
            SecBluetoothLeBroadcastSourceInfoController secBluetoothLeBroadcastSourceInfoController,
            String str,
            int i) {
        this.$r8$classId = i;
        this.f$0 = secBluetoothLeBroadcastSourceInfoController;
        this.f$1 = str;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$launchBroadcastCodeDialog$0(this.f$1, dialogInterface, i);
                break;
            case 1:
                this.f$0.lambda$launchBroadcastCodeDialog$1(this.f$1, dialogInterface, i);
                break;
            case 2:
                this.f$0.lambda$launchBroadcastCodeDialog$2(this.f$1, dialogInterface, i);
                break;
            case 3:
                this.f$0.lambda$launchBroadcastNameDialog$3(this.f$1, dialogInterface, i);
                break;
            default:
                this.f$0.lambda$launchBroadcastNameDialog$4(this.f$1, dialogInterface, i);
                break;
        }
    }
}
