package com.example.crearbdsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crearbdsqlite.utilidades.Utilidades;

public class BuscarPais extends AppCompatActivity {
    EditText etNombrePaisBuscar, etPoblacionBuscar, etPibBuscar;
    TextView tvResultado;
    Button btnSiguiente;
    ConexionSQLiteHelper conexion;
    int cont = 1;
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_pais);
        conexion= new ConexionSQLiteHelper(this,"bd_Paises",null,2);//conseguimos la base de datos y la abrimos para escribir

        etNombrePaisBuscar=findViewById(R.id.etNombrePaisBuscar);
        etPoblacionBuscar=findViewById(R.id.etPoblacionBuscar);
        etPibBuscar=findViewById(R.id.etPibBuscar);
        tvResultado=findViewById(R.id.tvCantResultados);
        btnSiguiente=findViewById(R.id.btnSiguiente);
    }

    public void onClick(View v){
        switch (v.getId()){

            case R.id.btnBuscarB:
                //buscar();
                buscarRawQuery();
                break;

            case R.id.btnActualizar:
                actualizar();
                break;

            case R.id.btnEliminar:
                eliminar();
                break;

            case R.id.btnSiguiente:
                siguienteRegistro();
                break;
        }
    }

    private void buscarRawQuery(){
        SQLiteDatabase db = conexion.getReadableDatabase();
        String [] parametros={this.etNombrePaisBuscar.getText().toString()};
        //String[]campos={Utilidades.CAMPO_POBLACION,Utilidades.CAMPO_PIB};

        try{
            c = db.rawQuery("Select "+ Utilidades.CAMPO_POBLACION + ", " + Utilidades.CAMPO_PIB + " from "+Utilidades.TABLA_PAIS + " where " + Utilidades.CAMPO_NOMBRE_PAIS +"=?",parametros);
            c.moveToFirst();


            this.etPibBuscar.setText("" +c.getInt(1));
            this.etPoblacionBuscar.setText(""+c.getInt(0));

            if(cont<c.getCount() && c.getCount()>0) {
                btnSiguiente.setEnabled(true);
                tvResultado.setText(cont + " / "+c.getCount() );

            }

        }catch (Exception e){
            e.getMessage();
        }


    }


//setEnableAtTrue
    public void siguienteRegistro(){

        if(cont<=c.getCount() && c.getCount()>0) {
            cont++;
            tvResultado.setText(cont + " / "+c.getCount() );
                c.moveToNext();
                this.etPibBuscar.setText("" +c.getInt(1));
                this.etPoblacionBuscar.setText(""+c.getInt(0));

        }if(cont==c.getCount()){
            btnSiguiente.setEnabled(false);
        }


    }



    private void eliminar(){
        SQLiteDatabase db = conexion.getWritableDatabase();
        String [] parametros={this.etNombrePaisBuscar.getText().toString()};

        db.delete(Utilidades.TABLA_PAIS,Utilidades.CAMPO_NOMBRE_PAIS+"=?",parametros);
        Toast.makeText(getApplicationContext(),"Se eliminó el pais",Toast.LENGTH_SHORT).show();

        db.close();
        limpiar();

    }

//cursor.getCount();


    private void actualizar(){
        SQLiteDatabase db = conexion.getWritableDatabase();
        String [] parametros={this.etNombrePaisBuscar.getText().toString()};
        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_NOMBRE_PAIS,this.etNombrePaisBuscar.getText().toString());
        values.put(Utilidades.CAMPO_POBLACION,this.etPoblacionBuscar.getText().toString());
        values.put(Utilidades.CAMPO_PIB,this.etPibBuscar.getText().toString());
        db.update(Utilidades.TABLA_PAIS, values,Utilidades.CAMPO_NOMBRE_PAIS+"=?",parametros);
        db.close();
        Toast.makeText(getApplicationContext(),"Pais actualizado ",Toast.LENGTH_SHORT).show();

        limpiar();
    }



    private void buscar(){
        //indicamos que vamos a abrir la base de datos para leerla...
        SQLiteDatabase db = conexion.getReadableDatabase();
        //Para poder escribir se hace pareido a como utilizamos una tabla hash, es decir, clave(nombre igual que la columana de la tabla) dato.
        String[]parametrosBusqueda = {this.etNombrePaisBuscar.getText().toString().trim()};
        String[]campos={Utilidades.CAMPO_POBLACION,Utilidades.CAMPO_PIB};
       try{
           Cursor cursor = db.query(Utilidades.TABLA_PAIS,campos,Utilidades.CAMPO_NOMBRE_PAIS+"=?",parametrosBusqueda,
                   null,null,null);   //cursor es un puntero que apunta a lo que devuelve select

            cursor.moveToFirst();
           Toast.makeText(getApplicationContext(),"elementos encontrados: " + cursor.getCount(),Toast.LENGTH_LONG).show();
           etPoblacionBuscar.setText(""+cursor.getInt(0));
            etPibBuscar.setText(""+cursor.getInt(1));

            cursor.close();



       }catch (Exception e){
           Toast.makeText(getApplicationContext(),"El pais no existe...",Toast.LENGTH_LONG).show();
           limpiar();
           e.getMessage();
       }
    }
    public void limpiar(){
        etPoblacionBuscar.setText("");
        etPibBuscar.setText("");
        this.etNombrePaisBuscar.setText("");
    }
}