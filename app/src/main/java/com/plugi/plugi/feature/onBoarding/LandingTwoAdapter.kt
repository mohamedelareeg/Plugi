package com.plugi.plugi.feature.onBoarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.plugi.domain.aamodels.StepModel
import com.plugi.plugi.R
import com.plugi.plugi.core.extentions.loadServerImage
import kotlinx.android.synthetic.main.row_landing_2.view.*


class LandingTwoAdapter(val list: List<StepModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_landing_2,parent,false)
        return HolderView((view))
    }

    override fun getItemCount(): Int =list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as HolderView).bind(list[position])
    }
    inner class  HolderView(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(stepModel: StepModel) {

            itemView.ivIcon.loadServerImage(stepModel.icon)
            itemView.tvTitle.text = stepModel.title
            itemView.tvSub.text = stepModel.description
        }

    }

}
