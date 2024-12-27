package com.android.settings.localepicker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.LocaleList;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.core.view.ViewCompat;
import androidx.picker.widget.SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.android.internal.app.LocalePicker;
import com.android.internal.app.LocaleStore;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.shortcut.ShortcutsUpdateTask;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class LocaleDragAndDropAdapter extends RecyclerView.Adapter {
    public final Context mContext;
    public DividerDecoration mDividerDecoration;
    public final List mFeedItemList;
    public final ItemTouchHelper mItemTouchHelper;
    public OnCheckChangeListener mListener;
    public LocaleList mLocalesToSetNext;
    public Menu mMenu;
    public boolean mShowingAddDialog;
    public RecyclerView mParentView = null;
    public boolean mRemoveMode = false;
    public boolean mDragEnabled = true;
    public NumberFormat mNumberFormatter = NumberFormat.getNumberInstance();
    public boolean mSelectMode = false;
    public boolean mEditMode = false;
    public AlertDialog mNofiticationDialog = null;
    public String mTempAddLocaleId = ApnSettings.MVNO_NONE;
    public LinearLayout mApplyBtnLayout = null;
    public View mAddLanguageBtnLayout = null;
    public LocaleList mLocalesSetLast = null;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CustomViewHolder extends RecyclerView.ViewHolder
            implements View.OnTouchListener {
        public final LocaleDragCell mLocaleDragCell;

        public CustomViewHolder(LocaleDragCell localeDragCell) {
            super(localeDragCell);
            this.mLocaleDragCell = localeDragCell;
            localeDragCell.mDragHandle.setOnTouchListener(this);
        }

        @Override // android.view.View.OnTouchListener
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            if (LocaleDragAndDropAdapter.this.mDragEnabled && motionEvent.getActionMasked() == 0) {
                ItemTouchHelper itemTouchHelper = LocaleDragAndDropAdapter.this.mItemTouchHelper;
                ItemTouchHelper.Callback callback = itemTouchHelper.mCallback;
                RecyclerView recyclerView = itemTouchHelper.mRecyclerView;
                int movementFlags = callback.getMovementFlags(this);
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                if (!((ItemTouchHelper.Callback.convertToAbsoluteDirection(
                                        movementFlags, recyclerView.getLayoutDirection())
                                & 16711680)
                        != 0)) {
                    Log.e(
                            "ItemTouchHelper",
                            "Start drag has been called but dragging is not enabled");
                    this.itemView.announceForAccessibility(
                            itemTouchHelper
                                    .mRecyclerView
                                    .getContext()
                                    .getString(
                                            R.string.dragndroplist_item_cannot_be_dragged,
                                            Integer.valueOf(getLayoutPosition() + 1)));
                } else if (this.itemView.getParent() != itemTouchHelper.mRecyclerView) {
                    Log.e(
                            "ItemTouchHelper",
                            "Start drag has been called with a view holder which is not a child of"
                                + " the RecyclerView which is controlled by this ItemTouchHelper.");
                } else {
                    VelocityTracker velocityTracker = itemTouchHelper.mVelocityTracker;
                    if (velocityTracker != null) {
                        velocityTracker.recycle();
                    }
                    itemTouchHelper.mVelocityTracker = VelocityTracker.obtain();
                    itemTouchHelper.mDy = 0.0f;
                    itemTouchHelper.mDx = 0.0f;
                    itemTouchHelper.select(this, 2);
                }
                this.mLocaleDragCell.setBackgroundResource(
                        R.drawable.sec_locale_drag_cell_background);
            }
            return false;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DividerDecoration extends RecyclerView.ItemDecoration {
        public final Context mContext;
        public final Drawable mDivider;
        public int mDividerState;

        public DividerDecoration(Context context) {
            int[] iArr = {android.R.attr.listDivider};
            this.mDividerState = 1;
            this.mContext = context;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(iArr);
            this.mDivider = obtainStyledAttributes.getDrawable(0);
            obtainStyledAttributes.recycle();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public final void getItemOffsets(
                Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            int i = this.mDividerState == 1 ? 0 : -1;
            recyclerView.getClass();
            if (RecyclerView.getChildAdapterPosition(view)
                    != recyclerView.getAdapter().getItemCount() + i) {
                rect.bottom = this.mDivider.getIntrinsicHeight();
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public final void onDraw(Canvas canvas, RecyclerView recyclerView) {
            int dimension =
                    (int)
                            (this.mContext
                                            .getResources()
                                            .getDimension(
                                                    R.dimen
                                                            .sec_widget_checkbox_width_for_divider_inset)
                                    + recyclerView.getLeft());
            if (this.mDividerState == 3) {
                dimension +=
                        (int)
                                (this.mContext
                                                .getResources()
                                                .getDimension(R.dimen.sec_locale_mini_label)
                                        + this.mContext
                                                .getResources()
                                                .getDimension(
                                                        R.dimen
                                                                .sec_widget_list_with_checkbox_margin_start));
            }
            int width = recyclerView.getWidth() - recyclerView.getPaddingRight();
            if (recyclerView.getLayoutDirection() == 1) {
                dimension = recyclerView.getPaddingLeft();
                width =
                        (int)
                                (recyclerView.getRight()
                                        - this.mContext
                                                .getResources()
                                                .getDimension(
                                                        R.dimen
                                                                .sec_widget_checkbox_width_for_divider_inset));
                if (this.mDividerState == 3) {
                    width -=
                            (int)
                                    (this.mContext
                                                    .getResources()
                                                    .getDimension(R.dimen.sec_locale_mini_label)
                                            + this.mContext
                                                    .getResources()
                                                    .getDimension(
                                                            R.dimen
                                                                    .sec_widget_list_with_checkbox_margin_start));
                }
            }
            int childCount = recyclerView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = recyclerView.getChildAt(i);
                int bottom =
                        childAt.getBottom()
                                + ((ViewGroup.MarginLayoutParams)
                                                ((RecyclerView.LayoutParams)
                                                        childAt.getLayoutParams()))
                                        .bottomMargin;
                this.mDivider.setBounds(
                        dimension, bottom, width, this.mDivider.getIntrinsicHeight() + bottom);
                this.mDivider.draw(canvas);
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnCheckChangeListener {}

    public LocaleDragAndDropAdapter(LocaleListEditor localeListEditor, List list) {
        this.mLocalesToSetNext = null;
        this.mFeedItemList = list;
        new ArrayList(list);
        Context context = localeListEditor.getContext();
        this.mContext = context;
        final float applyDimension =
                TypedValue.applyDimension(1, 8.0f, context.getResources().getDisplayMetrics());
        Log.d("LocaleDragAndDropAdapter", "init localeList");
        ArrayList arrayList = new ArrayList();
        arrayList.clear();
        ArrayList arrayList2 = (ArrayList) list;
        int size = arrayList2.size();
        for (int i = 0; i < size; i++) {
            arrayList.add((LocaleStore.LocaleInfo) arrayList2.get(i));
        }
        List list2 = this.mFeedItemList;
        int size2 = list2.size();
        Locale[] localeArr = new Locale[size2];
        for (int i2 = 0; i2 < size2; i2++) {
            LocaleStore.LocaleInfo localeInfo = (LocaleStore.LocaleInfo) list2.get(i2);
            localeArr[i2] = localeInfo.getLocale();
            StringBuilder m = ListPopupWindow$$ExternalSyntheticOutline0.m(i2, "lang[", "]=");
            m.append(localeInfo.getFullNameNative());
            Log.d("LocaleDragAndDropAdapter", m.toString());
        }
        this.mLocalesToSetNext = new LocaleList(localeArr);
        this.mItemTouchHelper =
                new ItemTouchHelper(
                        new ItemTouchHelper
                                .SimpleCallback() { // from class:
                                                    // com.android.settings.localepicker.LocaleDragAndDropAdapter.1
                            public int dragFrom;
                            public int dragTo;
                            public int mSelectionStatus;

                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(3, 0);
                                this.dragFrom = -1;
                                this.dragTo = -1;
                                this.mSelectionStatus = -1;
                            }

                            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
                            public final void clearView(
                                    RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                                int i3;
                                super.clearView(recyclerView, viewHolder);
                                viewHolder.itemView.setBackgroundColor(0);
                                int i4 = this.dragFrom;
                                if (i4 != -1 && (i3 = this.dragTo) != -1 && i4 != i3) {
                                    Log.d("LocaleDragAndDropAdapter", "actuallyMoved called");
                                    LocaleDragAndDropAdapter localeDragAndDropAdapter =
                                            LocaleDragAndDropAdapter.this;
                                    if (localeDragAndDropAdapter.mAddLanguageBtnLayout
                                                    .getVisibility()
                                            == 0) {
                                        localeDragAndDropAdapter.updateLanguageViewState(3);
                                    }
                                    if (!localeDragAndDropAdapter.mParentView.isComputingLayout()) {
                                        localeDragAndDropAdapter.notifyDataSetChanged();
                                    }
                                }
                                this.dragTo = -1;
                                this.dragFrom = -1;
                            }

                            @Override // androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback, androidx.recyclerview.widget.ItemTouchHelper.Callback
                            public final int getMovementFlags(RecyclerView.ViewHolder viewHolder) {
                                if (LocaleDragAndDropAdapter.this.mDragEnabled) {
                                    return ItemTouchHelper.Callback.makeMovementFlags(3, 0);
                                }
                                return 0;
                            }

                            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
                            public final void onChildDraw(
                                    Canvas canvas,
                                    RecyclerView recyclerView,
                                    RecyclerView.ViewHolder viewHolder,
                                    float f,
                                    float f2,
                                    int i3,
                                    boolean z) {
                                float top = viewHolder.itemView.getTop() + f2;
                                float height = viewHolder.itemView.getHeight() + top;
                                if (top >= 0.0f && height <= recyclerView.getHeight()) {
                                    super.onChildDraw(
                                            canvas, recyclerView, viewHolder, f, f2, i3, z);
                                }
                                int i4 = this.mSelectionStatus;
                                if (i4 != -1) {
                                    viewHolder.itemView.setElevation(
                                            i4 == 1 ? applyDimension : 0.0f);
                                    this.mSelectionStatus = -1;
                                }
                            }

                            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
                            public final boolean onMove(
                                    RecyclerView recyclerView,
                                    RecyclerView.ViewHolder viewHolder,
                                    RecyclerView.ViewHolder viewHolder2) {
                                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                                int bindingAdapterPosition2 =
                                        viewHolder2.getBindingAdapterPosition();
                                if (this.dragFrom == -1) {
                                    this.dragFrom = bindingAdapterPosition;
                                }
                                this.dragTo = bindingAdapterPosition2;
                                LocaleDragAndDropAdapter.this.onItemMove(
                                        viewHolder.getBindingAdapterPosition(),
                                        viewHolder2.getBindingAdapterPosition());
                                recyclerView.performHapticFeedback(
                                        HapticFeedbackConstants.semGetVibrationIndex(41));
                                return true;
                            }

                            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
                            public final void onSelectedChanged(int i3) {
                                if (i3 == 2) {
                                    this.mSelectionStatus = 1;
                                } else if (i3 == 0) {
                                    this.mSelectionStatus = 0;
                                }
                            }

                            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
                            public final void onSwiped(RecyclerView.ViewHolder viewHolder) {}
                        });
    }

    public final void doTheUpdate() {
        int size = this.mFeedItemList.size();
        Locale[] localeArr = new Locale[size];
        if (this.mSelectMode) {
            boolean z = false;
            for (int i = 0; i < size; i++) {
                LocaleStore.LocaleInfo localeInfo =
                        (LocaleStore.LocaleInfo) this.mFeedItemList.get(i);
                if (localeInfo.getSelected()) {
                    localeArr[0] = localeInfo.getLocale();
                    z = true;
                } else if (z) {
                    localeArr[i] = localeInfo.getLocale();
                } else {
                    localeArr[i + 1] = localeInfo.getLocale();
                }
            }
        } else {
            for (int i2 = 0; i2 < size; i2++) {
                localeArr[i2] = ((LocaleStore.LocaleInfo) this.mFeedItemList.get(i2)).getLocale();
            }
        }
        LocaleList localeList = new LocaleList(localeArr);
        if (localeList.equals(this.mLocalesToSetNext)) {
            return;
        }
        LocaleList.setDefault(localeList);
        this.mLocalesToSetNext = localeList;
        this.mParentView
                .getItemAnimator()
                .isRunning(
                        new RecyclerView.ItemAnimator
                                .ItemAnimatorFinishedListener() { // from class:
                                                                  // com.android.settings.localepicker.LocaleDragAndDropAdapter.6
                            @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator.ItemAnimatorFinishedListener
                            public final void onAnimationsFinished() {
                                LocaleDragAndDropAdapter localeDragAndDropAdapter =
                                        LocaleDragAndDropAdapter.this;
                                LocaleList localeList2 = localeDragAndDropAdapter.mLocalesToSetNext;
                                if (localeList2 == null
                                        || localeList2.equals(
                                                localeDragAndDropAdapter.mLocalesSetLast)) {
                                    return;
                                }
                                LocalePicker.updateLocales(
                                        localeDragAndDropAdapter.mLocalesToSetNext);
                                localeDragAndDropAdapter.mLocalesSetLast =
                                        localeDragAndDropAdapter.mLocalesToSetNext;
                                new ShortcutsUpdateTask(localeDragAndDropAdapter.mContext)
                                        .execute(new Void[0]);
                                localeDragAndDropAdapter.mLocalesToSetNext = null;
                                localeDragAndDropAdapter.mNumberFormatter =
                                        NumberFormat.getNumberInstance(Locale.getDefault());
                            }
                        });
    }

    public final int getCheckedCount$1() {
        Iterator it = this.mFeedItemList.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (((LocaleStore.LocaleInfo) it.next()).getChecked()) {
                i++;
            }
        }
        return i;
    }

    public final String getDefaultLangugeNameAfterDelete() {
        int size = this.mFeedItemList.size();
        for (int i = 0; i < size; i++) {
            if (!((LocaleStore.LocaleInfo) this.mFeedItemList.get(i)).getChecked()) {
                return ((LocaleStore.LocaleInfo) this.mFeedItemList.get(i)).getSecFullNameNative();
            }
        }
        return null;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        List list = this.mFeedItemList;
        int size = list != null ? list.size() : 0;
        if (size < 2 || !this.mEditMode) {
            this.mDragEnabled = false;
        } else {
            this.mDragEnabled = true;
        }
        return size;
    }

    public final void initializeSelectBoxState() {
        for (int i = 0; i < this.mFeedItemList.size(); i++) {
            if (i == 0) {
                ((LocaleStore.LocaleInfo) this.mFeedItemList.get(i)).setSelected(true);
            } else {
                ((LocaleStore.LocaleInfo) this.mFeedItemList.get(i)).setSelected(false);
            }
        }
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        CharSequence text;
        LocaleStore.LocaleInfo localeInfo = (LocaleStore.LocaleInfo) this.mFeedItemList.get(i);
        final LocaleDragCell localeDragCell = ((CustomViewHolder) viewHolder).mLocaleDragCell;
        String secFullNameNative = localeInfo.getSecFullNameNative();
        CharSequence fullNameInUiLanguage = localeInfo.getFullNameInUiLanguage();
        localeDragCell.mLabel.setText(secFullNameNative);
        localeDragCell.mLabel.setContentDescription(fullNameInUiLanguage);
        localeDragCell.invalidate();
        localeDragCell.mLocalized.setVisibility(localeInfo.isTranslated() ? 8 : 0);
        localeDragCell.invalidate();
        localeInfo.getLocale().equals(Locale.getDefault());
        localeDragCell.invalidate();
        localeDragCell.mMiniLabel.setText(this.mNumberFormatter.format(i + 1));
        localeDragCell.invalidate();
        localeDragCell.setTag(localeInfo);
        localeDragCell.mMiniLabel.setVisibility(0);
        localeDragCell.invalidate();
        localeDragCell.requestLayout();
        if (this.mEditMode) {
            localeDragCell.mCheckbox.setVisibility(0);
        } else {
            localeDragCell.mCheckbox.setVisibility(8);
        }
        localeDragCell.invalidate();
        localeDragCell.requestLayout();
        localeDragCell.mDragHandle.setVisibility(this.mEditMode && this.mDragEnabled ? 0 : 4);
        localeDragCell.invalidate();
        localeDragCell.requestLayout();
        localeDragCell.setContentDescription(fullNameInUiLanguage);
        localeDragCell.mPosition = i;
        localeDragCell.mSelectBox.setVisibility(
                !this.mEditMode && localeInfo.getSelected() ? 0 : 8);
        final CheckBox checkBox = localeDragCell.mCheckbox;
        checkBox.setOnCheckedChangeListener(null);
        boolean checked = this.mEditMode ? localeInfo.getChecked() : false;
        checkBox.setChecked(checked);
        setCheckBoxDescription(localeDragCell, checkBox, checked);
        localeDragCell.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.localepicker.LocaleDragAndDropAdapter.2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        ActionBarContextView$$ExternalSyntheticOutline0.m(
                                new StringBuilder("onClick called, Edit mode = "),
                                LocaleDragAndDropAdapter.this.mEditMode,
                                "LocaleDragAndDropAdapter");
                        if (LocaleDragAndDropAdapter.this.getItemCount() <= 1) {
                            return;
                        }
                        LocaleStore.LocaleInfo localeInfo2 =
                                (LocaleStore.LocaleInfo) localeDragCell.getTag();
                        LocaleDragAndDropAdapter localeDragAndDropAdapter =
                                LocaleDragAndDropAdapter.this;
                        if (!localeDragAndDropAdapter.mEditMode) {
                            if (localeDragCell.mPosition == 0) {
                                localeDragAndDropAdapter.updateLanguageViewState(1);
                            } else {
                                localeDragAndDropAdapter.updateLanguageViewState(2);
                            }
                            LocaleDragAndDropAdapter.this.updateSelectBoxState(localeInfo2);
                            return;
                        }
                        localeInfo2.setChecked(!checkBox.isChecked());
                        CheckBox checkBox2 = checkBox;
                        checkBox2.setChecked(true ^ checkBox2.isChecked());
                        OnCheckChangeListener onCheckChangeListener =
                                LocaleDragAndDropAdapter.this.mListener;
                        if (onCheckChangeListener != null) {
                            ((LocaleListEditor) onCheckChangeListener).onCheckedChange();
                        }
                        if (Utils.isTalkBackEnabled(LocaleDragAndDropAdapter.this.mContext)) {
                            LocaleDragAndDropAdapter.this.notifyItemChanged(
                                    localeDragCell.mPosition);
                        }
                    }
                });
        localeDragCell.setOnLongClickListener(
                new View
                        .OnLongClickListener() { // from class:
                                                 // com.android.settings.localepicker.LocaleDragAndDropAdapter.3
                    @Override // android.view.View.OnLongClickListener
                    public final boolean onLongClick(View view) {
                        LocaleDragAndDropAdapter localeDragAndDropAdapter =
                                LocaleDragAndDropAdapter.this;
                        if (localeDragAndDropAdapter.mSelectMode
                                || localeDragAndDropAdapter.mEditMode
                                || localeDragAndDropAdapter.getItemCount() <= 1) {
                            return false;
                        }
                        LocaleDragAndDropAdapter.this.updateLanguageViewState(3);
                        ((LocaleStore.LocaleInfo) localeDragCell.getTag())
                                .setChecked(!checkBox.isChecked());
                        CheckBox checkBox2 = checkBox;
                        checkBox2.setChecked(true ^ checkBox2.isChecked());
                        OnCheckChangeListener onCheckChangeListener =
                                LocaleDragAndDropAdapter.this.mListener;
                        if (onCheckChangeListener != null) {
                            ((LocaleListEditor) onCheckChangeListener).onCheckedChange();
                        }
                        view.performHapticFeedback(50025);
                        return false;
                    }
                });
        if (this.mEditMode) {
            StringBuilder sb = new StringBuilder();
            sb.append(
                    (Object)
                            (checkBox.isChecked()
                                    ? this.mContext.getText(R.string.ticked)
                                    : this.mContext.getText(R.string.not_ticked)));
            sb.append(" ");
            text =
                    SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0
                            .m(this.mContext, R.string.sec_locale_checkbox_tts, sb);
        } else {
            text =
                    localeInfo.getSelected()
                            ? this.mContext.getText(R.string.selected)
                            : this.mContext.getText(R.string.not_selected);
        }
        localeDragCell.setStateDescription(text);
        localeDragCell.sendAccessibilityEvent(NetworkAnalyticsConstants.DataPoints.FLAG_UID);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new CustomViewHolder(
                (LocaleDragCell)
                        LayoutInflater.from(this.mContext)
                                .inflate(R.layout.sec_locale_drag_cell, viewGroup, false));
    }

    public final void onItemMove(int i, int i2) {
        if (i < 0 || i2 < 0) {
            Locale locale = Locale.US;
            Log.e("LocaleDragAndDropAdapter", "Negative position in onItemMove " + i + " -> " + i2);
        } else {
            LocaleStore.LocaleInfo localeInfo = (LocaleStore.LocaleInfo) this.mFeedItemList.get(i);
            this.mFeedItemList.remove(i);
            this.mFeedItemList.add(i2, localeInfo);
        }
        if (i != i2) {
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            featureFactoryImpl.getMetricsFeatureProvider().action(this.mContext, 1833, new Pair[0]);
        }
        notifyItemMoved(i, i2);
    }

    public final void removeChecked() {
        int size = this.mFeedItemList.size();
        NotificationController notificationController =
                NotificationController.getInstance(this.mContext);
        for (int i = size - 1; i >= 0; i--) {
            LocaleStore.LocaleInfo localeInfo = (LocaleStore.LocaleInfo) this.mFeedItemList.get(i);
            if (localeInfo.getChecked()) {
                FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                if (featureFactoryImpl == null) {
                    throw new UnsupportedOperationException("No feature factory configured");
                }
                featureFactoryImpl
                        .getMetricsFeatureProvider()
                        .action(this.mContext, 1832, new Pair[0]);
                this.mFeedItemList.remove(i);
                String languageTag = localeInfo.getLocale().toLanguageTag();
                SharedPreferences.Editor edit =
                        notificationController
                                .mDataManager
                                .mContext
                                .getSharedPreferences("locale_notification", 0)
                                .edit();
                edit.remove(languageTag);
                edit.apply();
            }
        }
        notifyDataSetChanged();
        doTheUpdate();
    }

    public final void restoreState$1(Bundle bundle) {
        if (bundle != null) {
            if (!this.mEditMode) {
                boolean z = bundle.getBoolean("showingLocaleAddDialog", false);
                this.mShowingAddDialog = z;
                if (z) {
                    String string = bundle.getString("tempAddLocaleID", ApnSettings.MVNO_NONE);
                    this.mTempAddLocaleId = string;
                    if (string.isEmpty()) {
                        return;
                    }
                    showSetDefaultLocaleDialog(
                            LocaleStore.getLocaleInfo(
                                    Locale.forLanguageTag(this.mTempAddLocaleId)));
                    return;
                }
                return;
            }
            ArrayList<String> stringArrayList = bundle.getStringArrayList("order_locales");
            if (stringArrayList != null) {
                this.mFeedItemList.clear();
                for (int i = 0; i < stringArrayList.size(); i++) {
                    this.mFeedItemList.add(
                            i,
                            LocaleStore.getLocaleInfo(
                                    Locale.forLanguageTag(stringArrayList.get(i))));
                }
            }
            updateLanguageViewState(3);
            ArrayList<String> stringArrayList2 = bundle.getStringArrayList("selectedLocales");
            if (stringArrayList2 == null || stringArrayList2.isEmpty()) {
                return;
            }
            for (LocaleStore.LocaleInfo localeInfo : this.mFeedItemList) {
                localeInfo.setChecked(stringArrayList2.contains(localeInfo.getId()));
            }
            notifyItemRangeChanged(0, this.mFeedItemList.size());
        }
    }

    public void setCheckBoxDescription(
            LocaleDragCell localeDragCell, CheckBox checkBox, boolean z) {
        if (this.mRemoveMode) {
            checkBox.setContentDescription(
                    this.mContext.getText(
                            z
                                    ? android.R.string.config_satellite_sim_spn_identifier
                                    : android.R.string.search_language_hint));
        }
    }

    public final void setEditMode(boolean z) {
        this.mEditMode = z;
        int size = this.mFeedItemList.size();
        for (int i = 0; i < size; i++) {
            ((LocaleStore.LocaleInfo) this.mFeedItemList.get(i)).setChecked(false);
            notifyItemChanged(i);
        }
    }

    public final void showSetDefaultLocaleDialog(final LocaleStore.LocaleInfo localeInfo) {
        AlertDialog alertDialog = this.mNofiticationDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
            this.mNofiticationDialog = null;
        }
        this.mShowingAddDialog = true;
        String string =
                this.mContext
                        .getResources()
                        .getString(
                                R.string.language_settings_set_locale_title,
                                localeInfo.getSecFullNameNative());
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mMessage = string;
        final int i = 1;
        builder.setPositiveButton(
                R.string.set_as_default_btn,
                new DialogInterface.OnClickListener(
                        this) { // from class:
                                // com.android.settings.localepicker.LocaleDragAndDropAdapter.9
                    public final /* synthetic */ LocaleDragAndDropAdapter this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i2) {
                        switch (i) {
                            case 0:
                                LocaleStore.LocaleInfo localeInfo2 = localeInfo;
                                if (localeInfo2 != null
                                        && !this.this$0.mFeedItemList.contains(localeInfo2)) {
                                    this.this$0.mFeedItemList.add(localeInfo);
                                    break;
                                }
                                break;
                            default:
                                LocaleStore.LocaleInfo localeInfo3 = localeInfo;
                                if (localeInfo3 != null
                                        && !this.this$0.mFeedItemList.contains(localeInfo3)) {
                                    this.this$0.mFeedItemList.add(0, localeInfo);
                                    this.this$0.updateSelectBoxState(localeInfo);
                                    break;
                                }
                                break;
                        }
                    }
                });
        final int i2 = 0;
        builder.setNegativeButton(
                R.string.keep_current_btn,
                new DialogInterface.OnClickListener(
                        this) { // from class:
                                // com.android.settings.localepicker.LocaleDragAndDropAdapter.9
                    public final /* synthetic */ LocaleDragAndDropAdapter this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i22) {
                        switch (i2) {
                            case 0:
                                LocaleStore.LocaleInfo localeInfo2 = localeInfo;
                                if (localeInfo2 != null
                                        && !this.this$0.mFeedItemList.contains(localeInfo2)) {
                                    this.this$0.mFeedItemList.add(localeInfo);
                                    break;
                                }
                                break;
                            default:
                                LocaleStore.LocaleInfo localeInfo3 = localeInfo;
                                if (localeInfo3 != null
                                        && !this.this$0.mFeedItemList.contains(localeInfo3)) {
                                    this.this$0.mFeedItemList.add(0, localeInfo);
                                    this.this$0.updateSelectBoxState(localeInfo);
                                    break;
                                }
                                break;
                        }
                    }
                });
        alertParams.mOnDismissListener =
                new DialogInterface
                        .OnDismissListener() { // from class:
                                               // com.android.settings.localepicker.LocaleDragAndDropAdapter.8
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        LocaleDragAndDropAdapter localeDragAndDropAdapter =
                                LocaleDragAndDropAdapter.this;
                        localeDragAndDropAdapter.mTempAddLocaleId = ApnSettings.MVNO_NONE;
                        localeDragAndDropAdapter.mShowingAddDialog = false;
                        localeDragAndDropAdapter.notifyDataSetChanged();
                        LocaleDragAndDropAdapter.this.doTheUpdate();
                    }
                };
        AlertDialog create = builder.create();
        this.mNofiticationDialog = create;
        create.setOnShowListener(
                new DialogInterface
                        .OnShowListener() { // from class:
                                            // com.android.settings.localepicker.LocaleDragAndDropAdapter.11
                    @Override // android.content.DialogInterface.OnShowListener
                    public final void onShow(DialogInterface dialogInterface) {
                        TextView textView =
                                (TextView)
                                        LocaleDragAndDropAdapter.this.mNofiticationDialog
                                                .findViewById(android.R.id.message);
                        if (textView != null) {
                            textView.setTextDirection(5);
                        }
                    }
                });
        View view = this.mAddLanguageBtnLayout;
        if (view != null) {
            this.mNofiticationDialog.semSetAnchor(view);
        }
        this.mNofiticationDialog.show();
    }

    public final void updateLanguageViewState(int i) {
        MenuItem findItem;
        if (i == 1) {
            this.mAddLanguageBtnLayout.setVisibility(0);
            this.mApplyBtnLayout.setVisibility(8);
            updateRecyclerViewDivider(1);
            this.mParentView.semSetRoundedCorners(3);
            this.mParentView.semSetRoundedCornerColor(
                    3, this.mContext.getResources().getColor(R.color.sec_widget_round_and_bgcolor));
            this.mSelectMode = false;
            setEditMode(false);
            notifyDataSetChanged();
            return;
        }
        if (i == 2) {
            this.mAddLanguageBtnLayout.setVisibility(8);
            this.mApplyBtnLayout.setVisibility(0);
            updateRecyclerViewDivider(2);
            RecyclerView recyclerView = this.mParentView;
            recyclerView.semSetRoundedCorners(15);
            recyclerView.semSetRoundedCornerColor(
                    15,
                    this.mContext.getResources().getColor(R.color.sec_widget_round_and_bgcolor));
            this.mSelectMode = true;
            return;
        }
        if (i != 3) {
            return;
        }
        this.mAddLanguageBtnLayout.setVisibility(8);
        this.mApplyBtnLayout.setVisibility(8);
        updateRecyclerViewDivider(3);
        RecyclerView recyclerView2 = this.mParentView;
        recyclerView2.semSetRoundedCorners(15);
        recyclerView2.semSetRoundedCornerColor(
                15, this.mContext.getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        setEditMode(true);
        this.mSelectMode = false;
        Menu menu = this.mMenu;
        if (menu != null && (findItem = menu.findItem(2)) != null) {
            findItem.setVisible(false);
        }
        notifyDataSetChanged();
    }

    public final void updateRecyclerViewDivider(int i) {
        if (this.mParentView == null) {
            return;
        }
        if (this.mDividerDecoration == null) {
            this.mDividerDecoration = new DividerDecoration(this.mContext);
        }
        this.mParentView.removeItemDecoration(this.mDividerDecoration);
        DividerDecoration dividerDecoration = this.mDividerDecoration;
        dividerDecoration.mDividerState = i;
        this.mParentView.addItemDecoration(dividerDecoration);
    }

    public final void updateSelectBoxState(LocaleStore.LocaleInfo localeInfo) {
        for (int i = 0; i < this.mFeedItemList.size(); i++) {
            if (localeInfo.equals(this.mFeedItemList.get(i))) {
                ((LocaleStore.LocaleInfo) this.mFeedItemList.get(i)).setSelected(true);
            } else {
                ((LocaleStore.LocaleInfo) this.mFeedItemList.get(i)).setSelected(false);
            }
        }
        notifyDataSetChanged();
    }
}
