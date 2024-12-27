package com.android.settings.homepage.contextualcards;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import androidx.slice.SliceViewManager$SliceCallback;
import androidx.slice.SliceViewManagerWrapper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class EligibleCardChecker$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SliceViewManagerWrapper f$0;
    public final /* synthetic */ Uri f$1;
    public final /* synthetic */ SliceViewManager$SliceCallback f$2;

    public /* synthetic */ EligibleCardChecker$$ExternalSyntheticLambda1(
            SliceViewManagerWrapper sliceViewManagerWrapper,
            Uri uri,
            EligibleCardChecker$$ExternalSyntheticLambda0
                    eligibleCardChecker$$ExternalSyntheticLambda0,
            int i) {
        this.$r8$classId = i;
        this.f$0 = sliceViewManagerWrapper;
        this.f$1 = uri;
        this.f$2 = eligibleCardChecker$$ExternalSyntheticLambda0;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                AsyncTask.execute(
                        new EligibleCardChecker$$ExternalSyntheticLambda1(
                                this.f$0,
                                this.f$1,
                                (EligibleCardChecker$$ExternalSyntheticLambda0) this.f$2,
                                1));
                break;
            default:
                try {
                    this.f$0.unregisterSliceCallback(this.f$1, this.f$2);
                    break;
                } catch (SecurityException e) {
                    Log.d("EligibleCardChecker", "No permission currently: " + e);
                }
        }
    }
}
