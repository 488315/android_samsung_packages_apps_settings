package com.google.zxing.qrcode.encoder;

import androidx.compose.runtime.ComposerKt$$ExternalSyntheticOutline0;

import com.google.zxing.WriterException;
import com.google.zxing.common.ECIEncoderSet;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Mode;
import com.google.zxing.qrcode.decoder.Version;

import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class MinimalEncoder {
    public final ErrorCorrectionLevel ecLevel;
    public final ECIEncoderSet encoders;
    public final boolean isGS1;
    public final String stringToEncode;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Edge {
        public final int cachedTotalSize;
        public final int characterLength;
        public final int charsetEncoderIndex;
        public final int fromPosition;
        public final Mode mode;
        public final Edge previous;

        public Edge(
                MinimalEncoder minimalEncoder,
                Mode mode,
                int i,
                int i2,
                int i3,
                Edge edge,
                Version version) {
            this.mode = mode;
            this.fromPosition = i;
            Mode mode2 = Mode.BYTE;
            int i4 = (mode == mode2 || edge == null) ? i2 : edge.charsetEncoderIndex;
            this.charsetEncoderIndex = i4;
            this.characterLength = i3;
            this.previous = edge;
            boolean z = false;
            int i5 = edge != null ? edge.cachedTotalSize : 0;
            if ((mode == mode2 && edge == null && i4 != 0)
                    || (edge != null && i4 != edge.charsetEncoderIndex)) {
                z = true;
            }
            i5 =
                    (edge == null || mode != edge.mode || z)
                            ? i5 + mode.getCharacterCountBits(version) + 4
                            : i5;
            int ordinal = mode.ordinal();
            if (ordinal != 1) {
                if (ordinal == 2) {
                    i5 += i3 != 1 ? 11 : 6;
                } else if (ordinal == 4) {
                    i5 +=
                            minimalEncoder
                                            .stringToEncode
                                            .substring(i, i3 + i)
                                            .getBytes(
                                                    minimalEncoder.encoders.encoders[i2].charset())
                                            .length
                                    * 8;
                    if (z) {
                        i5 += 12;
                    }
                } else if (ordinal == 6) {
                    i5 += 13;
                }
            } else {
                i5 += i3 != 1 ? i3 == 2 ? 7 : 10 : 4;
            }
            this.cachedTotalSize = i5;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ResultList {
        public final List list = new ArrayList();
        public final Version version;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class ResultNode {
            public final int characterLength;
            public final int charsetEncoderIndex;
            public final int fromPosition;
            public final Mode mode;

            public ResultNode(Mode mode, int i, int i2, int i3) {
                this.mode = mode;
                this.fromPosition = i;
                this.charsetEncoderIndex = i2;
                this.characterLength = i3;
            }

            public final int getCharacterCountIndicator() {
                Mode mode = Mode.BYTE;
                Mode mode2 = this.mode;
                int i = this.characterLength;
                if (mode2 != mode) {
                    return i;
                }
                MinimalEncoder minimalEncoder = MinimalEncoder.this;
                ECIEncoderSet eCIEncoderSet = minimalEncoder.encoders;
                int i2 = this.fromPosition;
                return minimalEncoder
                        .stringToEncode
                        .substring(i2, i + i2)
                        .getBytes(eCIEncoderSet.encoders[this.charsetEncoderIndex].charset())
                        .length;
            }

            public final String toString() {
                StringBuilder sb = new StringBuilder();
                Mode mode = this.mode;
                sb.append(mode);
                sb.append('(');
                Mode mode2 = Mode.ECI;
                ResultList resultList = ResultList.this;
                if (mode == mode2) {
                    sb.append(
                            MinimalEncoder.this
                                    .encoders
                                    .encoders[this.charsetEncoderIndex]
                                    .charset()
                                    .displayName());
                } else {
                    String str = MinimalEncoder.this.stringToEncode;
                    int i = this.fromPosition;
                    String substring = str.substring(i, this.characterLength + i);
                    StringBuilder sb2 = new StringBuilder();
                    for (int i2 = 0; i2 < substring.length(); i2++) {
                        if (substring.charAt(i2) < ' ' || substring.charAt(i2) > '~') {
                            sb2.append('.');
                        } else {
                            sb2.append(substring.charAt(i2));
                        }
                    }
                    sb.append(sb2.toString());
                }
                sb.append(')');
                return sb.toString();
            }
        }

        public ResultList(Version version, Edge edge) {
            Mode mode;
            int i;
            ErrorCorrectionLevel errorCorrectionLevel;
            int i2;
            int i3;
            Edge edge2 = edge;
            int i4 = 0;
            int i5 = 0;
            while (true) {
                mode = Mode.ECI;
                if (edge2 == null) {
                    break;
                }
                int i6 = i4 + edge2.characterLength;
                Mode mode2 = Mode.BYTE;
                int i7 = edge2.charsetEncoderIndex;
                Edge edge3 = edge2.previous;
                Mode mode3 = edge2.mode;
                boolean z =
                        (mode3 == mode2 && edge3 == null && i7 != 0)
                                || !(edge3 == null || i7 == edge3.charsetEncoderIndex);
                i = z ? 1 : i5;
                if (edge3 == null || edge3.mode != mode3 || z) {
                    i2 = i;
                    ((ArrayList) this.list)
                            .add(0, new ResultNode(mode3, edge2.fromPosition, i7, i6));
                    i3 = 0;
                } else {
                    i2 = i;
                    i3 = i6;
                }
                if (z) {
                    ((ArrayList) this.list)
                            .add(
                                    0,
                                    new ResultNode(
                                            mode,
                                            edge2.fromPosition,
                                            edge2.charsetEncoderIndex,
                                            0));
                }
                i5 = i2;
                i4 = i3;
                edge2 = edge3;
            }
            if (MinimalEncoder.this.isGS1) {
                ResultNode resultNode = (ResultNode) ((ArrayList) this.list).get(0);
                if (resultNode != null && resultNode.mode != mode && i5 != 0) {
                    ((ArrayList) this.list).add(0, new ResultNode(mode, 0, 0, 0));
                }
                ((ArrayList) this.list)
                        .add(
                                ((ResultNode) ((ArrayList) this.list).get(0)).mode == mode ? 1 : 0,
                                new ResultNode(Mode.FNC1_FIRST_POSITION, 0, 0, 0));
            }
            int i8 = version.versionNumber;
            int i9 = 26;
            int ordinal =
                    (i8 <= 9
                                    ? VersionSize.SMALL
                                    : i8 <= 26 ? VersionSize.MEDIUM : VersionSize.LARGE)
                            .ordinal();
            if (ordinal == 0) {
                i9 = 9;
            } else if (ordinal != 1) {
                i = 27;
                i9 = 40;
            } else {
                i = 10;
            }
            int size = getSize(version);
            while (true) {
                errorCorrectionLevel = MinimalEncoder.this.ecLevel;
                if (i8 >= i9
                        || Encoder.willFit(
                                size, Version.getVersionForNumber(i8), errorCorrectionLevel)) {
                    break;
                } else {
                    i8++;
                }
            }
            while (i8 > i
                    && Encoder.willFit(
                            size, Version.getVersionForNumber(i8 - 1), errorCorrectionLevel)) {
                i8--;
            }
            this.version = Version.getVersionForNumber(i8);
        }

        public final int getSize(Version version) {
            int i = 0;
            for (ResultNode resultNode : this.list) {
                Mode mode = resultNode.mode;
                int characterCountBits = mode.getCharacterCountBits(version);
                int i2 = characterCountBits + 4;
                int ordinal = mode.ordinal();
                int i3 = resultNode.characterLength;
                if (ordinal == 1) {
                    int i4 = ((i3 / 3) * 10) + i2;
                    int i5 = i3 % 3;
                    i2 = i4 + (i5 != 1 ? i5 == 2 ? 7 : 0 : 4);
                } else if (ordinal == 2) {
                    i2 = ((i3 / 2) * 11) + i2 + (i3 % 2 != 1 ? 0 : 6);
                } else if (ordinal == 4) {
                    i2 += resultNode.getCharacterCountIndicator() * 8;
                } else if (ordinal == 5) {
                    i2 = characterCountBits + 12;
                } else if (ordinal == 6) {
                    i2 += i3 * 13;
                }
                i += i2;
            }
            return i;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            Iterator it = ((ArrayList) this.list).iterator();
            ResultNode resultNode = null;
            while (it.hasNext()) {
                ResultNode resultNode2 = (ResultNode) it.next();
                if (resultNode != null) {
                    sb.append(",");
                }
                sb.append(resultNode2.toString());
                resultNode = resultNode2;
            }
            return sb.toString();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    enum VersionSize {
        SMALL("version 1-9"),
        MEDIUM("version 10-26"),
        LARGE("version 27-40");

        private final String description;

        VersionSize(String str) {
            this.description = str;
        }

        @Override // java.lang.Enum
        public final String toString() {
            return this.description;
        }
    }

    public MinimalEncoder(
            String str, Charset charset, boolean z, ErrorCorrectionLevel errorCorrectionLevel) {
        this.stringToEncode = str;
        this.isGS1 = z;
        this.encoders = new ECIEncoderSet(str, charset, -1);
        this.ecLevel = errorCorrectionLevel;
    }

    public static void addEdge(Edge[][][] edgeArr, int i, Edge edge) {
        Edge[] edgeArr2 = edgeArr[i + edge.characterLength][edge.charsetEncoderIndex];
        Mode mode = edge.mode;
        char c = 0;
        if (mode != null) {
            int ordinal = mode.ordinal();
            if (ordinal == 1) {
                c = 2;
            } else if (ordinal == 2) {
                c = 1;
            } else if (ordinal == 4) {
                c = 3;
            } else if (ordinal != 6) {
                throw new IllegalStateException("Illegal mode " + mode);
            }
        }
        Edge edge2 = edgeArr2[c];
        if (edge2 != null) {
            if (edge2.cachedTotalSize <= edge.cachedTotalSize) {
                return;
            }
        }
        edgeArr2[c] = edge;
    }

    public static boolean canEncode(Mode mode, char c) {
        int i;
        int ordinal = mode.ordinal();
        if (ordinal == 1) {
            return c >= '0' && c <= '9';
        }
        if (ordinal != 2) {
            if (ordinal == 4) {
                return true;
            }
            if (ordinal != 6) {
                return false;
            }
            return Encoder.isOnlyDoubleByteKanji(String.valueOf(c));
        }
        if (c < '`') {
            i = Encoder.ALPHANUMERIC_TABLE[c];
        } else {
            int[] iArr = Encoder.ALPHANUMERIC_TABLE;
            i = -1;
        }
        return i != -1;
    }

    public static Version getVersion(VersionSize versionSize) {
        int ordinal = versionSize.ordinal();
        return ordinal != 0
                ? ordinal != 1 ? Version.getVersionForNumber(40) : Version.getVersionForNumber(26)
                : Version.getVersionForNumber(9);
    }

    public final void addEdges(Version version, Edge[][][] edgeArr, int i, Edge edge) {
        int i2;
        ECIEncoderSet eCIEncoderSet = this.encoders;
        int length = eCIEncoderSet.encoders.length;
        int i3 = eCIEncoderSet.priorityEncoderIndex;
        String str = this.stringToEncode;
        if (i3 < 0 || !eCIEncoderSet.canEncode(str.charAt(i), i3)) {
            i3 = 0;
        } else {
            length = i3 + 1;
        }
        int i4 = length;
        for (int i5 = i3; i5 < i4; i5++) {
            if (eCIEncoderSet.canEncode(str.charAt(i), i5)) {
                addEdge(edgeArr, i, new Edge(this, Mode.BYTE, i, i5, 1, edge, version));
            }
        }
        Mode mode = Mode.KANJI;
        if (canEncode(mode, str.charAt(i))) {
            addEdge(edgeArr, i, new Edge(this, mode, i, 0, 1, edge, version));
        }
        int length2 = str.length();
        Mode mode2 = Mode.ALPHANUMERIC;
        if (canEncode(mode2, str.charAt(i))) {
            int i6 = i + 1;
            addEdge(
                    edgeArr,
                    i,
                    new Edge(
                            this,
                            mode2,
                            i,
                            0,
                            (i6 >= length2 || !canEncode(mode2, str.charAt(i6))) ? 1 : 2,
                            edge,
                            version));
        }
        Mode mode3 = Mode.NUMERIC;
        if (canEncode(mode3, str.charAt(i))) {
            int i7 = i + 1;
            if (i7 >= length2 || !canEncode(mode3, str.charAt(i7))) {
                i2 = 1;
            } else {
                int i8 = i + 2;
                i2 = (i8 >= length2 || !canEncode(mode3, str.charAt(i8))) ? 2 : 3;
            }
            addEdge(edgeArr, i, new Edge(this, mode3, i, 0, i2, edge, version));
        }
    }

    public final ResultList encodeSpecificVersion(Version version) {
        int i;
        String str = this.stringToEncode;
        int length = str.length();
        ECIEncoderSet eCIEncoderSet = this.encoders;
        Edge[][][] edgeArr =
                (Edge[][][])
                        Array.newInstance(
                                (Class<?>) Edge.class,
                                length + 1,
                                eCIEncoderSet.encoders.length,
                                4);
        addEdges(version, edgeArr, 0, null);
        for (int i2 = 1; i2 <= length; i2++) {
            for (int i3 = 0; i3 < eCIEncoderSet.encoders.length; i3++) {
                for (int i4 = 0; i4 < 4; i4++) {
                    Edge edge = edgeArr[i2][i3][i4];
                    if (edge != null && i2 < length) {
                        addEdges(version, edgeArr, i2, edge);
                    }
                }
            }
        }
        int i5 = -1;
        int i6 = Integer.MAX_VALUE;
        int i7 = -1;
        for (int i8 = 0; i8 < eCIEncoderSet.encoders.length; i8++) {
            for (int i9 = 0; i9 < 4; i9++) {
                Edge edge2 = edgeArr[length][i8][i9];
                if (edge2 != null && (i = edge2.cachedTotalSize) < i6) {
                    i5 = i8;
                    i7 = i9;
                    i6 = i;
                }
            }
        }
        if (i5 >= 0) {
            return new ResultList(version, edgeArr[length][i5][i7]);
        }
        throw new WriterException(
                ComposerKt$$ExternalSyntheticOutline0.m(
                        "Internal error: failed to encode \"", str, "\""));
    }
}
