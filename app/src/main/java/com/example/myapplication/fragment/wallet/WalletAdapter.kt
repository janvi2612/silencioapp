package com.example.myapplication.fragment.wallet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.myapplication.R
import com.example.myapplication.databinding.WalletListBinding
import com.example.myapplication.model.FriendListModel
import com.example.myapplication.util.DiffUtilExt
import com.example.myapplication.util.Utils

class WalletAdapter: RecyclerView.Adapter<WalletAdapter.MyViewholder>(){
    private var calllist = emptyList<FriendListModel>()
    class MyViewholder (private val binding: WalletListBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: FriendListModel) {
            binding.imgviewwalletrecycler.load(currentItem.profilePic){
                crossfade(true)
                transformations(CircleCropTransformation())
                error(R.drawable.imagepersonprofile)
            }
            binding.textView.text =  String.format("%,.2f",currentItem.amount)
//            val sdf = SimpleDateFormat("hh", Locale.getDefault())
//            val date = Date((currentItem.friendId?.lastLogin?.toLong() ?: 0L))
//            val format = sdf.format(date)
            if (currentItem.friendId?.isActive == true && currentItem.timeStamp != 0L){
                binding.txttime.text = itemView.context.getString(
                    R.string.wallet_friend_list_inactivation_formatter,
                    Utils.timeAgo(currentItem?.timeStamp ?: 0L)
                )

            }
            else{
                binding.txttime.text = itemView.context.getText(R.string.wallet_friend_list_inactivation_formatter)
            }
        }

        companion object {

            fun from(parent: ViewGroup): MyViewholder {
                val layoutManager = LayoutInflater.from(parent.context)
                val binding = WalletListBinding.inflate(layoutManager, parent, false)
                return WalletAdapter.MyViewholder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        return MyViewholder.from(parent)
    }

    override fun getItemCount(): Int {
        return calllist.size
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        val currentItem=calllist.getOrNull(position)
        currentItem?.let {
            holder.bind(it)
        }

    }
    fun setData(WalletResponse: List<FriendListModel>){
        val diffUtil = DiffUtilExt(calllist,WalletResponse)
        val diffResult = DiffUtil.calculateDiff(diffUtil)

        calllist = WalletResponse
        diffResult.dispatchUpdatesTo(this)

    }
}