package com.android.settings.biometrics.fingerprint2.ui.settings.fragment;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.preference.Preference;

import com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.EnrollAdditionalFingerprint;
import com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsNavigationViewModel;

import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FingerprintSettingsV2Fragment$confirmDeviceResultListener$1
        implements ActivityResultCallback, Preference.OnPreferenceClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FingerprintSettingsV2Fragment this$0;

    public /* synthetic */ FingerprintSettingsV2Fragment$confirmDeviceResultListener$1(
            FingerprintSettingsV2Fragment fingerprintSettingsV2Fragment, int i) {
        this.$r8$classId = i;
        this.this$0 = fingerprintSettingsV2Fragment;
    }

    @Override // androidx.activity.result.ActivityResultCallback
    public void onActivityResult(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ActivityResult activityResult = (ActivityResult) obj;
                this.this$0.onConfirmDevice(activityResult.mResultCode, activityResult.mData);
                break;
            case 1:
                FingerprintSettingsV2Fragment fingerprintSettingsV2Fragment = this.this$0;
                BuildersKt.launch$default(
                        LifecycleOwnerKt.getLifecycleScope(fingerprintSettingsV2Fragment),
                        null,
                        null,
                        new FingerprintSettingsV2Fragment$launchAdditionalFingerprintListener$1$1(
                                (ActivityResult) obj, fingerprintSettingsV2Fragment, null),
                        3);
                break;
            default:
                FingerprintSettingsV2Fragment fingerprintSettingsV2Fragment2 = this.this$0;
                BuildersKt.launch$default(
                        LifecycleOwnerKt.getLifecycleScope(fingerprintSettingsV2Fragment2),
                        null,
                        null,
                        new FingerprintSettingsV2Fragment$launchFirstEnrollmentListener$1$1(
                                (ActivityResult) obj, fingerprintSettingsV2Fragment2, null),
                        3);
                break;
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public boolean onPreferenceClick(Preference it) {
        StateFlowImpl stateFlowImpl;
        Object value;
        Intrinsics.checkNotNullParameter(it, "it");
        FingerprintSettingsNavigationViewModel fingerprintSettingsNavigationViewModel =
                this.this$0.navigationViewModel;
        if (fingerprintSettingsNavigationViewModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("navigationViewModel");
            throw null;
        }
        do {
            stateFlowImpl = fingerprintSettingsNavigationViewModel._nextStep;
            value = stateFlowImpl.getValue();
        } while (!stateFlowImpl.compareAndSet(
                value,
                new EnrollAdditionalFingerprint(
                        fingerprintSettingsNavigationViewModel.userId,
                        fingerprintSettingsNavigationViewModel.token)));
        return true;
    }
}
