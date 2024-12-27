package com.samsung.android.settings.lockscreen;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;

import com.samsung.android.util.SemLog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class NotificationTemplateView extends FrameLayout {
    public final Context mContext;
    public DetailedNotiBehavior mCurrentBehavior;
    public int mCurrentMode;
    public final View mDimmedBGView;
    public final Handler mHandler;
    public final AnonymousClass1 mInvertTextRunnable;
    public boolean mIsAdaptiveTextColor;
    public boolean mIsInOpenTheme;
    public boolean mIsQuickColoring;
    public boolean mIsWhiteWallpaper;
    public final AnonymousClass2 mSettingsObserver;
    public final View mStackScrollerBGView;
    public boolean mTextColorInversion;
    public float mViewOpacity;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.lockscreen.NotificationTemplateView$2, reason: invalid class name */
    public final class AnonymousClass2 extends ContentObserver {
        public AnonymousClass2(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            NotificationTemplateView notificationTemplateView = NotificationTemplateView.this;
            notificationTemplateView.mHandler.post(notificationTemplateView.mInvertTextRunnable);
        }
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [com.samsung.android.settings.lockscreen.NotificationTemplateView$1] */
    public NotificationTemplateView(Context context) {
        super(context);
        this.mViewOpacity = -1.0f;
        this.mCurrentMode = 0;
        this.mIsWhiteWallpaper = false;
        this.mIsAdaptiveTextColor = false;
        Handler handler = new Handler();
        this.mHandler = handler;
        this.mInvertTextRunnable =
                new Runnable() { // from class:
                                 // com.samsung.android.settings.lockscreen.NotificationTemplateView.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        boolean z =
                                1
                                        == Settings.System.getInt(
                                                NotificationTemplateView.this.mContext
                                                        .getContentResolver(),
                                                "notification_text_color_inversion",
                                                1);
                        NotificationTemplateView notificationTemplateView =
                                NotificationTemplateView.this;
                        notificationTemplateView.mIsAdaptiveTextColor = z;
                        notificationTemplateView.setTextInversion();
                    }
                };
        this.mSettingsObserver = new AnonymousClass2(handler);
        this.mContext = context;
        this.mIsInOpenTheme =
                Settings.System.getString(
                                context.getContentResolver(), "current_sec_active_themepackage")
                        != null;
        try {
            Resources resourcesForApplication =
                    context.getPackageManager().getResourcesForApplication("com.android.systemui");
            this.mIsQuickColoring =
                    resourcesForApplication.getBoolean(
                            resourcesForApplication.getIdentifier(
                                    "theme_designer_quick_panel_turned_on",
                                    "bool",
                                    "com.android.systemui"));
        } catch (PackageManager.NameNotFoundException | Resources.NotFoundException unused) {
            this.mIsQuickColoring = false;
        }
        this.mDimmedBGView = new View(this.mContext);
        this.mStackScrollerBGView = new View(this.mContext);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
        layoutParams.gravity = 1;
        this.mDimmedBGView.setBackgroundTintList(ColorStateList.valueOf(getDimmedBgColor()));
        this.mStackScrollerBGView.setBackgroundTintList(ColorStateList.valueOf(getStackBGColor()));
        addView(this.mStackScrollerBGView, layoutParams);
        addView(this.mDimmedBGView, layoutParams);
        this.mIsAdaptiveTextColor =
                1
                        == Settings.System.getInt(
                                this.mContext.getContentResolver(),
                                "notification_text_color_inversion",
                                1);
        changeState();
    }

    public final void changeState() {
        View view;
        DetailedNotiBehavior detailedNotiBehavior = this.mCurrentBehavior;
        if (detailedNotiBehavior != null && (view = detailedNotiBehavior.mContentView) != null) {
            view.setVisibility(8);
        }
        DetailedNotiBehavior detailedNotiBehavior2 = new DetailedNotiBehavior();
        detailedNotiBehavior2.mContext = getContext();
        this.mCurrentBehavior = detailedNotiBehavior2;
        View findViewWithTag = findViewWithTag(DetailedNotiBehavior.class.getName());
        if (findViewWithTag == null) {
            DetailedNotiBehavior detailedNotiBehavior3 = this.mCurrentBehavior;
            if (detailedNotiBehavior3.mContentView == null) {
                View inflate =
                        LayoutInflater.from(detailedNotiBehavior3.mContext)
                                .inflate(
                                        R.layout.sec_lockscreen_notification_detail_preview,
                                        (ViewGroup) null);
                detailedNotiBehavior3.mContentView = inflate;
                inflate.findViewById(R.id.time).setTime(System.currentTimeMillis());
                ((ImageView) detailedNotiBehavior3.mContentView.findViewById(R.id.icon))
                        .setColorFilter(
                                detailedNotiBehavior3.mContext.getColor(
                                        R.color.sec_notification_preview_lock_icon_color));
                detailedNotiBehavior3.mContentView.setTag(DetailedNotiBehavior.class.getName());
            }
            addView(detailedNotiBehavior3.mContentView);
        } else {
            this.mCurrentBehavior.mContentView = findViewWithTag;
        }
        View view2 = this.mCurrentBehavior.mContentView;
        if (view2 != null) {
            view2.setVisibility(0);
        }
        this.mStackScrollerBGView.setVisibility(0);
        this.mDimmedBGView.setVisibility(0);
    }

    public final int getDimmedBgColor() {
        int i;
        int i2 = this.mContext.getResources().getConfiguration().uiMode & 48;
        if (this.mIsQuickColoring) {
            try {
                Resources resourcesForApplication =
                        this.mContext
                                .getPackageManager()
                                .getResourcesForApplication("com.android.systemui");
                i =
                        resourcesForApplication.getColor(
                                resourcesForApplication.getIdentifier(
                                        "qp_notification_background_color",
                                        "color",
                                        "com.android.systemui"),
                                null);
            } catch (PackageManager.NameNotFoundException | Resources.NotFoundException unused) {
                i = -1;
            }
            if (i != -1) {
                return i;
            }
        }
        return i2 != 32 ? -197380 : -14342875;
    }

    public final int getStackBGColor() {
        int i;
        int i2 = this.mContext.getResources().getConfiguration().uiMode & 48;
        if (this.mIsQuickColoring) {
            try {
                Resources resourcesForApplication =
                        this.mContext
                                .getPackageManager()
                                .getResourcesForApplication("com.android.systemui");
                i =
                        resourcesForApplication.getColor(
                                resourcesForApplication.getIdentifier(
                                        "sec_panel_background_color",
                                        "color",
                                        "com.android.systemui"),
                                null);
            } catch (PackageManager.NameNotFoundException | Resources.NotFoundException unused) {
                i = -1;
            }
            if (i != -1) {
                return i;
            }
        }
        return i2 != 16
                ? i2 != 32 ? -1513240 : -12829636
                : this.mIsWhiteWallpaper ? -5921371 : -1513240;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("notification_text_color_inversion"),
                        true,
                        this.mSettingsObserver);
        this.mSettingsObserver.onChange(false);
        this.mIsInOpenTheme =
                Settings.System.getString(
                                this.mContext.getContentResolver(),
                                "current_sec_active_themepackage")
                        != null;
        try {
            Resources resourcesForApplication =
                    this.mContext
                            .getPackageManager()
                            .getResourcesForApplication("com.android.systemui");
            this.mIsQuickColoring =
                    resourcesForApplication.getBoolean(
                            resourcesForApplication.getIdentifier(
                                    "theme_designer_quick_panel_turned_on",
                                    "bool",
                                    "com.android.systemui"));
        } catch (PackageManager.NameNotFoundException | Resources.NotFoundException unused) {
            this.mIsQuickColoring = false;
        }
        if (!this.mIsQuickColoring) {
            this.mCurrentBehavior.getClass();
            return;
        }
        SemLog.d("NTV", "NotiPreview Setting catch QSColoring color");
        DetailedNotiBehavior detailedNotiBehavior = this.mCurrentBehavior;
        boolean z = this.mIsQuickColoring;
        detailedNotiBehavior.getClass();
        if (z) {
            try {
                Resources resourcesForApplication2 =
                        detailedNotiBehavior
                                .mContext
                                .getPackageManager()
                                .getResourcesForApplication("com.android.systemui");
                int color =
                        resourcesForApplication2.getColor(
                                resourcesForApplication2.getIdentifier(
                                        "qp_notification_title_color",
                                        "color",
                                        "com.android.systemui"),
                                null);
                int color2 =
                        resourcesForApplication2.getColor(
                                resourcesForApplication2.getIdentifier(
                                        "qp_notification_content_color",
                                        "color",
                                        "com.android.systemui"),
                                null);
                ((ImageView) detailedNotiBehavior.mContentView.findViewById(R.id.icon))
                        .setColorFilter(
                                resourcesForApplication2.getColor(
                                        resourcesForApplication2.getIdentifier(
                                                "qp_notification_primary_color",
                                                "color",
                                                "com.android.systemui"),
                                        null));
                ((TextView) detailedNotiBehavior.mContentView.findViewById(R.id.title))
                        .setTextColor(color);
                ((TextView) detailedNotiBehavior.mContentView.findViewById(R.id.text))
                        .setTextColor(color2);
                ((TextView) detailedNotiBehavior.mContentView.findViewById(R.id.time))
                        .setTextColor(color2);
            } catch (PackageManager.NameNotFoundException | Resources.NotFoundException unused2) {
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mContext.getContentResolver().unregisterContentObserver(this.mSettingsObserver);
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i);
        DetailedNotiBehavior detailedNotiBehavior = this.mCurrentBehavior;
        ViewGroup.LayoutParams layoutParams = detailedNotiBehavior.mContentView.getLayoutParams();
        if (layoutParams.height >= 0) {
            detailedNotiBehavior.mContentView.measure(
                    View.MeasureSpec.makeMeasureSpec(layoutParams.width, 1073741824),
                    View.MeasureSpec.makeMeasureSpec(layoutParams.height, 1073741824));
        } else {
            detailedNotiBehavior.mContentView.measure(i, i2);
        }
        int measuredHeight = detailedNotiBehavior.mContentView.getMeasuredHeight();
        View view = this.mStackScrollerBGView;
        if (view != null) {
            view.measure(i, View.MeasureSpec.makeMeasureSpec(measuredHeight, 1073741824));
        }
        View view2 = this.mDimmedBGView;
        if (view2 != null) {
            view2.measure(i, View.MeasureSpec.makeMeasureSpec(measuredHeight, 1073741824));
        }
        setMeasuredDimension(size, measuredHeight);
    }

    public final void setSensitiveMode(int i) {
        DetailedNotiBehavior detailedNotiBehavior = this.mCurrentBehavior;
        boolean z = i == 1;
        TextView textView = (TextView) detailedNotiBehavior.mContentView.findViewById(R.id.text);
        if (textView != null) {
            textView.setVisibility(z ? 8 : 0);
        }
        TextView textView2 = (TextView) detailedNotiBehavior.mContentView.findViewById(R.id.title);
        if (textView2 != null) {
            textView2.setVisibility(z ? 8 : 0);
        }
        TextView textView3 =
                (TextView) detailedNotiBehavior.mContentView.findViewById(R.id.app_name_text);
        if (textView3 != null) {
            textView3.setVisibility(z ? 0 : 8);
        }
        setTextInversion();
    }

    public final void setTextInversion() {
        boolean z;
        if (this.mIsInOpenTheme
                || this.mIsQuickColoring
                || this.mTextColorInversion == (z = this.mIsAdaptiveTextColor)) {
            return;
        }
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(" textColor Inversion = ", "NTV", z);
        DetailedNotiBehavior detailedNotiBehavior = this.mCurrentBehavior;
        if (detailedNotiBehavior.mContentView != null) {
            boolean z2 = false;
            boolean z3 =
                    (detailedNotiBehavior.mContext.getResources().getConfiguration().uiMode & 48)
                            == 32;
            boolean z4 = detailedNotiBehavior.mIsWhiteWallpaper;
            if (z4 || !z3 ? !z4 || z3 : !z) {
                z2 = z;
            }
            ((TextView) detailedNotiBehavior.mContentView.findViewById(R.id.title))
                    .setTextColor(
                            z2
                                    ? detailedNotiBehavior.mContext.getColor(
                                            R.color.sec_notification_preview_title_inverse_color)
                                    : detailedNotiBehavior.mContext.getColor(
                                            R.color.sec_notification_preview_title_color));
            ((TextView) detailedNotiBehavior.mContentView.findViewById(R.id.app_name_text))
                    .setTextColor(
                            z2
                                    ? detailedNotiBehavior.mContext.getColor(
                                            R.color.sec_notification_preview_title_inverse_color)
                                    : detailedNotiBehavior.mContext.getColor(
                                            R.color.sec_notification_preview_title_color));
            ((TextView) detailedNotiBehavior.mContentView.findViewById(R.id.time))
                    .setTextColor(
                            z2
                                    ? detailedNotiBehavior.mContext.getColor(
                                            R.color.sec_notification_preview_time_inverse_color)
                                    : detailedNotiBehavior.mContext.getColor(
                                            R.color.sec_notification_preview_time_color));
            ((TextView) detailedNotiBehavior.mContentView.findViewById(R.id.text))
                    .setTextColor(
                            z2
                                    ? detailedNotiBehavior.mContext.getColor(
                                            R.color.sec_notification_preview_text_inverse_color)
                                    : detailedNotiBehavior.mContext.getColor(
                                            R.color.sec_notification_preview_text_color));
        }
        this.mTextColorInversion = z;
    }

    public final void setViewOpacity(float f, boolean z) {
        if (this.mViewOpacity != f || z) {
            this.mDimmedBGView.setAlpha(f);
            this.mStackScrollerBGView.setAlpha(f);
            setTextInversion();
            this.mViewOpacity = f;
        }
    }
}
