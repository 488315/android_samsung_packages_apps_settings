package com.samsung.android.gtscell.data;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

import com.google.gson.annotations.SerializedName;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
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
            "\u0000J\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010$\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\t\n"
                + "\u0002\b\u001f\n"
                + "\u0002\u0010\u000b\n"
                + "\u0000\n"
                + "\u0002\u0010\u0000\n"
                + "\u0002\b\u0007\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0087\b\u0018\u0000"
                + " ;2\u00020\u0001:\u0001;Bm\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0012\u0010"
                + "\t\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\n"
                + "\u0012\u0014\u0010\u000b\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0000\u0018\u00010\n"
                + "\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r"
                + "\u0012\u0006\u0010\u000e\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0003¢\u0006\u0002\u0010\u0010J"
                + "\t\u0010 \u001a\u00020\u0003HÆ\u0003J\t\u0010!\u001a\u00020\u0005HÆ\u0003J"
                + "\t\u0010\"\u001a\u00020\u0007HÆ\u0003J"
                + "\t\u0010#\u001a\u00020\u0007HÆ\u0003J\u0015\u0010$\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\n"
                + "HÆ\u0003J\u0017\u0010%\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0000\u0018\u00010\n"
                + "HÆ\u0003J\u0010\u0010&\u001a\u0004\u0018\u00010\r"
                + "HÆ\u0003¢\u0006\u0002\u0010\u001bJ\t\u0010'\u001a\u00020\u0003HÆ\u0003J"
                + "\t\u0010(\u001a\u00020\u0003HÆ\u0003J\u0084\u0001\u0010)\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\u0014\b\u0002\u0010"
                + "\t\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\n"
                + "2\u0016\b\u0002\u0010\u000b\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0000\u0018\u00010\n"
                + "2\n"
                + "\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r"
                + "2\b\b\u0002\u0010\u000e\u001a\u00020\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u0003HÆ\u0001¢\u0006\u0002\u0010*J"
                + "\t\u0010+\u001a\u00020\u0003HÖ\u0001J\u0013\u0010,\u001a\u00020-2\b\u0010.\u001a\u0004\u0018\u00010/HÖ\u0003J\u0011\u00100\u001a\u0002H1\"\u0004\b\u0000\u00101¢\u0006\u0002\u00102J\b\u00103\u001a\u0004\u0018\u00010\u0007J"
                + "\t\u00104\u001a\u00020\u0003HÖ\u0001J"
                + "\t\u00105\u001a\u00020\u0007HÖ\u0001J\u0019\u00106\u001a\u0002072\u0006\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020\u0003HÖ\u0001R$\u0010\u000b\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0000\u0018\u00010\n"
                + "8\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0016\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0016\u0010\u000e\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u0017\u0010\u0018R\"\u0010"
                + "\t\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\n"
                + "8\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u0019\u0010\u0012R\u001a\u0010\f\u001a\u0004\u0018\u00010\r"
                + "8\u0006X\u0087\u0004¢\u0006\n\n"
                + "\u0002\u0010\u001c\u001a\u0004\b\u001a\u0010\u001bR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u001d\u0010\u0018R\u0016\u0010\b\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u001e\u0010\u0016R\u0016\u0010\u000f\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u001f\u0010\u0018¨\u0006<"
        },
        d2 = {
            "Lcom/samsung/android/gtscell/data/GtsItem;",
            "Landroid/os/Parcelable;",
            "type",
            ApnSettings.MVNO_NONE,
            "format",
            "Lcom/samsung/android/gtscell/data/GtsItemFormat;",
            GoodSettingsContract.EXTRA_NAME.POLICY_KEY,
            ApnSettings.MVNO_NONE,
            "value",
            "tags",
            ApnSettings.MVNO_NONE,
            "embeddedItems",
            "timeout",
            ApnSettings.MVNO_NONE,
            FieldName.REVISION,
            FieldName.VERSION,
            "(ILcom/samsung/android/gtscell/data/GtsItemFormat;Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/util/Map;Ljava/lang/Long;II)V",
            "getEmbeddedItems",
            "()Ljava/util/Map;",
            "getFormat",
            "()Lcom/samsung/android/gtscell/data/GtsItemFormat;",
            "getKey",
            "()Ljava/lang/String;",
            "getRevision",
            "()I",
            "getTags",
            "getTimeout",
            "()Ljava/lang/Long;",
            "Ljava/lang/Long;",
            "getType",
            "getValue",
            "getVersion",
            "component1",
            "component2",
            "component3",
            "component4",
            "component5",
            "component6",
            "component7",
            "component8",
            "component9",
            "copy",
            "(ILcom/samsung/android/gtscell/data/GtsItemFormat;Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/util/Map;Ljava/lang/Long;II)Lcom/samsung/android/gtscell/data/GtsItem;",
            "describeContents",
            "equals",
            ApnSettings.MVNO_NONE,
            "other",
            ApnSettings.MVNO_NONE,
            "getTypedValue",
            "T",
            "()Ljava/lang/Object;",
            "getUID",
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
public final /* data */ class GtsItem implements Parcelable {
    public static final long DEFAULT_TIMEOUT = 1000;
    public static final String KEY_UID = "_gts_item_uid";
    public static final int TYPE_DEPENDENCY = 2;
    public static final int TYPE_EMBEDDED = 3;
    public static final int TYPE_ITEM = 1;
    private static final int VERSION = 1;

    @SerializedName(FieldName.ITEM_EMBEDDED_ITEMS)
    private final Map<String, GtsItem> embeddedItems;

    @SerializedName(FieldName.ITEM_FORMAT)
    private final GtsItemFormat format;

    @SerializedName(FieldName.ITEM_KEY)
    private final String key;

    @SerializedName(FieldName.REVISION)
    private final int revision;

    @SerializedName(FieldName.ITEM_TAGS)
    private final Map<String, String> tags;

    @SerializedName(FieldName.ITEM_TIMEOUT)
    private final Long timeout;

    @SerializedName(FieldName.ITEM_TYPE)
    private final int type;

    @SerializedName(FieldName.ITEM_VALUE)
    private final String value;

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
            LinkedHashMap linkedHashMap;
            Intrinsics.checkParameterIsNotNull(in, "in");
            int readInt = in.readInt();
            GtsItemFormat gtsItemFormat =
                    (GtsItemFormat) Enum.valueOf(GtsItemFormat.class, in.readString());
            String readString = in.readString();
            String readString2 = in.readString();
            int readInt2 = in.readInt();
            LinkedHashMap linkedHashMap2 = new LinkedHashMap(readInt2);
            while (readInt2 != 0) {
                linkedHashMap2.put(in.readString(), in.readString());
                readInt2--;
            }
            if (in.readInt() != 0) {
                int readInt3 = in.readInt();
                linkedHashMap = new LinkedHashMap(readInt3);
                while (readInt3 != 0) {
                    linkedHashMap.put(
                            in.readString(), (GtsItem) GtsItem.CREATOR.createFromParcel(in));
                    readInt3--;
                }
            } else {
                linkedHashMap = null;
            }
            return new GtsItem(
                    readInt,
                    gtsItemFormat,
                    readString,
                    readString2,
                    linkedHashMap2,
                    linkedHashMap,
                    in.readInt() != 0 ? Long.valueOf(in.readLong()) : null,
                    in.readInt(),
                    in.readInt());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new GtsItem[i];
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            k = 3,
            mv = {1, 1, 16})
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[GtsItemFormat.values().length];
            $EnumSwitchMapping$0 = iArr;
            iArr[GtsItemFormat.FORMAT_TEXT.ordinal()] = 1;
            iArr[GtsItemFormat.FORMAT_BOOLEAN.ordinal()] = 2;
            iArr[GtsItemFormat.FORMAT_INT.ordinal()] = 3;
            iArr[GtsItemFormat.FORMAT_LONG.ordinal()] = 4;
            iArr[GtsItemFormat.FORMAT_DOUBLE.ordinal()] = 5;
            iArr[GtsItemFormat.FORMAT_URI.ordinal()] = 6;
            iArr[GtsItemFormat.FORMAT_URL.ordinal()] = 7;
        }
    }

    public GtsItem(
            int i,
            GtsItemFormat format,
            String key,
            String value,
            Map<String, String> tags,
            Map<String, GtsItem> map,
            Long l,
            int i2,
            int i3) {
        Intrinsics.checkParameterIsNotNull(format, "format");
        Intrinsics.checkParameterIsNotNull(key, "key");
        Intrinsics.checkParameterIsNotNull(value, "value");
        Intrinsics.checkParameterIsNotNull(tags, "tags");
        this.type = i;
        this.format = format;
        this.key = key;
        this.value = value;
        this.tags = tags;
        this.embeddedItems = map;
        this.timeout = l;
        this.revision = i2;
        this.version = i3;
    }

    /* renamed from: component1, reason: from getter */
    public final int getType() {
        return this.type;
    }

    /* renamed from: component2, reason: from getter */
    public final GtsItemFormat getFormat() {
        return this.format;
    }

    /* renamed from: component3, reason: from getter */
    public final String getKey() {
        return this.key;
    }

    /* renamed from: component4, reason: from getter */
    public final String getValue() {
        return this.value;
    }

    public final Map<String, String> component5() {
        return this.tags;
    }

    public final Map<String, GtsItem> component6() {
        return this.embeddedItems;
    }

    /* renamed from: component7, reason: from getter */
    public final Long getTimeout() {
        return this.timeout;
    }

    /* renamed from: component8, reason: from getter */
    public final int getRevision() {
        return this.revision;
    }

    /* renamed from: component9, reason: from getter */
    public final int getVersion() {
        return this.version;
    }

    public final GtsItem copy(
            int type,
            GtsItemFormat format,
            String key,
            String value,
            Map<String, String> tags,
            Map<String, GtsItem> embeddedItems,
            Long timeout,
            int revision,
            int version) {
        Intrinsics.checkParameterIsNotNull(format, "format");
        Intrinsics.checkParameterIsNotNull(key, "key");
        Intrinsics.checkParameterIsNotNull(value, "value");
        Intrinsics.checkParameterIsNotNull(tags, "tags");
        return new GtsItem(
                type, format, key, value, tags, embeddedItems, timeout, revision, version);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GtsItem)) {
            return false;
        }
        GtsItem gtsItem = (GtsItem) other;
        return this.type == gtsItem.type
                && Intrinsics.areEqual(this.format, gtsItem.format)
                && Intrinsics.areEqual(this.key, gtsItem.key)
                && Intrinsics.areEqual(this.value, gtsItem.value)
                && Intrinsics.areEqual(this.tags, gtsItem.tags)
                && Intrinsics.areEqual(this.embeddedItems, gtsItem.embeddedItems)
                && Intrinsics.areEqual(this.timeout, gtsItem.timeout)
                && this.revision == gtsItem.revision
                && this.version == gtsItem.version;
    }

    public final Map<String, GtsItem> getEmbeddedItems() {
        return this.embeddedItems;
    }

    public final GtsItemFormat getFormat() {
        return this.format;
    }

    public final String getKey() {
        return this.key;
    }

    public final int getRevision() {
        return this.revision;
    }

    public final Map<String, String> getTags() {
        return this.tags;
    }

    public final Long getTimeout() {
        return this.timeout;
    }

    public final int getType() {
        return this.type;
    }

    public final <T> T getTypedValue() throws NumberFormatException, ClassCastException {
        switch (WhenMappings.$EnumSwitchMapping$0[this.format.ordinal()]) {
            case 1:
                return (T) this.value;
            case 2:
                return (T) Boolean.valueOf(Boolean.parseBoolean(this.value));
            case 3:
                return (T) Integer.valueOf(Integer.parseInt(this.value));
            case 4:
                return (T) Long.valueOf(Long.parseLong(this.value));
            case 5:
                return (T) Double.valueOf(Double.parseDouble(this.value));
            case 6:
                return (T) Uri.parse(this.value);
            case 7:
                return (T) this.value;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public final String getUID() {
        return this.tags.get(KEY_UID);
    }

    public final String getValue() {
        return this.value;
    }

    public final int getVersion() {
        return this.version;
    }

    public int hashCode() {
        int hashCode = Integer.hashCode(this.type) * 31;
        GtsItemFormat gtsItemFormat = this.format;
        int hashCode2 = (hashCode + (gtsItemFormat != null ? gtsItemFormat.hashCode() : 0)) * 31;
        String str = this.key;
        int hashCode3 = (hashCode2 + (str != null ? str.hashCode() : 0)) * 31;
        String str2 = this.value;
        int hashCode4 = (hashCode3 + (str2 != null ? str2.hashCode() : 0)) * 31;
        Map<String, String> map = this.tags;
        int hashCode5 = (hashCode4 + (map != null ? map.hashCode() : 0)) * 31;
        Map<String, GtsItem> map2 = this.embeddedItems;
        int hashCode6 = (hashCode5 + (map2 != null ? map2.hashCode() : 0)) * 31;
        Long l = this.timeout;
        return Integer.hashCode(this.version)
                + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(
                        this.revision, (hashCode6 + (l != null ? l.hashCode() : 0)) * 31, 31);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("GtsItem(type=");
        sb.append(this.type);
        sb.append(", format=");
        sb.append(this.format);
        sb.append(", key=");
        sb.append(this.key);
        sb.append(", value=");
        sb.append(this.value);
        sb.append(", tags=");
        sb.append(this.tags);
        sb.append(", embeddedItems=");
        sb.append(this.embeddedItems);
        sb.append(", timeout=");
        sb.append(this.timeout);
        sb.append(", revision=");
        sb.append(this.revision);
        sb.append(", version=");
        return Anchor$$ExternalSyntheticOutline0.m(sb, this.version, ")");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        Intrinsics.checkParameterIsNotNull(parcel, "parcel");
        parcel.writeInt(this.type);
        parcel.writeString(this.format.name());
        parcel.writeString(this.key);
        parcel.writeString(this.value);
        Map<String, String> map = this.tags;
        parcel.writeInt(map.size());
        for (Map.Entry<String, String> entry : map.entrySet()) {
            parcel.writeString(entry.getKey());
            parcel.writeString(entry.getValue());
        }
        Map<String, GtsItem> map2 = this.embeddedItems;
        if (map2 != null) {
            parcel.writeInt(1);
            parcel.writeInt(map2.size());
            for (Map.Entry<String, GtsItem> entry2 : map2.entrySet()) {
                parcel.writeString(entry2.getKey());
                entry2.getValue().writeToParcel(parcel, 0);
            }
        } else {
            parcel.writeInt(0);
        }
        Long l = this.timeout;
        if (l != null) {
            parcel.writeInt(1);
            parcel.writeLong(l.longValue());
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.revision);
        parcel.writeInt(this.version);
    }

    public /* synthetic */ GtsItem(
            int i,
            GtsItemFormat gtsItemFormat,
            String str,
            String str2,
            Map map,
            Map map2,
            Long l,
            int i2,
            int i3,
            int i4,
            DefaultConstructorMarker defaultConstructorMarker) {
        this(i, gtsItemFormat, str, str2, map, map2, l, i2, (i4 & 256) != 0 ? 1 : i3);
    }
}
