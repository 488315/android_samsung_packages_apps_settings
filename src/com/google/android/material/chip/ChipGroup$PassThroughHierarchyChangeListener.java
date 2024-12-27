package com.google.android.material.chip;

import android.view.View;
import android.view.ViewGroup;

import androidx.core.view.ViewCompat;

import com.google.android.material.internal.CheckableGroup;
import com.google.android.material.internal.CheckableGroup.AnonymousClass1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ChipGroup$PassThroughHierarchyChangeListener
        implements ViewGroup.OnHierarchyChangeListener {
    public ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener;
    public final /* synthetic */ SeslChipGroup this$0;

    public ChipGroup$PassThroughHierarchyChangeListener(SeslChipGroup seslChipGroup) {
        this.this$0 = seslChipGroup;
    }

    @Override // android.view.ViewGroup.OnHierarchyChangeListener
    public final void onChildViewAdded(View view, View view2) {
        if (view == this.this$0 && (view2 instanceof Chip)) {
            if (view2.getId() == -1) {
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                view2.setId(View.generateViewId());
            }
            CheckableGroup checkableGroup = this.this$0.checkableGroup;
            Chip chip = (Chip) view2;
            ((HashMap) checkableGroup.checkables).put(Integer.valueOf(chip.getId()), chip);
            if (chip.isChecked()) {
                checkableGroup.checkInternal(chip);
            }
            chip.onCheckedChangeListenerInternal = checkableGroup.new AnonymousClass1();
        }
        ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener =
                this.onHierarchyChangeListener;
        if (onHierarchyChangeListener != null) {
            onHierarchyChangeListener.onChildViewAdded(view, view2);
        }
    }

    @Override // android.view.ViewGroup.OnHierarchyChangeListener
    public final void onChildViewRemoved(View view, View view2) {
        SeslChipGroup seslChipGroup = this.this$0;
        if (view == seslChipGroup && (view2 instanceof Chip)) {
            CheckableGroup checkableGroup = seslChipGroup.checkableGroup;
            Chip chip = (Chip) view2;
            checkableGroup.getClass();
            chip.onCheckedChangeListenerInternal = null;
            ((HashMap) checkableGroup.checkables).remove(Integer.valueOf(chip.getId()));
            ((HashSet) checkableGroup.checkedIds).remove(Integer.valueOf(chip.getId()));
        }
        ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener =
                this.onHierarchyChangeListener;
        if (onHierarchyChangeListener != null) {
            onHierarchyChangeListener.onChildViewRemoved(view, view2);
        }
    }
}
