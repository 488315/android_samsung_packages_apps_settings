package com.google.protobuf;

import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class MessageSetSchema implements Schema {
    public final MessageLite defaultInstance;
    public final ExtensionSchemaLite extensionSchema;
    public final UnknownFieldSchema unknownFieldSchema;

    public MessageSetSchema(
            UnknownFieldSchema unknownFieldSchema,
            ExtensionSchemaLite extensionSchemaLite,
            MessageLite messageLite) {
        this.unknownFieldSchema = unknownFieldSchema;
        extensionSchemaLite.getClass();
        this.extensionSchema = extensionSchemaLite;
        this.defaultInstance = messageLite;
    }

    @Override // com.google.protobuf.Schema
    public final boolean equals(Object obj, Object obj2) {
        UnknownFieldSchema unknownFieldSchema = this.unknownFieldSchema;
        ((UnknownFieldSetLiteSchema) unknownFieldSchema).getClass();
        UnknownFieldSetLite unknownFieldSetLite = ((GeneratedMessageLite) obj).unknownFields;
        ((UnknownFieldSetLiteSchema) unknownFieldSchema).getClass();
        return unknownFieldSetLite.equals(((GeneratedMessageLite) obj2).unknownFields);
    }

    @Override // com.google.protobuf.Schema
    public final int getSerializedSize(Object obj) {
        ((UnknownFieldSetLiteSchema) this.unknownFieldSchema).getClass();
        UnknownFieldSetLite unknownFieldSetLite = ((GeneratedMessageLite) obj).unknownFields;
        int i = unknownFieldSetLite.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < unknownFieldSetLite.count; i3++) {
            int i4 = unknownFieldSetLite.tags[i3] >>> 3;
            i2 +=
                    CodedOutputStream$ArrayEncoder.computeBytesSize(
                                    3, (ByteString) unknownFieldSetLite.objects[i3])
                            + CodedOutputStream$ArrayEncoder.computeUInt32SizeNoTag(i4)
                            + CodedOutputStream$ArrayEncoder.computeTagSize(2)
                            + (CodedOutputStream$ArrayEncoder.computeTagSize(1) * 2);
        }
        unknownFieldSetLite.memoizedSerializedSize = i2;
        return i2;
    }

    @Override // com.google.protobuf.Schema
    public final int hashCode(Object obj) {
        ((UnknownFieldSetLiteSchema) this.unknownFieldSchema).getClass();
        return ((GeneratedMessageLite) obj).unknownFields.hashCode();
    }

    @Override // com.google.protobuf.Schema
    public final boolean isInitialized(Object obj) {
        this.extensionSchema.getClass();
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
        throw null;
    }

    @Override // com.google.protobuf.Schema
    public final void makeImmutable(Object obj) {
        ((UnknownFieldSetLiteSchema) this.unknownFieldSchema).getClass();
        ((GeneratedMessageLite) obj).unknownFields.isMutable = false;
        this.extensionSchema.getClass();
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
        throw null;
    }

    @Override // com.google.protobuf.Schema
    public final void mergeFrom(Object obj, Object obj2) {
        SchemaUtil.mergeUnknownFields(this.unknownFieldSchema, obj, obj2);
    }

    @Override // com.google.protobuf.Schema
    public final GeneratedMessageLite newInstance() {
        MessageLite messageLite = this.defaultInstance;
        return messageLite instanceof GeneratedMessageLite
                ? (GeneratedMessageLite) ((GeneratedMessageLite) messageLite).dynamicMethod$1()
                : ((GeneratedMessageLite.Builder)
                                ((GeneratedMessageLite) messageLite)
                                        .dynamicMethod(
                                                GeneratedMessageLite.MethodToInvoke.NEW_BUILDER,
                                                null))
                        .buildPartial();
    }

    @Override // com.google.protobuf.Schema
    public final void writeTo(Object obj, CodedOutputStreamWriter codedOutputStreamWriter) {
        this.extensionSchema.getClass();
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
        throw null;
    }

    @Override // com.google.protobuf.Schema
    public final void mergeFrom(
            Object obj, byte[] bArr, int i, int i2, ArrayDecoders.Registers registers) {
        GeneratedMessageLite generatedMessageLite = (GeneratedMessageLite) obj;
        if (generatedMessageLite.unknownFields == UnknownFieldSetLite.DEFAULT_INSTANCE) {
            generatedMessageLite.unknownFields = UnknownFieldSetLite.newInstance();
        }
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
        throw null;
    }
}
