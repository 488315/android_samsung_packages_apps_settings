package com.samsung.android.settings.eternal;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.lib.episode.Scene;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.eternal.provider.items.RecoverableItemFactory;
import com.samsung.android.util.SemLog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BackupFeatureProviderImpl {
    public final Bundle getValue(Context context, String str) {
        String[] split;
        String str2 =
                (TextUtils.isEmpty(str) || (split = str.split("/")) == null || split.length <= 2)
                        ? ApnSettings.MVNO_NONE
                        : split[2];
        if (TextUtils.isEmpty(str2)) {
            return null;
        }
        Scene build =
                RecoverableItemFactory.getItem(context, str2)
                        .getValue(context, ControlValue.buildEmptySourceInfo(), str)
                        .build();
        if (build != null) {
            return build.mSceneValue;
        }
        SemLog.e("BackupFeatureProviderImpl", "getValue() failed to build a scene for : " + str);
        return null;
    }
}
