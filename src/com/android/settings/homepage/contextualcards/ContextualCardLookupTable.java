package com.android.settings.homepage.contextualcards;

import com.android.settings.R;

import java.util.Comparator;
import java.util.Set;
import java.util.function.ToIntFunction;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ContextualCardLookupTable {
    static final Set<ControllerRendererMapping> LOOKUP_TABLE =
            Set.of(new ControllerRendererMapping());

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ControllerRendererMapping implements Comparable {
        @Override // java.lang.Comparable
        public final int compareTo(Object obj) {
            final int i = 0;
            final int i2 = 1;
            return Comparator.comparingInt(
                            new ToIntFunction() { // from class:
                                                  // com.android.settings.homepage.contextualcards.ContextualCardLookupTable$ControllerRendererMapping$$ExternalSyntheticLambda0
                                @Override // java.util.function.ToIntFunction
                                public final int applyAsInt(Object obj2) {
                                    ContextualCardLookupTable.ControllerRendererMapping
                                            controllerRendererMapping =
                                                    (ContextualCardLookupTable
                                                                    .ControllerRendererMapping)
                                                            obj2;
                                    switch (i) {
                                        case 0:
                                            controllerRendererMapping.getClass();
                                            return 2;
                                        default:
                                            controllerRendererMapping.getClass();
                                            return R.layout.sec_legacy_suggestion_tile;
                                    }
                                }
                            })
                    .thenComparingInt(
                            new ToIntFunction() { // from class:
                                                  // com.android.settings.homepage.contextualcards.ContextualCardLookupTable$ControllerRendererMapping$$ExternalSyntheticLambda0
                                @Override // java.util.function.ToIntFunction
                                public final int applyAsInt(Object obj2) {
                                    ContextualCardLookupTable.ControllerRendererMapping
                                            controllerRendererMapping =
                                                    (ContextualCardLookupTable
                                                                    .ControllerRendererMapping)
                                                            obj2;
                                    switch (i2) {
                                        case 0:
                                            controllerRendererMapping.getClass();
                                            return 2;
                                        default:
                                            controllerRendererMapping.getClass();
                                            return R.layout.sec_legacy_suggestion_tile;
                                    }
                                }
                            })
                    .compare(this, (ControllerRendererMapping) obj);
        }
    }
}
