package com.samsung.android.settings.accessibility.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.samsung.android.settings.accessibility.AccessibilityNotificationUtil;

import java.util.Set;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AccessibilityBroadcastReceiver extends BroadcastReceiver {
    public static final Set HANDLERS =
            (Set)
                    Set.of(
                                    "com.samsung.android.settings.accessibility.broadcast.AccessibilityShortcutReceiver")
                            .stream()
                            .map(new AccessibilityBroadcastReceiver$$ExternalSyntheticLambda2())
                            .filter(new AccessibilityBroadcastReceiver$$ExternalSyntheticLambda3())
                            .collect(Collectors.toSet());

    @Override // android.content.BroadcastReceiver
    public final void onReceive(final Context context, Intent intent) {
        String action = intent.getAction();
        if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
            Log.d("A11yBroadcastReceiver", "[onReceive] ACTION_BOOT_COMPLETED");
            final int i = 0;
            new Thread(
                            new Runnable() { // from class:
                                             // com.samsung.android.settings.accessibility.broadcast.AccessibilityBroadcastReceiver$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    int i2 = i;
                                    Context context2 = context;
                                    switch (i2) {
                                        case 0:
                                            for (AccessibilityShortcutReceiver
                                                    accessibilityShortcutReceiver :
                                                            AccessibilityBroadcastReceiver
                                                                    .HANDLERS) {
                                                if (accessibilityShortcutReceiver
                                                        instanceof AccessibilityShortcutReceiver) {
                                                    accessibilityShortcutReceiver.getClass();
                                                    AccessibilityShortcutReceiver.refreshShortcut(
                                                            context2);
                                                    AccessibilityNotificationUtil
                                                            .updateTapDurationNotification(
                                                                    context2);
                                                }
                                            }
                                            break;
                                        default:
                                            for (AccessibilityShortcutReceiver
                                                    accessibilityShortcutReceiver2 :
                                                            AccessibilityBroadcastReceiver
                                                                    .HANDLERS) {
                                                if (accessibilityShortcutReceiver2
                                                        instanceof AccessibilityShortcutReceiver) {
                                                    accessibilityShortcutReceiver2.getClass();
                                                    AccessibilityShortcutReceiver.refreshShortcut(
                                                            context2);
                                                    AccessibilityNotificationUtil
                                                            .updateTapDurationNotification(
                                                                    context2);
                                                }
                                            }
                                            break;
                                    }
                                }
                            })
                    .start();
        } else if ("android.intent.action.LOCALE_CHANGED".equals(action)) {
            Log.d("A11yBroadcastReceiver", "[onReceive] ACTION_LOCALE_CHANGED");
            final int i2 = 1;
            new Thread(
                            new Runnable() { // from class:
                                             // com.samsung.android.settings.accessibility.broadcast.AccessibilityBroadcastReceiver$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    int i22 = i2;
                                    Context context2 = context;
                                    switch (i22) {
                                        case 0:
                                            for (AccessibilityShortcutReceiver
                                                    accessibilityShortcutReceiver :
                                                            AccessibilityBroadcastReceiver
                                                                    .HANDLERS) {
                                                if (accessibilityShortcutReceiver
                                                        instanceof AccessibilityShortcutReceiver) {
                                                    accessibilityShortcutReceiver.getClass();
                                                    AccessibilityShortcutReceiver.refreshShortcut(
                                                            context2);
                                                    AccessibilityNotificationUtil
                                                            .updateTapDurationNotification(
                                                                    context2);
                                                }
                                            }
                                            break;
                                        default:
                                            for (AccessibilityShortcutReceiver
                                                    accessibilityShortcutReceiver2 :
                                                            AccessibilityBroadcastReceiver
                                                                    .HANDLERS) {
                                                if (accessibilityShortcutReceiver2
                                                        instanceof AccessibilityShortcutReceiver) {
                                                    accessibilityShortcutReceiver2.getClass();
                                                    AccessibilityShortcutReceiver.refreshShortcut(
                                                            context2);
                                                    AccessibilityNotificationUtil
                                                            .updateTapDurationNotification(
                                                                    context2);
                                                }
                                            }
                                            break;
                                    }
                                }
                            })
                    .start();
        }
    }
}
