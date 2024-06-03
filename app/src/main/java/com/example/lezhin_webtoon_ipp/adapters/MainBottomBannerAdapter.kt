package com.example.lezhin_webtoon_ipp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lezhin_webtoon_ipp.databinding.ItemBottomBannerBinding

class MainBottomBannerAdapter(private val bannerList: List<Int>) :
    RecyclerView.Adapter<MainBottomBannerAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemBottomBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(bannerList[position % bannerList.size])
    }

    override fun getItemCount(): Int = Int.MAX_VALUE


    class MyViewHolder(private var binding: ItemBottomBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imageResId: Int) {
            binding.itemBottomBannerImage.setImageResource(imageResId)
        }
    }
}
