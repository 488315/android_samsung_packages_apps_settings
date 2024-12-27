package com.samsung.android.gtscell.data;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

import com.google.gson.annotations.SerializedName;
import com.samsung.android.gtscell.utils.GtsCellExKt;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;

import kotlin.Metadata;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.android.parcel.Parcelize;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Parcelize
@Keep
@Metadata(
        bv = {1, 0, 3},
        d1 = {
            "\u0000f\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010 \n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010$\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\u000b\n"
                + "\u0002\b\u001b\n"
                + "\u0002\u0010\u0000\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0087\b\u0018\u0000"
                + " <2\u00020\u0001:\u0002<=Be\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070"
                + "\t\u0012\u000e\u0010\n"
                + "\u001a\n"
                + "\u0012\u0004\u0012\u00020\u000b\u0018\u00010"
                + "\t\u0012\u0014\u0010\f\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0003\u0018\u00010\r"
                + "\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0010J"
                + "\t\u0010\"\u001a\u00020\u0003HÆ\u0003J\t\u0010#\u001a\u00020\u0003HÆ\u0003J"
                + "\t\u0010$\u001a\u00020\u0003HÆ\u0003J"
                + "\t\u0010%\u001a\u00020\u0007HÆ\u0003J\u000f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00070"
                + "\tHÆ\u0003J\u0011\u0010'\u001a\n"
                + "\u0012\u0004\u0012\u00020\u000b\u0018\u00010"
                + "\tHÆ\u0003J\u0017\u0010(\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0003\u0018\u00010\r"
                + "HÆ\u0003J"
                + "\t\u0010)\u001a\u00020\u000fHÆ\u0003Ju\u0010*\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070"
                + "\t2\u0010\b\u0002\u0010\n"
                + "\u001a\n"
                + "\u0012\u0004\u0012\u00020\u000b\u0018\u00010"
                + "\t2\u0016\b\u0002\u0010\f\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0003\u0018\u00010\r"
                + "2\b\b\u0002\u0010\u000e\u001a\u00020\u000fHÆ\u0001J"
                + "\t\u0010+\u001a\u00020\u000fHÖ\u0001J\u0013\u0010,\u001a\u00020\u00122\b\u0010-\u001a\u0004\u0018\u00010.HÖ\u0003J\u0006\u0010/\u001a\u000200J"
                + "\t\u00101\u001a\u00020\u000fHÖ\u0001J\u0006\u00102\u001a\u000203J"
                + "\t\u00104\u001a\u00020\u0003HÖ\u0001J\u0019\u00105\u001a\u0002062\u0006\u00107\u001a\u0002082\u0006\u00109\u001a\u00020\u000fHÖ\u0001J\f\u0010:\u001a\u00020;*\u00020\u0007H\u0002R\u0011\u0010\u0011\u001a\u00020\u00128F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0016\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u0015\u0010\u0016R$\u0010\f\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0003\u0018\u00010\r"
                + "8\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u0019\u0010\u001aR\u001e\u0010\n"
                + "\u001a\n"
                + "\u0012\u0004\u0012\u00020\u000b\u0018\u00010\t8\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u001b\u0010\u001cR\u001c\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070"
                + "\t8\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u001d\u0010\u001cR\u0016\u0010\u0005\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u001e\u0010\u001aR\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u001f\u0010\u001aR\u0016\u0010\u000e\u001a\u00020\u000f8\u0006X\u0087\u0004¢\u0006\b\n"
                + "\u0000\u001a\u0004\b \u0010!¨\u0006>"
        },
        d2 = {
            "Lcom/samsung/android/gtscell/data/GtsExpressionRaw;",
            "Landroid/os/Parcelable;",
            "itemKey",
            ApnSettings.MVNO_NONE,
            UniversalCredentialUtil.AGENT_TITLE,
            "subTitle",
            "expression",
            "Lcom/samsung/android/gtscell/data/GtsExpressionRaw$GtsExpressionData;",
            "subExpressions",
            ApnSettings.MVNO_NONE,
            "storeContents",
            "Lcom/samsung/android/gtscell/data/GtsStoreContents;",
            "extra",
            ApnSettings.MVNO_NONE,
            FieldName.VERSION,
            ApnSettings.MVNO_NONE,
            "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/samsung/android/gtscell/data/GtsExpressionRaw$GtsExpressionData;Ljava/util/List;Ljava/util/List;Ljava/util/Map;I)V",
            "disabled",
            ApnSettings.MVNO_NONE,
            "getDisabled",
            "()Z",
            "getExpression",
            "()Lcom/samsung/android/gtscell/data/GtsExpressionRaw$GtsExpressionData;",
            "getExtra",
            "()Ljava/util/Map;",
            "getItemKey",
            "()Ljava/lang/String;",
            "getStoreContents",
            "()Ljava/util/List;",
            "getSubExpressions",
            "getSubTitle",
            "getTitle",
            "getVersion",
            "()I",
            "component1",
            "component2",
            "component3",
            "component4",
            "component5",
            "component6",
            "component7",
            "component8",
            "copy",
            "describeContents",
            "equals",
            "other",
            ApnSettings.MVNO_NONE,
            "getTypedExpression",
            "Lcom/samsung/android/gtscell/data/GtsExpression;",
            "hashCode",
            "toBuilder",
            "Lcom/samsung/android/gtscell/data/GtsExpressionBuilder;",
            "toString",
            "writeToParcel",
            ApnSettings.MVNO_NONE,
            "parcel",
            "Landroid/os/Parcel;",
            "flags",
            "toGtsExpressionValue",
            "Lcom/samsung/android/gtscell/data/GtsExpressionValue;",
            "Companion",
            "GtsExpressionData",
            "gtscell_release"
        },
        k = 1,
        mv = {1, 1, 16})
