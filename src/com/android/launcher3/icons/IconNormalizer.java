package com.android.launcher3.icons;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;

import androidx.compose.animation.AndroidFlingSpline$$ExternalSyntheticOutline0;

import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;

import java.nio.ByteBuffer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public final class IconNormalizer {
    public final RectF mAdaptiveIconBounds;
    public float mAdaptiveIconScale;
    public final Bitmap mBitmap;
    public final Rect mBounds;
    public final Canvas mCanvas;
    public final boolean mEnableShapeDetection;
    public final float[] mLeftBorder;
    public final Matrix mMatrix;
    public final int mMaxSize;
    public final Paint mPaintMaskShape;
    public final Paint mPaintMaskShapeOutline;
    public final byte[] mPixels;
    public final float[] mRightBorder;
    public final Path mShapePath;

    public IconNormalizer(Context context, int i, boolean z) {
        int i2 = i * 2;
        this.mMaxSize = i2;
        Bitmap createBitmap = Bitmap.createBitmap(i2, i2, Bitmap.Config.ALPHA_8);
        this.mBitmap = createBitmap;
        this.mCanvas = new Canvas(createBitmap);
        this.mPixels = new byte[i2 * i2];
        this.mLeftBorder = new float[i2];
        this.mRightBorder = new float[i2];
        this.mBounds = new Rect();
        this.mAdaptiveIconBounds = new RectF();
        Paint paint = new Paint();
        this.mPaintMaskShape = paint;
        paint.setColor(-65536);
        paint.setStyle(Paint.Style.FILL);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
        Paint paint2 = new Paint();
        this.mPaintMaskShapeOutline = paint2;
        paint2.setStrokeWidth(context.getResources().getDisplayMetrics().density * 2.0f);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setColor(EmergencyPhoneWidget.BG_COLOR);
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        this.mShapePath = new Path();
        this.mMatrix = new Matrix();
        this.mAdaptiveIconScale = 0.0f;
        this.mEnableShapeDetection = z;
    }

    public static void convertToConvexArray(int i, int i2, int i3, float[] fArr) {
        float[] fArr2 = new float[fArr.length - 1];
        int i4 = -1;
        float f = Float.MAX_VALUE;
        for (int i5 = i2 + 1; i5 <= i3; i5++) {
            float f2 = fArr[i5];
            if (f2 > -1.0f) {
                if (f == Float.MAX_VALUE) {
                    i4 = i2;
                } else {
                    float f3 = ((f2 - fArr[i4]) / (i5 - i4)) - f;
                    float f4 = i;
                    if (f3 * f4 < 0.0f) {
                        while (i4 > i2) {
                            i4--;
                            if ((((fArr[i5] - fArr[i4]) / (i5 - i4)) - fArr2[i4]) * f4 >= 0.0f) {
                                break;
                            }
                        }
                    }
                }
                f = (fArr[i5] - fArr[i4]) / (i5 - i4);
                for (int i6 = i4; i6 < i5; i6++) {
                    fArr2[i6] = f;
                    fArr[i6] = ((i6 - i4) * f) + fArr[i4];
                }
                i4 = i5;
            }
        }
    }

    public static float getScale(float f, float f2, float f3) {
        float f4 = f / f2;
        if (f / f3
                > (f4 < 0.7853982f
                        ? 0.6597222f
                        : AndroidFlingSpline$$ExternalSyntheticOutline0.m(
                                1.0f, f4, 0.040449437f, 0.6510417f))) {
            return (float) Math.sqrt(r4 / r3);
        }
        return 1.0f;
    }

    public static float normalizeAdaptiveIcon(Drawable drawable, int i, RectF rectF) {
        Rect rect = new Rect(drawable.getBounds());
        int i2 = 0;
        drawable.setBounds(0, 0, i, i);
        Path iconMask = ((AdaptiveIconDrawable) drawable).getIconMask();
        Region region = new Region();
        region.setPath(iconMask, new Region(0, 0, i, i));
        Rect bounds = region.getBounds();
        GraphicsUtils$$ExternalSyntheticLambda0 graphicsUtils$$ExternalSyntheticLambda0 =
                GraphicsUtils.sOnNewBitmapRunnable;
        RegionIterator regionIterator = new RegionIterator(region);
        Rect rect2 = new Rect();
        while (regionIterator.next(rect2)) {
            i2 += rect2.height() * rect2.width();
        }
        if (rectF != null) {
            float f = i;
            rectF.set(
                    bounds.left / f,
                    bounds.top / f,
                    1.0f - (bounds.right / f),
                    1.0f - (bounds.bottom / f));
        }
        drawable.setBounds(rect);
        float f2 = i2;
        return getScale(f2, f2, i * i);
    }

    public final boolean isShape(Path path) {
        Rect rect;
        if (Math.abs((this.mBounds.width() / this.mBounds.height()) - 1.0f) > 0.05f) {
            return false;
        }
        this.mMatrix.reset();
        this.mMatrix.setScale(this.mBounds.width(), this.mBounds.height());
        Matrix matrix = this.mMatrix;
        Rect rect2 = this.mBounds;
        matrix.postTranslate(rect2.left, rect2.top);
        path.transform(this.mMatrix, this.mShapePath);
        this.mCanvas.drawPath(this.mShapePath, this.mPaintMaskShape);
        this.mCanvas.drawPath(this.mShapePath, this.mPaintMaskShapeOutline);
        byte[] bArr = this.mPixels;
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        wrap.rewind();
        this.mBitmap.copyPixelsToBuffer(wrap);
        Rect rect3 = this.mBounds;
        int i = rect3.top;
        int i2 = this.mMaxSize;
        int i3 = i * i2;
        int i4 = i2 - rect3.right;
        int i5 = 0;
        while (true) {
            rect = this.mBounds;
            if (i >= rect.bottom) {
                break;
            }
            int i6 = rect.left;
            int i7 = i3 + i6;
            while (i6 < this.mBounds.right) {
                if ((bArr[i7] & 255) > 40) {
                    i5++;
                }
                i7++;
                i6++;
            }
            i3 = i7 + i4;
            i++;
        }
        return ((float) i5) / ((float) (this.mBounds.height() * rect.width())) < 0.005f;
    }

    /* JADX WARN: Code restructure failed: missing block: B:77:0x0050, code lost:

       if (r4 <= r16.mMaxSize) goto L28;
    */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00d8 A[Catch: all -> 0x001f, TryCatch #0 {all -> 0x001f, blocks: (B:4:0x0009, B:6:0x000e, B:8:0x0014, B:10:0x0024, B:11:0x0029, B:15:0x002d, B:19:0x003a, B:22:0x005c, B:26:0x0089, B:33:0x0098, B:36:0x009f, B:40:0x00b0, B:42:0x00ba, B:49:0x00c9, B:51:0x00d8, B:55:0x00eb, B:56:0x00e3, B:59:0x00ee, B:61:0x00fa, B:64:0x010e, B:66:0x0112, B:68:0x0115, B:69:0x011e, B:74:0x0040, B:76:0x004e, B:79:0x0056, B:81:0x005a, B:82:0x0052), top: B:3:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00fa A[Catch: all -> 0x001f, TryCatch #0 {all -> 0x001f, blocks: (B:4:0x0009, B:6:0x000e, B:8:0x0014, B:10:0x0024, B:11:0x0029, B:15:0x002d, B:19:0x003a, B:22:0x005c, B:26:0x0089, B:33:0x0098, B:36:0x009f, B:40:0x00b0, B:42:0x00ba, B:49:0x00c9, B:51:0x00d8, B:55:0x00eb, B:56:0x00e3, B:59:0x00ee, B:61:0x00fa, B:64:0x010e, B:66:0x0112, B:68:0x0115, B:69:0x011e, B:74:0x0040, B:76:0x004e, B:79:0x0056, B:81:0x005a, B:82:0x0052), top: B:3:0x0009 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized float getScale(
            android.graphics.drawable.Drawable r17,
            android.graphics.RectF r18,
            android.graphics.Path r19,
            boolean[] r20) {
        /*
            Method dump skipped, instructions count: 304
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.launcher3.icons.IconNormalizer.getScale(android.graphics.drawable.Drawable,"
                    + " android.graphics.RectF, android.graphics.Path, boolean[]):float");
    }
}
