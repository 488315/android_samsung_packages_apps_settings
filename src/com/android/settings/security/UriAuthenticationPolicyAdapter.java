package com.android.settings.security;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UriAuthenticationPolicyAdapter extends RecyclerView.Adapter {
    public final List mUris;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class UriViewHolder extends RecyclerView.ViewHolder {
        public TextView mUriNameView;
    }

    public UriAuthenticationPolicyAdapter(List list) {
        this.mUris = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.mUris.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ((UriViewHolder) viewHolder)
                .mUriNameView.setText(Uri.decode(((Uri) this.mUris.get(i)).toString()));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View m =
                MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                        viewGroup, R.layout.app_authentication_uri_item, viewGroup, false);
        UriViewHolder uriViewHolder = new UriViewHolder(m);
        uriViewHolder.mUriNameView = (TextView) m.findViewById(R.id.uri_name);
        return uriViewHolder;
    }
}
