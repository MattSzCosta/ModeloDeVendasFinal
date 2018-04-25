/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.modelodevendas.classes;

import br.com.modelodevendas.dal.ModuloConexao;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author mateus
 */
public class Cliente {
    
    JTextField nome = new JTextField();
    JTextField login = new JTextField();
    JTextField email = new JTextField();
    JTextField cpf = new JTextField();
    JTextField fone = new JTextField();
    JTextField campoPesquisar = new JTextField();
    
    Connection conexao = null;
    PreparedStatement pst =null;
    ResultSet rs = null;
    
    public Cliente(){
        conexao = ModuloConexao.conector();
    }

    public JTextField getNome() {
        return nome;
    }

    public JTextField getCpf() {
        return cpf;
    }

    
    
    //Seta o formulario para null (deixa o txt em branco)
    private void formBranco(){
                nome.setText(null);
                fone.setText(null);
                cpf.setText(null); 
                email.setText(null);
                campoPesquisar.setText(null);                
    }    
    //utilizado na TelaVendas
    public void formBranco(JTextField nome, JTextField cpf, JTextField pesquisar){
                nome.setText(null);
                cpf.setText(null); 
                pesquisar.setText(null);                
    }
    
    
    
    //Conferer campos null
    private boolean validacaoAdiciona(){
        return (nome.getText().isEmpty() || cpf.getText().isEmpty());    
    }    
    //utilizado na TelaVendas
    public boolean validacaoAdiciona(JTextField nome, JTextField cpf){
        return (nome.getText().isEmpty() || cpf.getText().isEmpty());    
    }
    
    
    
    //Adiciona no DB
    public void adicionar(JTextField nome, JTextField cpf, JTextField fone, JTextField email ){
        
        this.nome = nome;
        this.cpf = cpf;
        this.fone = fone;
        this.email = email;
        
        String sql = "INSERT INTO cliente (nomecli,cpfcli,fonecli,emailcli)"
            + "VALUES(?,?,?,?)";
        
        try {
            pst = conexao.prepareStatement(sql);
            
            pst.setString(1,nome.getText());
            pst.setString(2,cpf.getText());
            pst.setString(3,fone.getText());
            pst.setString(4,email.getText());
            
            
            
            if (validacaoAdiciona()) {
                JOptionPane.showMessageDialog(null,"Todos os campos são obrigatórios");
                
            } else {
                        
                int adicionado = pst.executeUpdate();                
                if(adicionado > 0){
                    JOptionPane.showMessageDialog(null,"Cliente adicionado com sucesso");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
            
        }
        formBranco();
    }
    
    //Busca no DB
    public void pesquisar_cliente(JTextField campoPesquisa, JTable tblClientes){
        String sql = "SELECT * FROM cliente WHERE nomecli LIKE ?";
        
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, campoPesquisa.getText()+"%");
            rs = pst.executeQuery();
            
            //utiliza rs2xml para preencher a tabela
            tblClientes.setModel(DbUtils.resultSetToTableModel(rs));
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    //Utilizado na TelaVendas
    public void pesquisar_cliente(JTextField pesquisar, JTable tblClientes, boolean i){
        String sql = "SELECT idcli AS id, nomecli AS nome, cpfcli AS cpf FROM "
                + "cliente WHERE nomecli LIKE ?";
        
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, pesquisar.getText()+"%");
            rs = pst.executeQuery();
            
            //utiliza rs2xml para preencher a tabela
            tblClientes.setModel(DbUtils.resultSetToTableModel(rs));
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    
    //Seta as tabelas
    public void setar_campo(JTextField id,JTextField nome, JTextField cpf, JTextField fone, JTextField email, JTable tblClientes){
        
        int setar = tblClientes.getSelectedRow();
        id.setText(tblClientes.getModel().getValueAt(setar, 0).toString());
        nome.setText(tblClientes.getModel().getValueAt(setar, 1).toString());
        cpf.setText(tblClientes.getModel().getValueAt(setar, 2).toString());
        fone.setText(tblClientes.getModel().getValueAt(setar, 3).toString());
        email.setText(tblClientes.getModel().getValueAt(setar, 4).toString());        
    }    
    //utilizado no TelaVendas
    public void setar_campos(JTextField id, JTextField nome, JTextField cpf,JTable tblClientes){
        //armazena os dados para o recibo
        this.nome = nome;
        this.cpf = cpf;
        
       
        int setarCli = tblClientes.getSelectedRow();
        id.setText(tblClientes.getModel().getValueAt(setarCli, 0).toString());
        nome.setText(tblClientes.getModel().getValueAt(setarCli, 1).toString());
        cpf.setText(tblClientes.getModel().getValueAt(setarCli, 2).toString());        
    }
    
    
    //Update no DB
    public void alterar(JTextField nome, JTextField cpf, JTextField fone, JTextField email, JTextField id){
        
        this.nome = nome;
        this.cpf = cpf;
        this.fone = fone;
        this.email = email;
        
        String sql = "UPDATE cliente SET nomecli=?,cpfcli=?, fonecli=?, emailcli=? WHERE idcli=?";
        
        try {
            pst = conexao.prepareStatement(sql);
            
            pst.setString(1,nome.getText());            
            pst.setString(2,cpf.getText());
            pst.setString(3,fone.getText());                     
            pst.setString(4,email.getText());
            pst.setString(5,id.getText());
            
            if(validacaoAdiciona()){
                JOptionPane.showMessageDialog(null,"Preencha todos os campos "
                        + "obrigatórios"); 
            }else{
                
                int adicionado = pst.executeUpdate();
                
                if(adicionado > 0){
                    JOptionPane.showMessageDialog(null,"Cliente alterado com sucesso");   
                }
            }            
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
        }
        
        formBranco();
    }
    
    
    //Delata no DB
    public void remover(JTextField id){
        int confirma = JOptionPane.showConfirmDialog(null,"Tem certeza que seja remover "
                + "este cliente ?","Atenção",JOptionPane.YES_NO_OPTION);
        
        if(confirma == JOptionPane.YES_OPTION ){
            String sql = "DELETE FROM cliente WHERE idcli=?";
            
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, id.getText());
                int apagado = pst.executeUpdate();
                if(apagado>0){
                    JOptionPane.showMessageDialog(null,"Cliente removido com sucesso");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,e);
            }
            
            formBranco();
        }
    }
    
}
