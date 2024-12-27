package com.samsung.android.gtscell.data;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        bv = {1, 0, 3},
        d1 = {
            "\u0000>\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0000\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0000\n"
                + "\u0002\u0010 \n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0006\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u000b\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001d\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r"
                + "0\u0005J#\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r"
                + "0\u00052\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00110\u0010H\u0086\bJ#\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u00052\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00110\u0010H\u0086\bJ\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00130\u0005J\u0012\u0010\u0015\u001a\u00020\u0016*\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0003R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\b\u0010"
                + "\tR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\n"
                + "\u0010\u000b¨\u0006\u0018"
        },
        d2 = {
            "Lcom/samsung/android/gtscell/data/GtsItemSupplierGroup;",
            ApnSettings.MVNO_NONE,
            "name",
            ApnSettings.MVNO_NONE,
            "suppliers",
            ApnSettings.MVNO_NONE,
            "Lcom/samsung/android/gtscell/data/GtsItemSupplier;",
            "(Ljava/lang/String;Ljava/util/List;)V",
            "getName",
            "()Ljava/lang/String;",
            "getSuppliers",
            "()Ljava/util/List;",
            "expressions",
            "Lcom/samsung/android/gtscell/data/GtsExpressionRaw;",
            "filterExpressions",
            "predicate",
            "Lkotlin/Function1;",
            ApnSettings.MVNO_NONE,
            "filterItems",
            "Lcom/samsung/android/gtscell/data/GtsItem;",
            FieldName.ITEMS,
            "verify",
            ApnSettings.MVNO_NONE,
            GoodSettingsContract.EXTRA_NAME.POLICY_KEY,
            "gtscell_release"
        },
        k = 1,
        mv = {1, 1, 16})
/* loaded from: classes4.dex */
public final class GtsItemSupplierGroup {
    private final String name;
    private final List<GtsItemSupplier> suppliers;

    public GtsItemSupplierGroup(String name, List<GtsItemSupplier> suppliers) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(suppliers, "suppliers");
        this.name = name;
        this.suppliers = suppliers;
    }

    public final List<GtsExpressionRaw> expressions() {
        List<GtsItemSupplier> list = this.suppliers;
        ArrayList arrayList = new ArrayList();
        for (GtsItemSupplier gtsItemSupplier : list) {
            GtsExpressionRaw gtsExpressionRaw =
                    gtsItemSupplier
                            .getExpression()
                            .get(new GtsExpressionBuilder(gtsItemSupplier.getItemKey()));
            if (gtsExpressionRaw != null) {
                verify(gtsItemSupplier, gtsExpressionRaw.getItemKey());
            } else {
                gtsExpressionRaw = null;
            }
            if (gtsExpressionRaw != null) {
                arrayList.add(gtsExpressionRaw);
            }
        }
        return arrayList;
    }

    public final List<GtsExpressionRaw> filterExpressions(Function1 predicate) {
        GtsExpressionRaw gtsExpressionRaw;
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        List<GtsItemSupplier> suppliers = getSuppliers();
        ArrayList arrayList = new ArrayList();
        for (GtsItemSupplier gtsItemSupplier : suppliers) {
            GtsExpressionRaw gtsExpressionRaw2 = null;
            if (((Boolean) predicate.invoke(gtsItemSupplier)).booleanValue()
                    && (gtsExpressionRaw =
                                    gtsItemSupplier
                                            .getExpression()
                                            .get(
                                                    new GtsExpressionBuilder(
                                                            gtsItemSupplier.getItemKey())))
                            != null) {
                verify(gtsItemSupplier, gtsExpressionRaw.getItemKey());
                gtsExpressionRaw2 = gtsExpressionRaw;
            }
            if (gtsExpressionRaw2 != null) {
                arrayList.add(gtsExpressionRaw2);
            }
        }
        return arrayList;
    }

    public final List<GtsItem> filterItems(Function1 predicate) {
        GtsItem gtsItem;
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        List<GtsItemSupplier> suppliers = getSuppliers();
        ArrayList arrayList = new ArrayList();
        for (GtsItemSupplier gtsItemSupplier : suppliers) {
            GtsItem gtsItem2 = null;
            if (((Boolean) predicate.invoke(gtsItemSupplier)).booleanValue()
                    && (gtsItem =
                                    gtsItemSupplier
                                            .getItem()
                                            .get(new GtsItemBuilder(gtsItemSupplier.getItemKey())))
                            != null) {
                verify(gtsItemSupplier, gtsItem.getKey());
                gtsItem2 = gtsItem;
            }
            if (gtsItem2 != null) {
                arrayList.add(gtsItem2);
            }
        }
        return arrayList;
    }

    public final String getName() {
        return this.name;
    }

    public final List<GtsItemSupplier> getSuppliers() {
        return this.suppliers;
    }

    public final List<GtsItem> items() {
        List<GtsItemSupplier> list = this.suppliers;
        ArrayList arrayList = new ArrayList();
        for (GtsItemSupplier gtsItemSupplier : list) {
            GtsItem gtsItem =
                    gtsItemSupplier.getItem().get(new GtsItemBuilder(gtsItemSupplier.getItemKey()));
            if (gtsItem != null) {
                verify(gtsItemSupplier, gtsItem.getKey());
            } else {
                gtsItem = null;
            }
            if (gtsItem != null) {
                arrayList.add(gtsItem);
            }
        }
        return arrayList;
    }

    public final void verify(GtsItemSupplier verify, String key) {
        Intrinsics.checkParameterIsNotNull(verify, "$this$verify");
        Intrinsics.checkParameterIsNotNull(key, "key");
        if (!key.equals(verify.getItemKey())) {
            throw new IllegalArgumentException(
                    "expected key(" + verify.getItemKey() + ") but key(" + key + ')');
        }
    }
}
