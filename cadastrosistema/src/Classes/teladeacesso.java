package Classes;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JFormattedTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class teladeacesso extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nome;
	private JTextField cpf;
	private JTextField telefone;
	private JTextField pesquisar;
	private JTable tbdados;
	private JTextField ID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					teladeacesso frame = new teladeacesso();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	/**
	 * Create the frame.
	 */
	public teladeacesso() {
		setResizable(false);
		setTitle("Cadastro e Consulta de Clientes");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 532, 421);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		nome = new JTextField();
		nome.setColumns(10);
		nome.setBounds(100, 106, 293, 20);
		contentPane.add(nome);
		
		cpf = new JTextField();
		cpf.setColumns(10);
		cpf.setBounds(100, 137, 293, 20);
		contentPane.add(cpf);
		
		telefone = new JTextField();
		telefone.setColumns(10);
		telefone.setBounds(100, 168, 293, 20);
		contentPane.add(telefone);
		
		JLabel lblNewLabel_1 = new JLabel("Pesquisar:");
		lblNewLabel_1.setBounds(24, 48, 66, 20);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Nome:");
		lblNewLabel_1_1.setBounds(24, 109, 50, 14);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("CPF:");
		lblNewLabel_1_1_1.setBounds(24, 140, 50, 14);
		contentPane.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Telefone:");
		lblNewLabel_1_1_1_1.setBounds(24, 171, 67, 14);
		contentPane.add(lblNewLabel_1_1_1_1);
		
		JButton btnNewButton = new JButton("Salvar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(nome.getText().equals("") || cpf.getText().equals("") || telefone.getText().equals("")){
				
					JOptionPane.showMessageDialog(null, "Campos não preenchidos");
					
				
				} else {
					
		    			
						try {
				
		
					
					Connection con = telainicial.faz_conexao();
					String sql = "insert dados_clientes(nome, cpf, telefone) values (?, ?, ?) ";
					
					PreparedStatement stmt = con.prepareStatement(sql);
					
					stmt.setString(1,nome.getText());
					stmt.setString(2, cpf.getText());
					stmt.setString(3,telefone.getText());
					
					stmt.execute();
					
					stmt.close();
					con.close();
						
			
					JOptionPane.showMessageDialog(null, "Cadastro Realizado!");
					
					nome.setText("");
					cpf.setText("");
					telefone.setText("");
										
			} catch (SQLException e2) {
				e2.printStackTrace();
			
			}
			}
						}
						
			
							});
	
		btnNewButton.setBounds(100, 199, 89, 23);
		contentPane.add(btnNewButton);

		
		JButton btnEditar = new JButton("Buscar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			if(pesquisar.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Digite o nome do Cliente !");
				
			}else {
			
				try {
					
					Connection con = telainicial.faz_conexao();
					
					String sql = "select *from dados_clientes where nome LIKE ?";
					
					PreparedStatement stmt = con.prepareStatement(sql);
					
					stmt.setString(1, "%"+ pesquisar.getText() + "%");
										
					ResultSet rs = stmt.executeQuery();
					boolean encontrado = false;
					
					while (rs.next())	{
						encontrado = true;
						
						ID.setText(rs.getString("id"));
						nome.setText(rs.getString("nome"));
						cpf.setText(rs.getString("cpf"));
						telefone.setText(rs.getString("telefone"));
						
						ID.setEnabled(false);
						nome.setEnabled(false);
						cpf.setEnabled(false);
						telefone.setEnabled(false);
					
					}
					
					if (!encontrado) {
						ID.setText("");
						nome.setText("");
						cpf.setText("");
						telefone.setText("");
						
					JOptionPane.showMessageDialog(null, "Cliente não cadastrado!");	
					
					}
					
					
					
					
					rs.close();
					con.close();
					
				
					
					
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
				
			}
			}
		});
		btnEditar.setBounds(403, 45, 89, 21);
		contentPane.add(btnEditar);
		
		JButton btnEdicao = new JButton("Atualizar");
		btnEdicao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 int selectedRow = tbdados.getSelectedRow();
				 
				 if (selectedRow == -1) {
			            JOptionPane.showMessageDialog(null, "Selecione um item para atualizar.");
			            return;
				 }
			String id = tbdados.getValueAt(selectedRow, 0).toString();	 
			String novoNome = nome.getText();
	        String novoCpf = cpf.getText();
	        String novoTelefone = telefone.getText();
            
            try {
                Connection con = telainicial.faz_conexao();
                String sql = "UPDATE dados_clientes SET nome=?, cpf=?, telefone=? WHERE id=?";
                PreparedStatement stmt = con.prepareStatement(sql);
               
                stmt.setString(1, novoNome);
                stmt.setString(2, novoCpf);
                stmt.setString(3, novoTelefone);
                stmt.setString(4, id);

                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Cadastro atualizado com sucesso!");
                   
                } else {
                    JOptionPane.showMessageDialog(null, "Falha ao atualizar o cadastro.");
                }

                stmt.close();
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();   
				
			}
			
			}	
			
		});
		btnEdicao.setBounds(199, 199, 89, 23);
		contentPane.add(btnEdicao);
		
		pesquisar = new JTextField();
		pesquisar.setColumns(10);
		pesquisar.setBounds(100, 45, 293, 20);
		contentPane.add(pesquisar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setBounds(10, 233, 496, 105);
		contentPane.add(scrollPane);
		
		tbdados = new JTable();
		tbdados.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Nome", "CPF", "Telefone"
			}
		));
		scrollPane.setViewportView(tbdados);
		
		JButton btlistardados = new JButton("Listar Dados");
		btlistardados.addActionListener(new ActionListener() {
			
			
		
			public void actionPerformed(ActionEvent e) {
				
				try {
					Connection con = telainicial.faz_conexao();
					
					String sql = "Select * from dados_clientes";
				
					PreparedStatement stmt = con.prepareStatement(sql);
					
					ResultSet rs = stmt.executeQuery();
					
					DefaultTableModel modelo = (DefaultTableModel) tbdados.getModel();
					modelo.setNumRows(0);
					
					while (rs.next()) {
						
						modelo.addRow(new Object[]{rs.getString("id"), rs.getString("nome"), rs.getString("cpf"), rs.getString("telefone")});
					}
					
					rs.close();
					con.close();
					
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
					
				}
			}
			
		});
		
