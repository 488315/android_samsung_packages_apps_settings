package com.caverock.androidsvg;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;

import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.accessibility.vision.routine.ReduceBrightnessRoutineActionHandler;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.zip.GZIPInputStream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SVG {
    public CSSParser.Ruleset cssRules;
    public Map idToElementMap;
    public Svg rootElement;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CSSClipRect {
        public Length bottom;
        public Length left;
        public Length right;
        public Length top;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Circle extends GraphicsElement {
        public Length cx;
        public Length cy;
        public Length r;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "circle";
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ClipPath extends Group implements NotDirectlyRendered {
        public Boolean clipPathUnitsAreUser;

        @Override // com.caverock.androidsvg.SVG.Group, com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "clipPath";
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Colour extends SvgPaint {
        public static final Colour BLACK = new Colour(EmergencyPhoneWidget.BG_COLOR);
        public static final Colour TRANSPARENT = new Colour(0);
        public final int colour;

        public Colour(int i) {
            this.colour = i;
        }

        public final String toString() {
            return String.format("#%08x", Integer.valueOf(this.colour));
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CurrentColor extends SvgPaint {
        public static final CurrentColor instance = new CurrentColor();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Defs extends Group implements NotDirectlyRendered {
        @Override // com.caverock.androidsvg.SVG.Group, com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "defs";
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Ellipse extends GraphicsElement {
        public Length cx;
        public Length cy;
        public Length rx;
        public Length ry;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "ellipse";
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class GradientElement extends SvgElementBase implements SvgContainer {
        public List children = new ArrayList();
        public Matrix gradientTransform;
        public Boolean gradientUnitsAreUser;
        public String href;
        public GradientSpread spreadMethod;

        @Override // com.caverock.androidsvg.SVG.SvgContainer
        public final void addChild(SvgObject svgObject) {
            if (svgObject instanceof Stop) {
                this.children.add(svgObject);
                return;
            }
            throw new SVGParseException(
                    "Gradient elements cannot contain " + svgObject + " elements.");
        }

        @Override // com.caverock.androidsvg.SVG.SvgContainer
        public final List getChildren() {
            return this.children;
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    final class GradientSpread {
        public static final /* synthetic */ GradientSpread[] $VALUES;
        public static final GradientSpread reflect;
        public static final GradientSpread repeat;

        /* JADX INFO: Fake field, exist only in values array */
        GradientSpread EF0;

        static {
            GradientSpread gradientSpread = new GradientSpread("pad", 0);
            GradientSpread gradientSpread2 = new GradientSpread("reflect", 1);
            reflect = gradientSpread2;
            GradientSpread gradientSpread3 = new GradientSpread("repeat", 2);
            repeat = gradientSpread3;
            $VALUES = new GradientSpread[] {gradientSpread, gradientSpread2, gradientSpread3};
        }

        public static GradientSpread valueOf(String str) {
            return (GradientSpread) Enum.valueOf(GradientSpread.class, str);
        }

        public static GradientSpread[] values() {
            return (GradientSpread[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class GraphicsElement extends SvgElement
            implements HasTransform, SvgConditional {
        public Matrix transform;
        public Set requiredFeatures = null;
        public String requiredExtensions = null;
        public Set systemLanguage = null;
        public Set requiredFormats = null;
        public Set requiredFonts = null;

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final String getRequiredExtensions() {
            return this.requiredExtensions;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final Set getRequiredFeatures() {
            return this.requiredFeatures;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final Set getRequiredFonts() {
            return this.requiredFonts;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final Set getRequiredFormats() {
            return this.requiredFormats;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final Set getSystemLanguage() {
            return this.systemLanguage;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final void setRequiredExtensions(String str) {
            this.requiredExtensions = str;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final void setRequiredFeatures(Set set) {
            this.requiredFeatures = set;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final void setRequiredFonts(Set set) {
            this.requiredFonts = set;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final void setRequiredFormats(Set set) {
            this.requiredFormats = set;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final void setSystemLanguage(Set set) {
            this.systemLanguage = set;
        }

        @Override // com.caverock.androidsvg.SVG.HasTransform
        public final void setTransform(Matrix matrix) {
            this.transform = matrix;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class Group extends SvgConditionalContainer implements HasTransform {
        public Matrix transform;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public String getNodeName() {
            return "group";
        }

        @Override // com.caverock.androidsvg.SVG.HasTransform
        public final void setTransform(Matrix matrix) {
            this.transform = matrix;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface HasTransform {
        void setTransform(Matrix matrix);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Image extends SvgPreserveAspectRatioContainer implements HasTransform {
        public Length height;
        public String href;
        public Matrix transform;
        public Length width;
        public Length x;
        public Length y;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "image";
        }

        @Override // com.caverock.androidsvg.SVG.HasTransform
        public final void setTransform(Matrix matrix) {
            this.transform = matrix;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Line extends GraphicsElement {
        public Length x1;
        public Length x2;
        public Length y1;
        public Length y2;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "line";
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Marker extends SvgViewBoxContainer implements NotDirectlyRendered {
        public Length markerHeight;
        public boolean markerUnitsAreUser;
        public Length markerWidth;
        public Float orient;
        public Length refX;
        public Length refY;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "marker";
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Mask extends SvgConditionalContainer implements NotDirectlyRendered {
        public Length height;
        public Boolean maskContentUnitsAreUser;
        public Boolean maskUnitsAreUser;
        public Length width;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "mask";
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface NotDirectlyRendered {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PaintReference extends SvgPaint {
        public final SvgPaint fallback;
        public final String href;

        public PaintReference(String str, SvgPaint svgPaint) {
            this.href = str;
            this.fallback = svgPaint;
        }

        public final String toString() {
            return this.href + " " + this.fallback;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Path extends GraphicsElement {
        public PathDefinition d;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "path";
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PathDefinition implements PathInterface {
        public byte[] commands;
        public int commandsLength;
        public float[] coords;
        public int coordsLength;

        public final void addCommand(byte b) {
            int i = this.commandsLength;
            byte[] bArr = this.commands;
            if (i == bArr.length) {
                byte[] bArr2 = new byte[bArr.length * 2];
                System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
                this.commands = bArr2;
            }
            byte[] bArr3 = this.commands;
            int i2 = this.commandsLength;
            this.commandsLength = i2 + 1;
            bArr3[i2] = b;
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public final void arcTo(
                float f, float f2, float f3, boolean z, boolean z2, float f4, float f5) {
            addCommand((byte) ((z ? 2 : 0) | 4 | (z2 ? 1 : 0)));
            coordsEnsure(5);
            float[] fArr = this.coords;
            int i = this.coordsLength;
            fArr[i] = f;
            fArr[i + 1] = f2;
            fArr[i + 2] = f3;
            fArr[i + 3] = f4;
            this.coordsLength = i + 5;
            fArr[i + 4] = f5;
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public final void close() {
            addCommand((byte) 8);
        }

        public final void coordsEnsure(int i) {
            float[] fArr = this.coords;
            if (fArr.length < this.coordsLength + i) {
                float[] fArr2 = new float[fArr.length * 2];
                System.arraycopy(fArr, 0, fArr2, 0, fArr.length);
                this.coords = fArr2;
            }
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public final void cubicTo(float f, float f2, float f3, float f4, float f5, float f6) {
            addCommand((byte) 2);
            coordsEnsure(6);
            float[] fArr = this.coords;
            int i = this.coordsLength;
            fArr[i] = f;
            fArr[i + 1] = f2;
            fArr[i + 2] = f3;
            fArr[i + 3] = f4;
            fArr[i + 4] = f5;
            this.coordsLength = i + 6;
            fArr[i + 5] = f6;
        }

        public final void enumeratePath(PathInterface pathInterface) {
            int i = 0;
            for (int i2 = 0; i2 < this.commandsLength; i2++) {
                byte b = this.commands[i2];
                if (b == 0) {
                    float[] fArr = this.coords;
                    int i3 = i + 1;
                    float f = fArr[i];
                    i += 2;
                    pathInterface.moveTo(f, fArr[i3]);
                } else if (b == 1) {
                    float[] fArr2 = this.coords;
                    int i4 = i + 1;
                    float f2 = fArr2[i];
                    i += 2;
                    pathInterface.lineTo(f2, fArr2[i4]);
                } else if (b == 2) {
                    float[] fArr3 = this.coords;
                    float f3 = fArr3[i];
                    float f4 = fArr3[i + 1];
                    float f5 = fArr3[i + 2];
                    float f6 = fArr3[i + 3];
                    int i5 = i + 5;
                    float f7 = fArr3[i + 4];
                    i += 6;
                    pathInterface.cubicTo(f3, f4, f5, f6, f7, fArr3[i5]);
                } else if (b == 3) {
                    float[] fArr4 = this.coords;
                    float f8 = fArr4[i];
                    float f9 = fArr4[i + 1];
                    int i6 = i + 3;
                    float f10 = fArr4[i + 2];
                    i += 4;
                    pathInterface.quadTo(f8, f9, f10, fArr4[i6]);
                } else if (b != 8) {
                    boolean z = (b & 2) != 0;
                    boolean z2 = (b & 1) != 0;
                    float[] fArr5 = this.coords;
                    float f11 = fArr5[i];
                    float f12 = fArr5[i + 1];
                    float f13 = fArr5[i + 2];
                    int i7 = i + 4;
                    float f14 = fArr5[i + 3];
                    i += 5;
                    pathInterface.arcTo(f11, f12, f13, z, z2, f14, fArr5[i7]);
                } else {
                    pathInterface.close();
                }
            }
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public final void lineTo(float f, float f2) {
            addCommand((byte) 1);
            coordsEnsure(2);
            float[] fArr = this.coords;
            int i = this.coordsLength;
            fArr[i] = f;
            this.coordsLength = i + 2;
            fArr[i + 1] = f2;
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public final void moveTo(float f, float f2) {
            addCommand((byte) 0);
            coordsEnsure(2);
            float[] fArr = this.coords;
            int i = this.coordsLength;
            fArr[i] = f;
            this.coordsLength = i + 2;
            fArr[i + 1] = f2;
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public final void quadTo(float f, float f2, float f3, float f4) {
            addCommand((byte) 3);
            coordsEnsure(4);
            float[] fArr = this.coords;
            int i = this.coordsLength;
            fArr[i] = f;
            fArr[i + 1] = f2;
            fArr[i + 2] = f3;
            this.coordsLength = i + 4;
            fArr[i + 3] = f4;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface PathInterface {
        void arcTo(float f, float f2, float f3, boolean z, boolean z2, float f4, float f5);

        void close();

        void cubicTo(float f, float f2, float f3, float f4, float f5, float f6);

        void lineTo(float f, float f2);

        void moveTo(float f, float f2);

        void quadTo(float f, float f2, float f3, float f4);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Pattern extends SvgViewBoxContainer implements NotDirectlyRendered {
        public Length height;
        public String href;
        public Boolean patternContentUnitsAreUser;
        public Matrix patternTransform;
        public Boolean patternUnitsAreUser;
        public Length width;
        public Length x;
        public Length y;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "pattern";
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class PolyLine extends GraphicsElement {
        public float[] points;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public String getNodeName() {
            return "polyline";
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Polygon extends PolyLine {
        @Override // com.caverock.androidsvg.SVG.PolyLine, com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "polygon";
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Rect extends GraphicsElement {
        public Length height;
        public Length rx;
        public Length ry;
        public Length width;
        public Length x;
        public Length y;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "rect";
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Style implements Cloneable {
        public CSSClipRect clip;
        public String clipPath;
        public FillRule clipRule;
        public Colour color;
        public TextDirection direction;
        public Boolean display;
        public SvgPaint fill;
        public Float fillOpacity;
        public FillRule fillRule;
        public List fontFamily;
        public Length fontSize;
        public FontStyle fontStyle;
        public Integer fontWeight;
        public RenderQuality imageRendering;
        public String markerEnd;
        public String markerMid;
        public String markerStart;
        public String mask;
        public Float opacity;
        public Boolean overflow;
        public SvgPaint solidColor;
        public Float solidOpacity;
        public long specifiedFlags = 0;
        public SvgPaint stopColor;
        public Float stopOpacity;
        public SvgPaint stroke;
        public Length[] strokeDashArray;
        public Length strokeDashOffset;
        public LineCap strokeLineCap;
        public LineJoin strokeLineJoin;
        public Float strokeMiterLimit;
        public Float strokeOpacity;
        public Length strokeWidth;
        public TextAnchor textAnchor;
        public TextDecoration textDecoration;
        public VectorEffect vectorEffect;
        public SvgPaint viewportFill;
        public Float viewportFillOpacity;
        public Boolean visibility;

        /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
        /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class FillRule {
            public static final /* synthetic */ FillRule[] $VALUES;
            public static final FillRule EvenOdd;
            public static final FillRule NonZero;

            static {
                FillRule fillRule = new FillRule("NonZero", 0);
                NonZero = fillRule;
                FillRule fillRule2 = new FillRule("EvenOdd", 1);
                EvenOdd = fillRule2;
                $VALUES = new FillRule[] {fillRule, fillRule2};
            }

            public static FillRule valueOf(String str) {
                return (FillRule) Enum.valueOf(FillRule.class, str);
            }

            public static FillRule[] values() {
                return (FillRule[]) $VALUES.clone();
            }
        }

        /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
        /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class FontStyle {
            public static final /* synthetic */ FontStyle[] $VALUES;
            public static final FontStyle Italic;
            public static final FontStyle Normal;
            public static final FontStyle Oblique;

            static {
                FontStyle fontStyle = new FontStyle("Normal", 0);
                Normal = fontStyle;
                FontStyle fontStyle2 = new FontStyle("Italic", 1);
                Italic = fontStyle2;
                FontStyle fontStyle3 = new FontStyle("Oblique", 2);
                Oblique = fontStyle3;
                $VALUES = new FontStyle[] {fontStyle, fontStyle2, fontStyle3};
            }

            public static FontStyle valueOf(String str) {
                return (FontStyle) Enum.valueOf(FontStyle.class, str);
            }

            public static FontStyle[] values() {
                return (FontStyle[]) $VALUES.clone();
            }
        }

        /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
        /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class LineCap {
            public static final /* synthetic */ LineCap[] $VALUES;
            public static final LineCap Butt;
            public static final LineCap Round;
            public static final LineCap Square;

            static {
                LineCap lineCap = new LineCap("Butt", 0);
                Butt = lineCap;
                LineCap lineCap2 = new LineCap("Round", 1);
                Round = lineCap2;
                LineCap lineCap3 = new LineCap("Square", 2);
                Square = lineCap3;
                $VALUES = new LineCap[] {lineCap, lineCap2, lineCap3};
            }

            public static LineCap valueOf(String str) {
                return (LineCap) Enum.valueOf(LineCap.class, str);
            }

            public static LineCap[] values() {
                return (LineCap[]) $VALUES.clone();
            }
        }

        /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
        /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class LineJoin {
            public static final /* synthetic */ LineJoin[] $VALUES;
            public static final LineJoin Bevel;
            public static final LineJoin Miter;
            public static final LineJoin Round;

            static {
                LineJoin lineJoin = new LineJoin("Miter", 0);
                Miter = lineJoin;
                LineJoin lineJoin2 = new LineJoin("Round", 1);
                Round = lineJoin2;
                LineJoin lineJoin3 = new LineJoin("Bevel", 2);
                Bevel = lineJoin3;
                $VALUES = new LineJoin[] {lineJoin, lineJoin2, lineJoin3};
            }

            public static LineJoin valueOf(String str) {
                return (LineJoin) Enum.valueOf(LineJoin.class, str);
            }

            public static LineJoin[] values() {
                return (LineJoin[]) $VALUES.clone();
            }
        }

        /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
        /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class RenderQuality {
            public static final /* synthetic */ RenderQuality[] $VALUES;
            public static final RenderQuality auto;
            public static final RenderQuality optimizeQuality;
            public static final RenderQuality optimizeSpeed;

            static {
                RenderQuality renderQuality = new RenderQuality("auto", 0);
                auto = renderQuality;
                RenderQuality renderQuality2 = new RenderQuality("optimizeQuality", 1);
                optimizeQuality = renderQuality2;
                RenderQuality renderQuality3 = new RenderQuality("optimizeSpeed", 2);
                optimizeSpeed = renderQuality3;
                $VALUES = new RenderQuality[] {renderQuality, renderQuality2, renderQuality3};
            }

            public static RenderQuality valueOf(String str) {
                return (RenderQuality) Enum.valueOf(RenderQuality.class, str);
            }

            public static RenderQuality[] values() {
                return (RenderQuality[]) $VALUES.clone();
            }
        }

        /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
        /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class TextAnchor {
            public static final /* synthetic */ TextAnchor[] $VALUES;
            public static final TextAnchor End;
            public static final TextAnchor Middle;
            public static final TextAnchor Start;

            static {
                TextAnchor textAnchor = new TextAnchor("Start", 0);
                Start = textAnchor;
                TextAnchor textAnchor2 = new TextAnchor("Middle", 1);
                Middle = textAnchor2;
                TextAnchor textAnchor3 = new TextAnchor("End", 2);
                End = textAnchor3;
                $VALUES = new TextAnchor[] {textAnchor, textAnchor2, textAnchor3};
            }

            public static TextAnchor valueOf(String str) {
                return (TextAnchor) Enum.valueOf(TextAnchor.class, str);
            }

            public static TextAnchor[] values() {
                return (TextAnchor[]) $VALUES.clone();
            }
        }

        /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
        /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class TextDecoration {
            public static final /* synthetic */ TextDecoration[] $VALUES;
            public static final TextDecoration Blink;
            public static final TextDecoration LineThrough;
            public static final TextDecoration None;
            public static final TextDecoration Overline;
            public static final TextDecoration Underline;

            static {
                TextDecoration textDecoration = new TextDecoration("None", 0);
                None = textDecoration;
                TextDecoration textDecoration2 = new TextDecoration("Underline", 1);
                Underline = textDecoration2;
                TextDecoration textDecoration3 = new TextDecoration("Overline", 2);
                Overline = textDecoration3;
                TextDecoration textDecoration4 = new TextDecoration("LineThrough", 3);
                LineThrough = textDecoration4;
                TextDecoration textDecoration5 = new TextDecoration("Blink", 4);
                Blink = textDecoration5;
                $VALUES =
                        new TextDecoration[] {
                            textDecoration,
                            textDecoration2,
                            textDecoration3,
                            textDecoration4,
                            textDecoration5
                        };
            }

            public static TextDecoration valueOf(String str) {
                return (TextDecoration) Enum.valueOf(TextDecoration.class, str);
            }

            public static TextDecoration[] values() {
                return (TextDecoration[]) $VALUES.clone();
            }
        }

        /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
        /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class TextDirection {
            public static final /* synthetic */ TextDirection[] $VALUES;
            public static final TextDirection LTR;
            public static final TextDirection RTL;

            static {
                TextDirection textDirection = new TextDirection("LTR", 0);
                LTR = textDirection;
                TextDirection textDirection2 = new TextDirection("RTL", 1);
                RTL = textDirection2;
                $VALUES = new TextDirection[] {textDirection, textDirection2};
            }

            public static TextDirection valueOf(String str) {
                return (TextDirection) Enum.valueOf(TextDirection.class, str);
            }

            public static TextDirection[] values() {
                return (TextDirection[]) $VALUES.clone();
            }
        }

        /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
        /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class VectorEffect {
            public static final /* synthetic */ VectorEffect[] $VALUES;
            public static final VectorEffect NonScalingStroke;
            public static final VectorEffect None;

            static {
                VectorEffect vectorEffect = new VectorEffect("None", 0);
                None = vectorEffect;
                VectorEffect vectorEffect2 = new VectorEffect("NonScalingStroke", 1);
                NonScalingStroke = vectorEffect2;
                $VALUES = new VectorEffect[] {vectorEffect, vectorEffect2};
            }

            public static VectorEffect valueOf(String str) {
                return (VectorEffect) Enum.valueOf(VectorEffect.class, str);
            }

            public static VectorEffect[] values() {
                return (VectorEffect[]) $VALUES.clone();
            }
        }

        public static Style getDefaultStyle() {
            Style style = new Style();
            style.specifiedFlags = -1L;
            Colour colour = Colour.BLACK;
            style.fill = colour;
            FillRule fillRule = FillRule.NonZero;
            style.fillRule = fillRule;
            Float valueOf = Float.valueOf(1.0f);
            style.fillOpacity = valueOf;
            style.stroke = null;
            style.strokeOpacity = valueOf;
            style.strokeWidth = new Length(1.0f);
            style.strokeLineCap = LineCap.Butt;
            style.strokeLineJoin = LineJoin.Miter;
            style.strokeMiterLimit = Float.valueOf(4.0f);
            style.strokeDashArray = null;
            style.strokeDashOffset = new Length(0.0f);
            style.opacity = valueOf;
            style.color = colour;
            style.fontFamily = null;
            style.fontSize = new Length(12.0f, Unit.pt);
            style.fontWeight = 400;
            style.fontStyle = FontStyle.Normal;
            style.textDecoration = TextDecoration.None;
            style.direction = TextDirection.LTR;
            style.textAnchor = TextAnchor.Start;
            Boolean bool = Boolean.TRUE;
            style.overflow = bool;
            style.clip = null;
            style.markerStart = null;
            style.markerMid = null;
            style.markerEnd = null;
            style.display = bool;
            style.visibility = bool;
            style.stopColor = colour;
            style.stopOpacity = valueOf;
            style.clipPath = null;
            style.clipRule = fillRule;
            style.mask = null;
            style.solidColor = null;
            style.solidOpacity = valueOf;
            style.viewportFill = null;
            style.viewportFillOpacity = valueOf;
            style.vectorEffect = VectorEffect.None;
            style.imageRendering = RenderQuality.auto;
            return style;
        }

        public final Object clone() {
            Style style = (Style) super.clone();
            Length[] lengthArr = this.strokeDashArray;
            if (lengthArr != null) {
                style.strokeDashArray = (Length[]) lengthArr.clone();
            }
            return style;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Svg extends SvgViewBoxContainer {
        public Length height;
        public Length width;
        public Length x;
        public Length y;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "svg";
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface SvgConditional {
        String getRequiredExtensions();

        Set getRequiredFeatures();

        Set getRequiredFonts();

        Set getRequiredFormats();

        Set getSystemLanguage();

        void setRequiredExtensions(String str);

        void setRequiredFeatures(Set set);

        void setRequiredFonts(Set set);

        void setRequiredFormats(Set set);

        void setSystemLanguage(Set set);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface SvgContainer {
        void addChild(SvgObject svgObject);

        List getChildren();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class SvgElement extends SvgElementBase {
        public Box boundingBox = null;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class SvgElementBase extends SvgObject {
        public String id = null;
        public Boolean spacePreserve = null;
        public Style baseStyle = null;
        public Style style = null;
        public List classNames = null;

        public final String toString() {
            return getNodeName();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SvgLinearGradient extends GradientElement {
        public Length x1;
        public Length x2;
        public Length y1;
        public Length y2;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "linearGradient";
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class SvgObject {
        public SVG document;
        public SvgContainer parent;

        public abstract String getNodeName();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class SvgPaint implements Cloneable {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class SvgPreserveAspectRatioContainer extends SvgConditionalContainer {
        public PreserveAspectRatio preserveAspectRatio = null;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SvgRadialGradient extends GradientElement {
        public Length cx;
        public Length cy;
        public Length fx;
        public Length fy;
        public Length r;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "radialGradient";
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class SvgViewBoxContainer extends SvgPreserveAspectRatioContainer {
        public Box viewBox;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Switch extends Group {
        @Override // com.caverock.androidsvg.SVG.Group, com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return ReduceBrightnessRoutineActionHandler.KEY_SWITCH;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Symbol extends SvgViewBoxContainer implements NotDirectlyRendered {
        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "symbol";
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class TRef extends TextContainer implements TextChild {
        public String href;
        public Text textRoot;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "tref";
        }

        @Override // com.caverock.androidsvg.SVG.TextChild
        public final Text getTextRoot() {
            return this.textRoot;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class TSpan extends TextPositionedContainer implements TextChild {
        public Text textRoot;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "tspan";
        }

        @Override // com.caverock.androidsvg.SVG.TextChild
        public final Text getTextRoot() {
            return this.textRoot;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Text extends TextPositionedContainer implements HasTransform {
        public Matrix transform;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "text";
        }

        @Override // com.caverock.androidsvg.SVG.HasTransform
        public final void setTransform(Matrix matrix) {
            this.transform = matrix;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface TextChild {
        Text getTextRoot();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class TextContainer extends SvgConditionalContainer {
        @Override // com.caverock.androidsvg.SVG.SvgConditionalContainer,
                  // com.caverock.androidsvg.SVG.SvgContainer
        public final void addChild(SvgObject svgObject) {
            if (svgObject instanceof TextChild) {
                this.children.add(svgObject);
                return;
            }
            throw new SVGParseException(
                    "Text content elements cannot contain " + svgObject + " elements.");
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class TextPath extends TextContainer implements TextChild {
        public String href;
        public Length startOffset;
        public Text textRoot;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "textPath";
        }

        @Override // com.caverock.androidsvg.SVG.TextChild
        public final Text getTextRoot() {
            return this.textRoot;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class TextPositionedContainer extends TextContainer {
        public List dx;
        public List dy;
        public List x;
        public List y;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class TextSequence extends SvgObject implements TextChild {
        public String text;

        @Override // com.caverock.androidsvg.SVG.TextChild
        public final Text getTextRoot() {
            return null;
        }

        public final String toString() {
            return ComponentActivity$1$$ExternalSyntheticOutline0.m(
                    new StringBuilder("TextChild: '"), this.text, "'");
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    final class Unit {
        public static final /* synthetic */ Unit[] $VALUES;
        public static final Unit percent;
        public static final Unit pt;
        public static final Unit px;

        static {
            Unit unit = new Unit("px", 0);
            px = unit;
            Unit unit2 = new Unit("em", 1);
            Unit unit3 = new Unit("ex", 2);
            Unit unit4 = new Unit("in", 3);
            Unit unit5 = new Unit("cm", 4);
            Unit unit6 = new Unit("mm", 5);
            Unit unit7 = new Unit("pt", 6);
            pt = unit7;
            Unit unit8 = new Unit("pc", 7);
            Unit unit9 = new Unit("percent", 8);
            percent = unit9;
            $VALUES = new Unit[] {unit, unit2, unit3, unit4, unit5, unit6, unit7, unit8, unit9};
        }

        public static Unit valueOf(String str) {
            return (Unit) Enum.valueOf(Unit.class, str);
        }

        public static Unit[] values() {
            return (Unit[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Use extends Group {
        public Length height;
        public String href;
        public Length width;
        public Length x;
        public Length y;

        @Override // com.caverock.androidsvg.SVG.Group, com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "use";
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class View extends SvgViewBoxContainer implements NotDirectlyRendered {
        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "view";
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static SvgElementBase getElementById(SvgContainer svgContainer, String str) {
        SvgElementBase elementById;
        SvgElementBase svgElementBase = (SvgElementBase) svgContainer;
        if (str.equals(svgElementBase.id)) {
            return svgElementBase;
        }
        for (Object obj : svgContainer.getChildren()) {
            if (obj instanceof SvgElementBase) {
                SvgElementBase svgElementBase2 = (SvgElementBase) obj;
                if (str.equals(svgElementBase2.id)) {
                    return svgElementBase2;
                }
                if ((obj instanceof SvgContainer)
                        && (elementById = getElementById((SvgContainer) obj, str)) != null) {
                    return elementById;
                }
            }
        }
        return null;
    }

    public static SVG getFromInputStream(InputStream inputStream) {
        SVGParser sVGParser = new SVGParser();
        sVGParser.svgDocument = null;
        sVGParser.currentElement = null;
        sVGParser.ignoring = false;
        sVGParser.inMetadataElement = false;
        sVGParser.metadataTag = null;
        sVGParser.metadataElementContents = null;
        sVGParser.inStyleElement = false;
        sVGParser.styleElementContents = null;
        if (!inputStream.markSupported()) {
            inputStream = new BufferedInputStream(inputStream);
        }
        try {
            inputStream.mark(3);
            int read = inputStream.read() + (inputStream.read() << 8);
            inputStream.reset();
            if (read == 35615) {
                inputStream = new BufferedInputStream(new GZIPInputStream(inputStream));
            }
        } catch (IOException unused) {
        }
        try {
            inputStream.mark(4096);
            sVGParser.parseUsingXmlPullParser(inputStream);
            return sVGParser.svgDocument;
        } finally {
            try {
                inputStream.close();
            } catch (IOException unused2) {
                Log.e("SVGParser", "Exception thrown closing input stream");
            }
        }
    }

    public final void renderToCanvas(Canvas canvas) {
        Box box = new Box(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight());
        SVGAndroidRenderer sVGAndroidRenderer = new SVGAndroidRenderer();
        sVGAndroidRenderer.canvas = canvas;
        sVGAndroidRenderer.dpi = 96.0f;
        sVGAndroidRenderer.document = this;
        Svg svg = this.rootElement;
        if (svg == null) {
            Log.w("SVGAndroidRenderer", "Nothing to render. Document is empty.");
            return;
        }
        Box box2 = svg.viewBox;
        PreserveAspectRatio preserveAspectRatio = svg.preserveAspectRatio;
        sVGAndroidRenderer.state = new SVGAndroidRenderer.RendererState();
        sVGAndroidRenderer.stateStack = new Stack();
        sVGAndroidRenderer.updateStyle(sVGAndroidRenderer.state, Style.getDefaultStyle());
        SVGAndroidRenderer.RendererState rendererState = sVGAndroidRenderer.state;
        rendererState.viewPort = null;
        rendererState.spacePreserve = false;
        sVGAndroidRenderer.stateStack.push(new SVGAndroidRenderer.RendererState(rendererState));
        sVGAndroidRenderer.matrixStack = new Stack();
        sVGAndroidRenderer.parentStack = new Stack();
        Boolean bool = svg.spacePreserve;
        if (bool != null) {
            sVGAndroidRenderer.state.spacePreserve = bool.booleanValue();
        }
        sVGAndroidRenderer.statePush();
        Box box3 = new Box(box);
        Length length = svg.width;
        if (length != null) {
            box3.width = length.floatValue(sVGAndroidRenderer, box3.width);
        }
        Length length2 = svg.height;
        if (length2 != null) {
            box3.height = length2.floatValue(sVGAndroidRenderer, box3.height);
        }
        sVGAndroidRenderer.render(svg, box3, box2, preserveAspectRatio);
        sVGAndroidRenderer.statePop();
    }

    public final SvgElementBase resolveIRI(String str) {
        String substring;
        if (str == null) {
            return null;
        }
        if (str.startsWith("\"") && str.endsWith("\"")) {
            str = str.substring(1, str.length() - 1).replace("\\\"", "\"");
        } else if (str.startsWith("'") && str.endsWith("'")) {
            str = str.substring(1, str.length() - 1).replace("\\'", "'");
        }
        String replace = str.replace("\\\n", ApnSettings.MVNO_NONE).replace("\\A", "\n");
        if (replace.length() <= 1
                || !replace.startsWith("#")
                || (substring = replace.substring(1)) == null
                || substring.length() == 0) {
            return null;
        }
        if (substring.equals(this.rootElement.id)) {
            return this.rootElement;
        }
        if (((HashMap) this.idToElementMap).containsKey(substring)) {
            return (SvgElementBase) ((HashMap) this.idToElementMap).get(substring);
        }
        SvgElementBase elementById = getElementById(this.rootElement, substring);
        ((HashMap) this.idToElementMap).put(substring, elementById);
        return elementById;
    }

    public final void setDocumentHeight(float f) {
        Svg svg = this.rootElement;
        if (svg == null) {
            throw new IllegalArgumentException("SVG document is empty");
        }
        svg.height = new Length(f);
    }

    public final void setDocumentWidth(float f) {
        Svg svg = this.rootElement;
        if (svg == null) {
            throw new IllegalArgumentException("SVG document is empty");
        }
        svg.width = new Length(f);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Length implements Cloneable {
        public final Unit unit;
        public final float value;

        public Length(float f, Unit unit) {
            this.value = f;
            this.unit = unit;
        }

        public final float floatValue(SVGAndroidRenderer sVGAndroidRenderer) {
            float f;
            if (this.unit != Unit.percent) {
                return floatValueX(sVGAndroidRenderer);
            }
            SVGAndroidRenderer.RendererState rendererState = sVGAndroidRenderer.state;
            Box box = rendererState.viewBox;
            if (box == null) {
                box = rendererState.viewPort;
            }
            if (box == null) {
                return this.value;
            }
            float f2 = box.width;
            if (f2 == box.height) {
                f = this.value;
            } else {
                f2 = (float) (Math.sqrt((r0 * r0) + (f2 * f2)) / 1.414213562373095d);
                f = this.value;
            }
            return (f * f2) / 100.0f;
        }

        public final float floatValueX(SVGAndroidRenderer sVGAndroidRenderer) {
            float f;
            float f2;
            switch (this.unit.ordinal()) {
                case 0:
                    return this.value;
                case 1:
                    return sVGAndroidRenderer.state.fillPaint.getTextSize() * this.value;
                case 2:
                    return (sVGAndroidRenderer.state.fillPaint.getTextSize() / 2.0f) * this.value;
                case 3:
                    return this.value * sVGAndroidRenderer.dpi;
                case 4:
                    f = this.value * sVGAndroidRenderer.dpi;
                    f2 = 2.54f;
                    break;
                case 5:
                    f = this.value * sVGAndroidRenderer.dpi;
                    f2 = 25.4f;
                    break;
                case 6:
                    f = this.value * sVGAndroidRenderer.dpi;
                    f2 = 72.0f;
                    break;
                case 7:
                    f = this.value * sVGAndroidRenderer.dpi;
                    f2 = 6.0f;
                    break;
                case 8:
                    SVGAndroidRenderer.RendererState rendererState = sVGAndroidRenderer.state;
                    Box box = rendererState.viewBox;
                    if (box == null) {
                        box = rendererState.viewPort;
                    }
                    if (box != null) {
                        f = this.value * box.width;
                        f2 = 100.0f;
                        break;
                    } else {
                        return this.value;
                    }
                default:
                    return this.value;
            }
            return f / f2;
        }

        public final float floatValueY(SVGAndroidRenderer sVGAndroidRenderer) {
            if (this.unit != Unit.percent) {
                return floatValueX(sVGAndroidRenderer);
            }
            SVGAndroidRenderer.RendererState rendererState = sVGAndroidRenderer.state;
            Box box = rendererState.viewBox;
            if (box == null) {
                box = rendererState.viewPort;
            }
            return box == null ? this.value : (this.value * box.height) / 100.0f;
        }

        public final boolean isNegative() {
            return this.value < 0.0f;
        }

        public final boolean isZero() {
            return this.value == 0.0f;
        }

        public final String toString() {
            return String.valueOf(this.value) + this.unit;
        }

        public Length(float f) {
            this.value = f;
            this.unit = Unit.px;
        }

        public final float floatValue(SVGAndroidRenderer sVGAndroidRenderer, float f) {
            if (this.unit == Unit.percent) {
                return (this.value * f) / 100.0f;
            }
            return floatValueX(sVGAndroidRenderer);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Box {
        public float height;
        public float minX;
        public float minY;
        public float width;

        public Box(float f, float f2, float f3, float f4) {
            this.minX = f;
            this.minY = f2;
            this.width = f3;
            this.height = f4;
        }

        public final float maxX() {
            return this.minX + this.width;
        }

        public final float maxY() {
            return this.minY + this.height;
        }

        public final String toString() {
            return "[" + this.minX + " " + this.minY + " " + this.width + " " + this.height + "]";
        }

        public Box(Box box) {
            this.minX = box.minX;
            this.minY = box.minY;
            this.width = box.width;
            this.height = box.height;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SolidColor extends SvgElementBase implements SvgContainer {
        @Override // com.caverock.androidsvg.SVG.SvgContainer
        public final List getChildren() {
            return Collections.emptyList();
        }

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "solidColor";
        }

        @Override // com.caverock.androidsvg.SVG.SvgContainer
        public final void addChild(SvgObject svgObject) {}
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Stop extends SvgElementBase implements SvgContainer {
        public Float offset;

        @Override // com.caverock.androidsvg.SVG.SvgContainer
        public final List getChildren() {
            return Collections.emptyList();
        }

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "stop";
        }

        @Override // com.caverock.androidsvg.SVG.SvgContainer
        public final void addChild(SvgObject svgObject) {}
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class SvgConditionalContainer extends SvgElement
            implements SvgContainer, SvgConditional {
        public List children = new ArrayList();
        public Set requiredFeatures = null;
        public String requiredExtensions = null;
        public Set requiredFormats = null;
        public Set requiredFonts = null;

        @Override // com.caverock.androidsvg.SVG.SvgContainer
        public void addChild(SvgObject svgObject) {
            this.children.add(svgObject);
        }

        @Override // com.caverock.androidsvg.SVG.SvgContainer
        public final List getChildren() {
            return this.children;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final String getRequiredExtensions() {
            return this.requiredExtensions;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final Set getRequiredFeatures() {
            return this.requiredFeatures;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final Set getRequiredFonts() {
            return this.requiredFonts;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final Set getRequiredFormats() {
            return this.requiredFormats;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final Set getSystemLanguage() {
            return null;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final void setRequiredExtensions(String str) {
            this.requiredExtensions = str;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final void setRequiredFeatures(Set set) {
            this.requiredFeatures = set;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final void setRequiredFonts(Set set) {
            this.requiredFonts = set;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final void setRequiredFormats(Set set) {
            this.requiredFormats = set;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final void setSystemLanguage(Set set) {}
    }
}
