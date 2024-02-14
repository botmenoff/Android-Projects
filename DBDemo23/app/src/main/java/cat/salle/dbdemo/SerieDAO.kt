package cat.salle.dbdemo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query

@Dao
interface SerieDAO {
    @Insert(onConflict = REPLACE)
    fun insert(serie: Serie)

    @Query("SELECT * FROM serie")
    fun loadAllSeries(): List<Serie>

    @Query("SELECT * FROM serie WHERE  autor = 'Nose'")
    fun loadNose(): List<Serie>
}