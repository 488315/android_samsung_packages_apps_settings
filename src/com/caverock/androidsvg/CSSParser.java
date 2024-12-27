package com.caverock.androidsvg;

import android.util.Log;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class CSSParser {
    public MediaType deviceMediaType;
    public boolean inMediaRule;
    public Source source;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Attrib {
        public final String name;
        public final AttribOp operation;
        public final String value;

        public Attrib(String str, AttribOp attribOp, String str2) {
            this.name = str;
            this.operation = attribOp;
            this.value = str2;
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    final class AttribOp {
        public static final /* synthetic */ AttribOp[] $VALUES;
        public static final AttribOp DASHMATCH;
        public static final AttribOp EQUALS;
        public static final AttribOp EXISTS;
        public static final AttribOp INCLUDES;

        static {
            AttribOp attribOp = new AttribOp("EXISTS", 0);
            EXISTS = attribOp;
            AttribOp attribOp2 = new AttribOp("EQUALS", 1);
            EQUALS = attribOp2;
            AttribOp attribOp3 = new AttribOp("INCLUDES", 2);
            INCLUDES = attribOp3;
            AttribOp attribOp4 = new AttribOp("DASHMATCH", 3);
            DASHMATCH = attribOp4;
            $VALUES = new AttribOp[] {attribOp, attribOp2, attribOp3, attribOp4};
        }

        public static AttribOp valueOf(String str) {
            return (AttribOp) Enum.valueOf(AttribOp.class, str);
        }

        public static AttribOp[] values() {
            return (AttribOp[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CSSTextScanner extends SVGParser.TextScanner {

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class AnPlusB {
            public final int a;
            public final int b;

            public AnPlusB(int i, int i2) {
                this.a = i;
                this.b = i2;
            }
        }

        public CSSTextScanner(String str) {
            super(str.replaceAll("(?s)/\\*.*?\\*/", ApnSettings.MVNO_NONE));
        }

        public static int hexChar(int i) {
            if (i >= 48 && i <= 57) {
                return i - 48;
            }
            if (i >= 65 && i <= 70) {
                return i - 55;
            }
            if (i < 97 || i > 102) {
                return -1;
            }
            return i - 87;
        }

        public final String nextCSSString() {
            int hexChar;
            if (empty()) {
                return null;
            }
            char charAt = this.input.charAt(this.position);
            if (charAt != '\'' && charAt != '\"') {
                return null;
            }
            StringBuilder sb = new StringBuilder();
            this.position++;
            int intValue = nextChar().intValue();
            while (intValue != -1 && intValue != charAt) {
                if (intValue == 92) {
                    intValue = nextChar().intValue();
                    if (intValue != -1) {
                        if (intValue == 10 || intValue == 13 || intValue == 12) {
                            intValue = nextChar().intValue();
                        } else {
                            int hexChar2 = hexChar(intValue);
                            if (hexChar2 != -1) {
                                for (int i = 1;
                                        i <= 5
                                                && (hexChar =
                                                                hexChar(
                                                                        (intValue =
                                                                                nextChar()
                                                                                        .intValue())))
                                                        != -1;
                                        i++) {
                                    hexChar2 = (hexChar2 * 16) + hexChar;
                                }
                                sb.append((char) hexChar2);
                            }
                        }
                    }
                }
                sb.append((char) intValue);
                intValue = nextChar().intValue();
            }
            return sb.toString();
        }

        public final String nextIdentifier() {
            int i;
            int i2;
            boolean empty = empty();
            String str = this.input;
            if (empty) {
                i2 = this.position;
            } else {
                int i3 = this.position;
                int charAt = str.charAt(i3);
                if (charAt == 45) {
                    charAt = advanceChar();
                }
                if ((charAt < 65 || charAt > 90)
                        && ((charAt < 97 || charAt > 122) && charAt != 95)) {
                    i = i3;
                } else {
                    int advanceChar = advanceChar();
                    while (true) {
                        if ((advanceChar < 65 || advanceChar > 90)
                                && ((advanceChar < 97 || advanceChar > 122)
                                        && !((advanceChar >= 48 && advanceChar <= 57)
                                                || advanceChar == 45
                                                || advanceChar == 95))) {
                            break;
                        }
                        advanceChar = advanceChar();
                    }
                    i = this.position;
                }
                this.position = i3;
                i2 = i;
            }
            int i4 = this.position;
            if (i2 == i4) {
                return null;
            }
            String substring = str.substring(i4, i2);
            this.position = i2;
            return substring;
        }

        /* JADX WARN: Code restructure failed: missing block: B:221:0x0470, code lost:

           r0 = r4.simpleSelectors;
        */
        /* JADX WARN: Code restructure failed: missing block: B:222:0x0472, code lost:

           if (r0 == null) goto L275;
        */
        /* JADX WARN: Code restructure failed: missing block: B:224:0x047a, code lost:

           if (((java.util.ArrayList) r0).isEmpty() == false) goto L274;
        */
        /* JADX WARN: Code restructure failed: missing block: B:225:0x047d, code lost:

           r1.add(r4);
        */
        /* JADX WARN: Code restructure failed: missing block: B:226:0x0480, code lost:

           return r1;
        */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:145:0x03f1  */
        /* JADX WARN: Removed duplicated region for block: B:146:0x0408 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:157:0x03ec  */
        /* JADX WARN: Removed duplicated region for block: B:210:0x0449  */
        /* JADX WARN: Removed duplicated region for block: B:219:0x046e A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:22:0x005d  */
        /* JADX WARN: Removed duplicated region for block: B:26:0x007c  */
        /* JADX WARN: Removed duplicated region for block: B:287:0x0063  */
        /* JADX WARN: Removed duplicated region for block: B:50:0x042b  */
        /* JADX WARN: Removed duplicated region for block: B:77:0x0255  */
        /* JADX WARN: Removed duplicated region for block: B:90:0x027b A[SYNTHETIC] */
        /* JADX WARN: Type inference failed for: r11v0, types: [com.caverock.androidsvg.CSSParser$AttribOp] */
        /* JADX WARN: Type inference failed for: r11v3, types: [com.caverock.androidsvg.CSSParser$AttribOp] */
        /* JADX WARN: Type inference failed for: r11v42, types: [com.caverock.androidsvg.CSSParser$PseudoClassAnPlusB] */
        /* JADX WARN: Type inference failed for: r11v43, types: [com.caverock.androidsvg.CSSParser$PseudoClassAnPlusB] */
        /* JADX WARN: Type inference failed for: r11v44, types: [com.caverock.androidsvg.CSSParser$PseudoClassAnPlusB] */
        /* JADX WARN: Type inference failed for: r11v45, types: [com.caverock.androidsvg.CSSParser$PseudoClassAnPlusB] */
        /* JADX WARN: Type inference failed for: r11v46, types: [com.caverock.androidsvg.CSSParser$PseudoClassOnlyChild] */
        /* JADX WARN: Type inference failed for: r11v47, types: [com.caverock.androidsvg.CSSParser$PseudoClassOnlyChild] */
        /* JADX WARN: Type inference failed for: r11v56, types: [com.caverock.androidsvg.CSSParser$PseudoClassNot, java.lang.Object] */
        /* JADX WARN: Type inference failed for: r11v58, types: [com.caverock.androidsvg.CSSParser$PseudoClassNotSupported] */
        /* JADX WARN: Type inference failed for: r11v61 */
        /* JADX WARN: Type inference failed for: r11v63, types: [com.caverock.androidsvg.CSSParser$PseudoClassNotSupported] */
        /* JADX WARN: Type inference failed for: r11v76 */
        /* JADX WARN: Type inference failed for: r11v77 */
        /* JADX WARN: Type inference failed for: r12v24 */
        /* JADX WARN: Type inference failed for: r12v25 */
        /* JADX WARN: Type inference failed for: r12v26, types: [java.util.ArrayList] */
        /* JADX WARN: Type inference failed for: r12v29 */
        /* JADX WARN: Type inference failed for: r12v30 */
        /* JADX WARN: Type inference failed for: r2v26, types: [com.caverock.androidsvg.CSSParser$PseudoClassAnPlusB] */
        /* JADX WARN: Type inference failed for: r3v0 */
        /* JADX WARN: Type inference failed for: r3v1, types: [int] */
        /* JADX WARN: Type inference failed for: r3v16 */
        /* JADX WARN: Type inference failed for: r3v2, types: [boolean] */
        /* JADX WARN: Type inference failed for: r3v3 */
        /* JADX WARN: Type inference failed for: r3v4 */
        /* JADX WARN: Type inference failed for: r6v13 */
        /* JADX WARN: Type inference failed for: r6v14 */
        /* JADX WARN: Type inference failed for: r6v3 */
        /* JADX WARN: Type inference failed for: r6v4, types: [com.caverock.androidsvg.CSSParser$Combinator] */
        /* JADX WARN: Type inference failed for: r7v11 */
        /* JADX WARN: Type inference failed for: r7v16 */
        /* JADX WARN: Type inference failed for: r7v6 */
        /* JADX WARN: Type inference failed for: r7v9, types: [com.caverock.androidsvg.CSSParser$CSSTextScanner$AnPlusB] */
        /* JADX WARN: Type inference failed for: r9v10, types: [com.caverock.androidsvg.CSSParser$SimpleSelector] */
        /* JADX WARN: Type inference failed for: r9v11 */
        /* JADX WARN: Type inference failed for: r9v12, types: [com.caverock.androidsvg.CSSParser$SimpleSelector] */
        /* JADX WARN: Type inference failed for: r9v13, types: [com.caverock.androidsvg.CSSParser$SimpleSelector] */
        /* JADX WARN: Type inference failed for: r9v14, types: [com.caverock.androidsvg.CSSParser$SimpleSelector] */
        /* JADX WARN: Type inference failed for: r9v15, types: [com.caverock.androidsvg.CSSParser$SimpleSelector] */
        /* JADX WARN: Type inference failed for: r9v16, types: [com.caverock.androidsvg.CSSParser$SimpleSelector] */
        /* JADX WARN: Type inference failed for: r9v19 */
        /* JADX WARN: Type inference failed for: r9v20 */
        /* JADX WARN: Type inference failed for: r9v21 */
        /* JADX WARN: Type inference failed for: r9v3 */
        /* JADX WARN: Type inference failed for: r9v6 */
        /* JADX WARN: Type inference failed for: r9v7 */
        /* JADX WARN: Type inference failed for: r9v8, types: [java.lang.Object] */
        /* JADX WARN: Type inference failed for: r9v9, types: [com.caverock.androidsvg.CSSParser$SimpleSelector] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.util.List nextSelectorGroup() {
            /*
                Method dump skipped, instructions count: 1206
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.caverock.androidsvg.CSSParser.CSSTextScanner.nextSelectorGroup():java.util.List");
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    final class Combinator {
        public static final /* synthetic */ Combinator[] $VALUES;
        public static final Combinator CHILD;
        public static final Combinator DESCENDANT;
        public static final Combinator FOLLOWS;

        static {
            Combinator combinator = new Combinator("DESCENDANT", 0);
            DESCENDANT = combinator;
            Combinator combinator2 = new Combinator("CHILD", 1);
            CHILD = combinator2;
            Combinator combinator3 = new Combinator("FOLLOWS", 2);
            FOLLOWS = combinator3;
            $VALUES = new Combinator[] {combinator, combinator2, combinator3};
        }

        public static Combinator valueOf(String str) {
            return (Combinator) Enum.valueOf(Combinator.class, str);
        }

        public static Combinator[] values() {
            return (Combinator[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    final class MediaType {
        public static final /* synthetic */ MediaType[] $VALUES;
        public static final MediaType all;
        public static final MediaType screen;

        static {
            MediaType mediaType = new MediaType("all", 0);
            all = mediaType;
            MediaType mediaType2 = new MediaType("aural", 1);
            MediaType mediaType3 = new MediaType("braille", 2);
            MediaType mediaType4 = new MediaType("embossed", 3);
            MediaType mediaType5 = new MediaType("handheld", 4);
            MediaType mediaType6 = new MediaType("print", 5);
            MediaType mediaType7 = new MediaType("projection", 6);
            MediaType mediaType8 = new MediaType("screen", 7);
            screen = mediaType8;
            $VALUES =
                    new MediaType[] {
                        mediaType,
                        mediaType2,
                        mediaType3,
                        mediaType4,
                        mediaType5,
                        mediaType6,
                        mediaType7,
                        mediaType8,
                        new MediaType("speech", 8),
                        new MediaType("tty", 9),
                        new MediaType("tv", 10)
                    };
        }

        public static MediaType valueOf(String str) {
            return (MediaType) Enum.valueOf(MediaType.class, str);
        }

        public static MediaType[] values() {
            return (MediaType[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface PseudoClass {
        boolean matches(RuleMatchContext ruleMatchContext, SVG.SvgElementBase svgElementBase);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PseudoClassAnPlusB implements PseudoClass {
        public final int a;
        public final int b;
        public final boolean isFromStart;
        public final boolean isOfType;
        public final String nodeName;

        public PseudoClassAnPlusB(int i, int i2, boolean z, boolean z2, String str) {
            this.a = i;
            this.b = i2;
            this.isFromStart = z;
            this.isOfType = z2;
            this.nodeName = str;
        }

        @Override // com.caverock.androidsvg.CSSParser.PseudoClass
        public final boolean matches(
                RuleMatchContext ruleMatchContext, SVG.SvgElementBase svgElementBase) {
            int i;
            int i2;
            boolean z = this.isOfType;
            String str = this.nodeName;
            if (z && str == null) {
                str = svgElementBase.getNodeName();
            }
            SVG.SvgContainer svgContainer = svgElementBase.parent;
            if (svgContainer != null) {
                Iterator it = svgContainer.getChildren().iterator();
                i2 = 0;
                i = 0;
                while (it.hasNext()) {
                    SVG.SvgElementBase svgElementBase2 =
                            (SVG.SvgElementBase) ((SVG.SvgObject) it.next());
                    if (svgElementBase2 == svgElementBase) {
                        i2 = i;
                    }
                    if (str == null || svgElementBase2.getNodeName().equals(str)) {
                        i++;
                    }
                }
            } else {
                i = 1;
                i2 = 0;
            }
            int i3 = this.isFromStart ? i2 + 1 : i - i2;
            int i4 = this.a;
            int i5 = this.b;
            if (i4 == 0) {
                return i3 == i5;
            }
            int i6 = i3 - i5;
            return i6 % i4 == 0
                    && (Integer.signum(i6) == 0 || Integer.signum(i6) == Integer.signum(i4));
        }

        public final String toString() {
            String str = this.isFromStart ? ApnSettings.MVNO_NONE : "last-";
            boolean z = this.isOfType;
            int i = this.b;
            int i2 = this.a;
            return z
                    ? String.format(
                            "nth-%schild(%dn%+d of type <%s>)",
                            str, Integer.valueOf(i2), Integer.valueOf(i), this.nodeName)
                    : String.format(
                            "nth-%schild(%dn%+d)", str, Integer.valueOf(i2), Integer.valueOf(i));
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    final class PseudoClassIdents {
        public static final /* synthetic */ PseudoClassIdents[] $VALUES;
        public static final PseudoClassIdents UNSUPPORTED;
        public static final Map cache;
        public static final PseudoClassIdents nth_child;
        public static final PseudoClassIdents nth_last_of_type;
        public static final PseudoClassIdents nth_of_type;

        /* JADX INFO: Fake field, exist only in values array */
        PseudoClassIdents EF0;

        static {
            PseudoClassIdents pseudoClassIdents = new PseudoClassIdents("target", 0);
            PseudoClassIdents pseudoClassIdents2 = new PseudoClassIdents("root", 1);
            PseudoClassIdents pseudoClassIdents3 = new PseudoClassIdents("nth_child", 2);
            nth_child = pseudoClassIdents3;
            PseudoClassIdents pseudoClassIdents4 = new PseudoClassIdents("nth_last_child", 3);
            PseudoClassIdents pseudoClassIdents5 = new PseudoClassIdents("nth_of_type", 4);
            nth_of_type = pseudoClassIdents5;
            PseudoClassIdents pseudoClassIdents6 = new PseudoClassIdents("nth_last_of_type", 5);
            nth_last_of_type = pseudoClassIdents6;
            PseudoClassIdents pseudoClassIdents7 = new PseudoClassIdents("first_child", 6);
            PseudoClassIdents pseudoClassIdents8 = new PseudoClassIdents("last_child", 7);
            PseudoClassIdents pseudoClassIdents9 = new PseudoClassIdents("first_of_type", 8);
            PseudoClassIdents pseudoClassIdents10 = new PseudoClassIdents("last_of_type", 9);
            PseudoClassIdents pseudoClassIdents11 = new PseudoClassIdents("only_child", 10);
            PseudoClassIdents pseudoClassIdents12 = new PseudoClassIdents("only_of_type", 11);
            PseudoClassIdents pseudoClassIdents13 = new PseudoClassIdents("empty", 12);
            PseudoClassIdents pseudoClassIdents14 = new PseudoClassIdents("not", 13);
            PseudoClassIdents pseudoClassIdents15 = new PseudoClassIdents("lang", 14);
            PseudoClassIdents pseudoClassIdents16 = new PseudoClassIdents("link", 15);
            PseudoClassIdents pseudoClassIdents17 = new PseudoClassIdents("visited", 16);
            PseudoClassIdents pseudoClassIdents18 = new PseudoClassIdents("hover", 17);
            PseudoClassIdents pseudoClassIdents19 = new PseudoClassIdents("active", 18);
            PseudoClassIdents pseudoClassIdents20 = new PseudoClassIdents("focus", 19);
            PseudoClassIdents pseudoClassIdents21 = new PseudoClassIdents("enabled", 20);
            PseudoClassIdents pseudoClassIdents22 = new PseudoClassIdents("disabled", 21);
            PseudoClassIdents pseudoClassIdents23 = new PseudoClassIdents("checked", 22);
            PseudoClassIdents pseudoClassIdents24 = new PseudoClassIdents("indeterminate", 23);
            PseudoClassIdents pseudoClassIdents25 = new PseudoClassIdents("UNSUPPORTED", 24);
            UNSUPPORTED = pseudoClassIdents25;
            $VALUES =
                    new PseudoClassIdents[] {
                        pseudoClassIdents,
                        pseudoClassIdents2,
                        pseudoClassIdents3,
                        pseudoClassIdents4,
                        pseudoClassIdents5,
                        pseudoClassIdents6,
                        pseudoClassIdents7,
                        pseudoClassIdents8,
                        pseudoClassIdents9,
                        pseudoClassIdents10,
                        pseudoClassIdents11,
                        pseudoClassIdents12,
                        pseudoClassIdents13,
                        pseudoClassIdents14,
                        pseudoClassIdents15,
                        pseudoClassIdents16,
                        pseudoClassIdents17,
                        pseudoClassIdents18,
                        pseudoClassIdents19,
                        pseudoClassIdents20,
                        pseudoClassIdents21,
                        pseudoClassIdents22,
                        pseudoClassIdents23,
                        pseudoClassIdents24,
                        pseudoClassIdents25
                    };
            cache = new HashMap();
            for (PseudoClassIdents pseudoClassIdents26 : values()) {
                if (pseudoClassIdents26 != UNSUPPORTED) {
                    ((HashMap) cache)
                            .put(pseudoClassIdents26.name().replace('_', '-'), pseudoClassIdents26);
                }
            }
        }

        public static PseudoClassIdents valueOf(String str) {
            return (PseudoClassIdents) Enum.valueOf(PseudoClassIdents.class, str);
        }

        public static PseudoClassIdents[] values() {
            return (PseudoClassIdents[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PseudoClassNot implements PseudoClass {
        public List selectorGroup;

        @Override // com.caverock.androidsvg.CSSParser.PseudoClass
        public final boolean matches(
                RuleMatchContext ruleMatchContext, SVG.SvgElementBase svgElementBase) {
            Iterator it = this.selectorGroup.iterator();
            while (it.hasNext()) {
                if (CSSParser.ruleMatch(ruleMatchContext, (Selector) it.next(), svgElementBase)) {
                    return false;
                }
            }
            return true;
        }

        public final String toString() {
            return "not(" + this.selectorGroup + ")";
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PseudoClassNotSupported implements PseudoClass {
        public final String clazz;

        public PseudoClassNotSupported(String str) {
            this.clazz = str;
        }

        @Override // com.caverock.androidsvg.CSSParser.PseudoClass
        public final boolean matches(
                RuleMatchContext ruleMatchContext, SVG.SvgElementBase svgElementBase) {
            return false;
        }

        public final String toString() {
            return this.clazz;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PseudoClassOnlyChild implements PseudoClass {
        public final boolean isOfType;
        public final String nodeName;

        public PseudoClassOnlyChild(String str, boolean z) {
            this.isOfType = z;
            this.nodeName = str;
        }

        @Override // com.caverock.androidsvg.CSSParser.PseudoClass
        public final boolean matches(
                RuleMatchContext ruleMatchContext, SVG.SvgElementBase svgElementBase) {
            int i;
            boolean z = this.isOfType;
            String str = this.nodeName;
            if (z && str == null) {
                str = svgElementBase.getNodeName();
            }
            SVG.SvgContainer svgContainer = svgElementBase.parent;
            if (svgContainer != null) {
                Iterator it = svgContainer.getChildren().iterator();
                i = 0;
                while (it.hasNext()) {
                    SVG.SvgElementBase svgElementBase2 =
                            (SVG.SvgElementBase) ((SVG.SvgObject) it.next());
                    if (str == null || svgElementBase2.getNodeName().equals(str)) {
                        i++;
                    }
                }
            } else {
                i = 1;
            }
            return i == 1;
        }

        public final String toString() {
            return this.isOfType
                    ? ComponentActivity$1$$ExternalSyntheticOutline0.m(
                            new StringBuilder("only-of-type <"), this.nodeName, ">")
                    : "only-child";
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PseudoClassRoot implements PseudoClass {
        public final /* synthetic */ int $r8$classId;

        public /* synthetic */ PseudoClassRoot(int i) {
            this.$r8$classId = i;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.caverock.androidsvg.CSSParser.PseudoClass
        public final boolean matches(
                RuleMatchContext ruleMatchContext, SVG.SvgElementBase svgElementBase) {
            switch (this.$r8$classId) {
                case 0:
                    return svgElementBase.parent == null;
                case 1:
                    return !(svgElementBase instanceof SVG.SvgContainer)
                            || ((SVG.SvgContainer) svgElementBase).getChildren().size() == 0;
                default:
                    return false;
            }
        }

        public final String toString() {
            switch (this.$r8$classId) {
                case 0:
                    return "root";
                case 1:
                    return "empty";
                default:
                    return "target";
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Rule {
        public Selector selector;
        public Source source;
        public SVG.Style style;

        public final String toString() {
            return String.valueOf(this.selector) + " {...} (src=" + this.source + ")";
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class RuleMatchContext {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Ruleset {
        public List rules = null;

        public final void add(Rule rule) {
            if (this.rules == null) {
                this.rules = new ArrayList();
            }
            for (int i = 0; i < this.rules.size(); i++) {
                if (((Rule) this.rules.get(i)).selector.specificity > rule.selector.specificity) {
                    this.rules.add(i, rule);
                    return;
                }
            }
            this.rules.add(rule);
        }

        public final void addAll(Ruleset ruleset) {
            if (ruleset.rules == null) {
                return;
            }
            if (this.rules == null) {
                this.rules = new ArrayList(((ArrayList) ruleset.rules).size());
            }
            Iterator it = ((ArrayList) ruleset.rules).iterator();
            while (it.hasNext()) {
                add((Rule) it.next());
            }
        }

        public final String toString() {
            if (this.rules == null) {
                return ApnSettings.MVNO_NONE;
            }
            StringBuilder sb = new StringBuilder();
            Iterator it = this.rules.iterator();
            while (it.hasNext()) {
                sb.append(((Rule) it.next()).toString());
                sb.append('\n');
            }
            return sb.toString();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Selector {
        public List simpleSelectors = null;
        public int specificity = 0;

        public final void addedAttributeOrPseudo() {
            this.specificity += 1000;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            Iterator it = this.simpleSelectors.iterator();
            while (it.hasNext()) {
                sb.append((SimpleSelector) it.next());
                sb.append(' ');
            }
            sb.append('[');
            return BackEventCompat$$ExternalSyntheticOutline0.m(sb, this.specificity, ']');
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SimpleSelector {
        public final Combinator combinator;
        public final String tag;
        public List attribs = null;
        public List pseudos = null;

        public SimpleSelector(Combinator combinator, String str) {
            this.combinator = null;
            this.tag = null;
            this.combinator = combinator == null ? Combinator.DESCENDANT : combinator;
            this.tag = str;
        }

        public final void addAttrib(String str, AttribOp attribOp, String str2) {
            if (this.attribs == null) {
                this.attribs = new ArrayList();
            }
            ((ArrayList) this.attribs).add(new Attrib(str, attribOp, str2));
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            Combinator combinator = Combinator.CHILD;
            Combinator combinator2 = this.combinator;
            if (combinator2 == combinator) {
                sb.append("> ");
            } else if (combinator2 == Combinator.FOLLOWS) {
                sb.append("+ ");
            }
            String str = this.tag;
            if (str == null) {
                str = "*";
            }
            sb.append(str);
            List list = this.attribs;
            if (list != null) {
                Iterator it = ((ArrayList) list).iterator();
                while (it.hasNext()) {
                    Attrib attrib = (Attrib) it.next();
                    sb.append('[');
                    sb.append(attrib.name);
                    int ordinal = attrib.operation.ordinal();
                    String str2 = attrib.value;
                    if (ordinal == 1) {
                        sb.append('=');
                        sb.append(str2);
                    } else if (ordinal == 2) {
                        sb.append("~=");
                        sb.append(str2);
                    } else if (ordinal == 3) {
                        sb.append("|=");
                        sb.append(str2);
                    }
                    sb.append(']');
                }
            }
            List list2 = this.pseudos;
            if (list2 != null) {
                Iterator it2 = ((ArrayList) list2).iterator();
                while (it2.hasNext()) {
                    PseudoClass pseudoClass = (PseudoClass) it2.next();
                    sb.append(':');
                    sb.append(pseudoClass);
                }
            }
            return sb.toString();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    final class Source {
        public static final /* synthetic */ Source[] $VALUES;
        public static final Source Document;

        static {
            Source source = new Source("Document", 0);
            Document = source;
            $VALUES = new Source[] {source, new Source("RenderOptions", 1)};
        }

        public static Source valueOf(String str) {
            return (Source) Enum.valueOf(Source.class, str);
        }

        public static Source[] values() {
            return (Source[]) $VALUES.clone();
        }
    }

    public static int getChildPosition(List list, int i, SVG.SvgElementBase svgElementBase) {
        int i2 = 0;
        if (i < 0) {
            return 0;
        }
        Object obj = ((ArrayList) list).get(i);
        SVG.SvgContainer svgContainer = svgElementBase.parent;
        if (obj != svgContainer) {
            return -1;
        }
        Iterator it = svgContainer.getChildren().iterator();
        while (it.hasNext()) {
            if (((SVG.SvgObject) it.next()) == svgElementBase) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    public static List parseMediaList(CSSTextScanner cSSTextScanner) {
        ArrayList arrayList = new ArrayList();
        while (!cSSTextScanner.empty()) {
            String str = null;
            if (!cSSTextScanner.empty()) {
                int i = cSSTextScanner.position;
                String str2 = cSSTextScanner.input;
                char charAt = str2.charAt(i);
                if ((charAt < 'A' || charAt > 'Z') && (charAt < 'a' || charAt > 'z')) {
                    cSSTextScanner.position = i;
                } else {
                    int advanceChar = cSSTextScanner.advanceChar();
                    while (true) {
                        if ((advanceChar < 65 || advanceChar > 90)
                                && (advanceChar < 97 || advanceChar > 122)) {
                            break;
                        }
                        advanceChar = cSSTextScanner.advanceChar();
                    }
                    str = str2.substring(i, cSSTextScanner.position);
                }
            }
            if (str == null) {
                break;
            }
            try {
                arrayList.add(MediaType.valueOf(str));
            } catch (IllegalArgumentException unused) {
            }
            if (!cSSTextScanner.skipCommaWhitespace()) {
                break;
            }
        }
        return arrayList;
    }

    public static boolean ruleMatch(
            RuleMatchContext ruleMatchContext,
            Selector selector,
            int i,
            List list,
            int i2,
            SVG.SvgElementBase svgElementBase) {
        SimpleSelector simpleSelector =
                (SimpleSelector) ((ArrayList) selector.simpleSelectors).get(i);
        if (!selectorMatch(ruleMatchContext, simpleSelector, svgElementBase)) {
            return false;
        }
        Combinator combinator = Combinator.DESCENDANT;
        Combinator combinator2 = simpleSelector.combinator;
        if (combinator2 == combinator) {
            if (i == 0) {
                return true;
            }
            while (i2 >= 0) {
                if (ruleMatchOnAncestors(ruleMatchContext, selector, i - 1, list, i2)) {
                    return true;
                }
                i2--;
            }
            return false;
        }
        if (combinator2 == Combinator.CHILD) {
            return ruleMatchOnAncestors(ruleMatchContext, selector, i - 1, list, i2);
        }
        int childPosition = getChildPosition(list, i2, svgElementBase);
        if (childPosition <= 0) {
            return false;
        }
        return ruleMatch(
                ruleMatchContext,
                selector,
                i - 1,
                list,
                i2,
                (SVG.SvgElementBase) svgElementBase.parent.getChildren().get(childPosition - 1));
    }

    public static boolean ruleMatchOnAncestors(
            RuleMatchContext ruleMatchContext, Selector selector, int i, List list, int i2) {
        SimpleSelector simpleSelector =
                (SimpleSelector) ((ArrayList) selector.simpleSelectors).get(i);
        SVG.SvgElementBase svgElementBase = (SVG.SvgElementBase) ((ArrayList) list).get(i2);
        if (!selectorMatch(ruleMatchContext, simpleSelector, svgElementBase)) {
            return false;
        }
        Combinator combinator = Combinator.DESCENDANT;
        Combinator combinator2 = simpleSelector.combinator;
        if (combinator2 == combinator) {
            if (i == 0) {
                return true;
            }
            while (i2 > 0) {
                i2--;
                if (ruleMatchOnAncestors(ruleMatchContext, selector, i - 1, list, i2)) {
                    return true;
                }
            }
            return false;
        }
        if (combinator2 == Combinator.CHILD) {
            return ruleMatchOnAncestors(ruleMatchContext, selector, i - 1, list, i2 - 1);
        }
        int childPosition = getChildPosition(list, i2, svgElementBase);
        if (childPosition <= 0) {
            return false;
        }
        return ruleMatch(
                ruleMatchContext,
                selector,
                i - 1,
                list,
                i2,
                (SVG.SvgElementBase) svgElementBase.parent.getChildren().get(childPosition - 1));
    }

    public static boolean selectorMatch(
            RuleMatchContext ruleMatchContext,
            SimpleSelector simpleSelector,
            SVG.SvgElementBase svgElementBase) {
        List list;
        String str = simpleSelector.tag;
        if (str != null && !str.equals(svgElementBase.getNodeName().toLowerCase(Locale.US))) {
            return false;
        }
        List list2 = simpleSelector.attribs;
        if (list2 != null) {
            Iterator it = ((ArrayList) list2).iterator();
            while (it.hasNext()) {
                Attrib attrib = (Attrib) it.next();
                String str2 = attrib.name;
                str2.getClass();
                String str3 = attrib.value;
                if (str2.equals("id")) {
                    if (!str3.equals(svgElementBase.id)) {
                        return false;
                    }
                } else if (!str2.equals("class")
                        || (list = svgElementBase.classNames) == null
                        || !list.contains(str3)) {
                    return false;
                }
            }
        }
        List list3 = simpleSelector.pseudos;
        if (list3 == null) {
            return true;
        }
        Iterator it2 = ((ArrayList) list3).iterator();
        while (it2.hasNext()) {
            if (!((PseudoClass) it2.next()).matches(ruleMatchContext, svgElementBase)) {
                return false;
            }
        }
        return true;
    }

    public final void parseAtRule(Ruleset ruleset, CSSTextScanner cSSTextScanner) {
        int intValue;
        int hexChar;
        String nextIdentifier = cSSTextScanner.nextIdentifier();
        cSSTextScanner.skipWhitespace();
        if (nextIdentifier == null) {
            throw new CSSParseException("Invalid '@' rule");
        }
        int i = 0;
        if (!this.inMediaRule && nextIdentifier.equals("media")) {
            List parseMediaList = parseMediaList(cSSTextScanner);
            if (!cSSTextScanner.consume('{')) {
                throw new CSSParseException("Invalid @media rule: missing rule set");
            }
            cSSTextScanner.skipWhitespace();
            MediaType mediaType = this.deviceMediaType;
            Iterator it = ((ArrayList) parseMediaList).iterator();
            while (it.hasNext()) {
                MediaType mediaType2 = (MediaType) it.next();
                if (mediaType2 == MediaType.all || mediaType2 == mediaType) {
                    this.inMediaRule = true;
                    ruleset.addAll(parseRuleset(cSSTextScanner));
                    this.inMediaRule = false;
                    break;
                }
            }
            parseRuleset(cSSTextScanner);
            if (!cSSTextScanner.empty() && !cSSTextScanner.consume('}')) {
                throw new CSSParseException("Invalid @media rule: expected '}' at end of rule set");
            }
        } else if (this.inMediaRule || !nextIdentifier.equals("import")) {
            Log.w("CSSParser", "Ignoring @" + nextIdentifier + " rule");
            while (!cSSTextScanner.empty()
                    && ((intValue = cSSTextScanner.nextChar().intValue()) != 59 || i != 0)) {
                if (intValue == 123) {
                    i++;
                } else if (intValue == 125 && i > 0 && i - 1 == 0) {
                    break;
                }
            }
        } else {
            String str = null;
            if (!cSSTextScanner.empty()) {
                int i2 = cSSTextScanner.position;
                if (cSSTextScanner.consume("url(")) {
                    cSSTextScanner.skipWhitespace();
                    String nextCSSString = cSSTextScanner.nextCSSString();
                    if (nextCSSString == null) {
                        StringBuilder sb = new StringBuilder();
                        while (!cSSTextScanner.empty()) {
                            int i3 = cSSTextScanner.position;
                            String str2 = cSSTextScanner.input;
                            char charAt = str2.charAt(i3);
                            if (charAt == '\''
                                    || charAt == '\"'
                                    || charAt == '('
                                    || charAt == ')'
                                    || SVGParser.TextScanner.isWhitespace(charAt)
                                    || Character.isISOControl((int) charAt)) {
                                break;
                            }
                            cSSTextScanner.position++;
                            if (charAt == '\\') {
                                if (!cSSTextScanner.empty()) {
                                    int i4 = cSSTextScanner.position;
                                    cSSTextScanner.position = i4 + 1;
                                    charAt = str2.charAt(i4);
                                    if (charAt != '\n' && charAt != '\r' && charAt != '\f') {
                                        int hexChar2 = CSSTextScanner.hexChar(charAt);
                                        if (hexChar2 != -1) {
                                            for (int i5 = 1;
                                                    i5 <= 5
                                                            && !cSSTextScanner.empty()
                                                            && (hexChar =
                                                                            CSSTextScanner.hexChar(
                                                                                    str2.charAt(
                                                                                            cSSTextScanner
                                                                                                    .position)))
                                                                    != -1;
                                                    i5++) {
                                                cSSTextScanner.position++;
                                                hexChar2 = (hexChar2 * 16) + hexChar;
                                            }
                                            sb.append((char) hexChar2);
                                        }
                                    }
                                }
                            }
                            sb.append(charAt);
                        }
                        nextCSSString = sb.length() == 0 ? null : sb.toString();
                    }
                    if (nextCSSString == null) {
                        cSSTextScanner.position = i2;
                    } else {
                        cSSTextScanner.skipWhitespace();
                        if (cSSTextScanner.empty() || cSSTextScanner.consume(")")) {
                            str = nextCSSString;
                        } else {
                            cSSTextScanner.position = i2;
                        }
                    }
                }
            }
            if (str == null) {
                str = cSSTextScanner.nextCSSString();
            }
            if (str == null) {
                throw new CSSParseException("Invalid @import rule: expected string or url()");
            }
            cSSTextScanner.skipWhitespace();
            parseMediaList(cSSTextScanner);
            if (!cSSTextScanner.empty() && !cSSTextScanner.consume(';')) {
                throw new CSSParseException("Invalid @media rule: expected '}' at end of rule set");
            }
        }
        cSSTextScanner.skipWhitespace();
    }

    public final boolean parseRule(Ruleset ruleset, CSSTextScanner cSSTextScanner) {
        List nextSelectorGroup = cSSTextScanner.nextSelectorGroup();
        if (nextSelectorGroup == null) {
            return false;
        }
        ArrayList arrayList = (ArrayList) nextSelectorGroup;
        if (arrayList.isEmpty()) {
            return false;
        }
        if (!cSSTextScanner.consume('{')) {
            throw new CSSParseException("Malformed rule block: expected '{'");
        }
        cSSTextScanner.skipWhitespace();
        SVG.Style style = new SVG.Style();
        do {
            String nextIdentifier = cSSTextScanner.nextIdentifier();
            cSSTextScanner.skipWhitespace();
            if (!cSSTextScanner.consume(':')) {
                throw new CSSParseException("Expected ':'");
            }
            cSSTextScanner.skipWhitespace();
            String str = null;
            if (!cSSTextScanner.empty()) {
                int i = cSSTextScanner.position;
                String str2 = cSSTextScanner.input;
                int charAt = str2.charAt(i);
                int i2 = i;
                while (charAt != -1
                        && charAt != 59
                        && charAt != 125
                        && charAt != 33
                        && charAt != 10
                        && charAt != 13) {
                    if (!SVGParser.TextScanner.isWhitespace(charAt)) {
                        i2 = cSSTextScanner.position + 1;
                    }
                    charAt = cSSTextScanner.advanceChar();
                }
                if (cSSTextScanner.position > i) {
                    str = str2.substring(i, i2);
                } else {
                    cSSTextScanner.position = i;
                }
            }
            if (str == null) {
                throw new CSSParseException("Expected property value");
            }
            cSSTextScanner.skipWhitespace();
            if (cSSTextScanner.consume('!')) {
                cSSTextScanner.skipWhitespace();
                if (!cSSTextScanner.consume("important")) {
                    throw new CSSParseException("Malformed rule set: found unexpected '!'");
                }
                cSSTextScanner.skipWhitespace();
            }
            cSSTextScanner.consume(';');
            SVGParser.processStyleProperty(style, nextIdentifier, str);
            cSSTextScanner.skipWhitespace();
            if (cSSTextScanner.empty()) {
                break;
            }
        } while (!cSSTextScanner.consume('}'));
        cSSTextScanner.skipWhitespace();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Selector selector = (Selector) it.next();
            Rule rule = new Rule();
            rule.selector = selector;
            rule.style = style;
            rule.source = this.source;
            ruleset.add(rule);
        }
        return true;
    }

    public final Ruleset parseRuleset(CSSTextScanner cSSTextScanner) {
        Ruleset ruleset = new Ruleset();
        while (!cSSTextScanner.empty()) {
            try {
                if (!cSSTextScanner.consume("<!--") && !cSSTextScanner.consume("-->")) {
                    if (!cSSTextScanner.consume('@')) {
                        if (!parseRule(ruleset, cSSTextScanner)) {
                            break;
                        }
                    } else {
                        parseAtRule(ruleset, cSSTextScanner);
                    }
                }
            } catch (CSSParseException e) {
                Log.e("CSSParser", "CSS parser terminated early due to error: " + e.getMessage());
            }
        }
        return ruleset;
    }

    public static boolean ruleMatch(
            RuleMatchContext ruleMatchContext,
            Selector selector,
            SVG.SvgElementBase svgElementBase) {
        ArrayList arrayList = new ArrayList();
        Object obj = svgElementBase.parent;
        while (true) {
            if (obj == null) {
                break;
            }
            arrayList.add(0, obj);
            obj = ((SVG.SvgObject) obj).parent;
        }
        int size = arrayList.size() - 1;
        List list = selector.simpleSelectors;
        if ((list == null ? 0 : ((ArrayList) list).size()) == 1) {
            return selectorMatch(
                    ruleMatchContext,
                    (SimpleSelector) ((ArrayList) selector.simpleSelectors).get(0),
                    svgElementBase);
        }
        return ruleMatch(
                ruleMatchContext,
                selector,
                (selector.simpleSelectors != null ? ((ArrayList) r0).size() : 0) - 1,
                arrayList,
                size,
                svgElementBase);
    }
}
