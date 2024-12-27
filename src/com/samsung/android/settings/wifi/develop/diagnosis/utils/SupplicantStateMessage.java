package com.samsung.android.settings.wifi.develop.diagnosis.utils;

import android.net.wifi.SupplicantState;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public enum SupplicantStateMessage {
    /* JADX INFO: Fake field, exist only in values array */
    FOUR_WAY_HANDSHAKE(SupplicantState.FOUR_WAY_HANDSHAKE, "FOUR WAY HANDSHAKE"),
    /* JADX INFO: Fake field, exist only in values array */
    ASSOCIATED(SupplicantState.ASSOCIATED, "ASSOCIATED"),
    /* JADX INFO: Fake field, exist only in values array */
    COMPLETED(SupplicantState.COMPLETED, "COMPLETED"),
    /* JADX INFO: Fake field, exist only in values array */
    DISCONNECTED(SupplicantState.DISCONNECTED, "DISCONNECTED"),
    /* JADX INFO: Fake field, exist only in values array */
    DORMANT(SupplicantState.DORMANT, "DORMANT"),
    /* JADX INFO: Fake field, exist only in values array */
    GROUP_HANDSHAKE(SupplicantState.GROUP_HANDSHAKE, "GROUP HANDSHAKE"),
    /* JADX INFO: Fake field, exist only in values array */
    INACTIVE(SupplicantState.INACTIVE, "INACTIVE"),
    INVALID(SupplicantState.INVALID, "INVALID"),
    /* JADX INFO: Fake field, exist only in values array */
    SCANNING(SupplicantState.SCANNING, "SCANNING"),
    /* JADX INFO: Fake field, exist only in values array */
    UNINITIALIZED(SupplicantState.UNINITIALIZED, "UNINITIALIZED");

    private final SupplicantState mSupplicantState;
    private final String mSupplicantText;

    SupplicantStateMessage(SupplicantState supplicantState, String str) {
        this.mSupplicantState = supplicantState;
        this.mSupplicantText = str;
    }

    public final String getMessage() {
        return this.mSupplicantText;
    }
}
