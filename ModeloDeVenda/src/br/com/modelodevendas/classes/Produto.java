/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.modelodevendas.classes;

import java.sql.*;
import br.com.modelodevendas.dal.ModuloConexao;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import net.proteanit.sql.DbUtils;
/**
 *
 * @author mateus
 */
public class Produto {
    
    JTextField nome = new JTextField();
    JTextField preco = new JTextField();
    
    Connection conexao = null;
    PreparedStatement pst =null;
    ResultSet rs = null;
    
    public Produto(){
        conexao = ModuloConexao.conector();
    }

    public JTextField getNome() {
        return nome;
    }
    public JTextField getPreco() {
        return preco;
    }
    
    private void formBranco(){
                nome.setText(null);
                preco.setText(null);               
                
    }
    
    //Utilizado na TelaVendas
    public void formBranco(JTextField nome, JTextField preco, JTextField Quantidade){
            nome.setText(null);
            preco.setText(null);
            Quantidade.setText("1"); 
    }
    
    private boolean validacaoAdiciona(){
        return (nome.getText().isEmpty() || preco.getText().isEmpty());
    
    }
    
    public void adicionar(JTextField nome, JTextField preco){
        
        this.nome = nome;
        this.preco = preco;
        
        String sql = "INSERT INTO produtos (nomepro,preco)"
            + "VALUES(?,?)";
        
        try {
            pst = conexao.prepareStatement(sql);
            
            pst.setString(1,nome.getText());
            pst.setString(2,preco.getText());         
           
            
            
           
            if (validacaoAdiciona()) {
                JOptionPane.showMessageDialog(null,"Todos os campos são obrigatórios");
                
            } else {
                        
                
                int adicionado = pst.executeUpdate();
               
                if(adicionado > 0){
                    JOptionPane.showMessageDialog(null,"Produtos adicionado com sucesso");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
            
        }
        formBranco();
    }
    
    
    
    public void pesquisar_produtos(JTextField pesquisar, JTable tblProdutos){
        String sql = "SELECT * FROM produtos WHERE nomepro LIKE ?";
        
        try {
            pst = conexao.prepareStatement(sql);
            //passando o conteúdo da caixa de pesquisa para o sql
            pst.setString(1, pesquisar.getText()+"%");
            rs = pst.executeQuery();
            
            //utiliza rs2xml para preencher a tabela
            tblProdutos.setModel(DbUtils.resultSetToTableModel(rs));
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
        }
    }
    //utilizado na TelaVendas
    public void pesquisar_produtos(JTextField pesquisador, JTextField nome, JTable produtos){
        String sql = "SELECT idpro AS id, nomepro AS nome, preco AS preco FROM "
                + "produtos WHERE nomepro LIKE ?";
        
        try {
            pst = conexao.prepareStatement(sql);
          
            pst.setString(1, pesquisador.getText()+"%");
            rs = pst.executeQuery();
            
            //utiliza rs2xml para preencher a tabela
            produtos.setModel(DbUtils.resultSetToTableModel(rs));
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
        }
        
    }
    
    
    
    public void setar_campo(JTextField id, JTextField nome, JTextField preco,JTable tblProdutos){
        
        int setar = tblProdutos.getSelectedRow();
        id.setText(tblProdutos.getModel().getValueAt(setar, 0).toString());
        nome.setText(tblProdutos.getModel().getValueAt(setar, 1).toString());
        preco.setText(tblProdutos.getModel().getValueAt(setar, 2).toString());
        
    }
    
    
    public void alterar(JTextField nome,JTextField preco,JTextField id){
        
        this.nome = nome;
        this.preco = preco;
        String sql = "UPDATE produtos SET preco=?, nomepro=? WHERE idpro=?";
        
        try {
            pst = conexao.prepareStatement(sql);
            
            pst.setString(1,preco.getText());
            pst.setString(2,nome.getText());
            pst.setString(3,id.getText());
                                 
            
            
            if(validacaoAdiciona()){
                JOptionPane.showMessageDialog(null,"Preencha todos os campos "); 
            }else{
                
                
                
                int adicionado = pst.executeUpdate();
                
                
                if(adicionado > 0){
                    JOptionPane.showMessageDialog(null,"Produto alterado com sucesso");   
                }
            }            
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
        }
        
        formBranco();
    }
    
    public void remover(JTextField id){
        int confirma = JOptionPane.showConfirmDialog(null,"Tem certeza que seja remover "
                + "este produto ?","Atenção",JOptionPane.YES_NO_OPTION);
        
        if(confirma == JOptionPane.YES_OPTION ){
            String sql = "DELETE FROM produtos WHERE idpro=?";
            
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, id.getText());
                int apagado = pst.executeUpdate();
                if(apagado>0){
                    JOptionPane.showMessageDialog(null,"Produto removido com sucesso");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,e);
            }
            
            formBranco();
        }
    }
}
