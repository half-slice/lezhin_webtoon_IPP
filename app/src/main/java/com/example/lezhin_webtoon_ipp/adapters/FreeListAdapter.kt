package com.example.lezhin_webtoon_ipp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setPadding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lezhin_webtoon_ipp.R
import com.example.lezhin_webtoon_ipp.activities.DetailActivity
import com.example.lezhin_webtoon_ipp.data.WebtoonItem
import com.example.lezhin_webtoon_ipp.databinding.ItemFreeRecyclerBinding


class FreeListAdapter: ListAdapter<WebtoonItem, FreeListAdapter.MyViewHolder>(diffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemFreeRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class MyViewHolder(private var binding: ItemFreeRecyclerBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: WebtoonItem){
            //이미지 설정
            Glide.with(binding.itemFreeImageview.context)
                .load(item.img)
                .error(R.drawable.ic_character)
                .into(binding.itemFreeImageview)

            binding.itemFreeTitle.text = item.title

            binding.itemFreeAuthor.text = item.author

            //신작 여부 확인
            setStatusIcon(item.additional.isNew, binding.itemFreeNewImageview)

            //휴재 여부 확인
            setStatusIcon(item.additional.isRest, binding.itemFreeRestImageview)

            //업로드 여부 확인
            setStatusIcon(item.additional.isUp, binding.itemFreeUpImageview)

            // 레이아웃 클릭 이벤트 처리
            binding.itemFreeLayout.setOnClickListener {
                val intent = Intent(binding.root.context, DetailActivity::class.java)
                intent.putExtra("URL", item.url)
                binding.root.context.startActivity(intent)
            }

            //레이아웃 패딩 설정
            setPaddingLayout(binding.itemFreeLayout)
        }
    }

    private fun setStatusIcon(status:Boolean, imageview:ImageView){
        if (status) {
            val lp = imageview.layoutParams as ViewGroup.MarginLayoutParams
            lp.marginEnd = 5
            imageview.layoutParams = lp
        } else {
            imageview.setImageDrawable(null)
        }
    }

    private fun setPaddingLayout(layout: ConstraintLayout){
        layout.setPadding(20)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<WebtoonItem>(){
            override fun areItemsTheSame(oldItem: WebtoonItem, newItem: WebtoonItem): Boolean {
                TODO("Not yet implemented")
            }

            override fun areContentsTheSame(oldItem: WebtoonItem, newItem: WebtoonItem): Boolean {
                TODO("Not yet implemented")
            }
        }
    }
}
