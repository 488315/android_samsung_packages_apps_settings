package com.android.net.module.util;

import android.text.TextUtils;

import com.android.net.module.annotation.NonNull;
import com.android.net.module.annotation.VisibleForTesting;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class DnsPacket$DnsRecord {
    public final String dName;
    public final byte[] mRdata;
    public final int nsClass;
    public final int nsType;
    public final int rType;
    public final long ttl;

    public DnsPacket$DnsRecord(int i, ByteBuffer byteBuffer) {
        Objects.requireNonNull(byteBuffer);
        this.rType = i;
        String parseName = DnsPacketUtils$DnsRecordParser.parseName(byteBuffer, 0, true);
        this.dName = parseName;
        if (parseName.length() > 255) {
            throw new DnsPacket$ParseException(
                    "Parse name fail, name size is too long: " + parseName.length());
        }
        this.nsType = Short.toUnsignedInt(byteBuffer.getShort());
        this.nsClass = Short.toUnsignedInt(byteBuffer.getShort());
        if (i == 0) {
            this.ttl = 0L;
            this.mRdata = null;
        } else {
            this.ttl = Integer.toUnsignedLong(byteBuffer.getInt());
            byte[] bArr = new byte[Short.toUnsignedInt(byteBuffer.getShort())];
            this.mRdata = bArr;
            byteBuffer.get(bArr);
        }
    }

    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PRIVATE)
    public static DnsPacket$DnsRecord parse(int i, @NonNull ByteBuffer byteBuffer)
            throws BufferUnderflowException, DnsPacket$ParseException {
        Objects.requireNonNull(byteBuffer);
        int position = byteBuffer.position();
        DnsPacketUtils$DnsRecordParser.parseName(byteBuffer, 0, true);
        int unsignedInt = Short.toUnsignedInt(byteBuffer.getShort());
        byteBuffer.position(position);
        return unsignedInt != 64
                ? new DnsPacket$DnsRecord(i, byteBuffer)
                : new DnsSvcbRecord(i, byteBuffer);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        DnsPacket$DnsRecord dnsPacket$DnsRecord = (DnsPacket$DnsRecord) obj;
        return this.rType == dnsPacket$DnsRecord.rType
                && this.nsType == dnsPacket$DnsRecord.nsType
                && this.nsClass == dnsPacket$DnsRecord.nsClass
                && this.ttl == dnsPacket$DnsRecord.ttl
                && TextUtils.equals(this.dName, dnsPacket$DnsRecord.dName)
                && Arrays.equals(this.mRdata, dnsPacket$DnsRecord.mRdata);
    }

    public final int hashCode() {
        int hash = Objects.hash(this.dName) * 31;
        long j = this.ttl;
        return Arrays.hashCode(this.mRdata)
                + (this.rType * 53)
                + (this.nsClass * 47)
                + (this.nsType * 43)
                + (((int) (j >> 32)) * 41)
                + (((int) j) * 37)
                + hash;
    }

    public String toString() {
        return "DnsRecord{rType="
                + this.rType
                + ", dName='"
                + this.dName
                + "', nsType="
                + this.nsType
                + ", nsClass="
                + this.nsClass
                + ", ttl="
                + this.ttl
                + ", mRdata="
                + Arrays.toString(this.mRdata)
                + '}';
    }
}
