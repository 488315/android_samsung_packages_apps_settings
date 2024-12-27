package com.android.settings.fuelgauge.batterytip;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import android.util.IconDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.Utils;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class HighUsageAdapter extends RecyclerView.Adapter {
    public final Context mContext;
    public final List mHighUsageAppList;
    public final IconDrawableFactory mIconDrawableFactory;
    public final PackageManager mPackageManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView appIcon;
        public TextView appName;
        public View view;
    }

    public HighUsageAdapter(Context context, List list) {
        this.mContext = context;
        this.mHighUsageAppList = list;
        this.mIconDrawableFactory = IconDrawableFactory.newInstance(context);
        this.mPackageManager = context.getPackageManager();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.mHighUsageAppList.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        Drawable defaultActivityIcon;
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        AppInfo appInfo = (AppInfo) this.mHighUsageAppList.get(i);
        ImageView imageView = viewHolder2.appIcon;
        IconDrawableFactory iconDrawableFactory = this.mIconDrawableFactory;
        PackageManager packageManager = this.mPackageManager;
        String str = appInfo.packageName;
        int userId = UserHandle.getUserId(appInfo.uid);
        StringBuilder sb = Utils.sBuilder;
        try {
            defaultActivityIcon =
                    iconDrawableFactory.getBadgedIcon(
                            packageManager.getApplicationInfoAsUser(str, 128, userId), userId);
        } catch (PackageManager.NameNotFoundException unused) {
            defaultActivityIcon = packageManager.getDefaultActivityIcon();
        }
        imageView.setImageDrawable(defaultActivityIcon);
        CharSequence applicationLabel =
                Utils.getApplicationLabel(this.mContext, appInfo.packageName);
        if (applicationLabel == null) {
            applicationLabel = appInfo.packageName;
        }
        viewHolder2.appName.setText(applicationLabel);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate =
                LayoutInflater.from(this.mContext)
                        .inflate(R.layout.app_high_usage_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        viewHolder.view = inflate;
        viewHolder.appIcon = (ImageView) inflate.findViewById(R.id.app_icon);
        viewHolder.appName = (TextView) inflate.findViewById(R.id.app_name);
        return viewHolder;
    }
}
