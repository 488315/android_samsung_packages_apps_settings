package com.samsung.android.settings.bluetooth.lebroadcast;

import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

import com.samsung.android.settings.logging.SALogging;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecBluetoothLeBroadcastAssistantListAdapter extends RecyclerView.Adapter {
    public SecBluetoothLeBroadcastAssistantSettings.AnonymousClass8 mItemClickListener;
    public ArrayList mSourceItems;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIconView;
        public TextView mSummaryView;
        public TextView mTitleView;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.mSourceItems.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        SecBluetoothLeBroadcastAssistantSourceItem secBluetoothLeBroadcastAssistantSourceItem =
                (SecBluetoothLeBroadcastAssistantSourceItem) this.mSourceItems.get(i);
        if (secBluetoothLeBroadcastAssistantSourceItem.mIsEncrypted) {
            viewHolder2.mIconView.setImageResource(R.drawable.sec_bluetooth_broadcast_lock);
        } else {
            viewHolder2.mIconView.setImageResource(R.drawable.sec_bluetooth_broadcast_stream);
        }
        viewHolder2.mTitleView.setText(secBluetoothLeBroadcastAssistantSourceItem.mTitle);
        if (secBluetoothLeBroadcastAssistantSourceItem.mSummary == null) {
            viewHolder2.mSummaryView.setVisibility(8);
        } else {
            viewHolder2.mSummaryView.setVisibility(0);
            viewHolder2.mSummaryView.setText(secBluetoothLeBroadcastAssistantSourceItem.mSummary);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View m =
                MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                        viewGroup,
                        R.layout.sec_bluetooth_broadcast_assistant_source_item,
                        viewGroup,
                        false);
        final ViewHolder viewHolder = new ViewHolder(m);
        viewHolder.mIconView = (ImageView) m.findViewById(R.id.icon);
        viewHolder.mTitleView = (TextView) m.findViewById(R.id.source_title);
        viewHolder.mSummaryView = (TextView) m.findViewById(R.id.source_summary);
        m.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastAssistantListAdapter.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                        if (bindingAdapterPosition != -1) {
                            SecBluetoothLeBroadcastAssistantListAdapter
                                    secBluetoothLeBroadcastAssistantListAdapter =
                                            SecBluetoothLeBroadcastAssistantListAdapter.this;
                            SecBluetoothLeBroadcastAssistantSettings.AnonymousClass8
                                    anonymousClass8 =
                                            secBluetoothLeBroadcastAssistantListAdapter
                                                    .mItemClickListener;
                            SecBluetoothLeBroadcastAssistantSourceItem
                                    secBluetoothLeBroadcastAssistantSourceItem =
                                            (SecBluetoothLeBroadcastAssistantSourceItem)
                                                    secBluetoothLeBroadcastAssistantListAdapter
                                                            .mSourceItems.get(
                                                            bindingAdapterPosition);
                            SecBluetoothLeBroadcastAssistantSettings
                                    secBluetoothLeBroadcastAssistantSettings =
                                            SecBluetoothLeBroadcastAssistantSettings.this;
                            secBluetoothLeBroadcastAssistantSettings.getClass();
                            BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata =
                                    secBluetoothLeBroadcastAssistantSourceItem.mMetadata;
                            if (bluetoothLeBroadcastMetadata == null) {
                                Log.d(
                                        "SecBluetoothLeBroadcastAssistantSettings",
                                        "processSourceItemClick : metadata is null");
                                return;
                            }
                            if (secBluetoothLeBroadcastAssistantSettings.mHandler.hasMessages(2)) {
                                Log.i(
                                        "SecBluetoothLeBroadcastAssistantSettings",
                                        "processSourceItemClick : previous operation is ongoing");
                                return;
                            }
                            StringBuilder sb = new StringBuilder("processSourceItemClick : ");
                            sb.append(secBluetoothLeBroadcastAssistantSourceItem.mBroadcastId);
                            sb.append(", encrypted ");
                            ActionBarContextView$$ExternalSyntheticOutline0.m(
                                    sb,
                                    secBluetoothLeBroadcastAssistantSourceItem.mIsEncrypted,
                                    "SecBluetoothLeBroadcastAssistantSettings");
                            SALogging.insertSALog(
                                    secBluetoothLeBroadcastAssistantSettings.mScreenId,
                                    secBluetoothLeBroadcastAssistantSettings
                                            .getResources()
                                            .getString(
                                                    R.string
                                                            .event_bluetooth_bass_broadcast_source_item));
                            if (!((ConcurrentHashMap)
                                            secBluetoothLeBroadcastAssistantSettings.mActiveSinkMap)
                                    .isEmpty()) {
                                secBluetoothLeBroadcastAssistantSettings.mPendingSourceItem =
                                        secBluetoothLeBroadcastAssistantSourceItem;
                            }
                            byte[] broadcastCode = bluetoothLeBroadcastMetadata.getBroadcastCode();
                            if (secBluetoothLeBroadcastAssistantSourceItem.mIsEncrypted
                                    && broadcastCode == null) {
                                secBluetoothLeBroadcastAssistantSettings.launchBroadcastCodeDialog(
                                        secBluetoothLeBroadcastAssistantSourceItem, false);
                            } else {
                                secBluetoothLeBroadcastAssistantSettings.addSource(
                                        secBluetoothLeBroadcastAssistantSourceItem);
                            }
                        }
                    }
                });
        return viewHolder;
    }
}
