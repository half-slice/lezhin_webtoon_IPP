package com.example.lezhin_webtoon_ipp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setPadding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.lezhin_webtoon_ipp.R
import com.example.lezhin_webtoon_ipp.activities.DetailActivity
import com.example.lezhin_webtoon_ipp.data.WebtoonItem
import com.example.lezhin_webtoon_ipp.databinding.ItemContentsSeriesRecyclerBinding

class ContentsSeriesListAdapter : ListAdapter<WebtoonItem, ContentsSeriesListAdapter.MyViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemContentsSeriesRecyclerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class MyViewHolder(private var binding: ItemContentsSeriesRecyclerBinding) :
        ViewHolder(binding.root) {
        fun bind(item: WebtoonItem) {

            //웹툰 이미지 바인드
            Glide.with(binding.webtoonImg.context)
                .load(item.img)                     //이미지의 url
                .error(R.drawable.ic_error)   //이미지 로드 중 에러 발생시 표시할 이미지
                .into(binding.webtoonImg)           //이미지를 설정할 이미지뷰

            //신작 여부 확인
            setStatusIcon(item.additional.isNew, binding.statusNewImageview)

            //휴재 여부 확인
            setStatusIcon(item.additional.isRest, binding.statusRestIamgeview)

            //업로드 여부 확인
            setStatusIcon(item.additional.isUp, binding.statusUpImageview)

            //이벤트 여부 확인
            //webtoonItem에 이벤트정보가 포함되어있지 않음

            //웹툰 제목
            binding.webtoonTitle.text = item.title

            //웹툰 작가
            binding.webtoonAuthor.text = item.author

            //웹툰 장르
            //webtoonItem에 장르정보가 포함되어있지 않음

            setMarginLayout(binding.webtoonLayout)

            binding.webtoonLayout.setOnClickListener {
                val intent = Intent(binding.root.context, DetailActivity::class.java)
                intent.putExtra("URL", item.url)
                binding.root.context.startActivity(intent)
            }
        }
    }

    private fun setMarginLayout(layout: ConstraintLayout){
        val lp = layout.layoutParams as ViewGroup.MarginLayoutParams
        lp.setMargins(0, 20, 0, 0)
        layout.layoutParams = lp
    }


    private fun setStatusIcon(status:Boolean, imageview: ImageView){
        if (status) {
            val lp = imageview.layoutParams as ViewGroup.MarginLayoutParams
            lp.marginEnd = 5
            imageview.layoutParams = lp
        } else {
            imageview.setImageDrawable(null)
        }
    }

    // DiffUtil을 사용하여 아이템 변경 사항을 처리하는 내부 클래스
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


