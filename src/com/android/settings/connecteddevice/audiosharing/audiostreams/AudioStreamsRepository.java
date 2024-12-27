package com.android.settings.connecteddevice.audiosharing.audiostreams;

import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AudioStreamsRepository {
    public static AudioStreamsRepository sInstance;
    public final ConcurrentHashMap mBroadcastIdToMetadataCacheMap = new ConcurrentHashMap();

    public static synchronized AudioStreamsRepository getInstance() {
        AudioStreamsRepository audioStreamsRepository;
        synchronized (AudioStreamsRepository.class) {
            try {
                if (sInstance == null) {
                    sInstance = new AudioStreamsRepository();
                }
                audioStreamsRepository = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return audioStreamsRepository;
    }
}
