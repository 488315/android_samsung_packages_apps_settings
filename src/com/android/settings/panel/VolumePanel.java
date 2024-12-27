package com.android.settings.panel;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.android.settings.R;
import com.android.settings.bluetooth.Utils;
import com.android.settings.slices.CustomSliceRegistry;
import com.android.settingslib.bluetooth.A2dpProfile;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class VolumePanel implements PanelContent, LifecycleObserver {
    public PanelFragment.LocalPanelCallback mCallback;
    public final Context mContext;
    public final int mControlSliceWidth;
    public final LocalBluetoothProfileManager mProfileManager;
    public final AnonymousClass1 mReceiver =
            new BroadcastReceiver() { // from class: com.android.settings.panel.VolumePanel.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if ("com.android.settings.panel.action.CLOSE_PANEL"
                            .equals(intent.getAction())) {
                        PanelFragment.LocalPanelCallback localPanelCallback =
                                VolumePanel.this.mCallback;
                        PanelFragment.this.mPanelClosedKey = "others";
                        localPanelCallback.getFragmentActivity().finish();
                    }
                }
            };

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settings.panel.VolumePanel$1] */
    public VolumePanel(FragmentActivity fragmentActivity) {
        this.mContext = fragmentActivity.getApplicationContext();
        this.mControlSliceWidth =
                fragmentActivity.getWindowManager().getCurrentWindowMetrics().getBounds().width()
                        - (fragmentActivity
                                        .getResources()
                                        .getDimensionPixelSize(
                                                R.dimen.panel_slice_Horizontal_padding)
                                * 2);
        FutureTask futureTask =
                new FutureTask(
                        new Callable() { // from class:
                                         // com.android.settings.panel.VolumePanel$$ExternalSyntheticLambda0
                            @Override // java.util.concurrent.Callable
                            public final Object call() {
                                return LocalBluetoothManager.getInstance(
                                        VolumePanel.this.mContext, Utils.mOnInitCallback);
                            }
                        });
        try {
            futureTask.run();
            LocalBluetoothManager localBluetoothManager = (LocalBluetoothManager) futureTask.get();
            if (localBluetoothManager != null) {
                this.mProfileManager = localBluetoothManager.mProfileManager;
            }
        } catch (InterruptedException | ExecutionException unused) {
            Log.w("VolumePanel", "Error getting LocalBluetoothManager.");
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1655;
    }

    @Override // com.android.settings.panel.PanelContent
    public final Intent getSeeMoreIntent() {
        return new Intent("android.settings.SOUND_SETTINGS")
                .setPackage(this.mContext.getPackageName())
                .addFlags(268435456);
    }

    @Override // com.android.settings.panel.PanelContent
    public final List getSlices() {
        A2dpProfile a2dpProfile;
        ArrayList arrayList = new ArrayList();
        arrayList.add(CustomSliceRegistry.REMOTE_MEDIA_SLICE_URI);
        arrayList.add(CustomSliceRegistry.VOLUME_MEDIA_URI);
        Uri uri = null;
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
        BluetoothDevice activeDevice =
                (localBluetoothProfileManager == null
                                || (a2dpProfile = localBluetoothProfileManager.mA2dpProfile)
                                        == null)
                        ? null
                        : a2dpProfile.getActiveDevice();
        if (activeDevice != null) {
            String controlUriMetaData = BluetoothUtils.getControlUriMetaData(activeDevice);
            if (!TextUtils.isEmpty(controlUriMetaData)) {
                try {
                    uri = Uri.parse(controlUriMetaData + this.mControlSliceWidth);
                } catch (NullPointerException unused) {
                    Log.d("VolumePanel", "unable to parse uri");
                }
            }
        }
        if (uri != null) {
            Log.d("VolumePanel", "add extra control slice");
            arrayList.add(uri);
        }
        arrayList.add(CustomSliceRegistry.MEDIA_OUTPUT_INDICATOR_SLICE_URI);
        arrayList.add(CustomSliceRegistry.VOLUME_CALL_URI);
        arrayList.add(CustomSliceRegistry.VOLUME_SEPARATE_RING_URI);
        arrayList.add(CustomSliceRegistry.VOLUME_NOTIFICATION_URI);
        arrayList.add(CustomSliceRegistry.VOLUME_ALARM_URI);
        return arrayList;
    }

    @Override // com.android.settings.panel.PanelContent
    public final CharSequence getTitle() {
        return this.mContext.getText(R.string.sound_settings);
    }

    @Override // com.android.settings.panel.PanelContent
    public final int getViewType() {
        return 1;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        this.mContext.unregisterReceiver(this.mReceiver);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        this.mContext.registerReceiver(
                this.mReceiver,
                AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(
                        "com.android.settings.panel.action.CLOSE_PANEL"),
                2);
    }

    @Override // com.android.settings.panel.PanelContent
    public final void registerCallback(PanelFragment.LocalPanelCallback localPanelCallback) {
        this.mCallback = localPanelCallback;
    }
}
