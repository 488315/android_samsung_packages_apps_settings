package com.android.settingslib;

import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Process;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.Log;

import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SliceBroadcastRelay {
    public static final Set sRegisteredUris = new ArraySet();

    public static void registerReceiver(
            Context context, Uri uri, Class cls, IntentFilter intentFilter) {
        Log.d("SliceBroadcastRelay", "Registering Uri for broadcast relay: " + uri);
        ((ArraySet) sRegisteredUris).add(uri);
        Intent intent = new Intent("com.android.settingslib.action.REGISTER_SLICE_RECEIVER");
        intent.setPackage("com.android.systemui");
        intent.putExtra(
                "uri", ContentProvider.maybeAddUserId(uri, Process.myUserHandle().getIdentifier()));
        intent.putExtra("receiver", new ComponentName(context.getPackageName(), cls.getName()));
        intent.putExtra("filter", intentFilter);
        context.sendBroadcastAsUser(intent, UserHandle.SYSTEM);
    }

    public static void unregisterReceivers(Context context, Uri uri) {
        Set set = sRegisteredUris;
        if (((ArraySet) set).contains(uri)) {
            Log.d("SliceBroadcastRelay", "Unregistering uri broadcast relay: " + uri);
            Intent intent = new Intent("com.android.settingslib.action.UNREGISTER_SLICE_RECEIVER");
            intent.setPackage("com.android.systemui");
            intent.putExtra(
                    "uri",
                    ContentProvider.maybeAddUserId(uri, Process.myUserHandle().getIdentifier()));
            context.sendBroadcastAsUser(intent, UserHandle.SYSTEM);
            ((ArraySet) set).remove(uri);
        }
    }
}
