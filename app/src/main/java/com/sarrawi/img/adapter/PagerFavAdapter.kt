package com.sarrawi.img.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sarrawi.img.R
import com.sarrawi.img.databinding.RowImagesBinding
import com.sarrawi.img.model.FavoriteImage

import com.sarrawi.img.model.ImgsModel

class PagerFavAdapter(val con: Context):
    RecyclerView.Adapter<PagerFavAdapter.ViewHolder>() {

    var onItemClick: ((Int) -> Unit)? = null
    var onbtnClick: ((item:FavoriteImage,position:Int) -> Unit)? = null
    private var isToolbarVisible = true


    inner class ViewHolder(val binding:RowImagesBinding):RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(img_list[layoutPosition].id?:0)
            }




        }


        fun bind(position: Int) {

            val current_imgModel = img_list[position]
            Glide.with(con)
                .load(current_imgModel.image_url)
                .into(binding.imageView)
            binding.apply {
                if(current_imgModel.is_fav){
                    imgFave.setImageResource(R.drawable.baseline_favorite_true)
                }else{
                    imgFave.setImageResource(R.drawable.baseline_favorite_border_false)
                }

            }
            binding.imgFave.setOnClickListener {
                onbtnClick?.invoke(img_list[position],position)
            }

        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<FavoriteImage>(){
        override fun areItemsTheSame(oldItem: FavoriteImage, newItem: FavoriteImage): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavoriteImage, newItem: FavoriteImage): Boolean {
            return newItem == oldItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var img_list: List<FavoriteImage>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(RowImagesBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return img_list.size
    }


}