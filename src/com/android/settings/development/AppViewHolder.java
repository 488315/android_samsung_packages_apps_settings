package com.android.settings.development;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppViewHolder {
    public ImageView appIcon;
    public TextView appName;
    public TextView disabled;
    public View rootView;
    public TextView summary;
    public View widget;

    public static AppViewHolder createOrRecycle(LayoutInflater layoutInflater, View view) {
        if (view != null) {
            return (AppViewHolder) view.getTag();
        }
        View inflate = layoutInflater.inflate(R.layout.preference_app, (ViewGroup) null);
        AppViewHolder appViewHolder = new AppViewHolder();
        appViewHolder.rootView = inflate;
        appViewHolder.appName = (TextView) inflate.findViewById(android.R.id.title);
        appViewHolder.appIcon = (ImageView) inflate.findViewById(android.R.id.icon);
        appViewHolder.summary = (TextView) inflate.findViewById(android.R.id.summary);
        appViewHolder.disabled = (TextView) inflate.findViewById(R.id.appendix);
        appViewHolder.widget = inflate.findViewById(android.R.id.widget_frame);
        inflate.setTag(appViewHolder);
        return appViewHolder;
    }
}
