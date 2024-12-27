package com.samsung.android.settingslib.bluetooth;

import android.bluetooth.BluetoothManufacturerData;
import android.util.Log;

import androidx.core.text.PrecomputedTextCompat$Params$$ExternalSyntheticOutline0;

import com.android.settingslib.bluetooth.BluetoothUtils;

import com.att.iqi.lib.metrics.hw.HwConstants;

import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ManufacturerData {
    public final Data mData;
    public byte[] mManufacturerRawData = null;
    public int mManufacturerType = 0;
    public final SSdevice mSSdevice;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Data {
        public byte mBluetoothType;
        public byte[] mContactCrc;
        public byte[] mContactHash;
        public byte mDeviceCategory;
        public byte mDeviceIconIndex;
        public byte[] mDeviceId;
        public byte mDeviceStatus;
        public byte mDeviceSubType;
        public boolean mIsDeviceCategoryInitialized;
        public int mTxPower;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SSdevice {
        public SSdevice() {
            new ArrayList(
                    Arrays.asList(
                            "[Phone] ",
                            "[Tablet] ",
                            "[Wearable] ",
                            "[PC] ",
                            "[Accessory] ",
                            "[TV] ",
                            "[AV] ",
                            "[Signage] ",
                            "[Refrigerator] ",
                            "[Washer] ",
                            "[Dryer] ",
                            "[Floor A/C] ",
                            "[Room A/C] ",
                            "[System A/C] ",
                            "[Air Purifier] ",
                            "[Oven] ",
                            "[Range] ",
                            "[Robot Vacuum] ",
                            "[Smart Home] ",
                            "[Printer] ",
                            "[Headphone] ",
                            "[Speaker] ",
                            "[Monitor] ",
                            "[E-Board] ",
                            "[IoT] ",
                            "[Camera] ",
                            "[Camcorder] ",
                            "[Cooktop] ",
                            "[Dish Washer] ",
                            "[Microwave Oven] ",
                            "[Hood] ",
                            "[KimchiRef] ",
                            "[Watch] ",
                            "[Band] ",
                            "[Router] ",
                            "[BD] ",
                            "[Tag] ",
                            "[Car] ",
                            "[Airdresser] ",
                            "[AI Speaker] "));
        }
    }

    public ManufacturerData(byte[] bArr) {
        Data data = new Data();
        data.mIsDeviceCategoryInitialized = false;
        data.mContactHash = new byte[] {0, 0, 0};
        data.mContactCrc = new byte[] {0, 0};
        data.mDeviceId = new byte[] {0, 0};
        data.mTxPower = 0;
        data.mDeviceCategory = (byte) 0;
        data.mDeviceIconIndex = (byte) 0;
        data.mDeviceStatus = (byte) 0;
        data.mDeviceSubType = (byte) 0;
        data.mBluetoothType = (byte) 0;
        this.mData = data;
        this.mSSdevice = new SSdevice();
        updateDeviceInfo(bArr);
    }

    public static String byteToString(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b : bArr) {
            sb.append("0123456789abcdef".charAt((b & 240) >> 4));
            sb.append("0123456789abcdef".charAt(b & 15));
        }
        return sb.toString();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x003c, code lost:

       if (r4 != 1) goto L14;
    */
    /* JADX WARN: Code restructure failed: missing block: B:17:?, code lost:

       return com.android.settings.R.drawable.list_ic_the_wall;
    */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x0134, code lost:

       if (r4 != 3) goto L38;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getDeviceIcon() {
        /*
            Method dump skipped, instructions count: 500
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settingslib.bluetooth.ManufacturerData.getDeviceIcon():int");
    }

    public final boolean isSupportFeature(byte b) {
        byte[] bArr;
        try {
            if (this.mManufacturerType != 3 || (bArr = this.mManufacturerRawData) == null) {
                return false;
            }
            int length = bArr.length;
            int i = BluetoothManufacturerData.OFFSET_SS_LE_FEATURES;
            if (length > i) {
                return (bArr[i] & b) == b;
            }
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return false;
        }
    }

    public final void updateDeviceInfo(byte[] bArr) {
        int i;
        int i2;
        Data data = this.mData;
        if (bArr == null) {
            return;
        }
        this.mManufacturerRawData = bArr;
        if (bArr.length < 9) {
            this.mManufacturerType = 0;
        } else {
            try {
                int i3 = BluetoothManufacturerData.OFFSET_OLD_SERVICE_ID;
                if (bArr[i3] == 0 && bArr[i3 + 1] == 2) {
                    this.mManufacturerType = 1;
                } else if (bArr[BluetoothManufacturerData.OFFSET_SS_SERVICE_ID] == 9
                        && bArr[BluetoothManufacturerData.OFFSET_SS_ASSOCIATED_SERVICE_ID] == 0) {
                    this.mManufacturerType = 2;
                } else if (bArr[BluetoothManufacturerData.OFFSET_SS_SERVICE_ID] == 9
                        && bArr[BluetoothManufacturerData.OFFSET_SS_ASSOCIATED_SERVICE_ID] == 2) {
                    this.mManufacturerType = 3;
                    int i4 = BluetoothManufacturerData.OFFSET_SS_LE_FEATURES;
                    byte b = bArr[i4];
                    int i5 = i4 + 1;
                    for (int i6 = 0; i6 < 5; i6++) {
                        byte b2 = (byte) (((byte) (1 << i6)) & b);
                        if (b2 == 1) {
                            BluetoothManufacturerData.OFFSET_SS_LE_PACKET_NUMBER = i5;
                            i = BluetoothManufacturerData.LENGTH_SS_LE_PACKET_NUMBER;
                        } else if (b2 == 2) {
                            BluetoothManufacturerData.OFFSET_SS_LE_PROXIMITY_TYPE = i5;
                            i = BluetoothManufacturerData.LENGTH_SS_LE_PROXIMITY;
                        } else if (b2 == 4) {
                            BluetoothManufacturerData.OFFSET_SS_LE_DEVICE_TYPE = i5;
                            i = BluetoothManufacturerData.LENGTH_SS_LE_DEVICE;
                        } else if (b2 == 8) {
                            BluetoothManufacturerData.OFFSET_SS_LE_CONNECTIVITY_TYPE = i5;
                            i = BluetoothManufacturerData.LENGTH_SS_LE_CONNECTIVITY;
                        } else if (b2 == 16) {
                            BluetoothManufacturerData.OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_LENGTH =
                                    i5;
                            i = bArr[i5] + 1;
                            BluetoothManufacturerData.LENGTH_SS_LE_ASSOCIATED_SERVICE_DATA = i;
                        }
                        i5 += i;
                    }
                } else {
                    this.mManufacturerType = 0;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
                this.mManufacturerType = 0;
            }
        }
        try {
            int i7 = this.mManufacturerType;
            if (i7 != 2) {
                if (i7 != 3) {
                    data.mTxPower = 0;
                } else {
                    if (isSupportFeature((byte) 2)
                            && bArr.length
                                    > BluetoothManufacturerData.OFFSET_SS_LE_PROXIMITY_TYPE
                                            + BluetoothManufacturerData
                                                    .OFFSET_SS_LE_PROXIMITY_INFO) {
                        int i8 = BluetoothManufacturerData.OFFSET_SS_LE_PROXIMITY_TYPE;
                        if (bArr[i8] == 1) {
                            data.mTxPower =
                                    bArr[
                                            i8
                                                    + BluetoothManufacturerData
                                                            .OFFSET_SS_LE_PROXIMITY_INFO];
                        }
                    }
                    data.mTxPower = 0;
                }
            } else if (bArr.length <= BluetoothManufacturerData.OFFSET_SS_BREDR_PROXIMITY_INFO
                    || (bArr[BluetoothManufacturerData.OFFSET_SS_BREDR_PROXIMITY_TYPE] & 1) != 1) {
                data.mTxPower = 0;
            } else {
                data.mTxPower = bArr[BluetoothManufacturerData.OFFSET_SS_BREDR_PROXIMITY_INFO];
            }
        } catch (ArrayIndexOutOfBoundsException unused) {
            data.mTxPower = 0;
        }
        try {
            int i9 = this.mManufacturerType;
            if (i9 == 1) {
                int length = bArr.length;
                int i10 = BluetoothManufacturerData.OFFSET_OLD_DEVICE_TYPE;
                if (length > i10) {
                    byte b3 = bArr[i10];
                    data.mIsDeviceCategoryInitialized = true;
                    data.mDeviceCategory = b3;
                }
            } else if (i9 == 2) {
                int length2 = bArr.length;
                int i11 = BluetoothManufacturerData.OFFSET_SS_BREDR_DEVICE_TYPE;
                if (length2 > i11) {
                    byte b4 = bArr[i11];
                    data.mIsDeviceCategoryInitialized = true;
                    data.mDeviceCategory = b4;
                }
            } else if (i9 == 3 && isSupportFeature((byte) 4)) {
                int length3 = bArr.length;
                int i12 = BluetoothManufacturerData.OFFSET_SS_LE_DEVICE_TYPE;
                if (length3 > i12) {
                    byte b5 = bArr[i12];
                    data.mIsDeviceCategoryInitialized = true;
                    data.mDeviceCategory = b5;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e2) {
            e2.printStackTrace();
        }
        try {
            int i13 = this.mManufacturerType;
            if (i13 == 2) {
                int length4 = bArr.length;
                int i14 = BluetoothManufacturerData.OFFSET_SS_BREDR_DEVICE_ICON;
                if (length4 > i14) {
                    data.mDeviceIconIndex = bArr[i14];
                }
            } else if (i13 == 3
                    && isSupportFeature((byte) 4)
                    && bArr.length
                            > BluetoothManufacturerData.OFFSET_SS_LE_DEVICE_TYPE
                                    + BluetoothManufacturerData.OFFSET_SS_LE_DEVICE_ICON) {
                data.mDeviceIconIndex =
                        bArr[
                                BluetoothManufacturerData.OFFSET_SS_LE_DEVICE_TYPE
                                        + BluetoothManufacturerData.OFFSET_SS_LE_DEVICE_ICON];
            }
        } catch (ArrayIndexOutOfBoundsException e3) {
            e3.printStackTrace();
        }
        try {
            if (this.mManufacturerType == 3
                    && isSupportFeature((byte) 4)
                    && bArr.length
                            > BluetoothManufacturerData.OFFSET_SS_LE_DEVICE_TYPE
                                    + BluetoothManufacturerData.OFFSET_SS_LE_DEVICE_STATUS) {
                data.mDeviceStatus =
                        bArr[
                                BluetoothManufacturerData.OFFSET_SS_LE_DEVICE_TYPE
                                        + BluetoothManufacturerData.OFFSET_SS_LE_DEVICE_STATUS];
            }
        } catch (ArrayIndexOutOfBoundsException e4) {
            e4.printStackTrace();
        }
        try {
            if (this.mManufacturerType == 3
                    && isSupportFeature((byte) 4)
                    && bArr.length
                            > BluetoothManufacturerData.OFFSET_SS_LE_DEVICE_TYPE
                                    + BluetoothManufacturerData.OFFSET_SS_LE_DEVICE_SUBTYPE) {
                data.mDeviceSubType =
                        bArr[
                                BluetoothManufacturerData.OFFSET_SS_LE_DEVICE_TYPE
                                        + BluetoothManufacturerData.OFFSET_SS_LE_DEVICE_SUBTYPE];
            }
        } catch (ArrayIndexOutOfBoundsException e5) {
            e5.printStackTrace();
        }
        try {
            int i15 = this.mManufacturerType;
            if (i15 == 2) {
                System.arraycopy(
                        bArr,
                        BluetoothManufacturerData.OFFSET_SS_BREDR_DEVICE_CONTACT_HASH,
                        data.mContactHash,
                        0,
                        3);
            } else if (i15 == 3 && isSupportFeature((byte) 4)) {
                System.arraycopy(
                        bArr,
                        BluetoothManufacturerData.OFFSET_SS_LE_DEVICE_TYPE
                                + BluetoothManufacturerData.OFFSET_SS_LE_DEVICE_CONTACT_HASH,
                        data.mContactHash,
                        0,
                        3);
            }
        } catch (ArrayIndexOutOfBoundsException e6) {
            e6.printStackTrace();
        }
        try {
            int i16 = this.mManufacturerType;
            if (i16 == 2) {
                System.arraycopy(
                        bArr,
                        BluetoothManufacturerData.OFFSET_SS_BREDR_DEVICE_CONTACT_CRC,
                        data.mContactCrc,
                        0,
                        2);
            } else if (i16 == 3
                    && isSupportFeature(HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED)
                    && bArr.length
                            > BluetoothManufacturerData.OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_LENGTH
                                    + BluetoothManufacturerData
                                            .OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_CONTACT_CRC
                    && (bArr[
                                            BluetoothManufacturerData
                                                            .OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_LENGTH
                                                    + BluetoothManufacturerData
                                                            .OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_EXTRA]
                                    & 1)
                            == 1) {
                System.arraycopy(
                        bArr,
                        BluetoothManufacturerData.OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_LENGTH
                                + BluetoothManufacturerData
                                        .OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_CONTACT_CRC,
                        data.mContactCrc,
                        0,
                        2);
            }
        } catch (ArrayIndexOutOfBoundsException e7) {
            e7.printStackTrace();
        }
        try {
            int i17 = this.mManufacturerType;
            if (i17 == 1) {
                System.arraycopy(
                        bArr, BluetoothManufacturerData.OFFSET_OLD_DEVICE_ID, data.mDeviceId, 0, 2);
            } else if (i17 != 2) {
                if (i17 == 3 && isSupportFeature(HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED)) {
                    System.arraycopy(
                            bArr,
                            BluetoothManufacturerData.OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_LENGTH
                                    + BluetoothManufacturerData
                                            .OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_DEVICE_ID,
                            data.mDeviceId,
                            0,
                            2);
                }
            } else if (bArr.length
                    > BluetoothManufacturerData.OFFSET_SS_BREDR_ASSOCIATED_SERVICE_DATA
                            + BluetoothManufacturerData
                                    .OFFSET_SS_BREDR_ASSOCIATED_SERVICE_DATA_DEVICE_ID) {
                System.arraycopy(
                        bArr,
                        BluetoothManufacturerData.OFFSET_SS_BREDR_ASSOCIATED_SERVICE_DATA
                                + BluetoothManufacturerData
                                        .OFFSET_SS_BREDR_ASSOCIATED_SERVICE_DATA_DEVICE_ID,
                        data.mDeviceId,
                        0,
                        2);
            }
        } catch (ArrayIndexOutOfBoundsException e8) {
            e8.printStackTrace();
        }
        try {
            int i18 = this.mManufacturerType;
            if (i18 == 1) {
                byte[] bArr2 = data.mDeviceId;
                if (bArr2 != null && bArr2[0] == 0 && (i2 = bArr2[1] & 255) >= 144 && i2 <= 255) {
                    int length5 = this.mManufacturerRawData.length;
                    int i19 = BluetoothManufacturerData.OFFSET_OLD_BLUETOOTH_TYPE;
                    if (length5 > i19 && bArr.length > i19) {
                        data.mBluetoothType = bArr[i19];
                    }
                }
            } else if (i18 != 2) {
                if (i18 == 3
                        && isSupportFeature(HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED)
                        && bArr.length
                                > BluetoothManufacturerData
                                                .OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_LENGTH
                                        + BluetoothManufacturerData
                                                .OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_DEVICE_TYPE) {
                    data.mBluetoothType =
                            bArr[
                                    BluetoothManufacturerData
                                                    .OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_LENGTH
                                            + BluetoothManufacturerData
                                                    .OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_DEVICE_TYPE];
                }
            } else if (bArr.length
                    > BluetoothManufacturerData.OFFSET_SS_BREDR_ASSOCIATED_SERVICE_DATA
                            + BluetoothManufacturerData
                                    .OFFSET_SS_BREDR_ASSOCIATED_SERVICE_DATA_DEVICE_TYPE) {
                data.mBluetoothType =
                        bArr[
                                BluetoothManufacturerData.OFFSET_SS_BREDR_ASSOCIATED_SERVICE_DATA
                                        + BluetoothManufacturerData
                                                .OFFSET_SS_BREDR_ASSOCIATED_SERVICE_DATA_DEVICE_TYPE];
            }
        } catch (ArrayIndexOutOfBoundsException e9) {
            e9.printStackTrace();
        }
        if (BluetoothUtils.DEBUG) {
            StringBuilder sb = new StringBuilder("updateDeviceInfo :: describe data = ");
            StringBuilder sb2 = new StringBuilder();
            StringBuilder m =
                    PrecomputedTextCompat$Params$$ExternalSyntheticOutline0.m(
                            PrecomputedTextCompat$Params$$ExternalSyntheticOutline0.m(
                                    PrecomputedTextCompat$Params$$ExternalSyntheticOutline0.m(
                                            PrecomputedTextCompat$Params$$ExternalSyntheticOutline0
                                                    .m(
                                                            PrecomputedTextCompat$Params$$ExternalSyntheticOutline0
                                                                    .m(
                                                                            new StringBuilder(
                                                                                    "[ManufacturerType]"
                                                                                        + " "),
                                                                            this.mManufacturerType,
                                                                            sb2,
                                                                            ", [TxPower] "),
                                                            data.mTxPower,
                                                            sb2,
                                                            ", [DeviceCategory] "),
                                            data.mDeviceCategory,
                                            sb2,
                                            ", [DeviceIconIndex] "),
                                    data.mDeviceIconIndex,
                                    sb2,
                                    ", [DeviceStatus] "),
                            255 & data.mDeviceStatus,
                            sb2,
                            ", [DeviceSubType] ");
            m.append((int) data.mDeviceSubType);
            sb2.append(m.toString());
            sb2.append(", [DevicePrefix] ");
            sb2.append(
                    ", [Contact] "
                            + byteToString(data.mContactHash)
                            + byteToString(data.mContactCrc));
            StringBuilder sb3 = new StringBuilder(", [Device ID] ");
            sb3.append(byteToString(data.mDeviceId));
            sb2.append(sb3.toString());
            sb2.append(", [BT Type] " + ((int) data.mBluetoothType));
            sb.append(sb2.toString());
            Log.d("ManufacturerData", sb.toString());
        }
    }
}
