package com.samsung.android.settings.bluetooth.bluetoothcast;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.service.quicksettings.TileService;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.bluetooth.Utils;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.bluetooth.BluetoothUtils;

import com.samsung.android.bluetooth.SemBluetoothCastAdapter;
import com.samsung.android.settings.bluetooth.BluetoothCastSettings;
import com.samsung.android.settings.bluetooth.BluetoothCastTile;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BluetoothCastTogglePreferenceController extends TogglePreferenceController
        implements LifecycleObserver {
    private static final String BLUETOOTH_CAST_MODE = "bluetooth_cast_mode";
    private static final Uri BLUETOOTH_CAST_MODE_URI =
            Settings.Secure.getUriFor(BLUETOOTH_CAST_MODE);
    private static final int CAST_MODE_OFF = 0;
    private static final int CAST_MODE_ON = 1;
    private static final String KEY_MUSIC_SHARE_PREF = "music_share_pref_key";
    private static final String PREF_FROM_CONNECTED_DEVICE = "music_share_setting";
    private static final String TAG = "BluetoothCastTogglePreferenceController";
    private ContentObserver mContentObserver;
    private Preference mPreference;

    public BluetoothCastTogglePreferenceController(Context context, String str) {
        super(context, str);
        this.mContentObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.samsung.android.settings.bluetooth.bluetoothcast.BluetoothCastTogglePreferenceController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z, Uri uri) {
                        if (BluetoothCastTogglePreferenceController.this.mPreference != null) {
                            BluetoothCastTogglePreferenceController
                                    bluetoothCastTogglePreferenceController =
                                            BluetoothCastTogglePreferenceController.this;
                            bluetoothCastTogglePreferenceController.updateState(
                                    bluetoothCastTogglePreferenceController.mPreference);
                        }
                    }
                };
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (BluetoothUtils.isEnabledUltraPowerSaving(this.mContext)
                        || !SemBluetoothCastAdapter.isBluetoothCastSupported())
                ? 2
                : 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (preference == null || !getPreferenceKey().equals(preference.getKey())) {
            return false;
        }
        if (!getPreferenceKey().equals(PREF_FROM_CONNECTED_DEVICE)) {
            SALogging.insertSALog(
                    this.mContext.getString(R.string.screen_bluetooth_advanced_setting),
                    this.mContext.getString(
                            R.string.event_bluetooth_advanced_settings_music_share));
        }
        Bundle bundle = new Bundle();
        bundle.putString(KEY_MUSIC_SHARE_PREF, getPreferenceKey());
        Context context = this.mContext;
        if (context instanceof SettingsActivity) {
            SubSettingLauncher subSettingLauncher = new SubSettingLauncher(context);
            SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
            launchRequest.mSourceMetricsCategory = 24;
            launchRequest.mDestinationName = BluetoothCastSettings.class.getCanonicalName();
            launchRequest.mArguments = bundle;
            subSettingLauncher.launch();
            return true;
        }
        Context applicationContext = context.getApplicationContext();
        Intent intent = new Intent("com.samsung.settings.BLUETOOTH_CAST_SETTINGS");
        boolean z = Utils.DEBUG;
        intent.setFlags(335544320);
        try {
            applicationContext.startActivity(intent);
            return true;
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            return true;
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return Settings.Secure.getInt(this.mContext.getContentResolver(), BLUETOOTH_CAST_MODE, 1)
                == 1;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        if (this.mContentObserver != null) {
            this.mContext.getContentResolver().unregisterContentObserver(this.mContentObserver);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        if (this.mContentObserver != null) {
            this.mContext
                    .getContentResolver()
                    .registerContentObserver(BLUETOOTH_CAST_MODE_URI, false, this.mContentObserver);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        SALogging.insertSALog(
                z ? 1L : 0L,
                this.mContext.getString(R.string.screen_bluetooth_advanced_setting),
                this.mContext.getString(
                        R.string.event_bluetooth_advanced_settings_music_share_onoff));
        Settings.Secure.putInt(this.mContext.getContentResolver(), BLUETOOTH_CAST_MODE, z ? 1 : 0);
        TileService.requestListeningState(
                this.mContext,
                new ComponentName(this.mContext, (Class<?>) BluetoothCastTile.class));
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
