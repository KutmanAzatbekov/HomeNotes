package com.geeks.homenotes.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.geeks.homenotes.data.models.OnBoardModel
import com.geeks.homenotes.databinding.ItemOnBoardBinding

class OnBoardAdapter(val listOnBoard: List<OnBoardModel>, val onStart:()-> Unit, val onSkip: (Int) -> Unit) : RecyclerView.Adapter<OnBoardAdapter.OnBoardViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OnBoardViewHolder {
        return OnBoardViewHolder(ItemOnBoardBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(
        holder: OnBoardViewHolder,
        position: Int
    ) {
        holder.onBind(listOnBoard[position])
    }

    override fun getItemCount() = listOnBoard.size
    inner class OnBoardViewHolder(private val binding: ItemOnBoardBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(model: OnBoardModel) {
            binding.apply {
                tvTitle.text = model.title
                tvDesc.text = model.desc
                Glide.with(root.context).asGif().load(model.gifResId).into(ivPicture)

                if (adapterPosition == listOnBoard.size -1){
                    tvSkip.visibility = View.INVISIBLE
                }
                tvSkip.setOnClickListener {
                    onSkip(listOnBoard.size)
                }
            }
        }

    }

}