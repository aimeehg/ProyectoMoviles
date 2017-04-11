package com.redes.boui.tabbed;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Boui on 26/03/2017.
 */

public class Session {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context ctx;

    public Session(Context ctx){
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences("ProyectoDAM", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setLoggedIn(boolean loggedIn, int userId){
        editor.putBoolean("loggedInMode", loggedIn);
        editor.putInt("KEY",userId);
        editor.commit();
    }

    public boolean loggedIn(){
        return prefs.getBoolean("loggedInMode", false);
    }
}
