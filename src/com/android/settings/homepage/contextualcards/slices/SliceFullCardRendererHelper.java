package com.android.settings.homepage.contextualcards.slices;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.slice.widget.SliceView;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SliceFullCardRendererHelper {
    public final Context mContext;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SliceViewHolder extends RecyclerView.ViewHolder {
        public final SliceView sliceView;

        public SliceViewHolder(View view) {
            super(view);
            this.sliceView = (SliceView) view.findViewById(R.id.slice_view);
        }
    }

    public SliceFullCardRendererHelper(Context context) {
        this.mContext = context;
    }
}
