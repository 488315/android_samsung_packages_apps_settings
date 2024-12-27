package com.android.settings.applications.credentials;

import android.os.Handler;
import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class DefaultCombinedPicker$1$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DefaultCombinedPicker.AnonymousClass1 f$0;

    public /* synthetic */ DefaultCombinedPicker$1$$ExternalSyntheticLambda0(
            DefaultCombinedPicker.AnonymousClass1 anonymousClass1, int i) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass1;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        DefaultCombinedPicker.AnonymousClass1 anonymousClass1 = this.f$0;
        switch (i) {
            case 0:
                if (DefaultCombinedPicker.this.getContext() != null) {
                    DefaultCombinedPicker defaultCombinedPicker = DefaultCombinedPicker.this;
                    Handler handler = DefaultCombinedPicker.sMainHandler;
                    defaultCombinedPicker.update$7$1();
                    break;
                } else {
                    Log.w("DefaultCombinedPicker", "context is null");
                    break;
                }
            case 1:
                if (DefaultCombinedPicker.this.getContext() != null) {
                    DefaultCombinedPicker defaultCombinedPicker2 = DefaultCombinedPicker.this;
                    Handler handler2 = DefaultCombinedPicker.sMainHandler;
                    defaultCombinedPicker2.update$7$1();
                    break;
                } else {
                    Log.w("DefaultCombinedPicker", "context is null");
                    break;
                }
            default:
                if (DefaultCombinedPicker.this.getContext() != null) {
                    DefaultCombinedPicker defaultCombinedPicker3 = DefaultCombinedPicker.this;
                    Handler handler3 = DefaultCombinedPicker.sMainHandler;
                    defaultCombinedPicker3.update$7$1();
                    break;
                } else {
                    Log.w("DefaultCombinedPicker", "context is null");
                    break;
                }
        }
    }
}
