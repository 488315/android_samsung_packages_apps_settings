package com.samsung.android.gtscell.data;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        bv = {1, 0, 3},
        d1 = {
            "\u0000\u001a\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\r"
                + "\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\u0003H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n"
                + "\u0000¨\u0006\b"
        },
        d2 = {
            "Lcom/samsung/android/gtscell/data/GtsItemSupplierGroupBuilder;",
            "Lcom/samsung/android/gtscell/data/GtsListBuilder;",
            "Lcom/samsung/android/gtscell/data/GtsItemSupplier;",
            "Lcom/samsung/android/gtscell/data/GtsItemSupplierGroup;",
            "groupName",
            ApnSettings.MVNO_NONE,
            "(Ljava/lang/String;)V",
            "build",
            "gtscell_release"
        },
        k = 1,
        mv = {1, 1, 16})
/* loaded from: classes4.dex */
public final class GtsItemSupplierGroupBuilder
        extends GtsListBuilder<GtsItemSupplier, GtsItemSupplierGroup> {
    private final String groupName;

    public GtsItemSupplierGroupBuilder(String groupName) {
        Intrinsics.checkParameterIsNotNull(groupName, "groupName");
        this.groupName = groupName;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.samsung.android.gtscell.data.GtsListBuilder
    public GtsItemSupplierGroup build() {
        return new GtsItemSupplierGroup(this.groupName, CollectionsKt___CollectionsKt.toList(this));
    }
}
