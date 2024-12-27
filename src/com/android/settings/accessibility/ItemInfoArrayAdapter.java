package com.android.settings.accessibility;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ItemInfoArrayAdapter extends ArrayAdapter {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class ItemInfo {
        public final int mDrawableId;
        public final CharSequence mSummary;
        public final CharSequence mTitle;

        public ItemInfo(CharSequence charSequence, CharSequence charSequence2, int i) {
            this.mTitle = charSequence;
            this.mSummary = charSequence2;
            this.mDrawableId = i;
        }
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public final View getView(int i, View view, ViewGroup viewGroup) {
        View view2 = super.getView(i, view, viewGroup);
        ItemInfo itemInfo = (ItemInfo) getItem(i);
        ((TextView) view2.findViewById(R.id.title)).setText(itemInfo.mTitle);
        TextView textView = (TextView) view2.findViewById(R.id.summary);
        if (TextUtils.isEmpty(itemInfo.mSummary)) {
            textView.setVisibility(8);
        } else {
            textView.setVisibility(0);
            textView.setText(itemInfo.mSummary);
        }
        ImageView imageView = (ImageView) view2.findViewById(R.id.image);
        imageView.setImageResource(itemInfo.mDrawableId);
        if (getContext().getResources().getConfiguration().getLayoutDirection() == 0) {
            imageView.setScaleType(ImageView.ScaleType.FIT_START);
        } else {
            imageView.setScaleType(ImageView.ScaleType.FIT_END);
        }
        return view2;
    }
}
