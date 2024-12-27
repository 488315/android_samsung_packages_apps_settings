package com.android.settings.homepage.contextualcards.legacysuggestion;

import android.os.RemoteException;
import android.service.settings.suggestions.ISuggestionService;
import android.service.settings.suggestions.Suggestion;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import com.android.settings.R;
import com.android.settingslib.suggestions.SuggestionController;
import com.android.settingslib.utils.ThreadUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */
class LegacySuggestionContextualCardController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LegacySuggestionContextualCardController f$0;

    public /* synthetic */ LegacySuggestionContextualCardController$$ExternalSyntheticLambda0(
            LegacySuggestionContextualCardController legacySuggestionContextualCardController,
            int i) {
        this.$r8$classId = i;
        this.f$0 = legacySuggestionContextualCardController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        LegacySuggestionContextualCardController legacySuggestionContextualCardController =
                this.f$0;
        switch (i) {
            case 0:
                SuggestionController suggestionController =
                        legacySuggestionContextualCardController.mSuggestionController;
                if (suggestionController != null
                        && legacySuggestionContextualCardController.mCardUpdateListener != null) {
                    ISuggestionService iSuggestionService = suggestionController.mRemoteService;
                    List<Suggestion> list = null;
                    if (iSuggestionService != null) {
                        try {
                            list = iSuggestionService.getSuggestions();
                        } catch (RemoteException | RuntimeException e) {
                            Log.w("SuggestionController", "Error when calling getSuggestion()", e);
                        } catch (NullPointerException e2) {
                            Log.w(
                                    "SuggestionController",
                                    "mRemote service detached before able to query",
                                    e2);
                        }
                    }
                    Log.d(
                            "LegacySuggestCardCtrl",
                            "Loaded suggests: "
                                    + (list == null ? "null" : String.valueOf(list.size())));
                    ArrayList arrayList = new ArrayList();
                    if (list != null) {
                        for (Suggestion suggestion : list) {
                            LegacySuggestionContextualCard.Builder builder =
                                    new LegacySuggestionContextualCard.Builder();
                            if (suggestion.getIcon() != null) {
                                builder.mIconDrawable =
                                        suggestion
                                                .getIcon()
                                                .loadDrawable(
                                                        legacySuggestionContextualCardController
                                                                .mContext);
                            }
                            if (!TextUtils.isEmpty(suggestion.getTitle())) {
                                builder.mTitleText = suggestion.getTitle().toString();
                            }
                            if (!TextUtils.isEmpty(suggestion.getSummary())) {
                                builder.mSummaryText = suggestion.getSummary().toString();
                            }
                            builder.mPendingIntent = suggestion.getPendingIntent();
                            builder.mSuggestion = suggestion;
                            builder.mName = suggestion.getId();
                            builder.mViewType = R.layout.sec_legacy_suggestion_tile;
                            arrayList.add(new LegacySuggestionContextualCard(builder));
                        }
                    }
                    legacySuggestionContextualCardController.mSuggestions.clear();
                    legacySuggestionContextualCardController.mSuggestions.addAll(arrayList);
                    ArrayMap arrayMap = new ArrayMap();
                    legacySuggestionContextualCardController.mSuggestionCards = arrayMap;
                    arrayMap.put(2, legacySuggestionContextualCardController.mSuggestions);
                    ThreadUtils.postOnMainThread(
                            new LegacySuggestionContextualCardController$$ExternalSyntheticLambda0(
                                    legacySuggestionContextualCardController, 1));
                    break;
                }
                break;
            case 1:
                legacySuggestionContextualCardController.mCardUpdateListener
                        .onContextualCardUpdated(
                                legacySuggestionContextualCardController.mSuggestionCards);
                break;
            default:
                legacySuggestionContextualCardController.mCardUpdateListener
                        .onContextualCardUpdated(
                                legacySuggestionContextualCardController.mSuggestionCards);
                break;
        }
    }
}
