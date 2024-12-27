package com.android.settingslib.spa.widget.card;

import androidx.compose.animation.Scale$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.modifiers.TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class CardModel {
    public final List buttons;
    public final long containerColor;
    public final ImageVector imageVector;
    public final Function0 isVisible;
    public final Function0 onClick;
    public final Function0 onDismiss;
    public final String text;
    public final long tintColor;
    public final String title;

    public CardModel(
            String str,
            String str2,
            ImageVector imageVector,
            Function0 function0,
            List list,
            int i) {
        AnonymousClass1 isVisible =
                new Function0() { // from class: com.android.settingslib.spa.widget.card.CardModel.1
                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final /* bridge */ /* synthetic */ Object mo1068invoke() {
                        return Boolean.TRUE;
                    }
                };
        function0 = (i & 16) != 0 ? null : function0;
        long j = Color.Unspecified;
        Intrinsics.checkNotNullParameter(isVisible, "isVisible");
        this.title = str;
        this.text = str2;
        this.imageVector = imageVector;
        this.isVisible = isVisible;
        this.onDismiss = function0;
        this.buttons = list;
        this.tintColor = j;
        this.containerColor = j;
        this.onClick = null;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CardModel)) {
            return false;
        }
        CardModel cardModel = (CardModel) obj;
        return Intrinsics.areEqual(this.title, cardModel.title)
                && Intrinsics.areEqual(this.text, cardModel.text)
                && Intrinsics.areEqual(this.imageVector, cardModel.imageVector)
                && Intrinsics.areEqual(this.isVisible, cardModel.isVisible)
                && Intrinsics.areEqual(this.onDismiss, cardModel.onDismiss)
                && Intrinsics.areEqual(this.buttons, cardModel.buttons)
                && Color.m313equalsimpl0(this.tintColor, cardModel.tintColor)
                && Color.m313equalsimpl0(this.containerColor, cardModel.containerColor)
                && Intrinsics.areEqual(this.onClick, cardModel.onClick);
    }

    public final int hashCode() {
        int m =
                TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0.m(
                        this.title.hashCode() * 31, 31, this.text);
        ImageVector imageVector = this.imageVector;
        int hashCode =
                (this.isVisible.hashCode()
                                + ((m + (imageVector == null ? 0 : imageVector.hashCode())) * 31))
                        * 31;
        Function0 function0 = this.onDismiss;
        int hashCode2 =
                (this.buttons.hashCode()
                                + ((hashCode + (function0 == null ? 0 : function0.hashCode()))
                                        * 31))
                        * 31;
        Color.Companion companion = Color.Companion;
        int m2 =
                Scale$$ExternalSyntheticOutline0.m(
                        Scale$$ExternalSyntheticOutline0.m(hashCode2, 31, this.tintColor),
                        31,
                        this.containerColor);
        Function0 function02 = this.onClick;
        return m2 + (function02 != null ? function02.hashCode() : 0);
    }

    public final String toString() {
        List list = this.buttons;
        String m319toStringimpl = Color.m319toStringimpl(this.tintColor);
        String m319toStringimpl2 = Color.m319toStringimpl(this.containerColor);
        StringBuilder sb = new StringBuilder("CardModel(title=");
        sb.append(this.title);
        sb.append(", text=");
        sb.append(this.text);
        sb.append(", imageVector=");
        sb.append(this.imageVector);
        sb.append(", isVisible=");
        sb.append(this.isVisible);
        sb.append(", onDismiss=");
        sb.append(this.onDismiss);
        sb.append(", buttons=");
        sb.append(list);
        sb.append(", tintColor=");
        ConstraintWidget$$ExternalSyntheticOutline0.m(
                sb, m319toStringimpl, ", containerColor=", m319toStringimpl2, ", onClick=");
        sb.append(this.onClick);
        sb.append(")");
        return sb.toString();
    }
}
