package com.android.settingslib.spaprivileged.model.enterprise;

import android.content.Context;
import android.content.Intent;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BlockedByEcmImpl implements RestrictedMode {
    public final Context context;
    public final Intent intent;

    public BlockedByEcmImpl(Context context, Intent intent) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.intent = intent;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BlockedByEcmImpl)) {
            return false;
        }
        BlockedByEcmImpl blockedByEcmImpl = (BlockedByEcmImpl) obj;
        return Intrinsics.areEqual(this.context, blockedByEcmImpl.context)
                && Intrinsics.areEqual(this.intent, blockedByEcmImpl.intent);
    }

    public final int hashCode() {
        return this.intent.hashCode() + (this.context.hashCode() * 31);
    }

    public final String toString() {
        return "BlockedByEcmImpl(context=" + this.context + ", intent=" + this.intent + ")";
    }
}
