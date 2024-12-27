package com.android.settingslib.spa.widget.scaffold;

import androidx.compose.animation.Scale$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Color;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class TopAppBarColors {
    public final long actionIconContentColor;
    public final long containerColor;
    public final long navigationIconContentColor;
    public final long scrolledContainerColor;
    public final long titleContentColor;

    public TopAppBarColors(long j, long j2, long j3, long j4, long j5) {
        this.containerColor = j;
        this.scrolledContainerColor = j2;
        this.navigationIconContentColor = j3;
        this.titleContentColor = j4;
        this.actionIconContentColor = j5;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof TopAppBarColors)) {
            return false;
        }
        TopAppBarColors topAppBarColors = (TopAppBarColors) obj;
        return Color.m313equalsimpl0(this.containerColor, topAppBarColors.containerColor)
                && Color.m313equalsimpl0(
                        this.scrolledContainerColor, topAppBarColors.scrolledContainerColor)
                && Color.m313equalsimpl0(
                        this.navigationIconContentColor, topAppBarColors.navigationIconContentColor)
                && Color.m313equalsimpl0(this.titleContentColor, topAppBarColors.titleContentColor)
                && Color.m313equalsimpl0(
                        this.actionIconContentColor, topAppBarColors.actionIconContentColor);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        return Long.hashCode(this.actionIconContentColor)
                + Scale$$ExternalSyntheticOutline0.m(
                        Scale$$ExternalSyntheticOutline0.m(
                                Scale$$ExternalSyntheticOutline0.m(
                                        Long.hashCode(this.containerColor) * 31,
                                        31,
                                        this.scrolledContainerColor),
                                31,
                                this.navigationIconContentColor),
                        31,
                        this.titleContentColor);
    }
}
