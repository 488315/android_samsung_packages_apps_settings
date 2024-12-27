package com.google.common.util.concurrent;

import com.google.common.base.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class FluentFuture extends AbstractFuture {
    public static final /* synthetic */ int $r8$clinit = 0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class TrustedFuture extends FluentFuture implements AbstractFuture.Trusted {
        @Override // com.google.common.util.concurrent.AbstractFuture, java.util.concurrent.Future
        public final boolean isCancelled() {
            return this.value instanceof AbstractFuture.Cancellation;
        }
    }

    public final AbstractTransformFuture$TransformFuture transform(Function function) {
        DirectExecutor directExecutor = DirectExecutor.INSTANCE;
        AbstractTransformFuture$TransformFuture abstractTransformFuture$TransformFuture =
                new AbstractTransformFuture$TransformFuture();
        abstractTransformFuture$TransformFuture.inputFuture = this;
        abstractTransformFuture$TransformFuture.function = function;
        addListener(abstractTransformFuture$TransformFuture, DirectExecutor.INSTANCE);
        return abstractTransformFuture$TransformFuture;
    }
}
