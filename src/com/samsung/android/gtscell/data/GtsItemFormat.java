package com.samsung.android.gtscell.data;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'FORMAT_BOOLEAN' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        bv = {1, 0, 3},
        d1 = {
            "\u0000\u0012\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0010\n"
                + "\u0000\n"
                + "\u0002\u0010\u000b\n"
                + "\u0002\b\u000b\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0011\b\u0002\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b"
                + "\tj\u0002\b\n"
                + "j\u0002\b\u000bj\u0002\b\fj\u0002\b\r"
                + "¨\u0006\u000e"
        },
        d2 = {
            "Lcom/samsung/android/gtscell/data/GtsItemFormat;",
            ApnSettings.MVNO_NONE,
            "fromGts",
            ApnSettings.MVNO_NONE,
            "(Ljava/lang/String;IZ)V",
            "getFromGts",
            "()Z",
            "FORMAT_TEXT",
            "FORMAT_BOOLEAN",
            "FORMAT_INT",
            "FORMAT_LONG",
            "FORMAT_DOUBLE",
            "FORMAT_URI",
            "FORMAT_URL",
            "gtscell_release"
        },
        k = 1,
        mv = {1, 1, 16})
/* loaded from: classes4.dex */
public final class GtsItemFormat {
    private static final /* synthetic */ GtsItemFormat[] $VALUES;
    public static final GtsItemFormat FORMAT_BOOLEAN;
    public static final GtsItemFormat FORMAT_DOUBLE;
    public static final GtsItemFormat FORMAT_INT;
    public static final GtsItemFormat FORMAT_LONG;
    public static final GtsItemFormat FORMAT_TEXT;
    public static final GtsItemFormat FORMAT_URI;
    public static final GtsItemFormat FORMAT_URL;
    private final boolean fromGts;

    static {
        GtsItemFormat gtsItemFormat = new GtsItemFormat("FORMAT_TEXT", 0, false, 1, null);
        FORMAT_TEXT = gtsItemFormat;
        int i = 1;
        DefaultConstructorMarker defaultConstructorMarker = null;
        boolean z = false;
        GtsItemFormat gtsItemFormat2 =
                new GtsItemFormat("FORMAT_BOOLEAN", 1, z, i, defaultConstructorMarker);
        FORMAT_BOOLEAN = gtsItemFormat2;
        int i2 = 1;
        DefaultConstructorMarker defaultConstructorMarker2 = null;
        boolean z2 = false;
        GtsItemFormat gtsItemFormat3 =
                new GtsItemFormat("FORMAT_INT", 2, z2, i2, defaultConstructorMarker2);
        FORMAT_INT = gtsItemFormat3;
        GtsItemFormat gtsItemFormat4 =
                new GtsItemFormat("FORMAT_LONG", 3, z, i, defaultConstructorMarker);
        FORMAT_LONG = gtsItemFormat4;
        GtsItemFormat gtsItemFormat5 =
                new GtsItemFormat("FORMAT_DOUBLE", 4, z2, i2, defaultConstructorMarker2);
        FORMAT_DOUBLE = gtsItemFormat5;
        GtsItemFormat gtsItemFormat6 =
                new GtsItemFormat("FORMAT_URI", 5, z, i, defaultConstructorMarker);
        FORMAT_URI = gtsItemFormat6;
        GtsItemFormat gtsItemFormat7 = new GtsItemFormat("FORMAT_URL", 6, true);
        FORMAT_URL = gtsItemFormat7;
        $VALUES =
                new GtsItemFormat[] {
                    gtsItemFormat,
                    gtsItemFormat2,
                    gtsItemFormat3,
                    gtsItemFormat4,
                    gtsItemFormat5,
                    gtsItemFormat6,
                    gtsItemFormat7
                };
    }

    private GtsItemFormat(String str, int i, boolean z) {
        this.fromGts = z;
    }

    public static GtsItemFormat valueOf(String str) {
        return (GtsItemFormat) Enum.valueOf(GtsItemFormat.class, str);
    }

    public static GtsItemFormat[] values() {
        return (GtsItemFormat[]) $VALUES.clone();
    }

    public final boolean getFromGts() {
        return this.fromGts;
    }

    public /* synthetic */ GtsItemFormat(
            String str,
            int i,
            boolean z,
            int i2,
            DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i, (i2 & 1) != 0 ? false : z);
    }
}
