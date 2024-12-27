package com.samsung.android.settings.bluetooth.controller;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;

import com.android.settings.R;
import com.android.settings.applications.manageapplications.CloneBackend$$ExternalSyntheticOutline0;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.BluetoothUtils$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.core.lifecycle.Lifecycle;

import com.samsung.android.bluetooth.SmepTag;
import com.samsung.android.knox.custom.CustomDeviceManager;
import com.samsung.android.settings.bluetooth.BluetoothMultiButtonPreference;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settingslib.bluetooth.smep.BluetoothSmepReceiver;
import com.samsung.android.settingslib.bluetooth.smep.SppByteUtil;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecBluetoothDetailNoiseController extends BasePreferenceController
        implements LifecycleObserver,
                Preference.OnPreferenceChangeListener,
                BluetoothSmepReceiver.SmepCallBack {
    private static final String KEY = "bluetooth_noise_controls";
    private static final String KEY_SEC_BLUETOOTH_MULTI_MODE = "bluetooth_multi_mode";
    private static final String KEY_SEC_BLUETOOTH_SWITCH_MODE = "bluetooth_switch_noice_canceling";
    private static final byte[] OFF = {0};
    private static final byte[] ON = {1};
    private static final String TAG = "SecBluetoothDetailNoiseController";
    private static final int WEARING_STATE_BOTH = 2;
    private static final int WEARING_STATE_OFF = 0;
    private static final int WEARING_STATE_ONE = 1;
    private boolean IS_SUPPORT_ADAPTIVE;
    private boolean IS_SUPPORT_AMBIENT;
    private boolean IS_SUPPORT_ANC;
    private RelativeLayout mBottomLayout;
    private CachedBluetoothDevice mCachedDevice;
    private BluetoothDevice mDevice;
    private BluetoothMultiButtonPreference mMultiBtn;
    private PreferenceScreen mPreferenceScreen;
    private String mScreenId;
    private BluetoothSmepReceiver mSmepReceiver;
    private SwitchPreferenceCompat mSwitchPref;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.bluetooth.controller.SecBluetoothDetailNoiseController$1, reason: invalid class name */
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
        }
    }

    public SecBluetoothDetailNoiseController(
            Context context,
            Fragment fragment,
            CachedBluetoothDevice cachedBluetoothDevice,
            BluetoothSmepReceiver bluetoothSmepReceiver,
            Lifecycle lifecycle) {
        super(context, KEY);
        this.IS_SUPPORT_ANC = false;
        this.IS_SUPPORT_AMBIENT = false;
        this.IS_SUPPORT_ADAPTIVE = false;
        this.mCachedDevice = cachedBluetoothDevice;
        this.mDevice = cachedBluetoothDevice.mDevice;
        this.mSmepReceiver = bluetoothSmepReceiver;
        this.mScreenId =
                fragment.getActivity()
                        .getResources()
                        .getString(R.string.screen_device_profiles_setting);
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }

    private int getWearingState() {
        try {
            byte b =
                    this.mDevice
                            .semGetMetadata(
                                    SppByteUtil.intToBytes(SmepTag.STATE_WEARING_L.getTag()))[3];
            byte b2 =
                    this.mDevice
                            .semGetMetadata(
                                    SppByteUtil.intToBytes(SmepTag.STATE_WEARING_R.getTag()))[3];
            if (b == 1 && b2 == 1) {
                return 2;
            }
            return (b == 1 || b2 == 1) ? 1 : 0;
        } catch (Exception e) {
            CloneBackend$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("getWearingState :: "), TAG);
            return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$setNoiseControlView$0(
            BluetoothMultiButtonPreference bluetoothMultiButtonPreference, int i) {
        return setMultiState(i);
    }

    private void parseSupportedFeatures(byte[] bArr) {
        if (bArr == null || bArr.length < 5) {
            Log.e(TAG, "parseSupportedFeatures :: DataPacket is too short.");
            return;
        }
        if ((((bArr[0] & 255) | ((bArr[1] & 255) << 8)) & CustomDeviceManager.QUICK_PANEL_ALL)
                == SmepTag.SUPPORTED_FEATURES.getTag()) {
            int i = 2;
            while (i < bArr.length) {
                int i2 =
                        ((bArr[i] & 255) | ((bArr[i + 1] & 255) << 8))
                                & CustomDeviceManager.QUICK_PANEL_ALL;
                int i3 = bArr[i + 2] & 255;
                byte[] bArr2 = new byte[i3];
                System.arraycopy(bArr, i + 3, bArr2, 0, i3);
                Log.e(
                        TAG,
                        "parseSupportedFeatures :: "
                                + SmepTag.getConstantName(i2)
                                + " = "
                                + Arrays.toString(bArr2));
                i += i3 + 3;
                switch (AnonymousClass1.$SwitchMap$com$samsung$android$bluetooth$SmepTag[
                        SmepTag.getSmepKey(i2).ordinal()]) {
                    case 1:
                        if (bArr2[0] != 1) {
                            this.IS_SUPPORT_ANC = false;
                            break;
                        } else {
                            this.IS_SUPPORT_ANC = true;
                            break;
                        }
                    case 2:
                        if (bArr2[0] != 1) {
                            this.IS_SUPPORT_AMBIENT = false;
                            break;
                        } else {
                            this.IS_SUPPORT_AMBIENT = true;
                            break;
                        }
                    case 3:
                        if (bArr2[0] != 1) {
                            this.IS_SUPPORT_ADAPTIVE = false;
                            break;
                        } else {
                            this.IS_SUPPORT_ADAPTIVE = true;
                            break;
                        }
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                    case 15:
                    case 16:
                    case 17:
                    case 18:
                    case 19:
                        break;
                    default:
                        Log.e(
                                TAG,
                                "parseSupportedFeatures > NOT SUPPORTED FEATURE : "
                                        .concat(String.format("%x", Integer.valueOf(i2))));
                        break;
                }
            }
        }
    }

    private boolean setMultiState(int i) {
        BluetoothDevice bluetoothDevice = this.mDevice;
        SmepTag smepTag = SmepTag.STATE_ANC;
        byte[] m = BluetoothUtils$$ExternalSyntheticOutline0.m(smepTag, bluetoothDevice);
        BluetoothDevice bluetoothDevice2 = this.mDevice;
        SmepTag smepTag2 = SmepTag.STATE_AMBIENT;
        byte[] m2 = BluetoothUtils$$ExternalSyntheticOutline0.m(smepTag2, bluetoothDevice2);
        BluetoothDevice bluetoothDevice3 = this.mDevice;
        SmepTag smepTag3 = SmepTag.STATE_RESPONSIVE_HEARING;
        byte[] m3 = BluetoothUtils$$ExternalSyntheticOutline0.m(smepTag3, bluetoothDevice3);
        boolean z = m != null && m.length > 3 && m[3] == 1;
        boolean z2 = m2 != null && m2.length > 3 && m2[3] == 1;
        boolean z3 = m3 != null && m3.length > 3 && m3[3] == 1;
        if (i == 0) {
            if (this.IS_SUPPORT_ADAPTIVE) {
                if (z) {
                    this.mDevice.semSetMetadata(SppByteUtil.makeTlv(smepTag.getTag(), OFF));
                } else if (z2) {
                    this.mDevice.semSetMetadata(SppByteUtil.makeTlv(smepTag2.getTag(), OFF));
                } else if (z3) {
                    this.mDevice.semSetMetadata(SppByteUtil.makeTlv(smepTag3.getTag(), OFF));
                }
            } else if (!z) {
                this.mDevice.semSetMetadata(SppByteUtil.makeTlv(smepTag.getTag(), ON));
            }
        } else if (i == 1) {
            if (this.IS_SUPPORT_ADAPTIVE) {
                if (!z2) {
                    this.mDevice.semSetMetadata(SppByteUtil.makeTlv(smepTag2.getTag(), ON));
                }
            } else if (z) {
                this.mDevice.semSetMetadata(SppByteUtil.makeTlv(smepTag.getTag(), OFF));
            } else if (z2) {
                this.mDevice.semSetMetadata(SppByteUtil.makeTlv(smepTag2.getTag(), OFF));
            } else if (z3) {
                this.mDevice.semSetMetadata(SppByteUtil.makeTlv(smepTag3.getTag(), OFF));
            }
        } else if (i == 2) {
            if (this.IS_SUPPORT_ADAPTIVE) {
                if (!z3) {
                    this.mDevice.semSetMetadata(SppByteUtil.makeTlv(smepTag3.getTag(), ON));
                }
            } else if (!z2) {
                this.mDevice.semSetMetadata(SppByteUtil.makeTlv(smepTag2.getTag(), ON));
            }
        } else {
            if (i != 3) {
                return false;
            }
            if (!this.IS_SUPPORT_ADAPTIVE) {
                Log.e(
                        TAG,
                        "[setMultiState] Click on the fourth item that occurred in the buds3pro"
                            + " previous model.");
            } else if (!z) {
                this.mDevice.semSetMetadata(SppByteUtil.makeTlv(smepTag.getTag(), ON));
            }
        }
        SALogging.insertSALog(
                this.mScreenId,
                this.mContext
                        .getResources()
                        .getString(R.string.event_device_profiles_setting_noise_control_switch),
                i == 0
                        ? "1"
                        : i == 2
                                ? "2"
                                : i == 3
                                        ? DATA.DM_FIELD_INDEX.PUBLIC_USER_ID
                                        : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
        return true;
    }

    private void setNoiseControlView() {
        boolean z = this.IS_SUPPORT_AMBIENT;
        if (z && this.IS_SUPPORT_ANC) {
            this.mMultiBtn.setVisible(true);
            this.mSwitchPref.setVisible(false);
            if (this.IS_SUPPORT_ADAPTIVE) {
                BluetoothMultiButtonPreference bluetoothMultiButtonPreference = this.mMultiBtn;
                bluetoothMultiButtonPreference.mLow4Icon =
                        R.drawable.sec_bluetooth_ic_buds3_anc_off_unselelcted;
                bluetoothMultiButtonPreference.mMid4Icon =
                        R.drawable.sec_bluetooth_ic_buds3_ambient_sound_unselelcted;
                bluetoothMultiButtonPreference.mHigh4Icon =
                        R.drawable.sec_bluetooth_ic_buds3_adaptive_unselelcted;
                bluetoothMultiButtonPreference.mDoubleHigh4Icon =
                        R.drawable.sec_bluetooth_ic_buds3_anc_off_unselelcted;
                String string = this.mContext.getString(R.string.sec_bluetooth_multi_mode_off);
                String string2 =
                        this.mContext.getString(R.string.sec_bluetooth_multi_mode_ambient_sound);
                String string3 = this.mContext.getString(R.string.sec_bluetooth_adaptive);
                String string4 =
                        this.mContext.getString(R.string.sec_bluetooth_multi_mode_noise_cancelling);
                bluetoothMultiButtonPreference.mLow4Text = string;
                bluetoothMultiButtonPreference.mMid4Text = string2;
                bluetoothMultiButtonPreference.mHigh4Text = string3;
                bluetoothMultiButtonPreference.mDoubleHigh4Text = string4;
                this.mMultiBtn.mDoubleHighVisible = 0;
            } else {
                BluetoothMultiButtonPreference bluetoothMultiButtonPreference2 = this.mMultiBtn;
                bluetoothMultiButtonPreference2.mLow4Icon =
                        R.drawable.sec_bluetooth_ic_buds3_anc_off_unselelcted;
                bluetoothMultiButtonPreference2.mMid4Icon =
                        R.drawable.sec_bluetooth_ic_buds3_anc_off_unselelcted;
                bluetoothMultiButtonPreference2.mHigh4Icon =
                        R.drawable.sec_bluetooth_ic_buds3_ambient_sound_unselelcted;
                bluetoothMultiButtonPreference2.mDoubleHigh4Icon =
                        R.drawable.sec_bluetooth_ic_buds3_ambient_sound_unselelcted;
                String string5 =
                        this.mContext.getString(R.string.sec_bluetooth_multi_mode_noise_cancelling);
                String string6 = this.mContext.getString(R.string.sec_bluetooth_multi_mode_off);
                String string7 =
                        this.mContext.getString(R.string.sec_bluetooth_multi_mode_ambient_sound);
                String string8 = this.mContext.getString(R.string.sec_bluetooth_adaptive);
                bluetoothMultiButtonPreference2.mLow4Text = string5;
                bluetoothMultiButtonPreference2.mMid4Text = string6;
                bluetoothMultiButtonPreference2.mHigh4Text = string7;
                bluetoothMultiButtonPreference2.mDoubleHigh4Text = string8;
            }
            this.mMultiBtn.mListener =
                    new SecBluetoothDetailNoiseController$$ExternalSyntheticLambda0(this);
        } else if (z || this.IS_SUPPORT_ANC) {
            this.mMultiBtn.setVisible(false);
            this.mSwitchPref.setVisible(true);
            Drawable drawable =
                    this.mContext.getDrawable(R.drawable.sec_bluetooth_2d_active_noise_canceling);
            if (drawable != null) {
                drawable.setTint(
                        this.mContext.getResources().getColor(R.color.bt_device_icon_tint_color));
                this.mSwitchPref.setIcon(drawable);
            }
            this.mSwitchPref.setTitle(
                    this.IS_SUPPORT_AMBIENT
                            ? this.mContext.getString(
                                    R.string.sec_bluetooth_multi_mode_ambient_sound)
                            : this.mContext.getString(
                                    R.string.sec_bluetooth_multi_mode_noise_cancelling));
            this.mSwitchPref.setOnPreferenceChangeListener(this);
        } else {
            this.mMultiBtn.setVisible(false);
            this.mSwitchPref.setVisible(false);
        }
        PreferenceCategory preferenceCategory =
                (PreferenceCategory) this.mPreferenceScreen.findPreference(getPreferenceKey());
        if (this.IS_SUPPORT_AMBIENT || this.IS_SUPPORT_ANC || preferenceCategory == null) {
            return;
        }
        this.mPreferenceScreen.removePreference(preferenceCategory);
    }

    private void updateMultiState() {
        byte[] m = BluetoothUtils$$ExternalSyntheticOutline0.m(SmepTag.STATE_ANC, this.mDevice);
        byte[] m2 =
                BluetoothUtils$$ExternalSyntheticOutline0.m(SmepTag.STATE_AMBIENT, this.mDevice);
        byte[] m3 =
                BluetoothUtils$$ExternalSyntheticOutline0.m(
                        SmepTag.STATE_RESPONSIVE_HEARING, this.mDevice);
        if (m2 == null || m2.length == 0) {
            Log.d(TAG, "ambientData = null");
        } else {
            Log.d(TAG, "ambientData = " + Arrays.toString(m2));
        }
        if (m == null || m.length == 0) {
            Log.d(TAG, "ancData = null");
        } else {
            Log.d(TAG, "ancData = " + Arrays.toString(m));
        }
        if (m3 == null || m3.length == 0) {
            Log.d(TAG, "adaptiveData = null");
        } else {
            Log.d(TAG, "adaptiveData = " + Arrays.toString(m3));
        }
        if (this.IS_SUPPORT_ADAPTIVE) {
            if (m != null && m.length > 3 && m[3] == 1) {
                this.mMultiBtn.setSelectedPosition(3);
                return;
            }
            if (m2 != null && m2.length > 3 && m2[3] == 1) {
                this.mMultiBtn.setSelectedPosition(1);
                return;
            } else if (m3 == null || m3.length <= 3 || m3[3] != 1) {
                this.mMultiBtn.setSelectedPosition(0);
                return;
            } else {
                this.mMultiBtn.setSelectedPosition(2);
                return;
            }
        }
        if (m != null && m.length > 3 && m[3] == 1) {
            this.mMultiBtn.setSelectedPosition(0);
            return;
        }
        if (m2 != null && m2.length > 3 && m2[3] == 1) {
            this.mMultiBtn.setSelectedPosition(2);
        } else if (m3 == null || m3.length <= 3 || m3[3] != 1) {
            this.mMultiBtn.setSelectedPosition(1);
        } else {
            this.mMultiBtn.setSelectedPosition(3);
        }
    }

    private void updateSwitchState() {
        int tag = (this.IS_SUPPORT_AMBIENT ? SmepTag.STATE_AMBIENT : SmepTag.STATE_ANC).getTag();
        byte[] semGetMetadata = this.mDevice.semGetMetadata(SppByteUtil.intToBytes(tag));
        if (semGetMetadata == null || semGetMetadata.length == 0) {
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(tag, "data = null ", TAG);
            return;
        }
        if (semGetMetadata.length <= 3) {
            Log.d(TAG, "data = " + Arrays.toString(semGetMetadata));
        } else {
            this.mSwitchPref.setChecked(semGetMetadata[3] == 1);
            Log.d(TAG, "data = " + Arrays.toString(semGetMetadata));
        }
    }

    @Override // com.samsung.android.settingslib.bluetooth.smep.BluetoothSmepReceiver.SmepCallBack
    public void connectionStateChange(BluetoothDevice bluetoothDevice, int i) {
        if (this.mDevice.equals(bluetoothDevice)) {
            if (i == 2 || i == 0) {
                boolean z = i == 2;
                BluetoothMultiButtonPreference bluetoothMultiButtonPreference = this.mMultiBtn;
                if (bluetoothMultiButtonPreference != null
                        && bluetoothMultiButtonPreference.isVisible()) {
                    setEnabled(this.mMultiBtn, z);
                    return;
                }
                SwitchPreferenceCompat switchPreferenceCompat = this.mSwitchPref;
                if (switchPreferenceCompat == null || !switchPreferenceCompat.isVisible()) {
                    return;
                }
                setEnabled(this.mSwitchPref, z);
            }
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreferenceScreen = preferenceScreen;
        this.mMultiBtn =
                (BluetoothMultiButtonPreference)
                        preferenceScreen.findPreference(KEY_SEC_BLUETOOTH_MULTI_MODE);
        this.mSwitchPref =
                (SwitchPreferenceCompat)
                        preferenceScreen.findPreference(KEY_SEC_BLUETOOTH_SWITCH_MODE);
        parseSupportedFeatures(
                BluetoothUtils$$ExternalSyntheticOutline0.m(
                        SmepTag.SUPPORTED_FEATURES, this.mDevice));
        setNoiseControlView();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return BluetoothUtils.isSupportSmep(this.mDevice) ? 0 : 3;
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
        return KEY;
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

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        if (getAvailabilityStatus() == 3) {
            return;
        }
        this.mSmepReceiver.unRegisterCallback(this);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        Log.d(TAG, "onPreferenceChange :: ");
        if (!(preference instanceof SwitchPreferenceCompat)) {
            Log.d(TAG, "key = " + preference.getKey());
            return true;
        }
        preference.getClass();
        boolean isChecked = ((SwitchPreferenceCompat) preference).isChecked();
        this.mDevice.semSetMetadata(
                SppByteUtil.makeTlv(
                        (this.IS_SUPPORT_AMBIENT ? SmepTag.STATE_AMBIENT : SmepTag.STATE_ANC)
                                .getTag(),
                        isChecked ? OFF : ON));
        SALogging.insertSALog(
                this.mScreenId,
                this.mContext
                        .getResources()
                        .getString(R.string.event_device_profiles_setting_noise_control_switch),
                isChecked ? DATA.DM_FIELD_INDEX.PCSCF_DOMAIN : this.IS_SUPPORT_AMBIENT ? "2" : "1");
        return true;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        if (getAvailabilityStatus() == 3) {
            this.mPreferenceScreen.setVisible(false);
            return;
        }
        if (!this.mPreferenceScreen.isVisible()) {
            this.mPreferenceScreen.setVisible(true);
            parseSupportedFeatures(
                    BluetoothUtils$$ExternalSyntheticOutline0.m(
                            SmepTag.SUPPORTED_FEATURES, this.mDevice));
            setNoiseControlView();
        }
        this.mSmepReceiver.registerCallback(this);
        boolean isConnected = this.mCachedDevice.isConnected();
        BluetoothMultiButtonPreference bluetoothMultiButtonPreference = this.mMultiBtn;
        if (bluetoothMultiButtonPreference != null && bluetoothMultiButtonPreference.isVisible()) {
            setEnabled(this.mMultiBtn, isConnected);
            updateMultiState();
            return;
        }
        SwitchPreferenceCompat switchPreferenceCompat = this.mSwitchPref;
        if (switchPreferenceCompat == null || !switchPreferenceCompat.isVisible()) {
            return;
        }
        setEnabled(this.mSwitchPref, isConnected);
        updateSwitchState();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setBottomLayout(RelativeLayout relativeLayout) {
        this.mMultiBtn.mBottomLayout = relativeLayout;
    }

    public void setDelayEnabled() {
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        if (cachedBluetoothDevice != null) {
            boolean isConnected = cachedBluetoothDevice.isConnected();
            BluetoothMultiButtonPreference bluetoothMultiButtonPreference = this.mMultiBtn;
            if (bluetoothMultiButtonPreference != null
                    && bluetoothMultiButtonPreference.isVisible()) {
                setEnabled(this.mMultiBtn, isConnected);
                return;
            }
            SwitchPreferenceCompat switchPreferenceCompat = this.mSwitchPref;
            if (switchPreferenceCompat == null || !switchPreferenceCompat.isVisible()) {
                return;
            }
            setEnabled(this.mSwitchPref, isConnected);
        }
    }

    public void setEnabled(boolean z) {
        SwitchPreferenceCompat switchPreferenceCompat;
        BluetoothMultiButtonPreference bluetoothMultiButtonPreference = this.mMultiBtn;
        if (bluetoothMultiButtonPreference != null
                && !bluetoothMultiButtonPreference.isVisible()
                && (switchPreferenceCompat = this.mSwitchPref) != null
                && !switchPreferenceCompat.isVisible()
                && isAvailable()) {
            parseSupportedFeatures(
                    BluetoothUtils$$ExternalSyntheticOutline0.m(
                            SmepTag.SUPPORTED_FEATURES, this.mDevice));
            setNoiseControlView();
            this.mSmepReceiver.registerCallback(this);
        }
        boolean isConnected = this.mCachedDevice.isConnected();
        BluetoothMultiButtonPreference bluetoothMultiButtonPreference2 = this.mMultiBtn;
        if (bluetoothMultiButtonPreference2 != null
                && bluetoothMultiButtonPreference2.isVisible()) {
            setEnabled(this.mMultiBtn, isConnected);
            updateMultiState();
            return;
        }
        SwitchPreferenceCompat switchPreferenceCompat2 = this.mSwitchPref;
        if (switchPreferenceCompat2 == null || !switchPreferenceCompat2.isVisible()) {
            return;
        }
        setEnabled(this.mSwitchPref, isConnected);
        updateSwitchState();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public void updateLayout() {
        if (this.mMultiBtn.isVisible()) {
            BluetoothMultiButtonPreference bluetoothMultiButtonPreference = this.mMultiBtn;
            bluetoothMultiButtonPreference.getClass();
            BluetoothMultiButtonPreference.AnonymousClass1 anonymousClass1 =
                    new BluetoothMultiButtonPreference.AnonymousClass1(
                            bluetoothMultiButtonPreference);
            if (BluetoothMultiButtonPreference.updateMidBarSizeFlag) {
                return;
            }
            View view = bluetoothMultiButtonPreference.mCustomView;
            if (view != null) {
                view.requestLayout();
            }
            anonymousClass1.start();
        }
    }

    @Override // com.samsung.android.settingslib.bluetooth.smep.BluetoothSmepReceiver.SmepCallBack
    public void updateNoiseControlState(BluetoothDevice bluetoothDevice, Map<Integer, byte[]> map) {
        if (this.mDevice.equals(bluetoothDevice)) {
            BluetoothMultiButtonPreference bluetoothMultiButtonPreference = this.mMultiBtn;
            if (bluetoothMultiButtonPreference != null
                    && bluetoothMultiButtonPreference.isVisible()) {
                updateMultiState();
                return;
            }
            SwitchPreferenceCompat switchPreferenceCompat = this.mSwitchPref;
            if (switchPreferenceCompat == null || !switchPreferenceCompat.isVisible()) {
                return;
            }
            updateSwitchState();
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        Log.d(TAG, "updateState");
        super.updateState(preference);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    private void setEnabled(Preference preference, boolean z) {
        if (preference instanceof SwitchPreferenceCompat) {
            this.mSwitchPref.setEnabled(z);
            return;
        }
        this.mMultiBtn.setEnabled(z);
        this.mMultiBtn.setButtonEnabled(0, z);
        this.mMultiBtn.setButtonEnabled(1, z);
        this.mMultiBtn.setButtonEnabled(2, z);
        if (this.IS_SUPPORT_ADAPTIVE) {
            this.mMultiBtn.setButtonEnabled(3, z);
        }
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}

    @Override // com.samsung.android.settingslib.bluetooth.smep.BluetoothSmepReceiver.SmepCallBack
    public void updateBatteryState(BluetoothDevice bluetoothDevice, Map<Integer, byte[]> map) {}

    @Override // com.samsung.android.settingslib.bluetooth.smep.BluetoothSmepReceiver.SmepCallBack
    public void updateTouchPadState(BluetoothDevice bluetoothDevice, Map<Integer, byte[]> map) {}
}
