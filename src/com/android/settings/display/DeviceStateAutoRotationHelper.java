package com.android.settings.display;

import android.content.Context;
import android.util.Log;

import com.android.internal.view.RotationPolicy;
import com.android.settings.R;
import com.android.settingslib.devicestate.DeviceStateRotationLockSettingsManager;

import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class DeviceStateAutoRotationHelper {
    public static ImmutableList createPreferenceControllers(Context context) {
        DeviceStateRotationLockSettingsManager deviceStateRotationLockSettingsManager =
                DeviceStateRotationLockSettingsManager.getInstance(context);
        deviceStateRotationLockSettingsManager.getClass();
        ArrayList arrayList =
                new ArrayList(deviceStateRotationLockSettingsManager.mSettableDeviceStates);
        int size = arrayList.size();
        if (size == 0) {
            return ImmutableList.of();
        }
        String[] stringArray =
                context.getResources()
                        .getStringArray(
                                R.array.config_settableAutoRotationDeviceStatesDescriptions);
        if (size != stringArray.length) {
            Log.wtf(
                    "DeviceStateAutoRotHelpr",
                    "Mismatch between number of device states and device states descriptions.");
            return ImmutableList.of();
        }
        ImmutableList.Itr itr = ImmutableList.EMPTY_ITR;
        CollectPreconditions.checkNonnegative(size, "expectedSize");
        CollectPreconditions.checkNonnegative(size, "initialCapacity");
        Object[] objArr = new Object[size];
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            DeviceStateRotationLockSettingsManager.SettableDeviceState settableDeviceState =
                    (DeviceStateRotationLockSettingsManager.SettableDeviceState) arrayList.get(i2);
            if (settableDeviceState.mIsSettable) {
                DeviceStateAutoRotateSettingController deviceStateAutoRotateSettingController =
                        new DeviceStateAutoRotateSettingController(
                                context,
                                settableDeviceState.mDeviceState,
                                stringArray[i2],
                                (-size) + i2);
                int i3 = i + 1;
                if (objArr.length < i3) {
                    objArr =
                            Arrays.copyOf(
                                    objArr,
                                    ImmutableCollection.Builder.expandedCapacity(
                                            objArr.length, i3));
                }
                objArr[i] = deviceStateAutoRotateSettingController;
                i = i3;
            }
        }
        return ImmutableList.asImmutableList(i, objArr);
    }

    public static boolean isDeviceStateRotationEnabled(Context context) {
        return RotationPolicy.isRotationLockToggleVisible(context)
                && context.getResources().getStringArray(17236282).length > 0;
    }
}
