package com.android.launcher3.icons;

import android.content.Context;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public final class IconFactory extends BaseIconFactory {
    public static IconFactory sPool;
    public static final Object sPoolSync = new Object();
    public final int mPoolId;
    public IconFactory next;

    public IconFactory(Context context, int i, int i2) {
        super(context, i, i2);
        this.mPoolId = 0;
    }

    @Override // com.android.launcher3.icons.BaseIconFactory, java.lang.AutoCloseable
    public final void close() {
        synchronized (sPoolSync) {
            try {
                if (this.mPoolId != 0) {
                    return;
                }
                this.mWrapperBackgroundColor = -1;
                this.next = sPool;
                sPool = this;
            } finally {
            }
        }
    }
}
