package com.samsung.android.gtscell.data;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        bv = {1, 0, 3},
        d1 = {
            "\u00002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0000\n"
                + "\u0002\b\n\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\b6\u0018\u00002\u00020\u0001:\t\u0003\u0004\u0005\u0006\u0007\b\t\n"
                + "\u000bB\u0007\b\u0002¢\u0006\u0002\u0010\u0002\u0082\u0001\t\f\r"
                + "\u000e\u000f\u0010\u0011\u0012\u0013\u0014¨\u0006\u0015"
        },
        d2 = {
            "Lcom/samsung/android/gtscell/data/GtsExpressionValue;",
            ApnSettings.MVNO_NONE,
            "()V",
            "Color",
            "Empty",
            "Icon",
            "Level",
            "MimeIcon",
            "MimeUri",
            "MimeUrl",
            "OnOff",
            "Progress",
            "Lcom/samsung/android/gtscell/data/GtsExpressionValue$OnOff;",
            "Lcom/samsung/android/gtscell/data/GtsExpressionValue$Color;",
            "Lcom/samsung/android/gtscell/data/GtsExpressionValue$MimeUri;",
            "Lcom/samsung/android/gtscell/data/GtsExpressionValue$MimeUrl;",
            "Lcom/samsung/android/gtscell/data/GtsExpressionValue$MimeIcon;",
            "Lcom/samsung/android/gtscell/data/GtsExpressionValue$Icon;",
            "Lcom/samsung/android/gtscell/data/GtsExpressionValue$Level;",
            "Lcom/samsung/android/gtscell/data/GtsExpressionValue$Progress;",
            "Lcom/samsung/android/gtscell/data/GtsExpressionValue$Empty;",
            "gtscell_release"
        },
        k = 1,
        mv = {1, 1, 16})
