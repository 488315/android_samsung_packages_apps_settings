package com.google.protobuf;

import com.android.settings.network.telephony.NetworkModeChoicesProto$UiOptions;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.AbstractList;
import java.util.List;
import java.util.RandomAccess;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class Internal {
    public static final byte[] EMPTY_BYTE_ARRAY;
    public static final Charset UTF_8;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface EnumLite {
        int getNumber();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface EnumVerifier {
        boolean isInRange(int i);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface IntList extends ProtobufList {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ListAdapter extends AbstractList {
        public final Converter converter;
        public final List fromList;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public interface Converter {}

        public ListAdapter(List list, Converter converter) {
            this.fromList = list;
            this.converter = converter;
        }

        @Override // java.util.AbstractList, java.util.List
        public final Object get(int i) {
            Converter converter = this.converter;
            Object obj = this.fromList.get(i);
            ((NetworkModeChoicesProto$UiOptions.AnonymousClass1) converter).getClass();
            NetworkModeChoicesProto$UiOptions.PresentFormat forNumber =
                    NetworkModeChoicesProto$UiOptions.PresentFormat.forNumber(
                            ((Integer) obj).intValue());
            return forNumber == null
                    ? NetworkModeChoicesProto$UiOptions.PresentFormat.PRESENT_FORMAT_UNSPECIFIED
                    : forNumber;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public final int size() {
            return this.fromList.size();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface ProtobufList extends List, RandomAccess {
        ProtobufList mutableCopyWithCapacity(int i);
    }

    static {
        Charset.forName("US-ASCII");
        UTF_8 = Charset.forName("UTF-8");
        Charset.forName("ISO-8859-1");
        byte[] bArr = new byte[0];
        EMPTY_BYTE_ARRAY = bArr;
        ByteBuffer.wrap(bArr);
        int length = bArr.length;
        try {
            if (length < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            int i = (0 - 0) + length;
            if (i < 0) {
                throw InvalidProtocolBufferException.parseFailure();
            }
            if (i > Integer.MAX_VALUE) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
        } catch (InvalidProtocolBufferException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static int hashLong(long j) {
        return (int) (j ^ (j >>> 32));
    }
}
