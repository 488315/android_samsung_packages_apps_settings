package com.android.settingslib.spaprivileged.model.enterprise;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class EnhancedConfirmation {
    public final String key;
    public final String packageName;

    public EnhancedConfirmation(String str, String packageName) {
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        this.key = str;
        this.packageName = packageName;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EnhancedConfirmation)) {
            return false;
        }
        EnhancedConfirmation enhancedConfirmation = (EnhancedConfirmation) obj;
        return Intrinsics.areEqual(this.key, enhancedConfirmation.key)
                && Intrinsics.areEqual(this.packageName, enhancedConfirmation.packageName);
    }

    public final int hashCode() {
        return this.packageName.hashCode() + (this.key.hashCode() * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("EnhancedConfirmation(key=");
        sb.append(this.key);
        sb.append(", packageName=");
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, this.packageName, ")");
    }
}
