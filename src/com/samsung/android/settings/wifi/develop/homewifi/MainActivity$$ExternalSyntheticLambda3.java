package com.samsung.android.settings.wifi.develop.homewifi;

import android.content.DialogInterface;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class MainActivity$$ExternalSyntheticLambda3
        implements DialogInterface.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MainActivity f$0;

    public /* synthetic */ MainActivity$$ExternalSyntheticLambda3(
            MainActivity mainActivity, int i) {
        this.$r8$classId = i;
        this.f$0 = mainActivity;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        int i2 = this.$r8$classId;
        MainActivity mainActivity = this.f$0;
        switch (i2) {
            case 0:
                int i3 = MainActivity.$r8$clinit;
                mainActivity.finish();
                break;
            case 1:
                SignalMeasureFragment signalMeasureFragment = mainActivity.mSignalMeasureFragment;
                if (signalMeasureFragment != null) {
                    signalMeasureFragment.startScan();
                    break;
                }
                break;
            default:
                int i4 = MainActivity.$r8$clinit;
                mainActivity.finish();
                break;
        }
    }
}
