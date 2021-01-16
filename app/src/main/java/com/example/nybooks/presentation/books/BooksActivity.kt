package com.example.nybooks.presentation.books

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nybooks.R
import com.example.nybooks.data.response.MainRepository
import com.example.nybooks.databinding.ActivityBooksBinding
import com.example.nybooks.presentation.details.BookDetailsActivity

class BooksActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBooksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBooksBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.toolbarMain.title = getString(R.string.books_title)
        setSupportActionBar(binding.toolbarMain)

        val viewModel = ViewModelProvider(this, BooksViewModel.MainViewModelFactory(MainRepository())).get(BooksViewModel::class.java)
        viewModel.booksLiveData.observe(this, {
            it?.let { books ->
                with(binding.recyclerBooks){
                    layoutManager = LinearLayoutManager(this@BooksActivity, RecyclerView.VERTICAL, false)
                    setHasFixedSize(true)
                    adapter = BooksAdapter(books) {book ->
                        val intent = BookDetailsActivity.getStartIntent(this@BooksActivity, book.title, book.description)
                        this@BooksActivity.startActivity(intent)
                    }
                }
            }
        })


        viewModel.getBooks()
    }




}