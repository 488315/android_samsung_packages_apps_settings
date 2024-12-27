package com.android.settings.remoteauth.settings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class RemoteAuthSettingsRecyclerViewAdapter extends RecyclerView.Adapter {
    public List uiStates;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ViewHolder extends RecyclerView.ViewHolder {}

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.uiStates.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(this.uiStates.get(i));
        Intrinsics.checkNotNullParameter(null, "authenticatorUiState");
        throw null;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        View inflate =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.remote_auth_settings_authenticator_item, parent, false);
        Intrinsics.checkNotNull(inflate);
        ViewHolder viewHolder = new ViewHolder(inflate);
        View requireViewById = inflate.requireViewById(R.id.authenticator_name_text);
        Intrinsics.checkNotNullExpressionValue(requireViewById, "requireViewById(...)");
        View requireViewById2 = inflate.requireViewById(R.id.remove_icon);
        Intrinsics.checkNotNullExpressionValue(requireViewById2, "requireViewById(...)");
        return viewHolder;
    }
}
