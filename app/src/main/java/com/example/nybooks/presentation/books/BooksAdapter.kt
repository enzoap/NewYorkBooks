package com.example.nybooks.presentation.books

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nybooks.data.model.Book
import com.example.nybooks.databinding.ItemBookBinding

class BooksAdapter(
    private val books: List<Book>
) : RecyclerView.Adapter<BooksAdapter.BooksViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        val viewBinding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BooksViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        holder.bindView(books[position])
    }

    override fun getItemCount() = books.count()


    class BooksViewHolder(itemBookBinding: ItemBookBinding) : RecyclerView.ViewHolder(itemBookBinding.root) {

        private val title = itemBookBinding.textTitle
        private val author = itemBookBinding.textAuthor

        fun bindView(book: Book){
            title.text = book.title
            author.text = book.author
        }
    }


}