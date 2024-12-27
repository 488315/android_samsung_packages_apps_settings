package com.android.settings.homepage.contextualcards;

import android.content.Context;
import android.util.Log;

import androidx.collection.ArraySet;
import androidx.collection.ArraySet.ElementIterator;
import androidx.lifecycle.LifecycleOwner;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.homepage.contextualcards.conditional.ConditionContextualCardController;
import com.android.settings.homepage.contextualcards.conditional.ConditionContextualCardRenderer;
import com.android.settings.homepage.contextualcards.conditional.ConditionFooterContextualCardRenderer;
import com.android.settings.homepage.contextualcards.conditional.ConditionHeaderContextualCardRenderer;
import com.android.settings.homepage.contextualcards.legacysuggestion.LegacySuggestionContextualCardController;
import com.android.settings.homepage.contextualcards.legacysuggestion.LegacySuggestionContextualCardRenderer;
import com.android.settings.homepage.contextualcards.slices.SliceContextualCardController;
import com.android.settings.homepage.contextualcards.slices.SliceContextualCardRenderer;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ControllerRendererPool {
    public final ArraySet mControllers = new ArraySet(0);
    public final ArraySet mRenderers = new ArraySet(0);

    public final ContextualCardController getController(Context context, int i) {
        ContextualCardController contextualCardController;
        Class cls;
        Iterator<ContextualCardLookupTable.ControllerRendererMapping> it =
                ContextualCardLookupTable.LOOKUP_TABLE.iterator();
        while (true) {
            contextualCardController = null;
            if (!it.hasNext()) {
                cls = null;
                break;
            }
            it.next().getClass();
            if (2 == i) {
                cls = LegacySuggestionContextualCardController.class;
                break;
            }
        }
        ArraySet arraySet = this.mControllers;
        arraySet.getClass();
        ArraySet.ElementIterator elementIterator = arraySet.new ElementIterator();
        while (elementIterator.hasNext()) {
            ContextualCardController contextualCardController2 =
                    (ContextualCardController) elementIterator.next();
            if (contextualCardController2.getClass().getName().equals(cls.getName())) {
                Log.d("ControllerRendererPool", "Controller is already there.");
                return contextualCardController2;
            }
        }
        if (ConditionContextualCardController.class == cls) {
            contextualCardController = new ConditionContextualCardController(context);
        } else if (SliceContextualCardController.class == cls) {
            contextualCardController = new SliceContextualCardController(context);
        } else if (LegacySuggestionContextualCardController.class == cls) {
            contextualCardController = new LegacySuggestionContextualCardController(context);
        }
        if (contextualCardController != null) {
            arraySet.add(contextualCardController);
        }
        return contextualCardController;
    }

    @VisibleForTesting
    public Set<ContextualCardController> getControllers() {
        return this.mControllers;
    }

    public final ContextualCardRenderer getRendererByViewType(
            Context context, LifecycleOwner lifecycleOwner, final int i) {
        Class<?> cls;
        List list =
                (List)
                        ContextualCardLookupTable.LOOKUP_TABLE.stream()
                                .filter(
                                        new Predicate() { // from class:
                                                          // com.android.settings.homepage.contextualcards.ContextualCardLookupTable$$ExternalSyntheticLambda0
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                int i2 = i;
                                                ((ContextualCardLookupTable
                                                                        .ControllerRendererMapping)
                                                                obj)
                                                        .getClass();
                                                return R.layout.sec_legacy_suggestion_tile == i2;
                                            }
                                        })
                                .collect(Collectors.toList());
        ContextualCardRenderer contextualCardRenderer = null;
        if (list == null || list.isEmpty()) {
            Log.w("ContextualCardLookup", "No matching mapping");
            cls = null;
        } else {
            if (list.size() != 1) {
                throw new IllegalStateException("Have duplicate VIEW_TYPE in lookup table.");
            }
            ((ContextualCardLookupTable.ControllerRendererMapping) list.get(0)).getClass();
            cls = LegacySuggestionContextualCardRenderer.class;
        }
        ArraySet arraySet = this.mRenderers;
        arraySet.getClass();
        ArraySet.ElementIterator elementIterator = arraySet.new ElementIterator();
        while (elementIterator.hasNext()) {
            ContextualCardRenderer contextualCardRenderer2 =
                    (ContextualCardRenderer) elementIterator.next();
            if (contextualCardRenderer2.getClass() == cls) {
                Log.d("ControllerRendererPool", "Renderer is already there.");
                return contextualCardRenderer2;
            }
        }
        if (ConditionContextualCardRenderer.class == cls) {
            contextualCardRenderer = new ConditionContextualCardRenderer(context, this);
        } else if (SliceContextualCardRenderer.class == cls) {
            contextualCardRenderer = new SliceContextualCardRenderer(context, lifecycleOwner, this);
        } else if (LegacySuggestionContextualCardRenderer.class == cls) {
            contextualCardRenderer = new LegacySuggestionContextualCardRenderer(context, this);
        } else if (ConditionFooterContextualCardRenderer.class == cls) {
            contextualCardRenderer = new ConditionFooterContextualCardRenderer(context, this);
        } else if (ConditionHeaderContextualCardRenderer.class == cls) {
            contextualCardRenderer = new ConditionHeaderContextualCardRenderer(context, this);
        }
        if (contextualCardRenderer != null) {
            arraySet.add(contextualCardRenderer);
        }
        return contextualCardRenderer;
    }

    @VisibleForTesting
    public Set<ContextualCardRenderer> getRenderers() {
        return this.mRenderers;
    }
}
