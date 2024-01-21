package dev.utils.app.assist.floating;

import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;

/**
 * detail: 悬浮窗触摸事件接口
 * @author Ttt
 * <pre>
 *     用于解决 onTouchEvent 与 click、longClick 冲突处理
 *     可搭配 {@link dev.utils.app.ViewUtils#isTouchInView(MotionEvent, View)} 判断
 * </pre>
 */
public interface IFloatingListener {

    /**
     * 悬浮窗 View 点击事件
     * @param view       {@link View}
     * @param event      触摸事件
     * @param firstPoint 首次触摸点记录
     * @return {@code true} 消费事件, {@code false} 不消费事件
     */
    boolean onClick(
            View view,
            MotionEvent event,
            PointF firstPoint
    );

    /**
     * 悬浮窗 View 长按事件
     * @param view       {@link View}
     * @param event      触摸事件
     * @param firstPoint 首次触摸点记录
     * @return {@code true} 消费事件, {@code false} 不消费事件
     */
    boolean onLongClick(
            View view,
            MotionEvent event,
            PointF firstPoint
    );

    // =

    /**
     * 获取点击事件间隔时间
     * @return 点击事件间隔时间
     */
    long getClickIntervalTime();

    /**
     * 获取点击事件间隔时间
     * @param time 点击事件间隔时间
     */
    void setClickIntervalTime(long time);

    /**
     * 获取长按事件间隔时间
     * @return 长按事件间隔时间
     */
    long getLongClickIntervalTime();

    /**
     * 获取长按事件间隔时间
     * @param time 长按事件间隔时间
     */
    void setLongClickIntervalTime(long time);
}