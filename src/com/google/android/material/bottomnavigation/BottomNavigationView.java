package com.google.android.material.bottomnavigation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.view.SeslTouchTargetDelegate;
import androidx.core.view.ViewCompat;
import com.android.settings.R;
import com.android.wifitrackerlib.WifiEntry;
import com.google.android.material.R$styleable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.drawable.DrawableUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.navigation.NavigationBarMenu;
import com.google.android.material.navigation.NavigationBarPresenter;
import com.google.android.material.navigation.NavigationBarView$1;
import com.google.android.material.navigation.NavigationBarView$SavedState;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.AbsoluteCornerSize;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.scloud.SCloudWifiDataManager;
import com.samsung.android.settings.wifi.managenetwork.SavedWifiEntrySettings;
import com.samsung.android.settings.wifi.managenetwork.SavedWifiEntrySettings$$ExternalSyntheticLambda0;
import com.samsung.android.settings.wifi.managenetwork.SavedWifiEntrySettings$$ExternalSyntheticLambda3;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class BottomNavigationView extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AnonymousClass2 mOnGlobalLayoutListenerForTD;
    public final NavigationBarView$1 mSelectedCallback;
    public final NavigationBarMenu menu;
    public SupportMenuInflater menuInflater;
    public final BottomNavigationMenuView menuView;
    public final NavigationBarPresenter presenter;
    public SavedWifiEntrySettings$$ExternalSyntheticLambda0 reselectedListener;
    public OnNavigationItemSelectedListener selectedListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnNavigationItemSelectedListener {
        boolean onNavigationItemSelected(MenuItem menuItem);
    }

    public BottomNavigationView(Context context) {
        this(context, null);
    }

    public final void inflateMenu(int i) {
        this.presenter.updateSuspended = true;
        if (this.menuInflater == null) {
            this.menuInflater = new SupportMenuInflater(getContext());
        }
        this.menuInflater.inflate(i, this.menu);
        NavigationBarPresenter navigationBarPresenter = this.presenter;
        navigationBarPresenter.updateSuspended = false;
        navigationBarPresenter.updateMenuView(true);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this);
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(getPaddingBottom() + getPaddingTop() + getSuggestedMinimumHeight(), 1073741824));
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        Parcelable parcelable2;
        if (!(parcelable instanceof NavigationBarView$SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        NavigationBarView$SavedState navigationBarView$SavedState = (NavigationBarView$SavedState) parcelable;
        super.onRestoreInstanceState(navigationBarView$SavedState.mSuperState);
        NavigationBarMenu navigationBarMenu = this.menu;
        Bundle bundle = navigationBarView$SavedState.menuPresenterState;
        navigationBarMenu.getClass();
        SparseArray sparseParcelableArray = bundle.getSparseParcelableArray("android:menu:presenters");
        if (sparseParcelableArray == null || navigationBarMenu.mPresenters.isEmpty()) {
            return;
        }
        Iterator it = navigationBarMenu.mPresenters.iterator();
        while (it.hasNext()) {
            WeakReference weakReference = (WeakReference) it.next();
            MenuPresenter menuPresenter = (MenuPresenter) weakReference.get();
            if (menuPresenter == null) {
                navigationBarMenu.mPresenters.remove(weakReference);
            } else {
                int id = menuPresenter.getId();
                if (id > 0 && (parcelable2 = (Parcelable) sparseParcelableArray.get(id)) != null) {
                    menuPresenter.onRestoreInstanceState(parcelable2);
                }
            }
        }
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        Parcelable onSaveInstanceState;
        NavigationBarView$SavedState navigationBarView$SavedState = new NavigationBarView$SavedState(super.onSaveInstanceState());
        Bundle bundle = new Bundle();
        navigationBarView$SavedState.menuPresenterState = bundle;
        NavigationBarMenu navigationBarMenu = this.menu;
        if (!navigationBarMenu.mPresenters.isEmpty()) {
            SparseArray<? extends Parcelable> sparseArray = new SparseArray<>();
            Iterator it = navigationBarMenu.mPresenters.iterator();
            while (it.hasNext()) {
                WeakReference weakReference = (WeakReference) it.next();
                MenuPresenter menuPresenter = (MenuPresenter) weakReference.get();
                if (menuPresenter == null) {
                    navigationBarMenu.mPresenters.remove(weakReference);
                } else {
                    int id = menuPresenter.getId();
                    if (id > 0 && (onSaveInstanceState = menuPresenter.onSaveInstanceState()) != null) {
                        sparseArray.put(id, onSaveInstanceState);
                    }
                }
            }
            bundle.putSparseParcelableArray("android:menu:presenters", sparseArray);
        }
        return navigationBarView$SavedState;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [android.view.ViewTreeObserver$OnGlobalLayoutListener, com.google.android.material.bottomnavigation.BottomNavigationView$2] */
    @Override // android.view.View
    public final void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        if (i != 0) {
            if (this.mOnGlobalLayoutListenerForTD != null) {
                getViewTreeObserver().removeOnGlobalLayoutListener(this.mOnGlobalLayoutListenerForTD);
                this.mOnGlobalLayoutListenerForTD = null;
                return;
            }
            return;
        }
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (viewTreeObserver == 0 || this.mOnGlobalLayoutListenerForTD != null) {
            return;
        }
        ?? r0 = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.google.android.material.bottomnavigation.BottomNavigationView.2
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                final BottomNavigationView bottomNavigationView = BottomNavigationView.this;
                if (bottomNavigationView != null) {
                    bottomNavigationView.post(new Runnable() { // from class: com.google.android.material.bottomnavigation.BottomNavigationView.2.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            View view;
                            SeslTouchTargetDelegate seslTouchTargetDelegate = new SeslTouchTargetDelegate(bottomNavigationView);
                            int childCount = bottomNavigationView.getChildCount();
                            boolean z = false;
                            int i2 = 0;
                            while (true) {
                                if (i2 >= childCount) {
                                    view = null;
                                    break;
                                }
                                view = bottomNavigationView.getChildAt(i2);
                                if (view instanceof BottomNavigationMenuView) {
                                    break;
                                } else {
                                    i2++;
                                }
                            }
                            if (view != null && view.getVisibility() == 0) {
                                ViewGroup viewGroup = (ViewGroup) view;
                                int childCount2 = viewGroup.getChildCount();
                                int i3 = 0;
                                boolean z2 = false;
                                while (i3 < childCount2) {
                                    View childAt = viewGroup.getChildAt(i3);
                                    if (childAt.getVisibility() == 0) {
                                        int measuredHeight = childAt.getMeasuredHeight() / 2;
                                        seslTouchTargetDelegate.addTouchDelegate(childAt, SeslTouchTargetDelegate.ExtraInsets.of(i3 == 0 ? measuredHeight : 0, measuredHeight, i3 == childCount2 + (-1) ? measuredHeight : 0, measuredHeight));
                                        z2 = true;
                                    }
                                    i3++;
                                }
                                z = z2;
                            }
                            if (z) {
                                bottomNavigationView.setTouchDelegate(seslTouchTargetDelegate);
                            }
                        }
                    });
                }
            }
        };
        this.mOnGlobalLayoutListenerForTD = r0;
        viewTreeObserver.addOnGlobalLayoutListener(r0);
    }

    @Override // android.view.View
    public final void setElevation(float f) {
        super.setElevation(f);
        MaterialShapeUtils.setElevation(this, f);
    }

    public BottomNavigationView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.bottomNavigationStyle);
    }

    public BottomNavigationView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 2132084665);
    }

    public BottomNavigationView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, i2), attributeSet, i);
        boolean z;
        int dimensionPixelSize;
        MenuBuilder.Callback callback = new MenuBuilder.Callback() { // from class: com.google.android.material.navigation.NavigationBarView$1
            @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
            public final boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
                int i3 = BottomNavigationView.$r8$clinit;
                BottomNavigationView bottomNavigationView = BottomNavigationView.this;
                bottomNavigationView.getClass();
                if (bottomNavigationView.reselectedListener == null || menuItem.getItemId() != bottomNavigationView.menuView.selectedItemId) {
                    BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = bottomNavigationView.selectedListener;
                    return (onNavigationItemSelectedListener == null || onNavigationItemSelectedListener.onNavigationItemSelected(menuItem)) ? false : true;
                }
                final SavedWifiEntrySettings savedWifiEntrySettings = bottomNavigationView.reselectedListener.f$0;
                savedWifiEntrySettings.getClass();
                if (menuItem.getItemId() == R.id.saved_access_points_bottom) {
                    if (savedWifiEntrySettings.mMode == 0) {
                        int checkedCount$1 = savedWifiEntrySettings.mAdapter.getCheckedCount$1();
                        if (checkedCount$1 > 0) {
                            AlertDialog alertDialog = savedWifiEntrySettings.mConfirmDialog;
                            if (alertDialog != null && alertDialog.isShowing()) {
                                savedWifiEntrySettings.mConfirmDialog.dismiss();
                            }
                            AlertDialog.Builder builder = new AlertDialog.Builder(savedWifiEntrySettings.getActivity());
                            String string = checkedCount$1 == savedWifiEntrySettings.mAdapter.getRemovableEntriesCount() ? savedWifiEntrySettings.mContext.getString(R.string.wifi_delete_all_networks) : savedWifiEntrySettings.mContext.getResources().getQuantityString(R.plurals.wifi_delete_item_network, checkedCount$1, Integer.valueOf(checkedCount$1));
                            AlertController.AlertParams alertParams = builder.P;
                            alertParams.mMessage = string;
                            alertParams.mCancelable = false;
                            builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() { // from class: com.samsung.android.settings.wifi.managenetwork.SavedWifiEntrySettings$$ExternalSyntheticLambda2
                                @Override // android.content.DialogInterface.OnClickListener
                                public final void onClick(DialogInterface dialogInterface, int i4) {
                                    SavedWifiEntrySettings savedWifiEntrySettings2 = SavedWifiEntrySettings.this;
                                    ProgressDialog progressDialog = savedWifiEntrySettings2.mProgressDialog;
                                    if (progressDialog != null && progressDialog.isShowing()) {
                                        savedWifiEntrySettings2.mProgressDialog.dismiss();
                                    }
                                    savedWifiEntrySettings2.mProgressDialog.show();
                                    final SavedWifiEntryListAdapter savedWifiEntryListAdapter = savedWifiEntrySettings2.mAdapter;
                                    synchronized (savedWifiEntryListAdapter.mLock) {
                                        try {
                                            SALogging.insertSALog(savedWifiEntryListAdapter.mSavedWifiEntries.size(), "WIFI_220", "12512", (String) null);
                                            Iterator it = savedWifiEntryListAdapter.mSavedWifiEntries.iterator();
                                            while (it.hasNext()) {
                                                final SavedWifiEntry savedWifiEntry = (SavedWifiEntry) it.next();
                                                if (savedWifiEntry.mIsChecked) {
                                                    savedWifiEntryListAdapter.mWifiEntryCandidates.add(savedWifiEntry);
                                                    boolean booleanValue = savedWifiEntry.isRemovableNetwork().booleanValue();
                                                    WifiEntry wifiEntry = savedWifiEntry.mWifiEntry;
                                                    if (booleanValue) {
                                                        wifiEntry.forget(new WifiEntry.ForgetCallback() { // from class: com.samsung.android.settings.wifi.managenetwork.SavedWifiEntryListAdapter$$ExternalSyntheticLambda3
                                                            @Override // com.android.wifitrackerlib.WifiEntry.ForgetCallback
                                                            public final void onForgetResult(int i5) {
                                                                SavedWifiEntryListAdapter savedWifiEntryListAdapter2 = SavedWifiEntryListAdapter.this;
                                                                savedWifiEntryListAdapter2.mWifiEntryCandidates.remove(savedWifiEntry);
                                                                if (savedWifiEntryListAdapter2.mWifiEntryCandidates.isEmpty()) {
                                                                    SavedWifiEntrySettings savedWifiEntrySettings3 = savedWifiEntryListAdapter2.mListener;
                                                                    ProgressDialog progressDialog2 = savedWifiEntrySettings3.mProgressDialog;
                                                                    if (progressDialog2 != null && progressDialog2.isShowing()) {
                                                                        savedWifiEntrySettings3.mProgressDialog.dismiss();
                                                                    }
                                                                    savedWifiEntrySettings3.setMode$2(1);
                                                                }
                                                            }
                                                        });
                                                    } else {
                                                        savedWifiEntryListAdapter.mWifiEntryCandidates.remove(savedWifiEntry);
                                                        if (savedWifiEntryListAdapter.mWifiEntryCandidates.isEmpty()) {
                                                            SavedWifiEntrySettings savedWifiEntrySettings3 = savedWifiEntryListAdapter.mListener;
                                                            ProgressDialog progressDialog2 = savedWifiEntrySettings3.mProgressDialog;
                                                            if (progressDialog2 != null && progressDialog2.isShowing()) {
                                                                savedWifiEntrySettings3.mProgressDialog.dismiss();
                                                            }
                                                            savedWifiEntrySettings3.setMode$2(1);
                                                        }
                                                    }
                                                    SCloudWifiDataManager.getInstance(savedWifiEntryListAdapter.mContext.getApplicationContext()).syncToRemove(wifiEntry.getWifiConfiguration());
                                                    Log.d("SavedWifiEntryListAdapter", "remove " + savedWifiEntry.mWifiEntry.getTitle());
                                                }
                                            }
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
                                }
                            });
                            builder.setNegativeButton(R.string.cancel, new SavedWifiEntrySettings$$ExternalSyntheticLambda3());
                            alertParams.mOnDismissListener = new DialogInterface.OnDismissListener() { // from class: com.samsung.android.settings.wifi.managenetwork.SavedWifiEntrySettings$$ExternalSyntheticLambda4
                                @Override // android.content.DialogInterface.OnDismissListener
                                public final void onDismiss(DialogInterface dialogInterface) {
                                    SavedWifiEntrySettings savedWifiEntrySettings2 = SavedWifiEntrySettings.this;
                                    if (savedWifiEntrySettings2.mConfirmDialog == dialogInterface) {
                                        savedWifiEntrySettings2.mConfirmDialog = null;
                                    }
                                }
                            };
                            savedWifiEntrySettings.mConfirmDialog = builder.show();
                        } else {
                            savedWifiEntrySettings.mMode = 1;
                            savedWifiEntrySettings.setMode$2(1);
                        }
                    } else {
                        savedWifiEntrySettings.mMode = 0;
                        savedWifiEntrySettings.setMode$2(0);
                    }
                }
                return true;
            }

            @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
            public final void onMenuModeChange(MenuBuilder menuBuilder) {
            }
        };
        Context context2 = getContext();
        TintTypedArray obtainTintedStyledAttributes = ThemeEnforcement.obtainTintedStyledAttributes(context2, attributeSet, R$styleable.NavigationBarView, i, i2, 13, 11, 18);
        NavigationBarMenu navigationBarMenu = new NavigationBarMenu(context2, getClass());
        this.menu = navigationBarMenu;
        BottomNavigationMenuView bottomNavigationMenuView = new BottomNavigationMenuView(context2);
        this.menuView = bottomNavigationMenuView;
        NavigationBarPresenter navigationBarPresenter = new NavigationBarPresenter(context2);
        this.presenter = navigationBarPresenter;
        bottomNavigationMenuView.mMaxItemCount = 5;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 17;
        bottomNavigationMenuView.setLayoutParams(layoutParams);
        int integer = obtainTintedStyledAttributes.mWrapped.getInteger(19, 3);
        bottomNavigationMenuView.mViewType = integer;
        navigationBarPresenter.menuView = bottomNavigationMenuView;
        navigationBarPresenter.id = 1;
        bottomNavigationMenuView.presenter = navigationBarPresenter;
        navigationBarMenu.addMenuPresenter(navigationBarPresenter, navigationBarMenu.mContext);
        navigationBarPresenter.initForMenu(getContext(), navigationBarMenu);
        if (obtainTintedStyledAttributes.mWrapped.hasValue(6)) {
            bottomNavigationMenuView.setIconTintList(obtainTintedStyledAttributes.getColorStateList(6));
        } else {
            bottomNavigationMenuView.setIconTintList(bottomNavigationMenuView.createDefaultColorStateList());
        }
        bottomNavigationMenuView.setItemIconSize(obtainTintedStyledAttributes.mWrapped.getDimensionPixelSize(5, getResources().getDimensionPixelSize(R.dimen.sesl_navigation_bar_icon_size)));
        if (obtainTintedStyledAttributes.mWrapped.hasValue(13)) {
            bottomNavigationMenuView.setItemTextAppearanceInactive(obtainTintedStyledAttributes.mWrapped.getResourceId(13, 0));
        }
        if (obtainTintedStyledAttributes.mWrapped.hasValue(18)) {
            bottomNavigationMenuView.seslSetLabelTextAppearance(obtainTintedStyledAttributes.mWrapped.getResourceId(18, 0));
        }
        if (obtainTintedStyledAttributes.mWrapped.hasValue(11)) {
            bottomNavigationMenuView.setItemTextAppearanceActive(obtainTintedStyledAttributes.mWrapped.getResourceId(11, 0));
        }
        bottomNavigationMenuView.setItemTextAppearanceActiveBoldEnabled(obtainTintedStyledAttributes.mWrapped.getBoolean(12, true));
        if (obtainTintedStyledAttributes.mWrapped.hasValue(14)) {
            bottomNavigationMenuView.setItemTextColor(obtainTintedStyledAttributes.getColorStateList(14));
        }
        Drawable background = getBackground();
        ColorStateList colorStateListOrNull = DrawableUtils.getColorStateListOrNull(background);
        if (background == null || colorStateListOrNull != null) {
            MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(ShapeAppearanceModel.builder(context2, attributeSet, i, i2).build());
            if (colorStateListOrNull != null) {
                materialShapeDrawable.setFillColor(colorStateListOrNull);
            }
            materialShapeDrawable.initializeElevationOverlay(context2);
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            setBackground(materialShapeDrawable);
        }
        if (background instanceof ColorDrawable) {
            bottomNavigationMenuView.mSBBTextColorDrawable = (ColorDrawable) background;
        }
        if (obtainTintedStyledAttributes.mWrapped.hasValue(8)) {
            bottomNavigationMenuView.setItemPaddingTop(obtainTintedStyledAttributes.mWrapped.getDimensionPixelSize(8, 0));
        }
        if (obtainTintedStyledAttributes.mWrapped.hasValue(7)) {
            bottomNavigationMenuView.setItemPaddingBottom(obtainTintedStyledAttributes.mWrapped.getDimensionPixelSize(7, 0));
        }
        if (obtainTintedStyledAttributes.mWrapped.hasValue(0)) {
            bottomNavigationMenuView.setActiveIndicatorLabelPadding(obtainTintedStyledAttributes.mWrapped.getDimensionPixelSize(0, 0));
        }
        if (obtainTintedStyledAttributes.mWrapped.hasValue(2)) {
            setElevation(obtainTintedStyledAttributes.mWrapped.getDimensionPixelSize(2, 0));
        }
        getBackground().mutate().setTintList(MaterialResources.getColorStateList(context2, obtainTintedStyledAttributes, 1));
        int integer2 = obtainTintedStyledAttributes.mWrapped.getInteger(15, -1);
        if (bottomNavigationMenuView.labelVisibilityMode != integer2) {
            bottomNavigationMenuView.labelVisibilityMode = integer2;
            navigationBarPresenter.updateMenuView(false);
        }
        int resourceId = obtainTintedStyledAttributes.mWrapped.getResourceId(4, 0);
        if (resourceId != 0) {
            bottomNavigationMenuView.setItemBackgroundRes(resourceId);
        } else {
            bottomNavigationMenuView.setItemRippleColor(MaterialResources.getColorStateList(context2, obtainTintedStyledAttributes, 9));
        }
        int resourceId2 = obtainTintedStyledAttributes.mWrapped.getResourceId(10, 0);
        if (resourceId2 != 0) {
            bottomNavigationMenuView.setItemStateListAnimator(resourceId2);
        }
        int resourceId3 = obtainTintedStyledAttributes.mWrapped.getResourceId(3, 0);
        if (resourceId3 != 0) {
            bottomNavigationMenuView.setItemActiveIndicatorEnabled();
            TypedArray obtainStyledAttributes = context2.obtainStyledAttributes(resourceId3, R$styleable.NavigationBarActiveIndicator);
            bottomNavigationMenuView.setItemActiveIndicatorWidth(obtainStyledAttributes.getDimensionPixelSize(1, 0));
            bottomNavigationMenuView.setItemActiveIndicatorHeight(obtainStyledAttributes.getDimensionPixelSize(0, 0));
            bottomNavigationMenuView.setItemActiveIndicatorMarginHorizontal(obtainStyledAttributes.getDimensionPixelOffset(3, 0));
            bottomNavigationMenuView.setItemActiveIndicatorColor(MaterialResources.getColorStateList(context2, obtainStyledAttributes, 2));
            bottomNavigationMenuView.setItemActiveIndicatorShapeAppearance(ShapeAppearanceModel.builder(context2, obtainStyledAttributes.getResourceId(4, 0), 0, new AbsoluteCornerSize(0)).build());
            obtainStyledAttributes.recycle();
        }
        if (obtainTintedStyledAttributes.mWrapped.hasValue(16)) {
            inflateMenu(obtainTintedStyledAttributes.mWrapped.getResourceId(16, 0));
        }
        if (obtainTintedStyledAttributes.mWrapped.hasValue(17)) {
            z = true;
            bottomNavigationMenuView.mExclusiveCheckable = obtainTintedStyledAttributes.mWrapped.getBoolean(17, true);
        } else {
            z = true;
        }
        obtainTintedStyledAttributes.recycle();
        addView(bottomNavigationMenuView);
        navigationBarMenu.mCallback = callback;
        bottomNavigationMenuView.mSelectedCallback = callback;
        int i3 = bottomNavigationMenuView.mVisibleItemCount;
        if (integer != 3 && i3 == 5) {
            int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.sesl_navigation_bar_icon_mode_min_padding_horizontal);
            setPadding(dimensionPixelSize2, getPaddingTop(), dimensionPixelSize2, getPaddingBottom());
        } else {
            int dimensionPixelSize3 = getResources().getDimensionPixelSize(R.dimen.sesl_navigation_bar_icon_mode_padding_horizontal);
            setPadding(dimensionPixelSize3, getPaddingTop(), dimensionPixelSize3, getPaddingBottom());
        }
        boolean z2 = z;
        TintTypedArray obtainTintedStyledAttributes2 = ThemeEnforcement.obtainTintedStyledAttributes(getContext(), attributeSet, R$styleable.BottomNavigationView, i, i2, new int[0]);
        boolean z3 = obtainTintedStyledAttributes2.mWrapped.getBoolean(5, z2);
        if (bottomNavigationMenuView.itemHorizontalTranslationEnabled != z3) {
            bottomNavigationMenuView.itemHorizontalTranslationEnabled = z3;
            navigationBarPresenter.updateMenuView(false);
        }
        obtainTintedStyledAttributes2.mWrapped.getBoolean(4, z2);
        obtainTintedStyledAttributes2.recycle();
        if (bottomNavigationMenuView.mViewType != 3) {
            dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.sesl_bottom_navigation_icon_mode_height);
        } else {
            dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.sesl_bottom_navigation_text_mode_height);
            int dimensionPixelSize4 = getResources().getDimensionPixelSize(R.dimen.sesl_navigation_bar_text_mode_padding_horizontal);
            setPadding(dimensionPixelSize4, getPaddingTop(), dimensionPixelSize4, getPaddingBottom());
        }
        bottomNavigationMenuView.setMinimumHeight(dimensionPixelSize);
        setMinimumHeight(dimensionPixelSize);
    }
}