/* loaded from: classes4.dex */
public final /* data */ class GtsExpressionRaw implements Parcelable {
    public static final String EXPRESSION_KEY_DISABLED = "exp_disabled";
    public static final String EXPRESSION_KEY_ENLARGEABLE = "exp_enlargeable";
    public static final String EXPRESSION_KEY_MAX = "exp_max";
    public static final String EXPRESSION_KEY_MIN = "exp_min";
    public static final String EXPRESSION_KEY_NAME = "exp_name";
    public static final String EXPRESSION_KEY_STEP = "exp_step";
    public static final String EXPRESSION_KEY_VALUE = "exp_value";
    public static final int VERSION = 1;

    @SerializedName(FieldName.ITEM_EXPRESSION)
    private final GtsExpressionData expression;

    @SerializedName(FieldName.ITEM_EXPRESSION_EXTRA)
    private final Map<String, String> extra;

    @SerializedName(FieldName.ITEM_KEY)
    private final String itemKey;

    @SerializedName(FieldName.ITEM_STORE_CONTENTS)
    private final List<GtsStoreContents> storeContents;

    @SerializedName(FieldName.ITEM_SUB_EXPRESSIONS)
    private final List<GtsExpressionData> subExpressions;

    @SerializedName(FieldName.ITEM_EXPRESSION_SUB_TITLE)
    private final String subTitle;

    @SerializedName(FieldName.ITEM_EXPRESSION_TITLE)
    private final String title;

    @SerializedName(FieldName.VERSION)
    private final int version;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final GtsExpressionRaw EMPTY =
            new GtsExpressionRaw(
                    ApnSettings.MVNO_NONE,
                    ApnSettings.MVNO_NONE,
                    ApnSettings.MVNO_NONE,
                    GtsExpressionData.INSTANCE.getEMPTY(),
                    EmptyList.INSTANCE,
                    null,
                    null,
                    0,
                    128,
                    null);
    public static final Parcelable.Creator CREATOR = new Creator();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            d1 = {
                "\u0000\"\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0010\u0000\n"
                    + "\u0002\b\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\b\u0003\n"
                    + "\u0002\u0010\u000e\n"
                    + "\u0002\b\u0007\n"
                    + "\u0002\u0010\b\n"
                    + "\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0086T¢\u0006\u0002\n"
                    + "\u0000R\u000e\u0010\t\u001a\u00020\bX\u0086T¢\u0006\u0002\n"
                    + "\u0000R\u000e\u0010\n"
                    + "\u001a\u00020\bX\u0086T¢\u0006\u0002\n"
                    + "\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0086T¢\u0006\u0002\n"
                    + "\u0000R\u000e\u0010\f\u001a\u00020\bX\u0086T¢\u0006\u0002\n"
                    + "\u0000R\u000e\u0010\r"
                    + "\u001a\u00020\bX\u0086T¢\u0006\u0002\n"
                    + "\u0000R\u000e\u0010\u000e\u001a\u00020\bX\u0086T¢\u0006\u0002\n"
                    + "\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0086T¢\u0006\u0002\n"
                    + "\u0000¨\u0006\u0011"
            },
            d2 = {
                "Lcom/samsung/android/gtscell/data/GtsExpressionRaw$Companion;",
                ApnSettings.MVNO_NONE,
                "()V",
                "EMPTY",
                "Lcom/samsung/android/gtscell/data/GtsExpressionRaw;",
                "getEMPTY",
                "()Lcom/samsung/android/gtscell/data/GtsExpressionRaw;",
                "EXPRESSION_KEY_DISABLED",
                ApnSettings.MVNO_NONE,
                "EXPRESSION_KEY_ENLARGEABLE",
                "EXPRESSION_KEY_MAX",
                "EXPRESSION_KEY_MIN",
                "EXPRESSION_KEY_NAME",
                "EXPRESSION_KEY_STEP",
                "EXPRESSION_KEY_VALUE",
                "VERSION",
                ApnSettings.MVNO_NONE,
                "gtscell_release"
            },
            k = 1,
            mv = {1, 1, 16})
    public static final class Companion {
        private Companion() {}

        public final GtsExpressionRaw getEMPTY() {
            return GtsExpressionRaw.EMPTY;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            k = 3,
            mv = {1, 1, 16})
    public static class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel in) {
            ArrayList arrayList;
            Intrinsics.checkParameterIsNotNull(in, "in");
            String readString = in.readString();
            String readString2 = in.readString();
            String readString3 = in.readString();
            GtsExpressionData gtsExpressionData =
                    (GtsExpressionData) GtsExpressionData.CREATOR.createFromParcel(in);
            int readInt = in.readInt();
            ArrayList arrayList2 = new ArrayList(readInt);
            while (readInt != 0) {
                arrayList2.add((GtsExpressionData) GtsExpressionData.CREATOR.createFromParcel(in));
                readInt--;
            }
            LinkedHashMap linkedHashMap = null;
            if (in.readInt() != 0) {
                int readInt2 = in.readInt();
                arrayList = new ArrayList(readInt2);
                while (readInt2 != 0) {
                    arrayList.add(
                            (GtsStoreContents)
                                    in.readParcelable(GtsExpressionRaw.class.getClassLoader()));
                    readInt2--;
                }
            } else {
                arrayList = null;
            }
            if (in.readInt() != 0) {
                int readInt3 = in.readInt();
                linkedHashMap = new LinkedHashMap(readInt3);
                while (readInt3 != 0) {
                    linkedHashMap.put(in.readString(), in.readString());
                    readInt3--;
                }
            }
            return new GtsExpressionRaw(
                    readString,
                    readString2,
                    readString3,
                    gtsExpressionData,
                    arrayList2,
                    arrayList,
                    linkedHashMap,
                    in.readInt());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new GtsExpressionRaw[i];
        }
    }

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
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\n"
                    + "\u0002\u0010$\n"
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
                    + "\u0002\b\u0003\b\u0087\b\u0018\u0000"
                    + " \u001c2\u00020\u0001:\u0001\u001cB!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J"
                    + "\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\u0015\u0010\r"
                    + "\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J)\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0014\b\u0002\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0001J"
                    + "\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014HÖ\u0003J"
                    + "\t\u0010\u0015\u001a\u00020\u0010HÖ\u0001J"
                    + "\t\u0010\u0016\u001a\u00020\u0006HÖ\u0001J\u0019\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0010HÖ\u0001R\"\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u00058\u0006X\u0087\u0004¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\b\u0010"
                    + "\tR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\n"
                    + "\u0010\u000b¨\u0006\u001d"
            },
            d2 = {
                "Lcom/samsung/android/gtscell/data/GtsExpressionRaw$GtsExpressionData;",
                "Landroid/os/Parcelable;",
                "expressionType",
                "Lcom/samsung/android/gtscell/data/GtsExpressionType;",
                "expression",
                ApnSettings.MVNO_NONE,
                ApnSettings.MVNO_NONE,
                "(Lcom/samsung/android/gtscell/data/GtsExpressionType;Ljava/util/Map;)V",
                "getExpression",
                "()Ljava/util/Map;",
                "getExpressionType",
                "()Lcom/samsung/android/gtscell/data/GtsExpressionType;",
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
                "Companion",
                "gtscell_release"
            },
            k = 1,
            mv = {1, 1, 16})
    public static final /* data */ class GtsExpressionData implements Parcelable {

        @SerializedName(FieldName.ITEM_EXPRESSION)
        private final Map<String, String> expression;

        @SerializedName(FieldName.ITEM_EXPRESSION_TYPE)
        private final GtsExpressionType expressionType;

        /* renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private static final GtsExpressionData EMPTY =
                new GtsExpressionData(GtsExpressionType.TYPE_NONE, MapsKt__MapsKt.emptyMap());
        public static final Parcelable.Creator CREATOR = new Creator();

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        @Metadata(
                bv = {1, 0, 3},
                d1 = {
                    "\u0000\u0014\n"
                        + "\u0002\u0018\u0002\n"
                        + "\u0002\u0010\u0000\n"
                        + "\u0002\b\u0002\n"
                        + "\u0002\u0018\u0002\n"
                        + "\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n"
                        + "\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"
                },
                d2 = {
                    "Lcom/samsung/android/gtscell/data/GtsExpressionRaw$GtsExpressionData$Companion;",
                    ApnSettings.MVNO_NONE,
                    "()V",
                    "EMPTY",
                    "Lcom/samsung/android/gtscell/data/GtsExpressionRaw$GtsExpressionData;",
                    "getEMPTY",
                    "()Lcom/samsung/android/gtscell/data/GtsExpressionRaw$GtsExpressionData;",
                    "gtscell_release"
                },
                k = 1,
                mv = {1, 1, 16})
        public static final class Companion {
            private Companion() {}

            public final GtsExpressionData getEMPTY() {
                return GtsExpressionData.EMPTY;
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        @Metadata(
                bv = {1, 0, 3},
                k = 3,
                mv = {1, 1, 16})
        public static class Creator implements Parcelable.Creator {
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel in) {
                Intrinsics.checkParameterIsNotNull(in, "in");
                GtsExpressionType gtsExpressionType =
                        (GtsExpressionType) Enum.valueOf(GtsExpressionType.class, in.readString());
                int readInt = in.readInt();
                LinkedHashMap linkedHashMap = new LinkedHashMap(readInt);
                while (readInt != 0) {
                    linkedHashMap.put(in.readString(), in.readString());
                    readInt--;
                }
                return new GtsExpressionData(gtsExpressionType, linkedHashMap);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new GtsExpressionData[i];
            }
        }

        public GtsExpressionData(GtsExpressionType expressionType, Map<String, String> expression) {
            Intrinsics.checkParameterIsNotNull(expressionType, "expressionType");
            Intrinsics.checkParameterIsNotNull(expression, "expression");
            this.expressionType = expressionType;
            this.expression = expression;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ GtsExpressionData copy$default(
                GtsExpressionData gtsExpressionData,
                GtsExpressionType gtsExpressionType,
                Map map,
                int i,
                Object obj) {
            if ((i & 1) != 0) {
                gtsExpressionType = gtsExpressionData.expressionType;
            }
            if ((i & 2) != 0) {
                map = gtsExpressionData.expression;
            }
            return gtsExpressionData.copy(gtsExpressionType, map);
        }

        /* renamed from: component1, reason: from getter */
        public final GtsExpressionType getExpressionType() {
            return this.expressionType;
        }

        public final Map<String, String> component2() {
            return this.expression;
        }

        public final GtsExpressionData copy(
                GtsExpressionType expressionType, Map<String, String> expression) {
            Intrinsics.checkParameterIsNotNull(expressionType, "expressionType");
            Intrinsics.checkParameterIsNotNull(expression, "expression");
            return new GtsExpressionData(expressionType, expression);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof GtsExpressionData)) {
                return false;
            }
            GtsExpressionData gtsExpressionData = (GtsExpressionData) other;
            return Intrinsics.areEqual(this.expressionType, gtsExpressionData.expressionType)
                    && Intrinsics.areEqual(this.expression, gtsExpressionData.expression);
        }

        public final Map<String, String> getExpression() {
            return this.expression;
        }

        public final GtsExpressionType getExpressionType() {
            return this.expressionType;
        }

        public int hashCode() {
            GtsExpressionType gtsExpressionType = this.expressionType;
            int hashCode = (gtsExpressionType != null ? gtsExpressionType.hashCode() : 0) * 31;
            Map<String, String> map = this.expression;
            return hashCode + (map != null ? map.hashCode() : 0);
        }

        public String toString() {
            return "GtsExpressionData(expressionType="
                    + this.expressionType
                    + ", expression="
                    + this.expression
                    + ")";
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int flags) {
            Intrinsics.checkParameterIsNotNull(parcel, "parcel");
            parcel.writeString(this.expressionType.name());
            Map<String, String> map = this.expression;
            parcel.writeInt(map.size());
            for (Map.Entry<String, String> entry : map.entrySet()) {
                parcel.writeString(entry.getKey());
                parcel.writeString(entry.getValue());
            }
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
            int[] iArr = new int[GtsExpressionType.values().length];
            $EnumSwitchMapping$0 = iArr;
            iArr[GtsExpressionType.TYPE_BOOLEAN.ordinal()] = 1;
            iArr[GtsExpressionType.TYPE_COLOR.ordinal()] = 2;
            iArr[GtsExpressionType.TYPE_MIME.ordinal()] = 3;
            iArr[GtsExpressionType.TYPE_MIME_ICON.ordinal()] = 4;
            iArr[GtsExpressionType.TYPE_ICON.ordinal()] = 5;
            iArr[GtsExpressionType.TYPE_ICON_URI.ordinal()] = 6;
            iArr[GtsExpressionType.TYPE_URL.ordinal()] = 7;
            iArr[GtsExpressionType.TYPE_LEVEL.ordinal()] = 8;
            iArr[GtsExpressionType.TYPE_PROGRESS.ordinal()] = 9;
        }
    }

    public GtsExpressionRaw(
            String itemKey,
            String title,
            String subTitle,
            GtsExpressionData expression,
            List<GtsExpressionData> subExpressions,
            List<GtsStoreContents> list,
            Map<String, String> map,
            int i) {
        Intrinsics.checkParameterIsNotNull(itemKey, "itemKey");
        Intrinsics.checkParameterIsNotNull(title, "title");
        Intrinsics.checkParameterIsNotNull(subTitle, "subTitle");
        Intrinsics.checkParameterIsNotNull(expression, "expression");
        Intrinsics.checkParameterIsNotNull(subExpressions, "subExpressions");
        this.itemKey = itemKey;
        this.title = title;
        this.subTitle = subTitle;
        this.expression = expression;
        this.subExpressions = subExpressions;
        this.storeContents = list;
        this.extra = map;
        this.version = i;
    }

    private final GtsExpressionValue toGtsExpressionValue(GtsExpressionData gtsExpressionData) {
        GtsMimeType gtsMimeType;
        GtsExpressionValue mimeIcon;
        Bitmap bitmap;
        try {
            int i =
                    WhenMappings.$EnumSwitchMapping$0[
                            gtsExpressionData.getExpressionType().ordinal()];
            String str = ApnSettings.MVNO_NONE;
            switch (i) {
                case 1:
                    String str2 = gtsExpressionData.getExpression().get(EXPRESSION_KEY_VALUE);
                    break;
                case 2:
                    String str3 = gtsExpressionData.getExpression().get(EXPRESSION_KEY_VALUE);
                    break;
                case 4:
                    String str4 = gtsExpressionData.getExpression().get(EXPRESSION_KEY_VALUE);
                    if (str4 == null) {
                        break;
                    } else {
                        try {
                            gtsMimeType = GtsMimeType.valueOf(str4);
                        } catch (Exception unused) {
                            gtsMimeType = GtsMimeType.UNKNOWN;
                        }
                        mimeIcon = new GtsExpressionValue.MimeIcon(gtsMimeType);
                        break;
                    }
                case 5:
                    String str5 = gtsExpressionData.getExpression().get(EXPRESSION_KEY_VALUE);
                    if (str5 != null && (bitmap = GtsCellExKt.toBitmap(str5)) != null) {
                        String str6 =
                                gtsExpressionData.getExpression().get(EXPRESSION_KEY_ENLARGEABLE);
                        mimeIcon =
                                new GtsExpressionValue.Icon(
                                        bitmap, str6 != null ? Boolean.parseBoolean(str6) : false);
                        break;
                    } else {
                        break;
                    }
                case 6:
                    String str7 = gtsExpressionData.getExpression().get(EXPRESSION_KEY_VALUE);
                    if (str7 == null) {
                        break;
                    } else {
                        Uri parse = Uri.parse(str7);
                        Intrinsics.checkExpressionValueIsNotNull(parse, "Uri.parse(uri)");
                        mimeIcon = new GtsExpressionValue.MimeUri(parse, true);
                        break;
                    }
                case 7:
                    String str8 = gtsExpressionData.getExpression().get(EXPRESSION_KEY_VALUE);
                    if (str8 == null) {
                        break;
                    } else {
                        String str9 =
                                gtsExpressionData.getExpression().get(EXPRESSION_KEY_ENLARGEABLE);
                        mimeIcon =
                                new GtsExpressionValue.MimeUrl(
                                        str8, str9 != null ? Boolean.parseBoolean(str9) : false);
                        break;
                    }
                case 8:
                    String str10 = gtsExpressionData.getExpression().get(EXPRESSION_KEY_VALUE);
                    int parseInt = str10 != null ? Integer.parseInt(str10) : 0;
                    String str11 = gtsExpressionData.getExpression().get(EXPRESSION_KEY_MIN);
                    int parseInt2 = str11 != null ? Integer.parseInt(str11) : 0;
                    String str12 = gtsExpressionData.getExpression().get(EXPRESSION_KEY_MAX);
                    int parseInt3 = str12 != null ? Integer.parseInt(str12) : 0;
                    String str13 = gtsExpressionData.getExpression().get(EXPRESSION_KEY_STEP);
                    int parseInt4 = str13 != null ? Integer.parseInt(str13) : 0;
                    String str14 = gtsExpressionData.getExpression().get(EXPRESSION_KEY_NAME);
                    break;
                case 9:
                    String str15 = gtsExpressionData.getExpression().get(EXPRESSION_KEY_VALUE);
                    int parseInt5 = str15 != null ? Integer.parseInt(str15) : 0;
                    String str16 = gtsExpressionData.getExpression().get(EXPRESSION_KEY_MIN);
                    int parseInt6 = str16 != null ? Integer.parseInt(str16) : 0;
                    String str17 = gtsExpressionData.getExpression().get(EXPRESSION_KEY_MAX);
                    int parseInt7 = str17 != null ? Integer.parseInt(str17) : 0;
                    String str18 = gtsExpressionData.getExpression().get(EXPRESSION_KEY_NAME);
                    if (str18 != null) {
                        str = str18;
                    }
                    mimeIcon =
                            new GtsExpressionValue.Progress(parseInt5, parseInt6, parseInt7, str);
                    break;
            }
        } catch (Exception unused2) {
            return GtsExpressionValue.Empty.INSTANCE;
        }
        return GtsExpressionValue.Empty.INSTANCE;
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
    public final GtsExpressionData getExpression() {
        return this.expression;
    }

    public final List<GtsExpressionData> component5() {
        return this.subExpressions;
    }

    public final List<GtsStoreContents> component6() {
        return this.storeContents;
    }

    public final Map<String, String> component7() {
        return this.extra;
    }

    /* renamed from: component8, reason: from getter */
    public final int getVersion() {
        return this.version;
    }

    public final GtsExpressionRaw copy(
            String itemKey,
            String title,
            String subTitle,
            GtsExpressionData expression,
            List<GtsExpressionData> subExpressions,
            List<GtsStoreContents> storeContents,
            Map<String, String> extra,
            int version) {
        Intrinsics.checkParameterIsNotNull(itemKey, "itemKey");
        Intrinsics.checkParameterIsNotNull(title, "title");
        Intrinsics.checkParameterIsNotNull(subTitle, "subTitle");
        Intrinsics.checkParameterIsNotNull(expression, "expression");
        Intrinsics.checkParameterIsNotNull(subExpressions, "subExpressions");
        return new GtsExpressionRaw(
                itemKey,
                title,
                subTitle,
                expression,
                subExpressions,
                storeContents,
                extra,
                version);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GtsExpressionRaw)) {
            return false;
        }
        GtsExpressionRaw gtsExpressionRaw = (GtsExpressionRaw) other;
        return Intrinsics.areEqual(this.itemKey, gtsExpressionRaw.itemKey)
                && Intrinsics.areEqual(this.title, gtsExpressionRaw.title)
                && Intrinsics.areEqual(this.subTitle, gtsExpressionRaw.subTitle)
                && Intrinsics.areEqual(this.expression, gtsExpressionRaw.expression)
                && Intrinsics.areEqual(this.subExpressions, gtsExpressionRaw.subExpressions)
                && Intrinsics.areEqual(this.storeContents, gtsExpressionRaw.storeContents)
                && Intrinsics.areEqual(this.extra, gtsExpressionRaw.extra)
                && this.version == gtsExpressionRaw.version;
    }

    public final boolean getDisabled() {
        String str;
        Map<String, String> map = this.extra;
        if (map == null || (str = map.get(EXPRESSION_KEY_DISABLED)) == null) {
            return false;
        }
        return Boolean.parseBoolean(str);
    }

    public final GtsExpressionData getExpression() {
        return this.expression;
    }

    public final Map<String, String> getExtra() {
        return this.extra;
    }

    public final String getItemKey() {
        return this.itemKey;
    }

    public final List<GtsStoreContents> getStoreContents() {
        return this.storeContents;
    }

    public final List<GtsExpressionData> getSubExpressions() {
        return this.subExpressions;
    }

    public final String getSubTitle() {
        return this.subTitle;
    }

    public final String getTitle() {
        return this.title;
    }

    public final GtsExpression getTypedExpression()
            throws NumberFormatException, ClassCastException {
        String str = this.itemKey;
        String str2 = this.title;
        String str3 = this.subTitle;
        boolean disabled = getDisabled();
        GtsExpressionValue gtsExpressionValue = toGtsExpressionValue(this.expression);
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = this.subExpressions.iterator();
        while (it.hasNext()) {
            arrayList.add(toGtsExpressionValue((GtsExpressionData) it.next()));
        }
        return new GtsExpression(str, str2, str3, disabled, gtsExpressionValue, arrayList);
    }

    public final int getVersion() {
        return this.version;
    }

    public int hashCode() {
        String str = this.itemKey;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.title;
        int hashCode2 = (hashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.subTitle;
        int hashCode3 = (hashCode2 + (str3 != null ? str3.hashCode() : 0)) * 31;
        GtsExpressionData gtsExpressionData = this.expression;
        int hashCode4 =
                (hashCode3 + (gtsExpressionData != null ? gtsExpressionData.hashCode() : 0)) * 31;
        List<GtsExpressionData> list = this.subExpressions;
        int hashCode5 = (hashCode4 + (list != null ? list.hashCode() : 0)) * 31;
        List<GtsStoreContents> list2 = this.storeContents;
        int hashCode6 = (hashCode5 + (list2 != null ? list2.hashCode() : 0)) * 31;
        Map<String, String> map = this.extra;
        return Integer.hashCode(this.version)
                + ((hashCode6 + (map != null ? map.hashCode() : 0)) * 31);
    }

    public final GtsExpressionBuilder toBuilder() {
        return new GtsExpressionBuilder(this);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("GtsExpressionRaw(itemKey=");
        sb.append(this.itemKey);
        sb.append(", title=");
        sb.append(this.title);
        sb.append(", subTitle=");
        sb.append(this.subTitle);
        sb.append(", expression=");
        sb.append(this.expression);
        sb.append(", subExpressions=");
        sb.append(this.subExpressions);
        sb.append(", storeContents=");
        sb.append(this.storeContents);
        sb.append(", extra=");
        sb.append(this.extra);
        sb.append(", version=");
        return Anchor$$ExternalSyntheticOutline0.m(sb, this.version, ")");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        Intrinsics.checkParameterIsNotNull(parcel, "parcel");
        parcel.writeString(this.itemKey);
        parcel.writeString(this.title);
        parcel.writeString(this.subTitle);
        this.expression.writeToParcel(parcel, 0);
        List<GtsExpressionData> list = this.subExpressions;
        parcel.writeInt(list.size());
        Iterator<GtsExpressionData> it = list.iterator();
        while (it.hasNext()) {
            it.next().writeToParcel(parcel, 0);
        }
        List<GtsStoreContents> list2 = this.storeContents;
        if (list2 != null) {
            parcel.writeInt(1);
            parcel.writeInt(list2.size());
            Iterator<GtsStoreContents> it2 = list2.iterator();
            while (it2.hasNext()) {
                parcel.writeParcelable(it2.next(), flags);
            }
        } else {
            parcel.writeInt(0);
        }
        Map<String, String> map = this.extra;
        if (map != null) {
            parcel.writeInt(1);
            parcel.writeInt(map.size());
            for (Map.Entry<String, String> entry : map.entrySet()) {
                parcel.writeString(entry.getKey());
                parcel.writeString(entry.getValue());
            }
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.version);
    }

    public /* synthetic */ GtsExpressionRaw(
            String str,
            String str2,
            String str3,
            GtsExpressionData gtsExpressionData,
            List list,
            List list2,
            Map map,
            int i,
            int i2,
            DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, str3, gtsExpressionData, list, list2, map, (i2 & 128) != 0 ? 1 : i);
    }
}
