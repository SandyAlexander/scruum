package com.example.maps

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
import java.io.Serializable


data class Producto(
    val id: Int,
    var name: String,
    var description: String,
    var price: String,
    val imageResId: Int,
    var availableQuantity: Int
) : Serializable {
    fun toContentValues(): ContentValues {
        val values = ContentValues()
        values.put(DatabaseHelper.COLUMN_NAME, name)
        values.put(DatabaseHelper.COLUMN_DESCRIPTION, description)
        values.put(DatabaseHelper.COLUMN_PRICE, price)
        values.put(DatabaseHelper.COLUMN_IMAGE_RES_ID, imageResId)
        values.put(DatabaseHelper.COLUMN_AVAILABLE_QUANTITY, availableQuantity)
        return values
    }

    override fun toString(): String {
        return "$name - $description - Precio: $price - Cantidad: $availableQuantity"
    }

}

data class Usuario(
    val nombre: String,
    val apellido: String,
    val correo: String,
    val contrasena: String
)

object UsuarioContract {
    object UsuarioEntry : BaseColumns {
        const val TABLE_NAME = "usuarios"
        const val COLUMN_NOMBRE = "nombre"
        const val COLUMN_APELLIDO = "apellido"
        const val COLUMN_CORREO = "correo"
        const val COLUMN_CONTRASENA = "contrasena"
    }
}

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    companion object {
        const val DATABASE_NAME = "product_database"
        const val DATABASE_VERSION = 5
        const val TABLE_NAME = "products"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_PRICE = "price"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_IMAGE_RES_ID = "image_res_id"
        const val COLUMN_AVAILABLE_QUANTITY = "available_quantity"
    }

    private val CREATE_TABLE =
        "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NAME TEXT, $COLUMN_PRICE TEXT, $COLUMN_DESCRIPTION TEXT, $COLUMN_IMAGE_RES_ID INTEGER, $COLUMN_AVAILABLE_QUANTITY INTEGER);"

    override fun onCreate(db: SQLiteDatabase?) {
        Log.d("DatabaseHelper", "Database: $db")
        Log.d("DatabaseHelper", "CREATE_TABLE: $CREATE_TABLE")

        try {
            db?.execSQL(CREATE_TABLE)

            val SQL_CREATE_ENTRIES =
                "CREATE TABLE ${UsuarioContract.UsuarioEntry.TABLE_NAME} (" +
                        "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                        "${UsuarioContract.UsuarioEntry.COLUMN_NOMBRE} TEXT," +
                        "${UsuarioContract.UsuarioEntry.COLUMN_APELLIDO} TEXT," +
                        "${UsuarioContract.UsuarioEntry.COLUMN_CORREO} TEXT," +
                        "${UsuarioContract.UsuarioEntry.COLUMN_CONTRASENA} TEXT);"

            Log.d("DatabaseHelper", "SQL_CREATE_ENTRIES: $SQL_CREATE_ENTRIES")

            db?.execSQL(SQL_CREATE_ENTRIES)

            // Agrega esta línea para insertar los productos iniciales
            insertarProductosIniciales(db)
            Log.d("DatabaseHelper", "Productos iniciales insertados correctamente.")
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error en la creación de la base de datos: ${e.message}")
        }


        val contentValues = ContentValues().apply {
            put(UsuarioContract.UsuarioEntry.COLUMN_NOMBRE, "scrum")
            put(UsuarioContract.UsuarioEntry.COLUMN_APELLIDO, "")
            put(UsuarioContract.UsuarioEntry.COLUMN_CORREO, "scrum")
            put(UsuarioContract.UsuarioEntry.COLUMN_CONTRASENA, "scrum")
        }

        db?.insert(UsuarioContract.UsuarioEntry.TABLE_NAME, null, contentValues)

        // Verificar si los productos ya existen en la base de datos
        if (!productosExistenEnDB(db)) {
            // Insertar los productos en la base de datos
            insertarProductosIniciales(db)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 5) {
            db?.execSQL("ALTER TABLE $TABLE_NAME ADD COLUMN $COLUMN_AVAILABLE_QUANTITY INTEGER DEFAULT 0")
        }
    }


    private fun productosExistenEnDB(db: SQLiteDatabase?): Boolean {
        val projection = arrayOf(DatabaseHelper.COLUMN_ID)
        val selection = "${DatabaseHelper.COLUMN_NAME} IN (?, ?, ?, ?, ?)"
        val selectionArgs = obtenerProductosIniciales().map { it.name }.toTypedArray()

        val cursor: Cursor = db?.query(
            DatabaseHelper.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        ) ?: return false

        val productosExisten = cursor.count > 0
        cursor.close()
        return productosExisten
    }
    private fun insertarProductosIniciales(db: SQLiteDatabase?) {
        for (producto in obtenerProductosIniciales()) {
            val id = db?.insert(DatabaseHelper.TABLE_NAME, null, producto.toContentValues())
            if (id == -1L) {
                Log.e(ContentValues.TAG, "Error insertando producto: $producto")
            } else {
                Log.d(ContentValues.TAG, "Producto insertado: $producto")
            }
        }
    }


    private fun obtenerProductosIniciales(): List<Producto> {
        return listOf(
            Producto(1, "Botella 625ML Aqua Premium", "Balance único de minerales", "$1.00", R.drawable.botella, 10),
            Producto(2, "Botellón 20l Aqua Premium (Liquído)", "Agua purificada PH neutro","$3.00", R.drawable.botellon, 5),
            Producto(3, "Agua Sin Gas Cielo Botella 2.5 L.",  "Disfruta de la pureza y frescura del agua con nuestra botella de agua sin gas Cielo", "$2.90",R.drawable.botella_litro, 8),
            Producto(4, "Funda aguazul 100ml"," Mantén tu hidratación al alcance con nuestra funda de agua premium","0.50 ctvs", R.drawable.funda, 20),
            Producto(5, "Galón de GAR Water 4.5L", "Eleva tu experiencia de hidratación con nuestro galón de agua de alta calidad","0.75 ctvs", R.drawable.galon, 15)
        )
    }
}

