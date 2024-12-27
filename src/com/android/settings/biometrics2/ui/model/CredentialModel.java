package com.android.settings.biometrics2.ui.model;

import android.os.Bundle;
import android.os.UserHandle;

import java.time.Clock;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class CredentialModel {
    public long challenge;
    public Long clearGkPwHandleMillis;
    public final Clock clock;
    public long gkPwHandle;
    public final long mInitMillis;
    public byte[] token;
    public Long updateChallengeMillis;
    public Long updateTokenMillis;
    public final int userId;

    public CredentialModel(Bundle bundle, Clock clock) {
        this.clock = clock;
        this.mInitMillis = clock.millis();
        this.userId =
                (bundle == null ? new Bundle() : bundle)
                        .getInt("android.intent.extra.USER_ID", UserHandle.myUserId());
        this.gkPwHandle = (bundle == null ? new Bundle() : bundle).getLong("gk_pw_handle", 0L);
        this.challenge = (bundle == null ? new Bundle() : bundle).getLong("challenge", -1L);
        this.token = (bundle == null ? new Bundle() : bundle).getByteArray("hw_auth_token");
    }

    public final String toString() {
        int length = String.valueOf(this.gkPwHandle).length();
        byte[] bArr = this.token;
        int length2 = bArr != null ? bArr.length : 0;
        return "CredentialModel:{initMillis:"
                + this.mInitMillis
                + ", userId:"
                + this.userId
                + ", challenge:{len:"
                + String.valueOf(this.challenge).length()
                + ", updateMillis:"
                + this.updateChallengeMillis
                + "}, token:{len:"
                + length2
                + ", isValid:"
                + (this.token != null)
                + ", updateMillis:"
                + this.updateTokenMillis
                + "}, gkPwHandle:{len:"
                + length
                + ", isValid:"
                + (this.gkPwHandle != 0)
                + ", clearMillis:"
                + this.clearGkPwHandleMillis
                + "} }";
    }
}
