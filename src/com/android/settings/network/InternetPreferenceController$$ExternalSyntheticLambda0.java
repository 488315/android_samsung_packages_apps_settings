package com.android.settings.network;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class InternetPreferenceController$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ InternetPreferenceController f$0;

    public /* synthetic */ InternetPreferenceController$$ExternalSyntheticLambda0(
            InternetPreferenceController internetPreferenceController, int i) {
        this.$r8$classId = i;
        this.f$0 = internetPreferenceController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        InternetPreferenceController internetPreferenceController = this.f$0;
        switch (i) {
            case 0:
                internetPreferenceController.updateState(internetPreferenceController.mPreference);
                break;
            default:
                internetPreferenceController.updateState(internetPreferenceController.mPreference);
                break;
        }
    }
}
