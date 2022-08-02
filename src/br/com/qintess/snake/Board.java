package br.com.qintess.snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener{
	
	// Definindo o tamanho do painel
	private final int TELA_WIDTH = 600;
	private final int TELA_HEIGHT = 600;
	
	// Definindo o tamanho dos quadratos
	private final int TAM_PONTO = 10;
	
	// Tamanho total do paniel altura x largura
	private final int TOT_TELA = (TELA_WIDTH * TELA_HEIGHT)/(TAM_PONTO*TAM_PONTO);
	
	// posi��o randonica
	 private final int RAND_POSICAO = 29;
	
	// Definindo o tempo de Jogo
	private final int DELAY = 120;
	
	// Defini��o de X e Y do jogo
	private int[] x = new int[TOT_TELA];
	private int[] y = new int[TOT_TELA];
	
	// Definindo o variavel da cobra
	private int pontos;
	
	// Definindo variavel da posi��o da comida
	private int comida_x,comida_y;
	
	// Score de pontua��o
	private int PONT=0;

	// Definindo variaveis dos movimentos
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
	
	// Definindo Status do Jogo
	private boolean estaJogando = true;
	
	// definindo as variaveis de tempo e imagens do jogo
	private Timer tempo;
	private Image bola;
	private Image comida;
	private Image cabeca;
	
	// Definindo mensagem do placar, fonte e a escrita da tela
		String SCORE ="SCORE: "+ PONT;
		Font SCORE_FONT = new Font("Consolas",Font.BOLD,12);
		FontMetrics SCORE_METRICA = this.getFontMetrics(SCORE_FONT);
	
	// definindo metodos e construtores do jogo
	
	public Board() {
		// definindo teclado e plano de fundo.
		addKeyListener(new TAdapter());
		setBackground(Color.WHITE);
		
		// imagens da Cobra, Cabe�a e comida
		ImageIcon bola_ = new ImageIcon("src/bola.png");
        bola = bola_.getImage();
        ImageIcon comida_ = new ImageIcon("src/comida.png");
        comida = comida_.getImage();
        ImageIcon cabe�a_ = new ImageIcon("src/cabeca.png");
        cabeca = cabe�a_.getImage();
		
        
        // Define o foco e tamanho da tela
        setFocusable(true);
        setSize(TELA_WIDTH, TELA_HEIGHT);

        // Inicializa��o do Jogo
        jogando();
	}
		
	public void jogando()
	{
		// Definindo a quantidade de pontos iniciais
        pontos = 2;

        // Definindo a posi��o de x e y no jogo
        for (int i = 0; i < pontos; i++) {
            x[i] = 50 - i*10;
            y[i] = 50;
        }

        // chama metodo da comida
        localComida();

        // Temporiza��o do jogo
        tempo = new Timer(DELAY, this);
        tempo.start();
	}
	
	// M�todo para desenhar elementos na tela do jogo
    
	@Override
    public void paint (Graphics g)
    {
        // Define o atribuito para a classe pr�pria
        super.paint(g);

        // Verifica se o jogo esta em andamento, se estiver desenha na tela, se n�o estiver, o jogo � dado como o fim
        if (estaJogando) {
            // Desenha a comida no plano (x,y) do jogo
            g.drawImage(comida, comida_x, comida_y, this);

            // Para cada ponto da cobrinha, desenha a cabe�a e o corpo em (x,y)
            for (int i = 0; i < pontos; i++){
                if (i == 0){ 
                	g.drawImage(cabeca, x[i], y[i], this); 
                	}else{ 
                	g.drawImage(bola, x[i], y[i], this); 
         }
     }

            // Desenha a pontua��o na tela
            paintPontuacao(g);

            // Executa a sincronia de dados
            Toolkit.getDefaultToolkit().sync();

            // Pausa os gr�ficos
            g.dispose();
        	}else{
            // Executa o fim de jogo
            FimDeJogo(g);
        }
    }
    
 // M�todo para desenhar a pontua��o na tela
    public void paintPontuacao (Graphics g) {
        // Define a frase para escrever
        SCORE = "PONTUA��O: " + PONT;
        // Define o tamanho da fonte
        SCORE_METRICA = this.getFontMetrics(SCORE_FONT);

        // Define a cor da fonte
        g.setColor(Color.white);
        // Seta a fonte para o gr�fico
        g.setFont(SCORE_FONT);
        // Desenha a fonte na tela
        g.drawString(SCORE, (TELA_WIDTH - SCORE_METRICA.stringWidth(SCORE)) - 10, TELA_HEIGHT - 10);
    }

    public void FimDeJogo (Graphics g) {
    	// Define a cor da fonte, tamanho e local da mensagem
        String msg = "FIM DE JOGO! Pontua��o: " + PONT;
        Font tam_fonte = new Font("Consolas", Font.BOLD, 12);
        FontMetrics metrica = this.getFontMetrics(tam_fonte);
        g.setColor(Color.white);
        g.setFont(tam_fonte);
        g.drawString(msg, (TELA_WIDTH - metrica.stringWidth(msg)) / 2, TELA_HEIGHT / 2);
    }
	
    // M�todo para checar se a cobrinha comeu a comida
    public void checarComida ()
    {
        // Se ele comer na mesma posi��o (x,y) ent�o aumenta o corpo da cobrinha
        // aumenta a pontua��o e gera uma nova comida
        if ((x[0] == comida_x) && (y[0] == comida_y))
        {
            pontos++;
            PONT++;
            localComida();
        }
    }

    // M�todo para mover a cobrinha na tela
    public void mover ()
    {
        // Crescimento da cobra na tela
        for (int i = pontos; i > 0; i--){
            x[i] = x[(i - 1)];
            y[i] = y[(i - 1)];
        }

        if (left){
            x[0] -= TAM_PONTO;
        }

        if (right) {
            x[0] += TAM_PONTO;
        }

        if (up) {
            y[0] -= TAM_PONTO;
        }

        if (down) {
            y[0] += TAM_PONTO;
        }
        
    }

    // M�todo para checar colis�o entre a cobrinha e as bordas do jogo
    public void checarColisao ()
    {
        // Verifica se houve colis�o
        
        for (int i = pontos; i > 0; i--){
            if ((i > 4) && (x[0] == x[i]) && (y[0] == y[i])){ 
            	estaJogando = false; 
            }
        }

        // Define os limites da cobra na tela
        
        if (y[0] > TELA_HEIGHT){ 
        	estaJogando = false; 
        	}

        if (y[0] < 0){ 
        	estaJogando = false; 
        	}

        if (x[0] > TELA_WIDTH){ 
        	estaJogando = false; 
        	}

        if (x[0] < 0){ 
        	estaJogando = false;
        	}
    }

    // M�todo que gera uma comida na tela
    public void localComida (){
        // Defini��o do local aletorio da comida
    	
        int random = (int) (Math.random() * RAND_POSICAO);
        comida_x = (random * TAM_PONTO);

        random = (int) (Math.random() * RAND_POSICAO);
        comida_y = (random * TAM_PONTO);
    }

    // M�todo na execu��o do jogo
    public void actionPerformed (ActionEvent e){
        // checagem geral
        
        if (estaJogando){
            checarComida();
            checarColisao();
            mover();
        }
        repaint();
    }

    // Classe para analisar o teclado
    private class TAdapter extends KeyAdapter
    {

        // M�todo para verificar o que foi teclado
        @Override
        public void keyPressed (KeyEvent e){
            
            int teclado =  e.getKeyCode();

            // Defini��o do comandos do teclado
           
            if ((teclado == KeyEvent.VK_LEFT) && (!right)) {
                left = true;
                up = false;
                down = false;
            }

            if ((teclado == KeyEvent.VK_RIGHT) && (!left)) {
                right = true;
                up = false;
                down = false;
            }

            if ((teclado == KeyEvent.VK_UP) && (!down)){
                up = true;
                left = false;
                right = false;
            }

            if ((teclado == KeyEvent.VK_DOWN) && (!up)) {
                down = true;
                left = false;
                right = false;
            }
        }
    }
	
			
	
}
