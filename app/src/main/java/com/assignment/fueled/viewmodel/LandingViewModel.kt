package com.assignment.fueled.viewmodel

import android.content.res.AssetManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.assignment.fueled.model.CommentResponse
import com.assignment.fueled.model.PostResponse
import com.assignment.fueled.model.UserBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class LandingViewModel : ViewModel() {
    private var finalList = MutableLiveData<ArrayList<UserBase>>()
    private var userResponse = ArrayList<UserBase>()
    private var postResponse = ArrayList<PostResponse>()
    private var commentResponse = ArrayList<CommentResponse>()


    fun getFinalData() : LiveData<ArrayList<UserBase>?>?{
        return finalList
    }

    /*This will take the local files names and parse them into the beans files and process them to
        * get the desired result*/
    fun ParseAndProcessData(userFile: String?, postFile: String?, commentsFile: String?,assetManager: AssetManager) {

        /*Reading the locally saved json file and parsing them into the model clases*/
        getJsonFromLocalData(userFile,postFile,commentsFile,assetManager);

        /*this will process the json data and finding the meaningfull data*/
        processTheData()

    }

    /*this will process the json data and finding the meaningfull data*/
    fun processTheData(){
        var all = HashMap<UserBase, HashMap<PostResponse, List<CommentResponse?>?>?>()
        finalList?.value = null

        CoroutineScope(Dispatchers.IO).launch {
            userResponse?.forEach { user ->
                var local = HashMap<PostResponse, List<CommentResponse?>?>()

                postResponse.filter { post ->
                    if (user.id == post.userId) {
                        var comments = commentResponse.filter { comment ->
                            post.id == comment.postId
                        }
                        local.put(post, comments)
                        true
                    } else {
                        false
                    }
                }
                all.put(user, local)
            }
            var set = ArrayList<UserBase>()

            all.forEach { userMap ->
                var user: UserBase
                user = userMap.key
                var noOfPosts = 0
                var noOfComments = 0
                userMap.value?.forEach { postComment ->
                    noOfPosts++
                    noOfComments += postComment?.value?.size ?: 0
                }
                if (noOfComments >= noOfPosts) {
                    user.score = noOfComments / noOfPosts
                }
                set.add(user)
            }
            set.sort()
            finalList?.postValue(set)
        }
    }

    /*Reading the locally saved json file and parsing them into the model clases*/
    fun getJsonFromLocalData(userFile: String?, postFile: String?, commentsFile: String?, assetManager: AssetManager) {
        var stringBuilderUser = StringBuilder()
        var stringBuilderPosts = StringBuilder()
        var stringBuilderComments = StringBuilder()

        try {
            var bfUser = BufferedReader(InputStreamReader(
                    assetManager.open(userFile!!)))
            var bfPost = BufferedReader(InputStreamReader(
                    assetManager.open(postFile!!)))
            var bfComment = BufferedReader(InputStreamReader(
                    assetManager.open(commentsFile!!)))
            var lineUser: String? = ""
            var linePost: String? = ""
            var lineComment: String? = ""

            while (bfUser.readLine().also({ lineUser = it }) != null) {
                stringBuilderUser.append(lineUser)
            }
            while (bfPost.readLine().also({ linePost = it }) != null) {
                stringBuilderPosts.append(linePost)
            }
            while (bfComment.readLine().also({ lineComment = it }) != null) {
                stringBuilderComments.append(lineComment)
            }

            var gson1 = Gson()
            var gson2 = Gson()
            var gson3 = Gson()

             userResponse = gson1.fromJson(
                    stringBuilderUser.toString(),
                    object : TypeToken<List<UserBase?>?>() {}.type
            ) as java.util.ArrayList<UserBase>

             postResponse = gson2.fromJson(
                    stringBuilderPosts.toString(),
                    object : TypeToken<List<PostResponse?>?>() {}.type
            ) as java.util.ArrayList<PostResponse>
             commentResponse = gson3.fromJson(
                    stringBuilderComments.toString(),
                    object : TypeToken<List<CommentResponse?>?>() {}.type
            ) as java.util.ArrayList<CommentResponse>
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


}