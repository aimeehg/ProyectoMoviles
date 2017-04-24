package com.redes.boui.tabbed;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.os.Environment;
        import android.util.Log;

        import java.io.File;
        import java.io.FileWriter;
        import java.text.DateFormat;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.List;

/**
 * Created by Boui on 06/03/2017.
 */

public class bd extends SQLiteOpenHelper {
    private static final int VERSION_BASEDATOS = 1;

    // Nombre de nuestro archivo de base de datos
    private static final String NOMBRE_BASEDATOS = "db_proyecto1.db";

    // Sentencia SQL para la creación de una tabla
    private static final String TABLA_USUARIOS = "CREATE TABLE usuarios" +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, paterno TEXT, materno TEXT," +
            "direccion TEXT, edad INTEGER, peso INTEGER, altura REAL, usuario TEXT, password TEXT," +
            "genero INTEGER, medicamento INTEGER)";
    private static final String TABLA_REGISTROS = "CREATE TABLE registros"+
            "(ID_REG INTEGER PRIMARY KEY AUTOINCREMENT, year INTEGER, mes INTEGER, dia INTEGER,"+
            "hora TEXT, cuando INTEGER, nivel INTEGER, id_paciente INTEGER, fecha TEXT, FOREIGN KEY(id_paciente) " +
            "REFERENCES usuarios(ID))";
    private static final String TABLA_MEDICO = "CREATE TABLE medico"+
            "(ID_MED INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, apellidos TEXT, " +
            "dir_consultorio TEXT, celular  TEXT, email TEXT, id_paciente INTEGER, FOREIGN KEY(id_paciente) " +
            "REFERENCES usuarios(ID))";
    public String databasePath = "";
    public File database;




