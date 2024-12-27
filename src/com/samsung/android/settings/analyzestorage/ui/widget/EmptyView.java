package com.samsung.android.settings.analyzestorage.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.analyzestorage.presenter.page.PageType;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

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
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0013\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002¢\u0006\u0004\b\u0004\u0010\u0005B\u001d\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\u0004\b\u0004\u0010\bB%\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\n"
                + "\u001a\u00020\t¢\u0006\u0004\b\u0004\u0010\u000b¨\u0006\f"
        },
        d2 = {
            "Lcom/samsung/android/settings/analyzestorage/ui/widget/EmptyView;",
            "Landroid/widget/LinearLayout;",
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
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes4.dex */
public final class EmptyView extends LinearLayout {
    public EmptyView(Context context) {
        super(context);
        LinearLayout.inflate(getContext(), R.layout.as_no_item, this);
    }

    public final void setSubText(PageType pageType, String str) {
        String string;
        int ordinal = pageType.ordinal();
        if (ordinal == 23) {
            string = getContext().getResources().getString(R.string.as_no_unused_apps_sub_text);
            Intrinsics.checkNotNull(string);
        } else if (ordinal != 24) {
            string = ApnSettings.MVNO_NONE;
        } else {
            string = getContext().getResources().getString(R.string.as_no_app_cache_sub_text, str);
            Intrinsics.checkNotNull(string);
        }
        ((TextView) requireViewById(R.id.no_item_sub_text)).setText(string);
    }

    public EmptyView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        LinearLayout.inflate(getContext(), R.layout.as_no_item, this);
    }

    public EmptyView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        LinearLayout.inflate(getContext(), R.layout.as_no_item, this);
    }
}
