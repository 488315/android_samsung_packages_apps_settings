package com.samsung.android.settings.asbase.vibration;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentManagerImpl;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class PagerAdapter extends androidx.viewpager.widget.PagerAdapter {
    public boolean mExecutingFinishUpdate;
    public final FragmentManager mFragmentManager;
    public BackStackRecord mCurTransaction = null;
    public final ArrayList mSavedState = new ArrayList();
    public final ArrayList mFragments = new ArrayList();
    public Fragment mCurrentPrimaryItem = null;
    public final List mFragmentList = new ArrayList();

    public PagerAdapter(FragmentManagerImpl fragmentManagerImpl) {
        this.mFragmentManager = fragmentManagerImpl;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        Fragment fragment = (Fragment) obj;
        BackStackRecord backStackRecord = this.mCurTransaction;
        FragmentManager fragmentManager = this.mFragmentManager;
        if (backStackRecord == null) {
            this.mCurTransaction =
                    DialogFragment$$ExternalSyntheticOutline0.m(fragmentManager, fragmentManager);
        }
        while (this.mSavedState.size() <= i) {
            this.mSavedState.add(null);
        }
        this.mSavedState.set(
                i, fragment.isAdded() ? fragmentManager.saveFragmentInstanceState(fragment) : null);
        this.mFragments.set(i, null);
        this.mCurTransaction.remove(fragment);
        if (fragment.equals(this.mCurrentPrimaryItem)) {
            this.mCurrentPrimaryItem = null;
        }
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final void finishUpdate(ViewGroup viewGroup) {
        BackStackRecord backStackRecord = this.mCurTransaction;
        if (backStackRecord != null) {
            if (!this.mExecutingFinishUpdate) {
                try {
                    this.mExecutingFinishUpdate = true;
                    if (backStackRecord.mAddToBackStack) {
                        throw new IllegalStateException(
                                "This transaction is already being added to the back stack");
                    }
                    backStackRecord.mAllowAddToBackStack = false;
                    backStackRecord.mManager.execSingleAction(backStackRecord, true);
                } finally {
                    this.mExecutingFinishUpdate = false;
                }
            }
            this.mCurTransaction = null;
        }
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final int getCount() {
        return ((ArrayList) this.mFragmentList).size();
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final Object instantiateItem(ViewGroup viewGroup, int i) {
        Fragment.SavedState savedState;
        Fragment fragment;
        if (this.mFragments.size() > i && (fragment = (Fragment) this.mFragments.get(i)) != null) {
            return fragment;
        }
        if (this.mCurTransaction == null) {
            FragmentManager fragmentManager = this.mFragmentManager;
            this.mCurTransaction =
                    DialogFragment$$ExternalSyntheticOutline0.m(fragmentManager, fragmentManager);
        }
        Fragment fragment2 = (Fragment) ((ArrayList) this.mFragmentList).get(i);
        if (this.mSavedState.size() > i
                && (savedState = (Fragment.SavedState) this.mSavedState.get(i)) != null) {
            fragment2.setInitialSavedState(savedState);
        }
        while (this.mFragments.size() <= i) {
            this.mFragments.add(null);
        }
        fragment2.setMenuVisibility(false);
        fragment2.setUserVisibleHint(false);
        this.mFragments.set(i, fragment2);
        this.mCurTransaction.doAddOp(viewGroup.getId(), fragment2, null, 1);
        return fragment2;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final boolean isViewFromObject(View view, Object obj) {
        return ((Fragment) obj).getView() == view;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final void restoreState(Parcelable parcelable, ClassLoader classLoader) {
        if (parcelable != null) {
            Bundle bundle = (Bundle) parcelable;
            bundle.setClassLoader(classLoader);
            Parcelable[] parcelableArray = bundle.getParcelableArray("states");
            this.mSavedState.clear();
            this.mFragments.clear();
            if (parcelableArray != null) {
                for (Parcelable parcelable2 : parcelableArray) {
                    this.mSavedState.add((Fragment.SavedState) parcelable2);
                }
            }
            for (String str : bundle.keySet()) {
                if (str.startsWith("f")) {
                    int parseInt = Integer.parseInt(str.substring(1));
                    Fragment fragment = this.mFragmentManager.getFragment(bundle, str);
                    if (fragment != null) {
                        while (this.mFragments.size() <= parseInt) {
                            this.mFragments.add(null);
                        }
                        fragment.setMenuVisibility(false);
                        this.mFragments.set(parseInt, fragment);
                    } else {
                        Log.w("FragmentStatePagerAdapt", "Bad fragment at key ".concat(str));
                    }
                }
            }
        }
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final Parcelable saveState() {
        Bundle bundle;
        if (this.mSavedState.size() > 0) {
            bundle = new Bundle();
            Fragment.SavedState[] savedStateArr = new Fragment.SavedState[this.mSavedState.size()];
            this.mSavedState.toArray(savedStateArr);
            bundle.putParcelableArray("states", savedStateArr);
        } else {
            bundle = null;
        }
        for (int i = 0; i < this.mFragments.size(); i++) {
            Fragment fragment = (Fragment) this.mFragments.get(i);
            if (fragment != null && fragment.isAdded()) {
                if (bundle == null) {
                    bundle = new Bundle();
                }
                this.mFragmentManager.putFragment(
                        bundle, SeslRoundedCorner$$ExternalSyntheticOutline0.m(i, "f"), fragment);
            }
        }
        return bundle;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final void setPrimaryItem(Object obj) {
        Fragment fragment = (Fragment) obj;
        Fragment fragment2 = this.mCurrentPrimaryItem;
        if (fragment != fragment2) {
            if (fragment2 != null) {
                fragment2.setMenuVisibility(false);
                this.mCurrentPrimaryItem.setUserVisibleHint(false);
            }
            fragment.setMenuVisibility(true);
            fragment.setUserVisibleHint(true);
            this.mCurrentPrimaryItem = fragment;
        }
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final void startUpdate(ViewGroup viewGroup) {
        if (viewGroup.getId() != -1) {
            return;
        }
        throw new IllegalStateException("ViewPager with adapter " + this + " requires a view id");
    }
}
