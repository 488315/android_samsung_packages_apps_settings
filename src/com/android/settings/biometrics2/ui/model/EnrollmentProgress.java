package com.android.settings.biometrics2.ui.model;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class EnrollmentProgress {
    public final int remaining;
    public final int steps;

    public EnrollmentProgress(int i, int i2) {
        this.steps = i;
        this.remaining = i2;
    }

    public final String toString() {
        StringBuilder m =
                ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                        "EnrollmentProgress@", Integer.toHexString(hashCode()), "{steps:");
        m.append(this.steps);
        m.append(", remaining:");
        return Anchor$$ExternalSyntheticOutline0.m(m, this.remaining, "}");
    }
}
