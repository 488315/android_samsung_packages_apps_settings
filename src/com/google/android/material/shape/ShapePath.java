package com.google.android.material.shape;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;

import com.google.android.material.shadow.ShadowRenderer;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ShapePath {
    public float currentShadowAngle;
    public float endShadowAngle;
    public float endX;
    public float endY;
    public final List operations = new ArrayList();
    public final List shadowCompatOperations = new ArrayList();
    public float startX;
    public float startY;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ArcShadowOperation extends ShadowCompatOperation {
        public final PathArcOperation operation;

        public ArcShadowOperation(PathArcOperation pathArcOperation) {
            this.operation = pathArcOperation;
        }

        @Override // com.google.android.material.shape.ShapePath.ShadowCompatOperation
        public final void draw(Matrix matrix, ShadowRenderer shadowRenderer, int i, Canvas canvas) {
            PathArcOperation pathArcOperation = this.operation;
            float f = pathArcOperation.startAngle;
            float f2 = pathArcOperation.sweepAngle;
            RectF rectF =
                    new RectF(
                            pathArcOperation.left,
                            pathArcOperation.top,
                            pathArcOperation.right,
                            pathArcOperation.bottom);
            shadowRenderer.getClass();
            boolean z = f2 < 0.0f;
            Path path = shadowRenderer.scratch;
            int[] iArr = ShadowRenderer.cornerColors;
            if (z) {
                iArr[0] = 0;
                iArr[1] = shadowRenderer.shadowEndColor;
                iArr[2] = shadowRenderer.shadowMiddleColor;
                iArr[3] = shadowRenderer.shadowStartColor;
            } else {
                path.rewind();
                path.moveTo(rectF.centerX(), rectF.centerY());
                path.arcTo(rectF, f, f2);
                path.close();
                float f3 = -i;
                rectF.inset(f3, f3);
                iArr[0] = 0;
                iArr[1] = shadowRenderer.shadowStartColor;
                iArr[2] = shadowRenderer.shadowMiddleColor;
                iArr[3] = shadowRenderer.shadowEndColor;
            }
            float width = rectF.width() / 2.0f;
            if (width <= 0.0f) {
                return;
            }
            float f4 = 1.0f - (i / width);
            float[] fArr = ShadowRenderer.cornerPositions;
            fArr[1] = f4;
            fArr[2] = ((1.0f - f4) / 2.0f) + f4;
            shadowRenderer.cornerShadowPaint.setShader(
                    new RadialGradient(
                            rectF.centerX(),
                            rectF.centerY(),
                            width,
                            iArr,
                            fArr,
                            Shader.TileMode.CLAMP));
            canvas.save();
            canvas.concat(matrix);
            canvas.scale(1.0f, rectF.height() / rectF.width());
            if (!z) {
                canvas.clipPath(path, Region.Op.DIFFERENCE);
                canvas.drawPath(path, shadowRenderer.transparentPaint);
            }
            canvas.drawArc(rectF, f, f2, true, shadowRenderer.cornerShadowPaint);
            canvas.restore();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class LineShadowOperation extends ShadowCompatOperation {
        public final PathLineOperation operation;
        public final float startX;
        public final float startY;

        public LineShadowOperation(PathLineOperation pathLineOperation, float f, float f2) {
            this.operation = pathLineOperation;
            this.startX = f;
            this.startY = f2;
        }

        @Override // com.google.android.material.shape.ShapePath.ShadowCompatOperation
        public final void draw(Matrix matrix, ShadowRenderer shadowRenderer, int i, Canvas canvas) {
            PathLineOperation pathLineOperation = this.operation;
            float f = pathLineOperation.y;
            float f2 = this.startY;
            float f3 = pathLineOperation.x;
            float f4 = this.startX;
            RectF rectF = new RectF(0.0f, 0.0f, (float) Math.hypot(f - f2, f3 - f4), 0.0f);
            this.renderMatrix.set(matrix);
            this.renderMatrix.preTranslate(f4, f2);
            this.renderMatrix.preRotate(getAngle());
            Matrix matrix2 = this.renderMatrix;
            shadowRenderer.getClass();
            rectF.bottom += i;
            rectF.offset(0.0f, -i);
            int[] iArr = ShadowRenderer.edgeColors;
            iArr[0] = shadowRenderer.shadowEndColor;
            iArr[1] = shadowRenderer.shadowMiddleColor;
            iArr[2] = shadowRenderer.shadowStartColor;
            Paint paint = shadowRenderer.edgeShadowPaint;
            float f5 = rectF.left;
            paint.setShader(
                    new LinearGradient(
                            f5,
                            rectF.top,
                            f5,
                            rectF.bottom,
                            iArr,
                            ShadowRenderer.edgePositions,
                            Shader.TileMode.CLAMP));
            canvas.save();
            canvas.concat(matrix2);
            canvas.drawRect(rectF, shadowRenderer.edgeShadowPaint);
            canvas.restore();
        }

        public final float getAngle() {
            PathLineOperation pathLineOperation = this.operation;
            return (float)
                    Math.toDegrees(
                            Math.atan(
                                    (pathLineOperation.y - this.startY)
                                            / (pathLineOperation.x - this.startX)));
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PathArcOperation extends PathOperation {
        public static final RectF rectF = new RectF();
        public final float bottom;
        public final float left;
        public final float right;
        public float startAngle;
        public float sweepAngle;
        public final float top;

        public PathArcOperation(float f, float f2, float f3, float f4) {
            this.left = f;
            this.top = f2;
            this.right = f3;
            this.bottom = f4;
        }

        @Override // com.google.android.material.shape.ShapePath.PathOperation
        public final void applyToPath(Matrix matrix, Path path) {
            Matrix matrix2 = this.matrix;
            matrix.invert(matrix2);
            path.transform(matrix2);
            RectF rectF2 = rectF;
            rectF2.set(this.left, this.top, this.right, this.bottom);
            path.arcTo(rectF2, this.startAngle, this.sweepAngle, false);
            path.transform(matrix);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PathLineOperation extends PathOperation {
        public float x;
        public float y;

        @Override // com.google.android.material.shape.ShapePath.PathOperation
        public final void applyToPath(Matrix matrix, Path path) {
            Matrix matrix2 = this.matrix;
            matrix.invert(matrix2);
            path.transform(matrix2);
            path.lineTo(this.x, this.y);
            path.transform(matrix);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class PathOperation {
        public final Matrix matrix = new Matrix();

        public abstract void applyToPath(Matrix matrix, Path path);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class ShadowCompatOperation {
        public static final Matrix IDENTITY_MATRIX = new Matrix();
        public final Matrix renderMatrix = new Matrix();

        public abstract void draw(
                Matrix matrix, ShadowRenderer shadowRenderer, int i, Canvas canvas);
    }

    public ShapePath() {
        reset(0.0f, 270.0f, 0.0f);
    }

    public final void addConnectingShadowIfNecessary(float f) {
        float f2 = this.currentShadowAngle;
        if (f2 == f) {
            return;
        }
        float f3 = ((f - f2) + 360.0f) % 360.0f;
        if (f3 > 180.0f) {
            return;
        }
        float f4 = this.endX;
        float f5 = this.endY;
        PathArcOperation pathArcOperation = new PathArcOperation(f4, f5, f4, f5);
        pathArcOperation.startAngle = this.currentShadowAngle;
        pathArcOperation.sweepAngle = f3;
        ((ArrayList) this.shadowCompatOperations).add(new ArcShadowOperation(pathArcOperation));
        this.currentShadowAngle = f;
    }

    public final void applyToPath(Matrix matrix, Path path) {
        int size = ((ArrayList) this.operations).size();
        for (int i = 0; i < size; i++) {
            ((PathOperation) ((ArrayList) this.operations).get(i)).applyToPath(matrix, path);
        }
    }

    public final void lineTo(float f, float f2) {
        PathLineOperation pathLineOperation = new PathLineOperation();
        pathLineOperation.x = f;
        pathLineOperation.y = f2;
        ((ArrayList) this.operations).add(pathLineOperation);
        LineShadowOperation lineShadowOperation =
                new LineShadowOperation(pathLineOperation, this.endX, this.endY);
        float angle = lineShadowOperation.getAngle() + 270.0f;
        float angle2 = lineShadowOperation.getAngle() + 270.0f;
        addConnectingShadowIfNecessary(angle);
        ((ArrayList) this.shadowCompatOperations).add(lineShadowOperation);
        this.currentShadowAngle = angle2;
        this.endX = f;
        this.endY = f2;
    }

    public final void reset(float f, float f2, float f3) {
        this.startX = 0.0f;
        this.startY = f;
        this.endX = 0.0f;
        this.endY = f;
        this.currentShadowAngle = f2;
        this.endShadowAngle = (f2 + f3) % 360.0f;
        ((ArrayList) this.operations).clear();
        ((ArrayList) this.shadowCompatOperations).clear();
    }
}
