package com.android.settings.wifi.calling;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DisclaimerItemListAdapter extends RecyclerView.Adapter {
    public List mDisclaimerItemList;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DisclaimerItemViewHolder extends RecyclerView.ViewHolder {

        @VisibleForTesting static final int ID_DISCLAIMER_ITEM_DESCRIPTION = 2131363118;

        @VisibleForTesting static final int ID_DISCLAIMER_ITEM_TITLE = 2131363121;
        public final TextView descriptionView;
        public final TextView titleView;

        public DisclaimerItemViewHolder(View view) {
            super(view);
            this.titleView = (TextView) view.findViewById(ID_DISCLAIMER_ITEM_TITLE);
            this.descriptionView = (TextView) view.findViewById(ID_DISCLAIMER_ITEM_DESCRIPTION);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.mDisclaimerItemList.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        DisclaimerItemViewHolder disclaimerItemViewHolder = (DisclaimerItemViewHolder) viewHolder;
        disclaimerItemViewHolder.titleView.setText(
                ((DisclaimerItem) this.mDisclaimerItemList.get(i)).getTitleId());
        disclaimerItemViewHolder.descriptionView.setText(
                ((DisclaimerItem) this.mDisclaimerItemList.get(i)).getMessageId());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new DisclaimerItemViewHolder(
                ((LayoutInflater) viewGroup.getContext().getSystemService("layout_inflater"))
                        .inflate(R.layout.wfc_simple_disclaimer_item, (ViewGroup) null, false));
    }
}
