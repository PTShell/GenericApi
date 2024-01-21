package dev.widget.decoration.grid;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import dev.widget.decoration.BaseColorGridItemDecoration;

/**
 * detail: RecyclerView Grid 行分割线处理 ( 每一行数据 )
 * @author Ttt
 * <pre>
 *     效果:
 *     每一行数据底部绘制分割线 ( 最后一行不绘制 )
 * </pre>
 */
public class GridRowColorItemDecoration
        extends BaseColorGridItemDecoration {

    public GridRowColorItemDecoration(
            final int spanCount,
            final boolean vertical,
            final float height
    ) {
        super(false, spanCount, vertical, height);
    }

    public GridRowColorItemDecoration(
            final int spanCount,
            final boolean vertical,
            final float height,
            @ColorInt final int color
    ) {
        super(false, spanCount, vertical, height, color);
    }

    // ==========
    // = 处理方法 =
    // ==========

    @Override
    public void getItemOffsets(
            @NonNull Rect outRect,
            @NonNull View view,
            @NonNull RecyclerView parent,
            @NonNull RecyclerView.State state
    ) {
        if (isVertical()) {
            verticalItemOffsets(outRect, view, parent, state);
        } else if (isHorizontal()) {
            horizontalItemOffsets(outRect, view, parent, state);
        }
    }

    @Override
    public void onDraw(
            @NonNull Canvas canvas,
            @NonNull RecyclerView parent,
            @NonNull RecyclerView.State state
    ) {
        if (isVertical()) {
            verticalDraw(canvas, parent, state);
        } else if (isHorizontal()) {
            horizontalDraw(canvas, parent, state);
        }
    }

    // ============
    // = vertical =
    // ============

    private void verticalItemOffsets(
            final Rect outRect,
            final View view,
            final RecyclerView parent,
            final RecyclerView.State state
    ) {
        int itemCount = state.getItemCount();
        if (itemCount <= mSpanCount) return;

        int index = parent.getChildAdapterPosition(view);
        if (index < mSpanCount) {
            outRect.set(0, 0, 0, 0);
        } else {
            outRect.set(0, (int) mHeight, 0, 0);
        }
    }

    private void verticalDraw(
            final Canvas canvas,
            final RecyclerView parent,
            final RecyclerView.State state
    ) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int  index = parent.getChildAdapterPosition(child);
            if (index >= mSpanCount) {
                canvas.drawRect(
                        child.getLeft() + mLeft,
                        child.getTop() - mHeight - mOffset,
                        child.getRight() - mRight,
                        child.getTop() - mOffset,
                        mPaint
                );
            }
        }
    }

    // ==============
    // = horizontal =
    // ==============

    private void horizontalItemOffsets(
            final Rect outRect,
            final View view,
            final RecyclerView parent,
            final RecyclerView.State state
    ) {
        float value     = mHeight / mSpanCount;
        int   index     = parent.getChildAdapterPosition(view);
        int   spanIndex = index % mSpanCount;
        if (spanIndex == 0) {
            outRect.set(0, 0, 0, 0);
        } else {
            outRect.set(0, (int) (value * spanIndex), 0, 0);
        }
    }

    private void horizontalDraw(
            final Canvas canvas,
            final RecyclerView parent,
            final RecyclerView.State state
    ) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child     = parent.getChildAt(i);
            int  index     = parent.getChildAdapterPosition(child);
            int  spanIndex = index % mSpanCount;
            if (spanIndex != 0) {
                canvas.drawRect(
                        child.getLeft() + mLeft,
                        child.getTop() - mHeight - mOffset,
                        child.getRight() - mRight,
                        child.getTop() - mOffset,
                        mPaint
                );
            }
        }
    }
}