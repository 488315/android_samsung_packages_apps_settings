package com.android.settings.applications;

import android.content.pm.PackageManager;
import android.icu.text.MessageFormat;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.PreferenceCategory;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settingslib.widget.FooterPreference;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class OpenSupportedLinks extends AppInfoWithHeader
        implements SelectorWithWidgetPreference.OnClickListener {
    SelectorWithWidgetPreference mAllowOpening;
    SelectorWithWidgetPreference mAskEveryTime;
    public int mCurrentIndex;
    SelectorWithWidgetPreference mNotAllowed;
    PackageManager mPackageManager;
    PreferenceCategory mPreferenceCategory;
    public final String[] mRadioKeys = {
        "app_link_open_always", "app_link_open_ask", "app_link_open_never"
    };

    public void addLinksToFooter(FooterPreference footerPreference) {
        ArraySet arraySet =
                (ArraySet) Utils.getHandledDomains(this.mPackageManager, this.mPackageName);
        if (arraySet.isEmpty()) {
            Log.w("OpenSupportedLinks", "Can't find any app links.");
            return;
        }
        String str = ((Object) footerPreference.getTitle()) + System.lineSeparator();
        Iterator it = arraySet.iterator();
        while (it.hasNext()) {
            str = ((Object) str) + System.lineSeparator() + ((String) it.next());
        }
        footerPreference.setTitle(str);
    }

    @Override // com.android.settings.applications.AppInfoBase
    public final AlertDialog createDialog(int i) {
        return null;
    }

    public int getEntriesNo() {
        return ((ArraySet) Utils.getHandledDomains(this.mPackageManager, this.mPackageName)).size();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1824;
    }

    public void initRadioPreferencesGroup() {
        this.mPreferenceCategory =
                (PreferenceCategory) findPreference("supported_links_radio_group");
        this.mAllowOpening =
                makeRadioPreference$1(R.string.app_link_open_always, "app_link_open_always");
        int entriesNo = getEntriesNo();
        MessageFormat messageFormat =
                new MessageFormat(
                        getResources().getString(R.string.app_link_open_always_summary),
                        Locale.getDefault());
        HashMap hashMap = new HashMap();
        hashMap.put("count", Integer.valueOf(entriesNo));
        SelectorWithWidgetPreference selectorWithWidgetPreference = this.mAllowOpening;
        View view = selectorWithWidgetPreference.mAppendix;
        if (view != null) {
            view.setVisibility(8);
        }
        selectorWithWidgetPreference.mAppendixVisibility = 8;
        this.mAllowOpening.setSummary(messageFormat.format(hashMap));
        this.mAskEveryTime = makeRadioPreference$1(R.string.app_link_open_ask, "app_link_open_ask");
        this.mNotAllowed =
                makeRadioPreference$1(R.string.app_link_open_never, "app_link_open_never");
        int intentVerificationStatusAsUser =
                this.mPackageManager.getIntentVerificationStatusAsUser(
                        this.mPackageName, this.mUserId);
        int i = 2;
        if (intentVerificationStatusAsUser == 2) {
            i = 0;
        } else if (intentVerificationStatusAsUser != 3) {
            i = 1;
        }
        this.mCurrentIndex = i;
        setRadioStatus(i);
    }

    public final SelectorWithWidgetPreference makeRadioPreference$1(int i, String str) {
        SelectorWithWidgetPreference selectorWithWidgetPreference =
                new SelectorWithWidgetPreference(this.mPreferenceCategory.getContext());
        selectorWithWidgetPreference.setKey(str);
        selectorWithWidgetPreference.setTitle(i);
        selectorWithWidgetPreference.mListener = this;
        this.mPreferenceCategory.addPreference(selectorWithWidgetPreference);
        return selectorWithWidgetPreference;
    }

    @Override // com.android.settings.applications.AppInfoBase,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mPackageManager = getPackageManager();
        addPreferencesFromResource(R.xml.open_supported_links);
        initRadioPreferencesGroup();
        FooterPreference footerPreference =
                (FooterPreference) findPreference("supported_links_footer");
        if (footerPreference == null) {
            Log.w("OpenSupportedLinks", "Can't find the footer preference.");
        } else {
            addLinksToFooter(footerPreference);
        }
    }

    @Override // com.android.settingslib.widget.SelectorWithWidgetPreference.OnClickListener
    public final void onRadioButtonClicked(
            SelectorWithWidgetPreference selectorWithWidgetPreference) {
        int i;
        String key = selectorWithWidgetPreference.getKey();
        int i2 = 0;
        while (true) {
            String[] strArr = this.mRadioKeys;
            i = 1;
            if (i2 >= strArr.length) {
                i2 = 1;
                break;
            } else if (TextUtils.equals(key, strArr[i2])) {
                break;
            } else {
                i2++;
            }
        }
        if (this.mCurrentIndex != i2) {
            this.mCurrentIndex = i2;
            setRadioStatus(i2);
            int i3 = this.mCurrentIndex;
            if (i3 == 0) {
                i = 2;
            } else if (i3 == 2) {
                i = 3;
            }
            if (this.mPackageManager.getIntentVerificationStatusAsUser(
                            this.mPackageName, this.mUserId)
                    == i) {
                return;
            }
            if (this.mPackageManager.updateIntentVerificationStatusAsUser(
                    this.mPackageName, i, this.mUserId)) {
                this.mPackageManager.getIntentVerificationStatusAsUser(
                        this.mPackageName, this.mUserId);
            } else {
                Log.e("OpenSupportedLinks", "Couldn't update intent verification status!");
            }
        }
    }

    @Override // com.android.settings.applications.AppInfoBase
    public final boolean refreshUi() {
        return true;
    }

    public final void setRadioStatus(int i) {
        this.mAllowOpening.setChecked(i == 0);
        this.mAskEveryTime.setChecked(i == 1);
        this.mNotAllowed.setChecked(i == 2);
    }
}
