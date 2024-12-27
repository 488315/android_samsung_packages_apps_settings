package com.google.zxing.common;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class PerspectiveTransform {
    public final float a11;
    public final float a12;
    public final float a13;
    public final float a21;
    public final float a22;
    public final float a23;
    public final float a31;
    public final float a32;
    public final float a33;

    public PerspectiveTransform(
            float f,
            float f2,
            float f3,
            float f4,
            float f5,
            float f6,
            float f7,
            float f8,
            float f9) {
        this.a11 = f;
        this.a12 = f4;
        this.a13 = f7;
        this.a21 = f2;
        this.a22 = f5;
        this.a23 = f8;
        this.a31 = f3;
        this.a32 = f6;
        this.a33 = f9;
    }

    public static PerspectiveTransform quadrilateralToQuadrilateral(
            float f,
            float f2,
            float f3,
            float f4,
            float f5,
            float f6,
            float f7,
            float f8,
            float f9,
            float f10,
            float f11,
            float f12,
            float f13,
            float f14,
            float f15,
            float f16) {
        PerspectiveTransform squareToQuadrilateral =
                squareToQuadrilateral(f, f2, f3, f4, f5, f6, f7, f8);
        float f17 = squareToQuadrilateral.a22;
        float f18 = squareToQuadrilateral.a33;
        float f19 = squareToQuadrilateral.a23;
        float f20 = squareToQuadrilateral.a32;
        float f21 = (f17 * f18) - (f19 * f20);
        float f22 = squareToQuadrilateral.a31;
        float f23 = squareToQuadrilateral.a21;
        float f24 = (f19 * f22) - (f23 * f18);
        float f25 = (f23 * f20) - (f17 * f22);
        float f26 = squareToQuadrilateral.a13;
        float f27 = squareToQuadrilateral.a12;
        float f28 = (f26 * f20) - (f27 * f18);
        float f29 = squareToQuadrilateral.a11;
        float f30 = (f18 * f29) - (f26 * f22);
        float f31 = (f22 * f27) - (f20 * f29);
        float f32 = (f27 * f19) - (f26 * f17);
        float f33 = (f26 * f23) - (f19 * f29);
        float f34 = (f29 * f17) - (f27 * f23);
        PerspectiveTransform squareToQuadrilateral2 =
                squareToQuadrilateral(f9, f10, f11, f12, f13, f14, f15, f16);
        float f35 = squareToQuadrilateral2.a11;
        float f36 = squareToQuadrilateral2.a21;
        float f37 = squareToQuadrilateral2.a31;
        float f38 = (f36 * f28) + (f35 * f21) + (f37 * f32);
        float f39 = (f37 * f33) + (f36 * f30) + (f35 * f24);
        float f40 = f37 * f34;
        float f41 = f40 + (f36 * f31) + (f35 * f25);
        float f42 = squareToQuadrilateral2.a12;
        float f43 = squareToQuadrilateral2.a22;
        float f44 = squareToQuadrilateral2.a32;
        float f45 = (f44 * f32) + (f43 * f28) + (f42 * f21);
        float f46 = (f44 * f33) + (f43 * f30) + (f42 * f24);
        float f47 = (f43 * f31) + (f42 * f25) + (f44 * f34);
        float f48 = squareToQuadrilateral2.a13;
        float f49 = squareToQuadrilateral2.a23;
        float f50 = f28 * f49;
        float f51 = squareToQuadrilateral2.a33;
        return new PerspectiveTransform(
                f38,
                f39,
                f41,
                f45,
                f46,
                f47,
                (f32 * f51) + f50 + (f21 * f48),
                (f30 * f49) + (f24 * f48) + (f33 * f51),
                (f51 * f34) + (f49 * f31) + (f48 * f25));
    }

    public static PerspectiveTransform squareToQuadrilateral(
            float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        float f9 = ((f - f3) + f5) - f7;
        float f10 = ((f2 - f4) + f6) - f8;
        if (f9 == 0.0f && f10 == 0.0f) {
            return new PerspectiveTransform(
                    f3 - f, f5 - f3, f, f4 - f2, f6 - f4, f2, 0.0f, 0.0f, 1.0f);
        }
        float f11 = f3 - f5;
        float f12 = f7 - f5;
        float f13 = f4 - f6;
        float f14 = f8 - f6;
        float f15 = (f11 * f14) - (f12 * f13);
        float f16 = ((f14 * f9) - (f12 * f10)) / f15;
        float f17 = ((f11 * f10) - (f9 * f13)) / f15;
        return new PerspectiveTransform(
                (f16 * f3) + (f3 - f),
                (f17 * f7) + (f7 - f),
                f,
                (f16 * f4) + (f4 - f2),
                (f17 * f8) + (f8 - f2),
                f2,
                f16,
                f17,
                1.0f);
    }
}
