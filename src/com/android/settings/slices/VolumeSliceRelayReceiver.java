package com.android.settings.slices;

import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.util.Log;

import java.util.Map;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class VolumeSliceRelayReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(final Context context, Intent intent) {
        String stringExtra;
        Map<Uri, Integer> map = VolumeSliceHelper.sRegisteredUri;
        String action = intent.getAction();
        IntentFilter intentFilter = VolumeSliceHelper.sIntentFilter;
        if (intentFilter == null
                || action == null
                || !intentFilter.hasAction(action)
                || (stringExtra = intent.getStringExtra("uri")) == null) {
            return;
        }
        if (!CustomSliceRegistry.VOLUME_SLICES_URI.equals(
                ContentProvider.getUriWithoutUserId(Uri.parse(stringExtra)))) {
            Log.w("VolumeSliceHelper", "Invalid uri: ".concat(stringExtra));
            return;
        }
        if ("android.media.VOLUME_CHANGED_ACTION".equals(action)) {
            if (intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_VALUE", -1)
                    != intent.getIntExtra("android.media.EXTRA_PREV_VOLUME_STREAM_VALUE", -1)) {
                VolumeSliceHelper.handleStreamChanged(
                        context, intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1));
            }
        } else {
            if (!"android.media.STREAM_MUTE_CHANGED_ACTION".equals(action)) {
                if ("android.media.STREAM_DEVICES_CHANGED_ACTION".equals(action)) {
                    VolumeSliceHelper.handleStreamChanged(
                            context,
                            intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1));
                    return;
                } else {
                    VolumeSliceHelper.sRegisteredUri
                            .keySet()
                            .forEach(
                                    new Consumer() { // from class:
                                                     // com.android.settings.slices.VolumeSliceHelper$$ExternalSyntheticLambda0
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj) {
                                            context.getContentResolver()
                                                    .notifyChange((Uri) obj, null);
                                        }
                                    });
                    return;
                }
            }
            int intExtra = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1);
            VolumeSliceHelper.handleStreamChanged(context, intExtra);
            if (intExtra == 2) {
                VolumeSliceHelper.handleStreamChanged(context, 5);
            }
        }
    }
}
