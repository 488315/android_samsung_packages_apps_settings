package com.android.settings.notification;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.graphics.drawable.IconCompat;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.media.MediaOutputIndicatorWorker;
import com.android.settings.slices.CustomSliceRegistry;
import com.android.settings.slices.SliceBackgroundWorker;
import com.android.settingslib.Utils;
import com.android.settingslib.media.MediaDevice;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class MediaVolumePreferenceController extends VolumeSeekBarPreferenceController {
    private static final String ACTION_LAUNCH_BROADCAST_DIALOG =
            "android.settings.MEDIA_BROADCAST_DIALOG";
    private static final String KEY_MEDIA_VOLUME = "media_volume";
    private static final String TAG = "MediaVolumePreCtrl";
    private MediaDevice mMediaDevice;
    private MediaOutputIndicatorWorker mWorker;

    public MediaVolumePreferenceController(Context context) {
        super(context, KEY_MEDIA_VOLUME);
        this.mVolumePreferenceListener =
                new VolumeSeekBarPreference
                        .Listener() { // from class:
                                      // com.android.settings.notification.MediaVolumePreferenceController$$ExternalSyntheticLambda0
                    @Override // com.android.settings.notification.VolumeSeekBarPreference.Listener
                    public final void onUpdateMuteState() {
                        MediaVolumePreferenceController.this.updateContentDescription();
                    }
                };
    }

    private IconCompat getBroadcastIcon(Context context) {
        Drawable drawable = context.getDrawable(R.drawable.settings_input_antenna);
        if (drawable == null) {
            return null;
        }
        drawable.setTint(Utils.getColorAttrDefaultColor(context, android.R.attr.colorAccent));
        return com.android.settings.Utils.createIconWithDrawable(drawable);
    }

    private Uri getUri() {
        return CustomSliceRegistry.VOLUME_MEDIA_URI;
    }

    private MediaOutputIndicatorWorker getWorker() {
        if (this.mWorker == null) {
            this.mWorker = (MediaOutputIndicatorWorker) SliceBackgroundWorker.getInstance(getUri());
        }
        return this.mWorker;
    }

    private boolean isConnectedBLEDevice() {
        if (getWorker() == null) {
            Log.d(TAG, "The Worker is null");
            return false;
        }
        MediaDevice currentConnectedMediaDevice = getWorker().getCurrentConnectedMediaDevice();
        this.mMediaDevice = currentConnectedMediaDevice;
        return currentConnectedMediaDevice != null
                && currentConnectedMediaDevice.mRouteInfo.getType() == 26;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateContentDescription() {
        VolumeSeekBarPreference volumeSeekBarPreference = this.mPreference;
        if (volumeSeekBarPreference != null) {
            if (!volumeSeekBarPreference.mMuted || volumeSeekBarPreference.mZenMuted) {
                volumeSeekBarPreference.updateContentDescription(
                        volumeSeekBarPreference.getTitle());
            } else {
                volumeSeekBarPreference.updateContentDescription(
                        this.mContext.getString(
                                R.string.volume_content_description_silent_mode,
                                volumeSeekBarPreference.getTitle()));
            }
        }
    }

    @Override // com.android.settings.notification.VolumeSeekBarPreferenceController
    public int getAudioStream() {
        return 3;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mContext.getResources().getBoolean(R.bool.config_show_media_volume) ? 0 : 3;
    }

    @Override // com.android.settings.notification.VolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public Class<? extends SliceBackgroundWorker> getBackgroundWorkerClass() {
        return MediaOutputIndicatorWorker.class;
    }

    @Override // com.android.settings.notification.VolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.notification.VolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.notification.VolumeSeekBarPreferenceController
    public int getMuteIcon() {
        return R.drawable.ic_media_stream_off;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY_MEDIA_VOLUME;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0055  */
    @Override // com.android.settings.core.SliderPreferenceController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public androidx.slice.builders.SliceAction getSliceEndItem(android.content.Context r7) {
        /*
            r6 = this;
            boolean r0 = r6.isSupportEndItem()
            r1 = 0
            java.lang.String r2 = "MediaVolumePreCtrl"
            if (r0 != 0) goto Lf
            java.lang.String r6 = "The slice doesn't support end item"
            android.util.Log.d(r2, r6)
            return r1
        Lf:
            android.content.Intent r0 = new android.content.Intent
            r0.<init>()
            com.android.settings.media.MediaOutputIndicatorWorker r3 = r6.getWorker()
            com.android.settingslib.bluetooth.LocalBluetoothManager r3 = r3.mLocalBluetoothManager
            r4 = 0
            if (r3 != 0) goto L26
            java.lang.String r3 = "MediaOutputIndWorker"
            java.lang.String r5 = "isDeviceBroadcasting: Bluetooth is not supported on this device"
            android.util.Log.e(r3, r5)
        L24:
            r3 = r4
            goto L31
        L26:
            com.android.settingslib.bluetooth.LocalBluetoothProfileManager r3 = r3.mProfileManager
            com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast r3 = r3.mLeAudioBroadcast
            if (r3 != 0) goto L2d
            goto L24
        L2d:
            boolean r3 = r3.isEnabled(r1)
        L31:
            r5 = 201326592(0xc000000, float:9.8607613E-32)
            if (r3 == 0) goto L55
            java.lang.String r1 = "com.android.systemui"
            r0.setPackage(r1)
            java.lang.String r1 = "com.android.systemui.action.LAUNCH_MEDIA_OUTPUT_BROADCAST_DIALOG"
            r0.setAction(r1)
            com.android.settings.media.MediaOutputIndicatorWorker r1 = r6.getWorker()
            android.media.session.MediaController r1 = r1.getActiveLocalMediaController()
            java.lang.String r1 = r1.getPackageName()
            java.lang.String r2 = "package_name"
            r0.putExtra(r2, r1)
            android.app.PendingIntent r0 = android.app.PendingIntent.getBroadcast(r7, r4, r0, r5)
            goto La0
        L55:
            com.android.settingslib.media.MediaDevice r3 = r6.mMediaDevice
            com.android.settingslib.media.BluetoothMediaDevice r3 = (com.android.settingslib.media.BluetoothMediaDevice) r3
            com.android.settingslib.bluetooth.CachedBluetoothDevice r3 = r3.mCachedDevice
            if (r3 != 0) goto L63
            java.lang.String r6 = "The bluetooth device is null"
            android.util.Log.d(r2, r6)
            return r1
        L63:
            java.lang.String r1 = "android.settings.MEDIA_BROADCAST_DIALOG"
            r0.setAction(r1)
            android.content.Context r1 = r6.mContext
            com.android.settings.media.MediaOutputIndicatorWorker r2 = r6.getWorker()
            java.lang.String r2 = r2.mPackageName
            java.lang.CharSequence r1 = com.android.settings.Utils.getApplicationLabel(r1, r2)
            java.lang.String r2 = "app_label"
            r0.putExtra(r2, r1)
            android.bluetooth.BluetoothDevice r1 = r3.mDevice
            java.lang.String r1 = r1.getAddress()
            java.lang.String r2 = "device_address"
            r0.putExtra(r2, r1)
            com.android.settings.media.MediaOutputIndicatorWorker r1 = r6.getWorker()
            if (r1 == 0) goto L96
            com.android.settings.media.MediaOutputIndicatorWorker r1 = r6.getWorker()
            android.media.session.MediaController r1 = r1.getActiveLocalMediaController()
            if (r1 == 0) goto L96
            r1 = 1
            goto L97
        L96:
            r1 = r4
        L97:
            java.lang.String r2 = "media_streaming"
            r0.putExtra(r2, r1)
            android.app.PendingIntent r0 = android.app.PendingIntent.getActivity(r7, r4, r0, r5)
        La0:
            androidx.core.graphics.drawable.IconCompat r7 = r6.getBroadcastIcon(r7)
            java.lang.String r6 = r6.getPreferenceKey()
            androidx.slice.builders.SliceAction r6 = androidx.slice.builders.SliceAction.createDeeplink(r0, r7, r4, r6)
            return r6
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.notification.MediaVolumePreferenceController.getSliceEndItem(android.content.Context):androidx.slice.builders.SliceAction");
    }

    @Override // com.android.settings.notification.VolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.notification.VolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.notification.VolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.notification.VolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.notification.VolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean isPublicSlice() {
        return true;
    }

    @Override // com.android.settings.notification.VolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean isSliceable() {
        return TextUtils.equals(getPreferenceKey(), KEY_MEDIA_VOLUME);
    }

    @VisibleForTesting
    public boolean isSupportEndItem() {
        return false;
    }

    @Override // com.android.settings.notification.VolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.notification.VolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.notification.VolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean useDynamicSliceSummary() {
        return true;
    }
}
