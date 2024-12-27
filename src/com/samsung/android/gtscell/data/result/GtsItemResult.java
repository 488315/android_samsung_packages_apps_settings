package com.samsung.android.gtscell.data.result;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.annotation.Keep;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.android.parcel.Parcelize;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        bv = {1, 0, 3},
        d1 = {
            "\u0000*\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\t\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\b6\u0018\u00002\u00020\u0001:\u0007\u0007\b\t\n"
                + "\u000b\f\r"
                + "B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0005\u000e\u000f\u0010\u0011\u0012¨\u0006\u0013"
        },
        d2 = {
            "Lcom/samsung/android/gtscell/data/result/GtsItemResult;",
            "Landroid/os/Parcelable;",
            "()V",
            "itemKey",
            ApnSettings.MVNO_NONE,
            "getItemKey",
            "()Ljava/lang/String;",
            "Error",
            "ErrorReason",
            "Ignore",
            "Info",
            "Pass",
            "Warning",
            "WarningReason",
            "Lcom/samsung/android/gtscell/data/result/GtsItemResult$Pass;",
            "Lcom/samsung/android/gtscell/data/result/GtsItemResult$Info;",
            "Lcom/samsung/android/gtscell/data/result/GtsItemResult$Warning;",
            "Lcom/samsung/android/gtscell/data/result/GtsItemResult$Error;",
            "Lcom/samsung/android/gtscell/data/result/GtsItemResult$Ignore;",
            "gtscell_release"
        },
        k = 1,
        mv = {1, 1, 16})
/* loaded from: classes4.dex */
public abstract class GtsItemResult implements Parcelable {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Keep
    @Metadata(
            bv = {1, 0, 3},
            d1 = {
                "\u0000D\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\b\u0002\n"
                    + "\u0002\u0010\u000e\n"
                    + "\u0000\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\b\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\b\u0010\n"
                    + "\u0002\u0010\b\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u000b\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u0000\n"
                    + "\u0002\b\u0003\n"
                    + "\u0002\u0010\u0002\n"
                    + "\u0002\b\u0003\b\u0087\b\u0018\u0000"
                    + " &2\u00020\u0001:\u0001&B\u000f\b\u0012\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B/\b\u0007\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\n"
                    + "\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0006\u0012\n"
                    + "\b\u0002\u0010\n"
                    + "\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\fJ"
                    + "\t\u0010\u0016\u001a\u00020\u0006HÆ\u0003J"
                    + "\t\u0010\u0017\u001a\u00020\bHÆ\u0003J\u000b\u0010\u0018\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010\u0019\u001a\u0004\u0018\u00010\u000bHÆ\u0003J5\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\n"
                    + "\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00062\n"
                    + "\b\u0002\u0010\n"
                    + "\u001a\u0004\u0018\u00010\u000bHÆ\u0001J\b\u0010\u001b\u001a\u00020\u001cH\u0016J\u0013\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010"
                    + " HÖ\u0003J\t\u0010!\u001a\u00020\u001cHÖ\u0001J"
                    + "\t\u0010\"\u001a\u00020\u0006HÖ\u0001J\u0018\u0010#\u001a\u00020$2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010%\u001a\u00020\u001cH\u0016R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\r"
                    + "\u0010\u000eR\u0013\u0010\t\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\u000f\u0010\u000eR\u001c\u0010\n"
                    + "\u001a\u0004\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n"
                    + "\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\u0014\u0010\u0015¨\u0006'"
            },
            d2 = {
                "Lcom/samsung/android/gtscell/data/result/GtsItemResult$Error;",
                "Lcom/samsung/android/gtscell/data/result/GtsItemResult;",
                "parcel",
                "Landroid/os/Parcel;",
                "(Landroid/os/Parcel;)V",
                "itemKey",
                ApnSettings.MVNO_NONE,
                "reason",
                "Lcom/samsung/android/gtscell/data/result/GtsItemResult$ErrorReason;",
                "message",
                "pendingIntent",
                "Landroid/app/PendingIntent;",
                "(Ljava/lang/String;Lcom/samsung/android/gtscell/data/result/GtsItemResult$ErrorReason;Ljava/lang/String;Landroid/app/PendingIntent;)V",
                "getItemKey",
                "()Ljava/lang/String;",
                "getMessage",
                "getPendingIntent",
                "()Landroid/app/PendingIntent;",
                "setPendingIntent",
                "(Landroid/app/PendingIntent;)V",
                "getReason",
                "()Lcom/samsung/android/gtscell/data/result/GtsItemResult$ErrorReason;",
                "component1",
                "component2",
                "component3",
                "component4",
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
                "flags",
                "CREATOR",
                "gtscell_release"
            },
            k = 1,
            mv = {1, 1, 16})
    public static final /* data */ class Error extends GtsItemResult {

        /* renamed from: CREATOR, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private final String itemKey;
        private final String message;
        private PendingIntent pendingIntent;
        private final ErrorReason reason;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        @Metadata(
                bv = {1, 0, 3},
                d1 = {
                    "\u0000$\n"
                        + "\u0002\u0018\u0002\n"
                        + "\u0002\u0018\u0002\n"
                        + "\u0002\u0018\u0002\n"
                        + "\u0002\b\u0003\n"
                        + "\u0002\u0018\u0002\n"
                        + "\u0000\n"
                        + "\u0002\u0010\u0011\n"
                        + "\u0000\n"
                        + "\u0002\u0010\b\n"
                        + "\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u001d\u0010\u0007\u001a\n"
                        + "\u0012\u0006\u0012\u0004\u0018\u00010\u00020\b2\u0006\u0010"
                        + "\t\u001a\u00020\n"
                        + "H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"
                },
                d2 = {
                    "Lcom/samsung/android/gtscell/data/result/GtsItemResult$Error$CREATOR;",
                    "Landroid/os/Parcelable$Creator;",
                    "Lcom/samsung/android/gtscell/data/result/GtsItemResult$Error;",
                    "()V",
                    "createFromParcel",
                    "parcel",
                    "Landroid/os/Parcel;",
                    "newArray",
                    ApnSettings.MVNO_NONE,
                    "size",
                    ApnSettings.MVNO_NONE,
                    "(I)[Lcom/samsung/android/gtscell/data/result/GtsItemResult$Error;",
                    "gtscell_release"
                },
                k = 1,
                mv = {1, 1, 16})
        /* renamed from: com.samsung.android.gtscell.data.result.GtsItemResult$Error$CREATOR, reason: from kotlin metadata */
        public static final class Companion implements Parcelable.Creator<Error> {
            private Companion() {}

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Error createFromParcel(Parcel parcel) {
                Intrinsics.checkParameterIsNotNull(parcel, "parcel");
                return new Error(parcel, (DefaultConstructorMarker) null);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Error[] newArray(int size) {
                return new Error[size];
            }
        }

        public Error(String str, ErrorReason errorReason) {
            this(str, errorReason, null, null, 12, null);
        }

        public static /* synthetic */ Error copy$default(
                Error error,
                String str,
                ErrorReason errorReason,
                String str2,
                PendingIntent pendingIntent,
                int i,
                Object obj) {
            if ((i & 1) != 0) {
                str = error.getItemKey();
            }
            if ((i & 2) != 0) {
                errorReason = error.reason;
            }
            if ((i & 4) != 0) {
                str2 = error.message;
            }
            if ((i & 8) != 0) {
                pendingIntent = error.pendingIntent;
            }
            return error.copy(str, errorReason, str2, pendingIntent);
        }

        public final String component1() {
            return getItemKey();
        }

        /* renamed from: component2, reason: from getter */
        public final ErrorReason getReason() {
            return this.reason;
        }

        /* renamed from: component3, reason: from getter */
        public final String getMessage() {
            return this.message;
        }

        /* renamed from: component4, reason: from getter */
        public final PendingIntent getPendingIntent() {
            return this.pendingIntent;
        }

        public final Error copy(
                String itemKey, ErrorReason reason, String message, PendingIntent pendingIntent) {
            Intrinsics.checkParameterIsNotNull(itemKey, "itemKey");
            Intrinsics.checkParameterIsNotNull(reason, "reason");
            return new Error(itemKey, reason, message, pendingIntent);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Error)) {
                return false;
            }
            Error error = (Error) other;
            return Intrinsics.areEqual(getItemKey(), error.getItemKey())
                    && Intrinsics.areEqual(this.reason, error.reason)
                    && Intrinsics.areEqual(this.message, error.message)
                    && Intrinsics.areEqual(this.pendingIntent, error.pendingIntent);
        }

