package cobalt.snake;

import java.io.IOException;
import java.io.PipedInputStream;

public class Input extends Thread{
	private static char command='r';
	PipedInputStream pis=null;
	
	public Input(PipedInputStream pis) {
		super();
		Input.command='r';
		this.pis = pis;
	}

	public static char getCommand() {
		return command;
	}
	
	@Override
	public void run(){
		int i=0;
		try {
			//'z'��ֵС��255��Լ����255��ʾ������Ȼ����char��unicodeֱֵ�ӱ�ʾָ��
			while((i=pis.read())!=255){
				command=(char)i;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				pis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
