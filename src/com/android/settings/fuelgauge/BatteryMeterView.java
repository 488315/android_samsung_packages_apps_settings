package com.android.settings.fuelgauge;

import android.content.Context;
import android.graphics.ColorFilter;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.android.settings.R;
import com.android.settingslib.Utils;
import com.android.settingslib.graph.ThemedBatteryDrawable;
import com.android.settingslib.graph.ThemedBatteryDrawable$sam$java_lang_Runnable$0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BatteryMeterView extends ImageView {
    ColorFilter mAccentColorFilter;
    BatteryMeterDrawable mDrawable;
    ColorFilter mErrorColorFilter;
    ColorFilter mForegroundColorFilter;

    public BatteryMeterView(Context context) {
        this(context, null, 0);
    }

    public int getBatteryLevel() {
        return this.mDrawable.batteryLevel;
    }

    public boolean getCharging() {
        return this.mDrawable.charging;
    }

    public boolean getPowerSave() {
        return this.mDrawable.powerSaveEnabled;
    }

    public void setBatteryLevel(int i) {
        this.mDrawable.setBatteryLevel(i);
        updateColorFilter();
    }

    public void setCharging(boolean z) {
        BatteryMeterDrawable batteryMeterDrawable = this.mDrawable;
        batteryMeterDrawable.charging = z;
        batteryMeterDrawable.unscheduleSelf(
                new ThemedBatteryDrawable$sam$java_lang_Runnable$0(
                        batteryMeterDrawable.invalidateRunnable));
        batteryMeterDrawable.scheduleSelf(
                new ThemedBatteryDrawable$sam$java_lang_Runnable$0(
                        batteryMeterDrawable.invalidateRunnable),
                0L);
        postInvalidate();
    }

    public void setPowerSave(boolean z) {
        BatteryMeterDrawable batteryMeterDrawable = this.mDrawable;
        batteryMeterDrawable.powerSaveEnabled = z;
        batteryMeterDrawable.unscheduleSelf(
                new ThemedBatteryDrawable$sam$java_lang_Runnable$0(
                        batteryMeterDrawable.invalidateRunnable));
        batteryMeterDrawable.scheduleSelf(
                new ThemedBatteryDrawable$sam$java_lang_Runnable$0(
                        batteryMeterDrawable.invalidateRunnable),
                0L);
        updateColorFilter();
    }

    public final void updateColorFilter() {
        BatteryMeterDrawable batteryMeterDrawable = this.mDrawable;
        boolean z = batteryMeterDrawable.powerSaveEnabled;
        int i = batteryMeterDrawable.batteryLevel;
        if (z) {
            batteryMeterDrawable.setColorFilter(this.mForegroundColorFilter);
        } else if (i < batteryMeterDrawable.criticalLevel) {
            batteryMeterDrawable.setColorFilter(this.mErrorColorFilter);
        } else {
            batteryMeterDrawable.setColorFilter(this.mAccentColorFilter);
        }
    }

    public BatteryMeterView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class BatteryMeterDrawable extends ThemedBatteryDrawable {
        public final int mIntrinsicHeight;
        public final int mIntrinsicWidth;

        public BatteryMeterDrawable(Context context, int i) {
            super(context, i);
            this.mIntrinsicWidth =
                    context.getResources().getDimensionPixelSize(R.dimen.battery_meter_width);
            this.mIntrinsicHeight =
                    context.getResources().getDimensionPixelSize(R.dimen.battery_meter_height);
        }

        @Override // android.graphics.drawable.Drawable
        public final int getIntrinsicHeight() {
            return this.mIntrinsicHeight;
        }

        @Override // android.graphics.drawable.Drawable
        public final int getIntrinsicWidth() {
            return this.mIntrinsicWidth;
        }

        public BatteryMeterDrawable(Context context, int i, int i2, int i3) {
            super(context, i);
            this.mIntrinsicWidth = i2;
            this.mIntrinsicHeight = i3;
        }
    }

    public BatteryMeterView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        int color = context.getColor(R.color.meter_background_color);
        this.mAccentColorFilter =
                Utils.getAlphaInvariantColorFilterForColor(
                        Utils.getColorAttrDefaultColor(context, android.R.attr.colorAccent));
        this.mErrorColorFilter =
                Utils.getAlphaInvariantColorFilterForColor(
                        context.getColor(R.color.battery_icon_color_error));
        this.mForegroundColorFilter =
                Utils.getAlphaInvariantColorFilterForColor(
                        Utils.getColorAttrDefaultColor(context, android.R.attr.colorForeground));
        BatteryMeterDrawable batteryMeterDrawable = new BatteryMeterDrawable(context, color);
        this.mDrawable = batteryMeterDrawable;
        batteryMeterDrawable.setColorFilter(this.mAccentColorFilter);
        setImageDrawable(this.mDrawable);
    }
}
