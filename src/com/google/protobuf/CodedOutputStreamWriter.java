package com.google.protobuf;

import java.nio.charset.Charset;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class CodedOutputStreamWriter {
    public final CodedOutputStream$ArrayEncoder output;

    public CodedOutputStreamWriter(CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder) {
        Charset charset = Internal.UTF_8;
        if (codedOutputStream$ArrayEncoder == null) {
            throw new NullPointerException("output");
        }
        this.output = codedOutputStream$ArrayEncoder;
        codedOutputStream$ArrayEncoder.wrapper = this;
    }

    public final void writeBytes(int i, ByteString byteString) {
        CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder = this.output;
        codedOutputStream$ArrayEncoder.writeTag(i, 2);
        codedOutputStream$ArrayEncoder.writeBytesNoTag(byteString);
    }

    public final void writeGroup(int i, Object obj, Schema schema) {
        CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder = this.output;
        codedOutputStream$ArrayEncoder.writeTag(i, 3);
        schema.writeTo((MessageLite) obj, codedOutputStream$ArrayEncoder.wrapper);
        codedOutputStream$ArrayEncoder.writeTag(i, 4);
    }

    public final void writeMessage(int i, Object obj, Schema schema) {
        MessageLite messageLite = (MessageLite) obj;
        CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder = this.output;
        codedOutputStream$ArrayEncoder.writeTag(i, 2);
        codedOutputStream$ArrayEncoder.writeUInt32NoTag(
                ((AbstractMessageLite) messageLite).getSerializedSize(schema));
        schema.writeTo(messageLite, codedOutputStream$ArrayEncoder.wrapper);
    }
}
