package com.android.settingslib.spa.widget.scaffold;

import androidx.compose.runtime.ParcelableSnapshotMutableState;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.StructuralEqualityPolicy;
import androidx.compose.ui.text.input.TextFieldValue;
import androidx.lifecycle.ViewModel;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SearchScaffoldViewModel extends ViewModel {
    public final ParcelableSnapshotMutableState searchQuery$delegate =
            SnapshotStateKt.mutableStateOf(
                    new TextFieldValue(0, 7, (String) null), StructuralEqualityPolicy.INSTANCE);
}
