package com.android.settings.connecteddevice.audiosharing.audiostreams;

import android.app.Notification;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothVolumeControl;
import android.content.Intent;
import android.media.MediaMetadata;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AudioStreamMediaService extends Service {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AnonymousClass2 mBluetoothCallback;
    public final AnonymousClass1 mBroadcastAssistantCallback;
    public int mBroadcastId;
    public ArrayList mDevices;
    public boolean mIsMuted;
    public int mLatestPositiveVolume;
    MediaSession mLocalSession;
    public final SettingsMetricsFeatureProvider mMetricsFeatureProvider;
    public final PlaybackState.Builder mPlayStatePausingBuilder;
    public final PlaybackState.Builder mPlayStatePlayingBuilder;
    public final AnonymousClass3 mVolumeControlCallback;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamMediaService$2, reason: invalid class name */
    public final class AnonymousClass2 implements BluetoothCallback {
        public AnonymousClass2() {}

        @Override // com.android.settingslib.bluetooth.BluetoothCallback
        public final void onBluetoothStateChanged(int i) {
            if (10 == i) {
                AudioStreamMediaService.this.stopSelf();
            }
        }

        @Override // com.android.settingslib.bluetooth.BluetoothCallback
        public final void onProfileConnectionStateChanged(
                CachedBluetoothDevice cachedBluetoothDevice, int i, int i2) {
            ArrayList arrayList;
            AudioStreamMediaService audioStreamMediaService = AudioStreamMediaService.this;
            if (i == 0 && i2 == 29 && (arrayList = audioStreamMediaService.mDevices) != null) {
                arrayList.remove(cachedBluetoothDevice.mDevice);
                cachedBluetoothDevice.mMemberDevices.forEach(
                        new Consumer() { // from class:
                                         // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamMediaService$2$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                CachedBluetoothDevice cachedBluetoothDevice2 =
                                        (CachedBluetoothDevice) obj;
                                ArrayList arrayList2 = AudioStreamMediaService.this.mDevices;
                                if (arrayList2 != null) {
                                    arrayList2.remove(cachedBluetoothDevice2.mDevice);
                                }
                            }
                        });
            }
            ArrayList arrayList2 = audioStreamMediaService.mDevices;
            if (arrayList2 == null || arrayList2.isEmpty()) {
                audioStreamMediaService.stopSelf();
            }
        }
    }

    public AudioStreamMediaService() {
        new AudioStreamsBroadcastAssistantCallback() { // from class:
                                                       // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamMediaService.1
            @Override // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsBroadcastAssistantCallback
            public final void onSourceLost(int i) {
                super.onSourceLost(i);
                AudioStreamMediaService audioStreamMediaService = AudioStreamMediaService.this;
                if (i == audioStreamMediaService.mBroadcastId) {
                    audioStreamMediaService.stopSelf();
                }
            }

            @Override // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsBroadcastAssistantCallback
            public final void onSourceRemoved(BluetoothDevice bluetoothDevice, int i, int i2) {
                super.onSourceRemoved(bluetoothDevice, i, i2);
                AudioStreamMediaService audioStreamMediaService = AudioStreamMediaService.this;
                int i3 = AudioStreamMediaService.$r8$clinit;
                audioStreamMediaService.getClass();
            }
        };
        new AnonymousClass2();
        new BluetoothVolumeControl
                .Callback() { // from class:
                              // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamMediaService.3
            public final void onDeviceVolumeChanged(BluetoothDevice bluetoothDevice, int i) {
                ArrayList arrayList = AudioStreamMediaService.this.mDevices;
                if (arrayList == null || arrayList.isEmpty()) {
                    Log.w("AudioStreamMediaService", "active device or device has source is null!");
                    return;
                }
                if (AudioStreamMediaService.this.mDevices.contains(bluetoothDevice)) {
                    Log.d(
                            "AudioStreamMediaService",
                            "onDeviceVolumeChanged() bluetoothDevice : "
                                    + bluetoothDevice
                                    + " volume: "
                                    + i);
                    if (i == 0) {
                        AudioStreamMediaService.this.mIsMuted = true;
                    } else {
                        AudioStreamMediaService audioStreamMediaService =
                                AudioStreamMediaService.this;
                        audioStreamMediaService.mIsMuted = false;
                        audioStreamMediaService.mLatestPositiveVolume = i;
                    }
                    AudioStreamMediaService audioStreamMediaService2 = AudioStreamMediaService.this;
                    MediaSession mediaSession = audioStreamMediaService2.mLocalSession;
                    if (mediaSession != null) {
                        mediaSession.setPlaybackState(audioStreamMediaService2.getPlaybackState());
                        AudioStreamMediaService.this.getClass();
                    }
                }
            }
        };
        this.mPlayStatePlayingBuilder =
                new PlaybackState.Builder()
                        .setActions(258L)
                        .setState(3, 30L, 0.0f)
                        .addCustomAction(
                                "leave_broadcast_action", "Leave Broadcast", R.drawable.ic_clear);
        this.mPlayStatePausingBuilder =
                new PlaybackState.Builder()
                        .setActions(260L)
                        .setState(2, 30L, 0.0f)
                        .addCustomAction(
                                "leave_broadcast_action", "Leave Broadcast", R.drawable.ic_clear);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
        Executors.newSingleThreadExecutor();
        this.mLatestPositiveVolume = 25;
        this.mIsMuted = false;
    }

    public final PlaybackState getPlaybackState() {
        return (this.mIsMuted ? this.mPlayStatePausingBuilder : this.mPlayStatePlayingBuilder)
                .build();
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public final void onCreate() {
        BluetoothAdapter.getDefaultAdapter();
    }

    @Override // android.app.Service
    public final void onDestroy() {
        super.onDestroy();
        BluetoothAdapter.getDefaultAdapter();
    }

    @Override // android.app.Service
    public final int onStartCommand(Intent intent, int i, int i2) {
        Log.d("AudioStreamMediaService", "onStartCommand()");
        int intExtra =
                intent != null
                        ? intent.getIntExtra("audio_stream_media_service_broadcast_id", -1)
                        : -1;
        this.mBroadcastId = intExtra;
        if (intExtra == -1) {
            Log.w("AudioStreamMediaService", "Invalid broadcast ID. Service will not start.");
            stopSelf();
            return 2;
        }
        if (intent != null) {
            this.mDevices =
                    intent.getParcelableArrayListExtra(
                            "audio_stream_media_service_devices", BluetoothDevice.class);
        }
        ArrayList arrayList = this.mDevices;
        if (arrayList == null || arrayList.isEmpty()) {
            Log.w("AudioStreamMediaService", "No device. Service will not start.");
            stopSelf();
            return 2;
        }
        if (intent != null) {
            String stringExtra =
                    intent.getStringExtra("audio_stream_media_service_broadcast_title");
            MediaSession mediaSession = new MediaSession(this, "AudioStreamMediaService");
            this.mLocalSession = mediaSession;
            mediaSession.setMetadata(
                    new MediaMetadata.Builder()
                            .putString("android.media.metadata.TITLE", stringExtra)
                            .putLong("android.media.metadata.DURATION", 100L)
                            .build());
            this.mLocalSession.setActive(true);
            this.mLocalSession.setPlaybackState(getPlaybackState());
            this.mLocalSession.setCallback(
                    new MediaSession
                            .Callback() { // from class:
                                          // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamMediaService.4
                        @Override // android.media.session.MediaSession.Callback
                        public final void onCustomAction(String str, Bundle bundle) {
                            if (AudioStreamMediaService$4$$ExternalSyntheticOutline0.m(
                                    "onCustomAction: ",
                                    str,
                                    "AudioStreamMediaService",
                                    "leave_broadcast_action")) {
                                AudioStreamMediaService audioStreamMediaService =
                                        AudioStreamMediaService.this;
                                int i3 = AudioStreamMediaService.$r8$clinit;
                                audioStreamMediaService.getClass();
                            }
                        }

                        @Override // android.media.session.MediaSession.Callback
                        public final void onPause() {
                            ArrayList arrayList2 = AudioStreamMediaService.this.mDevices;
                            if (arrayList2 == null || arrayList2.isEmpty()) {
                                Log.w(
                                        "AudioStreamMediaService",
                                        "active device or device has source is null!");
                                return;
                            }
                            Log.d(
                                    "AudioStreamMediaService",
                                    "onPause() setting volume for device : "
                                            + AudioStreamMediaService.this.mDevices.get(0)
                                            + " volume: 0");
                            AudioStreamMediaService.this.getClass();
                        }

                        @Override // android.media.session.MediaSession.Callback
                        public final void onPlay() {
                            ArrayList arrayList2 = AudioStreamMediaService.this.mDevices;
                            if (arrayList2 == null || arrayList2.isEmpty()) {
                                Log.w(
                                        "AudioStreamMediaService",
                                        "active device or device has source is null!");
                                return;
                            }
                            StringBuilder sb =
                                    new StringBuilder("onPlay() setting volume for device : ");
                            sb.append(AudioStreamMediaService.this.mDevices.get(0));
                            sb.append(" volume: ");
                            Preference$$ExternalSyntheticOutline0.m(
                                    sb,
                                    AudioStreamMediaService.this.mLatestPositiveVolume,
                                    "AudioStreamMediaService");
                            AudioStreamMediaService.this.getClass();
                            AudioStreamMediaService audioStreamMediaService =
                                    AudioStreamMediaService.this;
                            audioStreamMediaService.mMetricsFeatureProvider.action(
                                    audioStreamMediaService.getApplicationContext(), 1952, 0);
                        }

                        @Override // android.media.session.MediaSession.Callback
                        public final void onSeekTo(long j) {
                            Log.d("AudioStreamMediaService", "onSeekTo: " + j);
                            AudioStreamMediaService audioStreamMediaService =
                                    AudioStreamMediaService.this;
                            MediaSession mediaSession2 = audioStreamMediaService.mLocalSession;
                            if (mediaSession2 != null) {
                                mediaSession2.setPlaybackState(
                                        audioStreamMediaService.getPlaybackState());
                                AudioStreamMediaService.this.getClass();
                            }
                        }
                    });
            ArrayList arrayList2 = this.mDevices;
            if (arrayList2 != null) {
                arrayList2.isEmpty();
            }
            Notification.MediaStyle mediaStyle = new Notification.MediaStyle();
            MediaSession mediaSession2 = this.mLocalSession;
            startForeground(
                    1,
                    new Notification.Builder(this, "bluetooth_notification_channel")
                            .setSmallIcon(R.drawable.ic_bt_le_audio_sharing)
                            .setStyle(
                                    mediaStyle.setMediaSession(
                                            mediaSession2 != null
                                                    ? mediaSession2.getSessionToken()
                                                    : null))
                            .setContentText(getString(R.string.audio_streams_listening_now))
                            .setSilent(true)
                            .build());
        }
        return 2;
    }
}
