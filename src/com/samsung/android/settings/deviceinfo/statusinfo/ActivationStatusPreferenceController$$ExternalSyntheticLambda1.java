package com.samsung.android.settings.deviceinfo.statusinfo;

import org.json.JSONObject;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class ActivationStatusPreferenceController$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ActivationStatusPreferenceController f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ ActivationStatusPreferenceController$$ExternalSyntheticLambda1(
            ActivationStatusPreferenceController activationStatusPreferenceController,
            Object obj,
            int i) {
        this.$r8$classId = i;
        this.f$0 = activationStatusPreferenceController;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$showErrorMessage$2((JSONObject) this.f$1);
                break;
            default:
                this.f$0.lambda$parseActivationDate$1((String) this.f$1);
                break;
        }
    }
}
