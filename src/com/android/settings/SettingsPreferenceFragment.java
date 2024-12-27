package com.android.settings;

import android.R;
import android.app.Dialog;
import android.app.admin.DevicePolicyManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.util.SeslRoundedCorner;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.Insets;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.core.InstrumentedPreferenceFragment;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.search.actionbar.SearchMenuController;
import com.android.settings.widget.HighlightablePreferenceGroupAdapter;
import com.android.settings.widget.LoadingViewController;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settings.widget.WorkOnlyCategory;
import com.android.settingslib.CustomDialogPreferenceCompat;
import com.android.settingslib.CustomEditTextPreferenceCompat;
import com.android.settingslib.widget.LayoutPreference;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.custom.ProKioskManager;
import com.samsung.android.knox.custom.SettingsManager;
import com.samsung.android.settings.Trace;
import com.samsung.android.settings.core.SecSettingsBaseActivity;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;
import com.samsung.android.settings.widget.SecActionButtonsPreference;
import com.samsung.android.settings.widget.SecButtonPreference;
import com.samsung.android.settings.widget.SecInsetCategoryPreference;
import com.samsung.android.settings.widget.SecUnclickablePreference;
import com.sec.ims.settings.ImsProfile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public abstract class SettingsPreferenceFragment extends InstrumentedPreferenceFragment
        implements DialogCreatable {
    private static final long DELAY_SET_ITEM_ANIMATOR_DURATION_MILLIS = 700;
    public static final String KEY_FOOTER_PREFERENCE = "auto_added_footer_preference";
    private static final int ORDER_FIRST = -1;
    private static final int ORDER_LAST = 2147483646;
    private static final String SAVE_HIGHLIGHTED_KEY = "android:preference_highlighted";
    private static final String TAG = "SettingsPreferenceFragment";
    public HighlightablePreferenceGroupAdapter mAdapter;
    private boolean mAnimationAllowed;
    private AppBarLayout mAppBarLayout;
    private ContentResolver mContentResolver;
    private Context mContext;
    private RecyclerView.Adapter mCurrentRootAdapter;
    protected DevicePolicyManager mDevicePolicyManager;
    private SettingsDialogFragment mDialogFragment;
    private View mEmptyView;
    private LayoutPreference mFooter;
    private SecInsetCategoryPreference mFooterPreference;
    private LayoutPreference mHeader;
    private LinearLayoutManager mLayoutManager;
    ViewGroup mPinnedHeaderFrameLayout;
    private ArrayMap<String, Preference> mPreferenceCache;
    private boolean mIsDataSetObserverRegistered = false;
    private int mProfileType = 1;
    private RecyclerView.AdapterDataObserver mDataSetObserver =
            new RecyclerView
                    .AdapterDataObserver() { // from class:
                                             // com.android.settings.SettingsPreferenceFragment.1
                @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
                public final void onChanged() {
                    SettingsPreferenceFragment settingsPreferenceFragment =
                            SettingsPreferenceFragment.this;
                    settingsPreferenceFragment.onDataSetChanged();
                    settingsPreferenceFragment.updateFooterPreference$1();
                    settingsPreferenceFragment.setApplicationRestrictionsForKnox();
                }

                @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
                public final void onItemRangeChanged(int i, int i2) {
                    SettingsPreferenceFragment settingsPreferenceFragment =
                            SettingsPreferenceFragment.this;
                    settingsPreferenceFragment.onDataSetChanged();
                    settingsPreferenceFragment.setApplicationRestrictionsForKnox();
                }

                @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
                public final void onItemRangeInserted(int i, int i2) {
                    SettingsPreferenceFragment settingsPreferenceFragment =
                            SettingsPreferenceFragment.this;
                    settingsPreferenceFragment.onDataSetChanged();
                    settingsPreferenceFragment.removeNeedlessInsetCategory();
                    settingsPreferenceFragment.updateFooterPreference$1();
                    settingsPreferenceFragment.setApplicationRestrictionsForKnox();
                }

                @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
                public final void onItemRangeMoved(int i, int i2) {
                    SettingsPreferenceFragment settingsPreferenceFragment =
                            SettingsPreferenceFragment.this;
                    settingsPreferenceFragment.onDataSetChanged();
                    settingsPreferenceFragment.setApplicationRestrictionsForKnox();
                }

                @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
                public final void onItemRangeRemoved(int i, int i2) {
                    SettingsPreferenceFragment settingsPreferenceFragment =
                            SettingsPreferenceFragment.this;
                    settingsPreferenceFragment.onDataSetChanged();
                    settingsPreferenceFragment.removeNeedlessInsetCategory();
                    settingsPreferenceFragment.updateFooterPreference$1();
                    settingsPreferenceFragment.setApplicationRestrictionsForKnox();
                }

                @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
                public final void onItemRangeChanged(int i, int i2, Object obj) {
                    SettingsPreferenceFragment settingsPreferenceFragment =
                            SettingsPreferenceFragment.this;
                    settingsPreferenceFragment.onDataSetChanged();
                    settingsPreferenceFragment.setApplicationRestrictionsForKnox();
                }
            };
    private boolean mAutoRemoveAvailable = true;
    private boolean mAutoAddFooterPreference = true;
    public boolean mPreferenceHighlighted = false;
    private int mRelativeLinkViewCount = 0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class SettingsDialogFragment extends InstrumentedDialogFragment {
        public DialogInterface.OnCancelListener mOnCancelListener;
        public DialogInterface.OnDismissListener mOnDismissListener;
        public Fragment mParentFragment;

        /* JADX WARN: Multi-variable type inference failed */
        public static SettingsDialogFragment newInstance(DialogCreatable dialogCreatable, int i) {
            if (!(dialogCreatable instanceof Fragment)) {
                throw new IllegalArgumentException(
                        "fragment argument must be an instance of "
                                .concat(Fragment.class.getName()));
            }
            SettingsDialogFragment settingsDialogFragment = new SettingsDialogFragment();
            settingsDialogFragment.mParentFragment = (Fragment) dialogCreatable;
            settingsDialogFragment.mDialogId = i;
            return settingsDialogFragment;
        }

        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            LifecycleOwner lifecycleOwner = this.mParentFragment;
            if (lifecycleOwner == null) {
                return 0;
            }
            int dialogMetricsCategory =
                    ((DialogCreatable) lifecycleOwner).getDialogMetricsCategory(this.mDialogId);
            if (dialogMetricsCategory > 0) {
                return dialogMetricsCategory;
            }
            throw new IllegalStateException("Dialog must provide a metrics category");
        }

        @Override // androidx.fragment.app.DialogFragment,
                  // android.content.DialogInterface.OnCancelListener
        public final void onCancel(DialogInterface dialogInterface) {
            DialogInterface.OnCancelListener onCancelListener = this.mOnCancelListener;
            if (onCancelListener != null) {
                onCancelListener.onCancel(dialogInterface);
            }
        }

        @Override // androidx.fragment.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            if (bundle != null) {
                this.mDialogId = bundle.getInt("key_dialog_id", 0);
                this.mParentFragment = getParentFragment();
                int i = bundle.getInt("key_parent_fragment_id", -1);
                if (this.mParentFragment == null) {
                    this.mParentFragment = getFragmentManager().findFragmentById(i);
                }
                Fragment fragment = this.mParentFragment;
                if (!(fragment instanceof DialogCreatable)) {
                    StringBuilder sb = new StringBuilder();
                    Fragment fragment2 = this.mParentFragment;
                    sb.append(
                            fragment2 != null
                                    ? fragment2.getClass().getName()
                                    : Integer.valueOf(i));
                    sb.append(" must implement ");
                    sb.append(DialogCreatable.class.getName());
                    throw new IllegalArgumentException(sb.toString());
                }
                if (fragment instanceof SettingsPreferenceFragment) {
                    ((SettingsPreferenceFragment) fragment).mDialogFragment = this;
                }
            }
            return ((DialogCreatable) this.mParentFragment).onCreateDialog(this.mDialogId);
        }

        @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
        public final void onDetach() {
            super.onDetach();
            Fragment fragment = this.mParentFragment;
            if ((fragment instanceof SettingsPreferenceFragment)
                    && ((SettingsPreferenceFragment) fragment).mDialogFragment == this) {
                ((SettingsPreferenceFragment) this.mParentFragment).mDialogFragment = null;
            }
        }

        @Override // androidx.fragment.app.DialogFragment,
                  // android.content.DialogInterface.OnDismissListener
        public final void onDismiss(DialogInterface dialogInterface) {
            super.onDismiss(dialogInterface);
            DialogInterface.OnDismissListener onDismissListener = this.mOnDismissListener;
            if (onDismissListener != null) {
                onDismissListener.onDismiss(dialogInterface);
            }
        }

        @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
        public final void onSaveInstanceState(Bundle bundle) {
            super.onSaveInstanceState(bundle);
            if (this.mParentFragment != null) {
                bundle.putInt("key_dialog_id", this.mDialogId);
                bundle.putInt("key_parent_fragment_id", this.mParentFragment.getId());
            }
        }

        @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
                  // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
        public final void onStart() {
            super.onStart();
            Fragment fragment = this.mParentFragment;
            if (fragment == null || !(fragment instanceof SettingsPreferenceFragment)) {
                return;
            }
            ((SettingsPreferenceFragment) fragment).onDialogShowing();
        }
    }

    /* renamed from: $r8$lambda$HhpdMdZJS9-Pr9Dbn5_blaFEvnA, reason: not valid java name */
    public static /* synthetic */ void m649$r8$lambda$HhpdMdZJS9Pr9Dbn5_blaFEvnA(
            final SettingsPreferenceFragment settingsPreferenceFragment,
            Toolbar toolbar,
            AppBarLayout appBarLayout,
            int i) {
        settingsPreferenceFragment.getClass();
        Rect rect = new Rect();
        appBarLayout.getWindowVisibleDisplayFrame(rect);
        final ViewGroup.LayoutParams layoutParams =
                settingsPreferenceFragment.mEmptyView.getLayoutParams();
        int height =
                Math.abs(i) == appBarLayout.getTotalScrollRange()
                        ? -1
                        : ((rect.bottom - rect.top) - toolbar.getHeight())
                                - (appBarLayout.getTotalScrollRange() - Math.abs(i));
        if (layoutParams == null || layoutParams.height == height) {
            return;
        }
        layoutParams.height = height;
        settingsPreferenceFragment.mEmptyView.post(
                new Runnable() { // from class:
                                 // com.android.settings.SettingsPreferenceFragment$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        SettingsPreferenceFragment.$r8$lambda$QhJTqC91BsPfbBdPWE0jUfJ6hUw(
                                SettingsPreferenceFragment.this, layoutParams);
                    }
                });
    }

    public static /* synthetic */ void $r8$lambda$QhJTqC91BsPfbBdPWE0jUfJ6hUw(
            SettingsPreferenceFragment settingsPreferenceFragment,
            ViewGroup.LayoutParams layoutParams) {
        settingsPreferenceFragment.mEmptyView.setLayoutParams(layoutParams);
        settingsPreferenceFragment.mEmptyView.requestLayout();
    }

    public static Preference findLastPreference(PreferenceGroup preferenceGroup, int i) {
        for (int i2 = i - 1; i2 >= 0; i2--) {
            Preference preference = preferenceGroup.getPreference(i2);
            if (preference != null && preference.isVisible()) {
                if (preference instanceof PreferenceGroup) {
                    PreferenceGroup preferenceGroup2 = (PreferenceGroup) preference;
                    if (preferenceGroup2.getPreferenceCount() > 0) {
                        return findLastPreference(
                                preferenceGroup2, preferenceGroup2.getPreferenceCount());
                    }
                }
                return preference;
            }
        }
        return null;
    }

    public final void addPreferenceToBottom(LayoutPreference layoutPreference) {
        layoutPreference.setOrder(ORDER_LAST - (this.mRelativeLinkViewCount * 2));
        if (getPreferenceScreen() != null) {
            getPreferenceScreen().addPreference(layoutPreference);
            setRelativeLinkViewCount(this.mRelativeLinkViewCount + 1);
        }
    }

    public final void addPreferenceToTop(LayoutPreference layoutPreference) {
        layoutPreference.setOrder(-1);
        if (getPreferenceScreen() != null) {
            getPreferenceScreen().addPreference(layoutPreference);
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public void addPreferencesFromResource(int i) {
        super.addPreferencesFromResource(i);
        checkAvailablePrefs(getPreferenceScreen());
    }

    public void applyListSpacing() {
        RecyclerView listViewWithSpacing;
        View findViewById;
        SettingsMainSwitchBar settingsMainSwitchBar;
        if (isListSpacingNeeded() && (listViewWithSpacing = getListViewWithSpacing()) != null) {
            int listHorizontalPadding = Utils.getListHorizontalPadding(this.mContext);
            setPadding(listHorizontalPadding, 0, listHorizontalPadding, 0);
            if (getListView() == null) {
                final SeslRoundedCorner seslRoundedCorner = new SeslRoundedCorner(this.mContext);
                seslRoundedCorner.setRoundedCorners(3);
                listViewWithSpacing.setPadding(listHorizontalPadding, 0, listHorizontalPadding, 0);
                listViewWithSpacing.setScrollBarStyle(33554432);
                listViewWithSpacing.seslSetFillHorizontalPaddingEnabled(true);
                listViewWithSpacing.addItemDecoration(
                        new RecyclerView
                                .ItemDecoration() { // from class:
                                                    // com.android.settings.SettingsPreferenceFragment.2
                            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                            public final void seslOnDispatchDraw(
                                    Canvas canvas,
                                    RecyclerView recyclerView,
                                    RecyclerView.State state) {
                                SeslRoundedCorner.this.drawRoundedCorner(
                                        canvas,
                                        Insets.of(
                                                recyclerView.getPaddingStart(),
                                                0,
                                                recyclerView.getPaddingEnd(),
                                                0));
                            }
                        });
            }
            if ((getActivity() instanceof SettingsActivity)
                    && (settingsMainSwitchBar = ((SettingsActivity) getActivity()).mMainSwitch)
                            != null) {
                ViewGroup.MarginLayoutParams marginLayoutParams =
                        (ViewGroup.MarginLayoutParams) settingsMainSwitchBar.getLayoutParams();
                marginLayoutParams.setMargins(
                        listHorizontalPadding,
                        marginLayoutParams.topMargin,
                        listHorizontalPadding,
                        marginLayoutParams.bottomMargin);
                settingsMainSwitchBar.setLayoutParams(marginLayoutParams);
            }
            if (!(getActivity() instanceof SecSettingsBaseActivity)
                    || (findViewById = getActivity().findViewById(R.id.round_corner)) == null) {
                return;
            }
            CoordinatorLayout.LayoutParams layoutParams =
                    (CoordinatorLayout.LayoutParams) findViewById.getLayoutParams();
            layoutParams.setMarginsRelative(listHorizontalPadding, 0, listHorizontalPadding, 0);
            findViewById.setLayoutParams(layoutParams);
            findViewById.semSetRoundedCorners(12);
            findViewById.semSetRoundedCornerColor(
                    12, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        }
    }

    public void cacheRemoveAllPrefs(PreferenceGroup preferenceGroup) {
        this.mPreferenceCache = new ArrayMap<>();
        int preferenceCount = preferenceGroup.getPreferenceCount();
        for (int i = 0; i < preferenceCount; i++) {
            Preference preference = preferenceGroup.getPreference(i);
            if (!TextUtils.isEmpty(preference.getKey())) {
                this.mPreferenceCache.put(preference.getKey(), preference);
            }
        }
    }

    public void checkAvailablePrefs(PreferenceGroup preferenceGroup) {
        if (preferenceGroup == null) {
            return;
        }
        for (int i = 0; i < preferenceGroup.getPreferenceCount(); i++) {
            Preference preference = preferenceGroup.getPreference(i);
            if (preference instanceof WorkOnlyCategory) {
                Context context = getContext();
                ((WorkOnlyCategory) preference).getClass();
                UserManager userManager = UserManager.get(context);
                StringBuilder sb = Utils.sBuilder;
                if (Utils.getManagedProfileWithDisabled(userManager, UserHandle.myUserId())
                        == null) {
                    preference.setVisible(false);
                }
            }
            if (preference instanceof PreferenceGroup) {
                checkAvailablePrefs((PreferenceGroup) preference);
            }
        }
    }

    public void checkForKnoxCustomProKioskEnabledItems() {
        ProKioskManager proKioskManager = ProKioskManager.getInstance();
        if (proKioskManager == null || !proKioskManager.getProKioskState()) {
            return;
        }
        int settingsEnabledItems = proKioskManager.getSettingsEnabledItems();
        SettingsManager settingsManager = SettingsManager.getInstance();
        int settingsHiddenState =
                settingsManager != null ? settingsManager.getSettingsHiddenState() : 0;
        ArrayList arrayList = new ArrayList();
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (preferenceScreen != null) {
            int preferenceCount = preferenceScreen.getPreferenceCount();
            for (int i = 0; i < preferenceCount; i++) {
                Preference preference = preferenceScreen.getPreference(i);
                arrayList.add(preference.getKey());
                if (preference instanceof PreferenceGroup) {
                    PreferenceGroup preferenceGroup = (PreferenceGroup) preference;
                    int preferenceCount2 = preferenceGroup.getPreferenceCount();
                    for (int i2 = 0; i2 < preferenceCount2; i2++) {
                        arrayList.add(preferenceGroup.getPreference(i2).getKey());
                    }
                }
            }
        }
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            String str = (String) arrayList.get(i3);
            if (str != null && !str.isEmpty()) {
                boolean z =
                        (str.equals("wifi_settings")
                                        && (settingsHiddenState & 1) == 0
                                        && (settingsEnabledItems & 1) != 0)
                                ? false
                                : true;
                if (str.equals("bluetooth_settings")
                        && (settingsHiddenState & 2) == 0
                        && (settingsEnabledItems & 2) != 0) {
                    z = false;
                }
                if (str.equals("location_settings")
                        && (settingsHiddenState & 1024) == 0
                        && (settingsEnabledItems & 4) != 0) {
                    z = false;
                }
                if (z) {
                    removePreference(str);
                }
            }
        }
    }

    public void finish() {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            activity.finish();
        }
    }

    public final void finishFragment() {
        getActivity().onBackPressed();
    }

    public int getCachedCount() {
        ArrayMap<String, Preference> arrayMap = this.mPreferenceCache;
        if (arrayMap != null) {
            return arrayMap.size();
        }
        return 0;
    }

    public Preference getCachedPreference(String str) {
        ArrayMap<String, Preference> arrayMap = this.mPreferenceCache;
        if (arrayMap != null) {
            return arrayMap.remove(str);
        }
        return null;
    }

    public ContentResolver getContentResolver() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            this.mContentResolver = activity.getContentResolver();
        }
        return this.mContentResolver;
    }

    public SettingsDialogFragment getDialogFragment() {
        return this.mDialogFragment;
    }

    public View getEmptyView() {
        return this.mEmptyView;
    }

    public LayoutPreference getFooterView() {
        return this.mFooter;
    }

    public LayoutPreference getHeaderView() {
        return this.mHeader;
    }

    public int getInitialExpandedChildCount() {
        return 0;
    }

    public Intent getIntent() {
        if (getActivity() == null) {
            return null;
        }
        return getActivity().getIntent();
    }

    public RecyclerView getListViewWithSpacing() {
        return getListView();
    }

    public NestedScrollView getNestedScrollView() {
        return null;
    }

    public Button getNextButton() {
        return ((ButtonBarHandler) getActivity()).getNextButton();
    }

    public PackageManager getPackageManager() {
        return getActivity().getPackageManager();
    }

    public int getRelativeLinkViewCount() {
        return this.mRelativeLinkViewCount;
    }

    public Object getSystemService(String str) {
        return getActivity().getSystemService(str);
    }

    public boolean hasNextButton() {
        return ((ButtonBarHandler) getActivity()).hasNextButton();
    }

    public void highlightPreferenceIfNeeded(boolean z) {
        final HighlightablePreferenceGroupAdapter highlightablePreferenceGroupAdapter;
        if (isAdded() && (highlightablePreferenceGroupAdapter = this.mAdapter) != null) {
            View view = getView();
            final NestedScrollView nestedScrollView = getNestedScrollView();
            final RecyclerView listView = getListView();
            if (z) {
                highlightablePreferenceGroupAdapter.mHighlightRequested = false;
            }
            if (highlightablePreferenceGroupAdapter.mHighlightRequested
                    || listView == null
                    || TextUtils.isEmpty(highlightablePreferenceGroupAdapter.mHighlightKey)) {
                return;
            }
            highlightablePreferenceGroupAdapter.mHighlightRequested = true;
            view.postDelayed(
                    new Runnable() { // from class:
                                     // com.android.settings.widget.HighlightablePreferenceGroupAdapter$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            HighlightablePreferenceGroupAdapter
                                    highlightablePreferenceGroupAdapter2 =
                                            HighlightablePreferenceGroupAdapter.this;
                            NestedScrollView nestedScrollView2 = nestedScrollView;
                            RecyclerView recyclerView = listView;
                            int preferenceAdapterPosition =
                                    highlightablePreferenceGroupAdapter2
                                            .getPreferenceAdapterPosition(
                                                    highlightablePreferenceGroupAdapter2
                                                            .mHighlightKey);
                            if (preferenceAdapterPosition < 0) {
                                return;
                            }
                            if (nestedScrollView2 != null) {
                                RecyclerView.ViewHolder findViewHolderForAdapterPosition =
                                        recyclerView.findViewHolderForAdapterPosition(
                                                preferenceAdapterPosition);
                                if (findViewHolderForAdapterPosition != null) {
                                    nestedScrollView2.smoothScrollBy(
                                            0 - nestedScrollView2.getScrollX(),
                                            ((int) findViewHolderForAdapterPosition.itemView.getY())
                                                    - nestedScrollView2.getScrollY(),
                                            IKnoxCustomManager.Stub
                                                    .TRANSACTION_addDexURLShortcutExtend);
                                }
                            } else {
                                recyclerView.setItemAnimator(null);
                                recyclerView.smoothScrollToPosition(preferenceAdapterPosition);
                            }
                            highlightablePreferenceGroupAdapter2.mHighlightPosition =
                                    preferenceAdapterPosition;
                            highlightablePreferenceGroupAdapter2.notifyItemChanged(
                                    preferenceAdapterPosition);
                        }
                    },
                    600L);
        }
    }

    public boolean isAutoRemoveAvailable() {
        return this.mAutoRemoveAvailable;
    }

    public boolean isFinishingOrDestroyed() {
        FragmentActivity activity = getActivity();
        return activity == null || activity.isFinishing() || activity.isDestroyed();
    }

    public boolean isListSpacingNeeded() {
        return true;
    }

    public boolean isPreferenceAnimationAllowed() {
        return true;
    }

    public boolean isPreferenceExpanded(Preference preference) {
        HighlightablePreferenceGroupAdapter highlightablePreferenceGroupAdapter = this.mAdapter;
        return highlightablePreferenceGroupAdapter == null
                || highlightablePreferenceGroupAdapter.getPreferenceAdapterPosition(preference)
                        != -1;
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        SettingsMainSwitchBar settingsMainSwitchBar;
        super.onActivityCreated(bundle);
        setHasOptionsMenu(true);
        if (isPreferenceAnimationAllowed()) {
            new Handler()
                    .postDelayed(
                            new Runnable() { // from class:
                                             // com.android.settings.SettingsPreferenceFragment$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    RecyclerView listView =
                                            SettingsPreferenceFragment.this.getListView();
                                    if (listView != null) {
                                        listView.setItemAnimator(new DefaultItemAnimator());
                                    }
                                }
                            },
                            DELAY_SET_ITEM_ANIMATOR_DURATION_MILLIS);
        }
        if (!(getActivity() instanceof SettingsActivity)
                || (settingsMainSwitchBar = ((SettingsActivity) getActivity()).mMainSwitch)
                        == null) {
            return;
        }
        settingsMainSwitchBar.setSessionDescription(String.valueOf(getActivity().getTitle()));
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        if (shouldSkipForInitialSUW() && !WizardManagerHelper.isDeviceProvisioned(getContext())) {
            Log.w(TAG, "Skip " + getClass().getSimpleName() + " before SUW completed.");
            finish();
        }
        super.onAttach(context);
        this.mContext = context;
        if (getArguments() != null) {
            this.mProfileType = getArguments().getInt(ImsProfile.SERVICE_PROFILE);
        } else {
            Log.w(TAG, "getArguments() is null or EXTRA_PROFILE is missing");
        }
    }

    @Override // androidx.preference.PreferenceFragmentCompat
    public void onBindPreferences() {
        registerObserverIfNeeded();
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        applyListSpacing();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mDevicePolicyManager =
                (DevicePolicyManager) getContext().getSystemService(DevicePolicyManager.class);
        getSettingsLifecycle().addObserver(new SearchMenuController(this, getMetricsCategory()));
        if (bundle != null) {
            this.mPreferenceHighlighted = bundle.getBoolean(SAVE_HIGHLIGHTED_KEY);
        }
    }

    @Override // androidx.preference.PreferenceFragmentCompat
    public RecyclerView.Adapter onCreateAdapter(PreferenceScreen preferenceScreen) {
        Bundle arguments = getArguments();
        HighlightablePreferenceGroupAdapter highlightablePreferenceGroupAdapter =
                new HighlightablePreferenceGroupAdapter(
                        preferenceScreen,
                        arguments == null
                                ? null
                                : arguments.getString(":settings:fragment_args_key"),
                        this.mPreferenceHighlighted);
        this.mAdapter = highlightablePreferenceGroupAdapter;
        this.mPreferenceHighlighted = true;
        return highlightablePreferenceGroupAdapter;
    }

    @Override // com.android.settings.DialogCreatable
    public Dialog onCreateDialog(int i) {
        return null;
    }

    @Override // androidx.preference.PreferenceFragmentCompat
    public RecyclerView.LayoutManager onCreateLayoutManager() {
        getContext();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(1);
        this.mLayoutManager = linearLayoutManager;
        return linearLayoutManager;
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        this.mPinnedHeaderFrameLayout = (ViewGroup) onCreateView.findViewById(R.id.pinned_header);
        this.mAppBarLayout = (AppBarLayout) getActivity().findViewById(R.id.app_bar);
        return onCreateView;
    }

    public void onDataSetChanged() {
        highlightPreferenceIfNeeded();
        updateEmptyView();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDetach() {
        List list;
        if (isRemoving()) {
            SettingsDialogFragment settingsDialogFragment = this.mDialogFragment;
            if (settingsDialogFragment != null) {
                settingsDialogFragment.dismissInternal(false, false);
                this.mDialogFragment = null;
            }
            RecyclerView listView = getListView();
            if (listView != null && (list = listView.mScrollListeners) != null) {
                ((ArrayList) list).clear();
            }
        }
        super.onDetach();
    }

    @Override // androidx.preference.PreferenceFragmentCompat
    public void onDisplayPreferenceDialog(Preference preference) {
        DialogFragment customPreferenceDialogFragment;
        if (preference.getKey() == null) {
            preference.setKey(UUID.randomUUID().toString());
        }
        if (preference instanceof RestrictedListPreference) {
            String key = preference.getKey();
            customPreferenceDialogFragment =
                    new RestrictedListPreference.RestrictedListPreferenceDialogFragment();
            Bundle bundle = new Bundle(1);
            bundle.putString(GoodSettingsContract.EXTRA_NAME.POLICY_KEY, key);
            customPreferenceDialogFragment.setArguments(bundle);
        } else if (preference instanceof CustomListPreference) {
            String key2 = preference.getKey();
            customPreferenceDialogFragment =
                    new CustomListPreference.CustomListPreferenceDialogFragment();
            Bundle bundle2 = new Bundle(1);
            bundle2.putString(GoodSettingsContract.EXTRA_NAME.POLICY_KEY, key2);
            customPreferenceDialogFragment.setArguments(bundle2);
        } else if (preference instanceof CustomDialogPreferenceCompat) {
            String key3 = preference.getKey();
            customPreferenceDialogFragment =
                    new CustomDialogPreferenceCompat.CustomPreferenceDialogFragment();
            Bundle bundle3 = new Bundle(1);
            bundle3.putString(GoodSettingsContract.EXTRA_NAME.POLICY_KEY, key3);
            customPreferenceDialogFragment.setArguments(bundle3);
        } else {
            if (!(preference instanceof CustomEditTextPreferenceCompat)) {
                super.onDisplayPreferenceDialog(preference);
                return;
            }
            String key4 = preference.getKey();
            customPreferenceDialogFragment =
                    new CustomEditTextPreferenceCompat.CustomPreferenceDialogFragment();
            Bundle bundle4 = new Bundle(1);
            bundle4.putString(GoodSettingsContract.EXTRA_NAME.POLICY_KEY, key4);
            customPreferenceDialogFragment.setArguments(bundle4);
        }
        customPreferenceDialogFragment.setTargetFragment(this, 0);
        customPreferenceDialogFragment.show(getFragmentManager(), "dialog_preference");
        onDialogShowing();
    }

    public void onNumSavedNetworksChanged() {
        isFinishingOrDestroyed();
    }

    public void onNumSavedSubscriptionsChanged() {
        isFinishingOrDestroyed();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public void onResume() {
        Trace.beginSection("SettingsPreferenceFragment#onResume");
        super.onResume();
        Trace.beginSection("SettingsPreferenceFragment#highlightPreferenceIfNeeded");
        highlightPreferenceIfNeeded();
        Trace.endSection();
        Trace.endSection();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        HighlightablePreferenceGroupAdapter highlightablePreferenceGroupAdapter = this.mAdapter;
        if (highlightablePreferenceGroupAdapter != null) {
            bundle.putBoolean(
                    SAVE_HIGHLIGHTED_KEY, highlightablePreferenceGroupAdapter.mHighlightRequested);
        }
    }

    @Override // androidx.preference.PreferenceFragmentCompat
    public void onUnbindPreferences() {
        unregisterObserverIfNeeded();
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        applyListSpacing();
    }

    public void registerObserverIfNeeded() {
        if (this.mIsDataSetObserverRegistered) {
            return;
        }
        RecyclerView.Adapter adapter = this.mCurrentRootAdapter;
        if (adapter != null) {
            adapter.unregisterAdapterDataObserver(this.mDataSetObserver);
        }
        RecyclerView.Adapter adapter2 = getListView().getAdapter();
        this.mCurrentRootAdapter = adapter2;
        adapter2.registerAdapterDataObserver(this.mDataSetObserver);
        this.mIsDataSetObserverRegistered = true;
        onDataSetChanged();
        removeNeedlessInsetCategory();
        updateFooterPreference$1();
        setApplicationRestrictionsForKnox();
    }

    public void removeCachedPrefs(PreferenceGroup preferenceGroup) {
        Iterator<Preference> it = this.mPreferenceCache.values().iterator();
        while (it.hasNext()) {
            preferenceGroup.removePreference(it.next());
        }
        this.mPreferenceCache = null;
    }

    public void removeDialog(int i) {
        SettingsDialogFragment settingsDialogFragment = this.mDialogFragment;
        if (settingsDialogFragment != null && settingsDialogFragment.mDialogId == i) {
            settingsDialogFragment.dismissAllowingStateLoss();
        }
        this.mDialogFragment = null;
    }

    public void removeHeaderView() {
        if (this.mHeader != null) {
            getPreferenceScreen().removePreference(this.mHeader);
            this.mHeader = null;
        }
    }

    public void removeNeedlessInsetCategory() {
        if (this.mAutoRemoveAvailable) {
            PreferenceScreen preferenceScreen = getPreferenceScreen();
            ArrayList arrayList = new ArrayList();
            int preferenceCount = preferenceScreen.getPreferenceCount();
            if (preferenceCount <= 0) {
                return;
            }
            int i = 0;
            while (true) {
                if (i >= preferenceCount) {
                    i = 0;
                    break;
                }
                Preference preference = preferenceScreen.getPreference(i);
                if (preference != null && preference.isVisible()) {
                    if (!(preference instanceof SecInsetCategoryPreference)) {
                        break;
                    } else {
                        arrayList.add(preference);
                    }
                }
                i++;
            }
            Preference preference2 = null;
            while (i < preferenceCount) {
                Preference preference3 = preferenceScreen.getPreference(i);
                if (preference3 != null && preference3.isVisible()) {
                    if ((preference2 instanceof SecInsetCategoryPreference)
                            && (preference3 instanceof PreferenceCategory)) {
                        arrayList.add(preference2);
                    } else if ((preference2 instanceof PreferenceCategory)
                            && (preference3 instanceof SecInsetCategoryPreference)
                            && ((PreferenceCategory) preference2).getPreferenceCount() == 0) {
                        arrayList.add(preference3);
                    }
                    preference2 = preference3;
                }
                i++;
            }
            if (preference2 instanceof SecInsetCategoryPreference) {
                arrayList.add(preference2);
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Preference preference4 = (Preference) it.next();
                if ((preference4 instanceof SecInsetCategoryPreference)
                        && ((SecInsetCategoryPreference) preference4).mRemovable) {
                    preference4.setVisible(false);
                }
            }
        }
    }

    public boolean removePreference(String str) {
        return removePreference(getPreferenceScreen(), str);
    }

    public void replaceEnterprisePreferenceScreenTitle(String str, int i) {
        getActivity()
                .setTitle(
                        this.mDevicePolicyManager
                                .getResources()
                                .getString(
                                        str,
                                        new SettingsPreferenceFragment$$ExternalSyntheticLambda0(
                                                this, i, 1)));
    }

    public void replaceEnterpriseStringSummary(String str, String str2, int i) {
        Preference findPreference = findPreference(str);
        if (findPreference == null) {
            DialogFragment$$ExternalSyntheticOutline0.m(
                    "Could not find enterprise preference ", str, TAG);
        } else {
            findPreference.setSummary(
                    this.mDevicePolicyManager
                            .getResources()
                            .getString(
                                    str2,
                                    new SettingsPreferenceFragment$$ExternalSyntheticLambda0(
                                            this, i, 2)));
        }
    }

    public void replaceEnterpriseStringTitle(String str, String str2, int i) {
        Preference findPreference = findPreference(str);
        if (findPreference == null) {
            DialogFragment$$ExternalSyntheticOutline0.m(
                    "Could not find enterprise preference ", str, TAG);
        } else {
            findPreference.setTitle(
                    this.mDevicePolicyManager
                            .getResources()
                            .getString(
                                    str2,
                                    new SettingsPreferenceFragment$$ExternalSyntheticLambda0(
                                            this, i, 0)));
        }
    }

    public void setAnimationAllowed(boolean z) {
        this.mAnimationAllowed = z;
    }

    public void setApplicationRestrictionsForKnox() {
        Preference findPreference;
        if (KnoxUtils.appRestrictionState) {
            Bundle applicationRestrictions =
                    KnoxUtils.getApplicationRestrictions(
                            this.mContext,
                            this.mProfileType == 2
                                    ? UsefulfeatureUtils.getManagedProfileId(this.mContext)
                                    : 0);
            for (String str : applicationRestrictions.keySet()) {
                Bundle bundle = applicationRestrictions.getBundle(str);
                if (bundle != null) {
                    if (bundle.getBoolean("hide")) {
                        PreferenceGroup preferenceGroup = null;
                        if (str != null) {
                            PreferenceScreen preferenceScreen = getPreferenceScreen();
                            int preferenceCount = preferenceScreen.getPreferenceCount();
                            int i = 0;
                            while (true) {
                                if (i >= preferenceCount) {
                                    break;
                                }
                                if (preferenceScreen.getPreference(i) instanceof PreferenceGroup) {
                                    PreferenceGroup preferenceGroup2 =
                                            (PreferenceGroup) preferenceScreen.getPreference(i);
                                    if (preferenceGroup2.findPreference(str) != null
                                            && preferenceGroup2.findPreference(str) != null
                                            && preferenceGroup2.getKey() != null
                                            && !preferenceGroup2.getKey().equals(str)) {
                                        Log.d(
                                                TAG,
                                                "buildPreferenceForCOM getParentPreference ="
                                                        .concat(str));
                                        preferenceGroup = preferenceGroup2;
                                        break;
                                    }
                                }
                                i++;
                            }
                        }
                        Preference findPreference2 = findPreference(str);
                        if (!(findPreference2 instanceof PreferenceCategory)
                                && (preferenceGroup instanceof PreferenceGroup)) {
                            preferenceGroup.removePreference(findPreference2);
                        }
                        removePreference(str);
                    } else if (bundle.getBoolean("grayout")
                            && (findPreference = getPreferenceScreen().findPreference(str))
                                    != null) {
                        findPreference.setEnabled(false);
                    }
                }
            }
        }
    }

    public void setAutoAddFooterPreference(boolean z) {
        this.mAutoAddFooterPreference = z;
    }

    public void setAutoRemoveInsetCategory(boolean z) {
        this.mAutoRemoveAvailable = z;
    }

    /* JADX WARN: Removed duplicated region for block: B:58:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00e5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setBadgeIconWidget() {
        /*
            Method dump skipped, instructions count: 343
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.SettingsPreferenceFragment.setBadgeIconWidget():void");
    }

    public void setEmptyFooterView() {
        LayoutPreference layoutPreference =
                new LayoutPreference(
                        getPrefContext(),
                        ((LayoutInflater) getSystemService("layout_inflater"))
                                .inflate(R.layout.sec_empty_footer_view, (ViewGroup) null));
        this.mFooter = layoutPreference;
        layoutPreference.seslSetSubheaderRoundedBackground(0);
        SecInsetCategoryPreference secInsetCategoryPreference =
                new SecInsetCategoryPreference(getContext());
        secInsetCategoryPreference.setOrder(2147483645 - (this.mRelativeLinkViewCount * 2));
        secInsetCategoryPreference.seslSetSubheaderRoundedBackground(12);
        if (getPreferenceScreen() != null) {
            getPreferenceScreen().addPreference(secInsetCategoryPreference);
        }
        if (getListView() != null) {
            getListView().mDrawLastRoundedCorner = false;
        }
        addPreferenceToBottom(this.mFooter);
    }

    public void setEmptyView(View view) {
        View view2 = this.mEmptyView;
        if (view2 != null) {
            view2.setVisibility(8);
        }
        this.mEmptyView = view;
        updateEmptyView();
    }

    public void setFooterView(int i) {
        LayoutPreference layoutPreference = new LayoutPreference(getPrefContext(), i);
        this.mFooter = layoutPreference;
        addPreferenceToBottom(layoutPreference);
    }

    public void setHeaderView(int i) {
        LayoutPreference layoutPreference = new LayoutPreference(getPrefContext(), i);
        this.mHeader = layoutPreference;
        layoutPreference.setSelectable(false);
        addPreferenceToTop(this.mHeader);
    }

    public void setLoading(boolean z, boolean z2) {
        View findViewById = getView().findViewById(R.id.loading_container);
        if (findViewById != null) {
            LoadingViewController.setViewShown(findViewById, z, z2);
            LoadingViewController.setViewShown(getListView(), !z, z2);
        }
    }

    public void setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        SettingsDialogFragment settingsDialogFragment = this.mDialogFragment;
        if (settingsDialogFragment != null) {
            settingsDialogFragment.mOnCancelListener = onCancelListener;
        }
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        SettingsDialogFragment settingsDialogFragment = this.mDialogFragment;
        if (settingsDialogFragment != null) {
            settingsDialogFragment.mOnDismissListener = onDismissListener;
        }
    }

    public View setPinnedHeaderView(int i) {
        View inflate =
                getActivity().getLayoutInflater().inflate(i, this.mPinnedHeaderFrameLayout, false);
        setPinnedHeaderView(inflate);
        return inflate;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public void setPreferenceScreen(PreferenceScreen preferenceScreen) {
        boolean z;
        if (preferenceScreen != null && !(z = preferenceScreen.mAttachedToHierarchy)) {
            boolean z2 = this.mAnimationAllowed;
            if (z) {
                throw new IllegalStateException(
                        "Cannot change the usage of generated IDs while attached to the preference"
                            + " hierarchy");
            }
            preferenceScreen.mShouldUseGeneratedIds = z2;
        }
        super.setPreferenceScreen(preferenceScreen);
        if (preferenceScreen != null) {
            LayoutPreference layoutPreference = this.mHeader;
            if (layoutPreference != null) {
                preferenceScreen.addPreference(layoutPreference);
            }
            SecInsetCategoryPreference secInsetCategoryPreference =
                    new SecInsetCategoryPreference(getPrefContext());
            this.mFooterPreference = secInsetCategoryPreference;
            secInsetCategoryPreference.setKey(KEY_FOOTER_PREFERENCE);
            this.mFooterPreference.setOrder(ORDER_LAST);
            this.mFooterPreference.seslSetSubheaderRoundedBackground(12);
            this.mFooterPreference.mRemovable = false;
            if (preferenceScreen.findPreference(KEY_FOOTER_PREFERENCE) == null) {
                preferenceScreen.addPreference(this.mFooterPreference);
                if (getListView() != null) {
                    getListView().mDrawLastRoundedCorner = false;
                }
            }
            updateFooterPreference$1();
        }
    }

    public void setRelativeLinkViewCount(int i) {
        this.mRelativeLinkViewCount = i;
    }

    public void setResult(int i, Intent intent) {
        if (getActivity() == null) {
            return;
        }
        getActivity().setResult(i, intent);
    }

    public boolean shouldSkipForInitialSUW() {
        return false;
    }

    public void showDialog(int i) {
        if (this.mDialogFragment != null) {
            Log.e(TAG, "Old dialog fragment not null!");
        }
        SettingsDialogFragment newInstance = SettingsDialogFragment.newInstance(this, i);
        this.mDialogFragment = newInstance;
        newInstance.show(getChildFragmentManager(), Integer.toString(i));
    }

    public void showPinnedHeader(boolean z) {
        this.mPinnedHeaderFrameLayout.setVisibility(z ? 0 : 4);
    }

    public void unregisterObserverIfNeeded() {
        if (this.mIsDataSetObserverRegistered) {
            RecyclerView.Adapter adapter = this.mCurrentRootAdapter;
            if (adapter != null) {
                adapter.unregisterAdapterDataObserver(this.mDataSetObserver);
                this.mCurrentRootAdapter = null;
            }
            this.mIsDataSetObserverRegistered = false;
        }
    }

    public void updateEmptyView() {
        AppBarLayout appBarLayout;
        if (this.mEmptyView == null) {
            return;
        }
        if (getPreferenceScreen() != null) {
            View findViewById = getActivity().findViewById(R.id.list_container);
            boolean z = true;
            if ((getPreferenceScreen().getPreferenceCount()
                                            - (getPreferenceScreen()
                                                                    .findPreference(
                                                                            KEY_FOOTER_PREFERENCE)
                                                            != null
                                                    ? 1
                                                    : 0))
                                    - (this.mHeader != null ? 1 : 0)
                            > 0
                    && (findViewById == null || findViewById.getVisibility() == 0)) {
                z = false;
            }
            this.mEmptyView.setVisibility(z ? 0 : 8);
        } else {
            this.mEmptyView.setVisibility(0);
        }
        final Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.action_bar);
        if (this.mEmptyView == null
                || (appBarLayout = this.mAppBarLayout) == null
                || toolbar == null) {
            return;
        }
        appBarLayout.addOnOffsetChangedListener(
                new AppBarLayout
                        .OnOffsetChangedListener() { // from class:
                                                     // com.android.settings.SettingsPreferenceFragment$$ExternalSyntheticLambda4
                    @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
                    public final void onOffsetChanged(AppBarLayout appBarLayout2, int i) {
                        SettingsPreferenceFragment.m649$r8$lambda$HhpdMdZJS9Pr9Dbn5_blaFEvnA(
                                SettingsPreferenceFragment.this, toolbar, appBarLayout2, i);
                    }
                });
    }

    public final void updateFooterPreference$1() {
        if (this.mFooterPreference == null) {
            Log.d(TAG, "mFooterPreference isn't initialized.");
            return;
        }
        if (!WizardManagerHelper.isDeviceProvisioned(this.mContext)
                || !this.mAutoAddFooterPreference) {
            this.mFooterPreference.setVisible(false);
            return;
        }
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (preferenceScreen == null || preferenceScreen.getPreferenceCount() == 0) {
            Log.d(TAG, "root is null or empty.");
            return;
        }
        if (preferenceScreen.findPreference(this.mFooterPreference.getKey()) == null) {
            preferenceScreen.addPreference(this.mFooterPreference);
        }
        for (int i = 0; i < preferenceScreen.getPreferenceCount(); i++) {
            Preference preference = preferenceScreen.getPreference(i);
            if (preference.getOrder() == ORDER_LAST && preference != this.mFooterPreference) {
                preference.setOrder(2147483645);
            }
        }
        boolean isVisible = this.mFooterPreference.isVisible();
        Preference findLastPreference =
                findLastPreference(
                        preferenceScreen,
                        preferenceScreen.getPreferenceCount() - (isVisible ? 1 : 0));
        if (findLastPreference != null) {
            if ((findLastPreference instanceof PreferenceCategory)
                    || (findLastPreference instanceof SecUnclickablePreference)
                    || (findLastPreference instanceof SecButtonPreference)
                    || (findLastPreference instanceof SecActionButtonsPreference)
                    || ((findLastPreference instanceof LayoutPreference)
                            && ((LayoutPreference) findLastPreference).mIsRelativeLinkView)) {
                if (isVisible) {
                    this.mFooterPreference.setVisible(false);
                }
            } else {
                if (isVisible) {
                    return;
                }
                this.mFooterPreference.setVisible(true);
            }
        }
    }

    public <T> T getSystemService(Class<T> cls) {
        return (T) getActivity().getSystemService(cls);
    }

    public boolean removePreference(PreferenceGroup preferenceGroup, String str) {
        int preferenceCount = preferenceGroup.getPreferenceCount();
        for (int i = 0; i < preferenceCount; i++) {
            Preference preference = preferenceGroup.getPreference(i);
            if (TextUtils.equals(preference.getKey(), str)) {
                return preferenceGroup.removePreference(preference);
            }
            if ((preference instanceof PreferenceGroup)
                    && removePreference((PreferenceGroup) preference, str)) {
                return true;
            }
        }
        return false;
    }

    public void setFooterView(View view, boolean z) {
        if (z) {
            LayoutPreference layoutPreference = new LayoutPreference(getPrefContext(), view, z);
            this.mFooter = layoutPreference;
            layoutPreference.seslSetSubheaderRoundedBackground(0);
            SecInsetCategoryPreference secInsetCategoryPreference =
                    new SecInsetCategoryPreference(getContext());
            secInsetCategoryPreference.setOrder(2147483645 - (this.mRelativeLinkViewCount * 2));
            secInsetCategoryPreference.seslSetSubheaderRoundedBackground(12);
            if (getPreferenceScreen() != null) {
                getPreferenceScreen().addPreference(secInsetCategoryPreference);
            }
            if (getListView() != null) {
                getListView().mDrawLastRoundedCorner = false;
            }
        } else {
            this.mFooter = new LayoutPreference(getPrefContext(), view);
        }
        addPreferenceToBottom(this.mFooter);
    }

    public void setResult(int i) {
        if (getActivity() == null) {
            return;
        }
        getActivity().setResult(i);
    }

    public void setHeaderView(View view) {
        LayoutPreference layoutPreference = new LayoutPreference(getPrefContext(), view);
        this.mHeader = layoutPreference;
        layoutPreference.setSelectable(false);
        addPreferenceToTop(this.mHeader);
    }

    public void setPinnedHeaderView(View view) {
        this.mPinnedHeaderFrameLayout.addView(view);
        this.mPinnedHeaderFrameLayout.setVisibility(0);
    }

    public void setHeaderView(View view, boolean z) {
        LayoutPreference layoutPreference = new LayoutPreference(getPrefContext(), view, z);
        this.mHeader = layoutPreference;
        addPreferenceToTop(layoutPreference);
    }

    public void highlightPreferenceIfNeeded() {
        highlightPreferenceIfNeeded(false);
    }

    public void setHeaderView(View view, int i) {
        LayoutPreference layoutPreference = new LayoutPreference(getPrefContext(), view, i);
        this.mHeader = layoutPreference;
        addPreferenceToTop(layoutPreference);
    }

    public void replaceEnterpriseStringSummary(String str, String str2, final int i, final int i2) {
        Preference findPreference = findPreference(str);
        if (findPreference == null) {
            DialogFragment$$ExternalSyntheticOutline0.m(
                    "Could not find enterprise preference ", str, TAG);
        } else {
            findPreference.setSummary(
                    this.mDevicePolicyManager
                            .getResources()
                            .getString(
                                    str2,
                                    new Supplier() { // from class:
                                                     // com.android.settings.SettingsPreferenceFragment$$ExternalSyntheticLambda1
                                        @Override // java.util.function.Supplier
                                        public final Object get() {
                                            SettingsPreferenceFragment settingsPreferenceFragment =
                                                    SettingsPreferenceFragment.this;
                                            return String.format(
                                                    settingsPreferenceFragment.getString(i),
                                                    settingsPreferenceFragment.getString(i2));
                                        }
                                    }));
        }
    }

    public void onDialogShowing() {}

    @Override // com.android.settings.DialogCreatable
    public int getDialogMetricsCategory(int i) {
        return i;
    }
}
