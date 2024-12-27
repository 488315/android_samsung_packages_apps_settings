package com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FinishSettings extends NextStepViewModel {
    public final String reason;

    public FinishSettings(String str) {
        this.reason = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof FinishSettings)
                && Intrinsics.areEqual(this.reason, ((FinishSettings) obj).reason);
    }

    public final int hashCode() {
        return this.reason.hashCode();
    }

    public final String toString() {
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(
                new StringBuilder("FinishSettings(reason="), this.reason, ")");
    }
}
