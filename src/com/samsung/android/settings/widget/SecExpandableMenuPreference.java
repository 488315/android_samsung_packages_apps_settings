package com.samsung.android.settings.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecPreference;

import com.android.settings.R;
import com.android.settings.R$styleable;
import com.android.settings.Utils;

import com.airbnb.lottie.LottieAnimationView;
import com.samsung.android.settings.privacy.SecurityDashboardConstants$Status;
import com.samsung.android.util.SemLog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecExpandableMenuPreference extends SecPreference {
    public final int mIconEndId;
    public boolean mIsDividerVisible;
    public boolean mIsScanAnimationDone;
    public ProgressBar mProgressIcon;
    public final boolean mReserveIconSpace;
    public final String mScanIconDrawableName;
    public SecurityDashboardConstants$Status mStatus;
    public LottieAnimationView mStatusIcon;

    public SecExpandableMenuPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mStatus = SecurityDashboardConstants$Status.NONE;
        setLayoutResource(R.layout.preference_expandable_menu);
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(
                        attributeSet, R$styleable.SecExpandableMenuPreference);
        this.mIconEndId = obtainStyledAttributes.getResourceId(0, 0);
        this.mIsDividerVisible = obtainStyledAttributes.getBoolean(1, true);
        this.mScanIconDrawableName = obtainStyledAttributes.getString(3);
        this.mReserveIconSpace = obtainStyledAttributes.getBoolean(2, true);
        obtainStyledAttributes.recycle();
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.mDividerAllowedAbove = false;
        preferenceViewHolder.mDividerAllowedBelow = false;
        this.mStatusIcon =
                (LottieAnimationView) preferenceViewHolder.findViewById(R.id.icon_status);
        this.mProgressIcon =
                (ProgressBar) preferenceViewHolder.findViewById(R.id.menu_item_scan_progress_icon);
        View findViewById = preferenceViewHolder.findViewById(R.id.divider);
        int i = this.mIconEndId;
        if (i != 0) {
            this.mStatusIcon.setImageResource(i);
        }
        if (!this.mReserveIconSpace) {
            preferenceViewHolder.itemView.setPadding(
                    0,
                    0,
                    getContext()
                            .getResources()
                            .getDimensionPixelSize(
                                    R.dimen.sec_security_dashboard_menu_item_padding_end),
                    0);
        }
        findViewById.setVisibility(this.mIsDividerVisible ? 0 : 8);
        Context context = getContext();
        StringBuilder sb = Utils.sBuilder;
        if (TextUtils.isEmpty(
                Settings.System.getString(
                        context.getContentResolver(), "current_sec_active_themepackage"))) {
            findViewById.setBackgroundColor(
                    getContext()
                            .getColor(
                                    R.color.sec_security_dashboard_expandable_menu_divider_color));
            Drawable icon = getIcon();
            if (icon != null) {
                icon.setTint(
                        getContext()
                                .getResources()
                                .getColor(R.color.sec_security_dashboard_menu_icons_color, null));
            }
        }
        setStatus$1();
    }

    public final void seslSetSummaryColor(int i, boolean z) {
        if (TextUtils.isEmpty(this.mScanIconDrawableName) || this.mIsScanAnimationDone || z) {
            seslSetSummaryColor(i);
        }
    }

    public final void setStatus(
            SecurityDashboardConstants$Status securityDashboardConstants$Status) {
        StringBuilder sb = new StringBuilder("setStatus = ");
        sb.append(securityDashboardConstants$Status);
        sb.append(" iconStatus = ");
        sb.append(this.mStatusIcon != null);
        SemLog.d("SecExpandableMenuPreference", sb.toString());
        this.mStatus = securityDashboardConstants$Status;
        setStatus$1();
    }

    public final void setStatus$1() {
        if (this.mStatus == null || this.mStatusIcon == null || !isShown()) {
            return;
        }
        StringBuilder m =
                ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                        "setStatus: key = ", getKey(), " status : ");
        m.append(this.mStatus);
        SemLog.d("SecExpandableMenuPreference", m.toString());
        int ordinal = this.mStatus.ordinal();
        if (ordinal == 0) {
            this.mStatusIcon.setVisibility(8);
            this.mProgressIcon.setVisibility(8);
            return;
        }
        if (ordinal == 1) {
            this.mProgressIcon.setVisibility(0);
            this.mStatusIcon.setVisibility(8);
            this.mIsScanAnimationDone = true;
            return;
        }
        if (ordinal == 2) {
            if (TextUtils.isEmpty(this.mScanIconDrawableName) || this.mIsScanAnimationDone) {
                this.mProgressIcon.setVisibility(8);
                this.mStatusIcon.setVisibility(0);
                this.mStatusIcon.setImageResource(R.drawable.list_status_green);
                this.mStatusIcon.setContentDescription(
                        getContext()
                                .getResources()
                                .getString(
                                        R.string
                                                .security_dashboard_accessibility_status_desc_good));
                return;
            }
            return;
        }
        if (ordinal == 3) {
            if (TextUtils.isEmpty(this.mScanIconDrawableName) || this.mIsScanAnimationDone) {
                this.mProgressIcon.setVisibility(8);
                this.mStatusIcon.setVisibility(0);
                this.mStatusIcon.setImageResource(R.drawable.list_status_orange);
                this.mStatusIcon.setContentDescription(
                        getContext()
                                .getResources()
                                .getString(
                                        R.string
                                                .security_dashboard_accessibility_status_desc_suggestion));
                return;
            }
            return;
        }
        if (ordinal != 4) {
            return;
        }
        if (TextUtils.isEmpty(this.mScanIconDrawableName) || this.mIsScanAnimationDone) {
            this.mProgressIcon.setVisibility(8);
            this.mStatusIcon.setVisibility(0);
            this.mStatusIcon.setImageResource(R.drawable.list_status_red);
            this.mStatusIcon.setContentDescription(
                    getContext()
                            .getResources()
                            .getString(
                                    R.string.security_dashboard_accessibility_status_desc_warning));
        }
    }

    public final void setSummary(CharSequence charSequence, boolean z) {
        if (TextUtils.isEmpty(this.mScanIconDrawableName) || this.mIsScanAnimationDone || z) {
            setSummary(charSequence);
        }
    }
}
