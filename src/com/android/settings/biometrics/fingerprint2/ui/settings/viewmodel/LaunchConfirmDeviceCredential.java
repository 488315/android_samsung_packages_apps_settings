package com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel;

import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class LaunchConfirmDeviceCredential extends NextStepViewModel {
    public final int userId;

    public LaunchConfirmDeviceCredential(int i) {
        this.userId = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof LaunchConfirmDeviceCredential)
                && this.userId == ((LaunchConfirmDeviceCredential) obj).userId;
    }

    public final int hashCode() {
        return Integer.hashCode(this.userId);
    }

    public final String toString() {
        return Anchor$$ExternalSyntheticOutline0.m(
                new StringBuilder("LaunchConfirmDeviceCredential(userId="), this.userId, ")");
    }
}