btlistardados.setBounds(195, 348, 112, 23);
contentPane.add(btlistardados);	
JButton btnExclusao = new JButton("Excluir");
btnExclusao.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
		
		if(nome.getText().equals("")){
		JOptionPane.showMessageDialog(null, "Informe o Nome!");
	}   else {
				try {
		
		Connection con = telainicial.faz_conexao();
		
		String sql = "DELETE FROM dados_clientes where nome=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
			
		stmt.setString(1, nome.getText());
		
		stmt.execute();
		
		stmt.close();
		
		con.close();
		
		 JOptionPane.showMessageDialog(null, "Cadastro Excluído");
		 
		 	ID.setText("");
		 	nome.setText("");
			cpf.setText("");
			telefone.setText("");
		 
		
		} catch (Exception e2) {
			// TODO: handle exception
			e2.printStackTrace();
		}
	}
	}
});
btnExclusao.setBounds(298, 199, 89, 23);
contentPane.add(btnExclusao);
JLabel lblNewLabel_1_1_2 = new JLabel("ID:");
lblNewLabel_1_1_2.setBounds(24, 84, 50, 14);
contentPane.add(lblNewLabel_1_1_2);
ID = new JTextField();
ID.setEditable(false);
ID.setColumns(10);
ID.setBounds(100, 76, 77, 20);
contentPane.add(ID);

		
						tbdados.getSelectionModel().addListSelectionListener (new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent event) {
							if (!event.getValueIsAdjusting()) {
								int selectedRow = tbdados.getSelectedRow();
								if (selectedRow != -1) {
									ID.setText(tbdados.getValueAt(selectedRow, 0).toString());
									nome.setText(tbdados.getValueAt(selectedRow, 1).toString());
			                        cpf.setText(tbdados.getValueAt(selectedRow, 2).toString());
			                        telefone.setText(tbdados.getValueAt(selectedRow, 3).toString());
			                    
					}
							
			}	
	}
					
		
		});
		
	}
}
	

