package com.samsung.android.settings.asbase.widget;

import android.view.View;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class SecVibrationIconMotion$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SecVibrationIconMotion f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ View f$3;
    public final /* synthetic */ View f$4;
    public final /* synthetic */ View f$5;
    public final /* synthetic */ View f$6;
    public final /* synthetic */ View f$7;

    public /* synthetic */ SecVibrationIconMotion$$ExternalSyntheticLambda1(
            SecVibrationIconMotion secVibrationIconMotion,
            int i,
            int i2,
            View view,
            View view2,
            View view3,
            View view4,
            View view5,
            int i3) {
        this.$r8$classId = i3;
        this.f$0 = secVibrationIconMotion;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = view;
        this.f$4 = view2;
        this.f$5 = view3;
        this.f$6 = view4;
        this.f$7 = view5;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SecVibrationIconMotion secVibrationIconMotion = this.f$0;
                int i = this.f$1;
                int i2 = this.f$2;
                View view = this.f$3;
                View view2 = this.f$4;
                View view3 = this.f$5;
                View view4 = this.f$6;
                View view5 = this.f$7;
                secVibrationIconMotion.getClass();
                if (i != 2 && i != 3) {
                    if (i == 0) {
                        secVibrationIconMotion.startMuteAnimation(
                                i2, view, view2, view3, view4, view5, false);
                        break;
                    }
                } else {
                    secVibrationIconMotion.startMidAnimation(
                            i2, i, view, view2, view3, view4, view5, false);
                    break;
                }
                break;
            default:
                SecVibrationIconMotion secVibrationIconMotion2 = this.f$0;
                int i3 = this.f$1;
                int i4 = this.f$2;
                View view6 = this.f$3;
                View view7 = this.f$4;
                View view8 = this.f$5;
                View view9 = this.f$6;
                View view10 = this.f$7;
                secVibrationIconMotion2.getClass();
                if (i3 != 3) {
                    if (i3 == 1 || i3 == 0) {
                        secVibrationIconMotion2.startMinAnimation(
                                i4, i3, view6, view7, view8, view9, view10, false);
                        break;
                    }
                } else {
                    secVibrationIconMotion2.startMaxAnimation(
                            i4, view6, view7, view8, view9, view10, false);
                    break;
                }
        }
    }
}
