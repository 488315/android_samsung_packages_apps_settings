package com.google.zxing.datamatrix.encoder;

import com.google.zxing.common.MinimalECIInput;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.sec.ims.settings.ImsSettings;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class MinimalEncoder {
    public static final char[] C40_SHIFT2_CHARS = {'!', '\"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', ':', ';', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_'};

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Edge {
        public final int cachedTotalSize;
        public final int characterLength;
        public final int fromPosition;
        public final Input input;
        public final Mode mode;
        public final Edge previous;
        public static final int[] allCodewordCapacities = {3, 5, 8, 10, 12, 16, 18, 22, 30, 32, 36, 44, 49, 62, 86, 114, 144, 174, 204, IKnoxCustomManager.Stub.TRANSACTION_getForceSingleView, 368, 456, 576, 696, 816, 1050, 1304, 1558};
        public static final int[] squareCodewordCapacities = {3, 5, 8, 12, 18, 22, 30, 36, 44, 62, 86, 114, 144, 174, 204, IKnoxCustomManager.Stub.TRANSACTION_getForceSingleView, 368, 456, 576, 696, 816, 1050, 1304, 1558};
        public static final int[] rectangularCodewordCapacities = {5, 10, 16, 33, 32, 49};

        /* JADX WARN: Code restructure failed: missing block: B:25:0x0062, code lost:
        
            if (r15 != r3) goto L77;
         */
        /* JADX WARN: Code restructure failed: missing block: B:58:0x009a, code lost:
        
            if (r15 == r3) goto L40;
         */
        /* JADX WARN: Code restructure failed: missing block: B:70:0x00ba, code lost:
        
            if (r15 != r3) goto L77;
         */
        /* JADX WARN: Removed duplicated region for block: B:23:0x005e  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public Edge(com.google.zxing.datamatrix.encoder.MinimalEncoder.Input r11, com.google.zxing.datamatrix.encoder.MinimalEncoder.Mode r12, int r13, int r14, com.google.zxing.datamatrix.encoder.MinimalEncoder.Edge r15) {
            /*
                Method dump skipped, instructions count: 192
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.datamatrix.encoder.MinimalEncoder.Edge.<init>(com.google.zxing.datamatrix.encoder.MinimalEncoder$Input, com.google.zxing.datamatrix.encoder.MinimalEncoder$Mode, int, int, com.google.zxing.datamatrix.encoder.MinimalEncoder$Edge):void");
        }

        public static int getC40Value(boolean z, int i, char c, int i2) {
            if (c == i2) {
                return 27;
            }
            if (z) {
                if (c <= 31) {
                    return c;
                }
                if (c == ' ') {
                    return 3;
                }
                return c <= '/' ? c - '!' : c <= '9' ? c - ',' : c <= '@' ? c - '+' : c <= 'Z' ? c - '3' : c <= '_' ? c - 'E' : c <= 127 ? c - '`' : c;
            }
            if (c != 0) {
                if (i == 0 && c <= 3) {
                    return c - 1;
                }
                if (i == 1 && c <= 31) {
                    return c;
                }
                if (c == ' ') {
                    return 3;
                }
                if (c >= '!' && c <= '/') {
                    return c - '!';
                }
                if (c >= '0' && c <= '9') {
                    return c - ',';
                }
                if (c >= ':' && c <= '@') {
                    return c - '+';
                }
                if (c >= 'A' && c <= 'Z') {
                    return c - '@';
                }
                if (c >= '[' && c <= '_') {
                    return c - 'E';
                }
                if (c != '`') {
                    return (c < 'a' || c > 'z') ? (c < '{' || c > 127) ? c : c - '`' : c - 'S';
                }
            }
            return 0;
        }

        /* JADX WARN: Code restructure failed: missing block: B:19:0x001e, code lost:
        
            if (r5 == r7) goto L26;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static int getShiftValue(char r5, boolean r6, int r7) {
            /*
                r0 = 31
                r1 = 0
                if (r6 == 0) goto L8
                if (r5 > r0) goto L8
                goto L32
            L8:
                if (r6 != 0) goto Ld
                if (r5 > r0) goto Ld
                goto L32
            Ld:
                r0 = 27
                char[] r2 = com.google.zxing.datamatrix.encoder.MinimalEncoder.C40_SHIFT2_CHARS
                if (r6 == 0) goto L21
                r3 = r1
            L14:
                if (r3 >= r0) goto L1e
                char r4 = r2[r3]
                if (r4 != r5) goto L1b
                goto L2f
            L1b:
                int r3 = r3 + 1
                goto L14
            L1e:
                if (r5 != r7) goto L21
                goto L2f
            L21:
                if (r6 != 0) goto L31
            L23:
                if (r1 >= r0) goto L2d
                char r6 = r2[r1]
                if (r6 != r5) goto L2a
                goto L2f
            L2a:
                int r1 = r1 + 1
                goto L23
            L2d:
                if (r5 != r7) goto L31
            L2f:
                r1 = 1
                goto L32
            L31:
                r1 = 2
            L32:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.datamatrix.encoder.MinimalEncoder.Edge.getShiftValue(char, boolean, int):int");
        }

        public static int getX12Value(char c) {
            if (c == '\r') {
                return 0;
            }
            if (c == '*') {
                return 1;
            }
            if (c == '>') {
                return 2;
            }
            if (c == ' ') {
                return 3;
            }
            return (c < '0' || c > '9') ? (c < 'A' || c > 'Z') ? c : c - '3' : c - ',';
        }

        public static void setC40Word(byte[] bArr, int i, int i2, int i3, int i4) {
            int i5 = ((i3 & 255) * 40) + ((i2 & 255) * 1600) + (i4 & 255) + 1;
            bArr[i] = (byte) (i5 / 256);
            bArr[i + 1] = (byte) (i5 % 256);
        }

        public final byte[] getC40Words(int i, boolean z) {
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < this.characterLength; i2++) {
                char charAt = this.input.charAt(this.fromPosition + i2);
                if ((z && HighLevelEncoder.isNativeC40(charAt)) || (!z && HighLevelEncoder.isNativeText(charAt))) {
                    arrayList.add(Byte.valueOf((byte) getC40Value(z, 0, charAt, i)));
                } else if (MinimalEncoder.isExtendedASCII(charAt, i)) {
                    char c = (char) ((charAt & 255) - 128);
                    if (!(z && HighLevelEncoder.isNativeC40(c)) && (z || !HighLevelEncoder.isNativeText(c))) {
                        arrayList.add((byte) 1);
                        arrayList.add((byte) 30);
                        int shiftValue = getShiftValue(c, z, i);
                        arrayList.add(Byte.valueOf((byte) shiftValue));
                        arrayList.add(Byte.valueOf((byte) getC40Value(z, shiftValue, c, i)));
                    } else {
                        arrayList.add((byte) 1);
                        arrayList.add((byte) 30);
                        arrayList.add(Byte.valueOf((byte) getC40Value(z, 0, c, i)));
                    }
                } else {
                    int shiftValue2 = getShiftValue(charAt, z, i);
                    arrayList.add(Byte.valueOf((byte) shiftValue2));
                    arrayList.add(Byte.valueOf((byte) getC40Value(z, shiftValue2, charAt, i)));
                }
            }
            if (arrayList.size() % 3 != 0) {
                arrayList.add((byte) 0);
            }
            byte[] bArr = new byte[(arrayList.size() / 3) * 2];
            int i3 = 0;
            for (int i4 = 0; i4 < arrayList.size(); i4 += 3) {
                setC40Word(bArr, i3, ((Byte) arrayList.get(i4)).byteValue() & 255, ((Byte) arrayList.get(i4 + 1)).byteValue() & 255, ((Byte) arrayList.get(i4 + 2)).byteValue() & 255);
                i3 += 2;
            }
            return bArr;
        }

        public final Mode getEndMode() {
            Mode mode = Mode.EDF;
            int i = this.characterLength;
            int i2 = this.cachedTotalSize;
            Mode mode2 = Mode.ASCII;
            Mode mode3 = this.mode;
            if (mode3 == mode) {
                if (i < 4) {
                    return mode2;
                }
                int lastASCII = getLastASCII();
                if (lastASCII > 0) {
                    int i3 = i2 + lastASCII;
                    if (getMinSymbolSize(i3) - i3 <= 2 - lastASCII) {
                        return mode2;
                    }
                }
            }
            if (mode3 == Mode.C40 || mode3 == Mode.TEXT || mode3 == Mode.X12) {
                if (this.fromPosition + i >= this.input.bytes.length && getMinSymbolSize(i2) - i2 == 0) {
                    return mode2;
                }
                if (getLastASCII() == 1) {
                    int i4 = i2 + 1;
                    if (getMinSymbolSize(i4) - i4 == 0) {
                        return mode2;
                    }
                }
            }
            return mode3;
        }

        public final int getLastASCII() {
            Input input = this.input;
            int length = input.bytes.length;
            int i = this.fromPosition + this.characterLength;
            int i2 = length - i;
            if (i2 <= 4 && i < length) {
                if (i2 == 1) {
                    return MinimalEncoder.isExtendedASCII(input.charAt(i), input.fnc1) ? 0 : 1;
                }
                if (i2 == 2) {
                    if (!MinimalEncoder.isExtendedASCII(input.charAt(i), input.fnc1)) {
                        int i3 = i + 1;
                        if (!MinimalEncoder.isExtendedASCII(input.charAt(i3), input.fnc1)) {
                            return (HighLevelEncoder.isDigit(input.charAt(i)) && HighLevelEncoder.isDigit(input.charAt(i3))) ? 1 : 2;
                        }
                    }
                    return 0;
                }
                if (i2 == 3) {
                    if (HighLevelEncoder.isDigit(input.charAt(i)) && HighLevelEncoder.isDigit(input.charAt(i + 1)) && !MinimalEncoder.isExtendedASCII(input.charAt(i + 2), input.fnc1)) {
                        return 2;
                    }
                    return (HighLevelEncoder.isDigit(input.charAt(i + 1)) && HighLevelEncoder.isDigit(input.charAt(i + 2)) && !MinimalEncoder.isExtendedASCII(input.charAt(i), input.fnc1)) ? 2 : 0;
                }
                if (HighLevelEncoder.isDigit(input.charAt(i)) && HighLevelEncoder.isDigit(input.charAt(i + 1)) && HighLevelEncoder.isDigit(input.charAt(i + 2)) && HighLevelEncoder.isDigit(input.charAt(i + 3))) {
                    return 2;
                }
            }
            return 0;
        }

        public final int getMinSymbolSize(int i) {
            int ordinal = this.input.shape.ordinal();
            if (ordinal == 1) {
                int[] iArr = squareCodewordCapacities;
                for (int i2 = 0; i2 < 24; i2++) {
                    int i3 = iArr[i2];
                    if (i3 >= i) {
                        return i3;
                    }
                }
            } else if (ordinal == 2) {
                int[] iArr2 = rectangularCodewordCapacities;
                for (int i4 = 0; i4 < 6; i4++) {
                    int i5 = iArr2[i4];
                    if (i5 >= i) {
                        return i5;
                    }
                }
            }
            int[] iArr3 = allCodewordCapacities;
            for (int i6 = 0; i6 < 28; i6++) {
                int i7 = iArr3[i6];
                if (i7 >= i) {
                    return i7;
                }
            }
            return iArr3[27];
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Input extends MinimalECIInput {
        public final int macroId;
        public final SymbolShapeHint shape;

        public Input(String str, Charset charset, int i, SymbolShapeHint symbolShapeHint, int i2) {
            super(str, charset, i);
            this.shape = symbolShapeHint;
            this.macroId = i2;
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    final class Mode {
        public static final /* synthetic */ Mode[] $VALUES;
        public static final Mode ASCII;
        public static final Mode B256;
        public static final Mode C40;
        public static final Mode EDF;
        public static final Mode TEXT;
        public static final Mode X12;

        static {
            Mode mode = new Mode("ASCII", 0);
            ASCII = mode;
            Mode mode2 = new Mode("C40", 1);
            C40 = mode2;
            Mode mode3 = new Mode(ImsSettings.TYPE_TEXT, 2);
            TEXT = mode3;
            Mode mode4 = new Mode("X12", 3);
            X12 = mode4;
            Mode mode5 = new Mode("EDF", 4);
            EDF = mode5;
            Mode mode6 = new Mode("B256", 5);
            B256 = mode6;
            $VALUES = new Mode[]{mode, mode2, mode3, mode4, mode5, mode6};
        }

        public static Mode valueOf(String str) {
            return (Mode) Enum.valueOf(Mode.class, str);
        }

        public static Mode[] values() {
            return (Mode[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Result {
        public final byte[] bytes;

        public Result(Edge edge) {
            int i;
            int i2;
            Input input;
            Mode mode;
            byte[] bArr;
            byte[] bArr2;
            int i3;
            byte[] bArr3;
            int i4;
            int i5;
            int i6 = 2;
            int i7 = 1;
            Input input2 = edge.input;
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            Mode mode2 = Mode.C40;
            Mode mode3 = Mode.ASCII;
            Mode mode4 = edge.mode;
            int prepend = ((mode4 == mode2 || mode4 == Mode.TEXT || mode4 == Mode.X12) && edge.getEndMode() != mode3) ? prepend(new byte[]{(byte) 254}, arrayList) : 0;
            Edge edge2 = edge;
            while (edge2 != null) {
                Mode mode5 = edge2.mode;
                int ordinal = mode5.ordinal();
                int i8 = edge2.characterLength;
                int i9 = edge2.fromPosition;
                Input input3 = edge2.input;
                if (ordinal == 0) {
                    int i10 = i7;
                    input = input2;
                    mode = mode3;
                    if (input3.isECI(i9)) {
                        int eCIValue = input3.getECIValue(i9) + i10;
                        bArr2 = new byte[2];
                        bArr2[0] = (byte) IKnoxCustomManager.Stub.TRANSACTION_setZeroPageState;
                        bArr2[i10] = (byte) eCIValue;
                    } else if (MinimalEncoder.isExtendedASCII(input3.charAt(i9), input3.fnc1)) {
                        bArr2 = new byte[]{(byte) IKnoxCustomManager.Stub.TRANSACTION_setAppsButtonState, (byte) (input3.charAt(i9) - 127)};
                    } else {
                        bArr = i8 == 2 ? new byte[]{(byte) (input3.charAt(i9 + 1) + ((input3.charAt(i9) - '0') * 10) + 82)} : input3.isFNC1(i9) ? new byte[]{(byte) IKnoxCustomManager.Stub.TRANSACTION_addWidget} : new byte[]{(byte) (input3.charAt(i9) + 1)};
                    }
                    bArr = bArr2;
                } else if (ordinal == i7) {
                    input = input2;
                    mode = mode3;
                    bArr = edge2.getC40Words(input3.fnc1, true);
                } else if (ordinal == i6) {
                    input = input2;
                    mode = mode3;
                    bArr = edge2.getC40Words(input3.fnc1, false);
                } else if (ordinal == 3) {
                    input = input2;
                    mode = mode3;
                    int i11 = (i8 / 3) * 2;
                    byte[] bArr4 = new byte[i11];
                    int i12 = 0;
                    while (i12 < i11) {
                        int i13 = ((i12 / 2) * 3) + i9;
                        Edge.setC40Word(bArr4, i12, Edge.getX12Value(input3.charAt(i13)), Edge.getX12Value(input3.charAt(i13 + 1)), Edge.getX12Value(input3.charAt(i13 + 2)));
                        i12 += 2;
                        i11 = i11;
                    }
                    bArr = bArr4;
                } else if (ordinal != 4) {
                    if (ordinal != 5) {
                        bArr = new byte[0];
                    } else {
                        byte charAt = (byte) input3.charAt(i9);
                        byte[] bArr5 = new byte[i7];
                        bArr5[0] = charAt;
                        bArr = bArr5;
                    }
                    input = input2;
                    mode = mode3;
                } else {
                    input = input2;
                    int ceil = (int) Math.ceil(i8 / 4.0d);
                    bArr2 = new byte[ceil * 3];
                    char c = 1;
                    int min = Math.min((i8 + i9) - 1, input3.bytes.length - 1);
                    int i14 = 0;
                    while (i14 < ceil) {
                        int i15 = i14;
                        int i16 = i9;
                        int[] iArr = new int[4];
                        int i17 = ceil;
                        Mode mode6 = mode3;
                        int i18 = i16;
                        int i19 = 0;
                        for (int i20 = 4; i19 < i20; i20 = 4) {
                            if (i18 <= min) {
                                iArr[i19] = input3.charAt(i18) & '?';
                                i18++;
                            } else {
                                iArr[i19] = i18 == min + 1 ? 31 : 0;
                            }
                            i19++;
                        }
                        int i21 = iArr[3] | (iArr[0] << 18) | (iArr[c] << 12) | (iArr[2] << 6);
                        bArr2[i15] = (byte) ((i21 >> 16) & 255);
                        bArr2[i15 + 1] = (byte) ((i21 >> 8) & 255);
                        bArr2[i15 + 2] = (byte) (i21 & 255);
                        i14 = i15 + 3;
                        i9 = i18;
                        ceil = i17;
                        mode3 = mode6;
                        c = 1;
                    }
                    mode = mode3;
                    bArr = bArr2;
                }
                int prepend2 = prepend(bArr, arrayList) + prepend;
                edge2 = edge2.previous;
                if (edge2 == null || edge2.mode != mode5) {
                    if (mode5 == Mode.B256) {
                        if (prepend2 <= 249) {
                            arrayList.add(0, Byte.valueOf((byte) prepend2));
                            i5 = prepend2 + 1;
                        } else {
                            arrayList.add(0, Byte.valueOf((byte) (prepend2 % IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend)));
                            arrayList.add(0, Byte.valueOf((byte) ((prepend2 / IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend) + IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcut)));
                            i5 = prepend2 + 2;
                        }
                        arrayList2.add(Integer.valueOf(arrayList.size()));
                        arrayList3.add(Integer.valueOf(i5));
                    }
                    int ordinal2 = (edge2 == null ? mode : edge2.getEndMode()).ordinal();
                    if (ordinal2 != 0) {
                        if (ordinal2 != 1 && ordinal2 != 2 && ordinal2 != 3) {
                            i4 = (ordinal2 == 4 || ordinal2 != 5) ? 0 : 0;
                        } else if (mode5 != (edge2 == null ? mode : edge2.getEndMode())) {
                            int ordinal3 = mode5.ordinal();
                            if (ordinal3 == 0) {
                                bArr3 = new byte[]{(byte) 254};
                            } else if (ordinal3 != 1) {
                                i3 = 2;
                                if (ordinal3 == 2) {
                                    bArr3 = new byte[]{(byte) 254, (byte) IKnoxCustomManager.Stub.TRANSACTION_getFavoriteAppsMaxCount};
                                } else if (ordinal3 == 3) {
                                    bArr3 = new byte[]{(byte) 254, (byte) IKnoxCustomManager.Stub.TRANSACTION_removeFavoriteApp};
                                } else if (ordinal3 == 4) {
                                    bArr3 = new byte[]{(byte) 254, (byte) IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp};
                                } else if (ordinal3 != 5) {
                                    i4 = 0;
                                    bArr3 = new byte[i4];
                                    prepend(bArr3, arrayList);
                                    prepend = 0;
                                } else {
                                    bArr3 = new byte[]{(byte) 254, (byte) IKnoxCustomManager.Stub.TRANSACTION_removeShortcut};
                                }
                                prepend(bArr3, arrayList);
                                prepend = 0;
                            } else {
                                bArr3 = new byte[]{(byte) 254, (byte) 230};
                            }
                            i3 = 2;
                            prepend(bArr3, arrayList);
                            prepend = 0;
                        }
                        i3 = 2;
                        bArr3 = new byte[i4];
                        prepend(bArr3, arrayList);
                        prepend = 0;
                    }
                    int ordinal4 = mode5.ordinal();
                    if (ordinal4 != 1) {
                        i3 = 2;
                        if (ordinal4 == 2) {
                            bArr3 = new byte[]{(byte) IKnoxCustomManager.Stub.TRANSACTION_getFavoriteAppsMaxCount};
                        } else if (ordinal4 == 3) {
                            bArr3 = new byte[]{(byte) IKnoxCustomManager.Stub.TRANSACTION_removeFavoriteApp};
                        } else if (ordinal4 == 4) {
                            bArr3 = new byte[]{(byte) IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp};
                        } else if (ordinal4 != 5) {
                            i4 = 0;
                            bArr3 = new byte[i4];
                        } else {
                            bArr3 = new byte[]{(byte) IKnoxCustomManager.Stub.TRANSACTION_removeShortcut};
                        }
                    } else {
                        i3 = 2;
                        bArr3 = new byte[]{(byte) 230};
                    }
                    prepend(bArr3, arrayList);
                    prepend = 0;
                } else {
                    prepend = prepend2;
                    i3 = 2;
                }
                input2 = input;
                i6 = i3;
                mode3 = mode;
                i7 = 1;
            }
            Input input4 = input2;
            int i22 = input4.macroId;
            if (i22 == 5) {
                i = 1;
                i2 = 0;
                prepend(new byte[]{(byte) IKnoxCustomManager.Stub.TRANSACTION_getAppsButtonState}, arrayList);
            } else {
                i = 1;
                i2 = 0;
                if (i22 == 6) {
                    prepend(new byte[]{(byte) IKnoxCustomManager.Stub.TRANSACTION_setFavoriteApp}, arrayList);
                }
            }
            if (input4.fnc1 > 0) {
                byte[] bArr6 = new byte[i];
                bArr6[i2] = (byte) IKnoxCustomManager.Stub.TRANSACTION_addWidget;
                prepend(bArr6, arrayList);
            }
            for (int i23 = i2; i23 < arrayList2.size(); i23++) {
                int size = arrayList.size() - ((Integer) arrayList2.get(i23)).intValue();
                int intValue = ((Integer) arrayList3.get(i23)).intValue();
                for (int i24 = i2; i24 < intValue; i24++) {
                    int i25 = size + i24;
                    int byteValue = (((i25 + 1) * 149) % 255) + 1 + (((Byte) arrayList.get(i25)).byteValue() & 255);
                    if (byteValue > 255) {
                        byteValue -= 256;
                    }
                    arrayList.set(i25, Byte.valueOf((byte) byteValue));
                }
            }
            int minSymbolSize = edge.getMinSymbolSize(arrayList.size());
            if (arrayList.size() < minSymbolSize) {
                arrayList.add((byte) -127);
            }
            while (arrayList.size() < minSymbolSize) {
                int size2 = ((arrayList.size() + 1) * 149) % IKnoxCustomManager.Stub.TRANSACTION_getDexForegroundModePackageList;
                int i26 = size2 + 130;
                if (i26 > 254) {
                    i26 = size2 - 124;
                }
                arrayList.add(Byte.valueOf((byte) i26));
            }
            this.bytes = new byte[arrayList.size()];
            int i27 = i2;
            while (true) {
                byte[] bArr7 = this.bytes;
                if (i27 >= bArr7.length) {
                    return;
                }
                bArr7[i27] = ((Byte) arrayList.get(i27)).byteValue();
                i27++;
            }
        }

        public static int prepend(byte[] bArr, List list) {
            for (int length = bArr.length - 1; length >= 0; length--) {
                ((ArrayList) list).add(0, Byte.valueOf(bArr[length]));
            }
            return bArr.length;
        }
    }

    public static void addEdge(Edge[][] edgeArr, Edge edge) {
        int i = edge.fromPosition + edge.characterLength;
        if (edgeArr[i][edge.getEndMode().ordinal()] == null || edgeArr[i][edge.getEndMode().ordinal()].cachedTotalSize > edge.cachedTotalSize) {
            edgeArr[i][edge.getEndMode().ordinal()] = edge;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v0 */
    /* JADX WARN: Type inference failed for: r12v1 */
    /* JADX WARN: Type inference failed for: r12v4 */
    public static void addEdges(Input input, Edge[][] edgeArr, int i, Edge edge) {
        int i2;
        boolean isECI = input.isECI(i);
        Mode mode = Mode.ASCII;
        if (isECI) {
            addEdge(edgeArr, new Edge(input, mode, i, 1, edge));
            return;
        }
        char charAt = input.charAt(i);
        Mode mode2 = Mode.EDF;
        ?? r12 = 0;
        if (edge == null || edge.getEndMode() != mode2) {
            if (HighLevelEncoder.isDigit(charAt) && input.haveNCharacters(i, 2) && HighLevelEncoder.isDigit(input.charAt(i + 1))) {
                addEdge(edgeArr, new Edge(input, mode, i, 2, edge));
            } else {
                addEdge(edgeArr, new Edge(input, mode, i, 1, edge));
            }
            Mode mode3 = Mode.C40;
            Mode[] modeArr = {mode3, Mode.TEXT};
            int i3 = 0;
            while (i3 < 2) {
                Mode mode4 = modeArr[i3];
                int[] iArr = new int[1];
                if (getNumberOfC40Words(input, i, mode4 == mode3 ? true : r12, iArr) > 0) {
                    i2 = i3;
                    addEdge(edgeArr, new Edge(input, mode4, i, iArr[r12], edge));
                } else {
                    i2 = i3;
                }
                i3 = i2 + 1;
                r12 = 0;
            }
            if (input.haveNCharacters(i, 3) && HighLevelEncoder.isNativeX12(input.charAt(i)) && HighLevelEncoder.isNativeX12(input.charAt(i + 1)) && HighLevelEncoder.isNativeX12(input.charAt(i + 2))) {
                addEdge(edgeArr, new Edge(input, Mode.X12, i, 3, edge));
            }
            addEdge(edgeArr, new Edge(input, Mode.B256, i, 1, edge));
        }
        int i4 = 0;
        while (i4 < 3) {
            int i5 = i + i4;
            if (!input.haveNCharacters(i5, 1) || !HighLevelEncoder.isNativeEDIFACT(input.charAt(i5))) {
                break;
            }
            i4++;
            addEdge(edgeArr, new Edge(input, mode2, i, i4, edge));
        }
        if (i4 == 3 && input.haveNCharacters(i, 4) && HighLevelEncoder.isNativeEDIFACT(input.charAt(i + 3))) {
            addEdge(edgeArr, new Edge(input, mode2, i, 4, edge));
        }
    }

    public static int getNumberOfC40Words(Input input, int i, boolean z, int[] iArr) {
        int i2 = i;
        int i3 = 0;
        while (true) {
            int[] iArr2 = input.bytes;
            if (i2 >= iArr2.length) {
                iArr[0] = 0;
                return 0;
            }
            if (input.isECI(i2)) {
                iArr[0] = 0;
                return 0;
            }
            char charAt = input.charAt(i2);
            if ((z && HighLevelEncoder.isNativeC40(charAt)) || (!z && HighLevelEncoder.isNativeText(charAt))) {
                i3++;
            } else if (isExtendedASCII(charAt, input.fnc1)) {
                int i4 = charAt & 255;
                i3 = (i4 < 128 || (!(z && HighLevelEncoder.isNativeC40((char) (i4 + (-128)))) && (z || !HighLevelEncoder.isNativeText((char) (i4 + (-128)))))) ? i3 + 4 : i3 + 3;
            } else {
                i3 += 2;
            }
            if (i3 % 3 == 0 || ((i3 - 2) % 3 == 0 && i2 + 1 == iArr2.length)) {
                break;
            }
            i2++;
        }
        iArr[0] = (i2 - i) + 1;
        return (int) Math.ceil(i3 / 3.0d);
    }

    public static boolean isExtendedASCII(char c, int i) {
        return c != i && c >= 128 && c <= 255;
    }
}
