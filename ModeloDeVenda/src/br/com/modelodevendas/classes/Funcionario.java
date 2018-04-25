/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.modelodevendas.classes;

import javax.swing.JTextField;
import java.sql.*;
import br.com.modelodevendas.dal.ModuloConexao;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author mateus
 */
public class Funcionario{
    
    JTextField nome = new JTextField();
    JTextField login = new JTextField();
    JTextField senha = new JTextField();
    JTextField cpf = new JTextField();
    JComboBox perfil = new JComboBox();
    
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    public Funcionario() {
        conexao = ModuloConexao.conector();
    }
    
    //Consulta BD
    public void consultar (JTextField id, JTextField nome, JTextField login, JTextField senha, JTextField cpf,
    JComboBox perfil, JLabel ajuda){
        
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.cpf = cpf;
        this.perfil = perfil;
        
        String sql = "SELECT * FROM funcionario WHERE iduser =? ";
        
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, id.getText());
            rs = pst.executeQuery();
            
            if (rs.next()) {               
                
                nome.setText(rs.getString(2));
                login.setText(rs.getString(4));
                cpf.setText(rs.getString(3)); 
                senha.setText(rs.getString(5));
                perfil.setSelectedItem(rs.getString(6));
                
                ajuda.setVisible(false);//label de ajuda na hora de buscar
            } else {                
                ajuda.setVisible(true);
                JOptionPane.showMessageDialog(null,"Usuário não cadastrado");
                
                formBranco();
                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //validação caso null
    private boolean validacaoAdiciona(){
        return (nome.getText().isEmpty() || cpf.getText().isEmpty() 
             ||login.getText().isEmpty() ||senha.getText().isEmpty()
                || (perfil.getSelectedItem() == null));
            
        
    }
    
    //Coloca o formulario em branco
    public void formBranco(){
                nome.setText(null);
                login.setText(null);
                cpf.setText(null); 
                senha.setText(null);
                perfil.setSelectedItem(null);
    }
    
    //Adiciona no DB
    public void adicionar(JTextField nome, JTextField login, JTextField senha, 
            JTextField cpf,JComboBox perfil){
        
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.cpf = cpf;
        this.perfil = perfil;
        
        String sql = "INSERT INTO funcionario (usuario,cpf,login,senha,perfil)"
            + "VALUES(?,?,?,?,?)";
        
        try {
            pst = conexao.prepareStatement(sql);
            
            pst.setString(1,nome.getText());
            pst.setString(2,cpf.getText());
            pst.setString(3,login.getText());
            pst.setString(4,senha.getText());
            pst.setString(5,perfil.getSelectedItem().toString());
            
         
            if (validacaoAdiciona()) {
                JOptionPane.showMessageDialog(null,"Todos os campos são obrigatórios");
                
            } else {
                        
                
                int adicionado = pst.executeUpdate();
                
                if(adicionado > 0){
                    JOptionPane.showMessageDialog(null,"Funcionário adicionado com sucesso");
                   
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
            
        }
        formBranco();
    }
    
    //Update no DB
    public void alterar(JTextField id,JTextField nome, JTextField login, JTextField senha, JTextField cpf,
    JComboBox perfil){
        
        
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.cpf = cpf;
        this.perfil = perfil;
        
        
        String sql = "UPDATE funcionario SET usuario=?,login=?, senha=?,perfil=?, cpf=? WHERE id=?";
        
        try {
            pst = conexao.prepareStatement(sql);
            
            pst.setString(1,nome.getText());            
            pst.setString(2,login.getText());
            pst.setString(3,senha.getText());
            pst.setString(4,perfil.getSelectedItem().toString());
            pst.setString(5,cpf.getText());
            
            pst.setString(6,id.getText());
            
            if(validacaoAdiciona()){
                JOptionPane.showMessageDialog(null,"Preencha todos os campos "
                        + "obrigatórios"); 
            }else{
                
             
                int adicionado = pst.executeUpdate();
             
                if(adicionado > 0){
                    JOptionPane.showMessageDialog(null,"Funcionário alterado com sucesso");
                }
            }
            
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    //Deleta dado no DB
    public void remover(JTextField id){
        int confirma = JOptionPane.showConfirmDialog(null,"Tem certeza que seja remover "
                + "este funcionário ?","Atenção",JOptionPane.YES_NO_OPTION);
        
        if(confirma == JOptionPane.YES_OPTION ){
            String sql = "DELETE FROM funcionario WHERE iduser=?";
            
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, id.getText());
                int apagado = pst.executeUpdate();
                if(apagado>0){
                    JOptionPane.showMessageDialog(null,"Funcionário removido com sucesso");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,e);
            }
            
            formBranco();
        }
    }

       
}
