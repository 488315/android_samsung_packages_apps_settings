package com.samsung.android.settings.uwb.labs.view.statistics;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import com.samsung.android.settings.uwb.labs.common.Month;
import com.samsung.android.settings.uwb.labs.common.UwbLabsUtils;
import com.samsung.android.settings.uwb.labs.common.Week;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class WeeklyReport {
    public final Context mContext;
    public final ImageView mGraphDataImage1;
    public final ImageView mGraphDataImage2;
    public final ImageView mGraphDataImage3;
    public final TextView mGraphDate;
    public final TextView mGraphText1;
    public final TextView mGraphText2;
    public final TextView mGraphText3;
    public final TextView mGraphValue1;
    public final TextView mGraphValue2;
    public final TextView mGraphValue3;
    public final PreferenceViewHolder mHolder;
    public final List mWeeklyDate = new ArrayList();

    public WeeklyReport(Context context, PreferenceViewHolder preferenceViewHolder) {
        this.mContext = context;
        this.mHolder = preferenceViewHolder;
        this.mGraphDate = (TextView) preferenceViewHolder.findViewById(R.id.weekly_report_date_tv);
        this.mGraphText1 = (TextView) preferenceViewHolder.findViewById(R.id.graph_info_tv_0);
        this.mGraphText2 = (TextView) preferenceViewHolder.findViewById(R.id.graph_info_tv_1);
        this.mGraphText3 = (TextView) preferenceViewHolder.findViewById(R.id.graph_info_tv_2);
        this.mGraphValue1 = (TextView) preferenceViewHolder.findViewById(R.id.graph_info_tv_0_1);
        this.mGraphValue2 = (TextView) preferenceViewHolder.findViewById(R.id.graph_info_tv_1_1);
        this.mGraphValue3 = (TextView) preferenceViewHolder.findViewById(R.id.graph_info_tv_2_1);
        ImageView imageView =
                (ImageView) preferenceViewHolder.findViewById(R.id.graph_info_image_0);
        this.mGraphDataImage1 = imageView;
        ImageView imageView2 =
                (ImageView) preferenceViewHolder.findViewById(R.id.graph_info_image_1);
        this.mGraphDataImage2 = imageView2;
        ImageView imageView3 =
                (ImageView) preferenceViewHolder.findViewById(R.id.graph_info_image_2);
        this.mGraphDataImage3 = imageView3;
        imageView.setImageDrawable(
                getPointDrawable(R.color.sec_uwb_labs_weekly_report_bar_color_1));
        imageView2.setImageDrawable(
                getPointDrawable(R.color.sec_uwb_labs_weekly_report_bar_color_2));
        imageView3.setImageDrawable(
                getPointDrawable(R.color.sec_uwb_labs_weekly_report_bar_color_3));
    }

    public final void generateWeekData() {
        ((ArrayList) this.mWeeklyDate).clear();
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 7; i++) {
            ((ArrayList) this.mWeeklyDate)
                    .add(
                            Week.values()[UwbLabsUtils.convertWeekDay(calendar.get(7))].getName()
                                    + ". "
                                    + Month.values()[calendar.get(2)].getName()
                                    + " "
                                    + calendar.get(5)
                                    + UwbLabsUtils.toDateFormat(calendar.get(5)));
            calendar.add(5, -1);
        }
    }

    public final Drawable getPointDrawable(int i) {
        GradientDrawable gradientDrawable =
                (GradientDrawable) this.mContext.getDrawable(R.drawable.sec_uwb_labs_open_point);
        gradientDrawable.setColor(this.mContext.getColor(i));
        return gradientDrawable;
    }
}
