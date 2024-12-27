package com.samsung.android.settings.analyzestorage.ui.widget.viewholder;

import android.view.View;
import android.view.ViewStub;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SubAppListViewHolder extends RecyclerView.ViewHolder {
    public final ImageView appIcon;
    public ImageView appInfoIcon;
    public final ViewStub appInfoIconStub;
    public final CheckBox checkBox;
    public final View divider;
    public final TextView mainText;
    public final TextView subText;
    public View verticalDivider;

    public SubAppListViewHolder(View view) {
        super(view);
        this.checkBox = (CheckBox) view.findViewById(R.id.checkbox);
        this.appIcon = (ImageView) view.findViewById(R.id.app_icon);
        this.appInfoIcon = (ImageView) view.findViewById(R.id.app_info_icon);
        this.appInfoIconStub = (ViewStub) view.findViewById(R.id.app_info_icon_stub);
        this.mainText = (TextView) view.findViewById(R.id.main_text);
        this.subText = (TextView) view.findViewById(R.id.sub_text);
        this.divider = view.findViewById(R.id.divider);
        this.verticalDivider = view.findViewById(R.id.vertical_divider);
    }
}
