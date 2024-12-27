package com.android.settingslib.spa.widget.dialog;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AlertDialogButton {
    public final boolean enabled;
    public final Function0 onClick;
    public final String text;

    public AlertDialogButton(String text, boolean z, Function0 onClick) {
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(onClick, "onClick");
        this.text = text;
        this.enabled = z;
        this.onClick = onClick;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AlertDialogButton)) {
            return false;
        }
        AlertDialogButton alertDialogButton = (AlertDialogButton) obj;
        return Intrinsics.areEqual(this.text, alertDialogButton.text)
                && this.enabled == alertDialogButton.enabled
                && Intrinsics.areEqual(this.onClick, alertDialogButton.onClick);
    }

    public final int hashCode() {
        return this.onClick.hashCode()
                + TransitionData$$ExternalSyntheticOutline0.m(
                        this.text.hashCode() * 31, 31, this.enabled);
    }

    public final String toString() {
        return "AlertDialogButton(text="
                + this.text
                + ", enabled="
                + this.enabled
                + ", onClick="
                + this.onClick
                + ")";
    }

    public /* synthetic */ AlertDialogButton(String str, Function0 function0, int i) {
        this(
                str,
                true,
                (i & 4) != 0
                        ? new Function0() { // from class:
                                            // com.android.settingslib.spa.widget.dialog.AlertDialogButton.1
                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final /* bridge */ /* synthetic */ Object mo1068invoke() {
                                return Unit.INSTANCE;
                            }
                        }
                        : function0);
    }
}
