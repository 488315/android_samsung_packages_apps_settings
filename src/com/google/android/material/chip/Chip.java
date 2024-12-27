package com.google.android.material.chip;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.graphics.drawable.WrappedDrawable;
import androidx.core.graphics.drawable.WrappedDrawableApi14;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import androidx.preference.Preference;
import com.google.android.material.R$styleable;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.internal.CheckableGroup;
import com.google.android.material.internal.MaterialCheckable;
import com.google.android.material.internal.TextDrawableHelper;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.resources.TextAppearanceFontCallback;
import com.google.android.material.shape.AbsoluteCornerSize;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import com.samsung.android.knox.net.apn.ApnSettings;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class Chip extends AppCompatCheckBox implements ChipDrawable.Delegate, Shapeable, MaterialCheckable {
    public CharSequence accessibilityClassName;
    public final ChipDrawable chipDrawable;
    public boolean closeIconFocused;
    public boolean closeIconHovered;
    public boolean closeIconPressed;
    public boolean deferredCheckedValue;
    public final boolean ensureMinTouchTargetSize;
    public final AnonymousClass1 fontCallback;
    public InsetDrawable insetBackgroundDrawable;
    public int lastLayoutDirection;
    public int minTouchTargetSize;
    public CompoundButton.OnCheckedChangeListener onCheckedChangeListener;
    public CheckableGroup.AnonymousClass1 onCheckedChangeListenerInternal;
    public final Rect rect;
    public final RectF rectF;
    public RippleDrawable ripple;
    public static final Rect EMPTY_BOUNDS = new Rect();
    public static final int[] SELECTED_STATE = {R.attr.state_selected};
    public static final int[] CHECKABLE_STATE_SET = {R.attr.state_checkable};

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ChipTouchHelper extends ExploreByTouchHelper {
        public ChipTouchHelper(Chip chip) {
            super(chip);
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final int getVirtualViewAt(float f, float f2) {
            Rect rect = Chip.EMPTY_BOUNDS;
            Chip chip = Chip.this;
            return (chip.hasCloseIcon() && chip.getCloseIconTouchBounds().contains(f, f2)) ? 1 : 0;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void getVisibleVirtualViews(List list) {
            ((ArrayList) list).add(0);
            Rect rect = Chip.EMPTY_BOUNDS;
            Chip.this.hasCloseIcon();
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final boolean onPerformActionForVirtualView(int i, int i2) {
            if (i2 == 16) {
                Chip chip = Chip.this;
                if (i == 0) {
                    return chip.performClick();
                }
                if (i == 1) {
                    chip.playSoundEffect(0);
                }
            }
            return false;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void onPopulateNodeForHost(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            Chip chip = Chip.this;
            accessibilityNodeInfoCompat.setCheckable(chip.isCheckable());
            accessibilityNodeInfoCompat.setClickable(chip.isClickable());
            accessibilityNodeInfoCompat.setClassName(chip.getAccessibilityClassName());
            accessibilityNodeInfoCompat.setText(chip.getText());
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void onPopulateNodeForVirtualView(int i, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            CharSequence charSequence = ApnSettings.MVNO_NONE;
            if (i != 1) {
                accessibilityNodeInfoCompat.setContentDescription(ApnSettings.MVNO_NONE);
                accessibilityNodeInfoCompat.setBoundsInParent(Chip.EMPTY_BOUNDS);
                return;
            }
            Chip chip = Chip.this;
            ChipDrawable chipDrawable = chip.chipDrawable;
            CharSequence text = chip.getText();
            Context context = chip.getContext();
            if (!TextUtils.isEmpty(text)) {
                charSequence = text;
            }
            accessibilityNodeInfoCompat.setContentDescription(context.getString(com.android.settings.R.string.mtrl_chip_close_icon_content_description, charSequence).trim());
            RectF closeIconTouchBounds = chip.getCloseIconTouchBounds();
            chip.rect.set((int) closeIconTouchBounds.left, (int) closeIconTouchBounds.top, (int) closeIconTouchBounds.right, (int) closeIconTouchBounds.bottom);
            accessibilityNodeInfoCompat.setBoundsInParent(chip.rect);
            accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
            accessibilityNodeInfoCompat.mInfo.setEnabled(chip.isEnabled());
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void onVirtualViewKeyboardFocusChanged(int i, boolean z) {
            if (i == 1) {
                Chip chip = Chip.this;
                chip.closeIconFocused = z;
                chip.refreshDrawableState();
            }
        }
    }

    public Chip(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public final boolean dispatchHoverEvent(MotionEvent motionEvent) {
        return super.dispatchHoverEvent(motionEvent);
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [boolean, int] */
    @Override // androidx.appcompat.widget.AppCompatCheckBox, android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final void drawableStateChanged() {
        int i;
        super.drawableStateChanged();
        ChipDrawable chipDrawable = this.chipDrawable;
        boolean z = false;
        if (chipDrawable != null && ChipDrawable.isStateful(chipDrawable.closeIcon)) {
            ChipDrawable chipDrawable2 = this.chipDrawable;
            ?? isEnabled = isEnabled();
            int i2 = isEnabled;
            if (this.closeIconFocused) {
                i2 = isEnabled + 1;
            }
            int i3 = i2;
            if (this.closeIconHovered) {
                i3 = i2 + 1;
            }
            int i4 = i3;
            if (this.closeIconPressed) {
                i4 = i3 + 1;
            }
            int i5 = i4;
            if (isChecked()) {
                i5 = i4 + 1;
            }
            int[] iArr = new int[i5];
            if (isEnabled()) {
                iArr[0] = 16842910;
                i = 1;
            } else {
                i = 0;
            }
            if (this.closeIconFocused) {
                iArr[i] = 16842908;
                i++;
            }
            if (this.closeIconHovered) {
                iArr[i] = 16843623;
                i++;
            }
            if (this.closeIconPressed) {
                iArr[i] = 16842919;
                i++;
            }
            if (isChecked()) {
                iArr[i] = 16842913;
            }
            if (!Arrays.equals(chipDrawable2.closeIconStateSet, iArr)) {
                chipDrawable2.closeIconStateSet = iArr;
                if (chipDrawable2.showsCloseIcon()) {
                    z = chipDrawable2.onStateChange(chipDrawable2.getState(), iArr);
                }
            }
        }
        if (z) {
            invalidate();
        }
    }

    public final void ensureAccessibleTouchTarget(int i) {
        this.minTouchTargetSize = i;
        if (!this.ensureMinTouchTargetSize) {
            InsetDrawable insetDrawable = this.insetBackgroundDrawable;
            if (insetDrawable == null) {
                updateBackgroundDrawable();
                return;
            }
            if (insetDrawable != null) {
                this.insetBackgroundDrawable = null;
                setMinWidth(0);
                ChipDrawable chipDrawable = this.chipDrawable;
                setMinHeight((int) (chipDrawable != null ? chipDrawable.chipMinHeight : 0.0f));
                updateBackgroundDrawable();
                return;
            }
            return;
        }
        int max = Math.max(0, i - ((int) this.chipDrawable.chipMinHeight));
        int max2 = Math.max(0, i - this.chipDrawable.getIntrinsicWidth());
        if (max2 <= 0 && max <= 0) {
            InsetDrawable insetDrawable2 = this.insetBackgroundDrawable;
            if (insetDrawable2 == null) {
                updateBackgroundDrawable();
                return;
            }
            if (insetDrawable2 != null) {
                this.insetBackgroundDrawable = null;
                setMinWidth(0);
                ChipDrawable chipDrawable2 = this.chipDrawable;
                setMinHeight((int) (chipDrawable2 != null ? chipDrawable2.chipMinHeight : 0.0f));
                updateBackgroundDrawable();
                return;
            }
            return;
        }
        int i2 = max2 > 0 ? max2 / 2 : 0;
        int i3 = max > 0 ? max / 2 : 0;
        if (this.insetBackgroundDrawable != null) {
            Rect rect = new Rect();
            this.insetBackgroundDrawable.getPadding(rect);
            if (rect.top == i3 && rect.bottom == i3 && rect.left == i2 && rect.right == i2) {
                updateBackgroundDrawable();
                return;
            }
        }
        if (getMinHeight() != i) {
            setMinHeight(i);
        }
        if (getMinWidth() != i) {
            setMinWidth(i);
        }
        this.insetBackgroundDrawable = new InsetDrawable((Drawable) this.chipDrawable, i2, i3, i2, i3);
        updateBackgroundDrawable();
    }

    @Override // android.widget.CheckBox, android.widget.CompoundButton, android.widget.Button, android.widget.TextView, android.view.View
    public final CharSequence getAccessibilityClassName() {
        if (!TextUtils.isEmpty(this.accessibilityClassName)) {
            return this.accessibilityClassName;
        }
        if (!isCheckable()) {
            return isClickable() ? "android.widget.Button" : "android.view.View";
        }
        ViewParent parent = getParent();
        return ((parent instanceof SeslChipGroup) && ((SeslChipGroup) parent).checkableGroup.singleSelection) ? "android.widget.RadioButton" : "android.widget.Button";
    }

    public final RectF getCloseIconTouchBounds() {
        this.rectF.setEmpty();
        hasCloseIcon();
        return this.rectF;
    }

    @Override // android.widget.TextView
    public final TextUtils.TruncateAt getEllipsize() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            return chipDrawable.truncateAt;
        }
        return null;
    }

    public final boolean hasCloseIcon() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            Object obj = chipDrawable.closeIcon;
            if (obj == null) {
                obj = null;
            } else if (obj instanceof WrappedDrawable) {
                obj = ((WrappedDrawableApi14) ((WrappedDrawable) obj)).mDrawable;
            }
            if (obj != null) {
                return true;
            }
        }
        return false;
    }

    public final boolean isCheckable() {
        ChipDrawable chipDrawable = this.chipDrawable;
        return chipDrawable != null && chipDrawable.checkable;
    }

    @Override // android.widget.TextView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this, this.chipDrawable);
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 2);
        if (isChecked()) {
            CheckBox.mergeDrawableStates(onCreateDrawableState, SELECTED_STATE);
        }
        if (isCheckable()) {
            CheckBox.mergeDrawableStates(onCreateDrawableState, CHECKABLE_STATE_SET);
        }
        return onCreateDrawableState;
    }

    @Override // android.widget.TextView, android.view.View
    public final void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
    }

    @Override // android.view.View
    public final boolean onHoverEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 7) {
            boolean contains = getCloseIconTouchBounds().contains(motionEvent.getX(), motionEvent.getY());
            if (this.closeIconHovered != contains) {
                this.closeIconHovered = contains;
                refreshDrawableState();
            }
        } else if (actionMasked == 10 && this.closeIconHovered) {
            this.closeIconHovered = false;
            refreshDrawableState();
        }
        return super.onHoverEvent(motionEvent);
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        int i;
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(getAccessibilityClassName());
        accessibilityNodeInfo.setCheckable(isCheckable());
        accessibilityNodeInfo.setClickable(isClickable());
        if (getParent() instanceof SeslChipGroup) {
            SeslChipGroup seslChipGroup = (SeslChipGroup) getParent();
            if (seslChipGroup.singleLine) {
                i = 0;
                for (int i2 = 0; i2 < seslChipGroup.getChildCount(); i2++) {
                    View childAt = seslChipGroup.getChildAt(i2);
                    if ((childAt instanceof Chip) && seslChipGroup.getChildAt(i2).getVisibility() == 0) {
                        if (((Chip) childAt) == this) {
                            break;
                        } else {
                            i++;
                        }
                    }
                }
            }
            i = -1;
            Object tag = getTag(com.android.settings.R.id.row_index_key);
            accessibilityNodeInfo.setCollectionItemInfo((AccessibilityNodeInfo.CollectionItemInfo) AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(isChecked(), tag instanceof Integer ? ((Integer) tag).intValue() : -1, 1, i, 1).mInfo);
        }
    }

    @Override // android.widget.Button, android.widget.TextView, android.view.View
    public final PointerIcon onResolvePointerIcon(MotionEvent motionEvent, int i) {
        return (getCloseIconTouchBounds().contains(motionEvent.getX(), motionEvent.getY()) && isEnabled()) ? PointerIcon.getSystemIcon(getContext(), 1002) : super.onResolvePointerIcon(motionEvent, i);
    }

    @Override // android.widget.TextView, android.view.View
    public final void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        if (this.lastLayoutDirection != i) {
            this.lastLayoutDirection = i;
            updatePaddingInternal();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x001e, code lost:
    
        if (r0 != 3) goto L27;
     */
    /* JADX WARN: Removed duplicated region for block: B:11:0x003e  */
    @Override // android.widget.TextView, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onTouchEvent(android.view.MotionEvent r6) {
        /*
            r5 = this;
            int r0 = r6.getActionMasked()
            android.graphics.RectF r1 = r5.getCloseIconTouchBounds()
            float r2 = r6.getX()
            float r3 = r6.getY()
            boolean r1 = r1.contains(r2, r3)
            r2 = 1
            r3 = 0
            if (r0 == 0) goto L44
            if (r0 == r2) goto L30
            r4 = 2
            if (r0 == r4) goto L21
            r1 = 3
            if (r0 == r1) goto L39
            goto L50
        L21:
            boolean r0 = r5.closeIconPressed
            if (r0 == 0) goto L50
            if (r1 != 0) goto L2e
            if (r0 == 0) goto L2e
            r5.closeIconPressed = r3
            r5.refreshDrawableState()
        L2e:
            r0 = r2
            goto L51
        L30:
            boolean r0 = r5.closeIconPressed
            if (r0 == 0) goto L39
            r5.playSoundEffect(r3)
            r0 = r2
            goto L3a
        L39:
            r0 = r3
        L3a:
            boolean r1 = r5.closeIconPressed
            if (r1 == 0) goto L51
            r5.closeIconPressed = r3
            r5.refreshDrawableState()
            goto L51
        L44:
            if (r1 == 0) goto L50
            boolean r0 = r5.closeIconPressed
            if (r0 == r2) goto L2e
            r5.closeIconPressed = r2
            r5.refreshDrawableState()
            goto L2e
        L50:
            r0 = r3
        L51:
            if (r0 != 0) goto L5b
            boolean r5 = super.onTouchEvent(r6)
            if (r5 == 0) goto L5a
            goto L5b
        L5a:
            r2 = r3
        L5b:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.chip.Chip.onTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.view.View
    public final void setBackground(Drawable drawable) {
        Drawable drawable2 = this.insetBackgroundDrawable;
        if (drawable2 == null) {
            drawable2 = this.chipDrawable;
        }
        if (drawable == drawable2 || drawable == this.ripple) {
            super.setBackground(drawable);
        } else {
            Log.w("Chip", "Do not set the background; Chip manages its own background drawable.");
        }
    }

    @Override // android.view.View
    public final void setBackgroundColor(int i) {
        Log.w("Chip", "Do not set the background color; Chip manages its own background drawable.");
    }

    @Override // androidx.appcompat.widget.AppCompatCheckBox, android.view.View
    public final void setBackgroundDrawable(Drawable drawable) {
        Drawable drawable2 = this.insetBackgroundDrawable;
        if (drawable2 == null) {
            drawable2 = this.chipDrawable;
        }
        if (drawable == drawable2 || drawable == this.ripple) {
            super.setBackgroundDrawable(drawable);
        } else {
            Log.w("Chip", "Do not set the background drawable; Chip manages its own background drawable.");
        }
    }

    @Override // androidx.appcompat.widget.AppCompatCheckBox, android.view.View
    public final void setBackgroundResource(int i) {
        Log.w("Chip", "Do not set the background resource; Chip manages its own background drawable.");
    }

    @Override // android.view.View
    public final void setBackgroundTintList(ColorStateList colorStateList) {
        Log.w("Chip", "Do not set the background tint list; Chip manages its own background drawable.");
    }

    @Override // android.view.View
    public final void setBackgroundTintMode(PorterDuff.Mode mode) {
        Log.w("Chip", "Do not set the background tint mode; Chip manages its own background drawable.");
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    public final void setChecked(boolean z) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) {
            this.deferredCheckedValue = z;
        } else if (chipDrawable.checkable) {
            super.setChecked(z);
        }
    }

    @Override // androidx.appcompat.widget.AppCompatCheckBox, android.widget.TextView
    public final void setCompoundDrawables(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        if (drawable != null) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        }
        if (drawable3 != null) {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
        super.setCompoundDrawables(drawable, drawable2, drawable3, drawable4);
    }

    @Override // androidx.appcompat.widget.AppCompatCheckBox, android.widget.TextView
    public final void setCompoundDrawablesRelative(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        if (drawable != null) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        }
        if (drawable3 != null) {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
        super.setCompoundDrawablesRelative(drawable, drawable2, drawable3, drawable4);
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawablesRelativeWithIntrinsicBounds(int i, int i2, int i3, int i4) {
        if (i != 0) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        }
        if (i3 != 0) {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
        super.setCompoundDrawablesRelativeWithIntrinsicBounds(i, i2, i3, i4);
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawablesWithIntrinsicBounds(int i, int i2, int i3, int i4) {
        if (i != 0) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        }
        if (i3 != 0) {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
        super.setCompoundDrawablesWithIntrinsicBounds(i, i2, i3, i4);
    }

    @Override // android.view.View
    public final void setElevation(float f) {
        super.setElevation(f);
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setElevation(f);
        }
    }

    @Override // android.widget.TextView
    public final void setEllipsize(TextUtils.TruncateAt truncateAt) {
        if (this.chipDrawable == null) {
            return;
        }
        if (truncateAt == TextUtils.TruncateAt.MARQUEE) {
            throw new UnsupportedOperationException("Text within a chip are not allowed to scroll.");
        }
        super.setEllipsize(truncateAt);
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.truncateAt = truncateAt;
        }
    }

    @Override // android.widget.TextView
    public final void setGravity(int i) {
        if (i != 8388627) {
            Log.w("Chip", "Chip text must be vertically center and start aligned");
        } else {
            super.setGravity(i);
        }
    }

    @Override // android.view.View
    public final void setLayoutDirection(int i) {
        if (this.chipDrawable == null) {
            return;
        }
        super.setLayoutDirection(i);
    }

    @Override // android.widget.TextView
    public final void setLines(int i) {
        if (i > 1) {
            throw new UnsupportedOperationException("Chip does not support multi-line text");
        }
        super.setLines(i);
    }

    @Override // android.widget.TextView
    public final void setMaxLines(int i) {
        if (i > 1) {
            throw new UnsupportedOperationException("Chip does not support multi-line text");
        }
        super.setMaxLines(i);
    }

    @Override // android.widget.TextView
    public final void setMaxWidth(int i) {
        super.setMaxWidth(i);
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.maxWidth = i;
        }
    }

    @Override // android.widget.TextView
    public final void setMinLines(int i) {
        if (i > 1) {
            throw new UnsupportedOperationException("Chip does not support multi-line text");
        }
        super.setMinLines(i);
    }

    @Override // android.widget.CompoundButton
    public final void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    @Override // com.google.android.material.shape.Shapeable
    public final void setShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel) {
        this.chipDrawable.setShapeAppearanceModel(shapeAppearanceModel);
    }

    @Override // android.widget.TextView
    public final void setSingleLine(boolean z) {
        if (!z) {
            throw new UnsupportedOperationException("Chip does not support multi-line text");
        }
        super.setSingleLine(z);
    }

    @Override // android.widget.TextView
    public final void setText(CharSequence charSequence, TextView.BufferType bufferType) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) {
            return;
        }
        if (charSequence == null) {
            charSequence = ApnSettings.MVNO_NONE;
        }
        super.setText(chipDrawable.shouldDrawText ? null : charSequence, bufferType);
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 == null || TextUtils.equals(chipDrawable2.text, charSequence)) {
            return;
        }
        chipDrawable2.text = charSequence;
        chipDrawable2.textDrawableHelper.textSizeDirty = true;
        chipDrawable2.invalidateSelf();
        chipDrawable2.onSizeChange();
    }

    @Override // android.widget.TextView
    public final void setTextAppearance(Context context, int i) {
        super.setTextAppearance(context, i);
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.textDrawableHelper.setTextAppearance(new TextAppearance(chipDrawable.context, i), chipDrawable.context);
        }
        updateTextPaintDrawState();
    }

    @Override // android.widget.TextView
    public final void setTextSize(int i, float f) {
        super.setTextSize(i, f);
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            float applyDimension = TypedValue.applyDimension(i, f, getResources().getDisplayMetrics());
            TextDrawableHelper textDrawableHelper = chipDrawable.textDrawableHelper;
            TextAppearance textAppearance = textDrawableHelper.textAppearance;
            if (textAppearance != null) {
                textAppearance.textSize = applyDimension;
                textDrawableHelper.textPaint.setTextSize(applyDimension);
                chipDrawable.onTextSizeChange();
            }
        }
        updateTextPaintDrawState();
    }

    public final void updateBackgroundDrawable() {
        ColorStateList colorStateList = this.chipDrawable.rippleColor;
        if (colorStateList == null) {
            colorStateList = ColorStateList.valueOf(0);
        }
        Drawable drawable = this.insetBackgroundDrawable;
        if (drawable == null) {
            drawable = this.chipDrawable;
        }
        this.ripple = new RippleDrawable(colorStateList, drawable, null);
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable.useCompatRipple) {
            chipDrawable.useCompatRipple = false;
            chipDrawable.compatRippleColor = null;
            chipDrawable.onStateChange(chipDrawable.getState());
        }
        RippleDrawable rippleDrawable = this.ripple;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        setBackground(rippleDrawable);
        updatePaddingInternal();
    }

    public final void updatePaddingInternal() {
        ChipDrawable chipDrawable;
        if (TextUtils.isEmpty(getText()) || (chipDrawable = this.chipDrawable) == null) {
            return;
        }
        int calculateCloseIconWidth = (int) (chipDrawable.calculateCloseIconWidth() + chipDrawable.chipEndPadding + chipDrawable.textEndPadding);
        ChipDrawable chipDrawable2 = this.chipDrawable;
        int calculateChipIconWidth = (int) (chipDrawable2.calculateChipIconWidth() + chipDrawable2.chipStartPadding + chipDrawable2.textStartPadding);
        if (this.insetBackgroundDrawable != null) {
            Rect rect = new Rect();
            this.insetBackgroundDrawable.getPadding(rect);
            calculateChipIconWidth += rect.left;
            calculateCloseIconWidth += rect.right;
        }
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        setPaddingRelative(calculateChipIconWidth, paddingTop, calculateCloseIconWidth, paddingBottom);
    }

    public final void updateTextPaintDrawState() {
        TextPaint paint = getPaint();
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            paint.drawableState = chipDrawable.getState();
        }
        ChipDrawable chipDrawable2 = this.chipDrawable;
        TextAppearance textAppearance = chipDrawable2 != null ? chipDrawable2.textDrawableHelper.textAppearance : null;
        if (textAppearance != null) {
            textAppearance.updateDrawState(getContext(), paint, this.fontCallback);
        }
    }

    public Chip(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, com.android.settings.R.attr.chipStyle);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.google.android.material.chip.Chip$1] */
    public Chip(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, 2132084885), attributeSet, i);
        Drawable drawable;
        Drawable drawable2;
        ChipDrawable chipDrawable;
        ColorStateList colorStateList;
        Drawable drawable3;
        int resourceId;
        this.rect = new Rect();
        this.rectF = new RectF();
        this.fontCallback = new TextAppearanceFontCallback() { // from class: com.google.android.material.chip.Chip.1
            @Override // com.google.android.material.resources.TextAppearanceFontCallback
            public final void onFontRetrieved(Typeface typeface, boolean z) {
                Chip chip = Chip.this;
                ChipDrawable chipDrawable2 = chip.chipDrawable;
                chip.setText(chipDrawable2.shouldDrawText ? chipDrawable2.text : chip.getText());
                chip.requestLayout();
                chip.invalidate();
            }

            @Override // com.google.android.material.resources.TextAppearanceFontCallback
            public final void onFontRetrievalFailed(int i2) {
            }
        };
        Context context2 = getContext();
        if (attributeSet != null) {
            if (attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "background") != null) {
                Log.w("Chip", "Do not set the background; Chip manages its own background drawable.");
            }
            if (attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "drawableLeft") == null) {
                if (attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "drawableStart") == null) {
                    if (attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "drawableEnd") == null) {
                        if (attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "drawableRight") == null) {
                            if (attributeSet.getAttributeBooleanValue("http://schemas.android.com/apk/res/android", "singleLine", true) && attributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "lines", 1) == 1 && attributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "minLines", 1) == 1 && attributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "maxLines", 1) == 1) {
                                if (attributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "gravity", 8388627) != 8388627) {
                                    Log.w("Chip", "Chip text must be vertically center and start aligned");
                                }
                            } else {
                                throw new UnsupportedOperationException("Chip does not support multi-line text");
                            }
                        } else {
                            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
                        }
                    } else {
                        throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
                    }
                } else {
                    throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
                }
            } else {
                throw new UnsupportedOperationException("Please set left drawable using R.attr#chipIcon.");
            }
        }
        ChipDrawable chipDrawable2 = new ChipDrawable(context2, attributeSet, i);
        Context context3 = chipDrawable2.context;
        int[] iArr = R$styleable.Chip;
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context3, attributeSet, iArr, i, 2132084885, new int[0]);
        chipDrawable2.isShapeThemingEnabled = obtainStyledAttributes.hasValue(37);
        ColorStateList colorStateList2 = MaterialResources.getColorStateList(chipDrawable2.context, obtainStyledAttributes, 24);
        if (chipDrawable2.chipSurfaceColor != colorStateList2) {
            chipDrawable2.chipSurfaceColor = colorStateList2;
            chipDrawable2.onStateChange(chipDrawable2.getState());
        }
        ColorStateList colorStateList3 = MaterialResources.getColorStateList(chipDrawable2.context, obtainStyledAttributes, 11);
        if (chipDrawable2.chipBackgroundColor != colorStateList3) {
            chipDrawable2.chipBackgroundColor = colorStateList3;
            chipDrawable2.onStateChange(chipDrawable2.getState());
        }
        float dimension = obtainStyledAttributes.getDimension(19, 0.0f);
        if (chipDrawable2.chipMinHeight != dimension) {
            chipDrawable2.chipMinHeight = dimension;
            chipDrawable2.invalidateSelf();
            chipDrawable2.onSizeChange();
        }
        if (obtainStyledAttributes.hasValue(12)) {
            float dimension2 = obtainStyledAttributes.getDimension(12, 0.0f);
            if (chipDrawable2.chipCornerRadius != dimension2) {
                chipDrawable2.chipCornerRadius = dimension2;
                ShapeAppearanceModel.Builder builder = chipDrawable2.drawableState.shapeAppearanceModel.toBuilder();
                builder.topLeftCornerSize = new AbsoluteCornerSize(dimension2);
                builder.topRightCornerSize = new AbsoluteCornerSize(dimension2);
                builder.bottomRightCornerSize = new AbsoluteCornerSize(dimension2);
                builder.bottomLeftCornerSize = new AbsoluteCornerSize(dimension2);
                chipDrawable2.setShapeAppearanceModel(builder.build());
            }
        }
        ColorStateList colorStateList4 = MaterialResources.getColorStateList(chipDrawable2.context, obtainStyledAttributes, 22);
        if (chipDrawable2.chipStrokeColor != colorStateList4) {
            chipDrawable2.chipStrokeColor = colorStateList4;
            if (chipDrawable2.isShapeThemingEnabled) {
                MaterialShapeDrawable.MaterialShapeDrawableState materialShapeDrawableState = chipDrawable2.drawableState;
                if (materialShapeDrawableState.strokeColor != colorStateList4) {
                    materialShapeDrawableState.strokeColor = colorStateList4;
                    chipDrawable2.onStateChange(chipDrawable2.getState());
                }
            }
            chipDrawable2.onStateChange(chipDrawable2.getState());
        }
        float dimension3 = obtainStyledAttributes.getDimension(23, 0.0f);
        if (chipDrawable2.chipStrokeWidth != dimension3) {
            chipDrawable2.chipStrokeWidth = dimension3;
            chipDrawable2.chipPaint.setStrokeWidth(dimension3);
            if (chipDrawable2.isShapeThemingEnabled) {
                chipDrawable2.drawableState.strokeWidth = dimension3;
                chipDrawable2.invalidateSelf();
            }
            chipDrawable2.invalidateSelf();
        }
        ColorStateList colorStateList5 = MaterialResources.getColorStateList(chipDrawable2.context, obtainStyledAttributes, 36);
        if (chipDrawable2.rippleColor != colorStateList5) {
            chipDrawable2.rippleColor = colorStateList5;
            if (!chipDrawable2.useCompatRipple) {
                colorStateList5 = null;
            } else if (colorStateList5 == null) {
                colorStateList5 = ColorStateList.valueOf(0);
            }
            chipDrawable2.compatRippleColor = colorStateList5;
            chipDrawable2.onStateChange(chipDrawable2.getState());
        }
        CharSequence text = obtainStyledAttributes.getText(5);
        text = text == null ? ApnSettings.MVNO_NONE : text;
        if (!TextUtils.equals(chipDrawable2.text, text)) {
            chipDrawable2.text = text;
            chipDrawable2.textDrawableHelper.textSizeDirty = true;
            chipDrawable2.invalidateSelf();
            chipDrawable2.onSizeChange();
        }
        TextAppearance textAppearance = (!obtainStyledAttributes.hasValue(0) || (resourceId = obtainStyledAttributes.getResourceId(0, 0)) == 0) ? null : new TextAppearance(chipDrawable2.context, resourceId);
        textAppearance.textSize = obtainStyledAttributes.getDimension(1, textAppearance.textSize);
        chipDrawable2.textDrawableHelper.setTextAppearance(textAppearance, chipDrawable2.context);
        int i2 = obtainStyledAttributes.getInt(3, 0);
        if (i2 == 1) {
            chipDrawable2.truncateAt = TextUtils.TruncateAt.START;
        } else if (i2 == 2) {
            chipDrawable2.truncateAt = TextUtils.TruncateAt.MIDDLE;
        } else if (i2 == 3) {
            chipDrawable2.truncateAt = TextUtils.TruncateAt.END;
        }
        chipDrawable2.setChipIconVisible(obtainStyledAttributes.getBoolean(18, false));
        if (attributeSet != null && attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "chipIconEnabled") != null && attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "chipIconVisible") == null) {
            chipDrawable2.setChipIconVisible(obtainStyledAttributes.getBoolean(15, false));
        }
        Drawable drawable4 = MaterialResources.getDrawable(chipDrawable2.context, obtainStyledAttributes, 14);
        Drawable drawable5 = chipDrawable2.chipIcon;
        if (drawable5 != 0) {
            boolean z = drawable5 instanceof WrappedDrawable;
            drawable = drawable5;
            if (z) {
                drawable = ((WrappedDrawableApi14) ((WrappedDrawable) drawable5)).mDrawable;
            }
        } else {
            drawable = null;
        }
        if (drawable != drawable4) {
            float calculateChipIconWidth = chipDrawable2.calculateChipIconWidth();
            chipDrawable2.chipIcon = drawable4 != null ? drawable4.mutate() : null;
            float calculateChipIconWidth2 = chipDrawable2.calculateChipIconWidth();
            ChipDrawable.unapplyChildDrawable(drawable);
            if (chipDrawable2.showsChipIcon()) {
                chipDrawable2.applyChildDrawable(chipDrawable2.chipIcon);
            }
            chipDrawable2.invalidateSelf();
            if (calculateChipIconWidth != calculateChipIconWidth2) {
                chipDrawable2.onSizeChange();
            }
        }
        if (obtainStyledAttributes.hasValue(17)) {
            ColorStateList colorStateList6 = MaterialResources.getColorStateList(chipDrawable2.context, obtainStyledAttributes, 17);
            chipDrawable2.hasChipIconTint = true;
            if (chipDrawable2.chipIconTint != colorStateList6) {
                chipDrawable2.chipIconTint = colorStateList6;
                if (chipDrawable2.showsChipIcon()) {
                    chipDrawable2.chipIcon.setTintList(colorStateList6);
                }
                chipDrawable2.onStateChange(chipDrawable2.getState());
            }
        }
        float dimension4 = obtainStyledAttributes.getDimension(16, -1.0f);
        if (chipDrawable2.chipIconSize != dimension4) {
            float calculateChipIconWidth3 = chipDrawable2.calculateChipIconWidth();
            chipDrawable2.chipIconSize = dimension4;
            float calculateChipIconWidth4 = chipDrawable2.calculateChipIconWidth();
            chipDrawable2.invalidateSelf();
            if (calculateChipIconWidth3 != calculateChipIconWidth4) {
                chipDrawable2.onSizeChange();
            }
        }
        chipDrawable2.setCloseIconVisible(obtainStyledAttributes.getBoolean(31, false));
        if (attributeSet != null && attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "closeIconEnabled") != null && attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "closeIconVisible") == null) {
            chipDrawable2.setCloseIconVisible(obtainStyledAttributes.getBoolean(26, false));
        }
        Drawable drawable6 = MaterialResources.getDrawable(chipDrawable2.context, obtainStyledAttributes, 25);
        Drawable drawable7 = chipDrawable2.closeIcon;
        if (drawable7 != 0) {
            boolean z2 = drawable7 instanceof WrappedDrawable;
            drawable2 = drawable7;
            if (z2) {
                drawable2 = ((WrappedDrawableApi14) ((WrappedDrawable) drawable7)).mDrawable;
            }
        } else {
            drawable2 = null;
        }
        if (drawable2 != drawable6) {
            float calculateCloseIconWidth = chipDrawable2.calculateCloseIconWidth();
            chipDrawable2.closeIcon = drawable6 != null ? drawable6.mutate() : null;
            ColorStateList colorStateList7 = chipDrawable2.rippleColor;
            chipDrawable2.closeIconRipple = new RippleDrawable(colorStateList7 == null ? ColorStateList.valueOf(0) : colorStateList7, chipDrawable2.closeIcon, ChipDrawable.closeIconRippleMask);
            float calculateCloseIconWidth2 = chipDrawable2.calculateCloseIconWidth();
            ChipDrawable.unapplyChildDrawable(drawable2);
            if (chipDrawable2.showsCloseIcon()) {
                chipDrawable2.applyChildDrawable(chipDrawable2.closeIcon);
            }
            chipDrawable2.invalidateSelf();
            if (calculateCloseIconWidth != calculateCloseIconWidth2) {
                chipDrawable2.onSizeChange();
            }
        }
        ColorStateList colorStateList8 = MaterialResources.getColorStateList(chipDrawable2.context, obtainStyledAttributes, 30);
        if (chipDrawable2.closeIconTint != colorStateList8) {
            chipDrawable2.closeIconTint = colorStateList8;
            if (chipDrawable2.showsCloseIcon()) {
                chipDrawable2.closeIcon.setTintList(colorStateList8);
            }
            chipDrawable2.onStateChange(chipDrawable2.getState());
        }
        float dimension5 = obtainStyledAttributes.getDimension(28, 0.0f);
        if (chipDrawable2.closeIconSize != dimension5) {
            chipDrawable2.closeIconSize = dimension5;
            chipDrawable2.invalidateSelf();
            if (chipDrawable2.showsCloseIcon()) {
                chipDrawable2.onSizeChange();
            }
        }
        boolean z3 = obtainStyledAttributes.getBoolean(6, false);
        if (chipDrawable2.checkable != z3) {
            chipDrawable2.checkable = z3;
            float calculateChipIconWidth5 = chipDrawable2.calculateChipIconWidth();
            if (!z3 && chipDrawable2.currentChecked) {
                chipDrawable2.currentChecked = false;
            }
            float calculateChipIconWidth6 = chipDrawable2.calculateChipIconWidth();
            chipDrawable2.invalidateSelf();
            if (calculateChipIconWidth5 != calculateChipIconWidth6) {
                chipDrawable2.onSizeChange();
            }
        }
        chipDrawable2.setCheckedIconVisible(obtainStyledAttributes.getBoolean(10, false));
        if (attributeSet != null && attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "checkedIconEnabled") != null && attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "checkedIconVisible") == null) {
            chipDrawable2.setCheckedIconVisible(obtainStyledAttributes.getBoolean(8, false));
        }
        Drawable drawable8 = MaterialResources.getDrawable(chipDrawable2.context, obtainStyledAttributes, 7);
        if (chipDrawable2.checkedIcon != drawable8) {
            float calculateChipIconWidth7 = chipDrawable2.calculateChipIconWidth();
            chipDrawable2.checkedIcon = drawable8;
            float calculateChipIconWidth8 = chipDrawable2.calculateChipIconWidth();
            ChipDrawable.unapplyChildDrawable(chipDrawable2.checkedIcon);
            chipDrawable2.applyChildDrawable(chipDrawable2.checkedIcon);
            chipDrawable2.invalidateSelf();
            if (calculateChipIconWidth7 != calculateChipIconWidth8) {
                chipDrawable2.onSizeChange();
            }
        }
        if (obtainStyledAttributes.hasValue(9) && chipDrawable2.checkedIconTint != (colorStateList = MaterialResources.getColorStateList(chipDrawable2.context, obtainStyledAttributes, 9))) {
            chipDrawable2.checkedIconTint = colorStateList;
            if (chipDrawable2.checkedIconVisible && (drawable3 = chipDrawable2.checkedIcon) != null && chipDrawable2.checkable) {
                drawable3.setTintList(colorStateList);
            }
            chipDrawable2.onStateChange(chipDrawable2.getState());
        }
        MotionSpec.createFromAttribute(chipDrawable2.context, obtainStyledAttributes, 39);
        MotionSpec.createFromAttribute(chipDrawable2.context, obtainStyledAttributes, 33);
        float dimension6 = obtainStyledAttributes.getDimension(21, 0.0f);
        if (chipDrawable2.chipStartPadding != dimension6) {
            chipDrawable2.chipStartPadding = dimension6;
            chipDrawable2.invalidateSelf();
            chipDrawable2.onSizeChange();
        }
        float dimension7 = obtainStyledAttributes.getDimension(35, 0.0f);
        if (chipDrawable2.iconStartPadding != dimension7) {
            float calculateChipIconWidth9 = chipDrawable2.calculateChipIconWidth();
            chipDrawable2.iconStartPadding = dimension7;
            float calculateChipIconWidth10 = chipDrawable2.calculateChipIconWidth();
            chipDrawable2.invalidateSelf();
            if (calculateChipIconWidth9 != calculateChipIconWidth10) {
                chipDrawable2.onSizeChange();
            }
        }
        float dimension8 = obtainStyledAttributes.getDimension(34, 0.0f);
        if (chipDrawable2.iconEndPadding != dimension8) {
            float calculateChipIconWidth11 = chipDrawable2.calculateChipIconWidth();
            chipDrawable2.iconEndPadding = dimension8;
            float calculateChipIconWidth12 = chipDrawable2.calculateChipIconWidth();
            chipDrawable2.invalidateSelf();
            if (calculateChipIconWidth11 != calculateChipIconWidth12) {
                chipDrawable2.onSizeChange();
            }
        }
        float dimension9 = obtainStyledAttributes.getDimension(41, 0.0f);
        if (chipDrawable2.textStartPadding != dimension9) {
            chipDrawable2.textStartPadding = dimension9;
            chipDrawable2.invalidateSelf();
            chipDrawable2.onSizeChange();
        }
        float dimension10 = obtainStyledAttributes.getDimension(40, 0.0f);
        if (chipDrawable2.textEndPadding != dimension10) {
            chipDrawable2.textEndPadding = dimension10;
            chipDrawable2.invalidateSelf();
            chipDrawable2.onSizeChange();
        }
        float dimension11 = obtainStyledAttributes.getDimension(29, 0.0f);
        if (chipDrawable2.closeIconStartPadding != dimension11) {
            chipDrawable2.closeIconStartPadding = dimension11;
            chipDrawable2.invalidateSelf();
            if (chipDrawable2.showsCloseIcon()) {
                chipDrawable2.onSizeChange();
            }
        }
        float dimension12 = obtainStyledAttributes.getDimension(27, 0.0f);
        if (chipDrawable2.closeIconEndPadding != dimension12) {
            chipDrawable2.closeIconEndPadding = dimension12;
            chipDrawable2.invalidateSelf();
            if (chipDrawable2.showsCloseIcon()) {
                chipDrawable2.onSizeChange();
            }
        }
        float dimension13 = obtainStyledAttributes.getDimension(13, 0.0f);
        if (chipDrawable2.chipEndPadding != dimension13) {
            chipDrawable2.chipEndPadding = dimension13;
            chipDrawable2.invalidateSelf();
            chipDrawable2.onSizeChange();
        }
        chipDrawable2.maxWidth = obtainStyledAttributes.getDimensionPixelSize(4, Preference.DEFAULT_ORDER);
        obtainStyledAttributes.recycle();
        ThemeEnforcement.checkCompatibleTheme(context2, attributeSet, i, 2132084885);
        ThemeEnforcement.checkTextAppearance(context2, attributeSet, iArr, i, 2132084885, new int[0]);
        TypedArray obtainStyledAttributes2 = context2.obtainStyledAttributes(attributeSet, iArr, i, 2132084885);
        this.ensureMinTouchTargetSize = obtainStyledAttributes2.getBoolean(32, false);
        this.minTouchTargetSize = (int) Math.ceil(obtainStyledAttributes2.getDimension(20, (float) Math.ceil(ViewUtils.dpToPx(getContext(), 48))));
        obtainStyledAttributes2.recycle();
        ChipDrawable chipDrawable3 = this.chipDrawable;
        if (chipDrawable3 != chipDrawable2) {
            if (chipDrawable3 != null) {
                chipDrawable3.delegate = new WeakReference(null);
            }
            this.chipDrawable = chipDrawable2;
            chipDrawable2.shouldDrawText = false;
            chipDrawable2.delegate = new WeakReference(this);
            ensureAccessibleTouchTarget(this.minTouchTargetSize);
        }
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        chipDrawable2.setElevation(ViewCompat.Api21Impl.getElevation(this));
        ThemeEnforcement.checkCompatibleTheme(context2, attributeSet, i, 2132084885);
        ThemeEnforcement.checkTextAppearance(context2, attributeSet, iArr, i, 2132084885, new int[0]);
        TypedArray obtainStyledAttributes3 = context2.obtainStyledAttributes(attributeSet, iArr, i, 2132084885);
        boolean hasValue = obtainStyledAttributes3.hasValue(37);
        obtainStyledAttributes3.recycle();
        new ChipTouchHelper(this);
        if (hasCloseIcon() && (chipDrawable = this.chipDrawable) != null) {
            boolean z4 = chipDrawable.closeIconVisible;
        }
        ViewCompat.setAccessibilityDelegate(this, null);
        if (!hasValue) {
            setOutlineProvider(new ViewOutlineProvider() { // from class: com.google.android.material.chip.Chip.2
                @Override // android.view.ViewOutlineProvider
                public final void getOutline(View view, Outline outline) {
                    ChipDrawable chipDrawable4 = Chip.this.chipDrawable;
                    if (chipDrawable4 != null) {
                        chipDrawable4.getOutline(outline);
                    } else {
                        outline.setAlpha(0.0f);
                    }
                }
            });
        }
        setChecked(this.deferredCheckedValue);
        setText(chipDrawable2.text);
        setEllipsize(chipDrawable2.truncateAt);
        updateTextPaintDrawState();
        if (!this.chipDrawable.shouldDrawText) {
            setLines(1);
            setHorizontallyScrolling(true);
        }
        super.setGravity(8388627);
        updatePaddingInternal();
        if (this.ensureMinTouchTargetSize) {
            setMinHeight(this.minTouchTargetSize);
        }
        this.lastLayoutDirection = getLayoutDirection();
        super.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.google.android.material.chip.Chip$$ExternalSyntheticLambda0
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z5) {
                Chip chip = Chip.this;
                CheckableGroup.AnonymousClass1 anonymousClass1 = chip.onCheckedChangeListenerInternal;
                if (anonymousClass1 != null) {
                    CheckableGroup checkableGroup = CheckableGroup.this;
                    if (!z5 ? checkableGroup.uncheckInternal(chip, checkableGroup.selectionRequired) : checkableGroup.checkInternal(chip)) {
                        checkableGroup.onCheckedStateChanged();
                    }
                }
                CompoundButton.OnCheckedChangeListener onCheckedChangeListener = chip.onCheckedChangeListener;
                if (onCheckedChangeListener != null) {
                    onCheckedChangeListener.onCheckedChanged(compoundButton, z5);
                }
            }
        });
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawablesRelativeWithIntrinsicBounds(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        if (drawable != null) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        }
        if (drawable3 == null) {
            super.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
            return;
        }
        throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawablesWithIntrinsicBounds(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        if (drawable != null) {
            throw new UnsupportedOperationException("Please set left drawable using R.attr#chipIcon.");
        }
        if (drawable3 == null) {
            super.setCompoundDrawablesWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
            return;
        }
        throw new UnsupportedOperationException("Please set right drawable using R.attr#closeIcon.");
    }

    @Override // android.widget.TextView
    public final void setTextAppearance(int i) {
        super.setTextAppearance(i);
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.textDrawableHelper.setTextAppearance(new TextAppearance(chipDrawable.context, i), chipDrawable.context);
        }
        updateTextPaintDrawState();
    }
}
