package com.mobiria.bnft.ui.fragment.product_details

import android.graphics.Color
import android.graphics.DashPathEffect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.Utils
import com.mobiria.bnft.R
import com.mobiria.bnft.base.BaseFragment
import com.mobiria.bnft.databinding.FragmentDetailsBinding
import com.mobiria.bnft.interfaces.DialogListener
import com.mobiria.bnft.ui.comman.ViewModel
import com.mobiria.bnft.ui.fragment.home.dropped.DroppedAdapter
import com.mobiria.bnft.ui.fragment.home.dropped.DroppedItemViewModel
import com.mobiria.bnft.ui.fragment.product_details.adapter.*
import com.mobiria.bnft.ui.fragment.product_details.model.ODataProductDetails
import com.mobiria.bnft.ui.fragment.product_details.model.SizeItem
import com.mobiria.bnft.ui.fragment.product_details.properties.PropertiesAdapter
import com.mobiria.bnft.ui.fragment.product_details.properties.PropertiesItemViewModel
import com.mobiria.bnft.utils.Constants
import com.mobiria.bnft.utils.dialog.CustomDialog
import com.mobiria.bnft.utils.myToast
import kotlinx.coroutines.launch

class DetailsFragment(val isTrue: Boolean = false, val isAuction: Boolean = false) : BaseFragment(),
    DialogListener, ItemCallBack{
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var detailsVM: DetailsViewModel

    private val mBidHistoryAdapter: BidHistoryAdapter = BidHistoryAdapter(ArrayList())
    private val mDroppedAdapter: DroppedAdapter = DroppedAdapter(ArrayList())
    private val mSizeAdapter: SizeAdapter = SizeAdapter(ArrayList())
    private val mDetailsAdapter: DetailsAdapter = DetailsAdapter(ArrayList())
    private val mPropertiesAdapter: PropertiesAdapter = PropertiesAdapter(ArrayList())

     var productId: String? = null

    fun newInstance(productId: String?): DetailsFragment {
        this.productId = productId
        return DetailsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentDetailsBinding.bind(inflater.inflate(R.layout.fragment_details, container, false))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity.toolbarVM.manageToolBar(true, false, false, true, true, "Hoodies",
        true, true)

        // Call Size List
        sizeListSetup()
        // Properties Size List
        propertiesListSetup()
        // Call Size List
        detailListSetup()
        // Call Bid History
        bidHistory()
        // Collection List Setup
        collectionListSetup()

        // MAP  Setup
        mapSetup()
    }

    override fun onResume() {
        super.onResume()
        if (mActivity.isInternetAvailable()){
            productDetailsAPI(productId)
        }
    }

    // Size List Setup
    private fun sizeListSetup(){
        with(binding) {
            rvSize.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            rvSize.isNestedScrollingEnabled = false
            rvSize.adapter = mSizeAdapter
        }
    }


    // Details List Setup
    private fun detailListSetup(){
        with(binding) {
            rvDetails.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            rvDetails.isNestedScrollingEnabled = false
            rvDetails.adapter = mDetailsAdapter
        }
    }

    // Properties List Setup
    private fun propertiesListSetup(){
        with(binding) {
            rvProperties.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            rvProperties.isNestedScrollingEnabled = false
            rvProperties.adapter = mPropertiesAdapter
        }
    }

    // BID History
    private fun bidHistory(){
        with(binding) {
            rvBidHistory.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            rvBidHistory.isNestedScrollingEnabled = false
            rvBidHistory.adapter = mBidHistoryAdapter
        }
    }

    // Collection List Setup
    private fun collectionListSetup(){
        with(binding) {
            rvCollection.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            rvCollection.isNestedScrollingEnabled = false
            rvCollection.adapter = mDroppedAdapter
        }
    }

    // Product Details API CALL
    fun productDetailsAPI(productId: String?){
        startAnim()
        mActivity.viewModel.productDetailsAPI(pref.getStringData(Constants.ACCESS_TOKEN)!!, productId)
        mActivity.viewModel.productDetailsLiveData.observe(viewLifecycleOwner, Observer {
            stopAnim()
            if (it.status.equals("1")) {
                lifecycleScope.launch {
                    mActivity.toolbarVM.manageToolBar(true, false, false, true, true, it?.oData?.product?.name.toString(),
                        true, true, productId.toString(), it?.oData?.product?.share)
                    // initialize View Model
                    detailsVM = DetailsViewModel(mActivity, it?.oData!!, this@DetailsFragment)
                    binding.viewModel = detailsVM
                    detailsVM.isTrue.set(isTrue)
                    detailsVM.isAuction.set(isAuction)
                    detailsVM.mProductId.set(productId)

                    // Is Favorites
                    mActivity.toolbarVM.isFavoriteTrue.set(it.oData.product?.is_favorite!!)

                    detailsVM.mProductDetails.set(it.oData)
                    dataSetup(it.oData)
                }
            } else {
                mActivity?.myToast(it?.message!!)
            }
        })
    }


    // All UI Data Setup
    private fun dataSetup(item: ODataProductDetails?){
        // Size List set
        if (!item?.size.isNullOrEmpty()) {
            val sizeViewModels = ArrayList<ViewModel>()
            for (i in 0 until item?.size!!.size) {
                sizeViewModels.add(SizeItemViewModel(mActivity, item?.size.get(i), this))
            }
            mSizeAdapter.setList(sizeViewModels)
        }

        // Details List set
        if (!item?.details.isNullOrEmpty()) {
            val viewModels = ArrayList<ViewModel>()
            for (i in 0 until item?.details!!.size) {
                viewModels.add(DetailItemViewModel(mActivity, item.details.get(i)))
            }
            mDetailsAdapter.setList(viewModels)
        }
        // Properties List set
        if (!item?.properties.isNullOrEmpty()) {
            val viewModels = ArrayList<ViewModel>()
            for (i in 0 until item?.properties!!.size) {
                viewModels.add(PropertiesItemViewModel(mActivity, item.properties.get(i)))
            }
            mPropertiesAdapter.setList(viewModels)
        }

        // Collection List
        if (!item?.more_collections.isNullOrEmpty()) {
            val viewModels = ArrayList<ViewModel>()
            for (i in 0 until item?.more_collections!!.size) {
                viewModels.add(DroppedItemViewModel(mActivity, item.more_collections.get(i), this))
            }
            mDroppedAdapter.setList(viewModels)
        }

        if (isTrue) {
            // Bid History List
            if (!item?.bid_history.isNullOrEmpty()) {
                val viewModels = ArrayList<ViewModel>()
                for (i in 0 until item?.bid_history!!.size) {
                    viewModels.add(BidHistoryItemViewModel(mActivity, item?.bid_history.get(i)))
                }
                mBidHistoryAdapter.setList(viewModels)
            }

            // Price History
            if (!item?.price_history.isNullOrEmpty()) {
                setData(item?.price_history!!)
            }

        }

    }

    // Size item Selection refresh
    override fun itemRefresh(item: SizeItem) {
        val list : MutableList<ViewModel> = mSizeAdapter.getList()
        for (listItem in list) {
            if (listItem is SizeItemViewModel) {
                val itemVm = listItem as SizeItemViewModel
                if (item.id == itemVm.item.id) {
                    detailsVM.mSizeText.set(item.text)
                    detailsVM.mSizeId.set(item.id)
                    itemVm.isTrueItem.set(true)
                } else {
                    itemVm.isTrueItem.set(false)
                }
            }
        }
        mSizeAdapter.notifyDataSetChanged()
    }

    fun mapSetup(){
        // background color
        binding.chart.setBackgroundColor(Color.WHITE)

        // disable description text
        binding.chart.getDescription().setEnabled(false)

        // enable touch gestures
        binding.chart.setTouchEnabled(true)

        // set listeners
        binding.chart.setDrawGridBackground(false)

        // enable scaling and dragging
        binding.chart.setDragEnabled(true)
        binding.chart.setScaleEnabled(true)
        // chart.setScaleXEnabled(true);

        // force pinch zoom along both axis
       binding.chart.setPinchZoom(true)

        //setData(45, 180f)
    }


    private fun setData(item : ArrayList<String>) {
        val values = java.util.ArrayList<Entry>()
        for (i in 0 until item.size) {
           // val `val` = (Math.random() * range).toFloat() - 30
            try {
                values.add(
                    Entry(
                        i.toFloat(),
                        item.get(i).toFloat(),
                        resources.getDrawable(R.drawable.star)
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        val set1: LineDataSet
        if (binding.chart.getData() != null &&
            binding.chart.getData().getDataSetCount() > 0
        ) {
            set1 = binding.chart.getData().getDataSetByIndex(0) as LineDataSet
            set1.values = values
            set1.notifyDataSetChanged()
            binding.chart.getData().notifyDataChanged()
            binding.chart.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(values, "DataSet 1")
            set1.setDrawIcons(false)

            // draw dashed line
            set1.enableDashedLine(10f, 5f, 0f)

            // black lines and points
            set1.color = Color.BLACK
            set1.setCircleColor(Color.BLACK)

            // line thickness and point size
            set1.lineWidth = 1f
            set1.circleRadius = 3f

            // draw points as solid circles
            set1.setDrawCircleHole(false)

            // customize legend entry
            set1.formLineWidth = 1f
            set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            set1.formSize = 15f

            // text size of values
            set1.valueTextSize = 9f

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f)

            // set the filled area
            set1.setDrawFilled(true)
            set1.fillFormatter =
                IFillFormatter { dataSet, dataProvider -> binding.chart.getAxisLeft().getAxisMinimum() }

            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                val drawable = ContextCompat.getDrawable(mActivity, R.drawable.fade_map_bg)
                set1.fillDrawable = drawable
            } else {
                set1.fillColor = Color.BLACK
            }
            val dataSets = java.util.ArrayList<ILineDataSet>()
            dataSets.add(set1) // add the data sets

            // create a data object with the data sets
            val data = LineData(dataSets)

            // set data
            binding.chart.setData(data)
        }
    }

    override fun setValue(value: String, position: Int) {
        if (mActivity.isInternetAvailable()) {
            placeBidAPI(value)
        }
    }


    // Place Bid API CALL
    private fun placeBidAPI(salePrice: String){
        startAnim()
        mActivity?.viewModel.placeBidAPI(pref.getStringData(Constants.ACCESS_TOKEN)!!, productId, salePrice)
        mActivity?.viewModel.placeBidLiveData.observe(viewLifecycleOwner, Observer {
            stopAnim()
            if (it.status.equals("1")) {
                lifecycleScope.launch {
                    CustomDialog.showWelcomeDialog(mActivity, it?.message, object : DialogListener {
                        override fun setValue(value: String, position: Int) {
                            mActivity.onBackPressed()
                        }
                    })
                }
            } else {
                CustomDialog.showWelcomeDialog(mActivity, it?.message, object : DialogListener {
                    override fun setValue(value: String, position: Int) {

                    }
                })
            }
        })
    }
}