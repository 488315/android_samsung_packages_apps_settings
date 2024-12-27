package com.google.protobuf;

import java.nio.charset.Charset;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ManifestSchemaFactory {
    public static final AnonymousClass1 EMPTY_FACTORY = new AnonymousClass1();
    public final MessageInfoFactory messageInfoFactory;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.google.protobuf.ManifestSchemaFactory$1, reason: invalid class name */
    public final class AnonymousClass1 implements MessageInfoFactory {
        @Override // com.google.protobuf.MessageInfoFactory
        public final boolean isSupported(Class cls) {
            return false;
        }

        @Override // com.google.protobuf.MessageInfoFactory
        public final RawMessageInfo messageInfoFor(Class cls) {
            throw new IllegalStateException("This should never be called.");
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CompositeMessageInfoFactory implements MessageInfoFactory {
        public MessageInfoFactory[] factories;

        @Override // com.google.protobuf.MessageInfoFactory
        public final boolean isSupported(Class cls) {
            for (MessageInfoFactory messageInfoFactory : this.factories) {
                if (messageInfoFactory.isSupported(cls)) {
                    return true;
                }
            }
            return false;
        }

        @Override // com.google.protobuf.MessageInfoFactory
        public final RawMessageInfo messageInfoFor(Class cls) {
            for (MessageInfoFactory messageInfoFactory : this.factories) {
                if (messageInfoFactory.isSupported(cls)) {
                    return messageInfoFactory.messageInfoFor(cls);
                }
            }
            throw new UnsupportedOperationException(
                    "No factory is available for message type: ".concat(cls.getName()));
        }
    }

    public ManifestSchemaFactory() {
        MessageInfoFactory messageInfoFactory;
        GeneratedMessageInfoFactory generatedMessageInfoFactory =
                GeneratedMessageInfoFactory.instance;
        try {
            Class[] clsArr = new Class[0];
            messageInfoFactory =
                    (MessageInfoFactory)
                            Class.forName("com.google.protobuf.DescriptorMessageInfoFactory")
                                    .getDeclaredMethod("getInstance", null)
                                    .invoke(null, null);
        } catch (Exception unused) {
            messageInfoFactory = EMPTY_FACTORY;
        }
        CompositeMessageInfoFactory compositeMessageInfoFactory = new CompositeMessageInfoFactory();
        compositeMessageInfoFactory.factories =
                new MessageInfoFactory[] {generatedMessageInfoFactory, messageInfoFactory};
        Charset charset = Internal.UTF_8;
        this.messageInfoFactory = compositeMessageInfoFactory;
    }
}
