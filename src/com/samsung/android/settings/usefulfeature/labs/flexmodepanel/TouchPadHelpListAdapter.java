package com.samsung.android.settings.usefulfeature.labs.flexmodepanel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class TouchPadHelpListAdapter extends RecyclerView.Adapter {
    public final TouchPadGestureHelp mClickListener;
    public final ArrayList mTouchPadHelpModelList;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mDetail;
        public View mDivider;
        public ImageView mIcon;
        public TextView mTitle;
    }

    public TouchPadHelpListAdapter(ArrayList arrayList, TouchPadGestureHelp touchPadGestureHelp) {
        this.mTouchPadHelpModelList = arrayList;
        this.mClickListener = touchPadGestureHelp;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.mTouchPadHelpModelList.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        final TouchPadHelpModel touchPadHelpModel =
                (TouchPadHelpModel) this.mTouchPadHelpModelList.get(i);
        viewHolder2.mTitle.setText(touchPadHelpModel.mTitle);
        viewHolder2.mDetail.setText(touchPadHelpModel.mDetail);
        viewHolder2.mIcon.setImageResource(touchPadHelpModel.mIcon);
        viewHolder2.itemView.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.usefulfeature.labs.flexmodepanel.TouchPadHelpListAdapter$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        TouchPadHelpListAdapter touchPadHelpListAdapter =
                                TouchPadHelpListAdapter.this;
                        TouchPadHelpModel touchPadHelpModel2 = touchPadHelpModel;
                        TouchPadGestureHelp touchPadGestureHelp =
                                touchPadHelpListAdapter.mClickListener;
                        touchPadGestureHelp.getClass();
                        Intent intent =
                                new Intent(
                                        touchPadGestureHelp.getActivity(),
                                        (Class<?>) TapActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("page_position", touchPadHelpModel2.mPosition);
                        intent.putExtras(bundle);
                        touchPadGestureHelp.getActivity().startActivity(intent);
                    }
                });
        if (i == this.mTouchPadHelpModelList.size() - 1) {
            viewHolder2.mDivider.setVisibility(4);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View m =
                MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                        viewGroup, R.layout.sec_touchpad_tips_items, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(m);
        viewHolder.mTitle = (TextView) m.findViewById(R.id.title);
        viewHolder.mDetail = (TextView) m.findViewById(R.id.detail);
        viewHolder.mIcon = (ImageView) m.findViewById(R.id.touchpad_gesture_list_icon);
        viewHolder.mDivider = m.findViewById(R.id.divider_view);
        return viewHolder;
    }
}
