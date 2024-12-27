package com.samsung.android.settings.wifi.managenetwork;

import java.util.function.IntUnaryOperator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class SavedWifiEntryLoggingUtils$$ExternalSyntheticLambda0
        implements IntUnaryOperator {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ SavedWifiEntryLoggingUtils$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.IntUnaryOperator
    public final int applyAsInt(int i) {
        int lambda$getSecurityTypeForStatusLogging$0;
        int lambda$getSemConfigTypeForStatusLogging$1;
        switch (this.$r8$classId) {
            case 0:
                lambda$getSecurityTypeForStatusLogging$0 =
                        SavedWifiEntryLoggingUtils.lambda$getSecurityTypeForStatusLogging$0(i);
                return lambda$getSecurityTypeForStatusLogging$0;
            default:
                lambda$getSemConfigTypeForStatusLogging$1 =
                        SavedWifiEntryLoggingUtils.lambda$getSemConfigTypeForStatusLogging$1(i);
                return lambda$getSemConfigTypeForStatusLogging$1;
        }
    }
}
