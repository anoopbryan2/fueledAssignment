package com.assignment.fueled.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.assignment.fueled.R
import com.assignment.fueled.adapter.VerticalRecyclerAdapter
import com.assignment.fueled.helper.RecyclerViewMargin
import com.assignment.fueled.model.UserBase
import com.assignment.fueled.viewmodel.LandingViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class LandingActivity : AppCompatActivity() {

    private lateinit var viewModel: LandingViewModel
    private lateinit var adapter: VerticalRecyclerAdapter
    private var adapterData = ArrayList<UserBase>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(LandingViewModel::class.java)
        setSupportActionBar(toolbar)

        processData()


        initializeViews()

        viewModel.getFinalData()?.observe(this, Observer {
            it?.forEach {
                adapterData.add(it)
                adapter.notifyDataSetChanged()
            }
        })
    }

    /*This will take the local files names and parse them into the beans files and process them to
    * get the desired result*/
    fun processData() {
        viewModel.ParseAndProcessData("users.json", "posts.json", "comments.json", this.assets)
    }

    private fun initializeViews() {
        adapter = VerticalRecyclerAdapter(adapterData)
        verticalRecyclerView.adapter = adapter
        verticalRecyclerView.addItemDecoration(RecyclerViewMargin(resources.getDimensionPixelSize(R.dimen.dp16)))
    }


}