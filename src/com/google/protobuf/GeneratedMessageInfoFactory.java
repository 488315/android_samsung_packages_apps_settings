package com.google.protobuf;


/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class GeneratedMessageInfoFactory implements MessageInfoFactory {
    public static final GeneratedMessageInfoFactory instance = new GeneratedMessageInfoFactory();

    @Override // com.google.protobuf.MessageInfoFactory
    public final boolean isSupported(Class cls) {
        return GeneratedMessageLite.class.isAssignableFrom(cls);
    }

    @Override // com.google.protobuf.MessageInfoFactory
    public final RawMessageInfo messageInfoFor(Class cls) {
        if (!GeneratedMessageLite.class.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Unsupported message type: ".concat(cls.getName()));
        }
        try {
            return (RawMessageInfo)
                    GeneratedMessageLite.getDefaultInstance(
                                    cls.asSubclass(GeneratedMessageLite.class))
                            .dynamicMethod(
                                    GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO, null);
        } catch (Exception e) {
            throw new RuntimeException("Unable to get message info for ".concat(cls.getName()), e);
        }
    }
}
