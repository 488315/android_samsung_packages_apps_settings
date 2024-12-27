package com.samsung.android.settings.knox;

import android.view.View;
import android.widget.TextView;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class KnoxConfirmDeviceCredentialBaseFragmentHelper {
    public KnoxConfirmLockHelper mKnoxConfirmLockHelper;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Callback {
        void adjustPasswordViewWithIme(View view, int i);

        void disableConfirmCredentialCallback();

        void enableConfirmCredentialCallback();

        void onSaveInstanceStateCallback();

        void updateState();
    }

    public final void needToUpdateErrorMessage(int i, TextView textView) {
        KnoxConfirmLockHelper knoxConfirmLockHelper = this.mKnoxConfirmLockHelper;
        if (knoxConfirmLockHelper != null) {
            knoxConfirmLockHelper.needToUpdateErrorMessage(i, textView);
        }
    }
}
