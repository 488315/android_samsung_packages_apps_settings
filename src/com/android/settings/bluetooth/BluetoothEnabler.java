package com.android.settings.bluetooth;

import android.app.admin.DevicePolicyManager;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothLeAudio;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Message;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.SecPreferenceUtils;
import androidx.preference.SecSwitchPreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;

import com.android.internal.logging.MetricsLogger;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.WirelessUtils;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LeAudioProfile;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfile;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settingslib.bluetooth.SemBluetoothCallback;
import com.sec.ims.volte2.data.VolteConstants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothEnabler implements BluetoothCallback, SemBluetoothCallback {
    public Context mContext;
    public IntentFilter mIntentFilter;
    public boolean mIsUserInteraction;
    public LocalBluetoothAdapter mLocalAdapter;
    public LocalBluetoothManager mLocalManager;
    public boolean mPolicyEnabled;
    public RestrictionUtils mRestrictionUtils;
    public final BluetoothSwitchType mSwitchType;
    public final AnonymousClass1 mHandler =
            new Handler() { // from class: com.android.settings.bluetooth.BluetoothEnabler.1
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    BluetoothSwitchType bluetoothSwitchType;
                    LocalBluetoothManager localBluetoothManager;
                    LocalBluetoothProfileManager localBluetoothProfileManager;
                    LeAudioProfile leAudioProfile;
                    int i = message.what;
                    BluetoothEnabler bluetoothEnabler = BluetoothEnabler.this;
                    if (i == 0) {
                        if (bluetoothEnabler.mLocalAdapter.setBluetoothEnabled(
                                        message.getData().getBoolean("is_bluetooth_on"))
                                || (bluetoothSwitchType = bluetoothEnabler.mSwitchType) == null) {
                            return;
                        }
                        bluetoothSwitchType.setChecked(false);
                        if (bluetoothEnabler.mPolicyEnabled) {
                            bluetoothEnabler.mSwitchType.setEnabled(true);
                            return;
                        }
                        return;
                    }
                    if (i != 1) {
                        return;
                    }
                    int i2 = message.arg1;
                    if (i2 <= 0
                            || i2 >= 3
                            || (localBluetoothManager = bluetoothEnabler.mLocalManager) == null
                            || (localBluetoothProfileManager =
                                            localBluetoothManager.mProfileManager)
                                    == null
                            || (leAudioProfile = localBluetoothProfileManager.mLeAudioProfile)
                                    == null
                            || leAudioProfile.mIsProfileReady) {
                        BluetoothSwitchType bluetoothSwitchType2 = bluetoothEnabler.mSwitchType;
                        if (bluetoothSwitchType2 != null) {
                            bluetoothSwitchType2.setBluetoothSummary();
                            return;
                        }
                        return;
                    }
                    bluetoothEnabler.mHandler.removeMessages(1);
                    Message obtainMessage = bluetoothEnabler.mHandler.obtainMessage(1);
                    obtainMessage.arg1 = i2 + 1;
                    bluetoothEnabler.mHandler.sendMessageDelayed(obtainMessage, 200L);
                }
            };
    public final AnonymousClass2 mReceiver = new BroadcastReceiver() { // from class:
                // com.android.settings.bluetooth.BluetoothEnabler.2
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if (action == null) {
                        Log.d("BluetoothEnabler", "onReceive:: received null intent");
                        return;
                    }
                    Log.d("BluetoothEnabler", "onReceive:: action : ".concat(action));
                    if (action.equals(
                            "com.samsung.bluetooth.action.GEAR_CONNECTION_STATE_CHANGED")) {
                        removeMessages(1);
                        sendEmptyMessageDelayed(1, 50L);
                    } else if (action.equals(
                            "com.samsung.android.bluetooth.audiocast.action.device.CONNECTION_STATE_CHANGED")) {
                        removeMessages(1);
                        sendEmptyMessageDelayed(1, 50L);
                    }
                }
            };
    public final AnonymousClass3 mContentObserver =
            new ContentObserver(
                    new Handler()) { // from class:
                                     // com.android.settings.bluetooth.BluetoothEnabler.3
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    BluetoothEnabler bluetoothEnabler = BluetoothEnabler.this;
                    if (bluetoothEnabler.mContext != null) {
                        bluetoothEnabler.mSwitchType.setEnabled(
                                !BluetoothUtils.isSatelliteModeOn(r0));
                        BluetoothEnabler.this.mSwitchType.setChecked(
                                !BluetoothUtils.isSatelliteModeOn(r1.mContext));
                    }
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class BluetoothSwitchType
            implements CompoundButton.OnCheckedChangeListener,
                    Preference.OnPreferenceChangeListener {
        public final Object mSwitchObject;

        public BluetoothSwitchType(Object obj) {
            this.mSwitchObject = obj;
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            Log.d("BluetoothEnabler", "onCheckedChanged()");
            BluetoothEnabler bluetoothEnabler = BluetoothEnabler.this;
            if (!bluetoothEnabler.mIsUserInteraction
                    || z
                    || Settings.Global.getInt(
                                    bluetoothEnabler.mContext.getContentResolver(),
                                    "bluetooth_dial_for_btoff",
                                    0)
                            != 0) {
                BluetoothEnabler.this.onSwitchStateChange(z);
                return;
            }
            setChecked(!z);
            if (BluetoothEnabler.this.onSwitchStateChangeRestriction(z)) {
                BluetoothEnabler bluetoothEnabler2 = BluetoothEnabler.this;
                bluetoothEnabler2.getClass();
                Intent intent = new Intent("com.samsung.settings.bluetooth.offdialog.LAUNCH");
                intent.setFlags(335544320);
                Context context = bluetoothEnabler2.mContext;
                if (context != null) {
                    context.startActivity(intent);
                }
            }
        }

        @Override // androidx.preference.Preference.OnPreferenceChangeListener
        public final boolean onPreferenceChange(Preference preference, Object obj) {
            Log.d("BluetoothEnabler", "onPreferenceChange()");
            if (obj instanceof Boolean) {
                Boolean bool = (Boolean) obj;
                if (!bool.booleanValue()
                        && Settings.Global.getInt(
                                        BluetoothEnabler.this.mContext.getContentResolver(),
                                        "bluetooth_dial_for_btoff",
                                        0)
                                == 0) {
                    setChecked(!bool.booleanValue());
                    if (BluetoothEnabler.this.onSwitchStateChangeRestriction(bool.booleanValue())) {
                        BluetoothEnabler bluetoothEnabler = BluetoothEnabler.this;
                        bluetoothEnabler.getClass();
                        Intent intent =
                                new Intent("com.samsung.settings.bluetooth.offdialog.LAUNCH");
                        intent.setFlags(335544320);
                        Context context = bluetoothEnabler.mContext;
                        if (context != null) {
                            context.startActivity(intent);
                        }
                    }
                    return false;
                }
            }
            return BluetoothEnabler.this.onSwitchStateChange(((Boolean) obj).booleanValue());
        }

        public final void setBluetoothSummary() {
            if (this.mSwitchObject instanceof SwitchPreferenceCompat) {
                int state = BluetoothEnabler.this.mLocalAdapter.mAdapter.getState();
                String str = null;
                if (state == 11 || state == 12) {
                    if (BluetoothEnabler.this.mLocalAdapter.mAdapter.getConnectionState() == 2) {
                        LocalBluetoothAdapter localBluetoothAdapter =
                                BluetoothEnabler.this.mLocalAdapter;
                        List<BluetoothDevice> connectedDeviceList =
                                localBluetoothAdapter.mAdapter.getConnectedDeviceList();
                        if (connectedDeviceList != null) {
                            ArrayList arrayList = new ArrayList();
                            LeAudioProfile leAudioProfile =
                                    localBluetoothAdapter.mProfileManager.mLeAudioProfile;
                            if (leAudioProfile != null) {
                                for (BluetoothDevice bluetoothDevice : connectedDeviceList) {
                                    BluetoothLeAudio bluetoothLeAudio = leAudioProfile.mService;
                                    int groupId =
                                            bluetoothLeAudio == null
                                                    ? -1
                                                    : bluetoothLeAudio.getGroupId(bluetoothDevice);
                                    if (groupId == -1) {
                                        arrayList.add(bluetoothDevice);
                                    } else {
                                        Iterator it = arrayList.iterator();
                                        boolean z = false;
                                        while (it.hasNext()) {
                                            BluetoothDevice bluetoothDevice2 =
                                                    (BluetoothDevice) it.next();
                                            BluetoothLeAudio bluetoothLeAudio2 =
                                                    leAudioProfile.mService;
                                            if ((bluetoothLeAudio2 == null
                                                            ? -1
                                                            : bluetoothLeAudio2.getGroupId(
                                                                    bluetoothDevice2))
                                                    == groupId) {
                                                z = true;
                                            }
                                        }
                                        if (!z) {
                                            arrayList.add(bluetoothDevice);
                                        }
                                    }
                                }
                                connectedDeviceList = arrayList;
                            }
                        }
                        if (connectedDeviceList != null) {
                            int size = connectedDeviceList.size();
                            str =
                                    size == 1
                                            ? ((BluetoothDevice) connectedDeviceList.get(0))
                                                    .getAlias()
                                            : BluetoothEnabler.this.mContext.getString(
                                                    R.string.bluetooth_summary_connected_devices,
                                                    Integer.valueOf(size));
                        }
                    }
                    SecSwitchPreferenceScreen secSwitchPreferenceScreen =
                            (SecSwitchPreferenceScreen) this.mSwitchObject;
                    secSwitchPreferenceScreen.getClass();
                    SecPreferenceUtils.applySummaryColor(secSwitchPreferenceScreen, true);
                } else if (state == 13 || state == 10) {
                    SecSwitchPreferenceScreen secSwitchPreferenceScreen2 =
                            (SecSwitchPreferenceScreen) this.mSwitchObject;
                    secSwitchPreferenceScreen2.getClass();
                    SecPreferenceUtils.applySummaryColor(secSwitchPreferenceScreen2, false);
                } else {
                    str = ApnSettings.MVNO_NONE;
                }
                ((SwitchPreferenceCompat) this.mSwitchObject).setSummary(str);
            }
        }

        public final void setChecked(boolean z) {
            BluetoothEnabler.this.mIsUserInteraction = false;
            Object obj = this.mSwitchObject;
            if (obj instanceof SwitchPreferenceCompat) {
                ((SwitchPreferenceCompat) obj).setChecked(z);
            } else if (obj instanceof SettingsMainSwitchBar) {
                ((SettingsMainSwitchBar) obj).setChecked(z);
            }
            BluetoothEnabler.this.mIsUserInteraction = true;
        }

        public final void setEnabled(boolean z) {
            Object obj = this.mSwitchObject;
            if (obj instanceof SettingsMainSwitchBar) {
                ((SettingsMainSwitchBar) obj).setEnabled(z);
            } else if (obj instanceof SwitchPreferenceCompat) {
                ((SwitchPreferenceCompat) obj).setEnabled(z);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settings.bluetooth.BluetoothEnabler$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settings.bluetooth.BluetoothEnabler$2] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.settings.bluetooth.BluetoothEnabler$3] */
    public BluetoothEnabler() {
        Log.d(
                "BluetoothEnabler",
                "BluetoothEnabler(Context context, SwitchWidgetController switchController,\n"
                    + "            MetricsFeatureProvider metricsFeatureProvider, int"
                    + " metricsEvent,\n"
                    + "            RestrictionUtils restrictionUtils)");
    }

    public final void handleStateChanged(int i) {
        boolean isSatelliteModeOn = BluetoothUtils.isSatelliteModeOn(this.mContext);
        BluetoothSwitchType bluetoothSwitchType = this.mSwitchType;
        if (isSatelliteModeOn) {
            bluetoothSwitchType.setEnabled(false);
            return;
        }
        switch (i) {
            case 10:
                if (bluetoothSwitchType != null) {
                    bluetoothSwitchType.setChecked(false);
                    bluetoothSwitchType.setEnabled(true);
                    break;
                }
                break;
            case 11:
                if (bluetoothSwitchType != null) {
                    bluetoothSwitchType.setChecked(true);
                    bluetoothSwitchType.setEnabled(false);
                    break;
                }
                break;
            case 12:
                if (bluetoothSwitchType != null) {
                    bluetoothSwitchType.setChecked(true);
                    bluetoothSwitchType.setEnabled(true);
                    break;
                }
                break;
            case 13:
                if (bluetoothSwitchType != null) {
                    bluetoothSwitchType.setChecked(false);
                    bluetoothSwitchType.setEnabled(false);
                    break;
                }
                break;
            default:
                if (bluetoothSwitchType != null) {
                    bluetoothSwitchType.setChecked(false);
                    bluetoothSwitchType.setEnabled(true);
                    break;
                }
                break;
        }
        maybeEnforceRestrictions();
    }

    public final BluetoothSwitchType init(Context context, Object obj) {
        BluetoothSwitchType bluetoothSwitchType = new BluetoothSwitchType(obj);
        this.mContext = context;
        this.mIsUserInteraction = true;
        this.mRestrictionUtils = new RestrictionUtils();
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(context, Utils.mOnInitCallback);
        this.mLocalManager = localBluetoothManager;
        if (localBluetoothManager == null) {
            this.mLocalAdapter = null;
            bluetoothSwitchType.setEnabled(false);
        } else {
            this.mLocalAdapter = localBluetoothManager.mLocalAdapter;
        }
        IntentFilter intentFilter = new IntentFilter();
        this.mIntentFilter = intentFilter;
        intentFilter.addAction("com.samsung.bluetooth.action.GEAR_CONNECTION_STATE_CHANGED");
        this.mIntentFilter.addAction(
                "com.samsung.android.bluetooth.audiocast.action.device.CONNECTION_STATE_CHANGED");
        this.mPolicyEnabled = true;
        return bluetoothSwitchType;
    }

    public final RestrictedLockUtils.EnforcedAdmin maybeEnforceRestrictions() {
        RestrictionUtils restrictionUtils = this.mRestrictionUtils;
        Context context = this.mContext;
        restrictionUtils.getClass();
        RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced =
                RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                        context, UserHandle.myUserId(), "no_bluetooth");
        if (checkIfRestrictionEnforced == null) {
            RestrictionUtils restrictionUtils2 = this.mRestrictionUtils;
            Context context2 = this.mContext;
            restrictionUtils2.getClass();
            checkIfRestrictionEnforced =
                    RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                            context2, UserHandle.myUserId(), "no_config_bluetooth");
        }
        BluetoothSwitchType bluetoothSwitchType = this.mSwitchType;
        if (bluetoothSwitchType == null) {
            Log.e("BluetoothEnabler", "maybeEnforceRestrictions :: switch type is null");
        } else if (checkIfRestrictionEnforced != null) {
            Log.e("BluetoothEnabler", "maybeEnforceRestrictions :: restrict by admin");
            LocalBluetoothAdapter localBluetoothAdapter = this.mLocalAdapter;
            bluetoothSwitchType.setChecked(
                    localBluetoothAdapter != null
                            ? localBluetoothAdapter.mAdapter.isEnabled()
                            : false);
        } else {
            Log.e("BluetoothEnabler", "maybeEnforceRestrictions :: can not get restriction policy");
        }
        return checkIfRestrictionEnforced;
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onBluetoothStateChanged(int i) {
        Log.i("BluetoothEnabler", "onBluetoothStateChanged bluetoothState =" + i);
        handleStateChanged(i);
        AnonymousClass1 anonymousClass1 = this.mHandler;
        anonymousClass1.removeMessages(1);
        anonymousClass1.sendEmptyMessage(1);
    }

    @Override // com.samsung.android.settingslib.bluetooth.SemBluetoothCallback
    public final void onProfileStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice,
            LocalBluetoothProfile localBluetoothProfile,
            int i,
            int i2) {
        Log.i(
                "BluetoothEnabler",
                "onProfileStateChanged(), profile - "
                        + localBluetoothProfile
                        + ", newState - "
                        + i);
        AnonymousClass1 anonymousClass1 = this.mHandler;
        anonymousClass1.removeMessages(1);
        anonymousClass1.sendEmptyMessageDelayed(1, 500L);
    }

    public final boolean onSwitchStateChange(boolean z) {
        String string;
        String string2;
        String string3;
        int i;
        if (!onSwitchStateChangeRestriction(z)) {
            return false;
        }
        boolean z2 = this.mIsUserInteraction;
        BluetoothSwitchType bluetoothSwitchType = this.mSwitchType;
        if (bluetoothSwitchType != null) {
            bluetoothSwitchType.setEnabled(false);
        }
        if (!z2) {
            return true;
        }
        AnonymousClass1 anonymousClass1 = this.mHandler;
        anonymousClass1.removeMessages(0);
        Message message = new Message();
        message.what = 0;
        message.getData().putBoolean("is_bluetooth_on", z);
        anonymousClass1.sendMessageDelayed(message, 400L);
        if (bluetoothSwitchType == null
                || !(bluetoothSwitchType.mSwitchObject instanceof SettingsMainSwitchBar)) {
            string = this.mContext.getResources().getString(R.string.screen_connection_setting);
            string2 =
                    this.mContext
                            .getResources()
                            .getString(R.string.event_connection_setting_on_off_switch);
        } else {
            string = this.mContext.getResources().getString(R.string.screen_bluetooth_setting);
            string2 =
                    this.mContext
                            .getResources()
                            .getString(R.string.event_bluetooth_setting_on_off_switch);
        }
        if (z) {
            string3 = this.mContext.getResources().getString(R.string.detail_bluetooth_on);
            i = 23;
        } else {
            if (this.mLocalAdapter.mAdapter.getConnectionState() == 2) {
                SALogging.insertSALog(
                        this.mContext.getResources().getString(R.string.screen_bluetooth_global),
                        this.mContext.getResources().getString(R.string.event_bluetooth_bddc),
                        this.mContext
                                .getResources()
                                .getString(R.string.detail_bluetooth_bddc_settings_off));
            }
            string3 = this.mContext.getResources().getString(R.string.detail_bluetooth_off);
            i = 24;
        }
        SALogging.insertSALog(string, string2, string3);
        LoggingHelper.insertEventLogging(
                VolteConstants.ErrorCode.SIMULTANEOUS_CALL_LIMIT_HAS_ALREADY_BEEN_REACHED, 3209, z);
        Context context = this.mContext;
        UserHandle.getCallingUserId();
        Utils.insertMDMEventLog(context, i);
        return true;
    }

    public final boolean onSwitchStateChangeRestriction(boolean z) {
        String string;
        int bluetoothState = this.mLocalAdapter.getBluetoothState();
        boolean z2 = this.mIsUserInteraction;
        StringBuilder m =
                Utils$$ExternalSyntheticOutline0.m(
                        "onSwitchChanged :: isChecked = ",
                        z,
                        ", isUserInteraction = ",
                        z2,
                        ", mPolicyEnabled = ");
        m.append(this.mPolicyEnabled);
        m.append(", bluetoothState = ");
        m.append(bluetoothState);
        Log.d("BluetoothEnabler", m.toString());
        if (!z2 && ((z && bluetoothState == 10) || (!z && bluetoothState == 12))) {
            return false;
        }
        RestrictedLockUtils.EnforcedAdmin maybeEnforceRestrictions = maybeEnforceRestrictions();
        if (maybeEnforceRestrictions != null && z2) {
            Log.e("BluetoothEnabler", "onSwitchStateChange :: enabled enforce restriction");
            RestrictionUtils restrictionUtils = this.mRestrictionUtils;
            Context context = this.mContext;
            restrictionUtils.getClass();
            RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                    context, maybeEnforceRestrictions);
            return false;
        }
        int semGetAllowBluetoothMode =
                ((DevicePolicyManager) this.mContext.getSystemService("device_policy"))
                        .semGetAllowBluetoothMode(null);
        BluetoothSwitchType bluetoothSwitchType = this.mSwitchType;
        if (semGetAllowBluetoothMode == 0) {
            Toast.makeText(this.mContext, R.string.bluetooth_security_mode_disable, 1).show();
            if (bluetoothSwitchType != null) {
                bluetoothSwitchType.setChecked(false);
            }
            return false;
        }
        if (!this.mPolicyEnabled) {
            if (bluetoothSwitchType != null) {
                bluetoothSwitchType.setChecked(false);
                bluetoothSwitchType.setEnabled(false);
            }
            return false;
        }
        MetricsLogger.action(this.mContext, 159, z);
        if (z) {
            Context context2 = this.mContext;
            if (WirelessUtils.isAirplaneModeOn(context2)
                    && ((string =
                                            Settings.Global.getString(
                                                    context2.getContentResolver(),
                                                    "airplane_mode_toggleable_radios"))
                                    == null
                            || !string.contains("bluetooth"))) {
                Toast.makeText(this.mContext, R.string.wifi_in_airplane_mode, 0).show();
                if (bluetoothSwitchType != null) {
                    bluetoothSwitchType.setChecked(false);
                }
            }
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x00b8 A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void pause() {
        /*
            r11 = this;
            java.lang.String r0 = "CursorWindowAllocationException"
            java.lang.String r1 = "CursorIndexOutOfBoundsException"
            com.android.settingslib.bluetooth.LocalBluetoothAdapter r2 = r11.mLocalAdapter
            if (r2 != 0) goto L9
            return
        L9:
            com.android.settings.bluetooth.BluetoothEnabler$2 r2 = r11.mReceiver
            java.lang.String r3 = "BluetoothEnabler"
            if (r2 == 0) goto L20
            java.lang.String r4 = "pause :: unregister BroadcastReceiver"
            android.util.Log.d(r3, r4)     // Catch: java.lang.IllegalArgumentException -> L1a
            android.content.Context r4 = r11.mContext     // Catch: java.lang.IllegalArgumentException -> L1a
            r4.unregisterReceiver(r2)     // Catch: java.lang.IllegalArgumentException -> L1a
            goto L25
        L1a:
            java.lang.String r2 = "no BroadcastReceiver to be unregistered"
            android.util.Log.e(r3, r2)
            goto L25
        L20:
            java.lang.String r2 = "pause :: mReceiver is null"
            android.util.Log.d(r3, r2)
        L25:
            com.android.settingslib.bluetooth.LocalBluetoothManager r2 = r11.mLocalManager
            com.android.settingslib.bluetooth.BluetoothEventManager r2 = r2.mEventManager
            r2.unregisterCallback(r11)
            com.android.settingslib.bluetooth.LocalBluetoothManager r2 = r11.mLocalManager
            com.android.settingslib.bluetooth.BluetoothEventManager r2 = r2.mEventManager
            r2.unregisterSemCallback(r11)
            android.content.Context r2 = r11.mContext
            android.content.ContentResolver r2 = r2.getContentResolver()
            com.android.settings.bluetooth.BluetoothEnabler$3 r4 = r11.mContentObserver
            r2.unregisterContentObserver(r4)
            com.android.settings.bluetooth.BluetoothEnabler$BluetoothSwitchType r2 = r11.mSwitchType
            if (r2 == 0) goto L58
            java.lang.Object r4 = r2.mSwitchObject
            boolean r5 = r4 instanceof androidx.preference.SwitchPreferenceCompat
            if (r5 == 0) goto L4f
            androidx.preference.SwitchPreferenceCompat r4 = (androidx.preference.SwitchPreferenceCompat) r4
            r5 = 0
            r4.setOnPreferenceChangeListener(r5)
            goto L58
        L4f:
            boolean r5 = r4 instanceof com.android.settings.widget.SettingsMainSwitchBar
            if (r5 == 0) goto L58
            com.android.settings.widget.SettingsMainSwitchBar r4 = (com.android.settings.widget.SettingsMainSwitchBar) r4
            r4.removeOnSwitchChangeListener(r2)
        L58:
            java.lang.String r4 = "content://com.sec.knox.provider/BluetoothPolicy"
            android.net.Uri r6 = android.net.Uri.parse(r4)
            android.content.Context r4 = r11.mContext
            android.content.ContentResolver r5 = r4.getContentResolver()
            r7 = 0
            java.lang.String r8 = "isBluetoothEnabled"
            r9 = 0
            r10 = 0
            android.database.Cursor r4 = r5.query(r6, r7, r8, r9, r10)
            if (r4 == 0) goto Lb3
            r4.moveToFirst()     // Catch: java.lang.Throwable -> L86 android.database.CursorIndexOutOfBoundsException -> L88 android.database.CursorWindowAllocationException -> L8a
            java.lang.String r5 = "isBluetoothEnabled"
            int r5 = r4.getColumnIndex(r5)     // Catch: java.lang.Throwable -> L86 android.database.CursorIndexOutOfBoundsException -> L88 android.database.CursorWindowAllocationException -> L8a
            java.lang.String r5 = r4.getString(r5)     // Catch: java.lang.Throwable -> L86 android.database.CursorIndexOutOfBoundsException -> L88 android.database.CursorWindowAllocationException -> L8a
            java.lang.String r6 = "true"
            boolean r0 = r5.equals(r6)     // Catch: java.lang.Throwable -> L86 android.database.CursorIndexOutOfBoundsException -> L88 android.database.CursorWindowAllocationException -> L8a
            r4.close()
            goto Lb4
        L86:
            r11 = move-exception
            goto Laf
        L88:
            r0 = move-exception
            goto L8c
        L8a:
            r1 = move-exception
            goto L9f
        L8c:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L86
            r5.<init>(r1)     // Catch: java.lang.Throwable -> L86
            r5.append(r0)     // Catch: java.lang.Throwable -> L86
            java.lang.String r0 = r5.toString()     // Catch: java.lang.Throwable -> L86
            android.util.Log.e(r3, r0)     // Catch: java.lang.Throwable -> L86
        L9b:
            r4.close()
            goto Lb3
        L9f:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L86
            r5.<init>(r0)     // Catch: java.lang.Throwable -> L86
            r5.append(r1)     // Catch: java.lang.Throwable -> L86
            java.lang.String r0 = r5.toString()     // Catch: java.lang.Throwable -> L86
            android.util.Log.e(r3, r0)     // Catch: java.lang.Throwable -> L86
            goto L9b
        Laf:
            r4.close()
            throw r11
        Lb3:
            r0 = 1
        Lb4:
            com.android.settingslib.bluetooth.LocalBluetoothAdapter r11 = r11.mLocalAdapter
            if (r11 == 0) goto Lba
            if (r0 != 0) goto Lc3
        Lba:
            if (r2 == 0) goto Lc3
            r11 = 0
            r2.setChecked(r11)
            r2.setEnabled(r11)
        Lc3:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.bluetooth.BluetoothEnabler.pause():void");
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00f4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void resume(android.content.Context r12) {
        /*
            Method dump skipped, instructions count: 289
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.bluetooth.BluetoothEnabler.resume(android.content.Context):void");
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settings.bluetooth.BluetoothEnabler$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settings.bluetooth.BluetoothEnabler$2] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.settings.bluetooth.BluetoothEnabler$3] */
    public BluetoothEnabler(
            SettingsActivity settingsActivity, SettingsMainSwitchBar settingsMainSwitchBar) {
        this.mSwitchType = init(settingsActivity, settingsMainSwitchBar);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settings.bluetooth.BluetoothEnabler$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settings.bluetooth.BluetoothEnabler$2] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.settings.bluetooth.BluetoothEnabler$3] */
    public BluetoothEnabler(Context context, SecSwitchPreferenceScreen secSwitchPreferenceScreen) {
        this.mSwitchType = init(context, secSwitchPreferenceScreen);
        LocalBluetoothAdapter localBluetoothAdapter = this.mLocalAdapter;
        if (localBluetoothAdapter != null) {
            handleStateChanged(localBluetoothAdapter.mAdapter.getState());
        }
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onAudioModeChanged() {}

    @Override // com.samsung.android.settingslib.bluetooth.SemBluetoothCallback
    public final void onResourceUpdated() {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceAdded(CachedBluetoothDevice cachedBluetoothDevice) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceDeleted(CachedBluetoothDevice cachedBluetoothDevice) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onScanningStateChanged(boolean z) {}

    @Override // com.samsung.android.settingslib.bluetooth.SemBluetoothCallback
    public final void onSyncDeviceAdded(CachedBluetoothDevice cachedBluetoothDevice) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onActiveDeviceChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onConnectionStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceBondStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i) {}
}
