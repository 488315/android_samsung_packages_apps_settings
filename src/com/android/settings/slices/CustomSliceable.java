package com.android.settings.slices;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.slice.Slice;

import java.lang.reflect.InvocationTargetException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public interface CustomSliceable extends Sliceable {
    static CustomSliceable createInstance(Context context, Class cls) {
        try {
            return (CustomSliceable)
                    cls.getConstructor(Context.class).newInstance(context.getApplicationContext());
        } catch (IllegalAccessException
                | IllegalArgumentException
                | InstantiationException
                | NoSuchMethodException
                | InvocationTargetException e) {
            throw new IllegalStateException("Invalid sliceable class: " + cls, e);
        }
    }

    default PendingIntent getBroadcastIntent(Context context) {
        return PendingIntent.getBroadcast(
                context,
                0,
                new Intent(getUri$1().toString())
                        .setData(getUri$1())
                        .setClass(context, SliceBroadcastReceiver.class),
                167772160);
    }

    Intent getIntent();

    Slice getSlice();

    Uri getUri$1();

    default void onNotifyChange(Intent intent) {}
}
