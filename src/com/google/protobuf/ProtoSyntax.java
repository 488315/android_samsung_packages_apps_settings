package com.google.protobuf;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ProtoSyntax {
    public static final /* synthetic */ ProtoSyntax[] $VALUES;
    public static final ProtoSyntax PROTO2;
    public static final ProtoSyntax PROTO3;

    static {
        ProtoSyntax protoSyntax = new ProtoSyntax("PROTO2", 0);
        PROTO2 = protoSyntax;
        ProtoSyntax protoSyntax2 = new ProtoSyntax("PROTO3", 1);
        PROTO3 = protoSyntax2;
        $VALUES = new ProtoSyntax[] {protoSyntax, protoSyntax2};
    }

    public static ProtoSyntax valueOf(String str) {
        return (ProtoSyntax) Enum.valueOf(ProtoSyntax.class, str);
    }

    public static ProtoSyntax[] values() {
        return (ProtoSyntax[]) $VALUES.clone();
    }
}
