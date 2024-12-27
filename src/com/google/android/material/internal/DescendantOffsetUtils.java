package com.google.android.material.internal;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class DescendantOffsetUtils {
    public static final ThreadLocal matrix = new ThreadLocal();
    public static final ThreadLocal rectF = new ThreadLocal();

    public static void getDescendantRect(ViewGroup viewGroup, View view, Rect rect) {
        rect.set(0, 0, view.getWidth(), view.getHeight());
        ThreadLocal threadLocal = matrix;
        Matrix matrix2 = (Matrix) threadLocal.get();
        if (matrix2 == null) {
            matrix2 = new Matrix();
            threadLocal.set(matrix2);
        } else {
            matrix2.reset();
        }
        offsetDescendantMatrix(viewGroup, view, matrix2);
        ThreadLocal threadLocal2 = rectF;
        RectF rectF2 = (RectF) threadLocal2.get();
        if (rectF2 == null) {
            rectF2 = new RectF();
            threadLocal2.set(rectF2);
        }
        rectF2.set(rect);
        matrix2.mapRect(rectF2);
        rect.set(
                (int) (rectF2.left + 0.5f),
                (int) (rectF2.top + 0.5f),
                (int) (rectF2.right + 0.5f),
                (int) (rectF2.bottom + 0.5f));
    }

    public static void offsetDescendantMatrix(ViewParent viewParent, View view, Matrix matrix2) {
        Object parent = view.getParent();
        if ((parent instanceof View) && parent != viewParent) {
            offsetDescendantMatrix(viewParent, (View) parent, matrix2);
            matrix2.preTranslate(-r0.getScrollX(), -r0.getScrollY());
        }
        matrix2.preTranslate(view.getLeft(), view.getTop());
        if (view.getMatrix().isIdentity()) {
            return;
        }
        matrix2.preConcat(view.getMatrix());
    }
}
