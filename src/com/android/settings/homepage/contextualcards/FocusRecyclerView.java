package com.android.settings.homepage.contextualcards;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.homepage.contextualcards.conditional.ConditionalCardController;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FocusRecyclerView extends RecyclerView {
    public FocusListener mListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface FocusListener {}

    public FocusRecyclerView(Context context) {
        super(context);
    }

    @Override // android.view.View
    public final void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        FocusListener focusListener = this.mListener;
        if (focusListener != null) {
            ContextualCardManager contextualCardManager =
                    ((ContextualCardsFragment) focusListener).mContextualCardManager;
            contextualCardManager.getClass();
            Iterator it = new ArrayList(contextualCardManager.mContextualCards).iterator();
            while (it.hasNext()) {
                ContextualCardController controller =
                        contextualCardManager.mControllerRendererPool.getController(
                                contextualCardManager.mContext,
                                ((ContextualCard) it.next()).getCardType());
                boolean z2 = controller instanceof ConditionalCardController;
                if (z && (controller instanceof OnStart)) {
                    ((OnStart) controller).onStart();
                }
                if (!z && (controller instanceof OnStop)) {
                    ((OnStop) controller).onStop();
                }
            }
        }
    }

    public void setListener(FocusListener focusListener) {
        this.mListener = focusListener;
    }

    public FocusRecyclerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
