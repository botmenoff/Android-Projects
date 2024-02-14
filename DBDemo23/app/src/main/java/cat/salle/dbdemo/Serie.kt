package cat.salle.dbdemo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Serie(
    @PrimaryKey(autoGenerate = true) var uId: Int?,
    @ColumnInfo(name = "titulo") var firstName: String?,
    @ColumnInfo(name = "autor") var lastName: String?,

)