package com.google.android.material.bottomsheet;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.window.BackEvent;
import android.window.OnBackAnimationCallback;
import android.window.OnBackInvokedDispatcher;

import androidx.activity.BackEventCompat;
import androidx.appcompat.app.AppCompatDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.SoftwareKeyboardControllerCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import com.android.settings.R;

import com.google.android.material.color.MaterialColors;
import com.google.android.material.drawable.DrawableUtils;
import com.google.android.material.motion.MaterialBackOrchestrator;
import com.google.android.material.shape.MaterialShapeDrawable;

import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class BottomSheetDialog extends AppCompatDialog {
    public MaterialBackOrchestrator backOrchestrator;
    public BottomSheetBehavior behavior;
    public FrameLayout bottomSheet;
    public AnonymousClass5 bottomSheetCallback;
    public boolean cancelable;
    public boolean canceledOnTouchOutside;
    public boolean canceledOnTouchOutsideSet;
    public FrameLayout container;
    public CoordinatorLayout coordinator;
    public EdgeToEdgeCallback edgeToEdgeCallback;
    public boolean edgeToEdgeEnabled;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.google.android.material.bottomsheet.BottomSheetDialog$4, reason: invalid class name */
    public final class AnonymousClass4 implements View.OnTouchListener {
        @Override // android.view.View.OnTouchListener
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class EdgeToEdgeCallback extends BottomSheetBehavior.BottomSheetCallback {
        public final WindowInsetsCompat insetsCompat;
        public final Boolean lightBottomSheet;
        public boolean lightStatusBar;
        public Window window;

        public EdgeToEdgeCallback(View view, WindowInsetsCompat windowInsetsCompat) {
            ColorStateList backgroundTintList;
            this.insetsCompat = windowInsetsCompat;
            MaterialShapeDrawable materialShapeDrawable =
                    BottomSheetBehavior.from(view).materialShapeDrawable;
            if (materialShapeDrawable != null) {
                backgroundTintList = materialShapeDrawable.drawableState.fillColor;
            } else {
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                backgroundTintList = ViewCompat.Api21Impl.getBackgroundTintList(view);
            }
            if (backgroundTintList != null) {
                this.lightBottomSheet =
                        Boolean.valueOf(
                                MaterialColors.isColorLight(backgroundTintList.getDefaultColor()));
                return;
            }
            ColorStateList colorStateListOrNull =
                    DrawableUtils.getColorStateListOrNull(view.getBackground());
            Integer valueOf =
                    colorStateListOrNull != null
                            ? Integer.valueOf(colorStateListOrNull.getDefaultColor())
                            : null;
            if (valueOf != null) {
                this.lightBottomSheet =
                        Boolean.valueOf(MaterialColors.isColorLight(valueOf.intValue()));
            } else {
                this.lightBottomSheet = null;
            }
        }

        @Override // com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
        public final void onLayout(View view) {
            setPaddingForPosition(view);
        }

        @Override // com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
        public final void onSlide(View view) {
            setPaddingForPosition(view);
        }

        @Override // com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
        public final void onStateChanged(View view, int i) {
            setPaddingForPosition(view);
        }

        public final void setPaddingForPosition(View view) {
            int top = view.getTop();
            WindowInsetsCompat windowInsetsCompat = this.insetsCompat;
            if (top < windowInsetsCompat.getSystemWindowInsetTop()) {
                Window window = this.window;
                if (window != null) {
                    Boolean bool = this.lightBottomSheet;
                    new WindowInsetsControllerCompat(window, window.getDecorView())
                            .setAppearanceLightStatusBars(
                                    bool == null ? this.lightStatusBar : bool.booleanValue());
                }
                view.setPadding(
                        view.getPaddingLeft(),
                        windowInsetsCompat.getSystemWindowInsetTop() - view.getTop(),
                        view.getPaddingRight(),
                        view.getPaddingBottom());
                return;
            }
            if (view.getTop() != 0) {
                Window window2 = this.window;
                if (window2 != null) {
                    new WindowInsetsControllerCompat(window2, window2.getDecorView())
                            .setAppearanceLightStatusBars(this.lightStatusBar);
                }
                view.setPadding(
                        view.getPaddingLeft(), 0, view.getPaddingRight(), view.getPaddingBottom());
            }
        }

        public final void setWindow(Window window) {
            if (this.window == window) {
                return;
            }
            this.window = window;
            if (window != null) {
                WindowInsetsControllerCompat.Impl30 impl30 =
                        new WindowInsetsControllerCompat.Impl30(
                                window.getInsetsController(),
                                new SoftwareKeyboardControllerCompat(window.getDecorView()));
                impl30.mWindow = window;
                impl30.mInsetsController.setSystemBarsAppearance(0, 0);
                this.lightStatusBar = (impl30.mInsetsController.getSystemBarsAppearance() & 8) != 0;
            }
        }
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public final void cancel() {
        if (this.behavior == null) {
            ensureContainerAndBehavior();
        }
        super.cancel();
    }

    public final void ensureContainerAndBehavior() {
        if (this.container == null) {
            FrameLayout frameLayout =
                    (FrameLayout)
                            View.inflate(getContext(), R.layout.design_bottom_sheet_dialog, null);
            this.container = frameLayout;
            this.coordinator = (CoordinatorLayout) frameLayout.findViewById(R.id.coordinator);
            FrameLayout frameLayout2 =
                    (FrameLayout) this.container.findViewById(R.id.design_bottom_sheet);
            this.bottomSheet = frameLayout2;
            BottomSheetBehavior from = BottomSheetBehavior.from(frameLayout2);
            this.behavior = from;
            AnonymousClass5 anonymousClass5 = this.bottomSheetCallback;
            if (!from.callbacks.contains(anonymousClass5)) {
                from.callbacks.add(anonymousClass5);
            }
            this.behavior.setHideable(this.cancelable);
            this.backOrchestrator = new MaterialBackOrchestrator(this.behavior, this.bottomSheet);
        }
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        if (window != null) {
            boolean z = this.edgeToEdgeEnabled && Color.alpha(window.getNavigationBarColor()) < 255;
            FrameLayout frameLayout = this.container;
            if (frameLayout != null) {
                frameLayout.setFitsSystemWindows(!z);
            }
            CoordinatorLayout coordinatorLayout = this.coordinator;
            if (coordinatorLayout != null) {
                coordinatorLayout.setFitsSystemWindows(!z);
            }
            window.setDecorFitsSystemWindows(!z);
            EdgeToEdgeCallback edgeToEdgeCallback = this.edgeToEdgeCallback;
            if (edgeToEdgeCallback != null) {
                edgeToEdgeCallback.setWindow(window);
            }
        }
        updateListeningForBackCallbacks();
    }

    @Override // androidx.appcompat.app.AppCompatDialog, androidx.activity.ComponentDialog,
              // android.app.Dialog
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Window window = getWindow();
        if (window != null) {
            window.setStatusBarColor(0);
            window.addFlags(Integer.MIN_VALUE);
            window.setLayout(-1, -1);
        }
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public final void onDetachedFromWindow() {
        MaterialBackOrchestrator.Api34BackCallbackDelegate api34BackCallbackDelegate;
        OnBackInvokedDispatcher findOnBackInvokedDispatcher;
        EdgeToEdgeCallback edgeToEdgeCallback = this.edgeToEdgeCallback;
        if (edgeToEdgeCallback != null) {
            edgeToEdgeCallback.setWindow(null);
        }
        MaterialBackOrchestrator materialBackOrchestrator = this.backOrchestrator;
        if (materialBackOrchestrator == null
                || (api34BackCallbackDelegate = materialBackOrchestrator.backCallbackDelegate)
                        == null
                || (findOnBackInvokedDispatcher =
                                materialBackOrchestrator.view.findOnBackInvokedDispatcher())
                        == null) {
            return;
        }
        findOnBackInvokedDispatcher.unregisterOnBackInvokedCallback(
                api34BackCallbackDelegate.onBackInvokedCallback);
        api34BackCallbackDelegate.onBackInvokedCallback = null;
    }

    @Override // androidx.activity.ComponentDialog, android.app.Dialog
    public final void onStart() {
        super.onStart();
        BottomSheetBehavior bottomSheetBehavior = this.behavior;
        if (bottomSheetBehavior == null || bottomSheetBehavior.state != 5) {
            return;
        }
        bottomSheetBehavior.setState$1(4);
    }

    @Override // android.app.Dialog
    public final void setCancelable(boolean z) {
        super.setCancelable(z);
        if (this.cancelable != z) {
            this.cancelable = z;
            BottomSheetBehavior bottomSheetBehavior = this.behavior;
            if (bottomSheetBehavior != null) {
                bottomSheetBehavior.setHideable(z);
            }
            if (getWindow() != null) {
                updateListeningForBackCallbacks();
            }
        }
    }

    @Override // android.app.Dialog
    public final void setCanceledOnTouchOutside(boolean z) {
        super.setCanceledOnTouchOutside(z);
        if (z && !this.cancelable) {
            this.cancelable = true;
        }
        this.canceledOnTouchOutside = z;
        this.canceledOnTouchOutsideSet = true;
    }

    @Override // androidx.appcompat.app.AppCompatDialog, androidx.activity.ComponentDialog,
              // android.app.Dialog
    public final void setContentView(int i) {
        super.setContentView(wrapInBottomSheet(null, i, null));
    }

    public final void updateListeningForBackCallbacks() {
        OnBackInvokedDispatcher findOnBackInvokedDispatcher;
        OnBackInvokedDispatcher findOnBackInvokedDispatcher2;
        MaterialBackOrchestrator materialBackOrchestrator = this.backOrchestrator;
        if (materialBackOrchestrator == null) {
            return;
        }
        boolean z = this.cancelable;
        MaterialBackOrchestrator.Api34BackCallbackDelegate api34BackCallbackDelegate =
                materialBackOrchestrator.backCallbackDelegate;
        if (!z) {
            if (api34BackCallbackDelegate == null
                    || (findOnBackInvokedDispatcher =
                                    materialBackOrchestrator.view.findOnBackInvokedDispatcher())
                            == null) {
                return;
            }
            findOnBackInvokedDispatcher.unregisterOnBackInvokedCallback(
                    api34BackCallbackDelegate.onBackInvokedCallback);
            api34BackCallbackDelegate.onBackInvokedCallback = null;
            return;
        }
        if (api34BackCallbackDelegate != null) {
            View view = materialBackOrchestrator.view;
            if (api34BackCallbackDelegate.onBackInvokedCallback == null
                    && (findOnBackInvokedDispatcher2 = view.findOnBackInvokedDispatcher())
                            != null) {
                MaterialBackOrchestrator.Api34BackCallbackDelegate.AnonymousClass1 anonymousClass1 =
                        new OnBackAnimationCallback() { // from class:
                                                        // com.google.android.material.motion.MaterialBackOrchestrator.Api34BackCallbackDelegate.1
                            public final /* synthetic */ MaterialBackHandler val$backHandler;

                            public AnonymousClass1(MaterialBackHandler materialBackHandler) {
                                r2 = materialBackHandler;
                            }

                            @Override // android.window.OnBackAnimationCallback
                            public final void onBackCancelled() {
                                if (Api34BackCallbackDelegate.this.onBackInvokedCallback != null) {
                                    r2.cancelBackProgress();
                                }
                            }

                            @Override // android.window.OnBackInvokedCallback
                            public final void onBackInvoked() {
                                r2.handleBackInvoked();
                            }

                            @Override // android.window.OnBackAnimationCallback
                            public final void onBackProgressed(BackEvent backEvent) {
                                if (Api34BackCallbackDelegate.this.onBackInvokedCallback != null) {
                                    r2.updateBackProgress(new BackEventCompat(backEvent));
                                }
                            }

                            @Override // android.window.OnBackAnimationCallback
                            public final void onBackStarted(BackEvent backEvent) {
                                if (Api34BackCallbackDelegate.this.onBackInvokedCallback != null) {
                                    r2.startBackProgress(new BackEventCompat(backEvent));
                                }
                            }
                        };
                api34BackCallbackDelegate.onBackInvokedCallback = anonymousClass1;
                findOnBackInvokedDispatcher2.registerOnBackInvokedCallback(0, anonymousClass1);
            }
        }
    }

    public final View wrapInBottomSheet(View view, int i, ViewGroup.LayoutParams layoutParams) {
        ensureContainerAndBehavior();
        CoordinatorLayout coordinatorLayout =
                (CoordinatorLayout) this.container.findViewById(R.id.coordinator);
        if (i != 0 && view == null) {
            view = getLayoutInflater().inflate(i, (ViewGroup) coordinatorLayout, false);
        }
        if (this.edgeToEdgeEnabled) {
            FrameLayout frameLayout = this.bottomSheet;
            OnApplyWindowInsetsListener onApplyWindowInsetsListener =
                    new OnApplyWindowInsetsListener() { // from class:
                                                        // com.google.android.material.bottomsheet.BottomSheetDialog.1
                        @Override // androidx.core.view.OnApplyWindowInsetsListener
                        public final WindowInsetsCompat onApplyWindowInsets(
                                View view2, WindowInsetsCompat windowInsetsCompat) {
                            BottomSheetDialog bottomSheetDialog = BottomSheetDialog.this;
                            EdgeToEdgeCallback edgeToEdgeCallback =
                                    bottomSheetDialog.edgeToEdgeCallback;
                            if (edgeToEdgeCallback != null) {
                                bottomSheetDialog.behavior.callbacks.remove(edgeToEdgeCallback);
                            }
                            EdgeToEdgeCallback edgeToEdgeCallback2 =
                                    new EdgeToEdgeCallback(
                                            bottomSheetDialog.bottomSheet, windowInsetsCompat);
                            bottomSheetDialog.edgeToEdgeCallback = edgeToEdgeCallback2;
                            edgeToEdgeCallback2.setWindow(bottomSheetDialog.getWindow());
                            BottomSheetBehavior bottomSheetBehavior = bottomSheetDialog.behavior;
                            EdgeToEdgeCallback edgeToEdgeCallback3 =
                                    bottomSheetDialog.edgeToEdgeCallback;
                            if (!bottomSheetBehavior.callbacks.contains(edgeToEdgeCallback3)) {
                                bottomSheetBehavior.callbacks.add(edgeToEdgeCallback3);
                            }
                            return windowInsetsCompat;
                        }
                    };
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api21Impl.setOnApplyWindowInsetsListener(
                    frameLayout, onApplyWindowInsetsListener);
        }
        this.bottomSheet.removeAllViews();
        if (layoutParams == null) {
            this.bottomSheet.addView(view);
        } else {
            this.bottomSheet.addView(view, layoutParams);
        }
        coordinatorLayout
                .findViewById(R.id.touch_outside)
                .setOnClickListener(
                        new View
                                .OnClickListener() { // from class:
                                                     // com.google.android.material.bottomsheet.BottomSheetDialog.2
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view2) {
                                BottomSheetDialog bottomSheetDialog = BottomSheetDialog.this;
                                if (bottomSheetDialog.cancelable && bottomSheetDialog.isShowing()) {
                                    BottomSheetDialog bottomSheetDialog2 = BottomSheetDialog.this;
                                    if (!bottomSheetDialog2.canceledOnTouchOutsideSet) {
                                        TypedArray obtainStyledAttributes =
                                                bottomSheetDialog2
                                                        .getContext()
                                                        .obtainStyledAttributes(
                                                                new int[] {
                                                                    android.R.attr
                                                                            .windowCloseOnTouchOutside
                                                                });
                                        bottomSheetDialog2.canceledOnTouchOutside =
                                                obtainStyledAttributes.getBoolean(0, true);
                                        obtainStyledAttributes.recycle();
                                        bottomSheetDialog2.canceledOnTouchOutsideSet = true;
                                    }
                                    if (bottomSheetDialog2.canceledOnTouchOutside) {
                                        BottomSheetDialog.this.cancel();
                                    }
                                }
                            }
                        });
        ViewCompat.setAccessibilityDelegate(
                this.bottomSheet,
                new AccessibilityDelegateCompat() { // from class:
                                                    // com.google.android.material.bottomsheet.BottomSheetDialog.3
                    @Override // androidx.core.view.AccessibilityDelegateCompat
                    public final void onInitializeAccessibilityNodeInfo(
                            View view2, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                        this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(
                                view2, accessibilityNodeInfoCompat.mInfo);
                        if (!BottomSheetDialog.this.cancelable) {
                            accessibilityNodeInfoCompat.mInfo.setDismissable(false);
                        } else {
                            accessibilityNodeInfoCompat.addAction(1048576);
                            accessibilityNodeInfoCompat.mInfo.setDismissable(true);
                        }
                    }

                    @Override // androidx.core.view.AccessibilityDelegateCompat
                    public final boolean performAccessibilityAction(
                            View view2, int i2, Bundle bundle) {
                        if (i2 == 1048576) {
                            BottomSheetDialog bottomSheetDialog = BottomSheetDialog.this;
                            if (bottomSheetDialog.cancelable) {
                                bottomSheetDialog.cancel();
                                return true;
                            }
                        }
                        return super.performAccessibilityAction(view2, i2, bundle);
                    }
                });
        this.bottomSheet.setOnTouchListener(new AnonymousClass4());
        return this.container;
    }

    @Override // androidx.appcompat.app.AppCompatDialog, androidx.activity.ComponentDialog,
              // android.app.Dialog
    public final void setContentView(View view) {
        super.setContentView(wrapInBottomSheet(view, 0, null));
    }

    @Override // androidx.appcompat.app.AppCompatDialog, androidx.activity.ComponentDialog,
              // android.app.Dialog
    public final void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        super.setContentView(wrapInBottomSheet(view, 0, layoutParams));
    }
}
