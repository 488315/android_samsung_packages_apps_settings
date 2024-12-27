package com.samsung.android.settings.asbase.vibration;

import android.net.Uri;
import java.util.HashMap;
import java.util.Map;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'RINGTONE_WITH_INDEX' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class VibUri {
    public static final /* synthetic */ VibUri[] $VALUES;
    public static final VibUri NOTIFICATION_PATH_WITH_INDEX;
    public static final VibUri RINGTONE_PATH_WITH_INDEX;
    public static final VibUri RINGTONE_WITH_INDEX;
    public static final VibUri TABLE_PATH;
    public static final Map typeMap;
    private final PathIndex pathIndex;
    private final int uriType;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static abstract class PathIndex {
        public static final /* synthetic */ PathIndex[] $VALUES;
        public static final AnonymousClass1 EXIST;
        public static final AnonymousClass2 NOT_EXIST;

        /* JADX WARN: Type inference failed for: r0v0, types: [com.samsung.android.settings.asbase.vibration.VibUri$PathIndex, com.samsung.android.settings.asbase.vibration.VibUri$PathIndex$1] */
        /* JADX WARN: Type inference failed for: r1v0, types: [com.samsung.android.settings.asbase.vibration.VibUri$PathIndex, com.samsung.android.settings.asbase.vibration.VibUri$PathIndex$2] */
        static {
            ?? r0 = new PathIndex() { // from class: com.samsung.android.settings.asbase.vibration.VibUri.PathIndex.1
                @Override // com.samsung.android.settings.asbase.vibration.VibUri.PathIndex
                public final String getSelection(String str) {
                    return "vibration_pattern=?";
                }

                @Override // com.samsung.android.settings.asbase.vibration.VibUri.PathIndex
                public final String[] getSelectionArgs(Uri uri, String[] strArr) {
                    String lastPathSegment = uri.getLastPathSegment();
                    if (lastPathSegment.length() < 5) {
                        lastPathSegment = VibPickerConstants.getSepIndexById(lastPathSegment);
                    }
                    return new String[]{lastPathSegment};
                }
            };
            EXIST = r0;
            ?? r1 = new PathIndex() { // from class: com.samsung.android.settings.asbase.vibration.VibUri.PathIndex.2
                @Override // com.samsung.android.settings.asbase.vibration.VibUri.PathIndex
                public final String getSelection(String str) {
                    return str;
                }

                @Override // com.samsung.android.settings.asbase.vibration.VibUri.PathIndex
                public final String[] getSelectionArgs(Uri uri, String[] strArr) {
                    return strArr;
                }
            };
            NOT_EXIST = r1;
            $VALUES = new PathIndex[]{r0, r1};
        }

        public static PathIndex valueOf(String str) {
            return (PathIndex) Enum.valueOf(PathIndex.class, str);
        }

        public static PathIndex[] values() {
            return (PathIndex[]) $VALUES.clone();
        }

        public abstract String getSelection(String str);

        public abstract String[] getSelectionArgs(Uri uri, String[] strArr);
    }

    static {
        VibUri vibUri = new VibUri("TABLE_PATH", 0, -1, PathIndex.NOT_EXIST);
        TABLE_PATH = vibUri;
        PathIndex.AnonymousClass1 anonymousClass1 = PathIndex.EXIST;
        VibUri vibUri2 = new VibUri("RINGTONE_WITH_INDEX", 1, 1, anonymousClass1);
        RINGTONE_WITH_INDEX = vibUri2;
        VibUri vibUri3 = new VibUri("RINGTONE_PATH_WITH_INDEX", 2, 2, anonymousClass1);
        RINGTONE_PATH_WITH_INDEX = vibUri3;
        VibUri vibUri4 = new VibUri("NOTIFICATION_PATH_WITH_INDEX", 3, 3, anonymousClass1);
        NOTIFICATION_PATH_WITH_INDEX = vibUri4;
        $VALUES = new VibUri[]{vibUri, vibUri2, vibUri3, vibUri4};
        typeMap = new HashMap<Integer, VibUri>() { // from class: com.samsung.android.settings.asbase.vibration.VibUri.1
            {
                put(-1, VibUri.TABLE_PATH);
                put(1, VibUri.RINGTONE_WITH_INDEX);
                put(2, VibUri.RINGTONE_PATH_WITH_INDEX);
                put(3, VibUri.NOTIFICATION_PATH_WITH_INDEX);
            }
        };
    }

    public VibUri(String str, int i, int i2, PathIndex pathIndex) {
        this.uriType = i2;
        this.pathIndex = pathIndex;
    }

    public static VibUri valueOf(String str) {
        return (VibUri) Enum.valueOf(VibUri.class, str);
    }

    public static VibUri[] values() {
        return (VibUri[]) $VALUES.clone();
    }

    public final String getSelection(String str) {
        return this.pathIndex.getSelection(str);
    }

    public final String[] getSelectionArgs(Uri uri, String[] strArr) {
        return this.pathIndex.getSelectionArgs(uri, strArr);
    }

    public final int getType() {
        return this.uriType;
    }
}
