package com.android.settings.slices;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.ArrayMap;

import com.android.settingslib.media.MediaDevice;

import java.io.Closeable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SliceBackgroundWorker implements Closeable {
    public static final Map LIVE_WORKERS = new ArrayMap();
    public List mCachedResults;
    public final Context mContext;
    public final Uri mUri;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class NotifySliceChangeHandler extends Handler {
        public static NotifySliceChangeHandler sHandler;
        public final Map mLastUpdateTimeLookup;

        /* renamed from: -$$Nest$smgetInstance, reason: not valid java name */
        public static NotifySliceChangeHandler m1006$$Nest$smgetInstance() {
            if (sHandler == null) {
                HandlerThread handlerThread = new HandlerThread("NotifySliceChangeHandler", 10);
                handlerThread.start();
                sHandler = new NotifySliceChangeHandler(handlerThread.getLooper());
            }
            return sHandler;
        }

        public NotifySliceChangeHandler(Looper looper) {
            super(looper);
            this.mLastUpdateTimeLookup = Collections.synchronizedMap(new ArrayMap());
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 1000) {
                return;
            }
            SliceBackgroundWorker sliceBackgroundWorker = (SliceBackgroundWorker) message.obj;
            Uri uri = sliceBackgroundWorker.mUri;
            Context context = sliceBackgroundWorker.mContext;
            this.mLastUpdateTimeLookup.put(uri, Long.valueOf(SystemClock.uptimeMillis()));
            context.getContentResolver().notifyChange(uri, null);
        }
    }

    public SliceBackgroundWorker(Context context, Uri uri) {
        this.mContext = context;
        this.mUri = uri;
    }

    public static SliceBackgroundWorker getInstance(Uri uri) {
        return (SliceBackgroundWorker) ((ArrayMap) LIVE_WORKERS).get(uri);
    }

    public final void notifySliceChange() {
        NotifySliceChangeHandler m1006$$Nest$smgetInstance =
                NotifySliceChangeHandler.m1006$$Nest$smgetInstance();
        if (m1006$$Nest$smgetInstance.hasMessages(1000, this)) {
            return;
        }
        Message obtainMessage = m1006$$Nest$smgetInstance.obtainMessage(1000, this);
        long longValue =
                ((Long) m1006$$Nest$smgetInstance.mLastUpdateTimeLookup.getOrDefault(this.mUri, 0L))
                        .longValue();
        if (longValue == 0) {
            m1006$$Nest$smgetInstance.sendMessageDelayed(obtainMessage, 300L);
        } else if (SystemClock.uptimeMillis() - longValue > 300) {
            m1006$$Nest$smgetInstance.sendMessage(obtainMessage);
        } else {
            m1006$$Nest$smgetInstance.sendMessageAtTime(obtainMessage, longValue + 300);
        }
    }

    public void onDeviceAttributesChanged() {
        notifySliceChange();
    }

    public void onSelectedDeviceStateChanged(MediaDevice mediaDevice, int i) {
        notifySliceChange();
    }

    public abstract void onSlicePinned();

    public abstract void onSliceUnpinned();

    public void onWifiStateChanged() {
        notifySliceChange();
    }
}
