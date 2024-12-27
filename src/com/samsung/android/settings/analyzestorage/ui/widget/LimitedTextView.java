package com.samsung.android.settings.analyzestorage.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000 \n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u0013\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002¢\u0006\u0004\b\u0004\u0010\u0005B\u001d\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\u0004\b\u0004\u0010\bB%\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\n"
                + "\u001a\u00020"
                + "\t¢\u0006\u0004\b\u0004\u0010\u000bB-\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\n"
                + "\u001a\u00020\t\u0012\u0006\u0010\f\u001a\u00020\t¢\u0006\u0004\b\u0004\u0010\r"
                + "¨\u0006\u000e"
        },
        d2 = {
            "Lcom/samsung/android/settings/analyzestorage/ui/widget/LimitedTextView;",
            "Landroid/widget/TextView;",
            "Landroid/content/Context;",
            "context",
            "<init>",
            "(Landroid/content/Context;)V",
            "Landroid/util/AttributeSet;",
            "attrs",
            "(Landroid/content/Context;Landroid/util/AttributeSet;)V",
            ApnSettings.MVNO_NONE,
            "defStyleAttr",
            "(Landroid/content/Context;Landroid/util/AttributeSet;I)V",
            "defStyleRes",
            "(Landroid/content/Context;Landroid/util/AttributeSet;II)V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes4.dex */
public final class LimitedTextView extends TextView {
    public LimitedTextView(Context context) {
        super(context);
        setTextSize(getTextSize());
    }

    @Override // android.widget.TextView
    public final void setTextSize(float f) {
        float f2 = getResources().getConfiguration().fontScale;
        if (f2 > 1.2f) {
            f *= 1.2f / f2;
        }
        setTextSize(0, f);
    }

    public LimitedTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setTextSize(getTextSize());
    }

    public LimitedTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setTextSize(getTextSize());
    }

    public LimitedTextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        setTextSize(getTextSize());
    }
}
