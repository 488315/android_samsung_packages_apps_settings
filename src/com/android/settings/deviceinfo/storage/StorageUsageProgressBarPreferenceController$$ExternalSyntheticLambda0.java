package com.android.settings.deviceinfo.storage;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */
class StorageUsageProgressBarPreferenceController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ StorageUsageProgressBarPreferenceController f$0;

    public /* synthetic */ StorageUsageProgressBarPreferenceController$$ExternalSyntheticLambda0(
            StorageUsageProgressBarPreferenceController storageUsageProgressBarPreferenceController,
            int i) {
        this.$r8$classId = i;
        this.f$0 = storageUsageProgressBarPreferenceController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        StorageUsageProgressBarPreferenceController storageUsageProgressBarPreferenceController =
                this.f$0;
        switch (i) {
            case 0:
                storageUsageProgressBarPreferenceController.lambda$getStorageStatsAndUpdateUi$0();
                break;
            default:
                storageUsageProgressBarPreferenceController.lambda$getStorageStatsAndUpdateUi$1();
                break;
        }
    }
}
