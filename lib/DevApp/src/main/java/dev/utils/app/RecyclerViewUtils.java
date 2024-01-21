package dev.utils.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import dev.utils.LogPrintUtils;

/**
 * detail: RecyclerView 工具类
 * @author Ttt
 * <pre>
 *     RecyclerView 截图使用 {@link CapturePictureUtils}
 *     RecyclerView 滑动使用 {@link ListViewUtils}
 *     <p></p>
 *     判断当前 view 是否在屏幕可见
 *     @see <a href="https://www.jianshu.com/p/85e8b9de5ecc"/>
 *     判断 RecyclerView 中 View 的可见性
 *     @see <a href="https://blog.csdn.net/xlh1191860939/article/details/113182209"/>
 *     RecyclerView 嵌套 EditText, EditText 获取焦点时滑动异常问题解决记录 ( requestChildRectangleOnScreen )
 *     @see <a href="https://blog.csdn.net/sunnyjerry/article/details/104416593"/>
 *     解决 ScrollView 嵌套 RecyclerView 的显示及滑动问题
 *     @see <a href="https://segmentfault.com/a/1190000011553735"/>
 *     RecyclerView 使用 GridLayoutManager 间距设置
 *     @see <a href="https://www.jianshu.com/p/f85923bd14ba"/>
 *     Android 可伸缩布局 - FlexboxLayout ( 支持 RecyclerView 集成 )
 *     @see <a href="https://juejin.im/post/58d1035161ff4b00603ca9c4"/>
 *     Recycleview 中 item 没有填满屏幕的问题
 *     @see <a href="https://blog.csdn.net/shanshan_1117/article/details/79363971"/>
 *     android RecyclerView 悬浮吸顶效果
 *     @see <a href="https://www.zhangshengrong.com/p/JKN8Ejo5X6"/>
 *     @see <a href="https://github.com/LidongWen/MultiTypeAdapter"/>
 *     <p></p>
 *     // 此方法常用作判断是否能下拉刷新, 来解决滑动冲突
 *     int findFirstCompletelyVisibleItemPosition = linearManager.findFirstCompletelyVisibleItemPosition();
 *     // 最后一个完整的可见的 item 位置
 *     int findLastCompletelyVisibleItemPosition = linearManager.findLastCompletelyVisibleItemPosition();
 *     // 最后一个可见的位置
 *     int findLastVisibleItemPosition = linearManager.findLastVisibleItemPosition();
 *     // 第一个可见的位置
 *     int findFirstVisibleItemPosition = linearManager.findFirstVisibleItemPosition();
 * </pre>
 */
public final class RecyclerViewUtils {

    private RecyclerViewUtils() {
    }

    // 日志 TAG
    private static final String TAG = RecyclerViewUtils.class.getSimpleName();

    // ====================
    // = 获取 RecyclerView =
    // ====================

    /**
     * 获取 RecyclerView
     * @param view {@link View}
     * @param <T>  泛型
     * @return {@link RecyclerView}
     */
    public static <T extends RecyclerView> T getRecyclerView(final View view) {
        if (view instanceof RecyclerView) {
            try {
                return (T) view;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getRecyclerView");
            }
        }
        return null;
    }

    // ================
    // = LayoutParams =
    // ================

    /**
     * 获取 RecyclerView Item View LayoutParams
     * @param itemView Item View
     * @return Item View LayoutParams
     */
    public static RecyclerView.LayoutParams getLayoutParams(final View itemView) {
        if (itemView != null) {
            ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
            if (layoutParams instanceof RecyclerView.LayoutParams) {
                return (RecyclerView.LayoutParams) layoutParams;
            }
        }
        return null;
    }

    /**
     * 获取 RecyclerView Item View LayoutParams
     * @param recyclerView {@link RecyclerView}
     * @param position     索引
     * @return Item View LayoutParams
     */
    public static RecyclerView.LayoutParams getLayoutParams(
            final RecyclerView recyclerView,
            final int position
    ) {
        return getLayoutParams(
                findViewByPosition(recyclerView, position)
        );
    }

    // =================
    // = LayoutManager =
    // =================

    /**
     * 设置 RecyclerView LayoutManager
     * @param view          {@link View}
     * @param layoutManager LayoutManager
     * @return {@link View}
     */
    public static View setLayoutManager(
            final View view,
            final RecyclerView.LayoutManager layoutManager
    ) {
        setLayoutManager(getRecyclerView(view), layoutManager);
        return view;
    }

    /**
     * 设置 RecyclerView LayoutManager
     * @param recyclerView  {@link RecyclerView}
     * @param layoutManager LayoutManager
     * @param <T>           泛型
     * @return {@link RecyclerView}
     */
    public static <T extends RecyclerView> T setLayoutManager(
            final T recyclerView,
            final RecyclerView.LayoutManager layoutManager
    ) {
        if (recyclerView != null && layoutManager != null) {
            recyclerView.setLayoutManager(layoutManager);
        }
        return recyclerView;
    }

    /**
     * 获取 RecyclerView LayoutManager
     * @param view {@link View}
     * @return LayoutManager
     */
    public static RecyclerView.LayoutManager getLayoutManager(final View view) {
        return getLayoutManager(getRecyclerView(view));
    }

    /**
     * 获取 RecyclerView LayoutManager
     * @param recyclerView {@link RecyclerView}
     * @return LayoutManager
     */
    public static RecyclerView.LayoutManager getLayoutManager(final RecyclerView recyclerView) {
        if (recyclerView != null) {
            return recyclerView.getLayoutManager();
        }
        return null;
    }

    // =

    /**
     * 获取 LinearLayoutManager
     * @param view {@link View}
     * @return {@link LinearLayoutManager}
     */
    public static LinearLayoutManager getLinearLayoutManager(final View view) {
        return getLinearLayoutManager(getRecyclerView(view));
    }

