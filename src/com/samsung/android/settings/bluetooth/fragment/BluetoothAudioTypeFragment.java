package com.samsung.android.settings.bluetooth.fragment;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.settings.MainClear$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.bluetooth.Utils;
import com.android.settings.dashboard.RestrictedDashboardFragment;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.knox.EnterpriseContainerCallback;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.widget.SecRadioButtonPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BluetoothAudioTypeFragment extends RestrictedDashboardFragment
        implements SecRadioButtonPreference.OnClickListener, CachedBluetoothDevice.Callback {
    public CachedBluetoothDevice mCachedDevice;
    public BluetoothDevice mDevice;
    public String mDeviceAddress;
    public LocalBluetoothManager mManager;
    public SecRadioButtonPreference mRadioHeadphones;
    public SecRadioButtonPreference mRadioOthers;
    public SecRadioButtonPreference mRadioSpeaker;
    public String mScreenId;

    public BluetoothAudioTypeFragment() {
        super("no_config_bluetooth");
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "BluetoothAudioTypeFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_FAILURE;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_bluetooth_audio_type_fragment;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        boolean z;
        CachedBluetoothDevice findDevice;
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(context, Utils.mOnInitCallback);
        this.mManager = localBluetoothManager;
        boolean z2 = true;
        if (localBluetoothManager == null) {
            Log.w("BluetoothAudioTypeFragment", "Activity started without bluetooth is not ready");
            z = true;
        } else {
            z = false;
        }
        String string = getArguments().getString("device_address");
        this.mDeviceAddress = string;
        if (string == null) {
            Log.w(
                    "BluetoothAudioTypeFragment",
                    "Activity started without a remote bluetooth device is not ready");
            z = true;
        } else {
            LocalBluetoothManager localBluetoothManager2 = this.mManager;
            if (localBluetoothManager2 == null) {
                findDevice = null;
            } else {
                findDevice =
                        this.mManager.mCachedDeviceManager.findDevice(
                                localBluetoothManager2.mLocalAdapter.mAdapter.getRemoteDevice(
                                        string));
            }
            this.mCachedDevice = findDevice;
        }
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        if (cachedBluetoothDevice == null) {
            MainClear$$ExternalSyntheticOutline0.m$1(
                    new StringBuilder("onAttach() CachedDevice is null. address = "),
                    this.mDeviceAddress,
                    "BluetoothAudioTypeFragment");
        } else {
            this.mDevice = cachedBluetoothDevice.mDevice;
            z2 = z;
        }
        super.onAttach(context);
        if (z2) {
            finish();
        }
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        SecRadioButtonPreference secRadioButtonPreference =
                (SecRadioButtonPreference) findPreference("sec_bluetooth_audio_type_headphones");
        this.mRadioHeadphones = secRadioButtonPreference;
        secRadioButtonPreference.mListener = this;
        SecRadioButtonPreference secRadioButtonPreference2 =
                (SecRadioButtonPreference) findPreference("sec_bluetooth_audio_type_speaker");
        this.mRadioSpeaker = secRadioButtonPreference2;
        secRadioButtonPreference2.mListener = this;
        SecRadioButtonPreference secRadioButtonPreference3 =
                (SecRadioButtonPreference) findPreference("sec_bluetooth_audio_type_others");
        this.mRadioOthers = secRadioButtonPreference3;
        secRadioButtonPreference3.mListener = this;
        TextView textView =
                (TextView)
                        ((LayoutPreference) findPreference("sec_high_refresh_rate_preview"))
                                .mRootView.findViewById(R.id.sec_bluetooth_link);
        CharSequence text = getText(R.string.sec_bluetooth_audio_type_link);
        StringBuilder sb = new StringBuilder();
        sb.append(text);
        int indexOf = sb.indexOf("%1$s");
        if (indexOf == -1) {
            textView.setText(sb);
        } else {
            sb.delete(indexOf, indexOf + 4);
            int indexOf2 = sb.indexOf("%2$s");
            if (indexOf2 == -1) {
                textView.setText(sb);
            } else {
                sb.delete(indexOf2, indexOf2 + 4);
                textView.setText(sb.toString(), TextView.BufferType.SPANNABLE);
                textView.setMovementMethod(LinkMovementMethod.getInstance());
                ((Spannable) textView.getText())
                        .setSpan(
                                new ClickableSpan() { // from class:
                                    // com.samsung.android.settings.bluetooth.fragment.BluetoothAudioTypeFragment.1
                                    @Override // android.text.style.ClickableSpan
                                    public final void onClick(View view) {
                                        Intent intent = new Intent();
                                        intent.setPackage("com.samsung.android.forest");
                                        intent.setAction(
                                                "com.samsung.android.forest.OPEN_VOLUMEMONITOR_ONBOARDING");
                                        BluetoothAudioTypeFragment.this.startActivity(intent);
                                        BluetoothAudioTypeFragment bluetoothAudioTypeFragment =
                                                BluetoothAudioTypeFragment.this;
                                        SALogging.insertSALog(
                                                bluetoothAudioTypeFragment.mScreenId,
                                                bluetoothAudioTypeFragment
                                                        .getResources()
                                                        .getString(
                                                                R.string
                                                                        .event_device_profiles_setting_audio_type_link));
                                    }

                                    @Override // android.text.style.ClickableSpan,
                                    // android.text.style.CharacterStyle
                                    public final void updateDrawState(TextPaint textPaint) {
                                        super.updateDrawState(textPaint);
                                        textPaint.setUnderlineText(true);
                                        textPaint.setColor(
                                                BluetoothAudioTypeFragment.this
                                                        .getResources()
                                                        .getColor(
                                                                R.color
                                                                        .sec_tips_description_link_text_color));
                                    }
                                },
                                indexOf,
                                indexOf2,
                                33);
            }
        }
        this.mScreenId = getResources().getString(R.string.screen_device_profiles_setting);
    }

    @Override // com.android.settingslib.bluetooth.CachedBluetoothDevice.Callback
    public final void onDeviceAttributesChanged() {
        updateStatus();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        if (cachedBluetoothDevice != null) {
            cachedBluetoothDevice.unregisterCallback(this);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.samsung.android.settings.widget.SecRadioButtonPreference.OnClickListener
    public final void onRadioButtonClicked(SecRadioButtonPreference secRadioButtonPreference) {
        boolean z;
        int i = 0;
        String key = secRadioButtonPreference.getKey();
        String string =
                getResources()
                        .getString(R.string.event_device_profiles_setting_audio_type_radio_button);
        key.getClass();
        switch (key.hashCode()) {
            case -145534046:
                if (key.equals("sec_bluetooth_audio_type_headphones")) {
                    z = false;
                    break;
                }
                z = -1;
                break;
            case 1174759136:
                if (key.equals("sec_bluetooth_audio_type_others")) {
                    z = true;
                    break;
                }
                z = -1;
                break;
            case 1195428898:
                if (key.equals("sec_bluetooth_audio_type_speaker")) {
                    z = 2;
                    break;
                }
                z = -1;
                break;
            default:
                z = -1;
                break;
        }
        switch (z) {
            case false:
                this.mDevice.semSetAudioType(2);
                break;
            case true:
                this.mDevice.semSetAudioType(1);
                i = 2;
                break;
            case true:
                this.mDevice.semSetAudioType(3);
                i = 1;
                break;
            default:
                i = -1;
                break;
        }
        updateStatus();
        SALogging.insertSALog(i, this.mScreenId, string, (String) null);
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        Log.d("BluetoothAudioTypeFragment", "onResume");
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        if (cachedBluetoothDevice != null) {
            cachedBluetoothDevice.registerCallback(this);
        }
        updateStatus();
    }

    public final void updateStatus() {
        int semGetAudioType = this.mDevice.semGetAudioType();
        if (semGetAudioType == 2) {
            this.mRadioHeadphones.setChecked(true);
            this.mRadioSpeaker.setChecked(false);
            this.mRadioOthers.setChecked(false);
        } else if (semGetAudioType != 3) {
            this.mRadioHeadphones.setChecked(false);
            this.mRadioSpeaker.setChecked(false);
            this.mRadioOthers.setChecked(true);
        } else {
            this.mRadioHeadphones.setChecked(false);
            this.mRadioSpeaker.setChecked(true);
            this.mRadioOthers.setChecked(false);
        }
    }
}
