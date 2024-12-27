package com.google.android.material.navigation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.PathInterpolator;

import androidx.appcompat.view.menu.BaseMenuPresenter;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.StandardMenuPopup;
import androidx.appcompat.view.menu.SubMenuBuilder;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.android.settings.R;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeState;
import com.google.android.material.internal.ParcelableSparseArray;
import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class NavigationBarPresenter extends BaseMenuPresenter {
    public int id;
    public final AnonymousClass1 mAnimationHandler;
    public Context mContext;
    public OverflowPopup mOverflowPopup;
    public final PopupPresenterCallback mPopupPresenterCallback;
    public OpenOverflowRunnable mPostedOpenRunnable;
    public MenuBuilder menu;
    public NavigationBarMenuView menuView;
    public boolean updateSuspended;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class OpenOverflowRunnable implements Runnable {
        public final OverflowPopup mPopup;

        public OpenOverflowRunnable(OverflowPopup overflowPopup) {
            this.mPopup = overflowPopup;
        }

        @Override // java.lang.Runnable
        public final void run() {
            MenuBuilder.Callback callback;
            MenuBuilder menuBuilder = NavigationBarPresenter.this.menu;
            if (menuBuilder != null && (callback = menuBuilder.mCallback) != null) {
                callback.onMenuModeChange(menuBuilder);
            }
            NavigationBarMenuView navigationBarMenuView = NavigationBarPresenter.this.menuView;
            if (navigationBarMenuView != null
                    && navigationBarMenuView.getWindowToken() != null
                    && this.mPopup.tryShow$1()) {
                NavigationBarPresenter.this.mOverflowPopup = this.mPopup;
            }
            NavigationBarPresenter.this.mPostedOpenRunnable = null;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class OverflowPopup extends MenuPopupHelper {
        public OverflowPopup(Context context, MenuBuilder menuBuilder, View view) {
            super(R.attr.actionOverflowBottomMenuStyle, 0, context, view, menuBuilder, true);
            this.mDropDownGravity = 8388613;
            PopupPresenterCallback popupPresenterCallback =
                    NavigationBarPresenter.this.mPopupPresenterCallback;
            this.mPresenterCallback = popupPresenterCallback;
            StandardMenuPopup standardMenuPopup = this.mPopup;
            if (standardMenuPopup != null) {
                standardMenuPopup.mPresenterCallback = popupPresenterCallback;
            }
            this.mAnchorView = view;
            this.mOverlapAnchorSet = true;
            this.mOverlapAnchor = false;
            this.mForceShowUpper = true;
        }

        @Override // androidx.appcompat.view.menu.MenuPopupHelper
        public final void onDismiss() {
            NavigationBarPresenter navigationBarPresenter = NavigationBarPresenter.this;
            MenuBuilder menuBuilder = navigationBarPresenter.menu;
            if (menuBuilder != null) {
                menuBuilder.close(true);
            }
            navigationBarPresenter.mOverflowPopup = null;
            super.onDismiss();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PopupPresenterCallback implements MenuPresenter.Callback {
        public PopupPresenterCallback() {}

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public final void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
            if (menuBuilder instanceof SubMenuBuilder) {
                ((SubMenuBuilder) menuBuilder).mParentMenu.getRootMenu().close(false);
            }
            MenuPresenter.Callback callback = NavigationBarPresenter.this.mCallback;
            if (callback != null) {
                callback.onCloseMenu(menuBuilder, z);
            }
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public final boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            MenuPresenter.Callback callback;
            return (menuBuilder == null
                            || (callback = NavigationBarPresenter.this.mCallback) == null
                            || !callback.onOpenSubMenu(menuBuilder))
                    ? false
                    : true;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new AnonymousClass1();
        public ParcelableSparseArray badgeSavedStates;
        public int selectedItemId;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.google.android.material.navigation.NavigationBarPresenter$SavedState$1, reason: invalid class name */
        public final class AnonymousClass1 implements Parcelable.Creator {
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                SavedState savedState = new SavedState();
                savedState.selectedItemId = parcel.readInt();
                savedState.badgeSavedStates =
                        (ParcelableSparseArray)
                                parcel.readParcelable(SavedState.class.getClassLoader());
                return savedState;
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SavedState[i];
            }
        }

        @Override // android.os.Parcelable
        public final int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.selectedItemId);
            parcel.writeParcelable(this.badgeSavedStates, 0);
        }
    }

    public NavigationBarPresenter(Context context) {
        super(context);
        this.updateSuspended = false;
        new Handler(
                Looper
                        .getMainLooper()) { // from class:
                                            // com.google.android.material.navigation.NavigationBarPresenter.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                if (message.what == 100) {
                    final NavigationBarPresenter navigationBarPresenter =
                            NavigationBarPresenter.this;
                    if (navigationBarPresenter.menuView == null) {
                        return;
                    }
                    final PathInterpolator pathInterpolator =
                            new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
                    ObjectAnimator ofFloat =
                            ObjectAnimator.ofFloat(
                                    navigationBarPresenter.menuView, "y", r0.getHeight());
                    ofFloat.setDuration(400L);
                    ofFloat.setInterpolator(pathInterpolator);
                    ofFloat.start();
                    ofFloat.addListener(
                            new AnimatorListenerAdapter() { // from class:
                                                            // com.google.android.material.navigation.NavigationBarPresenter.3
                                @Override // android.animation.AnimatorListenerAdapter,
                                          // android.animation.Animator.AnimatorListener
                                public final void onAnimationEnd(Animator animator) {
                                    NavigationBarPresenter.this.menuView.buildMenuView();
                                    ObjectAnimator ofFloat2 =
                                            ObjectAnimator.ofFloat(
                                                    NavigationBarPresenter.this.menuView,
                                                    "y",
                                                    0.0f);
                                    ofFloat2.setDuration(400L);
                                    ofFloat2.setInterpolator(pathInterpolator);
                                    ofFloat2.start();
                                    super.onAnimationEnd(animator);
                                }
                            });
                }
            }
        };
        this.mPopupPresenterCallback = new PopupPresenterCallback();
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public final boolean flagActionItems() {
        return false;
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter,
              // androidx.appcompat.view.menu.MenuPresenter
    public final int getId() {
        return this.id;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public final void initForMenu(Context context, MenuBuilder menuBuilder) {
        this.menu = menuBuilder;
        this.menuView.menu = menuBuilder;
        this.mContext = context;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public final void onRestoreInstanceState(Parcelable parcelable) {
        BadgeDrawable badgeDrawable;
        if (parcelable instanceof SavedState) {
            NavigationBarMenuView navigationBarMenuView = this.menuView;
            SavedState savedState = (SavedState) parcelable;
            int i = savedState.selectedItemId;
            int size = navigationBarMenuView.menu.mItems.size();
            int i2 = 0;
            while (true) {
                if (i2 >= size) {
                    break;
                }
                MenuItem item = navigationBarMenuView.menu.getItem(i2);
                if (i == item.getItemId()) {
                    navigationBarMenuView.selectedItemId = i;
                    navigationBarMenuView.selectedItemPosition = i2;
                    item.setChecked(true);
                    break;
                }
                i2++;
            }
            Context context = this.menuView.getContext();
            ParcelableSparseArray parcelableSparseArray = savedState.badgeSavedStates;
            SparseArray sparseArray = new SparseArray(parcelableSparseArray.size());
            for (int i3 = 0; i3 < parcelableSparseArray.size(); i3++) {
                int keyAt = parcelableSparseArray.keyAt(i3);
                BadgeState.State state = (BadgeState.State) parcelableSparseArray.valueAt(i3);
                sparseArray.put(keyAt, state != null ? new BadgeDrawable(context, state) : null);
            }
            NavigationBarMenuView navigationBarMenuView2 = this.menuView;
            navigationBarMenuView2.getClass();
            for (int i4 = 0; i4 < sparseArray.size(); i4++) {
                int keyAt2 = sparseArray.keyAt(i4);
                if (navigationBarMenuView2.badgeDrawables.indexOfKey(keyAt2) < 0) {
                    navigationBarMenuView2.badgeDrawables.append(
                            keyAt2, (BadgeDrawable) sparseArray.get(keyAt2));
                }
            }
            NavigationBarItemView[] navigationBarItemViewArr = navigationBarMenuView2.buttons;
            if (navigationBarItemViewArr != null) {
                for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                    if (navigationBarItemView != null
                            && (badgeDrawable =
                                            (BadgeDrawable)
                                                    navigationBarMenuView2.badgeDrawables.get(
                                                            navigationBarItemView.getId()))
                                    != null) {
                        navigationBarItemView.setBadge(badgeDrawable);
                    }
                }
            }
        }
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState();
        NavigationBarMenuView navigationBarMenuView = this.menuView;
        savedState.selectedItemId = navigationBarMenuView.selectedItemId;
        SparseArray sparseArray = navigationBarMenuView.badgeDrawables;
        ParcelableSparseArray parcelableSparseArray = new ParcelableSparseArray();
        for (int i = 0; i < sparseArray.size(); i++) {
            int keyAt = sparseArray.keyAt(i);
            BadgeDrawable badgeDrawable = (BadgeDrawable) sparseArray.valueAt(i);
            parcelableSparseArray.put(
                    keyAt, badgeDrawable != null ? badgeDrawable.state.overridingState : null);
        }
        savedState.badgeSavedStates = parcelableSparseArray;
        return savedState;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public final boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        return false;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public final void updateMenuView(boolean z) {
        MenuBuilder menuBuilder;
        AutoTransition autoTransition;
        if (this.updateSuspended) {
            return;
        }
        if (z) {
            this.menuView.buildMenuView();
            return;
        }
        NavigationBarMenuView navigationBarMenuView = this.menuView;
        MenuBuilder menuBuilder2 = navigationBarMenuView.menu;
        if (menuBuilder2 == null
                || navigationBarMenuView.buttons == null
                || navigationBarMenuView.mVisibleBtns == null
                || navigationBarMenuView.mInvisibleBtns == null) {
            return;
        }
        int size = menuBuilder2.mItems.size();
        navigationBarMenuView.hideOverflowMenu();
        if (size
                != navigationBarMenuView.mVisibleBtns.cnt
                        + navigationBarMenuView.mInvisibleBtns.cnt) {
            navigationBarMenuView.buildMenuView();
            return;
        }
        int i = navigationBarMenuView.selectedItemId;
        int i2 = 0;
        while (true) {
            NavigationBarMenuView.InternalBtnInfo internalBtnInfo =
                    navigationBarMenuView.mVisibleBtns;
            if (i2 >= internalBtnInfo.cnt) {
                break;
            }
            MenuItem item = navigationBarMenuView.menu.getItem(internalBtnInfo.originPos[i2]);
            if (item.isChecked()) {
                navigationBarMenuView.selectedItemId = item.getItemId();
                navigationBarMenuView.selectedItemPosition = i2;
            }
            if (item instanceof MenuItemImpl) {
                navigationBarMenuView.seslRemoveBadge(item.getItemId());
                String str = ((MenuItemImpl) item).mBadgeText;
                if (str != null) {
                    navigationBarMenuView.seslAddBadge(item.getItemId(), str);
                }
            }
            i2++;
        }
        if (i != navigationBarMenuView.selectedItemId
                && (autoTransition = navigationBarMenuView.set) != null) {
            TransitionManager.beginDelayedTransition(navigationBarMenuView, autoTransition);
        }
        int i3 = navigationBarMenuView.labelVisibilityMode;
        navigationBarMenuView.menu.getVisibleItems().size();
        boolean z2 = i3 == 0;
        for (int i4 = 0; i4 < navigationBarMenuView.mVisibleBtns.cnt; i4++) {
            navigationBarMenuView.presenter.updateSuspended = true;
            NavigationBarItemView navigationBarItemView = navigationBarMenuView.buttons[i4];
            int i5 = navigationBarMenuView.labelVisibilityMode;
            if (navigationBarItemView.labelVisibilityMode != i5) {
                navigationBarItemView.labelVisibilityMode = i5;
                navigationBarItemView.activeIndicatorTransform =
                        NavigationBarItemView.ACTIVE_INDICATOR_LABELED_TRANSFORM;
                navigationBarItemView.updateActiveIndicatorLayoutParams(
                        navigationBarItemView.getWidth());
                navigationBarItemView.refreshChecked();
            }
            NavigationBarItemView navigationBarItemView2 = navigationBarMenuView.buttons[i4];
            if (navigationBarItemView2.isShifting != z2) {
                navigationBarItemView2.isShifting = z2;
                navigationBarItemView2.refreshChecked();
            }
            navigationBarMenuView.buttons[i4].initialize(
                    (MenuItemImpl)
                            navigationBarMenuView.menu.getItem(
                                    navigationBarMenuView.mVisibleBtns.originPos[i4]));
            navigationBarMenuView.presenter.updateSuspended = false;
        }
        int i6 = 0;
        boolean z3 = false;
        while (true) {
            NavigationBarMenuView.InternalBtnInfo internalBtnInfo2 =
                    navigationBarMenuView.mInvisibleBtns;
            if (i6 >= internalBtnInfo2.cnt) {
                break;
            }
            MenuItem item2 = navigationBarMenuView.menu.getItem(internalBtnInfo2.originPos[i6]);
            if ((item2 instanceof MenuItemImpl)
                    && (menuBuilder = navigationBarMenuView.mOverflowMenu) != null) {
                MenuItemImpl menuItemImpl = (MenuItemImpl) item2;
                MenuItemImpl menuItemImpl2 = (MenuItemImpl) item2;
                MenuItem findItem = menuBuilder.findItem(menuItemImpl2.mId);
                if (findItem instanceof MenuItemImpl) {
                    ((MenuItemImpl) findItem).setTitle(menuItemImpl2.mTitle);
                    ((MenuItemImpl) findItem).setBadgeText(menuItemImpl.mBadgeText);
                }
                z3 |= menuItemImpl.mBadgeText != null;
            }
            i6++;
        }
        if (z3) {
            navigationBarMenuView.seslAddBadge(R.id.bottom_overflow, ApnSettings.MVNO_NONE);
        } else {
            navigationBarMenuView.seslRemoveBadge(R.id.bottom_overflow);
        }
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public final void onCloseMenu(MenuBuilder menuBuilder, boolean z) {}
}
