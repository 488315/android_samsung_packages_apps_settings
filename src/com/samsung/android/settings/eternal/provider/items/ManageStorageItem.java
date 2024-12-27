package com.samsung.android.settings.eternal.provider.items;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.samsung.android.lib.episode.Scene;
import com.samsung.android.lib.episode.SceneResult;
import com.samsung.android.lib.episode.SourceInfo;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ManageStorageItem implements Recoverable {
    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final boolean followRestoreSkipPolicy(Scene scene) {
        return true;
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final List getTestScenes(Context context, String str) {
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final Scene.Builder getValue(Context context, SourceInfo sourceInfo, String str) {
        Scene.Builder builder = new Scene.Builder(str);
        str.getClass();
        if (str.equals("/Settings/ManageStorage/UnusedApps")) {
            int i =
                    PreferenceManager.getDefaultSharedPreferences(context)
                            .getInt("sort_type_unused_apps", 0);
            boolean z =
                    context.getSharedPreferences(
                                    PreferenceManager.getDefaultSharedPreferencesName(context), 0)
                            .getBoolean("sort_order_unused_apps", false);
            builder.addAttribute(Integer.valueOf(i), "sortTypeUnusedApps");
            builder.addAttribute(Boolean.valueOf(z), "sortOrderUnusedApps");
            int i2 = (i != 0 || z) ? 1 : 0;
            builder.setValue(Integer.valueOf(i2), false);
            builder.setDefault(i2 ^ 1);
        }
        return builder;
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final SceneResult isOpenable(Context context, String str) {
        return null;
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final SceneResult setValue(
            Context context, String str, Scene scene, SourceInfo sourceInfo) {
        str.getClass();
        if (str.equals("/Settings/ManageStorage/UnusedApps")) {
            int attributeInt = scene.getAttributeInt(0, "sortTypeUnusedApps");
            boolean attributeBoolean = scene.getAttributeBoolean("sortOrderUnusedApps");
            SharedPreferences.Editor edit =
                    PreferenceManager.getDefaultSharedPreferences(context).edit();
            edit.putInt("sort_type_unused_apps", attributeInt);
            edit.apply();
            SharedPreferences.Editor edit2 =
                    PreferenceManager.getDefaultSharedPreferences(context).edit();
            edit2.putBoolean("sort_order_unused_apps", attributeBoolean);
            edit2.apply();
        }
        SceneResult.Builder builder = new SceneResult.Builder(str);
        builder.mResultType = SceneResult.ResultType.RESULT_OK;
        return builder.build();
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final void open(Context context, String str) {}
}
