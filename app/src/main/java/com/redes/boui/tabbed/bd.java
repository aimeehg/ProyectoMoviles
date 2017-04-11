package com.redes.boui.tabbed;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;

        import java.util.ArrayList;
        import java.util.List;

/**
 * Created by Boui on 06/03/2017.
 */

public class bd extends SQLiteOpenHelper {
    private static final int VERSION_BASEDATOS = 2;

    // Nombre de nuestro archivo de base de datos
    private static final String NOMBRE_BASEDATOS = "basedatos_proyecto.db";

    // Sentencia SQL para la creación de una tabla
    private static final String TABLA_USUARIOS = "CREATE TABLE usuarios" +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, paterno TEXT, materno TEXT," +
            "direccion TEXT, edad INTEGER, peso INTEGER, altura REAL, usuario TEXT, password TEXT," +
            "genero INTEGER, medicamento INTEGER)";

   


    // CONSTRUCTOR de la clase
    public bd(Context context) {
        super(context, NOMBRE_BASEDATOS, null, VERSION_BASEDATOS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLA_USUARIOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_USUARIOS);
        onCreate(db);
    }

    public void insertarUsuario(String nom, String pat, String mat, String dir, int edad, int peso,
                                float alt, String usu, String pass, int gen, int med) {
        SQLiteDatabase db = getWritableDatabase();
        if(db != null){
            ContentValues valores = new ContentValues();
            valores.put("nombre", nom);
            valores.put("paterno", pat);
            valores.put("materno", mat);
            valores.put("direccion", dir);
            valores.put("edad", edad);
            valores.put("peso", peso);
            valores.put("altura", alt);
            valores.put("usuario", usu);
            valores.put("password", pass);
            valores.put("genero", gen);
            valores.put("medicamento", med);
            db.insert("usuarios", null, valores);
            db.close();
        }
    }
    public int Login(String usuario, String password){
        int id = -1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT id FROM usuarios WHERE usuario=? AND password=?",new String[]{usuario,password});
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            id = cursor.getInt(0);
            cursor.close();
        }
        return id;
    }

    public void borrarUsuario(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("usuarios","id="+id,null);
        db.close();
    }
    public void eliminarbd(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("usuarios",null,null);
        db.close();
    }
}
