package com.android.settings.fuelgauge.batteryusage;

import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class DataProcessManager$6$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Map f$1;

    public /* synthetic */ DataProcessManager$6$$ExternalSyntheticLambda0(
            DataProcessManager.AnonymousClass2 anonymousClass2, Map map) {
        this.f$0 = anonymousClass2;
        this.f$1 = map;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                DataProcessManager.AnonymousClass2 anonymousClass2 =
                        (DataProcessManager.AnonymousClass2) this.f$0;
                PowerUsageAdvanced.m938$$Nest$monBatteryDiffDataMapUpdate(
                        anonymousClass2.this$0.mCallbackFunction.f$0, this.f$1);
                break;
            default:
                DataProcessManager.AnonymousClass2 anonymousClass22 =
                        (DataProcessManager.AnonymousClass2) this.f$0;
                PowerUsageAdvanced.m938$$Nest$monBatteryDiffDataMapUpdate(
                        anonymousClass22.this$0.mCallbackFunction.f$0, this.f$1);
                break;
        }
    }

    public /* synthetic */ DataProcessManager$6$$ExternalSyntheticLambda0(
            DataProcessManager.AnonymousClass2 anonymousClass2, Map map, byte b) {
        this.f$0 = anonymousClass2;
        this.f$1 = map;
    }
}
