package com.android.settings.biometrics.face;

import android.content.Context;
import android.hardware.face.FaceManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FaceStatusUtils {
    public final Context mContext;
    public final FaceManager mFaceManager;
    public final int mUserId;

    public FaceStatusUtils(Context context, FaceManager faceManager, int i) {
        this.mContext = context;
        this.mFaceManager = faceManager;
        this.mUserId = i;
    }
}
