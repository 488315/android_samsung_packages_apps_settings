package com.samsung.android.settings.eternal.provider.items;

import android.content.Context;

import com.samsung.android.lib.episode.Scene;
import com.samsung.android.lib.episode.SceneResult;
import com.samsung.android.lib.episode.SourceInfo;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public interface Recoverable {
    boolean followRestoreSkipPolicy(Scene scene);

    List getTestScenes(Context context, String str);

    Scene.Builder getValue(Context context, SourceInfo sourceInfo, String str);

    SceneResult isOpenable(Context context, String str);

    void open(Context context, String str);

    SceneResult setValue(Context context, String str, Scene scene, SourceInfo sourceInfo);
}
