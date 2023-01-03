package com.mobiria.bnft.ui.comman

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.mobiria.bnft.R
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.interfaces.ListSelector
import com.mobiria.bnft.ui.fragment.home.model.BannersItem
import com.mobiria.bnft.ui.fragment.hoodies.HoodiesFragment

class ViewPagerAdapter(private val context: BaseActivity?, val mBanners: ArrayList<BannersItem>, val mCallBack: ListSelector) :
    PagerAdapter() {

    private var layoutInflater: LayoutInflater? = null

    override fun getCount(): Int {
        return mBanners.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        layoutInflater =
            context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater!!.inflate(R.layout.layout_banner_slider, null)
        val imageView = view.findViewById<View>(R.id.imageView) as ImageView
        val tvTitle = view.findViewById<View>(R.id.tv_title) as TextView
        val btnExploreNow = view.findViewById<View>(R.id.btn_explore_now) as TextView
      //  imageView.setImageResource(mBanners.get(position))

        tvTitle.setText(mBanners.get(position).banner_title)
        if (!mBanners?.get(position).banner_image.isNullOrEmpty()) {
            Glide.with(context)
                .load(mBanners?.get(position).banner_image)
                .placeholder(R.drawable.dummy_hud_one)
                .error(R.drawable.dummy_hud_one)
                .into(imageView)
        }

        btnExploreNow.setOnClickListener {
            context.replaceFragment(HoodiesFragment())
          /*  try {
                if (mList.get(position).typeId == 4){
                    mCallBack.selectedList(mList?.get(position)?.textEn, mList?.get(position)?.linkId!!)
                } else {
                    mCallBack.selectedList(
                        mList?.get(position)?.linkId?.toString(),
                        mList?.get(position)?.typeId!!
                    )
                }
            } catch (e: Exception){
                e.printStackTrace()
            }*/
        }
        val vp = container as ViewPager
        vp.addView(view, 0)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val view = `object` as View
        vp.removeView(view)
    }
}