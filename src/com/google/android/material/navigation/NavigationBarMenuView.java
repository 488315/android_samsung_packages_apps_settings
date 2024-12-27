package com.google.android.material.navigation;

import android.R;
import android.animation.AnimatorInflater;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.util.SeslMisc;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pools$SynchronizedPool;
import androidx.core.view.ViewCompat;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.internal.TextScale;
import com.google.android.material.navigation.NavigationBarPresenter;
import com.google.android.material.navigation.NavigationBarPresenter.OpenOverflowRunnable;
import com.google.android.material.navigation.NavigationBarPresenter.OverflowPopup;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.samsung.android.knox.net.apn.ApnSettings;
import java.util.HashSet;
import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class NavigationBarMenuView extends ViewGroup implements MenuView {
    public static final int[] CHECKED_STATE_SET = {R.attr.state_checked};
    public static final int[] DISABLED_STATE_SET = {-16842910};
    public final SparseArray badgeDrawables;
    NavigationBarItemView[] buttons;
    public ColorStateList itemActiveIndicatorColor;
    public ShapeAppearanceModel itemActiveIndicatorShapeAppearance;
    public int itemBackgroundRes;
    public int itemIconSize;
    public ColorStateList itemIconTint;
    public final Pools$SynchronizedPool itemPool;
    public int itemStateListAnimatorId;
    public int itemTextAppearanceActive;
    public int itemTextAppearanceInactive;
    public final ColorStateList itemTextColorDefault;
    public ColorStateList itemTextColorFromUser;
    public int labelVisibilityMode;
    public final ContentResolver mContentResolver;
    public MenuBuilder mDummyMenu;
    public boolean mExclusiveCheckable;
    public boolean mHasOverflowMenu;
    public InternalBtnInfo mInvisibleBtns;
    public int mMaxItemCount;
    public NavigationBarItemView mOverflowButton;
    public MenuBuilder mOverflowMenu;
    public ColorDrawable mSBBTextColorDrawable;
    public MenuBuilder.Callback mSelectedCallback;
    public int mSeslLabelTextAppearance;
    public boolean mUseItemPool;
    public int mViewType;
    public int mViewVisibleItemCount;
    public InternalBtnInfo mVisibleBtns;
    public int mVisibleItemCount;
    public MenuBuilder menu;
    public final AnonymousClass1 onClickListener;
    public NavigationBarPresenter presenter;
    public int selectedItemId;
    public int selectedItemPosition;
    public final AutoTransition set;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.google.android.material.navigation.NavigationBarMenuView$1, reason: invalid class name */
    public final class AnonymousClass1 implements View.OnClickListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ NavigationBarMenuView this$0;

        public /* synthetic */ AnonymousClass1(NavigationBarMenuView navigationBarMenuView, int i) {
            this.$r8$classId = i;
            this.this$0 = navigationBarMenuView;
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            switch (this.$r8$classId) {
                case 0:
                    MenuItemImpl menuItemImpl = ((NavigationBarItemView) view).itemData;
                    NavigationBarMenuView navigationBarMenuView = this.this$0;
                    if (!navigationBarMenuView.menu.performItemAction(menuItemImpl, navigationBarMenuView.presenter, 0)) {
                        if (!this.this$0.mExclusiveCheckable) {
                            menuItemImpl.setChecked(!menuItemImpl.isChecked());
                            break;
                        } else {
                            menuItemImpl.setChecked(true);
                            break;
                        }
                    }
                    break;
                default:
                    NavigationBarMenuView navigationBarMenuView2 = this.this$0;
                    MenuBuilder menuBuilder = navigationBarMenuView2.mOverflowMenu;
                    menuBuilder.mCallback = navigationBarMenuView2.mSelectedCallback;
                    NavigationBarPresenter navigationBarPresenter = navigationBarMenuView2.presenter;
                    NavigationBarPresenter.OverflowPopup overflowPopup = navigationBarPresenter.mOverflowPopup;
                    if ((overflowPopup == null || !overflowPopup.isShowing()) && menuBuilder != null && navigationBarPresenter.menuView != null && navigationBarPresenter.mPostedOpenRunnable == null) {
                        menuBuilder.flagActionItems();
                        if (!menuBuilder.mNonActionItems.isEmpty()) {
                            NavigationBarPresenter.OverflowPopup overflowPopup2 = navigationBarPresenter.new OverflowPopup(navigationBarPresenter.mContext, menuBuilder, navigationBarPresenter.menuView.mOverflowButton);
                            navigationBarPresenter.mOverflowPopup = overflowPopup2;
                            NavigationBarPresenter.OpenOverflowRunnable openOverflowRunnable = navigationBarPresenter.new OpenOverflowRunnable(overflowPopup2);
                            navigationBarPresenter.mPostedOpenRunnable = openOverflowRunnable;
                            navigationBarPresenter.menuView.post(openOverflowRunnable);
                            MenuPresenter.Callback callback = navigationBarPresenter.mCallback;
                            if (callback != null) {
                                callback.onOpenSubMenu(navigationBarPresenter.mMenu);
                                break;
                            }
                        }
                    }
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.google.android.material.navigation.NavigationBarMenuView$2, reason: invalid class name */
    public final class AnonymousClass2 extends NavigationBarItemView {
        public final /* synthetic */ int val$layoutType;
        public final /* synthetic */ MenuItemImpl val$menuItem;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Context context, int i, MenuItemImpl menuItemImpl, int i2) {
            super(context, i);
            this.val$menuItem = menuItemImpl;
            this.val$layoutType = i2;
        }

        @Override // androidx.appcompat.view.menu.MenuView.ItemView
        public final void initialize(MenuItemImpl menuItemImpl) {
            this.itemData = menuItemImpl;
            menuItemImpl.getClass();
            refreshDrawableState();
            setChecked(menuItemImpl.isChecked());
            setEnabled(menuItemImpl.isEnabled());
            Drawable icon = menuItemImpl.getIcon();
            if (icon != this.originalIconDrawable) {
                this.originalIconDrawable = icon;
                if (icon != null) {
                    Drawable.ConstantState constantState = icon.getConstantState();
                    if (constantState != null) {
                        icon = constantState.newDrawable();
                    }
                    icon = icon.mutate();
                    this.wrappedIconDrawable = icon;
                    ColorStateList colorStateList = this.iconTint;
                    if (colorStateList != null) {
                        icon.setTintList(colorStateList);
                    }
                }
                this.icon.setImageDrawable(icon);
            }
            CharSequence charSequence = menuItemImpl.mTitle;
            this.smallLabel.setText(charSequence);
            this.largeLabel.setText(charSequence);
            if (TextUtils.isEmpty(charSequence)) {
                this.smallLabel.setVisibility(8);
                this.largeLabel.setVisibility(8);
            }
            MenuItemImpl menuItemImpl2 = this.itemData;
            if (menuItemImpl2 == null || TextUtils.isEmpty(menuItemImpl2.mContentDescription)) {
                setContentDescription(charSequence);
            }
            MenuItemImpl menuItemImpl3 = this.itemData;
            setTooltipText(menuItemImpl3 != null ? menuItemImpl3.mTooltipText : null);
            setId(menuItemImpl.mId);
            if (!TextUtils.isEmpty(menuItemImpl.mContentDescription)) {
                setContentDescription(menuItemImpl.mContentDescription);
            }
            setTooltipText(menuItemImpl.mTooltipText);
            String str = menuItemImpl.mBadgeText;
            this.mBadgeType = (str == null || str.equals(ApnSettings.MVNO_NONE) || str.isEmpty()) ? 1 : menuItemImpl.mId == com.android.settings.R.id.bottom_overflow ? 0 : 2;
            this.initialized = true;
            menuItemImpl.setExclusiveCheckable(NavigationBarMenuView.this.mExclusiveCheckable);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class InternalBtnInfo {
        public int cnt = 0;
        public final int[] originPos;

        public InternalBtnInfo(int i) {
            this.originPos = new int[i];
        }
    }

    public NavigationBarMenuView(Context context) {
        super(context);
        this.itemPool = new Pools$SynchronizedPool(5);
        new SparseArray(5);
        int i = 0;
        this.selectedItemId = 0;
        this.selectedItemPosition = 0;
        this.badgeDrawables = new SparseArray(5);
        this.itemStateListAnimatorId = 0;
        this.mViewType = 1;
        this.mVisibleBtns = null;
        this.mInvisibleBtns = null;
        this.mOverflowButton = null;
        this.mHasOverflowMenu = false;
        this.mOverflowMenu = null;
        this.mVisibleItemCount = 0;
        this.mViewVisibleItemCount = 0;
        this.mMaxItemCount = 0;
        this.mUseItemPool = true;
        this.mExclusiveCheckable = true;
        this.itemTextColorDefault = createDefaultColorStateList();
        if (isInEditMode()) {
            this.set = null;
        } else {
            AutoTransition autoTransition = new AutoTransition();
            this.set = autoTransition;
            autoTransition.setOrdering(0);
            autoTransition.setDuration(0L);
            autoTransition.addTransition(new TextScale());
        }
        this.onClickListener = new AnonymousClass1((BottomNavigationMenuView) this, i);
        this.mContentResolver = context.getContentResolver();
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        setImportantForAccessibility(1);
    }

    public final NavigationBarItemView buildMenu(MenuItemImpl menuItemImpl, boolean z) {
        NavigationBarItemView navigationBarItemView = (NavigationBarItemView) this.itemPool.acquire();
        if (navigationBarItemView == null) {
            int i = this.mViewType;
            navigationBarItemView = new AnonymousClass2(getContext(), i, menuItemImpl, i);
        }
        navigationBarItemView.setIconTintList(this.itemIconTint);
        navigationBarItemView.setIconSize(this.itemIconSize);
        navigationBarItemView.setTextColor(this.itemTextColorDefault);
        int i2 = this.mSeslLabelTextAppearance;
        navigationBarItemView.mLargeLabelAppearance = i2;
        navigationBarItemView.mSmallLabelAppearance = i2;
        navigationBarItemView.smallLabel.setTextAppearance(i2);
        navigationBarItemView.calculateTextScaleFactors(navigationBarItemView.smallLabel.getTextSize(), navigationBarItemView.largeLabel.getTextSize());
        navigationBarItemView.setLargeTextSize(navigationBarItemView.mLargeLabelAppearance, navigationBarItemView.largeLabel);
        navigationBarItemView.setLargeTextSize(navigationBarItemView.mSmallLabelAppearance, navigationBarItemView.smallLabel);
        navigationBarItemView.setTextAppearanceInactive(this.itemTextAppearanceInactive);
        navigationBarItemView.setTextAppearanceActive(this.itemTextAppearanceActive);
        navigationBarItemView.setTextColor(this.itemTextColorFromUser);
        int i3 = this.itemBackgroundRes;
        Drawable drawable = i3 == 0 ? null : navigationBarItemView.getContext().getDrawable(i3);
        if (drawable != null && drawable.getConstantState() != null) {
            drawable = drawable.getConstantState().newDrawable().mutate();
        }
        navigationBarItemView.itemBackground = drawable;
        navigationBarItemView.refreshItemBackground();
        inflateStateListAnimator(navigationBarItemView);
        if (navigationBarItemView.isShifting != z) {
            navigationBarItemView.isShifting = z;
            navigationBarItemView.refreshChecked();
        }
        int i4 = this.labelVisibilityMode;
        if (navigationBarItemView.labelVisibilityMode != i4) {
            navigationBarItemView.labelVisibilityMode = i4;
            navigationBarItemView.activeIndicatorTransform = NavigationBarItemView.ACTIVE_INDICATOR_LABELED_TRANSFORM;
            navigationBarItemView.updateActiveIndicatorLayoutParams(navigationBarItemView.getWidth());
            navigationBarItemView.refreshChecked();
        }
        navigationBarItemView.initialize(menuItemImpl);
        return navigationBarItemView;
    }

    /* JADX WARN: Type inference failed for: r0v10, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r0v47 */
    /* JADX WARN: Type inference failed for: r0v9 */
    public final void buildMenuView() {
        MenuItemImpl menuItemImpl;
        MenuBuilder menuBuilder;
        int i;
        NavigationBarItemView navigationBarItemView;
        BadgeDrawable badgeDrawable;
        removeAllViews();
        TransitionManager.beginDelayedTransition(this, this.set);
        NavigationBarItemView[] navigationBarItemViewArr = this.buttons;
        if (navigationBarItemViewArr != null && this.mUseItemPool) {
            for (NavigationBarItemView navigationBarItemView2 : navigationBarItemViewArr) {
                if (navigationBarItemView2 != null) {
                    seslRemoveBadge(navigationBarItemView2.getId());
                    this.itemPool.release(navigationBarItemView2);
                    ImageView imageView = navigationBarItemView2.icon;
                    if (navigationBarItemView2.badgeDrawable != null) {
                        if (imageView != null) {
                            navigationBarItemView2.setClipChildren(true);
                            navigationBarItemView2.setClipToPadding(true);
                            BadgeDrawable badgeDrawable2 = navigationBarItemView2.badgeDrawable;
                            if (badgeDrawable2 != null) {
                                if (badgeDrawable2.getCustomBadgeParent() != null) {
                                    badgeDrawable2.getCustomBadgeParent().setForeground(null);
                                } else {
                                    imageView.getOverlay().remove(badgeDrawable2);
                                }
                            }
                        }
                        navigationBarItemView2.badgeDrawable = null;
                    }
                    navigationBarItemView2.itemData = null;
                    navigationBarItemView2.activeIndicatorProgress = 0.0f;
                    navigationBarItemView2.initialized = false;
                }
            }
        }
        if (this.mOverflowButton != null) {
            seslRemoveBadge(com.android.settings.R.id.bottom_overflow);
        }
        int size = this.menu.mItems.size();
        if (size == 0) {
            this.selectedItemId = 0;
            this.selectedItemPosition = 0;
            this.buttons = null;
            this.mVisibleItemCount = 0;
            this.mOverflowButton = null;
            this.mOverflowMenu = null;
            this.mVisibleBtns = null;
            this.mInvisibleBtns = null;
            return;
        }
        HashSet hashSet = new HashSet();
        for (int i2 = 0; i2 < this.menu.mItems.size(); i2++) {
            hashSet.add(Integer.valueOf(this.menu.getItem(i2).getItemId()));
        }
        for (int i3 = 0; i3 < this.badgeDrawables.size(); i3++) {
            int keyAt = this.badgeDrawables.keyAt(i3);
            if (!hashSet.contains(Integer.valueOf(keyAt))) {
                this.badgeDrawables.delete(keyAt);
            }
        }
        int i4 = this.labelVisibilityMode;
        this.menu.getVisibleItems().size();
        boolean z = i4 == 0;
        this.buttons = new NavigationBarItemView[this.menu.mItems.size()];
        this.mVisibleBtns = new InternalBtnInfo(size);
        this.mInvisibleBtns = new InternalBtnInfo(size);
        this.mOverflowMenu = new MenuBuilder(getContext());
        this.mVisibleBtns.cnt = 0;
        this.mInvisibleBtns.cnt = 0;
        int i5 = 0;
        int i6 = 0;
        for (int i7 = 0; i7 < size; i7++) {
            this.presenter.updateSuspended = true;
            this.menu.getItem(i7).setCheckable(true);
            this.presenter.updateSuspended = false;
            if (((MenuItemImpl) this.menu.getItem(i7)).requiresOverflow()) {
                InternalBtnInfo internalBtnInfo = this.mInvisibleBtns;
                int[] iArr = internalBtnInfo.originPos;
                int i8 = internalBtnInfo.cnt;
                internalBtnInfo.cnt = i8 + 1;
                iArr[i8] = i7;
                if (!this.menu.getItem(i7).isVisible()) {
                    i5++;
                }
            } else {
                InternalBtnInfo internalBtnInfo2 = this.mVisibleBtns;
                int[] iArr2 = internalBtnInfo2.originPos;
                int i9 = internalBtnInfo2.cnt;
                internalBtnInfo2.cnt = i9 + 1;
                iArr2[i9] = i7;
                if (this.menu.getItem(i7).isVisible()) {
                    i6++;
                }
            }
        }
        ?? r0 = this.mInvisibleBtns.cnt - i5 > 0 ? 1 : 0;
        this.mHasOverflowMenu = r0;
        int i10 = i6 + r0;
        int i11 = this.mMaxItemCount;
        if (i10 > i11) {
            int i12 = i10 - (i11 - 1);
            if (r0 != 0) {
                i12--;
            }
            for (int i13 = this.mVisibleBtns.cnt - 1; i13 >= 0; i13--) {
                if (this.menu.getItem(this.mVisibleBtns.originPos[i13]).isVisible()) {
                    InternalBtnInfo internalBtnInfo3 = this.mInvisibleBtns;
                    int[] iArr3 = internalBtnInfo3.originPos;
                    int i14 = internalBtnInfo3.cnt;
                    internalBtnInfo3.cnt = i14 + 1;
                    InternalBtnInfo internalBtnInfo4 = this.mVisibleBtns;
                    iArr3[i14] = internalBtnInfo4.originPos[i13];
                    internalBtnInfo4.cnt--;
                    i12--;
                    if (i12 == 0) {
                        break;
                    }
                } else {
                    InternalBtnInfo internalBtnInfo5 = this.mInvisibleBtns;
                    int[] iArr4 = internalBtnInfo5.originPos;
                    int i15 = internalBtnInfo5.cnt;
                    internalBtnInfo5.cnt = i15 + 1;
                    InternalBtnInfo internalBtnInfo6 = this.mVisibleBtns;
                    iArr4[i15] = internalBtnInfo6.originPos[i13];
                    internalBtnInfo6.cnt--;
                }
            }
        }
        this.mVisibleItemCount = 0;
        this.mViewVisibleItemCount = 0;
        int i16 = 0;
        while (true) {
            InternalBtnInfo internalBtnInfo7 = this.mVisibleBtns;
            if (i16 >= internalBtnInfo7.cnt) {
                break;
            }
            int i17 = internalBtnInfo7.originPos[i16];
            if (this.buttons != null) {
                if (i17 < 0 || i17 > this.menu.mItems.size() || !(this.menu.getItem(i17) instanceof MenuItemImpl)) {
                    StringBuilder m = ListPopupWindow$$ExternalSyntheticOutline0.m(i17, "position is out of index (pos=", "/size=");
                    m.append(this.menu.mItems.size());
                    m.append(") or not instance of MenuItemImpl");
                    Log.e("NavigationBarMenuView", m.toString());
                } else {
                    MenuItemImpl menuItemImpl2 = (MenuItemImpl) this.menu.getItem(i17);
                    NavigationBarItemView buildMenu = buildMenu(menuItemImpl2, z);
                    this.buttons[this.mVisibleItemCount] = buildMenu;
                    buildMenu.setVisibility(this.menu.getItem(i17).isVisible() ? 0 : 8);
                    buildMenu.setOnClickListener(this.onClickListener);
                    if (this.selectedItemId != 0 && this.menu.getItem(i17).getItemId() == this.selectedItemId) {
                        this.selectedItemPosition = this.mVisibleItemCount;
                    }
                    String str = menuItemImpl2.mBadgeText;
                    if (str != null) {
                        seslAddBadge(menuItemImpl2.mId, str);
                    } else {
                        seslRemoveBadge(menuItemImpl2.mId);
                    }
                    int id = buildMenu.getId();
                    if (id != -1 && (badgeDrawable = (BadgeDrawable) this.badgeDrawables.get(id)) != null) {
                        buildMenu.setBadge(badgeDrawable);
                    }
                    if (buildMenu.getParent() instanceof ViewGroup) {
                        ((ViewGroup) buildMenu.getParent()).removeView(buildMenu);
                    }
                    addView(buildMenu);
                    this.mVisibleItemCount++;
                    if (buildMenu.getVisibility() == 0) {
                        this.mViewVisibleItemCount++;
                    }
                }
            }
            i16++;
        }
        if (this.mInvisibleBtns.cnt > 0) {
            int i18 = 0;
            int i19 = 0;
            while (true) {
                InternalBtnInfo internalBtnInfo8 = this.mInvisibleBtns;
                i = internalBtnInfo8.cnt;
                if (i18 >= i) {
                    break;
                }
                MenuItemImpl menuItemImpl3 = (MenuItemImpl) this.menu.getItem(internalBtnInfo8.originPos[i18]);
                if (menuItemImpl3 != null) {
                    CharSequence charSequence = menuItemImpl3.mTitle;
                    if (charSequence == null) {
                        charSequence = menuItemImpl3.mContentDescription;
                    }
                    MenuItemImpl addInternal = this.mOverflowMenu.addInternal(menuItemImpl3.mGroup, menuItemImpl3.mId, menuItemImpl3.mCategoryOrder, charSequence);
                    addInternal.setVisible(menuItemImpl3.isVisible());
                    addInternal.setEnabled(menuItemImpl3.isEnabled());
                    this.mOverflowMenu.mGroupDividerEnabled = false;
                    menuItemImpl3.setBadgeText(menuItemImpl3.mBadgeText);
                    if (!menuItemImpl3.isVisible()) {
                        i19++;
                    }
                }
                i18++;
            }
            if (i - i19 > 0) {
                this.mHasOverflowMenu = true;
                this.mDummyMenu = new MenuBuilder(getContext());
                new MenuInflater(getContext()).inflate(com.android.settings.R.menu.nv_dummy_overflow_menu_icon, this.mDummyMenu);
                if (this.mDummyMenu.mItems.size() <= 0 || !(this.mDummyMenu.getItem(0) instanceof MenuItemImpl)) {
                    navigationBarItemView = null;
                } else {
                    MenuItemImpl menuItemImpl4 = (MenuItemImpl) this.mDummyMenu.getItem(0);
                    if (this.mViewType == 1) {
                        menuItemImpl4.setTooltipText((CharSequence) null);
                    } else {
                        menuItemImpl4.setTooltipText((CharSequence) getResources().getString(com.android.settings.R.string.sesl_more_item_label));
                    }
                    navigationBarItemView = buildMenu(menuItemImpl4, z);
                    inflateStateListAnimator(navigationBarItemView);
                    navigationBarItemView.mBadgeType = 0;
                    navigationBarItemView.setOnClickListener(new AnonymousClass1(this, 1));
                    navigationBarItemView.setContentDescription(getResources().getString(com.android.settings.R.string.sesl_action_menu_overflow_description));
                    if (this.mViewType == 3) {
                        Drawable drawable = getContext().getDrawable(com.android.settings.R.drawable.sesl_ic_menu_overflow_dark);
                        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(" ");
                        ImageSpan imageSpan = new ImageSpan(drawable);
                        drawable.setState(new int[]{R.attr.state_enabled, -16842910});
                        drawable.setTintList(this.itemTextColorFromUser);
                        drawable.setBounds(0, 0, getResources().getDimensionPixelSize(com.android.settings.R.dimen.sesl_bottom_navigation_icon_size), getResources().getDimensionPixelSize(com.android.settings.R.dimen.sesl_bottom_navigation_icon_size));
                        spannableStringBuilder.setSpan(imageSpan, 0, 1, 18);
                        navigationBarItemView.mLabelImgSpan = spannableStringBuilder;
                        navigationBarItemView.smallLabel.setText(spannableStringBuilder);
                        navigationBarItemView.largeLabel.setText(spannableStringBuilder);
                    }
                    if (navigationBarItemView.getParent() instanceof ViewGroup) {
                        ((ViewGroup) navigationBarItemView.getParent()).removeView(navigationBarItemView);
                    }
                    addView(navigationBarItemView);
                }
                this.mOverflowButton = navigationBarItemView;
                this.buttons[this.mVisibleBtns.cnt] = navigationBarItemView;
                this.mVisibleItemCount++;
                this.mViewVisibleItemCount++;
                navigationBarItemView.setVisibility(0);
            }
        }
        if (this.mViewVisibleItemCount > this.mMaxItemCount) {
            StringBuilder sb = new StringBuilder("Maximum number of visible items supported by BottomNavigationView is ");
            sb.append(this.mMaxItemCount);
            sb.append(". Current visible count is ");
            TooltipPopup$$ExternalSyntheticOutline0.m(sb, this.mViewVisibleItemCount, "NavigationBarMenuView");
            int i20 = this.mMaxItemCount;
            this.mVisibleItemCount = i20;
            this.mViewVisibleItemCount = i20;
        }
        int i21 = 0;
        while (true) {
            NavigationBarItemView[] navigationBarItemViewArr2 = this.buttons;
            if (i21 >= navigationBarItemViewArr2.length) {
                int min = Math.min(this.mMaxItemCount - 1, this.selectedItemPosition);
                this.selectedItemPosition = min;
                this.menu.getItem(min).setChecked(true);
                return;
            }
            NavigationBarItemView navigationBarItemView3 = navigationBarItemViewArr2[i21];
            if (navigationBarItemView3 != null) {
                ColorStateList colorStateList = this.itemTextColorFromUser;
                if (Settings.System.getInt(this.mContentResolver, "show_button_background", 0) == 1) {
                    ColorDrawable colorDrawable = this.mSBBTextColorDrawable;
                    int color = colorDrawable != null ? colorDrawable.getColor() : getResources().getColor(SeslMisc.isLightTheme(getContext()) ? com.android.settings.R.color.sesl_bottom_navigation_background_light : com.android.settings.R.color.sesl_bottom_navigation_background_dark, null);
                    Drawable drawable2 = navigationBarItemView3.getResources().getDrawable(com.android.settings.R.drawable.sesl_bottom_nav_show_button_shapes_background);
                    navigationBarItemView3.smallLabel.setTextColor(color);
                    navigationBarItemView3.largeLabel.setTextColor(color);
                    navigationBarItemView3.smallLabel.setBackground(drawable2);
                    navigationBarItemView3.largeLabel.setBackground(drawable2);
                    navigationBarItemView3.smallLabel.setBackgroundTintList(colorStateList);
                    navigationBarItemView3.largeLabel.setBackgroundTintList(colorStateList);
                    if (this.mOverflowButton != null && (menuItemImpl = navigationBarItemView3.itemData) != null && (menuBuilder = this.mDummyMenu) != null && menuItemImpl.mId == menuBuilder.getItem(0).getItemId()) {
                        setOverflowSpanColor(color, false);
                    }
                }
            }
            i21++;
        }
    }

    public final ColorStateList createDefaultColorStateList() {
        TypedValue typedValue = new TypedValue();
        if (!getContext().getTheme().resolveAttribute(R.attr.textColorSecondary, typedValue, true)) {
            return null;
        }
        ColorStateList colorStateList = ContextCompat.getColorStateList(getContext(), typedValue.resourceId);
        if (!getContext().getTheme().resolveAttribute(com.android.settings.R.attr.colorPrimary, typedValue, true)) {
            return null;
        }
        int i = typedValue.data;
        int defaultColor = colorStateList.getDefaultColor();
        int[] iArr = DISABLED_STATE_SET;
        return new ColorStateList(new int[][]{iArr, CHECKED_STATE_SET, ViewGroup.EMPTY_STATE_SET}, new int[]{colorStateList.getColorForState(iArr, defaultColor), i, defaultColor});
    }

    public final NavigationBarItemView findItemView(int i) {
        if (i == -1) {
            throw new IllegalArgumentException(i + " is not a valid view id");
        }
        NavigationBarItemView[] navigationBarItemViewArr = this.buttons;
        if (navigationBarItemViewArr == null) {
            return null;
        }
        for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
            if (navigationBarItemView == null) {
                return null;
            }
            if (navigationBarItemView.getId() == i) {
                return navigationBarItemView;
            }
        }
        return null;
    }

    public final void hideOverflowMenu() {
        NavigationBarPresenter navigationBarPresenter;
        NavigationBarPresenter.OverflowPopup overflowPopup;
        Object obj;
        if (!this.mHasOverflowMenu || (navigationBarPresenter = this.presenter) == null || (overflowPopup = navigationBarPresenter.mOverflowPopup) == null || !overflowPopup.isShowing()) {
            return;
        }
        NavigationBarPresenter navigationBarPresenter2 = this.presenter;
        NavigationBarPresenter.OpenOverflowRunnable openOverflowRunnable = navigationBarPresenter2.mPostedOpenRunnable;
        if (openOverflowRunnable != null && (obj = navigationBarPresenter2.mMenuView) != null) {
            ((View) obj).removeCallbacks(openOverflowRunnable);
            navigationBarPresenter2.mPostedOpenRunnable = null;
            return;
        }
        NavigationBarPresenter.OverflowPopup overflowPopup2 = navigationBarPresenter2.mOverflowPopup;
        if (overflowPopup2 == null || !overflowPopup2.isShowing()) {
            return;
        }
        overflowPopup2.mPopup.dismiss();
    }

    public final void inflateStateListAnimator(NavigationBarItemView navigationBarItemView) {
        if (this.itemStateListAnimatorId != 0) {
            navigationBarItemView.setStateListAnimator(AnimatorInflater.loadStateListAnimator(getContext(), this.itemStateListAnimatorId));
        }
    }

    @Override // androidx.appcompat.view.menu.MenuView
    public final void initialize(MenuBuilder menuBuilder) {
        this.menu = menuBuilder;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.mViewType != 3) {
            setItemIconSize(getResources().getDimensionPixelSize(com.android.settings.R.dimen.sesl_bottom_navigation_icon_size));
            NavigationBarItemView[] navigationBarItemViewArr = this.buttons;
            if (navigationBarItemViewArr != null) {
                for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                    if (navigationBarItemView == null) {
                        break;
                    }
                    int dimensionPixelSize = getResources().getDimensionPixelSize(com.android.settings.R.dimen.sesl_bottom_navigation_icon_size);
                    if (navigationBarItemView.labelGroup != null) {
                        navigationBarItemView.defaultMargin = navigationBarItemView.getResources().getDimensionPixelSize(com.android.settings.R.dimen.sesl_bottom_navigation_icon_inset);
                        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) navigationBarItemView.labelGroup.getLayoutParams();
                        if (marginLayoutParams != null) {
                            marginLayoutParams.topMargin = dimensionPixelSize + navigationBarItemView.defaultMargin;
                            navigationBarItemView.labelGroup.setLayoutParams(marginLayoutParams);
                        }
                    }
                }
            }
        }
        hideOverflowMenu();
    }

    public final void seslAddBadge(int i, String str) {
        TextView textView;
        NavigationBarItemView findItemView = findItemView(i);
        if (findItemView != null) {
            View findViewById = findItemView.findViewById(com.android.settings.R.id.notifications_badge_container);
            if (findViewById != null) {
                textView = (TextView) findViewById.findViewById(com.android.settings.R.id.notifications_badge);
            } else {
                View inflate = LayoutInflater.from(getContext()).inflate(com.android.settings.R.layout.sesl_navigation_bar_badge_layout, (ViewGroup) this, false);
                TextView textView2 = (TextView) inflate.findViewById(com.android.settings.R.id.notifications_badge);
                findItemView.addView(inflate);
                textView = textView2;
            }
            if (str != null) {
                try {
                    Integer.parseInt(str);
                    if (Integer.parseInt(str) > 999) {
                        findItemView.mIsBadgeNumberless = true;
                        str = "999+";
                    } else {
                        findItemView.mIsBadgeNumberless = false;
                    }
                } catch (NumberFormatException unused) {
                }
            }
            findItemView.mIsBadgeNumberless = false;
        } else {
            textView = null;
        }
        if (textView != null) {
            textView.setText(str);
        }
        updateBadge(findItemView);
    }

    public final void seslRemoveBadge(int i) {
        View findViewById;
        NavigationBarItemView findItemView = findItemView(i);
        if (findItemView == null || (findViewById = findItemView.findViewById(com.android.settings.R.id.notifications_badge_container)) == null) {
            return;
        }
        findItemView.removeView(findViewById);
    }

    public final void seslSetLabelTextAppearance(int i) {
        this.mSeslLabelTextAppearance = i;
        NavigationBarItemView[] navigationBarItemViewArr = this.buttons;
        if (navigationBarItemViewArr != null) {
            for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                if (navigationBarItemView == null) {
                    break;
                }
                navigationBarItemView.setTextAppearanceInactive(i);
                ColorStateList colorStateList = this.itemTextColorFromUser;
                if (colorStateList != null) {
                    navigationBarItemView.setTextColor(colorStateList);
                }
            }
        }
        NavigationBarItemView navigationBarItemView2 = this.mOverflowButton;
        if (navigationBarItemView2 != null) {
            navigationBarItemView2.setTextAppearanceInactive(i);
            ColorStateList colorStateList2 = this.itemTextColorFromUser;
            if (colorStateList2 != null) {
                this.mOverflowButton.setTextColor(colorStateList2);
            }
        }
    }

    public final void setActiveIndicatorLabelPadding(int i) {
        NavigationBarItemView[] navigationBarItemViewArr = this.buttons;
        if (navigationBarItemViewArr != null) {
            for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                if (navigationBarItemView.activeIndicatorLabelPadding != i) {
                    navigationBarItemView.activeIndicatorLabelPadding = i;
                    navigationBarItemView.refreshChecked();
                }
            }
        }
    }

    public final void setIconTintList(ColorStateList colorStateList) {
        this.itemIconTint = colorStateList;
        NavigationBarItemView[] navigationBarItemViewArr = this.buttons;
        if (navigationBarItemViewArr != null) {
            for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                if (navigationBarItemView == null) {
                    break;
                }
                navigationBarItemView.setIconTintList(colorStateList);
            }
        }
        NavigationBarItemView navigationBarItemView2 = this.mOverflowButton;
        if (navigationBarItemView2 != null) {
            navigationBarItemView2.setIconTintList(colorStateList);
        }
    }

    public final void setItemActiveIndicatorColor(ColorStateList colorStateList) {
        MaterialShapeDrawable materialShapeDrawable;
        this.itemActiveIndicatorColor = colorStateList;
        NavigationBarItemView[] navigationBarItemViewArr = this.buttons;
        if (navigationBarItemViewArr != null) {
            for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                if (this.itemActiveIndicatorShapeAppearance == null || this.itemActiveIndicatorColor == null) {
                    materialShapeDrawable = null;
                } else {
                    materialShapeDrawable = new MaterialShapeDrawable(this.itemActiveIndicatorShapeAppearance);
                    materialShapeDrawable.setFillColor(this.itemActiveIndicatorColor);
                }
                View view = navigationBarItemView.activeIndicatorView;
                if (view != null) {
                    view.setBackgroundDrawable(materialShapeDrawable);
                    navigationBarItemView.refreshItemBackground();
                }
            }
        }
    }

    public final void setItemActiveIndicatorEnabled() {
        NavigationBarItemView[] navigationBarItemViewArr = this.buttons;
        if (navigationBarItemViewArr != null) {
            for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                navigationBarItemView.activeIndicatorEnabled = true;
                navigationBarItemView.refreshItemBackground();
                View view = navigationBarItemView.activeIndicatorView;
                if (view != null) {
                    view.setVisibility(0);
                    navigationBarItemView.requestLayout();
                }
            }
        }
    }

    public final void setItemActiveIndicatorHeight(int i) {
        NavigationBarItemView[] navigationBarItemViewArr = this.buttons;
        if (navigationBarItemViewArr != null) {
            for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                navigationBarItemView.activeIndicatorDesiredHeight = i;
                navigationBarItemView.updateActiveIndicatorLayoutParams(navigationBarItemView.getWidth());
            }
        }
    }

    public final void setItemActiveIndicatorMarginHorizontal(int i) {
        NavigationBarItemView[] navigationBarItemViewArr = this.buttons;
        if (navigationBarItemViewArr != null) {
            for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                navigationBarItemView.activeIndicatorMarginHorizontal = i;
                navigationBarItemView.updateActiveIndicatorLayoutParams(navigationBarItemView.getWidth());
            }
        }
    }

    public final void setItemActiveIndicatorShapeAppearance(ShapeAppearanceModel shapeAppearanceModel) {
        MaterialShapeDrawable materialShapeDrawable;
        this.itemActiveIndicatorShapeAppearance = shapeAppearanceModel;
        NavigationBarItemView[] navigationBarItemViewArr = this.buttons;
        if (navigationBarItemViewArr != null) {
            for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                if (this.itemActiveIndicatorShapeAppearance == null || this.itemActiveIndicatorColor == null) {
                    materialShapeDrawable = null;
                } else {
                    materialShapeDrawable = new MaterialShapeDrawable(this.itemActiveIndicatorShapeAppearance);
                    materialShapeDrawable.setFillColor(this.itemActiveIndicatorColor);
                }
                View view = navigationBarItemView.activeIndicatorView;
                if (view != null) {
                    view.setBackgroundDrawable(materialShapeDrawable);
                    navigationBarItemView.refreshItemBackground();
                }
            }
        }
    }

    public final void setItemActiveIndicatorWidth(int i) {
        NavigationBarItemView[] navigationBarItemViewArr = this.buttons;
        if (navigationBarItemViewArr != null) {
            for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                navigationBarItemView.activeIndicatorDesiredWidth = i;
                navigationBarItemView.updateActiveIndicatorLayoutParams(navigationBarItemView.getWidth());
            }
        }
    }

    public final void setItemBackgroundRes(int i) {
        this.itemBackgroundRes = i;
        NavigationBarItemView[] navigationBarItemViewArr = this.buttons;
        if (navigationBarItemViewArr != null) {
            for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                if (navigationBarItemView == null) {
                    break;
                }
                Drawable drawable = i == 0 ? null : navigationBarItemView.getContext().getDrawable(i);
                if (drawable != null && drawable.getConstantState() != null) {
                    drawable = drawable.getConstantState().newDrawable().mutate();
                }
                navigationBarItemView.itemBackground = drawable;
                navigationBarItemView.refreshItemBackground();
            }
        }
        NavigationBarItemView navigationBarItemView2 = this.mOverflowButton;
        if (navigationBarItemView2 != null) {
            Drawable drawable2 = i != 0 ? navigationBarItemView2.getContext().getDrawable(i) : null;
            if (drawable2 != null && drawable2.getConstantState() != null) {
                drawable2 = drawable2.getConstantState().newDrawable().mutate();
            }
            navigationBarItemView2.itemBackground = drawable2;
            navigationBarItemView2.refreshItemBackground();
        }
    }

    public final void setItemIconSize(int i) {
        this.itemIconSize = i;
        NavigationBarItemView[] navigationBarItemViewArr = this.buttons;
        if (navigationBarItemViewArr != null) {
            for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                if (navigationBarItemView == null) {
                    break;
                }
                navigationBarItemView.setIconSize(i);
            }
        }
        NavigationBarItemView navigationBarItemView2 = this.mOverflowButton;
        if (navigationBarItemView2 != null) {
            navigationBarItemView2.setIconSize(i);
        }
    }

    public final void setItemPaddingBottom(int i) {
        NavigationBarItemView[] navigationBarItemViewArr = this.buttons;
        if (navigationBarItemViewArr != null) {
            for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                if (navigationBarItemView.itemPaddingBottom != i) {
                    navigationBarItemView.itemPaddingBottom = i;
                    navigationBarItemView.refreshChecked();
                }
            }
        }
    }

    public final void setItemPaddingTop(int i) {
        NavigationBarItemView[] navigationBarItemViewArr = this.buttons;
        if (navigationBarItemViewArr != null) {
            for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                if (navigationBarItemView.itemPaddingTop != i) {
                    navigationBarItemView.itemPaddingTop = i;
                    navigationBarItemView.refreshChecked();
                }
            }
        }
    }

    public final void setItemRippleColor(ColorStateList colorStateList) {
        NavigationBarItemView[] navigationBarItemViewArr = this.buttons;
        if (navigationBarItemViewArr != null) {
            for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                navigationBarItemView.itemRippleColor = colorStateList;
                navigationBarItemView.refreshItemBackground();
            }
        }
    }

    public final void setItemStateListAnimator(int i) {
        this.itemStateListAnimatorId = i;
        NavigationBarItemView[] navigationBarItemViewArr = this.buttons;
        if (navigationBarItemViewArr != null) {
            for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                if (navigationBarItemView == null) {
                    break;
                }
                inflateStateListAnimator(navigationBarItemView);
            }
        }
        NavigationBarItemView navigationBarItemView2 = this.mOverflowButton;
        if (navigationBarItemView2 != null) {
            inflateStateListAnimator(navigationBarItemView2);
        }
    }

    public final void setItemTextAppearanceActive(int i) {
        this.itemTextAppearanceActive = i;
        NavigationBarItemView[] navigationBarItemViewArr = this.buttons;
        if (navigationBarItemViewArr != null) {
            for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                if (navigationBarItemView == null) {
                    break;
                }
                navigationBarItemView.setTextAppearanceActive(i);
                ColorStateList colorStateList = this.itemTextColorFromUser;
                if (colorStateList != null) {
                    navigationBarItemView.setTextColor(colorStateList);
                }
            }
        }
        NavigationBarItemView navigationBarItemView2 = this.mOverflowButton;
        if (navigationBarItemView2 == null || this.itemTextColorFromUser == null) {
            return;
        }
        navigationBarItemView2.setTextAppearanceActive(i);
        this.mOverflowButton.setTextColor(this.itemTextColorFromUser);
    }

    public final void setItemTextAppearanceActiveBoldEnabled(boolean z) {
        NavigationBarItemView[] navigationBarItemViewArr = this.buttons;
        if (navigationBarItemViewArr != null) {
            for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                navigationBarItemView.setTextAppearanceActive(navigationBarItemView.activeTextAppearance);
                TextView textView = navigationBarItemView.largeLabel;
                textView.setTypeface(textView.getTypeface(), z ? 1 : 0);
            }
        }
    }

    public final void setItemTextAppearanceInactive(int i) {
        this.itemTextAppearanceInactive = i;
        NavigationBarItemView[] navigationBarItemViewArr = this.buttons;
        if (navigationBarItemViewArr != null) {
            for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                if (navigationBarItemView == null) {
                    break;
                }
                navigationBarItemView.setTextAppearanceInactive(i);
                ColorStateList colorStateList = this.itemTextColorFromUser;
                if (colorStateList != null) {
                    navigationBarItemView.setTextColor(colorStateList);
                }
            }
        }
        NavigationBarItemView navigationBarItemView2 = this.mOverflowButton;
        if (navigationBarItemView2 != null) {
            navigationBarItemView2.setTextAppearanceInactive(i);
            ColorStateList colorStateList2 = this.itemTextColorFromUser;
            if (colorStateList2 != null) {
                this.mOverflowButton.setTextColor(colorStateList2);
            }
        }
    }

    public final void setItemTextColor(ColorStateList colorStateList) {
        this.itemTextColorFromUser = colorStateList;
        NavigationBarItemView[] navigationBarItemViewArr = this.buttons;
        if (navigationBarItemViewArr != null) {
            for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                if (navigationBarItemView == null) {
                    break;
                }
                navigationBarItemView.setTextColor(colorStateList);
            }
        }
        NavigationBarItemView navigationBarItemView2 = this.mOverflowButton;
        if (navigationBarItemView2 != null) {
            navigationBarItemView2.setTextColor(colorStateList);
            setOverflowSpanColor(0, true);
        }
    }

    public final void setOverflowSpanColor(int i, boolean z) {
        SpannableStringBuilder spannableStringBuilder;
        NavigationBarItemView navigationBarItemView = this.mOverflowButton;
        if (navigationBarItemView == null || (spannableStringBuilder = navigationBarItemView.mLabelImgSpan) == null) {
            return;
        }
        Drawable drawable = getContext().getDrawable(com.android.settings.R.drawable.sesl_ic_menu_overflow_dark);
        ImageSpan[] imageSpanArr = (ImageSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), ImageSpan.class);
        if (imageSpanArr != null) {
            for (ImageSpan imageSpan : imageSpanArr) {
                spannableStringBuilder.removeSpan(imageSpan);
            }
        }
        ImageSpan imageSpan2 = new ImageSpan(drawable);
        drawable.setState(new int[]{R.attr.state_enabled, -16842910});
        if (z) {
            drawable.setTintList(this.itemTextColorFromUser);
        } else {
            drawable.setTint(i);
        }
        drawable.setBounds(0, 0, getResources().getDimensionPixelSize(com.android.settings.R.dimen.sesl_bottom_navigation_icon_size), getResources().getDimensionPixelSize(com.android.settings.R.dimen.sesl_bottom_navigation_icon_size));
        spannableStringBuilder.setSpan(imageSpan2, 0, 1, 18);
        NavigationBarItemView navigationBarItemView2 = this.mOverflowButton;
        navigationBarItemView2.mLabelImgSpan = spannableStringBuilder;
        navigationBarItemView2.smallLabel.setText(spannableStringBuilder);
        navigationBarItemView2.largeLabel.setText(spannableStringBuilder);
    }

    public final void updateBadge(NavigationBarItemView navigationBarItemView) {
        TextView textView;
        int i;
        int i2;
        int measuredWidth;
        if (navigationBarItemView == null || (textView = (TextView) navigationBarItemView.findViewById(com.android.settings.R.id.notifications_badge)) == null) {
            return;
        }
        Resources resources = getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(com.android.settings.R.dimen.sesl_navigation_bar_num_badge_size);
        float f = getResources().getConfiguration().fontScale;
        if (f > 1.2f) {
            textView.setTextSize(0, (dimensionPixelSize / f) * 1.2f);
        }
        int i3 = navigationBarItemView.mBadgeType;
        int dimensionPixelOffset = resources.getDimensionPixelOffset(com.android.settings.R.dimen.sesl_bottom_navigation_dot_badge_size);
        int dimensionPixelSize2 = this.mVisibleItemCount == this.mMaxItemCount ? resources.getDimensionPixelSize(com.android.settings.R.dimen.sesl_bottom_navigation_icon_mode_min_padding_horizontal) : resources.getDimensionPixelSize(com.android.settings.R.dimen.sesl_bottom_navigation_icon_mode_padding_horizontal);
        int dimensionPixelSize3 = resources.getDimensionPixelSize(com.android.settings.R.dimen.sesl_bottom_navigation_N_badge_top_margin);
        int dimensionPixelSize4 = resources.getDimensionPixelSize(com.android.settings.R.dimen.sesl_bottom_navigation_N_badge_start_margin);
        TextView textView2 = navigationBarItemView.smallLabel;
        if (textView2 == null) {
            textView2 = navigationBarItemView.largeLabel;
        }
        int width = textView2 == null ? 1 : textView2.getWidth();
        int height = textView2 == null ? 1 : textView2.getHeight();
        if (i3 == 1 || i3 == 0) {
            Drawable drawable = resources.getDrawable(com.android.settings.R.drawable.sesl_dot_badge);
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            textView.setBackground(drawable);
            i = dimensionPixelOffset;
            i2 = i;
        } else {
            Drawable drawable2 = resources.getDrawable(com.android.settings.R.drawable.sesl_tab_n_badge);
            WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
            textView.setBackground(drawable2);
            textView.measure(0, 0);
            i = textView.getMeasuredWidth();
            i2 = textView.getMeasuredHeight();
        }
        if (this.mViewType != 3) {
            if (i3 == 1) {
                measuredWidth = this.itemIconSize / 2;
            } else {
                measuredWidth = (textView.getMeasuredWidth() / 2) - dimensionPixelSize2;
                dimensionPixelOffset /= 2;
            }
        } else if (i3 == 1) {
            measuredWidth = (textView.getMeasuredWidth() + width) / 2;
            dimensionPixelOffset = (navigationBarItemView.getHeight() - height) / 2;
        } else if (i3 == 0) {
            measuredWidth = ((width - textView.getMeasuredWidth()) - dimensionPixelSize4) / 2;
            dimensionPixelOffset = ((navigationBarItemView.getHeight() - height) / 2) - dimensionPixelSize3;
        } else {
            measuredWidth = (textView.getMeasuredWidth() + width) / 2;
            dimensionPixelOffset = ((navigationBarItemView.getHeight() - height) / 2) - dimensionPixelSize3;
            if ((textView.getMeasuredWidth() / 2) + (navigationBarItemView.getWidth() / 2) + measuredWidth > navigationBarItemView.getWidth()) {
                measuredWidth += navigationBarItemView.getWidth() - ((textView.getMeasuredWidth() / 2) + ((navigationBarItemView.getWidth() / 2) + measuredWidth));
            }
        }
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
        int i4 = layoutParams.width;
        int i5 = layoutParams.leftMargin;
        if (i4 == i && i5 == measuredWidth) {
            return;
        }
        layoutParams.width = i;
        layoutParams.height = i2;
        layoutParams.topMargin = dimensionPixelOffset;
        layoutParams.setMarginStart(measuredWidth);
        textView.setLayoutParams(layoutParams);
    }

    public final void updateBadgeIfNeeded() {
        NavigationBarItemView[] navigationBarItemViewArr = this.buttons;
        if (navigationBarItemViewArr != null) {
            for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                if (navigationBarItemView == null) {
                    return;
                }
                updateBadge(navigationBarItemView);
            }
        }
    }
}
