package com.android.net.module.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public abstract class NetUtils {
    public static InetAddress getNetworkPart(InetAddress inetAddress, int i) {
        byte[] address = inetAddress.getAddress();
        if (i < 0 || i > address.length * 8) {
            throw new RuntimeException(
                    "IP address with " + address.length + " bytes has invalid prefix length " + i);
        }
        int i2 = i / 8;
        byte b = (byte) (255 << (8 - (i % 8)));
        if (i2 < address.length) {
            address[i2] = (byte) (b & address[i2]);
        }
        while (true) {
            i2++;
            if (i2 >= address.length) {
                try {
                    return InetAddress.getByAddress(address);
                } catch (UnknownHostException e) {
                    throw new RuntimeException("getNetworkPart error - " + e.toString());
                }
            }
            address[i2] = 0;
        }
    }
}
