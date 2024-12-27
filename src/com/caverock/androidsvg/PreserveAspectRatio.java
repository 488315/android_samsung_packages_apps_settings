package com.caverock.androidsvg;

import com.samsung.android.knox.zt.config.securelog.SignalSeverity;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class PreserveAspectRatio {
    public final Alignment alignment;
    public final Scale scale;
    public static final PreserveAspectRatio STRETCH = new PreserveAspectRatio(Alignment.none, null);
    public static final PreserveAspectRatio LETTERBOX =
            new PreserveAspectRatio(Alignment.xMidYMid, Scale.meet);

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Alignment {
        public static final /* synthetic */ Alignment[] $VALUES;
        public static final Alignment none;
        public static final Alignment xMaxYMax;
        public static final Alignment xMaxYMid;
        public static final Alignment xMaxYMin;
        public static final Alignment xMidYMax;
        public static final Alignment xMidYMid;
        public static final Alignment xMidYMin;
        public static final Alignment xMinYMax;
        public static final Alignment xMinYMid;
        public static final Alignment xMinYMin;

        static {
            Alignment alignment = new Alignment(SignalSeverity.NONE, 0);
            none = alignment;
            Alignment alignment2 = new Alignment("xMinYMin", 1);
            xMinYMin = alignment2;
            Alignment alignment3 = new Alignment("xMidYMin", 2);
            xMidYMin = alignment3;
            Alignment alignment4 = new Alignment("xMaxYMin", 3);
            xMaxYMin = alignment4;
            Alignment alignment5 = new Alignment("xMinYMid", 4);
            xMinYMid = alignment5;
            Alignment alignment6 = new Alignment("xMidYMid", 5);
            xMidYMid = alignment6;
            Alignment alignment7 = new Alignment("xMaxYMid", 6);
            xMaxYMid = alignment7;
            Alignment alignment8 = new Alignment("xMinYMax", 7);
            xMinYMax = alignment8;
            Alignment alignment9 = new Alignment("xMidYMax", 8);
            xMidYMax = alignment9;
            Alignment alignment10 = new Alignment("xMaxYMax", 9);
            xMaxYMax = alignment10;
            $VALUES =
                    new Alignment[] {
                        alignment,
                        alignment2,
                        alignment3,
                        alignment4,
                        alignment5,
                        alignment6,
                        alignment7,
                        alignment8,
                        alignment9,
                        alignment10
                    };
        }

        public static Alignment valueOf(String str) {
            return (Alignment) Enum.valueOf(Alignment.class, str);
        }

        public static Alignment[] values() {
            return (Alignment[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Scale {
        public static final /* synthetic */ Scale[] $VALUES;
        public static final Scale meet;
        public static final Scale slice;

        static {
            Scale scale = new Scale("meet", 0);
            meet = scale;
            Scale scale2 = new Scale("slice", 1);
            slice = scale2;
            $VALUES = new Scale[] {scale, scale2};
        }

        public static Scale valueOf(String str) {
            return (Scale) Enum.valueOf(Scale.class, str);
        }

        public static Scale[] values() {
            return (Scale[]) $VALUES.clone();
        }
    }

    public PreserveAspectRatio(Alignment alignment, Scale scale) {
        this.alignment = alignment;
        this.scale = scale;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || PreserveAspectRatio.class != obj.getClass()) {
            return false;
        }
        PreserveAspectRatio preserveAspectRatio = (PreserveAspectRatio) obj;
        return this.alignment == preserveAspectRatio.alignment
                && this.scale == preserveAspectRatio.scale;
    }

    public final String toString() {
        return this.alignment + " " + this.scale;
    }
}
