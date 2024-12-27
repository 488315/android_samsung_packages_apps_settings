package com.android.settings.applications.intentpicker;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.verify.domain.DomainOwner;
import android.text.TextUtils;
import android.util.Log;

import androidx.compose.runtime.PrioritySet$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;

import java.util.List;
import java.util.SortedSet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SupportedLinkWrapper implements Comparable {
    public String mHost;
    public boolean mIsChecked;
    public boolean mIsEnabled;
    public String mLastOwnerName;
    public SortedSet mOwnerSet;

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        SupportedLinkWrapper supportedLinkWrapper = (SupportedLinkWrapper) obj;
        boolean z = this.mIsEnabled;
        if (z != supportedLinkWrapper.mIsEnabled) {
            if (!z) {
                return 1;
            }
        } else {
            if (TextUtils.isEmpty(this.mLastOwnerName)
                    == TextUtils.isEmpty(supportedLinkWrapper.mLastOwnerName)) {
                return 0;
            }
            if (!TextUtils.isEmpty(this.mLastOwnerName)) {
                return 1;
            }
        }
        return -1;
    }

    public final String getLastPackageLabel(
            final FragmentActivity fragmentActivity, final boolean z) {
        return (String)
                PrioritySet$$ExternalSyntheticOutline0.m(
                        1,
                        (List)
                                this.mOwnerSet.stream()
                                        .filter(
                                                new Predicate() { // from class:
                                                                  // com.android.settings.applications.intentpicker.SupportedLinkWrapper$$ExternalSyntheticLambda1
                                                    @Override // java.util.function.Predicate
                                                    public final boolean test(Object obj) {
                                                        return ((DomainOwner) obj).isOverrideable()
                                                                == z;
                                                    }
                                                })
                                        .map(
                                                new Function() { // from class:
                                                                 // com.android.settings.applications.intentpicker.SupportedLinkWrapper$$ExternalSyntheticLambda2
                                                    @Override // java.util.function.Function
                                                    public final Object apply(Object obj) {
                                                        SupportedLinkWrapper supportedLinkWrapper =
                                                                SupportedLinkWrapper.this;
                                                        Context context = fragmentActivity;
                                                        supportedLinkWrapper.getClass();
                                                        String packageName =
                                                                ((DomainOwner) obj)
                                                                        .getPackageName();
                                                        try {
                                                            PackageManager packageManager =
                                                                    context.getPackageManager();
                                                            return packageManager
                                                                    .getApplicationInfo(
                                                                            packageName, 0)
                                                                    .loadLabel(packageManager)
                                                                    .toString();
                                                        } catch (
                                                                PackageManager.NameNotFoundException
                                                                        e) {
                                                            Log.w(
                                                                    "SupportedLinkWrapper",
                                                                    "getLabel error : "
                                                                            + e.getMessage());
                                                            return null;
                                                        }
                                                    }
                                                })
                                        .filter(
                                                new SupportedLinkWrapper$$ExternalSyntheticLambda0(
                                                        1))
                                        .collect(Collectors.toList()));
    }
}
