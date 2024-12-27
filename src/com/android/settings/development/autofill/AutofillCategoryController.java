package com.android.settings.development.autofill;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.autofill.AutofillManager;

import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AutofillCategoryController extends DeveloperOptionsPreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    public final ContentResolver mContentResolver;
    public final Handler mHandler;
    public final AnonymousClass1 mSettingsObserver;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.development.autofill.AutofillCategoryController$1, reason: invalid class name */
    public final class AnonymousClass1 extends ContentObserver {
        public AnonymousClass1(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri, int i) {
            AutofillCategoryController.this.mHandler.postDelayed(
                    new Runnable() { // from class:
                                     // com.android.settings.development.autofill.AutofillCategoryController$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            AutofillCategoryController autofillCategoryController =
                                    AutofillCategoryController.this;
                            autofillCategoryController.mPreference.notifyDependencyChange(
                                    AutofillCategoryController.m824$$Nest$mshouldDisableDependents(
                                            autofillCategoryController));
                        }
                    },
                    2000L);
        }
    }

    /* renamed from: -$$Nest$mshouldDisableDependents, reason: not valid java name */
    public static boolean m824$$Nest$mshouldDisableDependents(
            AutofillCategoryController autofillCategoryController) {
        AutofillManager autofillManager =
                (AutofillManager)
                        autofillCategoryController.mContext.getSystemService(AutofillManager.class);
        boolean z = autofillManager != null && autofillManager.isEnabled();
        Log.v("AutofillCategoryController", "isAutofillEnabled(): " + z);
        boolean z2 = z ^ true;
        Log.v("AutofillCategoryController", "shouldDisableDependents(): " + z2);
        return z2;
    }

    public AutofillCategoryController(Context context, Lifecycle lifecycle) {
        super(context);
        Handler handler = new Handler(Looper.getMainLooper());
        this.mHandler = handler;
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
        this.mSettingsObserver = new AnonymousClass1(handler);
        this.mContentResolver = context.getContentResolver();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "debug_autofill_category";
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public final void onStart() {
        this.mContentResolver.registerContentObserver(
                Settings.Secure.getUriFor("autofill_service"), false, this.mSettingsObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public final void onStop() {
        this.mContentResolver.unregisterContentObserver(this.mSettingsObserver);
    }
}
