package com.android.settingslib.wifi;


/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiStatusTracker$2$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WifiStatusTracker.AnonymousClass1 f$0;

    public /* synthetic */ WifiStatusTracker$2$$ExternalSyntheticLambda0(
            WifiStatusTracker.AnonymousClass1 anonymousClass1, int i) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass1;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        WifiStatusTracker.AnonymousClass1 anonymousClass1 = this.f$0;
        switch (i) {
            case 0:
                anonymousClass1.this$0.mCallback.run();
                break;
            default:
                anonymousClass1.this$0.mCallback.run();
                break;
        }
    }
}
