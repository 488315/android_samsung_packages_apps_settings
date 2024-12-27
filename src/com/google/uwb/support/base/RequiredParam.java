package com.google.uwb.support.base;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class RequiredParam {
    public boolean mIsSet = false;
    public Object mValue;

    public final Object get() {
        if (this.mIsSet) {
            return this.mValue;
        }
        throw new IllegalStateException("Required Parameter not set");
    }

    public final void set(Object obj) {
        this.mValue = obj;
        this.mIsSet = true;
    }
}