        @Override // com.samsung.android.gtscell.data.result.GtsItemResult
        public String getItemKey() {
            return this.itemKey;
        }

        public final String getMessage() {
            return this.message;
        }

        public final PendingIntent getPendingIntent() {
            return this.pendingIntent;
        }

        public final ErrorReason getReason() {
            return this.reason;
        }

        public int hashCode() {
            String itemKey = getItemKey();
            int hashCode = (itemKey != null ? itemKey.hashCode() : 0) * 31;
            ErrorReason errorReason = this.reason;
            int hashCode2 = (hashCode + (errorReason != null ? errorReason.hashCode() : 0)) * 31;
            String str = this.message;
            int hashCode3 = (hashCode2 + (str != null ? str.hashCode() : 0)) * 31;
            PendingIntent pendingIntent = this.pendingIntent;
            return hashCode3 + (pendingIntent != null ? pendingIntent.hashCode() : 0);
        }

        public final void setPendingIntent(PendingIntent pendingIntent) {
            this.pendingIntent = pendingIntent;
        }

        public String toString() {
            return "Error(itemKey="
                    + getItemKey()
                    + ", reason="
                    + this.reason
                    + ", message="
                    + this.message
                    + ", pendingIntent="
                    + this.pendingIntent
                    + ")";
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int flags) {
            Intrinsics.checkParameterIsNotNull(parcel, "parcel");
            parcel.writeString(getItemKey());
            parcel.writeString(this.reason.name());
            parcel.writeString(this.message);
            PendingIntent pendingIntent = this.pendingIntent;
            if (pendingIntent == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                pendingIntent.writeToParcel(parcel, 0);
            }
        }

