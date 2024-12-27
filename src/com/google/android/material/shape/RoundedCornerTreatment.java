package com.google.android.material.shape;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class RoundedCornerTreatment extends CornerTreatment {
    @Override // com.google.android.material.shape.CornerTreatment
    public final void getCornerPath(ShapePath shapePath, float f, float f2) {
        shapePath.reset(f2 * f, 180.0f, 90.0f);
        float f3 = f2 * 2.0f * f;
        ShapePath.PathArcOperation pathArcOperation =
                new ShapePath.PathArcOperation(0.0f, 0.0f, f3, f3);
        pathArcOperation.startAngle = 180.0f;
        pathArcOperation.sweepAngle = 90.0f;
        ((ArrayList) shapePath.operations).add(pathArcOperation);
        ShapePath.ArcShadowOperation arcShadowOperation =
                new ShapePath.ArcShadowOperation(pathArcOperation);
        shapePath.addConnectingShadowIfNecessary(180.0f);
        ((ArrayList) shapePath.shadowCompatOperations).add(arcShadowOperation);
        shapePath.currentShadowAngle = 270.0f;
        float f4 = (0.0f + f3) * 0.5f;
        float f5 = (f3 - 0.0f) / 2.0f;
        double d = 270.0f;
        shapePath.endX = (((float) Math.cos(Math.toRadians(d))) * f5) + f4;
        shapePath.endY = (f5 * ((float) Math.sin(Math.toRadians(d)))) + f4;
    }
}
