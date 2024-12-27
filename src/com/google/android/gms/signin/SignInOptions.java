package com.google.android.gms.signin;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.Objects;

import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SignInOptions implements Api.ApiOptions {
    public static final SignInOptions zaa = new SignInOptions();

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SignInOptions)) {
            return false;
        }
        ((SignInOptions) obj).getClass();
        return Objects.equal(null, null)
                && Objects.equal(null, null)
                && Objects.equal(null, null)
                && Objects.equal(null, null)
                && Objects.equal(null, null);
    }

    public final int hashCode() {
        Boolean bool = Boolean.FALSE;
        return Arrays.hashCode(new Object[] {bool, bool, null, bool, bool, null, null, null, null});
    }
}
