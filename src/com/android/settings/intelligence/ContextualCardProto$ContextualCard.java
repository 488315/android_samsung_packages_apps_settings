package com.android.settings.intelligence;

import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.Parser;
import com.google.protobuf.RawMessageInfo;
import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ContextualCardProto$ContextualCard extends GeneratedMessageLite {
    public static final int CARDNAME_FIELD_NUMBER = 3;
    public static final int CARD_CATEGORY_FIELD_NUMBER = 4;
    public static final int CARD_SCORE_FIELD_NUMBER = 5;
    private static final ContextualCardProto$ContextualCard DEFAULT_INSTANCE;
    private static volatile Parser PARSER = null;
    public static final int SLICEURI_FIELD_NUMBER = 1;
    private int bitField0_;
    private int cardCategory_;
    private double cardScore_;
    private String sliceUri_ = ApnSettings.MVNO_NONE;
    private String cardName_ = ApnSettings.MVNO_NONE;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder extends GeneratedMessageLite.Builder {
        public final void setCardCategory(Category category) {
            copyOnWrite();
            ContextualCardProto$ContextualCard.m941$$Nest$msetCardCategory(
                    (ContextualCardProto$ContextualCard) this.instance, category);
        }

        public final void setCardName(String str) {
            copyOnWrite();
            ContextualCardProto$ContextualCard.m942$$Nest$msetCardName(
                    (ContextualCardProto$ContextualCard) this.instance, str);
        }

        public final void setSliceUri(String str) {
            copyOnWrite();
            ContextualCardProto$ContextualCard.m943$$Nest$msetSliceUri(
                    (ContextualCardProto$ContextualCard) this.instance, str);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public enum Category implements Internal.EnumLite {
        DEFAULT(0),
        SUGGESTION(1),
        POSSIBLE(2),
        IMPORTANT(3),
        DEFERRED_SETUP(5),
        STICKY(6);

        private final int value;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class CategoryVerifier implements Internal.EnumVerifier {
            public static final CategoryVerifier INSTANCE = new CategoryVerifier();

            @Override // com.google.protobuf.Internal.EnumVerifier
            public final boolean isInRange(int i) {
                return (i != 0
                                ? i != 1
                                        ? i != 2
                                                ? i != 3
                                                        ? i != 5
                                                                ? i != 6 ? null : Category.STICKY
                                                                : Category.DEFERRED_SETUP
                                                        : Category.IMPORTANT
                                                : Category.POSSIBLE
                                        : Category.SUGGESTION
                                : Category.DEFAULT)
                        != null;
            }
        }

        Category(int i) {
            this.value = i;
        }

        @Override // com.google.protobuf.Internal.EnumLite
        public final int getNumber() {
            return this.value;
        }
    }

    /* renamed from: -$$Nest$msetCardCategory, reason: not valid java name */
    public static void m941$$Nest$msetCardCategory(
            ContextualCardProto$ContextualCard contextualCardProto$ContextualCard,
            Category category) {
        contextualCardProto$ContextualCard.getClass();
        contextualCardProto$ContextualCard.cardCategory_ = category.getNumber();
        contextualCardProto$ContextualCard.bitField0_ |= 4;
    }

    /* renamed from: -$$Nest$msetCardName, reason: not valid java name */
    public static void m942$$Nest$msetCardName(
            ContextualCardProto$ContextualCard contextualCardProto$ContextualCard, String str) {
        contextualCardProto$ContextualCard.getClass();
        str.getClass();
        contextualCardProto$ContextualCard.bitField0_ |= 2;
        contextualCardProto$ContextualCard.cardName_ = str;
    }

    /* renamed from: -$$Nest$msetSliceUri, reason: not valid java name */
    public static void m943$$Nest$msetSliceUri(
            ContextualCardProto$ContextualCard contextualCardProto$ContextualCard, String str) {
        contextualCardProto$ContextualCard.getClass();
        str.getClass();
        contextualCardProto$ContextualCard.bitField0_ |= 1;
        contextualCardProto$ContextualCard.sliceUri_ = str;
    }

    static {
        ContextualCardProto$ContextualCard contextualCardProto$ContextualCard =
                new ContextualCardProto$ContextualCard();
        DEFAULT_INSTANCE = contextualCardProto$ContextualCard;
        GeneratedMessageLite.registerDefaultInstance(
                ContextualCardProto$ContextualCard.class, contextualCardProto$ContextualCard);
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
                        "\u0001\u0004\u0000\u0001\u0001\u0005\u0004\u0000\u0000\u0000\u0001ဈ\u0000\u0003ဈ\u0001\u0004ဌ\u0002\u0005က\u0003",
                        new Object[] {
                            "bitField0_",
                            "sliceUri_",
                            "cardName_",
                            "cardCategory_",
                            Category.CategoryVerifier.INSTANCE,
                            "cardScore_"
                        });
            case 3:
                return new ContextualCardProto$ContextualCard();
            case 4:
                return new Builder(DEFAULT_INSTANCE);
            case 5:
                return DEFAULT_INSTANCE;
            case 6:
                Parser parser = PARSER;
                if (parser == null) {
                    synchronized (ContextualCardProto$ContextualCard.class) {
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

    public final String getSliceUri() {
        return this.sliceUri_;
    }
}
