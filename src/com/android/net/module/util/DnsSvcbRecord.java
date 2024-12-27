package com.android.net.module.util;

import android.text.TextUtils;
import android.util.SparseArray;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0;

import com.android.net.module.annotation.NonNull;
import com.android.net.module.annotation.VisibleForTesting;

import com.samsung.android.knox.accounts.HostAuth;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
/* loaded from: classes.dex */
public final class DnsSvcbRecord extends DnsPacket$DnsRecord {
    public final SparseArray mAllSvcParams;
    public final int mSvcPriority;
    public final String mTargetName;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class SvcParam {
        public final int mKey;

        public SvcParam(int i) {
            this.mKey = i;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SvcParamAlpn extends SvcParam {
        public final List mValue;

        public SvcParamAlpn(ByteBuffer byteBuffer) {
            super(1);
            ByteBuffer sliceAndAdvance =
                    DnsSvcbRecord.sliceAndAdvance(
                            byteBuffer, Short.toUnsignedInt(byteBuffer.getShort()));
            ArrayList arrayList = new ArrayList();
            while (sliceAndAdvance.hasRemaining()) {
                int unsignedInt = Byte.toUnsignedInt(sliceAndAdvance.get());
                if (unsignedInt == 0) {
                    throw new DnsPacket$ParseException("alpn should not be an empty string");
                }
                byte[] bArr = new byte[unsignedInt];
                sliceAndAdvance.get(bArr);
                arrayList.add(new String(bArr, StandardCharsets.UTF_8));
            }
            this.mValue = arrayList;
            if (arrayList.isEmpty()) {
                throw new DnsPacket$ParseException("alpn value must be non-empty");
            }
        }

        public final String toString() {
            return DnsSvcbRecord.m647$$Nest$smtoKeyName(this.mKey)
                    + "="
                    + TextUtils.join(",", this.mValue);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SvcParamDohPath extends SvcParam {
        public final String mValue;

        public SvcParamDohPath(ByteBuffer byteBuffer) {
            super(7);
            byte[] bArr = new byte[Short.toUnsignedInt(byteBuffer.getShort())];
            byteBuffer.get(bArr);
            this.mValue = new String(bArr, StandardCharsets.UTF_8);
        }

        public final String toString() {
            return DnsSvcbRecord.m647$$Nest$smtoKeyName(this.mKey) + "=" + this.mValue;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SvcParamEch extends SvcParamGeneric {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SvcParamIpv4Hint extends SvcParam {
        public final List mValue;

        public SvcParamIpv4Hint(int i, ByteBuffer byteBuffer, int i2) {
            super(i);
            ByteBuffer sliceAndAdvance =
                    DnsSvcbRecord.sliceAndAdvance(
                            byteBuffer, Short.toUnsignedInt(byteBuffer.getShort()));
            if (sliceAndAdvance.remaining() % i2 != 0) {
                throw new DnsPacket$ParseException("Can't parse whole byte array");
            }
            ArrayList arrayList = new ArrayList();
            byte[] bArr = new byte[i2];
            while (sliceAndAdvance.remaining() >= i2) {
                sliceAndAdvance.get(bArr);
                try {
                    arrayList.add(InetAddress.getByAddress(bArr));
                } catch (UnknownHostException unused) {
                    throw new DnsPacket$ParseException("Can't parse byte array as an IP address");
                }
            }
            this.mValue = arrayList;
            if (arrayList.isEmpty()) {
                throw new DnsPacket$ParseException(
                        DnsSvcbRecord.m647$$Nest$smtoKeyName(this.mKey)
                                + " value must be non-empty");
            }
        }

        public final String toString() {
            StringJoiner stringJoiner = new StringJoiner(",");
            Iterator it = ((ArrayList) this.mValue).iterator();
            while (it.hasNext()) {
                stringJoiner.add(((InetAddress) it.next()).getHostAddress());
            }
            return DnsSvcbRecord.m647$$Nest$smtoKeyName(this.mKey) + "=" + stringJoiner.toString();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SvcParamNoDefaultAlpn extends SvcParam {
        public final String toString() {
            return DnsSvcbRecord.m647$$Nest$smtoKeyName(this.mKey);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SvcParamPort extends SvcParam {
        public final int mValue;

        public SvcParamPort(ByteBuffer byteBuffer) {
            super(3);
            short s = byteBuffer.getShort();
            if (s != 2) {
                throw new DnsPacket$ParseException(
                        SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                s, "key port len is not 2 but "));
            }
            this.mValue = Short.toUnsignedInt(byteBuffer.getShort());
        }

        public final String toString() {
            return DnsSvcbRecord.m647$$Nest$smtoKeyName(this.mKey) + "=" + this.mValue;
        }
    }

    /* renamed from: -$$Nest$smtoKeyName, reason: not valid java name */
    public static String m647$$Nest$smtoKeyName(int i) {
        switch (i) {
            case 0:
                return "mandatory";
            case 1:
                return "alpn";
            case 2:
                return "no-default-alpn";
            case 3:
                return HostAuth.PORT;
            case 4:
                return "ipv4hint";
            case 5:
                return "ech";
            case 6:
                return "ipv6hint";
            case 7:
                return "dohpath";
            default:
                return SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                        i, GoodSettingsContract.EXTRA_NAME.POLICY_KEY);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x00b4 A[LOOP:0: B:16:0x004d->B:24:0x00b4, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00ba A[SYNTHETIC] */
    @com.android.net.module.annotation.VisibleForTesting(
            visibility = com.android.net.module.annotation.VisibleForTesting.Visibility.PACKAGE)
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public DnsSvcbRecord(int r3, @com.android.net.module.annotation.NonNull java.nio.ByteBuffer r4)
            throws java.lang.IllegalStateException,
                    com.android.net.module.util.DnsPacket$ParseException {
        /*
            Method dump skipped, instructions count: 342
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.android.net.module.util.DnsSvcbRecord.<init>(int,"
                    + " java.nio.ByteBuffer):void");
    }

    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PRIVATE)
    public static ByteBuffer sliceAndAdvance(@NonNull ByteBuffer byteBuffer, int i)
            throws BufferUnderflowException {
        if (byteBuffer.remaining() < i) {
            throw new BufferUnderflowException();
        }
        int position = byteBuffer.position();
        ByteBuffer slice = ((ByteBuffer) byteBuffer.slice().limit(i)).slice();
        byteBuffer.position(position + i);
        return slice.asReadOnlyBuffer();
    }

    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
    @NonNull
    public List<InetAddress> getAddresses() {
        ArrayList arrayList = new ArrayList();
        SvcParamIpv4Hint svcParamIpv4Hint = (SvcParamIpv4Hint) this.mAllSvcParams.get(4);
        if (svcParamIpv4Hint != null) {
            arrayList.addAll(Collections.unmodifiableList(svcParamIpv4Hint.mValue));
        }
        SvcParamIpv4Hint svcParamIpv4Hint2 = (SvcParamIpv4Hint) this.mAllSvcParams.get(6);
        if (svcParamIpv4Hint2 != null) {
            arrayList.addAll(Collections.unmodifiableList(svcParamIpv4Hint2.mValue));
        }
        return arrayList;
    }

    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
    @NonNull
    public List<String> getAlpns() {
        SvcParamAlpn svcParamAlpn = (SvcParamAlpn) this.mAllSvcParams.get(1);
        return Collections.unmodifiableList(
                svcParamAlpn != null
                        ? Collections.unmodifiableList(svcParamAlpn.mValue)
                        : Collections.EMPTY_LIST);
    }

    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
    @NonNull
    public String getDohPath() {
        SvcParamDohPath svcParamDohPath = (SvcParamDohPath) this.mAllSvcParams.get(7);
        return svcParamDohPath != null ? svcParamDohPath.mValue : ApnSettings.MVNO_NONE;
    }

    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
    public int getPort() {
        SvcParamPort svcParamPort = (SvcParamPort) this.mAllSvcParams.get(3);
        if (svcParamPort != null) {
            return svcParamPort.mValue;
        }
        return -1;
    }

    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
    @NonNull
    public String getTargetName() {
        return this.mTargetName;
    }

    @Override // com.android.net.module.util.DnsPacket$DnsRecord
    public final String toString() {
        int i = this.rType;
        String str = this.dName;
        if (i == 0) {
            return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, " IN SVCB");
        }
        StringJoiner stringJoiner = new StringJoiner(" ");
        for (int i2 = 0; i2 < this.mAllSvcParams.size(); i2++) {
            stringJoiner.add(((SvcParam) this.mAllSvcParams.valueAt(i2)).toString());
        }
        StringBuilder m =
                PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(str, " ");
        m.append(this.ttl);
        m.append(" IN SVCB ");
        m.append(this.mSvcPriority);
        m.append(" ");
        m.append(this.mTargetName);
        m.append(" ");
        m.append(stringJoiner.toString());
        return m.toString();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class SvcParamGeneric extends SvcParam {
        public final /* synthetic */ int $r8$classId = 0;
        public final Object mValue;

        public SvcParamGeneric(ByteBuffer byteBuffer) {
            super(0);
            ByteBuffer sliceAndAdvance =
                    DnsSvcbRecord.sliceAndAdvance(
                            byteBuffer, Short.toUnsignedInt(byteBuffer.getShort()));
            if (sliceAndAdvance.remaining() % 2 != 0) {
                throw new DnsPacket$ParseException("Can't parse whole byte array");
            }
            ShortBuffer asShortBuffer = sliceAndAdvance.asShortBuffer();
            int remaining = asShortBuffer.remaining();
            short[] sArr = new short[remaining];
            asShortBuffer.get(sArr);
            this.mValue = sArr;
            if (remaining == 0) {
                throw new DnsPacket$ParseException("mandatory value must be non-empty");
            }
        }

        public final String toString() {
            switch (this.$r8$classId) {
                case 0:
                    StringBuilder sb = new StringBuilder();
                    sb.append(DnsSvcbRecord.m647$$Nest$smtoKeyName(this.mKey));
                    byte[] bArr = (byte[]) this.mValue;
                    if (bArr != null && bArr.length > 0) {
                        sb.append("=");
                        int length = bArr.length;
                        char[] cArr = HexDump.HEX_DIGITS;
                        char[] cArr2 = new char[length * 2];
                        int i = 0;
                        for (byte b : bArr) {
                            int i2 = i + 1;
                            cArr2[i] = cArr[(b >>> 4) & 15];
                            i += 2;
                            cArr2[i2] = cArr[b & 15];
                        }
                        sb.append(new String(cArr2));
                    }
                    return sb.toString();
                default:
                    StringJoiner stringJoiner = new StringJoiner(",");
                    for (short s : (short[]) this.mValue) {
                        stringJoiner.add(DnsSvcbRecord.m647$$Nest$smtoKeyName(s));
                    }
                    return DnsSvcbRecord.m647$$Nest$smtoKeyName(this.mKey)
                            + "="
                            + stringJoiner.toString();
            }
        }

        public SvcParamGeneric(int i, ByteBuffer byteBuffer) {
            super(i);
            byte[] bArr = new byte[Short.toUnsignedInt(byteBuffer.getShort())];
            this.mValue = bArr;
            byteBuffer.get(bArr);
        }
    }
}
