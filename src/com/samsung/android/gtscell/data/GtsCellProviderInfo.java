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
                + "\u0002\u0010\b\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0010\n"
                + "\u0002\u0010\u000b\n"
                + "\u0000\n"
                + "\u0002\u0010\u0000\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\bJ"
                + "\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J"
                + "\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J"
                + "\t\u0010\u0011\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u0012\u001a\u0004\u0018\u00010\u0005HÆ\u0003J3\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\n"
                + "\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005HÆ\u0001J"
                + "\t\u0010\u0014\u001a\u00020\u0003HÖ\u0001J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018HÖ\u0003J"
                + "\t\u0010\u0019\u001a\u00020\u0003HÖ\u0001J"
                + "\t\u0010\u001a\u001a\u00020\u0005HÖ\u0001J\u0019\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0003HÖ\u0001R\u0018\u0010\u0007\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\t\u0010\n"
                + "R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u000b\u0010\fR\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\r"
                + "\u0010\n"
                + "R\u0016\u0010\u0006\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u000e\u0010\n"
                + "¨\u0006 "
        },
        d2 = {
            "Lcom/samsung/android/gtscell/data/GtsCellProviderInfo;",
            "Landroid/os/Parcelable;",
            "icon",
            ApnSettings.MVNO_NONE,
            "label",
            ApnSettings.MVNO_NONE,
            "packageName",
            "authority",
            "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V",
            "getAuthority",
            "()Ljava/lang/String;",
            "getIcon",
            "()I",
            "getLabel",
            "getPackageName",
            "component1",
            "component2",
            "component3",
            "component4",
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
            "gtscell_release"
        },
        k = 1,
        mv = {1, 1, 16})
/* loaded from: classes4.dex */
public final /* data */ class GtsCellProviderInfo implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Creator();

    @SerializedName(FieldName.PROVIDER_INFO_AUTHORITY)
    private final String authority;

    @SerializedName(FieldName.PROVIDER_INFO_ICON)
    private final int icon;

    @SerializedName(FieldName.PROVIDER_INFO_LABEL)
    private final String label;

    @SerializedName(FieldName.PROVIDER_INFO_PACKAGE_NAME)
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
            return new GtsCellProviderInfo(
                    in.readInt(), in.readString(), in.readString(), in.readString());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new GtsCellProviderInfo[i];
        }
    }

    public GtsCellProviderInfo(int i, String label, String packageName, String str) {
        Intrinsics.checkParameterIsNotNull(label, "label");
        Intrinsics.checkParameterIsNotNull(packageName, "packageName");
        this.icon = i;
        this.label = label;
        this.packageName = packageName;
        this.authority = str;
    }

    public static /* synthetic */ GtsCellProviderInfo copy$default(
            GtsCellProviderInfo gtsCellProviderInfo,
            int i,
            String str,
            String str2,
            String str3,
            int i2,
            Object obj) {
        if ((i2 & 1) != 0) {
            i = gtsCellProviderInfo.icon;
        }
        if ((i2 & 2) != 0) {
            str = gtsCellProviderInfo.label;
        }
        if ((i2 & 4) != 0) {
            str2 = gtsCellProviderInfo.packageName;
        }
        if ((i2 & 8) != 0) {
            str3 = gtsCellProviderInfo.authority;
        }
        return gtsCellProviderInfo.copy(i, str, str2, str3);
    }

    /* renamed from: component1, reason: from getter */
    public final int getIcon() {
        return this.icon;
    }

    /* renamed from: component2, reason: from getter */
    public final String getLabel() {
        return this.label;
    }

    /* renamed from: component3, reason: from getter */
    public final String getPackageName() {
        return this.packageName;
    }

    /* renamed from: component4, reason: from getter */
    public final String getAuthority() {
        return this.authority;
    }

    public final GtsCellProviderInfo copy(
            int icon, String label, String packageName, String authority) {
        Intrinsics.checkParameterIsNotNull(label, "label");
        Intrinsics.checkParameterIsNotNull(packageName, "packageName");
        return new GtsCellProviderInfo(icon, label, packageName, authority);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GtsCellProviderInfo)) {
            return false;
        }
        GtsCellProviderInfo gtsCellProviderInfo = (GtsCellProviderInfo) other;
        return this.icon == gtsCellProviderInfo.icon
                && Intrinsics.areEqual(this.label, gtsCellProviderInfo.label)
                && Intrinsics.areEqual(this.packageName, gtsCellProviderInfo.packageName)
                && Intrinsics.areEqual(this.authority, gtsCellProviderInfo.authority);
    }

    public final String getAuthority() {
        return this.authority;
    }

    public final int getIcon() {
        return this.icon;
    }

    public final String getLabel() {
        return this.label;
    }

    public final String getPackageName() {
        return this.packageName;
    }

    public int hashCode() {
        int hashCode = Integer.hashCode(this.icon) * 31;
        String str = this.label;
        int hashCode2 = (hashCode + (str != null ? str.hashCode() : 0)) * 31;
        String str2 = this.packageName;
        int hashCode3 = (hashCode2 + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.authority;
        return hashCode3 + (str3 != null ? str3.hashCode() : 0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("GtsCellProviderInfo(icon=");
        sb.append(this.icon);
        sb.append(", label=");
        sb.append(this.label);
        sb.append(", packageName=");
        sb.append(this.packageName);
        sb.append(", authority=");
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, this.authority, ")");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        Intrinsics.checkParameterIsNotNull(parcel, "parcel");
        parcel.writeInt(this.icon);
        parcel.writeString(this.label);
        parcel.writeString(this.packageName);
        parcel.writeString(this.authority);
    }
}
