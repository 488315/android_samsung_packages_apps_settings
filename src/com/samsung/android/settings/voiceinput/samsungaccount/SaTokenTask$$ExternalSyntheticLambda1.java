package com.samsung.android.settings.voiceinput.samsungaccount;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class SaTokenTask$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SaTokenTask$$ExternalSyntheticLambda1(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                SaTokenTask.$r8$lambda$r9KO_dVQnuooyqB30BYIJXWQAtw((SaTokenTask) obj);
                break;
            default:
                SaTokenTask saTokenTask = SaTokenTask.this;
                int i2 = SaTokenTask.$r8$clinit;
                saTokenTask.requestToken();
                break;
        }
    }
}
