package com.android.settings.network.telephony;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.Annotation;
import android.text.SpannableString;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.utils.AnnotationSpan;
import com.android.settingslib.HelpUtils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.ToIntFunction;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NrDisabledInDsdsFooterPreferenceController extends BasePreferenceController {
    private int mSubId;

    public NrDisabledInDsdsFooterPreferenceController(Context context, String str) {
        super(context, str);
        this.mSubId = -1;
    }

    private CharSequence getFooterText() {
        Context context = this.mContext;
        AnnotationSpan.LinkInfo linkInfo =
                new AnnotationSpan.LinkInfo(
                        this.mContext,
                        HelpUtils.getHelpIntent(
                                context,
                                context.getString(R.string.help_uri_5g_dsds),
                                this.mContext.getClass().getName()));
        if (linkInfo.mActionable.booleanValue()) {
            return AnnotationSpan.linkify(
                    this.mContext.getText(R.string.no_5g_in_dsds_text), linkInfo);
        }
        CharSequence text = this.mContext.getText(R.string.no_5g_in_dsds_text);
        int i = AnnotationSpan.$r8$clinit;
        final SpannableString spannableString = new SpannableString(text);
        Annotation[] annotationArr =
                (Annotation[])
                        spannableString.getSpans(0, spannableString.length(), Annotation.class);
        if (annotationArr == null) {
            return text;
        }
        Arrays.sort(
                annotationArr,
                Comparator.comparingInt(
                        new ToIntFunction() { // from class:
                                              // com.android.settings.utils.AnnotationSpan$$ExternalSyntheticLambda0
                            @Override // java.util.function.ToIntFunction
                            public final int applyAsInt(Object obj) {
                                return -spannableString.getSpanStart((Annotation) obj);
                            }
                        }));
        StringBuilder sb = new StringBuilder(spannableString.toString());
        for (Annotation annotation : annotationArr) {
            sb.delete(
                    spannableString.getSpanStart(annotation),
                    spannableString.getSpanEnd(annotation));
        }
        return sb.toString();
    }

    private boolean is5GSupportedByRadio(TelephonyManager telephonyManager) {
        return (telephonyManager.getSupportedRadioAccessFamily() & 524288) > 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (this.mSubId == -1) {
            return 2;
        }
        TelephonyManager createForSubscriptionId =
                ((TelephonyManager) this.mContext.getSystemService("phone"))
                        .createForSubscriptionId(this.mSubId);
        int[] activeSubscriptionIdList =
                ((SubscriptionManager)
                                this.mContext.getSystemService("telephony_subscription_service"))
                        .getActiveSubscriptionIdList();
        return (!createForSubscriptionId.isDataEnabled()
                        || (activeSubscriptionIdList == null ? 0 : activeSubscriptionIdList.length)
                                < 2
                        || !is5GSupportedByRadio(createForSubscriptionId)
                        || createForSubscriptionId.canConnectTo5GInDsdsMode())
                ? 2
                : 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public void init(int i) {
        this.mSubId = i;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (preference != null) {
            preference.setTitle(ApnSettings.MVNO_NONE);
            preference.setTitle(getFooterText());
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