/* loaded from: classes4.dex */
public abstract class GtsExpressionValue {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            d1 = {
                "\u0000&\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\n"
                    + "\u0002\u0010\b\n"
                    + "\u0002\b\u0006\n"
                    + "\u0002\u0010\u000b\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u0000\n"
                    + "\u0002\b\u0002\n"
                    + "\u0002\u0010\u000e\n"
                    + "\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r"
                    + "\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J"
                    + "\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010"
                    + "\t\u001a\u00020\n"
                    + "2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r"
                    + "\u001a\u00020\u0003HÖ\u0001J"
                    + "\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"
            },
            d2 = {
                "Lcom/samsung/android/gtscell/data/GtsExpressionValue$Color;",
                "Lcom/samsung/android/gtscell/data/GtsExpressionValue;",
                "value",
                ApnSettings.MVNO_NONE,
                "(I)V",
                "getValue",
                "()I",
                "component1",
                "copy",
                "equals",
                ApnSettings.MVNO_NONE,
                "other",
                ApnSettings.MVNO_NONE,
                "hashCode",
                "toString",
                ApnSettings.MVNO_NONE,
                "gtscell_release"
            },
            k = 1,
            mv = {1, 1, 16})
    public static final /* data */ class Color extends GtsExpressionValue {
        private final int value;

        public Color(int i) {
            super(null);
            this.value = i;
        }

        public static /* synthetic */ Color copy$default(Color color, int i, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                i = color.value;
            }
            return color.copy(i);
        }

        /* renamed from: component1, reason: from getter */
        public final int getValue() {
            return this.value;
        }

        public final Color copy(int value) {
            return new Color(value);
        }

        public boolean equals(Object other) {
            if (this != other) {
                return (other instanceof Color) && this.value == ((Color) other).value;
            }
            return true;
        }

        public final int getValue() {
            return this.value;
        }

        public int hashCode() {
            return Integer.hashCode(this.value);
        }

        public String toString() {
            return Anchor$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Color(value="), this.value, ")");
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            d1 = {
                "\u0000\f\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"
            },
            d2 = {
                "Lcom/samsung/android/gtscell/data/GtsExpressionValue$Empty;",
                "Lcom/samsung/android/gtscell/data/GtsExpressionValue;",
                "()V",
                "gtscell_release"
            },
            k = 1,
            mv = {1, 1, 16})
    public static final class Empty extends GtsExpressionValue {
        public static final Empty INSTANCE = new Empty();

        private Empty() {
            super(null);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            d1 = {
                "\u0000*\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u000b\n"
                    + "\u0002\b\n\n"
                    + "\u0002\u0010\u0000\n"
                    + "\u0000\n"
                    + "\u0002\u0010\b\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u000e\n"
                    + "\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J"
                    + "\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J"
                    + "\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r"
                    + "\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u00052\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010HÖ\u0003J"
                    + "\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J"
                    + "\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\t\u0010\n"
                    + "¨\u0006\u0015"
            },
            d2 = {
                "Lcom/samsung/android/gtscell/data/GtsExpressionValue$Icon;",
                "Lcom/samsung/android/gtscell/data/GtsExpressionValue;",
                "value",
                "Landroid/graphics/Bitmap;",
                "enlargeable",
                ApnSettings.MVNO_NONE,
                "(Landroid/graphics/Bitmap;Z)V",
                "getEnlargeable",
                "()Z",
                "getValue",
                "()Landroid/graphics/Bitmap;",
                "component1",
                "component2",
                "copy",
                "equals",
                "other",
                ApnSettings.MVNO_NONE,
                "hashCode",
                ApnSettings.MVNO_NONE,
                "toString",
                ApnSettings.MVNO_NONE,
                "gtscell_release"
            },
            k = 1,
            mv = {1, 1, 16})
    public static final /* data */ class Icon extends GtsExpressionValue {
        private final boolean enlargeable;
        private final Bitmap value;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Icon(Bitmap value, boolean z) {
            super(null);
            Intrinsics.checkParameterIsNotNull(value, "value");
            this.value = value;
            this.enlargeable = z;
        }

        public static /* synthetic */ Icon copy$default(
                Icon icon, Bitmap bitmap, boolean z, int i, Object obj) {
            if ((i & 1) != 0) {
                bitmap = icon.value;
            }
            if ((i & 2) != 0) {
                z = icon.enlargeable;
            }
            return icon.copy(bitmap, z);
        }

        /* renamed from: component1, reason: from getter */
        public final Bitmap getValue() {
            return this.value;
        }

        /* renamed from: component2, reason: from getter */
        public final boolean getEnlargeable() {
            return this.enlargeable;
        }

        public final Icon copy(Bitmap value, boolean enlargeable) {
            Intrinsics.checkParameterIsNotNull(value, "value");
            return new Icon(value, enlargeable);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Icon)) {
                return false;
            }
            Icon icon = (Icon) other;
            return Intrinsics.areEqual(this.value, icon.value)
                    && this.enlargeable == icon.enlargeable;
        }

        public final boolean getEnlargeable() {
            return this.enlargeable;
        }

        public final Bitmap getValue() {
            return this.value;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public int hashCode() {
            Bitmap bitmap = this.value;
            int hashCode = (bitmap != null ? bitmap.hashCode() : 0) * 31;
            boolean z = this.enlargeable;
            int i = z;
            if (z != 0) {
                i = 1;
            }
            return hashCode + i;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("Icon(value=");
            sb.append(this.value);
            sb.append(", enlargeable=");
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.enlargeable, ")");
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            d1 = {
                "\u0000(\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\n"
                    + "\u0002\u0010\b\n"
                    + "\u0002\b\u0004\n"
                    + "\u0002\u0010\u000e\n"
                    + "\u0002\b\u000f\n"
                    + "\u0002\u0010\u000b\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u0000\n"
                    + "\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010"
                    + "\tJ\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J"
                    + "\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J"
                    + "\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J"
                    + "\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J"
                    + "\t\u0010\u0015\u001a\u00020\bHÆ\u0003J;\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\bHÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aHÖ\u0003J"
                    + "\t\u0010\u001b\u001a\u00020\u0003HÖ\u0001J"
                    + "\t\u0010\u001c\u001a\u00020\bHÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\n"
                    + "\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\r"
                    + "\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\u000f\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\u0010\u0010\u000b¨\u0006\u001d"
            },
            d2 = {
                "Lcom/samsung/android/gtscell/data/GtsExpressionValue$Level;",
                "Lcom/samsung/android/gtscell/data/GtsExpressionValue;",
                "value",
                ApnSettings.MVNO_NONE,
                "min",
                "max",
                "step",
                "name",
                ApnSettings.MVNO_NONE,
                "(IIIILjava/lang/String;)V",
                "getMax",
                "()I",
                "getMin",
                "getName",
                "()Ljava/lang/String;",
                "getStep",
                "getValue",
                "component1",
                "component2",
                "component3",
                "component4",
                "component5",
                "copy",
                "equals",
                ApnSettings.MVNO_NONE,
                "other",
                ApnSettings.MVNO_NONE,
                "hashCode",
                "toString",
                "gtscell_release"
            },
            k = 1,
            mv = {1, 1, 16})
    public static final /* data */ class Level extends GtsExpressionValue {
        private final int max;
        private final int min;
        private final String name;
        private final int step;
        private final int value;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Level(int i, int i2, int i3, int i4, String name) {
            super(null);
            Intrinsics.checkParameterIsNotNull(name, "name");
            this.value = i;
            this.min = i2;
            this.max = i3;
            this.step = i4;
            this.name = name;
        }

        public static /* synthetic */ Level copy$default(
                Level level, int i, int i2, int i3, int i4, String str, int i5, Object obj) {
            if ((i5 & 1) != 0) {
                i = level.value;
            }
            if ((i5 & 2) != 0) {
                i2 = level.min;
            }
            int i6 = i2;
            if ((i5 & 4) != 0) {
                i3 = level.max;
            }
            int i7 = i3;
            if ((i5 & 8) != 0) {
                i4 = level.step;
            }
            int i8 = i4;
            if ((i5 & 16) != 0) {
                str = level.name;
            }
            return level.copy(i, i6, i7, i8, str);
        }

        /* renamed from: component1, reason: from getter */
        public final int getValue() {
            return this.value;
        }

        /* renamed from: component2, reason: from getter */
        public final int getMin() {
            return this.min;
        }

        /* renamed from: component3, reason: from getter */
        public final int getMax() {
            return this.max;
        }

        /* renamed from: component4, reason: from getter */
        public final int getStep() {
            return this.step;
        }

        /* renamed from: component5, reason: from getter */
        public final String getName() {
            return this.name;
        }

        public final Level copy(int value, int min, int max, int step, String name) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            return new Level(value, min, max, step, name);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Level)) {
                return false;
            }
            Level level = (Level) other;
            return this.value == level.value
                    && this.min == level.min
                    && this.max == level.max
                    && this.step == level.step
                    && Intrinsics.areEqual(this.name, level.name);
        }

        public final int getMax() {
            return this.max;
        }

        public final int getMin() {
            return this.min;
        }

        public final String getName() {
            return this.name;
        }

        public final int getStep() {
            return this.step;
        }

        public final int getValue() {
            return this.value;
        }

        public int hashCode() {
            int m =
                    KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(
                            this.step,
                            KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(
                                    this.max,
                                    KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(
                                            this.min, Integer.hashCode(this.value) * 31, 31),
                                    31),
                            31);
            String str = this.name;
            return m + (str != null ? str.hashCode() : 0);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("Level(value=");
            sb.append(this.value);
            sb.append(", min=");
            sb.append(this.min);
            sb.append(", max=");
            sb.append(this.max);
            sb.append(", step=");
            sb.append(this.step);
            sb.append(", name=");
            return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, this.name, ")");
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            d1 = {
                "\u0000*\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\b\u0006\n"
                    + "\u0002\u0010\u000b\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u0000\n"
                    + "\u0000\n"
                    + "\u0002\u0010\b\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u000e\n"
                    + "\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r"
                    + "\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J"
                    + "\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010"
                    + "\t\u001a\u00020\n"
                    + "2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r"
                    + "\u001a\u00020\u000eHÖ\u0001J"
                    + "\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"
            },
            d2 = {
                "Lcom/samsung/android/gtscell/data/GtsExpressionValue$MimeIcon;",
                "Lcom/samsung/android/gtscell/data/GtsExpressionValue;",
                "mimeType",
                "Lcom/samsung/android/gtscell/data/GtsMimeType;",
                "(Lcom/samsung/android/gtscell/data/GtsMimeType;)V",
                "getMimeType",
                "()Lcom/samsung/android/gtscell/data/GtsMimeType;",
                "component1",
                "copy",
                "equals",
                ApnSettings.MVNO_NONE,
                "other",
                ApnSettings.MVNO_NONE,
                "hashCode",
                ApnSettings.MVNO_NONE,
                "toString",
                ApnSettings.MVNO_NONE,
                "gtscell_release"
            },
            k = 1,
            mv = {1, 1, 16})
    public static final /* data */ class MimeIcon extends GtsExpressionValue {
        private final GtsMimeType mimeType;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public MimeIcon(GtsMimeType mimeType) {
            super(null);
            Intrinsics.checkParameterIsNotNull(mimeType, "mimeType");
            this.mimeType = mimeType;
        }

        public static /* synthetic */ MimeIcon copy$default(
                MimeIcon mimeIcon, GtsMimeType gtsMimeType, int i, Object obj) {
            if ((i & 1) != 0) {
                gtsMimeType = mimeIcon.mimeType;
            }
            return mimeIcon.copy(gtsMimeType);
        }

        /* renamed from: component1, reason: from getter */
        public final GtsMimeType getMimeType() {
            return this.mimeType;
        }

        public final MimeIcon copy(GtsMimeType mimeType) {
            Intrinsics.checkParameterIsNotNull(mimeType, "mimeType");
            return new MimeIcon(mimeType);
        }

        public boolean equals(Object other) {
            if (this != other) {
                return (other instanceof MimeIcon)
                        && Intrinsics.areEqual(this.mimeType, ((MimeIcon) other).mimeType);
            }
            return true;
        }

        public final GtsMimeType getMimeType() {
            return this.mimeType;
        }

        public int hashCode() {
            GtsMimeType gtsMimeType = this.mimeType;
            if (gtsMimeType != null) {
                return gtsMimeType.hashCode();
            }
            return 0;
        }

        public String toString() {
            return "MimeIcon(mimeType=" + this.mimeType + ")";
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            d1 = {
                "\u0000*\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u000b\n"
                    + "\u0002\b\n\n"
                    + "\u0002\u0010\u0000\n"
                    + "\u0000\n"
                    + "\u0002\u0010\b\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u000e\n"
                    + "\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J"
                    + "\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J"
                    + "\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r"
                    + "\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u00052\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010HÖ\u0003J"
                    + "\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J"
                    + "\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\t\u0010\n"
                    + "¨\u0006\u0015"
            },
            d2 = {
                "Lcom/samsung/android/gtscell/data/GtsExpressionValue$MimeUri;",
                "Lcom/samsung/android/gtscell/data/GtsExpressionValue;",
                "value",
                "Landroid/net/Uri;",
                "enlargeable",
                ApnSettings.MVNO_NONE,
                "(Landroid/net/Uri;Z)V",
                "getEnlargeable",
                "()Z",
                "getValue",
                "()Landroid/net/Uri;",
                "component1",
                "component2",
                "copy",
                "equals",
                "other",
                ApnSettings.MVNO_NONE,
                "hashCode",
                ApnSettings.MVNO_NONE,
                "toString",
                ApnSettings.MVNO_NONE,
                "gtscell_release"
            },
            k = 1,
            mv = {1, 1, 16})
    public static final /* data */ class MimeUri extends GtsExpressionValue {
        private final boolean enlargeable;
        private final Uri value;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public MimeUri(Uri value, boolean z) {
            super(null);
            Intrinsics.checkParameterIsNotNull(value, "value");
            this.value = value;
            this.enlargeable = z;
        }

        public static /* synthetic */ MimeUri copy$default(
                MimeUri mimeUri, Uri uri, boolean z, int i, Object obj) {
            if ((i & 1) != 0) {
                uri = mimeUri.value;
            }
            if ((i & 2) != 0) {
                z = mimeUri.enlargeable;
            }
            return mimeUri.copy(uri, z);
        }

        /* renamed from: component1, reason: from getter */
        public final Uri getValue() {
            return this.value;
        }

        /* renamed from: component2, reason: from getter */
        public final boolean getEnlargeable() {
            return this.enlargeable;
        }

        public final MimeUri copy(Uri value, boolean enlargeable) {
            Intrinsics.checkParameterIsNotNull(value, "value");
            return new MimeUri(value, enlargeable);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof MimeUri)) {
                return false;
            }
            MimeUri mimeUri = (MimeUri) other;
            return Intrinsics.areEqual(this.value, mimeUri.value)
                    && this.enlargeable == mimeUri.enlargeable;
        }

        public final boolean getEnlargeable() {
            return this.enlargeable;
        }

        public final Uri getValue() {
            return this.value;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public int hashCode() {
            Uri uri = this.value;
            int hashCode = (uri != null ? uri.hashCode() : 0) * 31;
            boolean z = this.enlargeable;
            int i = z;
            if (z != 0) {
                i = 1;
            }
            return hashCode + i;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("MimeUri(value=");
            sb.append(this.value);
            sb.append(", enlargeable=");
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.enlargeable, ")");
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            d1 = {
                "\u0000&\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u000e\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u000b\n"
                    + "\u0002\b\n\n"
                    + "\u0002\u0010\u0000\n"
                    + "\u0000\n"
                    + "\u0002\u0010\b\n"
                    + "\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J"
                    + "\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J"
                    + "\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r"
                    + "\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u00052\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010HÖ\u0003J"
                    + "\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J"
                    + "\t\u0010\u0013\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\t\u0010\n"
                    + "¨\u0006\u0014"
            },
            d2 = {
                "Lcom/samsung/android/gtscell/data/GtsExpressionValue$MimeUrl;",
                "Lcom/samsung/android/gtscell/data/GtsExpressionValue;",
                "value",
                ApnSettings.MVNO_NONE,
                "enlargeable",
                ApnSettings.MVNO_NONE,
                "(Ljava/lang/String;Z)V",
                "getEnlargeable",
                "()Z",
                "getValue",
                "()Ljava/lang/String;",
                "component1",
                "component2",
                "copy",
                "equals",
                "other",
                ApnSettings.MVNO_NONE,
                "hashCode",
                ApnSettings.MVNO_NONE,
                "toString",
                "gtscell_release"
            },
            k = 1,
            mv = {1, 1, 16})
    public static final /* data */ class MimeUrl extends GtsExpressionValue {
        private final boolean enlargeable;
        private final String value;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public MimeUrl(String value, boolean z) {
            super(null);
            Intrinsics.checkParameterIsNotNull(value, "value");
            this.value = value;
            this.enlargeable = z;
        }

        public static /* synthetic */ MimeUrl copy$default(
                MimeUrl mimeUrl, String str, boolean z, int i, Object obj) {
            if ((i & 1) != 0) {
                str = mimeUrl.value;
            }
            if ((i & 2) != 0) {
                z = mimeUrl.enlargeable;
            }
            return mimeUrl.copy(str, z);
        }

        /* renamed from: component1, reason: from getter */
        public final String getValue() {
            return this.value;
        }

        /* renamed from: component2, reason: from getter */
        public final boolean getEnlargeable() {
            return this.enlargeable;
        }

        public final MimeUrl copy(String value, boolean enlargeable) {
            Intrinsics.checkParameterIsNotNull(value, "value");
            return new MimeUrl(value, enlargeable);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof MimeUrl)) {
                return false;
            }
            MimeUrl mimeUrl = (MimeUrl) other;
            return Intrinsics.areEqual(this.value, mimeUrl.value)
                    && this.enlargeable == mimeUrl.enlargeable;
        }

        public final boolean getEnlargeable() {
            return this.enlargeable;
        }

        public final String getValue() {
            return this.value;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public int hashCode() {
            String str = this.value;
            int hashCode = (str != null ? str.hashCode() : 0) * 31;
            boolean z = this.enlargeable;
            int i = z;
            if (z != 0) {
                i = 1;
            }
            return hashCode + i;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("MimeUrl(value=");
            sb.append(this.value);
            sb.append(", enlargeable=");
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.enlargeable, ")");
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            d1 = {
                "\u0000$\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u000b\n"
                    + "\u0002\b\u0007\n"
                    + "\u0002\u0010\u0000\n"
                    + "\u0000\n"
                    + "\u0002\u0010\b\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u000e\n"
                    + "\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r"
                    + "\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J"
                    + "\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010"
                    + "\t\u001a\u00020\u00032\b\u0010\n"
                    + "\u001a\u0004\u0018\u00010\u000bHÖ\u0003J\t\u0010\f\u001a\u00020\r"
                    + "HÖ\u0001J"
                    + "\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"
            },
            d2 = {
                "Lcom/samsung/android/gtscell/data/GtsExpressionValue$OnOff;",
                "Lcom/samsung/android/gtscell/data/GtsExpressionValue;",
                "value",
                ApnSettings.MVNO_NONE,
                "(Z)V",
                "getValue",
                "()Z",
                "component1",
                "copy",
                "equals",
                "other",
                ApnSettings.MVNO_NONE,
                "hashCode",
                ApnSettings.MVNO_NONE,
                "toString",
                ApnSettings.MVNO_NONE,
                "gtscell_release"
            },
            k = 1,
            mv = {1, 1, 16})
    public static final /* data */ class OnOff extends GtsExpressionValue {
        private final boolean value;

        public OnOff(boolean z) {
            super(null);
            this.value = z;
        }

        public static /* synthetic */ OnOff copy$default(
                OnOff onOff, boolean z, int i, Object obj) {
            if ((i & 1) != 0) {
                z = onOff.value;
            }
            return onOff.copy(z);
        }

        /* renamed from: component1, reason: from getter */
        public final boolean getValue() {
            return this.value;
        }

        public final OnOff copy(boolean value) {
            return new OnOff(value);
        }

        public boolean equals(Object other) {
            if (this != other) {
                return (other instanceof OnOff) && this.value == ((OnOff) other).value;
            }
            return true;
        }

        public final boolean getValue() {
            return this.value;
        }

        public int hashCode() {
            boolean z = this.value;
            if (z) {
                return 1;
            }
            return z ? 1 : 0;
        }

        public String toString() {
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(
                    new StringBuilder("OnOff(value="), this.value, ")");
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            d1 = {
                "\u0000(\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\n"
                    + "\u0002\u0010\b\n"
                    + "\u0002\b\u0003\n"
                    + "\u0002\u0010\u000e\n"
                    + "\u0002\b\r\n"
                    + "\u0002\u0010\u000b\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u0000\n"
                    + "\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ"
                    + "\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J"
                    + "\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J"
                    + "\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J"
                    + "\t\u0010\u0012\u001a\u00020\u0007HÆ\u0003J1\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017HÖ\u0003J"
                    + "\t\u0010\u0018\u001a\u00020\u0003HÖ\u0001J"
                    + "\t\u0010\u0019\u001a\u00020\u0007HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\t\u0010\n"
                    + "R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\u000b\u0010\n"
                    + "R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\f\u0010\r"
                    + "R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\u000e\u0010\n"
                    + "¨\u0006\u001a"
            },
            d2 = {
                "Lcom/samsung/android/gtscell/data/GtsExpressionValue$Progress;",
                "Lcom/samsung/android/gtscell/data/GtsExpressionValue;",
                "value",
                ApnSettings.MVNO_NONE,
                "min",
                "max",
                "name",
                ApnSettings.MVNO_NONE,
                "(IIILjava/lang/String;)V",
                "getMax",
                "()I",
                "getMin",
                "getName",
                "()Ljava/lang/String;",
                "getValue",
                "component1",
                "component2",
                "component3",
                "component4",
                "copy",
                "equals",
                ApnSettings.MVNO_NONE,
                "other",
                ApnSettings.MVNO_NONE,
                "hashCode",
                "toString",
                "gtscell_release"
            },
            k = 1,
            mv = {1, 1, 16})
    public static final /* data */ class Progress extends GtsExpressionValue {
        private final int max;
        private final int min;
        private final String name;
        private final int value;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Progress(int i, int i2, int i3, String name) {
            super(null);
            Intrinsics.checkParameterIsNotNull(name, "name");
            this.value = i;
            this.min = i2;
            this.max = i3;
            this.name = name;
        }

        public static /* synthetic */ Progress copy$default(
                Progress progress, int i, int i2, int i3, String str, int i4, Object obj) {
            if ((i4 & 1) != 0) {
                i = progress.value;
            }
            if ((i4 & 2) != 0) {
                i2 = progress.min;
            }
            if ((i4 & 4) != 0) {
                i3 = progress.max;
            }
            if ((i4 & 8) != 0) {
                str = progress.name;
            }
            return progress.copy(i, i2, i3, str);
        }

        /* renamed from: component1, reason: from getter */
        public final int getValue() {
            return this.value;
        }

        /* renamed from: component2, reason: from getter */
        public final int getMin() {
            return this.min;
        }

        /* renamed from: component3, reason: from getter */
        public final int getMax() {
            return this.max;
        }

        /* renamed from: component4, reason: from getter */
        public final String getName() {
            return this.name;
        }

        public final Progress copy(int value, int min, int max, String name) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            return new Progress(value, min, max, name);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Progress)) {
                return false;
            }
            Progress progress = (Progress) other;
            return this.value == progress.value
                    && this.min == progress.min
                    && this.max == progress.max
                    && Intrinsics.areEqual(this.name, progress.name);
        }

        public final int getMax() {
            return this.max;
        }

        public final int getMin() {
            return this.min;
        }

        public final String getName() {
            return this.name;
        }

        public final int getValue() {
            return this.value;
        }

        public int hashCode() {
            int m =
                    KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(
                            this.max,
                            KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(
                                    this.min, Integer.hashCode(this.value) * 31, 31),
                            31);
            String str = this.name;
            return m + (str != null ? str.hashCode() : 0);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("Progress(value=");
            sb.append(this.value);
            sb.append(", min=");
            sb.append(this.min);
            sb.append(", max=");
            sb.append(this.max);
            sb.append(", name=");
            return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, this.name, ")");
        }
    }

    private GtsExpressionValue() {}

    public /* synthetic */ GtsExpressionValue(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}
