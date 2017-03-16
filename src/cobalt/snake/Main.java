package cobalt.snake;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main {
	public static void main(String[] args) throws IOException{
		PipedOutputStream pos=new PipedOutputStream();
		PipedInputStream pis=new PipedInputStream(pos);
		Input in=new Input(pis);
		Thread main=new Thread(){
			@Override
			public void run(){
				//TODO
				Data data=null;
				MainFrame mf=null;
				try{
					data=new Data();
					mf=new MainFrame(data,pos);
					for(;;){					
						mf.reflect();
						Thread.sleep(300);
						data.round();
					}
				}catch(GameOverException e){
					new gameOver(mf,"Game Over",data.score);
				}catch(ArrayIndexOutOfBoundsException e){
					new gameOver(mf,"Game Over",data.score);
				}catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					try {
						pis.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
				
		in.start();
		main.start();
	}
}

class gameOver extends JDialog{
	public gameOver(JFrame jf,String s,int score){
		super(jf,s);
		
		this.setVisible(true);
		this.setBounds(400,200,200,150);
		this.setTitle("Game Over");
		this.setLayout(new BorderLayout());
		this.add(new JLabel("Final Score:"+score),BorderLayout.CENTER);
		//添加“确定”按钮
		this.add(new JButton("确定"){
			//匿名内部类的构造代码块
			{
				this.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						new WindowEvent(jf,WindowEvent.WINDOW_CLOSING);
						System.exit(0);
					}
					
				});
			}
		},BorderLayout.SOUTH);
	}
}
