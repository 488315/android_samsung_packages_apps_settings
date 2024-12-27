package com.samsung.android.settings.display;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.settings.R;
import com.android.settings.Utils;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ScreenModePreviewPagerAdapter extends PagerAdapter {
    public final Context context;
    public final ArrayList mDescriptions;
    public final LayoutInflater mInflater;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Description {
        public int animation = 0;
    }

    public ScreenModePreviewPagerAdapter(Context context) {
        ArrayList arrayList = new ArrayList();
        this.mDescriptions = arrayList;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        Description description = new Description();
        Description description2 = new Description();
        Description description3 = new Description();
        description.animation = R.drawable.sec_st_screen_mode_previewmode_img_01;
        description2.animation = R.drawable.sec_st_screen_mode_previewmode_img_02;
        description3.animation = R.drawable.sec_st_screen_mode_previewmode_img_03;
        arrayList.add(description);
        arrayList.add(description2);
        arrayList.add(description3);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final void destroyItem(int i, View view, Object obj) {
        ((ViewPager) view).removeView((View) obj);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final int getCount() {
        return this.mDescriptions.size();
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final Object instantiateItem(ViewGroup viewGroup, int i) {
        View inflate =
                this.mInflater.inflate(R.layout.sec_screen_mode_pager_adapter, (ViewGroup) null);
        inflate.setTag(Integer.valueOf(i));
        ImageView imageView = (ImageView) inflate.findViewById(R.id.preview_image);
        if (Utils.isRTL(this.context)) {
            i = (this.mDescriptions.size() - 1) - i;
        }
        if (((Description) this.mDescriptions.get(i)).animation == 0) {
            imageView.setVisibility(8);
        } else {
            imageView.setVisibility(0);
            imageView.setImageResource(((Description) this.mDescriptions.get(i)).animation);
        }
        ((ViewPager) viewGroup).addView(inflate, 0);
        return inflate;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }
}
