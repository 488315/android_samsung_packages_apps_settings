package com.samsung.android.settings.notification.brief;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.preference.PreferenceViewHolder;
import androidx.preference.SwitchPreferenceCompat;

import com.android.settings.R;

import com.samsung.android.settings.notification.brief.widget.BriefAppInfo;
import com.samsung.android.settings.notification.brief.widget.DrawerDividerView;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BriefPopupAppItemPreference extends SwitchPreferenceCompat {
    public final String TAG;
    public boolean mAllowDrawerDivider;
    public boolean mAllowLineDivider;
    public BriefAppInfo mBriefAppInfo;
    public View mLineDivider;

    public BriefPopupAppItemPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.TAG = "BriefPopupAppItemPreference";
        this.mAllowDrawerDivider = false;
        this.mAllowLineDivider = true;
        setLayoutResource(R.layout.app_item_preference_layout);
    }

    @Override // androidx.preference.SwitchPreferenceCompat, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        Drawable drawable;
        super.onBindViewHolder(preferenceViewHolder);
        Log.i(this.TAG, "onBindViewHolder ");
        ImageView imageView = (ImageView) preferenceViewHolder.findViewById(R.id.app_icon);
        BriefAppInfo briefAppInfo = this.mBriefAppInfo;
        if (briefAppInfo != null
                && (drawable = briefAppInfo.appIcon) != null
                && briefAppInfo.appName != null) {
            imageView.setImageDrawable(drawable);
        }
        this.mLineDivider = preferenceViewHolder.findViewById(R.id.line_divider);
        DrawerDividerView drawerDividerView =
                (DrawerDividerView) preferenceViewHolder.findViewById(R.id.dot_divider);
        preferenceViewHolder.mDividerAllowedAbove = false;
        preferenceViewHolder.mDividerAllowedBelow = false;
        if (this.mAllowDrawerDivider) {
            if (drawerDividerView != null) {
                drawerDividerView.setVisibility(0);
            }
        } else if (drawerDividerView != null) {
            drawerDividerView.setVisibility(8);
        }
        if (this.mAllowLineDivider) {
            View view = this.mLineDivider;
            if (view != null) {
                view.setVisibility(0);
                return;
            }
            return;
        }
        View view2 = this.mLineDivider;
        if (view2 != null) {
            view2.setVisibility(8);
        }
    }

    public BriefPopupAppItemPreference(Context context) {
        super(context);
        this.TAG = "BriefPopupAppItemPreference";
        this.mAllowDrawerDivider = false;
        this.mAllowLineDivider = true;
        setLayoutResource(R.layout.app_item_preference_layout);
    }

    public BriefPopupAppItemPreference(Context context, int i) {
        super(context);
        this.TAG = "BriefPopupAppItemPreference";
        this.mAllowDrawerDivider = false;
        this.mAllowLineDivider = true;
        setLayoutResource(R.layout.app_item_preference_layout_allapps);
    }
}