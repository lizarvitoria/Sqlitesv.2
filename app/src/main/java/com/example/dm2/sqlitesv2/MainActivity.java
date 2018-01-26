package com.example.dm2.sqlitesv2;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void reset(View v){
        UsuariosSQLiteHelper usdbh =
                new UsuariosSQLiteHelper(this, "DBUsuarios", null, 1);
        SQLiteDatabase db = usdbh.getWritableDatabase();
        db.delete("Usuarios",null,null);
        db.close();
    }

    public void eliminar(View v){
        UsuariosSQLiteHelper usdbh =
                new UsuariosSQLiteHelper(this, "DBUsuarios", null, 1);
        SQLiteDatabase db = usdbh.getWritableDatabase();
        EditText codigo = (EditText)findViewById(R.id.codigo);
        EditText nombre = (EditText)findViewById(R.id.nombre);
        EditText email = (EditText)findViewById(R.id.email);
        EditText telf = (EditText)findViewById(R.id.telefono);
        TextView resultado = (TextView)findViewById(R.id.result);
        if(codigo.getText()==null||nombre.getText()==null||email.getText()==null||telf.getText()==null) {
            resultado.setText("No se han introducido todos los campos");
        }else {
            String[] campos = new String[]{"idUsuario", "nombre", "email", "telf"};
            String[] args = new String[]{codigo.getText() + "", nombre.getText() + "", email.getText() + "", telf.getText() + ""};
            Cursor max = db.query("Usuarios", campos, null, args, null, null, null);
            if (max.moveToFirst())
                db.delete("Usuarios", "idUsuario=" + args[0], null);
            else {
                resultado.setText("No existe ese usuario");
            }
        }
    }

    public void insertar(View v){
        UsuariosSQLiteHelper usdbh =
                new UsuariosSQLiteHelper(this, "DBUsuarios", null, 1);
        SQLiteDatabase db = usdbh.getWritableDatabase();
        EditText codigo = (EditText)findViewById(R.id.codigo);
        EditText nombre = (EditText)findViewById(R.id.nombre);
        EditText email = (EditText)findViewById(R.id.email);
        EditText telf = (EditText)findViewById(R.id.telefono);
        TextView resultado = (TextView)findViewById(R.id.result);
        String[] campos = new String[] {"idUsuario"};
        String[] args = new String[] {};
        Cursor max = db.query("Usuarios",campos,null,args,null,null,"idUsuario desc");
        int maxi;
        if (max.moveToFirst()) {
            maxi=max.getInt(0);
        }else{
            maxi=1;
        }
        if(codigo.getText()==null||nombre.getText()==null||email.getText()==null||telf.getText()==null){
            resultado.setText("No se han introducido todos los campos");
        }else{
            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("idUsuario", maxi);
            nuevoRegistro.put("nombre", nombre.getText().toString());
            nuevoRegistro.put("email", email.getText().toString());
            nuevoRegistro.put("telf", telf.getText().toString());
            db.insert("Usuarios",null,nuevoRegistro);
        }
        db.close();
    }
    public void actualizar(View v){
        UsuariosSQLiteHelper usdbh =
                new UsuariosSQLiteHelper(this, "DBUsuarios", null, 1);
        SQLiteDatabase db = usdbh.getWritableDatabase();
        EditText codigo = (EditText)findViewById(R.id.codigo);
        EditText nombre = (EditText)findViewById(R.id.nombre);
        EditText email = (EditText)findViewById(R.id.email);
        EditText telf = (EditText)findViewById(R.id.telefono);
        TextView resultado = (TextView)findViewById(R.id.result);
        String[] campos = new String[] {"idUsuario"};
        String[] args = new String[] {};
        Cursor max = db.query("Usuarios",campos,null,args,null,null,"idUsuario desc");
        int maxi;
        if (max.moveToFirst()) {
            maxi=max.getInt(0);
            if(codigo.getText()==null||nombre.getText()==null||email.getText()==null||telf.getText()==null){
                resultado.setText("No se han introducido todos los campos");
            }else {
                ContentValues nuevoRegistro = new ContentValues();
                nuevoRegistro.put("idUsuario", maxi);
                nuevoRegistro.put("nombre", nombre.getText() + "");
                nuevoRegistro.put("email", email.getText() + "");
                nuevoRegistro.put("telf", telf.getText() + "");
                db.update("Usuarios", nuevoRegistro, "idUsuario="+codigo.getText(), null);
            }
        }else{
            resultado.setText("Ese id de usuario no existe");
        }
        db.close();
    }

    public void consultar(View v){
        UsuariosSQLiteHelper usdbh =
                new UsuariosSQLiteHelper(this, "DBUsuarios", null, 1);
        SQLiteDatabase db = usdbh.getWritableDatabase();
        EditText codigo = (EditText)findViewById(R.id.codigo);
        EditText nombre = (EditText)findViewById(R.id.nombre);
        EditText email = (EditText)findViewById(R.id.email);
        EditText telf = (EditText)findViewById(R.id.telefono);
        TextView resultado = (TextView)findViewById(R.id.result);
        if(codigo.getText()!=null) {
            String[] args = new String[]{codigo.getText() + ""};
            String sql = "SELECT idUsuario, nombre, email,telf FROM Usuarios ";
            Cursor c = db.rawQuery("SELECT idUsuario, nombre, email FROM Usuarios " +
                    "WHERE nombre=? ", args);
            resultado.setText("");//Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros.
                do {
                    int cod = c.getInt(0);
                    String nom = c.getString(1);
                    String em = c.getString(2);
                    String tel = c.getString(3);
                    resultado.append(cod + " - " + nom + " - " + em + " - " + tel + "\n");
                } while (c.moveToNext());
            }
        }else {
            String[] args = new String[]{};
            Cursor c = db.rawQuery("SELECT idUsuario, nombre, email,telf FROM Usuarios",args);
            resultado.setText("");//Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros.
                do {
                    int cod = c.getInt(0);
                    String nom = c.getString(1);
                    String em = c.getString(2);
                    String tel = c.getString(3);
                    resultado.append(cod + " - " + nom + " - " + em + " - " + tel + "\n");
                } while (c.moveToNext());
            }
        }
    }
}

