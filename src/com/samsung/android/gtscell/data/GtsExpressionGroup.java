package com.samsung.android.gtscell.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

import com.google.gson.annotations.SerializedName;
import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.android.parcel.Parcelize;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Parcelize
@Keep
@Metadata(
        bv = {1, 0, 3},
        d1 = {
            "\u0000>\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0000\n"
                + "\u0002\u0010 \n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\r\n"
                + "\u0002\u0010\u000b\n"
                + "\u0000\n"
                + "\u0002\u0010\u0000\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0087\b\u0018\u0000  2\u00020\u0001:\u0001"
                + " B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010"
                + "\tJ\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J"
                + "\t\u0010\u0012\u001a\u00020\bHÆ\u0003J-\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\b\b\u0002\u0010\u0007\u001a\u00020\bHÆ\u0001J"
                + "\t\u0010\u0014\u001a\u00020\bHÖ\u0001J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018HÖ\u0003J"
                + "\t\u0010\u0019\u001a\u00020\bHÖ\u0001J"
                + "\t\u0010\u001a\u001a\u00020\u0003HÖ\u0001J\u0019\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\bHÖ\u0001R\u001c\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00058\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\n"
                + "\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\f\u0010\r"
                + "R\u0016\u0010\u0007\u001a\u00020\b8\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006!"
        },
        d2 = {
            "Lcom/samsung/android/gtscell/data/GtsExpressionGroup;",
            "Landroid/os/Parcelable;",
            "name",
            ApnSettings.MVNO_NONE,
            "expressions",
            ApnSettings.MVNO_NONE,
            "Lcom/samsung/android/gtscell/data/GtsExpressionRaw;",
            FieldName.VERSION,
            ApnSettings.MVNO_NONE,
            "(Ljava/lang/String;Ljava/util/List;I)V",
            "getExpressions",
            "()Ljava/util/List;",
            "getName",
            "()Ljava/lang/String;",
            "getVersion",
            "()I",
            "component1",
            "component2",
            "component3",
            "copy",
            "describeContents",
            "equals",
            ApnSettings.MVNO_NONE,
            "other",
            ApnSettings.MVNO_NONE,
            "hashCode",
            "toString",
            "writeToParcel",
            ApnSettings.MVNO_NONE,
            "parcel",
            "Landroid/os/Parcel;",
            "flags",
            "Companion",
            "gtscell_release"
        },
        k = 1,
        mv = {1, 1, 16})
/* loaded from: classes4.dex */
public final /* data */ class GtsExpressionGroup implements Parcelable {
    private static final int VERSION = 1;

    @SerializedName(FieldName.GROUP_EXPRESSIONS)
    private final List<GtsExpressionRaw> expressions;

    @SerializedName(FieldName.GROUP_NAME)
    private final String name;

    @SerializedName(FieldName.VERSION)
    private final int version;

    public static final Parcelable.Creator CREATOR = new Creator();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            k = 3,
            mv = {1, 1, 16})
    public static class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel in) {
            Intrinsics.checkParameterIsNotNull(in, "in");
            String readString = in.readString();
            int readInt = in.readInt();
            ArrayList arrayList = new ArrayList(readInt);
            while (readInt != 0) {
                arrayList.add(
                        (GtsExpressionRaw)
                                in.readParcelable(GtsExpressionGroup.class.getClassLoader()));
                readInt--;
            }
            return new GtsExpressionGroup(readString, arrayList, in.readInt());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new GtsExpressionGroup[i];
        }
    }

    public GtsExpressionGroup(String name, List<GtsExpressionRaw> expressions, int i) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(expressions, "expressions");
        this.name = name;
        this.expressions = expressions;
        this.version = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ GtsExpressionGroup copy$default(
            GtsExpressionGroup gtsExpressionGroup,
            String str,
            List list,
            int i,
            int i2,
            Object obj) {
        if ((i2 & 1) != 0) {
            str = gtsExpressionGroup.name;
        }
        if ((i2 & 2) != 0) {
            list = gtsExpressionGroup.expressions;
        }
        if ((i2 & 4) != 0) {
            i = gtsExpressionGroup.version;
        }
        return gtsExpressionGroup.copy(str, list, i);
    }

    /* renamed from: component1, reason: from getter */
    public final String getName() {
        return this.name;
    }

    public final List<GtsExpressionRaw> component2() {
        return this.expressions;
    }

    /* renamed from: component3, reason: from getter */
    public final int getVersion() {
        return this.version;
    }

    public final GtsExpressionGroup copy(
            String name, List<GtsExpressionRaw> expressions, int version) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(expressions, "expressions");
        return new GtsExpressionGroup(name, expressions, version);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GtsExpressionGroup)) {
            return false;
        }
        GtsExpressionGroup gtsExpressionGroup = (GtsExpressionGroup) other;
        return Intrinsics.areEqual(this.name, gtsExpressionGroup.name)
                && Intrinsics.areEqual(this.expressions, gtsExpressionGroup.expressions)
                && this.version == gtsExpressionGroup.version;
    }

    public final List<GtsExpressionRaw> getExpressions() {
        return this.expressions;
    }

    public final String getName() {
        return this.name;
    }

    public final int getVersion() {
        return this.version;
    }

    public int hashCode() {
        String str = this.name;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        List<GtsExpressionRaw> list = this.expressions;
        return Integer.hashCode(this.version)
                + ((hashCode + (list != null ? list.hashCode() : 0)) * 31);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("GtsExpressionGroup(name=");
        sb.append(this.name);
        sb.append(", expressions=");
        sb.append(this.expressions);
        sb.append(", version=");
        return Anchor$$ExternalSyntheticOutline0.m(sb, this.version, ")");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        Intrinsics.checkParameterIsNotNull(parcel, "parcel");
        parcel.writeString(this.name);
        List<GtsExpressionRaw> list = this.expressions;
        parcel.writeInt(list.size());
        Iterator<GtsExpressionRaw> it = list.iterator();
        while (it.hasNext()) {
            parcel.writeParcelable(it.next(), flags);
        }
        parcel.writeInt(this.version);
    }

    public /* synthetic */ GtsExpressionGroup(
            String str,
            List list,
            int i,
            int i2,
            DefaultConstructorMarker defaultConstructorMarker) {
        this(str, list, (i2 & 4) != 0 ? 1 : i);
    }
}
