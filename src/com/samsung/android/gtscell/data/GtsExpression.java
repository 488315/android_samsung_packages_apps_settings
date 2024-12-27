package com.samsung.android.gtscell.data;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        bv = {1, 0, 3},
        d1 = {
            "\u0000.\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0000\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0010\u000b\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010 \n"
                + "\u0002\b\u0015\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B;\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020"
                + "\t\u0012\f\u0010\n"
                + "\u001a\b\u0012\u0004\u0012\u00020\t0\u000b¢\u0006\u0002\u0010\fJ"
                + "\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J"
                + "\t\u0010\u0018\u001a\u00020\u0003HÆ\u0003J"
                + "\t\u0010\u0019\u001a\u00020\u0003HÆ\u0003J"
                + "\t\u0010\u001a\u001a\u00020\u0007HÆ\u0003J\t\u0010\u001b\u001a\u00020"
                + "\tHÆ\u0003J\u000f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020"
                + "\t0\u000bHÆ\u0003JK\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020"
                + "\t2\u000e\b\u0002\u0010\n"
                + "\u001a\b\u0012\u0004\u0012\u00020"
                + "\t0\u000bHÆ\u0001J\u0013\u0010\u001e\u001a\u00020\u00072\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J"
                + "\t\u0010 \u001a\u00020!HÖ\u0001J"
                + "\t\u0010\"\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\r"
                + "\u0010\u000eR\u0017\u0010\n"
                + "\u001a\b\u0012\u0004\u0012\u00020\t0\u000b¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u0015\u0010\u0014R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u0016\u0010\u0014¨\u0006#"
        },
        d2 = {
            "Lcom/samsung/android/gtscell/data/GtsExpression;",
            ApnSettings.MVNO_NONE,
            "itemKey",
            ApnSettings.MVNO_NONE,
            UniversalCredentialUtil.AGENT_TITLE,
            "subTitle",
            "disabled",
            ApnSettings.MVNO_NONE,
            "expressionValue",
            "Lcom/samsung/android/gtscell/data/GtsExpressionValue;",
            "expressionSubValues",
            ApnSettings.MVNO_NONE,
            "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZLcom/samsung/android/gtscell/data/GtsExpressionValue;Ljava/util/List;)V",
            "getDisabled",
            "()Z",
            "getExpressionSubValues",
            "()Ljava/util/List;",
            "getExpressionValue",
            "()Lcom/samsung/android/gtscell/data/GtsExpressionValue;",
            "getItemKey",
            "()Ljava/lang/String;",
            "getSubTitle",
            "getTitle",
            "component1",
            "component2",
            "component3",
            "component4",
            "component5",
            "component6",
            "copy",
            "equals",
            "other",
            "hashCode",
            ApnSettings.MVNO_NONE,
            "toString",
            "gtscell_release"
        },
        k = 1,
        mv = {1, 1, 16})
