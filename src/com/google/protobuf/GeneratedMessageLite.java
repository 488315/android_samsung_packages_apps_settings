package com.google.protobuf;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.preference.Preference;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class GeneratedMessageLite extends AbstractMessageLite {
    private static final int MEMOIZED_SERIALIZED_SIZE_MASK = Integer.MAX_VALUE;
    private static final int MUTABLE_FLAG_MASK = Integer.MIN_VALUE;
    static final int UNINITIALIZED_HASH_CODE = 0;
    static final int UNINITIALIZED_SERIALIZED_SIZE = Integer.MAX_VALUE;
    private static Map<Object, GeneratedMessageLite> defaultInstanceMap = new ConcurrentHashMap();
    private int memoizedSerializedSize;
    protected UnknownFieldSetLite unknownFields;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class Builder implements Cloneable {
        public final GeneratedMessageLite defaultInstance;
        public GeneratedMessageLite instance;

        public Builder(GeneratedMessageLite generatedMessageLite) {
            this.defaultInstance = generatedMessageLite;
            if (generatedMessageLite.isMutable()) {
                throw new IllegalArgumentException("Default instance must be immutable.");
            }
            this.instance = (GeneratedMessageLite) generatedMessageLite.dynamicMethod$1();
        }

        public final GeneratedMessageLite build() {
            GeneratedMessageLite buildPartial = buildPartial();
            buildPartial.getClass();
            if (GeneratedMessageLite.isInitialized(buildPartial, true)) {
                return buildPartial;
            }
            throw new UninitializedMessageException();
        }

        public final GeneratedMessageLite buildPartial() {
            if (!this.instance.isMutable()) {
                return this.instance;
            }
            GeneratedMessageLite generatedMessageLite = this.instance;
            generatedMessageLite.getClass();
            Protobuf protobuf = Protobuf.INSTANCE;
            protobuf.getClass();
            protobuf.schemaFor(generatedMessageLite.getClass()).makeImmutable(generatedMessageLite);
            generatedMessageLite.markImmutable();
            return this.instance;
        }

        public final void clear() {
            if (this.defaultInstance.isMutable()) {
                throw new IllegalArgumentException("Default instance must be immutable.");
            }
            this.instance = (GeneratedMessageLite) this.defaultInstance.dynamicMethod$1();
        }

        public final Object clone() {
            Builder builder =
                    (Builder) this.defaultInstance.dynamicMethod(MethodToInvoke.NEW_BUILDER, null);
            builder.instance = buildPartial();
            return builder;
        }

        public final void copyOnWrite() {
            if (this.instance.isMutable()) {
                return;
            }
            GeneratedMessageLite generatedMessageLite =
                    (GeneratedMessageLite) this.defaultInstance.dynamicMethod$1();
            GeneratedMessageLite generatedMessageLite2 = this.instance;
            Protobuf protobuf = Protobuf.INSTANCE;
            protobuf.getClass();
            protobuf.schemaFor(generatedMessageLite.getClass())
                    .mergeFrom(generatedMessageLite, generatedMessageLite2);
            this.instance = generatedMessageLite;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DefaultInstanceBasedParser implements Parser {
        public static final ExtensionRegistryLite EMPTY_REGISTRY;
        public final GeneratedMessageLite defaultInstance;

        static {
            ExtensionRegistryLite extensionRegistryLite = ExtensionRegistryLite.emptyRegistry;
            if (extensionRegistryLite == null) {
                synchronized (ExtensionRegistryLite.class) {
                    try {
                        extensionRegistryLite = ExtensionRegistryLite.emptyRegistry;
                        if (extensionRegistryLite == null) {
                            Class cls = ExtensionRegistryFactory.EXTENSION_REGISTRY_CLASS;
                            ExtensionRegistryLite extensionRegistryLite2 = null;
                            if (cls != null) {
                                try {
                                    Class[] clsArr = new Class[0];
                                    extensionRegistryLite2 =
                                            (ExtensionRegistryLite)
                                                    cls.getDeclaredMethod("getEmptyRegistry", null)
                                                            .invoke(null, null);
                                } catch (Exception unused) {
                                }
                            }
                            if (extensionRegistryLite2 == null) {
                                extensionRegistryLite2 = ExtensionRegistryLite.EMPTY_REGISTRY_LITE;
                            }
                            ExtensionRegistryLite.emptyRegistry = extensionRegistryLite2;
                            extensionRegistryLite = extensionRegistryLite2;
                        }
                    } finally {
                    }
                }
            }
            EMPTY_REGISTRY = extensionRegistryLite;
        }

        public DefaultInstanceBasedParser(GeneratedMessageLite generatedMessageLite) {
            this.defaultInstance = generatedMessageLite;
        }

        public final GeneratedMessageLite parseFrom(byte[] bArr) {
            int length = bArr.length;
            GeneratedMessageLite generatedMessageLite =
                    (GeneratedMessageLite) this.defaultInstance.dynamicMethod$1();
            try {
                Protobuf protobuf = Protobuf.INSTANCE;
                protobuf.getClass();
                Schema schemaFor = protobuf.schemaFor(generatedMessageLite.getClass());
                ExtensionRegistryLite extensionRegistryLite = EMPTY_REGISTRY;
                ArrayDecoders.Registers registers = new ArrayDecoders.Registers();
                extensionRegistryLite.getClass();
                schemaFor.mergeFrom(generatedMessageLite, bArr, 0, length, registers);
                schemaFor.makeImmutable(generatedMessageLite);
                if (GeneratedMessageLite.isInitialized(generatedMessageLite, true)) {
                    return generatedMessageLite;
                }
                InvalidProtocolBufferException invalidProtocolBufferException =
                        new InvalidProtocolBufferException(
                                new UninitializedMessageException().getMessage());
                invalidProtocolBufferException.setUnfinishedMessage(generatedMessageLite);
                throw invalidProtocolBufferException;
            } catch (InvalidProtocolBufferException e) {
                e = e;
                if (e.getThrownFromInputStream()) {
                    e = new InvalidProtocolBufferException(e);
                }
                e.setUnfinishedMessage(generatedMessageLite);
                throw e;
            } catch (UninitializedMessageException e2) {
                InvalidProtocolBufferException invalidProtocolBufferException2 =
                        new InvalidProtocolBufferException(e2.getMessage());
                invalidProtocolBufferException2.setUnfinishedMessage(generatedMessageLite);
                throw invalidProtocolBufferException2;
            } catch (IOException e3) {
                if (e3.getCause() instanceof InvalidProtocolBufferException) {
                    throw ((InvalidProtocolBufferException) e3.getCause());
                }
                InvalidProtocolBufferException invalidProtocolBufferException3 =
                        new InvalidProtocolBufferException(e3);
                invalidProtocolBufferException3.setUnfinishedMessage(generatedMessageLite);
                throw invalidProtocolBufferException3;
            } catch (IndexOutOfBoundsException unused) {
                InvalidProtocolBufferException truncatedMessage =
                        InvalidProtocolBufferException.truncatedMessage();
                truncatedMessage.setUnfinishedMessage(generatedMessageLite);
                throw truncatedMessage;
            }
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class MethodToInvoke {
        public static final /* synthetic */ MethodToInvoke[] $VALUES;
        public static final MethodToInvoke BUILD_MESSAGE_INFO;
        public static final MethodToInvoke GET_DEFAULT_INSTANCE;
        public static final MethodToInvoke GET_MEMOIZED_IS_INITIALIZED;
        public static final MethodToInvoke GET_PARSER;
        public static final MethodToInvoke NEW_BUILDER;
        public static final MethodToInvoke NEW_MUTABLE_INSTANCE;
        public static final MethodToInvoke SET_MEMOIZED_IS_INITIALIZED;

        static {
            MethodToInvoke methodToInvoke = new MethodToInvoke("GET_MEMOIZED_IS_INITIALIZED", 0);
            GET_MEMOIZED_IS_INITIALIZED = methodToInvoke;
            MethodToInvoke methodToInvoke2 = new MethodToInvoke("SET_MEMOIZED_IS_INITIALIZED", 1);
            SET_MEMOIZED_IS_INITIALIZED = methodToInvoke2;
            MethodToInvoke methodToInvoke3 = new MethodToInvoke("BUILD_MESSAGE_INFO", 2);
            BUILD_MESSAGE_INFO = methodToInvoke3;
            MethodToInvoke methodToInvoke4 = new MethodToInvoke("NEW_MUTABLE_INSTANCE", 3);
            NEW_MUTABLE_INSTANCE = methodToInvoke4;
            MethodToInvoke methodToInvoke5 = new MethodToInvoke("NEW_BUILDER", 4);
            NEW_BUILDER = methodToInvoke5;
            MethodToInvoke methodToInvoke6 = new MethodToInvoke("GET_DEFAULT_INSTANCE", 5);
            GET_DEFAULT_INSTANCE = methodToInvoke6;
            MethodToInvoke methodToInvoke7 = new MethodToInvoke("GET_PARSER", 6);
            GET_PARSER = methodToInvoke7;
            $VALUES =
                    new MethodToInvoke[] {
                        methodToInvoke,
                        methodToInvoke2,
                        methodToInvoke3,
                        methodToInvoke4,
                        methodToInvoke5,
                        methodToInvoke6,
                        methodToInvoke7
                    };
        }

        public static MethodToInvoke valueOf(String str) {
            return (MethodToInvoke) Enum.valueOf(MethodToInvoke.class, str);
        }

        public static MethodToInvoke[] values() {
            return (MethodToInvoke[]) $VALUES.clone();
        }
    }

    public GeneratedMessageLite() {
        this.memoizedHashCode = 0;
        this.memoizedSerializedSize = -1;
        this.unknownFields = UnknownFieldSetLite.DEFAULT_INSTANCE;
    }

    public static GeneratedMessageLite getDefaultInstance(Class cls) {
        GeneratedMessageLite generatedMessageLite = defaultInstanceMap.get(cls);
        if (generatedMessageLite == null) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
                generatedMessageLite = defaultInstanceMap.get(cls);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Class initialization cannot fail.", e);
            }
        }
        if (generatedMessageLite == null) {
            generatedMessageLite =
                    (GeneratedMessageLite)
                            ((GeneratedMessageLite) UnsafeUtil.allocateInstance(cls))
                                    .dynamicMethod(MethodToInvoke.GET_DEFAULT_INSTANCE, null);
            if (generatedMessageLite == null) {
                throw new IllegalStateException();
            }
            defaultInstanceMap.put(cls, generatedMessageLite);
        }
        return generatedMessageLite;
    }

    public static Object invokeOrDie(Object obj, Method method, Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(
                    "Couldn't use Java reflection to implement protocol message reflection.", e);
        } catch (InvocationTargetException e2) {
            Throwable cause = e2.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            }
            if (cause instanceof Error) {
                throw ((Error) cause);
            }
            throw new RuntimeException(
                    "Unexpected exception thrown by generated accessor method.", cause);
        }
    }

    public static final boolean isInitialized(
            GeneratedMessageLite generatedMessageLite, boolean z) {
        byte byteValue =
                ((Byte)
                                generatedMessageLite.dynamicMethod(
                                        MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED, null))
                        .byteValue();
        if (byteValue == 1) {
            return true;
        }
        if (byteValue == 0) {
            return false;
        }
        Protobuf protobuf = Protobuf.INSTANCE;
        protobuf.getClass();
        boolean isInitialized =
                protobuf.schemaFor(generatedMessageLite.getClass())
                        .isInitialized(generatedMessageLite);
        if (z) {
            generatedMessageLite.dynamicMethod(
                    MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED,
                    isInitialized ? generatedMessageLite : null);
        }
        return isInitialized;
    }

    public static void registerDefaultInstance(
            Class cls, GeneratedMessageLite generatedMessageLite) {
        generatedMessageLite.markImmutable();
        defaultInstanceMap.put(cls, generatedMessageLite);
    }

    public final Builder createBuilder() {
        return (Builder) dynamicMethod(MethodToInvoke.NEW_BUILDER, null);
    }

    public abstract Object dynamicMethod(
            MethodToInvoke methodToInvoke, GeneratedMessageLite generatedMessageLite);

    public final Object dynamicMethod$1() {
        return dynamicMethod(MethodToInvoke.NEW_MUTABLE_INSTANCE, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Protobuf protobuf = Protobuf.INSTANCE;
        protobuf.getClass();
        return protobuf.schemaFor(getClass()).equals(this, (GeneratedMessageLite) obj);
    }

    @Override // com.google.protobuf.AbstractMessageLite
    public final int getSerializedSize(Schema schema) {
        int serializedSize;
        int serializedSize2;
        if (isMutable()) {
            if (schema == null) {
                Protobuf protobuf = Protobuf.INSTANCE;
                protobuf.getClass();
                serializedSize2 = protobuf.schemaFor(getClass()).getSerializedSize(this);
            } else {
                serializedSize2 = schema.getSerializedSize(this);
            }
            if (serializedSize2 >= 0) {
                return serializedSize2;
            }
            throw new IllegalStateException(
                    SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                            serializedSize2, "serialized size must be non-negative, was "));
        }
        int i = this.memoizedSerializedSize;
        if ((i & Preference.DEFAULT_ORDER) != Integer.MAX_VALUE) {
            return i & Preference.DEFAULT_ORDER;
        }
        if (schema == null) {
            Protobuf protobuf2 = Protobuf.INSTANCE;
            protobuf2.getClass();
            serializedSize = protobuf2.schemaFor(getClass()).getSerializedSize(this);
        } else {
            serializedSize = schema.getSerializedSize(this);
        }
        setMemoizedSerializedSize(serializedSize);
        return serializedSize;
    }

    public final int hashCode() {
        if (isMutable()) {
            Protobuf protobuf = Protobuf.INSTANCE;
            protobuf.getClass();
            return protobuf.schemaFor(getClass()).hashCode(this);
        }
        if (this.memoizedHashCode == 0) {
            Protobuf protobuf2 = Protobuf.INSTANCE;
            protobuf2.getClass();
            this.memoizedHashCode = protobuf2.schemaFor(getClass()).hashCode(this);
        }
        return this.memoizedHashCode;
    }

    public final boolean isMutable() {
        return (this.memoizedSerializedSize & MUTABLE_FLAG_MASK) != 0;
    }

    public final void markImmutable() {
        this.memoizedSerializedSize &= Preference.DEFAULT_ORDER;
    }

    public final void setMemoizedSerializedSize(int i) {
        if (i < 0) {
            throw new IllegalStateException(
                    SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                            i, "serialized size must be non-negative, was "));
        }
        this.memoizedSerializedSize =
                (i & Preference.DEFAULT_ORDER) | (this.memoizedSerializedSize & MUTABLE_FLAG_MASK);
    }

    public final String toString() {
        String obj = super.toString();
        char[] cArr = MessageLiteToString.INDENT_BUFFER;
        StringBuilder sb = new StringBuilder();
        sb.append("# ");
        sb.append(obj);
        MessageLiteToString.reflectivePrintWithIndent(this, sb, 0);
        return sb.toString();
    }

    public final void writeTo(CodedOutputStream$ArrayEncoder codedOutputStream$ArrayEncoder) {
        Protobuf protobuf = Protobuf.INSTANCE;
        protobuf.getClass();
        Schema schemaFor = protobuf.schemaFor(getClass());
        CodedOutputStreamWriter codedOutputStreamWriter = codedOutputStream$ArrayEncoder.wrapper;
        if (codedOutputStreamWriter == null) {
            codedOutputStreamWriter = new CodedOutputStreamWriter(codedOutputStream$ArrayEncoder);
        }
        schemaFor.writeTo(this, codedOutputStreamWriter);
    }

    public final int getSerializedSize() {
        return getSerializedSize(null);
    }
}
