package com.android.settings.spa;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SpaDestination {
    public final String destination;
    public final String highlightMenuKey;

    public SpaDestination(String destination, String str) {
        Intrinsics.checkNotNullParameter(destination, "destination");
        this.destination = destination;
        this.highlightMenuKey = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SpaDestination)) {
            return false;
        }
        SpaDestination spaDestination = (SpaDestination) obj;
        return Intrinsics.areEqual(this.destination, spaDestination.destination)
                && Intrinsics.areEqual(this.highlightMenuKey, spaDestination.highlightMenuKey);
    }

    public final int hashCode() {
        int hashCode = this.destination.hashCode() * 31;
        String str = this.highlightMenuKey;
        return hashCode + (str == null ? 0 : str.hashCode());
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("SpaDestination(destination=");
        sb.append(this.destination);
        sb.append(", highlightMenuKey=");
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, this.highlightMenuKey, ")");
    }
}
