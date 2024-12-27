package com.android.settingslib.suggestions;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.service.settings.suggestions.ISuggestionService;

import com.android.settings.homepage.contextualcards.legacysuggestion.LegacySuggestionContextualCardController;
import com.android.settings.homepage.contextualcards.legacysuggestion.LegacySuggestionContextualCardController$$ExternalSyntheticLambda0;
import com.android.settingslib.utils.ThreadUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SuggestionController {
    public final LegacySuggestionContextualCardController mConnectionListener;
    public final Context mContext;
    public ISuggestionService mRemoteService;
    public final AnonymousClass1 mServiceConnection =
            new ServiceConnection() { // from class:
                                      // com.android.settingslib.suggestions.SuggestionController.1
                @Override // android.content.ServiceConnection
                public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    SuggestionController.this.mRemoteService =
                            ISuggestionService.Stub.asInterface(iBinder);
                    LegacySuggestionContextualCardController
                            legacySuggestionContextualCardController =
                                    SuggestionController.this.mConnectionListener;
                    if (legacySuggestionContextualCardController != null) {
                        legacySuggestionContextualCardController.getClass();
                        ThreadUtils.postOnBackgroundThread(
                                new LegacySuggestionContextualCardController$$ExternalSyntheticLambda0(
                                        legacySuggestionContextualCardController, 0));
                    }
                }

                @Override // android.content.ServiceConnection
                public final void onServiceDisconnected(ComponentName componentName) {
                    SuggestionController suggestionController = SuggestionController.this;
                    LegacySuggestionContextualCardController
                            legacySuggestionContextualCardController =
                                    suggestionController.mConnectionListener;
                    if (legacySuggestionContextualCardController != null) {
                        suggestionController.mRemoteService = null;
                        legacySuggestionContextualCardController.getClass();
                    }
                }
            };
    public final Intent mServiceIntent;

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.settingslib.suggestions.SuggestionController$1] */
    public SuggestionController(
            Context context,
            ComponentName componentName,
            LegacySuggestionContextualCardController legacySuggestionContextualCardController) {
        this.mContext = context.getApplicationContext();
        this.mConnectionListener = legacySuggestionContextualCardController;
        this.mServiceIntent = new Intent().setComponent(componentName);
    }
}
