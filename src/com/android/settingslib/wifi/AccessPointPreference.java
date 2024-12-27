package com.android.settingslib.wifi;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.wifi.WifiConfiguration;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AccessPointPreference extends Preference {
    public final AccessPoint mAccessPoint;
    public final int mBadgePadding;
    public final StateListDrawable mFrictionSld;
    public final int mLevel;
    public final AnonymousClass1 mNotifyChanged;
    public TextView mTitleView;
    public static final int[] STATE_SECURED = {R.attr.state_encrypted};
    public static final int[] STATE_METERED = {R.attr.state_metered};
    public static final int[] WIFI_CONNECTION_STRENGTH = {
        R.string.accessibility_no_wifi,
        R.string.accessibility_wifi_one_bar,
        R.string.accessibility_wifi_two_bars,
        R.string.accessibility_wifi_three_bars,
        R.string.accessibility_wifi_signal_full
    };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class IconInjector {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class UserBadgeCache {}

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.settingslib.wifi.AccessPointPreference$1] */
    public AccessPointPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mNotifyChanged =
                new Runnable() { // from class: com.android.settingslib.wifi.AccessPointPreference.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        AccessPointPreference.this.notifyChanged();
                    }
                };
        this.mFrictionSld = null;
        this.mBadgePadding = 0;
    }

    public static CharSequence buildContentDescription(
            Context context, Preference preference, AccessPoint accessPoint) {
        CharSequence title = preference.getTitle();
        CharSequence summary = preference.getSummary();
        if (!TextUtils.isEmpty(summary)) {
            title = TextUtils.concat(title, ",", summary);
        }
        int level = accessPoint.getLevel();
        if (level >= 0 && level < 5) {
            title =
                    TextUtils.concat(
                            title, ",", context.getString(WIFI_CONNECTION_STRENGTH[level]));
        }
        CharSequence[] charSequenceArr = new CharSequence[3];
        charSequenceArr[0] = title;
        charSequenceArr[1] = ",";
        charSequenceArr[2] =
                accessPoint.security == 0
                        ? context.getString(R.string.accessibility_wifi_security_type_none)
                        : context.getString(R.string.accessibility_wifi_security_type_secured);
        return TextUtils.concat(charSequenceArr);
    }

    public static void setTitle(
            AccessPointPreference accessPointPreference, AccessPoint accessPoint) {
        accessPointPreference.setTitle(accessPoint.getTitle());
    }

    @Override // androidx.preference.Preference
    public final void notifyChanged() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            super.notifyChanged();
            return;
        }
        TextView textView = this.mTitleView;
        if (textView != null) {
            textView.post(this.mNotifyChanged);
        }
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        StateListDrawable stateListDrawable;
        super.onBindViewHolder(preferenceViewHolder);
        if (this.mAccessPoint == null) {
            return;
        }
        Drawable icon = getIcon();
        if (icon != null) {
            icon.setLevel(this.mLevel);
        }
        TextView textView = (TextView) preferenceViewHolder.findViewById(android.R.id.title);
        this.mTitleView = textView;
        if (textView != null) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    (Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
            this.mTitleView.setCompoundDrawablePadding(this.mBadgePadding);
        }
        preferenceViewHolder.itemView.setContentDescription(null);
        ImageView imageView = (ImageView) preferenceViewHolder.findViewById(R.id.friction_icon);
        if (imageView != null && (stateListDrawable = this.mFrictionSld) != null) {
            AccessPoint accessPoint = this.mAccessPoint;
            int i = accessPoint.security;
            if (i != 0 && i != 4) {
                stateListDrawable.setState(STATE_SECURED);
            } else if (accessPoint.mIsScoredNetworkMetered
                    || WifiConfiguration.isMetered(accessPoint.mConfig, accessPoint.mInfo)) {
                this.mFrictionSld.setState(STATE_METERED);
            }
            imageView.setImageDrawable(this.mFrictionSld.getCurrent());
        }
        preferenceViewHolder.findViewById(R.id.two_target_divider).setVisibility(4);
    }

    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.settingslib.wifi.AccessPointPreference$1] */
    public AccessPointPreference(
            AccessPoint accessPoint,
            Context context,
            UserBadgeCache userBadgeCache,
            int i,
            boolean z,
            StateListDrawable stateListDrawable,
            int i2,
            IconInjector iconInjector) {
        super(context);
        this.mNotifyChanged =
                new Runnable() { // from class: com.android.settingslib.wifi.AccessPointPreference.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        AccessPointPreference.this.notifyChanged();
                    }
                };
        setLayoutResource(R.layout.preference_access_point);
        setWidgetLayoutResource(R.layout.access_point_friction_widget);
        this.mAccessPoint = accessPoint;
        accessPoint.getClass();
        this.mLevel = i2;
        this.mFrictionSld = stateListDrawable;
        this.mBadgePadding =
                context.getResources().getDimensionPixelSize(R.dimen.wifi_preference_badge_padding);
    }
}