    /**
     * 获取 LinearLayoutManager
     * @param recyclerView {@link RecyclerView}
     * @return {@link LinearLayoutManager}
     */
    public static LinearLayoutManager getLinearLayoutManager(final RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = getLayoutManager(recyclerView);
        if (layoutManager instanceof LinearLayoutManager) {
            return (LinearLayoutManager) layoutManager;
        }
        return null;
    }

    /**
     * 获取 GridLayoutManager
     * @param view {@link View}
     * @return {@link GridLayoutManager}
     */
    public static GridLayoutManager getGridLayoutManager(final View view) {
        return getGridLayoutManager(getRecyclerView(view));
    }

    /**
     * 获取 GridLayoutManager
     * @param recyclerView {@link RecyclerView}
     * @return {@link GridLayoutManager}
     */
    public static GridLayoutManager getGridLayoutManager(final RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = getLayoutManager(recyclerView);
        if (layoutManager instanceof GridLayoutManager) {
            return (GridLayoutManager) layoutManager;
        }
        return null;
    }

    /**
     * 获取 StaggeredGridLayoutManager
     * @param view {@link View}
     * @return {@link StaggeredGridLayoutManager}
     */
    public static StaggeredGridLayoutManager getStaggeredGridLayoutManager(final View view) {
        return getStaggeredGridLayoutManager(getRecyclerView(view));
    }

