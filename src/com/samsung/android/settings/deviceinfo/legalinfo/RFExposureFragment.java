package com.samsung.android.settings.deviceinfo.legalinfo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

import com.samsung.android.settings.deviceinfo.aboutphone.ModelNameGetter;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class RFExposureFragment extends SettingsPreferenceFragment {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 4812;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.sec_rf_exposure, viewGroup, false);
        TextView textView = (TextView) inflate.findViewById(R.id.rf_exposure_description_view);
        String string =
                getResources()
                        .getString(
                                R.string.rf_exposure_description, "https://www.samsung.com/sar/");
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(string);
        final int indexOf = string.indexOf("https://www.samsung.com/sar/");
        final int i = indexOf + 28;
        spannableStringBuilder.setSpan(
                new ClickableSpan() { // from class:
                                      // com.samsung.android.settings.deviceinfo.legalinfo.RFExposureFragment.1
                    @Override // android.text.style.ClickableSpan
                    public final void onClick(View view) {
                        Selection.setSelection((Spannable) ((TextView) view).getText(), indexOf, i);
                        view.invalidate();
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        String modelName =
                                new ModelNameGetter(RFExposureFragment.this.getContext())
                                        .getModelName();
                        String upperCase = Utils.readCountryCode().toUpperCase();
                        String upperCase2 = Locale.getDefault().getLanguage().toUpperCase();
                        StringBuilder m =
                                SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                        "modelName=",
                                        modelName,
                                        ", country=",
                                        upperCase,
                                        ", language=");
                        m.append(upperCase2);
                        Log.i("RF_Expoure", m.toString());
                        String str =
                                "https://www.samsung.com/sar/sarMain?site_cd=&prd_mdl_name="
                                        + modelName
                                        + "&selNatCd="
                                        + upperCase
                                        + "&languageCode="
                                        + upperCase2;
                        Log.i("RF_Expoure", "targetURL=" + str);
                        intent.setData(Uri.parse(str));
                        RFExposureFragment.this.startActivity(intent);
                    }
                },
                indexOf,
                i,
                33);
        textView.setText(spannableStringBuilder);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        return inflate;
    }
}
