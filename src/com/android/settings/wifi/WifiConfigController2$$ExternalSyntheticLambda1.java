package com.android.settings.wifi;


/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiConfigController2$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                WifiConfigController2 wifiConfigController2 = (WifiConfigController2) obj;
                wifiConfigController2.showWarningMessagesIfAppropriate();
                wifiConfigController2.enableSubmitIfAppropriate();
                break;
            default:
                WifiConfigController2.AnonymousClass2 anonymousClass2 =
                        (WifiConfigController2.AnonymousClass2) obj;
                anonymousClass2.this$0.showWarningMessagesIfAppropriate();
                anonymousClass2.this$0.enableSubmitIfAppropriate();
                break;
        }
    }
}
