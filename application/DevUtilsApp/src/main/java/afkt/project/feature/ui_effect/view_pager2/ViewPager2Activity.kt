package afkt.project.feature.ui_effect.view_pager2

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.databinding.ActivityViewpager2Binding
import afkt.project.model.item.RouterPath
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.therouter.router.Route
import dev.utils.app.ResourceUtils

/**
 * detail: ViewPager2
 * @author Ttt
 */
@Route(path = RouterPath.UI_EFFECT.ViewPager2Activity_PATH)
class ViewPager2Activity : BaseActivity<ActivityViewpager2Binding>() {

    override fun baseLayoutId(): Int = R.layout.activity_viewpager2

    override fun initValue() {
        super.initValue()

        val list = mutableListOf<Fragment>()
        list.add(newPagerFragment(1))
        list.add(newPagerFragment(2))
        list.add(newPagerFragment(3))
        list.add(newPagerFragment(4))

        binding.vidVp.adapter = MyPagerAdapter(this, list)
    }

    override fun initListener() {
        super.initListener()
        binding.vidTl.setTabTextColors(
            ResourceUtils.getColor(R.color.black),
            ResourceUtils.getColor(R.color.white)
        )

        // TabLayout 与 ViewPager2 联动
        TabLayoutMediator(
            binding.vidTl, binding.vidVp
        ) { tab, position ->
            tab.text = "Pager-${position + 1}"
        }.attach()
    }

    class MyPagerAdapter(
        val activity: FragmentActivity,
        val list: MutableList<Fragment>
    ) : FragmentStateAdapter(activity) {

        override fun getItemCount(): Int = list.size

        override fun createFragment(position: Int): Fragment = list[position]
    }

    // ========
    // = Menu =
    // ========

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_pager, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.vid_menu_ltr -> {
                binding.vidTl.layoutDirection = View.LAYOUT_DIRECTION_LTR
                binding.vidVp.layoutDirection = View.LAYOUT_DIRECTION_LTR
            }

            R.id.vid_menu_rtl -> {
                binding.vidTl.layoutDirection = View.LAYOUT_DIRECTION_RTL
                binding.vidVp.layoutDirection = View.LAYOUT_DIRECTION_RTL
            }

            R.id.vid_menu_horizontal -> {
                binding.vidVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            }

            R.id.vid_menu_vertical -> {
                binding.vidVp.orientation = ViewPager2.ORIENTATION_VERTICAL
            }

            R.id.vid_menu_reset -> {
                binding.vidTl.layoutDirection = View.LAYOUT_DIRECTION_LTR
                binding.vidVp.layoutDirection = View.LAYOUT_DIRECTION_LTR
                binding.vidVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            }
        }
        binding.vidVp.adapter?.notifyDataSetChanged()
        return true
    }
}