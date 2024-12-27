package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothUuid;
import android.os.ParcelUuid;
import android.util.Log;

import com.android.internal.util.ArrayUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BluetoothDeviceFilter {
    public static final AllFilter ALL_FILTER;
    public static final AllFilter BONDED_DEVICE_FILTER;
    public static final Filter[] FILTERS;
    public static final AllFilter RESTORED_DEVICE_FILTER;
    public static final AllFilter UNBONDED_DEVICE_FILTER;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Filter {
        boolean matches(BluetoothDevice bluetoothDevice);

        boolean matches(CachedBluetoothDevice cachedBluetoothDevice);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class HidFilter implements Filter {
        public final /* synthetic */ int $r8$classId;

        public /* synthetic */ HidFilter(int i) {
            this.$r8$classId = i;
        }

        @Override // com.android.settingslib.bluetooth.BluetoothDeviceFilter.Filter
        public final boolean matches(BluetoothDevice bluetoothDevice) {
            return matches(bluetoothDevice.getUuids(), bluetoothDevice.getBluetoothClass());
        }

        public final boolean matches(ParcelUuid[] parcelUuidArr, BluetoothClass bluetoothClass) {
            switch (this.$r8$classId) {
                case 0:
                    if (parcelUuidArr == null
                            || parcelUuidArr.length <= 0
                            || !ArrayUtils.contains(parcelUuidArr, BluetoothUuid.HID)) {
                        if (bluetoothClass == null || !bluetoothClass.doesClassMatch(3)) {}
                    }
                    break;
                case 1:
                    if (parcelUuidArr == null || parcelUuidArr.length <= 0) {
                        if (bluetoothClass == null
                                || !bluetoothClass.doesClassMatch(0)
                                || bluetoothClass.getMajorDeviceClass() == 1536) {}
                    } else if (!BluetoothUuid.containsAnyUuid(parcelUuidArr, HeadsetProfile.UUIDS)
                            && !ArrayUtils.contains(parcelUuidArr, BluetoothUuid.HEARING_AID)) {
                    }
                    break;
                case 2:
                    if (parcelUuidArr == null || parcelUuidArr.length <= 0) {
                        if (bluetoothClass == null
                                || !bluetoothClass.doesClassMatch(1)
                                || bluetoothClass.getMajorDeviceClass() == 1536) {}
                    } else if (BluetoothUuid.containsAnyUuid(
                            parcelUuidArr, A2dpProfile.SINK_UUIDS)) {
                    }
                    break;
                case 3:
                    if (parcelUuidArr == null || parcelUuidArr.length <= 0) {
                        if (bluetoothClass != null) {
                            if ((!bluetoothClass.doesClassMatch(1)
                                            && !bluetoothClass.doesClassMatch(0))
                                    || bluetoothClass.getMajorDeviceClass() == 1536) {}
                        }
                    } else if (!BluetoothUuid.containsAnyUuid(parcelUuidArr, A2dpProfile.SINK_UUIDS)
                            && !BluetoothUuid.containsAnyUuid(
                                    parcelUuidArr, HeadsetProfile.UUIDS)) {
                    }
                    break;
                case 4:
                    if (parcelUuidArr == null
                            || parcelUuidArr.length <= 0
                            || !ArrayUtils.contains(parcelUuidArr, BluetoothUuid.NAP)) {
                        if (bluetoothClass == null || !bluetoothClass.doesClassMatch(5)) {}
                    }
                    break;
                case 5:
                    if (parcelUuidArr == null
                            || parcelUuidArr.length <= 0
                            || !ArrayUtils.contains(parcelUuidArr, BluetoothUuid.PANU)) {
                        if (bluetoothClass == null || !bluetoothClass.doesClassMatch(4)) {}
                    }
                    break;
                default:
                    if (parcelUuidArr == null
                            || parcelUuidArr.length <= 0
                            || !ArrayUtils.contains(
                                    parcelUuidArr, BluetoothUuid.OBEX_OBJECT_PUSH)) {
                        if (bluetoothClass == null || !bluetoothClass.doesClassMatch(2)) {}
                    }
                    break;
            }
            return true;
        }

        @Override // com.android.settingslib.bluetooth.BluetoothDeviceFilter.Filter
        public final boolean matches(CachedBluetoothDevice cachedBluetoothDevice) {
            return matches(
                    cachedBluetoothDevice.mDevice.getUuids(),
                    cachedBluetoothDevice.mDevice.getBluetoothClass());
        }
    }

    static {
        AllFilter allFilter = new AllFilter(0);
        ALL_FILTER = allFilter;
        BONDED_DEVICE_FILTER = new AllFilter(1);
        UNBONDED_DEVICE_FILTER = new AllFilter(4);
        RESTORED_DEVICE_FILTER = new AllFilter(3);
        FILTERS =
                new Filter[] {
                    allFilter,
                    new HidFilter(3),
                    new HidFilter(6),
                    new HidFilter(5),
                    new HidFilter(4),
                    new HidFilter(1),
                    new HidFilter(2),
                    new HidFilter(0),
                    new AllFilter(2)
                };
    }

    public static Filter getFilter(int i) {
        int i2 = i == 105 ? 5 : i == 106 ? 6 : i;
        if (i2 >= 0 && i2 < 9) {
            return FILTERS[i2];
        }
        Log.w("BluetoothDeviceFilter", "Invalid filter type " + i + " for device picker");
        return ALL_FILTER;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AllFilter implements Filter {
        public final /* synthetic */ int $r8$classId;

        public /* synthetic */ AllFilter(int i) {
            this.$r8$classId = i;
        }

        public static boolean isHearingAidDevice(BluetoothDevice bluetoothDevice) {
            ParcelUuid[] uuids = bluetoothDevice.getUuids();
            ParcelUuid parcelUuid = BluetoothUuid.HEARING_AID;
            return ArrayUtils.contains(uuids, parcelUuid)
                    || ArrayUtils.contains(uuids, BluetoothUuid.HAS)
                    || ArrayUtils.contains(uuids, BluetoothUuid.MFI_HAS)
                    || ArrayUtils.contains(bluetoothDevice.getLeService16BitsUuidData(), parcelUuid)
                    || ArrayUtils.contains(
                            bluetoothDevice.getLeComplete128BitsUuidData(),
                            ParcelUuid.fromString("7d74f4bd-c74a-4431-862c-cce884371592"));
        }

        @Override // com.android.settingslib.bluetooth.BluetoothDeviceFilter.Filter
        public final boolean matches(BluetoothDevice bluetoothDevice) {
            switch (this.$r8$classId) {
                case 0:
                    return true;
                case 1:
                    return bluetoothDevice.getBondState() == 12;
                case 2:
                    return isHearingAidDevice(bluetoothDevice);
                case 3:
                    return false;
                default:
                    return bluetoothDevice.getBondState() != 12;
            }
        }

        @Override // com.android.settingslib.bluetooth.BluetoothDeviceFilter.Filter
        public final boolean matches(CachedBluetoothDevice cachedBluetoothDevice) {
            switch (this.$r8$classId) {
                case 0:
                    return true;
                case 1:
                    return cachedBluetoothDevice.mBondState == 12;
                case 2:
                    return isHearingAidDevice(cachedBluetoothDevice.mDevice);
                case 3:
                    return cachedBluetoothDevice.mIsRestored;
                default:
                    return cachedBluetoothDevice.mBondState != 12;
            }
        }
    }
}
