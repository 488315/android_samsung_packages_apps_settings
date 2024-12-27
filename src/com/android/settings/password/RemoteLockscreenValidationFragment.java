package com.android.settings.password;

import android.app.RemoteLockscreenValidationResult;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.android.internal.widget.LockscreenCredential;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RemoteLockscreenValidationFragment extends Fragment {
    public String mErrorMessage;
    public Handler mHandler;
    public boolean mIsInProgress;
    public Listener mListener;
    public LockscreenCredential mLockscreenCredential;
    public RemoteLockscreenValidationResult mResult;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Listener {
        void onRemoteLockscreenValidationResult(
                RemoteLockscreenValidationResult remoteLockscreenValidationResult);
    }

    public final void clearLockscreenCredential() {
        LockscreenCredential lockscreenCredential = this.mLockscreenCredential;
        if (lockscreenCredential != null) {
            lockscreenCredential.zeroize();
            this.mLockscreenCredential = null;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRetainInstance(true);
    }

    @Override // androidx.fragment.app.Fragment
    public final void onDestroy() {
        clearLockscreenCredential();
        if (this.mResult != null && this.mErrorMessage != null) {
            Log.w(
                    "RemoteLockscreenValidationFragment",
                    "Unprocessed remote lockscreen validation result");
        }
        super.onDestroy();
    }

    public final void setListener(Listener listener, Handler handler) {
        if (this.mListener == listener) {
            return;
        }
        this.mListener = listener;
        this.mHandler = handler;
        if (this.mResult != null) {
            if (handler != null) {
                handler.post(
                        new RemoteLockscreenValidationFragment$$ExternalSyntheticLambda0(this, 0));
            }
        } else {
            if (this.mErrorMessage == null || handler == null) {
                return;
            }
            handler.post(new RemoteLockscreenValidationFragment$$ExternalSyntheticLambda0(this, 1));
        }
    }
}
