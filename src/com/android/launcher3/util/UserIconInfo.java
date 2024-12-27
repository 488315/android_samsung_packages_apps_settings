package com.android.launcher3.util;

import android.os.UserHandle;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public final class UserIconInfo {
    public final int type;

    public UserIconInfo(UserHandle userHandle, int i) {
        if (userHandle != null) {
            userHandle.hashCode();
        }
        this.type = i;
    }
}
