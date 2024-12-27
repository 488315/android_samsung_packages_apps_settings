package com.google.android.material.carousel;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class KeylineState {
    public final float itemSize;
    public final List keylines;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class Keyline {
        public final float loc;
        public final float locOffset;
        public final float mask;
        public final float maskedItemSize;
    }
}
