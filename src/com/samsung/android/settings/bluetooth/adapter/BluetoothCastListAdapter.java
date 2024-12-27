package com.samsung.android.settings.bluetooth.adapter;

import android.bluetooth.BluetoothClass;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settingslib.bluetooth.BluetoothUtils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.bluetooth.BluetoothListController;
import com.samsung.android.settings.bluetooth.SecDeviceListPreferenceFragment;
import com.samsung.android.settings.bluetooth.bluetoothcast.BluetoothCastDevicePreference;
import com.samsung.android.settingslib.bluetooth.ManufacturerData;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.AudioCastProfile;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.CachedBluetoothCastDevice;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BluetoothCastListAdapter extends BluetoothListAdapter {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CastViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIcon;
        public LinearLayout mLinearLayout;
        public TextView mSummary;
        public ImageView mTipsBtn;
        public TextView mTitle;
        public View mView;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.mPreferenceList.size();
    }

    @Override // com.samsung.android.settings.bluetooth.adapter.BluetoothListAdapter
    public final String getLogTag() {
        return "BluetoothCastListAdapter";
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator.ItemAnimatorFinishedListener
    public final void onAnimationsFinished() {
        Log.d("BluetoothCastListAdapter", "BluetoothCastListAdapter: onAnimationsFinished");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        String string;
        if (i < 0 || i > this.mPreferenceList.size()) {
            Log.d("BluetoothCastListAdapter", "onBindViewHolder: failed - invalid index");
            return;
        }
        CastViewHolder castViewHolder = (CastViewHolder) viewHolder;
        final BluetoothCastDevicePreference bluetoothCastDevicePreference =
                (BluetoothCastDevicePreference) this.mPreferenceList.get(i);
        CachedBluetoothCastDevice cachedBluetoothCastDevice =
                bluetoothCastDevicePreference.mCachedCastDevice;
        String str =
                "\u200e"
                        + Html.escapeHtml(bluetoothCastDevicePreference.mCachedCastDevice.getName())
                        + "\u200e";
        new ManufacturerData(
                        bluetoothCastDevicePreference.mCachedCastDevice.mCastDevice
                                .getManufacturerData())
                .mData.getClass();
        String str2 = ApnSettings.MVNO_NONE + str;
        castViewHolder.mView.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.bluetooth.adapter.BluetoothCastListAdapter$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        BluetoothCastListAdapter bluetoothCastListAdapter =
                                BluetoothCastListAdapter.this;
                        SecDeviceListPreferenceFragment.this.onCastDevicePreferenceClick(
                                bluetoothCastDevicePreference);
                    }
                });
        Log.d(
                "BluetoothCastListAdapter",
                "onBindViewHolder: " + cachedBluetoothCastDevice.mCastDevice.getAddressForLog());
        castViewHolder.mTitle.setText(Html.fromHtml(str2));
        if (cachedBluetoothCastDevice.getMaxConnectionState() > 0) {
            castViewHolder.mTitle.setTextAppearance(
                    this.mContext, R.style.BluetoothDeviceConnectedHighlight);
        } else if (isDialogType()) {
            castViewHolder.mTitle.setTextAppearance(
                    this.mContext, R.style.BluetoothDeviceDialogTitleTextStyle);
        } else {
            castViewHolder.mTitle.setTextAppearance(
                    this.mContext, R.style.BluetoothDeviceTitleTextStyle);
        }
        boolean isBusy = cachedBluetoothCastDevice.isBusy();
        castViewHolder.mTitle.setEnabled(!isBusy);
        if (isBusy) {
            TextView textView = castViewHolder.mTitle;
            textView.setTextColor(textView.getTextColors().withAlpha(94));
        } else {
            TextView textView2 = castViewHolder.mTitle;
            textView2.setTextColor(textView2.getTextColors().withAlpha(255));
        }
        List castProfiles = cachedBluetoothCastDevice.getCastProfiles();
        int i2 = 0;
        boolean z = false;
        boolean z2 = false;
        while (true) {
            if (i2 < castProfiles.size()) {
                AudioCastProfile audioCastProfile = (AudioCastProfile) castProfiles.get(i2);
                if (audioCastProfile == null) {
                    Log.d("CachedBluetoothCastDevice", "getConnectionSummary :: profile is null");
                } else {
                    int castProfileConnectionState =
                            cachedBluetoothCastDevice.getCastProfileConnectionState(
                                    audioCastProfile);
                    if (castProfileConnectionState == 0) {
                        String str3 = cachedBluetoothCastDevice.mErrorMsg;
                        string =
                                (str3 == null || TextUtils.isEmpty(str3))
                                        ? cachedBluetoothCastDevice.mContext.getString(
                                                R.string.bluetooth_cast_via,
                                                cachedBluetoothCastDevice.mCastDevice.getPeerName())
                                        : cachedBluetoothCastDevice.mErrorMsg;
                    } else if (castProfileConnectionState == 1) {
                        string =
                                cachedBluetoothCastDevice.mContext.getString(
                                        R.string.bluetooth_cast_waiting_auth,
                                        cachedBluetoothCastDevice.mCastDevice.getPeerName());
                        break;
                    } else if (castProfileConnectionState == 2) {
                        z = true;
                        z2 = true;
                    } else if (castProfileConnectionState == 3) {
                        string =
                                cachedBluetoothCastDevice.mContext.getString(
                                        BluetoothUtils.getConnectionStateSummary(
                                                castProfileConnectionState));
                        break;
                    }
                }
                i2++;
            } else {
                string =
                        (z && z2)
                                ? cachedBluetoothCastDevice.mContext.getString(
                                        R.string.bluetooth_cast_connected_via,
                                        cachedBluetoothCastDevice.mCastDevice.getPeerName())
                                : null;
            }
        }
        if (string != null) {
            castViewHolder.mSummary.setText(string);
            castViewHolder.mSummary.setVisibility(0);
        } else {
            castViewHolder.mSummary.setVisibility(8);
        }
        ImageView imageView = castViewHolder.mIcon;
        CachedBluetoothCastDevice cachedBluetoothCastDevice2 =
                bluetoothCastDevicePreference.mCachedCastDevice;
        Drawable drawable =
                cachedBluetoothCastDevice2
                        .mContext
                        .getResources()
                        .getDrawable(R.drawable.list_ic_general_device);
        if (cachedBluetoothCastDevice2.mCastDevice.getBluetoothCastType() == 1) {
            Resources resources = cachedBluetoothCastDevice2.mContext.getResources();
            String deviceName = cachedBluetoothCastDevice2.mCastDevice.getDeviceName();
            BluetoothClass bluetoothClass =
                    cachedBluetoothCastDevice2.mCastDevice.getBluetoothClass();
            ManufacturerData manufacturerData =
                    new ManufacturerData(
                            cachedBluetoothCastDevice2.mCastDevice.getManufacturerData());
            Log.d(
                    "CachedBluetoothCastDevice",
                    "getBtClassDrawable :: "
                            + cachedBluetoothCastDevice2.mCastDevice.getAddressForLog()
                            + ", BluetoothClass = "
                            + bluetoothClass);
            int deviceIcon = manufacturerData.getDeviceIcon();
            if (deviceIcon == 0) {
                deviceIcon = R.drawable.list_ic_sound_accessory_default;
                if (bluetoothClass != null) {
                    int majorDeviceClass = bluetoothClass.getMajorDeviceClass();
                    if (majorDeviceClass == 256) {
                        if (bluetoothClass.getDeviceClass() != 284) {
                            deviceIcon = R.drawable.list_ic_laptop;
                        }
                        deviceIcon = R.drawable.list_ic_tablet;
                    } else if (majorDeviceClass == 512) {
                        byte[] manufacturerData2 =
                                cachedBluetoothCastDevice2.mCastDevice.getManufacturerData();
                        ManufacturerData manufacturerData3 =
                                new ManufacturerData(manufacturerData2);
                        if (manufacturerData2 == null
                                || manufacturerData3.mManufacturerType != 2
                                || manufacturerData3.mData.mDeviceCategory != 2) {
                            deviceIcon = R.drawable.list_ic_mobile;
                        }
                        deviceIcon = R.drawable.list_ic_tablet;
                    } else if (majorDeviceClass == 1024) {
                        if (deviceName != null) {
                            int i3 =
                                    deviceName.toUpperCase().startsWith("SAMSUNG LEVEL")
                                            ? deviceName.toUpperCase().contains("BOX")
                                                    ? R.drawable.list_ic_dlna_audio
                                                    : R.drawable.list_ic_headset
                                            : (deviceName.toUpperCase().startsWith("GEAR CIRCLE")
                                                            && cachedBluetoothCastDevice2
                                                                    .isGearIconX())
                                                    ? R.drawable.list_ic_gear_circle
                                                    : 0;
                            if (i3 != 0) {
                                deviceIcon = i3;
                            }
                        }
                        if (cachedBluetoothCastDevice2.isGearIconX()) {
                            deviceIcon = R.drawable.list_ic_true_wireless_earbuds;
                        } else if (bluetoothClass.getDeviceClass() == 1084) {
                            deviceIcon = R.drawable.list_ic_tv;
                        } else {
                            if (bluetoothClass.getDeviceClass() == 1076) {
                                deviceIcon = R.drawable.list_ic_camcoder;
                            }
                            if (!bluetoothClass.doesClassMatch(1)) {
                                deviceIcon = R.drawable.list_ic_mono_headset;
                            }
                        }
                    } else if (majorDeviceClass != 1536) {
                        if (majorDeviceClass == 1792 && bluetoothClass.getDeviceClass() == 1796) {
                            deviceIcon =
                                    (deviceName == null
                                                    || !(deviceName
                                                                    .toUpperCase()
                                                                    .startsWith("GEAR FIT")
                                                            || deviceName
                                                                    .toUpperCase()
                                                                    .startsWith("GALAXY FIT")))
                                            ? R.drawable.list_ic_wearable
                                            : R.drawable.list_ic_band;
                        }
                        if (!bluetoothClass.doesClassMatch(1) && bluetoothClass.doesClassMatch(0)) {
                            deviceIcon = R.drawable.list_ic_mono_headset;
                        }
                    } else {
                        deviceIcon =
                                (bluetoothClass.getDeviceClass() == 1664
                                                || bluetoothClass.getDeviceClass() == 1600)
                                        ? R.drawable.list_ic_printer
                                        : R.drawable.list_ic_camera;
                    }
                } else {
                    Log.w("CachedBluetoothCastDevice", "mBtClass is null");
                }
            }
            drawable = resources.getDrawable(deviceIcon);
        }
        imageView.setImageDrawable(
                BluetoothUtils.getOverlayIconTintableDrawable(
                        cachedBluetoothCastDevice2.mContext,
                        drawable,
                        R.drawable.sharing_ic_overlay,
                        R.drawable.sharing_ic_tintable));
        LinearLayout linearLayout = castViewHolder.mLinearLayout;
        if (linearLayout != null) {
            linearLayout.setVisibility(8);
        }
        ImageView imageView2 = castViewHolder.mTipsBtn;
        if (imageView2 != null) {
            imageView2.setVisibility(8);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate =
                LayoutInflater.from(this.mContext)
                        .inflate(
                                R.layout.sec_preference_bt_icon,
                                (ViewGroup) this.mParentView,
                                false);
        CastViewHolder castViewHolder = new CastViewHolder(inflate);
        castViewHolder.mView = inflate;
        castViewHolder.mIcon = (ImageView) inflate.findViewById(android.R.id.icon);
        castViewHolder.mTitle = (TextView) inflate.findViewById(android.R.id.title);
        castViewHolder.mSummary = (TextView) inflate.findViewById(android.R.id.summary);
        castViewHolder.mTipsBtn = (ImageView) inflate.findViewById(R.id.tips_btn);
        castViewHolder.mLinearLayout = (LinearLayout) inflate.findViewById(R.id.layout_details);
        return castViewHolder;
    }

    @Override // com.samsung.android.settings.bluetooth.adapter.BluetoothListAdapter
    public final void update() {
        int size = this.mPreferenceList.size();
        BluetoothListController bluetoothListController = this.mController;
        if (size == 0) {
            bluetoothListController.setVisibleCastGroup(8);
        } else {
            bluetoothListController.setVisibleCastGroup(0);
        }
    }

    @Override // com.samsung.android.settings.bluetooth.adapter.BluetoothListAdapter
    public final void printAddLog(Preference preference) {}
}
