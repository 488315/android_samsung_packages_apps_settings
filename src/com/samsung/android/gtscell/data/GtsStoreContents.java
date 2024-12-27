package com.samsung.android.gtscell.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;
import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
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
            "\u0000F\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0000\n"
                + "\u0002\u0010 \n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0004\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\b\n"
                + "\u0002\u0010\b\n"
                + "\u0000\n"
                + "\u0002\u0010\u000b\n"
                + "\u0000\n"
                + "\u0002\u0010\u0000\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J"
                + "\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J#\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0001J"
                + "\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018HÖ\u0003J"
                + "\t\u0010\u0019\u001a\u00020\u0014HÖ\u0001J"
                + "\t\u0010\u001a\u001a\u00020\u0003HÖ\u0001J\u0019\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0014HÖ\u0001R\u001c\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00058\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n"
                + "\u001a\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\f\u0010\r"
                + "R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006 "
        },
        d2 = {
            "Lcom/samsung/android/gtscell/data/GtsStoreContents;",
            "Landroid/os/Parcelable;",
            "typeName",
            ApnSettings.MVNO_NONE,
            "packages",
            ApnSettings.MVNO_NONE,
            "Lcom/samsung/android/gtscell/data/GtsStorePackage;",
            "(Ljava/lang/String;Ljava/util/List;)V",
            "getPackages",
            "()Ljava/util/List;",
            "type",
            "Lcom/samsung/android/gtscell/data/GtsStoreType;",
            "getType",
            "()Lcom/samsung/android/gtscell/data/GtsStoreType;",
            "getTypeName",
            "()Ljava/lang/String;",
            "component1",
            "component2",
            "copy",
            "describeContents",
            ApnSettings.MVNO_NONE,
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
            "gtscell_release"
        },
        k = 1,
        mv = {1, 1, 16})
/* loaded from: classes4.dex */
public final /* data */ class GtsStoreContents implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Creator();

    @SerializedName(FieldName.ITEM_STORE_PACKAGES)
    private final List<GtsStorePackage> packages;

    @SerializedName(FieldName.ITEM_STORE_TYPE)
    private final String typeName;

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
                arrayList.add((GtsStorePackage) GtsStorePackage.CREATOR.createFromParcel(in));
                readInt--;
            }
            return new GtsStoreContents(readString, arrayList);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new GtsStoreContents[i];
        }
    }

    public GtsStoreContents(String typeName, List<GtsStorePackage> packages) {
        Intrinsics.checkParameterIsNotNull(typeName, "typeName");
        Intrinsics.checkParameterIsNotNull(packages, "packages");
        this.typeName = typeName;
        this.packages = packages;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ GtsStoreContents copy$default(
            GtsStoreContents gtsStoreContents, String str, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            str = gtsStoreContents.typeName;
        }
        if ((i & 2) != 0) {
            list = gtsStoreContents.packages;
        }
        return gtsStoreContents.copy(str, list);
    }

    /* renamed from: component1, reason: from getter */
    public final String getTypeName() {
        return this.typeName;
    }

    public final List<GtsStorePackage> component2() {
        return this.packages;
    }

    public final GtsStoreContents copy(String typeName, List<GtsStorePackage> packages) {
        Intrinsics.checkParameterIsNotNull(typeName, "typeName");
        Intrinsics.checkParameterIsNotNull(packages, "packages");
        return new GtsStoreContents(typeName, packages);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GtsStoreContents)) {
            return false;
        }
        GtsStoreContents gtsStoreContents = (GtsStoreContents) other;
        return Intrinsics.areEqual(this.typeName, gtsStoreContents.typeName)
                && Intrinsics.areEqual(this.packages, gtsStoreContents.packages);
    }

    public final List<GtsStorePackage> getPackages() {
        return this.packages;
    }

    public final GtsStoreType getType() {
        try {
            return GtsStoreType.valueOf(this.typeName);
        } catch (Exception unused) {
            return GtsStoreType.NONE;
        }
    }

    public final String getTypeName() {
        return this.typeName;
    }

    public int hashCode() {
        String str = this.typeName;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        List<GtsStorePackage> list = this.packages;
        return hashCode + (list != null ? list.hashCode() : 0);
    }

    public String toString() {
        return "GtsStoreContents(typeName=" + this.typeName + ", packages=" + this.packages + ")";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        Intrinsics.checkParameterIsNotNull(parcel, "parcel");
        parcel.writeString(this.typeName);
        List<GtsStorePackage> list = this.packages;
        parcel.writeInt(list.size());
        Iterator<GtsStorePackage> it = list.iterator();
        while (it.hasNext()) {
            it.next().writeToParcel(parcel, 0);
        }
    }
}
