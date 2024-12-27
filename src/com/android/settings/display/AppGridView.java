package com.android.settings.display;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.UserHandle;
import android.util.AttributeSet;
import android.util.IconDrawableFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

import androidx.core.util.Preconditions;

import com.android.settings.R;
import com.android.settings.R$styleable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AppGridView extends GridView {
    public int mAppCount;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class ActivityEntry implements Comparable<ActivityEntry> {
        public final ResolveInfo info;
        public final String label;
        public final IconDrawableFactory mIconFactory;
        public final int mUserId = UserHandle.myUserId();

        public ActivityEntry(
                ResolveInfo resolveInfo, String str, IconDrawableFactory iconDrawableFactory) {
            this.info = resolveInfo;
            this.label = str;
            this.mIconFactory = iconDrawableFactory;
        }

        @Override // java.lang.Comparable
        public final int compareTo(ActivityEntry activityEntry) {
            return this.label.compareToIgnoreCase(activityEntry.label);
        }

        public final String toString() {
            return this.label;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class AppsAdapter extends ArrayAdapter<ActivityEntry> {
        public final int mAppCount;
        public final int mIconResId;

        public AppsAdapter(Context context, int i) {
            super(context, R.layout.screen_zoom_preview_app_icon, android.R.id.text1);
            this.mIconResId = android.R.id.icon1;
            PackageManager packageManager = context.getPackageManager();
            this.mAppCount = i;
            Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
            intent.addCategory("android.intent.category.LAUNCHER");
            ArrayList arrayList = new ArrayList();
            List<ResolveInfo> queryIntentActivities =
                    packageManager.queryIntentActivities(intent, 0);
            if (i > queryIntentActivities.size()) {
                Log.d("AppGridView", "Visible app icon count does not meet the target count.");
            }
            IconDrawableFactory newInstance = IconDrawableFactory.newInstance(getContext());
            for (ResolveInfo resolveInfo : queryIntentActivities) {
                CharSequence loadLabel = resolveInfo.loadLabel(packageManager);
                if (loadLabel != null) {
                    arrayList.add(
                            new ActivityEntry(resolveInfo, loadLabel.toString(), newInstance));
                }
                if (arrayList.size() >= this.mAppCount) {
                    break;
                }
            }
            Collections.sort(arrayList);
            addAll(arrayList);
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public final long getItemId(int i) {
            return i;
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            View view2 = super.getView(i, view, viewGroup);
            ActivityEntry item = getItem(i);
            ImageView imageView = (ImageView) view2.findViewById(this.mIconResId);
            IconDrawableFactory iconDrawableFactory = item.mIconFactory;
            ActivityInfo activityInfo = item.info.activityInfo;
            imageView.setImageDrawable(
                    iconDrawableFactory.getBadgedIcon(
                            activityInfo, activityInfo.applicationInfo, item.mUserId));
            return view2;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public final boolean hasStableIds() {
            return true;
        }

        @Override // android.widget.BaseAdapter, android.widget.ListAdapter
        public final boolean isEnabled(int i) {
            return false;
        }
    }

    public AppGridView(Context context) {
        super(context);
        this.mAppCount = 6;
        init(context);
    }

    public final void applyAttributeSet(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R$styleable.AppGridView);
        int integer = obtainStyledAttributes.getInteger(0, 6);
        this.mAppCount = integer;
        Preconditions.checkArgument("App count may not be negative or zero", integer >= 1);
        obtainStyledAttributes.recycle();
    }

    public final void init(Context context) {
        setAdapter((ListAdapter) new AppsAdapter(context, this.mAppCount));
    }

    public AppGridView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAppCount = 6;
        applyAttributeSet(context, attributeSet);
        init(context);
    }

    public AppGridView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mAppCount = 6;
        applyAttributeSet(context, attributeSet);
        init(context);
    }

    public AppGridView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mAppCount = 6;
        applyAttributeSet(context, attributeSet);
        init(context);
    }
}
