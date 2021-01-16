package com.example.nybooks.presentation.books

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nybooks.data.ApiService
import com.example.nybooks.data.model.Book
import com.example.nybooks.data.response.BookBodyResponse
import com.example.nybooks.data.response.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("UNCHECKED_CAST")
class BooksViewModel(private val repository: MainRepository) : ViewModel() {
    val booksLiveData : MutableLiveData<List<Book>> = MutableLiveData()

    fun getBooks(){
        ApiService.service.getBooks().enqueue(object : Callback<BookBodyResponse> {
            override fun onResponse(
                call: Call<BookBodyResponse>,
                response: Response<BookBodyResponse>
            ) {
                if (response.isSuccessful){
                    val books: MutableList<Book> = mutableListOf()

                    response.body()?.let { bookBodyResponse ->
                        for (result in bookBodyResponse.bookResults){
                            val book = Book(
                                title = result.bookDetails[0].title,
                                author = result.bookDetails[0].author,
                                description = result.bookDetails[0].description
                            )

                            books.add(book)
                        }
                    }
                    booksLiveData.value = books
                }
            }

            override fun onFailure(call: Call<BookBodyResponse>, t: Throwable) {
            }

        })
    }

    class MainViewModelFactory(
        private val repository: MainRepository
    ) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return BooksViewModel(repository) as T
        }

    }
}