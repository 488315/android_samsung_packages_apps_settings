package com.android.settingslib.spa.widget.ui;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SpinnerOption {
    public final int id;
    public final String text;

    public SpinnerOption(int i, String str) {
        this.id = i;
        this.text = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SpinnerOption)) {
            return false;
        }
        SpinnerOption spinnerOption = (SpinnerOption) obj;
        return this.id == spinnerOption.id && Intrinsics.areEqual(this.text, spinnerOption.text);
    }

    public final int hashCode() {
        return this.text.hashCode() + (Integer.hashCode(this.id) * 31);
    }

    public final String toString() {
        return "SpinnerOption(id=" + this.id + ", text=" + this.text + ")";
    }
}
