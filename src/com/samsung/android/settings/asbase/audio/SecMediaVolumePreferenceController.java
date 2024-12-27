package com.samsung.android.settings.asbase.audio;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.telecom.TelecomManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.core.graphics.drawable.IconCompat;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.media.MediaOutputIndicatorWorker;
import com.android.settings.notification.AudioHelper;
import com.android.settings.slices.CustomSliceRegistry;
import com.android.settings.slices.SliceBackgroundWorker;
import com.android.settingslib.Utils;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.media.MediaDevice;

import com.samsung.android.settings.asbase.utils.SecAudioHelper;
import com.samsung.android.settings.asbase.widget.SecVolumeSeekBarPreference;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecMediaVolumePreferenceController extends SecVolumeSeekBarPreferenceController {
    private static final String ACTION_LAUNCH_BROADCAST_DIALOG =
            "android.settings.MEDIA_BROADCAST_DIALOG";
    private static final String KEY_MEDIA_VOLUME = "media_volume";
    private static final String TAG = "SecMediaVolumePreCtrl";
    private static final long UPDATE_SEEKBAR_DELAY_MILLIS = 10;
    private final H mHandler;
    private MediaDevice mMediaDevice;
    private final MediaChangedReceiver mReceiver;
    private MediaOutputIndicatorWorker mWorker;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class H extends Handler {
        public H() {
            super(Looper.getMainLooper());
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            SecMediaVolumePreferenceController.this.setDualColorSeekBar();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class MediaChangedReceiver extends BroadcastReceiver {
        public boolean mRegistered;

        public MediaChangedReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }
            MotionLayout$$ExternalSyntheticOutline0.m(
                    "MediaChangedReceiver is called by : ",
                    intent.getAction(),
                    SecMediaVolumePreferenceController.TAG);
            SecMediaVolumePreferenceController.this.mHandler.sendEmptyMessage(1);
        }

        public final void register(boolean z) {
            if (this.mRegistered == z) {
                return;
            }
            if (z) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(
                        "android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED");
                intentFilter.addAction("android.intent.action.HEADSET_PLUG");
                intentFilter.addAction("android.intent.action.DOCK_EVENT");
                intentFilter.addAction("android.media.VOLUME_CHANGED_ACTION");
                intentFilter.addAction("com.sec.android.fm.player_lock.status.off");
                intentFilter.addAction("com.sec.android.fm.player_lock.status.on");
                ((AbstractPreferenceController) SecMediaVolumePreferenceController.this)
                        .mContext.registerReceiver(this, intentFilter, 2);
                new Handler()
                        .postDelayed(
                                new Runnable() { // from class:
                                                 // com.samsung.android.settings.asbase.audio.SecMediaVolumePreferenceController$MediaChangedReceiver$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        SecMediaVolumePreferenceController.this
                                                .setDualColorSeekBar();
                                    }
                                },
                                SecMediaVolumePreferenceController.UPDATE_SEEKBAR_DELAY_MILLIS);
                Log.i(SecMediaVolumePreferenceController.TAG, "register MediaChangedReceiver");
            } else {
                ((AbstractPreferenceController) SecMediaVolumePreferenceController.this)
                        .mContext.unregisterReceiver(this);
                Log.i(SecMediaVolumePreferenceController.TAG, "unregister MediaChangedReceiver");
            }
            this.mRegistered = z;
        }
    }

    public SecMediaVolumePreferenceController(Context context) {
        super(context, KEY_MEDIA_VOLUME);
        this.mHandler = new H();
        this.mReceiver = new MediaChangedReceiver();
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
        MediaDevice currentConnectedMediaDevice = getWorker().getCurrentConnectedMediaDevice();
        this.mMediaDevice = currentConnectedMediaDevice;
        return currentConnectedMediaDevice != null
                && currentConnectedMediaDevice.mRouteInfo.getType() == 26;
    }

    private boolean isSupportEndItem() {
        return (getWorker() == null
                        || getWorker().getActiveLocalMediaController() == null
                        || !isConnectedBLEDevice())
                ? false
                : true;
    }

    private boolean isZenModeEnabled() {
        return ((NotificationManager) this.mContext.getSystemService(NotificationManager.class))
                        .getZenMode()
                == 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDualColorSeekBar() {
        TelecomManager telecomManager = (TelecomManager) this.mContext.getSystemService("telecom");
        if (telecomManager != null
                && ((telecomManager.isRinging() || telecomManager.isInCall())
                        && !((SecVolumeSeekBarPreferenceController) this)
                                .mHelper.mAudioManager.semIsSafeMediaVolumeDeviceOn())) {
            SecVolumeSeekBarPreference secVolumeSeekBarPreference = this.mPreference;
            if (secVolumeSeekBarPreference != null) {
                secVolumeSeekBarPreference.updateOverlapPointForDualColor(-1);
                return;
            }
            return;
        }
        if (this.mPreference == null) {
            Log.w(TAG, "setDualColorSeekBar : Preference is null");
            return;
        }
        if (!((SecVolumeSeekBarPreferenceController) this).mHelper.isEarDeviceStatusOn()) {
            this.mPreference.updateOverlapPointForDualColor(-1);
            return;
        }
        SecVolumeSeekBarPreference secVolumeSeekBarPreference2 = this.mPreference;
        ((SecVolumeSeekBarPreferenceController) this).mHelper.getClass();
        secVolumeSeekBarPreference2.updateOverlapPointForDualColor(
                SecAudioHelper.getEarProtectLevel());
        Log.d(TAG, "STREAM_MUSIC : mEarProtectLevel");
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        if (isAvailable()) {
            SecVolumeSeekBarPreference secVolumeSeekBarPreference = this.mPreference;
            ((SecVolumeSeekBarPreferenceController) this).mHelper.getClass();
            SecAudioHelper.getEarProtectLevel();
            secVolumeSeekBarPreference.getClass();
        }
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController
    public int getAudioStream() {
        return 3;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mContext.getResources().getBoolean(R.bool.config_show_media_volume) ? 0 : 3;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public Class<? extends SliceBackgroundWorker> getBackgroundWorkerClass() {
        return MediaOutputIndicatorWorker.class;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController
    public int getMuteIcon() {
        return R.drawable.tw_ic_audio_media_mute_mtrl;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController
    public int getNormalIcon() {
        return R.drawable.tw_ic_audio_media_note;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY_MEDIA_VOLUME;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0056  */
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
            java.lang.String r2 = "SecMediaVolumePreCtrl"
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
            if (r3 == 0) goto L56
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
            goto L89
        L56:
            com.android.settingslib.media.MediaDevice r3 = r6.mMediaDevice
            com.android.settingslib.media.BluetoothMediaDevice r3 = (com.android.settingslib.media.BluetoothMediaDevice) r3
            com.android.settingslib.bluetooth.CachedBluetoothDevice r3 = r3.mCachedDevice
            if (r3 != 0) goto L64
            java.lang.String r6 = "The bluetooth device is null"
            android.util.Log.d(r2, r6)
            return r1
        L64:
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
            android.app.PendingIntent r0 = android.app.PendingIntent.getActivity(r7, r4, r0, r5)
        L89:
            androidx.core.graphics.drawable.IconCompat r7 = r6.getBroadcastIcon(r7)
            java.lang.String r6 = r6.getPreferenceKey()
            androidx.slice.builders.SliceAction r6 = androidx.slice.builders.SliceAction.createDeeplink(r0, r7, r4, r6)
            return r6
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.asbase.audio.SecMediaVolumePreferenceController.getSliceEndItem(android.content.Context):androidx.slice.builders.SliceAction");
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean isPublicSlice() {
        return true;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean isSliceable() {
        return TextUtils.equals(getPreferenceKey(), KEY_MEDIA_VOLUME);
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController
    public void onPause() {
        super.onPause();
        this.mReceiver.register(false);
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController
    public void onResume() {
        super.onResume();
        SecAudioHelper secAudioHelper = ((SecVolumeSeekBarPreferenceController) this).mHelper;
        updatePreferenceIcon(
                ((AudioHelper) secAudioHelper).mAudioManager.getStreamVolume(getAudioStream()));
        setDualColorSeekBar();
        this.mReceiver.register(true);
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController
    public void updatePreferenceIcon(int i) {
        this.mPreference.showIcon(
                (i <= 0 || !(isZenModeEnabled() ^ true)) ? getMuteIcon() : getNormalIcon());
    }

    @Override // com.samsung.android.settings.asbase.audio.SecVolumeSeekBarPreferenceController,
              // com.android.settings.notification.AdjustVolumeRestrictedPreferenceController,
              // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean useDynamicSliceSummary() {
        return true;
    }
}
