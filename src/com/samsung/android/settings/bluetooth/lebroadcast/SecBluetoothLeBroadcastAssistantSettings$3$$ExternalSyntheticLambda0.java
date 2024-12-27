package com.samsung.android.settings.bluetooth.lebroadcast;


/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */
class SecBluetoothLeBroadcastAssistantSettings$3$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SecBluetoothLeBroadcastAssistantSettings.AnonymousClass3 f$0;

    public /* synthetic */ SecBluetoothLeBroadcastAssistantSettings$3$$ExternalSyntheticLambda0(
            SecBluetoothLeBroadcastAssistantSettings.AnonymousClass3 anonymousClass3, int i) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        SecBluetoothLeBroadcastAssistantSettings.AnonymousClass3 anonymousClass3 = this.f$0;
        switch (i) {
            case 0:
                anonymousClass3.this$0.finish();
                break;
            default:
                anonymousClass3.getClass();
                int i2 = SecBluetoothLeBroadcastAssistantSettings.$r8$clinit;
                anonymousClass3.this$0.finishActivity$1();
                break;
        }
    }
}
