package com.google.zxing.common;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ComposerKt$$ExternalSyntheticOutline0;
import androidx.preference.Preference;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class MinimalECIInput implements ECIInput {
    public final int[] bytes;
    public final int fnc1;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class InputEdge {
        public final char c;
        public final int cachedTotalSize;
        public final int encoderIndex;
        public final InputEdge previous;

        public InputEdge(char c, ECIEncoderSet eCIEncoderSet, int i, InputEdge inputEdge, int i2) {
            int length;
            char c2 = c == i2 ? (char) 1000 : c;
            this.c = c2;
            this.encoderIndex = i;
            this.previous = inputEdge;
            if (c2 == 1000) {
                length = 1;
            } else {
                length =
                        (ApnSettings.MVNO_NONE + c)
                                .getBytes(eCIEncoderSet.encoders[i].charset())
                                .length;
            }
            length = (inputEdge == null ? 0 : inputEdge.encoderIndex) != i ? length + 3 : length;
            this.cachedTotalSize = inputEdge != null ? length + inputEdge.cachedTotalSize : length;
        }
    }

    public MinimalECIInput(String str, Charset charset, int i) {
        int i2;
        int i3;
        this.fnc1 = i;
        ECIEncoderSet eCIEncoderSet = new ECIEncoderSet(str, charset, i);
        int i4 = 0;
        if (eCIEncoderSet.encoders.length == 1) {
            this.bytes = new int[str.length()];
            while (i4 < this.bytes.length) {
                char charAt = str.charAt(i4);
                int[] iArr = this.bytes;
                if (charAt == i) {
                    charAt = 1000;
                }
                iArr[i4] = charAt;
                i4++;
            }
            return;
        }
        int length = str.length();
        InputEdge[][] inputEdgeArr =
                (InputEdge[][])
                        Array.newInstance(
                                (Class<?>) InputEdge.class,
                                length + 1,
                                eCIEncoderSet.encoders.length);
        addEdges(str, eCIEncoderSet, inputEdgeArr, 0, null, i);
        for (int i5 = 1; i5 <= length; i5++) {
            int i6 = 0;
            while (i6 < eCIEncoderSet.encoders.length) {
                InputEdge inputEdge = inputEdgeArr[i5][i6];
                if (inputEdge == null || i5 >= length) {
                    i3 = i6;
                } else {
                    i3 = i6;
                    addEdges(str, eCIEncoderSet, inputEdgeArr, i5, inputEdge, i);
                }
                i6 = i3 + 1;
            }
            for (int i7 = 0; i7 < eCIEncoderSet.encoders.length; i7++) {
                inputEdgeArr[i5 - 1][i7] = null;
            }
        }
        int i8 = -1;
        int i9 = Preference.DEFAULT_ORDER;
        for (int i10 = 0; i10 < eCIEncoderSet.encoders.length; i10++) {
            InputEdge inputEdge2 = inputEdgeArr[length][i10];
            if (inputEdge2 != null && (i2 = inputEdge2.cachedTotalSize) < i9) {
                i8 = i10;
                i9 = i2;
            }
        }
        if (i8 < 0) {
            throw new IllegalStateException(
                    ComposerKt$$ExternalSyntheticOutline0.m("Failed to encode \"", str, "\""));
        }
        ArrayList arrayList = new ArrayList();
        InputEdge inputEdge3 = inputEdgeArr[length][i8];
        while (inputEdge3 != null) {
            int i11 = inputEdge3.encoderIndex;
            char c = inputEdge3.c;
            if (c == 1000) {
                arrayList.add(0, 1000);
            } else {
                byte[] bytes =
                        (ApnSettings.MVNO_NONE + c).getBytes(eCIEncoderSet.encoders[i11].charset());
                for (int length2 = bytes.length - 1; length2 >= 0; length2--) {
                    arrayList.add(0, Integer.valueOf(bytes[length2] & 255));
                }
            }
            inputEdge3 = inputEdge3.previous;
            if ((inputEdge3 == null ? 0 : inputEdge3.encoderIndex) != i11) {
                arrayList.add(
                        0,
                        Integer.valueOf(
                                CharacterSetECI.getCharacterSetECI(
                                                        eCIEncoderSet.encoders[i11].charset())
                                                .getValue()
                                        + 256));
            }
        }
        int size = arrayList.size();
        int[] iArr2 = new int[size];
        while (i4 < size) {
            iArr2[i4] = ((Integer) arrayList.get(i4)).intValue();
            i4++;
        }
        this.bytes = iArr2;
    }

    public static void addEdges(
            String str,
            ECIEncoderSet eCIEncoderSet,
            InputEdge[][] inputEdgeArr,
            int i,
            InputEdge inputEdge,
            int i2) {
        char charAt = str.charAt(i);
        int length = eCIEncoderSet.encoders.length;
        int i3 = eCIEncoderSet.priorityEncoderIndex;
        if (i3 < 0 || !(charAt == i2 || eCIEncoderSet.canEncode(charAt, i3))) {
            i3 = 0;
        } else {
            length = i3 + 1;
        }
        int i4 = length;
        for (int i5 = i3; i5 < i4; i5++) {
            if (charAt == i2 || eCIEncoderSet.canEncode(charAt, i5)) {
                InputEdge inputEdge2 = new InputEdge(charAt, eCIEncoderSet, i5, inputEdge, i2);
                InputEdge[] inputEdgeArr2 = inputEdgeArr[i + 1];
                InputEdge inputEdge3 = inputEdgeArr2[i5];
                if (inputEdge3 != null) {
                    if (inputEdge3.cachedTotalSize <= inputEdge2.cachedTotalSize) {}
                }
                inputEdgeArr2[i5] = inputEdge2;
            }
        }
    }

    @Override // com.google.zxing.common.ECIInput
    public final char charAt(int i) {
        if (i >= 0) {
            int[] iArr = this.bytes;
            if (i < iArr.length) {
                if (isECI(i)) {
                    throw new IllegalArgumentException(
                            LazyListMeasuredItem$$ExternalSyntheticOutline0.m(
                                    i, "value at ", " is not a character but an ECI"));
                }
                return (char) (isFNC1(i) ? this.fnc1 : iArr[i]);
            }
        }
        throw new IndexOutOfBoundsException(
                SeslRoundedCorner$$ExternalSyntheticOutline0.m(i, ApnSettings.MVNO_NONE));
    }

    @Override // com.google.zxing.common.ECIInput
    public final int getECIValue(int i) {
        if (i >= 0) {
            if (i < this.bytes.length) {
                if (isECI(i)) {
                    return r0[i] - 256;
                }
                throw new IllegalArgumentException(
                        LazyListMeasuredItem$$ExternalSyntheticOutline0.m(
                                i, "value at ", " is not an ECI but a character"));
            }
        }
        throw new IndexOutOfBoundsException(
                SeslRoundedCorner$$ExternalSyntheticOutline0.m(i, ApnSettings.MVNO_NONE));
    }

    public final boolean haveNCharacters(int i, int i2) {
        if ((i + i2) - 1 >= this.bytes.length) {
            return false;
        }
        for (int i3 = 0; i3 < i2; i3++) {
            if (isECI(i + i3)) {
                return false;
            }
        }
        return true;
    }

    @Override // com.google.zxing.common.ECIInput
    public final boolean isECI(int i) {
        if (i >= 0) {
            int[] iArr = this.bytes;
            if (i < iArr.length) {
                int i2 = iArr[i];
                return i2 > 255 && i2 <= 999;
            }
        }
        throw new IndexOutOfBoundsException(
                SeslRoundedCorner$$ExternalSyntheticOutline0.m(i, ApnSettings.MVNO_NONE));
    }

    public final boolean isFNC1(int i) {
        if (i >= 0) {
            int[] iArr = this.bytes;
            if (i < iArr.length) {
                return iArr[i] == 1000;
            }
        }
        throw new IndexOutOfBoundsException(
                SeslRoundedCorner$$ExternalSyntheticOutline0.m(i, ApnSettings.MVNO_NONE));
    }

    @Override // com.google.zxing.common.ECIInput
    public final int length() {
        return this.bytes.length;
    }

    @Override // com.google.zxing.common.ECIInput
    public final CharSequence subSequence(int i, int i2) {
        if (i < 0 || i > i2 || i2 > this.bytes.length) {
            throw new IndexOutOfBoundsException(
                    SeslRoundedCorner$$ExternalSyntheticOutline0.m(i, ApnSettings.MVNO_NONE));
        }
        StringBuilder sb = new StringBuilder();
        while (i < i2) {
            if (isECI(i)) {
                throw new IllegalArgumentException(
                        LazyListMeasuredItem$$ExternalSyntheticOutline0.m(
                                i, "value at ", " is not a character but an ECI"));
            }
            sb.append(charAt(i));
            i++;
        }
        return sb;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.bytes.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            if (isECI(i)) {
                sb.append("ECI(");
                sb.append(getECIValue(i));
                sb.append(')');
            } else if (charAt(i) < 128) {
                sb.append('\'');
                sb.append(charAt(i));
                sb.append('\'');
            } else {
                sb.append((int) charAt(i));
            }
        }
        return sb.toString();
    }
}
