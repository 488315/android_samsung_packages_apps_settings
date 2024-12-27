package com.google.android.material.button;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ToggleButton;

import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import com.android.settings.R;

import com.google.android.material.R$styleable;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.shape.AbsoluteCornerSize;
import com.google.android.material.shape.CornerSize;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import com.google.android.material.timepicker.TimePickerView$$ExternalSyntheticLambda0;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class MaterialButtonToggleGroup extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Set checkedIds;
    public Integer[] childOrder;
    public final AnonymousClass1 childOrderComparator;
    public final int defaultCheckId;
    public final LinkedHashSet onButtonCheckedListeners;
    public final List originalCornerData;
    public final PressedStateTracker pressedStateTracker;
    public final boolean selectionRequired;
    public final boolean singleSelection;
    public boolean skipCheckedStateTracker;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CornerData {
        public static final AbsoluteCornerSize noCorner = new AbsoluteCornerSize(0.0f);
        public final CornerSize bottomLeft;
        public final CornerSize bottomRight;
        public final CornerSize topLeft;
        public final CornerSize topRight;

        public CornerData(
                CornerSize cornerSize,
                CornerSize cornerSize2,
                CornerSize cornerSize3,
                CornerSize cornerSize4) {
            this.topLeft = cornerSize;
            this.topRight = cornerSize3;
            this.bottomRight = cornerSize4;
            this.bottomLeft = cornerSize2;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PressedStateTracker {
        public PressedStateTracker() {}
    }

    public MaterialButtonToggleGroup(Context context) {
        this(context, null);
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (!(view instanceof MaterialButton)) {
            Log.e("MButtonToggleGroup", "Child views must be of type MaterialButton.");
            return;
        }
        super.addView(view, i, layoutParams);
        MaterialButton materialButton = (MaterialButton) view;
        if (materialButton.getId() == -1) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            materialButton.setId(View.generateViewId());
        }
        materialButton.setMaxLines(1);
        materialButton.setEllipsize(TextUtils.TruncateAt.END);
        if (materialButton.isUsingOriginalBackground()) {
            materialButton.materialButtonHelper.checkable = true;
        }
        materialButton.onPressedChangeListenerInternal = this.pressedStateTracker;
        if (materialButton.isUsingOriginalBackground()) {
            MaterialButtonHelper materialButtonHelper = materialButton.materialButtonHelper;
            materialButtonHelper.shouldDrawSurfaceColorStroke = true;
            MaterialShapeDrawable materialShapeDrawable =
                    materialButtonHelper.getMaterialShapeDrawable(false);
            MaterialShapeDrawable materialShapeDrawable2 =
                    materialButtonHelper.getMaterialShapeDrawable(true);
            if (materialShapeDrawable != null) {
                float f = materialButtonHelper.strokeWidth;
                ColorStateList colorStateList = materialButtonHelper.strokeColor;
                materialShapeDrawable.drawableState.strokeWidth = f;
                materialShapeDrawable.invalidateSelf();
                MaterialShapeDrawable.MaterialShapeDrawableState materialShapeDrawableState =
                        materialShapeDrawable.drawableState;
                if (materialShapeDrawableState.strokeColor != colorStateList) {
                    materialShapeDrawableState.strokeColor = colorStateList;
                    materialShapeDrawable.onStateChange(materialShapeDrawable.getState());
                }
                if (materialShapeDrawable2 != null) {
                    float f2 = materialButtonHelper.strokeWidth;
                    int color =
                            materialButtonHelper.shouldDrawSurfaceColorStroke
                                    ? MaterialColors.getColor(
                                            materialButtonHelper.materialButton,
                                            R.attr.colorSurface)
                                    : 0;
                    materialShapeDrawable2.drawableState.strokeWidth = f2;
                    materialShapeDrawable2.invalidateSelf();
                    ColorStateList valueOf = ColorStateList.valueOf(color);
                    MaterialShapeDrawable.MaterialShapeDrawableState materialShapeDrawableState2 =
                            materialShapeDrawable2.drawableState;
                    if (materialShapeDrawableState2.strokeColor != valueOf) {
                        materialShapeDrawableState2.strokeColor = valueOf;
                        materialShapeDrawable2.onStateChange(materialShapeDrawable2.getState());
                    }
                }
            }
        }
        checkInternal(materialButton.getId(), materialButton.checked);
        if (!materialButton.isUsingOriginalBackground()) {
            throw new IllegalStateException(
                    "Attempted to get ShapeAppearanceModel from a MaterialButton which has an"
                        + " overwritten background.");
        }
        ShapeAppearanceModel shapeAppearanceModel =
                materialButton.materialButtonHelper.shapeAppearanceModel;
        ((ArrayList) this.originalCornerData)
                .add(
                        new CornerData(
                                shapeAppearanceModel.topLeftCornerSize,
                                shapeAppearanceModel.bottomLeftCornerSize,
                                shapeAppearanceModel.topRightCornerSize,
                                shapeAppearanceModel.bottomRightCornerSize));
        materialButton.setEnabled(isEnabled());
        ViewCompat.setAccessibilityDelegate(
                materialButton,
                new AccessibilityDelegateCompat() { // from class:
                                                    // com.google.android.material.button.MaterialButtonToggleGroup.2
                    @Override // androidx.core.view.AccessibilityDelegateCompat
                    public final void onInitializeAccessibilityNodeInfo(
                            View view2, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                        this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(
                                view2, accessibilityNodeInfoCompat.mInfo);
                        int i2 = MaterialButtonToggleGroup.$r8$clinit;
                        MaterialButtonToggleGroup materialButtonToggleGroup =
                                MaterialButtonToggleGroup.this;
                        materialButtonToggleGroup.getClass();
                        int i3 = -1;
                        if (view2 instanceof MaterialButton) {
                            int i4 = 0;
                            int i5 = 0;
                            while (true) {
                                if (i4 >= materialButtonToggleGroup.getChildCount()) {
                                    break;
                                }
                                if (materialButtonToggleGroup.getChildAt(i4) == view2) {
                                    i3 = i5;
                                    break;
                                }
                                if ((materialButtonToggleGroup.getChildAt(i4)
                                                instanceof MaterialButton)
                                        && materialButtonToggleGroup.isChildVisible(i4)) {
                                    i5++;
                                }
                                i4++;
                            }
                        }
                        accessibilityNodeInfoCompat.setCollectionItemInfo(
                                AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(
                                        ((MaterialButton) view2).checked, 0, 1, i3, 1));
                    }
                });
    }

    public final void adjustChildMarginsAndUpdateLayout() {
        int childCount = getChildCount();
        int i = 0;
        while (true) {
            if (i >= childCount) {
                i = -1;
                break;
            } else if (isChildVisible(i)) {
                break;
            } else {
                i++;
            }
        }
        if (i == -1) {
            return;
        }
        for (int i2 = i + 1; i2 < getChildCount(); i2++) {
            MaterialButton materialButton = (MaterialButton) getChildAt(i2);
            MaterialButton materialButton2 = (MaterialButton) getChildAt(i2 - 1);
            int min =
                    Math.min(
                            materialButton.isUsingOriginalBackground()
                                    ? materialButton.materialButtonHelper.strokeWidth
                                    : 0,
                            materialButton2.isUsingOriginalBackground()
                                    ? materialButton2.materialButtonHelper.strokeWidth
                                    : 0);
            ViewGroup.LayoutParams layoutParams = materialButton.getLayoutParams();
            LinearLayout.LayoutParams layoutParams2 =
                    layoutParams instanceof LinearLayout.LayoutParams
                            ? (LinearLayout.LayoutParams) layoutParams
                            : new LinearLayout.LayoutParams(
                                    layoutParams.width, layoutParams.height);
            if (getOrientation() == 0) {
                layoutParams2.setMarginEnd(0);
                layoutParams2.setMarginStart(-min);
                layoutParams2.topMargin = 0;
            } else {
                layoutParams2.bottomMargin = 0;
                layoutParams2.topMargin = -min;
                layoutParams2.setMarginStart(0);
            }
            materialButton.setLayoutParams(layoutParams2);
        }
        if (getChildCount() == 0 || i == -1) {
            return;
        }
        LinearLayout.LayoutParams layoutParams3 =
                (LinearLayout.LayoutParams) ((MaterialButton) getChildAt(i)).getLayoutParams();
        if (getOrientation() == 1) {
            layoutParams3.topMargin = 0;
            layoutParams3.bottomMargin = 0;
        } else {
            layoutParams3.setMarginEnd(0);
            layoutParams3.setMarginStart(0);
            layoutParams3.leftMargin = 0;
            layoutParams3.rightMargin = 0;
        }
    }

    public final void checkInternal(int i, boolean z) {
        if (i == -1) {
            WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                    .m(i, "Button ID is not valid: ", "MButtonToggleGroup");
            return;
        }
        HashSet hashSet = new HashSet(this.checkedIds);
        if (z && !hashSet.contains(Integer.valueOf(i))) {
            if (this.singleSelection && !hashSet.isEmpty()) {
                hashSet.clear();
            }
            hashSet.add(Integer.valueOf(i));
        } else {
            if (z || !hashSet.contains(Integer.valueOf(i))) {
                return;
            }
            if (!this.selectionRequired || hashSet.size() > 1) {
                hashSet.remove(Integer.valueOf(i));
            }
        }
        updateCheckedIds(hashSet);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        TreeMap treeMap = new TreeMap(this.childOrderComparator);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            treeMap.put((MaterialButton) getChildAt(i), Integer.valueOf(i));
        }
        this.childOrder = (Integer[]) treeMap.values().toArray(new Integer[0]);
        super.dispatchDraw(canvas);
    }

    @Override // android.view.ViewGroup
    public final int getChildDrawingOrder(int i, int i2) {
        Integer[] numArr = this.childOrder;
        if (numArr != null && i2 < numArr.length) {
            return numArr[i2].intValue();
        }
        Log.w("MButtonToggleGroup", "Child order wasn't updated");
        return i2;
    }

    public final boolean isChildVisible(int i) {
        return getChildAt(i).getVisibility() != 8;
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        int i = this.defaultCheckId;
        if (i != -1) {
            updateCheckedIds(Collections.singleton(Integer.valueOf(i)));
        }
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(
            AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        int i = 0;
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            if ((getChildAt(i2) instanceof MaterialButton) && isChildVisible(i2)) {
                i++;
            }
        }
        accessibilityNodeInfo.setCollectionInfo(
                (AccessibilityNodeInfo.CollectionInfo)
                        AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(
                                        1, i, this.singleSelection ? 1 : 2)
                                .mInfo);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        updateChildShapes();
        adjustChildMarginsAndUpdateLayout();
        super.onMeasure(i, i2);
    }

    @Override // android.view.ViewGroup
    public final void onViewRemoved(View view) {
        super.onViewRemoved(view);
        if (view instanceof MaterialButton) {
            ((MaterialButton) view).onPressedChangeListenerInternal = null;
        }
        int indexOfChild = indexOfChild(view);
        if (indexOfChild >= 0) {
            ((ArrayList) this.originalCornerData).remove(indexOfChild);
        }
        updateChildShapes();
        adjustChildMarginsAndUpdateLayout();
    }

    @Override // android.view.View
    public final void setEnabled(boolean z) {
        super.setEnabled(z);
        for (int i = 0; i < getChildCount(); i++) {
            ((MaterialButton) getChildAt(i)).setEnabled(z);
        }
    }

    public final void updateCheckedIds(Set set) {
        Set set2 = this.checkedIds;
        this.checkedIds = new HashSet(set);
        for (int i = 0; i < getChildCount(); i++) {
            int id = ((MaterialButton) getChildAt(i)).getId();
            boolean contains = set.contains(Integer.valueOf(id));
            View findViewById = findViewById(id);
            if (findViewById instanceof MaterialButton) {
                this.skipCheckedStateTracker = true;
                ((MaterialButton) findViewById).setChecked(contains);
                this.skipCheckedStateTracker = false;
            }
            if (((HashSet) set2).contains(Integer.valueOf(id))
                    != set.contains(Integer.valueOf(id))) {
                set.contains(Integer.valueOf(id));
                Iterator it = this.onButtonCheckedListeners.iterator();
                while (it.hasNext()) {
                    ((TimePickerView$$ExternalSyntheticLambda0) it.next()).onButtonChecked();
                }
            }
        }
        invalidate();
    }

    public void updateChildShapes() {
        int i;
        CornerData cornerData;
        int childCount = getChildCount();
        int childCount2 = getChildCount();
        int i2 = 0;
        while (true) {
            i = -1;
            if (i2 >= childCount2) {
                i2 = -1;
                break;
            } else if (isChildVisible(i2)) {
                break;
            } else {
                i2++;
            }
        }
        int childCount3 = getChildCount() - 1;
        while (true) {
            if (childCount3 < 0) {
                break;
            }
            if (isChildVisible(childCount3)) {
                i = childCount3;
                break;
            }
            childCount3--;
        }
        for (int i3 = 0; i3 < childCount; i3++) {
            MaterialButton materialButton = (MaterialButton) getChildAt(i3);
            if (materialButton.getVisibility() != 8) {
                if (!materialButton.isUsingOriginalBackground()) {
                    throw new IllegalStateException(
                            "Attempted to get ShapeAppearanceModel from a MaterialButton which has"
                                + " an overwritten background.");
                }
                ShapeAppearanceModel.Builder builder =
                        materialButton.materialButtonHelper.shapeAppearanceModel.toBuilder();
                CornerData cornerData2 = (CornerData) ((ArrayList) this.originalCornerData).get(i3);
                if (i2 != i) {
                    boolean z = getOrientation() == 0;
                    AbsoluteCornerSize absoluteCornerSize = CornerData.noCorner;
                    if (i3 == i2) {
                        cornerData =
                                z
                                        ? ViewUtils.isLayoutRtl(this)
                                                ? new CornerData(
                                                        absoluteCornerSize,
                                                        absoluteCornerSize,
                                                        cornerData2.topRight,
                                                        cornerData2.bottomRight)
                                                : new CornerData(
                                                        cornerData2.topLeft,
                                                        cornerData2.bottomLeft,
                                                        absoluteCornerSize,
                                                        absoluteCornerSize)
                                        : new CornerData(
                                                cornerData2.topLeft,
                                                absoluteCornerSize,
                                                cornerData2.topRight,
                                                absoluteCornerSize);
                    } else if (i3 == i) {
                        cornerData =
                                z
                                        ? ViewUtils.isLayoutRtl(this)
                                                ? new CornerData(
                                                        cornerData2.topLeft,
                                                        cornerData2.bottomLeft,
                                                        absoluteCornerSize,
                                                        absoluteCornerSize)
                                                : new CornerData(
                                                        absoluteCornerSize,
                                                        absoluteCornerSize,
                                                        cornerData2.topRight,
                                                        cornerData2.bottomRight)
                                        : new CornerData(
                                                absoluteCornerSize,
                                                cornerData2.bottomLeft,
                                                absoluteCornerSize,
                                                cornerData2.bottomRight);
                    } else {
                        cornerData2 = null;
                    }
                    cornerData2 = cornerData;
                }
                if (cornerData2 == null) {
                    builder.topLeftCornerSize = new AbsoluteCornerSize(0.0f);
                    builder.topRightCornerSize = new AbsoluteCornerSize(0.0f);
                    builder.bottomRightCornerSize = new AbsoluteCornerSize(0.0f);
                    builder.bottomLeftCornerSize = new AbsoluteCornerSize(0.0f);
                } else {
                    builder.topLeftCornerSize = cornerData2.topLeft;
                    builder.bottomLeftCornerSize = cornerData2.bottomLeft;
                    builder.topRightCornerSize = cornerData2.topRight;
                    builder.bottomRightCornerSize = cornerData2.bottomRight;
                }
                materialButton.setShapeAppearanceModel(builder.build());
            }
        }
    }

    public MaterialButtonToggleGroup(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.materialButtonToggleGroupStyle);
    }

    /* JADX WARN: Type inference failed for: r9v5, types: [com.google.android.material.button.MaterialButtonToggleGroup$1] */
    public MaterialButtonToggleGroup(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, 2132084903), attributeSet, i);
        this.originalCornerData = new ArrayList();
        this.pressedStateTracker = new PressedStateTracker();
        this.onButtonCheckedListeners = new LinkedHashSet();
        this.childOrderComparator =
                new Comparator() { // from class:
                                   // com.google.android.material.button.MaterialButtonToggleGroup.1
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        MaterialButton materialButton = (MaterialButton) obj;
                        MaterialButton materialButton2 = (MaterialButton) obj2;
                        int compareTo =
                                Boolean.valueOf(materialButton.checked)
                                        .compareTo(Boolean.valueOf(materialButton2.checked));
                        if (compareTo != 0) {
                            return compareTo;
                        }
                        int compareTo2 =
                                Boolean.valueOf(materialButton.isPressed())
                                        .compareTo(Boolean.valueOf(materialButton2.isPressed()));
                        return compareTo2 != 0
                                ? compareTo2
                                : Integer.valueOf(
                                                MaterialButtonToggleGroup.this.indexOfChild(
                                                        materialButton))
                                        .compareTo(
                                                Integer.valueOf(
                                                        MaterialButtonToggleGroup.this.indexOfChild(
                                                                materialButton2)));
                    }
                };
        this.skipCheckedStateTracker = false;
        this.checkedIds = new HashSet();
        TypedArray obtainStyledAttributes =
                ThemeEnforcement.obtainStyledAttributes(
                        getContext(),
                        attributeSet,
                        R$styleable.MaterialButtonToggleGroup,
                        i,
                        2132084903,
                        new int[0]);
        boolean z = obtainStyledAttributes.getBoolean(3, false);
        if (this.singleSelection != z) {
            this.singleSelection = z;
            updateCheckedIds(new HashSet());
        }
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            ((MaterialButton) getChildAt(i2)).accessibilityClassName =
                    (this.singleSelection ? RadioButton.class : ToggleButton.class).getName();
        }
        this.defaultCheckId = obtainStyledAttributes.getResourceId(1, -1);
        this.selectionRequired = obtainStyledAttributes.getBoolean(2, false);
        setChildrenDrawingOrderEnabled(true);
        setEnabled(obtainStyledAttributes.getBoolean(0, true));
        obtainStyledAttributes.recycle();
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        setImportantForAccessibility(1);
    }
}
