package com.mobiria.bnft.ui.fragment.cms.faq.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mobiria.bnft.R
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.databinding.LayoutFaqsItemBinding
import com.mobiria.bnft.ui.fragment.cms.faq.ODataItem

class FaqsAdapter(): RecyclerView.Adapter<FaqsAdapter.ViewHolder>() {
    private var itemList: ArrayList<ODataItem>? = null
    private var activity: BaseActivity? = null


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val mBinding = DataBindingUtil.inflate<LayoutFaqsItemBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.layout_faqs_item, viewGroup, false
        )
        return ViewHolder(mBinding)
    }

    override fun onBindViewHolder(ViewHolder: ViewHolder, i: Int) {
        val model = itemList?.get(i)

     /*   ViewHolder.mBinding.expandable.parentLayouts.parent_title?.text = model?.title

        ViewHolder.mBinding.expandable.childLayouts?.child_title?.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(model?.description, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(model?.description)
        }
*/

        ViewHolder.mBinding.expandable.setOnExpandListener {
           /* if (it){
              //  ViewHolder.mBinding.arrowImg.setImageResource(R.drawable.ic_arrow_up)
            }else{
              //  ViewHolder.mBinding.arrowImg.setImageResource(R.drawable.ic_down)
            }*/
        }


        ViewHolder.mBinding.expandable.parentLayout.setOnClickListener {
            ViewHolder.mBinding.expandable.toggleLayout()

        }
    }


    override fun getItemCount(): Int {
        return if (itemList == null) 0 else itemList!!.size
    }


    fun setFaqsList(
        activity: BaseActivity,
        itemList: ArrayList<ODataItem>?
    ) {
        this.itemList = itemList
        this.activity = activity
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mBinding: LayoutFaqsItemBinding) :
        RecyclerView.ViewHolder(mBinding.root)

}