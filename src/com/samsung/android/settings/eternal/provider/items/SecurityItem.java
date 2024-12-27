package com.samsung.android.settings.eternal.provider.items;

import android.content.Context;

import com.samsung.android.lib.episode.Scene;
import com.samsung.android.lib.episode.SceneResult;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecurityItem implements Recoverable {
    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final boolean followRestoreSkipPolicy(Scene scene) {
        return true;
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final List getTestScenes(Context context, String str) {
        return null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00b4, code lost:

       return r3;
    */
    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.samsung.android.lib.episode.Scene.Builder getValue(
            android.content.Context r4,
            com.samsung.android.lib.episode.SourceInfo r5,
            java.lang.String r6) {
        /*
            r3 = this;
            com.samsung.android.lib.episode.Scene$Builder r3 = new com.samsung.android.lib.episode.Scene$Builder
            r3.<init>(r6)
            android.content.ContentResolver r4 = r4.getContentResolver()
            r6.getClass()
            r5 = 1
            r0 = 0
            r1 = -1
            int r2 = r6.hashCode()
            switch(r2) {
                case -1559170289: goto L43;
                case -906327805: goto L38;
                case 500580953: goto L2d;
                case 1183591744: goto L22;
                case 1253580727: goto L17;
                default: goto L16;
            }
        L16:
            goto L4d
        L17:
            java.lang.String r2 = "/Settings/Security/MakePasswordVisible"
            boolean r6 = r6.equals(r2)
            if (r6 != 0) goto L20
            goto L4d
        L20:
            r1 = 4
            goto L4d
        L22:
            java.lang.String r2 = "/Settings/Security/PinWindows"
            boolean r6 = r6.equals(r2)
            if (r6 != 0) goto L2b
            goto L4d
        L2b:
            r1 = 3
            goto L4d
        L2d:
            java.lang.String r2 = "/Settings/Security/GalaxySystemAppUpdate"
            boolean r6 = r6.equals(r2)
            if (r6 != 0) goto L36
            goto L4d
        L36:
            r1 = 2
            goto L4d
        L38:
            java.lang.String r2 = "/Settings/Security/AskPinBeforeUnpinning"
            boolean r6 = r6.equals(r2)
            if (r6 != 0) goto L41
            goto L4d
        L41:
            r1 = r5
            goto L4d
        L43:
            java.lang.String r2 = "/Settings/Security/GalaxySystemAppUpdateUseWiFiOnly"
            boolean r6 = r6.equals(r2)
            if (r6 != 0) goto L4c
            goto L4d
        L4c:
            r1 = r0
        L4d:
            switch(r1) {
                case 0: goto La0;
                case 1: goto L8b;
                case 2: goto L76;
                case 3: goto L61;
                case 4: goto L52;
                default: goto L50;
            }
        L50:
            goto Lb4
        L52:
            java.lang.String r6 = "show_password"
            int r4 = android.provider.Settings.System.getInt(r4, r6, r5)
            java.lang.String r4 = java.lang.String.valueOf(r4)
            r3.setValue(r4, r0)
            goto Lb4
        L61:
            java.lang.String r6 = "lock_to_app_enabled"
            int r4 = android.provider.Settings.System.getInt(r4, r6, r0)
            java.lang.Integer r6 = java.lang.Integer.valueOf(r4)
            r3.setValue(r6, r0)
            if (r4 != 0) goto L71
            goto L72
        L71:
            r5 = r0
        L72:
            r3.setDefault(r5)
            goto Lb4
        L76:
            java.lang.String r6 = "galaxy_system_update"
            int r4 = android.provider.Settings.Global.getInt(r4, r6, r5)
            java.lang.Integer r6 = java.lang.Integer.valueOf(r4)
            r3.setValue(r6, r0)
            if (r5 != r4) goto L86
            goto L87
        L86:
            r5 = r0
        L87:
            r3.setDefault(r5)
            goto Lb4
        L8b:
            java.lang.String r6 = "lock_to_app_exit_locked"
            int r4 = android.provider.Settings.Secure.getInt(r4, r6, r0)
            java.lang.Integer r6 = java.lang.Integer.valueOf(r4)
            r3.setValue(r6, r0)
            if (r4 != 0) goto L9b
            goto L9c
        L9b:
            r5 = r0
        L9c:
            r3.setDefault(r5)
            goto Lb4
        La0:
            java.lang.String r6 = "galaxy_system_update_use_wifi_only"
            int r4 = android.provider.Settings.Global.getInt(r4, r6, r5)
            java.lang.Integer r6 = java.lang.Integer.valueOf(r4)
            r3.setValue(r6, r0)
            if (r5 != r4) goto Lb0
            goto Lb1
        Lb0:
            r5 = r0
        Lb1:
            r3.setDefault(r5)
        Lb4:
            return r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.eternal.provider.items.SecurityItem.getValue(android.content.Context,"
                    + " com.samsung.android.lib.episode.SourceInfo,"
                    + " java.lang.String):com.samsung.android.lib.episode.Scene$Builder");
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final SceneResult isOpenable(Context context, String str) {
        return null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0052, code lost:

       if (r5.equals("/Settings/Security/GalaxySystemAppUpdateUseWiFiOnly") == false) goto L4;
    */
    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.samsung.android.lib.episode.SceneResult setValue(
            android.content.Context r4,
            java.lang.String r5,
            com.samsung.android.lib.episode.Scene r6,
            com.samsung.android.lib.episode.SourceInfo r7) {
        /*
            r3 = this;
            r3 = 0
            android.content.ContentResolver r7 = r4.getContentResolver()
            r0 = 0
            java.lang.String r6 = r6.getValue(r0, r3)
            com.samsung.android.lib.episode.SceneResult$Builder r0 = new com.samsung.android.lib.episode.SceneResult$Builder
            r0.<init>(r5)
            com.samsung.android.lib.episode.SceneResult$ResultType r1 = com.samsung.android.lib.episode.SceneResult.ResultType.RESULT_OK
            r0.mResultType = r1
            r5.getClass()
            r1 = -1
            int r2 = r5.hashCode()
            switch(r2) {
                case -1559170289: goto L4c;
                case -906327805: goto L41;
                case 500580953: goto L36;
                case 1183591744: goto L2b;
                case 1253580727: goto L20;
                default: goto L1e;
            }
        L1e:
            r3 = r1
            goto L55
        L20:
            java.lang.String r3 = "/Settings/Security/MakePasswordVisible"
            boolean r3 = r5.equals(r3)
            if (r3 != 0) goto L29
            goto L1e
        L29:
            r3 = 4
            goto L55
        L2b:
            java.lang.String r3 = "/Settings/Security/PinWindows"
            boolean r3 = r5.equals(r3)
            if (r3 != 0) goto L34
            goto L1e
        L34:
            r3 = 3
            goto L55
        L36:
            java.lang.String r3 = "/Settings/Security/GalaxySystemAppUpdate"
            boolean r3 = r5.equals(r3)
            if (r3 != 0) goto L3f
            goto L1e
        L3f:
            r3 = 2
            goto L55
        L41:
            java.lang.String r3 = "/Settings/Security/AskPinBeforeUnpinning"
            boolean r3 = r5.equals(r3)
            if (r3 != 0) goto L4a
            goto L1e
        L4a:
            r3 = 1
            goto L55
        L4c:
            java.lang.String r2 = "/Settings/Security/GalaxySystemAppUpdateUseWiFiOnly"
            boolean r5 = r5.equals(r2)
            if (r5 != 0) goto L55
            goto L1e
        L55:
            switch(r3) {
                case 0: goto L9a;
                case 1: goto L74;
                case 2: goto L66;
                case 3: goto L60;
                case 4: goto L59;
                default: goto L58;
            }
        L58:
            goto La7
        L59:
            java.lang.String r3 = "show_password"
            com.samsung.android.settings.asbase.vibration.SecSoundDeviceVibrationController$$ExternalSyntheticOutline0.m(r7, r6, r3)
            goto La7
        L60:
            java.lang.String r3 = "lock_to_app_enabled"
            com.samsung.android.settings.asbase.vibration.SecSoundDeviceVibrationController$$ExternalSyntheticOutline0.m(r7, r6, r3)
            goto La7
        L66:
            java.lang.Integer r3 = java.lang.Integer.valueOf(r6)
            int r3 = r3.intValue()
            java.lang.String r4 = "galaxy_system_update"
            android.provider.Settings.Global.putInt(r7, r4, r3)
            goto La7
        L74:
            com.android.internal.widget.LockPatternUtils r3 = new com.android.internal.widget.LockPatternUtils
            r3.<init>(r4)
            int r4 = android.os.UserHandle.myUserId()
            boolean r3 = r3.isSecure(r4)
            if (r3 == 0) goto L91
            java.lang.Integer r3 = java.lang.Integer.valueOf(r6)
            int r3 = r3.intValue()
            java.lang.String r4 = "lock_to_app_exit_locked"
            android.provider.Settings.Secure.putInt(r7, r4, r3)
            goto La7
        L91:
            com.samsung.android.lib.episode.SceneResult$ResultType r3 = com.samsung.android.lib.episode.SceneResult.ResultType.RESULT_FAIL
            r0.mResultType = r3
            com.samsung.android.lib.episode.SceneResult$ErrorType r3 = com.samsung.android.lib.episode.SceneResult.ErrorType.NOT_SUPPORTED
            r0.mErrorType = r3
            goto La7
        L9a:
            java.lang.Integer r3 = java.lang.Integer.valueOf(r6)
            int r3 = r3.intValue()
            java.lang.String r4 = "galaxy_system_update_use_wifi_only"
            android.provider.Settings.Global.putInt(r7, r4, r3)
        La7:
            com.samsung.android.lib.episode.SceneResult r3 = r0.build()
            return r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.eternal.provider.items.SecurityItem.setValue(android.content.Context,"
                    + " java.lang.String, com.samsung.android.lib.episode.Scene,"
                    + " com.samsung.android.lib.episode.SourceInfo):com.samsung.android.lib.episode.SceneResult");
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final void open(Context context, String str) {}
}
