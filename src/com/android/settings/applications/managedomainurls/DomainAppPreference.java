package com.android.settings.applications.managedomainurls;

import android.content.Context;
import android.content.pm.verify.domain.DomainVerificationManager;
import android.content.pm.verify.domain.DomainVerificationUserState;
import android.graphics.drawable.Drawable;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.applications.intentpicker.IntentPickerUtils;
import com.android.settingslib.applications.AppUtils;
import com.android.settingslib.applications.ApplicationsState;
import com.android.settingslib.utils.ThreadUtils;
import com.android.settingslib.widget.AppPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DomainAppPreference extends AppPreference {
    public Drawable mCacheIcon;
    public final DomainVerificationManager mDomainVerificationManager;
    public final ApplicationsState.AppEntry mEntry;

    public DomainAppPreference(Context context, ApplicationsState.AppEntry appEntry) {
        super(context);
        this.mDomainVerificationManager =
                (DomainVerificationManager)
                        context.getSystemService(DomainVerificationManager.class);
        this.mEntry = appEntry;
        appEntry.ensureLabel(getContext());
        this.mCacheIcon = AppUtils.getIconFromCache(appEntry);
        setState$2();
    }

    @Override // com.android.settingslib.widget.AppPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        if (this.mCacheIcon == null) {
            ThreadUtils.postOnBackgroundThread(
                    new Runnable() { // from class:
                                     // com.android.settings.applications.managedomainurls.DomainAppPreference$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            final DomainAppPreference domainAppPreference =
                                    DomainAppPreference.this;
                            final Drawable icon =
                                    AppUtils.getIcon(
                                            domainAppPreference.getContext(),
                                            domainAppPreference.mEntry);
                            ThreadUtils.postOnMainThread(
                                    new Runnable() { // from class:
                                                     // com.android.settings.applications.managedomainurls.DomainAppPreference$$ExternalSyntheticLambda1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            DomainAppPreference domainAppPreference2 =
                                                    DomainAppPreference.this;
                                            Drawable drawable = icon;
                                            domainAppPreference2.setIcon(drawable);
                                            domainAppPreference2.mCacheIcon = drawable;
                                        }
                                    });
                        }
                    });
        }
        super.onBindViewHolder(preferenceViewHolder);
    }

    public final void setState$2() {
        setTitle(this.mEntry.label);
        Drawable drawable = this.mCacheIcon;
        if (drawable != null) {
            setIcon(drawable);
        } else {
            setIcon(R.drawable.empty_icon);
        }
        String str = this.mEntry.info.packageName;
        Context context = getContext();
        DomainVerificationUserState domainVerificationUserState =
                IntentPickerUtils.getDomainVerificationUserState(
                        this.mDomainVerificationManager, str);
        setSummary(
                context.getText(
                        domainVerificationUserState == null
                                ? false
                                : domainVerificationUserState.isLinkHandlingAllowed()
                                        ? R.string.app_link_open_always
                                        : R.string.app_link_open_never));
    }
}
