package com.samsung.android.settings.accessibility.advanced.shortcut;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.view.accessibility.AccessibilityManager;

import com.samsung.android.settings.accessibility.AccessibilityConstant;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class ShortcutContentObserver extends ContentObserver {
    public static final /* synthetic */ int $r8$clinit = 0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.accessibility.advanced.shortcut.ShortcutContentObserver$1, reason: invalid class name */
    public final class AnonymousClass1 extends ShortcutContentObserver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Context val$context;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public /* synthetic */ AnonymousClass1(Handler handler, Context context, int i) {
            super(handler);
            this.$r8$classId = i;
            this.val$context = context;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            switch (this.$r8$classId) {
                case 0:
                    int i =
                            SecAccessibilityUtils.getValuesInSettings(
                                                    this.val$context,
                                                    "com.android.server.accessibility.MagnificationController")
                                            != 0
                                    ? 1
                                    : 0;
                    Settings.Secure.putInt(
                            this.val$context.getContentResolver(),
                            "accessibility_magnification_shortcut",
                            i);
                    if (i == 0) {
                        ((AccessibilityManager) this.val$context.getSystemService("accessibility"))
                                .setMagnificationDisactivate();
                        break;
                    }
                    break;
                case 1:
                    int i2 =
                            SecAccessibilityUtils.getValuesInSettings(
                                                    this.val$context,
                                                    AccessibilityConstant
                                                            .COMPONENT_NAME_AMPLIFY_AMBIENT_SOUND_SHORTCUT
                                                            .flattenToString())
                                            != 0
                                    ? 1
                                    : 0;
                    Context context = this.val$context;
                    Intent intent = new Intent();
                    intent.setClassName(
                            "com.samsung.accessibility",
                            "com.samsung.accessibility.amplifyambientsound.AmplifyAmbientSoundService");
                    intent.setPackage("com.samsung.accessibility");
                    if (i2 != 0) {
                        context.startService(intent);
                    } else if (Settings.System.getInt(
                                    context.getContentResolver(), "run_amplify_ambient_sound", 0)
                            == 1) {
                        Intent intent2 = new Intent();
                        intent2.setAction("com.samsung.accessibility.AMPLIFY_AMBIENT_SOUND_TOGGLE");
                        intent2.putExtra("isEnabled", false);
                        intent2.putExtra("stopself", true);
                        context.sendBroadcast(intent2);
                    } else {
                        context.stopService(intent);
                    }
                    Settings.System.putInt(
                            context.getContentResolver(), "amplify_ambient_sound", i2);
                    break;
                default:
                    Settings.System.putInt(
                            this.val$context.getContentResolver(),
                            "access_control_use",
                            SecAccessibilityUtils.getValuesInSettings(
                                                    this.val$context,
                                                    AccessibilityConstant
                                                            .COMPONENT_NAME_INTERACTION_CONTROL_SHORTCUT
                                                            .flattenToShortString())
                                            != 0
                                    ? 1
                                    : 0);
                    break;
            }
        }
    }

    public final void register(ContentResolver contentResolver) {
        contentResolver.registerContentObserver(
                Settings.Secure.getUriFor("accessibility_button_targets"), false, this);
        contentResolver.registerContentObserver(
                Settings.Secure.getUriFor("accessibility_shortcut_target_service"), false, this);
        contentResolver.registerContentObserver(
                Settings.Secure.getUriFor("accessibility_display_magnification_enabled"),
                false,
                this);
        contentResolver.registerContentObserver(
                Settings.Secure.getUriFor("accessibility_direct_access_target_service"),
                false,
                this);
        contentResolver.registerContentObserver(
                Settings.Secure.getUriFor("accessibility_qs_targets"), false, this);
    }
}
