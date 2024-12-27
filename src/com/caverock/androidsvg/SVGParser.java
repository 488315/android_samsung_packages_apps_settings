package com.caverock.androidsvg;

import android.graphics.Matrix;
import android.util.Log;
import android.util.Xml;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.compose.animation.AndroidFlingSpline$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ComposerKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;

import com.android.settings.wifi.tether.WifiHotspotSpeedSettings$$ExternalSyntheticOutline0;

import com.samsung.android.gtscell.data.FieldName;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import com.samsung.android.settings.accessibility.vision.routine.ReduceBrightnessRoutineActionHandler;
import com.sec.ims.configuration.DATA;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.DefaultHandler2;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SVGParser {
    public SVG.SvgContainer currentElement;
    public int ignoreDepth;
    public boolean ignoring;
    public boolean inMetadataElement;
    public boolean inStyleElement;
    public StringBuilder metadataElementContents;
    public SVGElem metadataTag;
    public StringBuilder styleElementContents;
    public SVG svgDocument;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class AspectRatioKeywords {
        public static final Map aspectRatioKeywords;

        static {
            HashMap hashMap = new HashMap(10);
            aspectRatioKeywords = hashMap;
            hashMap.put(SignalSeverity.NONE, PreserveAspectRatio.Alignment.none);
            hashMap.put("xMinYMin", PreserveAspectRatio.Alignment.xMinYMin);
            hashMap.put("xMidYMin", PreserveAspectRatio.Alignment.xMidYMin);
            hashMap.put("xMaxYMin", PreserveAspectRatio.Alignment.xMaxYMin);
            hashMap.put("xMinYMid", PreserveAspectRatio.Alignment.xMinYMid);
            hashMap.put("xMidYMid", PreserveAspectRatio.Alignment.xMidYMid);
            hashMap.put("xMaxYMid", PreserveAspectRatio.Alignment.xMaxYMid);
            hashMap.put("xMinYMax", PreserveAspectRatio.Alignment.xMinYMax);
            hashMap.put("xMidYMax", PreserveAspectRatio.Alignment.xMidYMax);
            hashMap.put("xMaxYMax", PreserveAspectRatio.Alignment.xMaxYMax);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class ColourKeywords {
        public static final Map colourKeywords;

        static {
            HashMap hashMap = new HashMap(47);
            colourKeywords = hashMap;
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -984833, hashMap, "aliceblue", -332841, "antiquewhite");
            hashMap.put("aqua", -16711681);
            hashMap.put("aquamarine", -8388652);
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -983041, hashMap, "azure", -657956, "beige");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -6972, hashMap, "bisque", EmergencyPhoneWidget.BG_COLOR, "black");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -5171, hashMap, "blanchedalmond", -16776961, "blue");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -7722014, hashMap, "blueviolet", -5952982, "brown");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -2180985, hashMap, "burlywood", -10510688, "cadetblue");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -8388864, hashMap, "chartreuse", -2987746, "chocolate");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -32944, hashMap, "coral", -10185235, "cornflowerblue");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -1828, hashMap, "cornsilk", -2354116, "crimson");
            hashMap.put("cyan", -16711681);
            hashMap.put("darkblue", -16777077);
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -16741493, hashMap, "darkcyan", -4684277, "darkgoldenrod");
            hashMap.put("darkgray", -5658199);
            hashMap.put("darkgreen", -16751616);
            hashMap.put("darkgrey", -5658199);
            hashMap.put("darkkhaki", -4343957);
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -7667573, hashMap, "darkmagenta", -11179217, "darkolivegreen");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -29696, hashMap, "darkorange", -6737204, "darkorchid");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -7667712, hashMap, "darkred", -1468806, "darksalmon");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -7357297, hashMap, "darkseagreen", -12042869, "darkslateblue");
            hashMap.put("darkslategray", -13676721);
            hashMap.put("darkslategrey", -13676721);
            hashMap.put("darkturquoise", -16724271);
            hashMap.put("darkviolet", -7077677);
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -60269, hashMap, "deeppink", -16728065, "deepskyblue");
            hashMap.put("dimgray", -9868951);
            hashMap.put("dimgrey", -9868951);
            hashMap.put("dodgerblue", -14774017);
            hashMap.put("firebrick", -5103070);
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -1296, hashMap, "floralwhite", -14513374, "forestgreen");
            hashMap.put("fuchsia", -65281);
            hashMap.put("gainsboro", -2302756);
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -460545, hashMap, "ghostwhite", -10496, "gold");
            hashMap.put("goldenrod", -2448096);
            hashMap.put("gray", -8355712);
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -16744448, hashMap, "green", -5374161, "greenyellow");
            hashMap.put("grey", -8355712);
            hashMap.put("honeydew", -983056);
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -38476, hashMap, "hotpink", -3318692, "indianred");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -11861886, hashMap, "indigo", -16, "ivory");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -989556, hashMap, "khaki", -1644806, "lavender");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -3851, hashMap, "lavenderblush", -8586240, "lawngreen");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -1331, hashMap, "lemonchiffon", -5383962, "lightblue");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -1015680, hashMap, "lightcoral", -2031617, "lightcyan");
            hashMap.put("lightgoldenrodyellow", -329006);
            hashMap.put("lightgray", -2894893);
            hashMap.put("lightgreen", -7278960);
            hashMap.put("lightgrey", -2894893);
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -18751, hashMap, "lightpink", -24454, "lightsalmon");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -14634326, hashMap, "lightseagreen", -7876870, "lightskyblue");
            hashMap.put("lightslategray", -8943463);
            hashMap.put("lightslategrey", -8943463);
            hashMap.put("lightsteelblue", -5192482);
            hashMap.put("lightyellow", -32);
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -16711936, hashMap, "lime", -13447886, "limegreen");
            hashMap.put("linen", -331546);
            hashMap.put("magenta", -65281);
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -8388608, hashMap, "maroon", -10039894, "mediumaquamarine");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -16777011, hashMap, "mediumblue", -4565549, "mediumorchid");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -7114533, hashMap, "mediumpurple", -12799119, "mediumseagreen");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -8689426, hashMap, "mediumslateblue", -16713062, "mediumspringgreen");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -12004916, hashMap, "mediumturquoise", -3730043, "mediumvioletred");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -15132304, hashMap, "midnightblue", -655366, "mintcream");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -6943, hashMap, "mistyrose", -6987, "moccasin");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -8531, hashMap, "navajowhite", -16777088, "navy");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -133658, hashMap, "oldlace", -8355840, "olive");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -9728477, hashMap, "olivedrab", -23296, "orange");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -47872, hashMap, "orangered", -2461482, "orchid");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -1120086, hashMap, "palegoldenrod", -6751336, "palegreen");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -5247250, hashMap, "paleturquoise", -2396013, "palevioletred");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -4139, hashMap, "papayawhip", -9543, "peachpuff");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -3308225, hashMap, "peru", -16181, "pink");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -2252579, hashMap, "plum", -5185306, "powderblue");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -8388480, hashMap, "purple", -10079335, "rebeccapurple");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -65536, hashMap, "red", -4419697, "rosybrown");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -12490271, hashMap, "royalblue", -7650029, "saddlebrown");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -360334, hashMap, "salmon", -744352, "sandybrown");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -13726889, hashMap, "seagreen", -2578, "seashell");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -6270419, hashMap, "sienna", -4144960, "silver");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -7876885, hashMap, "skyblue", -9807155, "slateblue");
            hashMap.put("slategray", -9404272);
            hashMap.put("slategrey", -9404272);
            hashMap.put("snow", -1286);
            hashMap.put("springgreen", -16711809);
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -12156236, hashMap, "steelblue", -2968436, "tan");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -16744320, hashMap, "teal", -2572328, "thistle");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -40121, hashMap, "tomato", -12525360, "turquoise");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -1146130, hashMap, "violet", -663885, "wheat");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -1, hashMap, "white", -657931, "whitesmoke");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    -256, hashMap, "yellow", -6632142, "yellowgreen");
            hashMap.put("transparent", 0);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class FontSizeKeywords {
        public static final Map fontSizeKeywords;

        static {
            HashMap hashMap = new HashMap(9);
            fontSizeKeywords = hashMap;
            SVG.Unit unit = SVG.Unit.pt;
            hashMap.put("xx-small", new SVG.Length(0.694f, unit));
            hashMap.put("x-small", new SVG.Length(0.833f, unit));
            hashMap.put("small", new SVG.Length(10.0f, unit));
            hashMap.put("medium", new SVG.Length(12.0f, unit));
            hashMap.put("large", new SVG.Length(14.4f, unit));
            hashMap.put("x-large", new SVG.Length(17.3f, unit));
            hashMap.put("xx-large", new SVG.Length(20.7f, unit));
            SVG.Unit unit2 = SVG.Unit.percent;
            hashMap.put("smaller", new SVG.Length(83.33f, unit2));
            hashMap.put("larger", new SVG.Length(120.0f, unit2));
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class FontWeightKeywords {
        public static final Map fontWeightKeywords;

        static {
            HashMap hashMap = new HashMap(13);
            fontWeightKeywords = hashMap;
            hashMap.put("normal", 400);
            Integer valueOf =
                    Integer.valueOf(KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED);
            hashMap.put("bold", valueOf);
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    1, hashMap, "bolder", -1, "lighter");
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                    100, hashMap, DATA.DM_FIELD_INDEX.UT_PDN, 200, "200");
            hashMap.put("300", 300);
            hashMap.put("400", 400);
            WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(500, hashMap, "500", 600, "600");
            hashMap.put("700", valueOf);
            hashMap.put("800", 800);
            hashMap.put(DATA.DM_FIELD_INDEX.SHOW_REG_INFO_TO_SETTING_APP, 900);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SAXHandler extends DefaultHandler2 {
        public SAXHandler() {}

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public final void characters(char[] cArr, int i, int i2) {
            SVGParser.this.text(new String(cArr, i, i2));
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public final void endDocument() {
            SVGParser.this.getClass();
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public final void endElement(String str, String str2, String str3) {
            SVGParser.this.endElement(str, str2, str3);
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public final void processingInstruction(String str, String str2) {
            TextScanner textScanner = new TextScanner(str2);
            SVGParser.this.getClass();
            SVGParser.parseProcessingInstructionAttributes(textScanner);
            SVGParser.this.getClass();
            str.equals("xml-stylesheet");
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public final void startDocument() {
            SVGParser.this.startDocument();
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public final void startElement(
                String str, String str2, String str3, Attributes attributes) {
            SVGParser.this.startElement(str, str2, str3, attributes);
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    final class SVGAttr {
        public static final SVGAttr CLASS = new SVGAttr("CLASS", 0);
        public static final SVGAttr clip = new SVGAttr("clip", 1);
        public static final SVGAttr clip_path = new SVGAttr("clip_path", 2);
        public static final SVGAttr clipPathUnits = new SVGAttr("clipPathUnits", 3);
        public static final SVGAttr clip_rule = new SVGAttr("clip_rule", 4);
        public static final SVGAttr color = new SVGAttr("color", 5);
        public static final SVGAttr cx = new SVGAttr("cx", 6);
        public static final SVGAttr cy = new SVGAttr("cy", 7);
        public static final SVGAttr direction = new SVGAttr("direction", 8);
        public static final SVGAttr dx = new SVGAttr("dx", 9);
        public static final SVGAttr dy = new SVGAttr("dy", 10);
        public static final SVGAttr fx = new SVGAttr("fx", 11);
        public static final SVGAttr fy = new SVGAttr("fy", 12);
        public static final SVGAttr d = new SVGAttr("d", 13);
        public static final SVGAttr display = new SVGAttr("display", 14);
        public static final SVGAttr fill = new SVGAttr("fill", 15);
        public static final SVGAttr fill_rule = new SVGAttr("fill_rule", 16);
        public static final SVGAttr fill_opacity = new SVGAttr("fill_opacity", 17);
        public static final SVGAttr font = new SVGAttr("font", 18);
        public static final SVGAttr font_family = new SVGAttr("font_family", 19);
        public static final SVGAttr font_size = new SVGAttr("font_size", 20);
        public static final SVGAttr font_weight = new SVGAttr("font_weight", 21);
        public static final SVGAttr font_style = new SVGAttr("font_style", 22);
        public static final SVGAttr gradientTransform = new SVGAttr("gradientTransform", 23);
        public static final SVGAttr gradientUnits = new SVGAttr("gradientUnits", 24);
        public static final SVGAttr height = new SVGAttr("height", 25);
        public static final SVGAttr href = new SVGAttr("href", 26);
        public static final SVGAttr image_rendering = new SVGAttr("image_rendering", 27);
        public static final SVGAttr marker = new SVGAttr("marker", 28);
        public static final SVGAttr marker_start = new SVGAttr("marker_start", 29);
        public static final SVGAttr marker_mid = new SVGAttr("marker_mid", 30);
        public static final SVGAttr marker_end = new SVGAttr("marker_end", 31);
        public static final SVGAttr markerHeight = new SVGAttr("markerHeight", 32);
        public static final SVGAttr markerUnits = new SVGAttr("markerUnits", 33);
        public static final SVGAttr markerWidth = new SVGAttr("markerWidth", 34);
        public static final SVGAttr mask = new SVGAttr("mask", 35);
        public static final SVGAttr maskContentUnits = new SVGAttr("maskContentUnits", 36);
        public static final SVGAttr maskUnits = new SVGAttr("maskUnits", 37);
        public static final SVGAttr media = new SVGAttr("media", 38);
        public static final SVGAttr offset = new SVGAttr("offset", 39);
        public static final SVGAttr opacity = new SVGAttr("opacity", 40);
        public static final SVGAttr orient = new SVGAttr("orient", 41);
        public static final SVGAttr overflow = new SVGAttr("overflow", 42);
        public static final SVGAttr pathLength = new SVGAttr("pathLength", 43);
        public static final SVGAttr patternContentUnits = new SVGAttr("patternContentUnits", 44);
        public static final SVGAttr patternTransform = new SVGAttr("patternTransform", 45);
        public static final SVGAttr patternUnits = new SVGAttr("patternUnits", 46);
        public static final SVGAttr points = new SVGAttr("points", 47);
        public static final SVGAttr preserveAspectRatio = new SVGAttr("preserveAspectRatio", 48);
        public static final SVGAttr r = new SVGAttr("r", 49);
        public static final SVGAttr refX = new SVGAttr("refX", 50);
        public static final SVGAttr refY = new SVGAttr("refY", 51);
        public static final SVGAttr requiredFeatures = new SVGAttr("requiredFeatures", 52);
        public static final SVGAttr requiredExtensions = new SVGAttr("requiredExtensions", 53);
        public static final SVGAttr requiredFormats = new SVGAttr("requiredFormats", 54);
        public static final SVGAttr requiredFonts = new SVGAttr("requiredFonts", 55);
        public static final SVGAttr rx = new SVGAttr("rx", 56);
        public static final SVGAttr ry = new SVGAttr("ry", 57);
        public static final SVGAttr solid_color = new SVGAttr("solid_color", 58);
        public static final SVGAttr solid_opacity = new SVGAttr("solid_opacity", 59);
        public static final SVGAttr spreadMethod = new SVGAttr("spreadMethod", 60);
        public static final SVGAttr startOffset = new SVGAttr("startOffset", 61);
        public static final SVGAttr stop_color = new SVGAttr("stop_color", 62);
        public static final SVGAttr stop_opacity = new SVGAttr("stop_opacity", 63);
        public static final SVGAttr stroke = new SVGAttr("stroke", 64);
        public static final SVGAttr stroke_dasharray = new SVGAttr("stroke_dasharray", 65);
        public static final SVGAttr stroke_dashoffset = new SVGAttr("stroke_dashoffset", 66);
        public static final SVGAttr stroke_linecap = new SVGAttr("stroke_linecap", 67);
        public static final SVGAttr stroke_linejoin = new SVGAttr("stroke_linejoin", 68);
        public static final SVGAttr stroke_miterlimit = new SVGAttr("stroke_miterlimit", 69);
        public static final SVGAttr stroke_opacity = new SVGAttr("stroke_opacity", 70);
        public static final SVGAttr stroke_width = new SVGAttr("stroke_width", 71);
        public static final SVGAttr style = new SVGAttr("style", 72);
        public static final SVGAttr systemLanguage = new SVGAttr("systemLanguage", 73);
        public static final SVGAttr text_anchor = new SVGAttr("text_anchor", 74);
        public static final SVGAttr text_decoration = new SVGAttr("text_decoration", 75);
        public static final SVGAttr transform = new SVGAttr("transform", 76);
        public static final SVGAttr type = new SVGAttr("type", 77);
        public static final SVGAttr vector_effect = new SVGAttr("vector_effect", 78);
        public static final SVGAttr version = new SVGAttr(FieldName.VERSION, 79);
        public static final SVGAttr viewBox = new SVGAttr("viewBox", 80);
        public static final SVGAttr width = new SVGAttr("width", 81);
        public static final SVGAttr x = new SVGAttr("x", 82);
        public static final SVGAttr y = new SVGAttr("y", 83);
        public static final SVGAttr x1 = new SVGAttr("x1", 84);
        public static final SVGAttr y1 = new SVGAttr("y1", 85);
        public static final SVGAttr x2 = new SVGAttr("x2", 86);
        public static final SVGAttr y2 = new SVGAttr("y2", 87);
        public static final SVGAttr viewport_fill = new SVGAttr("viewport_fill", 88);
        public static final SVGAttr viewport_fill_opacity =
                new SVGAttr("viewport_fill_opacity", 89);
        public static final SVGAttr visibility = new SVGAttr("visibility", 90);
        public static final SVGAttr UNSUPPORTED = new SVGAttr("UNSUPPORTED", 91);
        public static final /* synthetic */ SVGAttr[] $VALUES = {
            CLASS,
            clip,
            clip_path,
            clipPathUnits,
            clip_rule,
            color,
            cx,
            cy,
            direction,
            dx,
            dy,
            fx,
            fy,
            d,
            display,
            fill,
            fill_rule,
            fill_opacity,
            font,
            font_family,
            font_size,
            font_weight,
            font_style,
            gradientTransform,
            gradientUnits,
            height,
            href,
            image_rendering,
            marker,
            marker_start,
            marker_mid,
            marker_end,
            markerHeight,
            markerUnits,
            markerWidth,
            mask,
            maskContentUnits,
            maskUnits,
            media,
            offset,
            opacity,
            orient,
            overflow,
            pathLength,
            patternContentUnits,
            patternTransform,
            patternUnits,
            points,
            preserveAspectRatio,
            r,
            refX,
            refY,
            requiredFeatures,
            requiredExtensions,
            requiredFormats,
            requiredFonts,
            rx,
            ry,
            solid_color,
            solid_opacity,
            spreadMethod,
            startOffset,
            stop_color,
            stop_opacity,
            stroke,
            stroke_dasharray,
            stroke_dashoffset,
            stroke_linecap,
            stroke_linejoin,
            stroke_miterlimit,
            stroke_opacity,
            stroke_width,
            style,
            systemLanguage,
            text_anchor,
            text_decoration,
            transform,
            type,
            vector_effect,
            version,
            viewBox,
            width,
            x,
            y,
            x1,
            y1,
            x2,
            y2,
            viewport_fill,
            viewport_fill_opacity,
            visibility,
            UNSUPPORTED
        };
        public static final Map cache = new HashMap();

        static {
            for (SVGAttr sVGAttr : values()) {
                if (sVGAttr == CLASS) {
                    cache.put("class", sVGAttr);
                } else if (sVGAttr != UNSUPPORTED) {
                    cache.put(sVGAttr.name().replace('_', '-'), sVGAttr);
                }
            }
        }

        public static SVGAttr fromString(String str) {
            SVGAttr sVGAttr = (SVGAttr) ((HashMap) cache).get(str);
            return sVGAttr != null ? sVGAttr : UNSUPPORTED;
        }

        public static SVGAttr valueOf(String str) {
            return (SVGAttr) Enum.valueOf(SVGAttr.class, str);
        }

        public static SVGAttr[] values() {
            return (SVGAttr[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    final class SVGElem {
        public static final /* synthetic */ SVGElem[] $VALUES;
        public static final SVGElem SWITCH;
        public static final SVGElem UNSUPPORTED;
        public static final Map cache;
        public static final SVGElem desc;
        public static final SVGElem title;

        /* JADX INFO: Fake field, exist only in values array */
        SVGElem EF0;

        static {
            SVGElem sVGElem = new SVGElem("svg", 0);
            SVGElem sVGElem2 = new SVGElem("a", 1);
            SVGElem sVGElem3 = new SVGElem("circle", 2);
            SVGElem sVGElem4 = new SVGElem("clipPath", 3);
            SVGElem sVGElem5 = new SVGElem("defs", 4);
            SVGElem sVGElem6 = new SVGElem("desc", 5);
            desc = sVGElem6;
            SVGElem sVGElem7 = new SVGElem("ellipse", 6);
            SVGElem sVGElem8 = new SVGElem("g", 7);
            SVGElem sVGElem9 = new SVGElem("image", 8);
            SVGElem sVGElem10 = new SVGElem("line", 9);
            SVGElem sVGElem11 = new SVGElem("linearGradient", 10);
            SVGElem sVGElem12 = new SVGElem("marker", 11);
            SVGElem sVGElem13 = new SVGElem("mask", 12);
            SVGElem sVGElem14 = new SVGElem("path", 13);
            SVGElem sVGElem15 = new SVGElem("pattern", 14);
            SVGElem sVGElem16 = new SVGElem("polygon", 15);
            SVGElem sVGElem17 = new SVGElem("polyline", 16);
            SVGElem sVGElem18 = new SVGElem("radialGradient", 17);
            SVGElem sVGElem19 = new SVGElem("rect", 18);
            SVGElem sVGElem20 = new SVGElem("solidColor", 19);
            SVGElem sVGElem21 = new SVGElem("stop", 20);
            SVGElem sVGElem22 = new SVGElem("style", 21);
            SVGElem sVGElem23 = new SVGElem("SWITCH", 22);
            SWITCH = sVGElem23;
            SVGElem sVGElem24 = new SVGElem("symbol", 23);
            SVGElem sVGElem25 = new SVGElem("text", 24);
            SVGElem sVGElem26 = new SVGElem("textPath", 25);
            SVGElem sVGElem27 = new SVGElem(UniversalCredentialUtil.AGENT_TITLE, 26);
            title = sVGElem27;
            SVGElem sVGElem28 = new SVGElem("tref", 27);
            SVGElem sVGElem29 = new SVGElem("tspan", 28);
            SVGElem sVGElem30 = new SVGElem("use", 29);
            SVGElem sVGElem31 = new SVGElem("view", 30);
            SVGElem sVGElem32 = new SVGElem("UNSUPPORTED", 31);
            UNSUPPORTED = sVGElem32;
            $VALUES =
                    new SVGElem[] {
                        sVGElem, sVGElem2, sVGElem3, sVGElem4, sVGElem5, sVGElem6, sVGElem7,
                        sVGElem8, sVGElem9, sVGElem10, sVGElem11, sVGElem12, sVGElem13, sVGElem14,
                        sVGElem15, sVGElem16, sVGElem17, sVGElem18, sVGElem19, sVGElem20, sVGElem21,
                        sVGElem22, sVGElem23, sVGElem24, sVGElem25, sVGElem26, sVGElem27, sVGElem28,
                        sVGElem29, sVGElem30, sVGElem31, sVGElem32
                    };
            cache = new HashMap();
            for (SVGElem sVGElem33 : values()) {
                if (sVGElem33 == SWITCH) {
                    ((HashMap) cache)
                            .put(ReduceBrightnessRoutineActionHandler.KEY_SWITCH, sVGElem33);
                } else if (sVGElem33 != UNSUPPORTED) {
                    ((HashMap) cache).put(sVGElem33.name(), sVGElem33);
                }
            }
        }

        public static SVGElem valueOf(String str) {
            return (SVGElem) Enum.valueOf(SVGElem.class, str);
        }

        public static SVGElem[] values() {
            return (SVGElem[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class TextScanner {
        public final String input;
        public final int inputLength;
        public int position = 0;
        public final NumberParser numberParser = new NumberParser();

        public TextScanner(String str) {
            this.inputLength = 0;
            String trim = str.trim();
            this.input = trim;
            this.inputLength = trim.length();
        }

        public static boolean isWhitespace(int i) {
            return i == 32 || i == 10 || i == 13 || i == 9;
        }

        public final int advanceChar() {
            int i = this.position;
            int i2 = this.inputLength;
            if (i == i2) {
                return -1;
            }
            int i3 = i + 1;
            this.position = i3;
            if (i3 < i2) {
                return this.input.charAt(i3);
            }
            return -1;
        }

        public final Boolean checkedNextFlag(Object obj) {
            if (obj == null) {
                return null;
            }
            skipCommaWhitespace();
            int i = this.position;
            if (i == this.inputLength) {
                return null;
            }
            char charAt = this.input.charAt(i);
            if (charAt != '0' && charAt != '1') {
                return null;
            }
            this.position++;
            return Boolean.valueOf(charAt == '1');
        }

        public final float checkedNextFloat(float f) {
            if (Float.isNaN(f)) {
                return Float.NaN;
            }
            skipCommaWhitespace();
            return nextFloat();
        }

        public final boolean consume(char c) {
            int i = this.position;
            boolean z = i < this.inputLength && this.input.charAt(i) == c;
            if (z) {
                this.position++;
            }
            return z;
        }

        public final boolean empty() {
            return this.position == this.inputLength;
        }

        public final Integer nextChar() {
            int i = this.position;
            if (i == this.inputLength) {
                return null;
            }
            this.position = i + 1;
            return Integer.valueOf(this.input.charAt(i));
        }

        public final float nextFloat() {
            int i = this.position;
            int i2 = this.inputLength;
            NumberParser numberParser = this.numberParser;
            float parseNumber = numberParser.parseNumber(i, i2, this.input);
            if (!Float.isNaN(parseNumber)) {
                this.position = numberParser.pos;
            }
            return parseNumber;
        }

        public final SVG.Length nextLength() {
            float nextFloat = nextFloat();
            if (Float.isNaN(nextFloat)) {
                return null;
            }
            SVG.Unit nextUnit = nextUnit();
            return nextUnit == null
                    ? new SVG.Length(nextFloat, SVG.Unit.px)
                    : new SVG.Length(nextFloat, nextUnit);
        }

        public final String nextQuotedString() {
            if (empty()) {
                return null;
            }
            int i = this.position;
            String str = this.input;
            char charAt = str.charAt(i);
            if (charAt != '\'' && charAt != '\"') {
                return null;
            }
            int advanceChar = advanceChar();
            while (advanceChar != -1 && advanceChar != charAt) {
                advanceChar = advanceChar();
            }
            if (advanceChar == -1) {
                this.position = i;
                return null;
            }
            int i2 = this.position;
            this.position = i2 + 1;
            return str.substring(i + 1, i2);
        }

        public final String nextToken() {
            return nextToken(' ', false);
        }

        public final SVG.Unit nextUnit() {
            if (empty()) {
                return null;
            }
            int i = this.position;
            String str = this.input;
            if (str.charAt(i) == '%') {
                this.position++;
                return SVG.Unit.percent;
            }
            int i2 = this.position;
            if (i2 > this.inputLength - 2) {
                return null;
            }
            try {
                SVG.Unit valueOf =
                        SVG.Unit.valueOf(str.substring(i2, i2 + 2).toLowerCase(Locale.US));
                this.position += 2;
                return valueOf;
            } catch (IllegalArgumentException unused) {
                return null;
            }
        }

        public final float possibleNextFloat() {
            skipCommaWhitespace();
            int i = this.position;
            int i2 = this.inputLength;
            NumberParser numberParser = this.numberParser;
            float parseNumber = numberParser.parseNumber(i, i2, this.input);
            if (!Float.isNaN(parseNumber)) {
                this.position = numberParser.pos;
            }
            return parseNumber;
        }

        public final boolean skipCommaWhitespace() {
            skipWhitespace();
            int i = this.position;
            if (i == this.inputLength || this.input.charAt(i) != ',') {
                return false;
            }
            this.position++;
            skipWhitespace();
            return true;
        }

        public final void skipWhitespace() {
            while (true) {
                int i = this.position;
                if (i >= this.inputLength || !isWhitespace(this.input.charAt(i))) {
                    return;
                } else {
                    this.position++;
                }
            }
        }

        public final String nextToken(char c, boolean z) {
            if (empty()) {
                return null;
            }
            int i = this.position;
            String str = this.input;
            char charAt = str.charAt(i);
            if ((!z && isWhitespace(charAt)) || charAt == c) {
                return null;
            }
            int i2 = this.position;
            int advanceChar = advanceChar();
            while (advanceChar != -1 && advanceChar != c && (z || !isWhitespace(advanceChar))) {
                advanceChar = advanceChar();
            }
            return str.substring(i2, this.position);
        }

        public final boolean consume(String str) {
            int length = str.length();
            int i = this.position;
            boolean z =
                    i <= this.inputLength - length
                            && this.input.substring(i, i + length).equals(str);
            if (z) {
                this.position += length;
            }
            return z;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class XPPAttributesWrapper implements Attributes {
        public XmlPullParser parser;

        @Override // org.xml.sax.Attributes
        public final int getIndex(String str) {
            return -1;
        }

        @Override // org.xml.sax.Attributes
        public final int getLength() {
            return this.parser.getAttributeCount();
        }

        @Override // org.xml.sax.Attributes
        public final String getLocalName(int i) {
            return this.parser.getAttributeName(i);
        }

        @Override // org.xml.sax.Attributes
        public final String getQName(int i) {
            String attributeName = this.parser.getAttributeName(i);
            if (this.parser.getAttributePrefix(i) == null) {
                return attributeName;
            }
            return this.parser.getAttributePrefix(i) + ':' + attributeName;
        }

        @Override // org.xml.sax.Attributes
        public final String getType(int i) {
            return null;
        }

        @Override // org.xml.sax.Attributes
        public final String getURI(int i) {
            return this.parser.getAttributeNamespace(i);
        }

        @Override // org.xml.sax.Attributes
        public final String getValue(String str) {
            return null;
        }

        @Override // org.xml.sax.Attributes
        public final int getIndex(String str, String str2) {
            return -1;
        }

        @Override // org.xml.sax.Attributes
        public final String getType(String str) {
            return null;
        }

        @Override // org.xml.sax.Attributes
        public final String getValue(String str, String str2) {
            return null;
        }

        @Override // org.xml.sax.Attributes
        public final String getType(String str, String str2) {
            return null;
        }

        @Override // org.xml.sax.Attributes
        public final String getValue(int i) {
            return this.parser.getAttributeValue(i);
        }
    }

    public static int clamp255(float f) {
        if (f < 0.0f) {
            return 0;
        }
        if (f > 255.0f) {
            return 255;
        }
        return Math.round(f);
    }

    public static int hslToRgb(float f, float f2, float f3) {
        float f4 = f % 360.0f;
        if (f < 0.0f) {
            f4 += 360.0f;
        }
        float f5 = f4 / 60.0f;
        float f6 = f2 / 100.0f;
        float f7 = f3 / 100.0f;
        if (f6 < 0.0f) {
            f6 = 0.0f;
        } else if (f6 > 1.0f) {
            f6 = 1.0f;
        }
        float f8 = f7 >= 0.0f ? f7 > 1.0f ? 1.0f : f7 : 0.0f;
        float f9 = f8 <= 0.5f ? (f6 + 1.0f) * f8 : (f8 + f6) - (f6 * f8);
        float f10 = (f8 * 2.0f) - f9;
        return clamp255(hueToRgb(f10, f9, f5 - 2.0f) * 256.0f)
                | (clamp255(hueToRgb(f10, f9, f5 + 2.0f) * 256.0f) << 16)
                | (clamp255(hueToRgb(f10, f9, f5) * 256.0f) << 8);
    }

    public static float hueToRgb(float f, float f2, float f3) {
        if (f3 < 0.0f) {
            f3 += 6.0f;
        }
        if (f3 >= 6.0f) {
            f3 -= 6.0f;
        }
        return f3 < 1.0f
                ? AndroidFlingSpline$$ExternalSyntheticOutline0.m(f2, f, f3, f)
                : f3 < 3.0f
                        ? f2
                        : f3 < 4.0f
                                ? AndroidFlingSpline$$ExternalSyntheticOutline0.m(
                                        4.0f, f3, f2 - f, f)
                                : f;
    }

    public static void parseAttributesConditional(
            SVG.SvgConditional svgConditional, Attributes attributes) {
        for (int i = 0; i < attributes.getLength(); i++) {
            String trim = attributes.getValue(i).trim();
            int ordinal = SVGAttr.fromString(attributes.getLocalName(i)).ordinal();
            if (ordinal != 73) {
                switch (ordinal) {
                    case 52:
                        TextScanner textScanner = new TextScanner(trim);
                        HashSet hashSet = new HashSet();
                        while (!textScanner.empty()) {
                            String nextToken = textScanner.nextToken();
                            if (nextToken.startsWith("http://www.w3.org/TR/SVG11/feature#")) {
                                hashSet.add(nextToken.substring(35));
                            } else {
                                hashSet.add("UNSUPPORTED");
                            }
                            textScanner.skipWhitespace();
                        }
                        svgConditional.setRequiredFeatures(hashSet);
                        break;
                    case 53:
                        svgConditional.setRequiredExtensions(trim);
                        break;
                    case 54:
                        TextScanner textScanner2 = new TextScanner(trim);
                        HashSet hashSet2 = new HashSet();
                        while (!textScanner2.empty()) {
                            hashSet2.add(textScanner2.nextToken());
                            textScanner2.skipWhitespace();
                        }
                        svgConditional.setRequiredFormats(hashSet2);
                        break;
                    case 55:
                        List parseFontFamily = parseFontFamily(trim);
                        svgConditional.setRequiredFonts(
                                parseFontFamily != null
                                        ? new HashSet(parseFontFamily)
                                        : new HashSet(0));
                        break;
                }
            } else {
                TextScanner textScanner3 = new TextScanner(trim);
                HashSet hashSet3 = new HashSet();
                while (!textScanner3.empty()) {
                    String nextToken2 = textScanner3.nextToken();
                    int indexOf = nextToken2.indexOf(45);
                    if (indexOf != -1) {
                        nextToken2 = nextToken2.substring(0, indexOf);
                    }
                    hashSet3.add(
                            new Locale(nextToken2, ApnSettings.MVNO_NONE, ApnSettings.MVNO_NONE)
                                    .getLanguage());
                    textScanner3.skipWhitespace();
                }
                svgConditional.setSystemLanguage(hashSet3);
            }
        }
    }

    public static void parseAttributesCore(
            SVG.SvgElementBase svgElementBase, Attributes attributes) {
        for (int i = 0; i < attributes.getLength(); i++) {
            String qName = attributes.getQName(i);
            if (qName.equals("id") || qName.equals("xml:id")) {
                svgElementBase.id = attributes.getValue(i).trim();
                return;
            }
            if (qName.equals("xml:space")) {
                String trim = attributes.getValue(i).trim();
                if ("default".equals(trim)) {
                    svgElementBase.spacePreserve = Boolean.FALSE;
                    return;
                } else {
                    if (!"preserve".equals(trim)) {
                        throw new SVGParseException(
                                AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                                        "Invalid value for \"xml:space\" attribute: ", trim));
                    }
                    svgElementBase.spacePreserve = Boolean.TRUE;
                    return;
                }
            }
        }
    }

    public static void parseAttributesGradient(
            SVG.GradientElement gradientElement, Attributes attributes) {
        for (int i = 0; i < attributes.getLength(); i++) {
            String trim = attributes.getValue(i).trim();
            int ordinal = SVGAttr.fromString(attributes.getLocalName(i)).ordinal();
            if (ordinal == 23) {
                gradientElement.gradientTransform = parseTransformList(trim);
            } else if (ordinal != 24) {
                if (ordinal != 26) {
                    if (ordinal != 60) {
                        continue;
                    } else {
                        try {
                            gradientElement.spreadMethod = SVG.GradientSpread.valueOf(trim);
                        } catch (IllegalArgumentException unused) {
                            throw new SVGParseException(
                                    ComposerKt$$ExternalSyntheticOutline0.m(
                                            "Invalid spreadMethod attribute. \"",
                                            trim,
                                            "\" is not a valid value."));
                        }
                    }
                } else if (ApnSettings.MVNO_NONE.equals(attributes.getURI(i))
                        || "http://www.w3.org/1999/xlink".equals(attributes.getURI(i))) {
                    gradientElement.href = trim;
                }
            } else if ("objectBoundingBox".equals(trim)) {
                gradientElement.gradientUnitsAreUser = Boolean.FALSE;
            } else {
                if (!"userSpaceOnUse".equals(trim)) {
                    throw new SVGParseException("Invalid value for attribute gradientUnits");
                }
                gradientElement.gradientUnitsAreUser = Boolean.TRUE;
            }
        }
    }

    public static void parseAttributesPolyLine(
            SVG.PolyLine polyLine, Attributes attributes, String str) {
        for (int i = 0; i < attributes.getLength(); i++) {
            if (SVGAttr.fromString(attributes.getLocalName(i)) == SVGAttr.points) {
                TextScanner textScanner = new TextScanner(attributes.getValue(i));
                ArrayList arrayList = new ArrayList();
                textScanner.skipWhitespace();
                while (!textScanner.empty()) {
                    float nextFloat = textScanner.nextFloat();
                    if (Float.isNaN(nextFloat)) {
                        throw new SVGParseException(
                                ComposerKt$$ExternalSyntheticOutline0.m(
                                        "Invalid <",
                                        str,
                                        "> points attribute. Non-coordinate content found in"
                                            + " list."));
                    }
                    textScanner.skipCommaWhitespace();
                    float nextFloat2 = textScanner.nextFloat();
                    if (Float.isNaN(nextFloat2)) {
                        throw new SVGParseException(
                                ComposerKt$$ExternalSyntheticOutline0.m(
                                        "Invalid <",
                                        str,
                                        "> points attribute. There should be an even number of"
                                            + " coordinates."));
                    }
                    textScanner.skipCommaWhitespace();
                    arrayList.add(Float.valueOf(nextFloat));
                    arrayList.add(Float.valueOf(nextFloat2));
                }
                polyLine.points = new float[arrayList.size()];
                Iterator it = arrayList.iterator();
                int i2 = 0;
                while (it.hasNext()) {
                    polyLine.points[i2] = ((Float) it.next()).floatValue();
                    i2++;
                }
            }
        }
    }

    public static void parseAttributesStyle(
            SVG.SvgElementBase svgElementBase, Attributes attributes) {
        for (int i = 0; i < attributes.getLength(); i++) {
            String trim = attributes.getValue(i).trim();
            if (trim.length() != 0) {
                int ordinal = SVGAttr.fromString(attributes.getLocalName(i)).ordinal();
                if (ordinal == 0) {
                    CSSParser.CSSTextScanner cSSTextScanner = new CSSParser.CSSTextScanner(trim);
                    ArrayList arrayList = null;
                    while (!cSSTextScanner.empty()) {
                        String nextToken = cSSTextScanner.nextToken();
                        if (nextToken != null) {
                            if (arrayList == null) {
                                arrayList = new ArrayList();
                            }
                            arrayList.add(nextToken);
                            cSSTextScanner.skipWhitespace();
                        }
                    }
                    svgElementBase.classNames = arrayList;
                } else if (ordinal != 72) {
                    if (svgElementBase.baseStyle == null) {
                        svgElementBase.baseStyle = new SVG.Style();
                    }
                    processStyleProperty(
                            svgElementBase.baseStyle,
                            attributes.getLocalName(i),
                            attributes.getValue(i).trim());
                } else {
                    TextScanner textScanner =
                            new TextScanner(trim.replaceAll("/\\*.*?\\*/", ApnSettings.MVNO_NONE));
                    while (true) {
                        String nextToken2 = textScanner.nextToken(':', false);
                        textScanner.skipWhitespace();
                        if (!textScanner.consume(':')) {
                            break;
                        }
                        textScanner.skipWhitespace();
                        String nextToken3 = textScanner.nextToken(';', true);
                        if (nextToken3 == null) {
                            break;
                        }
                        textScanner.skipWhitespace();
                        if (textScanner.empty() || textScanner.consume(';')) {
                            if (svgElementBase.style == null) {
                                svgElementBase.style = new SVG.Style();
                            }
                            processStyleProperty(svgElementBase.style, nextToken2, nextToken3);
                            textScanner.skipWhitespace();
                        }
                    }
                }
            }
        }
    }

    public static void parseAttributesTextPosition(
            SVG.TextPositionedContainer textPositionedContainer, Attributes attributes) {
        for (int i = 0; i < attributes.getLength(); i++) {
            String trim = attributes.getValue(i).trim();
            int ordinal = SVGAttr.fromString(attributes.getLocalName(i)).ordinal();
            if (ordinal == 9) {
                textPositionedContainer.dx = parseLengthList(trim);
            } else if (ordinal == 10) {
                textPositionedContainer.dy = parseLengthList(trim);
            } else if (ordinal == 82) {
                textPositionedContainer.x = parseLengthList(trim);
            } else if (ordinal == 83) {
                textPositionedContainer.y = parseLengthList(trim);
            }
        }
    }

    public static void parseAttributesTransform(
            SVG.HasTransform hasTransform, Attributes attributes) {
        for (int i = 0; i < attributes.getLength(); i++) {
            if (SVGAttr.fromString(attributes.getLocalName(i)) == SVGAttr.transform) {
                hasTransform.setTransform(parseTransformList(attributes.getValue(i)));
            }
        }
    }

    public static void parseAttributesViewBox(
            SVG.SvgViewBoxContainer svgViewBoxContainer, Attributes attributes) {
        for (int i = 0; i < attributes.getLength(); i++) {
            String trim = attributes.getValue(i).trim();
            int ordinal = SVGAttr.fromString(attributes.getLocalName(i)).ordinal();
            if (ordinal == 48) {
                parsePreserveAspectRatio(svgViewBoxContainer, trim);
            } else if (ordinal != 80) {
                continue;
            } else {
                TextScanner textScanner = new TextScanner(trim);
                textScanner.skipWhitespace();
                float nextFloat = textScanner.nextFloat();
                textScanner.skipCommaWhitespace();
                float nextFloat2 = textScanner.nextFloat();
                textScanner.skipCommaWhitespace();
                float nextFloat3 = textScanner.nextFloat();
                textScanner.skipCommaWhitespace();
                float nextFloat4 = textScanner.nextFloat();
                if (Float.isNaN(nextFloat)
                        || Float.isNaN(nextFloat2)
                        || Float.isNaN(nextFloat3)
                        || Float.isNaN(nextFloat4)) {
                    throw new SVGParseException(
                            "Invalid viewBox definition - should have four numbers");
                }
                if (nextFloat3 < 0.0f) {
                    throw new SVGParseException("Invalid viewBox. width cannot be negative");
                }
                if (nextFloat4 < 0.0f) {
                    throw new SVGParseException("Invalid viewBox. height cannot be negative");
                }
                svgViewBoxContainer.viewBox =
                        new SVG.Box(nextFloat, nextFloat2, nextFloat3, nextFloat4);
            }
        }
    }

    public static SVG.Colour parseColour(String str) {
        long j;
        int i;
        if (str.charAt(0) == '#') {
            int length = str.length();
            IntegerParser integerParser = null;
            if (1 < length) {
                long j2 = 0;
                int i2 = 1;
                while (i2 < length) {
                    char charAt = str.charAt(i2);
                    if (charAt < '0' || charAt > '9') {
                        if (charAt >= 'A' && charAt <= 'F') {
                            j = j2 * 16;
                            i = charAt - 'A';
                        } else {
                            if (charAt < 'a' || charAt > 'f') {
                                break;
                            }
                            j = j2 * 16;
                            i = charAt - 'a';
                        }
                        j2 = j + i + 10;
                    } else {
                        j2 = (j2 * 16) + (charAt - '0');
                    }
                    if (j2 > 4294967295L) {
                        break;
                    }
                    i2++;
                }
                if (i2 != 1) {
                    integerParser = new IntegerParser(i2, j2);
                }
            }
            if (integerParser == null) {
                throw new SVGParseException("Bad hex colour value: ".concat(str));
            }
            long j3 = integerParser.value;
            int i3 = integerParser.pos;
            if (i3 == 4) {
                int i4 = (int) j3;
                int i5 = i4 & 3840;
                int i6 = i4 & IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp;
                int i7 = i4 & 15;
                return new SVG.Colour(
                        i7
                                | (i5 << 8)
                                | (-16777216)
                                | (i5 << 12)
                                | (i6 << 8)
                                | (i6 << 4)
                                | (i7 << 4));
            }
            if (i3 != 5) {
                if (i3 == 7) {
                    return new SVG.Colour(((int) j3) | EmergencyPhoneWidget.BG_COLOR);
                }
                if (i3 == 9) {
                    return new SVG.Colour((((int) j3) << 24) | (((int) j3) >>> 8));
                }
                throw new SVGParseException("Bad hex colour value: ".concat(str));
            }
            int i8 = (int) j3;
            int i9 = 61440 & i8;
            int i10 = i8 & 3840;
            int i11 = i8 & IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp;
            int i12 = i8 & 15;
            return new SVG.Colour(
                    (i12 << 24)
                            | (i12 << 28)
                            | (i9 << 8)
                            | (i9 << 4)
                            | (i10 << 4)
                            | i10
                            | i11
                            | (i11 >> 4));
        }
        String lowerCase = str.toLowerCase(Locale.US);
        boolean startsWith = lowerCase.startsWith("rgba(");
        if (startsWith || lowerCase.startsWith("rgb(")) {
            TextScanner textScanner = new TextScanner(str.substring(startsWith ? 5 : 4));
            textScanner.skipWhitespace();
            float nextFloat = textScanner.nextFloat();
            if (!Float.isNaN(nextFloat) && textScanner.consume('%')) {
                nextFloat = (nextFloat * 256.0f) / 100.0f;
            }
            float checkedNextFloat = textScanner.checkedNextFloat(nextFloat);
            if (!Float.isNaN(checkedNextFloat) && textScanner.consume('%')) {
                checkedNextFloat = (checkedNextFloat * 256.0f) / 100.0f;
            }
            float checkedNextFloat2 = textScanner.checkedNextFloat(checkedNextFloat);
            if (!Float.isNaN(checkedNextFloat2) && textScanner.consume('%')) {
                checkedNextFloat2 = (checkedNextFloat2 * 256.0f) / 100.0f;
            }
            if (!startsWith) {
                textScanner.skipWhitespace();
                if (Float.isNaN(checkedNextFloat2) || !textScanner.consume(')')) {
                    throw new SVGParseException("Bad rgb() colour value: ".concat(str));
                }
                return new SVG.Colour(
                        (clamp255(nextFloat) << 16)
                                | EmergencyPhoneWidget.BG_COLOR
                                | (clamp255(checkedNextFloat) << 8)
                                | clamp255(checkedNextFloat2));
            }
            float checkedNextFloat3 = textScanner.checkedNextFloat(checkedNextFloat2);
            textScanner.skipWhitespace();
            if (Float.isNaN(checkedNextFloat3) || !textScanner.consume(')')) {
                throw new SVGParseException("Bad rgba() colour value: ".concat(str));
            }
            return new SVG.Colour(
                    (clamp255(checkedNextFloat3 * 256.0f) << 24)
                            | (clamp255(nextFloat) << 16)
                            | (clamp255(checkedNextFloat) << 8)
                            | clamp255(checkedNextFloat2));
        }
        boolean startsWith2 = lowerCase.startsWith("hsla(");
        if (!startsWith2 && !lowerCase.startsWith("hsl(")) {
            Integer num = (Integer) ((HashMap) ColourKeywords.colourKeywords).get(lowerCase);
            if (num != null) {
                return new SVG.Colour(num.intValue());
            }
            throw new SVGParseException("Invalid colour keyword: ".concat(lowerCase));
        }
        TextScanner textScanner2 = new TextScanner(str.substring(startsWith2 ? 5 : 4));
        textScanner2.skipWhitespace();
        float nextFloat2 = textScanner2.nextFloat();
        float checkedNextFloat4 = textScanner2.checkedNextFloat(nextFloat2);
        if (!Float.isNaN(checkedNextFloat4)) {
            textScanner2.consume('%');
        }
        float checkedNextFloat5 = textScanner2.checkedNextFloat(checkedNextFloat4);
        if (!Float.isNaN(checkedNextFloat5)) {
            textScanner2.consume('%');
        }
        if (!startsWith2) {
            textScanner2.skipWhitespace();
            if (Float.isNaN(checkedNextFloat5) || !textScanner2.consume(')')) {
                throw new SVGParseException("Bad hsl() colour value: ".concat(str));
            }
            return new SVG.Colour(
                    hslToRgb(nextFloat2, checkedNextFloat4, checkedNextFloat5)
                            | EmergencyPhoneWidget.BG_COLOR);
        }
        float checkedNextFloat6 = textScanner2.checkedNextFloat(checkedNextFloat5);
        textScanner2.skipWhitespace();
        if (Float.isNaN(checkedNextFloat6) || !textScanner2.consume(')')) {
            throw new SVGParseException("Bad hsla() colour value: ".concat(str));
        }
        return new SVG.Colour(
                (clamp255(checkedNextFloat6 * 256.0f) << 24)
                        | hslToRgb(nextFloat2, checkedNextFloat4, checkedNextFloat5));
    }

    public static float parseFloat(String str) {
        int length = str.length();
        if (length != 0) {
            return parseFloat(length, str);
        }
        throw new SVGParseException("Invalid float value (empty string)");
    }

    public static List parseFontFamily(String str) {
        TextScanner textScanner = new TextScanner(str);
        ArrayList arrayList = null;
        do {
            String nextQuotedString = textScanner.nextQuotedString();
            if (nextQuotedString == null) {
                nextQuotedString = textScanner.nextToken(',', true);
            }
            if (nextQuotedString == null) {
                break;
            }
            if (arrayList == null) {
                arrayList = new ArrayList();
            }
            arrayList.add(nextQuotedString);
            textScanner.skipCommaWhitespace();
        } while (!textScanner.empty());
        return arrayList;
    }

    public static String parseFunctionalIRI(String str) {
        if (!str.equals(SignalSeverity.NONE) && str.startsWith("url(")) {
            return str.endsWith(")")
                    ? str.substring(4, str.length() - 1).trim()
                    : str.substring(4).trim();
        }
        return null;
    }

    public static SVG.Length parseLength(String str) {
        if (str.length() == 0) {
            throw new SVGParseException("Invalid length value (empty string)");
        }
        int length = str.length();
        SVG.Unit unit = SVG.Unit.px;
        char charAt = str.charAt(length - 1);
        if (charAt == '%') {
            length--;
            unit = SVG.Unit.percent;
        } else if (length > 2
                && Character.isLetter(charAt)
                && Character.isLetter(str.charAt(length - 2))) {
            length -= 2;
            try {
                unit = SVG.Unit.valueOf(str.substring(length).toLowerCase(Locale.US));
            } catch (IllegalArgumentException unused) {
                throw new SVGParseException("Invalid length unit specifier: ".concat(str));
            }
        }
        try {
            return new SVG.Length(parseFloat(length, str), unit);
        } catch (NumberFormatException e) {
            throw new SVGParseException("Invalid length value: ".concat(str), e);
        }
    }

    public static List parseLengthList(String str) {
        String str2;
        if (str.length() == 0) {
            throw new SVGParseException("Invalid length list (empty string)");
        }
        ArrayList arrayList = new ArrayList(1);
        TextScanner textScanner = new TextScanner(str);
        textScanner.skipWhitespace();
        while (!textScanner.empty()) {
            float nextFloat = textScanner.nextFloat();
            if (Float.isNaN(nextFloat)) {
                StringBuilder sb = new StringBuilder("Invalid length list value: ");
                int i = textScanner.position;
                while (true) {
                    boolean empty = textScanner.empty();
                    str2 = textScanner.input;
                    if (empty || TextScanner.isWhitespace(str2.charAt(textScanner.position))) {
                        break;
                    }
                    textScanner.position++;
                }
                String substring = str2.substring(i, textScanner.position);
                textScanner.position = i;
                sb.append(substring);
                throw new SVGParseException(sb.toString());
            }
            SVG.Unit nextUnit = textScanner.nextUnit();
            if (nextUnit == null) {
                nextUnit = SVG.Unit.px;
            }
            arrayList.add(new SVG.Length(nextFloat, nextUnit));
            textScanner.skipCommaWhitespace();
        }
        return arrayList;
    }

    public static SVG.Length parseLengthOrAuto(TextScanner textScanner) {
        return textScanner.consume("auto") ? new SVG.Length(0.0f) : textScanner.nextLength();
    }

    public static Float parseOpacity(String str) {
        try {
            float parseFloat = parseFloat(str);
            float f = 0.0f;
            if (parseFloat >= 0.0f) {
                f = 1.0f;
                if (parseFloat > 1.0f) {}
                return Float.valueOf(parseFloat);
            }
            parseFloat = f;
            return Float.valueOf(parseFloat);
        } catch (SVGParseException unused) {
            return null;
        }
    }

    public static SVG.SvgPaint parsePaintSpecifier(String str) {
        SVG.SvgPaint svgPaint = null;
        if (!str.startsWith("url(")) {
            if (str.equals(SignalSeverity.NONE)) {
                return SVG.Colour.TRANSPARENT;
            }
            if (str.equals("currentColor")) {
                return SVG.CurrentColor.instance;
            }
            try {
                return parseColour(str);
            } catch (SVGParseException unused) {
                return null;
            }
        }
        int indexOf = str.indexOf(")");
        if (indexOf == -1) {
            return new SVG.PaintReference(str.substring(4).trim(), null);
        }
        String trim = str.substring(4, indexOf).trim();
        String trim2 = str.substring(indexOf + 1).trim();
        if (trim2.length() > 0) {
            if (trim2.equals(SignalSeverity.NONE)) {
                svgPaint = SVG.Colour.TRANSPARENT;
            } else if (trim2.equals("currentColor")) {
                svgPaint = SVG.CurrentColor.instance;
            } else {
                try {
                    svgPaint = parseColour(trim2);
                } catch (SVGParseException unused2) {
                }
            }
        }
        return new SVG.PaintReference(trim, svgPaint);
    }

    public static void parsePreserveAspectRatio(
            SVG.SvgPreserveAspectRatioContainer svgPreserveAspectRatioContainer, String str) {
        PreserveAspectRatio.Scale scale;
        TextScanner textScanner = new TextScanner(str);
        textScanner.skipWhitespace();
        String nextToken = textScanner.nextToken();
        if ("defer".equals(nextToken)) {
            textScanner.skipWhitespace();
            nextToken = textScanner.nextToken();
        }
        PreserveAspectRatio.Alignment alignment =
                (PreserveAspectRatio.Alignment)
                        ((HashMap) AspectRatioKeywords.aspectRatioKeywords).get(nextToken);
        textScanner.skipWhitespace();
        if (textScanner.empty()) {
            scale = null;
        } else {
            String nextToken2 = textScanner.nextToken();
            nextToken2.getClass();
            if (nextToken2.equals("meet")) {
                scale = PreserveAspectRatio.Scale.meet;
            } else {
                if (!nextToken2.equals("slice")) {
                    throw new SVGParseException(
                            "Invalid preserveAspectRatio definition: ".concat(str));
                }
                scale = PreserveAspectRatio.Scale.slice;
            }
        }
        svgPreserveAspectRatioContainer.preserveAspectRatio =
                new PreserveAspectRatio(alignment, scale);
    }

    public static Map parseProcessingInstructionAttributes(TextScanner textScanner) {
        HashMap hashMap = new HashMap();
        textScanner.skipWhitespace();
        String nextToken = textScanner.nextToken('=', false);
        while (nextToken != null) {
            textScanner.consume('=');
            hashMap.put(nextToken, textScanner.nextQuotedString());
            textScanner.skipWhitespace();
            nextToken = textScanner.nextToken('=', false);
        }
        return hashMap;
    }

    public static Matrix parseTransformList(String str) {
        Matrix matrix = new Matrix();
        TextScanner textScanner = new TextScanner(str);
        textScanner.skipWhitespace();
        while (!textScanner.empty()) {
            String str2 = null;
            if (!textScanner.empty()) {
                int i = textScanner.position;
                String str3 = textScanner.input;
                int charAt = str3.charAt(i);
                while (true) {
                    if ((charAt >= 97 && charAt <= 122) || (charAt >= 65 && charAt <= 90)) {
                        charAt = textScanner.advanceChar();
                    }
                }
                int i2 = textScanner.position;
                while (TextScanner.isWhitespace(charAt)) {
                    charAt = textScanner.advanceChar();
                }
                if (charAt == 40) {
                    textScanner.position++;
                    str2 = str3.substring(i, i2);
                } else {
                    textScanner.position = i;
                }
            }
            if (str2 == null) {
                throw new SVGParseException(
                        "Bad transform function encountered in transform list: ".concat(str));
            }
            switch (str2) {
                case "matrix":
                    textScanner.skipWhitespace();
                    float nextFloat = textScanner.nextFloat();
                    textScanner.skipCommaWhitespace();
                    float nextFloat2 = textScanner.nextFloat();
                    textScanner.skipCommaWhitespace();
                    float nextFloat3 = textScanner.nextFloat();
                    textScanner.skipCommaWhitespace();
                    float nextFloat4 = textScanner.nextFloat();
                    textScanner.skipCommaWhitespace();
                    float nextFloat5 = textScanner.nextFloat();
                    textScanner.skipCommaWhitespace();
                    float nextFloat6 = textScanner.nextFloat();
                    textScanner.skipWhitespace();
                    if (!Float.isNaN(nextFloat6) && textScanner.consume(')')) {
                        Matrix matrix2 = new Matrix();
                        matrix2.setValues(
                                new float[] {
                                    nextFloat,
                                    nextFloat3,
                                    nextFloat5,
                                    nextFloat2,
                                    nextFloat4,
                                    nextFloat6,
                                    0.0f,
                                    0.0f,
                                    1.0f
                                });
                        matrix.preConcat(matrix2);
                        break;
                    } else {
                        throw new SVGParseException("Invalid transform list: ".concat(str));
                    }
                    break;
                case "rotate":
                    textScanner.skipWhitespace();
                    float nextFloat7 = textScanner.nextFloat();
                    float possibleNextFloat = textScanner.possibleNextFloat();
                    float possibleNextFloat2 = textScanner.possibleNextFloat();
                    textScanner.skipWhitespace();
                    if (Float.isNaN(nextFloat7) || !textScanner.consume(')')) {
                        throw new SVGParseException("Invalid transform list: ".concat(str));
                    }
                    if (Float.isNaN(possibleNextFloat)) {
                        matrix.preRotate(nextFloat7);
                        break;
                    } else if (!Float.isNaN(possibleNextFloat2)) {
                        matrix.preRotate(nextFloat7, possibleNextFloat, possibleNextFloat2);
                        break;
                    } else {
                        throw new SVGParseException("Invalid transform list: ".concat(str));
                    }
                    break;
                case "scale":
                    textScanner.skipWhitespace();
                    float nextFloat8 = textScanner.nextFloat();
                    float possibleNextFloat3 = textScanner.possibleNextFloat();
                    textScanner.skipWhitespace();
                    if (!Float.isNaN(nextFloat8) && textScanner.consume(')')) {
                        if (!Float.isNaN(possibleNextFloat3)) {
                            matrix.preScale(nextFloat8, possibleNextFloat3);
                            break;
                        } else {
                            matrix.preScale(nextFloat8, nextFloat8);
                            break;
                        }
                    } else {
                        throw new SVGParseException("Invalid transform list: ".concat(str));
                    }
                    break;
                case "skewX":
                    textScanner.skipWhitespace();
                    float nextFloat9 = textScanner.nextFloat();
                    textScanner.skipWhitespace();
                    if (!Float.isNaN(nextFloat9) && textScanner.consume(')')) {
                        matrix.preSkew((float) Math.tan(Math.toRadians(nextFloat9)), 0.0f);
                        break;
                    } else {
                        throw new SVGParseException("Invalid transform list: ".concat(str));
                    }
                    break;
                case "skewY":
                    textScanner.skipWhitespace();
                    float nextFloat10 = textScanner.nextFloat();
                    textScanner.skipWhitespace();
                    if (!Float.isNaN(nextFloat10) && textScanner.consume(')')) {
                        matrix.preSkew(0.0f, (float) Math.tan(Math.toRadians(nextFloat10)));
                        break;
                    } else {
                        throw new SVGParseException("Invalid transform list: ".concat(str));
                    }
                    break;
                case "translate":
                    textScanner.skipWhitespace();
                    float nextFloat11 = textScanner.nextFloat();
                    float possibleNextFloat4 = textScanner.possibleNextFloat();
                    textScanner.skipWhitespace();
                    if (!Float.isNaN(nextFloat11) && textScanner.consume(')')) {
                        if (!Float.isNaN(possibleNextFloat4)) {
                            matrix.preTranslate(nextFloat11, possibleNextFloat4);
                            break;
                        } else {
                            matrix.preTranslate(nextFloat11, 0.0f);
                            break;
                        }
                    } else {
                        throw new SVGParseException("Invalid transform list: ".concat(str));
                    }
                    break;
                default:
                    throw new SVGParseException(
                            ComposerKt$$ExternalSyntheticOutline0.m(
                                    "Invalid transform list fn: ", str2, ")"));
            }
            if (textScanner.empty()) {
                return matrix;
            }
            textScanner.skipCommaWhitespace();
        }
        return matrix;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:363:0x04f6, code lost:

       if (r22.equals("underline") == false) goto L285;
    */
    /* JADX WARN: Code restructure failed: missing block: B:433:0x05f0, code lost:

       if (r22.equals("scroll") == false) goto L358;
    */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:120:0x01fb  */
    /* JADX WARN: Removed duplicated region for block: B:464:0x06f5  */
    /* JADX WARN: Removed duplicated region for block: B:466:? A[ADDED_TO_REGION, REMOVE, RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void processStyleProperty(
            com.caverock.androidsvg.SVG.Style r20, java.lang.String r21, java.lang.String r22) {
        /*
            Method dump skipped, instructions count: 2022
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.caverock.androidsvg.SVGParser.processStyleProperty(com.caverock.androidsvg.SVG$Style,"
                    + " java.lang.String, java.lang.String):void");
    }

    public final void appendToTextContainer(String str) {
        SVG.SvgConditionalContainer svgConditionalContainer =
                (SVG.SvgConditionalContainer) this.currentElement;
        int size = svgConditionalContainer.children.size();
        SVG.SvgObject svgObject =
                size == 0 ? null : (SVG.SvgObject) svgConditionalContainer.children.get(size - 1);
        if (svgObject instanceof SVG.TextSequence) {
            SVG.TextSequence textSequence = (SVG.TextSequence) svgObject;
            textSequence.text =
                    ComponentActivity$1$$ExternalSyntheticOutline0.m(
                            new StringBuilder(), textSequence.text, str);
        } else {
            SVG.SvgContainer svgContainer = this.currentElement;
            SVG.TextSequence textSequence2 = new SVG.TextSequence();
            textSequence2.text = str;
            svgContainer.addChild(textSequence2);
        }
    }

    public final void endElement(String str, String str2, String str3) {
        if (this.ignoring) {
            int i = this.ignoreDepth - 1;
            this.ignoreDepth = i;
            if (i == 0) {
                this.ignoring = false;
            }
        }
        if ("http://www.w3.org/2000/svg".equals(str) || ApnSettings.MVNO_NONE.equals(str)) {
            if (str2.length() <= 0) {
                str2 = str3;
            }
            SVGElem sVGElem = (SVGElem) ((HashMap) SVGElem.cache).get(str2);
            if (sVGElem == null) {
                sVGElem = SVGElem.UNSUPPORTED;
            }
            switch (sVGElem.ordinal()) {
                case 0:
                case 3:
                case 4:
                case 7:
                case 8:
                case 10:
                case 11:
                case 12:
                case 14:
                case 17:
                case 19:
                case 20:
                case 22:
                case 23:
                case 24:
                case 25:
                case 28:
                case 29:
                case 30:
                    this.currentElement = ((SVG.SvgObject) this.currentElement).parent;
                    break;
                case 5:
                case 26:
                    this.inMetadataElement = false;
                    if (this.metadataElementContents != null) {
                        SVGElem sVGElem2 = this.metadataTag;
                        if (sVGElem2 == SVGElem.title) {
                            this.svgDocument.getClass();
                        } else if (sVGElem2 == SVGElem.desc) {
                            this.svgDocument.getClass();
                        }
                        this.metadataElementContents.setLength(0);
                        break;
                    }
                    break;
                case 21:
                    StringBuilder sb = this.styleElementContents;
                    if (sb != null) {
                        this.inStyleElement = false;
                        String sb2 = sb.toString();
                        CSSParser.MediaType mediaType = CSSParser.MediaType.screen;
                        CSSParser.Source source = CSSParser.Source.Document;
                        CSSParser cSSParser = new CSSParser();
                        cSSParser.inMediaRule = false;
                        cSSParser.deviceMediaType = mediaType;
                        cSSParser.source = source;
                        SVG svg = this.svgDocument;
                        CSSParser.CSSTextScanner cSSTextScanner = new CSSParser.CSSTextScanner(sb2);
                        cSSTextScanner.skipWhitespace();
                        svg.cssRules.addAll(cSSParser.parseRuleset(cSSTextScanner));
                        this.styleElementContents.setLength(0);
                        break;
                    }
                    break;
            }
        }
    }

    public final void parseUsingSAX(InputStream inputStream) {
        Log.d("SVGParser", "Falling back to SAX parser");
        try {
            SAXParserFactory newInstance = SAXParserFactory.newInstance();
            newInstance.setFeature("http://xml.org/sax/features/external-general-entities", false);
            newInstance.setFeature(
                    "http://xml.org/sax/features/external-parameter-entities", false);
            XMLReader xMLReader = newInstance.newSAXParser().getXMLReader();
            SAXHandler sAXHandler = new SAXHandler();
            xMLReader.setContentHandler(sAXHandler);
            xMLReader.setProperty("http://xml.org/sax/properties/lexical-handler", sAXHandler);
            xMLReader.parse(new InputSource(inputStream));
        } catch (IOException e) {
            throw new SVGParseException("Stream error", e);
        } catch (ParserConfigurationException e2) {
            throw new SVGParseException("XML parser problem", e2);
        } catch (SAXException e3) {
            throw new SVGParseException("SVG parse error", e3);
        }
    }

    public final void parseUsingXmlPullParser(InputStream inputStream) {
        try {
            try {
                XmlPullParser newPullParser = Xml.newPullParser();
                XPPAttributesWrapper xPPAttributesWrapper = new XPPAttributesWrapper();
                xPPAttributesWrapper.parser = newPullParser;
                newPullParser.setFeature(
                        "http://xmlpull.org/v1/doc/features.html#process-docdecl", false);
                newPullParser.setFeature(
                        "http://xmlpull.org/v1/doc/features.html#process-namespaces", true);
                newPullParser.setInput(inputStream, null);
                for (int eventType = newPullParser.getEventType();
                        eventType != 1;
                        eventType = newPullParser.nextToken()) {
                    if (eventType == 0) {
                        startDocument();
                    } else if (eventType == 8) {
                        Log.d("SVGParser", "PROC INSTR: " + newPullParser.getText());
                        TextScanner textScanner = new TextScanner(newPullParser.getText());
                        String nextToken = textScanner.nextToken();
                        parseProcessingInstructionAttributes(textScanner);
                        nextToken.equals("xml-stylesheet");
                    } else if (eventType != 10) {
                        if (eventType == 2) {
                            String name = newPullParser.getName();
                            if (newPullParser.getPrefix() != null) {
                                name = newPullParser.getPrefix() + ':' + name;
                            }
                            startElement(
                                    newPullParser.getNamespace(),
                                    newPullParser.getName(),
                                    name,
                                    xPPAttributesWrapper);
                        } else if (eventType == 3) {
                            String name2 = newPullParser.getName();
                            if (newPullParser.getPrefix() != null) {
                                name2 = newPullParser.getPrefix() + ':' + name2;
                            }
                            endElement(
                                    newPullParser.getNamespace(), newPullParser.getName(), name2);
                        } else if (eventType == 4) {
                            int[] iArr = new int[2];
                            text(newPullParser.getTextCharacters(iArr), iArr[0], iArr[1]);
                        } else if (eventType == 5) {
                            text(newPullParser.getText());
                        }
                    } else if (this.svgDocument.rootElement == null
                            && newPullParser.getText().contains("<!ENTITY ")) {
                        try {
                            Log.d("SVGParser", "Switching to SAX parser to process entities");
                            inputStream.reset();
                            parseUsingSAX(inputStream);
                            return;
                        } catch (IOException unused) {
                            Log.w(
                                    "SVGParser",
                                    "Detected internal entity definitions, but could not parse"
                                        + " them.");
                            return;
                        }
                    }
                }
            } catch (IOException e) {
                throw new SVGParseException("Stream error", e);
            }
        } catch (XmlPullParserException e2) {
            throw new SVGParseException("XML parser problem", e2);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:135:0x02f2, code lost:

       r20 = r4;
    */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x0350, code lost:

       android.util.Log.e("SVGParser", "Bad path coords for " + ((char) r7) + " path segment");
    */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0320  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0363 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void path(org.xml.sax.Attributes r23) {
        /*
            Method dump skipped, instructions count: 974
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.caverock.androidsvg.SVGParser.path(org.xml.sax.Attributes):void");
    }

    public final void startDocument() {
        SVG svg = new SVG();
        svg.rootElement = null;
        svg.cssRules = new CSSParser.Ruleset();
        svg.idToElementMap = new HashMap();
        this.svgDocument = svg;
    }

    /* JADX WARN: Code restructure failed: missing block: B:262:0x04bf, code lost:

       continue;
    */
    /* JADX WARN: Code restructure failed: missing block: B:355:0x068c, code lost:

       continue;
    */
    /* JADX WARN: Code restructure failed: missing block: B:422:0x0752, code lost:

       continue;
    */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x010b, code lost:

       continue;
    */
    /* JADX WARN: Code restructure failed: missing block: B:574:0x099a, code lost:

       continue;
    */
    /* JADX WARN: Code restructure failed: missing block: B:716:0x0bf1, code lost:

       continue;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void startElement(
            java.lang.String r17,
            java.lang.String r18,
            java.lang.String r19,
            org.xml.sax.Attributes r20) {
        /*
            Method dump skipped, instructions count: 3254
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.caverock.androidsvg.SVGParser.startElement(java.lang.String,"
                    + " java.lang.String, java.lang.String, org.xml.sax.Attributes):void");
    }

    public final void text(String str) {
        if (this.ignoring) {
            return;
        }
        if (this.inMetadataElement) {
            if (this.metadataElementContents == null) {
                this.metadataElementContents = new StringBuilder(str.length());
            }
            this.metadataElementContents.append(str);
        } else if (this.inStyleElement) {
            if (this.styleElementContents == null) {
                this.styleElementContents = new StringBuilder(str.length());
            }
            this.styleElementContents.append(str);
        } else if (this.currentElement instanceof SVG.TextContainer) {
            appendToTextContainer(str);
        }
    }

    public static float parseFloat(int i, String str) {
        float parseNumber = new NumberParser().parseNumber(0, i, str);
        if (Float.isNaN(parseNumber)) {
            throw new SVGParseException(
                    AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                            "Invalid float value: ", str));
        }
        return parseNumber;
    }

    public final void text(char[] cArr, int i, int i2) {
        if (this.ignoring) {
            return;
        }
        if (this.inMetadataElement) {
            if (this.metadataElementContents == null) {
                this.metadataElementContents = new StringBuilder(i2);
            }
            this.metadataElementContents.append(cArr, i, i2);
        } else if (this.inStyleElement) {
            if (this.styleElementContents == null) {
                this.styleElementContents = new StringBuilder(i2);
            }
            this.styleElementContents.append(cArr, i, i2);
        } else if (this.currentElement instanceof SVG.TextContainer) {
            appendToTextContainer(new String(cArr, i, i2));
        }
    }
}
