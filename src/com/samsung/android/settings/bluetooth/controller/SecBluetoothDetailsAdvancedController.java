package com.samsung.android.settings.bluetooth.controller;

import android.app.ActivityThread;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.BluetoothUtils$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;

import com.samsung.android.bluetooth.SmepTag;
import com.samsung.android.knox.custom.CustomDeviceManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.bluetooth.fragment.BluetoothAboutFragment;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settingslib.bluetooth.ManufacturerData;
import com.samsung.android.settingslib.bluetooth.smep.BluetoothSmepReceiver;
import com.samsung.android.settingslib.bluetooth.smep.SppByteUtil;
import com.sec.ims.configuration.DATA;

import java.util.Arrays;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecBluetoothDetailsAdvancedController extends AbstractPreferenceController
        implements Preference.OnPreferenceChangeListener,
                Preference.OnPreferenceClickListener,
                LifecycleObserver,
                BluetoothSmepReceiver.SmepCallBack {
    public boolean isSupportTouchBlock;
    public boolean isSupportTouchControl;
    public Preference mAboutPreference;
    public PreferenceGroup mAdvancedContainer;
    public SwitchPreferenceCompat mBlockTouchesPreference;
    public final CachedBluetoothDevice mCachedDevice;
    public final BluetoothDevice mDevice;
    public final PreferenceFragmentCompat mFragment;
    public Preference mOpenAppPreference;
    public final String mScreenId;
    public final BluetoothSmepReceiver mSmepReceiver;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.bluetooth.controller.SecBluetoothDetailsAdvancedController$1, reason: invalid class name */
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
                                SmepTag.FEATURE_TOUCH_CONTROL.ordinal()] =
                        7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.FEATURE_SWITCH_AUDIO_PATH_BY_WEARING_STATE.ordinal()] =
                        8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.FEATURE_ALWAYS_ON_MIC.ordinal()] =
                        9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.FEATURE_SEAMLESS_EARBUD_CONNECTION.ordinal()] =
                        10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.FEATURE_FIND_MY_BUDS.ordinal()] =
                        11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.FEATURE_BINARY_UPDATE.ordinal()] =
                        12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.FEATURE_AUTO_SWITCH.ordinal()] =
                        13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.FEATURE_SPATIAL_AUDIO.ordinal()] =
                        14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.FEATURE_REAL_SOUND_RECORDING.ordinal()] =
                        15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.FEATURE_TOUCH_LONG_PRESS_HOTKEY_VOICE_ASSISTANT.ordinal()] =
                        16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.FEATURE_TOUCH_LONG_PRESS_HOTKEY_ANC.ordinal()] =
                        17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.FEATURE_TOUCH_LONG_PRESS_HOTKEY_AMBIENT.ordinal()] =
                        18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.FEATURE_TOUCH_LONG_PRESS_HOTKEY_VOLUME_CTRL.ordinal()] =
                        19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$com$samsung$android$bluetooth$SmepTag[
                                SmepTag.FEATURE_USE_AMBIENT_DURING_CALL.ordinal()] =
                        20;
            } catch (NoSuchFieldError unused20) {
            }
        }
    }

    public SecBluetoothDetailsAdvancedController(
            Context context,
            PreferenceFragmentCompat preferenceFragmentCompat,
            CachedBluetoothDevice cachedBluetoothDevice,
            BluetoothSmepReceiver bluetoothSmepReceiver,
            Lifecycle lifecycle) {
        super(context);
        this.isSupportTouchBlock = false;
        this.isSupportTouchControl = false;
        this.mCachedDevice = cachedBluetoothDevice;
        BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
        this.mDevice = bluetoothDevice;
        this.mFragment = preferenceFragmentCompat;
        bluetoothDevice.semGetManufacturerData();
        this.mSmepReceiver = bluetoothSmepReceiver;
        this.mScreenId =
                SecBluetoothBroadcastAssistantController$$ExternalSyntheticOutline0.m(
                        preferenceFragmentCompat, R.string.screen_device_profiles_setting);
        lifecycle.addObserver(this);
    }

    @Override // com.samsung.android.settingslib.bluetooth.smep.BluetoothSmepReceiver.SmepCallBack
    public final void connectionStateChange(BluetoothDevice bluetoothDevice, int i) {
        if (this.mDevice.equals(bluetoothDevice)) {
            if (i == 2 || i == 0) {
                setEnabled(i == 2);
            }
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        Log.d("SecBluetoothDetailsAdvancedController", "displayPreference :: ");
        this.mAdvancedContainer =
                (PreferenceGroup) preferenceScreen.findPreference("bluetooth_advanced");
        this.mFragment
                .getActivity()
                .getResources()
                .getDrawable(R.drawable.list_ic_true_wireless_earbuds, null);
        this.mAboutPreference = preferenceScreen.findPreference("bluetooth_pref_about_earbuds");
        this.mOpenAppPreference =
                preferenceScreen.findPreference("bluetooth_pref_open_galaxy_wearable");
        this.mBlockTouchesPreference =
                (SwitchPreferenceCompat)
                        preferenceScreen.findPreference("bluetooth_pref_touch_controls");
        if (getDeviceIconIndex() == R.drawable.list_ic_earbuds_stem) {
            setPreference$1(
                    this.mAboutPreference,
                    this.mContext.getDrawable(R.drawable.sec_bluetooth_2d_buds3f_l_line_));
        } else if (getDeviceIconIndex() == R.drawable.list_ic_true_wireless_earbuds) {
            setPreference$1(
                    this.mAboutPreference,
                    this.mContext.getDrawable(R.drawable.sec_bluetooth_2d_buds_left));
        } else {
            setPreference$1(this.mAboutPreference, this.mContext.getDrawable(getDeviceIconIndex()));
        }
        setPreference$1(
                this.mOpenAppPreference,
                this.mContext.getDrawable(R.drawable.sec_bluetooth_2d_open));
        SwitchPreferenceCompat switchPreferenceCompat = this.mBlockTouchesPreference;
        Drawable drawable = this.mContext.getDrawable(R.drawable.sec_bluetooth_2d_block_touch);
        Log.d("SecBluetoothDetailsAdvancedController", "setPreference :: ");
        if (!TextUtils.isEmpty(null)) {
            switchPreferenceCompat.setTitle((CharSequence) null);
        }
        if (drawable != null) {
            drawable.setTint(
                    this.mContext.getResources().getColor(R.color.bt_device_icon_tint_color));
            switchPreferenceCompat.setIcon(drawable);
        }
        switchPreferenceCompat.setOnPreferenceChangeListener(this);
        if (BluetoothUtils.isSupportSmep(this.mDevice)) {
            initFeatureList();
        } else {
            this.mAdvancedContainer.setVisible(false);
        }
        super.displayPreference(preferenceScreen);
    }

    public final int getDeviceIconIndex() {
        ManufacturerData manufacturerData;
        try {
            CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
            if (cachedBluetoothDevice == null
                    || (manufacturerData = cachedBluetoothDevice.mManufacturerData) == null) {
                return R.drawable.ic_bluetooth_earbuds_left;
            }
            int deviceIcon = manufacturerData.getDeviceIcon();
            return deviceIcon > 0 ? deviceIcon : R.drawable.ic_bluetooth_earbuds_left;
        } catch (Exception e) {
            e.printStackTrace();
            return R.drawable.ic_bluetooth_earbuds_left;
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bluetooth_advanced";
    }

    public final void initCurrentStatus() {
        byte[] m =
                BluetoothUtils$$ExternalSyntheticOutline0.m(SmepTag.STATE_TOUCH_LOCK, this.mDevice);
        if (m == null || m.length <= 0) {
            return;
        }
        Log.d(
                "SecBluetoothDetailsAdvancedController",
                "updateTouchData :: STATE_TOUCH_LOCK = " + SppByteUtil.byteArrayToHex(m));
        this.mBlockTouchesPreference.setChecked(m[3] == 1);
    }

    public final void initFeatureList() {
        BluetoothDevice bluetoothDevice = this.mDevice;
        if (bluetoothDevice == null) {
            Log.e("SecBluetoothDetailsAdvancedController", "initFeatureList :: device is null");
            return;
        }
        SmepTag smepTag = SmepTag.SUPPORTED_FEATURES;
        byte[] m = BluetoothUtils$$ExternalSyntheticOutline0.m(smepTag, bluetoothDevice);
        byte[] m2 =
                BluetoothUtils$$ExternalSyntheticOutline0.m(
                        SmepTag.ALL_CURRENT_STATES, this.mDevice);
        if (m == null || m.length == 0) {
            Log.e(
                    "SecBluetoothDetailsAdvancedController",
                    "initFeatureList :: SEM_METADATA_SUPPROT_FEATURE  is null");
            return;
        }
        Log.d(
                "SecBluetoothDetailsAdvancedController",
                "feature data = " + SppByteUtil.byteArrayToHex(m));
        Log.d(
                "SecBluetoothDetailsAdvancedController",
                "current All data = " + SppByteUtil.byteArrayToHex(m2));
        if (m.length < 5) {
            Log.e(
                    "SecBluetoothDetailsAdvancedController",
                    "parseSupportedFeatures :: DataPacket is too short.");
            return;
        }
        if ((((m[0] & 255) | ((m[1] & 255) << 8)) & CustomDeviceManager.QUICK_PANEL_ALL)
                == smepTag.getTag()) {
            int i = 2;
            while (i < m.length) {
                int i2 =
                        ((m[i] & 255) | ((m[i + 1] & 255) << 8))
                                & CustomDeviceManager.QUICK_PANEL_ALL;
                int i3 = m[i + 2] & 255;
                byte[] bArr = new byte[i3];
                System.arraycopy(m, i + 3, bArr, 0, i3);
                Log.e(
                        "SecBluetoothDetailsAdvancedController",
                        "parseSupportedFeatures :: "
                                + SmepTag.getConstantName(i2)
                                + " = "
                                + Arrays.toString(bArr));
                i += i3 + 3;
                switch (AnonymousClass1.$SwitchMap$com$samsung$android$bluetooth$SmepTag[
                        SmepTag.getSmepKey(i2).ordinal()]) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
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
                    case 20:
                        break;
                    case 6:
                    case 7:
                        if (bArr[0] != 0) {
                            if (this.mBlockTouchesPreference != null) {
                                if (i2 != SmepTag.FEATURE_TOUCH_CONTROL.getTag()) {
                                    this.isSupportTouchBlock = true;
                                    this.mBlockTouchesPreference.setVisible(true);
                                    break;
                                } else if (Build.VERSION.SEM_PLATFORM_INT < 120500) {
                                    this.mBlockTouchesPreference.setVisible(false);
                                    break;
                                } else {
                                    String m3 =
                                            SecBluetoothBroadcastAssistantController$$ExternalSyntheticOutline0
                                                    .m(
                                                            this.mFragment,
                                                            R.string.sec_bluetooth_touch_control);
                                    this.isSupportTouchControl = true;
                                    this.mBlockTouchesPreference.setTitle(m3);
                                    this.mBlockTouchesPreference.setVisible(true);
                                    break;
                                }
                            } else {
                                break;
                            }
                        } else {
                            SwitchPreferenceCompat switchPreferenceCompat =
                                    this.mBlockTouchesPreference;
                            if (switchPreferenceCompat != null
                                    && !this.isSupportTouchBlock
                                    && !this.isSupportTouchControl) {
                                switchPreferenceCompat.setVisible(false);
                                break;
                            }
                        }
                        break;
                    default:
                        Log.e(
                                "SecBluetoothDetailsAdvancedController",
                                "process_MSG_ID_READ_PROPERTY > NOT SUPPORTED FEATURE : "
                                        .concat(String.format("%x", Integer.valueOf(i2))));
                        break;
                }
            }
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return BluetoothUtils.isSupportSmep(this.mDevice);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        Log.d("SecBluetoothDetailsAdvancedController", "onDestroy");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        if (BluetoothUtils.isSupportSmep(this.mDevice)) {
            this.mSmepReceiver.unRegisterCallback(this);
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        Log.d("SecBluetoothDetailsAdvancedController", "onPreferenceChange :: ");
        if (preference instanceof SwitchPreferenceCompat) {
            String key = preference.getKey();
            boolean isChecked = ((SwitchPreferenceCompat) preference).isChecked();
            String str = isChecked ? DATA.DM_FIELD_INDEX.PCSCF_DOMAIN : "1";
            if ("bluetooth_pref_touch_controls".equals(key)) {
                this.mDevice.semSetMetadata(
                        SppByteUtil.makeTlv(
                                SmepTag.STATE_TOUCH_LOCK.getTag(),
                                isChecked ? new byte[] {0} : new byte[] {1}));
                SALogging.insertSALog(
                        this.mScreenId,
                        this.mContext
                                .getResources()
                                .getString(
                                        R.string
                                                .event_device_profiles_setting_block_touches_switch),
                        str);
            }
        } else {
            Log.d("SecBluetoothDetailsAdvancedController", "key = " + preference.getKey());
        }
        return true;
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        String str;
        Log.d(
                "SecBluetoothDetailsAdvancedController",
                "onPreferenceClick :: key = " + preference.getKey());
        String key = preference.getKey();
        if ("bluetooth_pref_open_galaxy_wearable".equals(key)) {
            str =
                    this.mContext
                            .getResources()
                            .getString(R.string.event_device_profiles_setting_open_galaxy_wearable);
            this.mCachedDevice.shouldLaunchGM(ActivityThread.currentPackageName(), true);
        } else if ("bluetooth_pref_about_earbuds".equals(key)) {
            str =
                    this.mContext
                            .getResources()
                            .getString(R.string.event_device_profiles_setting_about_earbuds);
            BluetoothDevice bluetoothDevice = this.mDevice;
            String canonicalName = BluetoothAboutFragment.class.getCanonicalName();
            Bundle bundle = new Bundle(2);
            bundle.putString("device_address", bluetoothDevice.getAddress());
            PreferenceFragmentCompat preferenceFragmentCompat = this.mFragment;
            if (preferenceFragmentCompat.getActivity() instanceof SettingsActivity) {
                SubSettingLauncher subSettingLauncher =
                        new SubSettingLauncher(preferenceFragmentCompat.getActivity());
                SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
                launchRequest.mSourceMetricsCategory = 24;
                launchRequest.mDestinationName = canonicalName;
                launchRequest.mArguments = bundle;
                subSettingLauncher.launch();
            }
        } else {
            str = ApnSettings.MVNO_NONE;
        }
        SALogging.insertSALog(this.mScreenId, str);
        return true;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        Log.d("SecBluetoothDetailsAdvancedController", "onResume");
        if (!BluetoothUtils.isSupportSmep(this.mDevice)) {
            this.mAdvancedContainer.setVisible(false);
            return;
        }
        if (!this.mAdvancedContainer.isVisible()) {
            this.mAdvancedContainer.setVisible(true);
            initFeatureList();
        }
        this.mSmepReceiver.registerCallback(this);
        boolean isConnected = this.mCachedDevice.isConnected();
        this.mAdvancedContainer.setEnabled(isConnected);
        setIconEnabled(isConnected);
        initCurrentStatus();
    }

    public final void setEnabled(boolean z) {
        if (!this.mAdvancedContainer.isVisible() && BluetoothUtils.isSupportSmep(this.mDevice)) {
            this.mAdvancedContainer.setVisible(true);
            initFeatureList();
            this.mSmepReceiver.registerCallback(this);
        }
        this.mAdvancedContainer.setEnabled(z);
        setIconEnabled(z);
        initCurrentStatus();
    }

    public final void setIconEnabled(boolean z) {
        int i = z ? 255 : 102;
        Preference preference = this.mAboutPreference;
        if (preference != null) {
            preference.getIcon().setAlpha(i);
        }
        SwitchPreferenceCompat switchPreferenceCompat = this.mBlockTouchesPreference;
        if (switchPreferenceCompat != null) {
            switchPreferenceCompat.getIcon().setAlpha(i);
        }
        Preference preference2 = this.mOpenAppPreference;
        if (preference2 != null) {
            preference2.getIcon().setAlpha(i);
        }
    }

    public final void setPreference$1(Preference preference, Drawable drawable) {
        Log.d("SecBluetoothDetailsAdvancedController", "setPreference :: ");
        if (!TextUtils.isEmpty(null)) {
            preference.setTitle((CharSequence) null);
        }
        if (!TextUtils.isEmpty(null)) {
            preference.setSummary((CharSequence) null);
        }
        if (drawable != null) {
            drawable.setTint(
                    this.mContext.getResources().getColor(R.color.bt_device_icon_tint_color));
            preference.setIcon(drawable);
        }
        preference.setOnPreferenceClickListener(this);
    }

    @Override // com.samsung.android.settingslib.bluetooth.smep.BluetoothSmepReceiver.SmepCallBack
    public final void updateTouchPadState(BluetoothDevice bluetoothDevice, Map map) {
        initCurrentStatus();
    }

    @Override // com.samsung.android.settingslib.bluetooth.smep.BluetoothSmepReceiver.SmepCallBack
    public final void updateBatteryState(BluetoothDevice bluetoothDevice, Map map) {}

    @Override // com.samsung.android.settingslib.bluetooth.smep.BluetoothSmepReceiver.SmepCallBack
    public final void updateNoiseControlState(BluetoothDevice bluetoothDevice, Map map) {}
}
