package com.example.nybooks.presentation.books

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.nybooks.R
import com.example.nybooks.data.BooksResult
import com.example.nybooks.data.model.Book
import com.example.nybooks.data.repository.BooksRepository
import com.nhaarman.mockitokotlin2.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BooksViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var booksLiveDataObserver: Observer<List<Book>>
    @Mock
    private lateinit var viewFlipperLiveDataObserver: Observer<Pair<Int, Int?>>

    private lateinit var viewModel: BooksViewModel

    @Test
    fun `when view model getBooks get sucess then set booksLiveData`() {
        val books = listOf(
            Book("title 1", "author 1", "description 1")
        )

        val resultSucess = MockRepository(BooksResult.Sucess(books))
        viewModel = BooksViewModel(resultSucess)
        viewModel.booksLiveData.observeForever(booksLiveDataObserver)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        viewModel.getBooks()

        verify(booksLiveDataObserver).onChanged(books)
        verify(viewFlipperLiveDataObserver).onChanged(Pair(1, null))
    }

    @Test
    fun `when view model getBooks get server error then set viewFlipperLiveData`(){
        val resultServerError = MockRepository(BooksResult.ServerError)
        viewModel = BooksViewModel(resultServerError)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        viewModel.getBooks()

        verify(viewFlipperLiveDataObserver).onChanged(Pair(2, R.string.books_error_500_generic))
    }

    @Test
    fun `when view model getBooks get api error status code 401 then set viewFlipperLiveData`() {
        val resultApiError = MockRepository(BooksResult.ApiError(401))
        viewModel = BooksViewModel(resultApiError)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        viewModel.getBooks()

        verify(viewFlipperLiveDataObserver).onChanged(Pair(2, R.string.books_error_401))
    }

    @Test
    fun `when view model getBooks get api error status code generic then set viewFlipperLiveData`() {
        val resultApiError = MockRepository(BooksResult.ApiError(402))
        viewModel = BooksViewModel(resultApiError)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        viewModel.getBooks()

        verify(viewFlipperLiveDataObserver).onChanged(Pair(2, R.string.books_error_400_generic))
    }
}

class MockRepository(private val result: BooksResult) : BooksRepository {
    override fun getBooks(booksResultCallback: (result: BooksResult) -> Unit) {
        booksResultCallback(result)
    }
}