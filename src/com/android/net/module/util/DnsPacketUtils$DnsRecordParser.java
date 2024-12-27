package com.android.net.module.util;

import android.text.TextUtils;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.apppickerview.features.applabel.AbstractAppLabelMapFactory$$ExternalSyntheticOutline0;

import com.android.net.module.annotation.NonNull;
import com.android.net.module.annotation.VisibleForTesting;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.FieldPosition;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public abstract class DnsPacketUtils$DnsRecordParser {
    public static final DecimalFormat sByteFormat = new DecimalFormat();
    public static final FieldPosition sPos = new FieldPosition(0);

    @VisibleForTesting
    public static String labelToString(@NonNull byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bArr) {
            int unsignedInt = Byte.toUnsignedInt(b);
            if (unsignedInt <= 32 || unsignedInt >= 127) {
                stringBuffer.append('\\');
                sByteFormat.format(unsignedInt, stringBuffer, sPos);
            } else if (unsignedInt == 34
                    || unsignedInt == 46
                    || unsignedInt == 59
                    || unsignedInt == 92
                    || unsignedInt == 40
                    || unsignedInt == 41
                    || unsignedInt == 64
                    || unsignedInt == 36) {
                stringBuffer.append('\\');
                stringBuffer.append((char) unsignedInt);
            } else {
                stringBuffer.append((char) unsignedInt);
            }
        }
        return stringBuffer.toString();
    }

    public static String parseName(ByteBuffer byteBuffer, int i, boolean z) {
        if (i > 128) {
            throw new DnsPacket$ParseException("Failed to parse name, too many labels");
        }
        int unsignedInt = Byte.toUnsignedInt(byteBuffer.get());
        int i2 = unsignedInt & 192;
        if (unsignedInt == 0) {
            return ApnSettings.MVNO_NONE;
        }
        if ((i2 != 0 && i2 != 192) || (!z && i2 == 192)) {
            throw new DnsPacket$ParseException(
                    SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                            i2, "Parse name fail, bad label type: "));
        }
        if (i2 != 192) {
            byte[] bArr = new byte[unsignedInt];
            byteBuffer.get(bArr);
            String labelToString = labelToString(bArr);
            if (labelToString.length() > 63) {
                throw new DnsPacket$ParseException("Parse name fail, invalid label length");
            }
            String parseName = parseName(byteBuffer, i + 1, z);
            return TextUtils.isEmpty(parseName)
                    ? labelToString
                    : AbstractAppLabelMapFactory$$ExternalSyntheticOutline0.m(
                            labelToString, ".", parseName);
        }
        int unsignedInt2 = Byte.toUnsignedInt(byteBuffer.get()) + ((unsignedInt & (-193)) << 8);
        int position = byteBuffer.position();
        if (unsignedInt2 >= position - 2) {
            throw new DnsPacket$ParseException("Parse compression name fail, invalid compression");
        }
        byteBuffer.position(unsignedInt2);
        String parseName2 = parseName(byteBuffer, i + 1, z);
        byteBuffer.position(position);
        return parseName2;
    }
}
