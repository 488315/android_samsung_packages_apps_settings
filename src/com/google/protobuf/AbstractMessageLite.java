package com.google.protobuf;

import java.io.IOException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class AbstractMessageLite implements MessageLite {
    protected int memoizedHashCode;

    public abstract int getSerializedSize(Schema schema);

    public final byte[] toByteArray() {
        try {
            int serializedSize = ((GeneratedMessageLite) this).getSerializedSize(null);
            byte[] bArr = new byte[serializedSize];
            CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder =
                    new CodedOutputStream$ArrayEncoder(serializedSize, bArr);
            ((GeneratedMessageLite) this).writeTo(codedOutputStream$ArrayEncoder);
            if (serializedSize - codedOutputStream$ArrayEncoder.position == 0) {
                return bArr;
            }
            throw new IllegalStateException("Did not write as much data as expected.");
        } catch (IOException e) {
            throw new RuntimeException(
                    "Serializing "
                            + getClass().getName()
                            + " to a byte array threw an IOException (should never happen).",
                    e);
        }
    }
}
