package com.mobiria.bnft.ui.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.mobiria.bnft.R
import com.mobiria.bnft.interfaces.ListSelector
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.base.BaseFragment
import com.mobiria.bnft.databinding.FragmentHomeBinding
import com.mobiria.bnft.ui.comman.ViewModel
import com.mobiria.bnft.ui.comman.ViewPagerAdapter
import com.mobiria.bnft.ui.fragment.home.dropped.DroppedAdapter
import com.mobiria.bnft.ui.fragment.home.dropped.DroppedItemViewModel
import com.mobiria.bnft.ui.fragment.home.hud_and_auctions.AuctionsItemViewModel
import com.mobiria.bnft.ui.fragment.home.hud_and_auctions.AuctionsListAdapter
import com.mobiria.bnft.ui.fragment.home.hud_and_auctions.HoodCategoryListAdapter
import com.mobiria.bnft.ui.fragment.home.hud_and_auctions.HoodCattegoryItemViewModel
import com.mobiria.bnft.ui.fragment.home.model.BannersItem
import com.mobiria.bnft.ui.fragment.home.model.ODataHome
import com.mobiria.bnft.utils.DataFactory
import com.mobiria.bnft.utils.myToast
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : BaseFragment(), ListSelector {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeVM: HomeViewModel
    private lateinit var dots: Array<ImageView>

    // Dropped List Adapter
   // private val mDroppedAdapter: DroppedAdapter = DroppedAdapter(ArrayList())
    private val mPopularCollection: DroppedAdapter = DroppedAdapter(ArrayList())
    private val mHoodCategoryListAdapter: HoodCategoryListAdapter = HoodCategoryListAdapter(ArrayList())
    private val mAuctionsListAdapter: AuctionsListAdapter = AuctionsListAdapter(ArrayList())
  //  private val mCarouselAdapter: CarouselAdapter = CarouselAdapter(ArrayList())

    val hoodiesViewModels = ArrayList<ViewModel>()
    val auctionViewModels = ArrayList<ViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentHomeBinding.bind(inflater.inflate(R.layout.fragment_home, container, false))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity?.toolbarVM?.manageToolBar(true,true, true)
        //View Model Initialize
        homeVM = HomeViewModel(mActivity, this)
        binding.viewModel = homeVM


        // Call Dropped List Setup
        //droppedListSetup()
        // Carousel
        //carouselListSetup()
        // Hoodies Category List setup
    //    hoodCategoryListSetup()
        // Popular Collections list Setup
        popularCollectionsListSetup()

        if (mActivity?.isInternetAvailable()) {
            if (DataFactory.homeData == null) {
                homeGetAPI()
            } else {
                setHomeData(DataFactory.homeData!!)
            }
        }
    }

    // Sliding Banners
    private fun bannerImageSlider(banners: ArrayList<BannersItem>){
        // slider
        binding.SliderDots?.removeAllViews()
        val viewPagerAdapter =
            ViewPagerAdapter(mActivity, banners, object : ListSelector{
                override fun selectedList(id: String?, position: Int) {
                }
            })
        binding.viewPager.adapter = viewPagerAdapter

        val timer = Timer()
        timer.scheduleAtFixedRate(
            MyTimerTask(mActivity, binding.viewPager),
            5000,
            6000
        )
        dots =
            arrayOfNulls<ImageView>(banners.size) as Array<ImageView>
        for (i in 0 until banners.size) {
            dots[i] = ImageView(context)
            dots.get(i).setImageDrawable(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_unactive_dot)
            )
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 0, 8, 0)
            binding.SliderDots?.addView(dots[i], params)
        }


        dots.get(0)
            .setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_active_dot
                )
            )
        binding.viewPager.addOnPageChangeListener(object :
            ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                for (i in 0 until banners.size) {
                    dots[i].setImageDrawable(
                        ContextCompat.getDrawable(
                            mActivity!!,
                            R.drawable.ic_unactive_dot
                        )
                    )
                }
                dots[position].setImageDrawable(
                    ContextCompat.getDrawable(
                        mActivity!!,
                        R.drawable.ic_active_dot
                    )
                )
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

    }
    class MyTimerTask(var mActivity: BaseActivity?, var mPager: ViewPager) : TimerTask() {
        override fun run() {
            mActivity?.runOnUiThread(Runnable {
                try {
                    if (mPager.getCurrentItem() == 0) {
                        mPager.setCurrentItem(1)
                    } else if (mPager.getCurrentItem() == 1) {
                        mPager.setCurrentItem(0) //2
                    } else {
                        mPager.setCurrentItem(0)
                    }
                } catch (e: Exception) {
                    Log.e("exception", e.message.toString())
                }
            })
        }
    }

  /*  // Dropped List Setup
    private fun droppedListSetup(){
        with(binding) {
            rvJustDropped.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            rvJustDropped.isNestedScrollingEnabled = false
            rvJustDropped.adapter = mDroppedAdapter
        }
        val viewModels = ArrayList<ViewModel>()
        for (i in 0..4) {
            viewModels.add(DroppedItemViewModel(mActivity))
        }
        mDroppedAdapter.setList(viewModels)
    }


    private fun carouselListSetup(){
      binding?.itemList?.initialize(mCarouselAdapter)
      val viewModels = ArrayList<ViewModel>()
     // for (i in 0..4) {
          viewModels.add(CarouselItemViewModel(mActivity, CarouselModel(R.drawable.dummy_hud_one)))
          viewModels.add(CarouselItemViewModel(mActivity, CarouselModel(R.drawable.dummy_hud_two)))
          viewModels.add(CarouselItemViewModel(mActivity, CarouselModel(R.drawable.dummy_hud_one)))
          viewModels.add(CarouselItemViewModel(mActivity, CarouselModel(R.drawable.dummy_hud_two)))
          viewModels.add(CarouselItemViewModel(mActivity, CarouselModel(R.drawable.dummy_hud_one)))
          viewModels.add(CarouselItemViewModel(mActivity, CarouselModel(R.drawable.dummy_hud_two)))
     // }
      mCarouselAdapter.setList(viewModels)
    }*/

    // Auctions and hoody List Setup
    private fun auctionsListSetup(){
        with(binding) {
            rvComman.layoutManager = GridLayoutManager(requireContext(), 2)
            rvComman.isNestedScrollingEnabled = false
            rvComman.adapter = mAuctionsListAdapter
        }
    }

    //  hoodies Category List Setup
    private fun hoodCategoryListSetup(){
        with(binding) {
            rvComman.layoutManager = GridLayoutManager(requireContext(), 2)
            rvComman.isNestedScrollingEnabled = false
            rvComman.adapter = mHoodCategoryListAdapter
        }
    }

    // Popular Collections List Setup
    private fun popularCollectionsListSetup(){
        with(binding) {
            rvPopularCollection.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            rvPopularCollection.isNestedScrollingEnabled = false
            rvPopularCollection.adapter = mPopularCollection
        }
    }

    override fun selectedList(id: String?, position: Int) {
        if (id.equals("hod")) {
            hoodCategoryListSetup()
            mHoodCategoryListAdapter.setList(hoodiesViewModels)
        } else if (id.equals("auction")){
          auctionsListSetup()
            mAuctionsListAdapter?.setList(auctionViewModels)
        } else {

        }
    }

    private fun homeGetAPI(){
        startAnim()
        mActivity?.viewModel.homeAPI()
        mActivity?.viewModel.homeLiveData.observe(viewLifecycleOwner, Observer {
            stopAnim()
            if (it.status.equals("1")) {
                lifecycleScope.launch {
                    DataFactory.homeData = it?.oData
                    setHomeData(it?.oData!!)
                }
            } else {
                mActivity?.myToast(it?.message!!)
            }
        })
    }

    private fun setHomeData(model: ODataHome){
        // SET BANNER IMAGE SETUP
        if(!model?.banners.isNullOrEmpty()) {
            bannerImageSlider(model?.banners!!)
        }

        // CATEGORY LIST SETUP
        if (!model?.categories.isNullOrEmpty()){
            hoodiesViewModels?.clear()
            hoodCategoryListSetup()
            for (i in 0..(model?.categories?.size!!-1)) {
                hoodiesViewModels.add(HoodCattegoryItemViewModel(mActivity, model?.categories?.get(i)))
            }
            mHoodCategoryListAdapter.setList(hoodiesViewModels)
        }

        // AUCTION LIST SETUP
        if (!model?.categories.isNullOrEmpty()){
            auctionViewModels.clear()
            hoodCategoryListSetup()
            for (i in 0..(model?.auctions?.size!!-1)) {
                auctionViewModels.add(AuctionsItemViewModel(mActivity, model?.auctions?.get(i)!!, true))
            }
            mAuctionsListAdapter.setList(auctionViewModels)
        }


        // POPULAR COLLECTION
        if(!model?.popular_collections.isNullOrEmpty()) {
            val viewModels = ArrayList<ViewModel>()
            for (i in 0..(model?.popular_collections!!.size-1)) {
                viewModels.add(DroppedItemViewModel(mActivity, model?.popular_collections?.get(i)!!))
            }
            mPopularCollection.setList(viewModels)
        }
    }

}