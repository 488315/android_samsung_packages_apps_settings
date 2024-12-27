package com.samsung.android.settings.gts;

import android.util.Log;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;

import java.util.function.BiConsumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class GtsFeatureProviderImpl$$ExternalSyntheticLambda1
        implements BiConsumer {
    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        StringBuilder m =
                ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                        "Key : ", (String) obj, " / Gts : ");
        m.append(((GtsGroup) obj2).getGroupName());
        Log.i("GtsFeatureProviderImpl", m.toString());
    }
}
