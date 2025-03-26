package com.example.googletasksclone.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.googletasksclone.data.Category
import com.example.todoapp.data.Result
import com.example.todoapp.data.Result.Success
import com.example.todoapp.data.Result.Error
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryLocalDataSource @Inject constructor(
    private val categoryDao: CategoryDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CategoryDataSource {

    override suspend fun saveCategory(category: Category) = withContext(ioDispatcher) {
        categoryDao.insertCategory(category)
    }

    override suspend fun getCategory(categoryId: String): Result<Category> =
        withContext(ioDispatcher) {
            try {
                val category = categoryDao.getCategoryById(categoryId)
                if (category != null) {
                    return@withContext Success(category)
                } else {
                    return@withContext Error(Exception("Task not found!"))
                }
            } catch (e: Exception) {
                return@withContext Error(e)
            }
        }

    override fun observeCategories(): LiveData<Result<List<Category>>> {
        return categoryDao.observeAllCategories().map {
            Success(it)
        }
    }
}