package com.android.settings.network;

import android.telephony.TelephonyCallback;
import android.util.Log;

import java.util.concurrent.Executor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AllowedNetworkTypesListener extends TelephonyCallback
        implements TelephonyCallback.AllowedNetworkTypesListener {
    public final Executor mExecutor;
    OnAllowedNetworkTypesListener mListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnAllowedNetworkTypesListener {
        void onAllowedNetworkTypesChanged();
    }

    public AllowedNetworkTypesListener(Executor executor) {
        this.mExecutor = executor;
    }

    public final void onAllowedNetworkTypesChanged(int i, long j) {
        OnAllowedNetworkTypesListener onAllowedNetworkTypesListener;
        if ((i == 0 || i == 2) && (onAllowedNetworkTypesListener = this.mListener) != null) {
            onAllowedNetworkTypesListener.onAllowedNetworkTypesChanged();
            Log.d("NetworkModeListener", "onAllowedNetworkChanged: " + j);
        }
    }

    public final void setAllowedNetworkTypesListener(
            OnAllowedNetworkTypesListener onAllowedNetworkTypesListener) {
        this.mListener = onAllowedNetworkTypesListener;
    }
}
