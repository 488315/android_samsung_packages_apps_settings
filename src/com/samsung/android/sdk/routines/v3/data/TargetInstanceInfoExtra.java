package com.samsung.android.sdk.routines.v3.data;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import androidx.annotation.Keep;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Keep
@Metadata(
        d1 = {
            "\u0000 \n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0000\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0006\n"
                + "\u0002\u0010\u000b\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0010\u000e\n"
                + "\u0000\b\u0087\b\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J"
                + "\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010"
                + "\t\u001a\u00020\n"
                + "2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J"
                + "\t\u0010\f\u001a\u00020\u0003HÖ\u0001J\t\u0010\r"
                + "\u001a\u00020\u000eHÖ\u0001R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n"
                + "\u0000\u001a\u0004\b\u0002\u0010\u0005\"\u0004\b\u0006\u0010\u0004¨\u0006\u000f"
        },
        d2 = {
            "Lcom/samsung/android/sdk/routines/v3/data/TargetInstanceInfoExtra;",
            ApnSettings.MVNO_NONE,
            "isNegative",
            ApnSettings.MVNO_NONE,
            "(I)V",
            "()I",
            "setNegative",
            "component1",
            "copy",
            "equals",
            ApnSettings.MVNO_NONE,
            "other",
            "hashCode",
            "toString",
            ApnSettings.MVNO_NONE,
            "routine-plugin-sdk-3.1.9_release"
        },
        k = 1,
        mv = {1, 7, 1},
        xi = 48)
/* loaded from: classes4.dex */
public final /* data */ class TargetInstanceInfoExtra {
    private int isNegative;

    public TargetInstanceInfoExtra() {
        this(0, 1, null);
    }

    public static /* synthetic */ TargetInstanceInfoExtra copy$default(
            TargetInstanceInfoExtra targetInstanceInfoExtra, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = targetInstanceInfoExtra.isNegative;
        }
        return targetInstanceInfoExtra.copy(i);
    }

    /* renamed from: component1, reason: from getter */
    public final int getIsNegative() {
        return this.isNegative;
    }

    public final TargetInstanceInfoExtra copy(int isNegative) {
        return new TargetInstanceInfoExtra(isNegative);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof TargetInstanceInfoExtra)
                && this.isNegative == ((TargetInstanceInfoExtra) other).isNegative;
    }

    public int hashCode() {
        return Integer.hashCode(this.isNegative);
    }

    public final int isNegative() {
        return this.isNegative;
    }

    public final void setNegative(int i) {
        this.isNegative = i;
    }

    public String toString() {
        return BackEventCompat$$ExternalSyntheticOutline0.m(
                new StringBuilder("TargetInstanceInfoExtra(isNegative="), this.isNegative, ')');
    }

    public TargetInstanceInfoExtra(int i) {
        this.isNegative = i;
    }

    public /* synthetic */ TargetInstanceInfoExtra(
            int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0 : i);
    }
}
