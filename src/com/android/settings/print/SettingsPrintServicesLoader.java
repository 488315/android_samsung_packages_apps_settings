package com.android.settings.print;

import android.content.Context;
import android.print.PrintManager;
import android.print.PrintServicesLoader;

import androidx.loader.content.Loader;

import com.android.internal.util.Preconditions;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SettingsPrintServicesLoader extends Loader {
    public final AnonymousClass1 mLoader;

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.settings.print.SettingsPrintServicesLoader$1] */
    public SettingsPrintServicesLoader(PrintManager printManager, Context context) {
        super((Context) Preconditions.checkNotNull(context));
        this.mLoader =
                new PrintServicesLoader(
                        printManager,
                        context) { // from class:
                                   // com.android.settings.print.SettingsPrintServicesLoader.1
                    public final void deliverResult(Object obj) {
                        List list = (List) obj;
                        super.deliverResult(list);
                        SettingsPrintServicesLoader.this.deliverResult(list);
                    }
                };
    }

    @Override // androidx.loader.content.Loader
    public final void onAbandon() {
        abandon();
    }

    @Override // androidx.loader.content.Loader
    public final boolean onCancelLoad() {
        return cancelLoad();
    }

    @Override // androidx.loader.content.Loader
    public final void onForceLoad() {
        forceLoad();
    }

    @Override // androidx.loader.content.Loader
    public final void onReset() {
        reset();
    }

    @Override // androidx.loader.content.Loader
    public final void onStartLoading() {
        startLoading();
    }

    @Override // androidx.loader.content.Loader
    public final void onStopLoading() {
        stopLoading();
    }
}
