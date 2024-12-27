package com.samsung.android.settings.eternal.validate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
enum ResultCode {
    PASS("PASS"),
    FAIL("FAIL"),
    SKIP("SKIP"),
    CONDITIONAL_PASS("CONDITIONAL PASS");

    private String mResult;

    ResultCode(String str) {
        this.mResult = str;
    }

    @Override // java.lang.Enum
    public final String toString() {
        return this.mResult;
    }
}
