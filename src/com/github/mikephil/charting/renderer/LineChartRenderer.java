package com.github.mikephil.charting.renderer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.DataSet$Rounding;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class LineChartRenderer extends BarLineScatterCandleBubbleRenderer {
    public Path cubicFillPath;
    public Path cubicPath;
    public Canvas mBitmapCanvas;
    public Bitmap.Config mBitmapConfig;
    public LineDataProvider mChart;
    public Paint mCirclePaintInner;
    public float[] mCirclesBuffer;
    public WeakReference mDrawBitmap;
    public Path mGenerateFilledPathBuffer;
    public Path mHighlightLinePath;
    public HashMap mImageCaches;
    public float[] mLineBuffer;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DataSetImageCache {
        public Bitmap[] circleBitmaps;
        public final Path mCirclePathBuffer = new Path();

        public DataSetImageCache() {}
    }

    public final void drawCubicFill(
            Canvas canvas,
            LineDataSet lineDataSet,
            Path path,
            Transformer transformer,
            BarLineScatterCandleBubbleRenderer.XBounds xBounds) {
        float fillLinePosition =
                lineDataSet.mFillFormatter.getFillLinePosition(lineDataSet, this.mChart);
        path.lineTo(lineDataSet.getEntryForIndex(xBounds.min + xBounds.range).x, fillLinePosition);
        path.lineTo(lineDataSet.getEntryForIndex(xBounds.min).x, fillLinePosition);
        path.close();
        transformer.pathValueToPixel(path);
        Drawable drawable = lineDataSet.mFillDrawable;
        if (drawable != null) {
            drawFilledPath(canvas, path, drawable);
            return;
        }
        int i = (lineDataSet.mFillColor & 16777215) | (lineDataSet.mFillAlpha << 24);
        DisplayMetrics displayMetrics = Utils.mMetrics;
        int save = canvas.save();
        canvas.clipPath(path);
        canvas.drawColor(i);
        canvas.restoreToCount(save);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.github.mikephil.charting.renderer.BarLineScatterCandleBubbleRenderer
    public final void drawData(Canvas canvas) {
        LineDataProvider lineDataProvider;
        Bitmap bitmap;
        PathEffect pathEffect;
        int i;
        LineDataProvider lineDataProvider2;
        LineDataSet lineDataSet;
        LineDataProvider lineDataProvider3;
        PathEffect pathEffect2;
        LineDataProvider lineDataProvider4;
        LineDataProvider lineDataProvider5;
        Bitmap bitmap2;
        int i2;
        int i3;
        char c;
        char c2;
        LineDataSet.Mode mode;
        LineDataSet.Mode mode2;
        int i4;
        int i5 = 3;
        int i6 = 2;
        int i7 = 1;
        ViewPortHandler viewPortHandler = this.mViewPortHandler;
        int i8 = (int) viewPortHandler.mChartWidth;
        int i9 = (int) viewPortHandler.mChartHeight;
        WeakReference weakReference = this.mDrawBitmap;
        PathEffect pathEffect3 = null;
        Bitmap bitmap3 = weakReference == null ? null : (Bitmap) weakReference.get();
        if (bitmap3 == null || bitmap3.getWidth() != i8 || bitmap3.getHeight() != i9) {
            if (i8 <= 0 || i9 <= 0) {
                return;
            }
            bitmap3 = Bitmap.createBitmap(i8, i9, this.mBitmapConfig);
            this.mDrawBitmap = new WeakReference(bitmap3);
            this.mBitmapCanvas = new Canvas(bitmap3);
        }
        Bitmap bitmap4 = bitmap3;
        int i10 = 0;
        bitmap4.eraseColor(0);
        LineDataProvider lineDataProvider6 = this.mChart;
        for (LineDataSet lineDataSet2 :
                ((LineData) ((LineChart) lineDataProvider6).mData).mDataSets) {
            if (!lineDataSet2.mVisible || lineDataSet2.mEntries.size() < i7) {
                lineDataProvider = lineDataProvider6;
                bitmap = bitmap4;
                pathEffect = pathEffect3;
                i = i7;
            } else {
                this.mRenderPaint.setStrokeWidth(lineDataSet2.mLineWidth);
                this.mRenderPaint.setPathEffect(pathEffect3);
                LineDataSet.Mode mode3 = lineDataSet2.mMode;
                int ordinal = mode3.ordinal();
                BarLineScatterCandleBubbleRenderer.XBounds xBounds = this.mXBounds;
                ChartAnimator chartAnimator = this.mAnimator;
                if (ordinal != i6) {
                    if (ordinal != i5) {
                        int size = lineDataSet2.mEntries.size();
                        LineDataSet.Mode mode4 = LineDataSet.Mode.STEPPED;
                        int i11 = mode3 == mode4 ? i7 : i10;
                        int i12 = i11 != 0 ? 4 : i6;
                        Transformer transformer =
                                ((BarLineChartBase) lineDataProvider6)
                                        .getTransformer(lineDataSet2.mAxisDependency);
                        chartAnimator.getClass();
                        this.mRenderPaint.setStyle(Paint.Style.STROKE);
                        xBounds.set(lineDataProvider6, lineDataSet2);
                        if (!lineDataSet2.mDrawFilled || size <= 0) {
                            lineDataProvider5 = lineDataProvider6;
                            bitmap2 = bitmap4;
                            i2 = size;
                            i3 = i7;
                        } else {
                            Path path = this.mGenerateFilledPathBuffer;
                            int i13 = xBounds.min;
                            int i14 = xBounds.range + i13;
                            bitmap2 = bitmap4;
                            int i15 = 0;
                            while (true) {
                                int i16 = (i15 * 128) + i13;
                                int i17 = i13;
                                int i18 = i16 + 128;
                                if (i18 > i14) {
                                    i18 = i14;
                                }
                                if (i16 <= i18) {
                                    i4 = i14;
                                    float fillLinePosition =
                                            lineDataSet2.mFillFormatter.getFillLinePosition(
                                                    lineDataSet2, lineDataProvider6);
                                    boolean z = mode3 == mode4;
                                    path.reset();
                                    mode = mode3;
                                    Entry entryForIndex = lineDataSet2.getEntryForIndex(i16);
                                    mode2 = mode4;
                                    path.moveTo(entryForIndex.x, fillLinePosition);
                                    path.lineTo(entryForIndex.x, entryForIndex.getY() * 1.0f);
                                    int i19 = i16 + 1;
                                    lineDataProvider5 = lineDataProvider6;
                                    Entry entry = entryForIndex;
                                    Entry entry2 = null;
                                    while (i19 <= i18) {
                                        entry2 = lineDataSet2.getEntryForIndex(i19);
                                        int i20 = size;
                                        if (z) {
                                            path.lineTo(entry2.x, entry.getY() * 1.0f);
                                        }
                                        path.lineTo(entry2.x, entry2.getY() * 1.0f);
                                        i19++;
                                        entry = entry2;
                                        size = i20;
                                    }
                                    i2 = size;
                                    if (entry2 != null) {
                                        path.lineTo(entry2.x, fillLinePosition);
                                    }
                                    path.close();
                                    transformer.pathValueToPixel(path);
                                    Drawable drawable = lineDataSet2.mFillDrawable;
                                    if (drawable != null) {
                                        drawFilledPath(canvas, path, drawable);
                                    } else {
                                        int i21 =
                                                (lineDataSet2.mFillColor & 16777215)
                                                        | (lineDataSet2.mFillAlpha << 24);
                                        DisplayMetrics displayMetrics = Utils.mMetrics;
                                        int save = canvas.save();
                                        canvas.clipPath(path);
                                        canvas.drawColor(i21);
                                        canvas.restoreToCount(save);
                                    }
                                } else {
                                    mode = mode3;
                                    lineDataProvider5 = lineDataProvider6;
                                    i2 = size;
                                    mode2 = mode4;
                                    i4 = i14;
                                }
                                i3 = 1;
                                i15++;
                                if (i16 > i18) {
                                    break;
                                }
                                i13 = i17;
                                i14 = i4;
                                mode3 = mode;
                                mode4 = mode2;
                                lineDataProvider6 = lineDataProvider5;
                                size = i2;
                            }
                        }
                        if (lineDataSet2.mColors.size() > i3) {
                            int i22 = i12 * 2;
                            if (this.mLineBuffer.length <= i22) {
                                this.mLineBuffer = new float[i12 * 4];
                            }
                            int i23 = xBounds.min;
                            int i24 = xBounds.range + i23;
                            while (i23 < i24) {
                                Entry entryForIndex2 = lineDataSet2.getEntryForIndex(i23);
                                if (entryForIndex2 != null) {
                                    float[] fArr = this.mLineBuffer;
                                    fArr[0] = entryForIndex2.x;
                                    fArr[1] = entryForIndex2.getY() * 1.0f;
                                    if (i23 < xBounds.max) {
                                        Entry entryForIndex3 =
                                                lineDataSet2.getEntryForIndex(i23 + 1);
                                        if (entryForIndex3 == null) {
                                            break;
                                        }
                                        if (i11 != 0) {
                                            float[] fArr2 = this.mLineBuffer;
                                            float f = entryForIndex3.x;
                                            fArr2[2] = f;
                                            float f2 = fArr2[1];
                                            fArr2[3] = f2;
                                            fArr2[4] = f;
                                            fArr2[5] = f2;
                                            fArr2[6] = f;
                                            fArr2[7] = entryForIndex3.getY() * 1.0f;
                                            c = 0;
                                        } else {
                                            float[] fArr3 = this.mLineBuffer;
                                            fArr3[2] = entryForIndex3.x;
                                            fArr3[3] = entryForIndex3.getY() * 1.0f;
                                            c = 0;
                                        }
                                        c2 = 1;
                                    } else {
                                        float[] fArr4 = this.mLineBuffer;
                                        c = 0;
                                        fArr4[2] = fArr4[0];
                                        c2 = 1;
                                        fArr4[3] = fArr4[1];
                                    }
                                    float[] fArr5 = this.mLineBuffer;
                                    float f3 = fArr5[c];
                                    float f4 = fArr5[c2];
                                    float f5 = fArr5[i22 - 2];
                                    float f6 = fArr5[i22 - 1];
                                    if (f3 != f5 || f4 != f6) {
                                        transformer.pointValuesToPixel(fArr5);
                                        if (!viewPortHandler.isInBoundsRight(f3)) {
                                            break;
                                        }
                                        if (viewPortHandler.isInBoundsLeft(f5)) {
                                            if (viewPortHandler.mContentRect.top
                                                    <= Math.max(f4, f6)) {
                                                if (viewPortHandler.mContentRect.bottom
                                                        >= ((int) (Math.min(f4, f6) * 100.0f))
                                                                / 100.0f) {
                                                    this.mRenderPaint.setColor(
                                                            lineDataSet2.getColor(i23));
                                                    canvas.drawLines(
                                                            this.mLineBuffer,
                                                            0,
                                                            i22,
                                                            this.mRenderPaint);
                                                }
                                            }
                                        }
                                    }
                                }
                                i23++;
                            }
                        } else {
                            int i25 = i2 * i12;
                            if (this.mLineBuffer.length < Math.max(i25, i12) * 2) {
                                this.mLineBuffer = new float[Math.max(i25, i12) * 4];
                            }
                            if (lineDataSet2.getEntryForIndex(xBounds.min) != null) {
                                int i26 = xBounds.min;
                                int i27 = 0;
                                while (i26 <= xBounds.range + xBounds.min) {
                                    Entry entryForIndex4 =
                                            lineDataSet2.getEntryForIndex(i26 == 0 ? 0 : i26 - 1);
                                    Entry entryForIndex5 = lineDataSet2.getEntryForIndex(i26);
                                    if (entryForIndex4 != null && entryForIndex5 != null) {
                                        float[] fArr6 = this.mLineBuffer;
                                        fArr6[i27] = entryForIndex4.x;
                                        int i28 = i27 + 2;
                                        fArr6[i27 + 1] = entryForIndex4.getY() * 1.0f;
                                        if (i11 != 0) {
                                            float[] fArr7 = this.mLineBuffer;
                                            fArr7[i28] = entryForIndex5.x;
                                            fArr7[i27 + 3] = entryForIndex4.getY() * 1.0f;
                                            float[] fArr8 = this.mLineBuffer;
                                            fArr8[i27 + 4] = entryForIndex5.x;
                                            i28 = i27 + 6;
                                            fArr8[i27 + 5] = entryForIndex4.getY() * 1.0f;
                                        }
                                        float[] fArr9 = this.mLineBuffer;
                                        fArr9[i28] = entryForIndex5.x;
                                        fArr9[i28 + 1] = entryForIndex5.getY() * 1.0f;
                                        i27 = i28 + 2;
                                    }
                                    i26++;
                                }
                                if (i27 > 0) {
                                    transformer.pointValuesToPixel(this.mLineBuffer);
                                    int max = Math.max((xBounds.range + 1) * i12, i12) * 2;
                                    this.mRenderPaint.setColor(lineDataSet2.getColor());
                                    canvas.drawLines(this.mLineBuffer, 0, max, this.mRenderPaint);
                                }
                                this.mRenderPaint.setPathEffect(null);
                                pathEffect = null;
                                bitmap = bitmap2;
                                lineDataProvider4 = lineDataProvider5;
                            }
                        }
                        this.mRenderPaint.setPathEffect(null);
                        pathEffect = null;
                        bitmap = bitmap2;
                        lineDataProvider4 = lineDataProvider5;
                    } else {
                        LineDataProvider lineDataProvider7 = lineDataProvider6;
                        Bitmap bitmap5 = bitmap4;
                        chartAnimator.getClass();
                        Transformer transformer2 =
                                ((BarLineChartBase) lineDataProvider7)
                                        .getTransformer(lineDataSet2.mAxisDependency);
                        xBounds.set(lineDataProvider7, lineDataSet2);
                        this.cubicPath.reset();
                        if (xBounds.range >= 1) {
                            Entry entryForIndex6 = lineDataSet2.getEntryForIndex(xBounds.min);
                            this.cubicPath.moveTo(entryForIndex6.x, entryForIndex6.getY() * 1.0f);
                            int i29 = xBounds.min + 1;
                            while (i29 <= xBounds.range + xBounds.min) {
                                Entry entryForIndex7 = lineDataSet2.getEntryForIndex(i29);
                                float f7 = entryForIndex6.x;
                                float f8 = ((entryForIndex7.x - f7) / 2.0f) + f7;
                                this.cubicPath.cubicTo(
                                        f8,
                                        entryForIndex6.getY() * 1.0f,
                                        f8,
                                        entryForIndex7.getY() * 1.0f,
                                        entryForIndex7.x,
                                        entryForIndex7.getY() * 1.0f);
                                i29++;
                                entryForIndex6 = entryForIndex7;
                            }
                        }
                        if (lineDataSet2.mDrawFilled) {
                            this.cubicFillPath.reset();
                            this.cubicFillPath.addPath(this.cubicPath);
                            lineDataSet = lineDataSet2;
                            lineDataProvider3 = lineDataProvider7;
                            bitmap = bitmap5;
                            pathEffect2 = null;
                            drawCubicFill(
                                    this.mBitmapCanvas,
                                    lineDataSet,
                                    this.cubicFillPath,
                                    transformer2,
                                    this.mXBounds);
                        } else {
                            lineDataSet = lineDataSet2;
                            lineDataProvider3 = lineDataProvider7;
                            pathEffect2 = null;
                            bitmap = bitmap5;
                        }
                        this.mRenderPaint.setColor(lineDataSet.getColor());
                        this.mRenderPaint.setStyle(Paint.Style.STROKE);
                        transformer2.pathValueToPixel(this.cubicPath);
                        this.mBitmapCanvas.drawPath(this.cubicPath, this.mRenderPaint);
                        this.mRenderPaint.setPathEffect(pathEffect2);
                        pathEffect = pathEffect2;
                        lineDataProvider4 = lineDataProvider3;
                    }
                    lineDataProvider2 = lineDataProvider4;
                } else {
                    LineDataProvider lineDataProvider8 = lineDataProvider6;
                    int i30 = i10;
                    bitmap = bitmap4;
                    chartAnimator.getClass();
                    Transformer transformer3 =
                            ((BarLineChartBase) lineDataProvider8)
                                    .getTransformer(lineDataSet2.mAxisDependency);
                    xBounds.set(lineDataProvider8, lineDataSet2);
                    this.cubicPath.reset();
                    if (xBounds.range >= 1) {
                        int i31 = xBounds.min;
                        Entry entryForIndex8 =
                                lineDataSet2.getEntryForIndex(Math.max(i31 - 1, i30));
                        Entry entryForIndex9 = lineDataSet2.getEntryForIndex(Math.max(i31, i30));
                        if (entryForIndex9 == null) {
                            pathEffect = null;
                            lineDataProvider2 = lineDataProvider8;
                        } else {
                            this.cubicPath.moveTo(entryForIndex9.x, entryForIndex9.getY() * 1.0f);
                            i = 1;
                            int i32 = xBounds.min + 1;
                            int i33 = -1;
                            Entry entry3 = entryForIndex9;
                            while (i32 <= xBounds.range + xBounds.min) {
                                if (i33 != i32) {
                                    entryForIndex9 = lineDataSet2.getEntryForIndex(i32);
                                }
                                int i34 = i32 + 1;
                                if (i34 < lineDataSet2.mEntries.size()) {
                                    i32 = i34;
                                }
                                Entry entryForIndex10 = lineDataSet2.getEntryForIndex(i32);
                                float f9 = entryForIndex9.x - entryForIndex8.x;
                                float f10 = lineDataSet2.mCubicIntensity;
                                this.cubicPath.cubicTo(
                                        entry3.x + (f9 * f10),
                                        (entry3.getY()
                                                        + ((entryForIndex9.getY()
                                                                        - entryForIndex8.getY())
                                                                * f10))
                                                * 1.0f,
                                        entryForIndex9.x - ((entryForIndex10.x - entry3.x) * f10),
                                        (entryForIndex9.getY()
                                                        - ((entryForIndex10.getY() - entry3.getY())
                                                                * f10))
                                                * 1.0f,
                                        entryForIndex9.x,
                                        entryForIndex9.getY() * 1.0f);
                                entryForIndex8 = entry3;
                                i33 = i32;
                                i32 = i34;
                                entry3 = entryForIndex9;
                                entryForIndex9 = entryForIndex10;
                            }
                        }
                    } else {
                        i = 1;
                    }
                    if (lineDataSet2.mDrawFilled) {
                        this.cubicFillPath.reset();
                        this.cubicFillPath.addPath(this.cubicPath);
                        drawCubicFill(
                                this.mBitmapCanvas,
                                lineDataSet2,
                                this.cubicFillPath,
                                transformer3,
                                this.mXBounds);
                    }
                    this.mRenderPaint.setColor(lineDataSet2.getColor());
                    this.mRenderPaint.setStyle(Paint.Style.STROKE);
                    transformer3.pathValueToPixel(this.cubicPath);
                    this.mBitmapCanvas.drawPath(this.cubicPath, this.mRenderPaint);
                    pathEffect = null;
                    this.mRenderPaint.setPathEffect(null);
                    lineDataProvider = lineDataProvider8;
                    this.mRenderPaint.setPathEffect(pathEffect);
                }
                i = 1;
                lineDataProvider = lineDataProvider2;
                this.mRenderPaint.setPathEffect(pathEffect);
            }
            pathEffect3 = pathEffect;
            bitmap4 = bitmap;
            lineDataProvider6 = lineDataProvider;
            i7 = i;
            i10 = 0;
            i5 = 3;
            i6 = 2;
        }
        canvas.drawBitmap(bitmap4, 0.0f, 0.0f, this.mRenderPaint);
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x012d  */
    @Override // com.github.mikephil.charting.renderer.BarLineScatterCandleBubbleRenderer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void drawExtras(android.graphics.Canvas r21) {
        /*
            Method dump skipped, instructions count: 409
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.github.mikephil.charting.renderer.LineChartRenderer.drawExtras(android.graphics.Canvas):void");
    }

    public final void drawFilledPath(Canvas canvas, Path path, Drawable drawable) {
        DisplayMetrics displayMetrics = Utils.mMetrics;
        int save = canvas.save();
        canvas.clipPath(path);
        RectF rectF = this.mViewPortHandler.mContentRect;
        drawable.setBounds(
                (int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
        drawable.draw(canvas);
        canvas.restoreToCount(save);
    }

    @Override // com.github.mikephil.charting.renderer.BarLineScatterCandleBubbleRenderer
    public final void drawHighlighted(Canvas canvas, Highlight[] highlightArr) {
        BarLineScatterCandleBubbleDataProvider barLineScatterCandleBubbleDataProvider = this.mChart;
        LineData lineData = (LineData) ((LineChart) barLineScatterCandleBubbleDataProvider).mData;
        for (Highlight highlight : highlightArr) {
            LineDataSet lineDataSet =
                    (LineDataSet) lineData.getDataSetByIndex(highlight.mDataSetIndex);
            if (lineDataSet != null && lineDataSet.mHighlightEnabled) {
                Entry entryForXValue =
                        lineDataSet.getEntryForXValue(
                                highlight.mX, highlight.mY, DataSet$Rounding.CLOSEST);
                if (isInBoundsX(entryForXValue, lineDataSet)) {
                    Transformer transformer =
                            ((BarLineChartBase) barLineScatterCandleBubbleDataProvider)
                                    .getTransformer(lineDataSet.mAxisDependency);
                    float f = entryForXValue.x;
                    float y = entryForXValue.getY();
                    this.mAnimator.getClass();
                    MPPointD pixelForValues = transformer.getPixelForValues(f, y * 1.0f);
                    float f2 = (float) pixelForValues.x;
                    float f3 = (float) pixelForValues.y;
                    highlight.mDrawX = f2;
                    highlight.mDrawY = f3;
                    this.mHighlightPaint.setColor(lineDataSet.mHighLightColor);
                    this.mHighlightPaint.setStrokeWidth(lineDataSet.mHighlightLineWidth);
                    this.mHighlightPaint.setPathEffect(null);
                    boolean z = lineDataSet.mDrawVerticalHighlightIndicator;
                    ViewPortHandler viewPortHandler = this.mViewPortHandler;
                    if (z) {
                        this.mHighlightLinePath.reset();
                        this.mHighlightLinePath.moveTo(f2, viewPortHandler.mContentRect.top);
                        this.mHighlightLinePath.lineTo(f2, viewPortHandler.mContentRect.bottom);
                        canvas.drawPath(this.mHighlightLinePath, this.mHighlightPaint);
                    }
                    if (lineDataSet.mDrawHorizontalHighlightIndicator) {
                        this.mHighlightLinePath.reset();
                        this.mHighlightLinePath.moveTo(viewPortHandler.mContentRect.left, f3);
                        this.mHighlightLinePath.lineTo(viewPortHandler.mContentRect.right, f3);
                        canvas.drawPath(this.mHighlightLinePath, this.mHighlightPaint);
                    }
                }
            }
        }
    }

    @Override // com.github.mikephil.charting.renderer.BarLineScatterCandleBubbleRenderer
    public final void drawValues(Canvas canvas) {
        int i;
        float[] fArr;
        BarLineScatterCandleBubbleDataProvider barLineScatterCandleBubbleDataProvider = this.mChart;
        BarLineChartBase barLineChartBase =
                (BarLineChartBase) barLineScatterCandleBubbleDataProvider;
        int i2 = 1;
        if (((float) barLineChartBase.mData.getEntryCount())
                < ((float) barLineChartBase.mMaxVisibleCount) * this.mViewPortHandler.mScaleX) {
            List list =
                    ((LineData) ((LineChart) barLineScatterCandleBubbleDataProvider).mData)
                            .mDataSets;
            int i3 = 0;
            while (i3 < list.size()) {
                LineDataSet lineDataSet = (LineDataSet) list.get(i3);
                if (lineDataSet.mVisible
                        && ((lineDataSet.mDrawValues || lineDataSet.mDrawIcons)
                                && lineDataSet.mEntries.size() >= i2)) {
                    this.mValuePaint.setTypeface(null);
                    this.mValuePaint.setTextSize(lineDataSet.mValueTextSize);
                    Transformer transformer =
                            ((BarLineChartBase) barLineScatterCandleBubbleDataProvider)
                                    .getTransformer(lineDataSet.mAxisDependency);
                    int i4 = (int) (lineDataSet.mCircleRadius * 1.75f);
                    if (!lineDataSet.mDrawCircles) {
                        i4 /= 2;
                    }
                    int i5 = i4;
                    BarLineScatterCandleBubbleRenderer.XBounds xBounds = this.mXBounds;
                    xBounds.set(barLineScatterCandleBubbleDataProvider, lineDataSet);
                    this.mAnimator.getClass();
                    int i6 = xBounds.min;
                    int i7 = (((int) ((xBounds.max - i6) * 1.0f)) + i2) * 2;
                    if (transformer.valuePointsForGenerateTransformedValuesLine.length != i7) {
                        transformer.valuePointsForGenerateTransformedValuesLine = new float[i7];
                    }
                    float[] fArr2 = transformer.valuePointsForGenerateTransformedValuesLine;
                    for (int i8 = 0; i8 < i7; i8 += 2) {
                        Entry entryForIndex = lineDataSet.getEntryForIndex((i8 / 2) + i6);
                        if (entryForIndex != null) {
                            fArr2[i8] = entryForIndex.x;
                            fArr2[i8 + 1] = entryForIndex.getY() * 1.0f;
                        } else {
                            fArr2[i8] = 0.0f;
                            fArr2[i8 + 1] = 0.0f;
                        }
                    }
                    transformer.mMBuffer1.set(transformer.mMatrixValueToPx);
                    transformer.mMBuffer1.postConcat(transformer.mViewPortHandler.mMatrixTouch);
                    transformer.mMBuffer1.postConcat(transformer.mMatrixOffset);
                    transformer.mMBuffer1.mapPoints(fArr2);
                    MPPointF mPPointF = lineDataSet.mIconsOffset;
                    MPPointF mPPointF2 = (MPPointF) MPPointF.pool.get();
                    float f = mPPointF.x;
                    mPPointF2.x = f;
                    mPPointF2.y = mPPointF.y;
                    mPPointF2.x = Utils.convertDpToPixel(f);
                    mPPointF2.y = Utils.convertDpToPixel(mPPointF2.y);
                    int i9 = 0;
                    while (i9 < fArr2.length) {
                        float f2 = fArr2[i9];
                        float f3 = fArr2[i9 + 1];
                        ViewPortHandler viewPortHandler = this.mViewPortHandler;
                        if (!viewPortHandler.isInBoundsRight(f2)) {
                            break;
                        }
                        if (viewPortHandler.isInBoundsLeft(f2) && viewPortHandler.isInBoundsY(f3)) {
                            int i10 = i9 / 2;
                            Entry entryForIndex2 = lineDataSet.getEntryForIndex(xBounds.min + i10);
                            if (lineDataSet.mDrawValues) {
                                i = i9;
                                fArr = fArr2;
                                drawValue(
                                        canvas,
                                        lineDataSet.getValueFormatter(),
                                        entryForIndex2.getY(),
                                        f2,
                                        f3 - i5,
                                        lineDataSet.getValueTextColor(i10));
                            } else {
                                i = i9;
                                fArr = fArr2;
                            }
                            entryForIndex2.getClass();
                        } else {
                            i = i9;
                            fArr = fArr2;
                        }
                        i9 = i + 2;
                        fArr2 = fArr;
                    }
                    MPPointF.pool.recycle(mPPointF2);
                }
                i3++;
                i2 = 1;
            }
        }
    }

    @Override // com.github.mikephil.charting.renderer.BarLineScatterCandleBubbleRenderer
    public final void initBuffers() {}
}
