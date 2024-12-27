package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothLeAudioContentMetadata;
import android.bluetooth.BluetoothLeBroadcastChannel;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.bluetooth.BluetoothLeBroadcastSubgroup;
import android.util.Base64;
import android.util.Log;

import androidx.compose.runtime.ComposerKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.SaversKt$LocaleSaver$2$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Pair;
import kotlin.UInt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt___StringsKt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BluetoothLeBroadcastMetadataExt {
    public static BluetoothLeBroadcastMetadata convertToBroadcastMetadata(String qrCodeString) {
        Intrinsics.checkNotNullParameter(qrCodeString, "qrCodeString");
        if (!StringsKt__StringsJVMKt.startsWith$default(qrCodeString, "BLUETOOTH:UUID:184F;")) {
            SaversKt$LocaleSaver$2$$ExternalSyntheticOutline0.m(
                    "String \"",
                    qrCodeString,
                    "\" does not begin with \"BLUETOOTH:UUID:184F;\"",
                    "BtLeBroadcastMetadataExt");
            return null;
        }
        try {
            Log.d("BtLeBroadcastMetadataExt", "Parsing QR string: ".concat(qrCodeString));
            String removeSuffix =
                    StringsKt___StringsKt.removeSuffix(
                            StringsKt___StringsKt.removePrefix(
                                    qrCodeString, "BLUETOOTH:UUID:184F;"),
                            ";;");
            Log.d("BtLeBroadcastMetadataExt", "Stripped to: ".concat(removeSuffix));
            return parseQrCodeToMetadata(removeSuffix);
        } catch (Exception e) {
            Log.w("BtLeBroadcastMetadataExt", "Cannot parse: ".concat(qrCodeString), e);
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:217:0x0435, code lost:

       if (r12.data != (-1)) goto L171;
    */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:192:0x04a4  */
    /* JADX WARN: Removed duplicated region for block: B:196:0x04bd A[LOOP:4: B:194:0x04b7->B:196:0x04bd, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:200:0x04d0  */
    /* JADX WARN: Removed duplicated region for block: B:203:0x04d2  */
    /* JADX WARN: Removed duplicated region for block: B:205:0x04a8  */
    /* JADX WARN: Type inference failed for: r0v16, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r0v17 */
    /* JADX WARN: Type inference failed for: r0v24, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r9v11 */
    /* JADX WARN: Type inference failed for: r9v12, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r9v13 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.bluetooth.BluetoothLeBroadcastMetadata parseQrCodeToMetadata(
            java.lang.String r32) {
        /*
            Method dump skipped, instructions count: 1450
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.bluetooth.BluetoothLeBroadcastMetadataExt.parseQrCodeToMetadata(java.lang.String):android.bluetooth.BluetoothLeBroadcastMetadata");
    }

    public static String toQrCodeString(BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
        byte[] bArr;
        BluetoothLeAudioContentMetadata publicBroadcastMetadata;
        byte[] rawMetadata;
        ArrayList arrayList = new ArrayList();
        if (bluetoothLeBroadcastMetadata.getBroadcastName() == null) {
            throw new IllegalArgumentException(
                    "Broadcast name is mandatory for QR code".toString());
        }
        String broadcastName = bluetoothLeBroadcastMetadata.getBroadcastName();
        if (broadcastName != null) {
            bArr = broadcastName.getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bArr, "getBytes(...)");
        } else {
            bArr = null;
        }
        arrayList.add(new Pair("BN", Base64.encodeToString(bArr, 2)));
        arrayList.add(
                new Pair(
                        "AT", String.valueOf(bluetoothLeBroadcastMetadata.getSourceAddressType())));
        String address = bluetoothLeBroadcastMetadata.getSourceDevice().getAddress();
        Intrinsics.checkNotNullExpressionValue(address, "getAddress(...)");
        arrayList.add(
                new Pair(
                        "AD",
                        StringsKt__StringsJVMKt.replace$default(
                                address, ":", ApnSettings.MVNO_NONE)));
        arrayList.add(
                new Pair(
                        "BI",
                        String.format(
                                "%X",
                                Arrays.copyOf(
                                        new Object[] {
                                            Long.valueOf(
                                                    bluetoothLeBroadcastMetadata.getBroadcastId())
                                        },
                                        1))));
        if (bluetoothLeBroadcastMetadata.getBroadcastCode() != null) {
            arrayList.add(
                    new Pair(
                            "BC",
                            Base64.encodeToString(
                                    bluetoothLeBroadcastMetadata.getBroadcastCode(), 2)));
        }
        if (bluetoothLeBroadcastMetadata.getPublicBroadcastMetadata() != null
                && ((publicBroadcastMetadata =
                                        bluetoothLeBroadcastMetadata.getPublicBroadcastMetadata())
                                == null
                        || (rawMetadata = publicBroadcastMetadata.getRawMetadata()) == null
                        || rawMetadata.length != 0)) {
            BluetoothLeAudioContentMetadata publicBroadcastMetadata2 =
                    bluetoothLeBroadcastMetadata.getPublicBroadcastMetadata();
            arrayList.add(
                    new Pair(
                            "MD",
                            Base64.encodeToString(
                                    publicBroadcastMetadata2 != null
                                            ? publicBroadcastMetadata2.getRawMetadata()
                                            : null,
                                    2)));
        }
        if ((bluetoothLeBroadcastMetadata.getAudioConfigQuality() & 1) != 0) {
            arrayList.add(new Pair("SQ", "1"));
        }
        if ((bluetoothLeBroadcastMetadata.getAudioConfigQuality() & 2) != 0) {
            arrayList.add(new Pair("HQ", "1"));
        }
        arrayList.add(
                new Pair(
                        "AS",
                        String.format(
                                "%X",
                                Arrays.copyOf(
                                        new Object[] {
                                            Long.valueOf(
                                                    bluetoothLeBroadcastMetadata
                                                            .getSourceAdvertisingSid())
                                        },
                                        1))));
        arrayList.add(
                new Pair(
                        "PI",
                        String.format(
                                "%X",
                                Arrays.copyOf(
                                        new Object[] {
                                            Long.valueOf(
                                                    bluetoothLeBroadcastMetadata
                                                            .getPaSyncInterval())
                                        },
                                        1))));
        arrayList.add(
                new Pair(
                        "NS",
                        String.format(
                                "%X",
                                Arrays.copyOf(
                                        new Object[] {
                                            Long.valueOf(
                                                    bluetoothLeBroadcastMetadata
                                                            .getSubgroups()
                                                            .size())
                                        },
                                        1))));
        List<BluetoothLeBroadcastSubgroup> subgroups = bluetoothLeBroadcastMetadata.getSubgroups();
        Intrinsics.checkNotNullExpressionValue(subgroups, "getSubgroups(...)");
        for (BluetoothLeBroadcastSubgroup bluetoothLeBroadcastSubgroup : subgroups) {
            List<BluetoothLeBroadcastChannel> channels = bluetoothLeBroadcastSubgroup.getChannels();
            Intrinsics.checkNotNullExpressionValue(channels, "getChannels(...)");
            int i = 0;
            int i2 = 0;
            for (BluetoothLeBroadcastChannel bluetoothLeBroadcastChannel : channels) {
                if (bluetoothLeBroadcastChannel.getChannelIndex() > 0) {
                    i2++;
                    if (bluetoothLeBroadcastChannel.isSelected()) {
                        i |= 1 << (bluetoothLeBroadcastChannel.getChannelIndex() - 1);
                    }
                }
            }
            Pair pair =
                    i == 0
                            ? new Pair(new UInt(-1), new UInt(i2))
                            : new Pair(new UInt(i), new UInt(i2));
            int i3 = ((UInt) pair.getFirst()).data;
            int i4 = ((UInt) pair.getSecond()).data;
            arrayList.add(
                    new Pair(
                            "BS",
                            String.format(
                                    "%X",
                                    Arrays.copyOf(
                                            new Object[] {Long.valueOf(i3 & 4294967295L)}, 1))));
            if (Integer.compareUnsigned(i4, 0) > 0) {
                arrayList.add(
                        new Pair(
                                "NB",
                                String.format(
                                        "%X",
                                        Arrays.copyOf(
                                                new Object[] {Long.valueOf(i4 & 4294967295L)},
                                                1))));
            }
            if (bluetoothLeBroadcastSubgroup.getContentMetadata().getRawMetadata().length != 0) {
                arrayList.add(
                        new Pair(
                                "SM",
                                Base64.encodeToString(
                                        bluetoothLeBroadcastSubgroup
                                                .getContentMetadata()
                                                .getRawMetadata(),
                                        2)));
            }
        }
        ArrayList arrayList2 =
                new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Pair pair2 = (Pair) it.next();
            arrayList2.add(pair2.getFirst() + ":" + pair2.getSecond());
        }
        String m =
                ComposerKt$$ExternalSyntheticOutline0.m(
                        "BLUETOOTH:UUID:184F;",
                        CollectionsKt___CollectionsKt.joinToString$default(
                                arrayList2, ";", null, null, null, 62),
                        ";;");
        DialogFragment$$ExternalSyntheticOutline0.m(
                "Generated QR string : ", m, "BtLeBroadcastMetadataExt");
        return m;
    }
}
