package com.android.settingslib.widget;

import android.os.UserHandle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ProfileViewPagerAdapter extends FragmentStateAdapter {
    public final ProfileSelectFragment mParentFragments;

    public ProfileViewPagerAdapter(ProfileSelectFragment profileSelectFragment) {
        super(profileSelectFragment);
        this.mParentFragments = profileSelectFragment;
    }

    @Override // androidx.viewpager2.adapter.FragmentStateAdapter
    public final Fragment createFragment(int i) {
        ProfileSelectFragment profileSelectFragment = this.mParentFragments;
        if (profileSelectFragment.mUsingUserIds) {
            i = ((UserHandle) profileSelectFragment.mProfileTabsByUsers.keyAt(i)).getIdentifier();
        }
        return profileSelectFragment.createFragment(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        ProfileSelectFragment profileSelectFragment = this.mParentFragments;
        if (profileSelectFragment.mUsingUserIds) {
            return profileSelectFragment.mProfileTabsByUsers.size();
        }
        return 2;
    }
}
