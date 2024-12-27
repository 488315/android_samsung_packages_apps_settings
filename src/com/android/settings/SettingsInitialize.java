package com.android.settings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.text.TextUtils;
import android.util.Log;

import com.samsung.android.settings.Rune;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class SettingsInitialize extends BroadcastReceiver {
    public static final /* synthetic */ int $r8$clinit = 0;

    /* JADX WARN: Removed duplicated region for block: B:35:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00d2 A[SYNTHETIC] */
    @Override // android.content.BroadcastReceiver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onReceive(final android.content.Context r13, android.content.Intent r14) {
        /*
            Method dump skipped, instructions count: 309
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.SettingsInitialize.onReceive(android.content.Context,"
                    + " android.content.Intent):void");
    }

    public void refreshExistingShortcuts(Context context) {
        ShortcutManager shortcutManager =
                (ShortcutManager) context.getSystemService(ShortcutManager.class);
        List<ShortcutInfo> pinnedShortcuts = shortcutManager.getPinnedShortcuts();
        ArrayList arrayList = new ArrayList();
        for (ShortcutInfo shortcutInfo : pinnedShortcuts) {
            if (!shortcutInfo.isImmutable()) {
                Intent intent = shortcutInfo.getIntent();
                intent.setFlags(335544320);
                if (Rune.isJapanModel() && TextUtils.equals("change_home", shortcutInfo.getId())) {
                    Log.i(
                            "Settings",
                            "replace change_home shortcut target component to"
                                + " PermissionController");
                    intent.setClassName(
                            "com.google.android.permissioncontroller",
                            "com.android.permissioncontroller.role.ui.DefaultAppActivity");
                    intent.putExtra("android.intent.extra.ROLE_NAME", "android.app.role.HOME");
                }
                arrayList.add(
                        new ShortcutInfo.Builder(context, shortcutInfo.getId())
                                .setIntent(intent)
                                .build());
            }
        }
        shortcutManager.updateShortcuts(arrayList);
    }
}
