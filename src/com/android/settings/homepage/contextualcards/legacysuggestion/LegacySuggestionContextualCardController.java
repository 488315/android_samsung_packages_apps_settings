package com.android.settings.homepage.contextualcards.legacysuggestion;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.os.Process;
import android.os.RemoteException;
import android.service.settings.suggestions.ISuggestionService;
import android.service.settings.suggestions.Suggestion;
import android.util.ArrayMap;
import android.util.Log;

import com.android.settings.MainClear$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.homepage.contextualcards.ContextualCard;
import com.android.settings.homepage.contextualcards.ContextualCardController;
import com.android.settings.homepage.contextualcards.ContextualCardUpdateListener;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import com.android.settingslib.suggestions.SuggestionController;
import com.android.settingslib.utils.ThreadUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class LegacySuggestionContextualCardController
        implements ContextualCardController, LifecycleObserver, OnStart, OnStop {
    public ContextualCardUpdateListener mCardUpdateListener;
    public final Context mContext;
    SuggestionController mSuggestionController;
    public Map mSuggestionCards = new ArrayMap();
    final List<ContextualCard> mSuggestions = new ArrayList();

    public LegacySuggestionContextualCardController(Context context) {
        this.mContext = context;
        if (!context.getResources().getBoolean(R.bool.config_use_legacy_suggestion)) {
            Log.w("LegacySuggestCardCtrl", "Legacy suggestion contextual card disabled, skipping.");
            return;
        }
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getSuggestionFeatureProvider().getClass();
        this.mSuggestionController =
                new SuggestionController(
                        context,
                        new ComponentName(
                                "com.android.settings.intelligence",
                                "com.android.settings.intelligence.suggestions.SuggestionService"),
                        this);
    }

    @Override // com.android.settings.homepage.contextualcards.ContextualCardController
    public final void onActionClick(ContextualCard contextualCard) {
        List<ContextualCard> list = (List) ((ArrayMap) this.mSuggestionCards).get(2);
        for (ContextualCard contextualCard2 : list) {
            if (contextualCard2.mName.equals(contextualCard.mName)) {
                list.remove(contextualCard2);
                ThreadUtils.postOnMainThread(
                        new LegacySuggestionContextualCardController$$ExternalSyntheticLambda0(
                                this, 2));
                return;
            }
        }
    }

    @Override // com.android.settings.homepage.contextualcards.ContextualCardController
    public final void onDismissed(ContextualCard contextualCard) {
        SuggestionController suggestionController = this.mSuggestionController;
        Suggestion suggestion = ((LegacySuggestionContextualCard) contextualCard).mSuggestion;
        ISuggestionService iSuggestionService = suggestionController.mRemoteService;
        if (iSuggestionService != null) {
            try {
                iSuggestionService.dismissSuggestion(suggestion);
            } catch (RemoteException | RuntimeException e) {
                Log.w("SuggestionController", "Error when calling dismissSuggestion()", e);
            }
        } else {
            Log.w(
                    "SuggestionController",
                    "SuggestionController not ready, cannot dismiss " + suggestion.getId());
        }
        this.mSuggestions.remove(contextualCard);
        ArrayMap arrayMap = new ArrayMap();
        this.mSuggestionCards = arrayMap;
        arrayMap.put(2, this.mSuggestions);
        ThreadUtils.postOnMainThread(
                new LegacySuggestionContextualCardController$$ExternalSyntheticLambda0(this, 1));
    }

    @Override // com.android.settings.homepage.contextualcards.ContextualCardController
    public final void onPrimaryClick(ContextualCard contextualCard) {
        try {
            ((LegacySuggestionContextualCard) contextualCard).mPendingIntent.send();
        } catch (PendingIntent.CanceledException unused) {
            MainClear$$ExternalSyntheticOutline0.m$1(
                    new StringBuilder("Failed to start suggestion "),
                    contextualCard.mTitleText,
                    "LegacySuggestCardCtrl");
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public final void onStart() {
        SuggestionController suggestionController = this.mSuggestionController;
        if (suggestionController == null) {
            return;
        }
        suggestionController.mContext.bindServiceAsUser(
                suggestionController.mServiceIntent,
                suggestionController.mServiceConnection,
                1,
                Process.myUserHandle());
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public final void onStop() {
        SuggestionController suggestionController = this.mSuggestionController;
        if (suggestionController == null || suggestionController.mRemoteService == null) {
            return;
        }
        suggestionController.mRemoteService = null;
        suggestionController.mContext.unbindService(suggestionController.mServiceConnection);
    }

    @Override // com.android.settings.homepage.contextualcards.ContextualCardController
    public final void setCardUpdateListener(
            ContextualCardUpdateListener contextualCardUpdateListener) {
        this.mCardUpdateListener = contextualCardUpdateListener;
    }
}
