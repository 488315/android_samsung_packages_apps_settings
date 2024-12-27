package com.samsung.android.sdk.command.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.sdk.command.Command;
import com.samsung.android.settings.actions.SettingsCommandActionHandler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class CommandProvider extends ContentProvider {
    public static final String[] WELL_KNOWN_CALLING_PACKAGES = {
        "com.android.settings.intelligence",
        KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
        "com.samsung.android.app.routines",
        "com.samsung.android.app.settings.bixby",
        "com.samsung.accessibility",
        "com.samsung.android.app.galaxyfinder",
        "com.samsung.android.app.galaxyregistry",
        "com.sec.android.app.launcher"
    };
    public static final String[] CORE_SYSTEM_PACKAGES = {
        "com.android.settings.intelligence",
        "com.samsung.android.app.galaxyfinder",
        "com.samsung.android.app.galaxyregistry",
        "com.sec.android.app.launcher"
    };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.sdk.command.provider.CommandProvider$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public final /* synthetic */ Bundle val$bundle;
        public final /* synthetic */ String val$commandId;
        public final /* synthetic */ SettingsCommandActionHandler val$handler;

        public AnonymousClass1(
                Bundle bundle,
                SettingsCommandActionHandler settingsCommandActionHandler,
                String str) {
            this.val$bundle = bundle;
            this.val$handler = settingsCommandActionHandler;
            this.val$commandId = str;
        }

        public final void onActionFinished(int i, String str) {
            this.val$bundle.putInt("response_code", i);
            this.val$bundle.putString("response_message", str);
            Command loadStatefulCommand = this.val$handler.loadStatefulCommand(this.val$commandId);
            if (loadStatefulCommand != null) {
                this.val$bundle.putBundle("command", loadStatefulCommand.getDataBundle());
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Can't wrap try/catch for region: R(9:(3:133|134|(9:136|(1:138)|(5:113|114|115|116|117)(1:86)|87|88|89|90|91|(2:93|94)))|84|(0)(0)|87|88|89|90|91|(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x04ca, code lost:

       r9.putInt("response_code", r11);
       r0 = "CommandProvider";
       r2 = new java.lang.StringBuilder("failed to load a command : ");
    */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x0493, code lost:

       r0 = th;
    */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x0494, code lost:

       r12 = r29;
    */
    /* JADX WARN: Code restructure failed: missing block: B:167:0x02e3, code lost:

       if (r12.containsKey("response_code") == false) goto L118;
    */
    /* JADX WARN: Code restructure failed: missing block: B:168:0x02e5, code lost:

       r12.putInt("response_code", 2);
       com.samsung.android.sdk.command.util.LogWrapper.e("CommandProvider", "failed to load all commands");
    */
    /* JADX WARN: Code restructure failed: missing block: B:174:0x0315, code lost:

       if (r12.containsKey("response_code") != false) goto L51;
    */
    /* JADX WARN: Code restructure failed: missing block: B:190:0x0148, code lost:

       if (r12.containsKey("response_code") == false) goto L63;
    */
    /* JADX WARN: Code restructure failed: missing block: B:191:0x014a, code lost:

       r12.putInt("response_code", 2);
       com.samsung.android.sdk.command.util.LogWrapper.e("CommandProvider", "cannot create command list");
    */
    /* JADX WARN: Code restructure failed: missing block: B:197:0x0179, code lost:

       if (r12.containsKey("response_code") != false) goto L51;
    */
    /* JADX WARN: Code restructure failed: missing block: B:218:0x00e1, code lost:

       if (r28.equals("method_LOAD") == false) goto L29;
    */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x0497, code lost:

       r0 = e;
    */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x0498, code lost:

       r12 = r29;
    */
    /* JADX WARN: Removed duplicated region for block: B:103:0x04ca  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x04e7  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0412 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x051d  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0457  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0476  */
    @Override // android.content.ContentProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.Bundle call(
            java.lang.String r28, java.lang.String r29, android.os.Bundle r30) {
        /*
            Method dump skipped, instructions count: 1422
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.sdk.command.provider.CommandProvider.call(java.lang.String,"
                    + " java.lang.String, android.os.Bundle):android.os.Bundle");
    }

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        return false;
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }
}
