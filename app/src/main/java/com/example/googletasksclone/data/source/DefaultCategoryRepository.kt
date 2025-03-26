package com.example.googletasksclone.data.source

import androidx.lifecycle.LiveData
import com.example.googletasksclone.data.Category
import com.example.googletasksclone.data.source.local.CategoryDataSource
import com.example.todoapp.data.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class DefaultCategoryRepository @Inject constructor(
    private val categoryLocalDataSource: CategoryDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CategoryRepository {

    override suspend fun saveCategory(category: Category) {
        coroutineScope {
            launch {
                categoryLocalDataSource.saveCategory(category)
            }
        }
    }

    override suspend fun getCategory(categoryId: String) : Result<Category> {
        return categoryLocalDataSource.getCategory(categoryId)
    }

    override fun observeCategories(): LiveData<Result<List<Category>>> {
        return categoryLocalDataSource.observeCategories()
    }
}