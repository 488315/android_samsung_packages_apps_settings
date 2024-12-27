package com.samsung.android.settings.bluetooth.controller;

import android.bluetooth.BluetoothHearingAid;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.SeekBar;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.widget.SeekBarPreference;
import com.android.settingslib.bluetooth.HearingAidProfile;

import com.samsung.android.settings.logging.SALogging;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecBluetoothHearingAidVolumePreference extends SeekBarPreference {
    public SecBluetoothDetailsHearingAidController mCallBack;
    public final Context mContext;
    public final String mScreenId;
    public SeekBar mSeekBar;
    public int mVolumeKey;

    public SecBluetoothHearingAidVolumePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        setLayoutResource(R.layout.sec_bluetooth_preference_asha_slider);
        this.mScreenId = context.getResources().getString(R.string.screen_device_profiles_setting);
    }

    @Override // com.android.settings.widget.SeekBarPreference,
              // com.android.settingslib.RestrictedPreference,
              // com.android.settingslib.widget.TwoTargetPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        Log.d(
                "SecBluetoothHearingAidVolumePreference",
                "onBindViewHolder :: key = " + this.mVolumeKey);
        SeekBar seekBar = (SeekBar) preferenceViewHolder.findViewById(android.R.id.textClassifier);
        this.mSeekBar = seekBar;
        seekBar.setSoundEffectsEnabled(true);
        this.mSeekBar.setMax(100);
        Drawable icon = getIcon();
        if (icon != null) {
            icon.setColorFilter(
                    this.mContext.getResources().getColor(R.color.sec_bluetooth_icon_primary),
                    PorterDuff.Mode.SRC_IN);
        }
        SecBluetoothDetailsHearingAidController secBluetoothDetailsHearingAidController =
                this.mCallBack;
        if (secBluetoothDetailsHearingAidController != null) {
            this.mSeekBar.setProgress(
                    secBluetoothDetailsHearingAidController.getGain(this.mVolumeKey));
        }
        this.mSeekBar.setOnSeekBarChangeListener(this);
    }

    @Override // com.android.settings.widget.SeekBarPreference,
              // android.widget.SeekBar.OnSeekBarChangeListener
    public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        super.onProgressChanged(seekBar, i, z);
        Log.d(
                "SecBluetoothHearingAidVolumePreference",
                "onProgressChanged :: progress = " + i + ", fromTouch = " + z);
        SecBluetoothDetailsHearingAidController secBluetoothDetailsHearingAidController =
                this.mCallBack;
        if (secBluetoothDetailsHearingAidController == null) {
            return;
        }
        if (z) {
            SALogging.insertSALog(
                    i,
                    this.mScreenId,
                    String.valueOf(
                            Integer.parseInt(
                                    this.mContext
                                            .getResources()
                                            .getString(
                                                    R.string
                                                            .event_device_profiles_setting_hearing_volume))),
                    String.valueOf(String.valueOf(this.mVolumeKey)));
        } else {
            i = secBluetoothDetailsHearingAidController.getGain(this.mVolumeKey);
            seekBar.setProgress(i);
        }
        SecBluetoothDetailsHearingAidController secBluetoothDetailsHearingAidController2 =
                this.mCallBack;
        int i2 = this.mVolumeKey;
        HearingAidProfile hearingAidProfile =
                secBluetoothDetailsHearingAidController2.mProfileProxy;
        if (hearingAidProfile != null) {
            if (i2 == 0) {
                BluetoothHearingAid bluetoothHearingAid = hearingAidProfile.mService;
                if (bluetoothHearingAid == null) {
                    return;
                }
                bluetoothHearingAid.setIndependentGain(i, 0);
                return;
            }
            if (i2 == 1) {
                BluetoothHearingAid bluetoothHearingAid2 = hearingAidProfile.mService;
                if (bluetoothHearingAid2 == null) {
                    return;
                }
                bluetoothHearingAid2.setIndependentGain(i, 1);
                return;
            }
            if (i2 != 2) {
                return;
            }
            BluetoothHearingAid bluetoothHearingAid3 = hearingAidProfile.mService;
            if (bluetoothHearingAid3 != null) {
                bluetoothHearingAid3.setIndependentGain(i, 0);
            }
            BluetoothHearingAid bluetoothHearingAid4 =
                    secBluetoothDetailsHearingAidController2.mProfileProxy.mService;
            if (bluetoothHearingAid4 == null) {
                return;
            }
            bluetoothHearingAid4.setIndependentGain(i, 1);
        }
    }
}
