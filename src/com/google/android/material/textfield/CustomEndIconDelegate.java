package com.google.android.material.textfield;

import com.google.android.material.internal.CheckableImageButton;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class CustomEndIconDelegate extends EndIconDelegate {
    @Override // com.google.android.material.textfield.EndIconDelegate
    public final void setUp() {
        EndCompoundLayout endCompoundLayout = this.endLayout;
        endCompoundLayout.endIconOnLongClickListener = null;
        CheckableImageButton checkableImageButton = endCompoundLayout.endIconView;
        checkableImageButton.setOnLongClickListener(null);
        IconHelper.setIconClickable(checkableImageButton, null);
    }
}
