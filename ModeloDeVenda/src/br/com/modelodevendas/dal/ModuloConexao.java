/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.modelodevendas.dal;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author mateus
 */
public class ModuloConexao {
    //método responsável por estabelecer a conexão com o banco
    public static Connection conector (){
        java.sql.Connection conexao = null;
        
        String driver = "com.mysql.jdbc.Driver";// "chama" o driver
        
        //Armezenar informações referente ao banco
        String url = "jdbc:mysql://localhost:3306/dbloja";
        String user = "mateus";
        String password = "";
        
        //Estabelencedo a conexão com o banco
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
            //Ajuda a identificação do erro
            //System.out.println(e);
            return null;
        }
    }
    
}