class DatabaseManager(context: Context) {

    private val dbHelper: DatabaseHelper = DatabaseHelper(context)


    fun insertarUsuario(nombre: String, apellido: String, correo: String, contrasena: String): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(UsuarioContract.UsuarioEntry.COLUMN_NOMBRE, nombre)
            put(UsuarioContract.UsuarioEntry.COLUMN_APELLIDO, apellido)
            put(UsuarioContract.UsuarioEntry.COLUMN_CORREO, correo)
            put(UsuarioContract.UsuarioEntry.COLUMN_CONTRASENA, contrasena)
        }
        return db.insert(UsuarioContract.UsuarioEntry.TABLE_NAME, null, values)
    }

    fun obtenerUsuario(correo: String): Usuario? {
        val db = dbHelper.readableDatabase
        val projection = arrayOf(
            UsuarioContract.UsuarioEntry.COLUMN_NOMBRE,
            UsuarioContract.UsuarioEntry.COLUMN_APELLIDO,
            UsuarioContract.UsuarioEntry.COLUMN_CORREO,
            UsuarioContract.UsuarioEntry.COLUMN_CONTRASENA
        )

        val selection = "${UsuarioContract.UsuarioEntry.COLUMN_CORREO} = ?"
        val selectionArgs = arrayOf(correo)

        val cursor: Cursor = db.query(
            UsuarioContract.UsuarioEntry.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        return if (cursor.moveToFirst()) {
            Usuario(
                cursor.getString(cursor.getColumnIndexOrThrow(UsuarioContract.UsuarioEntry.COLUMN_NOMBRE)),
                cursor.getString(cursor.getColumnIndexOrThrow(UsuarioContract.UsuarioEntry.COLUMN_APELLIDO)),
                cursor.getString(cursor.getColumnIndexOrThrow(UsuarioContract.UsuarioEntry.COLUMN_CORREO)),
                cursor.getString(cursor.getColumnIndexOrThrow(UsuarioContract.UsuarioEntry.COLUMN_CONTRASENA))
            )
        } else {
            null
        }
    }


    fun insertarProducto(nombre: String, precio: String, descripcion: String, imageResId: Int, cantidad: Int): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_NAME, nombre)
            put(DatabaseHelper.COLUMN_PRICE, precio)
            put(DatabaseHelper.COLUMN_DESCRIPTION, descripcion)
            put(DatabaseHelper.COLUMN_IMAGE_RES_ID, imageResId)
            put(DatabaseHelper.COLUMN_AVAILABLE_QUANTITY, cantidad)
        }
        val id = db.insert(DatabaseHelper.TABLE_NAME, null, values)

        // Notificar a la actividad que se ha insertado un nuevo producto
        if (id != -1L) {
            listener?.onProductoInsertado()
        }

        return id
    }
    fun actualizarCantidadProducto(productoId: Int, nuevaCantidad: Int): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_AVAILABLE_QUANTITY, nuevaCantidad)
        }

        return db.update(
            DatabaseHelper.TABLE_NAME,
            values,
            "${DatabaseHelper.COLUMN_ID} = ?",
            arrayOf(productoId.toString())
        )
    }

    // Interfaz para escuchar eventos de cambio en la base de datos
    interface DatabaseListener {
        fun onProductoInsertado()
    }
    // En la clase DatabaseManager
    fun obtenerProductos(): List<Producto> {
        val productos = mutableListOf<Producto>()
        val db = dbHelper.readableDatabase
        val projection = arrayOf(
            DatabaseHelper.COLUMN_ID,
            DatabaseHelper.COLUMN_NAME,
            DatabaseHelper.COLUMN_DESCRIPTION,
            DatabaseHelper.COLUMN_PRICE,
            DatabaseHelper.COLUMN_IMAGE_RES_ID,
            DatabaseHelper.COLUMN_AVAILABLE_QUANTITY
        )

        val cursor: Cursor = db.query(
            DatabaseHelper.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )

        while (cursor.moveToNext()) {
            val producto = Producto(
                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRICE)),
                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_IMAGE_RES_ID)),
                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AVAILABLE_QUANTITY))
            )
            productos.add(producto)
        }

        cursor.close()
        return productos
    }

    // En la clase DatabaseManager
    private var listener: DatabaseListener? = null

    // Método para establecer el listener
    fun setDatabaseListener(databaseListener: DatabaseListener) {
        listener = databaseListener
    }
    fun actualizarProducto(producto: Producto): Int {
        val db = dbHelper.writableDatabase
        val values = producto.toContentValues()

        return db.update(
            DatabaseHelper.TABLE_NAME,
            values,
            "${DatabaseHelper.COLUMN_ID} = ?",
            arrayOf(producto.id.toString())
        )
    }
    fun eliminarProducto(productId: Int): Int {
        val db = dbHelper.writableDatabase

        return db.delete(
            DatabaseHelper.TABLE_NAME,
            "${DatabaseHelper.COLUMN_ID} = ?",
            arrayOf(productId.toString())
        )
    }


    fun obtenerCantidadProducto(productId: Int): Int {
        val db = dbHelper.readableDatabase
        val projection = arrayOf(DatabaseHelper.COLUMN_AVAILABLE_QUANTITY)
        val selection = "${DatabaseHelper.COLUMN_ID} = ?"
        val selectionArgs = arrayOf(productId.toString())

        val cursor = db.query(
            DatabaseHelper.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        val availableQuantity: Int = if (cursor.moveToFirst()) {
            cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AVAILABLE_QUANTITY))
        } else {
            0
        }

        cursor.close()
        return availableQuantity
    }

    fun cerrarConexion() {
        dbHelper.close()
    }
}
