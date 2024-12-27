package com.google.android.material.shape;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;

import com.google.android.material.R$styleable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ShapeAppearanceModel {
    public static final RelativeCornerSize PILL = new RelativeCornerSize(0.5f);
    public CornerTreatment topLeftCorner = new RoundedCornerTreatment();
    public CornerTreatment topRightCorner = new RoundedCornerTreatment();
    public CornerTreatment bottomRightCorner = new RoundedCornerTreatment();
    public CornerTreatment bottomLeftCorner = new RoundedCornerTreatment();
    public CornerSize topLeftCornerSize = new AbsoluteCornerSize(0.0f);
    public CornerSize topRightCornerSize = new AbsoluteCornerSize(0.0f);
    public CornerSize bottomRightCornerSize = new AbsoluteCornerSize(0.0f);
    public CornerSize bottomLeftCornerSize = new AbsoluteCornerSize(0.0f);
    public EdgeTreatment topEdge = new EdgeTreatment();
    public EdgeTreatment rightEdge = new EdgeTreatment();
    public EdgeTreatment bottomEdge = new EdgeTreatment();
    public EdgeTreatment leftEdge = new EdgeTreatment();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder {
        public CornerTreatment topLeftCorner = new RoundedCornerTreatment();
        public CornerTreatment topRightCorner = new RoundedCornerTreatment();
        public CornerTreatment bottomRightCorner = new RoundedCornerTreatment();
        public CornerTreatment bottomLeftCorner = new RoundedCornerTreatment();
        public CornerSize topLeftCornerSize = new AbsoluteCornerSize(0.0f);
        public CornerSize topRightCornerSize = new AbsoluteCornerSize(0.0f);
        public CornerSize bottomRightCornerSize = new AbsoluteCornerSize(0.0f);
        public CornerSize bottomLeftCornerSize = new AbsoluteCornerSize(0.0f);
        public EdgeTreatment topEdge = new EdgeTreatment();
        public EdgeTreatment rightEdge = new EdgeTreatment();
        public EdgeTreatment bottomEdge = new EdgeTreatment();
        public EdgeTreatment leftEdge = new EdgeTreatment();

        public static void compatCornerTreatmentSize(CornerTreatment cornerTreatment) {
            if (cornerTreatment instanceof RoundedCornerTreatment) {
                ((RoundedCornerTreatment) cornerTreatment).getClass();
            } else if (cornerTreatment instanceof CutCornerTreatment) {
                ((CutCornerTreatment) cornerTreatment).getClass();
            }
        }

        public final ShapeAppearanceModel build() {
            ShapeAppearanceModel shapeAppearanceModel = new ShapeAppearanceModel();
            shapeAppearanceModel.topLeftCorner = this.topLeftCorner;
            shapeAppearanceModel.topRightCorner = this.topRightCorner;
            shapeAppearanceModel.bottomRightCorner = this.bottomRightCorner;
            shapeAppearanceModel.bottomLeftCorner = this.bottomLeftCorner;
            shapeAppearanceModel.topLeftCornerSize = this.topLeftCornerSize;
            shapeAppearanceModel.topRightCornerSize = this.topRightCornerSize;
            shapeAppearanceModel.bottomRightCornerSize = this.bottomRightCornerSize;
            shapeAppearanceModel.bottomLeftCornerSize = this.bottomLeftCornerSize;
            shapeAppearanceModel.topEdge = this.topEdge;
            shapeAppearanceModel.rightEdge = this.rightEdge;
            shapeAppearanceModel.bottomEdge = this.bottomEdge;
            shapeAppearanceModel.leftEdge = this.leftEdge;
            return shapeAppearanceModel;
        }
    }

    public static Builder builder(Context context, AttributeSet attributeSet, int i, int i2) {
        AbsoluteCornerSize absoluteCornerSize = new AbsoluteCornerSize(0);
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R$styleable.MaterialShape, i, i2);
        int resourceId = obtainStyledAttributes.getResourceId(0, 0);
        int resourceId2 = obtainStyledAttributes.getResourceId(1, 0);
        obtainStyledAttributes.recycle();
        return builder(context, resourceId, resourceId2, absoluteCornerSize);
    }

    public static CornerSize getCornerSize(TypedArray typedArray, int i, CornerSize cornerSize) {
        TypedValue peekValue = typedArray.peekValue(i);
        if (peekValue == null) {
            return cornerSize;
        }
        int i2 = peekValue.type;
        return i2 == 5
                ? new AbsoluteCornerSize(
                        TypedValue.complexToDimensionPixelSize(
                                peekValue.data, typedArray.getResources().getDisplayMetrics()))
                : i2 == 6 ? new RelativeCornerSize(peekValue.getFraction(1.0f, 1.0f)) : cornerSize;
    }

    public final boolean isRoundRect(RectF rectF) {
        boolean z =
                this.leftEdge.getClass().equals(EdgeTreatment.class)
                        && this.rightEdge.getClass().equals(EdgeTreatment.class)
                        && this.topEdge.getClass().equals(EdgeTreatment.class)
                        && this.bottomEdge.getClass().equals(EdgeTreatment.class);
        float cornerSize = this.topLeftCornerSize.getCornerSize(rectF);
        return z
                && ((this.topRightCornerSize.getCornerSize(rectF) > cornerSize
                                        ? 1
                                        : (this.topRightCornerSize.getCornerSize(rectF)
                                                        == cornerSize
                                                ? 0
                                                : -1))
                                == 0
                        && (this.bottomLeftCornerSize.getCornerSize(rectF) > cornerSize
                                        ? 1
                                        : (this.bottomLeftCornerSize.getCornerSize(rectF)
                                                        == cornerSize
                                                ? 0
                                                : -1))
                                == 0
                        && (this.bottomRightCornerSize.getCornerSize(rectF) > cornerSize
                                        ? 1
                                        : (this.bottomRightCornerSize.getCornerSize(rectF)
                                                        == cornerSize
                                                ? 0
                                                : -1))
                                == 0)
                && ((this.topRightCorner instanceof RoundedCornerTreatment)
                        && (this.topLeftCorner instanceof RoundedCornerTreatment)
                        && (this.bottomRightCorner instanceof RoundedCornerTreatment)
                        && (this.bottomLeftCorner instanceof RoundedCornerTreatment));
    }

    public final Builder toBuilder() {
        Builder builder = new Builder();
        builder.topLeftCorner = this.topLeftCorner;
        builder.topRightCorner = this.topRightCorner;
        builder.bottomRightCorner = this.bottomRightCorner;
        builder.bottomLeftCorner = this.bottomLeftCorner;
        builder.topLeftCornerSize = this.topLeftCornerSize;
        builder.topRightCornerSize = this.topRightCornerSize;
        builder.bottomRightCornerSize = this.bottomRightCornerSize;
        builder.bottomLeftCornerSize = this.bottomLeftCornerSize;
        builder.topEdge = this.topEdge;
        builder.rightEdge = this.rightEdge;
        builder.bottomEdge = this.bottomEdge;
        builder.leftEdge = this.leftEdge;
        return builder;
    }

    public static Builder builder(Context context, int i, int i2, CornerSize cornerSize) {
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, i);
        if (i2 != 0) {
            contextThemeWrapper = new ContextThemeWrapper(contextThemeWrapper, i2);
        }
        TypedArray obtainStyledAttributes =
                contextThemeWrapper.obtainStyledAttributes(R$styleable.ShapeAppearance);
        try {
            int i3 = obtainStyledAttributes.getInt(0, 0);
            int i4 = obtainStyledAttributes.getInt(3, i3);
            int i5 = obtainStyledAttributes.getInt(4, i3);
            int i6 = obtainStyledAttributes.getInt(2, i3);
            int i7 = obtainStyledAttributes.getInt(1, i3);
            CornerSize cornerSize2 = getCornerSize(obtainStyledAttributes, 5, cornerSize);
            CornerSize cornerSize3 = getCornerSize(obtainStyledAttributes, 8, cornerSize2);
            CornerSize cornerSize4 = getCornerSize(obtainStyledAttributes, 9, cornerSize2);
            CornerSize cornerSize5 = getCornerSize(obtainStyledAttributes, 7, cornerSize2);
            CornerSize cornerSize6 = getCornerSize(obtainStyledAttributes, 6, cornerSize2);
            Builder builder = new Builder();
            CornerTreatment createCornerTreatment = MaterialShapeUtils.createCornerTreatment(i4);
            builder.topLeftCorner = createCornerTreatment;
            Builder.compatCornerTreatmentSize(createCornerTreatment);
            builder.topLeftCornerSize = cornerSize3;
            CornerTreatment createCornerTreatment2 = MaterialShapeUtils.createCornerTreatment(i5);
            builder.topRightCorner = createCornerTreatment2;
            Builder.compatCornerTreatmentSize(createCornerTreatment2);
            builder.topRightCornerSize = cornerSize4;
            CornerTreatment createCornerTreatment3 = MaterialShapeUtils.createCornerTreatment(i6);
            builder.bottomRightCorner = createCornerTreatment3;
            Builder.compatCornerTreatmentSize(createCornerTreatment3);
            builder.bottomRightCornerSize = cornerSize5;
            CornerTreatment createCornerTreatment4 = MaterialShapeUtils.createCornerTreatment(i7);
            builder.bottomLeftCorner = createCornerTreatment4;
            Builder.compatCornerTreatmentSize(createCornerTreatment4);
            builder.bottomLeftCornerSize = cornerSize6;
            return builder;
        } finally {
            obtainStyledAttributes.recycle();
        }
    }
}
