package com.google.android.material.internal;

import com.google.android.material.chip.ChipGroup$1;
import com.google.android.material.chip.SeslChipGroup;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class CheckableGroup {
    public final Map checkables = new HashMap();
    public final Set checkedIds = new HashSet();
    public ChipGroup$1 onCheckedStateChangeListener;
    public boolean selectionRequired;
    public boolean singleSelection;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.google.android.material.internal.CheckableGroup$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {}
    }

    public final boolean checkInternal(MaterialCheckable materialCheckable) {
        int id = materialCheckable.getId();
        if (((HashSet) this.checkedIds).contains(Integer.valueOf(id))) {
            return false;
        }
        MaterialCheckable materialCheckable2 =
                (MaterialCheckable)
                        ((HashMap) this.checkables)
                                .get(
                                        Integer.valueOf(
                                                (!this.singleSelection
                                                                || ((HashSet) this.checkedIds)
                                                                        .isEmpty())
                                                        ? -1
                                                        : ((Integer)
                                                                        ((HashSet) this.checkedIds)
                                                                                .iterator()
                                                                                .next())
                                                                .intValue()));
        if (materialCheckable2 != null) {
            uncheckInternal(materialCheckable2, false);
        }
        boolean add = ((HashSet) this.checkedIds).add(Integer.valueOf(id));
        if (!materialCheckable.isChecked()) {
            materialCheckable.setChecked(true);
        }
        return add;
    }

    public final void onCheckedStateChanged() {
        ChipGroup$1 chipGroup$1 = this.onCheckedStateChangeListener;
        if (chipGroup$1 != null) {
            new HashSet(this.checkedIds);
            int i = SeslChipGroup.$r8$clinit;
            chipGroup$1.this$0.getClass();
        }
    }

    public final boolean uncheckInternal(MaterialCheckable materialCheckable, boolean z) {
        int id = materialCheckable.getId();
        if (!((HashSet) this.checkedIds).contains(Integer.valueOf(id))) {
            return false;
        }
        if (z && ((HashSet) this.checkedIds).size() == 1) {
            if (((HashSet) this.checkedIds).contains(Integer.valueOf(id))) {
                materialCheckable.setChecked(true);
                return false;
            }
        }
        boolean remove = ((HashSet) this.checkedIds).remove(Integer.valueOf(id));
        if (materialCheckable.isChecked()) {
            materialCheckable.setChecked(false);
        }
        return remove;
    }
}
