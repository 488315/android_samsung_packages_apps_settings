package com.samsung.android.settings.bluetooth.controller;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;

import com.android.settings.R;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.bluetooth.BluetoothDetailsController;
import com.android.settingslib.bluetooth.A2dpProfile;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.HeadsetProfile;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.settingslib.core.lifecycle.Lifecycle;

import com.samsung.android.settings.logging.SALogging;
import com.sec.ims.configuration.DATA;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecBluetoothDetailsCodecController extends BluetoothDetailsController
        implements Preference.OnPreferenceChangeListener {
    public final AnonymousClass1 mBroadcastReceiver;
    public final CachedBluetoothDevice mCachedDevice;
    public PreferenceGroup mCodecContainer;
    public boolean mCodecGroupIsRemoved;
    public final LocalBluetoothProfileManager mProfileManager;
    public boolean mReceiverRegistered;
    public String mScreenId;
    public boolean mSscUhqStateChangedFromSettings;
    public boolean mSwbStateChangedFromSettings;

    /* JADX WARN: Type inference failed for: r1v2, types: [com.samsung.android.settings.bluetooth.controller.SecBluetoothDetailsCodecController$1] */
    public SecBluetoothDetailsCodecController(
            Context context,
            PreferenceFragmentCompat preferenceFragmentCompat,
            LocalBluetoothManager localBluetoothManager,
            CachedBluetoothDevice cachedBluetoothDevice,
            Lifecycle lifecycle) {
        super(context, preferenceFragmentCompat, cachedBluetoothDevice, lifecycle);
        this.mSwbStateChangedFromSettings = false;
        this.mReceiverRegistered = false;
        this.mSscUhqStateChangedFromSettings = false;
        this.mBroadcastReceiver =
                new BroadcastReceiver() { // from class:
                    // com.samsung.android.settings.bluetooth.controller.SecBluetoothDetailsCodecController.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        BluetoothDevice bluetoothDevice;
                        String action = intent.getAction();
                        if (action == null) {
                            Log.w(
                                    "SecBluetoothDetailsCodecController",
                                    "onReceive : action is null");
                            return;
                        }
                        Log.i(
                                "SecBluetoothDetailsCodecController",
                                "onReceive : action ".concat(action));
                        if (action.equals(
                                "com.samsung.bluetooth.a2dp.intent.action.SSC_UHQ_ENABLED_STATE_CHANGED")) {
                            BluetoothDevice bluetoothDevice2 =
                                    (BluetoothDevice)
                                            intent.getParcelableExtra(
                                                    "android.bluetooth.device.extra.DEVICE");
                            if (bluetoothDevice2 == null
                                    || !bluetoothDevice2.equals(
                                            SecBluetoothDetailsCodecController.this
                                                    .mCachedDevice
                                                    .mDevice)
                                    || SecBluetoothDetailsCodecController.this
                                            .mSscUhqStateChangedFromSettings) {
                                return;
                            }
                            Log.d(
                                    "SecBluetoothDetailsCodecController",
                                    "onReceive : update ssc uhq enabled status");
                            SecBluetoothDetailsCodecController.this.refresh();
                            return;
                        }
                        if (action.equals(
                                        "com.samsung.bt.hfp.intent.action.SWB_ENABLED_STATE_CHANGED")
                                && (bluetoothDevice =
                                                (BluetoothDevice)
                                                        intent.getParcelableExtra(
                                                                "android.bluetooth.device.extra.DEVICE"))
                                        != null
                                && bluetoothDevice.equals(
                                        SecBluetoothDetailsCodecController.this
                                                .mCachedDevice
                                                .mDevice)
                                && !SecBluetoothDetailsCodecController.this
                                        .mSwbStateChangedFromSettings) {
                            Log.d(
                                    "SecBluetoothDetailsCodecController",
                                    "onReceive : update swb codec enabled status");
                            SecBluetoothDetailsCodecController.this.refresh();
                        }
                    }
                };
        this.mProfileManager = localBluetoothManager.mProfileManager;
        this.mCachedDevice = cachedBluetoothDevice;
    }

    public final SwitchPreferenceCompat createCodecSwitchPreference(int i, String str) {
        Log.d(
                "SecBluetoothDetailsCodecController",
                "createCodecSwitchPreference :: codec : " + str);
        PreferenceFragmentCompat preferenceFragmentCompat = this.mFragment;
        SwitchPreferenceCompat switchPreferenceCompat =
                new SwitchPreferenceCompat(
                        new ContextThemeWrapper(
                                preferenceFragmentCompat.getActivity(),
                                R.style.PreferenceThemeOverlay));
        switchPreferenceCompat.setKey(str);
        switchPreferenceCompat.setTitle(str);
        switchPreferenceCompat.setPersistent();
        switchPreferenceCompat.setOrder(i);
        switchPreferenceCompat.setOnPreferenceChangeListener(this);
        Resources resources = preferenceFragmentCompat.getActivity().getResources();
        Drawable drawable = resources.getDrawable(R.drawable.list_ic_sound_accessory_default);
        if (resources.getString(R.string.bluetooth_hfp_codec_lc3).equals(str)) {
            drawable = resources.getDrawable(R.drawable.list_ic_call_audio);
        }
        if (drawable != null) {
            drawable.setTint(resources.getColor(R.color.bt_device_icon_tint_color));
            switchPreferenceCompat.setIcon(drawable);
        }
        return switchPreferenceCompat;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bluetooth_codec";
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController
    public final void init(PreferenceScreen preferenceScreen) {
        this.mScreenId =
                SecBluetoothBroadcastAssistantController$$ExternalSyntheticOutline0.m(
                        this.mFragment, R.string.screen_device_profiles_setting);
        this.mCodecContainer = (PreferenceGroup) preferenceScreen.findPreference("bluetooth_codec");
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController,
              // com.android.settingslib.core.lifecycle.events.OnPause
    public final void onPause() {
        super.onPause();
        Log.d("SecBluetoothDetailsCodecController", "onPause");
        if (this.mReceiverRegistered) {
            this.mFragment.getActivity().unregisterReceiver(this.mBroadcastReceiver);
            this.mReceiverRegistered = false;
            Log.d("SecBluetoothDetailsCodecController", "onPause : unregister receiver");
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(final Preference preference, Object obj) {
        A2dpProfile a2dpProfile;
        Log.d("SecBluetoothDetailsCodecController", "prof is null in onPreferenceChange");
        String key = preference.getKey();
        PreferenceFragmentCompat preferenceFragmentCompat = this.mFragment;
        boolean equals =
                key.equals(
                        preferenceFragmentCompat
                                .getActivity()
                                .getResources()
                                .getString(R.string.bluetooth_hfp_codec_lc3));
        String str = DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        if (equals) {
            HeadsetProfile headsetProfile =
                    (HeadsetProfile) localBluetoothProfileManager.getProfileByName("HEADSET");
            if (headsetProfile != null) {
                BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
                BluetoothHeadset bluetoothHeadset = headsetProfile.mService;
                int audioState =
                        bluetoothHeadset == null
                                ? 10
                                : bluetoothHeadset.getAudioState(bluetoothDevice);
                if (audioState == 12 || audioState == 11) {
                    Toast.makeText(
                                    preferenceFragmentCompat.getActivity(),
                                    preferenceFragmentCompat
                                            .getActivity()
                                            .getResources()
                                            .getString(
                                                    R.string.bluetooth_hfp_codec_lc3_toast_message),
                                    0)
                            .show();
                    Log.w(
                            "SecBluetoothDetailsCodecController",
                            "onPreferenceChange :: Not allow to change the LC3 option while sco"
                                + " connected");
                    return false;
                }
                SwitchPreferenceCompat switchPreferenceCompat = (SwitchPreferenceCompat) preference;
                boolean z = !switchPreferenceCompat.isChecked();
                BluetoothDevice bluetoothDevice2 = cachedBluetoothDevice.mDevice;
                BluetoothHeadset bluetoothHeadset2 = headsetProfile.mService;
                if (bluetoothHeadset2 != null) {
                    bluetoothHeadset2.setScoCodecEnabled(bluetoothDevice2, z, 4);
                }
                switchPreferenceCompat.setEnabled(false);
                SALogging.insertSALog(
                        this.mScreenId,
                        SecBluetoothBroadcastAssistantController$$ExternalSyntheticOutline0.m(
                                preferenceFragmentCompat,
                                R.string.event_device_profiles_setting_super_wide_band_speech),
                        z ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
                Handler handler = new Handler() { // from class:
                            // com.samsung.android.settings.bluetooth.controller.SecBluetoothDetailsCodecController.2
                            @Override // android.os.Handler
                            public final void handleMessage(Message message) {
                                if (message.what != 0) {
                                    return;
                                }
                                removeMessages(0);
                                SecBluetoothDetailsCodecController
                                        secBluetoothDetailsCodecController =
                                                SecBluetoothDetailsCodecController.this;
                                secBluetoothDetailsCodecController.mSwbStateChangedFromSettings =
                                        false;
                                if (secBluetoothDetailsCodecController.mIsCalledDestroy) {
                                    Log.d(
                                            "SecBluetoothDetailsCodecController",
                                            "EVENT_LC3_ENABLING :: it doesn't need to update view,"
                                                + " it is destroyed");
                                } else {
                                    secBluetoothDetailsCodecController.refresh();
                                }
                            }
                        };
                Message message = new Message();
                message.what = 0;
                handler.sendMessageDelayed(message, 1000L);
                this.mSwbStateChangedFromSettings = true;
            } else {
                Log.e(
                        "SecBluetoothDetailsCodecController",
                        "headset profile is null in onPreferenceChange");
            }
        }
        if (key.equals(
                preferenceFragmentCompat
                        .getActivity()
                        .getResources()
                        .getString(R.string.bluetooth_a2dp_codec_ssc_uhq))) {
            A2dpProfile a2dpProfile2 =
                    (A2dpProfile) localBluetoothProfileManager.getProfileByName("A2DP");
            if (a2dpProfile2 != null) {
                SwitchPreferenceCompat switchPreferenceCompat2 =
                        (SwitchPreferenceCompat) preference;
                boolean z2 = !switchPreferenceCompat2.isChecked();
                BluetoothDevice bluetoothDevice3 = cachedBluetoothDevice.mDevice;
                if (a2dpProfile2.mService == null) {
                    Log.d("A2dpProfile", "setEnableScalableUhqMode mService is null");
                } else if (a2dpProfile2.getConnectionStatus(bluetoothDevice3) != 2) {
                    Log.d("A2dpProfile", "setEnableScalableUhqMode not connected");
                } else {
                    a2dpProfile2.mService.semSetCodecEnabled(bluetoothDevice3, z2, 8);
                }
                switchPreferenceCompat2.setEnabled(false);
                String str2 = this.mScreenId;
                String m =
                        SecBluetoothBroadcastAssistantController$$ExternalSyntheticOutline0.m(
                                preferenceFragmentCompat,
                                R.string.event_device_profiles_setting_uhq);
                if (z2) {
                    str = "1";
                }
                SALogging.insertSALog(str2, m, str);
                final int i = 0;
                Handler handler2 = new Handler(this) { // from class:
                            // com.samsung.android.settings.bluetooth.controller.SecBluetoothDetailsCodecController.3
                            public final /* synthetic */ SecBluetoothDetailsCodecController this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.os.Handler
                            public final void handleMessage(Message message2) {
                                switch (i) {
                                    case 0:
                                        if (message2.what == 0) {
                                            removeMessages(0);
                                            SecBluetoothDetailsCodecController
                                                    secBluetoothDetailsCodecController =
                                                            this.this$0;
                                            secBluetoothDetailsCodecController
                                                            .mSscUhqStateChangedFromSettings =
                                                    false;
                                            if (!secBluetoothDetailsCodecController
                                                    .mIsCalledDestroy) {
                                                ((SwitchPreferenceCompat) preference)
                                                        .setEnabled(true);
                                                secBluetoothDetailsCodecController.refresh();
                                                break;
                                            } else {
                                                Log.d(
                                                        "SecBluetoothDetailsCodecController",
                                                        "EVENT_LDAC_SWITCHING :: it doesn't need to"
                                                            + " update view, it is destroyed");
                                                break;
                                            }
                                        }
                                        break;
                                    default:
                                        if (message2.what == 0) {
                                            removeMessages(0);
                                            SecBluetoothDetailsCodecController
                                                    secBluetoothDetailsCodecController2 =
                                                            this.this$0;
                                            if (!secBluetoothDetailsCodecController2
                                                    .mIsCalledDestroy) {
                                                ((SwitchPreferenceCompat) preference)
                                                        .setEnabled(true);
                                                secBluetoothDetailsCodecController2.refresh();
                                                break;
                                            } else {
                                                Log.d(
                                                        "SecBluetoothDetailsCodecController",
                                                        "EVENT_LDAC_SWITCHING :: it doesn't need to"
                                                            + " update view, it is destroyed");
                                                break;
                                            }
                                        }
                                        break;
                                }
                            }
                        };
                Message message2 = new Message();
                message2.what = 0;
                handler2.sendMessageDelayed(message2, 2000L);
                this.mSscUhqStateChangedFromSettings = true;
            }
        } else if (key.equals(
                        preferenceFragmentCompat
                                .getActivity()
                                .getResources()
                                .getString(R.string.bluetooth_a2dp_codec_ldac))
                && (a2dpProfile =
                                (A2dpProfile) localBluetoothProfileManager.getProfileByName("A2DP"))
                        != null) {
            SwitchPreferenceCompat switchPreferenceCompat3 = (SwitchPreferenceCompat) preference;
            boolean z3 = !switchPreferenceCompat3.isChecked();
            BluetoothDevice bluetoothDevice4 = cachedBluetoothDevice.mDevice;
            if (a2dpProfile.mService == null) {
                Log.d("A2dpProfile", "setEnableLdacMode mService is null");
            } else if (a2dpProfile.getConnectionStatus(bluetoothDevice4) != 2) {
                Log.d("A2dpProfile", "setEnableLdacMode not connected");
            } else {
                a2dpProfile.mService.semSetCodecEnabled(bluetoothDevice4, z3, 4);
            }
            switchPreferenceCompat3.setEnabled(false);
            String str3 = this.mScreenId;
            String m2 =
                    SecBluetoothBroadcastAssistantController$$ExternalSyntheticOutline0.m(
                            preferenceFragmentCompat, R.string.event_device_profiles_setting_ldac);
            if (z3) {
                str = "1";
            }
            SALogging.insertSALog(str3, m2, str);
            final int i2 = 1;
            Handler handler3 = new Handler(this) { // from class:
                        // com.samsung.android.settings.bluetooth.controller.SecBluetoothDetailsCodecController.3
                        public final /* synthetic */ SecBluetoothDetailsCodecController this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.os.Handler
                        public final void handleMessage(Message message22) {
                            switch (i2) {
                                case 0:
                                    if (message22.what == 0) {
                                        removeMessages(0);
                                        SecBluetoothDetailsCodecController
                                                secBluetoothDetailsCodecController = this.this$0;
                                        secBluetoothDetailsCodecController
                                                        .mSscUhqStateChangedFromSettings =
                                                false;
                                        if (!secBluetoothDetailsCodecController.mIsCalledDestroy) {
                                            ((SwitchPreferenceCompat) preference).setEnabled(true);
                                            secBluetoothDetailsCodecController.refresh();
                                            break;
                                        } else {
                                            Log.d(
                                                    "SecBluetoothDetailsCodecController",
                                                    "EVENT_LDAC_SWITCHING :: it doesn't need to"
                                                        + " update view, it is destroyed");
                                            break;
                                        }
                                    }
                                    break;
                                default:
                                    if (message22.what == 0) {
                                        removeMessages(0);
                                        SecBluetoothDetailsCodecController
                                                secBluetoothDetailsCodecController2 = this.this$0;
                                        if (!secBluetoothDetailsCodecController2.mIsCalledDestroy) {
                                            ((SwitchPreferenceCompat) preference).setEnabled(true);
                                            secBluetoothDetailsCodecController2.refresh();
                                            break;
                                        } else {
                                            Log.d(
                                                    "SecBluetoothDetailsCodecController",
                                                    "EVENT_LDAC_SWITCHING :: it doesn't need to"
                                                        + " update view, it is destroyed");
                                            break;
                                        }
                                    }
                                    break;
                            }
                        }
                    };
            Message message3 = new Message();
            message3.what = 0;
            handler3.sendMessageDelayed(message3, 2000L);
        }
        return true;
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController,
              // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        boolean semIsCodecSupported;
        super.onResume();
        Log.d("SecBluetoothDetailsCodecController", "onResume");
        IntentFilter intentFilter = new IntentFilter();
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
        HeadsetProfile headsetProfile =
                (HeadsetProfile) localBluetoothProfileManager.getProfileByName("HEADSET");
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        if (headsetProfile != null
                && headsetProfile.isSwbMenuSupported(cachedBluetoothDevice.mDevice)) {
            intentFilter.addAction("com.samsung.bt.hfp.intent.action.SWB_ENABLED_STATE_CHANGED");
        }
        A2dpProfile a2dpProfile =
                (A2dpProfile) localBluetoothProfileManager.getProfileByName("A2DP");
        if (a2dpProfile != null) {
            BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
            BluetoothA2dp bluetoothA2dp = a2dpProfile.mService;
            if (bluetoothA2dp == null) {
                Log.d("A2dpProfile", "isSupportScalableUhq mService is null");
                semIsCodecSupported = false;
            } else {
                semIsCodecSupported = bluetoothA2dp.semIsCodecSupported(bluetoothDevice, 8);
            }
            if (semIsCodecSupported) {
                intentFilter.addAction(
                        "com.samsung.bluetooth.a2dp.intent.action.SSC_UHQ_ENABLED_STATE_CHANGED");
            }
        }
        if (intentFilter.countActions() > 0) {
            this.mFragment.getActivity().registerReceiver(this.mBroadcastReceiver, intentFilter);
            this.mReceiverRegistered = true;
            Log.d("SecBluetoothDetailsCodecController", "onResume : register receiver");
        }
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController
    public final void refresh() {
        boolean semIsCodecSupported;
        boolean semIsCodecEnabled;
        boolean semIsCodecSupported2;
        boolean semIsCodecEnabled2;
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        if (cachedBluetoothDevice.mIsRestored) {
            showOrHideCodecGroup();
            Log.d(
                    "SecBluetoothDetailsCodecController",
                    "refreshCodecs :: Device is restored. It is not support container");
            return;
        }
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
        HeadsetProfile headsetProfile =
                (HeadsetProfile) localBluetoothProfileManager.getProfileByName("HEADSET");
        boolean z = false;
        PreferenceFragmentCompat preferenceFragmentCompat = this.mFragment;
        if (headsetProfile != null
                && headsetProfile.isSwbMenuSupported(cachedBluetoothDevice.mDevice)) {
            SwitchPreferenceCompat switchPreferenceCompat =
                    (SwitchPreferenceCompat)
                            this.mCodecContainer.findPreference(
                                    preferenceFragmentCompat
                                            .getActivity()
                                            .getResources()
                                            .getString(R.string.bluetooth_hfp_codec_lc3));
            if (switchPreferenceCompat == null) {
                switchPreferenceCompat =
                        createCodecSwitchPreference(
                                this.mCodecContainer.getOrder() + 20,
                                SecBluetoothBroadcastAssistantController$$ExternalSyntheticOutline0
                                        .m(
                                                preferenceFragmentCompat,
                                                R.string.bluetooth_hfp_codec_lc3));
                this.mCodecContainer.addPreference(switchPreferenceCompat);
            }
            BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
            BluetoothHeadset bluetoothHeadset = headsetProfile.mService;
            boolean z2 =
                    bluetoothHeadset != null
                            && bluetoothHeadset.getScoCodecEnabled(bluetoothDevice, 4) == 1;
            boolean z3 = cachedBluetoothDevice.getProfileConnectionState(headsetProfile) == 2;
            switchPreferenceCompat.setChecked(z2);
            switchPreferenceCompat.setEnabled(z3);
            if (!z3) {
                switchPreferenceCompat.setSummary(
                        R.string.bluetooth_hfp_codec_lc3_hfp_disconnected_summary);
            } else if (z2) {
                switchPreferenceCompat.setSummary(R.string.bluetooth_hfp_codec_lc3_summary);
            } else {
                switchPreferenceCompat.setSummary(
                        R.string.bluetooth_hfp_codec_lc3_disabled_summary);
            }
            Utils$$ExternalSyntheticOutline0.m653m(
                    "refreshCodecs :: isLc3Enabled : ",
                    z2,
                    ", isHfpConnected : ",
                    z3,
                    "SecBluetoothDetailsCodecController");
        }
        A2dpProfile a2dpProfile =
                (A2dpProfile) localBluetoothProfileManager.getProfileByName("A2DP");
        int i = R.string.bluetooth_a2dp_codec_ldac_dual_audio_summary;
        if (a2dpProfile != null) {
            BluetoothDevice bluetoothDevice2 = cachedBluetoothDevice.mDevice;
            BluetoothA2dp bluetoothA2dp = a2dpProfile.mService;
            if (bluetoothA2dp == null) {
                Log.d("A2dpProfile", "isSupportScalableUhq mService is null");
                semIsCodecSupported2 = false;
            } else {
                semIsCodecSupported2 = bluetoothA2dp.semIsCodecSupported(bluetoothDevice2, 8);
            }
            if (semIsCodecSupported2) {
                SwitchPreferenceCompat switchPreferenceCompat2 =
                        (SwitchPreferenceCompat)
                                this.mCodecContainer.findPreference(
                                        preferenceFragmentCompat
                                                .getActivity()
                                                .getResources()
                                                .getString(R.string.bluetooth_a2dp_codec_ssc_uhq));
                if (switchPreferenceCompat2 == null) {
                    switchPreferenceCompat2 =
                            createCodecSwitchPreference(
                                    this.mCodecContainer.getOrder() + 30,
                                    SecBluetoothBroadcastAssistantController$$ExternalSyntheticOutline0
                                            .m(
                                                    preferenceFragmentCompat,
                                                    R.string.bluetooth_a2dp_codec_ssc_uhq));
                    this.mCodecContainer.addPreference(switchPreferenceCompat2);
                }
                BluetoothDevice bluetoothDevice3 = cachedBluetoothDevice.mDevice;
                BluetoothA2dp bluetoothA2dp2 = a2dpProfile.mService;
                if (bluetoothA2dp2 == null) {
                    Log.d("A2dpProfile", "getScalableUhqEnabled mService is null");
                    semIsCodecEnabled2 = false;
                } else {
                    semIsCodecEnabled2 = bluetoothA2dp2.semIsCodecEnabled(bluetoothDevice3, 8);
                }
                Log.d(
                        "SecBluetoothDetailsCodecController",
                        "refreshCodecs :: isScalableUhqEnabled : " + semIsCodecEnabled2);
                switchPreferenceCompat2.setChecked(semIsCodecEnabled2);
                int profileConnectionState =
                        cachedBluetoothDevice.getProfileConnectionState(a2dpProfile);
                if (profileConnectionState == 2) {
                    BluetoothA2dp bluetoothA2dp3 = a2dpProfile.mService;
                    if (!(bluetoothA2dp3 == null ? false : bluetoothA2dp3.semIsDualPlayMode())) {
                        z = true;
                    }
                }
                if (z) {
                    i =
                            semIsCodecEnabled2
                                    ? R.string.bluetooth_a2dp_codec_ssc_uhq_use_summary
                                    : R.string.bluetooth_a2dp_codec_ssc_uhq_off_summary;
                } else if (profileConnectionState != 2) {
                    i = R.string.bluetooth_a2dp_codec_ssc_uhq_not_use_summary;
                }
                switchPreferenceCompat2.setEnabled(z);
                switchPreferenceCompat2.setSummary(i);
                showOrHideCodecGroup();
            }
        }
        if (a2dpProfile != null) {
            BluetoothDevice bluetoothDevice4 = cachedBluetoothDevice.mDevice;
            BluetoothA2dp bluetoothA2dp4 = a2dpProfile.mService;
            if (bluetoothA2dp4 == null) {
                Log.d("A2dpProfile", "isSupportLdac mService is null");
                semIsCodecSupported = false;
            } else {
                semIsCodecSupported = bluetoothA2dp4.semIsCodecSupported(bluetoothDevice4, 4);
            }
            if (semIsCodecSupported) {
                SwitchPreferenceCompat switchPreferenceCompat3 =
                        (SwitchPreferenceCompat)
                                this.mCodecContainer.findPreference(
                                        preferenceFragmentCompat
                                                .getActivity()
                                                .getResources()
                                                .getString(R.string.bluetooth_a2dp_codec_ldac));
                if (switchPreferenceCompat3 == null) {
                    switchPreferenceCompat3 =
                            createCodecSwitchPreference(
                                    this.mCodecContainer.getOrder() + 30,
                                    SecBluetoothBroadcastAssistantController$$ExternalSyntheticOutline0
                                            .m(
                                                    preferenceFragmentCompat,
                                                    R.string.bluetooth_a2dp_codec_ldac));
                    this.mCodecContainer.addPreference(switchPreferenceCompat3);
                }
                BluetoothDevice bluetoothDevice5 = cachedBluetoothDevice.mDevice;
                BluetoothA2dp bluetoothA2dp5 = a2dpProfile.mService;
                if (bluetoothA2dp5 == null) {
                    Log.d("A2dpProfile", "getLdacEnabled mService is null");
                    semIsCodecEnabled = false;
                } else {
                    semIsCodecEnabled = bluetoothA2dp5.semIsCodecEnabled(bluetoothDevice5, 4);
                }
                Log.d(
                        "SecBluetoothDetailsCodecController",
                        "refreshCodecs :: isLdacEnabled : " + semIsCodecEnabled);
                switchPreferenceCompat3.setChecked(semIsCodecEnabled);
                int profileConnectionState2 =
                        cachedBluetoothDevice.getProfileConnectionState(a2dpProfile);
                if (profileConnectionState2 == 2) {
                    BluetoothA2dp bluetoothA2dp6 = a2dpProfile.mService;
                    if (!(bluetoothA2dp6 == null ? false : bluetoothA2dp6.semIsDualPlayMode())) {
                        z = true;
                    }
                }
                if (z) {
                    i =
                            semIsCodecEnabled
                                    ? R.string.bluetooth_a2dp_codec_ldac_use_summary
                                    : R.string.bluetooth_a2dp_codec_ldac_off_summary;
                } else if (profileConnectionState2 != 2) {
                    i = R.string.bluetooth_a2dp_codec_ldac_not_use_summary;
                }
                switchPreferenceCompat3.setEnabled(z);
                switchPreferenceCompat3.setSummary(i);
            }
        }
        showOrHideCodecGroup();
    }

    public final void showOrHideCodecGroup() {
        PreferenceGroup preferenceGroup = this.mCodecContainer;
        if (preferenceGroup == null) {
            this.mCodecGroupIsRemoved = false;
            return;
        }
        int preferenceCount = preferenceGroup.getPreferenceCount();
        boolean z = this.mCodecGroupIsRemoved;
        PreferenceFragmentCompat preferenceFragmentCompat = this.mFragment;
        if (!z && preferenceCount == 0) {
            preferenceFragmentCompat.getPreferenceScreen().removePreference(this.mCodecContainer);
            this.mCodecGroupIsRemoved = true;
        } else {
            if (!z || preferenceCount == 0) {
                return;
            }
            preferenceFragmentCompat.getPreferenceScreen().addPreference(this.mCodecContainer);
            this.mCodecGroupIsRemoved = false;
        }
    }
}
