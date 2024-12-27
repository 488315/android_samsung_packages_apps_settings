package com.google.android.material.shape;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class CutCornerTreatment extends CornerTreatment {
    @Override // com.google.android.material.shape.CornerTreatment
    public final void getCornerPath(ShapePath shapePath, float f, float f2) {
        shapePath.reset(f2 * f, 180.0f, 90.0f);
        double d = f2;
        double d2 = f;
        shapePath.lineTo(
                (float) (Math.sin(Math.toRadians(90.0f)) * d * d2),
                (float) (Math.sin(Math.toRadians(0.0f)) * d * d2));
    }
}
