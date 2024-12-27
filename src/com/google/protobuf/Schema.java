package com.google.protobuf;


/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public interface Schema {
    boolean equals(Object obj, Object obj2);

    int getSerializedSize(Object obj);

    int hashCode(Object obj);

    boolean isInitialized(Object obj);

    void makeImmutable(Object obj);

    void mergeFrom(Object obj, Object obj2);

    void mergeFrom(Object obj, byte[] bArr, int i, int i2, ArrayDecoders.Registers registers);

    GeneratedMessageLite newInstance();

    void writeTo(Object obj, CodedOutputStreamWriter codedOutputStreamWriter);
}
