package com.mobiria.bnft.utils.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobiria.bnft.*
import com.mobiria.bnft.interfaces.DialogListener
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.databinding.*
import com.mobiria.bnft.ui.comman.ViewModel
import com.mobiria.bnft.ui.fragment.my_order.order_detail.ODataOrderDetail
import com.mobiria.bnft.ui.fragment.my_order.resale_interface.ResaleListener
import com.mobiria.bnft.ui.fragment.product_details.adapter.DetailItemViewModel
import com.mobiria.bnft.ui.fragment.product_details.adapter.DetailsAdapter
import com.mobiria.bnft.ui.fragment.product_details.model.ODataProductDetails

class CustomDialog {
    companion object {
        lateinit var detailsVM: ResaleDialogViewModel
        lateinit var resaleBidVM: ResaleBidDialogViewModel
        lateinit var placeBidVM: PlaceBidViewModel

        fun showDialogPlaceBid(activity: BaseActivity?, item: ODataProductDetails, listener: DialogListener?) {
            val dialog = Dialog(activity!!, R.style.Theme_Dialog)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            val binding: LayoutPlaceBidPopupBinding = DataBindingUtil.inflate(
                LayoutInflater.from(activity),
                R.layout.layout_place_bid_popup,
                null,
                false
            )
            dialog.setContentView(binding.getRoot())
            placeBidVM = PlaceBidViewModel(activity, item, dialog, listener)
            binding.viewModel = placeBidVM
            // Do Any Here

            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }


        fun showResaleDialog(activity: BaseActivity?, item: ODataOrderDetail, listener: ResaleListener?) {
            val mDetailsAdapter: DetailsAdapter = DetailsAdapter(ArrayList())
            val dialog = Dialog(activity!!, R.style.Theme_Dialog)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            val binding: LayoutResalePopupBinding = DataBindingUtil.inflate(
                LayoutInflater.from(activity),
                R.layout.layout_resale_popup,
                null,
                false
            )
            dialog.setContentView(binding.getRoot())
            with(binding) {
                rvDetails.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                rvDetails.isNestedScrollingEnabled = false
                rvDetails.adapter = mDetailsAdapter
            }

            detailsVM = ResaleDialogViewModel(activity, dialog, item, listener)
            binding.viewModel = detailsVM

            // Details List set
            if (!item.details.isNullOrEmpty()) {
                val viewModels = ArrayList<ViewModel>()
                for (i in 0 until item?.details!!.size) {
                    viewModels.add(DetailItemViewModel(activity, item.details.get(i)))
                }
                mDetailsAdapter.setList(viewModels)
            }

            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }


        fun showResaleBidDialog(activity: BaseActivity?, item: ODataOrderDetail, listener: ResaleListener?) {
            val dialog = Dialog(activity!!, R.style.Theme_Dialog)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            val binding: LayoutResaleBidPopupBinding = DataBindingUtil.inflate(
                LayoutInflater.from(activity),
                R.layout.layout_resale_bid_popup,
                null,
                false
            )
            dialog.setContentView(binding.getRoot())
            resaleBidVM = ResaleBidDialogViewModel(activity, item, dialog, listener)
            binding?.viewModel = resaleBidVM

            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }

        fun showLogoutDialog(activity: BaseActivity?, message: String? = "", listener: DialogListener?) {
            val dialog = Dialog(activity!!, R.style.Theme_Dialog)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            val binding: LogoutPopupBinding = DataBindingUtil.inflate(
                LayoutInflater.from(activity),
                R.layout.logout_popup,
                null,
                false
            )
            dialog.setContentView(binding.getRoot())
            if (!message.isNullOrEmpty()) {
                binding?.titles.text = message
            }
            binding?.dismissAlertBtn?.setOnClickListener {
                dialog.dismiss()
                if (listener != null) {
                    listener.setValue("logout", 0)
                }
            }
            binding?.cancel.setOnClickListener { dialog.dismiss() }
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }


        fun showDialogSessionExpired(activity: BaseActivity?, code: String?, message: String?, listener: DialogListener?) {
            val dialog = Dialog(activity!!, R.style.Theme_Dialog)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            val binding: SessionExpiredPopupBinding = DataBindingUtil.inflate(
                LayoutInflater.from(activity),
                R.layout.session_expired_popup,
                null,
                false
            )
            dialog.setContentView(binding.getRoot())
            binding?.tvCode?.text = code
            if (!message.isNullOrEmpty()) {
                binding?.titles?.text = message
            }
            binding?.dismissAlertBtn?.setOnClickListener {
                dialog.dismiss()
                if (listener != null) {
                    listener.setValue("session_expired", 0)
                }
            }
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }


        fun showWelcomeDialog(activity: BaseActivity?, message: String? = "", listener: DialogListener?) {
            val dialog = Dialog(activity!!, R.style.Theme_Dialog)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            val binding: WelcomePopupBinding = DataBindingUtil.inflate(
                LayoutInflater.from(activity),
                R.layout.welcome_popup,
                null,
                false
            )
            dialog.setContentView(binding.getRoot())
            if (!message.isNullOrEmpty()) {
                binding?.titles.text = message
            }
            binding?.dismissAlertBtn?.setOnClickListener {
                dialog.dismiss()
                if (listener != null) {
                    listener.setValue("welcome", 0)
                }
            }
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }
    }
}