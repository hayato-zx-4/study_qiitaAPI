package com.example.hayato.qiitaapplication.util

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.hayato.qiitaapplication.model.Article
import com.example.hayato.qiitaapplication.view.ArticleView

class ArticleListAdapter(private val context: Context) : BaseAdapter(){
    var articleList = emptyList<Article>()


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
       return ((convertView as? ArticleView) ?: ArticleView(context)).apply { setArticle(articleList[position]) }
    }

    override fun getItem(position: Int): Any? = articleList[position]

    override fun getItemId(position: Int): Long = 0

    override fun getCount(): Int = articleList.size


}