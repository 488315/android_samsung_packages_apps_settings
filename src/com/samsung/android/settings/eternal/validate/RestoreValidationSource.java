package com.samsung.android.settings.eternal.validate;

import android.content.Context;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;

import com.samsung.android.gtscell.data.FieldName;
import com.samsung.android.lib.episode.Scene;
import com.samsung.android.lib.episode.SourceInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class RestoreValidationSource {
    public HashMap mBaseSceneMap;
    public HashMap mBaseSourceInfo;
    public HashMap mCompareSceneMap;
    public HashMap mCompareSourceInfo;
    public Context mContext;
    public HashMap mRestoreResultMap;
    public HashMap mSupplierMap;

    public static void generateSourceInfo(HashMap hashMap, HashMap hashMap2) {
        Scene build;
        if (hashMap == null || hashMap2 == null) {
            return;
        }
        for (Map.Entry entry : hashMap.entrySet()) {
            String str = (String) entry.getKey();
            List list = (List) entry.getValue();
            SourceInfo sourceInfo = (SourceInfo) hashMap2.get(str);
            if (sourceInfo == null) {
                build = null;
            } else {
                Scene.Builder builder =
                        new Scene.Builder(
                                ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                                "/", str, "/#EpisodeProviderInfo")
                                        .toString());
                builder.setValue(" ", false);
                builder.addAttribute(sourceInfo.mVersion, FieldName.VERSION);
                builder.addAttribute(sourceInfo.mDTDVersion, "dtd_version");
                build = builder.build();
            }
            if (list != null && build != null) {
                list.add(build);
            }
        }
    }
}
