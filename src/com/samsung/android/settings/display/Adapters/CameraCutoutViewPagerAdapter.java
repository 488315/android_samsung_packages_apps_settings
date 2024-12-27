package com.samsung.android.settings.display.Adapters;

import android.content.Context;
import android.text.TextUtils;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class CameraCutoutViewPagerAdapter extends FragmentStateAdapter {
    public static final int[] LABEL;
    public static final int mTabCount;
    public Fragment[] mChildFragments;
    public Context mContext;

    static {
        int[] iArr = {R.string.work_sound_personal_for_samsung, R.string.work_title};
        LABEL = iArr;
        mTabCount = iArr.length;
    }

    public static int convertPosition(int i) {
        if (TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1) {
            return (mTabCount - 1) - i;
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, ApnSettings.MVNO_NONE, "CameraCutoutViewPagerAdapter");
        return i;
    }

    @Override // androidx.viewpager2.adapter.FragmentStateAdapter
    public final Fragment createFragment(int i) {
        return this.mChildFragments[i];
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.mChildFragments.length;
    }
}
