package com.android.settings.security;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class CredentialManagementAppAdapter extends RecyclerView.Adapter {
    public final Map mAppUriAuthentication;
    public final Context mContext;
    public final String mCredentialManagerPackage;
    public final boolean mIncludeExpander;
    public final boolean mIncludeHeader;
    public final boolean mIsLayoutRtl;
    public final PackageManager mPackageManager;
    public final List mSortedAppNames;
    public final RecyclerView.RecycledViewPool mViewPool;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AppAuthenticationViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mAppIconView;
        public final TextView mAppNameView;
        public final RecyclerView mChildRecyclerView;
        public final List mExpandedApps;
        public final ImageView mExpanderIconView;
        public final TextView mNumberOfUrisView;

        public AppAuthenticationViewHolder(View view) {
            super(view);
            this.mAppIconView = (ImageView) view.findViewById(R.id.app_icon);
            this.mAppNameView = (TextView) view.findViewById(R.id.app_name);
            this.mNumberOfUrisView = (TextView) view.findViewById(R.id.number_of_uris);
            ImageView imageView = (ImageView) view.findViewById(R.id.expand);
            this.mExpanderIconView = imageView;
            this.mChildRecyclerView = (RecyclerView) view.findViewById(R.id.uris);
            this.mExpandedApps = new ArrayList();
            if (CredentialManagementAppAdapter.this.mIsLayoutRtl) {
                RelativeLayout.LayoutParams layoutParams =
                        (RelativeLayout.LayoutParams)
                                ((RelativeLayout) view.findViewById(R.id.app_details))
                                        .getLayoutParams();
                layoutParams.addRule(0, R.id.app_icon);
                layoutParams.addRule(1, R.id.expand);
                view.setLayoutParams(layoutParams);
            }
            imageView.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.android.settings.security.CredentialManagementAppAdapter$AppAuthenticationViewHolder$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            CredentialManagementAppAdapter.AppAuthenticationViewHolder
                                    appAuthenticationViewHolder =
                                            CredentialManagementAppAdapter
                                                    .AppAuthenticationViewHolder.this;
                            String str =
                                    (String)
                                            ((ArrayList)
                                                            CredentialManagementAppAdapter.this
                                                                    .mSortedAppNames)
                                                    .get(
                                                            appAuthenticationViewHolder
                                                                    .getBindingAdapterPosition());
                            if (((ArrayList) appAuthenticationViewHolder.mExpandedApps)
                                    .contains(str)) {
                                ((ArrayList) appAuthenticationViewHolder.mExpandedApps).remove(str);
                            } else {
                                ((ArrayList) appAuthenticationViewHolder.mExpandedApps).add(str);
                            }
                            appAuthenticationViewHolder.bindPolicyView(str);
                        }
                    });
        }

        public final void bindChildView(Map map) {
            RecyclerView recyclerView = this.mChildRecyclerView;
            recyclerView.getContext();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(1);
            linearLayoutManager.mInitialPrefetchItemCount = map.size();
            UriAuthenticationPolicyAdapter uriAuthenticationPolicyAdapter =
                    new UriAuthenticationPolicyAdapter(new ArrayList(map.keySet()));
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setVisibility(0);
            recyclerView.setAdapter(uriAuthenticationPolicyAdapter);
            recyclerView.setRecycledViewPool(CredentialManagementAppAdapter.this.mViewPool);
        }

        public final void bindPolicyView(String str) {
            CredentialManagementAppAdapter credentialManagementAppAdapter =
                    CredentialManagementAppAdapter.this;
            if (!credentialManagementAppAdapter.mIncludeExpander) {
                this.mNumberOfUrisView.setVisibility(8);
                this.mExpanderIconView.setVisibility(8);
                bindChildView((Map) credentialManagementAppAdapter.mAppUriAuthentication.get(str));
                return;
            }
            this.mExpanderIconView.setVisibility(0);
            if (((ArrayList) this.mExpandedApps).contains(str)) {
                this.mNumberOfUrisView.setVisibility(8);
                this.mExpanderIconView.setImageResource(R.drawable.ic_expand_less);
                bindChildView((Map) credentialManagementAppAdapter.mAppUriAuthentication.get(str));
            } else {
                this.mChildRecyclerView.setVisibility(8);
                this.mNumberOfUrisView.setVisibility(0);
                TextView textView = this.mNumberOfUrisView;
                int size =
                        ((Map) credentialManagementAppAdapter.mAppUriAuthentication.get(str))
                                .size();
                textView.setText(
                        credentialManagementAppAdapter
                                .mContext
                                .getResources()
                                .getQuantityString(
                                        R.plurals.number_of_websites, size, Integer.valueOf(size)));
                this.mExpanderIconView.setImageResource(android.R.drawable.ic_jog_dial_decline);
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class HeaderViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mAppIconView;
        public final TextView mTitleView;

        public HeaderViewHolder(View view) {
            super(view);
            this.mAppIconView = (ImageView) view.findViewById(R.id.credential_management_app_icon);
            this.mTitleView = (TextView) view.findViewById(R.id.credential_management_app_title);
        }
    }

    public CredentialManagementAppAdapter(
            Context context, String str, Map map, boolean z, boolean z2) {
        this.mContext = context;
        this.mCredentialManagerPackage = str;
        this.mPackageManager = context.getPackageManager();
        this.mAppUriAuthentication = map;
        ArrayList arrayList = new ArrayList(map.keySet());
        arrayList.sort(
                new Comparator() { // from class:
                                   // com.android.settings.security.CredentialManagementAppAdapter$$ExternalSyntheticLambda0
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        boolean z3;
                        CredentialManagementAppAdapter credentialManagementAppAdapter =
                                CredentialManagementAppAdapter.this;
                        String str2 = (String) obj;
                        String str3 = (String) obj2;
                        credentialManagementAppAdapter.getClass();
                        boolean z4 = false;
                        try {
                            credentialManagementAppAdapter.mPackageManager.getPackageInfo(str2, 0);
                            z3 = true;
                        } catch (PackageManager.NameNotFoundException unused) {
                            z3 = false;
                        }
                        try {
                            credentialManagementAppAdapter.mPackageManager.getPackageInfo(str3, 0);
                            z4 = true;
                        } catch (PackageManager.NameNotFoundException unused2) {
                        }
                        return z3 == z4 ? str2.compareTo(str3) : z3 ? -1 : 1;
                    }
                });
        this.mSortedAppNames = arrayList;
        this.mViewPool = new RecyclerView.RecycledViewPool();
        this.mIncludeHeader = z;
        this.mIncludeExpander = z2;
        this.mIsLayoutRtl = context.getResources().getConfiguration().getLayoutDirection() == 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        boolean z = this.mIncludeHeader;
        int size = this.mAppUriAuthentication.size();
        return z ? size + 1 : size;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        return (this.mIncludeHeader && i == 0) ? 1 : 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) viewHolder;
            CredentialManagementAppAdapter credentialManagementAppAdapter =
                    CredentialManagementAppAdapter.this;
            try {
                ApplicationInfo applicationInfo =
                        credentialManagementAppAdapter.mPackageManager.getApplicationInfo(
                                credentialManagementAppAdapter.mCredentialManagerPackage, 0);
                headerViewHolder.mAppIconView.setImageDrawable(
                        credentialManagementAppAdapter.mPackageManager.getApplicationIcon(
                                applicationInfo));
                headerViewHolder.mTitleView.setText(
                        TextUtils.expandTemplate(
                                credentialManagementAppAdapter.mContext.getText(
                                        R.string.request_manage_credentials_title),
                                applicationInfo.loadLabel(
                                        credentialManagementAppAdapter.mPackageManager)));
                return;
            } catch (PackageManager.NameNotFoundException unused) {
                headerViewHolder.mAppIconView.setImageDrawable(null);
                headerViewHolder.mTitleView.setText(
                        TextUtils.expandTemplate(
                                credentialManagementAppAdapter.mContext.getText(
                                        R.string.request_manage_credentials_title),
                                credentialManagementAppAdapter.mCredentialManagerPackage));
                return;
            }
        }
        if (viewHolder instanceof AppAuthenticationViewHolder) {
            if (this.mIncludeHeader) {
                i--;
            }
            AppAuthenticationViewHolder appAuthenticationViewHolder =
                    (AppAuthenticationViewHolder) viewHolder;
            CredentialManagementAppAdapter credentialManagementAppAdapter2 =
                    CredentialManagementAppAdapter.this;
            String str =
                    (String) ((ArrayList) credentialManagementAppAdapter2.mSortedAppNames).get(i);
            try {
                ApplicationInfo applicationInfo2 =
                        credentialManagementAppAdapter2.mPackageManager.getApplicationInfo(str, 0);
                appAuthenticationViewHolder.mAppIconView.setImageDrawable(
                        credentialManagementAppAdapter2.mPackageManager.getApplicationIcon(
                                applicationInfo2));
                appAuthenticationViewHolder.mAppNameView.setText(
                        String.valueOf(
                                applicationInfo2.loadLabel(
                                        credentialManagementAppAdapter2.mPackageManager)));
            } catch (PackageManager.NameNotFoundException unused2) {
                appAuthenticationViewHolder.mAppIconView.setImageDrawable(null);
                appAuthenticationViewHolder.mAppNameView.setText(str);
            }
            appAuthenticationViewHolder.bindPolicyView(str);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i != 1) {
            return new AppAuthenticationViewHolder(
                    MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                            viewGroup, R.layout.app_authentication_item, viewGroup, false));
        }
        View inflate =
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.request_manage_credentials_header, viewGroup, false);
        inflate.setEnabled(false);
        return new HeaderViewHolder(inflate);
    }
}
