package com.android.settingslib.media;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaRoute2Info;
import android.media.NearbyDevice;
import android.text.TextUtils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.sec.ims.presence.ServiceTuple;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class MediaDevice implements Comparable {
    public final AudioManager mAudioManager;
    public int mConnectedRecord;
    public final Context mContext;
    public final MediaRoute2Info mRouteInfo;
    public int mState;
    int mType;

    public MediaDevice(Context context, MediaRoute2Info mediaRoute2Info) {
        this.mContext = context;
        this.mRouteInfo = mediaRoute2Info;
        this.mAudioManager = (AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        if (mediaRoute2Info == null) {
            this.mType = 5;
            return;
        }
        int type = mediaRoute2Info.getType();
        if (type == 2) {
            this.mType = 1;
            return;
        }
        if (type == 3 || type == 4) {
            this.mType = 3;
            return;
        }
        if (type != 22) {
            if (type != 23 && type != 26) {
                if (type != 29) {
                    if (type == 1003) {
                        this.mType = 8;
                        return;
                    }
                    if (type == 2000) {
                        this.mType = 7;
                        return;
                    }
                    switch (type) {
                        case 8:
                            break;
                        case 9:
                        case 10:
                        case 11:
                        case 12:
                        case 13:
                            break;
                        default:
                            this.mType = 6;
                            break;
                    }
                    return;
                }
            }
            this.mType = 5;
            return;
        }
        this.mType = 2;
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        String str;
        MediaDevice mediaDevice = (MediaDevice) obj;
        if (mediaDevice == null) {
            return -1;
        }
        if (isConnected() ^ mediaDevice.isConnected()) {
            if (isConnected()) {
                return -1;
            }
        } else {
            if (this.mState == 4) {
                return -1;
            }
            if (mediaDevice.mState != 4) {
                int i = this.mType;
                int i2 = mediaDevice.mType;
                if (i == i2) {
                    if (isMutingExpectedDevice()) {
                        return -1;
                    }
                    if (!mediaDevice.isMutingExpectedDevice()) {
                        if (isFastPairDevice()) {
                            return -1;
                        }
                        if (!mediaDevice.isFastPairDevice()) {
                            if (isCarKitDevice()) {
                                return -1;
                            }
                            if (!mediaDevice.isCarKitDevice()) {
                                if (NearbyDevice.compareRangeZones(0, 0) != 0) {
                                    return NearbyDevice.compareRangeZones(0, 0);
                                }
                                ConnectionRecordManager connectionRecordManager =
                                        ConnectionRecordManager.getInstance();
                                synchronized (connectionRecordManager) {
                                    str = connectionRecordManager.mLastSelectedDevice;
                                }
                                if (TextUtils.equals(str, getId())) {
                                    return -1;
                                }
                                if (!TextUtils.equals(str, mediaDevice.getId())) {
                                    int i3 = this.mConnectedRecord;
                                    int i4 = mediaDevice.mConnectedRecord;
                                    return (i3 == i4 || (i4 <= 0 && i3 <= 0))
                                            ? getName().compareToIgnoreCase(mediaDevice.getName())
                                            : i4 - i3;
                                }
                            }
                        }
                    }
                } else if (i < i2) {
                    return -1;
                }
            }
        }
        return 1;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof MediaDevice) {
            return ((MediaDevice) obj).getId().equals(getId());
        }
        return false;
    }

    public String getAddress() {
        return ApnSettings.MVNO_NONE;
    }

    public int getDevice() {
        return 1073741824;
    }

    public abstract String getId();

    public abstract String getName();

    public final void initDeviceRecord() {
        int i;
        ConnectionRecordManager connectionRecordManager = ConnectionRecordManager.getInstance();
        Context context = this.mContext;
        synchronized (connectionRecordManager) {
            connectionRecordManager.mLastSelectedDevice =
                    context.getSharedPreferences("seamless_transfer_record", 0)
                            .getString("last_selected_device", null);
        }
        ConnectionRecordManager connectionRecordManager2 = ConnectionRecordManager.getInstance();
        Context context2 = this.mContext;
        String id = getId();
        synchronized (connectionRecordManager2) {
            i = context2.getSharedPreferences("seamless_transfer_record", 0).getInt(id, 0);
        }
        this.mConnectedRecord = i;
    }

    public boolean isCarKitDevice() {
        return false;
    }

    public abstract boolean isConnected();

    public boolean isFastPairDevice() {
        return false;
    }

    public boolean isMutingExpectedDevice() {
        return false;
    }
}