    /**
     * 获取 StaggeredGridLayoutManager
     * @param recyclerView {@link RecyclerView}
     * @return {@link StaggeredGridLayoutManager}
     */
    public static StaggeredGridLayoutManager getStaggeredGridLayoutManager(final RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = getLayoutManager(recyclerView);
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            return (StaggeredGridLayoutManager) layoutManager;
        }
        return null;
    }

    // =============
    // = Grid Span =
    // =============

    /**
     * 设置 GridLayoutManager SpanCount
     * @param view      {@link View}
     * @param spanCount Span Count
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setSpanCount(
            final View view,
            final int spanCount
    ) {
        return setSpanCount(getLayoutManager(view), spanCount);
    }

    /**
     * 设置 GridLayoutManager SpanCount
     * @param recyclerView {@link RecyclerView}
     * @param spanCount    Span Count
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setSpanCount(
            final RecyclerView recyclerView,
            final int spanCount
    ) {
        return setSpanCount(getLayoutManager(recyclerView), spanCount);
    }

    /**
     * 设置 GridLayoutManager SpanCount
     * @param layoutManager LayoutManager
     * @param spanCount     Span Count
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setSpanCount(
            final RecyclerView.LayoutManager layoutManager,
            final int spanCount
    ) {
        if (spanCount < 1) return false;
        if (layoutManager instanceof GridLayoutManager) {
            ((GridLayoutManager) layoutManager).setSpanCount(spanCount);
            return true;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            ((StaggeredGridLayoutManager) layoutManager).setSpanCount(spanCount);
            return true;
        }
        return false;
    }

    // =

    /**
     * 获取 GridLayoutManager SpanCount
     * @param view {@link View}
     * @return Span Count
     */
    public static int getSpanCount(final View view) {
        return getSpanCount(getLayoutManager(view));
    }

    /**
     * 获取 GridLayoutManager SpanCount
     * @param recyclerView {@link RecyclerView}
     * @return Span Count
     */
    public static int getSpanCount(final RecyclerView recyclerView) {
        return getSpanCount(getLayoutManager(recyclerView));
    }

    /**
     * 获取 GridLayoutManager SpanCount
     * @param layoutManager LayoutManager
     * @return Span Count
     */
    public static int getSpanCount(final RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof GridLayoutManager) {
            return ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return 0;
    }

    // ============
    // = Position =
    // ============

    /**
     * 获取 RecyclerView 对应 Item View 索引
     * @param view     {@link View}
     * @param itemView Item View
     * @return 对应 Item View 索引
     */
    public static int getPosition(
            final View view,
            final View itemView
    ) {
        return getPosition(getLayoutManager(view), itemView);
    }

    /**
     * 获取 RecyclerView 对应 Item View 索引
     * @param recyclerView {@link RecyclerView}
     * @param itemView     Item View
     * @return 对应 Item View 索引
     */
    public static int getPosition(
            final RecyclerView recyclerView,
            final View itemView
    ) {
        return getPosition(getLayoutManager(recyclerView), itemView);
    }

    /**
     * 获取 RecyclerView 对应 Item View 索引
     * @param layoutManager LayoutManager
     * @param itemView      Item View
     * @return 对应 Item View 索引
     */
    public static int getPosition(
            final RecyclerView.LayoutManager layoutManager,
            final View itemView
    ) {
        if (layoutManager != null && itemView != null) {
            try {
                return layoutManager.getPosition(itemView);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getPosition");
            }
        }
        return -1;
    }

    // =

    /**
     * 获取 RecyclerView 对应索引 Item View
     * @param view     {@link View}
     * @param position 索引
     * @return 对应索引 Item View
     */
    public static View findViewByPosition(
            final View view,
            final int position
    ) {
        return findViewByPosition(getLayoutManager(view), position);
    }

    /**
     * 获取 RecyclerView 对应索引 Item View
     * @param recyclerView {@link RecyclerView}
     * @param position     索引
     * @return 对应索引 Item View
     */
    public static View findViewByPosition(
            final RecyclerView recyclerView,
            final int position
    ) {
        return findViewByPosition(getLayoutManager(recyclerView), position);
    }

    /**
     * 获取 RecyclerView 对应索引 Item View
     * @param layoutManager LayoutManager
     * @param position      索引
     * @return 对应索引 Item View
     */
    public static View findViewByPosition(
            final RecyclerView.LayoutManager layoutManager,
            final int position
    ) {
        if (layoutManager != null && position >= 0) {
            try {
                return layoutManager.findViewByPosition(position);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "findViewByPosition");
            }
        }
        return null;
    }

    // =

    /**
     * 获取 RecyclerView 第一条完全显示 Item 索引
     * @param view {@link View}
     * @return 第一条完全显示 Item 索引
     */
    public static int findFirstCompletelyVisibleItemPosition(final View view) {
        return findFirstCompletelyVisibleItemPosition(getLayoutManager(view));
    }

    /**
     * 获取 RecyclerView 第一条完全显示 Item 索引
     * @param recyclerView {@link RecyclerView}
     * @return 第一条完全显示 Item 索引
     */
    public static int findFirstCompletelyVisibleItemPosition(final RecyclerView recyclerView) {
        return findFirstCompletelyVisibleItemPosition(getLayoutManager(recyclerView));
    }

    /**
     * 获取 RecyclerView 第一条完全显示 Item 索引
     * @param layoutManager LayoutManager
     * @return 第一条完全显示 Item 索引
     */
    public static int findFirstCompletelyVisibleItemPosition(final RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
        }
        return -1;
    }

    // =

    /**
     * 获取 RecyclerView 第一条完全显示 Item 索引数组
     * @param view {@link View}
     * @return 第一条完全显示 Item 索引数组
     */
    public static int[] findFirstCompletelyVisibleItemPositions(final View view) {
        return findFirstCompletelyVisibleItemPositions(getLayoutManager(view));
    }

    /**
     * 获取 RecyclerView 第一条完全显示 Item 索引数组
     * @param recyclerView {@link RecyclerView}
     * @return 第一条完全显示 Item 索引数组
     */
    public static int[] findFirstCompletelyVisibleItemPositions(final RecyclerView recyclerView) {
        return findFirstCompletelyVisibleItemPositions(getLayoutManager(recyclerView));
    }

    /**
     * 获取 RecyclerView 第一条完全显示 Item 索引数组
     * @param layoutManager LayoutManager
     * @return 第一条完全显示 Item 索引数组
     */
    public static int[] findFirstCompletelyVisibleItemPositions(final RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) layoutManager).findFirstCompletelyVisibleItemPositions(null);
        }
        return null;
    }

    // =

    /**
     * 获取 RecyclerView 最后一条完全显示 Item 索引
     * @param view {@link View}
     * @return 最后一条完全显示 Item 索引
     */
    public static int findLastCompletelyVisibleItemPosition(final View view) {
        return findLastCompletelyVisibleItemPosition(getLayoutManager(view));
    }

    /**
     * 获取 RecyclerView 最后一条完全显示 Item 索引
     * @param recyclerView {@link RecyclerView}
     * @return 最后一条完全显示 Item 索引
     */
    public static int findLastCompletelyVisibleItemPosition(final RecyclerView recyclerView) {
        return findLastCompletelyVisibleItemPosition(getLayoutManager(recyclerView));
    }

    /**
     * 获取 RecyclerView 最后一条完全显示 Item 索引
     * @param layoutManager LayoutManager
     * @return 最后一条完全显示 Item 索引
     */
    public static int findLastCompletelyVisibleItemPosition(final RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
        }
        return -1;
    }

    // =

    /**
     * 获取 RecyclerView 最后一条完全显示 Item 索引数组
     * @param view {@link View}
     * @return 最后一条完全显示 Item 索引数组
     */
    public static int[] findLastCompletelyVisibleItemPositions(final View view) {
        return findLastCompletelyVisibleItemPositions(getLayoutManager(view));
    }

    /**
     * 获取 RecyclerView 最后一条完全显示 Item 索引数组
     * @param recyclerView {@link RecyclerView}
     * @return 最后一条完全显示 Item 索引数组
     */
    public static int[] findLastCompletelyVisibleItemPositions(final RecyclerView recyclerView) {
        return findLastCompletelyVisibleItemPositions(getLayoutManager(recyclerView));
    }

    /**
     * 获取 RecyclerView 最后一条完全显示 Item 索引数组
     * @param layoutManager LayoutManager
     * @return 最后一条完全显示 Item 索引数组
     */
    public static int[] findLastCompletelyVisibleItemPositions(final RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) layoutManager).findLastCompletelyVisibleItemPositions(null);
        }
        return null;
    }

    // =

    /**
     * 获取 RecyclerView 第一条显示 Item 索引
     * @param view {@link View}
     * @return 第一条显示 Item 索引
     */
    public static int findFirstVisibleItemPosition(final View view) {
        return findFirstVisibleItemPosition(getLayoutManager(view));
    }

    /**
     * 获取 RecyclerView 第一条显示 Item 索引
     * @param recyclerView {@link RecyclerView}
     * @return 第一条显示 Item 索引
     */
    public static int findFirstVisibleItemPosition(final RecyclerView recyclerView) {
        return findFirstVisibleItemPosition(getLayoutManager(recyclerView));
    }

    /**
     * 获取 RecyclerView 第一条显示 Item 索引
     * @param layoutManager LayoutManager
     * @return 第一条显示 Item 索引
     */
    public static int findFirstVisibleItemPosition(final RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        }
        return -1;
    }

    // =

    /**
     * 获取 RecyclerView 第一条显示 Item 索引数组
     * @param view {@link View}
     * @return 第一条显示 Item 索引数组
     */
    public static int[] findFirstVisibleItemPositions(final View view) {
        return findFirstVisibleItemPositions(getLayoutManager(view));
    }

    /**
     * 获取 RecyclerView 第一条显示 Item 索引数组
     * @param recyclerView {@link RecyclerView}
     * @return 第一条显示 Item 索引数组
     */
    public static int[] findFirstVisibleItemPositions(final RecyclerView recyclerView) {
        return findFirstVisibleItemPositions(getLayoutManager(recyclerView));
    }

    /**
     * 获取 RecyclerView 第一条显示 Item 索引数组
     * @param layoutManager LayoutManager
     * @return 第一条显示 Item 索引数组
     */
    public static int[] findFirstVisibleItemPositions(final RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) layoutManager).findFirstVisibleItemPositions(null);
        }
        return null;
    }

    // =

    /**
     * 获取 RecyclerView 最后一条显示 Item 索引
     * @param view {@link View}
     * @return 最后一条显示 Item 索引
     */
    public static int findLastVisibleItemPosition(final View view) {
        return findLastVisibleItemPosition(getLayoutManager(view));
    }

    /**
     * 获取 RecyclerView 最后一条显示 Item 索引
     * @param recyclerView {@link RecyclerView}
     * @return 最后一条显示 Item 索引
     */
    public static int findLastVisibleItemPosition(final RecyclerView recyclerView) {
        return findLastVisibleItemPosition(getLayoutManager(recyclerView));
    }

    /**
     * 获取 RecyclerView 最后一条显示 Item 索引
     * @param layoutManager LayoutManager
     * @return 最后一条显示 Item 索引
     */
    public static int findLastVisibleItemPosition(final RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        }
        return -1;
    }

    // =

    /**
     * 获取 RecyclerView 最后一条显示 Item 索引数组
     * @param view {@link View}
     * @return 最后一条显示 Item 索引数组
     */
    public static int[] findLastVisibleItemPositions(final View view) {
        return findLastVisibleItemPositions(getLayoutManager(view));
    }

    /**
     * 获取 RecyclerView 最后一条显示 Item 索引数组
     * @param recyclerView {@link RecyclerView}
     * @return 最后一条显示 Item 索引数组
     */
    public static int[] findLastVisibleItemPositions(final RecyclerView recyclerView) {
        return findLastVisibleItemPositions(getLayoutManager(recyclerView));
    }

    /**
     * 获取 RecyclerView 最后一条显示 Item 索引数组
     * @param layoutManager LayoutManager
     * @return 最后一条显示 Item 索引数组
     */
    public static int[] findLastVisibleItemPositions(final RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(null);
        }
        return null;
    }

    // ===============
    // = Orientation =
    // ===============

    /**
     * 设置 RecyclerView Orientation
     * @param view        {@link View}
     * @param orientation 方向
     * @return {@link View}
     */
    public static View setOrientation(
            final View view,
            @RecyclerView.Orientation final int orientation
    ) {
        setOrientation(getRecyclerView(view), orientation);
        return view;
    }

    /**
     * 设置 RecyclerView Orientation
     * @param recyclerView {@link RecyclerView}
     * @param orientation  方向
     * @param <T>          泛型
     * @return {@link RecyclerView}
     */
    public static <T extends RecyclerView> T setOrientation(
            final T recyclerView,
            @RecyclerView.Orientation final int orientation
    ) {
        RecyclerView.LayoutManager layoutManager = getLayoutManager(recyclerView);
        if (layoutManager instanceof LinearLayoutManager) {
            ((LinearLayoutManager) layoutManager).setOrientation(orientation);
        }
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            ((StaggeredGridLayoutManager) layoutManager).setOrientation(orientation);
        }
        return recyclerView;
    }

    // =

    /**
     * 获取 RecyclerView Orientation
     * @param view {@link View}
     * @return Orientation
     */
    public static int getOrientation(final View view) {
        return getOrientation(getLayoutManager(view));
    }

    /**
     * 获取 RecyclerView Orientation
     * @param recyclerView {@link RecyclerView}
     * @return Orientation
     */
    public static int getOrientation(final RecyclerView recyclerView) {
        return getOrientation(getLayoutManager(recyclerView));
    }

    /**
     * 获取 RecyclerView Orientation
     * @param layoutManager LayoutManager
     * @return Orientation
     */
    public static int getOrientation(final RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).getOrientation();
        }
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) layoutManager).getOrientation();
        }
        return -1;
    }

    // =

    /**
     * 校验 RecyclerView Orientation 是否为 VERTICAL
     * @param view {@link View}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean canScrollVertically(final View view) {
        return canScrollVertically(getLayoutManager(view));
    }

    /**
     * 校验 RecyclerView Orientation 是否为 VERTICAL
     * @param recyclerView {@link RecyclerView}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean canScrollVertically(final RecyclerView recyclerView) {
        return canScrollVertically(getLayoutManager(recyclerView));
    }

    /**
     * 校验 RecyclerView Orientation 是否为 VERTICAL
     * @param layoutManager LayoutManager
     * @return {@code true} yes, {@code false} no
     */
    public static boolean canScrollVertically(final RecyclerView.LayoutManager layoutManager) {
        return getOrientation(layoutManager) == RecyclerView.VERTICAL;
    }

    // =

    /**
     * 校验 RecyclerView Orientation 是否为 HORIZONTAL
     * @param view {@link View}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean canScrollHorizontally(final View view) {
        return canScrollHorizontally(getLayoutManager(view));
    }

    /**
     * 校验 RecyclerView Orientation 是否为 HORIZONTAL
     * @param recyclerView {@link RecyclerView}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean canScrollHorizontally(final RecyclerView recyclerView) {
        return canScrollHorizontally(getLayoutManager(recyclerView));
    }

    /**
     * 校验 RecyclerView Orientation 是否为 HORIZONTAL
     * @param layoutManager LayoutManager
     * @return {@code true} yes, {@code false} no
     */
    public static boolean canScrollHorizontally(final RecyclerView.LayoutManager layoutManager) {
        return getOrientation(layoutManager) == RecyclerView.HORIZONTAL;
    }

    // ===========
    // = Adapter =
    // ===========

    /**
     * 设置 RecyclerView Adapter
     * @param view    {@link View}
     * @param adapter Adapter
     * @return {@link View}
     */
    public static View setAdapter(
            final View view,
            final RecyclerView.Adapter<?> adapter
    ) {
        setAdapter(getRecyclerView(view), adapter);
        return view;
    }

    /**
     * 设置 RecyclerView Adapter
     * @param recyclerView {@link RecyclerView}
     * @param adapter      Adapter
     * @param <T>          泛型
     * @return {@link RecyclerView}
     */
    public static <T extends RecyclerView> T setAdapter(
            final T recyclerView,
            final RecyclerView.Adapter<?> adapter
    ) {
        if (recyclerView != null && adapter != null) {
            recyclerView.setAdapter(adapter);
        }
        return recyclerView;
    }

    /**
     * 获取 RecyclerView Adapter
     * @param view {@link View}
     * @param <T>  泛型
     * @return LayoutManager
     */
    public static <T extends RecyclerView.Adapter<?>> T getAdapter(final View view) {
        return getAdapter(getRecyclerView(view));
    }

    /**
     * 获取 RecyclerView Adapter
     * @param recyclerView {@link RecyclerView}
     * @param <T>          泛型
     * @return LayoutManager
     */
    public static <T extends RecyclerView.Adapter<?>> T getAdapter(final RecyclerView recyclerView) {
        if (recyclerView != null) {
            RecyclerView.Adapter<?> adapter = recyclerView.getAdapter();
            if (adapter != null) {
                try {
                    return (T) adapter;
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "getAdapter");
                }
            }
        }
        return null;
    }

    // =

    /**
     * 获取 Adapter ItemCount
     * @param view {@link View}
     * @return ItemCount
     */
    public static int getItemCount(final View view) {
        return getItemCount(getAdapter(view));
    }

    /**
     * 获取 Adapter ItemCount
     * @param recyclerView {@link RecyclerView}
     * @return ItemCount
     */
    public static int getItemCount(final RecyclerView recyclerView) {
        return getItemCount(getAdapter(recyclerView));
    }

    /**
     * 获取 Adapter ItemCount
     * @param adapter RecyclerView.Adapter
     * @return ItemCount
     */
    public static int getItemCount(final RecyclerView.Adapter<?> adapter) {
        if (adapter != null) {
            return adapter.getItemCount();
        }
        return 0;
    }

    // =

    /**
     * 获取 Adapter 指定索引 Item Id
     * @param view     {@link View}
     * @param position 索引
     * @return Item Id
     */
    public static long getItemId(
            final View view,
            final int position
    ) {
        return getItemId(getAdapter(view), position);
    }

    /**
     * 获取 Adapter 指定索引 Item Id
     * @param recyclerView {@link RecyclerView}
     * @param position     索引
     * @return Item Id
     */
    public static long getItemId(
            final RecyclerView recyclerView,
            final int position
    ) {
        return getItemId(getAdapter(recyclerView), position);
    }

    /**
     * 获取 Adapter 指定索引 Item Id
     * @param adapter  RecyclerView.Adapter
     * @param position 索引
     * @return Item Id
     */
    public static long getItemId(
            final RecyclerView.Adapter<?> adapter,
            final int position
    ) {
        if (adapter != null) {
            try {
                return adapter.getItemId(position);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getItemId");
            }
        }
        return RecyclerView.NO_ID;
    }

    // =

    /**
     * 获取 Adapter 指定索引 Item Type
     * @param view     {@link View}
     * @param position 索引
     * @return Item Type
     */
    public static int getItemViewType(
            final View view,
            final int position
    ) {
        return getItemViewType(getAdapter(view), position);
    }

    /**
     * 获取 Adapter 指定索引 Item Type
     * @param recyclerView {@link RecyclerView}
     * @param position     索引
     * @return Item Type
     */
    public static int getItemViewType(
            final RecyclerView recyclerView,
            final int position
    ) {
        return getItemViewType(getAdapter(recyclerView), position);
    }

    /**
     * 获取 Adapter 指定索引 Item Type
     * @param adapter  RecyclerView.Adapter
     * @param position 索引
     * @return Item Type
     */
    public static int getItemViewType(
            final RecyclerView.Adapter<?> adapter,
            final int position
    ) {
        if (adapter != null) {
            try {
                return adapter.getItemViewType(position);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getItemViewType");
            }
        }
        return -1;
    }

    // =

    /**
     * RecyclerView notifyItemRemoved
     * @param view     {@link View}
     * @param position 索引
     * @return {@code true} success, {@code false} fail
     */
    public static boolean notifyItemRemoved(
            final View view,
            final int position
    ) {
        return notifyItemRemoved(getAdapter(view), position);
    }

    /**
     * RecyclerView notifyItemRemoved
     * @param recyclerView {@link RecyclerView}
     * @param position     索引
     * @return {@code true} success, {@code false} fail
     */
    public static boolean notifyItemRemoved(
            final RecyclerView recyclerView,
            final int position
    ) {
        return notifyItemRemoved(getAdapter(recyclerView), position);
    }

    /**
     * RecyclerView notifyItemRemoved
     * @param adapter  RecyclerView.Adapter
     * @param position 索引
     * @return {@code true} success, {@code false} fail
     */
    public static boolean notifyItemRemoved(
            final RecyclerView.Adapter<?> adapter,
            final int position
    ) {
        if (adapter != null) {
            try {
                adapter.notifyItemRemoved(position);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "notifyItemRemoved");
            }
        }
        return false;
    }

    // =

    /**
     * RecyclerView notifyItemInserted
     * @param view     {@link View}
     * @param position 索引
     * @return {@code true} success, {@code false} fail
     */
    public static boolean notifyItemInserted(
            final View view,
            final int position
    ) {
        return notifyItemInserted(getAdapter(view), position);
    }

    /**
     * RecyclerView notifyItemInserted
     * @param recyclerView {@link RecyclerView}
     * @param position     索引
     * @return {@code true} success, {@code false} fail
     */
    public static boolean notifyItemInserted(
            final RecyclerView recyclerView,
            final int position
    ) {
        return notifyItemInserted(getAdapter(recyclerView), position);
    }

    /**
     * RecyclerView notifyItemInserted
     * @param adapter  RecyclerView.Adapter
     * @param position 索引
     * @return {@code true} success, {@code false} fail
     */
    public static boolean notifyItemInserted(
            final RecyclerView.Adapter<?> adapter,
            final int position
    ) {
        if (adapter != null) {
            try {
                adapter.notifyItemInserted(position);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "notifyItemInserted");
            }
        }
        return false;
    }

    // =

    /**
     * RecyclerView notifyItemMoved
     * @param view         {@link View}
     * @param fromPosition 当前索引
     * @param toPosition   更新后索引
     * @return {@code true} success, {@code false} fail
     */
    public static boolean notifyItemMoved(
            final View view,
            final int fromPosition,
            final int toPosition
    ) {
        return notifyItemMoved(getAdapter(view), fromPosition, toPosition);
    }

    /**
     * RecyclerView notifyItemMoved
     * @param recyclerView {@link RecyclerView}
     * @param fromPosition 当前索引
     * @param toPosition   更新后索引
     * @return {@code true} success, {@code false} fail
     */
    public static boolean notifyItemMoved(
            final RecyclerView recyclerView,
            final int fromPosition,
            final int toPosition
    ) {
        return notifyItemMoved(getAdapter(recyclerView), fromPosition, toPosition);
    }

    /**
     * RecyclerView notifyItemMoved
     * @param adapter      RecyclerView.Adapter
     * @param fromPosition 当前索引
     * @param toPosition   更新后索引
     * @return {@code true} success, {@code false} fail
     */
    public static boolean notifyItemMoved(
            final RecyclerView.Adapter<?> adapter,
            final int fromPosition,
            final int toPosition
    ) {
        if (adapter != null) {
            try {
                adapter.notifyItemMoved(fromPosition, toPosition);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "notifyItemMoved");
            }
        }
        return false;
    }

    // =

    /**
     * RecyclerView notifyDataSetChanged
     * @param view {@link View}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean notifyDataSetChanged(final View view) {
        return notifyDataSetChanged(getAdapter(view));
    }

    /**
     * RecyclerView notifyDataSetChanged
     * @param recyclerView {@link RecyclerView}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean notifyDataSetChanged(final RecyclerView recyclerView) {
        return notifyDataSetChanged(getAdapter(recyclerView));
    }

    /**
     * RecyclerView notifyDataSetChanged
     * @param adapter RecyclerView.Adapter
     * @return {@code true} success, {@code false} fail
     */
    @SuppressLint("NotifyDataSetChanged")
    public static boolean notifyDataSetChanged(final RecyclerView.Adapter<?> adapter) {
        if (adapter != null) {
            try {
                adapter.notifyDataSetChanged();
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "notifyDataSetChanged");
            }
        }
        return false;
    }

    // ==============
    // = SnapHelper =
    // ==============

    /**
     * 设置 RecyclerView LinearSnapHelper
     * @param view {@link View}
     * @return {@link LinearSnapHelper}
     */
    public static LinearSnapHelper attachLinearSnapHelper(final View view) {
        return attachLinearSnapHelper(getRecyclerView(view));
    }

    /**
     * 设置 RecyclerView LinearSnapHelper
     * <pre>
     *     滑动多页居中显示, 类似 Gallery
     * </pre>
     * @param recyclerView {@link RecyclerView}
     * @return {@link LinearSnapHelper}
     */
    public static LinearSnapHelper attachLinearSnapHelper(final RecyclerView recyclerView) {
        if (recyclerView != null) {
            LinearSnapHelper helper = new LinearSnapHelper();
            helper.attachToRecyclerView(recyclerView);
            return helper;
        }
        return null;
    }

    // =

    /**
     * 设置 RecyclerView PagerSnapHelper
     * @param view {@link View}
     * @return {@link PagerSnapHelper}
     */
    public static PagerSnapHelper attachPagerSnapHelper(final View view) {
        return attachPagerSnapHelper(getRecyclerView(view));
    }

    /**
     * 设置 RecyclerView PagerSnapHelper
     * <pre>
     *     每次滑动一页居中显示, 类似 ViewPager
     * </pre>
     * @param recyclerView {@link RecyclerView}
     * @return {@link PagerSnapHelper}
     */
    public static PagerSnapHelper attachPagerSnapHelper(final RecyclerView recyclerView) {
        if (recyclerView != null) {
            PagerSnapHelper helper = new PagerSnapHelper();
            helper.attachToRecyclerView(recyclerView);
            return helper;
        }
        return null;
    }

    // ==================
    // = ItemDecoration =
    // ==================

    /**
     * 获取 RecyclerView ItemDecoration 总数
     * @param view {@link View}
     * @return RecyclerView ItemDecoration 总数
     */
    public static int getItemDecorationCount(final View view) {
        return getItemDecorationCount(getRecyclerView(view));
    }

    /**
     * 获取 RecyclerView ItemDecoration 总数
     * @param recyclerView {@link RecyclerView}
     * @return RecyclerView ItemDecoration 总数
     */
    public static int getItemDecorationCount(final RecyclerView recyclerView) {
        if (recyclerView != null) {
            return recyclerView.getItemDecorationCount();
        }
        return 0;
    }

    // =

    /**
     * 获取 RecyclerView ItemDecoration
     * @param view  {@link View}
     * @param index RecyclerView ItemDecoration 索引
     * @return RecyclerView ItemDecoration
     */
    public static RecyclerView.ItemDecoration getItemDecorationAt(
            final View view,
            final int index
    ) {
        return getItemDecorationAt(getRecyclerView(view), index);
    }

    /**
     * 获取 RecyclerView ItemDecoration
     * @param recyclerView {@link RecyclerView}
     * @param index        RecyclerView ItemDecoration 索引
     * @return RecyclerView ItemDecoration
     */
    public static RecyclerView.ItemDecoration getItemDecorationAt(
            final RecyclerView recyclerView,
            final int index
    ) {
        if (recyclerView != null) {
            try {
                return recyclerView.getItemDecorationAt(index);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getItemDecorationAt");
            }
        }
        return null;
    }

    // =

    /**
     * 添加 RecyclerView ItemDecoration
     * @param view  {@link View}
     * @param decor RecyclerView ItemDecoration
     * @return {@code true} success, {@code false} fail
     */
    public static boolean addItemDecoration(
            final View view,
            final RecyclerView.ItemDecoration decor
    ) {
        return addItemDecoration(getRecyclerView(view), decor);
    }

    /**
     * 添加 RecyclerView ItemDecoration
     * @param recyclerView {@link RecyclerView}
     * @param decor        RecyclerView ItemDecoration
     * @return {@code true} success, {@code false} fail
     */
    public static boolean addItemDecoration(
            final RecyclerView recyclerView,
            final RecyclerView.ItemDecoration decor
    ) {
        if (recyclerView != null && decor != null) {
            try {
                recyclerView.addItemDecoration(decor);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "addItemDecoration");
            }
        }
        return false;
    }

    /**
     * 添加 RecyclerView ItemDecoration
     * @param view  {@link View}
     * @param decor RecyclerView ItemDecoration
     * @param index 添加索引
     * @return {@code true} success, {@code false} fail
     */
    public static boolean addItemDecoration(
            final View view,
            final RecyclerView.ItemDecoration decor,
            final int index
    ) {
        return addItemDecoration(getRecyclerView(view), decor, index);
    }

    /**
     * 添加 RecyclerView ItemDecoration
     * @param recyclerView {@link RecyclerView}
     * @param decor        RecyclerView ItemDecoration
     * @param index        添加索引
     * @return {@code true} success, {@code false} fail
     */
    public static boolean addItemDecoration(
            final RecyclerView recyclerView,
            final RecyclerView.ItemDecoration decor,
            final int index
    ) {
        if (recyclerView != null && decor != null) {
            try {
                recyclerView.addItemDecoration(decor, index);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "addItemDecoration");
            }
        }
        return false;
    }

    // =

    /**
     * 移除 RecyclerView ItemDecoration
     * @param view  {@link View}
     * @param decor RecyclerView ItemDecoration
     * @return {@code true} success, {@code false} fail
     */
    public static boolean removeItemDecoration(
            final View view,
            final RecyclerView.ItemDecoration decor
    ) {
        return removeItemDecoration(getRecyclerView(view), decor);
    }

    /**
     * 移除 RecyclerView ItemDecoration
     * @param recyclerView {@link RecyclerView}
     * @param decor        RecyclerView ItemDecoration
     * @return {@code true} success, {@code false} fail
     */
    public static boolean removeItemDecoration(
            final RecyclerView recyclerView,
            final RecyclerView.ItemDecoration decor
    ) {
        if (recyclerView != null && decor != null) {
            try {
                recyclerView.removeItemDecoration(decor);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "removeItemDecoration");
            }
        }
        return false;
    }

    // =

    /**
     * 移除 RecyclerView ItemDecoration
     * @param view  {@link View}
     * @param index RecyclerView ItemDecoration 索引
     * @return {@code true} success, {@code false} fail
     */
    public static boolean removeItemDecorationAt(
            final View view,
            final int index
    ) {
        return removeItemDecorationAt(getRecyclerView(view), index);
    }

    /**
     * 移除 RecyclerView ItemDecoration
     * @param recyclerView {@link RecyclerView}
     * @param index        RecyclerView ItemDecoration 索引
     * @return {@code true} success, {@code false} fail
     */
    public static boolean removeItemDecorationAt(
            final RecyclerView recyclerView,
            final int index
    ) {
        if (recyclerView != null) {
            try {
                recyclerView.removeItemDecorationAt(index);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "removeItemDecorationAt");
            }
        }
        return false;
    }

    /**
     * 移除 RecyclerView 全部 ItemDecoration
     * @param view {@link View}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean removeAllItemDecoration(final View view) {
        return removeAllItemDecoration(getRecyclerView(view));
    }

    /**
     * 移除 RecyclerView 全部 ItemDecoration
     * @param recyclerView {@link RecyclerView}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean removeAllItemDecoration(final RecyclerView recyclerView) {
        if (recyclerView != null) {
            for (int i = 0, len = recyclerView.getItemDecorationCount(); i < len; i++) {
                try {
                    recyclerView.removeItemDecorationAt(0);
                } catch (Exception ignored) {
                }
            }
            return true;
        }
        return false;
    }

    // ====================
    // = OnScrollListener =
    // ====================

    /**
     * 设置 RecyclerView ScrollListener
     * @param view     {@link View}
     * @param listener ScrollListener
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setOnScrollListener(
            final View view,
            final RecyclerView.OnScrollListener listener
    ) {
        return setOnScrollListener(getRecyclerView(view), listener);
    }

    /**
     * 设置 RecyclerView ScrollListener
     * @param recyclerView {@link RecyclerView}
     * @param listener     ScrollListener
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setOnScrollListener(
            final RecyclerView recyclerView,
            final RecyclerView.OnScrollListener listener
    ) {
        if (recyclerView != null) {
            try {
                recyclerView.setOnScrollListener(listener);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setOnScrollListener");
            }
        }
        return false;
    }

    // =

    /**
     * 添加 RecyclerView ScrollListener
     * @param view     {@link View}
     * @param listener ScrollListener
     * @return {@code true} success, {@code false} fail
     */
    public static boolean addOnScrollListener(
            final View view,
            final RecyclerView.OnScrollListener listener
    ) {
        return addOnScrollListener(getRecyclerView(view), listener);
    }

    /**
     * 添加 RecyclerView ScrollListener
     * @param recyclerView {@link RecyclerView}
     * @param listener     ScrollListener
     * @return {@code true} success, {@code false} fail
     */
    public static boolean addOnScrollListener(
            final RecyclerView recyclerView,
            final RecyclerView.OnScrollListener listener
    ) {
        if (recyclerView != null && listener != null) {
            try {
                recyclerView.addOnScrollListener(listener);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "addOnScrollListener");
            }
        }
        return false;
    }

    // =

    /**
     * 移除 RecyclerView ScrollListener
     * @param view     {@link View}
     * @param listener ScrollListener
     * @return {@code true} success, {@code false} fail
     */
    public static boolean removeOnScrollListener(
            final View view,
            final RecyclerView.OnScrollListener listener
    ) {
        return removeOnScrollListener(getRecyclerView(view), listener);
    }

    /**
     * 移除 RecyclerView ScrollListener
     * @param recyclerView {@link RecyclerView}
     * @param listener     ScrollListener
     * @return {@code true} success, {@code false} fail
     */
    public static boolean removeOnScrollListener(
            final RecyclerView recyclerView,
            final RecyclerView.OnScrollListener listener
    ) {
        if (recyclerView != null && listener != null) {
            try {
                recyclerView.removeOnScrollListener(listener);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "removeOnScrollListener");
            }
        }
        return false;
    }

    // =

    /**
     * 清空 RecyclerView ScrollListener
     * @param view {@link View}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean clearOnScrollListeners(final View view) {
        return clearOnScrollListeners(getRecyclerView(view));
    }

    /**
     * 清空 RecyclerView ScrollListener
     * @param recyclerView {@link RecyclerView}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean clearOnScrollListeners(final RecyclerView recyclerView) {
        if (recyclerView != null) {
            try {
                recyclerView.clearOnScrollListeners();
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "clearOnScrollListeners");
            }
        }
        return false;
    }

    // =

    /**
     * 获取 RecyclerView 滑动状态
     * @param view {@link View}
     * @return RecyclerView 滑动状态
     */
    public static int getScrollState(final View view) {
        return getScrollState(getRecyclerView(view));
    }

    /**
     * 获取 RecyclerView 滑动状态
     * @param recyclerView {@link RecyclerView}
     * @return RecyclerView 滑动状态
     */
    public static int getScrollState(final RecyclerView recyclerView) {
        if (recyclerView != null) {
            return recyclerView.getScrollState();
        }
        return -1;
    }

    // =

    /**
     * 获取 RecyclerView 嵌套滚动开关
     * @param view {@link View}
     * @return RecyclerView 嵌套滚动开关
     */
    public static boolean isNestedScrollingEnabled(final View view) {
        return isNestedScrollingEnabled(getRecyclerView(view));
    }

    /**
     * 获取 RecyclerView 嵌套滚动开关
     * @param recyclerView {@link RecyclerView}
     * @return RecyclerView 嵌套滚动开关
     */
    public static boolean isNestedScrollingEnabled(final RecyclerView recyclerView) {
        if (recyclerView != null) {
            return recyclerView.isNestedScrollingEnabled();
        }
        return false;
    }

    // =

    /**
     * 设置 RecyclerView 嵌套滚动开关
     * @param view    {@link View}
     * @param enabled 嵌套滚动开关
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setNestedScrollingEnabled(
            final View view,
            final boolean enabled
    ) {
        return setNestedScrollingEnabled(getRecyclerView(view), enabled);
    }

    /**
     * 设置 RecyclerView 嵌套滚动开关
     * @param recyclerView {@link RecyclerView}
     * @param enabled      嵌套滚动开关
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setNestedScrollingEnabled(
            final RecyclerView recyclerView,
            final boolean enabled
    ) {
        if (recyclerView != null) {
            recyclerView.setNestedScrollingEnabled(enabled);
            return true;
        }
        return false;
    }

    // =============
    // = 快捷部分实现 =
    // =============

    /**
     * detail: 修复子 View 导致滑动 Bug LinearLayoutManager
     * @author Ttt
     */
    public static class FixChildScrollBugLinearLayoutManager
            extends LinearLayoutManager {

        public FixChildScrollBugLinearLayoutManager(Context context) {
            super(context);
        }

        public FixChildScrollBugLinearLayoutManager(
                Context context,
                int orientation,
                boolean reverseLayout
        ) {
            super(context, orientation, reverseLayout);
        }

        public FixChildScrollBugLinearLayoutManager(
                Context context,
                AttributeSet attrs,
                int defStyleAttr,
                int defStyleRes
        ) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        public boolean requestChildRectangleOnScreen(
                @NonNull RecyclerView parent,
                @NonNull View child,
                @NonNull Rect rect,
                boolean immediate
        ) {
            return false;
        }

        @Override
        public boolean requestChildRectangleOnScreen(
                @NonNull RecyclerView parent,
                @NonNull View child,
                @NonNull Rect rect,
                boolean immediate,
                boolean focusedChildVisible
        ) {
            return false;
        }
    }
}