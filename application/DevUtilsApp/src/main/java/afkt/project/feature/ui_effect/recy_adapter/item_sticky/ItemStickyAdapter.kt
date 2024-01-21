package afkt.project.feature.ui_effect.recy_adapter.item_sticky

import afkt.project.R
import afkt.project.databinding.AdapterItemStickyBinding
import android.view.ViewGroup
import dev.adapter.DevDataAdapter
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder

/**
 * detail: 吸附 Item 预览 View Adapter
 * @author Ttt
 */
class ItemStickyAdapter(data: List<ItemStickyBean>) : DevDataAdapter<ItemStickyBean, DevBaseViewBindingVH<AdapterItemStickyBinding>>() {

    init {
        setDataList(data, false)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevBaseViewBindingVH<AdapterItemStickyBinding> {
        return newBindingViewHolder(parent, R.layout.adapter_item_sticky)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewBindingVH<AdapterItemStickyBinding>,
        position: Int
    ) {
        val item = getDataItem(position)
        holder.binding.vidTitleTv.text = item.title
        holder.binding.vidTimeTv.text = item.timeFormat
    }
}