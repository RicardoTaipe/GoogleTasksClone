package com.example.googletasksclone.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.googletasksclone.data.Category

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    @Query("SELECT * FROM category")
    suspend fun getAllCategories(): List<Category>

    @Query("SELECT * FROM category")
    fun observeAllCategories(): LiveData<List<Category>>

    @Query("SELECT * FROM category WHERE category_id = :categoryId")
    suspend fun getCategoryById(categoryId: String): Category?

    @Query("UPDATE category SET isFavorite = 0 WHERE isFavorite = 1")
    suspend fun resetFavoriteCategory()

    @Query("UPDATE category SET isFavorite = 1 WHERE category_id = :categoryId")
    suspend fun setFavoriteCategory(categoryId: String)

    @Query("DELETE FROM category WHERE category_id = :categoryId")
    suspend fun deleteCategoryById(categoryId: String): Int

    @Upsert
    suspend fun upsert(category: Category)
}
