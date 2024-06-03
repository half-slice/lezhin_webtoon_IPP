package com.example.lezhin_webtoon_ipp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lezhin_webtoon_ipp.R
import com.example.lezhin_webtoon_ipp.databinding.ItemWaitFreeRecyclerBinding

class FreeWaitListAdapter : ListAdapter<Int, FreeWaitListAdapter.MyViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemWaitFreeRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(currentList[position], position)
    }

    inner class MyViewHolder(private var binding: ItemWaitFreeRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Int, position: Int){
            //이미지 설정
            Glide.with(binding.itemWaitFreeImageview.context)
                .load(item)
                .error(R.drawable.ic_character)
                .into(binding.itemWaitFreeImageview)

            //이미지 마진 설정
            setMarginImage(binding.itemWaitFreeImageview, position)
        }
    }

    private fun setMarginImage(imageView: ImageView, position: Int){
        val lp = imageView.layoutParams as ViewGroup.MarginLayoutParams
        Log.d("position number", position.toString())
        if(position == 0){
            lp.setMargins(30, 0, 30, 0)
        }
        else{
            lp.setMargins(0, 0, 30, 0)
        }
        imageView.layoutParams = lp
    }


    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Int>() {
            override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
                TODO("Not yet implemented")
            }

            override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
                TODO("Not yet implemented")
            }
        }
    }
}
