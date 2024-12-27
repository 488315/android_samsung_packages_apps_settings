package com.samsung.android.settings.usefulfeature.intelligenceservice;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecSwitchPreferenceScreen;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;

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
                + "\u0002\b\b\b\u0007\u0018\u00002\u00020\u0001B-\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0006¢\u0006\u0004\b"
                + "\t\u0010\n"
                + "B%\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004\u0012\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0004\b"
                + "\t\u0010\u000bB\u001d\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004¢\u0006\u0004\b"
                + "\t\u0010\fB\u0013\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002¢\u0006\u0004\b"
                + "\t\u0010\r"
                + "¨\u0006\u000e"
        },
        d2 = {
            "Lcom/samsung/android/settings/usefulfeature/intelligenceservice/SecIntelligenceSwitchPreferenceScreen;",
            "Landroidx/preference/SecSwitchPreferenceScreen;",
            "Landroid/content/Context;",
            "context",
            "Landroid/util/AttributeSet;",
            "attrs",
            ApnSettings.MVNO_NONE,
            "defStyleAttr",
            "defStyleRes",
            "<init>",
            "(Landroid/content/Context;Landroid/util/AttributeSet;II)V",
            "(Landroid/content/Context;Landroid/util/AttributeSet;I)V",
            "(Landroid/content/Context;Landroid/util/AttributeSet;)V",
            "(Landroid/content/Context;)V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes3.dex */
public final class SecIntelligenceSwitchPreferenceScreen extends SecSwitchPreferenceScreen {
    public SecIntelligenceSwitchPreferenceScreen(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // androidx.preference.SecSwitchPreferenceScreen,
              // androidx.preference.SeslSwitchPreferenceScreen,
              // androidx.preference.SwitchPreferenceCompat, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        TextView textView = (TextView) preferenceViewHolder.itemView.findViewById(R.id.summary);
        if (textView != null) {
            textView.setMaxLines(30);
        }
    }

    public SecIntelligenceSwitchPreferenceScreen(
            Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public SecIntelligenceSwitchPreferenceScreen(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SecIntelligenceSwitchPreferenceScreen(Context context) {
        super(context);
    }
}
