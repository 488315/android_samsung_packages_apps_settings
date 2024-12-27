package com.samsung.android.settings.wifi.develop.diagnosis.packetUtils;

import android.net.LinkProperties;
import android.net.util.SocketUtils;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.Log;

import com.android.settingslib.qrcode.QrCamera$DecodingTask$$ExternalSyntheticOutline0;

import java.io.FileDescriptor;
import java.io.IOException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ArpPeer {
    public static final byte[] ETHER_ARP_TYPE = {8, 6};
    public byte[] L2_BROADCAST;
    public FileDescriptor mSocketGArp;

    /* renamed from: -$$Nest$mmacStringToByteArray, reason: not valid java name */
    public static byte[] m1334$$Nest$mmacStringToByteArray(ArpPeer arpPeer, String str) {
        arpPeer.getClass();
        byte[] bArr = new byte[6];
        if (str != null) {
            for (int i = 0; i < 6; i++) {
                int i2 = i * 3;
                bArr[i] = (byte) Integer.parseInt(str.substring(i2, i2 + 2), 16);
            }
        }
        return bArr;
    }

    /* renamed from: -$$Nest$mmakeEthernet, reason: not valid java name */
    public static void m1335$$Nest$mmakeEthernet(
            ArpPeer arpPeer, ByteBuffer byteBuffer, byte[] bArr, byte[] bArr2, byte[] bArr3) {
        arpPeer.getClass();
        if (bArr != null) {
            Log.d("ArpPeer", "makeEthernet");
            byteBuffer.put(bArr);
            byteBuffer.put(bArr2);
            byteBuffer.put(bArr3);
            byteBuffer.flip();
        }
    }

    /* renamed from: -$$Nest$mmakeGARP, reason: not valid java name */
    public static void m1336$$Nest$mmakeGARP(
            ArpPeer arpPeer, ByteBuffer byteBuffer, byte[] bArr, byte[] bArr2) {
        arpPeer.getClass();
        if (bArr2 == null) {
            return;
        }
        Log.d("ArpPeer", "makeGARP");
        byteBuffer.putShort((short) 1);
        byteBuffer.putShort((short) OsConstants.ETH_P_IP);
        byteBuffer.put((byte) 6);
        byteBuffer.put((byte) 4);
        byteBuffer.putShort((short) 1);
        byteBuffer.put(bArr);
        byteBuffer.put(bArr2);
        byteBuffer.put(arpPeer.L2_BROADCAST);
        byteBuffer.put(bArr2);
        byteBuffer.flip();
    }

    public final void sendGArp(
            final LinkProperties linkProperties, final byte[] bArr, final String str) {
        Log.d("ArpPeer", "sendGarp");
        Log.d("ArpPeer", "myMac=" + str + "     mMyAddr=" + bArr);
        new Thread(
                        new Runnable() { // from class:
                                         // com.samsung.android.settings.wifi.develop.diagnosis.packetUtils.ArpPeer.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                NetworkInterface networkInterface;
                                try {
                                    try {
                                        try {
                                            try {
                                                Log.d("ArpPeer", "socket create");
                                                ArpPeer.this.mSocketGArp =
                                                        Os.socket(
                                                                OsConstants.AF_PACKET,
                                                                OsConstants.SOCK_RAW,
                                                                0);
                                                Log.d("ArpPeer", "getInterfaceName");
                                                try {
                                                    networkInterface =
                                                            NetworkInterface.getByName(
                                                                    linkProperties
                                                                            .getInterfaceName());
                                                } catch (NullPointerException
                                                        | SocketException unused) {
                                                    networkInterface = null;
                                                }
                                                int index =
                                                        networkInterface == null
                                                                ? -1
                                                                : networkInterface.getIndex();
                                                if (index <= 0) {
                                                    throw new IllegalArgumentException(
                                                            "invalid interface index");
                                                }
                                                Log.d("ArpPeer", "makePacketSocketAddress");
                                                Os.bind(
                                                        ArpPeer.this.mSocketGArp,
                                                        SocketUtils.makePacketSocketAddress(
                                                                (short) OsConstants.ETH_P_IP,
                                                                index));
                                                Log.d("ArpPeer", "macStringToByteArray");
                                                byte[] m1334$$Nest$mmacStringToByteArray =
                                                        ArpPeer.m1334$$Nest$mmacStringToByteArray(
                                                                ArpPeer.this, str);
                                                Log.d("ArpPeer", "ByteBuffer allocate");
                                                ByteBuffer allocate = ByteBuffer.allocate(1500);
                                                ByteBuffer allocate2 = ByteBuffer.allocate(14);
                                                ByteBuffer allocate3 = ByteBuffer.allocate(28);
                                                allocate.clear();
                                                ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
                                                allocate.order(byteOrder);
                                                allocate2.clear();
                                                allocate2.order(byteOrder);
                                                allocate3.clear();
                                                allocate3.order(byteOrder);
                                                Log.d("ArpPeer", "makeEthernet");
                                                ArpPeer arpPeer = ArpPeer.this;
                                                ArpPeer.m1335$$Nest$mmakeEthernet(
                                                        arpPeer,
                                                        allocate2,
                                                        arpPeer.L2_BROADCAST,
                                                        m1334$$Nest$mmacStringToByteArray,
                                                        ArpPeer.ETHER_ARP_TYPE);
                                                ArpPeer.m1336$$Nest$mmakeGARP(
                                                        ArpPeer.this,
                                                        allocate3,
                                                        m1334$$Nest$mmacStringToByteArray,
                                                        bArr);
                                                Log.d(
                                                        "ArpPeer",
                                                        "mMyMac="
                                                                + m1334$$Nest$mmacStringToByteArray
                                                                + "     mMyAddr="
                                                                + bArr);
                                                allocate.put(allocate2).put(allocate3);
                                                allocate.flip();
                                                Os.sendto(
                                                        ArpPeer.this.mSocketGArp,
                                                        allocate.array(),
                                                        0,
                                                        allocate.limit(),
                                                        0,
                                                        SocketUtils.makePacketSocketAddress(
                                                                index, ArpPeer.this.L2_BROADCAST));
                                                FileDescriptor fileDescriptor =
                                                        ArpPeer.this.mSocketGArp;
                                                if (fileDescriptor != null) {
                                                    SocketUtils.closeSocket(fileDescriptor);
                                                    ArpPeer.this.mSocketGArp = null;
                                                }
                                            } catch (RuntimeException e) {
                                                Log.e("ArpPeer", "RuntimeException " + e);
                                                FileDescriptor fileDescriptor2 =
                                                        ArpPeer.this.mSocketGArp;
                                                if (fileDescriptor2 != null) {
                                                    SocketUtils.closeSocket(fileDescriptor2);
                                                    ArpPeer.this.mSocketGArp = null;
                                                }
                                            }
                                        } catch (Exception e2) {
                                            Throwable cause = e2.getCause();
                                            if ((cause instanceof ErrnoException)
                                                    && ((ErrnoException) cause).errno
                                                            != OsConstants.EAGAIN) {
                                                Log.e(
                                                        "ArpPeer",
                                                        "Exception "
                                                                + Thread.currentThread()
                                                                        .getStackTrace()[2]
                                                                        .getLineNumber()
                                                                + e2);
                                            }
                                            FileDescriptor fileDescriptor3 =
                                                    ArpPeer.this.mSocketGArp;
                                            if (fileDescriptor3 != null) {
                                                SocketUtils.closeSocket(fileDescriptor3);
                                                ArpPeer.this.mSocketGArp = null;
                                            }
                                        }
                                    } catch (IOException e3) {
                                        QrCamera$DecodingTask$$ExternalSyntheticOutline0.m(
                                                "IOException ", e3, "ArpPeer");
                                    }
                                } catch (Throwable th) {
                                    try {
                                        FileDescriptor fileDescriptor4 = ArpPeer.this.mSocketGArp;
                                        if (fileDescriptor4 != null) {
                                            SocketUtils.closeSocket(fileDescriptor4);
                                            ArpPeer.this.mSocketGArp = null;
                                        }
                                    } catch (IOException e4) {
                                        QrCamera$DecodingTask$$ExternalSyntheticOutline0.m(
                                                "IOException ", e4, "ArpPeer");
                                    }
                                    throw th;
                                }
                            }
                        })
                .start();
    }
}
