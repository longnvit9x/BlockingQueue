package neo.vn.myapplication

import com.google.gson.annotations.SerializedName


class Question {
    @SerializedName("title")
    var mTitle: String? = null
    @SerializedName("link")
    var mLink: String? = null

    override fun toString(): String {
        return mTitle?:""
    }
}