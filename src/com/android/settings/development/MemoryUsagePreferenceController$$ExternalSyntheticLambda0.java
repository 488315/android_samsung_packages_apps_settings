package com.android.settings.development;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class MemoryUsagePreferenceController$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MemoryUsagePreferenceController f$0;

    public /* synthetic */ MemoryUsagePreferenceController$$ExternalSyntheticLambda0(
            MemoryUsagePreferenceController memoryUsagePreferenceController, int i) {
        this.$r8$classId = i;
        this.f$0 = memoryUsagePreferenceController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        MemoryUsagePreferenceController memoryUsagePreferenceController = this.f$0;
        switch (i) {
            case 0:
                MemoryUsagePreferenceController.$r8$lambda$bttyscuSUXMpljDiTNR0ymFc0qI(
                        memoryUsagePreferenceController);
                break;
            default:
                memoryUsagePreferenceController.setDuration();
                break;
        }
    }
}
