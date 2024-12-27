package com.samsung.android.settings.lockscreen;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.widget.SecUnclickablePreference;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u001c\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\b\b\u0007\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0006¢\u0006\u0004\b"
                + "\t\u0010\n"
                + "B\u0011\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0004\b"
                + "\t\u0010\u000bB\u001b\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004¢\u0006\u0004\b"
                + "\t\u0010\fB#\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004\u0012\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0004\b"
                + "\t\u0010\r"
                + "¨\u0006\u000e"
        },
        d2 = {
            "Lcom/samsung/android/settings/lockscreen/SecHomeTimeZonePreference;",
            "Lcom/samsung/android/settings/widget/SecUnclickablePreference;",
            "Landroid/content/Context;",
            "context",
            "Landroid/util/AttributeSet;",
            "attrs",
            ApnSettings.MVNO_NONE,
            "defStyleAttr",
            "defStyleRes",
            "<init>",
            "(Landroid/content/Context;Landroid/util/AttributeSet;II)V",
            "(Landroid/content/Context;)V",
            "(Landroid/content/Context;Landroid/util/AttributeSet;)V",
            "(Landroid/content/Context;Landroid/util/AttributeSet;I)V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes4.dex */
public final class SecHomeTimeZonePreference extends SecUnclickablePreference {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecHomeTimeZonePreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        setLayoutResource(R.layout.sec_home_time_zone_preference_unclickable);
    }

    @Override // com.samsung.android.settings.widget.SecUnclickablePreference,
              // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.title);
        TextView textView2 = (TextView) preferenceViewHolder.findViewById(R.id.summary);
        if (textView != null) {
            ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
            Intrinsics.checkNotNull(
                    layoutParams,
                    "null cannot be cast to non-null type"
                        + " android.widget.LinearLayout.LayoutParams");
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) layoutParams;
            int dimensionPixelSize =
                    getContext()
                            .getResources()
                            .getDimensionPixelSize(R.dimen.sec_widget_body_text_padding_start_end);
            layoutParams2.setMargins(dimensionPixelSize, 0, dimensionPixelSize, 0);
            textView.setLayoutParams(layoutParams2);
        }
        if (textView2 != null) {
            textView2.setText(getSummary());
            textView2.setVisibility(0);
            textView2.setTextColor(
                    getContext().getResources().getColor(R.color.sec_widget_color_primary));
            ViewGroup.LayoutParams layoutParams3 = textView2.getLayoutParams();
            Intrinsics.checkNotNull(
                    layoutParams3,
                    "null cannot be cast to non-null type"
                        + " android.widget.LinearLayout.LayoutParams");
            LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) layoutParams3;
            int dimensionPixelSize2 =
                    getContext()
                            .getResources()
                            .getDimensionPixelSize(R.dimen.sec_widget_body_text_padding_start_end);
            layoutParams4.setMargins(
                    dimensionPixelSize2,
                    0,
                    dimensionPixelSize2,
                    ((SecUnclickablePreference) this)
                            .mContext
                            .getResources()
                            .getDimensionPixelSize(
                                    R.dimen.sec_widget_preference_unclickable_margin_bottom));
            textView2.setLayoutParams(layoutParams4);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SecHomeTimeZonePreference(Context context) {
        this(context, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SecHomeTimeZonePreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SecHomeTimeZonePreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }
}
