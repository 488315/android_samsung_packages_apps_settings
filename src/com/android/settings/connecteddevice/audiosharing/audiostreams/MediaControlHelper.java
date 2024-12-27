package com.android.settings.connecteddevice.audiosharing.audiostreams;

import android.content.Context;
import android.media.session.MediaSessionManager;

import com.android.settingslib.bluetooth.LocalBluetoothManager;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class MediaControlHelper {
    public final Context mContext;
    public final LocalBluetoothManager mLocalBluetoothManager;
    public final List mLocalMediaManagers = new ArrayList();
    public final MediaSessionManager mMediaSessionManager;

    public MediaControlHelper(Context context, LocalBluetoothManager localBluetoothManager) {
        this.mContext = context;
        this.mMediaSessionManager =
                (MediaSessionManager) context.getSystemService(MediaSessionManager.class);
        this.mLocalBluetoothManager = localBluetoothManager;
    }
}
