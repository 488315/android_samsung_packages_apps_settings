package com.google.protobuf;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;

import java.io.IOException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class CodedOutputStream$OutOfSpaceException extends IOException {
    private static final long serialVersionUID = -6947486886997889499L;

    public CodedOutputStream$OutOfSpaceException(String str) {
        super(
                AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                        "CodedOutputStream was writing to a flat byte array and ran out of space.:"
                            + " ",
                        str));
    }

    public CodedOutputStream$OutOfSpaceException(Throwable th) {
        super("CodedOutputStream was writing to a flat byte array and ran out of space.", th);
    }

    public CodedOutputStream$OutOfSpaceException(String str, Throwable th) {
        super(
                "CodedOutputStream was writing to a flat byte array and ran out of space.: "
                        .concat(str),
                th);
    }
}
