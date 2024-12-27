package com.facebook.shimmer;

import android.content.res.TypedArray;
import android.graphics.RectF;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.viewpager2.widget.ViewPager2$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class Shimmer {
    public boolean alphaShimmer;
    public long animationDuration;
    public boolean autoStart;
    public int baseColor;
    public boolean clipToChildren;
    public int direction;
    public float dropoff;
    public int fixedHeight;
    public int fixedWidth;
    public float heightRatio;
    public int highlightColor;
    public float intensity;
    public int repeatCount;
    public long repeatDelay;
    public int repeatMode;
    public int shape;
    public float tilt;
    public float widthRatio;
    public final float[] positions = new float[4];
    public final int[] colors = new int[4];

    public Shimmer() {
        new RectF();
        this.direction = 0;
        this.highlightColor = -1;
        this.baseColor = 1291845631;
        this.shape = 0;
        this.fixedWidth = 0;
        this.fixedHeight = 0;
        this.widthRatio = 1.0f;
        this.heightRatio = 1.0f;
        this.intensity = 0.0f;
        this.dropoff = 0.5f;
        this.tilt = 20.0f;
        this.clipToChildren = true;
        this.autoStart = true;
        this.alphaShimmer = true;
        this.repeatCount = -1;
        this.repeatMode = 1;
        this.animationDuration = 1000L;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AlphaHighlightBuilder {
        public final /* synthetic */ int $r8$classId;
        public final Shimmer mShimmer;

        public AlphaHighlightBuilder(byte b) {
            this.mShimmer = new Shimmer();
        }

        public final Shimmer build() {
            Shimmer shimmer = this.mShimmer;
            int i = shimmer.shape;
            int[] iArr = shimmer.colors;
            if (i != 1) {
                int i2 = shimmer.baseColor;
                iArr[0] = i2;
                int i3 = shimmer.highlightColor;
                iArr[1] = i3;
                iArr[2] = i3;
                iArr[3] = i2;
            } else {
                int i4 = shimmer.highlightColor;
                iArr[0] = i4;
                iArr[1] = i4;
                int i5 = shimmer.baseColor;
                iArr[2] = i5;
                iArr[3] = i5;
            }
            float[] fArr = shimmer.positions;
            if (i != 1) {
                fArr[0] = Math.max(((1.0f - shimmer.intensity) - shimmer.dropoff) / 2.0f, 0.0f);
                fArr[1] = Math.max(((1.0f - shimmer.intensity) - 0.001f) / 2.0f, 0.0f);
                fArr[2] = Math.min(((shimmer.intensity + 1.0f) + 0.001f) / 2.0f, 1.0f);
                fArr[3] = Math.min(((shimmer.intensity + 1.0f) + shimmer.dropoff) / 2.0f, 1.0f);
            } else {
                fArr[0] = 0.0f;
                fArr[1] = Math.min(shimmer.intensity, 1.0f);
                fArr[2] = Math.min(shimmer.intensity + shimmer.dropoff, 1.0f);
                fArr[3] = 1.0f;
            }
            return shimmer;
        }

        /* JADX WARN: Failed to find 'out' block for switch in B:97:0x01f4. Please report as an issue. */
        public final AlphaHighlightBuilder consumeAttributes$com$facebook$shimmer$Shimmer$Builder(
                TypedArray typedArray) {
            boolean hasValue = typedArray.hasValue(3);
            Shimmer shimmer = this.mShimmer;
            if (hasValue) {
                shimmer.clipToChildren = typedArray.getBoolean(3, shimmer.clipToChildren);
            }
            if (typedArray.hasValue(0)) {
                shimmer.autoStart = typedArray.getBoolean(0, shimmer.autoStart);
            }
            if (typedArray.hasValue(1)) {
                shimmer.baseColor =
                        (((int)
                                                (Math.min(
                                                                1.0f,
                                                                Math.max(
                                                                        0.0f,
                                                                        typedArray.getFloat(
                                                                                1, 0.3f)))
                                                        * 255.0f))
                                        << 24)
                                | (shimmer.baseColor & 16777215);
            }
            if (typedArray.hasValue(11)) {
                shimmer.highlightColor =
                        (((int)
                                                (Math.min(
                                                                1.0f,
                                                                Math.max(
                                                                        0.0f,
                                                                        typedArray.getFloat(
                                                                                11, 1.0f)))
                                                        * 255.0f))
                                        << 24)
                                | (shimmer.highlightColor & 16777215);
            }
            if (typedArray.hasValue(7)) {
                long j = typedArray.getInt(7, (int) shimmer.animationDuration);
                if (j < 0) {
                    throw new IllegalArgumentException(
                            ViewPager2$$ExternalSyntheticOutline0.m(
                                    j, "Given a negative duration: "));
                }
                shimmer.animationDuration = j;
            }
            if (typedArray.hasValue(14)) {
                shimmer.repeatCount = typedArray.getInt(14, shimmer.repeatCount);
            }
            if (typedArray.hasValue(15)) {
                long j2 = typedArray.getInt(15, (int) shimmer.repeatDelay);
                if (j2 < 0) {
                    throw new IllegalArgumentException(
                            ViewPager2$$ExternalSyntheticOutline0.m(
                                    j2, "Given a negative repeat delay: "));
                }
                shimmer.repeatDelay = j2;
            }
            if (typedArray.hasValue(16)) {
                shimmer.repeatMode = typedArray.getInt(16, shimmer.repeatMode);
            }
            if (typedArray.hasValue(5)) {
                int i = typedArray.getInt(5, shimmer.direction);
                if (i == 1) {
                    shimmer.direction = 1;
                } else if (i == 2) {
                    shimmer.direction = 2;
                } else if (i != 3) {
                    shimmer.direction = 0;
                } else {
                    shimmer.direction = 3;
                }
            }
            if (typedArray.hasValue(17)) {
                if (typedArray.getInt(17, shimmer.shape) != 1) {
                    shimmer.shape = 0;
                } else {
                    shimmer.shape = 1;
                }
            }
            if (typedArray.hasValue(6)) {
                float f = typedArray.getFloat(6, shimmer.dropoff);
                if (f < 0.0f) {
                    throw new IllegalArgumentException("Given invalid dropoff value: " + f);
                }
                shimmer.dropoff = f;
            }
            if (typedArray.hasValue(9)) {
                int dimensionPixelSize = typedArray.getDimensionPixelSize(9, shimmer.fixedWidth);
                if (dimensionPixelSize < 0) {
                    throw new IllegalArgumentException(
                            SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                    dimensionPixelSize, "Given invalid width: "));
                }
                shimmer.fixedWidth = dimensionPixelSize;
            }
            if (typedArray.hasValue(8)) {
                int dimensionPixelSize2 = typedArray.getDimensionPixelSize(8, shimmer.fixedHeight);
                if (dimensionPixelSize2 < 0) {
                    throw new IllegalArgumentException(
                            SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                    dimensionPixelSize2, "Given invalid height: "));
                }
                shimmer.fixedHeight = dimensionPixelSize2;
            }
            if (typedArray.hasValue(13)) {
                float f2 = typedArray.getFloat(13, shimmer.intensity);
                if (f2 < 0.0f) {
                    throw new IllegalArgumentException("Given invalid intensity value: " + f2);
                }
                shimmer.intensity = f2;
            }
            if (typedArray.hasValue(19)) {
                float f3 = typedArray.getFloat(19, shimmer.widthRatio);
                if (f3 < 0.0f) {
                    throw new IllegalArgumentException("Given invalid width ratio: " + f3);
                }
                shimmer.widthRatio = f3;
            }
            if (typedArray.hasValue(10)) {
                float f4 = typedArray.getFloat(10, shimmer.heightRatio);
                if (f4 < 0.0f) {
                    throw new IllegalArgumentException("Given invalid height ratio: " + f4);
                }
                shimmer.heightRatio = f4;
            }
            if (typedArray.hasValue(18)) {
                shimmer.tilt = typedArray.getFloat(18, shimmer.tilt);
            }
            switch (this.$r8$classId) {
            }
            return this;
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public AlphaHighlightBuilder(int i) {
            this((byte) 0);
            this.$r8$classId = i;
            switch (i) {
                case 1:
                    this((byte) 0);
                    break;
                default:
                    this.mShimmer.alphaShimmer = true;
                    break;
            }
        }
    }
}
