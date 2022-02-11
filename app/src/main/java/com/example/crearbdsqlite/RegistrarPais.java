package com.example.crearbdsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crearbdsqlite.utilidades.Utilidades;

public class RegistrarPais extends AppCompatActivity {

    EditText etNombrePais, etPoblacion, etPib;
    Button btnAnadir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_pais);

        etNombrePais=findViewById(R.id.etNombrePais);
        etPoblacion=findViewById(R.id.etPoblacion);
        etPib=findViewById(R.id.etPib);
        btnAnadir=findViewById(R.id.btnAnadir);

    }

    public void onClick(View v){
        switch (v.getId()){

            case R.id.btnAnadir:
                //anadirPais();
                anadirPaisSQL();
                break;


        }
    }

    private void anadirPaisSQL(){
        ConexionSQLiteHelper conexion= new ConexionSQLiteHelper(this,"bd_Paises",null,2);//conseguimos la base de datos y la abrimos para escribir
        //indicamos que vamos a abrir la base de datos para escribir...
        SQLiteDatabase db = conexion.getWritableDatabase();
        String insertar="INSERT INTO "+Utilidades.TABLA_PAIS + " ( " + Utilidades.CAMPO_NOMBRE_PAIS +", "+Utilidades.CAMPO_POBLACION +", " + Utilidades.CAMPO_PIB+")" + " VALUES ('"+
                this.etNombrePais.getText().toString() +"', " + this.etPoblacion.getText().toString() +", "+this.etPib.getText().toString()+");";

        db.execSQL(insertar);
        db.close();

        limpiarCampos();

    }



    private void anadirPais(){
        ConexionSQLiteHelper conexion= new ConexionSQLiteHelper(this,"bd_Paises",null,2);//conseguimos la base de datos y la abrimos para escribir
        //indicamos que vamos a abrir la base de datos para escribir...
        SQLiteDatabase db = conexion.getWritableDatabase();
        //Para poder escribir se hace pareido a como utilizamos una tabla hash, es decir, clave(nombre igual que la columana de la tabla) dato.
        ContentValues valores= new ContentValues();
        valores.put(Utilidades.CAMPO_NOMBRE_PAIS,this.etNombrePais.getText().toString());//Se puede utilizar UTILIDADES.... O EL NOMBRE DE LA COLUMNA
        valores.put("Poblacion",Integer.parseInt(this.etPoblacion.getText().toString()));
        valores.put("PIB",Integer.parseInt(this.etPib.getText().toString()));

        Long idResultado = db.insert("Pais","NombrePais",valores);
        Toast.makeText(getApplicationContext(),"Nacion: "+ idResultado,Toast.LENGTH_LONG).show();

        db.close();
        limpiarCampos();

    }

    private void limpiarCampos(){
        etNombrePais.setText("");
        etPoblacion.setText("");
        etPib.setText("");
    }

}