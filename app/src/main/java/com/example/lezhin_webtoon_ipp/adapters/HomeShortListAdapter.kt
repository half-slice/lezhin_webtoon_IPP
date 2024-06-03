package com.example.lezhin_webtoon_ipp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lezhin_webtoon_ipp.R
import com.example.lezhin_webtoon_ipp.activities.DetailActivity
import com.example.lezhin_webtoon_ipp.data.WebtoonItem
import com.example.lezhin_webtoon_ipp.databinding.ItemShortRecyclerBinding

class HomeShortListAdapter : ListAdapter<WebtoonItem, HomeShortListAdapter.MyViewHolder>(
    diffUtil
)  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemShortRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(currentList[position], position)
    }

    inner class MyViewHolder(private var binding: ItemShortRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WebtoonItem, position: Int){
            //웹툰 이미지
            Glide.with(binding.itemShortImageview.context)
                .load(item.img)                   //이미지의 ur
                .error(R.drawable.ic_error)   //이미지 로드 중 에러 발생시 표시할 이미지
                .into(binding.itemShortImageview)           //이미지를 설정할 이미지뷰

            //웹툰 제목
            binding.itemShortTextview.text = item.title

            binding.itemShortLayout.setOnClickListener {
                val intent = Intent(binding.root.context, DetailActivity::class.java)
                intent.putExtra("URL", item.url)
                binding.root.context.startActivity(intent)
            }

            setMarginLayout(binding.itemShortLayout, position)
        }
    }

    private fun setMarginLayout(layout: ConstraintLayout, position: Int){
        val lp = layout.layoutParams as ViewGroup.MarginLayoutParams
        if(position == 0){
            lp.setMargins(30, 0, 30, 0)
        }
        else{
            lp.setMargins(0, 0, 30, 0)
        }
        layout.layoutParams = lp
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<WebtoonItem>() {
            override fun areItemsTheSame(oldItem: WebtoonItem, newItem: WebtoonItem): Boolean {
                TODO("Not yet implemented")
            }

            override fun areContentsTheSame(oldItem: WebtoonItem, newItem: WebtoonItem): Boolean {
                TODO("Not yet implemented")
            }
        }
    }
}