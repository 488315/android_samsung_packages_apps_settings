package com.samsung.android.settings.inputmethod.bnr;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public enum Constants$ERROR_CODE {
    SUCCESS(0),
    UNKNOWN_ERROR(1),
    STORAGE_FULL(2),
    INVALID_DATA(3),
    PARTIAL_SUCCESS(7);

    private int code;

    Constants$ERROR_CODE(int i) {
        this.code = i;
    }

    public final boolean equals() {
        return this.code == STORAGE_FULL.code;
    }

    public final int getCode() {
        return this.code;
    }

    public final boolean isSuccess() {
        return SUCCESS.code == this.code;
    }
}
