package com.samsung.android.settings.cube;

import android.text.TextUtils;

import com.android.settings.MainClear$$ExternalSyntheticOutline0;

import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class CubeCallbackManager {
    public final HashMap mCubeFactoryHashMap = new HashMap();
    public CubeInteractionActivity mCubeInteractionActivity;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class LazyHolder {
        public static final CubeCallbackManager INSTANCE = new CubeCallbackManager();
    }

    public static String parsePreferenceKey(String str) {
        String[] split;
        if (TextUtils.isEmpty(str) || (split = str.split("@@")) == null || split.length != 2) {
            return null;
        }
        return split[0];
    }

    public final void notifyControlResult(String str, ControlResult controlResult) {
        if (this.mCubeFactoryHashMap.isEmpty()) {
            return;
        }
        if (TextUtils.isEmpty(str)) {
            MainClear$$ExternalSyntheticOutline0.m$1(
                    new StringBuilder("notifyControlFinished() contorlId is empty  / "),
                    controlResult.mKey,
                    "CubeCallbackManager");
        }
        for (Map.Entry entry : this.mCubeFactoryHashMap.entrySet()) {
            if (entry.getValue() != null) {
                ((CubeFactoryProvider) entry.getValue()).notifyControlResult(str, controlResult);
            }
        }
        CubeInteractionActivity cubeInteractionActivity = this.mCubeInteractionActivity;
        if (cubeInteractionActivity != null) {
            cubeInteractionActivity.notifyInteractionFinished();
        }
    }
}
