package com.samsung.android.settings.wifi;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.wifitrackerlib.HotspotNetworkEntry;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class InstantHotspotListAdapter extends RecyclerView.Adapter {
    public List mBleAccessPoints;
    public Context mContext;
    public RecyclerView mParentView;
    public static final int[] STATE_SECURED = {R.attr.state_encrypted};
    public static final int[] STATE_NONE = new int[0];

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AutoHotspotApViewHolder extends RecyclerView.ViewHolder
            implements View.OnCreateContextMenuListener {
        public static final /* synthetic */ int $r8$clinit = 0;
        public StateListDrawable mFrictionSld;
        public ImageView mIcon;
        public TextView mSummary;
        public TextView mTitle;
        public View mView;

        @Override // android.view.View.OnCreateContextMenuListener
        public final void onCreateContextMenu(
                ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            Log.i("InstantHotspotListAdapter", "onCreateContextMenu() - Start");
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        List list = this.mBleAccessPoints;
        if (list != null) {
            return list.size();
        }
        Log.i(
                "InstantHotspotListAdapter",
                "getItemCount() - mBleAccessPoints is null so returning 0");
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        int intValue;
        AutoHotspotApViewHolder autoHotspotApViewHolder = (AutoHotspotApViewHolder) viewHolder;
        Log.i("InstantHotspotListAdapter", "onBindViewHolder() - Start");
        if (i < 0 || i >= this.mBleAccessPoints.size()) {
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    i, "onBindViewHolder() - invalid index ", "InstantHotspotListAdapter");
            return;
        }
        final HotspotNetworkEntry hotspotNetworkEntry =
                (HotspotNetworkEntry) this.mBleAccessPoints.get(i);
        Log.d(
                "InstantHotspotListAdapter",
                "onBindViewHolder() - Current BleAp values, getSsid: "
                        + hotspotNetworkEntry.getSsid()
                        + ", getSummary: "
                        + hotspotNetworkEntry.getSummary(true)
                        + ", semGetBssid:"
                        + hotspotNetworkEntry.semGetBssid());
        ImageView imageView = autoHotspotApViewHolder.mIcon;
        StateListDrawable stateListDrawable = autoHotspotApViewHolder.mFrictionSld;
        Log.d("InstantHotspotListAdapter", "updateIcon() - Triggered");
        int upstreamConnectionStrength = hotspotNetworkEntry.getUpstreamConnectionStrength();
        if (stateListDrawable != null) {
            Drawable current = stateListDrawable.getCurrent();
            if (current != null) {
                imageView.setImageDrawable(current);
                current.setLevel(upstreamConnectionStrength);
            } else {
                imageView.setImageDrawable(
                        this.mContext.getResources().getDrawable(R.drawable.ic_wifi_signal_0));
            }
            ArrayList arrayList = new ArrayList();
            if (hotspotNetworkEntry.mHotspotNetworkData != null) {
                arrayList =
                        new ArrayList(
                                hotspotNetworkEntry.mHotspotNetworkData.getHotspotSecurityTypes());
            }
            if (arrayList.size() != 0
                    && (arrayList.size() != 1
                            ? !(arrayList.size() == 2
                                    && arrayList.contains(0)
                                    && arrayList.contains(6))
                            : !((intValue = ((Integer) arrayList.get(0)).intValue()) == 0
                                    || intValue == 6))) {
                stateListDrawable.setState(STATE_SECURED);
            } else {
                stateListDrawable.setState(STATE_NONE);
            }
        }
        int i2 = AutoHotspotApViewHolder.$r8$clinit;
        autoHotspotApViewHolder.mTitle.setText(hotspotNetworkEntry.getTitle());
        autoHotspotApViewHolder.mView.setEnabled(true);
        autoHotspotApViewHolder.mSummary.setText(hotspotNetworkEntry.getSummary(true));
        if (autoHotspotApViewHolder.mView.isEnabled()) {
            autoHotspotApViewHolder.mView.setClickable(true);
            autoHotspotApViewHolder.mTitle.setAlpha(1.0f);
            autoHotspotApViewHolder.mSummary.setAlpha(1.0f);
            autoHotspotApViewHolder.mIcon.setAlpha(1.0f);
        } else {
            Log.d("InstantHotspotListAdapter", "onBindViewHolder(): BleAp item is disabled");
            autoHotspotApViewHolder.mView.setClickable(false);
            autoHotspotApViewHolder.mTitle.setAlpha(0.4f);
            autoHotspotApViewHolder.mSummary.setAlpha(0.4f);
            autoHotspotApViewHolder.mIcon.setAlpha(0.4f);
        }
        autoHotspotApViewHolder.mView.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.wifi.InstantHotspotListAdapter.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        HotspotNetworkEntry hotspotNetworkEntry2 = HotspotNetworkEntry.this;
                        if (hotspotNetworkEntry2 != null) {
                            hotspotNetworkEntry2.connect(null);
                        }
                    }
                });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.i("InstantHotspotListAdapter", "onCreateViewHolder() - Start");
        View inflate =
                LayoutInflater.from(this.mContext)
                        .inflate(
                                R.layout.sec_wifi_list_preference,
                                (ViewGroup) this.mParentView,
                                false);
        AutoHotspotApViewHolder autoHotspotApViewHolder = new AutoHotspotApViewHolder(inflate);
        Log.i("InstantHotspotListAdapter", "AutoHotspotApViewHolder() - initiated");
        autoHotspotApViewHolder.mIcon = (ImageView) inflate.findViewById(R.id.wifi_icon);
        autoHotspotApViewHolder.mTitle = (TextView) inflate.findViewById(R.id.title);
        autoHotspotApViewHolder.mSummary = (TextView) inflate.findViewById(R.id.summary);
        autoHotspotApViewHolder.mView = inflate;
        autoHotspotApViewHolder.mFrictionSld =
                (StateListDrawable) this.mContext.getDrawable(R.drawable.wifi_signal);
        return autoHotspotApViewHolder;
    }
}
