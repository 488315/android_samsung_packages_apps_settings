package com.android.settingslib.spaprivileged.model.enterprise;

import android.content.Context;

import com.android.settingslib.RestrictedLockUtils;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BlockedByAdminImpl implements RestrictedMode {
    public final Context context;
    public final RestrictedLockUtils.EnforcedAdmin enforcedAdmin;
    public final EnterpriseRepository enterpriseRepository;

    public BlockedByAdminImpl(Context context, RestrictedLockUtils.EnforcedAdmin enforcedAdmin) {
        EnterpriseRepository enterpriseRepository = new EnterpriseRepository(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.enforcedAdmin = enforcedAdmin;
        this.enterpriseRepository = enterpriseRepository;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BlockedByAdminImpl)) {
            return false;
        }
        BlockedByAdminImpl blockedByAdminImpl = (BlockedByAdminImpl) obj;
        return Intrinsics.areEqual(this.context, blockedByAdminImpl.context)
                && Intrinsics.areEqual(this.enforcedAdmin, blockedByAdminImpl.enforcedAdmin)
                && Intrinsics.areEqual(
                        this.enterpriseRepository, blockedByAdminImpl.enterpriseRepository);
    }

    public final int hashCode() {
        return this.enterpriseRepository.hashCode()
                + ((this.enforcedAdmin.hashCode() + (this.context.hashCode() * 31)) * 31);
    }

    public final String toString() {
        return "BlockedByAdminImpl(context="
                + this.context
                + ", enforcedAdmin="
                + this.enforcedAdmin
                + ", enterpriseRepository="
                + this.enterpriseRepository
                + ")";
    }
}
