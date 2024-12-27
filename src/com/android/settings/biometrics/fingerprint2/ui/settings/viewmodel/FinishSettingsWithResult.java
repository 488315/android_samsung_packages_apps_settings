package com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FinishSettingsWithResult extends NextStepViewModel {
    public final String reason;
    public final int result;

    public FinishSettingsWithResult(int i, String str) {
        this.result = i;
        this.reason = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FinishSettingsWithResult)) {
            return false;
        }
        FinishSettingsWithResult finishSettingsWithResult = (FinishSettingsWithResult) obj;
        return this.result == finishSettingsWithResult.result
                && Intrinsics.areEqual(this.reason, finishSettingsWithResult.reason);
    }

    public final int hashCode() {
        return this.reason.hashCode() + (Integer.hashCode(this.result) * 31);
    }

    public final String toString() {
        return "FinishSettingsWithResult(result=" + this.result + ", reason=" + this.reason + ")";
    }
}
