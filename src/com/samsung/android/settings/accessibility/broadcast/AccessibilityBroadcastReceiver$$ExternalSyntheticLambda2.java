package com.samsung.android.settings.accessibility.broadcast;

import android.util.Log;

import java.util.Set;
import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class AccessibilityBroadcastReceiver$$ExternalSyntheticLambda2
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        Object newInstance;
        String str = (String) obj;
        Set set = AccessibilityBroadcastReceiver.HANDLERS;
        try {
            newInstance = Class.forName(str).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            Log.w(
                    "A11yBroadcastReceiver",
                    "getReceiverInstance occurs exception. className : " + str,
                    e);
        }
        if (newInstance instanceof AccessibilityShortcutReceiver) {
            return (AccessibilityShortcutReceiver) newInstance;
        }
        Log.w("A11yBroadcastReceiver", str + " is not inherited OnHandleBroadcast");
        return null;
    }
}
