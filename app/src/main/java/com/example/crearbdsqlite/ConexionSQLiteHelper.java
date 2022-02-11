package com.example.crearbdsqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConexionSQLiteHelper extends SQLiteOpenHelper { //Creamos esta JAVA CLASS para crear la BD

    final String CREAR_TABLA="CREATE TABLE Pais(NombrePais TEXT, Poblacion INTEGER, PIB INTEGER)"; //sentencia para crear la tabla


    //constructor de conexion. Si cambiamos la version se borran los datos que hubiese y se crea otra vez la bd
    public ConexionSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREAR_TABLA);
        //se puede hacer tambien con:  db.execSQL(Utilidades.CREAR_TABLA_PAIS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) { //aqui es donde compruebas que si la version es la misma se upgradea y si no se borra y se crea nueva
    db.execSQL("DROP TABLE IF EXISTS Pais");
    onCreate(db);
    }
}
