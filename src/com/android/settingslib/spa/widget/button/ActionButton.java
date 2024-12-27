package com.android.settingslib.spa.widget.button;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.vector.ImageVector;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ActionButton {
    public final boolean enabled;
    public final ImageVector imageVector;
    public final Function0 onClick;
    public final String text;

    public ActionButton(String text, ImageVector imageVector, boolean z, Function0 onClick) {
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(imageVector, "imageVector");
        Intrinsics.checkNotNullParameter(onClick, "onClick");
        this.text = text;
        this.imageVector = imageVector;
        this.enabled = z;
        this.onClick = onClick;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ActionButton)) {
            return false;
        }
        ActionButton actionButton = (ActionButton) obj;
        return Intrinsics.areEqual(this.text, actionButton.text)
                && Intrinsics.areEqual(this.imageVector, actionButton.imageVector)
                && this.enabled == actionButton.enabled
                && Intrinsics.areEqual(this.onClick, actionButton.onClick);
    }

    public final int hashCode() {
        return this.onClick.hashCode()
                + TransitionData$$ExternalSyntheticOutline0.m(
                        (this.imageVector.hashCode() + (this.text.hashCode() * 31)) * 31,
                        31,
                        this.enabled);
    }

    public final String toString() {
        return "ActionButton(text="
                + this.text
                + ", imageVector="
                + this.imageVector
                + ", enabled="
                + this.enabled
                + ", onClick="
                + this.onClick
                + ")";
    }
}
