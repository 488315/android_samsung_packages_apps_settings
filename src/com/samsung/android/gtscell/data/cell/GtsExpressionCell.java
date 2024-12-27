package com.samsung.android.gtscell.data.cell;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

import com.google.gson.annotations.SerializedName;
import com.samsung.android.gtscell.data.FieldName;
import com.samsung.android.gtscell.data.GtsCellProviderInfo;
import com.samsung.android.gtscell.data.GtsConfiguration;
import com.samsung.android.gtscell.data.GtsExpressionGroup;
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
            "\u0000L\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010 \n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0015\n"
                + "\u0002\u0010\u000b\n"
                + "\u0000\n"
                + "\u0002\u0010\u0000\n"
                + "\u0002\b\u0004\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0087\b\u0018\u0000"
                + " .2\u00020\u0001:\u0001.B?\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n"
                + "0\t\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\f\u001a\u00020\r"
                + "¢\u0006\u0002\u0010\u000eJ\t\u0010\u001a\u001a\u00020\u0003HÆ\u0003J"
                + "\t\u0010\u001b\u001a\u00020\u0005HÆ\u0003J"
                + "\t\u0010\u001c\u001a\u00020\u0007HÆ\u0003J\u000f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\n"
                + "0\tHÆ\u0003J\u000b\u0010\u001e\u001a\u0004\u0018\u00010\u0003HÆ\u0003J"
                + "\t\u0010\u001f\u001a\u00020\r"
                + "HÆ\u0003JM\u0010"
                + " \u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n"
                + "0\t2\n"
                + "\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\f\u001a\u00020\r"
                + "HÆ\u0001J\t\u0010!\u001a\u00020\r"
                + "HÖ\u0001J\u0013\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010%HÖ\u0003J\u0006\u0010&\u001a\u00020\r"
                + "J\t\u0010'\u001a\u00020\r"
                + "HÖ\u0001J"
                + "\t\u0010(\u001a\u00020\u0003HÖ\u0001J\u0019\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020\r"
                + "HÖ\u0001R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001c\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n"
                + "0\t8\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0018\u0010\u000b\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u0015\u0010\u0010R\u0016\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0016\u0010\f\u001a\u00020\r"
                + "8\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u0018\u0010\u0019¨\u0006/"
        },
        d2 = {
            "Lcom/samsung/android/gtscell/data/cell/GtsExpressionCell;",
            "Landroid/os/Parcelable;",
            "category",
            ApnSettings.MVNO_NONE,
            "configuration",
            "Lcom/samsung/android/gtscell/data/GtsConfiguration;",
            "providerInfo",
            "Lcom/samsung/android/gtscell/data/GtsCellProviderInfo;",
            "groups",
            ApnSettings.MVNO_NONE,
            "Lcom/samsung/android/gtscell/data/GtsExpressionGroup;",
            "privateCategory",
            FieldName.VERSION,
            ApnSettings.MVNO_NONE,
            "(Ljava/lang/String;Lcom/samsung/android/gtscell/data/GtsConfiguration;Lcom/samsung/android/gtscell/data/GtsCellProviderInfo;Ljava/util/List;Ljava/lang/String;I)V",
            "getCategory",
            "()Ljava/lang/String;",
            "getConfiguration",
            "()Lcom/samsung/android/gtscell/data/GtsConfiguration;",
            "getGroups",
            "()Ljava/util/List;",
            "getPrivateCategory",
            "getProviderInfo",
            "()Lcom/samsung/android/gtscell/data/GtsCellProviderInfo;",
            "getVersion",
            "()I",
            "component1",
            "component2",
            "component3",
            "component4",
            "component5",
            "component6",
            "copy",
            "describeContents",
            "equals",
            ApnSettings.MVNO_NONE,
            "other",
            ApnSettings.MVNO_NONE,
            "getExpressionCount",
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
public final /* data */ class GtsExpressionCell implements Parcelable {
    private static final int VERSION = 1;

    @SerializedName("category")
    private final String category;

    @SerializedName(FieldName.CONFIG)
    private final GtsConfiguration configuration;

    @SerializedName(FieldName.ITEMS)
    private final List<GtsExpressionGroup> groups;

    @SerializedName(FieldName.PRIVATE_CATEGORY)
    private final String privateCategory;

    @SerializedName(FieldName.PROVIDER_INFO)
    private final GtsCellProviderInfo providerInfo;

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
            GtsConfiguration gtsConfiguration =
                    (GtsConfiguration) in.readParcelable(GtsExpressionCell.class.getClassLoader());
            GtsCellProviderInfo gtsCellProviderInfo =
                    (GtsCellProviderInfo)
                            in.readParcelable(GtsExpressionCell.class.getClassLoader());
            int readInt = in.readInt();
            ArrayList arrayList = new ArrayList(readInt);
            while (readInt != 0) {
                arrayList.add(
                        (GtsExpressionGroup)
                                in.readParcelable(GtsExpressionCell.class.getClassLoader()));
                readInt--;
            }
            return new GtsExpressionCell(
                    readString,
                    gtsConfiguration,
                    gtsCellProviderInfo,
                    arrayList,
                    in.readString(),
                    in.readInt());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new GtsExpressionCell[i];
        }
    }

    public GtsExpressionCell(
            String category,
            GtsConfiguration configuration,
            GtsCellProviderInfo providerInfo,
            List<GtsExpressionGroup> groups,
            String str,
            int i) {
        Intrinsics.checkParameterIsNotNull(category, "category");
        Intrinsics.checkParameterIsNotNull(configuration, "configuration");
        Intrinsics.checkParameterIsNotNull(providerInfo, "providerInfo");
        Intrinsics.checkParameterIsNotNull(groups, "groups");
        this.category = category;
        this.configuration = configuration;
        this.providerInfo = providerInfo;
        this.groups = groups;
        this.privateCategory = str;
        this.version = i;
    }

    public static /* synthetic */ GtsExpressionCell copy$default(
            GtsExpressionCell gtsExpressionCell,
            String str,
            GtsConfiguration gtsConfiguration,
            GtsCellProviderInfo gtsCellProviderInfo,
            List list,
            String str2,
            int i,
            int i2,
            Object obj) {
        if ((i2 & 1) != 0) {
            str = gtsExpressionCell.category;
        }
        if ((i2 & 2) != 0) {
            gtsConfiguration = gtsExpressionCell.configuration;
        }
        GtsConfiguration gtsConfiguration2 = gtsConfiguration;
        if ((i2 & 4) != 0) {
            gtsCellProviderInfo = gtsExpressionCell.providerInfo;
        }
        GtsCellProviderInfo gtsCellProviderInfo2 = gtsCellProviderInfo;
        if ((i2 & 8) != 0) {
            list = gtsExpressionCell.groups;
        }
        List list2 = list;
        if ((i2 & 16) != 0) {
            str2 = gtsExpressionCell.privateCategory;
        }
        String str3 = str2;
        if ((i2 & 32) != 0) {
            i = gtsExpressionCell.version;
        }
        return gtsExpressionCell.copy(str, gtsConfiguration2, gtsCellProviderInfo2, list2, str3, i);
    }

    /* renamed from: component1, reason: from getter */
    public final String getCategory() {
        return this.category;
    }

    /* renamed from: component2, reason: from getter */
    public final GtsConfiguration getConfiguration() {
        return this.configuration;
    }

    /* renamed from: component3, reason: from getter */
    public final GtsCellProviderInfo getProviderInfo() {
        return this.providerInfo;
    }

    public final List<GtsExpressionGroup> component4() {
        return this.groups;
    }

    /* renamed from: component5, reason: from getter */
    public final String getPrivateCategory() {
        return this.privateCategory;
    }

    /* renamed from: component6, reason: from getter */
    public final int getVersion() {
        return this.version;
    }

    public final GtsExpressionCell copy(
            String category,
            GtsConfiguration configuration,
            GtsCellProviderInfo providerInfo,
            List<GtsExpressionGroup> groups,
            String privateCategory,
            int version) {
        Intrinsics.checkParameterIsNotNull(category, "category");
        Intrinsics.checkParameterIsNotNull(configuration, "configuration");
        Intrinsics.checkParameterIsNotNull(providerInfo, "providerInfo");
        Intrinsics.checkParameterIsNotNull(groups, "groups");
        return new GtsExpressionCell(
                category, configuration, providerInfo, groups, privateCategory, version);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GtsExpressionCell)) {
            return false;
        }
        GtsExpressionCell gtsExpressionCell = (GtsExpressionCell) other;
        return Intrinsics.areEqual(this.category, gtsExpressionCell.category)
                && Intrinsics.areEqual(this.configuration, gtsExpressionCell.configuration)
                && Intrinsics.areEqual(this.providerInfo, gtsExpressionCell.providerInfo)
                && Intrinsics.areEqual(this.groups, gtsExpressionCell.groups)
                && Intrinsics.areEqual(this.privateCategory, gtsExpressionCell.privateCategory)
                && this.version == gtsExpressionCell.version;
    }

    public final String getCategory() {
        return this.category;
    }

    public final GtsConfiguration getConfiguration() {
        return this.configuration;
    }

    public final int getExpressionCount() {
        Iterator<T> it = this.groups.iterator();
        int i = 0;
        while (it.hasNext()) {
            i += ((GtsExpressionGroup) it.next()).getExpressions().size();
        }
        return i;
    }

    public final List<GtsExpressionGroup> getGroups() {
        return this.groups;
    }

    public final String getPrivateCategory() {
        return this.privateCategory;
    }

    public final GtsCellProviderInfo getProviderInfo() {
        return this.providerInfo;
    }

    public final int getVersion() {
        return this.version;
    }

    public int hashCode() {
        String str = this.category;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        GtsConfiguration gtsConfiguration = this.configuration;
        int hashCode2 =
                (hashCode + (gtsConfiguration != null ? gtsConfiguration.hashCode() : 0)) * 31;
        GtsCellProviderInfo gtsCellProviderInfo = this.providerInfo;
        int hashCode3 =
                (hashCode2 + (gtsCellProviderInfo != null ? gtsCellProviderInfo.hashCode() : 0))
                        * 31;
        List<GtsExpressionGroup> list = this.groups;
        int hashCode4 = (hashCode3 + (list != null ? list.hashCode() : 0)) * 31;
        String str2 = this.privateCategory;
        return Integer.hashCode(this.version)
                + ((hashCode4 + (str2 != null ? str2.hashCode() : 0)) * 31);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("GtsExpressionCell(category=");
        sb.append(this.category);
        sb.append(", configuration=");
        sb.append(this.configuration);
        sb.append(", providerInfo=");
        sb.append(this.providerInfo);
        sb.append(", groups=");
        sb.append(this.groups);
        sb.append(", privateCategory=");
        sb.append(this.privateCategory);
        sb.append(", version=");
        return Anchor$$ExternalSyntheticOutline0.m(sb, this.version, ")");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        Intrinsics.checkParameterIsNotNull(parcel, "parcel");
        parcel.writeString(this.category);
        parcel.writeParcelable(this.configuration, flags);
        parcel.writeParcelable(this.providerInfo, flags);
        List<GtsExpressionGroup> list = this.groups;
        parcel.writeInt(list.size());
        Iterator<GtsExpressionGroup> it = list.iterator();
        while (it.hasNext()) {
            parcel.writeParcelable(it.next(), flags);
        }
        parcel.writeString(this.privateCategory);
        parcel.writeInt(this.version);
    }

    public /* synthetic */ GtsExpressionCell(
            String str,
            GtsConfiguration gtsConfiguration,
            GtsCellProviderInfo gtsCellProviderInfo,
            List list,
            String str2,
            int i,
            int i2,
            DefaultConstructorMarker defaultConstructorMarker) {
        this(str, gtsConfiguration, gtsCellProviderInfo, list, str2, (i2 & 32) != 0 ? 1 : i);
    }
}
