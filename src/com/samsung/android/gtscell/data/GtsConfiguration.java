package com.samsung.android.gtscell.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;
import androidx.compose.animation.Scale$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;

import com.google.android.setupdesign.util.DeviceHelper;
import com.google.gson.annotations.SerializedName;
import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.android.parcel.Parcelize;

import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Parcelize
@Keep
@Metadata(
        bv = {1, 0, 3},
        d1 = {
            "\u0000D\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0000\n"
                + "\u0002\u0010\t\n"
                + "\u0002\b\u0007\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010%\n"
                + "\u0002\b\u001e\n"
                + "\u0002\u0010\u000b\n"
                + "\u0000\n"
                + "\u0002\u0010\u0000\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001Bk\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010"
                + "\t\u001a\u00020\u0003\u0012\u0006\u0010\n"
                + "\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\u0006\u0010\f\u001a\u00020\r"
                + "\u0012\u0006\u0010\u000e\u001a\u00020\r"
                + "\u0012\u0014\b\u0002\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0010¢\u0006\u0002\u0010\u0011J"
                + "\t\u0010!\u001a\u00020\u0003HÆ\u0003J\t\u0010\"\u001a\u00020\r"
                + "HÆ\u0003J\u0015\u0010#\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0010HÆ\u0003J"
                + "\t\u0010$\u001a\u00020\u0005HÆ\u0003J\t\u0010%\u001a\u00020\u0003HÆ\u0003J"
                + "\t\u0010&\u001a\u00020\u0003HÆ\u0003J\t\u0010'\u001a\u00020\u0003HÆ\u0003J"
                + "\t\u0010(\u001a\u00020\u0003HÆ\u0003J\t\u0010)\u001a\u00020\u0003HÆ\u0003J"
                + "\t\u0010*\u001a\u00020\u0003HÆ\u0003J\t\u0010+\u001a\u00020\r"
                + "HÆ\u0003J\u0083\u0001\u0010,\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010"
                + "\t\u001a\u00020\u00032\b\b\u0002\u0010\n"
                + "\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\r"
                + "2\b\b\u0002\u0010\u000e\u001a\u00020\r"
                + "2\u0014\b\u0002\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0010HÆ\u0001J"
                + "\t\u0010-\u001a\u00020\r"
                + "HÖ\u0001J\u0013\u0010.\u001a\u00020/2\b\u00100\u001a\u0004\u0018\u000101HÖ\u0003J"
                + "\t\u00102\u001a\u00020\r"
                + "HÖ\u0001J"
                + "\t\u00103\u001a\u00020\u0003HÖ\u0001J\u0019\u00104\u001a\u0002052\u0006\u00106\u001a\u0002072\u0006\u00108\u001a\u00020\r"
                + "HÖ\u0001R\u0016\u0010\u000b\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0016\u0010"
                + "\t\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u0014\u0010\u0013R\u0016\u0010\n"
                + "\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u0015\u0010\u0013R\u0016\u0010\b\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u0016\u0010\u0013R\"\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u00108\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0016\u0010\f\u001a\u00020\r"
                + "8\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u001b\u0010\u0013R\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0016\u0010\u0006\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u001e\u0010\u0013R\u0016\u0010\u0007\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u001f\u0010\u0013R\u0016\u0010\u000e\u001a\u00020\r"
                + "8\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b \u0010\u001a¨\u00069"
        },
        d2 = {
            "Lcom/samsung/android/gtscell/data/GtsConfiguration;",
            "Landroid/os/Parcelable;",
            "packageName",
            ApnSettings.MVNO_NONE,
            "packageVersionCode",
            ApnSettings.MVNO_NONE,
            "packageVersionName",
            "productName",
            "deviceName",
            "cscCountryCode",
            "cscSalesCode",
            "buildCharacteristics",
            "osVersion",
            ApnSettings.MVNO_NONE,
            "sepVersion",
            "extra",
            ApnSettings.MVNO_NONE,
            "(Ljava/lang/String;JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/util/Map;)V",
            "getBuildCharacteristics",
            "()Ljava/lang/String;",
            "getCscCountryCode",
            "getCscSalesCode",
            DeviceHelper.GET_DEVICE_NAME_METHOD,
            "getExtra",
            "()Ljava/util/Map;",
            "getOsVersion",
            "()I",
            "getPackageName",
            "getPackageVersionCode",
            "()J",
            "getPackageVersionName",
            "getProductName",
            "getSepVersion",
            "component1",
            "component10",
            "component11",
            "component2",
            "component3",
            "component4",
            "component5",
            "component6",
            "component7",
            "component8",
            "component9",
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
public final /* data */ class GtsConfiguration implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Creator();

    @SerializedName(FieldName.CONFIG_BUILD_CHARACTERISTICS)
    private final String buildCharacteristics;

    @SerializedName(FieldName.CONFIG_CSC_COUNTRY_CODE)
    private final String cscCountryCode;

    @SerializedName(FieldName.CONFIG_CSC_SALES_CODE)
    private final String cscSalesCode;

    @SerializedName(FieldName.CONFIG_DEVICE_NAME)
    private final String deviceName;

    @SerializedName(FieldName.CONFIG_EXTRA)
    private final Map<String, String> extra;

    @SerializedName(FieldName.CONFIG_OS_VERSION)
    private final int osVersion;

    @SerializedName(FieldName.CONFIG_PACKAGE_NAME)
    private final String packageName;

    @SerializedName(FieldName.CONFIG_PACKAGE_VERSION_CODE)
    private final long packageVersionCode;

    @SerializedName(FieldName.CONFIG_PACKAGE_VERSION_NAME)
    private final String packageVersionName;

    @SerializedName(FieldName.CONFIG_PRODUCT_NAME)
    private final String productName;

    @SerializedName(FieldName.CONFIG_SEP_VERSION)
    private final int sepVersion;

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
            long readLong = in.readLong();
            String readString2 = in.readString();
            String readString3 = in.readString();
            String readString4 = in.readString();
            String readString5 = in.readString();
            String readString6 = in.readString();
            String readString7 = in.readString();
            int readInt = in.readInt();
            int readInt2 = in.readInt();
            int readInt3 = in.readInt();
            LinkedHashMap linkedHashMap = new LinkedHashMap(readInt3);
            while (readInt3 != 0) {
                linkedHashMap.put(in.readString(), in.readString());
                readInt3--;
            }
            return new GtsConfiguration(
                    readString,
                    readLong,
                    readString2,
                    readString3,
                    readString4,
                    readString5,
                    readString6,
                    readString7,
                    readInt,
                    readInt2,
                    linkedHashMap);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new GtsConfiguration[i];
        }
    }

    public GtsConfiguration(
            String packageName,
            long j,
            String packageVersionName,
            String productName,
            String deviceName,
            String cscCountryCode,
            String cscSalesCode,
            String buildCharacteristics,
            int i,
            int i2,
            Map<String, String> extra) {
        Intrinsics.checkParameterIsNotNull(packageName, "packageName");
        Intrinsics.checkParameterIsNotNull(packageVersionName, "packageVersionName");
        Intrinsics.checkParameterIsNotNull(productName, "productName");
        Intrinsics.checkParameterIsNotNull(deviceName, "deviceName");
        Intrinsics.checkParameterIsNotNull(cscCountryCode, "cscCountryCode");
        Intrinsics.checkParameterIsNotNull(cscSalesCode, "cscSalesCode");
        Intrinsics.checkParameterIsNotNull(buildCharacteristics, "buildCharacteristics");
        Intrinsics.checkParameterIsNotNull(extra, "extra");
        this.packageName = packageName;
        this.packageVersionCode = j;
        this.packageVersionName = packageVersionName;
        this.productName = productName;
        this.deviceName = deviceName;
        this.cscCountryCode = cscCountryCode;
        this.cscSalesCode = cscSalesCode;
        this.buildCharacteristics = buildCharacteristics;
        this.osVersion = i;
        this.sepVersion = i2;
        this.extra = extra;
    }

    /* renamed from: component1, reason: from getter */
    public final String getPackageName() {
        return this.packageName;
    }

    /* renamed from: component10, reason: from getter */
    public final int getSepVersion() {
        return this.sepVersion;
    }

    public final Map<String, String> component11() {
        return this.extra;
    }

    /* renamed from: component2, reason: from getter */
    public final long getPackageVersionCode() {
        return this.packageVersionCode;
    }

    /* renamed from: component3, reason: from getter */
    public final String getPackageVersionName() {
        return this.packageVersionName;
    }

    /* renamed from: component4, reason: from getter */
    public final String getProductName() {
        return this.productName;
    }

    /* renamed from: component5, reason: from getter */
    public final String getDeviceName() {
        return this.deviceName;
    }

    /* renamed from: component6, reason: from getter */
    public final String getCscCountryCode() {
        return this.cscCountryCode;
    }

    /* renamed from: component7, reason: from getter */
    public final String getCscSalesCode() {
        return this.cscSalesCode;
    }

    /* renamed from: component8, reason: from getter */
    public final String getBuildCharacteristics() {
        return this.buildCharacteristics;
    }

    /* renamed from: component9, reason: from getter */
    public final int getOsVersion() {
        return this.osVersion;
    }

    public final GtsConfiguration copy(
            String packageName,
            long packageVersionCode,
            String packageVersionName,
            String productName,
            String deviceName,
            String cscCountryCode,
            String cscSalesCode,
            String buildCharacteristics,
            int osVersion,
            int sepVersion,
            Map<String, String> extra) {
        Intrinsics.checkParameterIsNotNull(packageName, "packageName");
        Intrinsics.checkParameterIsNotNull(packageVersionName, "packageVersionName");
        Intrinsics.checkParameterIsNotNull(productName, "productName");
        Intrinsics.checkParameterIsNotNull(deviceName, "deviceName");
        Intrinsics.checkParameterIsNotNull(cscCountryCode, "cscCountryCode");
        Intrinsics.checkParameterIsNotNull(cscSalesCode, "cscSalesCode");
        Intrinsics.checkParameterIsNotNull(buildCharacteristics, "buildCharacteristics");
        Intrinsics.checkParameterIsNotNull(extra, "extra");
        return new GtsConfiguration(
                packageName,
                packageVersionCode,
                packageVersionName,
                productName,
                deviceName,
                cscCountryCode,
                cscSalesCode,
                buildCharacteristics,
                osVersion,
                sepVersion,
                extra);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GtsConfiguration)) {
            return false;
        }
        GtsConfiguration gtsConfiguration = (GtsConfiguration) other;
        return Intrinsics.areEqual(this.packageName, gtsConfiguration.packageName)
                && this.packageVersionCode == gtsConfiguration.packageVersionCode
                && Intrinsics.areEqual(this.packageVersionName, gtsConfiguration.packageVersionName)
                && Intrinsics.areEqual(this.productName, gtsConfiguration.productName)
                && Intrinsics.areEqual(this.deviceName, gtsConfiguration.deviceName)
                && Intrinsics.areEqual(this.cscCountryCode, gtsConfiguration.cscCountryCode)
                && Intrinsics.areEqual(this.cscSalesCode, gtsConfiguration.cscSalesCode)
                && Intrinsics.areEqual(
                        this.buildCharacteristics, gtsConfiguration.buildCharacteristics)
                && this.osVersion == gtsConfiguration.osVersion
                && this.sepVersion == gtsConfiguration.sepVersion
                && Intrinsics.areEqual(this.extra, gtsConfiguration.extra);
    }

    public final String getBuildCharacteristics() {
        return this.buildCharacteristics;
    }

    public final String getCscCountryCode() {
        return this.cscCountryCode;
    }

    public final String getCscSalesCode() {
        return this.cscSalesCode;
    }

    public final String getDeviceName() {
        return this.deviceName;
    }

    public final Map<String, String> getExtra() {
        return this.extra;
    }

    public final int getOsVersion() {
        return this.osVersion;
    }

    public final String getPackageName() {
        return this.packageName;
    }

    public final long getPackageVersionCode() {
        return this.packageVersionCode;
    }

    public final String getPackageVersionName() {
        return this.packageVersionName;
    }

    public final String getProductName() {
        return this.productName;
    }

    public final int getSepVersion() {
        return this.sepVersion;
    }

    public int hashCode() {
        String str = this.packageName;
        int m =
                Scale$$ExternalSyntheticOutline0.m(
                        (str != null ? str.hashCode() : 0) * 31, 31, this.packageVersionCode);
        String str2 = this.packageVersionName;
        int hashCode = (m + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.productName;
        int hashCode2 = (hashCode + (str3 != null ? str3.hashCode() : 0)) * 31;
        String str4 = this.deviceName;
        int hashCode3 = (hashCode2 + (str4 != null ? str4.hashCode() : 0)) * 31;
        String str5 = this.cscCountryCode;
        int hashCode4 = (hashCode3 + (str5 != null ? str5.hashCode() : 0)) * 31;
        String str6 = this.cscSalesCode;
        int hashCode5 = (hashCode4 + (str6 != null ? str6.hashCode() : 0)) * 31;
        String str7 = this.buildCharacteristics;
        int m2 =
                KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(
                        this.sepVersion,
                        KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(
                                this.osVersion,
                                (hashCode5 + (str7 != null ? str7.hashCode() : 0)) * 31,
                                31),
                        31);
        Map<String, String> map = this.extra;
        return m2 + (map != null ? map.hashCode() : 0);
    }

    public String toString() {
        return "GtsConfiguration(packageName="
                + this.packageName
                + ", packageVersionCode="
                + this.packageVersionCode
                + ", packageVersionName="
                + this.packageVersionName
                + ", productName="
                + this.productName
                + ", deviceName="
                + this.deviceName
                + ", cscCountryCode="
                + this.cscCountryCode
                + ", cscSalesCode="
                + this.cscSalesCode
                + ", buildCharacteristics="
                + this.buildCharacteristics
                + ", osVersion="
                + this.osVersion
                + ", sepVersion="
                + this.sepVersion
                + ", extra="
                + this.extra
                + ")";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        Intrinsics.checkParameterIsNotNull(parcel, "parcel");
        parcel.writeString(this.packageName);
        parcel.writeLong(this.packageVersionCode);
        parcel.writeString(this.packageVersionName);
        parcel.writeString(this.productName);
        parcel.writeString(this.deviceName);
        parcel.writeString(this.cscCountryCode);
        parcel.writeString(this.cscSalesCode);
        parcel.writeString(this.buildCharacteristics);
        parcel.writeInt(this.osVersion);
        parcel.writeInt(this.sepVersion);
        Map<String, String> map = this.extra;
        parcel.writeInt(map.size());
        for (Map.Entry<String, String> entry : map.entrySet()) {
            parcel.writeString(entry.getKey());
            parcel.writeString(entry.getValue());
        }
    }

    public /* synthetic */ GtsConfiguration(
            String str,
            long j,
            String str2,
            String str3,
            String str4,
            String str5,
            String str6,
            String str7,
            int i,
            int i2,
            Map map,
            int i3,
            DefaultConstructorMarker defaultConstructorMarker) {
        this(
                str,
                j,
                str2,
                str3,
                str4,
                str5,
                str6,
                str7,
                i,
                i2,
                (i3 & 1024) != 0 ? new LinkedHashMap() : map);
    }
}
