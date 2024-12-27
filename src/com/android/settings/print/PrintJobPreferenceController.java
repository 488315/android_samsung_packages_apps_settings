package com.android.settings.print;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.print.PrintJob;
import android.print.PrintJobInfo;
import android.text.format.DateUtils;

import com.android.settings.R;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PrintJobPreferenceController extends PrintJobPreferenceControllerBase {
    public PrintJobPreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.print.PrintJobPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.print.PrintJobPreferenceControllerBase
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.print.PrintJobPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.print.PrintJobPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.print.PrintJobPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.print.PrintJobPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.print.PrintJobPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.print.PrintJobPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.print.PrintJobPreferenceControllerBase
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.print.PrintJobPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.print.PrintJobPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.print.PrintJobPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.print.PrintJobPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.print.PrintJobPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.print.PrintJobPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settings.print.PrintJobPreferenceControllerBase
    public void updateUi() {
        PrintJob printJob = getPrintJob();
        if (printJob == null) {
            this.mFragment.finish();
            return;
        }
        if (printJob.isCancelled() || printJob.isCompleted()) {
            this.mFragment.finish();
            return;
        }
        PrintJobInfo info = printJob.getInfo();
        int state = info.getState();
        if (state == 1) {
            this.mPreference.setTitle(
                    this.mContext.getString(
                            R.string.print_configuring_state_title_template, info.getLabel()));
        } else if (state == 2 || state == 3) {
            if (printJob.getInfo().isCancelling()) {
                this.mPreference.setTitle(
                        this.mContext.getString(
                                R.string.print_cancelling_state_title_template, info.getLabel()));
            } else {
                this.mPreference.setTitle(
                        this.mContext.getString(
                                R.string.print_printing_state_title_template, info.getLabel()));
            }
        } else if (state != 4) {
            if (state == 6) {
                this.mPreference.setTitle(
                        this.mContext.getString(
                                R.string.print_failed_state_title_template, info.getLabel()));
            }
        } else if (printJob.getInfo().isCancelling()) {
            this.mPreference.setTitle(
                    this.mContext.getString(
                            R.string.print_cancelling_state_title_template, info.getLabel()));
        } else {
            this.mPreference.setTitle(
                    this.mContext.getString(
                            R.string.print_blocked_state_title_template, info.getLabel()));
        }
        this.mPreference.setSummary(
                this.mContext.getString(
                        R.string.print_job_summary,
                        info.getPrinterName(),
                        DateUtils.formatSameDayTime(
                                info.getCreationTime(), info.getCreationTime(), 3, 3)));
        TypedArray obtainStyledAttributes =
                this.mContext.obtainStyledAttributes(new int[] {android.R.attr.colorControlNormal});
        int color = obtainStyledAttributes.getColor(0, 0);
        obtainStyledAttributes.recycle();
        int state2 = info.getState();
        if (state2 == 2 || state2 == 3) {
            Drawable drawable =
                    this.mContext.getDrawable(
                            android.R.drawable.ic_signal_wifi_transient_animation_drawable);
            drawable.setTint(color);
            this.mPreference.setIcon(drawable);
        } else if (state2 == 4 || state2 == 6) {
            Drawable drawable2 =
                    this.mContext.getDrawable(android.R.drawable.ic_sim_card_multi_24px_clr);
            drawable2.setTint(color);
            this.mPreference.setIcon(drawable2);
        }
    }

    @Override // com.android.settings.print.PrintJobPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
