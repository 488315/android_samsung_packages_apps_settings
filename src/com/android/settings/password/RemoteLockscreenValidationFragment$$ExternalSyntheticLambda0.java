package com.android.settings.password;

import android.app.RemoteLockscreenValidationResult;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class RemoteLockscreenValidationFragment$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ RemoteLockscreenValidationFragment f$0;

    public /* synthetic */ RemoteLockscreenValidationFragment$$ExternalSyntheticLambda0(
            RemoteLockscreenValidationFragment remoteLockscreenValidationFragment, int i) {
        this.$r8$classId = i;
        this.f$0 = remoteLockscreenValidationFragment;
    }

    @Override // java.lang.Runnable
    public final void run() {
        RemoteLockscreenValidationResult remoteLockscreenValidationResult;
        String str;
        int i = this.$r8$classId;
        RemoteLockscreenValidationFragment remoteLockscreenValidationFragment = this.f$0;
        switch (i) {
            case 0:
                RemoteLockscreenValidationFragment.Listener listener =
                        remoteLockscreenValidationFragment.mListener;
                if (listener != null
                        && (remoteLockscreenValidationResult =
                                        remoteLockscreenValidationFragment.mResult)
                                != null) {
                    remoteLockscreenValidationFragment.mIsInProgress = false;
                    listener.onRemoteLockscreenValidationResult(remoteLockscreenValidationResult);
                    remoteLockscreenValidationFragment.mResult = null;
                    break;
                }
                break;
            default:
                Object obj = remoteLockscreenValidationFragment.mListener;
                if (obj != null
                        && (str = remoteLockscreenValidationFragment.mErrorMessage) != null) {
                    remoteLockscreenValidationFragment.mIsInProgress = false;
                    ((ConfirmDeviceCredentialBaseFragment) obj)
                            .onRemoteLockscreenValidationFailure(
                                    "Remote lockscreen validation failed: ".concat(str));
                    remoteLockscreenValidationFragment.mErrorMessage = null;
                    break;
                }
                break;
        }
    }
}
