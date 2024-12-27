package com.samsung.android.settings.accessibility.vision.color;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Slog;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.ActionMenuView;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.InstrumentedFragment;

import com.samsung.android.settings.accessibility.SecAccessibilityUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ColorAdjustmentTestFragment extends InstrumentedFragment
        implements View.OnClickListener, View.OnLongClickListener, View.OnDragListener {
    public Context mContext;
    public int mDraggedColorChipIndex;
    public Menu mMenu;
    public MenuInflater mMenuInflater;
    public MenuItem mPortraitDoneButton;
    public ViewGroup mRootView;
    public final ArrayList mSelectColorChipViewIds = new ArrayList(15);
    public final ArrayList mSelectColorChipSelectedStatusList = new ArrayList(15);
    public final ArrayList mSelectColorChipDrawables = new ArrayList(15);
    public final ArrayList mSelectColorChipIndexes = new ArrayList(15);
    public final ArrayList mDraggedColorChipIndexes = new ArrayList(15);
    public final ArrayList mSampleColorChipViewIds = new ArrayList(15);
    public final ArrayList mSampleColorChipSelectedStatusList = new ArrayList(15);
    public final ArrayList mSampleColorChipDrawables = new ArrayList(15);
    public final ArrayList mSampleColorChipIndex = new ArrayList(15);
    public boolean mIsRunningAnimation = false;
    public int mInitialDraggingViewIndex = -1;
    public int mCurrentDraggingViewIndex = -1;
    public final AnonymousClass1 mAnimationListener =
            new Animation
                    .AnimationListener() { // from class:
                                           // com.samsung.android.settings.accessibility.vision.color.ColorAdjustmentTestFragment.1
                @Override // android.view.animation.Animation.AnimationListener
                public final void onAnimationEnd(Animation animation) {
                    ColorAdjustmentTestFragment.this.mIsRunningAnimation = false;
                }

                @Override // android.view.animation.Animation.AnimationListener
                public final void onAnimationRepeat(Animation animation) {}

                @Override // android.view.animation.Animation.AnimationListener
                public final void onAnimationStart(Animation animation) {}
            };

    public static float getCenterYCoordinate(View view) {
        return (view.getY() + view.getHeight()) / 2.0f;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 3074;
    }

    public final ImageView getSelectColorChipView(int i) {
        return (ImageView)
                this.mRootView.findViewById(
                        ((Integer) this.mSelectColorChipViewIds.get(i)).intValue());
    }

    public final void handleTradeChip(int i) {
        float x;
        float centerYCoordinate;
        float centerYCoordinate2;
        float x2;
        float centerYCoordinate3;
        if (this.mIsRunningAnimation) {
            return;
        }
        int dimensionPixelSize =
                getResources().getDimensionPixelSize(R.dimen.test_big_colorchip_image_size);
        getSelectColorChipView(i).setImageDrawable(null);
        int i2 = this.mCurrentDraggingViewIndex;
        if (i2 > i) {
            while (i2 > i) {
                ArrayList arrayList = this.mDraggedColorChipIndexes;
                arrayList.set(i2, (Integer) arrayList.get(i2 - 1));
                getSelectColorChipView(i2)
                        .setImageResource(
                                ((Integer)
                                                this.mSelectColorChipDrawables.get(
                                                        ((Integer)
                                                                        this
                                                                                .mDraggedColorChipIndexes
                                                                                .get(i2))
                                                                .intValue()))
                                        .intValue());
                i2--;
            }
            int i3 = -dimensionPixelSize;
            for (int i4 = this.mCurrentDraggingViewIndex; i4 > i; i4--) {
                if (isPortrait$1()) {
                    if (i4 == 0) {
                        View findViewById = this.mRootView.findViewById(R.id.colorChipimgBase);
                        ImageView selectColorChipView = getSelectColorChipView(i4);
                        x2 =
                                ((selectColorChipView.getX() + selectColorChipView.getWidth())
                                                / 2.0f)
                                        - ((findViewById.getX() + findViewById.getWidth()) / 2.0f);
                        centerYCoordinate3 =
                                getCenterYCoordinate(selectColorChipView)
                                        - getCenterYCoordinate(findViewById);
                    } else {
                        ImageView selectColorChipView2 = getSelectColorChipView(i4 - 1);
                        ImageView selectColorChipView3 = getSelectColorChipView(i4);
                        x2 =
                                ((selectColorChipView2.getX() + selectColorChipView2.getWidth())
                                                / 2.0f)
                                        - ((selectColorChipView3.getX()
                                                        + selectColorChipView3.getWidth())
                                                / 2.0f);
                        centerYCoordinate3 =
                                getCenterYCoordinate(selectColorChipView2)
                                        - getCenterYCoordinate(selectColorChipView3);
                    }
                    TranslateAnimation translateAnimation =
                            new TranslateAnimation(x2, 0.0f, centerYCoordinate3, 0.0f);
                    this.mIsRunningAnimation = true;
                    translateAnimation.setDuration(300L);
                    translateAnimation.setAnimationListener(this.mAnimationListener);
                    getSelectColorChipView(i4).startAnimation(translateAnimation);
                } else {
                    TranslateAnimation translateAnimation2 =
                            new TranslateAnimation(
                                    Utils.isRTL(this.mContext) ? -i3 : i3, 0.0f, 0.0f, 0.0f);
                    this.mIsRunningAnimation = true;
                    translateAnimation2.setDuration(300L);
                    translateAnimation2.setAnimationListener(this.mAnimationListener);
                    getSelectColorChipView(i4).startAnimation(translateAnimation2);
                }
            }
        } else if (i2 < i) {
            while (i2 < i) {
                ArrayList arrayList2 = this.mDraggedColorChipIndexes;
                int i5 = i2 + 1;
                arrayList2.set(i2, (Integer) arrayList2.get(i5));
                getSelectColorChipView(i2)
                        .setImageResource(
                                ((Integer)
                                                this.mSelectColorChipDrawables.get(
                                                        ((Integer)
                                                                        this
                                                                                .mDraggedColorChipIndexes
                                                                                .get(i2))
                                                                .intValue()))
                                        .intValue());
                i2 = i5;
            }
            for (int i6 = this.mCurrentDraggingViewIndex; i6 < i; i6++) {
                if (isPortrait$1()) {
                    if (i6 == 14) {
                        View findViewById2 = this.mRootView.findViewById(R.id.colorChipimgBase);
                        ImageView selectColorChipView4 = getSelectColorChipView(i6);
                        x =
                                ((findViewById2.getX() + findViewById2.getWidth()) / 2.0f)
                                        - ((selectColorChipView4.getX()
                                                        + selectColorChipView4.getWidth())
                                                / 2.0f);
                        centerYCoordinate = getCenterYCoordinate(findViewById2);
                        centerYCoordinate2 = getCenterYCoordinate(selectColorChipView4);
                    } else {
                        ImageView selectColorChipView5 = getSelectColorChipView(i6 + 1);
                        ImageView selectColorChipView6 = getSelectColorChipView(i6);
                        x =
                                ((selectColorChipView5.getX() + selectColorChipView5.getWidth())
                                                / 2.0f)
                                        - ((selectColorChipView6.getX()
                                                        + selectColorChipView6.getWidth())
                                                / 2.0f);
                        centerYCoordinate = getCenterYCoordinate(selectColorChipView5);
                        centerYCoordinate2 = getCenterYCoordinate(selectColorChipView6);
                    }
                    TranslateAnimation translateAnimation3 =
                            new TranslateAnimation(
                                    x, 0.0f, centerYCoordinate - centerYCoordinate2, 0.0f);
                    this.mIsRunningAnimation = true;
                    translateAnimation3.setDuration(300L);
                    translateAnimation3.setAnimationListener(this.mAnimationListener);
                    getSelectColorChipView(i6).startAnimation(translateAnimation3);
                } else {
                    TranslateAnimation translateAnimation4 =
                            new TranslateAnimation(
                                    Utils.isRTL(this.mContext)
                                            ? -dimensionPixelSize
                                            : dimensionPixelSize,
                                    0.0f,
                                    0.0f,
                                    0.0f);
                    this.mIsRunningAnimation = true;
                    translateAnimation4.setDuration(300L);
                    translateAnimation4.setAnimationListener(this.mAnimationListener);
                    getSelectColorChipView(i6).startAnimation(translateAnimation4);
                }
            }
        }
        this.mDraggedColorChipIndexes.set(i, Integer.valueOf(this.mDraggedColorChipIndex));
        this.mCurrentDraggingViewIndex = i;
    }

    public final void initEditMenu$1() {
        ActionMenuView actionMenuView =
                (ActionMenuView) this.mRootView.findViewById(R.id.edit_action_menu);
        if (actionMenuView != null) {
            MenuBuilder menu = actionMenuView.getMenu();
            this.mMenuInflater.inflate(R.menu.color_action_bar_menu, menu);
            actionMenuView.mOnMenuItemClickListener =
                    new ActionMenuView
                            .OnMenuItemClickListener() { // from class:
                                                         // com.samsung.android.settings.accessibility.vision.color.ColorAdjustmentTestFragment$$ExternalSyntheticLambda0
                        @Override // androidx.appcompat.widget.ActionMenuView.OnMenuItemClickListener
                        public final boolean onMenuItemClick(MenuItem menuItem) {
                            return ColorAdjustmentTestFragment.this.onOptionsItemSelected(menuItem);
                        }
                    };
            this.mPortraitDoneButton = menu.findItem(R.id.menu_done);
        }
        Menu menu2 = this.mMenu;
        if (menu2 != null) {
            menu2.setGroupVisible(R.id.bottom_menu_group, !isPortrait$1());
        }
        toggleDoneButtonState();
    }

    public final void initLayout$4() {
        View findViewById = this.mRootView.findViewById(R.id.chunking_layout);
        findViewById.semSetRoundedCorners(15);
        findViewById.semSetRoundedCornerColor(
                15, this.mContext.getColor(R.color.rounded_corner_color));
        findViewById.setOnDragListener(this);
        initSampleCircles();
        initSelectedCircles();
    }

    public final void initSampleCircles() {
        Iterator it = this.mSampleColorChipViewIds.iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            int indexOf = this.mSampleColorChipViewIds.indexOf(num);
            ImageView imageView = (ImageView) this.mRootView.findViewById(num.intValue());
            if (((Boolean) this.mSampleColorChipSelectedStatusList.get(indexOf)).booleanValue()) {
                imageView.setImageResource(R.drawable.color_chip_blank);
                imageView.setContentDescription(getString(R.string.color_adjusetment_no_color_set));
            } else {
                imageView.setImageResource(
                        ((Integer)
                                        this.mSampleColorChipDrawables.get(
                                                ((Integer) this.mSampleColorChipIndex.get(indexOf))
                                                        .intValue()))
                                .intValue());
                imageView.setOnClickListener(this);
                imageView.setOnLongClickListener(this);
                imageView.setContentDescription(
                        getString(
                                R.string.color_adjustment_candicate_color_pd,
                                Integer.valueOf(indexOf + 1)));
            }
        }
    }

    public final void initSelectedCircles() {
        if (isPortrait$1() && Utils.isRTL(this.mContext)) {
            ViewGroup viewGroup = (ViewGroup) this.mRootView.findViewById(R.id.upper);
            viewGroup.removeAllViews();
            LayoutInflater.from(this.mContext)
                    .inflate(R.layout.color_adjustment_circular_rtl, viewGroup);
        }
        Iterator it = this.mSelectColorChipViewIds.iterator();
        while (it.hasNext()) {
            ((ImageView) this.mRootView.findViewById(((Integer) it.next()).intValue()))
                    .setContentDescription(getString(R.string.blank_box));
        }
        for (int i = 0; i < this.mSelectColorChipSelectedStatusList.size(); i++) {
            if (((Boolean) this.mSelectColorChipSelectedStatusList.get(i)).booleanValue()) {
                ImageView selectColorChipView = getSelectColorChipView(i);
                selectColorChipView.setImageResource(
                        ((Integer)
                                        this.mSelectColorChipDrawables.get(
                                                ((Integer) this.mSelectColorChipIndexes.get(i))
                                                        .intValue()))
                                .intValue());
                selectColorChipView.setContentDescription(
                        getString(
                                R.string.color_adjustment_selected_color_in_position_pd,
                                Integer.valueOf(i + 1)));
                selectColorChipView.setClickable(true);
                selectColorChipView.setOnLongClickListener(this);
            }
        }
    }

    public final boolean isPortrait$1() {
        return getResources().getConfiguration().orientation == 1;
    }

    @Override // com.android.settings.core.InstrumentedFragment,
              // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        Slog.d("ChipTestFragment", "initResources()");
        TypedArray obtainTypedArray =
                getResources()
                        .obtainTypedArray(R.array.color_adjustment_test_selected_view_drawables);
        for (int i = 0; i < obtainTypedArray.length(); i++) {
            this.mSelectColorChipDrawables.add(
                    Integer.valueOf(obtainTypedArray.getResourceId(i, -1)));
        }
        obtainTypedArray.recycle();
        TypedArray obtainTypedArray2 =
                getResources().obtainTypedArray(R.array.color_adjustment_test_selected_view_ids);
        for (int i2 = 0; i2 < obtainTypedArray2.length(); i2++) {
            this.mSelectColorChipViewIds.add(
                    Integer.valueOf(obtainTypedArray2.getResourceId(i2, -1)));
            this.mSelectColorChipSelectedStatusList.add(Boolean.FALSE);
        }
        obtainTypedArray2.recycle();
        TypedArray obtainTypedArray3 =
                getResources()
                        .obtainTypedArray(R.array.color_adjustment_test_sample_view_drawables);
        for (int i3 = 0; i3 < obtainTypedArray3.length(); i3++) {
            this.mSampleColorChipDrawables.add(
                    Integer.valueOf(obtainTypedArray3.getResourceId(i3, -1)));
        }
        obtainTypedArray3.recycle();
        TypedArray obtainTypedArray4 =
                getResources().obtainTypedArray(R.array.color_adjustment_test_sample_view_ids);
        for (int i4 = 0; i4 < obtainTypedArray4.length(); i4++) {
            this.mSampleColorChipViewIds.add(
                    Integer.valueOf(obtainTypedArray4.getResourceId(i4, -1)));
            this.mSampleColorChipSelectedStatusList.add(Boolean.FALSE);
        }
        obtainTypedArray4.recycle();
        int[] iArr = new int[15];
        iArr[0] = 0;
        iArr[1] = 1;
        iArr[2] = 2;
        iArr[3] = 3;
        iArr[4] = 4;
        iArr[5] = 5;
        iArr[6] = 6;
        iArr[7] = 7;
        iArr[8] = 8;
        iArr[9] = 9;
        iArr[10] = 10;
        iArr[11] = 11;
        iArr[12] = 12;
        iArr[13] = 13;
        iArr[14] = 14;
        Slog.d("ChipTestFragment", "random()");
        Random random = new Random(System.currentTimeMillis() + 100);
        for (int i5 = 14; i5 >= 0; i5--) {
            int nextInt = random.nextInt(i5 + 1);
            int i6 = iArr[nextInt];
            iArr[nextInt] = iArr[i5];
            iArr[i5] = i6;
            this.mSampleColorChipIndex.add(Integer.valueOf(i6));
        }
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int indexOf;
        if ((view instanceof ImageView)
                && (indexOf = this.mSampleColorChipViewIds.indexOf(Integer.valueOf(view.getId())))
                        != -1) {
            ((ImageView) view).setImageResource(R.drawable.color_chip_blank);
            view.setContentDescription(getString(R.string.color_adjusetment_no_color_set));
            int i = 0;
            view.setFocusable(false);
            view.setOnClickListener(null);
            view.setOnLongClickListener(null);
            this.mSampleColorChipSelectedStatusList.set(indexOf, Boolean.TRUE);
            while (true) {
                if (i >= this.mSelectColorChipSelectedStatusList.size()) {
                    break;
                }
                if (!((Boolean) this.mSelectColorChipSelectedStatusList.get(i)).booleanValue()) {
                    this.mSelectColorChipIndexes.add(
                            (Integer) this.mSampleColorChipIndex.get(indexOf));
                    ImageView selectColorChipView = getSelectColorChipView(i);
                    selectColorChipView.setImageResource(
                            ((Integer)
                                            this.mSelectColorChipDrawables.get(
                                                    ((Integer) this.mSelectColorChipIndexes.get(i))
                                                            .intValue()))
                                    .intValue());
                    selectColorChipView.setContentDescription(
                            getString(
                                    R.string.color_adjustment_selected_color_in_position_pd,
                                    Integer.valueOf(i + 1)));
                    selectColorChipView.setClickable(true);
                    selectColorChipView.setOnLongClickListener(this);
                    this.mSelectColorChipSelectedStatusList.set(i, Boolean.TRUE);
                    break;
                }
                i++;
            }
            toggleDoneButtonState();
        }
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mRootView.removeAllViews();
        LayoutInflater.from(this.mContext)
                .inflate(R.layout.fragment_color_adjustment_chip_test, this.mRootView);
        initLayout$4();
        initEditMenu$1();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        this.mMenuInflater = menuInflater;
        menuInflater.inflate(R.menu.color_action_bar_menu, menu);
        this.mMenu = menu;
        initEditMenu$1();
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        setHasOptionsMenu(true);
        return layoutInflater.inflate(
                R.layout.fragment_color_adjustment_chip_test, viewGroup, false);
    }

    @Override // android.view.View.OnDragListener
    public final boolean onDrag(View view, DragEvent dragEvent) {
        View view2 = (View) dragEvent.getLocalState();
        int action = dragEvent.getAction();
        if (action != 1) {
            if (action == 2) {
                int i = this.mCurrentDraggingViewIndex;
                if (i != -1) {
                    if (this.mSelectColorChipViewIds.contains(
                            Integer.valueOf(getSelectColorChipView(i).getId()))) {
                        Iterator it = this.mSelectColorChipViewIds.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            Integer num = (Integer) it.next();
                            int intValue = num.intValue();
                            if (intValue
                                    != ((Integer) this.mSelectColorChipViewIds.get(i)).intValue()) {
                                int indexOf = this.mSelectColorChipViewIds.indexOf(num);
                                if (((Boolean) this.mSelectColorChipSelectedStatusList.get(indexOf))
                                        .booleanValue()) {
                                    View findViewById = this.mRootView.findViewById(intValue);
                                    int[] iArr = new int[2];
                                    this.mRootView
                                            .findViewById(R.id.chunking_layout)
                                            .getLocationOnScreen(iArr);
                                    int[] iArr2 = new int[2];
                                    findViewById.getLocationOnScreen(iArr2);
                                    int[] iArr3 = {iArr2[0] - iArr[0], iArr2[1] - iArr[1]};
                                    if (dragEvent.getX() >= iArr3[0]) {
                                        if (dragEvent.getX() <= findViewById.getWidth() + iArr3[0]
                                                && dragEvent.getY() >= iArr3[1]) {
                                            if (dragEvent.getY()
                                                    <= findViewById.getHeight() + iArr3[1]) {
                                                handleTradeChip(indexOf);
                                                break;
                                            }
                                        }
                                    } else {
                                        continue;
                                    }
                                } else {
                                    continue;
                                }
                            }
                        }
                    }
                }
            } else if (action != 3) {
                if (action != 4) {
                    if (action != 6) {
                        return false;
                    }
                    handleTradeChip(this.mInitialDraggingViewIndex);
                } else if (!dragEvent.getResult()) {
                    if (this.mSampleColorChipViewIds.contains(Integer.valueOf(view2.getId()))) {
                        initSampleCircles();
                        setFocusFirstBlankItem(false);
                    } else {
                        handleTradeChip(
                                this.mSelectColorChipViewIds.indexOf(
                                        Integer.valueOf(view2.getId())));
                        initSelectedCircles();
                    }
                }
            } else if (this.mSampleColorChipViewIds.contains(Integer.valueOf(view2.getId()))) {
                onClick(view2);
            } else {
                getSelectColorChipView(this.mCurrentDraggingViewIndex)
                        .setImageResource(
                                ((Integer)
                                                this.mSelectColorChipDrawables.get(
                                                        this.mDraggedColorChipIndex))
                                        .intValue());
                this.mSelectColorChipIndexes.clear();
                this.mSelectColorChipIndexes.addAll(this.mDraggedColorChipIndexes);
            }
        } else if (this.mSampleColorChipViewIds.contains(Integer.valueOf(view2.getId()))) {
            ((ImageView) view2).setImageResource(R.drawable.color_chip_blank);
            view2.setContentDescription(getString(R.string.color_adjusetment_no_color_set));
            setFocusFirstBlankItem(true);
        } else {
            this.mDraggedColorChipIndex =
                    ((Integer)
                                    this.mSelectColorChipIndexes.get(
                                            this.mSelectColorChipViewIds.indexOf(
                                                    Integer.valueOf(view2.getId()))))
                            .intValue();
            this.mDraggedColorChipIndexes.clear();
            this.mDraggedColorChipIndexes.addAll(this.mSelectColorChipIndexes);
            ((ImageView) view2).setImageResource(R.drawable.small_color_chip_focused);
        }
        return true;
    }

    @Override // android.view.View.OnLongClickListener
    public final boolean onLongClick(View view) {
        view.startDragAndDrop(null, new View.DragShadowBuilder(view), view, 0);
        int indexOf = this.mSelectColorChipViewIds.indexOf(Integer.valueOf(view.getId()));
        this.mInitialDraggingViewIndex = indexOf;
        this.mCurrentDraggingViewIndex = indexOf;
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return false;
        }
        int itemId = menuItem.getItemId();
        if (itemId == R.id.menu_cancel) {
            Slog.d("ChipTestFragment", "onOptionsItemSelected() :: action_bar_cancel");
            activity.onBackPressed();
            return true;
        }
        if (itemId != R.id.menu_done) {
            return false;
        }
        Slog.d("ChipTestFragment", "onOptionsItemSelected() :: action_bar_done");
        Settings.System.putInt(this.mContext.getContentResolver(), "color_blind_test", 1);
        int[] iArr = new int[15];
        for (int i = 0; i < 15; i++) {
            iArr[i] = ((Integer) this.mSelectColorChipIndexes.get(i)).intValue() + 1;
        }
        AccessibilityManager accessibilityManager =
                (AccessibilityManager) this.mContext.getSystemService("accessibility");
        if (accessibilityManager != null) {
            accessibilityManager.semCheckMdnieColorBlind(iArr);
        }
        Settings.Secure.putInt(this.mContext.getContentResolver(), "color_adjustment_type", 4);
        activity.onBackPressed();
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        SecAccessibilityUtils.setColorAdjustment(this.mContext);
    }

    @Override // com.android.settings.core.InstrumentedFragment,
              // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        SecAccessibilityUtils.setColorAdjustment(this.mContext, false);
    }

    @Override // androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        this.mRootView = (ViewGroup) view;
        initLayout$4();
    }

    public final void setFocusFirstBlankItem(boolean z) {
        for (int i = 0; i < this.mSelectColorChipSelectedStatusList.size(); i++) {
            if (!((Boolean) this.mSelectColorChipSelectedStatusList.get(i)).booleanValue()) {
                getSelectColorChipView(i)
                        .setImageResource(
                                z
                                        ? R.drawable.small_color_chip_selected
                                        : R.drawable.color_chip_blank);
                return;
            }
        }
    }

    public final void toggleDoneButtonState() {
        boolean z = !this.mSelectColorChipSelectedStatusList.contains(Boolean.FALSE);
        Menu menu = this.mMenu;
        if (menu != null) {
            menu.findItem(R.id.menu_done).setEnabled(z);
        }
        MenuItem menuItem = this.mPortraitDoneButton;
        if (menuItem != null) {
            menuItem.setEnabled(z);
        }
    }
}
