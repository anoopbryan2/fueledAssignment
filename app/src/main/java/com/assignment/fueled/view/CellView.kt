package com.assignment.fueled.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.assignment.fueled.R
import com.assignment.fueled.model.UserBase

class CellView : FrameLayout {

    private lateinit var ctx: Context
    lateinit var viewHolder: ViewHolder

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        ctx = context
        layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        inflate(ctx, R.layout.vertical_view, this)
        viewHolder = ViewHolder(this)
    }

    fun setData(user: UserBase?) {
        viewHolder.apply {
            id.text = user!!.id.toString()
            name.text = user.name
            score.text = user.score.toString()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var id: TextView = view.findViewById(R.id.id)
        var name: TextView = view.findViewById(R.id.name)
        var score: TextView = view.findViewById(R.id.score)
    }
}