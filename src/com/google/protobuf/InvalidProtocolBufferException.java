package com.google.protobuf;

import java.io.IOException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class InvalidProtocolBufferException extends IOException {
    public static final /* synthetic */ int $r8$clinit = 0;
    private static final long serialVersionUID = -1616151763072450476L;
    private MessageLite unfinishedMessage;
    private boolean wasThrownFromInputStream;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class InvalidWireTypeException extends InvalidProtocolBufferException {
        private static final long serialVersionUID = 3283890091615336259L;

        public InvalidWireTypeException(String str) {
            super(str);
        }
    }

    public InvalidProtocolBufferException(String str) {
        super(str);
        this.unfinishedMessage = null;
    }

    public static InvalidProtocolBufferException invalidUtf8() {
        return new InvalidProtocolBufferException("Protocol message had invalid UTF-8.");
    }

    public static InvalidProtocolBufferException negativeSize() {
        return new InvalidProtocolBufferException(
                "CodedInputStream encountered an embedded string or message which claimed to have"
                    + " negative size.");
    }

    public static InvalidProtocolBufferException parseFailure() {
        return new InvalidProtocolBufferException("Failed to parse the message.");
    }

    public static InvalidProtocolBufferException truncatedMessage() {
        return new InvalidProtocolBufferException(
                "While parsing a protocol message, the input ended unexpectedly in the middle of a"
                    + " field.  This could mean either that the input has been truncated or that an"
                    + " embedded message misreported its own length.");
    }

    public final boolean getThrownFromInputStream() {
        return this.wasThrownFromInputStream;
    }

    public final void setUnfinishedMessage(GeneratedMessageLite generatedMessageLite) {
        this.unfinishedMessage = generatedMessageLite;
    }

    public InvalidProtocolBufferException(IOException iOException) {
        super(iOException.getMessage(), iOException);
        this.unfinishedMessage = null;
    }
}
