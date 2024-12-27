package com.samsung.android.settings.deviceinfo.legalinfo;

import com.samsung.android.settings.Rune;
import com.sec.android.app.swlpcontract.SWLPContract;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class SamsungEula$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SamsungEula$$ExternalSyntheticLambda0(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                SamsungEula samsungEula = (SamsungEula) obj;
                if (samsungEula.mIsShowAAOnly) {
                    samsungEula.mLegalText =
                            SWLPContract.getStringByUri(
                                    samsungEula.mContext, SWLPContract.URI_AA_GET, false);
                } else {
                    samsungEula.mLegalText =
                            SWLPContract.getStringByUri(
                                    samsungEula.mContext, SWLPContract.URI_EULA_GET, Rune.isUSA());
                }
                samsungEula.mHandlerLoading.sendEmptyMessage(0);
                break;
            default:
                SamsungEula.AnonymousClass1 anonymousClass1 = (SamsungEula.AnonymousClass1) obj;
                anonymousClass1.this$0.mLoadingProgress.setVisibility(8);
                SamsungEula samsungEula2 = anonymousClass1.this$0;
                samsungEula2.mLegalTextView.setText(samsungEula2.mLegalText);
                break;
        }
    }
}
