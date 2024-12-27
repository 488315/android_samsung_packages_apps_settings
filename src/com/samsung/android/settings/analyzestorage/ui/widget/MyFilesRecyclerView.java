package com.samsung.android.settings.analyzestorage.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.recyclerview.widget.RecyclerView;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.presenter.keyboardmouse.KeyMouseCommand;
import com.samsung.android.settings.analyzestorage.presenter.keyboardmouse.KeyboardMouseManager;
import com.samsung.android.settings.analyzestorage.presenter.keyboardmouse.KeyboardMouseManager$$ExternalSyntheticLambda0;
import com.samsung.android.settings.analyzestorage.presenter.utils.ConfigurationUtils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import java.util.Optional;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000$\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002:\u0001\r"
                + "B\u0011\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0004\b\u0005\u0010\u0006B\u001b\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0007¢\u0006\u0004\b\u0005\u0010"
                + "\tB#\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\u000b\u001a\u00020\n"
                + "¢\u0006\u0004\b\u0005\u0010\f¨\u0006\u000e"
        },
        d2 = {
            "Lcom/samsung/android/settings/analyzestorage/ui/widget/MyFilesRecyclerView;",
            "Landroidx/recyclerview/widget/RecyclerView;",
            "Lcom/samsung/android/settings/analyzestorage/presenter/keyboardmouse/KeyMouseCommand$IMouseScroll;",
            "Landroid/content/Context;",
            "context",
            "<init>",
            "(Landroid/content/Context;)V",
            "Landroid/util/AttributeSet;",
            "attrs",
            "(Landroid/content/Context;Landroid/util/AttributeSet;)V",
            ApnSettings.MVNO_NONE,
            "defStyleAttr",
            "(Landroid/content/Context;Landroid/util/AttributeSet;I)V",
            "GestureListener",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes4.dex */
public final class MyFilesRecyclerView extends RecyclerView
        implements KeyMouseCommand.IMouseScroll {
    public final GestureDetector gestureDetector;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        public GestureListener() {}

        @Override // android.view.GestureDetector.SimpleOnGestureListener,
                  // android.view.GestureDetector.OnDoubleTapListener
        public final boolean onDoubleTap(MotionEvent event) {
            Intrinsics.checkNotNullParameter(event, "event");
            MyFilesRecyclerView.this.mIsCtrlKeyPressed = false;
            Log.d("DoubleTap", "Tapped at : (" + event.getX() + ", " + event.getY() + ")");
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener,
                  // android.view.GestureDetector.OnGestureListener
        public final boolean onDown(MotionEvent event) {
            Intrinsics.checkNotNullParameter(event, "event");
            if (MyFilesRecyclerView.this.findChildViewUnder(event.getX(), event.getY()) == null
                    || KeyboardMouseManager.sPressedKey.get(2)) {
                return true;
            }
            MyFilesRecyclerView.this.mIsCtrlKeyPressed = false;
            return true;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MyFilesRecyclerView(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        setVerticalScrollbarPosition(ConfigurationUtils.isInRTLMode(getContext()) ? 1 : 2);
        this.gestureDetector = new GestureDetector(getContext(), new GestureListener());
        setImportantForAccessibility(2);
    }

    @Override // android.view.View
    public final boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        boolean z;
        if (motionEvent != null
                && motionEvent.getAction() == 8
                && KeyboardMouseManager.sPressedKey.get(2)) {
            KeyMouseCommand keyMouseCommand =
                    (KeyMouseCommand) KeyboardMouseManager.sKeyMouseCommandMap.get(2);
            KeyboardMouseManager.sKeyMouseCommand = keyMouseCommand;
            z =
                    ((KeyMouseCommand)
                                    Optional.ofNullable(keyMouseCommand)
                                            .orElseGet(
                                                    new KeyboardMouseManager$$ExternalSyntheticLambda0(
                                                            0)))
                            .onMouseScroll(this);
        } else {
            z = false;
        }
        return z || super.dispatchGenericMotionEvent(motionEvent);
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup,
              // android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (motionEvent != null) {
            SparseArray sparseArray = KeyboardMouseManager.sEventContextMap;
            if (motionEvent.getAction() == 0 && motionEvent.getButtonState() == 2) {
                float x = (int) motionEvent.getX();
                float y = (int) motionEvent.getY();
                RecyclerView.getChildAdapterPosition(findChildViewUnder(x, y));
                showContextMenu(x, y);
            }
            GestureDetector gestureDetector = this.gestureDetector;
            if (gestureDetector != null) {
                gestureDetector.onTouchEvent(motionEvent);
            }
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MyFilesRecyclerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        setVerticalScrollbarPosition(ConfigurationUtils.isInRTLMode(getContext()) ? 1 : 2);
        this.gestureDetector = new GestureDetector(getContext(), new GestureListener());
        setImportantForAccessibility(2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MyFilesRecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        setVerticalScrollbarPosition(ConfigurationUtils.isInRTLMode(getContext()) ? 1 : 2);
        this.gestureDetector = new GestureDetector(getContext(), new GestureListener());
        setImportantForAccessibility(2);
    }
}