/* loaded from: classes4.dex */
public final /* data */ class GtsExpression {
    private final boolean disabled;
    private final List<GtsExpressionValue> expressionSubValues;
    private final GtsExpressionValue expressionValue;
    private final String itemKey;
    private final String subTitle;
    private final String title;

    /* JADX WARN: Multi-variable type inference failed */
    public GtsExpression(
            String itemKey,
            String title,
            String subTitle,
            boolean z,
            GtsExpressionValue expressionValue,
            List<? extends GtsExpressionValue> expressionSubValues) {
        Intrinsics.checkParameterIsNotNull(itemKey, "itemKey");
        Intrinsics.checkParameterIsNotNull(title, "title");
        Intrinsics.checkParameterIsNotNull(subTitle, "subTitle");
        Intrinsics.checkParameterIsNotNull(expressionValue, "expressionValue");
        Intrinsics.checkParameterIsNotNull(expressionSubValues, "expressionSubValues");
        this.itemKey = itemKey;
        this.title = title;
        this.subTitle = subTitle;
        this.disabled = z;
        this.expressionValue = expressionValue;
        this.expressionSubValues = expressionSubValues;
    }

    public static /* synthetic */ GtsExpression copy$default(
            GtsExpression gtsExpression,
            String str,
            String str2,
            String str3,
            boolean z,
            GtsExpressionValue gtsExpressionValue,
            List list,
            int i,
            Object obj) {
        if ((i & 1) != 0) {
            str = gtsExpression.itemKey;
        }
        if ((i & 2) != 0) {
            str2 = gtsExpression.title;
        }
        String str4 = str2;
        if ((i & 4) != 0) {
            str3 = gtsExpression.subTitle;
        }
        String str5 = str3;
        if ((i & 8) != 0) {
            z = gtsExpression.disabled;
        }
        boolean z2 = z;
        if ((i & 16) != 0) {
            gtsExpressionValue = gtsExpression.expressionValue;
        }
        GtsExpressionValue gtsExpressionValue2 = gtsExpressionValue;
        if ((i & 32) != 0) {
            list = gtsExpression.expressionSubValues;
        }
        return gtsExpression.copy(str, str4, str5, z2, gtsExpressionValue2, list);
    }

    /* renamed from: component1, reason: from getter */
    public final String getItemKey() {
        return this.itemKey;
    }

    /* renamed from: component2, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    /* renamed from: component3, reason: from getter */
    public final String getSubTitle() {
        return this.subTitle;
    }

    /* renamed from: component4, reason: from getter */
    public final boolean getDisabled() {
        return this.disabled;
    }

    /* renamed from: component5, reason: from getter */
    public final GtsExpressionValue getExpressionValue() {
        return this.expressionValue;
    }

    public final List<GtsExpressionValue> component6() {
        return this.expressionSubValues;
    }

    public final GtsExpression copy(
            String itemKey,
            String title,
            String subTitle,
            boolean disabled,
            GtsExpressionValue expressionValue,
            List<? extends GtsExpressionValue> expressionSubValues) {
        Intrinsics.checkParameterIsNotNull(itemKey, "itemKey");
        Intrinsics.checkParameterIsNotNull(title, "title");
        Intrinsics.checkParameterIsNotNull(subTitle, "subTitle");
        Intrinsics.checkParameterIsNotNull(expressionValue, "expressionValue");
        Intrinsics.checkParameterIsNotNull(expressionSubValues, "expressionSubValues");
        return new GtsExpression(
                itemKey, title, subTitle, disabled, expressionValue, expressionSubValues);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GtsExpression)) {
            return false;
        }
        GtsExpression gtsExpression = (GtsExpression) other;
        return Intrinsics.areEqual(this.itemKey, gtsExpression.itemKey)
                && Intrinsics.areEqual(this.title, gtsExpression.title)
                && Intrinsics.areEqual(this.subTitle, gtsExpression.subTitle)
                && this.disabled == gtsExpression.disabled
                && Intrinsics.areEqual(this.expressionValue, gtsExpression.expressionValue)
                && Intrinsics.areEqual(this.expressionSubValues, gtsExpression.expressionSubValues);
    }

    public final boolean getDisabled() {
        return this.disabled;
    }

    public final List<GtsExpressionValue> getExpressionSubValues() {
        return this.expressionSubValues;
    }

    public final GtsExpressionValue getExpressionValue() {
        return this.expressionValue;
    }

    public final String getItemKey() {
        return this.itemKey;
    }

    public final String getSubTitle() {
        return this.subTitle;
    }

    public final String getTitle() {
        return this.title;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        String str = this.itemKey;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.title;
        int hashCode2 = (hashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.subTitle;
        int hashCode3 = (hashCode2 + (str3 != null ? str3.hashCode() : 0)) * 31;
        boolean z = this.disabled;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = (hashCode3 + i) * 31;
        GtsExpressionValue gtsExpressionValue = this.expressionValue;
        int hashCode4 =
                (i2 + (gtsExpressionValue != null ? gtsExpressionValue.hashCode() : 0)) * 31;
        List<GtsExpressionValue> list = this.expressionSubValues;
        return hashCode4 + (list != null ? list.hashCode() : 0);
    }

    public String toString() {
        return "GtsExpression(itemKey="
                + this.itemKey
                + ", title="
                + this.title
                + ", subTitle="
                + this.subTitle
                + ", disabled="
                + this.disabled
                + ", expressionValue="
                + this.expressionValue
                + ", expressionSubValues="
                + this.expressionSubValues
                + ")";
    }
}
