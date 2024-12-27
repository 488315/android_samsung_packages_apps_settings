package com.google.protobuf;

import com.samsung.android.settings.goodsettings.GoodSettingsContract;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class MessageLiteToString {
    public static final char[] INDENT_BUFFER;

    static {
        char[] cArr = new char[80];
        INDENT_BUFFER = cArr;
        Arrays.fill(cArr, ' ');
    }

    public static void indent(int i, StringBuilder sb) {
        while (i > 0) {
            int i2 = 80;
            if (i <= 80) {
                i2 = i;
            }
            sb.append(INDENT_BUFFER, 0, i2);
            i -= i2;
        }
    }

    public static void printField(StringBuilder sb, int i, String str, Object obj) {
        if (obj instanceof List) {
            Iterator it = ((List) obj).iterator();
            while (it.hasNext()) {
                printField(sb, i, str, it.next());
            }
            return;
        }
        if (obj instanceof Map) {
            Iterator it2 = ((Map) obj).entrySet().iterator();
            while (it2.hasNext()) {
                printField(sb, i, str, (Map.Entry) it2.next());
            }
            return;
        }
        sb.append('\n');
        indent(i, sb);
        if (!str.isEmpty()) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(Character.toLowerCase(str.charAt(0)));
            for (int i2 = 1; i2 < str.length(); i2++) {
                char charAt = str.charAt(i2);
                if (Character.isUpperCase(charAt)) {
                    sb2.append("_");
                }
                sb2.append(Character.toLowerCase(charAt));
            }
            str = sb2.toString();
        }
        sb.append(str);
        if (obj instanceof String) {
            sb.append(": \"");
            ByteString byteString = ByteString.EMPTY;
            sb.append(
                    TextFormatEscaper.escapeBytes(
                            new ByteString.LiteralByteString(
                                    ((String) obj).getBytes(Internal.UTF_8))));
            sb.append('\"');
            return;
        }
        if (obj instanceof ByteString) {
            sb.append(": \"");
            sb.append(TextFormatEscaper.escapeBytes((ByteString) obj));
            sb.append('\"');
            return;
        }
        if (obj instanceof GeneratedMessageLite) {
            sb.append(" {");
            reflectivePrintWithIndent((GeneratedMessageLite) obj, sb, i + 2);
            sb.append("\n");
            indent(i, sb);
            sb.append("}");
            return;
        }
        if (!(obj instanceof Map.Entry)) {
            sb.append(": ");
            sb.append(obj);
            return;
        }
        sb.append(" {");
        Map.Entry entry = (Map.Entry) obj;
        int i3 = i + 2;
        printField(sb, i3, GoodSettingsContract.EXTRA_NAME.POLICY_KEY, entry.getKey());
        printField(sb, i3, "value", entry.getValue());
        sb.append("\n");
        indent(i, sb);
        sb.append("}");
    }

    /* JADX WARN: Code restructure failed: missing block: B:85:0x0196, code lost:

       if (((java.lang.Integer) r7).intValue() == 0) goto L74;
    */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0198, code lost:

       r13 = true;
    */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x01a9, code lost:

       if (java.lang.Float.floatToRawIntBits(((java.lang.Float) r7).floatValue()) == 0) goto L74;
    */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x01bf, code lost:

       if (java.lang.Double.doubleToRawLongBits(((java.lang.Double) r7).doubleValue()) == 0) goto L74;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void reflectivePrintWithIndent(
            com.google.protobuf.MessageLite r20, java.lang.StringBuilder r21, int r22) {
        /*
            Method dump skipped, instructions count: 565
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.protobuf.MessageLiteToString.reflectivePrintWithIndent(com.google.protobuf.MessageLite,"
                    + " java.lang.StringBuilder, int):void");
    }
}
