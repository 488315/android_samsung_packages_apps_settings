package com.google.android.material.textfield;

import android.widget.EditText;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class EditTextUtils {
    public static boolean isEditable(EditText editText) {
        return editText.getInputType() != 0;
    }
}
