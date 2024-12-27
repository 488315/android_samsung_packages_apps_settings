package com.android.settings.biometrics.fingerprint2.lib.model;

import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class FingerprintAuthAttemptModel {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Error extends FingerprintAuthAttemptModel {
        public final int error;
        public final String message;

        public Error(int i, String message) {
            Intrinsics.checkNotNullParameter(message, "message");
            this.error = i;
            this.message = message;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Error)) {
                return false;
            }
            Error error = (Error) obj;
            return this.error == error.error && Intrinsics.areEqual(this.message, error.message);
        }

        public final int hashCode() {
            return this.message.hashCode() + (Integer.hashCode(this.error) * 31);
        }

        public final String toString() {
            return "Error(error=" + this.error + ", message=" + this.message + ")";
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Success extends FingerprintAuthAttemptModel {
        public final int fingerId;

        public Success(int i) {
            this.fingerId = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Success) && this.fingerId == ((Success) obj).fingerId;
        }

        public final int hashCode() {
            return Integer.hashCode(this.fingerId);
        }

        public final String toString() {
            return Anchor$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Success(fingerId="), this.fingerId, ")");
        }
    }
}
