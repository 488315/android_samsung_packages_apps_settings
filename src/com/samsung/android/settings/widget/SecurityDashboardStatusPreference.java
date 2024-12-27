package com.samsung.android.settings.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecPreferenceScreen;

import com.android.settings.R;
import com.android.settings.R$styleable;

import com.airbnb.lottie.LottieAnimationView;
import com.github.mikephil.charting.utils.Utils;
import com.samsung.android.settings.privacy.SecurityDashboardConstants$Status;
import com.samsung.android.util.SemLog;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecurityDashboardStatusPreference extends SecPreferenceScreen {
    public int mCriticalCount;
    public final Map mCurrentSaLogStatusMap;
    public final Map mExpandableMenusStatus;
    public final Handler mHandler;
    public boolean mIsScanning;
    public final AnonymousClass1 mOnGlobalLayoutListener;
    public SecurityDashboardConstants$Status mOverallStatus;
    public final Map mOverallStatusMap;
    public LottieAnimationView mStatusIconBGView;
    public LottieAnimationView mStatusIconView;
    public TextView mStatusSubTextView;
    public TextView mStatusTextView;
    public View mStatusViewLayout;
    public int mWarningCount;

    /* JADX WARN: Type inference failed for: r0v4, types: [com.samsung.android.settings.widget.SecurityDashboardStatusPreference$1] */
    public SecurityDashboardStatusPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOverallStatus = SecurityDashboardConstants$Status.NONE;
        this.mExpandableMenusStatus = new ConcurrentHashMap();
        this.mOverallStatusMap = new ConcurrentHashMap();
        this.mCurrentSaLogStatusMap = new ConcurrentHashMap();
        this.mOnGlobalLayoutListener =
                new ViewTreeObserver
                        .OnGlobalLayoutListener() { // from class:
                                                    // com.samsung.android.settings.widget.SecurityDashboardStatusPreference.1
                    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                    public final void onGlobalLayout() {
                        SecurityDashboardStatusPreference.this
                                .mStatusSubTextView
                                .getViewTreeObserver()
                                .removeOnGlobalLayoutListener(
                                        SecurityDashboardStatusPreference.this
                                                .mOnGlobalLayoutListener);
                        SecurityDashboardStatusPreference securityDashboardStatusPreference =
                                SecurityDashboardStatusPreference.this;
                        securityDashboardStatusPreference.getClass();
                        LinearLayout.LayoutParams layoutParams =
                                new LinearLayout.LayoutParams(-1, -2);
                        layoutParams.gravity = 1;
                        if (securityDashboardStatusPreference.mStatusSubTextView.getLineCount()
                                > 1) {
                            layoutParams.setMargins(
                                    0,
                                    0,
                                    0,
                                    (int)
                                            Utils.convertDpToPixel(
                                                    securityDashboardStatusPreference
                                                            .getContext()
                                                            .getResources()
                                                            .getDimension(
                                                                    R.dimen
                                                                            .sec_security_dashboard_status_vertical_view_bottom_margin_two_line)));
                        } else {
                            layoutParams.setMargins(
                                    0,
                                    0,
                                    0,
                                    (int)
                                            Utils.convertDpToPixel(
                                                    securityDashboardStatusPreference
                                                            .getContext()
                                                            .getResources()
                                                            .getDimension(
                                                                    R.dimen
                                                                            .sec_security_dashboard_status_vertical_view_bottom_margin_one_line)));
                        }
                        securityDashboardStatusPreference.mStatusViewLayout.setLayoutParams(
                                layoutParams);
                    }
                };
        context.obtainStyledAttributes(attributeSet, R$styleable.Preference).recycle();
        setLayoutResource(R.layout.security_dashboard_status_view);
        this.mHandler = new Handler(Looper.getMainLooper());
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        this.mStatusViewLayout =
                preferenceViewHolder.findViewById(
                        R.id.security_dashboard_status_slide_animation_layout_vertical);
        this.mStatusIconView =
                (LottieAnimationView) preferenceViewHolder.findViewById(R.id.status_icon);
        this.mStatusIconBGView =
                (LottieAnimationView) preferenceViewHolder.findViewById(R.id.status_icon_bg);
        this.mStatusTextView = (TextView) preferenceViewHolder.findViewById(R.id.status_text);
        this.mStatusSubTextView =
                (TextView) preferenceViewHolder.findViewById(R.id.status_sub_text);
        setStatusView(this.mOverallStatus, this.mCriticalCount, this.mWarningCount, false);
    }

    public final void setMenuStatus(
            String str,
            String str2,
            SecurityDashboardConstants$Status securityDashboardConstants$Status) {
        ((ConcurrentHashMap) this.mExpandableMenusStatus)
                .put(str, securityDashboardConstants$Status);
        ((ConcurrentHashMap) this.mOverallStatusMap).put(str2, securityDashboardConstants$Status);
    }

    public final void setScanning(boolean z, boolean z2) {
        if (z) {
            setStatus(SecurityDashboardConstants$Status.SCANNING, 0, 0, false);
            this.mIsScanning = true;
        } else {
            this.mIsScanning = false;
            setupStatus(z2);
        }
    }

    public final void setStatus(
            SecurityDashboardConstants$Status securityDashboardConstants$Status,
            int i,
            int i2,
            boolean z) {
        StringBuilder sb = new StringBuilder("status = ");
        sb.append(securityDashboardConstants$Status);
        sb.append(", criticalCount = ");
        sb.append(i);
        sb.append(", warningCount = ");
        sb.append(i2);
        sb.append(", imgView = ");
        sb.append(this.mStatusIconView != null);
        SemLog.d("SecurityDashboardStatusPreference", sb.toString());
        this.mOverallStatus = securityDashboardConstants$Status;
        this.mCriticalCount = i;
        this.mWarningCount = i2;
        setStatusView(securityDashboardConstants$Status, i, i2, z);
    }

    public final void setStatusView(
            SecurityDashboardConstants$Status securityDashboardConstants$Status,
            int i,
            int i2,
            boolean z) {
        TextView textView = this.mStatusSubTextView;
        if (textView != null) {
            textView.getViewTreeObserver().addOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
            if (securityDashboardConstants$Status == SecurityDashboardConstants$Status.OK) {
                this.mStatusSubTextView.setText(R.string.security_dashboard_no_security_issues);
            } else if (securityDashboardConstants$Status
                    == SecurityDashboardConstants$Status.WARNING) {
                this.mStatusSubTextView.setText(
                        R.string.security_dashboard_suggestions_can_keep_your_device_secure);
            } else if (securityDashboardConstants$Status
                    == SecurityDashboardConstants$Status.CRITICAL) {
                this.mStatusSubTextView.setText(
                        R.string.security_dashboard_action_needed_to_keep_device_safe);
            }
        }
        if ((!this.mIsScanning
                        || securityDashboardConstants$Status
                                == SecurityDashboardConstants$Status.SCANNING)
                && this.mStatusIconView != null
                && isShown()) {
            int ordinal = securityDashboardConstants$Status.ordinal();
            if (ordinal == 1) {
                this.mStatusIconView.setVisibility(0);
                updateScanCategoryIcon(
                        getContext()
                                .getResources()
                                .getDrawable(R.drawable.ic_security_status_lock, null));
                this.mStatusIconBGView.setImageTintList(
                        ColorStateList.valueOf(
                                getContext().getColor(R.color.security_dashboard_scan_tint_color)));
                this.mStatusTextView.setText(R.string.security_dashboard_scanning);
                this.mStatusSubTextView.setVisibility(4);
                return;
            }
            if (ordinal == 2) {
                this.mStatusIconView.setVisibility(8);
                if (z) {
                    this.mStatusIconBGView.setAnimation("state_good.json");
                    this.mStatusIconBGView.playAnimation$1();
                } else {
                    this.mStatusIconBGView.setImageTintList(null);
                    this.mStatusIconBGView.setImageResource(
                            R.drawable.sec_security_privacy_status_bg_green);
                }
                this.mStatusIconView.setContentDescription(
                        getContext()
                                .getResources()
                                .getString(
                                        R.string
                                                .security_dashboard_accessibility_status_desc_good));
                this.mStatusTextView.setText(R.string.security_dashboard_looks_good);
                this.mStatusSubTextView.setVisibility(0);
                return;
            }
            if (ordinal == 3) {
                this.mStatusIconView.setVisibility(8);
                if (z) {
                    this.mStatusIconBGView.setAnimation("state_suggesion.json");
                    this.mStatusIconBGView.playAnimation$1();
                } else {
                    this.mStatusIconBGView.setImageTintList(null);
                    this.mStatusIconBGView.setImageResource(
                            R.drawable.sec_security_privacy_status_bg_yellow);
                }
                this.mStatusIconView.setContentDescription(
                        getContext()
                                .getResources()
                                .getString(
                                        R.string
                                                .security_dashboard_accessibility_status_desc_suggestion));
                this.mStatusTextView.setText(
                        getContext()
                                .getResources()
                                .getQuantityString(
                                        R.plurals.security_dashboard_suggestion,
                                        i2,
                                        Integer.valueOf(i2)));
                this.mStatusSubTextView.setVisibility(0);
                return;
            }
            if (ordinal != 4) {
                return;
            }
            this.mStatusIconView.setVisibility(8);
            if (z) {
                this.mStatusIconBGView.setAnimation("state_warning.json");
                this.mStatusIconBGView.playAnimation$1();
            } else {
                this.mStatusIconBGView.setImageTintList(null);
                this.mStatusIconBGView.setImageResource(
                        R.drawable.sec_security_privacy_status_bg_red);
            }
            this.mStatusIconView.setContentDescription(
                    getContext()
                            .getResources()
                            .getString(
                                    R.string.security_dashboard_accessibility_status_desc_warning));
            this.mStatusTextView.setText(
                    getContext()
                            .getResources()
                            .getQuantityString(
                                    R.plurals.security_dashboard_warning, i, Integer.valueOf(i)));
            this.mStatusSubTextView.setVisibility(0);
        }
    }

    public final void setupStatus(boolean z) {
        SecurityDashboardConstants$Status securityDashboardConstants$Status;
        SecurityDashboardConstants$Status securityDashboardConstants$Status2;
        Iterator it = ((ConcurrentHashMap) this.mExpandableMenusStatus).values().iterator();
        int i = 0;
        int i2 = 0;
        while (true) {
            boolean hasNext = it.hasNext();
            securityDashboardConstants$Status = SecurityDashboardConstants$Status.WARNING;
            securityDashboardConstants$Status2 = SecurityDashboardConstants$Status.CRITICAL;
            if (!hasNext) {
                break;
            }
            SecurityDashboardConstants$Status securityDashboardConstants$Status3 =
                    (SecurityDashboardConstants$Status) it.next();
            if (securityDashboardConstants$Status3.equals(securityDashboardConstants$Status2)) {
                i++;
            }
            if (securityDashboardConstants$Status3.equals(securityDashboardConstants$Status)) {
                i2++;
            }
        }
        if (i > 0) {
            securityDashboardConstants$Status = securityDashboardConstants$Status2;
        } else if (i2 <= 0) {
            securityDashboardConstants$Status = SecurityDashboardConstants$Status.OK;
        }
        setStatus(securityDashboardConstants$Status, i, i2, z);
    }

    public final void updateScanCategoryIcon(Drawable drawable) {
        LottieAnimationView lottieAnimationView;
        if (drawable == null || (lottieAnimationView = this.mStatusIconView) == null) {
            return;
        }
        lottieAnimationView.setImageDrawable(drawable);
        this.mStatusIconView.setImageTintList(
                ColorStateList.valueOf(getContext().getColor(R.color.white)));
    }
}
