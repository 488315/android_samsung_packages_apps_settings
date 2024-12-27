package com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment.education;

import android.util.Log;
import android.view.View;

import com.android.settings.biometrics.fingerprint2.ui.enrollment.viewmodel.FingerprintAction;

import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SfpsEnrollFindSensorFragment$setupPrimaryButton$1
        implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SfpsEnrollFindSensorFragment this$0;

    public /* synthetic */ SfpsEnrollFindSensorFragment$setupPrimaryButton$1(
            SfpsEnrollFindSensorFragment sfpsEnrollFindSensorFragment, int i) {
        this.$r8$classId = i;
        this.this$0 = sfpsEnrollFindSensorFragment;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        Object value;
        StateFlowImpl stateFlowImpl = null;
        switch (this.$r8$classId) {
            case 0:
                Log.d("SfpsEnrollFindSensor", "onStartButtonClick");
                SfpsEnrollFindSensorFragment.access$getViewModel(this.this$0).getClass();
                do {
                    value = stateFlowImpl.getValue();
                    ((Boolean) value).getClass();
                } while (!stateFlowImpl.compareAndSet(value, Boolean.FALSE));
                FingerprintAction[] fingerprintActionArr = FingerprintAction.$VALUES;
                throw null;
            default:
                SfpsEnrollFindSensorFragment.access$getViewModel(this.this$0);
                FingerprintAction[] fingerprintActionArr2 = FingerprintAction.$VALUES;
                throw null;
        }
    }
}
