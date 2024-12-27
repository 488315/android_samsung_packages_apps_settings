package com.samsung.android.settings.batterywidget;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

import com.samsung.android.knox.container.EnterpriseContainerConstants;
import com.samsung.android.os.SemCompanionDeviceBatteryInfo;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BudsTypeConverter implements DeviceTypeConverter {
    @Override // com.samsung.android.settings.batterywidget.DeviceTypeConverter
    public final SemCompanionDeviceBatteryInfo convertDeviceType(
            SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo) {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null) {
            Log.w(
                    "BatteryWidget/BudsTypeConverter",
                    "convertDeviceTypeIfNeeded bluetoothAdapter is null");
            return semCompanionDeviceBatteryInfo;
        }
        try {
            BluetoothDevice remoteDevice =
                    defaultAdapter.getRemoteDevice(semCompanionDeviceBatteryInfo.getAddress());
            long currentTimeMillis = System.currentTimeMillis();
            byte[] semGetManufacturerDeviceIconIndex =
                    remoteDevice.semGetManufacturerDeviceIconIndex();
            Log.i(
                    "BatteryWidget/BudsTypeConverter",
                    "isBudsTypeStem() " + (System.currentTimeMillis() - currentTimeMillis));
            if (semGetManufacturerDeviceIconIndex != null
                    && semGetManufacturerDeviceIconIndex.length >= 2
                    && semGetManufacturerDeviceIconIndex[0] == 21
                    && semGetManufacturerDeviceIconIndex[1] == 5) {
                semCompanionDeviceBatteryInfo.setDeviceType(
                        EnterpriseContainerConstants.SYSTEM_SIGNED_APP);
                Log.i(
                        "BatteryWidget/BudsTypeConverter",
                        "convertDeviceTypeIfNeeded() convert type BUDS_STEM : "
                                + semCompanionDeviceBatteryInfo.getDeviceName());
            }
        } catch (IllegalArgumentException e) {
            Log.w(
                    "BatteryWidget/BudsTypeConverter",
                    "convertDeviceTypeIfNeeded IllegalArgumentException " + e.getMessage());
        }
        return semCompanionDeviceBatteryInfo;
    }
}
