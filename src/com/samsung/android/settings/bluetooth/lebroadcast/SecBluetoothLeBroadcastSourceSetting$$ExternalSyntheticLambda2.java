package com.samsung.android.settings.bluetooth.lebroadcast;


/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class SecBluetoothLeBroadcastSourceSetting$$ExternalSyntheticLambda2
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SecBluetoothLeBroadcastSourceSetting$$ExternalSyntheticLambda2(
            int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                boolean z = SecBluetoothLeBroadcastSourceSetting.DEBUG;
                ((SecBluetoothLeBroadcastSourceSetting) obj).refreshUI();
                break;
            case 1:
                boolean z2 = SecBluetoothLeBroadcastSourceSetting.DEBUG;
                ((SecBluetoothLeBroadcastSourceSetting) obj).refreshUI();
                break;
            default:
                SecBluetoothLeBroadcastSourceSetting.AnonymousClass1 anonymousClass1 =
                        (SecBluetoothLeBroadcastSourceSetting.AnonymousClass1) obj;
                anonymousClass1.getClass();
                boolean z3 = SecBluetoothLeBroadcastSourceSetting.DEBUG;
                anonymousClass1.this$0.refreshUI();
                break;
        }
    }
}
