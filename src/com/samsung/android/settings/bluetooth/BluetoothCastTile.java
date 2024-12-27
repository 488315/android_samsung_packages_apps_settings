package com.samsung.android.settings.bluetooth;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.util.ArraySet;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.accounts.ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamMediaService$4$$ExternalSyntheticOutline0;

import com.samsung.android.bluetooth.SemBluetoothCastAdapter;
import com.samsung.android.graphics.spr.SemPathRenderingDrawable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BluetoothCastTile extends TileService {
    public List mAvailableDevices;
    public JSONObject mDisallowedDevices;
    public SharedPreferences pref;
    public boolean isBluetoothCastOn = false;
    public Context mContext = null;
    public final SettingsObserver mSettingsObserver = new SettingsObserver(new Handler());
    public final AnonymousClass1 mReceiver = new BroadcastReceiver() { // from class:
                // com.samsung.android.settings.bluetooth.BluetoothCastTile.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if (!AudioStreamMediaService$4$$ExternalSyntheticOutline0.m(
                            "action = ",
                            action,
                            "BluetoothCastTile",
                            "com.samsung.android.settings.bluetooth.AVAILABLE_DEVICE")) {
                        if (action.equals(
                                "com.samsung.android.bluetooth.audiocast.action.device.CONNECTION_STATE_CHANGED")) {
                            int intExtra =
                                    intent.getIntExtra(
                                            "com.samsung.android.bluetooth.cast.extra.STATE", 0);
                            if (intExtra == 2 || intExtra == 0) {
                                Log.d(
                                        "BluetoothCastTile",
                                        "onReceive : " + action + " - state : " + intExtra);
                                BluetoothCastTile.this.semUpdateDetailView();
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    String str =
                            (String)
                                    ((ArrayList) BluetoothCastTile.this.mAvailableDevices)
                                            .get(intent.getIntExtra("count", 0));
                    BluetoothCastTile bluetoothCastTile = BluetoothCastTile.this;
                    if (bluetoothCastTile.mDisallowedDevices == null) {
                        try {
                            String string =
                                    Settings.Secure.getString(
                                            bluetoothCastTile.mContext.getContentResolver(),
                                            "bluetooth_cast_disallowed_devices");
                            if (string == null) {
                                BluetoothCastTile.this.mDisallowedDevices = new JSONObject();
                            } else {
                                BluetoothCastTile.this.mDisallowedDevices = new JSONObject(string);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    JSONObject jSONObject = BluetoothCastTile.this.mDisallowedDevices;
                    if (jSONObject != null) {
                        if (jSONObject.has(str)) {
                            BluetoothCastTile.this.mDisallowedDevices.remove(str);
                        } else {
                            try {
                                BluetoothCastTile.this.mDisallowedDevices.put(str, false);
                            } catch (JSONException e2) {
                                e2.printStackTrace();
                            }
                        }
                        Settings.Secure.putString(
                                BluetoothCastTile.this.mContext.getContentResolver(),
                                "bluetooth_cast_disallowed_devices",
                                BluetoothCastTile.this.mDisallowedDevices.toString());
                    }
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettingsObserver extends ContentObserver {
        public final Uri BLUETOOTH_CAST_DISALLOWED_URI;
        public final Uri BLUETOOTH_CAST_MODE_URI;

        public SettingsObserver(Handler handler) {
            super(handler);
            this.BLUETOOTH_CAST_MODE_URI = Settings.Secure.getUriFor("bluetooth_cast_mode");
            this.BLUETOOTH_CAST_DISALLOWED_URI =
                    Settings.Secure.getUriFor("bluetooth_cast_disallowed_devices");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            Log.d("BluetoothCastTile", "onChange");
            if (!this.BLUETOOTH_CAST_MODE_URI.equals(uri)) {
                if (this.BLUETOOTH_CAST_DISALLOWED_URI.equals(uri)) {
                    BluetoothCastTile.this.semUpdateDetailView();
                }
            } else {
                int i =
                        Settings.Secure.getInt(
                                BluetoothCastTile.this.mContext.getContentResolver(),
                                "bluetooth_cast_mode",
                                1);
                BluetoothCastTile bluetoothCastTile = BluetoothCastTile.this;
                boolean z2 = i == 1;
                bluetoothCastTile.isBluetoothCastOn = z2;
                bluetoothCastTile.updateState(z2);
                BluetoothCastTile.this.semUpdateDetailView();
            }
        }
    }

    @Override // android.service.quicksettings.TileService, android.app.Service
    public final IBinder onBind(Intent intent) {
        Log.d("BluetoothCastTile", "onBind()");
        if (this.mContext == null) {
            Log.d("BluetoothCastTile", "mContext is null");
            this.mContext = getApplicationContext();
        }
        return super.onBind(intent);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.service.quicksettings.TileService
    public final void onClick() {
        Log.d("BluetoothCastTile", "onClick");
        if (SemBluetoothCastAdapter.isBluetoothCastSupported()) {
            int enterprisePolicyEnabled =
                    Utils.getEnterprisePolicyEnabled(
                            this.mContext,
                            "content://com.sec.knox.provider/RestrictionPolicy3",
                            "isSettingsChangesAllowed",
                            new String[] {"false"});
            boolean z = enterprisePolicyEnabled != -1 && enterprisePolicyEnabled == 0;
            AbsAdapter$$ExternalSyntheticOutline0.m(
                    "isBlockedEdmSettingsChange: ", "BluetoothCastTile", z);
            if (z) {
                Context context = this.mContext;
                Toast makeText =
                        Toast.makeText(
                                context,
                                context.getString(
                                        R.string.prevent_to_change_by_device_policy,
                                        context.getString(R.string.sec_bluetooth_cast_title)),
                                0);
                if (makeText != null) {
                    makeText.show();
                    return;
                }
                return;
            }
            byte b = Settings.Secure.getInt(getContentResolver(), "lock_function_val", 0) != 0;
            boolean z2 =
                    Settings.Secure.getInt(
                                    this.mContext.getContentResolver(), "bluetooth_cast_mode", 1)
                            != 0;
            this.isBluetoothCastOn = z2;
            if (b != true) {
                this.isBluetoothCastOn = !z2;
                Settings.Secure.putInt(
                        this.mContext.getContentResolver(),
                        "bluetooth_cast_mode",
                        this.isBluetoothCastOn ? 1 : 0);
            } else if (isSecure()) {
                unlockAndRun(
                        new Runnable() { // from class:
                                         // com.samsung.android.settings.bluetooth.BluetoothCastTile.2
                            @Override // java.lang.Runnable
                            public final void run() {
                                Log.d("BluetoothCastTile", "lock has been released");
                                BluetoothCastTile bluetoothCastTile = BluetoothCastTile.this;
                                bluetoothCastTile.isBluetoothCastOn =
                                        !bluetoothCastTile.isBluetoothCastOn;
                                Settings.Secure.putInt(
                                        bluetoothCastTile.mContext.getContentResolver(),
                                        "bluetooth_cast_mode",
                                        BluetoothCastTile.this.isBluetoothCastOn ? 1 : 0);
                            }
                        });
            } else {
                this.isBluetoothCastOn = !this.isBluetoothCastOn;
                Settings.Secure.putInt(
                        this.mContext.getContentResolver(),
                        "bluetooth_cast_mode",
                        this.isBluetoothCastOn ? 1 : 0);
            }
        }
    }

    @Override // android.app.Service
    public final void onCreate() {
        super.onCreate();
        Log.d("BluetoothCastTile", "onCreate");
        if (this.mContext == null) {
            Log.d("BluetoothCastTile", "mContext is null");
            this.mContext = getApplicationContext();
        }
        this.pref = this.mContext.getSharedPreferences("bluetooth_cast_pref", 0);
        ArrayList arrayList = new ArrayList();
        this.mAvailableDevices = arrayList;
        arrayList.addAll(
                this.pref.getStringSet("bluetooth_cast_available_devices", new ArraySet()));
        SettingsObserver settingsObserver = this.mSettingsObserver;
        ContentResolver contentResolver = BluetoothCastTile.this.getContentResolver();
        contentResolver.registerContentObserver(
                settingsObserver.BLUETOOTH_CAST_MODE_URI, false, settingsObserver);
        contentResolver.registerContentObserver(
                settingsObserver.BLUETOOTH_CAST_DISALLOWED_URI, false, settingsObserver);
        registerReceiver(true);
    }

    @Override // android.service.quicksettings.TileService, android.app.Service
    public final void onDestroy() {
        super.onDestroy();
        Log.d("BluetoothCastTile", "onDestroy");
        SettingsObserver settingsObserver = this.mSettingsObserver;
        BluetoothCastTile.this.getContentResolver().unregisterContentObserver(settingsObserver);
        registerReceiver(false);
    }

    @Override // android.service.quicksettings.TileService
    public final void onStartListening() {
        super.onStartListening();
        Log.d("BluetoothCastTile", "onStartListening");
        boolean z =
                Settings.Secure.getInt(this.mContext.getContentResolver(), "bluetooth_cast_mode", 1)
                        != 0;
        this.isBluetoothCastOn = z;
        updateState(z);
        semUpdateDetailView();
    }

    @Override // android.service.quicksettings.TileService
    public final void onStopListening() {
        super.onStopListening();
        Log.d("BluetoothCastTile", "onStopListening");
    }

    @Override // android.service.quicksettings.TileService
    public final void onTileAdded() {
        super.onTileAdded();
        Log.d("BluetoothCastTile", "onTileAdded");
        TileService.requestListeningState(
                getApplicationContext(),
                new ComponentName(getApplicationContext(), (Class<?>) BluetoothCastTile.class));
    }

    @Override // android.service.quicksettings.TileService
    public final void onTileRemoved() {
        super.onTileRemoved();
        Log.d("BluetoothCastTile", "onTileRemoved");
    }

    @Override // android.app.Service
    public final boolean onUnbind(Intent intent) {
        Log.d("BluetoothCastTile", "onUnBind()");
        return super.onUnbind(intent);
    }

    public final void registerReceiver(boolean z) {
        if (z) {
            IntentFilter m =
                    ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0.m(
                            "com.samsung.android.settings.bluetooth.AVAILABLE_DEVICE",
                            "com.samsung.android.bluetooth.audiocast.action.device.CONNECTION_STATE_CHANGED");
            Context context = this.mContext;
            if (context != null) {
                context.registerReceiver(this.mReceiver, m);
                return;
            } else {
                Log.e("BluetoothCastTile", "unable to register receiver");
                return;
            }
        }
        Context context2 = this.mContext;
        if (context2 == null) {
            Log.e("BluetoothCastTile", "unable to unregister receiver");
            return;
        }
        try {
            context2.unregisterReceiver(this.mReceiver);
        } catch (IllegalArgumentException unused) {
            Log.e(
                    "BluetoothCastTile",
                    "unable to unregister receiver. Receiver was not registered or already"
                        + " unregistered.");
        }
    }

    public final RemoteViews semGetDetailView() {
        Log.d("BluetoothCastTile", "semGetDetailView");
        return null;
    }

    public final CharSequence semGetDetailViewTitle() {
        return this.mContext.getString(R.string.sec_bluetooth_cast_title);
    }

    public final Intent semGetSettingsIntent() {
        Log.d("BluetoothCastTile", "semGetSettingsIntent start");
        if (SemBluetoothCastAdapter.isBluetoothCastSupported()) {
            return new Intent("com.samsung.settings.BLUETOOTH_CAST_SETTINGS");
        }
        return null;
    }

    public final boolean semIsToggleButtonChecked() {
        boolean z =
                Settings.Secure.getInt(this.mContext.getContentResolver(), "bluetooth_cast_mode", 1)
                        != 0;
        this.isBluetoothCastOn = z;
        return z;
    }

    public final boolean semIsToggleButtonExists() {
        return true;
    }

    public final void semSetToggleButtonChecked(boolean z) {
        Log.d("BluetoothCastTile", "semSetToggleButtonChecked start");
        this.isBluetoothCastOn = z;
        Settings.Secure.putInt(
                this.mContext.getContentResolver(),
                "bluetooth_cast_mode",
                this.isBluetoothCastOn ? 1 : 0);
    }

    public final void updateState(boolean z) {
        Bitmap bitmap;
        Tile qsTile = getQsTile();
        if (qsTile == null) {
            Log.e("BluetoothCastTile", "tile is null");
            return;
        }
        SemPathRenderingDrawable drawable =
                getResources().getDrawable(R.drawable.sec_st_ic_bluetoothcasttile);
        Icon icon = null;
        if (drawable instanceof SemPathRenderingDrawable) {
            bitmap = drawable.getBitmap();
        } else {
            Log.e("BluetoothCastTile", "drawable is not suitable for SemPathRenderingDrawable");
            bitmap = null;
        }
        if (bitmap != null) {
            icon = Icon.createWithBitmap(bitmap);
        } else {
            Log.e("BluetoothCastTile", "unable to generate bitmap");
        }
        qsTile.setIcon(icon);
        qsTile.setLabel(this.mContext.getString(R.string.sec_bluetooth_cast_title));
        qsTile.setState(z ? 2 : 1);
        qsTile.updateTile();
        semFireToggleStateChanged(z, true);
    }
}
