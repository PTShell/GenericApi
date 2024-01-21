package afkt.project.feature.ui_effect.recy_adapter.linear_snap

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.model.bean.ItemBean
import afkt.project.model.bean.ItemBean.Companion.newItemBean
import afkt.project.model.item.RouterPath
import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.therouter.router.Route
import dev.expand.engine.log.log_dTag
import dev.utils.app.ListViewUtils
import dev.utils.app.helper.quick.QuickHelper

/**
 * detail: LinearSnapHelper - 无限滑动
 * @author Ttt
 * LinearSnapHelper : 滑动多页居中显示, 类似 Gallery
 */
@Route(path = RouterPath.UI_EFFECT.LinearSnapMAXActivity_PATH)
class LinearSnapMAXActivity : BaseActivity<BaseViewRecyclerviewBinding>() {

    lateinit var adapter: LinearSnapMAXAdapter

    override fun baseLayoutId(): Int = R.layout.base_view_recyclerview

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val parent = binding.vidRv.parent as? ViewGroup
        // 根布局处理
        QuickHelper.get(parent).setPadding(0)
    }

    override fun initValue() {
        super.initValue()

        val lists = mutableListOf<ItemBean>()
        for (i in 0..9) lists.add(newItemBean())

        // 初始化布局管理器、适配器
        adapter = LinearSnapMAXAdapter(lists)
        binding.vidRv.layoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false) // VERTICAL
        adapter.bindAdapter(binding.vidRv)
        val helper = LinearSnapHelper()
        helper.attachToRecyclerView(binding.vidRv)
        val size = lists.size
        // 滑动到中间 ( 无滑动过程 )
        (binding.vidRv.layoutManager as LinearLayoutManager?)?.scrollToPositionWithOffset(
            size * 100 - 1,
            10
        )
        // 复位到中间
        ListViewUtils.smoothScrollToPosition(binding.vidRv, size * 100 + 1)
    }

    override fun initListener() {
        super.initListener()
        binding.vidRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(
                recyclerView: RecyclerView,
                newState: Int
            ) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val layoutManager = recyclerView.layoutManager
                    if (layoutManager is LinearLayoutManager) {
                        // 获取最后一个可见 view 的位置
                        val lastItemPosition = layoutManager.findLastVisibleItemPosition()
                        // 获取第一个可见 view 的位置
                        val firstItemPosition = layoutManager.findFirstVisibleItemPosition()
                        // 获取居中索引
                        val currentPosition = (lastItemPosition + firstItemPosition) / 2
                        // 真实索引
                        val index = adapter.getRealIndex(currentPosition)
                        TAG.log_dTag(
                            message = "%s - %s 当前显示索引: %s - %s",
                            args = arrayOf(
                                lastItemPosition,
                                firstItemPosition,
                                currentPosition,
                                index
                            )
                        )
                    }
                }
            }
        })
    }
}