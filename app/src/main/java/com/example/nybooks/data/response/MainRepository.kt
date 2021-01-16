package com.example.nybooks.data.response

import com.example.nybooks.data.ApiService
import com.example.nybooks.data.model.Book
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository {

//    fun createFakeBooks(): List<Book> {
//        return listOf<Book>(
//            Book("Title 1", "Author 1"),
//            Book("Title 2", "Author 2"),
//            Book("Title 3", "Author 3"),
//        )
//    }

//    fun getBooksApi(): Unit {
//        ApiService.service.getBooks().enqueue(object : Callback<BookBodyResponse> {
//            override fun onResponse(
//                call: Call<BookBodyResponse>,
//                response: Response<BookBodyResponse>
//            ) {
//                if (response.isSuccessful){
//                    val books: MutableList<Book> = mutableListOf()
//
//                    response.body()?.let { bookBodyResponse ->
//                        for (result in bookBodyResponse.bookResults){
//                            val book: Book = Book(
//                                title = result.bookDetails[0].title,
//                                author = result.bookDetails[0].author
//                            )
//
//                            books.add(book)
//                        }
//                    }
//
//                }
//            }
//
//            override fun onFailure(call: Call<BookBodyResponse>, t: Throwable) {
//            }
//
//        })
//    }
}