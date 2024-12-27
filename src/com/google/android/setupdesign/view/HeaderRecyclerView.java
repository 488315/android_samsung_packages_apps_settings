package com.google.android.setupdesign.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.FrameLayout;

import androidx.preference.Preference;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.setupdesign.DividerItemDecoration;
import com.google.android.setupdesign.R$styleable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class HeaderRecyclerView extends RecyclerView {
    public View header;
    public int headerRes;
    public boolean shouldHandleActionUp;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class HeaderAdapter extends RecyclerView.Adapter {
        public final RecyclerView.Adapter adapter;
        public View header;
        public final AnonymousClass1 observer;

        public HeaderAdapter(RecyclerView.Adapter adapter) {
            RecyclerView.AdapterDataObserver adapterDataObserver =
                    new RecyclerView
                            .AdapterDataObserver() { // from class:
                                                     // com.google.android.setupdesign.view.HeaderRecyclerView.HeaderAdapter.1
                        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
                        public final void onChanged() {
                            HeaderAdapter.this.notifyDataSetChanged();
                        }

                        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
                        public final void onItemRangeChanged(int i, int i2) {
                            HeaderAdapter headerAdapter = HeaderAdapter.this;
                            if (headerAdapter.header != null) {
                                i++;
                            }
                            headerAdapter.notifyItemRangeChanged(i, i2);
                        }

                        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
                        public final void onItemRangeInserted(int i, int i2) {
                            HeaderAdapter headerAdapter = HeaderAdapter.this;
                            if (headerAdapter.header != null) {
                                i++;
                            }
                            headerAdapter.notifyItemRangeInserted(i, i2);
                        }

                        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
                        public final void onItemRangeMoved(int i, int i2) {
                            HeaderAdapter headerAdapter = HeaderAdapter.this;
                            if (headerAdapter.header != null) {
                                i++;
                                i2++;
                            }
                            headerAdapter.notifyItemMoved(i, i2);
                        }

                        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
                        public final void onItemRangeRemoved(int i, int i2) {
                            HeaderAdapter headerAdapter = HeaderAdapter.this;
                            if (headerAdapter.header != null) {
                                i++;
                            }
                            headerAdapter.notifyItemRangeRemoved(i, i2);
                        }
                    };
            this.adapter = adapter;
            adapter.registerAdapterDataObserver(adapterDataObserver);
            setHasStableIds(adapter.mHasStableIds);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final int getItemCount() {
            int itemCount = this.adapter.getItemCount();
            return this.header != null ? itemCount + 1 : itemCount;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final long getItemId(int i) {
            if (this.header != null) {
                i--;
            }
            if (i < 0) {
                return Long.MAX_VALUE;
            }
            return this.adapter.getItemId(i);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final int getItemViewType(int i) {
            if (this.header != null) {
                i--;
            }
            return i < 0 ? Preference.DEFAULT_ORDER : this.adapter.getItemViewType(i);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            View view = this.header;
            if (view != null) {
                i--;
            }
            if (!(viewHolder instanceof HeaderViewHolder)) {
                this.adapter.onBindViewHolder(viewHolder, i);
            } else {
                if (view == null) {
                    throw new IllegalStateException("HeaderViewHolder cannot find mHeader");
                }
                if (view.getParent() != null) {
                    ((ViewGroup) this.header.getParent()).removeView(this.header);
                }
                ((FrameLayout) viewHolder.itemView).addView(this.header);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            if (i != Integer.MAX_VALUE) {
                return this.adapter.onCreateViewHolder(viewGroup, i);
            }
            FrameLayout frameLayout = new FrameLayout(viewGroup.getContext());
            frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -2));
            return new HeaderViewHolder(frameLayout);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class HeaderViewHolder extends RecyclerView.ViewHolder
            implements DividerItemDecoration.DividedViewHolder {
        @Override // com.google.android.setupdesign.DividerItemDecoration.DividedViewHolder
        public final boolean isDividerAllowedAbove() {
            return false;
        }

        @Override // com.google.android.setupdesign.DividerItemDecoration.DividedViewHolder
        public final boolean isDividerAllowedBelow() {
            return false;
        }
    }

    public HeaderRecyclerView(Context context) {
        super(context);
        this.shouldHandleActionUp = false;
        init$1(null, 0);
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup,
              // android.view.View
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        View findFocus;
        boolean z = false;
        if (this.shouldHandleActionUp && keyEvent.getAction() == 1) {
            this.shouldHandleActionUp = false;
            z = true;
        } else if (keyEvent.getAction() == 0) {
            int keyCode = keyEvent.getKeyCode();
            if (keyCode != 19) {
                if (keyCode == 20 && (findFocus = findFocus()) != null) {
                    int[] iArr = new int[2];
                    int[] iArr2 = new int[2];
                    findFocus.getLocationInWindow(iArr);
                    getLocationInWindow(iArr2);
                    int measuredHeight =
                            (findFocus.getMeasuredHeight() + iArr[1])
                                    - (getMeasuredHeight() + iArr2[1]);
                    if (measuredHeight > 0) {
                        smoothScrollBy(
                                0,
                                Math.min((int) (getMeasuredHeight() * 0.7f), measuredHeight),
                                false);
                        z = true;
                    }
                }
                this.shouldHandleActionUp = z;
            } else {
                View findFocus2 = findFocus();
                if (findFocus2 != null) {
                    int[] iArr3 = new int[2];
                    int[] iArr4 = new int[2];
                    findFocus2.getLocationInWindow(iArr3);
                    getLocationInWindow(iArr4);
                    int i = iArr3[1] - iArr4[1];
                    if (i < 0) {
                        smoothScrollBy(
                                0, Math.max((int) (getMeasuredHeight() * (-0.7f)), i), false);
                        z = true;
                    }
                }
                this.shouldHandleActionUp = z;
            }
        }
        if (z) {
            return true;
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    public final void init$1(AttributeSet attributeSet, int i) {
        if (isInEditMode()) {
            return;
        }
        TypedArray obtainStyledAttributes =
                getContext()
                        .obtainStyledAttributes(
                                attributeSet, R$styleable.SudHeaderRecyclerView, i, 0);
        this.headerRes = obtainStyledAttributes.getResourceId(0, 0);
        obtainStyledAttributes.recycle();
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        int i = this.header != null ? 1 : 0;
        accessibilityEvent.setItemCount(accessibilityEvent.getItemCount() - i);
        accessibilityEvent.setFromIndex(Math.max(accessibilityEvent.getFromIndex() - i, 0));
        accessibilityEvent.setToIndex(Math.max(accessibilityEvent.getToIndex() - i, 0));
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public final void setAdapter(RecyclerView.Adapter adapter) {
        if (this.header != null && adapter != null) {
            HeaderAdapter headerAdapter = new HeaderAdapter(adapter);
            headerAdapter.header = this.header;
            adapter = headerAdapter;
        }
        super.setAdapter(adapter);
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public final void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        super.setLayoutManager(layoutManager);
        if (layoutManager == null || this.header != null || this.headerRes == 0) {
            return;
        }
        this.header =
                LayoutInflater.from(getContext()).inflate(this.headerRes, (ViewGroup) this, false);
    }

    public HeaderRecyclerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.shouldHandleActionUp = false;
        init$1(attributeSet, 0);
    }

    public HeaderRecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.shouldHandleActionUp = false;
        init$1(attributeSet, i);
    }
}
