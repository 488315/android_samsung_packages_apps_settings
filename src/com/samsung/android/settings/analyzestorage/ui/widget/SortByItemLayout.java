package com.samsung.android.settings.analyzestorage.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.preference.PreferenceManager;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.analyzestorage.presenter.controllers.AppListController;

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
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0013\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002¢\u0006\u0004\b\u0004\u0010\u0005B\u001d\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\u0004\b\u0004\u0010\bB%\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\n"
                + "\u001a\u00020\t¢\u0006\u0004\b\u0004\u0010\u000b¨\u0006\f"
        },
        d2 = {
            "Lcom/samsung/android/settings/analyzestorage/ui/widget/SortByItemLayout;",
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
public final class SortByItemLayout extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AppListController controller;
    public final LinearLayout sortBy;
    public final ImageView sortByIcon;
    public final ImageView sortByOrder;
    public final TextView sortByText;

    public SortByItemLayout(Context context) {
        super(context);
        setOrientation(0);
        setGravity(8388613);
        LinearLayout.inflate(getContext(), R.layout.as_sort_by_item_layout, this);
        this.sortBy = (LinearLayout) findViewById(R.id.sort_by);
        this.sortByOrder = (ImageView) findViewById(R.id.sort_by_order);
        this.sortByText = (TextView) findViewById(R.id.sort_by_text);
        this.sortByIcon = (ImageView) findViewById(R.id.sort_by_icon);
    }

    public final void updateSortByText() {
        int i;
        int i2 =
                PreferenceManager.getDefaultSharedPreferences(getContext())
                        .getInt("sort_type_unused_apps", 0);
        if (i2 != 0) {
            i = R.string.as_name;
            if (i2 != 1 && i2 == 2) {
                i = R.string.as_date_used;
            }
        } else {
            i = R.string.as_size;
        }
        TextView textView = this.sortByText;
        if (textView != null) {
            textView.setText(i);
        }
    }

    public SortByItemLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setOrientation(0);
        setGravity(8388613);
        LinearLayout.inflate(getContext(), R.layout.as_sort_by_item_layout, this);
        this.sortBy = (LinearLayout) findViewById(R.id.sort_by);
        this.sortByOrder = (ImageView) findViewById(R.id.sort_by_order);
        this.sortByText = (TextView) findViewById(R.id.sort_by_text);
        this.sortByIcon = (ImageView) findViewById(R.id.sort_by_icon);
    }

    public SortByItemLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setOrientation(0);
        setGravity(8388613);
        LinearLayout.inflate(getContext(), R.layout.as_sort_by_item_layout, this);
        this.sortBy = (LinearLayout) findViewById(R.id.sort_by);
        this.sortByOrder = (ImageView) findViewById(R.id.sort_by_order);
        this.sortByText = (TextView) findViewById(R.id.sort_by_text);
        this.sortByIcon = (ImageView) findViewById(R.id.sort_by_icon);
    }
}
