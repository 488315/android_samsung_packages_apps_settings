package com.android.settingslib.media;

import android.content.Context;
import android.media.AudioDeviceInfo;
import android.media.MediaRoute2Info;
import android.os.SystemProperties;

import com.android.media.flags.Flags;
import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.CachedBluetoothCastDevice;
import com.sec.ims.configuration.DATA;

import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PhoneMediaDevice extends MediaDevice {
    public static CachedBluetoothCastDevice sCachedBluetoothCastDevice;
    public static String sDisplayDeviceName;

    public static String getMediaTransferThisDeviceName(Context context) {
        context.getPackageManager().hasSystemFeature("android.software.leanback");
        return Arrays.asList(SystemProperties.get("ro.build.characteristics").split(","))
                        .contains("tablet")
                ? context.getString(R.string.sec_tablet_speaker)
                : context.getString(R.string.sec_phone_speaker);
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final String getAddress() {
        int device = getDevice();
        if (device == 32768) {
            return DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
        }
        for (AudioDeviceInfo audioDeviceInfo : this.mAudioManager.getDevices(2)) {
            if (audioDeviceInfo.getDeviceId() == device) {
                return audioDeviceInfo.getAddress();
            }
        }
        return ApnSettings.MVNO_NONE;
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final int getDevice() {
        int type = this.mRouteInfo.getType();
        if (type == 3) {
            return 4;
        }
        if (type == 4) {
            return 8;
        }
        if (type != 9) {
            if (type != 22) {
                if (type == 25) {
                    return NetworkAnalyticsConstants.DataPoints.FLAG_UID;
                }
                switch (type) {
                    case 11:
                    case 12:
                        break;
                    case 13:
                        break;
                    default:
                        return 2;
                }
            }
            return 67108864;
        }
        return 1024;
    }

    public int getDrawableResId() {
        int type = this.mRouteInfo.getType();
        if (type != 3 && type != 4 && type != 9 && type != 22) {
            if (type == 25) {
                String str = sDisplayDeviceName;
                return (str == null || !str.contains("DeX"))
                        ? R.drawable.list_ic_tv
                        : R.drawable.list_ic_laptop;
            }
            switch (type) {
                case 11:
                case 12:
                case 13:
                    break;
                default:
                    return R.drawable.list_ic_mobile;
            }
        }
        return R.drawable.list_ic_headset;
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final String getId() {
        if (Flags.enableAudioPoliciesDeviceAndBluetoothController()) {
            return this.mRouteInfo.getId();
        }
        int type = this.mRouteInfo.getType();
        if (type != 2) {
            if (type == 3 || type == 4) {
                return "wired_headset_media_device_id";
            }
            if (type != 22) {
                if (type != 25) {
                    if (type != 29) {
                        switch (type) {
                            case 9:
                            case 10:
                            case 11:
                            case 12:
                            case 13:
                                break;
                            default:
                                return "phone_media_device_id";
                        }
                    }
                }
            }
            return "usb_headset_media_device_id";
        }
        return "remote_submix_media_device_id";
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final String getName() {
        String mediaTransferThisDeviceName;
        Context context = this.mContext;
        MediaRoute2Info mediaRoute2Info = this.mRouteInfo;
        context.getPackageManager().hasSystemFeature("android.software.leanback");
        int type = mediaRoute2Info.getType();
        if (type != 2) {
            if (type != 3 && type != 4 && type != 22) {
                if (type != 25) {
                    if (type != 29) {
                        switch (type) {
                            case 9:
                                mediaTransferThisDeviceName = mediaRoute2Info.getName();
                                break;
                            case 10:
                                break;
                            case 11:
                            case 12:
                                break;
                            case 13:
                                mediaTransferThisDeviceName = mediaRoute2Info.getName();
                                break;
                            default:
                                mediaTransferThisDeviceName =
                                        getMediaTransferThisDeviceName(context);
                                break;
                        }
                    }
                    mediaTransferThisDeviceName =
                            context.getString(R.string.media_transfer_external_device_name);
                } else {
                    mediaTransferThisDeviceName = mediaRoute2Info.getName();
                    CachedBluetoothCastDevice cachedBluetoothCastDevice =
                            sCachedBluetoothCastDevice;
                    if (cachedBluetoothCastDevice != null) {
                        mediaTransferThisDeviceName = cachedBluetoothCastDevice.getName();
                    }
                    String str = sDisplayDeviceName;
                    if (str != null) {
                        mediaTransferThisDeviceName =
                                str.contains("DeX") ? "PC" : sDisplayDeviceName;
                    }
                }
            }
            mediaTransferThisDeviceName =
                    context.getString(R.string.media_transfer_wired_usb_device_name);
        } else {
            mediaTransferThisDeviceName = getMediaTransferThisDeviceName(context);
        }
        return mediaTransferThisDeviceName.toString();
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final boolean isConnected() {
        return true;
    }
}
