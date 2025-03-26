package com.example.googletasksclone.data.source.local

import androidx.lifecycle.LiveData
import com.example.googletasksclone.data.Category
import com.example.todoapp.data.Result

interface CategoryDataSource {
    suspend fun saveCategory(category: Category)
    suspend fun getCategory(categoryId: String): Result<Category>
    fun observeCategories(): LiveData<Result<List<Category>>>
}