package com.android.settings.password;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class CredentialCheckResultTracker extends Fragment {
    public boolean mHasResult = false;
    public Listener mListener;
    public Intent mResultData;
    public int mResultEffectiveUserId;
    public boolean mResultMatched;
    public int mResultTimeoutMs;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Listener {
        void onCredentialChecked(boolean z, Intent intent, int i, int i2, boolean z2);
    }

    @Override // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRetainInstance(true);
    }

    public final void setListener(Listener listener) {
        if (this.mListener == listener) {
            return;
        }
        this.mListener = listener;
        if (listener == null || !this.mHasResult) {
            return;
        }
        listener.onCredentialChecked(
                this.mResultMatched,
                this.mResultData,
                this.mResultTimeoutMs,
                this.mResultEffectiveUserId,
                false);
    }

    public final void setResult(boolean z, Intent intent, int i, int i2) {
        this.mResultMatched = z;
        this.mResultData = intent;
        this.mResultTimeoutMs = i;
        this.mResultEffectiveUserId = i2;
        this.mHasResult = true;
        Listener listener = this.mListener;
        if (listener != null) {
            listener.onCredentialChecked(z, intent, i, i2, true);
            this.mHasResult = false;
        }
    }
}
