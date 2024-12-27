package com.samsung.android.knox.kpm;

import android.os.RemoteException;
import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class KnoxPushServiceCallback {
    public static final String TAG = "KnoxPushServiceCallback";
    public KnoxPushServiceCallback acb = this;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class PushServiceCallback extends IKnoxPushServiceCallback.Stub {
        public PushServiceCallback() {}

        @Override // com.samsung.android.knox.kpm.IKnoxPushServiceCallback
        public void onRegistrationFinished(KnoxPushServiceResult knoxPushServiceResult)
                throws RemoteException {
            Log.d(KnoxPushServiceCallback.TAG, "onRegistrationFinished: ");
            KnoxPushService.getInstance().removeFromTrackMap(KnoxPushServiceCallback.this.acb);
            KnoxPushServiceCallback.this.acb.onRegistrationFinished(knoxPushServiceResult);
        }

        @Override // com.samsung.android.knox.kpm.IKnoxPushServiceCallback
        public void onRegistrationStatus(KnoxPushServiceResult knoxPushServiceResult)
                throws RemoteException {
            Log.d(KnoxPushServiceCallback.TAG, "onRegistrationStatus: ");
            KnoxPushService.getInstance().removeFromTrackMap(KnoxPushServiceCallback.this.acb);
            KnoxPushServiceCallback.this.acb.onRegistrationStatus(knoxPushServiceResult);
        }

        @Override // com.samsung.android.knox.kpm.IKnoxPushServiceCallback
        public void onUnRegistrationFinished(KnoxPushServiceResult knoxPushServiceResult)
                throws RemoteException {
            Log.d(KnoxPushServiceCallback.TAG, "onUnRegistrationFinished: ");
            KnoxPushService.getInstance().removeFromTrackMap(KnoxPushServiceCallback.this.acb);
            KnoxPushServiceCallback.this.acb.onUnRegistrationFinished(knoxPushServiceResult);
        }
    }

    public final IKnoxPushServiceCallback getKnoxPushServiceCb() {
        return new PushServiceCallback();
    }

    public abstract void onRegistrationFinished(KnoxPushServiceResult knoxPushServiceResult);

    public abstract void onRegistrationStatus(KnoxPushServiceResult knoxPushServiceResult);

    public abstract void onUnRegistrationFinished(KnoxPushServiceResult knoxPushServiceResult);
}
