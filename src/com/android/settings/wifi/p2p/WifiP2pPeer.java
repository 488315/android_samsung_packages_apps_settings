package com.android.settings.wifi.p2p;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import com.samsung.android.wifi.p2p.SemWifiP2pDevice;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class WifiP2pPeer extends Preference {
    public static final int[] deviceTypeDefaultIcon = {
        R.drawable.sec_wifidirect_list_ic_mobile,
        R.drawable.sec_wifidirect_list_ic_tablet,
        R.drawable.sec_wifidirect_list_ic_wearable,
        R.drawable.sec_wifidirect_list_ic_pc,
        R.drawable.sec_wifidirect_list_ic_accessory,
        R.drawable.sec_wifidirect_list_ic_tv,
        R.drawable.sec_wifidirect_list_ic_level_box,
        R.drawable.sec_wifidirect_list_ic_signage,
        R.drawable.sec_wifidirect_list_ic_refrigerator,
        R.drawable.sec_wifidirect_list_ic_washing_machine,
        R.drawable.sec_wifidirect_list_ic_dryer,
        R.drawable.sec_wifidirect_list_ic_floor_airconditioner,
        R.drawable.sec_wifidirect_list_ic_room_airconditioner,
        R.drawable.sec_wifidirect_list_ic_system_airconditioner,
        R.drawable.sec_wifidirect_list_ic_air_purifier,
        R.drawable.sec_wifidirect_list_ic_oven,
        R.drawable.sec_wifidirect_list_ic_range,
        R.drawable.sec_wifidirect_list_ic_robot_vacuum,
        R.drawable.sec_wifidirect_list_ic_smart_home,
        R.drawable.sec_wifidirect_list_ic_printer,
        R.drawable.sec_wifidirect_list_ic_level_u,
        R.drawable.sec_wifidirect_list_ic_stereo,
        R.drawable.sec_wifidirect_list_ic_tv,
        R.drawable.sec_wifidirect_list_ic_signage,
        R.drawable.sec_wifidirect_list_ic_samsung_connect,
        R.drawable.sec_wifidirect_list_ic_camera,
        R.drawable.sec_wifidirect_list_ic_camcorder
    };
    public static final int[] deviceTypeDefaultIconExt = {R.drawable.sec_wifidirect_list_ic_vst};
    public static final int[] sDeviceTypeImages = {
        R.drawable.sec_wifidirect_list_ic_pc,
        R.drawable.sec_wifidirect_list_ic_keyboard,
        R.drawable.sec_wifidirect_list_ic_printer,
        R.drawable.sec_wifidirect_list_ic_camera,
        R.drawable.sec_wifidirect_list_ic_storage,
        R.drawable.sec_wifidirect_list_ic_network_infra,
        R.drawable.sec_wifidirect_list_ic_tv,
        R.drawable.sec_wifidirect_list_ic_soundbar,
        R.drawable.sec_wifidirect_list_ic_game_device,
        R.drawable.sec_wifidirect_list_ic_mobile,
        R.drawable.sec_wifidirect_list_ic_level_box
    };
    public boolean mBinding;
    public final WifiP2pDevice mDevice;
    public TextView mDeviceName;
    public final int mIconIndex;
    public TextView mSecondSummary;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public enum devTypeNum {
        pc(1),
        /* JADX INFO: Fake field, exist only in values array */
        keyboard(2),
        /* JADX INFO: Fake field, exist only in values array */
        printer(3),
        /* JADX INFO: Fake field, exist only in values array */
        camera(4),
        /* JADX INFO: Fake field, exist only in values array */
        storage(5),
        /* JADX INFO: Fake field, exist only in values array */
        network_infra(6),
        /* JADX INFO: Fake field, exist only in values array */
        tv(7),
        /* JADX INFO: Fake field, exist only in values array */
        soundbar(8),
        /* JADX INFO: Fake field, exist only in values array */
        game_device(9),
        mobile(10),
        level_box(11);

        int mValue;

        devTypeNum(int i) {
            this.mValue = i;
        }
    }

    public WifiP2pPeer(
            Context context, WifiP2pDevice wifiP2pDevice, SemWifiP2pDevice semWifiP2pDevice) {
        super(context);
        this.mIconIndex = -1;
        this.mDevice = wifiP2pDevice;
        int i = 0;
        int deviceType = semWifiP2pDevice != null ? semWifiP2pDevice.getDeviceType() : 0;
        setLayoutResource(R.layout.sec_wifi_p2p_custom_preference);
        if (deviceType > 0 && deviceType <= 27) {
            this.mIconIndex = deviceTypeDefaultIcon[deviceType - 1];
            return;
        }
        if (deviceType == 50) {
            this.mIconIndex = deviceTypeDefaultIconExt[deviceType - 50];
            return;
        }
        boolean isEmpty = TextUtils.isEmpty(wifiP2pDevice.primaryDeviceType);
        devTypeNum devtypenum = devTypeNum.mobile;
        int[] iArr = sDeviceTypeImages;
        if (isEmpty) {
            Log.e("WifiP2pPeer", "Malformed primaryDeviceType");
            this.mIconIndex = iArr[devtypenum.mValue - 1];
            return;
        }
        if (wifiP2pDevice.primaryDeviceType.contains("-")) {
            String[] split = wifiP2pDevice.primaryDeviceType.split("-");
            if (split != null) {
                i = Integer.parseInt(split[0]);
            }
        } else {
            String substring = wifiP2pDevice.primaryDeviceType.substring(2, 4);
            if (substring != null) {
                i = Integer.parseInt(substring, 16);
            }
        }
        if (i < devTypeNum.pc.mValue || i > devTypeNum.level_box.mValue) {
            this.mIconIndex = iArr[devtypenum.mValue - 1];
        } else {
            this.mIconIndex = iArr[i - 1];
        }
    }

    @Override // androidx.preference.Preference
    public final void notifyChanged() {
        if (this.mBinding) {
            return;
        }
        super.notifyChanged();
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        this.mBinding = true;
        if (TextUtils.isEmpty(this.mDevice.deviceName)) {
            setTitle(this.mDevice.deviceAddress);
        } else {
            setTitle(this.mDevice.deviceName);
        }
        this.mDeviceName = (TextView) preferenceViewHolder.findViewById(android.R.id.title);
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.second_summary);
        this.mSecondSummary = textView;
        textView.setVisibility(8);
        Context context = getContext();
        String[] stringArray = context.getResources().getStringArray(R.array.wifi_p2p_status);
        int i = this.mDevice.status;
        if (i == 1) {
            this.mDeviceName.setAlpha(0.37f);
            this.mDeviceName.setTextColor(
                    context.getResources().getColor(R.color.wifi_p2p_preference_title_color));
            setSummary(R.string.sec_wifi_p2p_connecting);
            this.mSecondSummary.setVisibility(0);
            TextView textView2 = this.mSecondSummary;
            if (textView2 != null) {
                textView2.setText(R.string.sec_wifi_p2p_tap_to_cancel_connect);
            }
        } else if (i == 0) {
            this.mDeviceName.setAlpha(1.0f);
            this.mDeviceName.setTextColor(
                    context.getResources().getColor(R.color.sec_wifi_p2p_highlight_text_color));
            this.mSecondSummary.setVisibility(0);
            TextView textView3 = this.mSecondSummary;
            if (textView3 != null) {
                textView3.setText(R.string.sec_wifi_p2p_tap_to_disconnect);
            }
        } else {
            this.mDeviceName.setAlpha(1.0f);
            this.mDeviceName.setTextColor(
                    context.getResources().getColor(R.color.wifi_p2p_preference_title_color));
            setSummary(stringArray[this.mDevice.status]);
        }
        setIcon(this.mIconIndex);
        getIcon().setTint(context.getResources().getColor(R.color.bt_device_icon_tint_color));
        this.mBinding = false;
        super.onBindViewHolder(preferenceViewHolder);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.preference.Preference, java.lang.Comparable
    public final int compareTo(Preference preference) {
        if (!(preference instanceof WifiP2pPeer)) {
            return 1;
        }
        WifiP2pDevice wifiP2pDevice = this.mDevice;
        int i = wifiP2pDevice.status;
        WifiP2pDevice wifiP2pDevice2 = ((WifiP2pPeer) preference).mDevice;
        int i2 = wifiP2pDevice2.status;
        if (i != i2) {
            return i < i2 ? -1 : 1;
        }
        String str = wifiP2pDevice.deviceName;
        return str != null
                ? str.compareToIgnoreCase(wifiP2pDevice2.deviceName)
                : wifiP2pDevice.deviceAddress.compareToIgnoreCase(wifiP2pDevice2.deviceAddress);
    }
}
