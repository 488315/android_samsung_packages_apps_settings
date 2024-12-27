package com.samsung.android.gtscell.data;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        bv = {1, 0, 3},
        d1 = {
            "\u00008\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0000\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\b\n"
                + "\u0000\n"
                + "\u0002\u0010\u000b\n"
                + "\u0002\b\r"
                + "\u0018\u0000"
                + " \u001b2\u00020\u0001:\u0001\u001bB5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005\u0012\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020"
                + "\t\u0012\u0004\u0012\u00020\n"
                + "0\u0005¢\u0006\u0002\u0010\u000bJ\u0006\u0010\u0019\u001a\u00020\u0000J\u0006\u0010\u001a\u001a\u00020\u0000R\u000e\u0010\f\u001a\u00020\r"
                + "X\u0082\u000e¢\u0006\u0002\n"
                + "\u0000R\u0011\u0010\u000e\u001a\u00020\u000f8F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0012\u001a\u00020\u000f8F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0011R\u001d\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u0014\u0010\u0015R\u001d\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020"
                + "\t\u0012\u0004\u0012\u00020\n"
                + "0\u0005¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u0016\u0010\u0015R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u0017\u0010\u0018¨\u0006\u001c"
        },
        d2 = {
            "Lcom/samsung/android/gtscell/data/GtsItemSupplier;",
            ApnSettings.MVNO_NONE,
            "itemKey",
            ApnSettings.MVNO_NONE,
            "expression",
            "Lcom/samsung/android/gtscell/data/GtsSupplier;",
            "Lcom/samsung/android/gtscell/data/GtsExpressionBuilder;",
            "Lcom/samsung/android/gtscell/data/GtsExpressionRaw;",
            "item",
            "Lcom/samsung/android/gtscell/data/GtsItemBuilder;",
            "Lcom/samsung/android/gtscell/data/GtsItem;",
            "(Ljava/lang/String;Lcom/samsung/android/gtscell/data/GtsSupplier;Lcom/samsung/android/gtscell/data/GtsSupplier;)V",
            "allowFeature",
            ApnSettings.MVNO_NONE,
            "canBackup",
            ApnSettings.MVNO_NONE,
            "getCanBackup",
            "()Z",
            "canShare",
            "getCanShare",
            "getExpression",
            "()Lcom/samsung/android/gtscell/data/GtsSupplier;",
            "getItem",
            "getItemKey",
            "()Ljava/lang/String;",
            "disallowBackup",
            "disallowShare",
            "Companion",
            "gtscell_release"
        },
        k = 1,
        mv = {1, 1, 16})
/* loaded from: classes4.dex */
public final class GtsItemSupplier {
    private static final int ALL = 3;
    private static final int BACKUP = 2;
    private static final int SHARE = 1;
    private int allowFeature;
    private final GtsSupplier<GtsExpressionBuilder, GtsExpressionRaw> expression;
    private final GtsSupplier<GtsItemBuilder, GtsItem> item;
    private final String itemKey;

    public GtsItemSupplier(
            String itemKey,
            GtsSupplier<GtsExpressionBuilder, GtsExpressionRaw> expression,
            GtsSupplier<GtsItemBuilder, GtsItem> item) {
        Intrinsics.checkParameterIsNotNull(itemKey, "itemKey");
        Intrinsics.checkParameterIsNotNull(expression, "expression");
        Intrinsics.checkParameterIsNotNull(item, "item");
        this.itemKey = itemKey;
        this.expression = expression;
        this.item = item;
        this.allowFeature = 3;
    }

    public final GtsItemSupplier disallowBackup() {
        this.allowFeature &= -3;
        return this;
    }

    public final GtsItemSupplier disallowShare() {
        this.allowFeature &= -2;
        return this;
    }

    public final boolean getCanBackup() {
        return (this.allowFeature & 2) != 0;
    }

    public final boolean getCanShare() {
        return (this.allowFeature & 1) != 0;
    }

    public final GtsSupplier<GtsExpressionBuilder, GtsExpressionRaw> getExpression() {
        return this.expression;
    }

    public final GtsSupplier<GtsItemBuilder, GtsItem> getItem() {
        return this.item;
    }

    public final String getItemKey() {
        return this.itemKey;
    }
}
