package com.example.lezhin_webtoon_ipp.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.lezhin_webtoon_ipp.R
import com.example.lezhin_webtoon_ipp.activities.DetailActivity
import com.example.lezhin_webtoon_ipp.data.WebtoonItem
import com.example.lezhin_webtoon_ipp.databinding.ItemHomeRankingRecyclerBinding

class HomeRankingListAdapter : ListAdapter<WebtoonItem, HomeRankingListAdapter.MyViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemHomeRankingRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(currentList[position], position)
    }

    inner class MyViewHolder(private var binding: ItemHomeRankingRecyclerBinding) : ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: WebtoonItem, position: Int) {
            //웹툰 이미지
            Glide.with(binding.homeRankingImg.context)
                .load(item.img)                   //이미지의 ur
                .error(R.drawable.ic_error)   //이미지 로드 중 에러 발생시 표시할 이미지
                .into(binding.homeRankingImg)           //이미지를 설정할 이미지뷰

            //신작 여부 확인
            setStatusIcon(item.additional.isNew, binding.homeRankingNew)

            //휴재 여부 확인
            setStatusIcon(item.additional.isRest, binding.homeRankingRest)

            //업로드 여부 확인
            setStatusIcon(item.additional.isUp, binding.homeRankingUp)

            //이벤트 여부 확인
            //webtoonItem에 이벤트정보가 포함되어있지 않음

            //인덱스 번호
            binding.homeRankingIndex.text = (position + 1).toString()

            //웹툰 제목
            binding.homeRankingTitle.text = item.title

            //웹툰 작가
            binding.homeRankingAuthor.text = item.author

            //전체 마진 설정
            setMarginLayout(binding.itemHomeRankingLayout)

            binding.itemHomeRankingLayout.setOnClickListener {
                val intent = Intent(binding.root.context, DetailActivity::class.java)
                intent.putExtra("URL", item.url)
                binding.root.context.startActivity(intent)
            }
        }
    }

    private fun setMarginLayout(layout: ConstraintLayout){
        val lp = layout.layoutParams as ViewGroup.MarginLayoutParams
        lp.setMargins(30, 0, 30, 20)
        layout.layoutParams = lp
    }

    private fun setStatusIcon(status:Boolean, imageview: ImageView){
        if (status) {
            //있다면 해당 marginEnd=5
            val lp = imageview.layoutParams as ViewGroup.MarginLayoutParams
            lp.marginEnd = 10
            imageview.layoutParams = lp
        } else {
            //해당 이미지 삭제
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