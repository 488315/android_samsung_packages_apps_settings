package com.android.settings.biometrics2.ui.viewmodel;

import androidx.lifecycle.LifecycleCoroutineScopeImpl;

import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AutoCredentialViewModel$generateChallenge$1 {
    public final /* synthetic */ long $gkPwHandle;
    public final /* synthetic */ boolean $revokeGkPwHandle;
    public final /* synthetic */ CoroutineScope $scope;
    public final /* synthetic */ AutoCredentialViewModel this$0;

    public AutoCredentialViewModel$generateChallenge$1(
            AutoCredentialViewModel autoCredentialViewModel,
            long j,
            boolean z,
            LifecycleCoroutineScopeImpl lifecycleCoroutineScopeImpl) {
        this.this$0 = autoCredentialViewModel;
        this.$gkPwHandle = j;
        this.$revokeGkPwHandle = z;
        this.$scope = lifecycleCoroutineScopeImpl;
    }
}
