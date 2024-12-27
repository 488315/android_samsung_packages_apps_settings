package com.android.settings.development;

import com.android.settingslib.utils.ThreadUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */
class EnableVerboseVendorLoggingPreferenceController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ EnableVerboseVendorLoggingPreferenceController f$0;

    public /* synthetic */ EnableVerboseVendorLoggingPreferenceController$$ExternalSyntheticLambda1(
            EnableVerboseVendorLoggingPreferenceController
                    enableVerboseVendorLoggingPreferenceController,
            int i) {
        this.$r8$classId = i;
        this.f$0 = enableVerboseVendorLoggingPreferenceController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        EnableVerboseVendorLoggingPreferenceController
                enableVerboseVendorLoggingPreferenceController = this.f$0;
        switch (i) {
            case 0:
                enableVerboseVendorLoggingPreferenceController.setVerboseLoggingEnabled(false);
                break;
            default:
                ThreadUtils.getUiThreadHandler()
                        .post(
                                new EnableVerboseVendorLoggingPreferenceController$$ExternalSyntheticLambda0(
                                        enableVerboseVendorLoggingPreferenceController,
                                        enableVerboseVendorLoggingPreferenceController
                                                .getVerboseLoggingEnabled(),
                                        1));
                break;
        }
    }
}
