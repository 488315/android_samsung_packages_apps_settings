package com.samsung.android.settings.wifi.develop.diagnosis;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class NetworkDiagnosisSettings$$ExternalSyntheticLambda5
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NetworkDiagnosisSettings f$0;

    public /* synthetic */ NetworkDiagnosisSettings$$ExternalSyntheticLambda5(
            NetworkDiagnosisSettings networkDiagnosisSettings, int i) {
        this.$r8$classId = i;
        this.f$0 = networkDiagnosisSettings;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        NetworkDiagnosisSettings networkDiagnosisSettings = this.f$0;
        switch (i) {
            case 0:
                networkDiagnosisSettings.mStepView.append(networkDiagnosisSettings.result + "\n");
                break;
            case 1:
                networkDiagnosisSettings.mStepView.append(networkDiagnosisSettings.result + "\n");
                break;
            case 2:
                networkDiagnosisSettings.mStepView.append(networkDiagnosisSettings.result + "\n");
                break;
            default:
                networkDiagnosisSettings.mStepView.append(networkDiagnosisSettings.result + "\n");
                break;
        }
    }
}
