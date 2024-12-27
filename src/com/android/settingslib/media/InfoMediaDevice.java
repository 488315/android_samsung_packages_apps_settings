package com.android.settingslib.media;

import com.android.settings.R;

import com.samsung.android.knox.EnterpriseContainerCallback;
import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class InfoMediaDevice extends MediaDevice {
    public int getDrawableResIdByType() {
        int type = this.mRouteInfo.getType();
        if (type == 1001) {
            return R.drawable.ic_media_display_device;
        }
        if (type == 2000) {
            return R.drawable.ic_media_group_device;
        }
        switch (type) {
            case VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI /* 1004 */:
                return R.drawable.ic_media_tablet;
            case 1005:
                return R.drawable.ic_dock_device;
            case 1006:
                return R.drawable.ic_media_computer;
            case 1007:
                return R.drawable.ic_media_game_console;
            case EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_SUCCESS /* 1008 */:
                return R.drawable.ic_media_car;
            case EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_FAILURE /* 1009 */:
                return R.drawable.ic_media_smartwatch;
            case EnterpriseContainerCallback.CONTAINER_MOUNT_STATUS /* 1010 */:
                return R.drawable.ic_smartphone;
            default:
                return R.drawable.ic_media_speaker_device;
        }
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final String getId() {
        return this.mRouteInfo.getId();
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final String getName() {
        return this.mRouteInfo.getName().toString();
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final boolean isConnected() {
        return true;
    }
}
