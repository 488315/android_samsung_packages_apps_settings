package com.samsung.android.settings.eternal.provider.items;

import android.content.Context;

import com.android.internal.widget.LockPatternUtils;

import com.samsung.android.lib.episode.Scene;
import com.samsung.android.lib.episode.SceneResult;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class LockScreenItem implements Recoverable {
    public LockPatternUtils mLockPatternUtils;

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final boolean followRestoreSkipPolicy(Scene scene) {
        return ("/Settings/LockScreen/ShowShortcut".equals(scene.mSceneKey)
                        || "/Settings/LockScreen/NotificationIconOnly".equals(scene.mSceneKey)
                        || "/Settings/LockScreen/ShowNotification".equals(scene.mSceneKey)
                        || "/Settings/LockScreen/ShowNotificationOnKeyguard".equals(scene.mSceneKey)
                        || "/Settings/LockScreen/HideContent".equals(scene.mSceneKey))
                ? false
                : true;
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final List getTestScenes(Context context, String str) {
        return null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x0514, code lost:

       return r3;
    */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x027c, code lost:

       if ((-1) == r13) goto L160;
    */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x027e, code lost:

       r2 = true;
    */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0281, code lost:

       r12 = r12 & r2;
    */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0280, code lost:

       r2 = false;
    */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x02a5, code lost:

       if ((-1) == r2) goto L160;
    */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x02b8, code lost:

       if (1 == r2) goto L160;
    */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x02cb, code lost:

       if ((-1) == r2) goto L160;
    */
    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.samsung.android.lib.episode.Scene.Builder getValue(
            android.content.Context r17,
            com.samsung.android.lib.episode.SourceInfo r18,
            java.lang.String r19) {
        /*
            Method dump skipped, instructions count: 1488
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.eternal.provider.items.LockScreenItem.getValue(android.content.Context,"
                    + " com.samsung.android.lib.episode.SourceInfo,"
                    + " java.lang.String):com.samsung.android.lib.episode.Scene$Builder");
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final SceneResult isOpenable(Context context, String str) {
        return null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x0361, code lost:

       return null;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v10 */
    /* JADX WARN: Type inference failed for: r7v18 */
    /* JADX WARN: Type inference failed for: r7v3 */
    /* JADX WARN: Type inference failed for: r7v6 */
    /* JADX WARN: Type inference failed for: r7v9 */
    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.samsung.android.lib.episode.SceneResult setValue(
            android.content.Context r11,
            java.lang.String r12,
            com.samsung.android.lib.episode.Scene r13,
            com.samsung.android.lib.episode.SourceInfo r14) {
        /*
            Method dump skipped, instructions count: 1052
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.eternal.provider.items.LockScreenItem.setValue(android.content.Context,"
                    + " java.lang.String, com.samsung.android.lib.episode.Scene,"
                    + " com.samsung.android.lib.episode.SourceInfo):com.samsung.android.lib.episode.SceneResult");
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final void open(Context context, String str) {}
}
