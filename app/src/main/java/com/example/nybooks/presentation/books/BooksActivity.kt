package com.example.nybooks.presentation.books

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nybooks.R
import com.example.nybooks.data.repository.BooksApiDataSource
import com.example.nybooks.data.response.MainRepository
import com.example.nybooks.databinding.ActivityBooksBinding
import com.example.nybooks.presentation.base.BaseActivity
import com.example.nybooks.presentation.details.BookDetailsActivity

class BooksActivity : BaseActivity() {
    private lateinit var binding: ActivityBooksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBooksBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupToolbar(binding.toolbarMain.toolbarMain, R.string.books_title)

        val viewModel = ViewModelProvider(this, BooksViewModel.MainViewModelFactory(BooksApiDataSource())).get(BooksViewModel::class.java)
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

        viewModel.viewFlipperLiveData.observe(this, {
            it?.let {  viewFlipper ->
                binding.viewFlipperBooks.displayedChild = viewFlipper.first
                viewFlipper.second?.let {errorMessageId ->
                    binding.textViewError.text = getString(errorMessageId)
                }
            }
        })

        viewModel.getBooks()
    }




}