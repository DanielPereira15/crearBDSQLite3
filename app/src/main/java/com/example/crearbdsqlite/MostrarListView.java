package com.example.crearbdsqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.crearbdsqlite.entidades.Pais;
import com.example.crearbdsqlite.utilidades.Utilidades;

import java.util.ArrayList;

public class MostrarListView extends AppCompatActivity {
    ConexionSQLiteHelper conexion;
    ListView lvMostrar;
    String nombrePais="";
    Button btnMostrar;
    Button btnConsiguePais;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_list_view);
        conexion= new ConexionSQLiteHelper(this,"bd_Paises",null,2);//conseguimos la base de datos y la abrimos para escribir

        btnConsiguePais=findViewById(R.id.btnconsiguePais);
        btnMostrar=findViewById(R.id.btnMuestra);
        lvMostrar=findViewById(R.id.lvMuesta);

        btnMostrar.setEnabled(false);
        btnMostrar.setVisibility(View.INVISIBLE);
    }

    public void consigueNombrePais(View v){
        final EditText entrada= new EditText(this);
        entrada.setHint("Escribe el nombre del pais");
        entrada.setInputType(InputType.TYPE_CLASS_TEXT);

        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Pais");
        builder.setView(entrada);
        //escribe el nombre de la partida
        builder.setMessage("Escribe el nombre del pais").setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                nombrePais= entrada.getText().toString();
                //nombrePartida=
                btnMostrar.setEnabled(true);
                btnMostrar.setVisibility(View.VISIBLE);
                btnConsiguePais.setEnabled(false);
                btnConsiguePais.setVisibility(View.INVISIBLE);
            }
        })

        .setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });


        AlertDialog alertDialog=builder.create();
        alertDialog.show();


    }



    public void mostrar(View v){



        SQLiteDatabase db = conexion.getReadableDatabase();
        String[]parametrosBusqueda = {nombrePais};
        String[]campos={Utilidades.CAMPO_NOMBRE_PAIS,Utilidades.CAMPO_POBLACION,Utilidades.CAMPO_PIB};
        try{
            Cursor cursor = db.query(Utilidades.TABLA_PAIS,campos,Utilidades.CAMPO_NOMBRE_PAIS+"=?",parametrosBusqueda,
                    null,null,null);   //cursor es un puntero que apunta a lo que devuelve select

            ArrayList<Pais> alPais= new ArrayList<Pais>();
            while(cursor.moveToNext()){
                Pais p= new Pais();
                p.setNombrePais(cursor.getString(0));
                p.setPoblacion(cursor.getInt(1));
                p.setPIB(cursor.getInt(2));
                alPais.add(p);
            }
            cursor.close();

            ArrayList<String> alPaisTexto= new ArrayList<String>();
            for(int i=0;i<alPais.size();i++){
                alPaisTexto.add(alPais.get(i).toString());
            }


            ArrayAdapter adaptador= new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1,alPaisTexto);

            lvMostrar.setAdapter(adaptador);

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"El pais no existe...",Toast.LENGTH_LONG).show();
        }
    }
}