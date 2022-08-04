package com.handsome.club.hnh.cookbook.model.food

import com.handsome.club.hnh.cookbook.data.database.FoodDao
import com.handsome.club.hnh.cookbook.infrastructure.UseCase
import com.handsome.club.hnh.cookbook.utils.getExecutionTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject


class PopulateFoodRepositoryUseCase @Inject constructor(
    private val persistance: FoodDao,
    private val source: FoodsSource,
) : UseCase {

    suspend operator fun invoke(): Result<Unit> {
        return withContext(Dispatchers.IO) {
            if (persistance.isEmpty()) {
                val executionTime = getExecutionTime {
                    source.fetchAllFoods()
                        .run { persistance.insertFoodsWithIngredientsAndFeps(this) }
                }
                Timber.i("Elapsed time = $executionTime s")
            }

            Result.success(Unit)
        }
    }

}
