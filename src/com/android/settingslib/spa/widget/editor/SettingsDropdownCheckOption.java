package com.android.settingslib.spa.widget.editor;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.ParcelableSnapshotMutableState;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SettingsDropdownCheckOption {
    public final boolean changeable;
    public final boolean isSelectAll;
    public final Function0 onClick;
    public final MutableState selected;
    public final String text;

    public SettingsDropdownCheckOption(
            String text, boolean z, boolean z2, MutableState selected, Function0 onClick) {
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(selected, "selected");
        Intrinsics.checkNotNullParameter(onClick, "onClick");
        this.text = text;
        this.isSelectAll = z;
        this.changeable = z2;
        this.selected = selected;
        this.onClick = onClick;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SettingsDropdownCheckOption)) {
            return false;
        }
        SettingsDropdownCheckOption settingsDropdownCheckOption = (SettingsDropdownCheckOption) obj;
        return Intrinsics.areEqual(this.text, settingsDropdownCheckOption.text)
                && this.isSelectAll == settingsDropdownCheckOption.isSelectAll
                && this.changeable == settingsDropdownCheckOption.changeable
                && Intrinsics.areEqual(this.selected, settingsDropdownCheckOption.selected)
                && Intrinsics.areEqual(this.onClick, settingsDropdownCheckOption.onClick);
    }

    public final int hashCode() {
        return this.onClick.hashCode()
                + ((this.selected.hashCode()
                                + TransitionData$$ExternalSyntheticOutline0.m(
                                        TransitionData$$ExternalSyntheticOutline0.m(
                                                this.text.hashCode() * 31, 31, this.isSelectAll),
                                        31,
                                        this.changeable))
                        * 31);
    }

    public final String toString() {
        return "SettingsDropdownCheckOption(text="
                + this.text
                + ", isSelectAll="
                + this.isSelectAll
                + ", changeable="
                + this.changeable
                + ", selected="
                + this.selected
                + ", onClick="
                + this.onClick
                + ")";
    }

    public /* synthetic */ SettingsDropdownCheckOption(
            String str, ParcelableSnapshotMutableState parcelableSnapshotMutableState) {
        this(
                str,
                false,
                true,
                parcelableSnapshotMutableState,
                new Function0() { // from class:
                                  // com.android.settingslib.spa.widget.editor.SettingsDropdownCheckOption.1
                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final /* bridge */ /* synthetic */ Object mo1068invoke() {
                        return Unit.INSTANCE;
                    }
                });
    }
}
