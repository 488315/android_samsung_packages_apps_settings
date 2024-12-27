package com.samsung.android.sdk.cover;

import android.graphics.Rect;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ScoverState {
    public boolean attached;
    public int color;
    public boolean fakeCover;
    public int fotaMode;
    public int heightPixel;
    public Rect mVisibleRect;
    public boolean switchState;
    public int type;
    public int widthPixel;

    public final String toString() {
        return String.format(
                "ScoverState(switchState=%b type=%d color=%d widthPixel=%d heightPixel=%d"
                    + " attached=%b fakeCover=%b fotaMode=%d VisibleRect=%s)",
                Boolean.valueOf(this.switchState),
                Integer.valueOf(this.type),
                Integer.valueOf(this.color),
                Integer.valueOf(this.widthPixel),
                Integer.valueOf(this.heightPixel),
                Boolean.valueOf(this.attached),
                Boolean.valueOf(this.fakeCover),
                Integer.valueOf(this.fotaMode),
                this.mVisibleRect);
    }
}
