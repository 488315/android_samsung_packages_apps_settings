package com.android.settings.biometrics.fingerprint2.ui.settings.binder;

import androidx.lifecycle.LifecycleCoroutineScopeImpl;

import com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsNavigationViewModel;
import com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel;

import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.Dispatchers;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class FingerprintSettingsViewBinder {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface FingerprintView {
        void finish();
    }

    public static void bind(
            FingerprintView view,
            FingerprintSettingsViewModel fingerprintSettingsViewModel,
            FingerprintSettingsNavigationViewModel navigationViewModel,
            LifecycleCoroutineScopeImpl lifecycleCoroutineScopeImpl) {
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(navigationViewModel, "navigationViewModel");
        BuildersKt.launch$default(
                lifecycleCoroutineScopeImpl,
                null,
                null,
                new FingerprintSettingsViewBinder$bind$1(view, fingerprintSettingsViewModel, null),
                3);
        BuildersKt.launch$default(
                lifecycleCoroutineScopeImpl,
                null,
                null,
                new FingerprintSettingsViewBinder$bind$2(view, fingerprintSettingsViewModel, null),
                3);
        BuildersKt.launch$default(
                lifecycleCoroutineScopeImpl,
                null,
                null,
                new FingerprintSettingsViewBinder$bind$3(view, fingerprintSettingsViewModel, null),
                3);
        BuildersKt.launch$default(
                lifecycleCoroutineScopeImpl,
                null,
                null,
                new FingerprintSettingsViewBinder$bind$4(view, fingerprintSettingsViewModel, null),
                3);
        BuildersKt.launch$default(
                lifecycleCoroutineScopeImpl,
                null,
                null,
                new FingerprintSettingsViewBinder$bind$5(view, fingerprintSettingsViewModel, null),
                3);
        BuildersKt.launch$default(
                lifecycleCoroutineScopeImpl,
                Dispatchers.Default,
                null,
                new FingerprintSettingsViewBinder$bind$6(navigationViewModel, view, null),
                2);
    }
}
