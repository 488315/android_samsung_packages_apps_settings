package com.google.protobuf;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class UninitializedMessageException extends RuntimeException {
    private static final long serialVersionUID = -7466929953374883507L;
    private final List<String> missingFields;

    public UninitializedMessageException() {
        super(
                "Message was missing required fields.  (Lite runtime could not determine which"
                    + " fields were missing).");
        this.missingFields = null;
    }
}
