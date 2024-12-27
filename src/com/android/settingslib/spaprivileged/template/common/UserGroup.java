package com.android.settingslib.spaprivileged.template.common;

import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UserGroup {
    public final List userInfos;

    public UserGroup(List list) {
        this.userInfos = list;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof UserGroup)
                && Intrinsics.areEqual(this.userInfos, ((UserGroup) obj).userInfos);
    }

    public final int hashCode() {
        return this.userInfos.hashCode();
    }

    public final String toString() {
        return "UserGroup(userInfos=" + this.userInfos + ")";
    }
}
