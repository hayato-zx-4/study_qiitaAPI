package com.example.hayato.qiitaapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import com.example.hayato.qiitaapplication.client.ArticleClient

import com.example.hayato.qiitaapplication.util.ArticleListAdapter

import com.example.hayato.qiitaapplication.view.toast
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.list_view)
        val searchButton = findViewById<Button>(R.id.search_botton)
        val queryEditText = findViewById<EditText>(R.id.query_edit_text)

        val listAdapter = ArticleListAdapter(applicationContext)
        listView.adapter = listAdapter
        listView.setOnItemClickListener { parent, view, position, id ->
            val intent = ArticleActivity.intent(this, listAdapter.articleList[position])
            startActivity(intent)
        }

         val gson = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()

        val retrofit = Retrofit.Builder()
                .baseUrl("https://qiita.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        val articleClient = retrofit.create(ArticleClient::class.java)

        searchButton.setOnClickListener{
            articleClient.search(queryEditText.text.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .bindToLifecycle(this)
                    .subscribe({
                        queryEditText.text.clear()
                        listAdapter.articleList = it
                        listAdapter.notifyDataSetChanged()
                    }, {
                        toast("エラー： $it")
                    })
        }




    }

}

