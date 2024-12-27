package com.samsung.android.gtscell.data;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.sec.ims.IMSParameter;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000>\n"
                + "\u0002\u0010\u000e\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010!\n"
                + "\u0002\u0010 \n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0010\u000b\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\u001a,\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0001\u001a\u00020\u00002\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002H\u0086\b¢\u0006\u0004\b\u0007\u0010\b\u001a0\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\n"
                + "2\u0018\u0010\u0005\u001a\u0014\u0012\n"
                + "\u0012\b\u0012\u0004\u0012\u00020\u00060"
                + "\t\u0012\u0004\u0012\u00020\u00040\u0002H\u0086\b¢\u0006\u0004\b\u000b\u0010\f\u001a(\u0010\u000e\u001a\u00020\u0004*\b\u0012\u0004\u0012\u00020\u00060"
                + "\t2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\r"
                + "H\u0086\b¢\u0006\u0004\b\u000e\u0010\u000f\u001a*\u0010\u0010\u001a\u00020\u0004*\b\u0012\u0004\u0012\u00020\u00060"
                + "\t2\u000e\u0010\u0005\u001a\n"
                + "\u0012\u0006\u0012\u0004\u0018\u00010\u00060\r"
                + "H\u0086\b¢\u0006\u0004\b\u0010\u0010\u000f\u001a6\u0010\u000e\u001a\u00020\u0011*\b\u0012\u0004\u0012\u00020\u00060"
                + "\t2\u0006\u0010\u0001\u001a\u00020\u00002\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002H\u0086\b¢\u0006\u0004\b\u000e\u0010\u0012\u001a\"\u0010\u0014\u001a\u00020\u0004*\u00020\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00130\r"
                + "H\u0086\b¢\u0006\u0004\b\u0014\u0010\u0015¨\u0006\u0016"
        },
        d2 = {
            ApnSettings.MVNO_NONE,
            "groupName",
            "Lkotlin/Function1;",
            "Lcom/samsung/android/gtscell/data/GtsItemSupplierGroupBuilder;",
            ApnSettings.MVNO_NONE,
            IMSParameter.CALL.ACTION,
            "Lcom/samsung/android/gtscell/data/GtsItemSupplierGroup;",
            "gtsItemSupplierGroup",
            "(Ljava/lang/String;Lkotlin/jvm/functions/Function1;)Lcom/samsung/android/gtscell/data/GtsItemSupplierGroup;",
            ApnSettings.MVNO_NONE,
            ApnSettings.MVNO_NONE,
            "gtsItemSupplierGroups",
            "(Lkotlin/jvm/functions/Function1;)Ljava/util/List;",
            "Lkotlin/Function0;",
            "group",
            "(Ljava/util/List;Lkotlin/jvm/functions/Function0;)V",
            "groupNotNull",
            ApnSettings.MVNO_NONE,
            "(Ljava/util/List;Ljava/lang/String;Lkotlin/jvm/functions/Function1;)Z",
            "Lcom/samsung/android/gtscell/data/GtsItemSupplier;",
            "item",
            "(Lcom/samsung/android/gtscell/data/GtsItemSupplierGroupBuilder;Lkotlin/jvm/functions/Function0;)V",
            "gtscell_release"
        },
        k = 2,
        mv = {1, 4, 0})
/* loaded from: classes4.dex */
public final class GtsItemSupplierGroupBuilderKt {
    /* JADX WARN: Multi-variable type inference failed */
    public static final void group(List<GtsItemSupplierGroup> group, Function0 action) {
        Intrinsics.checkParameterIsNotNull(group, "$this$group");
        Intrinsics.checkParameterIsNotNull(action, "action");
        group.add(action.mo1068invoke());
    }

    public static final void groupNotNull(
            List<GtsItemSupplierGroup> groupNotNull, Function0 action) {
        Intrinsics.checkParameterIsNotNull(groupNotNull, "$this$groupNotNull");
        Intrinsics.checkParameterIsNotNull(action, "action");
        GtsItemSupplierGroup gtsItemSupplierGroup = (GtsItemSupplierGroup) action.mo1068invoke();
        if (gtsItemSupplierGroup != null) {
            groupNotNull.add(gtsItemSupplierGroup);
        }
    }

    public static final GtsItemSupplierGroup gtsItemSupplierGroup(
            String groupName, Function1 action) {
        Intrinsics.checkParameterIsNotNull(groupName, "groupName");
        Intrinsics.checkParameterIsNotNull(action, "action");
        GtsItemSupplierGroupBuilder gtsItemSupplierGroupBuilder =
                new GtsItemSupplierGroupBuilder(groupName);
        action.invoke(gtsItemSupplierGroupBuilder);
        return gtsItemSupplierGroupBuilder.build();
    }

    public static final List<GtsItemSupplierGroup> gtsItemSupplierGroups(Function1 action) {
        Intrinsics.checkParameterIsNotNull(action, "action");
        ArrayList arrayList = new ArrayList();
        action.invoke(arrayList);
        return arrayList;
    }

    public static final void item(GtsItemSupplierGroupBuilder item, Function0 action) {
        Intrinsics.checkParameterIsNotNull(item, "$this$item");
        Intrinsics.checkParameterIsNotNull(action, "action");
        item.add(action.mo1068invoke());
    }

    public static final boolean group(
            List<GtsItemSupplierGroup> group, String groupName, Function1 action) {
        Intrinsics.checkParameterIsNotNull(group, "$this$group");
        Intrinsics.checkParameterIsNotNull(groupName, "groupName");
        Intrinsics.checkParameterIsNotNull(action, "action");
        GtsItemSupplierGroupBuilder gtsItemSupplierGroupBuilder =
                new GtsItemSupplierGroupBuilder(groupName);
        action.invoke(gtsItemSupplierGroupBuilder);
        return group.add(gtsItemSupplierGroupBuilder.build());
    }
}
