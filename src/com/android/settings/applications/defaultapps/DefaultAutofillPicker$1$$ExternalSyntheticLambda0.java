package com.android.settings.applications.defaultapps;

import android.content.Intent;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class DefaultAutofillPicker$1$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DefaultAutofillPicker.AnonymousClass1 f$0;

    public /* synthetic */ DefaultAutofillPicker$1$$ExternalSyntheticLambda0(
            DefaultAutofillPicker.AnonymousClass1 anonymousClass1, int i) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass1;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        DefaultAutofillPicker.AnonymousClass1 anonymousClass1 = this.f$0;
        switch (i) {
            case 0:
                DefaultAutofillPicker defaultAutofillPicker = anonymousClass1.this$0;
                Intent intent = DefaultAutofillPicker.AUTOFILL_PROBE;
                defaultAutofillPicker.update$6$1();
                break;
            case 1:
                DefaultAutofillPicker defaultAutofillPicker2 = anonymousClass1.this$0;
                Intent intent2 = DefaultAutofillPicker.AUTOFILL_PROBE;
                defaultAutofillPicker2.update$6$1();
                break;
            default:
                DefaultAutofillPicker defaultAutofillPicker3 = anonymousClass1.this$0;
                Intent intent3 = DefaultAutofillPicker.AUTOFILL_PROBE;
                defaultAutofillPicker3.update$6$1();
                break;
        }
    }
}
