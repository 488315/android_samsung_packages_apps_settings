package com.android.settings.intelligence;

import com.google.protobuf.AbstractProtobufList;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtobufArrayList;
import com.google.protobuf.RawMessageInfo;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ContextualCardProto$ContextualCardList extends GeneratedMessageLite {
    public static final int CARD_FIELD_NUMBER = 1;
    private static final ContextualCardProto$ContextualCardList DEFAULT_INSTANCE;
    private static volatile Parser PARSER;
    private Internal.ProtobufList card_ = ProtobufArrayList.EMPTY_LIST;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder extends GeneratedMessageLite.Builder {
        public final void addCard(
                ContextualCardProto$ContextualCard contextualCardProto$ContextualCard) {
            copyOnWrite();
            ContextualCardProto$ContextualCardList.m944$$Nest$maddCard(
                    (ContextualCardProto$ContextualCardList) this.instance,
                    contextualCardProto$ContextualCard);
        }
    }

    /* renamed from: -$$Nest$maddCard, reason: not valid java name */
    public static void m944$$Nest$maddCard(
            ContextualCardProto$ContextualCardList contextualCardProto$ContextualCardList,
            ContextualCardProto$ContextualCard contextualCardProto$ContextualCard) {
        contextualCardProto$ContextualCardList.getClass();
        Internal.ProtobufList protobufList = contextualCardProto$ContextualCardList.card_;
        if (!((AbstractProtobufList) protobufList).isMutable) {
            int size = protobufList.size();
            contextualCardProto$ContextualCardList.card_ =
                    protobufList.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
        }
        contextualCardProto$ContextualCardList.card_.add(contextualCardProto$ContextualCard);
    }

    static {
        ContextualCardProto$ContextualCardList contextualCardProto$ContextualCardList =
                new ContextualCardProto$ContextualCardList();
        DEFAULT_INSTANCE = contextualCardProto$ContextualCardList;
        GeneratedMessageLite.registerDefaultInstance(
                ContextualCardProto$ContextualCardList.class,
                contextualCardProto$ContextualCardList);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    public final Object dynamicMethod(
            GeneratedMessageLite.MethodToInvoke methodToInvoke,
            GeneratedMessageLite generatedMessageLite) {
        switch (methodToInvoke.ordinal()) {
            case 0:
                return (byte) 1;
            case 1:
                return null;
            case 2:
                return new RawMessageInfo(
                        DEFAULT_INSTANCE,
                        "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b",
                        new Object[] {"card_", ContextualCardProto$ContextualCard.class});
            case 3:
                return new ContextualCardProto$ContextualCardList();
            case 4:
                return new Builder(DEFAULT_INSTANCE);
            case 5:
                return DEFAULT_INSTANCE;
            case 6:
                Parser parser = PARSER;
                if (parser == null) {
                    synchronized (ContextualCardProto$ContextualCardList.class) {
                        try {
                            parser = PARSER;
                            if (parser == null) {
                                parser =
                                        new GeneratedMessageLite.DefaultInstanceBasedParser(
                                                DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        } finally {
                        }
                    }
                }
                return parser;
            default:
                throw new UnsupportedOperationException();
        }
    }

    public final Internal.ProtobufList getCardList() {
        return this.card_;
    }
}
