package com.mobiria.bnft.ui.fragment.notification

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.mobiria.bnft.R
import com.mobiria.bnft.base.BaseFragment
import com.mobiria.bnft.databinding.FragmentNotificationBinding
import com.mobiria.bnft.firebase.NotificationModel
import com.mobiria.bnft.ui.comman.ViewModel

class NotificationFragment : BaseFragment() {
    private lateinit var binding: FragmentNotificationBinding
    private val mNotificationAdapter: NotificationAdapter = NotificationAdapter(ArrayList())
    val viewModels = ArrayList<ViewModel>()

    // FireBase
    var myRef: DatabaseReference? = null
    var data_key: Query? = null
    var database: FirebaseDatabase? = null
    var listner: ValueEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentNotificationBinding.bind(inflater.inflate(R.layout.fragment_notification, container, false))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity.toolbarVM.manageToolBar(true, false, false, true, true,
            mActivity?.resources.getString(R.string.notifications), false, false)
        mActivity.toolbarVM.isAllClear.set(true)
        // Notification List Setup call
        notificationListSetup()
        database = FirebaseDatabase.getInstance()
        myRef = database!!.getReference()
        if (mActivity.isInternetAvailable()) {
            getNotificationListFromFirebase()
        }
    }

    // Notification List Setup
    private fun notificationListSetup(){
        with(binding) {
            rvNotification.layoutManager = LinearLayoutManager(requireContext())
            rvNotification.isNestedScrollingEnabled = false
            rvNotification.adapter = mNotificationAdapter
        }
    }


    private fun getNotificationListFromFirebase() {
        mActivity.startAnim()
        val firebaseKey: String? = mActivity.userLogin?.firebase_user_key
        data_key = myRef?.child("Notifications")!!.child(firebaseKey!!)
        listner = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                viewModels.clear()
                if (dataSnapshot.childrenCount > 0) {
                    for (singleSnapshot in dataSnapshot.children) {
                        binding.emptyLayout.layoutMain.visibility = View.GONE
                        if (singleSnapshot.value != null) {
                            try {
                                val model: NotificationModel? =
                                    singleSnapshot.getValue(NotificationModel::class.java)
                                model?.setNotificationId(singleSnapshot.key)
                                if (model != null) {
                                    viewModels.add(NotificationItemViewModel(mActivity, model))
                                }

                                try {
                                    if (model?.getRead().equals("0")) {
                                        val model_list: MutableMap<String, Any> = HashMap()
                                        model_list["read"] = "1"
                                        myRef?.child("Notifications")?.child(mActivity.userLogin?.firebase_user_key!!)?.child(
                                            singleSnapshot.key!!
                                        )?.updateChildren(model_list)
                                    }
                                } catch (e: NullPointerException) {
                                    mActivity.stopAnim()
                                    Log.d("error", e.message!!)
                                    //   rvNotificationVisibility.set(false)
                                }


                            } catch (e: Exception){
                                mActivity.stopAnim()
                                binding.emptyLayout.layoutMain.visibility = View.VISIBLE
                                e.printStackTrace()
                            }

                        }
                    }

                    viewModels.reverse()
                    mActivity.stopAnim()
                    mNotificationAdapter.setList(viewModels)

                } else {
                    mActivity.stopAnim()
                    binding.emptyLayout.layoutMain.visibility = View.VISIBLE
                    viewModels.clear()
                    mNotificationAdapter.setList(viewModels)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                mActivity.stopAnim()
                binding.emptyLayout.layoutMain.visibility = View.VISIBLE
            }
        }

        data_key?.addValueEventListener(listner as ValueEventListener)
    }



}