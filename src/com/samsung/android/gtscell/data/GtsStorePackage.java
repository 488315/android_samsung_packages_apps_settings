package com.samsung.android.gtscell.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;
import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.android.parcel.Parcelize;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Parcelize
@Keep
@Metadata(
        bv = {1, 0, 3},
        d1 = {
            "\u00004\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\t\n"
                + "\u0002\u0010\b\n"
                + "\u0000\n"
                + "\u0002\u0010\u000b\n"
                + "\u0000\n"
                + "\u0002\u0010\u0000\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005J"
                + "\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\n"
                + "\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u001f\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n"
                + "\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003HÆ\u0001J"
                + "\t\u0010\f\u001a\u00020\r"
                + "HÖ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011HÖ\u0003J"
                + "\t\u0010\u0012\u001a\u00020\r"
                + "HÖ\u0001J"
                + "\t\u0010\u0013\u001a\u00020\u0003HÖ\u0001J\u0019\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\r"
                + "HÖ\u0001R\u0018\u0010\u0004\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0019"
        },
        d2 = {
            "Lcom/samsung/android/gtscell/data/GtsStorePackage;",
            "Landroid/os/Parcelable;",
            "packageName",
            ApnSettings.MVNO_NONE,
            "contentType",
            "(Ljava/lang/String;Ljava/lang/String;)V",
            "getContentType",
            "()Ljava/lang/String;",
            "getPackageName",
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
public final /* data */ class GtsStorePackage implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Creator();

    @SerializedName(FieldName.ITEM_STORE_CONTENT_TYPE)
    private final String contentType;

    @SerializedName(FieldName.ITEM_STORE_PACKAGE)
    private final String packageName;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            k = 3,
            mv = {1, 1, 16})
    public static class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel in) {
            Intrinsics.checkParameterIsNotNull(in, "in");
            return new GtsStorePackage(in.readString(), in.readString());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new GtsStorePackage[i];
        }
    }

    public GtsStorePackage(String packageName, String str) {
        Intrinsics.checkParameterIsNotNull(packageName, "packageName");
        this.packageName = packageName;
        this.contentType = str;
    }

    public static /* synthetic */ GtsStorePackage copy$default(
            GtsStorePackage gtsStorePackage, String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = gtsStorePackage.packageName;
        }
        if ((i & 2) != 0) {
            str2 = gtsStorePackage.contentType;
        }
        return gtsStorePackage.copy(str, str2);
    }

    /* renamed from: component1, reason: from getter */
    public final String getPackageName() {
        return this.packageName;
    }

    /* renamed from: component2, reason: from getter */
    public final String getContentType() {
        return this.contentType;
    }

    public final GtsStorePackage copy(String packageName, String contentType) {
        Intrinsics.checkParameterIsNotNull(packageName, "packageName");
        return new GtsStorePackage(packageName, contentType);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GtsStorePackage)) {
            return false;
        }
        GtsStorePackage gtsStorePackage = (GtsStorePackage) other;
        return Intrinsics.areEqual(this.packageName, gtsStorePackage.packageName)
                && Intrinsics.areEqual(this.contentType, gtsStorePackage.contentType);
    }

    public final String getContentType() {
        return this.contentType;
    }

    public final String getPackageName() {
        return this.packageName;
    }

    public int hashCode() {
        String str = this.packageName;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.contentType;
        return hashCode + (str2 != null ? str2.hashCode() : 0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("GtsStorePackage(packageName=");
        sb.append(this.packageName);
        sb.append(", contentType=");
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, this.contentType, ")");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        Intrinsics.checkParameterIsNotNull(parcel, "parcel");
        parcel.writeString(this.packageName);
        parcel.writeString(this.contentType);
    }
}
