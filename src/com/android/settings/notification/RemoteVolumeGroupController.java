package com.android.settings.notification;

import android.annotation.NonNull;
import android.annotation.Nullable;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.media.MediaRouter2Manager;
import android.media.RoutingSessionInfo;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnDestroy;
import com.android.settingslib.media.LocalMediaManager;
import com.android.settingslib.media.MediaDevice;
import com.android.settingslib.utils.ThreadUtils;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RemoteVolumeGroupController extends BasePreferenceController
        implements Preference.OnPreferenceChangeListener,
                LifecycleObserver,
                OnDestroy,
                LocalMediaManager.DeviceCallback {
    private static final String KEY_REMOTE_VOLUME_GROUP = "remote_media_group";
    static final String SWITCHER_PREFIX = "OUTPUT_SWITCHER";
    private static final String TAG = "RemoteVolumePrefCtr";
    LocalMediaManager mLocalMediaManager;

    @Nullable private PreferenceCategory mPreferenceCategory;
    MediaRouter2Manager mRouterManager;
    private final List<RoutingSessionInfo> mRoutingSessionInfos;

    public RemoteVolumeGroupController(Context context, String str) {
        super(context, str);
        this.mRoutingSessionInfos = new ArrayList();
        if (this.mLocalMediaManager == null) {
            LocalMediaManager localMediaManager = new LocalMediaManager(this.mContext, null);
            this.mLocalMediaManager = localMediaManager;
            localMediaManager.registerCallback(this);
            this.mLocalMediaManager.mInfoMediaManager.startScanOnRouter();
        }
        this.mRouterManager = MediaRouter2Manager.getInstance(context);
    }

    private void initRemoteMediaSession() {
        this.mRoutingSessionInfos.clear();
        this.mRoutingSessionInfos.addAll(
                this.mLocalMediaManager.mInfoMediaManager.getRemoteSessions());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDeviceListUpdate$1() {
        initRemoteMediaSession();
        refreshPreference();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPreferenceChange$0(Preference preference, Object obj) {
        this.mLocalMediaManager.adjustSessionVolume(
                ((Integer) obj).intValue(), preference.getKey());
    }

    private synchronized void refreshPreference() {
        if (!isAvailable()) {
            this.mPreferenceCategory.setVisible(false);
            return;
        }
        CharSequence text = this.mContext.getText(R.string.remote_media_volume_option_title);
        this.mPreferenceCategory.setVisible(true);
        for (RoutingSessionInfo routingSessionInfo : this.mRoutingSessionInfos) {
            CharSequence applicationLabel =
                    Utils.getApplicationLabel(
                            this.mContext, routingSessionInfo.getClientPackageName());
            RemoteVolumeSeekBarPreference remoteVolumeSeekBarPreference =
                    (RemoteVolumeSeekBarPreference)
                            this.mPreferenceCategory.findPreference(routingSessionInfo.getId());
            if (remoteVolumeSeekBarPreference != null) {
                if (remoteVolumeSeekBarPreference.mProgress != routingSessionInfo.getVolume()) {
                    remoteVolumeSeekBarPreference.setProgress(routingSessionInfo.getVolume(), true);
                }
                remoteVolumeSeekBarPreference.setEnabled(
                        this.mLocalMediaManager.shouldEnableVolumeSeekBar(routingSessionInfo));
            } else {
                RemoteVolumeSeekBarPreference remoteVolumeSeekBarPreference2 =
                        new RemoteVolumeSeekBarPreference(this.mContext);
                remoteVolumeSeekBarPreference2.setKey(routingSessionInfo.getId());
                remoteVolumeSeekBarPreference2.setTitle(text);
                remoteVolumeSeekBarPreference2.setMax(routingSessionInfo.getVolumeMax());
                remoteVolumeSeekBarPreference2.setProgress(routingSessionInfo.getVolume(), true);
                remoteVolumeSeekBarPreference2.setMin(0);
                remoteVolumeSeekBarPreference2.setOnPreferenceChangeListener(this);
                remoteVolumeSeekBarPreference2.setIcon(R.drawable.ic_volume_remote);
                remoteVolumeSeekBarPreference2.setEnabled(
                        this.mLocalMediaManager.shouldEnableVolumeSeekBar(routingSessionInfo));
                this.mPreferenceCategory.addPreference(remoteVolumeSeekBarPreference2);
            }
            Preference findPreference =
                    this.mPreferenceCategory.findPreference(
                            SWITCHER_PREFIX + routingSessionInfo.getId());
            boolean isEmpty =
                    this.mRouterManager
                            .getTransferableRoutes(routingSessionInfo.getClientPackageName())
                            .isEmpty();
            String string =
                    this.mContext.getString(R.string.media_output_label_title, applicationLabel);
            if (findPreference != null) {
                if (!isEmpty) {
                    applicationLabel = string;
                }
                findPreference.setTitle(applicationLabel);
                findPreference.setSummary(routingSessionInfo.getName());
                findPreference.setEnabled(!isEmpty);
            } else {
                Preference preference = new Preference(this.mContext);
                preference.setKey(SWITCHER_PREFIX + routingSessionInfo.getId());
                if (!isEmpty) {
                    applicationLabel = string;
                }
                preference.setTitle(applicationLabel);
                preference.setSummary(routingSessionInfo.getName());
                preference.setEnabled(!isEmpty);
                this.mPreferenceCategory.addPreference(preference);
            }
        }
        for (int i = 0; i < this.mPreferenceCategory.getPreferenceCount(); i += 2) {
            Preference preference2 = this.mPreferenceCategory.getPreference(i);
            Iterator<RoutingSessionInfo> it = this.mRoutingSessionInfos.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (TextUtils.equals(preference2.getKey(), it.next().getId())) {
                        break;
                    }
                } else {
                    Preference preference3 = this.mPreferenceCategory.getPreference(i + 1);
                    if (preference3 != null) {
                        this.mPreferenceCategory.removePreference(preference2);
                        this.mPreferenceCategory.removePreference(preference3);
                    }
                }
            }
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreferenceCategory =
                (PreferenceCategory) preferenceScreen.findPreference(getPreferenceKey());
        initRemoteMediaSession();
        refreshPreference();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mRoutingSessionInfos.isEmpty() ? 2 : 1;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY_REMOTE_VOLUME_GROUP;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!preference.getKey().startsWith(SWITCHER_PREFIX)) {
            return false;
        }
        for (RoutingSessionInfo routingSessionInfo : this.mRoutingSessionInfos) {
            if (TextUtils.equals(routingSessionInfo.getId(), preference.getKey().substring(15))) {
                this.mContext.sendBroadcast(
                        new Intent()
                                .setAction("com.android.systemui.action.LAUNCH_MEDIA_OUTPUT_DIALOG")
                                .setPackage("com.android.systemui")
                                .putExtra(
                                        "package_name", routingSessionInfo.getClientPackageName()));
                return true;
            }
        }
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnDestroy
    public void onDestroy() {
        this.mLocalMediaManager.unregisterCallback(this);
        this.mLocalMediaManager.mInfoMediaManager.stopScanOnRouter();
    }

    @Override // com.android.settingslib.media.LocalMediaManager.DeviceCallback
    public void onDeviceListUpdate(List<MediaDevice> list) {
        if (this.mPreferenceCategory == null) {
            return;
        }
        ThreadUtils.postOnMainThread(
                new Runnable() { // from class:
                                 // com.android.settings.notification.RemoteVolumeGroupController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        RemoteVolumeGroupController.this.lambda$onDeviceListUpdate$1();
                    }
                });
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(final Preference preference, final Object obj) {
        ThreadUtils.postOnBackgroundThread(
                new Runnable() { // from class:
                                 // com.android.settings.notification.RemoteVolumeGroupController$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        RemoteVolumeGroupController.this.lambda$onPreferenceChange$0(
                                preference, obj);
                    }
                });
        return true;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public RemoteVolumeGroupController(
            @NonNull Context context,
            @NonNull String str,
            @NonNull LocalMediaManager localMediaManager,
            @NonNull MediaRouter2Manager mediaRouter2Manager) {
        super(context, str);
        this.mRoutingSessionInfos = new ArrayList();
        this.mLocalMediaManager = localMediaManager;
        this.mRouterManager = mediaRouter2Manager;
        localMediaManager.registerCallback(this);
        this.mLocalMediaManager.mInfoMediaManager.startScanOnRouter();
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}

    public /* bridge */ /* synthetic */ void onAboutToConnectDeviceRemoved() {}

    @Override // com.android.settingslib.media.LocalMediaManager.DeviceCallback
    public /* bridge */ /* synthetic */ void onDeviceAttributesChanged() {}

    @Override // com.android.settingslib.media.LocalMediaManager.DeviceCallback
    public /* bridge */ /* synthetic */ void onRequestFailed(int i) {}

    @Override // com.android.settingslib.media.LocalMediaManager.DeviceCallback
    public void onSelectedDeviceStateChanged(MediaDevice mediaDevice, int i) {}

    public /* bridge */ /* synthetic */ void onAboutToConnectDeviceAdded(
            String str, String str2, Drawable drawable) {}
}
