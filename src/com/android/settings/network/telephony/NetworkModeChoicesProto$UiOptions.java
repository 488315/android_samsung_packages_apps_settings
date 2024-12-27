package com.android.settings.network.telephony;

import com.google.protobuf.AbstractProtobufList;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.IntArrayList;
import com.google.protobuf.Internal;
import com.google.protobuf.Parser;
import com.google.protobuf.RawMessageInfo;

import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NetworkModeChoicesProto$UiOptions extends GeneratedMessageLite {
    public static final int CHOICES_FIELD_NUMBER = 2;
    private static final NetworkModeChoicesProto$UiOptions DEFAULT_INSTANCE;
    public static final int FORMAT_FIELD_NUMBER = 3;
    private static volatile Parser PARSER = null;
    public static final int TYPE_FIELD_NUMBER = 1;
    private static final Internal.ListAdapter.Converter format_converter_ = new AnonymousClass1();
    private int bitField0_;
    private int choices_;
    private int type_;
    private byte memoizedIsInitialized = 2;
    private Internal.IntList format_ = IntArrayList.EMPTY_LIST;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.network.telephony.NetworkModeChoicesProto$UiOptions$1, reason: invalid class name */
    public final class AnonymousClass1 implements Internal.ListAdapter.Converter {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder extends GeneratedMessageLite.Builder {
        public final void addFormat(PresentFormat presentFormat) {
            copyOnWrite();
            NetworkModeChoicesProto$UiOptions.m969$$Nest$maddFormat(
                    (NetworkModeChoicesProto$UiOptions) this.instance, presentFormat);
        }

        public final void setChoices(int i) {
            copyOnWrite();
            NetworkModeChoicesProto$UiOptions.m970$$Nest$msetChoices(
                    (NetworkModeChoicesProto$UiOptions) this.instance, i);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public enum PresentFormat implements Internal.EnumLite {
        PRESENT_FORMAT_UNSPECIFIED(0),
        add1xEntry(1),
        add2gEntry(2),
        add3gEntry(3),
        addGlobalEntry(4),
        addWorldModeCdmaEntry(5),
        addWorldModeGsmEntry(6),
        add4gEntry(7),
        addLteEntry(8),
        add5gEntry(9),
        add5gAnd4gEntry(10),
        add5gAndLteEntry(11);

        private final int value;

        PresentFormat(int i) {
            this.value = i;
        }

        public static PresentFormat forNumber(int i) {
            switch (i) {
                case 0:
                    return PRESENT_FORMAT_UNSPECIFIED;
                case 1:
                    return add1xEntry;
                case 2:
                    return add2gEntry;
                case 3:
                    return add3gEntry;
                case 4:
                    return addGlobalEntry;
                case 5:
                    return addWorldModeCdmaEntry;
                case 6:
                    return addWorldModeGsmEntry;
                case 7:
                    return add4gEntry;
                case 8:
                    return addLteEntry;
                case 9:
                    return add5gEntry;
                case 10:
                    return add5gAnd4gEntry;
                case 11:
                    return add5gAndLteEntry;
                default:
                    return null;
            }
        }

        @Override // com.google.protobuf.Internal.EnumLite
        public final int getNumber() {
            return this.value;
        }
    }

    /* renamed from: -$$Nest$maddFormat, reason: not valid java name */
    public static void m969$$Nest$maddFormat(
            NetworkModeChoicesProto$UiOptions networkModeChoicesProto$UiOptions,
            PresentFormat presentFormat) {
        networkModeChoicesProto$UiOptions.getClass();
        Internal.ProtobufList protobufList = networkModeChoicesProto$UiOptions.format_;
        if (!((AbstractProtobufList) protobufList).isMutable) {
            int size = protobufList.size();
            int i = size == 0 ? 10 : size * 2;
            IntArrayList intArrayList = (IntArrayList) protobufList;
            if (i < intArrayList.size) {
                throw new IllegalArgumentException();
            }
            networkModeChoicesProto$UiOptions.format_ =
                    new IntArrayList(intArrayList.size, Arrays.copyOf(intArrayList.array, i));
        }
        ((IntArrayList) networkModeChoicesProto$UiOptions.format_)
                .addInt(presentFormat.getNumber());
    }

    /* renamed from: -$$Nest$msetChoices, reason: not valid java name */
    public static void m970$$Nest$msetChoices(
            NetworkModeChoicesProto$UiOptions networkModeChoicesProto$UiOptions, int i) {
        networkModeChoicesProto$UiOptions.bitField0_ |= 2;
        networkModeChoicesProto$UiOptions.choices_ = i;
    }

    /* renamed from: -$$Nest$msetType, reason: not valid java name */
    public static void m971$$Nest$msetType(
            NetworkModeChoicesProto$UiOptions networkModeChoicesProto$UiOptions,
            NetworkModeChoicesProto$EnabledNetworks networkModeChoicesProto$EnabledNetworks) {
        networkModeChoicesProto$UiOptions.getClass();
        networkModeChoicesProto$UiOptions.type_ =
                networkModeChoicesProto$EnabledNetworks.getNumber();
        networkModeChoicesProto$UiOptions.bitField0_ |= 1;
    }

    static {
        NetworkModeChoicesProto$UiOptions networkModeChoicesProto$UiOptions =
                new NetworkModeChoicesProto$UiOptions();
        DEFAULT_INSTANCE = networkModeChoicesProto$UiOptions;
        GeneratedMessageLite.registerDefaultInstance(
                NetworkModeChoicesProto$UiOptions.class, networkModeChoicesProto$UiOptions);
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
                return Byte.valueOf(this.memoizedIsInitialized);
            case 1:
                this.memoizedIsInitialized = (byte) (generatedMessageLite == null ? 0 : 1);
                return null;
            case 2:
                return new RawMessageInfo(
                        DEFAULT_INSTANCE,
                        "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0001\u0002\u0001ᔌ\u0000\u0002ᔄ\u0001\u0003\u001e",
                        new Object[] {
                            "bitField0_",
                            "type_",
                            NetworkModeChoicesProto$EnabledNetworks.EnabledNetworksVerifier
                                    .INSTANCE,
                            "choices_",
                            "format_",
                            NetworkModeChoicesProto$EnabledNetworks.EnabledNetworksVerifier
                                    .INSTANCE$1
                        });
            case 3:
                return new NetworkModeChoicesProto$UiOptions();
            case 4:
                return new Builder(DEFAULT_INSTANCE);
            case 5:
                return DEFAULT_INSTANCE;
            case 6:
                Parser parser = PARSER;
                if (parser == null) {
                    synchronized (NetworkModeChoicesProto$UiOptions.class) {
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

    public final int getChoices() {
        return this.choices_;
    }

    public final List getFormatList() {
        return new Internal.ListAdapter(this.format_, format_converter_);
    }

    public final NetworkModeChoicesProto$EnabledNetworks getType() {
        NetworkModeChoicesProto$EnabledNetworks forNumber =
                NetworkModeChoicesProto$EnabledNetworks.forNumber(this.type_);
        return forNumber == null
                ? NetworkModeChoicesProto$EnabledNetworks.ENABLED_NETWORKS_UNSPECIFIED
                : forNumber;
    }
}
