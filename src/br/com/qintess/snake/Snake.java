package br.com.qintess.snake;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class Snake extends JFrame{
	
	public Snake() {
		inicJogo();
	}	
		
	public void inicJogo() {	
		
		add(new Board());
		
		 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	       
		 	// Define o tamanho da janela
	       	setSize(600, 600);
	        
	        // A localização
	        setLocationRelativeTo(null);
	       
	        // Define o titulo da janela
	        setTitle("Snake");

	        // Impede o redimensionamento da janela
	        setResizable(false);
	        
	        // Mostra a janela
	        setVisible(true);
	}
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(() -> {
            JFrame jg = new Snake();
            jg.setVisible(true);
        });
	}

}