    // CONSTRUCTOR de la clase
    public bd(Context context) {
        super(context, NOMBRE_BASEDATOS, null, VERSION_BASEDATOS);
        databasePath = context.getDatabasePath("db_proyecto.db").getPath();
        database = context.getDatabasePath("db_proyecto.db");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLA_USUARIOS);
        db.execSQL(TABLA_REGISTROS);
        db.execSQL(TABLA_MEDICO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_USUARIOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_REGISTROS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_MEDICO);
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
    public Paciente getPaciente(int id_paciente){
        SQLiteDatabase db = getReadableDatabase();
        String[] valores_recuperar = {"ID", "nombre", "paterno", "materno", "direccion", "edad", "peso", "altura",
                                        "usuario", "password", "genero", "medicamento"};
        Cursor c = db.query("usuarios", valores_recuperar, "ID=" + id_paciente,
                null, null, null, null, null);
        Paciente paciente = null;
        if(c.getCount()>0) {
            if (c != null) {
                c.moveToFirst();
            }
            Log.e("getpaciente1 ", c.getInt(0) + c.getString(1) + c.getString(2)+
                    c.getString(3) +c.getString(4) +" "+c.getInt(5)+" "+c.getInt(6)+" "+c.getFloat(7)+" "+c.getString(8)+
                    " "+c.getString(9)+" "+c.getInt(10)+" "+c.getInt(11));
            paciente = new Paciente(c.getInt(0),c.getString(1), c.getString(2),
                    c.getString(3),c.getString(4),c.getInt(5),c.getInt(6),c.getFloat(7),c.getString(8),
                    c.getString(9),c.getInt(10),c.getInt(11));

        }

        db.close();
        c.close();
        return paciente;
    }
    public Paciente getPacienteShort(int id_paciente){
        SQLiteDatabase db = getReadableDatabase();
        String[] valores_recuperar = {"ID", "nombre", "paterno", "materno"};
        Cursor c = db.query("usuarios", valores_recuperar, "ID=" + id_paciente,
                null, null, null, null, null);
        Paciente paciente = null;
        if(c.getCount()>0) {
            if (c != null) {
                c.moveToFirst();
            }
            Log.e("getpaciente ", c.getString(1));
            paciente = new Paciente(c.getInt(0),c.getString(1), c.getString(2),
                    c.getString(3));

        }
        //medico = null;
        db.close();
        c.close();
        return paciente;
    }
    public void insertarRegistro(int year, int mes, int dia, String hora, int cuando, int nivel,
                                 int idpaciente) {
        SQLiteDatabase db = getWritableDatabase();
        String mes1 = "";
        String dia1 = "";
        if(db != null){
            ContentValues valores = new ContentValues();
           valores.put("year", year);
            valores.put("mes", mes);
            valores.put("dia", dia);
            valores.put("hora", hora);
            valores.put("cuando", cuando);
            valores.put("nivel", nivel);
            valores.put("id_paciente", idpaciente);
            if (mes <10){
                mes1 = "0"+String.valueOf(mes);
            }else{
                mes1 = String.valueOf(mes);
            }
            if (dia <10){
                dia1 = "0"+String.valueOf(dia);
            }else{
                dia1 = String.valueOf(dia);
            }
            String fechita = String.valueOf(year) + "-" + mes1 + "-" + dia1;
            valores.put("fecha", fechita);

            db.insert("registros", null, valores);
            db.close();
        }
    }
    public void insertarMedico(String nombre, String apellidos, String dir, String cel, String email,
                                 int idpaciente) {
        SQLiteDatabase db = getWritableDatabase();
        if(db != null){
            ContentValues valores = new ContentValues();
            valores.put("nombre", nombre);
            valores.put("apellidos", apellidos);
            valores.put("dir_consultorio", dir);
            valores.put("celular", cel);
            valores.put("email", email);
            valores.put("id_paciente", idpaciente);

            db.insert("medico", null, valores);
            db.close();
        }
    }
    public Medico getMedico(int id_paciente){
        SQLiteDatabase db = getReadableDatabase();
        String[] valores_recuperar = {"ID_MED", "nombre", "apellidos", "dir_consultorio", "celular", "email", "id_paciente"};
        Cursor c = db.query("medico", valores_recuperar, "id_paciente=" + id_paciente,
                null, null, null, null, null);
        Medico medico = null;
        if(c.getCount()>0) {
            if (c != null) {
                c.moveToFirst();
            }
            Log.e("hey", c.getString(1));
           medico = new Medico(c.getInt(0),c.getString(1), c.getString(2),
                    c.getString(3), c.getString(5), c.getString(4), c.getInt(6));

        }
        //medico = null;
        db.close();
        c.close();
        return medico;
    }
    public int editarMedico(Medico medico){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("nombre", medico.getNombre());
        values.put("apellidos", medico.getApellidos());
        values.put("dir_consultorio", medico.getDirConsultorio());
        values.put("celular", medico.getCelular());
        values.put("email", medico.getEmail());
        // updating row
        return db.update("medico", values, "ID_MED = ?",
                new String[] { String.valueOf(medico.getId_med()) });
    }
    public int editarPaciente(Paciente paciente){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("nombre", paciente.getNom());
        values.put("paterno", paciente.getPat());
        values.put("materno", paciente.getMat());
        values.put("direccion", paciente.getDir());
        values.put("edad", paciente.getEdad());
        values.put("peso", paciente.getPeso());
        values.put("altura", paciente.getAlt());
        values.put("usuario", paciente.getUsu());
        values.put("password", paciente.getPass());
        values.put("genero", paciente.getGen());
        values.put("medicamento", paciente.getMed());

        // updating row
        return db.update("usuarios", values, "ID = ?",
                new String[] { String.valueOf(paciente.getId()) });
    }
    public List<RegistroGlucosa> getRegistros(int id_paciente, String mes ){
        List<RegistroGlucosa> registros = new ArrayList<RegistroGlucosa>();
        String selectQuery = "SELECT * FROM registros WHERE id_paciente=? AND mes=?";
        Log.e("bd.java", selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, new String[] { String.valueOf(id_paciente), mes});
        //yyyy, mm, dd, hh, cuando;
        if(c.moveToFirst()){
            do{
                RegistroGlucosa reg = new RegistroGlucosa(
                    c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),
                        c.getString(5), c.getInt(6), c.getInt(7)
                );
              //  Log.e("bd.java", "HEYYYYYYYYYYYYYYY");
               // Log.e("bd.java", c.getString(3));
                registros.add(reg);
            }while (c.moveToNext());
        }
        db.close();
        c.close();
        return registros;
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
    public void exportDB(String path, int id_paciente) {

        File dbFile = database;
       // DBHelper dbhelper = new DBHelper(getApplicationContext());
        File exportDir = new File(path, "");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }
        DateFormat df = new SimpleDateFormat("dd MM yyyy, HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());
        File file = new File(exportDir, date+".csv");
        try {
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            SQLiteDatabase db = this.getReadableDatabase();
            //Cursor curCSV = db.rawQuery("SELECT * FROM registros", null);
            String selectQuery = "SELECT * FROM registros WHERE id_paciente=?";
            Cursor curCSV = db.rawQuery(selectQuery, new String[] { String.valueOf(id_paciente)});
            String[] holi = {"ID_REG", "Fecha", "Hora", "Nivel", "Cuando", "id_paciente"};
            csvWrite.writeNext(holi);
           // Log.e("bd.java",curCSV.getColumnNames() );
            while (curCSV.moveToNext()) {
                //Which column you want to exprort
                String arrStr[] = {curCSV.getString(0), curCSV.getString(8), curCSV.getString(4),curCSV.getString(6),
                        curCSV.getString(5),curCSV.getString(7)};
                csvWrite.writeNext(arrStr);
            }
            csvWrite.close();
            curCSV.close();
        } catch (Exception sqlEx) {
            Log.e("bd.java", sqlEx.getMessage(), sqlEx);
        }
    }


    public void eliminar_3meses(){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM registros WHERE fecha <= date('now','-3 month')";
        db.execSQL(sql);

    }

}
