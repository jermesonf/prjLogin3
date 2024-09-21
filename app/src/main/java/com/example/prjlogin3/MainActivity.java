package com.example.prjlogin3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    EditText login,senha;
    Acessa objA = new Acessa();
    String loginAntigo, senhaAntigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.txtLogin);
        senha = findViewById(R.id.txtSenha);
        objA.entBanco(this);

        consultar();

    }


    public void consultar(){


        try{
            objA.RS = objA.stmt.executeQuery("SELECT * FROM login ORDER BY usuario");
            objA.RS.next();
            preencher();
        }catch (SQLException ex) { }


    }
    // FIM METODO CONSULTAR

    public void preencher(){


        try{
            login.setText(objA.RS.getString("usuario"));
            senha.setText(objA.RS.getString("senha"));
            loginAntigo=objA.RS.getString("usuario");
            senhaAntigo=objA.RS.getString("senha");
        }catch (SQLException ex) { }


    }
    //FIM METODO PREENCHER

     public void avancar(View v){


        try{
            if(objA.RS.next()){
                preencher();
            }
        }catch (SQLException ex){ }


     }
     //FIM METODO AVANCAR


    public void voltar(View v){


        try{
            if(objA.RS.previous()){
                preencher();
            }
        }catch (SQLException ex){ }


    }
    //FIM METODO VOLTAR


    public void alterar(View v){

        //OBS: O IDEAL PARA UPDATE E DELETE É ID
        String loginNovo, senhaNovo;
        loginNovo = login.getText().toString();
        senhaNovo = senha.getText().toString();

        try{
            int result = objA.stmt.executeUpdate("UPDATE login SET usuario = '"+loginNovo+"', senha='"+senhaNovo+"' WHERE usuario='"+loginAntigo+"' AND senha='"+senhaAntigo+"'" );
            if(result > 0){
                Toast.makeText(getApplicationContext(), "Dados alterados com sucesso", Toast.LENGTH_SHORT).show();
                consultar();
            }
            else{
                Toast.makeText(this, "Não foi possivel alterar.", Toast.LENGTH_LONG).show();
            }
        }catch (SQLException ex)
        {
        }


    }
    //FIM METODO ALTERAR

    public void deletar(View v){

        try{
            int result = objA.stmt.executeUpdate("DELETE FROM login WHERE usuario='"+loginAntigo+"' AND senha='"+senhaAntigo+"'" );
            if(result > 0){
                Toast.makeText(getApplicationContext(), "Dados deletado com sucesso", Toast.LENGTH_SHORT).show();
                consultar();
            }
            else{
                Toast.makeText(getApplicationContext(), "Não foi possivel deletar.", Toast.LENGTH_LONG).show();
            }
        }catch (SQLException ex)
        {
        }


    }
    //FIM METODO DELETAR

}//FIM