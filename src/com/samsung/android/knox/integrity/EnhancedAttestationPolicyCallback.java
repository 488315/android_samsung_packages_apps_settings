package com.samsung.android.knox.integrity;

import android.os.RemoteException;
import android.util.Log;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class EnhancedAttestationPolicyCallback {
    public static final String TAG = "EAPolicyCb";
    public EnhancedAttestationPolicyCallback acb = this;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class EaAttestationPolicyCallback extends IEnhancedAttestationPolicyCallback.Stub {
        public String mNonce = ApnSettings.MVNO_NONE;

        public EaAttestationPolicyCallback() {}

        @Override // com.samsung.android.knox.integrity.IEnhancedAttestationPolicyCallback
        public void onAttestationFinished(EnhancedAttestationResult enhancedAttestationResult)
                throws RemoteException {
            Log.d(
                    EnhancedAttestationPolicyCallback.TAG,
                    "onAttestationFinished: " + this.mNonce.length());
            EnhancedAttestationPolicy.getInstance().removeFromTrackMap(this.mNonce);
            EnhancedAttestationPolicyCallback.this.acb.onAttestationFinished(
                    enhancedAttestationResult);
        }
    }

    public final IEnhancedAttestationPolicyCallback getEaAttestationCb(String str) {
        EaAttestationPolicyCallback eaAttestationPolicyCallback = new EaAttestationPolicyCallback();
        eaAttestationPolicyCallback.mNonce = str;
        return eaAttestationPolicyCallback;
    }

    public abstract void onAttestationFinished(EnhancedAttestationResult enhancedAttestationResult);
}