        public Error(String str, ErrorReason errorReason, String str2) {
            this(str, errorReason, str2, null, 8, null);
        }

        public /* synthetic */ Error(
                Parcel parcel, DefaultConstructorMarker defaultConstructorMarker) {
            this(parcel);
        }

        public /* synthetic */ Error(
                String str,
                ErrorReason errorReason,
                String str2,
                PendingIntent pendingIntent,
                int i,
                DefaultConstructorMarker defaultConstructorMarker) {
            this(str, errorReason, (i & 4) != 0 ? null : str2, (i & 8) != 0 ? null : pendingIntent);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Error(String itemKey, ErrorReason reason, String str, PendingIntent pendingIntent) {
            super(null);
            Intrinsics.checkParameterIsNotNull(itemKey, "itemKey");
            Intrinsics.checkParameterIsNotNull(reason, "reason");
            this.itemKey = itemKey;
            this.reason = reason;
            this.message = str;
            this.pendingIntent = pendingIntent;
        }

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private Error(android.os.Parcel r6) {
            /*
                r5 = this;
                java.lang.String r0 = r6.readString()
                r1 = 0
                if (r0 == 0) goto L2e
                java.lang.String r2 = "parcel.readString()!!"
                kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r0, r2)
                com.samsung.android.gtscell.data.result.GtsItemResult$ErrorReason$Companion r2 = com.samsung.android.gtscell.data.result.GtsItemResult.ErrorReason.INSTANCE
                java.lang.String r3 = r6.readString()
                com.samsung.android.gtscell.data.result.GtsItemResult$ErrorReason r2 = r2.toEnum(r3)
                java.lang.String r3 = r6.readString()
                int r4 = r6.readInt()
                if (r4 == 0) goto L2a
                android.os.Parcelable$Creator r1 = android.app.PendingIntent.CREATOR
                java.lang.Object r6 = r1.createFromParcel(r6)
                r1 = r6
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
            L2a:
                r5.<init>(r0, r2, r3, r1)
                return
            L2e:
                kotlin.jvm.internal.Intrinsics.throwNpe()
                throw r1
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.samsung.android.gtscell.data.result.GtsItemResult.Error.<init>(android.os.Parcel):void");
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            d1 = {
                "\u0000\f\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0010\u0010\n"
                    + "\u0002\b\u0013\b\u0086\u0001\u0018\u0000"
                    + " \u00132\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0013B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b"
                    + "\tj\u0002\b\n"
                    + "j\u0002\b\u000bj\u0002\b\fj\u0002\b\r"
                    + "j\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012¨\u0006\u0014"
            },
            d2 = {
                "Lcom/samsung/android/gtscell/data/result/GtsItemResult$ErrorReason;",
                ApnSettings.MVNO_NONE,
                "(Ljava/lang/String;I)V",
                "NONE",
                "UNKNOWN",
                "NOT_INITIALIZE",
                "UNSUPPORTED_ITEM",
                "UNSUPPORTED_DEVICE",
                "UNSUPPORTED_FORM_FACTOR",
                "UNSUPPORTED_ONE_UI",
                "PERMISSION",
                "INVALID_DATA_TYPE",
                "INVALID_DATA_VALUE",
                "DEPENDENT_ITEM",
                "EXCLUSIVE_ITEM",
                "EXCEEDED_MAXIMUM_ITEM",
                "TIMEOUT",
                "FATAL",
                "ITEM_MADE_BY_GTS",
                "Companion",
                "gtscell_release"
            },
            k = 1,
            mv = {1, 1, 16})
    public enum ErrorReason {
        NONE,
        UNKNOWN,
        NOT_INITIALIZE,
        UNSUPPORTED_ITEM,
        UNSUPPORTED_DEVICE,
        UNSUPPORTED_FORM_FACTOR,
        UNSUPPORTED_ONE_UI,
        PERMISSION,
        INVALID_DATA_TYPE,
        INVALID_DATA_VALUE,
        DEPENDENT_ITEM,
        EXCLUSIVE_ITEM,
        EXCEEDED_MAXIMUM_ITEM,
        TIMEOUT,
        FATAL,
        ITEM_MADE_BY_GTS;

        /* renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        @Metadata(
                bv = {1, 0, 3},
                d1 = {
                    "\u0000\u0018\n"
                        + "\u0002\u0018\u0002\n"
                        + "\u0002\u0010\u0000\n"
                        + "\u0002\b\u0002\n"
                        + "\u0002\u0018\u0002\n"
                        + "\u0000\n"
                        + "\u0002\u0010\u000e\n"
                        + "\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¨\u0006\u0007"
                },
                d2 = {
                    "Lcom/samsung/android/gtscell/data/result/GtsItemResult$ErrorReason$Companion;",
                    ApnSettings.MVNO_NONE,
                    "()V",
                    "toEnum",
                    "Lcom/samsung/android/gtscell/data/result/GtsItemResult$ErrorReason;",
                    "value",
                    ApnSettings.MVNO_NONE,
                    "gtscell_release"
                },
                k = 1,
                mv = {1, 1, 16})
        public static final class Companion {
            private Companion() {}

            public final ErrorReason toEnum(String value) {
                if (value != null) {
                    try {
                        ErrorReason valueOf = ErrorReason.valueOf(value);
                        if (valueOf != null) {
                            return valueOf;
                        }
                    } catch (IllegalArgumentException unused) {
                        return ErrorReason.UNKNOWN;
                    }
                }
                return ErrorReason.UNKNOWN;
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }
    }

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
                    + "\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n"
                    + "\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005J"
                    + "\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\n"
                    + "\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u001f\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n"
                    + "\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003HÆ\u0001J"
                    + "\t\u0010\f\u001a\u00020\r"
                    + "HÖ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011HÖ\u0003J"
                    + "\t\u0010\u0012\u001a\u00020\r"
                    + "HÖ\u0001J"
                    + "\t\u0010\u0013\u001a\u00020\u0003HÖ\u0001J\u0019\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\r"
                    + "HÖ\u0001R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0019"
            },
            d2 = {
                "Lcom/samsung/android/gtscell/data/result/GtsItemResult$Ignore;",
                "Lcom/samsung/android/gtscell/data/result/GtsItemResult;",
                "itemKey",
                ApnSettings.MVNO_NONE,
                "message",
                "(Ljava/lang/String;Ljava/lang/String;)V",
                "getItemKey",
                "()Ljava/lang/String;",
                "getMessage",
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
    public static final /* data */ class Ignore extends GtsItemResult {
        public static final Parcelable.Creator CREATOR = new Creator();
        private final String itemKey;
        private final String message;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        @Metadata(
                bv = {1, 0, 3},
                k = 3,
                mv = {1, 1, 16})
        public static class Creator implements Parcelable.Creator {
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel in) {
                Intrinsics.checkParameterIsNotNull(in, "in");
                return new Ignore(in.readString(), in.readString());
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new Ignore[i];
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Ignore(String itemKey, String str) {
            super(null);
            Intrinsics.checkParameterIsNotNull(itemKey, "itemKey");
            this.itemKey = itemKey;
            this.message = str;
        }

        public static /* synthetic */ Ignore copy$default(
                Ignore ignore, String str, String str2, int i, Object obj) {
            if ((i & 1) != 0) {
                str = ignore.getItemKey();
            }
            if ((i & 2) != 0) {
                str2 = ignore.message;
            }
            return ignore.copy(str, str2);
        }

        public final String component1() {
            return getItemKey();
        }

        /* renamed from: component2, reason: from getter */
        public final String getMessage() {
            return this.message;
        }

        public final Ignore copy(String itemKey, String message) {
            Intrinsics.checkParameterIsNotNull(itemKey, "itemKey");
            return new Ignore(itemKey, message);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Ignore)) {
                return false;
            }
            Ignore ignore = (Ignore) other;
            return Intrinsics.areEqual(getItemKey(), ignore.getItemKey())
                    && Intrinsics.areEqual(this.message, ignore.message);
        }

        @Override // com.samsung.android.gtscell.data.result.GtsItemResult
        public String getItemKey() {
            return this.itemKey;
        }

        public final String getMessage() {
            return this.message;
        }

        public int hashCode() {
            String itemKey = getItemKey();
            int hashCode = (itemKey != null ? itemKey.hashCode() : 0) * 31;
            String str = this.message;
            return hashCode + (str != null ? str.hashCode() : 0);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("Ignore(itemKey=");
            sb.append(getItemKey());
            sb.append(", message=");
            return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, this.message, ")");
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int flags) {
            Intrinsics.checkParameterIsNotNull(parcel, "parcel");
            parcel.writeString(this.itemKey);
            parcel.writeString(this.message);
        }

        public /* synthetic */ Ignore(
                String str, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, (i & 2) != 0 ? null : str2);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Parcelize
    @Keep
    @Metadata(
            bv = {1, 0, 3},
            d1 = {
                "\u0000<\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u000e\n"
                    + "\u0002\b\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\b\r\n"
                    + "\u0002\u0010\b\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u000b\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u0000\n"
                    + "\u0002\b\u0003\n"
                    + "\u0002\u0010\u0002\n"
                    + "\u0000\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\n"
                    + "\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007J"
                    + "\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J"
                    + "\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0006HÆ\u0003J)\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n"
                    + "\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006HÆ\u0001J"
                    + "\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018HÖ\u0003J"
                    + "\t\u0010\u0019\u001a\u00020\u0014HÖ\u0001J"
                    + "\t\u0010\u001a\u001a\u00020\u0003HÖ\u0001J\u0019\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0014HÖ\u0001R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\b\u0010"
                    + "\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\n"
                    + "\u0010"
                    + "\tR\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n"
                    + "\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r"
                    + "\u0010\u000e¨\u0006 "
            },
            d2 = {
                "Lcom/samsung/android/gtscell/data/result/GtsItemResult$Info;",
                "Lcom/samsung/android/gtscell/data/result/GtsItemResult;",
                "itemKey",
                ApnSettings.MVNO_NONE,
                "message",
                "pendingIntent",
                "Landroid/app/PendingIntent;",
                "(Ljava/lang/String;Ljava/lang/String;Landroid/app/PendingIntent;)V",
                "getItemKey",
                "()Ljava/lang/String;",
                "getMessage",
                "getPendingIntent",
                "()Landroid/app/PendingIntent;",
                "setPendingIntent",
                "(Landroid/app/PendingIntent;)V",
                "component1",
                "component2",
                "component3",
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
    public static final /* data */ class Info extends GtsItemResult {
        public static final Parcelable.Creator CREATOR = new Creator();
        private final String itemKey;
        private final String message;
        private PendingIntent pendingIntent;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        @Metadata(
                bv = {1, 0, 3},
                k = 3,
                mv = {1, 1, 16})
        public static class Creator implements Parcelable.Creator {
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel in) {
                Intrinsics.checkParameterIsNotNull(in, "in");
                return new Info(
                        in.readString(),
                        in.readString(),
                        in.readInt() != 0
                                ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(in)
                                : null);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new Info[i];
            }
        }

        public /* synthetic */ Info(
                String str,
                String str2,
                PendingIntent pendingIntent,
                int i,
                DefaultConstructorMarker defaultConstructorMarker) {
            this(str, str2, (i & 4) != 0 ? null : pendingIntent);
        }

        public static /* synthetic */ Info copy$default(
                Info info,
                String str,
                String str2,
                PendingIntent pendingIntent,
                int i,
                Object obj) {
            if ((i & 1) != 0) {
                str = info.getItemKey();
            }
            if ((i & 2) != 0) {
                str2 = info.message;
            }
            if ((i & 4) != 0) {
                pendingIntent = info.pendingIntent;
            }
            return info.copy(str, str2, pendingIntent);
        }

        public final String component1() {
            return getItemKey();
        }

        /* renamed from: component2, reason: from getter */
        public final String getMessage() {
            return this.message;
        }

        /* renamed from: component3, reason: from getter */
        public final PendingIntent getPendingIntent() {
            return this.pendingIntent;
        }

        public final Info copy(String itemKey, String message, PendingIntent pendingIntent) {
            Intrinsics.checkParameterIsNotNull(itemKey, "itemKey");
            Intrinsics.checkParameterIsNotNull(message, "message");
            return new Info(itemKey, message, pendingIntent);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Info)) {
                return false;
            }
            Info info = (Info) other;
            return Intrinsics.areEqual(getItemKey(), info.getItemKey())
                    && Intrinsics.areEqual(this.message, info.message)
                    && Intrinsics.areEqual(this.pendingIntent, info.pendingIntent);
        }

        @Override // com.samsung.android.gtscell.data.result.GtsItemResult
        public String getItemKey() {
            return this.itemKey;
        }

        public final String getMessage() {
            return this.message;
        }

        public final PendingIntent getPendingIntent() {
            return this.pendingIntent;
        }

        public int hashCode() {
            String itemKey = getItemKey();
            int hashCode = (itemKey != null ? itemKey.hashCode() : 0) * 31;
            String str = this.message;
            int hashCode2 = (hashCode + (str != null ? str.hashCode() : 0)) * 31;
            PendingIntent pendingIntent = this.pendingIntent;
            return hashCode2 + (pendingIntent != null ? pendingIntent.hashCode() : 0);
        }

        public final void setPendingIntent(PendingIntent pendingIntent) {
            this.pendingIntent = pendingIntent;
        }

        public String toString() {
            return "Info(itemKey="
                    + getItemKey()
                    + ", message="
                    + this.message
                    + ", pendingIntent="
                    + this.pendingIntent
                    + ")";
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int flags) {
            Intrinsics.checkParameterIsNotNull(parcel, "parcel");
            parcel.writeString(this.itemKey);
            parcel.writeString(this.message);
            PendingIntent pendingIntent = this.pendingIntent;
            if (pendingIntent == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                pendingIntent.writeToParcel(parcel, 0);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Info(String itemKey, String message, PendingIntent pendingIntent) {
            super(null);
            Intrinsics.checkParameterIsNotNull(itemKey, "itemKey");
            Intrinsics.checkParameterIsNotNull(message, "message");
            this.itemKey = itemKey;
            this.message = message;
            this.pendingIntent = pendingIntent;
        }
    }

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
                    + "\u0002\b\u0006\n"
                    + "\u0002\u0010\b\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u000b\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u0000\n"
                    + "\u0002\b\u0003\n"
                    + "\u0002\u0010\u0002\n"
                    + "\u0000\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\r"
                    + "\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J"
                    + "\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J"
                    + "\t\u0010\t\u001a\u00020\n"
                    + "HÖ\u0001J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r"
                    + "\u001a\u0004\u0018\u00010\u000eHÖ\u0003J\t\u0010\u000f\u001a\u00020\n"
                    + "HÖ\u0001J"
                    + "\t\u0010\u0010\u001a\u00020\u0003HÖ\u0001J\u0019\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\n"
                    + "HÖ\u0001R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0016"
            },
            d2 = {
                "Lcom/samsung/android/gtscell/data/result/GtsItemResult$Pass;",
                "Lcom/samsung/android/gtscell/data/result/GtsItemResult;",
                "itemKey",
                ApnSettings.MVNO_NONE,
                "(Ljava/lang/String;)V",
                "getItemKey",
                "()Ljava/lang/String;",
                "component1",
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
    public static final /* data */ class Pass extends GtsItemResult {
        public static final Parcelable.Creator CREATOR = new Creator();
        private final String itemKey;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        @Metadata(
                bv = {1, 0, 3},
                k = 3,
                mv = {1, 1, 16})
        public static class Creator implements Parcelable.Creator {
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel in) {
                Intrinsics.checkParameterIsNotNull(in, "in");
                return new Pass(in.readString());
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new Pass[i];
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Pass(String itemKey) {
            super(null);
            Intrinsics.checkParameterIsNotNull(itemKey, "itemKey");
            this.itemKey = itemKey;
        }

        public static /* synthetic */ Pass copy$default(Pass pass, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                str = pass.getItemKey();
            }
            return pass.copy(str);
        }

        public final String component1() {
            return getItemKey();
        }

        public final Pass copy(String itemKey) {
            Intrinsics.checkParameterIsNotNull(itemKey, "itemKey");
            return new Pass(itemKey);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object other) {
            if (this != other) {
                return (other instanceof Pass)
                        && Intrinsics.areEqual(getItemKey(), ((Pass) other).getItemKey());
            }
            return true;
        }

        @Override // com.samsung.android.gtscell.data.result.GtsItemResult
        public String getItemKey() {
            return this.itemKey;
        }

        public int hashCode() {
            String itemKey = getItemKey();
            if (itemKey != null) {
                return itemKey.hashCode();
            }
            return 0;
        }

        public String toString() {
            return "Pass(itemKey=" + getItemKey() + ")";
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int flags) {
            Intrinsics.checkParameterIsNotNull(parcel, "parcel");
            parcel.writeString(this.itemKey);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Keep
    @Metadata(
            bv = {1, 0, 3},
            d1 = {
                "\u0000D\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\b\u0002\n"
                    + "\u0002\u0010\u000e\n"
                    + "\u0000\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\b\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\b\u0010\n"
                    + "\u0002\u0010\b\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u000b\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u0000\n"
                    + "\u0002\b\u0003\n"
                    + "\u0002\u0010\u0002\n"
                    + "\u0002\b\u0003\b\u0087\b\u0018\u0000"
                    + " &2\u00020\u0001:\u0001&B\u000f\b\u0012\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B/\b\u0007\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\n"
                    + "\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0006\u0012\n"
                    + "\b\u0002\u0010\n"
                    + "\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\fJ"
                    + "\t\u0010\u0016\u001a\u00020\u0006HÆ\u0003J"
                    + "\t\u0010\u0017\u001a\u00020\bHÆ\u0003J\u000b\u0010\u0018\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010\u0019\u001a\u0004\u0018\u00010\u000bHÆ\u0003J5\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\n"
                    + "\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00062\n"
                    + "\b\u0002\u0010\n"
                    + "\u001a\u0004\u0018\u00010\u000bHÆ\u0001J\b\u0010\u001b\u001a\u00020\u001cH\u0016J\u0013\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010"
                    + " HÖ\u0003J\t\u0010!\u001a\u00020\u001cHÖ\u0001J"
                    + "\t\u0010\"\u001a\u00020\u0006HÖ\u0001J\u0018\u0010#\u001a\u00020$2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010%\u001a\u00020\u001cH\u0016R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\r"
                    + "\u0010\u000eR\u0013\u0010\t\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\u000f\u0010\u000eR\u001c\u0010\n"
                    + "\u001a\u0004\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n"
                    + "\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\u0014\u0010\u0015¨\u0006'"
            },
            d2 = {
                "Lcom/samsung/android/gtscell/data/result/GtsItemResult$Warning;",
                "Lcom/samsung/android/gtscell/data/result/GtsItemResult;",
                "parcel",
                "Landroid/os/Parcel;",
                "(Landroid/os/Parcel;)V",
                "itemKey",
                ApnSettings.MVNO_NONE,
                "reason",
                "Lcom/samsung/android/gtscell/data/result/GtsItemResult$WarningReason;",
                "message",
                "pendingIntent",
                "Landroid/app/PendingIntent;",
                "(Ljava/lang/String;Lcom/samsung/android/gtscell/data/result/GtsItemResult$WarningReason;Ljava/lang/String;Landroid/app/PendingIntent;)V",
                "getItemKey",
                "()Ljava/lang/String;",
                "getMessage",
                "getPendingIntent",
                "()Landroid/app/PendingIntent;",
                "setPendingIntent",
                "(Landroid/app/PendingIntent;)V",
                "getReason",
                "()Lcom/samsung/android/gtscell/data/result/GtsItemResult$WarningReason;",
                "component1",
                "component2",
                "component3",
                "component4",
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
                "flags",
                "CREATOR",
                "gtscell_release"
            },
            k = 1,
            mv = {1, 1, 16})
    public static final /* data */ class Warning extends GtsItemResult {

        /* renamed from: CREATOR, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private final String itemKey;
        private final String message;
        private PendingIntent pendingIntent;
        private final WarningReason reason;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        @Metadata(
                bv = {1, 0, 3},
                d1 = {
                    "\u0000$\n"
                        + "\u0002\u0018\u0002\n"
                        + "\u0002\u0018\u0002\n"
                        + "\u0002\u0018\u0002\n"
                        + "\u0002\b\u0003\n"
                        + "\u0002\u0018\u0002\n"
                        + "\u0000\n"
                        + "\u0002\u0010\u0011\n"
                        + "\u0000\n"
                        + "\u0002\u0010\b\n"
                        + "\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u001d\u0010\u0007\u001a\n"
                        + "\u0012\u0006\u0012\u0004\u0018\u00010\u00020\b2\u0006\u0010"
                        + "\t\u001a\u00020\n"
                        + "H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"
                },
                d2 = {
                    "Lcom/samsung/android/gtscell/data/result/GtsItemResult$Warning$CREATOR;",
                    "Landroid/os/Parcelable$Creator;",
                    "Lcom/samsung/android/gtscell/data/result/GtsItemResult$Warning;",
                    "()V",
                    "createFromParcel",
                    "parcel",
                    "Landroid/os/Parcel;",
                    "newArray",
                    ApnSettings.MVNO_NONE,
                    "size",
                    ApnSettings.MVNO_NONE,
                    "(I)[Lcom/samsung/android/gtscell/data/result/GtsItemResult$Warning;",
                    "gtscell_release"
                },
                k = 1,
                mv = {1, 1, 16})
        /* renamed from: com.samsung.android.gtscell.data.result.GtsItemResult$Warning$CREATOR, reason: from kotlin metadata */
        public static final class Companion implements Parcelable.Creator<Warning> {
            private Companion() {}

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Warning createFromParcel(Parcel parcel) {
                Intrinsics.checkParameterIsNotNull(parcel, "parcel");
                return new Warning(parcel, (DefaultConstructorMarker) null);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Warning[] newArray(int size) {
                return new Warning[size];
            }
        }

        public Warning(String str, WarningReason warningReason) {
            this(str, warningReason, null, null, 12, null);
        }

        public static /* synthetic */ Warning copy$default(
                Warning warning,
                String str,
                WarningReason warningReason,
                String str2,
                PendingIntent pendingIntent,
                int i,
                Object obj) {
            if ((i & 1) != 0) {
                str = warning.getItemKey();
            }
            if ((i & 2) != 0) {
                warningReason = warning.reason;
            }
            if ((i & 4) != 0) {
                str2 = warning.message;
            }
            if ((i & 8) != 0) {
                pendingIntent = warning.pendingIntent;
            }
            return warning.copy(str, warningReason, str2, pendingIntent);
        }

        public final String component1() {
            return getItemKey();
        }

        /* renamed from: component2, reason: from getter */
        public final WarningReason getReason() {
            return this.reason;
        }

        /* renamed from: component3, reason: from getter */
        public final String getMessage() {
            return this.message;
        }

        /* renamed from: component4, reason: from getter */
        public final PendingIntent getPendingIntent() {
            return this.pendingIntent;
        }

        public final Warning copy(
                String itemKey, WarningReason reason, String message, PendingIntent pendingIntent) {
            Intrinsics.checkParameterIsNotNull(itemKey, "itemKey");
            Intrinsics.checkParameterIsNotNull(reason, "reason");
            return new Warning(itemKey, reason, message, pendingIntent);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Warning)) {
                return false;
            }
            Warning warning = (Warning) other;
            return Intrinsics.areEqual(getItemKey(), warning.getItemKey())
                    && Intrinsics.areEqual(this.reason, warning.reason)
                    && Intrinsics.areEqual(this.message, warning.message)
                    && Intrinsics.areEqual(this.pendingIntent, warning.pendingIntent);
        }

        @Override // com.samsung.android.gtscell.data.result.GtsItemResult
        public String getItemKey() {
            return this.itemKey;
        }

        public final String getMessage() {
            return this.message;
        }

        public final PendingIntent getPendingIntent() {
            return this.pendingIntent;
        }

        public final WarningReason getReason() {
            return this.reason;
        }

        public int hashCode() {
            String itemKey = getItemKey();
            int hashCode = (itemKey != null ? itemKey.hashCode() : 0) * 31;
            WarningReason warningReason = this.reason;
            int hashCode2 =
                    (hashCode + (warningReason != null ? warningReason.hashCode() : 0)) * 31;
            String str = this.message;
            int hashCode3 = (hashCode2 + (str != null ? str.hashCode() : 0)) * 31;
            PendingIntent pendingIntent = this.pendingIntent;
            return hashCode3 + (pendingIntent != null ? pendingIntent.hashCode() : 0);
        }

        public final void setPendingIntent(PendingIntent pendingIntent) {
            this.pendingIntent = pendingIntent;
        }

        public String toString() {
            return "Warning(itemKey="
                    + getItemKey()
                    + ", reason="
                    + this.reason
                    + ", message="
                    + this.message
                    + ", pendingIntent="
                    + this.pendingIntent
                    + ")";
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int flags) {
            Intrinsics.checkParameterIsNotNull(parcel, "parcel");
            parcel.writeString(getItemKey());
            parcel.writeString(this.reason.name());
            parcel.writeString(this.message);
            PendingIntent pendingIntent = this.pendingIntent;
            if (pendingIntent == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                pendingIntent.writeToParcel(parcel, 0);
            }
        }

        public Warning(String str, WarningReason warningReason, String str2) {
            this(str, warningReason, str2, null, 8, null);
        }

        public /* synthetic */ Warning(
                Parcel parcel, DefaultConstructorMarker defaultConstructorMarker) {
            this(parcel);
        }

        public /* synthetic */ Warning(
                String str,
                WarningReason warningReason,
                String str2,
                PendingIntent pendingIntent,
                int i,
                DefaultConstructorMarker defaultConstructorMarker) {
            this(
                    str,
                    warningReason,
                    (i & 4) != 0 ? null : str2,
                    (i & 8) != 0 ? null : pendingIntent);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Warning(
                String itemKey, WarningReason reason, String str, PendingIntent pendingIntent) {
            super(null);
            Intrinsics.checkParameterIsNotNull(itemKey, "itemKey");
            Intrinsics.checkParameterIsNotNull(reason, "reason");
            this.itemKey = itemKey;
            this.reason = reason;
            this.message = str;
            this.pendingIntent = pendingIntent;
        }

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private Warning(android.os.Parcel r6) {
            /*
                r5 = this;
                java.lang.String r0 = r6.readString()
                r1 = 0
                if (r0 == 0) goto L2e
                java.lang.String r2 = "parcel.readString()!!"
                kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r0, r2)
                com.samsung.android.gtscell.data.result.GtsItemResult$WarningReason$Companion r2 = com.samsung.android.gtscell.data.result.GtsItemResult.WarningReason.INSTANCE
                java.lang.String r3 = r6.readString()
                com.samsung.android.gtscell.data.result.GtsItemResult$WarningReason r2 = r2.toEnum(r3)
                java.lang.String r3 = r6.readString()
                int r4 = r6.readInt()
                if (r4 == 0) goto L2a
                android.os.Parcelable$Creator r1 = android.app.PendingIntent.CREATOR
                java.lang.Object r6 = r1.createFromParcel(r6)
                r1 = r6
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
            L2a:
                r5.<init>(r0, r2, r3, r1)
                return
            L2e:
                kotlin.jvm.internal.Intrinsics.throwNpe()
                throw r1
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.samsung.android.gtscell.data.result.GtsItemResult.Warning.<init>(android.os.Parcel):void");
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            d1 = {
                "\u0000\f\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0010\u0010\n"
                    + "\u0002\b\u0007\b\u0086\u0001\u0018\u0000"
                    + " \u00072\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0007B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\b"
            },
            d2 = {
                "Lcom/samsung/android/gtscell/data/result/GtsItemResult$WarningReason;",
                ApnSettings.MVNO_NONE,
                "(Ljava/lang/String;I)V",
                "NONE",
                "UNKNOWN",
                "DUPLICATED_ITEM",
                "REQUIRE_USER_ACTION",
                "Companion",
                "gtscell_release"
            },
            k = 1,
            mv = {1, 1, 16})
    public enum WarningReason {
        NONE,
        UNKNOWN,
        DUPLICATED_ITEM,
        REQUIRE_USER_ACTION;

        /* renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        @Metadata(
                bv = {1, 0, 3},
                d1 = {
                    "\u0000\u0018\n"
                        + "\u0002\u0018\u0002\n"
                        + "\u0002\u0010\u0000\n"
                        + "\u0002\b\u0002\n"
                        + "\u0002\u0018\u0002\n"
                        + "\u0000\n"
                        + "\u0002\u0010\u000e\n"
                        + "\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¨\u0006\u0007"
                },
                d2 = {
                    "Lcom/samsung/android/gtscell/data/result/GtsItemResult$WarningReason$Companion;",
                    ApnSettings.MVNO_NONE,
                    "()V",
                    "toEnum",
                    "Lcom/samsung/android/gtscell/data/result/GtsItemResult$WarningReason;",
                    "value",
                    ApnSettings.MVNO_NONE,
                    "gtscell_release"
                },
                k = 1,
                mv = {1, 1, 16})
        public static final class Companion {
            private Companion() {}

            public final WarningReason toEnum(String value) {
                if (value != null) {
                    try {
                        WarningReason valueOf = WarningReason.valueOf(value);
                        if (valueOf != null) {
                            return valueOf;
                        }
                    } catch (IllegalArgumentException unused) {
                        return WarningReason.UNKNOWN;
                    }
                }
                return WarningReason.UNKNOWN;
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }
    }

    private GtsItemResult() {}

    public abstract String getItemKey();

    public /* synthetic */ GtsItemResult(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}
