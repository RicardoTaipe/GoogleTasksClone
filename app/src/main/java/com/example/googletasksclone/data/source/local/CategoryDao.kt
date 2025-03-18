package com.example.googletasksclone.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.googletasksclone.data.Category

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    @Query("SELECT * FROM category")
    suspend fun getAllCategories(): List<Category>

    @Query("SELECT * FROM category")
    suspend fun observeAllCategories(): LiveData<List<Category>>

    @Query("UPDATE category SET isFavorite = 0 WHERE isFavorite = 1")
    suspend fun resetFavoriteCategory()

    @Query("UPDATE category SET isFavorite = 1 WHERE category_id = :categoryId")
    suspend fun setFavoriteCategory(categoryId: Long)

    @Query("DELETE FROM category WHERE category_id = :categoryId")
    suspend fun deleteCategoryById(categoryId: Long): Int
}
