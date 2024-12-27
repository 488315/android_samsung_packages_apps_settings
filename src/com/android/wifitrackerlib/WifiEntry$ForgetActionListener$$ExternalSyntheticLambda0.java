package com.android.wifitrackerlib;


/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiEntry$ForgetActionListener$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WifiEntry.ForgetActionListener f$0;

    public /* synthetic */ WifiEntry$ForgetActionListener$$ExternalSyntheticLambda0(
            WifiEntry.ForgetActionListener forgetActionListener, int i) {
        this.$r8$classId = i;
        this.f$0 = forgetActionListener;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        WifiEntry.ForgetActionListener forgetActionListener = this.f$0;
        switch (i) {
            case 0:
                WifiEntry.ForgetCallback forgetCallback =
                        forgetActionListener.this$0.mForgetCallback;
                if (forgetCallback != null) {
                    forgetCallback.onForgetResult(1);
                    break;
                }
                break;
            default:
                WifiEntry.ForgetCallback forgetCallback2 =
                        forgetActionListener.this$0.mForgetCallback;
                if (forgetCallback2 != null) {
                    forgetCallback2.onForgetResult(0);
                    break;
                }
                break;
        }
    }
}
