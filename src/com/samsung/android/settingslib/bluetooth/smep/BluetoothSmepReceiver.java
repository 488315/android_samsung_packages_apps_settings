package com.samsung.android.settingslib.bluetooth.smep;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.settings.accounts.ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0;

import com.samsung.android.bluetooth.SmepTag;
import com.samsung.android.knox.custom.CustomDeviceManager;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class BluetoothSmepReceiver extends BroadcastReceiver {
    public final Context mContext;
    public boolean mRegistered;
    public final Collection mSmepCallbacks = new CopyOnWriteArrayList();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settingslib.bluetooth.smep.BluetoothSmepReceiver$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$samsung$android$bluetooth$SmepTag;

        static {
            int[] iArr = new int[SmepTag.values().length];
            $SwitchMap$com$samsung$android$bluetooth$SmepTag = iArr;
            try {
                iArr[SmepTag.FEATURE_ANC_LEVEL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.FEATURE_AMBIENT_LEVEL.ordinal()] =
                        2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.FEATURE_RESPONSIVE_HEARING.ordinal()] =
                        3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.FEATURE_BATTERY.ordinal()] =
                        4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[SmepTag.FEATURE_EQ.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.FEATURE_TOUCH_LOCK.ordinal()] =
                        6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.FEATURE_SWITCH_AUDIO_PATH_BY_WEARING_STATE.ordinal()] =
                        7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.FEATURE_ALWAYS_ON_MIC.ordinal()] =
                        8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.FEATURE_SEAMLESS_EARBUD_CONNECTION.ordinal()] =
                        9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.FEATURE_FIND_MY_BUDS.ordinal()] =
                        10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.FEATURE_BINARY_UPDATE.ordinal()] =
                        11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.FEATURE_AUTO_SWITCH.ordinal()] =
                        12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.FEATURE_SPATIAL_AUDIO.ordinal()] =
                        13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.FEATURE_REAL_SOUND_RECORDING.ordinal()] =
                        14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.FEATURE_TOUCH_LONG_PRESS_HOTKEY_VOICE_ASSISTANT.ordinal()] =
                        15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.FEATURE_TOUCH_LONG_PRESS_HOTKEY_ANC.ordinal()] =
                        16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.FEATURE_TOUCH_LONG_PRESS_HOTKEY_AMBIENT.ordinal()] =
                        17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.FEATURE_TOUCH_LONG_PRESS_HOTKEY_VOLUME_CTRL.ordinal()] =
                        18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.FEATURE_USE_AMBIENT_DURING_CALL.ordinal()] =
                        19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.STATE_BATTERY_L.ordinal()] =
                        20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.STATE_BATTERY_R.ordinal()] =
                        21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.STATE_BATTERY_CRADLE.ordinal()] =
                        22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.STATE_WEARING_L.ordinal()] =
                        23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.STATE_WEARING_R.ordinal()] =
                        24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[SmepTag.STATE_AMBIENT.ordinal()] =
                        25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[SmepTag.STATE_ANC.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.STATE_TOUCH_LOCK.ordinal()] =
                        27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.STATE_TOUCH_LONG_PRESS_HOTKEY_MAPPING_L.ordinal()] =
                        28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.STATE_TOUCH_LONG_PRESS_HOTKEY_MAPPING_R.ordinal()] =
                        29;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[SmepTag.STATE_MODEL.ordinal()] =
                        30;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[SmepTag.STATE_COUPLED.ordinal()] =
                        31;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[SmepTag.STATE_PRIMARY.ordinal()] =
                        32;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.STATE_DEVICE_ID_L.ordinal()] =
                        33;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.STATE_DEVICE_ID_R.ordinal()] =
                        34;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[SmepTag.STATE_EQ.ordinal()] = 35;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.STATE_ALWAYS_ON_MIC.ordinal()] =
                        36;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.STATE_ALWAYS_ON_MIC_WAKEUP_LANGUAGE.ordinal()] =
                        37;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.STATE_SEAMLESS_EARBUD_CONNECTION.ordinal()] =
                        38;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.STATE_GAME_MODE.ordinal()] =
                        39;
            } catch (NoSuchFieldError unused39) {
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface SmepCallBack {
        void connectionStateChange(BluetoothDevice bluetoothDevice, int i);

        void updateBatteryState(BluetoothDevice bluetoothDevice, Map map);

        void updateNoiseControlState(BluetoothDevice bluetoothDevice, Map map);

        void updateTouchPadState(BluetoothDevice bluetoothDevice, Map map);
    }

    public BluetoothSmepReceiver(Context context) {
        this.mContext = context;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null) {
            Log.d("BluetoothSmepReceiver", action);
        }
        if ("com.samsung.bluetooth.device.action.SMEP_CONNECTION_STATE_CHANGED".equals(action)) {
            BluetoothDevice bluetoothDevice =
                    (BluetoothDevice)
                            intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
            int intExtra = intent.getIntExtra("android.bluetooth.profile.extra.STATE", -1);
            byte[] byteArrayExtra =
                    intent.getByteArrayExtra("com.samsung.bluetooth.device.extra.META_DATA");
            if (byteArrayExtra != null) {
                Log.d(
                        "BluetoothSmepReceiver",
                        "data = " + SppByteUtil.byteArrayToHex(byteArrayExtra));
            }
            if (intExtra != -1) {
                Iterator it = ((CopyOnWriteArrayList) this.mSmepCallbacks).iterator();
                while (it.hasNext()) {
                    ((SmepCallBack) it.next()).connectionStateChange(bluetoothDevice, intExtra);
                }
                return;
            }
            return;
        }
        if ("com.samsung.bluetooth.device.action.META_DATA_CHANGED".equals(action)) {
            BluetoothDevice bluetoothDevice2 =
                    (BluetoothDevice)
                            intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
            byte[] byteArrayExtra2 =
                    intent.getByteArrayExtra("com.samsung.bluetooth.device.extra.META_DATA");
            if (byteArrayExtra2 != null) {
                Log.d(
                        "BluetoothSmepReceiver",
                        "data = " + SppByteUtil.byteArrayToHex(byteArrayExtra2));
                if (byteArrayExtra2.length < 4) {
                    Log.e(
                            "BluetoothSmepReceiver",
                            "parseSupportedFeatures :: DataPacket is too short.");
                    return;
                }
                int i = 0;
                int i2 = (byteArrayExtra2[0] & 255) | ((byteArrayExtra2[1] & 255) << 8);
                int i3 = CustomDeviceManager.QUICK_PANEL_ALL;
                if ((i2 & CustomDeviceManager.QUICK_PANEL_ALL)
                        == SmepTag.SUPPORTED_FEATURES.getTag()) {
                    int i4 = 2;
                    while (i4 < byteArrayExtra2.length) {
                        int i5 =
                                ((byteArrayExtra2[i4] & 255)
                                                | ((byteArrayExtra2[i4 + 1] & 255) << 8))
                                        & CustomDeviceManager.QUICK_PANEL_ALL;
                        int i6 = byteArrayExtra2[i4 + 2] & 255;
                        byte[] bArr = new byte[i6];
                        System.arraycopy(byteArrayExtra2, i4 + 3, bArr, 0, i6);
                        Log.e(
                                "BluetoothSmepReceiver",
                                "parseTlvs :: "
                                        + SmepTag.getConstantName(i5)
                                        + " = "
                                        + Arrays.toString(bArr));
                        i4 += i6 + 3;
                        int i7 =
                                AnonymousClass1.$SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                        SmepTag.getSmepKey(i5).ordinal()];
                        Log.e(
                                "BluetoothSmepReceiver",
                                "parseSupportedFeatures :: NOT SUPPORTED FEATURE : "
                                        .concat(String.format("%x", Integer.valueOf(i5))));
                    }
                    return;
                }
                HashMap hashMap = new HashMap();
                HashMap hashMap2 = new HashMap();
                HashMap hashMap3 = new HashMap();
                int i8 = 0;
                while (i8 < byteArrayExtra2.length) {
                    int i9 =
                            ((byteArrayExtra2[i8] & 255) | ((byteArrayExtra2[i8 + 1] & 255) << 8))
                                    & i3;
                    int i10 = byteArrayExtra2[i8 + 2] & 255;
                    byte[] bArr2 = new byte[i10];
                    System.arraycopy(byteArrayExtra2, i8 + 3, bArr2, i, i10);
                    Log.e(
                            "BluetoothSmepReceiver",
                            "parseTlvs :: "
                                    + SmepTag.getConstantName(i9)
                                    + " = "
                                    + Arrays.toString(bArr2));
                    i8 += i10 + 3;
                    switch (AnonymousClass1.$SwitchMap$com$samsung$android$bluetooth$SmepTag[
                            SmepTag.getSmepKey(i9).ordinal()]) {
                        case 20:
                        case 21:
                        case 22:
                        case 23:
                        case 24:
                            hashMap.put(Integer.valueOf(i9), bArr2);
                            break;
                        case 25:
                        case 26:
                            hashMap2.put(Integer.valueOf(i9), bArr2);
                            break;
                        case 27:
                        case 28:
                        case 29:
                            hashMap3.put(Integer.valueOf(i9), bArr2);
                            break;
                        case 30:
                        case 31:
                        case 32:
                        case 33:
                        case 34:
                        case 35:
                        case 36:
                        case 37:
                        case 38:
                        case 39:
                            break;
                        default:
                            Log.e(
                                    "BluetoothSmepReceiver",
                                    "parseTlvs :: NOT SUPPORTED STATE : "
                                            .concat(String.format("%x", Integer.valueOf(i9))));
                            break;
                    }
                    i = 0;
                    i3 = CustomDeviceManager.QUICK_PANEL_ALL;
                }
                boolean z = hashMap.size() > 0;
                boolean z2 = hashMap2.size() > 0;
                boolean z3 = hashMap3.size() > 0;
                if (z || z2 || z3) {
                    Iterator it2 = ((CopyOnWriteArrayList) this.mSmepCallbacks).iterator();
                    while (it2.hasNext()) {
                        SmepCallBack smepCallBack = (SmepCallBack) it2.next();
                        if (z) {
                            smepCallBack.updateBatteryState(bluetoothDevice2, hashMap);
                        }
                        if (z2) {
                            smepCallBack.updateNoiseControlState(bluetoothDevice2, hashMap2);
                        }
                        if (z3) {
                            smepCallBack.updateTouchPadState(bluetoothDevice2, hashMap3);
                        }
                    }
                }
            }
        }
    }

    public final void registerCallback(SmepCallBack smepCallBack) {
        if (((CopyOnWriteArrayList) this.mSmepCallbacks).contains(smepCallBack)) {
            return;
        }
        ((CopyOnWriteArrayList) this.mSmepCallbacks).add(smepCallBack);
        if (this.mRegistered) {
            return;
        }
        setListening(true);
    }

    public final void setListening(boolean z) {
        if (z && !this.mRegistered) {
            this.mContext.registerReceiver(
                    this,
                    ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0.m(
                            "com.samsung.bluetooth.device.action.SMEP_CONNECTION_STATE_CHANGED",
                            "com.samsung.bluetooth.device.action.META_DATA_CHANGED"));
            this.mRegistered = true;
        } else {
            if (z || !this.mRegistered) {
                return;
            }
            this.mContext.unregisterReceiver(this);
            this.mRegistered = false;
        }
    }

    public final void unRegisterCallback(SmepCallBack smepCallBack) {
        ((CopyOnWriteArrayList) this.mSmepCallbacks).remove(smepCallBack);
        if (((CopyOnWriteArrayList) this.mSmepCallbacks).size() == 0) {
            setListening(false);
        }
    }
}
