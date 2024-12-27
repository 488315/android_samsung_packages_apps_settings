package com.google.zxing.datamatrix.encoder;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SymbolShapeHint {
    public static final /* synthetic */ SymbolShapeHint[] $VALUES;
    public static final SymbolShapeHint FORCE_NONE;
    public static final SymbolShapeHint FORCE_RECTANGLE;
    public static final SymbolShapeHint FORCE_SQUARE;

    static {
        SymbolShapeHint symbolShapeHint = new SymbolShapeHint("FORCE_NONE", 0);
        FORCE_NONE = symbolShapeHint;
        SymbolShapeHint symbolShapeHint2 = new SymbolShapeHint("FORCE_SQUARE", 1);
        FORCE_SQUARE = symbolShapeHint2;
        SymbolShapeHint symbolShapeHint3 = new SymbolShapeHint("FORCE_RECTANGLE", 2);
        FORCE_RECTANGLE = symbolShapeHint3;
        $VALUES = new SymbolShapeHint[] {symbolShapeHint, symbolShapeHint2, symbolShapeHint3};
    }

    public static SymbolShapeHint valueOf(String str) {
        return (SymbolShapeHint) Enum.valueOf(SymbolShapeHint.class, str);
    }

    public static SymbolShapeHint[] values() {
        return (SymbolShapeHint[]) $VALUES.clone();
    }
}
