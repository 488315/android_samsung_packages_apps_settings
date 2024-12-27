package com.samsung.android.knox.localservice;

import java.io.PrintWriter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class ConstrainedModeInternal {
    public abstract boolean checkConstrainedState();

    public abstract void cleanUpConstrainedState(int i);

    public abstract boolean disableConstrainedState(int i);

    public abstract void dump(PrintWriter printWriter);

    public abstract boolean enableConstrainedState(
            int i, String str, String str2, String str3, String str4, int i2);

    public abstract int getConstrainedState();

    public abstract boolean isRestrictedByConstrainedState(int i);
}
