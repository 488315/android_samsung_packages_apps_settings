package com.samsung.android.settings.wifi.intelligent;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Debug;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecPreference;

import com.android.settings.R;
import com.android.settings.R$styleable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiWcmUnclickablePreference extends SecPreference {
    public final Context mContext;

    static {
        Debug.semIsProductDev();
    }

    public WifiWcmUnclickablePreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mContext = context;
        setSelectable(false);
        if (attributeSet == null) {
            setLayoutResource(R.layout.sec_widget_preference_unclickable);
            return;
        }
        context.obtainStyledAttributes(attributeSet, R$styleable.SecUnclickable).recycle();
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R$styleable.Preference, i, i2);
        setLayoutResource(
                obtainStyledAttributes.getResourceId(
                        3, R.layout.sec_widget_preference_unclickable));
        obtainStyledAttributes.recycle();
        TypedArray obtainStyledAttributes2 =
                context.obtainStyledAttributes(attributeSet, R$styleable.SecCategory);
        obtainStyledAttributes2.getInt(0, 15);
        obtainStyledAttributes2.recycle();
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.title);
        if (textView != null && this.mContext != null) {
            textView.setText(getTitle());
            textView.setVisibility(0);
        }
        preferenceViewHolder.mDividerAllowedAbove = false;
        preferenceViewHolder.mDividerAllowedBelow = false;
    }

    public WifiWcmUnclickablePreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public WifiWcmUnclickablePreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }
}
