package com.example.hnhapp.data.productResponse

import com.google.gson.annotations.SerializedName

//@Entity(
//    tableName = ProductDAO.DATABASE_NAME,
//    indices = [Index("id")],
//    foreignKeys = [
//        ForeignKey(
//            entity = Badge::class,
//            parentColumns = ["id"],
//            childColumns = ["badges"],
//            onDelete = ForeignKey.CASCADE
//        ),
//        ForeignKey(
//            entity = Size::class,
//            parentColumns = ["id"],
//            childColumns = ["sizes"],
//            onDelete = ForeignKey.CASCADE
//        )
//    ]
//)
data class Product(
    //@PrimaryKey
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("department")
    val department: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("badge")
    //@ColumnInfo(name = "badges")
    val badge: List<Badge>,
    @SerializedName("preview")
    val previewImageUrl: String,
    @SerializedName("images")
    val images: List<String>,
    @SerializedName("sizes")
    //@ColumnInfo(name = "sizes")
    val sizes: List<Size>,
    @SerializedName("description")
    val description: String,
    @SerializedName("details")
    val details: List<String>,
)
