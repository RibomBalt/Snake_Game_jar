package cobalt.snake;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PipedOutputStream;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainFrame extends JFrame{
	Data data=null;
	PipedOutputStream pos=null;
	JLabel jl=null;
	JPanel jp=null;
	
	public MainFrame(Data data,PipedOutputStream pos){
		this.data=data;
		this.pos=pos;
		this.setTitle("̰����-Cobalt");
		this.setBounds(400,100,500,500);
		//������岼��
		this.setLayout(new BorderLayout(5,5));
		jp=new JPanel();
		JPanel jp2=new JPanel();
		this.add(jp, BorderLayout.CENTER);
		this.add(jp2, BorderLayout.NORTH);
		//jp2�Ʒְ岼��
		jp2.setLayout(new FlowLayout());
		jl=new JLabel("Score:0");
		jp2.add(jl);
		//����岼��
		jp.setLayout(new GridLayout(Const.MAX_HEIGHTS,Const.MAX_WIDTH,5,5));
		for(int i=0;i<Const.MAX_HEIGHTS;i++){
			for(int j=0;j<Const.MAX_WIDTH;j++){
				jp.add(new Px(Color.YELLOW));
			}
		}
		
		//Ϊ�������Ӽ���������
		this.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				try{
				switch(e.getKeyCode()){
				case KeyEvent.VK_UP:{
					pos.write((int)'u');
					break;
				}
				case KeyEvent.VK_DOWN:{
					pos.write((int)'d');
					break;
				}
				case KeyEvent.VK_LEFT:{
					pos.write((int)'l');
					break;
				}
				case KeyEvent.VK_RIGHT:{
					pos.write((int)'r');
					break;
				}
				}
				}catch(IOException ex){
					
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		//��Ӵ��ڼ�����
		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		this.setVisible(true);
	}
	
	public boolean reflect(){
		//TODO
		//�����ı�
		this.jl.setText("Score:"+this.data.score);
		//��ȡ���е��ߡ�����Ϣ��0�ǿգ�1���ߣ�2�ǵ�
		int[][] map=new int[Const.MAX_HEIGHTS][Const.MAX_WIDTH];
		for(Site s:this.data.snake){
			map[s.x][s.y]=1;
		}
		Site s=this.data.egg;
		map[s.x][s.y]=2;
		//ˢ��������
		this.invalidate();
		this.remove(jp);
		jp=new JPanel();
		jp.setLayout(new GridLayout(Const.MAX_HEIGHTS,Const.MAX_WIDTH,5,5));
		for(int i=0;i<Const.MAX_HEIGHTS;i++){
			for(int j=0;j<Const.MAX_WIDTH;j++){
				if(map[i][j]==1){
					jp.add(new Px(Color.BLUE));
				}else if(map[i][j]==2){
					jp.add(new Px(Color.RED));
				}else{
					jp.add(new Px(Color.YELLOW));
				}
			}
		}
		this.add(jp,BorderLayout.CENTER);
		this.validate();
		return true;
	}
}

class Px extends JPanel{
	public Px(Color c){
		this.setBackground(c);
	}
}
