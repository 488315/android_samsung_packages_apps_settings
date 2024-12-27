package com.android.settingslib.spa.widget.card;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class CardButton {
    public final String contentDescription;
    public final Function0 onClick;
    public final String text;

    public CardButton(String text, String str, Function0 onClick) {
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(onClick, "onClick");
        this.text = text;
        this.contentDescription = str;
        this.onClick = onClick;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CardButton)) {
            return false;
        }
        CardButton cardButton = (CardButton) obj;
        return Intrinsics.areEqual(this.text, cardButton.text)
                && Intrinsics.areEqual(this.contentDescription, cardButton.contentDescription)
                && Intrinsics.areEqual(this.onClick, cardButton.onClick);
    }

    public final int hashCode() {
        int hashCode = this.text.hashCode() * 31;
        String str = this.contentDescription;
        return this.onClick.hashCode() + ((hashCode + (str == null ? 0 : str.hashCode())) * 31);
    }

    public final String toString() {
        return "CardButton(text="
                + this.text
                + ", contentDescription="
                + this.contentDescription
                + ", onClick="
                + this.onClick
                + ")";
    }
}
